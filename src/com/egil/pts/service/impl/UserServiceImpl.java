package com.egil.pts.service.impl;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.poi.hssf.util.HSSFColor;
import org.hibernate.annotations.common.util.StringHelper;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.egil.pts.constants.PTSConstants;
import com.egil.pts.dao.domain.Announcements;
import com.egil.pts.dao.domain.Credential;
import com.egil.pts.dao.domain.CustomerAccounts;
import com.egil.pts.dao.domain.Location;
import com.egil.pts.dao.domain.PTSWorkingDays;
import com.egil.pts.dao.domain.Person;
import com.egil.pts.dao.domain.Pillar;
import com.egil.pts.dao.domain.Privilege;
import com.egil.pts.dao.domain.Project;
import com.egil.pts.dao.domain.Roles;
import com.egil.pts.dao.domain.Streams;
import com.egil.pts.dao.domain.Technologies;
import com.egil.pts.dao.domain.UserAccounts;
import com.egil.pts.dao.domain.UserNetworkCodes;
import com.egil.pts.dao.domain.UserPlatformCompetencyScore;
import com.egil.pts.dao.domain.UserPlatforms;
import com.egil.pts.dao.domain.UserProjects;
import com.egil.pts.dao.domain.UserRole;
import com.egil.pts.dao.domain.UserSkillScore;
import com.egil.pts.dao.domain.UserSkills;
import com.egil.pts.dao.domain.UserStableTeams;
import com.egil.pts.dao.domain.UserSupervisor;
import com.egil.pts.dao.domain.UserTimesheet;
import com.egil.pts.dao.domain.UserTypes;
import com.egil.pts.dao.domain.UserWeekOff;
import com.egil.pts.modal.BulkResponse;
import com.egil.pts.modal.DashboardUtilizationReport;
import com.egil.pts.modal.EssDetails;
import com.egil.pts.modal.LocationUserCount;
import com.egil.pts.modal.MonthHolidays;
import com.egil.pts.modal.Pagination;
import com.egil.pts.modal.PtsHolidays;
import com.egil.pts.modal.ResourceUtilization;
import com.egil.pts.modal.SearchSortContainer;
import com.egil.pts.modal.StableTeams;
import com.egil.pts.modal.SummaryResponse;
import com.egil.pts.modal.User;
import com.egil.pts.modal.UserAcctPillarAppContribution;
import com.egil.pts.modal.UserAppContribution;
import com.egil.pts.modal.UserCapacity;
import com.egil.pts.modal.UserComparator;
import com.egil.pts.modal.UserPillarAppContribution;
import com.egil.pts.service.UserService;
import com.egil.pts.service.common.BaseUIService;
import com.egil.pts.util.DesEncrypter;
import com.egil.pts.util.GenericExcel;
import com.egil.pts.util.GenericMail;
import com.egil.pts.util.Utility;

@Service("userService")
public class UserServiceImpl extends BaseUIService implements UserService {
	String months[] = { "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec" };
	private static DecimalFormat df2 = new DecimalFormat("#.##");

	@Override
	@Transactional
	public SummaryResponse<User> getUserSummary(Pagination pagination, SearchSortContainer searchSortObj,
			boolean isAdmin) throws Throwable {
		SummaryResponse<User> summary = new SummaryResponse<User>();

		summary.setTotalRecords(getUsersSummaryNew(null, searchSortObj).size());
		summary.setEnitities(getUsersSummaryNew(pagination, searchSortObj));
		if (searchSortObj.getStableTeamId() > 0 || searchSortObj.isSearchByStable()) {
			summary.setTotalRecords(getUsersSummaryNew(null, searchSortObj).size() + 1);
		}
		return summary;
	}

	@Override
	@Transactional
	public List<com.egil.pts.modal.UserStableTeams> getUserStableTeams(Long userId) throws Throwable {
		List<com.egil.pts.modal.UserStableTeams> data = daoManager.getUserDao().getUserStableTeams(userId);
		return data;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true)
	public List<User> getUsers(Pagination pagination, SearchSortContainer searchSortContainer) throws Throwable {
		List<User> userList = new ArrayList<User>();
		List<com.egil.pts.dao.domain.User> domainUserList = daoManager.getUserDao().getUsers(pagination,
				searchSortContainer);
		User modalUser = null;
		for (com.egil.pts.dao.domain.User domainUser : domainUserList) {
			modalUser = new User();
			convertDomainToModal(modalUser, domainUser, searchSortContainer.getStableTeamId());
			userList.add(modalUser);

		}
		if (searchSortContainer != null && searchSortContainer.getSidx() != null
				&& !searchSortContainer.getSidx().equals("") && searchSortContainer.getSord() != null
				&& !searchSortContainer.getSord().equals("")) {
			if (searchSortContainer.getSidx().equalsIgnoreCase("skillName")
					|| searchSortContainer.getSidx().equalsIgnoreCase("projectName")) {
				Collections.sort(userList,
						new UserComparator(searchSortContainer.getSord(), searchSortContainer.getSidx()));
			}
		}
		return userList;
	}

	@Override
	public List<EssDetails> getEssFeed(String month, Pagination pagination) throws Throwable {
		return daoManager.getUserDao().getEssFeed(month, pagination);
	}

	@Override
	public String getJSONStringOfUsers() throws Throwable {
		String returnValue = "";
		returnValue = -1 + ":" + "Please Select" + ";";
		for (User user : getUsers(null, null)) {
			returnValue = returnValue + user.getId() + ":" + user.getName() + ";";
		}
		return returnValue.substring(0, returnValue.length() - 1);
	}

	@Override
	public void getSupervisorMap(Map<Long, String> supervisorMap, Long supervisorId, boolean manageFlag)
			throws Throwable {
		supervisorMap.clear();
		SearchSortContainer searchSortContainer = new SearchSortContainer();
		searchSortContainer.setSupervisorMapFlag(true);
		if (manageFlag) {
			searchSortContainer.setSearchSupervisor(supervisorId + "");
		}
		supervisorMap.put(-1L, "All");
		for (User user : getUsers(null, searchSortContainer)) {
			if (user.getRole() != null && !user.getRole().equalsIgnoreCase("USER")) {
				supervisorMap.put(user.getId(), (user.getName()));
			}
		}
	}

	@Override
	public void getSuperviosrs(Map<Long, String> supervisorMap) throws Throwable {
		supervisorMap.clear();
		List<UserSupervisor> supervisors = daoManager.getUserDao().getSuperviosrs();
		for (UserSupervisor user : supervisors) {
			supervisorMap.put(user.getSupervisor().getId(), (user.getSupervisor().getPersonalInfo().getName()));
		}
	}

	@Override
	public void getUsersToBackFill(Map<String, String> userToBackFillMap) throws Throwable {
		userToBackFillMap.clear();
		List<com.egil.pts.dao.domain.User> domainUsers = daoManager.getUserDao().getUsersToBackFill();
		for (com.egil.pts.dao.domain.User user : domainUsers) {
			userToBackFillMap.put(user.getPersonalInfo().getName(), (user.getPersonalInfo().getName()));
		}
	}

	@Override
	@Transactional
	public void createUser(User modelUser) throws Throwable {
		com.egil.pts.dao.domain.User domainUser = new com.egil.pts.dao.domain.User();
		convertModalToDomain(modelUser, domainUser);
		try {

			com.egil.pts.dao.domain.User s = daoManager.getUserDao().save(domainUser);
			daoManager.getUserDao().flush();
			if (modelUser.getStatus().contains("Interns")) {
				daoManager.getUserNetworkCodeDao().addNWDataforinterns(s.getId());
				daoManager.getUserNetworkCodeDao().flush();
			}
		} catch (Throwable e) {
			if (e instanceof ConstraintViolationException) {
				throw new Exception("DUP_USER");
			}

		}
		modelUser.setId(domainUser.getId());
		saveUserAppSkillDetails(modelUser, "add", domainUser);
		daoManager.getUserDao().flush();
	}

	@Override
	@Transactional
	public void modifyUser(User modelUser) throws Throwable {
		com.egil.pts.dao.domain.User domainUser = (com.egil.pts.dao.domain.User) this.daoManager.getUserDao()
				.get(modelUser.getId());
		convertModalToDomain(modelUser, domainUser);
		saveUserAppSkillDetails(modelUser, "edit", domainUser);
		daoManager.getUserDao().save(domainUser);
		daoManager.getUserDao().flush();
	}

	private void saveUserAppSkillDetails(com.egil.pts.modal.User modelUser, String opr,
			com.egil.pts.dao.domain.User domainUser) throws Throwable {

		List<Long> removedProjects = new ArrayList<Long>();
		List<Long> removedPlatforms = new ArrayList<Long>();
		Map<Long, UserProjects> existingProjects = new LinkedHashMap<Long, UserProjects>();
		if ((domainUser.getUserProjects() != null) && (domainUser.getUserProjects().size() > 0)) {
			for (UserProjects userProjects : domainUser.getUserProjects()) {
				existingProjects.put(userProjects.getProject().getId(), userProjects);
			}
		}

		Map<Long, UserPlatforms> existingPlatforms = new LinkedHashMap<Long, UserPlatforms>();
		if ((domainUser.getUserPlatforms() != null) && (domainUser.getUserProjects().size() > 0)) {
			for (UserPlatforms userPlatforms : domainUser.getUserPlatforms()) {
				existingPlatforms.put(userPlatforms.getPlatform().getId(), userPlatforms);
			}
		}

		if ((modelUser.getUserAccounts() != null) && (modelUser.getUserAccounts().size() > 0)) {
			List<UserAccounts> userAccountList = new ArrayList<UserAccounts>();
			UserAccounts userAccount = null;
			for (Long accountId : modelUser.getUserAccounts()) {
				userAccount = new UserAccounts();
				com.egil.pts.dao.domain.User user = new com.egil.pts.dao.domain.User();
				user.setId(modelUser.getId());
				userAccount.setUser(user);

				CustomerAccounts custAccount = new CustomerAccounts();
				custAccount.setId(accountId);
				userAccount.setAccount(custAccount);
				userAccountList.add(userAccount);
			}
			this.daoManager.getUserAccountsDao().saveUserAccounts(userAccountList);
		}

		if ((modelUser.getUserTechnologies() != null) && (modelUser.getUserTechnologies().size() > 0)) {
			List<UserSkills> userSkillList = new ArrayList<UserSkills>();
			UserSkills userSkill = null;
			UserSkillScore uss = null;
			for (Long techId : modelUser.getUserTechnologies()) {
				userSkill = new UserSkills();
				com.egil.pts.dao.domain.User user = new com.egil.pts.dao.domain.User();
				user.setId(modelUser.getId());
				userSkill.setUser(user);

				Technologies technology = new Technologies();
				technology.setId(techId);
				if ((modelUser.getPrimarySkillId() != null) && (modelUser.getPrimarySkillId() == techId)) {
					userSkill.setPrimaryFlag("Y");
					if (opr != null && opr.equalsIgnoreCase("add")) {
						Long selectedYear = Long.valueOf(Calendar.getInstance().get(Calendar.YEAR));
						String halfYear = (Calendar.getInstance().get(Calendar.MONTH) > 5) ? "2H" : "1H";
						uss = new UserSkillScore();
						uss.setUserSkill(userSkill);
						uss.setUser(user);
						uss.setTechnology(technology);
						uss.setYear(selectedYear);
						uss.setYearHalf(halfYear);
						uss.setTechScore(modelUser.getTechScore());
					}
				} else {
					userSkill.setPrimaryFlag(null);
				}
				userSkill.setTechnology(technology);
				userSkillList.add(userSkill);
			}
			this.daoManager.getUserSkillsDao().saveUserSkills(userSkillList);
			if (uss != null) {
				daoManager.getUserSkillScoreDao().save(uss);
			}
		}

		UserProjects userProject = null;
		List<UserProjects> userProjectList = new ArrayList<UserProjects>();

		Pillar pillar = null;
		List<UserPlatforms> userPlatformsList = new ArrayList<UserPlatforms>();
		UserPlatforms userPlatform = null;
		com.egil.pts.dao.domain.User user = null;

		for (UserAcctPillarAppContribution userAcctPillarAppContribution : modelUser
				.getUserAcctPillarAppContribution()) {

			for (UserPillarAppContribution userPillarAppContribution : userAcctPillarAppContribution.getUserPACList()) {

				boolean hasContribution = false;
				float pillarContribution = 0.0f;
				if ((opr != null) && (opr.equalsIgnoreCase("add"))) {
					userPlatform = new UserPlatforms();
					user = new com.egil.pts.dao.domain.User();
					user.setId(modelUser.getId());
					userPlatform.setUser(user);
					pillar = new Pillar();
					pillar.setId(userPillarAppContribution.getPillarId());
					userPlatform.setPlatform(pillar);
				} else {
					if (existingPlatforms.containsKey(userPillarAppContribution.getPillarId())) {
						userPlatform = existingPlatforms.get(userPillarAppContribution.getPillarId());
						user = userPlatform.getUser();
					} else {
						userPlatform = new UserPlatforms();
						user = new com.egil.pts.dao.domain.User();
						user.setId(modelUser.getId());
						userPlatform.setUser(user);
						pillar = new Pillar();
						pillar.setId(userPillarAppContribution.getPillarId());
						userPlatform.setPlatform(pillar);
					}

				}

				for (UserAppContribution userAppContribution : userPillarAppContribution.getUserACList()) {
					if (userAppContribution.getAppContribution() > 0.0f) {
						hasContribution = true;
						if ((opr != null) && (opr.equalsIgnoreCase("add"))) {
							userProject = new UserProjects();
							userProject.setUser(user);

							Project project = new Project();
							project.setId(userAppContribution.getAppId());

							userProject.setProject(project);
							userProject.setContribution(userAppContribution.getAppContribution());

							userProjectList.add(userProject);

							pillarContribution = pillarContribution + userAppContribution.getAppContribution();
						} else {
							if (existingProjects.containsKey(userAppContribution.getAppId())) {
								existingProjects.get(userAppContribution.getAppId())
										.setContribution(userAppContribution.getAppContribution());
								userProjectList.add(existingProjects.get(userAppContribution.getAppId()));
							} else {
								userProject = new UserProjects();
								userProject.setUser(user);

								Project project = new Project();
								project.setId(userAppContribution.getAppId());

								userProject.setProject(project);
								userProject.setContribution(userAppContribution.getAppContribution());

								userProjectList.add(userProject);

							}
							pillarContribution = pillarContribution + userAppContribution.getAppContribution();
						}
					} else {
						if (existingProjects.containsKey(userAppContribution.getAppId())) {
							removedProjects.add(userAppContribution.getAppId());
						}
					}
				}
				if (pillarContribution > 0.0f) {
					userPlatform.setContribution(pillarContribution);
					if (hasContribution) {
						userPlatformsList.add(userPlatform);
					}
				} else {
					if (existingPlatforms.containsKey(userPillarAppContribution.getPillarId())) {
						removedPlatforms.add(userPillarAppContribution.getPillarId());
					}
				}
			}
		}

		if (userPlatformsList != null && userPlatformsList.size() > 0) {
			this.daoManager.getUserPlatformDao().saveUserPlatforms(userPlatformsList);
		}

		if (userProjectList != null && userProjectList.size() > 0) {
			this.daoManager.getUserProjectsDao().saveUserProjects(userProjectList);
		}

		if ((opr != null) && (opr.equalsIgnoreCase("edit"))) {

			if ((modelUser.getRemovedUserAccounts() != null) && (modelUser.getRemovedUserAccounts().size() > 0)) {
				this.daoManager.getUserAccountsDao().removeUserAccounts(modelUser.getId(),
						modelUser.getRemovedUserAccounts());
			}

			if ((modelUser.getRemovedUserTechnologies() != null)
					&& (modelUser.getRemovedUserTechnologies().size() > 0)) {
				this.daoManager.getUserSkillsDao().removeUserSkills(modelUser.getId(),
						modelUser.getRemovedUserTechnologies());
			}
			if ((removedProjects != null) && (removedProjects.size() > 0)) {
				this.daoManager.getUserProjectsDao().removeUserProjects(modelUser.getId(), removedProjects);
			}
			if ((removedPlatforms != null) && (removedPlatforms.size() > 0)) {
				this.daoManager.getUserPlatformDao().removeUserPlatforms(modelUser.getId(), removedPlatforms);
			}
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public Integer deleteUsers(List<Long> userIdList) throws Throwable {
		return daoManager.getUserDao().deleteUsers(userIdList);
	}

	@Override
	@Transactional
	public User getUser(Long id) throws Throwable {
		User modalUser = new User();
		com.egil.pts.dao.domain.User domainUser = daoManager.getUserDao().getUser(id);
		convertDomainToModal(modalUser, domainUser, -1L);
		return modalUser;
	}

	@Override
	@Transactional
	public User getUser(String userName, String password) throws Throwable {
		User modalUser = new User();
		com.egil.pts.dao.domain.User domainUser = daoManager.getUserDao().getUser(userName, password);
		convertDomainToModal(modalUser, domainUser, -1L);
		return modalUser;
	}

	@SuppressWarnings("unused")
	private void convertModalToDomain_old(User modelUser, com.egil.pts.dao.domain.User domainUser) {
		if (domainUser != null && modelUser != null) {
			if (modelUser.getId() != null) {
				domainUser.setId(modelUser.getId());
			}
			Person person = new Person();
			if (modelUser.getName() != null && !modelUser.getName().equals("")) {
				person.setName(modelUser.getName());
			}

			if (modelUser.getFirstName() != null && !modelUser.getFirstName().equals("")) {
				person.setFirstName(modelUser.getFirstName());
			}
			if (modelUser.getMiddleName() != null && !modelUser.getMiddleName().equals("")) {
				person.setMiddleName(modelUser.getMiddleName());
			}
			if (modelUser.getLastName() != null && !modelUser.getLastName().equals("")) {
				person.setLastName(modelUser.getLastName());
			}
			if (modelUser.getEmail() != null && !modelUser.getEmail().equals("")) {
				person.setEmail(modelUser.getEmail());
			}
			domainUser.setPersonalInfo(person);

			if (modelUser.getStatus() != null) {
				domainUser.setStatus(modelUser.getStatus());
			}

			if (modelUser.getComments() != null) {
				domainUser.setComments(modelUser.getComments());
			}
			if (modelUser.getReleaseComments() != null) {
				domainUser.setReleaseComments(modelUser.getReleaseComments());
			}
			Credential credential = null;
			if (domainUser.getCredentials() != null && (domainUser.getCredentials().getUserName() != null
					&& !domainUser.getCredentials().getUserName().equals(""))) {
				credential = domainUser.getCredentials();
			} else {

				credential = new Credential();
			}
			if (modelUser.getUserName() != null && !modelUser.getUserName().equals("")) {
				credential.setUserName(modelUser.getUserName());
			}
			if (credential.getPassword() == null
					|| (credential.getPassword() != null && credential.getPassword().equals(""))) {
				credential.setPassword(PTSConstants.USER_PASSWORD);
			}
			domainUser.setCredentials(credential);

			UserRole userRole = null;
			if (domainUser.getUserRole() != null && domainUser.getUserRole().getId() != null) {
				userRole = domainUser.getUserRole();
			} else {
				userRole = new UserRole();
				if (modelUser.getUserRoleId() != null) {
					userRole.setId(modelUser.getUserRoleId());
				}
			}
			if (modelUser.getRoleId() != null) {
				userRole.setUser(domainUser);
				Roles role = new Roles();
				role.setId(modelUser.getRoleId());
				userRole.setRole(role);
			}
			if (modelUser.getPrivilegeId() != null) {
				Privilege privilege = new Privilege();
				privilege.setId(modelUser.getPrivilegeId());
				userRole.setPrivilege(privilege);
			}

			if (modelUser.getUserTypeId() != null) {
				UserTypes userType = new UserTypes();
				userType.setId(modelUser.getUserTypeId());
				userRole.setUserType(userType);
			}

			domainUser.setUserRole(userRole);

			UserSupervisor userSupervisor = null;
			if (domainUser.getUserSupervisor() != null && domainUser.getUserSupervisor().getId() != null) {
				userSupervisor = domainUser.getUserSupervisor();
			} else {
				userSupervisor = new UserSupervisor();
			}

			if (modelUser.getSupervisorId() != null) {
				com.egil.pts.dao.domain.User user = new com.egil.pts.dao.domain.User();
				if (modelUser.getSupervisorId() != -1) {
					user.setId(modelUser.getSupervisorId());
					userSupervisor.setSupervisor(user);
					userSupervisor.setUser(domainUser);
				}
			}
			domainUser.setUserSupervisor(userSupervisor);

			if (modelUser.getCreatedBy() != null && !modelUser.getCreatedBy().equals("")) {
				domainUser.setCreatedBy(modelUser.getCreatedBy());
			}

			if (modelUser.getCreatedDate() != null) {
				domainUser.setCreatedDate(modelUser.getCreatedDate());
			}

			if (modelUser.getUpdatedBy() != null && !modelUser.getUpdatedBy().equals("")) {
				domainUser.setUpdatedBy(modelUser.getUpdatedBy());
			}
			if (modelUser.getUpdatedDate() != null) {
				domainUser.setUpdatedDate(modelUser.getUpdatedDate());
			}

			if (modelUser.getLastLogin() != null) {
				domainUser.setLastLogin(modelUser.getLastLogin());
			}

			List<Long> removedPlatforms = new ArrayList<Long>();
			if (domainUser.getUserPlatforms() != null && domainUser.getUserPlatforms().size() > 0) {
				for (UserPlatforms platform : domainUser.getUserPlatforms()) {
					if (modelUser.getUserPlatforms() != null && modelUser.getUserPlatforms().size() > 0) {
						if (modelUser.getUserPlatforms().contains(platform.getPlatform().getId())) {
							modelUser.getUserPlatforms().remove(platform.getPlatform().getId());
						} else {
							removedPlatforms.add(platform.getPlatform().getId());
						}
					} else {
						removedPlatforms.add(platform.getPlatform().getId());
					}
				}
				modelUser.setRemovedUserPlatforms(removedPlatforms);
			}

			List<Long> removedSkills = new ArrayList<Long>();
			if (domainUser.getUserSkills() != null && domainUser.getUserSkills().size() > 0) {
				for (UserSkills skill : domainUser.getUserSkills()) {
					if (modelUser.getUserTechnologies() != null && modelUser.getUserTechnologies().size() > 0) {
						if (modelUser.getUserTechnologies().contains(skill.getTechnology().getId())) {
							modelUser.getUserTechnologies().remove(skill.getTechnology().getId());
						} else {
							removedSkills.add(skill.getTechnology().getId());
						}
					} else {
						removedSkills.add(skill.getTechnology().getId());
					}
				}
				modelUser.setRemovedUserTechnologies(removedSkills);
			}

			if (modelUser.getLocation() != null) {
				domainUser.setLocationName(modelUser.getLocation());
			}
			if (modelUser.getPhone() != null) {
				domainUser.setPhone(modelUser.getPhone());
			}
			if (modelUser.getMobile() != null) {
				domainUser.setMobile(modelUser.getMobile());
			}

			if (modelUser.getPersonalNo() != null) {
				domainUser.setPersonalNo(modelUser.getPersonalNo());
			}

			if (modelUser.getAllocation() != null) {
				domainUser.setAllocation(modelUser.getAllocation());
			}

			if (modelUser.getVzid() != null) {
				domainUser.setVzid(modelUser.getVzid());
			}

			if (modelUser.getMhrCategory() != null) {
				domainUser.setMhrCategory(modelUser.getMhrCategory());
			}

			if (modelUser.getEriProjNo() != null) {
				domainUser.setEriProjNo(modelUser.getEriProjNo());
			}

			if (modelUser.getJobStage() != null) {
				domainUser.setJobStage(modelUser.getJobStage());
			}

			if (modelUser.getJobDescription() != null) {
				domainUser.setJobDescription(modelUser.getJobDescription());
			}
			domainUser.setDoj(modelUser.getDoj());
			domainUser.setDateOfBilling(modelUser.getDateOfBilling());
			domainUser.setReleseDate(modelUser.getReleseDate());
			domainUser.setResignDate(modelUser.getResignDate());
			if (modelUser.getCertifications() != null) {
				domainUser.setCertifications(modelUser.getCertifications());
			}
			if (modelUser.getStream() != null) {
				com.egil.pts.dao.domain.Streams stream = new com.egil.pts.dao.domain.Streams();
				stream.setId(modelUser.getStream());
				domainUser.setStream(stream);
			}
			domainUser.setBackFillOf(modelUser.getBackFillOff());
		}

	}

	@SuppressWarnings("unused")
	private void convertDomainToModal_old(User modelUser, com.egil.pts.dao.domain.User domainUser) {
		if (domainUser != null && modelUser != null) {
			if (domainUser.getId() != null) {
				modelUser.setId(domainUser.getId());
			}
			if (domainUser.getPersonalInfo() != null) {
				modelUser.setName(domainUser.getPersonalInfo().getName());
				modelUser.setFirstName(domainUser.getPersonalInfo().getFirstName());
				modelUser.setMiddleName(domainUser.getPersonalInfo().getMiddleName());
				modelUser.setLastName(domainUser.getPersonalInfo().getLastName());
				modelUser.setEmail(domainUser.getPersonalInfo().getEmail());
			}

			if (domainUser.getStatus() != null) {
				modelUser.setStatus(domainUser.getStatus());
			}

			if (domainUser.getComments() != null) {
				modelUser.setComments(domainUser.getComments());
			}
			if (domainUser.getReleaseComments() != null) {
				modelUser.setReleaseComments(domainUser.getReleaseComments());
			}
			if (domainUser.getCredentials() != null) {
				modelUser.setUserName(domainUser.getCredentials().getUserName());
			}

			if (domainUser.getUserRole() != null) {
				modelUser.setUserRoleId(domainUser.getUserRole().getId());
				if (domainUser.getUserRole().getPrivilege() != null) {
					modelUser.setPrivilege(domainUser.getUserRole().getPrivilege().getPrivName());
					modelUser.setPrivilegeId(domainUser.getUserRole().getPrivilege().getId());
				}
				if (domainUser.getUserRole().getRole() != null) {
					modelUser.setRole(domainUser.getUserRole().getRole().getRoleName());
					modelUser.setRoleId(domainUser.getUserRole().getRole().getId());
				}
				if (domainUser.getUserRole().getUserType() != null) {
					modelUser.setUserType(domainUser.getUserRole().getUserType().getUserType());
					modelUser.setUserTypeId(domainUser.getUserRole().getUserType().getId());
				}
			}
			if (domainUser.getUserSupervisor() != null && domainUser.getUserSupervisor().getSupervisor() != null) {
				modelUser.setUserSupervisorId(domainUser.getUserSupervisor().getId());
				if (domainUser.getUserSupervisor().getSupervisor().getPersonalInfo() != null) {
					modelUser.setSupervisor(domainUser.getUserSupervisor().getSupervisor().getPersonalInfo().getName());
				}
				modelUser.setSupervisorId(domainUser.getUserSupervisor().getSupervisor().getId());
			}

			if (domainUser.getCreatedBy() != null) {
				modelUser.setCreatedBy(domainUser.getCreatedBy());
			}

			if (domainUser.getCreatedDate() != null) {
				modelUser.setCreatedDate(domainUser.getCreatedDate());
			}

			if (domainUser.getUpdatedBy() != null) {
				modelUser.setUpdatedBy(domainUser.getUpdatedBy());
			}
			if (domainUser.getUpdatedDate() != null) {
				modelUser.setUpdatedDate(domainUser.getUpdatedDate());
			}

			if (domainUser.getUserPlatforms() != null && domainUser.getUserPlatforms().size() > 0) {
				for (UserPlatforms platform : domainUser.getUserPlatforms()) {
					modelUser.addPlatforms(platform.getPlatform().getId());
				}
				modelUser.setPlatformName(domainUser.getUserPlatforms().get(0).getPlatform().getPillarName());
			}

			if (domainUser.getUserSkills() != null && domainUser.getUserSkills().size() > 0) {
				for (UserSkills skill : domainUser.getUserSkills()) {
					modelUser.addTechnologies(skill.getTechnology().getId());
				}
				modelUser.setSkillName(domainUser.getUserSkills().get(0).getTechnology().getTechnologyName());
			}

			/*
			 * if (domainUser.getUserNetworkCodes() != null &&
			 * domainUser.getUserNetworkCodes().size() > 0) { modelUser.setProjectName(
			 * domainUser.getUserNetworkCodes().get(0).getNetworkCodes().getProject().
			 * getProjectName()); }
			 */

			if (domainUser.getLocation() != null) {
				modelUser.setLocation(domainUser.getLocationName());
			}
			if (domainUser.getPhone() != null) {
				modelUser.setPhone(domainUser.getPhone());
			}
			if (domainUser.getMobile() != null) {
				modelUser.setMobile(domainUser.getMobile());
			}

			if (domainUser.getPersonalNo() != null) {
				modelUser.setPersonalNo(domainUser.getPersonalNo());
			}

			if (domainUser.getAllocation() != null) {
				modelUser.setAllocation(domainUser.getAllocation());
			}

			if (domainUser.getVzid() != null) {
				modelUser.setVzid(domainUser.getVzid());
			}

			if (domainUser.getMhrCategory() != null) {
				modelUser.setMhrCategory(domainUser.getMhrCategory());
			}

			if (domainUser.getEriProjNo() != null) {
				modelUser.setEriProjNo(domainUser.getEriProjNo());
			}

			if (domainUser.getDoj() != null) {
				modelUser.setDoj(domainUser.getDoj());
			}

			if (domainUser.getJobStage() != null) {
				modelUser.setJobStage(domainUser.getJobStage());
			}

			if (domainUser.getJobDescription() != null) {
				modelUser.setJobDescription(domainUser.getJobDescription());
			}

			if (domainUser.getDateOfBilling() != null) {
				modelUser.setDateOfBilling(domainUser.getDateOfBilling());
			}
			if (domainUser.getReleseDate() != null) {
				modelUser.setReleseDate(domainUser.getReleseDate());
			}
			if (domainUser.getResignDate() != null) {
				modelUser.setResignDate(domainUser.getResignDate());
			}
			if (domainUser.getCertifications() != null) {
				modelUser.setCertifications(domainUser.getCertifications());
			}
			if (domainUser.getStream() != null) {
				modelUser.setStream(domainUser.getStream().getId());
				modelUser.setStreamName(domainUser.getStream().getStreamName());
			}
			if (domainUser.getBackFillOf() != null) {
				modelUser.setBackFillOff(domainUser.getBackFillOf());
			}
		}
	}

	private void convertModalToDomain(com.egil.pts.modal.User modelUser, com.egil.pts.dao.domain.User domainUser) {
		if ((domainUser != null) && (modelUser != null)) {
			if (modelUser.getId() != null) {
				domainUser.setId(modelUser.getId());
			}

			if (modelUser.getStableTeams() != null && modelUser.getId() != null) {
				List<UserStableTeams> userStableTeams = new ArrayList<UserStableTeams>();
				for (StableTeams s : modelUser.getStableTeams()) {
					UserStableTeams stableTeam = new UserStableTeams();
					stableTeam.setContribution(s.getValue());
					stableTeam.setStableTeamId(s.getId());
					stableTeam.setUserId(modelUser.getId());
					if (s.getId() != null)
						stableTeam.setId(s.getId());
					userStableTeams.add(stableTeam);
				}

				domainUser.setUserStableTeams(userStableTeams);
			}

			Person person = new Person();
			if ((modelUser.getName() != null) && (!modelUser.getName().equals(""))) {
				person.setName(modelUser.getName());
			}
			if ((modelUser.getFirstName() != null) && (!modelUser.getFirstName().equals(""))) {
				person.setFirstName(modelUser.getFirstName());
			}
			if ((modelUser.getMiddleName() != null) && (!modelUser.getMiddleName().equals(""))) {
				person.setMiddleName(modelUser.getMiddleName());
			}
			if ((modelUser.getLastName() != null) && (!modelUser.getLastName().equals(""))) {
				person.setLastName(modelUser.getLastName());
			}
			if ((modelUser.getEmail() != null) && (!modelUser.getEmail().equals(""))) {
				person.setEmail(modelUser.getEmail());
			}
			domainUser.setPersonalInfo(person);
			if (modelUser.getStatus() != null) {
				domainUser.setStatus(modelUser.getStatus());
			}

			if (modelUser.getComments() != null) {
				domainUser.setComments(modelUser.getComments());
			}
			if (modelUser.getReleaseComments() != null) {
				domainUser.setReleaseComments(modelUser.getReleaseComments());
			}
			Credential credential = null;
			if ((domainUser.getCredentials() != null) && (domainUser.getCredentials().getUserName() != null)
					&& (!domainUser.getCredentials().getUserName().equals(""))) {
				credential = domainUser.getCredentials();
			} else {
				credential = new Credential();
			}
			if ((modelUser.getUserName() != null) && (!modelUser.getUserName().equals(""))) {
				credential.setUserName(modelUser.getUserName());
			}
			if ((credential.getPassword() == null)
					|| ((credential.getPassword() != null) && (credential.getPassword().equals("")))) {
				credential.setPassword(PTSConstants.USER_PASSWORD);
			}
			domainUser.setCredentials(credential);

			UserRole userRole = null;
			if ((domainUser.getUserRole() != null) && (domainUser.getUserRole().getId() != null)) {
				userRole = domainUser.getUserRole();
			} else {
				userRole = new UserRole();
				if (modelUser.getUserRoleId() != null) {
					userRole.setId(modelUser.getUserRoleId());
				}
			}
			if (modelUser.getRoleId() != null) {
				userRole.setUser(domainUser);
				Roles role = new Roles();
				role.setId(modelUser.getRoleId());
				userRole.setRole(role);
			}
			if (modelUser.getPrivilegeId() != null) {
				Privilege privilege = new Privilege();
				privilege.setId(modelUser.getPrivilegeId());
				userRole.setPrivilege(privilege);
			}
			if (modelUser.getUserTypeId() != null) {
				UserTypes userType = new UserTypes();
				userType.setId(modelUser.getUserTypeId());
				userRole.setUserType(userType);
			}
			domainUser.setUserRole(userRole);

			UserSupervisor userSupervisor = null;
			if ((domainUser.getUserSupervisor() != null) && (domainUser.getUserSupervisor().getId() != null)) {
				userSupervisor = domainUser.getUserSupervisor();
			} else {
				userSupervisor = new UserSupervisor();
			}
			if (modelUser.getSupervisorId() != null) {
				com.egil.pts.dao.domain.User user = new com.egil.pts.dao.domain.User();
				if (modelUser.getSupervisorId().longValue() != -1L) {
					user.setId(modelUser.getSupervisorId());
					userSupervisor.setSupervisor(user);
					userSupervisor.setUser(domainUser);
				}
			}
			domainUser.setUserSupervisor(userSupervisor);
			if ((modelUser.getCreatedBy() != null) && (!modelUser.getCreatedBy().equals(""))) {
				domainUser.setCreatedBy(modelUser.getCreatedBy());
			}
			if (modelUser.getCreatedDate() != null) {
				domainUser.setCreatedDate(modelUser.getCreatedDate());
			}
			if ((modelUser.getUpdatedBy() != null) && (!modelUser.getUpdatedBy().equals(""))) {
				domainUser.setUpdatedBy(modelUser.getUpdatedBy());
			}
			if (modelUser.getUpdatedDate() != null) {
				domainUser.setUpdatedDate(modelUser.getUpdatedDate());
			}
			if (modelUser.getLastLogin() != null) {
				domainUser.setLastLogin(modelUser.getLastLogin());
			}

			domainUser.setTimesheetFlag(modelUser.getTimesheetFlag());

			List<Long> removedAccounts = new ArrayList<Long>();
			if ((domainUser.getUserAccounts() != null) && (domainUser.getUserAccounts().size() > 0)) {
				for (UserAccounts userAccount : domainUser.getUserAccounts()) {
					if ((modelUser.getUserAccounts() != null) && (modelUser.getUserAccounts().size() > 0)) {
						if (modelUser.getUserAccounts().contains(userAccount.getAccount().getId())) {
							modelUser.getUserAccounts().remove(userAccount.getAccount().getId());
						} else {
							removedAccounts.add(userAccount.getAccount().getId());
						}
					} else {
						removedAccounts.add(userAccount.getAccount().getId());
					}
				}
				modelUser.setRemovedUserAccounts(removedAccounts);
			}

			List<Long> removedSkills = new ArrayList<Long>();
			if ((domainUser.getUserSkills() != null) && (domainUser.getUserSkills().size() > 0)) {
				for (UserSkills userSkill : domainUser.getUserSkills()) {
					if ((modelUser.getUserTechnologies() != null) && (modelUser.getUserTechnologies().size() > 0)) {
						if (modelUser.getUserTechnologies().contains(userSkill.getTechnology().getId())) {
							if (modelUser.getPrimarySkillId().equals(userSkill.getTechnology().getId())) {
								userSkill.setPrimaryFlag("Y");
							} else {
								userSkill.setPrimaryFlag(null);
							}
							modelUser.getUserTechnologies().remove(userSkill.getTechnology().getId());
						} else {
							removedSkills.add(userSkill.getTechnology().getId());
						}
					} else {
						removedSkills.add(userSkill.getTechnology().getId());
					}
				}
				modelUser.setRemovedUserTechnologies(removedSkills);
			}
			if (modelUser.getLocation() != null) {
				domainUser.setLocationName(modelUser.getLocation());
			}

			if (modelUser.getLocationId() != null) {
				Location loc = new Location();
				loc.setId(modelUser.getLocationId());
				domainUser.setLocation(loc);
			}

			if (modelUser.getPhone() != null) {
				domainUser.setPhone(modelUser.getPhone());
			}
			if (modelUser.getMobile() != null) {
				domainUser.setMobile(modelUser.getMobile());
			}
			if (modelUser.getPersonalNo() != null) {
				domainUser.setPersonalNo(modelUser.getPersonalNo());
			}
			if (modelUser.getAllocation() != null) {
				domainUser.setAllocation(modelUser.getAllocation());
			}
			if (modelUser.getVzid() != null) {
				domainUser.setVzid(modelUser.getVzid());
			}

			if (modelUser.getMhrCategory() != null) {
				domainUser.setMhrCategory(modelUser.getMhrCategory());
			}

			if (modelUser.getEriProjNo() != null) {
				domainUser.setEriProjNo(modelUser.getEriProjNo());
			}
			if (modelUser.getJobStage() != null) {
				domainUser.setJobStage(modelUser.getJobStage());
			}
			if (modelUser.getJobDescription() != null) {
				domainUser.setJobDescription(modelUser.getJobDescription());
			}
			domainUser.setDoj(modelUser.getDoj());
			domainUser.setDateOfBilling(modelUser.getDateOfBilling());
			domainUser.setReleseDate(modelUser.getReleseDate());
			domainUser.setResignDate(modelUser.getResignDate());
			if (modelUser.getCertifications() != null) {
				domainUser.setCertifications(modelUser.getCertifications());
			}
			if (modelUser.getStream() != null) {
				Streams stream = new Streams();
				stream.setId(modelUser.getStream());
				domainUser.setStream(stream);
			}
			domainUser.setBackFillOf(modelUser.getBackFillOff());
		}
	}

	private void convertDomainToModal(com.egil.pts.modal.User modelUser, com.egil.pts.dao.domain.User domainUser,
			long stableTeamId) {
		List<com.egil.pts.modal.StableTeams> stableTeams = new ArrayList<com.egil.pts.modal.StableTeams>();
		if ((domainUser != null) && (modelUser != null)) {
			float stableContribution = 0.0F;
			if (domainUser.getUserStableTeams().size() > 0) {

				for (UserStableTeams st : domainUser.getUserStableTeams()) {

					if (st.getId() == null)
						continue;

					if (stableTeamId == -1L) {
						StableTeams stable = new StableTeams();
						stable.setId(st.getId());
						stable.setValue(st.getContribution());
						stable.setUserId(st.getUserId());
						stable.setTeamName(st.getStableTeam().getTeamName());
						stable.setProject(st.getStableTeam().getProjectId());
						stableTeams.add(stable);
						stableContribution += st.getContribution();
					} else {
						if (st.getId().longValue() == st.getId()) {
							StableTeams stable = new StableTeams();
							stable.setId(st.getId());
							stable.setValue(st.getContribution());
							stable.setUserId(st.getUserId());
							stable.setTeamName(st.getStableTeam().getTeamName());
							stable.setProject(st.getStableTeam().getProjectId());
							stableTeams.add(stable);
							stableContribution += st.getContribution();
						}
						stableContribution += st.getContribution();
						break;
					}

				}

			}
			modelUser.setStableTeams(stableTeams);

			if (domainUser.getId() != null) {
				modelUser.setId(domainUser.getId());
			}
			if (domainUser.getPersonalInfo() != null) {
				modelUser.setName(domainUser.getPersonalInfo().getName());
				modelUser.setFirstName(domainUser.getPersonalInfo().getFirstName());
				modelUser.setMiddleName(domainUser.getPersonalInfo().getMiddleName());
				modelUser.setLastName(domainUser.getPersonalInfo().getLastName());
				modelUser.setEmail(domainUser.getPersonalInfo().getEmail());
			}
			if (domainUser.getStatus() != null) {
				modelUser.setStatus(domainUser.getStatus());
			}
			if (domainUser.getComments() != null) {
				modelUser.setComments(domainUser.getComments());
			}
			if (domainUser.getReleaseComments() != null) {
				modelUser.setReleaseComments(domainUser.getReleaseComments());
			}
			if (domainUser.getCredentials() != null) {
				modelUser.setUserName(domainUser.getCredentials().getUserName());
			}
			if (domainUser.getUserRole() != null) {
				modelUser.setUserRoleId(domainUser.getUserRole().getId());
				if (domainUser.getUserRole().getPrivilege() != null) {
					modelUser.setPrivilege(domainUser.getUserRole().getPrivilege().getPrivName());
					modelUser.setPrivilegeId(domainUser.getUserRole().getPrivilege().getId());
				}
				if (domainUser.getUserRole().getRole() != null) {
					modelUser.setRole(domainUser.getUserRole().getRole().getRoleName());
					modelUser.setRoleId(domainUser.getUserRole().getRole().getId());
				}
				if (domainUser.getUserRole().getUserType() != null) {
					modelUser.setUserType(domainUser.getUserRole().getUserType().getUserType());
					modelUser.setUserTypeId(domainUser.getUserRole().getUserType().getId());
				}
			}
			if ((domainUser.getUserSupervisor() != null) && (domainUser.getUserSupervisor().getSupervisor() != null)) {
				modelUser.setUserSupervisorId(domainUser.getUserSupervisor().getId());
				if (domainUser.getUserSupervisor().getSupervisor().getPersonalInfo() != null) {
					modelUser.setSupervisor(domainUser.getUserSupervisor().getSupervisor().getPersonalInfo().getName());
				}
				modelUser.setSupervisorId(domainUser.getUserSupervisor().getSupervisor().getId());
			}
			if (domainUser.getCreatedBy() != null) {
				modelUser.setCreatedBy(domainUser.getCreatedBy());
			}
			if (domainUser.getCreatedDate() != null) {
				modelUser.setCreatedDate(domainUser.getCreatedDate());
			}
			if (domainUser.getUpdatedBy() != null) {
				modelUser.setUpdatedBy(domainUser.getUpdatedBy());
			}
			if (domainUser.getUpdatedDate() != null) {
				modelUser.setUpdatedDate(domainUser.getUpdatedDate());
			}
			boolean compFlag = false;
			if ((domainUser.getUserPlatforms() != null) && (domainUser.getUserPlatforms().size() > 0)) {
				for (UserPlatforms platform : domainUser.getUserPlatforms()) {
					modelUser.addPlatforms(platform.getPlatform().getId());
					if (!compFlag) {
						Long selectedYear = Long.valueOf(Calendar.getInstance().get(Calendar.YEAR));
						String halfYear = (Calendar.getInstance().get(Calendar.MONTH) > 5) ? "2H" : "1H";
						try {
							UserPlatformCompetencyScore uacs = getUserPlatformCompScore(domainUser.getId(),
									platform.getPlatform().getId(), selectedYear, halfYear);
							if (uacs != null) {
								modelUser.setCompetencyScore(uacs.getCompScore());
								compFlag = true;
							}
						} catch (Throwable e) {
						}
					}
					switch (platform.getPlatform().getPillarName()) {
					case "CX":
						modelUser.setCxContribution(platform.getContribution());
						break;
					case "UCC":
						modelUser.setConfContribution(platform.getContribution());
						break;
					case "EMDA":
						modelUser.setEmdaContribution(platform.getContribution());
						break;
					case "IPCOMMS":
						modelUser.setVoipContribution(platform.getContribution());
						break;
					case "Admin":
						modelUser.setAdminContribution(platform.getContribution());
						break;
					case "OneTalk":
						modelUser.setCmproxyContribution(platform.getContribution());
						break;
					case "IPCOMMS-NSRS":
						modelUser.setNsrsContribution(platform.getContribution());
						break;

					}
				}
				modelUser.setPlatformName(
						((UserPlatforms) domainUser.getUserPlatforms().get(0)).getPlatform().getPillarName());

			}
			if ((domainUser.getUserSkills() != null) && (domainUser.getUserSkills().size() > 0)) {
				for (UserSkills skill : domainUser.getUserSkills()) {
					if ((skill.getPrimaryFlag() != null) && (skill.getPrimaryFlag().equalsIgnoreCase("Y"))) {
						modelUser.setPrimarySkillId(skill.getTechnology().getId());
						modelUser.setPrimarySkillName(skill.getTechnology().getTechnologyName());
						Long selectedYear = Long.valueOf(Calendar.getInstance().get(Calendar.YEAR));
						String halfYear = (Calendar.getInstance().get(Calendar.MONTH) > 5) ? "2H" : "1H";
						try {
							UserSkillScore uss = getUserSkillScore(domainUser.getId(), skill.getTechnology().getId(),
									selectedYear, halfYear);
							if (uss != null) {
								modelUser.setTechScore(uss.getTechScore());
							}
						} catch (Throwable e) {
						}
					} else {
						modelUser.addTechnologies(skill.getTechnology().getId());
					}
				}
				modelUser.setSkillName(
						((UserSkills) domainUser.getUserSkills().get(0)).getTechnology().getTechnologyName());
			}
			if ((domainUser.getUserProjects() != null) && (domainUser.getUserProjects().size() > 0)) {
				for (UserProjects userProject : domainUser.getUserProjects()) {
					if (userProject.getPrimaryFlag() != null) {
						if (userProject.getPrimaryFlag().equalsIgnoreCase("Y")) {
							modelUser.setPrimaryProjectId(userProject.getProject().getId());
						}
					}
					modelUser.addProjects(userProject.getProject().getId());
				}
				modelUser.setProjectName(
						((UserProjects) domainUser.getUserProjects().get(0)).getProject().getProjectName());
			}
			if ((domainUser.getUserNetworkCodes() != null) && (domainUser.getUserNetworkCodes().size() > 0)) {
				if (domainUser.getUserNetworkCodes().get(0).getNetworkCodes() != null)
					if (((UserNetworkCodes) domainUser.getUserNetworkCodes().get(0)).getNetworkCodes()
							.getProjectId() != null) {
						Project project = daoManager.getProjectDao()
								.get(((UserNetworkCodes) domainUser.getUserNetworkCodes().get(0)).getNetworkCodes()
										.getProjectId());
						modelUser.setProjectName(project.getProjectName());
					}
			}
			if (domainUser.getLocationName() != null) {
				modelUser.setLocation(domainUser.getLocationName());
			}

			if (domainUser.getLocation() != null) {
				modelUser.setLocationId(domainUser.getLocation().getId());
				modelUser.setLocationName(domainUser.getLocation().getName());
				modelUser.setRegion(domainUser.getLocation().getRegion());
			}

			if (domainUser.getPhone() != null) {
				modelUser.setPhone(domainUser.getPhone());
			}
			if (domainUser.getMobile() != null) {
				modelUser.setMobile(domainUser.getMobile());
			}
			if (domainUser.getPersonalNo() != null) {
				modelUser.setPersonalNo(domainUser.getPersonalNo());
			}
			if (domainUser.getAllocation() != null) {
				modelUser.setAllocation(domainUser.getAllocation());
			}
			if (domainUser.getVzid() != null) {
				modelUser.setVzid(domainUser.getVzid());
			}
			if (domainUser.getMhrCategory() != null) {
				modelUser.setMhrCategory(domainUser.getMhrCategory());
			}
			if (domainUser.getEriProjNo() != null) {
				modelUser.setEriProjNo(domainUser.getEriProjNo());
			}
			if (domainUser.getDoj() != null) {
				modelUser.setDoj(domainUser.getDoj());
			}
			if (domainUser.getJobStage() != null) {
				modelUser.setJobStage(domainUser.getJobStage());
			}
			if (domainUser.getJobDescription() != null) {
				modelUser.setJobDescription(domainUser.getJobDescription());
			}
			if (domainUser.getDateOfBilling() != null) {
				modelUser.setDateOfBilling(domainUser.getDateOfBilling());
			}
			if (domainUser.getReleseDate() != null) {
				modelUser.setReleseDate(domainUser.getReleseDate());
			}
			if (domainUser.getResignDate() != null) {
				modelUser.setResignDate(domainUser.getResignDate());
			}
			if (domainUser.getCertifications() != null) {
				modelUser.setCertifications(domainUser.getCertifications());
			}
			if (domainUser.getStream() != null) {
				modelUser.setStream(domainUser.getStream().getId());
				modelUser.setStreamName(domainUser.getStream().getStreamName());
			}
			if (domainUser.getBackFillOf() != null) {
				modelUser.setBackFillOff(domainUser.getBackFillOf());
			}
		}

	}

	@Override
	@Transactional
	public void getUserList(Long supervisorId, Map<Long, String> userList, Set<Long> idList, String searchValue)
			throws Throwable {

		if (userList == null) {
			userList = new LinkedHashMap<Long, String>();
		} else {
			userList.clear();
		}
		List<com.egil.pts.modal.User> domainUserList = daoManager.getUserDao().getSubordinates(supervisorId, idList,
				searchValue);
		userList.put(-1L, "All");
		for (com.egil.pts.modal.User subordinates : domainUserList) {
			userList.put(subordinates.getId(), subordinates.getName());
		}
	}

	@Override
	@Transactional
	public int resetPassword(String userId, String newPassword, String oldPassword) throws Throwable {
		return daoManager.getUserDao().resetPassword(userId, newPassword, oldPassword);
	}

	@Override
	@Transactional
	public void mapResourceNetworkCodes(Long userId, List<Long> selectedNetworkCodeIds) throws Throwable {

		List<UserNetworkCodes> userNetworkCodesList = new ArrayList<UserNetworkCodes>();

		List<Long> existingNetworkCodesList = daoManager.getUserNetworkCodeDao().getNetworkCodeIds(userId);

		List<Long> addedList = new ArrayList<Long>();
		addedList.addAll(selectedNetworkCodeIds);

		addedList.removeAll(existingNetworkCodesList);

		UserNetworkCodes un = null;
		for (Long networkCodeId : addedList) {
			un = new UserNetworkCodes();
			com.egil.pts.dao.domain.User user = new com.egil.pts.dao.domain.User();
			user.setId(userId);
			un.setUser(user);
			com.egil.pts.dao.domain.NetworkCodes nc = new com.egil.pts.dao.domain.NetworkCodes();
			nc.setId(networkCodeId);
			un.setNetworkCodes(nc);
			userNetworkCodesList.add(un);
		}
		if (userNetworkCodesList.size() > 0) {
			daoManager.getUserNetworkCodeDao().saveUserNetworkCodes(userNetworkCodesList);
		}

		List<Long> removedList = new ArrayList<Long>();
		if (existingNetworkCodesList != null && existingNetworkCodesList.size() > 0) {
			removedList.addAll(existingNetworkCodesList);
			removedList.removeAll(selectedNetworkCodeIds);
		}

		if (removedList.size() > 0) {
			daoManager.getUserNetworkCodeDao().deleteUserNetworkCodes(userId, null, null, null, removedList);
		}

	}

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true)
	public void getUserList(Long supervisorId, Map<Long, String> userList) throws Throwable {
		if (userList == null) {
			userList = new LinkedHashMap<Long, String>();
		} else {
			userList.clear();
		}
		List<com.egil.pts.dao.domain.UserSupervisor> domainUserList = daoManager.getUserDao()
				.getSubordinates(supervisorId);

		for (UserSupervisor subordinates : domainUserList) {
			userList.put(subordinates.getUser().getId(), subordinates.getUser().getPersonalInfo().getName());
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true)
	public void getUserListNew(Long supervisorId, Map<Long, String> userList) throws Throwable {
		if (userList == null) {
			userList = new LinkedHashMap<Long, String>();
		} else {
			userList.clear();
		}
		List<User> domainUserList = daoManager.getUserDao().getSubordinatesNew(supervisorId);

		for (User subordinates : domainUserList) {
			userList.put(subordinates.getId(), subordinates.getName());
		}
	}

	@Override
	public String getProgramManagersMap() throws Throwable {
		List<com.egil.pts.dao.domain.User> domainProgramManagersList = daoManager.getUserDao().getProgramManagers();
		String returnValue = "";
		returnValue = -1 + ":" + "Please Select" + ";";
		for (com.egil.pts.dao.domain.User programManager : domainProgramManagersList) {
			returnValue = returnValue + programManager.getId() + ":" + programManager.getPersonalInfo().getName() + ";";
		}
		return returnValue.substring(0, returnValue.length() - 1);
	}

	@Override
	public String getProjectManagersMap() throws Throwable {
		List<com.egil.pts.dao.domain.User> domainProjectManagersList = daoManager.getUserDao().getProjectManagers();
		String returnValue = "";
		returnValue = -1 + ":" + "Please Select" + ";";
		for (com.egil.pts.dao.domain.User projectManager : domainProjectManagersList) {
			returnValue = returnValue + projectManager.getId() + ":" + projectManager.getPersonalInfo().getName() + ";";
		}
		return returnValue.substring(0, returnValue.length() - 1);
	}

	@Override
	public Map<Long, String> getProjectManagersMap(Map<Long, String> data) throws Throwable {
		List<com.egil.pts.modal.User> domainProjectManagersList = daoManager.getUserDao().getProjectManagers(null);
		data.put(-1L, "All");
		for (com.egil.pts.modal.User projectManager : domainProjectManagersList) {
			data.put(projectManager.getId(), projectManager.getName());
		}
		return data;
	}

	@Override
	@Transactional
	public void getUserListToAssignNetworkCode(boolean showAllEmp, Long supervisorId, Map<Long, String> userList,
			Set<Long> idList, String searchValue, Long selectedNetworkCodeId) throws Throwable {

		if (userList == null) {
			userList = new LinkedHashMap<Long, String>();
		} else {
			userList.clear();
		}
		List<com.egil.pts.dao.domain.UserSupervisor> domainUserList = daoManager.getUserDao()
				.getUserListToAssignNetworkCode(supervisorId, idList, searchValue, selectedNetworkCodeId);

		for (UserSupervisor subordinates : domainUserList) {
			userList.put(subordinates.getUser().getId(), subordinates.getUser().getPersonalInfo().getName());
		}
		if (showAllEmp) {
			List<Object[]> data = daoManager.getUserNetworkCodeDao().getUserNetworkUnliked(true, selectedNetworkCodeId,
					supervisorId);
			if (data.size() > 0)
				for (Object[] user : data) {
					userList.put((Long.parseLong(user[0] + "")), user[1] + "");
				}
		}

	}

	public BulkResponse exportUsersSearchResults(String userId, HashMap<String, String> userColHeaders,
			SearchSortContainer searchSortObj, String filename, boolean isAdmin) throws Throwable {
		DateFormat format = new SimpleDateFormat("dd-MMM-yyyy");
		BulkResponse response = new BulkResponse();
		try {

			List<User> userList = new ArrayList<User>();

			if (isAdmin) {
				userList = getUsers(null, searchSortObj);
			} else {
				userList = getUsersSummaryNew(null, searchSortObj);
			}

			if (userList == null || userColHeaders == null || StringHelper.isEmpty(userId)) {
				logger.error("Invalid input..! Either usersList or pbxColHeaders or userId is/are null");
				response.setErrorMessage("Invalid input..!");
				return response;
			}

			GenericExcel excel = new GenericExcel(filename);
			String[][] userRecords = new String[userList.size() + 1][userColHeaders.size()];
			int colCount = 0;
			for (String colHeader : userColHeaders.values()) {
				userRecords[0][colCount++] = colHeader;

			}

			for (int i = 0; i < userList.size(); i++) {
				User user = userList.get(i);
				if (user.getName() != null)
					userRecords[i + 1][0] = user.getName();
				else
					userRecords[i + 1][0] = "";
				if (user.getUserName() != null)
					userRecords[i + 1][1] = user.getUserName();
				else
					userRecords[i + 1][1] = "";
				if (user.getEmail() != null)
					userRecords[i + 1][2] = user.getEmail();
				else
					userRecords[i + 1][2] = "";

				if (user.getSupervisor() != null)
					userRecords[i + 1][3] = user.getSupervisor();
				else
					userRecords[i + 1][3] = "";

				String strDoj = user.getDoj() == null ? "" : format.format(user.getDoj());
				userRecords[i + 1][4] = strDoj;

				if (user.getDateOfBilling() != null)
					userRecords[i + 1][5] = format.format(user.getDateOfBilling());
				else
					userRecords[i + 1][5] = "";

				if (user.getJobStage() != null)
					userRecords[i + 1][6] = "Job Stage " + user.getJobStage();
				else
					userRecords[i + 1][6] = "";

				if (user.getJobDescription() != null)
					userRecords[i + 1][7] = user.getJobDescription();
				else
					userRecords[i + 1][7] = "";

				if (user.getBackFillOff() != null)
					userRecords[i + 1][8] = user.getBackFillOff();
				else
					userRecords[i + 1][8] = "";

				if (user.getLocation() != null)
					userRecords[i + 1][9] = user.getLocation();
				else
					userRecords[i + 1][9] = "";

				if (user.getAllocation() != null)
					userRecords[i + 1][10] = user.getAllocation();
				else
					userRecords[i + 1][10] = "";

				if (user.getEriProjNo() != null)
					userRecords[i + 1][11] = user.getEriProjNo();
				else
					userRecords[i + 1][11] = "";

				if (user.getPhone() != null)
					userRecords[i + 1][12] = user.getPhone();
				else
					userRecords[i + 1][13] = "";

				if (user.getPersonalNo() != null)
					userRecords[i + 1][13] = user.getPersonalNo();
				else
					userRecords[i + 1][14] = "";

				if (user.getMobile() != null)
					userRecords[i + 1][14] = user.getMobile();
				else
					userRecords[i + 1][14] = "";

				if (user.getUserType() != null)
					userRecords[i + 1][15] = user.getUserType();
				else
					userRecords[i + 1][15] = "";

				if (user.getRole() != null)
					userRecords[i + 1][16] = user.getRole();
				else
					userRecords[i + 1][16] = "";

				if (user.getStreamName() != null)
					userRecords[i + 1][17] = user.getStreamName();
				else
					userRecords[i + 1][17] = "";

				if (user.getStatus() != null)
					userRecords[i + 1][18] = user.getStatus();
				else
					userRecords[i + 1][18] = "";

				if (user.getPlatformName() != null)
					userRecords[i + 1][19] = user.getPlatformName();
				else
					userRecords[i + 1][19] = "";

				if (user.getSkillName() != null)
					userRecords[i + 1][20] = user.getSkillName();
				else
					userRecords[i + 1][20] = "";

				if (user.getCertifications() != null)
					userRecords[i + 1][21] = user.getCertifications();
				else
					userRecords[i + 1][21] = "";

				if (user.getComments() != null)
					userRecords[i + 1][22] = user.getComments();
				else
					userRecords[i + 1][22] = "";

				if (user.getReleaseComments() != null)
					userRecords[i + 1][23] = user.getReleaseComments();
				else
					userRecords[i + 1][23] = "";
			}

			excel.createSheet("Employee Details", userRecords);
			excel.setCustomRowStyleForHeader(0, 0, HSSFColor.GREY_40_PERCENT.index, 30);
			// excel.setRowFont(0,0,1);
			boolean status = excel.writeWorkbook();
			if (!status) {
				logger.error("Not able to create excelsheet..");
				response.setErrorMessage("Not able to create excelsheet..");
				response.setStatus("FAILURE");
				return response;
			}
			response.setStatus("SUCCESS");
			response.setFilePath(filename);
		} catch (Throwable throwable) {
			logger.error(throwable);
		}
		if (logger.isInfoEnabled()) {
			logger.info("Manage Users Group Export" + " Table successfully processed");
		}
		return response;
	}

	@Override
	public void getProgramManagersMap(Map<Long, String> programManagersMapObj, String pillarId) throws Throwable {
		List<com.egil.pts.modal.User> domainProgramManagersList = daoManager.getUserDao().getProgramManagers(pillarId);

		for (com.egil.pts.modal.User programManager : domainProgramManagersList) {
			programManagersMapObj.put(programManager.getId(), programManager.getName());
		}

	}

	@Override
	public void getProjectManagersMap(Map<Long, String> projectManagersMapObj, String pillarId) throws Throwable {
		List<com.egil.pts.modal.User> domainProjectmManagersList = daoManager.getUserDao().getProjectManagers(pillarId);
		projectManagersMapObj.put(-1L, "Please Select");
		for (com.egil.pts.modal.User programManager : domainProjectmManagersList) {
			projectManagersMapObj.put(programManager.getId(), programManager.getName());
		}

	}

	@Override
	public List<Long> getLineManagersIds() throws Throwable {
		return daoManager.getUserDao().getLineManagersIds();
	}

	@Override
	public List<Long> getResourcesToAddProjects() throws Throwable {
		return daoManager.getUserDao().getResourcesToAddProjects();
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true)
	private List<User> getUsersSummaryNew(Pagination pagination, SearchSortContainer searchSortContainer)
			throws Throwable {
		return daoManager.getUserDao().getUsersSummary(pagination, searchSortContainer);
	}

	@Override
	public Integer sendTemporaryPwdMail(String username) throws Throwable {
		String newPassword = Utility.generateRandomShortString(8);
		Integer result = resetPassword(username, DesEncrypter.encrypt(newPassword), null);
		String email = daoManager.getUserDao().getMailByUserName(username);
		GenericMail.sendPasswordMail(email, newPassword);
		return result;
	}

	@Override
	public void saveUserWeekOff(List<Long> selectedEmployeeList, Date weekendingDate, boolean weekOffFlag)
			throws Throwable {
		List<com.egil.pts.dao.domain.User> userList = new ArrayList<com.egil.pts.dao.domain.User>();
		List<UserWeekOff> userWeekOffList = new ArrayList<UserWeekOff>();
		com.egil.pts.dao.domain.User user = null;
		UserWeekOff userWeekOff = null;
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String weekEndDate = null;
		List<Long> removedWeekOffList = new ArrayList<Long>();
		for (Long userId : selectedEmployeeList) {
			weekEndDate = format.format(weekendingDate);
			userWeekOff = daoManager.getUserWeekOffDao().getUserWeekOff(userId, weekEndDate);
			if (userWeekOff == null && weekOffFlag) {
				userWeekOff = new UserWeekOff();
				user = new com.egil.pts.dao.domain.User();
				user.setId(userId);
				userList.add(user);
				userWeekOff.setUser(user);
				userWeekOff.setWeekendingDate(weekendingDate);
				userWeekOff.setWeekOffFlag(weekOffFlag);

				userWeekOffList.add(userWeekOff);
			} else {
				if (userWeekOff != null)
					removedWeekOffList.add(userWeekOff.getId());
			}

			if (weekOffFlag) {
				Set<Long> userSet = new HashSet<Long>();
				userSet.add(userId);
				boolean showOld = true;
				List<UserTimesheet> userEffortList = daoManager.getUserNetworkCodeEffortDao()
						.getEffortDetailsOfWeek(weekEndDate, null, userSet, "All", showOld);
				if (userEffortList != null && userEffortList.size() > 0) {
					List<Long> removedIds = new ArrayList<Long>();
					for (UserTimesheet usertime : userEffortList) {
						removedIds.add(usertime.getId());
					}
					daoManager.getUserNetworkCodeEffortDao().delete(removedIds);
				}
			}
		}
		if (userWeekOffList.size() > 0) {
			daoManager.getUserWeekOffDao().saveUserWeekOff(userWeekOffList);
		}

		if (removedWeekOffList.size() > 0) {
			daoManager.getUserWeekOffDao().delete(removedWeekOffList);
		}
	}

	@Override
	@Transactional
	public UserSkillScore getUserSkillScore(Long userId, Long skillId, Long year, String yearHalf) throws Throwable {
		return daoManager.getUserSkillScoreDao().getUserSkillScore(userId, skillId, year, yearHalf);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true)
	public void saveUserSkillScore(UserSkillScore userSkillScore) throws Throwable {
		daoManager.getUserSkillScoreDao().save(userSkillScore);
		daoManager.getUserSkillScoreDao().flush();
	}

	@Override
	@Transactional
	public List<UserSkills> getUserSkill(Long userId, Long skillId) throws Throwable {
		return daoManager.getUserSkillsDao().getUserSkill(userId, skillId);
	}

	@Override
	@Transactional
	public UserPlatformCompetencyScore getUserPlatformCompScore(Long userId, Long projectId, Long year, String yearHalf)
			throws Throwable {
		return daoManager.getUserAppCompetencyScore().getUserCompScore(userId, projectId, year, yearHalf);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true)
	public void saveUserPlatformCompScore(UserPlatformCompetencyScore userSkillScore) throws Throwable {
		daoManager.getUserAppCompetencyScore().save(userSkillScore);
		daoManager.getUserAppCompetencyScore().flush();
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true)
	public List<UserProjects> getUserProjects(Long userId, Long projectId) throws Throwable {
		return daoManager.getUserProjectsDao().getUserProjects(userId, projectId);
	}

	@Override
	@Transactional
	public List<UserPlatforms> getUserPlatforms(Long userId, Long pillarId) throws Throwable {
		return daoManager.getUserPlatformDao().getUserPlatforms(userId, pillarId);
	}

	@Override
	public boolean getUserWeekOff(Long userId, Date weekendingDate) throws Throwable {
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String weekEndDate = null;
		weekEndDate = format.format(weekendingDate);
		UserWeekOff userWeekOff = daoManager.getUserWeekOffDao().getUserWeekOff(userId, weekEndDate);
		if (userWeekOff != null && userWeekOff.getWeekOffFlag() != null) {
			return userWeekOff.getWeekOffFlag();
		} else {
			return false;
		}

	}

	@Override
	public List<PtsHolidays> getEGIHolidayList() {
		return daoManager.getUserDao().getEGIHolidayList();
	}

	@Override
	public List<PtsHolidays> getMANAHolidayList() {
		return daoManager.getUserDao().getMANAHolidayList();
	}

	@Override
	public void getHeadCnt(Long supervisorId, Map<String, String> headCntMap) {
		daoManager.getUserDao().getHeadCnt(supervisorId, headCntMap);

	}

	@Override
	public List<DashboardUtilizationReport> getDashboardUtilizatoinDetails_New(Long supervisorId, int monthSelected,
			boolean teamFlag, int year, SearchSortContainer searchSortContainer, String role) {
		List<DashboardUtilizationReport> dashboardUtilizationReportList = new ArrayList<DashboardUtilizationReport>();
		try {
			List<LocationUserCount> locationUserCntList = daoManager.getUserUtilizationDao().getLocationUserCntNew1(
					supervisorId, teamFlag, monthSelected, year, null, searchSortContainer, role);
			if (locationUserCntList != null && locationUserCntList.size() > 0) {
				teamFlag = locationUserCntList.get(0).isTeamFlag();
				for (LocationUserCount loc : locationUserCntList) {
					SimpleDateFormat sdformat = new SimpleDateFormat("MM/dd/yyyy");
					Calendar c = new GregorianCalendar();
					c.set(Calendar.YEAR, year);
					c.set(Calendar.DAY_OF_MONTH, 1);
					String monthtargetHrs = "";
					Double targetHrs = loc.getTargetHrs();
					if (targetHrs == null) {
						targetHrs = 176D;
						loc.setTargetHrs(targetHrs);
						if (loc.getMonth() == null)
							loc.setMonth((long) (new Date().getMonth() + 1));
					}
					DashboardUtilizationReport dashboardUtilizationReport = new DashboardUtilizationReport();
					dashboardUtilizationReport.setMonth(months[loc.getMonth().intValue() - 1]);
					dashboardUtilizationReport.setTargetHrs(targetHrs);

					dashboardUtilizationReport.setHeadCount(loc.getUserCnt());
					int minDays = 0;
					int maxDays = 0;
					String fromDate = "";
					String toDate = "";
					{
						switch (months[loc.getMonth().intValue() - 1]) {
						case "Jan":
							c.set(Calendar.MONTH, Calendar.JANUARY);
							minDays = c.getActualMinimum(Calendar.DAY_OF_MONTH);
							maxDays = c.getActualMaximum(Calendar.DAY_OF_MONTH);
							fromDate = "01/" + minDays + "/" + year;
							toDate = "01/" + maxDays + "/" + year;
							if (loc.getEgiworkingHours() != null) {
								monthtargetHrs = String.format("%1.1f", loc.getTargetHrs());
							} else {
								monthtargetHrs = String.format("%1.1f", loc.getTargetHrs());
							}
							break;
						case "Feb":
							c.set(Calendar.MONTH, Calendar.FEBRUARY);
							minDays = c.getActualMinimum(Calendar.DAY_OF_MONTH);
							maxDays = c.getActualMaximum(Calendar.DAY_OF_MONTH);
							fromDate = "02/" + minDays + "/" + year;
							toDate = "02/" + maxDays + "/" + year;
							if (loc.getEgiworkingHours() != null) {
								monthtargetHrs = String.format("%1.1f", loc.getTargetHrs());
							} else {
								monthtargetHrs = String.format("%1.1f", loc.getTargetHrs());
							}
							break;
						case "Mar":
							c.set(Calendar.MONTH, Calendar.MARCH);
							minDays = c.getActualMinimum(Calendar.DAY_OF_MONTH);
							maxDays = c.getActualMaximum(Calendar.DAY_OF_MONTH);
							fromDate = "03/" + minDays + "/" + year;
							toDate = "03/" + maxDays + "/" + year;
							if (loc.getEgiworkingHours() != null) {
								monthtargetHrs = String.format("%1.1f", loc.getTargetHrs());
							} else {
								monthtargetHrs = String.format("%1.1f", loc.getTargetHrs());
							}
							break;
						case "Apr":
							c.set(Calendar.MONTH, Calendar.APRIL);
							minDays = c.getActualMinimum(Calendar.DAY_OF_MONTH);
							maxDays = c.getActualMaximum(Calendar.DAY_OF_MONTH);
							fromDate = "04/" + minDays + "/" + year;
							toDate = "04/" + maxDays + "/" + year;
							if (loc.getEgiworkingHours() != null) {
								monthtargetHrs = String.format("%1.1f", loc.getTargetHrs());
							} else {
								monthtargetHrs = String.format("%1.1f", loc.getTargetHrs());
							}
							break;
						case "May":
							c.set(Calendar.MONTH, Calendar.MAY);
							minDays = c.getActualMinimum(Calendar.DAY_OF_MONTH);
							maxDays = c.getActualMaximum(Calendar.DAY_OF_MONTH);
							fromDate = "05/" + minDays + "/" + year;
							toDate = "05/" + maxDays + "/" + year;
							if (loc.getEgiworkingHours() != null) {
								monthtargetHrs = String.format("%1.1f", loc.getTargetHrs());
							} else {
								monthtargetHrs = String.format("%1.1f", loc.getTargetHrs());
							}
							break;
						case "Jun":
							c.set(Calendar.MONTH, Calendar.JUNE);
							minDays = c.getActualMinimum(Calendar.DAY_OF_MONTH);
							maxDays = c.getActualMaximum(Calendar.DAY_OF_MONTH);
							fromDate = "06/" + minDays + "/" + year;
							toDate = "06/" + maxDays + "/" + year;
							if (loc.getEgiworkingHours() != null) {
								monthtargetHrs = String.format("%1.1f", loc.getTargetHrs());
							} else {
								monthtargetHrs = String.format("%1.1f", loc.getTargetHrs());
							}
							break;
						case "Jul":
							c.set(Calendar.MONTH, Calendar.JULY);
							minDays = c.getActualMinimum(Calendar.DAY_OF_MONTH);
							maxDays = c.getActualMaximum(Calendar.DAY_OF_MONTH);
							fromDate = "07/" + minDays + "/" + year;
							toDate = "07/" + maxDays + "/" + year;
							if (loc.getEgiworkingHours() != null) {
								monthtargetHrs = String.format("%1.1f", loc.getTargetHrs());
							} else {
								monthtargetHrs = String.format("%1.1f", loc.getTargetHrs());
							}
							break;
						case "Aug":
							c.set(Calendar.MONTH, Calendar.AUGUST);
							minDays = c.getActualMinimum(Calendar.DAY_OF_MONTH);
							maxDays = c.getActualMaximum(Calendar.DAY_OF_MONTH);
							fromDate = "08/" + minDays + "/" + year;
							toDate = "08/" + maxDays + "/" + year;
							if (loc.getEgiworkingHours() != null) {
								monthtargetHrs = String.format("%1.1f", loc.getTargetHrs());
							} else {
								monthtargetHrs = String.format("%1.1f", loc.getTargetHrs());
							}
							break;
						case "Sep":
							c.set(Calendar.MONTH, Calendar.SEPTEMBER);
							minDays = c.getActualMinimum(Calendar.DAY_OF_MONTH);
							maxDays = c.getActualMaximum(Calendar.DAY_OF_MONTH);
							fromDate = "09/" + minDays + "/" + year;
							toDate = "09/" + maxDays + "/" + year;
							if (loc.getEgiworkingHours() != null) {
								monthtargetHrs = String.format("%1.1f", loc.getTargetHrs());
							} else {
								monthtargetHrs = String.format("%1.1f", loc.getTargetHrs());
							}
							break;
						case "Oct":
							c.set(Calendar.MONTH, Calendar.OCTOBER);
							minDays = c.getActualMinimum(Calendar.DAY_OF_MONTH);
							maxDays = c.getActualMaximum(Calendar.DAY_OF_MONTH);
							fromDate = "10/" + minDays + "/" + year;
							toDate = "10/" + maxDays + "/" + year;
							if (loc.getEgiworkingHours() != null) {
								monthtargetHrs = String.format("%1.1f", loc.getTargetHrs());
							} else {
								monthtargetHrs = String.format("%1.1f", loc.getTargetHrs());
							}
							break;
						case "Nov":
							c.set(Calendar.MONTH, Calendar.NOVEMBER);
							minDays = c.getActualMinimum(Calendar.DAY_OF_MONTH);
							maxDays = c.getActualMaximum(Calendar.DAY_OF_MONTH);
							fromDate = "11/" + minDays + "/" + year;
							toDate = "11/" + maxDays + "/" + year;
							if (loc.getEgiworkingHours() != null) {
								monthtargetHrs = String.format("%1.1f", loc.getTargetHrs());
							} else {
								monthtargetHrs = String.format("%1.1f", loc.getTargetHrs());
							}
							break;
						case "Dec":
							c.set(Calendar.MONTH, Calendar.DECEMBER);
							minDays = c.getActualMinimum(Calendar.DAY_OF_MONTH);
							maxDays = c.getActualMaximum(Calendar.DAY_OF_MONTH);
							fromDate = "12/" + minDays + "/" + year;
							toDate = "12/" + maxDays + "/" + year;
							if (loc.getEgiworkingHours() != null) {
								monthtargetHrs = String.format("%1.1f", loc.getTargetHrs());
							} else {
								monthtargetHrs = String.format("%1.1f", loc.getTargetHrs());
							}
							break;
						}
						Calendar gc = Calendar.getInstance();

						if (c.get(Calendar.MONTH) <= gc.get(Calendar.MONTH)) {
							String startWeek = Utility.getWeekEnding(fromDate);
							String endWeek = Utility.getWeekEnding(toDate);
							String endDateOfMonth = (new SimpleDateFormat("yyyy-MM-dd")).format(sdformat.parse(toDate));
							String startDay = (new SimpleDateFormat("EEE")).format(sdformat.parse(fromDate));
							String endDay = (new SimpleDateFormat("EEE")).format(sdformat.parse(toDate));
							Double actualHrs = daoManager.getUserUtilizationDao().getDashboardEffortDetails(
									supervisorId, startWeek, endWeek, startDay, endDay, teamFlag,
									searchSortContainer.isDetailLevel());

							try {
								List<EssDetails> essDetList = daoManager.getUserUtilizationDao()
										.getDashboardEffortESSDetails(supervisorId, year,
												months[loc.getMonth().intValue() - 1], teamFlag, endDateOfMonth,
												searchSortContainer.isDetailLevel());

								if (essDetList != null && essDetList.size() > 0) {
									String targetESSHrs = String.format("%1.1f", loc.getTargetHrs());
									dashboardUtilizationReport.setTargetHrs(Double.valueOf(targetESSHrs));
									Double ess = (essDetList.get(0).getChargedHrs() != null)
											? essDetList.get(0).getChargedHrs()
											: 0.0;
									String actualESSHrs = String.format("%1.1f", ess);
									dashboardUtilizationReport.setEssActualHrs(Double.valueOf(actualESSHrs));
									dashboardUtilizationReport.setHeadCount(loc.getUserCnt());

									Double percentage = (Double.valueOf(actualESSHrs) / Double.valueOf(targetESSHrs))
											* 100;
									String str1 = String.format("%1.1f", percentage);
									dashboardUtilizationReport.setPercentage(Double.valueOf(str1));
									dashboardUtilizationReport.setEsspercentage(Double.valueOf(str1));
								}
							} catch (Exception e) {
							}
							if (actualHrs == null) {
								actualHrs = 0.0d;
							}
							monthtargetHrs = (monthtargetHrs.isEmpty()) ? "0.0" : monthtargetHrs;
							String str = String.format("%1.1f", actualHrs);
							if (loc.getUserCnt() == 0) {
								dashboardUtilizationReport.setUtilization("0.0");
								dashboardUtilizationReport.setEssPtsDiff("0.0");
								dashboardUtilizationReport.setPercentage(0.0);
								dashboardUtilizationReport.setTargetHrs(0.0);
								dashboardUtilizationReport.setActualHrs(Double.valueOf(str));
							} else {
								Double ess = (dashboardUtilizationReport.getEssActualHrs() == null) ? 0.0
										: dashboardUtilizationReport.getEssActualHrs();
								Double essPer = 0.0;
								if (ess != 0.0) {
									essPer = (Double.valueOf(str) / ess);
									if (str.equalsIgnoreCase(dashboardUtilizationReport.getEssActualHrs() + "")) {
										essPer = 0.0;
									}
								}
								String percentage = String.format("%1.1f", (essPer));
								Double ptspercentage = (Double.valueOf(str) / Double.valueOf(monthtargetHrs)) * 100;
								String str1 = String.format("%1.1f", ptspercentage);
								dashboardUtilizationReport.setPtspercentage(Double.valueOf(str1));
								dashboardUtilizationReport.setPercentage(Double.valueOf(percentage));
								dashboardUtilizationReport.setTargetHrs(Double.valueOf(monthtargetHrs));
								dashboardUtilizationReport.setActualHrs(Double.valueOf(str));
								dashboardUtilizationReport.setUtilization(String.format("%1.1f",
										(loc.getTargetHrs() != null && loc.getTargetHrs() != 0.0)
												? ess / loc.getTargetHrs()
												: 0.0));
								dashboardUtilizationReport
										.setEssPtsDiff(String.format("%1.1f", ess - Double.valueOf(str)));
								if (dashboardUtilizationReport.getEssActualHrs() == null
										|| (dashboardUtilizationReport.getEssActualHrs() != null
												&& dashboardUtilizationReport.getEssActualHrs() == 0)) {
									dashboardUtilizationReport.setEssActualHrs(0.0);
								}
							}
						}
					}

					dashboardUtilizationReportList.add(dashboardUtilizationReport);
				}
			}
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return dashboardUtilizationReportList;
	}

	@Override
	public void SaveDashboardUtilizatoinDetail(List<DashboardUtilizationReport> dashbordDetails,
			SearchSortContainer searchSortContainer) {
		// daoManager.getUserUtilizationDao().saveUserUtilization(dashbordDetails);
	}

	@Override
	public List<DashboardUtilizationReport> getDashboardUtilizatoinDetails(Long supervisorId, int year,
			boolean teamFlag) {
		List<DashboardUtilizationReport> dashboardUtilizationReportList = new ArrayList<DashboardUtilizationReport>();
		try {
			Map<String, Integer> monthHolidayCntMap = new LinkedHashMap<String, Integer>();
			Utility.getMonths(year, monthHolidayCntMap);
			List<MonthHolidays> holidayCntList = daoManager.getUserDao().getMonthHolidayCount(year);
			Map<Long, Map<String, Integer>> locationHolidaCntMap = new LinkedHashMap<Long, Map<String, Integer>>();
			Map<String, Integer> monthHolidayCnt = new LinkedHashMap<String, Integer>();
			for (MonthHolidays holidayCntObj : holidayCntList) {
				if (locationHolidaCntMap.containsKey(holidayCntObj.getLocation_id())) {
					locationHolidaCntMap.get(holidayCntObj.getLocation_id()).put(holidayCntObj.getMonth(),
							holidayCntObj.getCount());
				} else {
					monthHolidayCnt = new LinkedHashMap<String, Integer>();
					monthHolidayCnt.put(holidayCntObj.getMonth(), holidayCntObj.getCount());
					locationHolidaCntMap.put(holidayCntObj.getLocation_id(), monthHolidayCnt);
				}
			}
			Map<String, Double> targetWorkingDaysHrsCntMap = new LinkedHashMap<String, Double>();
			List<LocationUserCount> locationUserCntList = daoManager.getUserUtilizationDao()
					.getLocationUserCnt(supervisorId, teamFlag, null);
			if (locationUserCntList != null && locationUserCntList.size() > 0) {
				teamFlag = (locationUserCntList.get(0).isTeamFlag()) ? true : teamFlag;
			}

			int headCnt = 0;
			for (LocationUserCount locationUserCntObj : locationUserCntList) {
				headCnt = headCnt + locationUserCntObj.getUserCnt();
				if (locationHolidaCntMap.containsKey(locationUserCntObj.getLocationId())) {
					for (String month : monthHolidayCntMap.keySet()) {
						int noOfWorkingDays = monthHolidayCntMap.get(month)
								- (locationHolidaCntMap.get(locationUserCntObj.getLocationId()).containsKey(month)
										? locationHolidaCntMap.get(locationUserCntObj.getLocationId()).get(month)
										: 0);
						if (targetWorkingDaysHrsCntMap.containsKey(month)) {
							targetWorkingDaysHrsCntMap.put(month, targetWorkingDaysHrsCntMap.get(month)
									+ locationUserCntObj.getUserCnt() * noOfWorkingDays * 8.5);
						} else {
							targetWorkingDaysHrsCntMap.put(month,
									locationUserCntObj.getUserCnt() * noOfWorkingDays * 8.5);
						}

					}
				}
			}
			SimpleDateFormat sdformat = new SimpleDateFormat("MM/dd/yyyy");
			Calendar c = new GregorianCalendar();
			c.set(Calendar.YEAR, year);
			c.set(Calendar.DAY_OF_MONTH, 1);
			PTSWorkingDays ptstargetHrsDetails = daoManager.getWorkingDaysDao().getWorkingDaysDetails(year);
			String monthtargetHrs = "";
			for (String month : targetWorkingDaysHrsCntMap.keySet()) {
				DashboardUtilizationReport dashboardUtilizationReport = new DashboardUtilizationReport();
				dashboardUtilizationReport.setMonth(month);
				Double targetHrs = targetWorkingDaysHrsCntMap.get(month);
				dashboardUtilizationReport.setTargetHrs(targetHrs);
				dashboardUtilizationReport.setHeadCount(headCnt);
				int minDays = 0;
				int maxDays = 0;
				String fromDate = "";
				String toDate = "";
				switch (month) {
				case "Jan":
					c.set(Calendar.MONTH, Calendar.JANUARY);
					minDays = c.getActualMinimum(Calendar.DAY_OF_MONTH);
					maxDays = c.getActualMaximum(Calendar.DAY_OF_MONTH);
					fromDate = "01/" + minDays + "/" + year;
					toDate = "01/" + maxDays + "/" + year;
					if (ptstargetHrsDetails != null) {
						monthtargetHrs = String.format("%1.1f",
								ptstargetHrsDetails.getJanWorkingDays() * ptstargetHrsDetails.getHrsPerDay() * headCnt);
					}
					break;
				case "Feb":
					c.set(Calendar.MONTH, Calendar.FEBRUARY);
					minDays = c.getActualMinimum(Calendar.DAY_OF_MONTH);
					maxDays = c.getActualMaximum(Calendar.DAY_OF_MONTH);
					fromDate = "02/" + minDays + "/" + year;
					toDate = "02/" + maxDays + "/" + year;
					if (ptstargetHrsDetails != null) {
						monthtargetHrs = String.format("%1.1f",
								ptstargetHrsDetails.getFebWorkingDays() * ptstargetHrsDetails.getHrsPerDay() * headCnt);
					}
					break;
				case "Mar":
					c.set(Calendar.MONTH, Calendar.MARCH);
					minDays = c.getActualMinimum(Calendar.DAY_OF_MONTH);
					maxDays = c.getActualMaximum(Calendar.DAY_OF_MONTH);
					fromDate = "03/" + minDays + "/" + year;
					toDate = "03/" + maxDays + "/" + year;
					if (ptstargetHrsDetails != null) {
						monthtargetHrs = String.format("%1.1f",
								ptstargetHrsDetails.getMarWorkingDays() * ptstargetHrsDetails.getHrsPerDay() * headCnt);
					}
					break;
				case "Apr":
					c.set(Calendar.MONTH, Calendar.APRIL);
					minDays = c.getActualMinimum(Calendar.DAY_OF_MONTH);
					maxDays = c.getActualMaximum(Calendar.DAY_OF_MONTH);
					fromDate = "04/" + minDays + "/" + year;
					toDate = "04/" + maxDays + "/" + year;
					if (ptstargetHrsDetails != null) {
						monthtargetHrs = String.format("%1.1f",
								ptstargetHrsDetails.getAprWorkingDays() * ptstargetHrsDetails.getHrsPerDay() * headCnt);
					}
					break;
				case "May":
					c.set(Calendar.MONTH, Calendar.MAY);
					minDays = c.getActualMinimum(Calendar.DAY_OF_MONTH);
					maxDays = c.getActualMaximum(Calendar.DAY_OF_MONTH);
					fromDate = "05/" + minDays + "/" + year;
					toDate = "05/" + maxDays + "/" + year;
					if (ptstargetHrsDetails != null) {
						monthtargetHrs = String.format("%1.1f",
								ptstargetHrsDetails.getMayWorkingDays() * ptstargetHrsDetails.getHrsPerDay() * headCnt);
					}
					break;
				case "Jun":
					c.set(Calendar.MONTH, Calendar.JUNE);
					minDays = c.getActualMinimum(Calendar.DAY_OF_MONTH);
					maxDays = c.getActualMaximum(Calendar.DAY_OF_MONTH);
					fromDate = "06/" + minDays + "/" + year;
					toDate = "06/" + maxDays + "/" + year;
					if (ptstargetHrsDetails != null) {
						monthtargetHrs = String.format("%1.1f",
								ptstargetHrsDetails.getJunWorkingDays() * ptstargetHrsDetails.getHrsPerDay() * headCnt);
					}
					break;
				case "Jul":
					c.set(Calendar.MONTH, Calendar.JULY);
					minDays = c.getActualMinimum(Calendar.DAY_OF_MONTH);
					maxDays = c.getActualMaximum(Calendar.DAY_OF_MONTH);
					fromDate = "07/" + minDays + "/" + year;
					toDate = "07/" + maxDays + "/" + year;
					if (ptstargetHrsDetails != null) {
						monthtargetHrs = String.format("%1.1f",
								ptstargetHrsDetails.getJulWorkingDays() * ptstargetHrsDetails.getHrsPerDay() * headCnt);
					}
					break;
				case "Aug":
					c.set(Calendar.MONTH, Calendar.AUGUST);
					minDays = c.getActualMinimum(Calendar.DAY_OF_MONTH);
					maxDays = c.getActualMaximum(Calendar.DAY_OF_MONTH);
					fromDate = "08/" + minDays + "/" + year;
					toDate = "08/" + maxDays + "/" + year;
					if (ptstargetHrsDetails != null) {
						monthtargetHrs = String.format("%1.1f",
								ptstargetHrsDetails.getAugWorkingDays() * ptstargetHrsDetails.getHrsPerDay() * headCnt);
					}
					break;
				case "Sep":
					c.set(Calendar.MONTH, Calendar.SEPTEMBER);
					minDays = c.getActualMinimum(Calendar.DAY_OF_MONTH);
					maxDays = c.getActualMaximum(Calendar.DAY_OF_MONTH);
					fromDate = "09/" + minDays + "/" + year;
					toDate = "09/" + maxDays + "/" + year;
					if (ptstargetHrsDetails != null) {
						monthtargetHrs = String.format("%1.1f",
								ptstargetHrsDetails.getSepWorkingDays() * ptstargetHrsDetails.getHrsPerDay() * headCnt);
					}
					break;
				case "Oct":
					c.set(Calendar.MONTH, Calendar.OCTOBER);
					minDays = c.getActualMinimum(Calendar.DAY_OF_MONTH);
					maxDays = c.getActualMaximum(Calendar.DAY_OF_MONTH);
					fromDate = "10/" + minDays + "/" + year;
					toDate = "10/" + maxDays + "/" + year;
					if (ptstargetHrsDetails != null) {
						monthtargetHrs = String.format("%1.1f",
								ptstargetHrsDetails.getOctWorkingDays() * ptstargetHrsDetails.getHrsPerDay() * headCnt);
					}
					break;
				case "Nov":
					c.set(Calendar.MONTH, Calendar.NOVEMBER);
					minDays = c.getActualMinimum(Calendar.DAY_OF_MONTH);
					maxDays = c.getActualMaximum(Calendar.DAY_OF_MONTH);
					fromDate = "11/" + minDays + "/" + year;
					toDate = "11/" + maxDays + "/" + year;
					if (ptstargetHrsDetails != null) {
						monthtargetHrs = String.format("%1.1f",
								ptstargetHrsDetails.getNovWorkingDays() * ptstargetHrsDetails.getHrsPerDay() * headCnt);
					}
					break;
				case "Dec":
					c.set(Calendar.MONTH, Calendar.DECEMBER);
					minDays = c.getActualMinimum(Calendar.DAY_OF_MONTH);
					maxDays = c.getActualMaximum(Calendar.DAY_OF_MONTH);
					fromDate = "12/" + minDays + "/" + year;
					toDate = "12/" + maxDays + "/" + year;
					if (ptstargetHrsDetails != null) {
						monthtargetHrs = String.format("%1.1f",
								ptstargetHrsDetails.getDecWorkingDays() * ptstargetHrsDetails.getHrsPerDay() * headCnt);
					}
					break;
				}
				Calendar gc = Calendar.getInstance();

				if (c.get(Calendar.MONTH) <= gc.get(Calendar.MONTH)) {
					String startWeek = Utility.getWeekEnding(fromDate);
					String endWeek = Utility.getWeekEnding(toDate);
					String endDateOfMonth = (new SimpleDateFormat("yyyy-MM-dd")).format(sdformat.parse(toDate));
					String startDay = (new SimpleDateFormat("EEE")).format(sdformat.parse(fromDate));
					String endDay = (new SimpleDateFormat("EEE")).format(sdformat.parse(toDate));
					Double actualHrs = daoManager.getUserUtilizationDao().getDashboardEffortDetails(supervisorId,
							startWeek, endWeek, startDay, endDay, teamFlag, false);
					try {
						List<EssDetails> essDetList = daoManager.getUserUtilizationDao().getDashboardEffortESSDetails(
								supervisorId, year, month, teamFlag, endDateOfMonth, false);
						if (essDetList != null && essDetList.size() > 0) {
							String targetESSHrs = String.format("%1.1f", essDetList.get(0).getTargetHrs());
							dashboardUtilizationReport.setTargetHrs(Double.valueOf(targetESSHrs));
							String actualESSHrs = String.format("%1.1f", essDetList.get(0).getChargedHrs());
							dashboardUtilizationReport.setEssActualHrs(Double.valueOf(actualESSHrs));
							dashboardUtilizationReport.setHeadCount(essDetList.get(0).getHeadCount().intValue());

							Double percentage = (Double.valueOf(actualESSHrs) / Double.valueOf(targetESSHrs)) * 100;
							String str1 = String.format("%1.1f", percentage);
							dashboardUtilizationReport.setPercentage(Double.valueOf(str1));
						}
					} catch (Exception e) {
					}
					if (actualHrs == null) {
						actualHrs = 0.0d;
					}
					monthtargetHrs = (monthtargetHrs.isEmpty()) ? "0.0" : monthtargetHrs;
					String str = String.format("%1.1f", actualHrs);
					Double percentage = (Double.valueOf(str) / Double.valueOf(monthtargetHrs)) * 100;
					String str1 = String.format("%1.1f", percentage);
					dashboardUtilizationReport.setTargetHrs(Double.valueOf(monthtargetHrs));
					dashboardUtilizationReport.setPercentage(Double.valueOf(str1));
					dashboardUtilizationReport.setActualHrs(Double.valueOf(str));

				}
				dashboardUtilizationReportList.add(dashboardUtilizationReport);
			}
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return dashboardUtilizationReportList;
	}

	@Override
	public SummaryResponse<ResourceUtilization> getResourceUtilizatoinDetails(Long userId, int year,
			String selectedMonth, Pagination pagination, boolean descrepencyFlag) throws Throwable {
		SummaryResponse<ResourceUtilization> userUtilizationReportSummary = new SummaryResponse<ResourceUtilization>();
		List<ResourceUtilization> userUtilizationReportList = new ArrayList<ResourceUtilization>();
		try {
			Map<String, Integer> monthHolidayCntMap = new LinkedHashMap<String, Integer>();
			Utility.getMonths(year, monthHolidayCntMap);
			List<MonthHolidays> holidayCntList = daoManager.getUserDao().getMonthHolidayCount(year);
			Map<Long, Map<String, Integer>> locationHolidaCntMap = new LinkedHashMap<Long, Map<String, Integer>>();
			Map<String, Integer> monthHolidayCnt = new LinkedHashMap<String, Integer>();
			for (MonthHolidays holidayCntObj : holidayCntList) {
				if (locationHolidaCntMap.containsKey(holidayCntObj.getLocation_id())) {
					locationHolidaCntMap.get(holidayCntObj.getLocation_id()).put(holidayCntObj.getMonth(),
							holidayCntObj.getCount());
				} else {
					monthHolidayCnt = new LinkedHashMap<String, Integer>();
					monthHolidayCnt.put(holidayCntObj.getMonth(), holidayCntObj.getCount());
					locationHolidaCntMap.put(holidayCntObj.getLocation_id(), monthHolidayCnt);
				}
			}

			Map<Long, Map<String, Double>> locationmontTargetHrsMap = new LinkedHashMap<Long, Map<String, Double>>();

			for (Long locationId : locationHolidaCntMap.keySet()) {
				Map<String, Double> targetWorkingDaysHrsCntMap = new LinkedHashMap<String, Double>();
				for (String month : monthHolidayCntMap.keySet()) {
					int noOfWorkingDays = monthHolidayCntMap.get(month)
							- (locationHolidaCntMap.get(locationId).containsKey(month)
									? locationHolidaCntMap.get(locationId).get(month)
									: 0);
					if (targetWorkingDaysHrsCntMap.containsKey(month)) {
						targetWorkingDaysHrsCntMap.put(month,
								targetWorkingDaysHrsCntMap.get(month) + noOfWorkingDays * 8.5);
					} else {
						targetWorkingDaysHrsCntMap.put(month, noOfWorkingDays * 8.5);
					}
				}
				locationmontTargetHrsMap.put(locationId, targetWorkingDaysHrsCntMap);
			}

			SimpleDateFormat sdformat = new SimpleDateFormat("MM/dd/yyyy");
			Calendar c = new GregorianCalendar();
			c.set(Calendar.YEAR, year);
			c.set(Calendar.DAY_OF_MONTH, 1);
			PTSWorkingDays ptstargetHrsDetails = daoManager.getWorkingDaysDao().getWorkingDaysDetails(year);
			String monthtargetHrs = "";
			for (String month : monthHolidayCntMap.keySet()) {
				int minDays = 0;
				int maxDays = 0;
				String fromDate = "";
				String toDate = "";
				if (selectedMonth != null && !selectedMonth.equals("") && !selectedMonth.equalsIgnoreCase(month)) {
					continue;
				}
				switch (month) {
				case "Jan":
					c.set(Calendar.MONTH, Calendar.JANUARY);
					minDays = c.getActualMinimum(Calendar.DAY_OF_MONTH);
					maxDays = c.getActualMaximum(Calendar.DAY_OF_MONTH);
					fromDate = "01/" + minDays + "/" + year;
					toDate = "01/" + maxDays + "/" + year;
					if (ptstargetHrsDetails != null) {
						monthtargetHrs = String.format("%1.1f",
								ptstargetHrsDetails.getJanWorkingDays() * ptstargetHrsDetails.getHrsPerDay());
					}
					break;
				case "Feb":
					c.set(Calendar.MONTH, Calendar.FEBRUARY);
					minDays = c.getActualMinimum(Calendar.DAY_OF_MONTH);
					maxDays = c.getActualMaximum(Calendar.DAY_OF_MONTH);
					fromDate = "02/" + minDays + "/" + year;
					toDate = "02/" + maxDays + "/" + year;
					if (ptstargetHrsDetails != null) {
						monthtargetHrs = String.format("%1.1f",
								ptstargetHrsDetails.getFebWorkingDays() * ptstargetHrsDetails.getHrsPerDay());
					}
					break;
				case "Mar":
					c.set(Calendar.MONTH, Calendar.MARCH);
					minDays = c.getActualMinimum(Calendar.DAY_OF_MONTH);
					maxDays = c.getActualMaximum(Calendar.DAY_OF_MONTH);
					fromDate = "03/" + minDays + "/" + year;
					toDate = "03/" + maxDays + "/" + year;
					if (ptstargetHrsDetails != null) {
						monthtargetHrs = String.format("%1.1f",
								ptstargetHrsDetails.getMarWorkingDays() * ptstargetHrsDetails.getHrsPerDay());
					}
					break;
				case "Apr":
					c.set(Calendar.MONTH, Calendar.APRIL);
					minDays = c.getActualMinimum(Calendar.DAY_OF_MONTH);
					maxDays = c.getActualMaximum(Calendar.DAY_OF_MONTH);
					fromDate = "04/" + minDays + "/" + year;
					toDate = "04/" + maxDays + "/" + year;
					if (ptstargetHrsDetails != null) {
						monthtargetHrs = String.format("%1.1f",
								ptstargetHrsDetails.getAprWorkingDays() * ptstargetHrsDetails.getHrsPerDay());
					}
					break;
				case "May":
					c.set(Calendar.MONTH, Calendar.MAY);
					minDays = c.getActualMinimum(Calendar.DAY_OF_MONTH);
					maxDays = c.getActualMaximum(Calendar.DAY_OF_MONTH);
					fromDate = "05/" + minDays + "/" + year;
					toDate = "05/" + maxDays + "/" + year;
					if (ptstargetHrsDetails != null) {
						monthtargetHrs = String.format("%1.1f",
								ptstargetHrsDetails.getMayWorkingDays() * ptstargetHrsDetails.getHrsPerDay());
					}
					break;
				case "Jun":
					c.set(Calendar.MONTH, Calendar.JUNE);
					minDays = c.getActualMinimum(Calendar.DAY_OF_MONTH);
					maxDays = c.getActualMaximum(Calendar.DAY_OF_MONTH);
					fromDate = "06/" + minDays + "/" + year;
					toDate = "06/" + maxDays + "/" + year;
					if (ptstargetHrsDetails != null) {
						monthtargetHrs = String.format("%1.1f",
								ptstargetHrsDetails.getJunWorkingDays() * ptstargetHrsDetails.getHrsPerDay());
					}
					break;
				case "Jul":
					c.set(Calendar.MONTH, Calendar.JULY);
					minDays = c.getActualMinimum(Calendar.DAY_OF_MONTH);
					maxDays = c.getActualMaximum(Calendar.DAY_OF_MONTH);
					fromDate = "07/" + minDays + "/" + year;
					toDate = "07/" + maxDays + "/" + year;
					if (ptstargetHrsDetails != null) {
						monthtargetHrs = String.format("%1.1f",
								ptstargetHrsDetails.getJulWorkingDays() * ptstargetHrsDetails.getHrsPerDay());
					}
					break;
				case "Aug":
					c.set(Calendar.MONTH, Calendar.AUGUST);
					minDays = c.getActualMinimum(Calendar.DAY_OF_MONTH);
					maxDays = c.getActualMaximum(Calendar.DAY_OF_MONTH);
					fromDate = "08/" + minDays + "/" + year;
					toDate = "08/" + maxDays + "/" + year;
					if (ptstargetHrsDetails != null) {
						monthtargetHrs = String.format("%1.1f",
								ptstargetHrsDetails.getAugWorkingDays() * ptstargetHrsDetails.getHrsPerDay());
					}
					break;
				case "Sep":
					c.set(Calendar.MONTH, Calendar.SEPTEMBER);
					minDays = c.getActualMinimum(Calendar.DAY_OF_MONTH);
					maxDays = c.getActualMaximum(Calendar.DAY_OF_MONTH);
					fromDate = "09/" + minDays + "/" + year;
					toDate = "09/" + maxDays + "/" + year;
					if (ptstargetHrsDetails != null) {
						monthtargetHrs = String.format("%1.1f",
								ptstargetHrsDetails.getSepWorkingDays() * ptstargetHrsDetails.getHrsPerDay());
					}
					break;
				case "Oct":
					c.set(Calendar.MONTH, Calendar.OCTOBER);
					minDays = c.getActualMinimum(Calendar.DAY_OF_MONTH);
					maxDays = c.getActualMaximum(Calendar.DAY_OF_MONTH);
					fromDate = "10/" + minDays + "/" + year;
					toDate = "10/" + maxDays + "/" + year;
					if (ptstargetHrsDetails != null) {
						monthtargetHrs = String.format("%1.1f",
								ptstargetHrsDetails.getOctWorkingDays() * ptstargetHrsDetails.getHrsPerDay());
					}
					break;
				case "Nov":
					c.set(Calendar.MONTH, Calendar.NOVEMBER);
					minDays = c.getActualMinimum(Calendar.DAY_OF_MONTH);
					maxDays = c.getActualMaximum(Calendar.DAY_OF_MONTH);
					fromDate = "11/" + minDays + "/" + year;
					toDate = "11/" + maxDays + "/" + year;
					if (ptstargetHrsDetails != null) {
						monthtargetHrs = String.format("%1.1f",
								ptstargetHrsDetails.getNovWorkingDays() * ptstargetHrsDetails.getHrsPerDay());
					}
					break;
				case "Dec":
					c.set(Calendar.MONTH, Calendar.DECEMBER);
					minDays = c.getActualMinimum(Calendar.DAY_OF_MONTH);
					maxDays = c.getActualMaximum(Calendar.DAY_OF_MONTH);
					fromDate = "12/" + minDays + "/" + year;
					toDate = "12/" + maxDays + "/" + year;
					if (ptstargetHrsDetails != null) {
						monthtargetHrs = String.format("%1.1f",
								ptstargetHrsDetails.getDecWorkingDays() * ptstargetHrsDetails.getHrsPerDay());
					}
					break;
				}
				Calendar gc = Calendar.getInstance();

				if (c.get(Calendar.MONTH) <= gc.get(Calendar.MONTH)) {
					String startWeek = Utility.getWeekEnding(fromDate);
					String endWeek = Utility.getWeekEnding(toDate);
					String startDay = (new SimpleDateFormat("EEE")).format(sdformat.parse(fromDate));
					String endDay = (new SimpleDateFormat("EEE")).format(sdformat.parse(toDate));
					userUtilizationReportList = daoManager.getUserUtilizationDao().getResourceUtilization(userId, month,
							startWeek, endWeek, startDay, endDay, pagination, false);
					List<ResourceUtilization> descrepencyUserList = new LinkedList<ResourceUtilization>();
					for (ResourceUtilization resourceUtilization : userUtilizationReportList) {
						Double targetHours = resourceUtilization.getTargetHours() == null
								? locationmontTargetHrsMap.get(resourceUtilization.getLocationId()).get(month)
								: resourceUtilization.getTargetHours();
						String tarHrs = String.format("%1.1f", targetHours);

						Double essHours = resourceUtilization.getEssHrs() == null ? 0.0d
								: resourceUtilization.getEssHrs();
						String acthrs = String.format("%1.1f", resourceUtilization.getActualHours());
						String esshrs = String.format("%1.1f", essHours);
						String diffHrs = String.format("%1.1f", (essHours - resourceUtilization.getActualHours()));
						String differencePtsHrs = String.format("%1.1f",
								(targetHours - resourceUtilization.getActualHours()));

						Double percentage = (resourceUtilization.getActualHours() / targetHours) * 100;
						String per = String.format("%1.1f", percentage);

						resourceUtilization.setActualHours(Double.valueOf(acthrs));
						resourceUtilization.setEssHrs(Double.valueOf(esshrs));
						// resourceUtilization.setTargetHours(Double.valueOf(tarHrs));
						resourceUtilization.setTargetHours(Double.valueOf(monthtargetHrs));
						resourceUtilization.setDifferenceHrs(Double.valueOf(diffHrs));
						resourceUtilization.setDifferencePtsHrs(Double.valueOf(differencePtsHrs));
						resourceUtilization.setPercentage(Double.valueOf(per));
						if (descrepencyFlag && ((essHours - resourceUtilization.getActualHours()) > 0
								|| (essHours - resourceUtilization.getActualHours()) < 0)) {
							descrepencyUserList.add(resourceUtilization);
						}

					}
					if (descrepencyFlag) {
						if (userUtilizationReportSummary.getEnitities() != null) {
							userUtilizationReportSummary.getEnitities().addAll(descrepencyUserList);
						} else {
							userUtilizationReportSummary.setEnitities(descrepencyUserList);
						}
					} else {
						if (userUtilizationReportSummary.getEnitities() != null) {
							userUtilizationReportSummary.getEnitities().addAll(userUtilizationReportList);
						} else {
							userUtilizationReportSummary.setEnitities(userUtilizationReportList);
						}
					}

					/*
					 * if (selectedMonth != null && !selectedMonth.equals("") &&
					 * selectedMonth.equalsIgnoreCase(month)) {
					 */
					userUtilizationReportSummary.setTotalRecords(
							userUtilizationReportSummary.getTotalRecords() + daoManager.getUserUtilizationDao()
									.getResourceUtilizationCount(userId, month, startWeek, endWeek, startDay, endDay));
					/*
					 * break; }
					 */
				}

			}

		} catch (Throwable th) {
			throw th;
		}

		return userUtilizationReportSummary;
	}

	@Override
	public SummaryResponse<ResourceUtilization> getResourceUtilizatoinDetailsNew(Long userId, int year,
			String selectedMonth, Pagination pagination, boolean descrepencyFlag) throws Throwable {
		SummaryResponse<ResourceUtilization> userUtilizationReportSummary = new SummaryResponse<ResourceUtilization>();
		List<ResourceUtilization> userUtilizationReportList = new ArrayList<ResourceUtilization>();
		try {
			List<LocationUserCount> locationUserCntList = daoManager.getUserUtilizationDao().getLocationUserCntNew(
					userId, false, Utility.monthToNoMap.get(selectedMonth), year, null, null, null);
			if (locationUserCntList != null) {
				LocationUserCount loc = locationUserCntList.get(0);
				if (loc.getUserCnt() == 0) {
					loc.setUserCnt(1);
				}
				SimpleDateFormat sdformat = new SimpleDateFormat("MM/dd/yyyy");
				Calendar c = new GregorianCalendar();
				c.set(Calendar.YEAR, year);
				c.set(Calendar.DAY_OF_MONTH, 1);
				int minDays = 0;
				int maxDays = 0;
				String fromDate = "";
				String toDate = "";
				switch (selectedMonth) {
				case "Jan":
					c.set(Calendar.MONTH, Calendar.JANUARY);
					minDays = c.getActualMinimum(Calendar.DAY_OF_MONTH);
					maxDays = c.getActualMaximum(Calendar.DAY_OF_MONTH);
					fromDate = "01/" + minDays + "/" + year;
					toDate = "01/" + maxDays + "/" + year;
					break;
				case "Feb":
					c.set(Calendar.MONTH, Calendar.FEBRUARY);
					minDays = c.getActualMinimum(Calendar.DAY_OF_MONTH);
					maxDays = c.getActualMaximum(Calendar.DAY_OF_MONTH);
					fromDate = "02/" + minDays + "/" + year;
					toDate = "02/" + maxDays + "/" + year;
					break;
				case "Mar":
					c.set(Calendar.MONTH, Calendar.MARCH);
					minDays = c.getActualMinimum(Calendar.DAY_OF_MONTH);
					maxDays = c.getActualMaximum(Calendar.DAY_OF_MONTH);
					fromDate = "03/" + minDays + "/" + year;
					toDate = "03/" + maxDays + "/" + year;
					break;
				case "Apr":
					c.set(Calendar.MONTH, Calendar.APRIL);
					minDays = c.getActualMinimum(Calendar.DAY_OF_MONTH);
					maxDays = c.getActualMaximum(Calendar.DAY_OF_MONTH);
					fromDate = "04/" + minDays + "/" + year;
					toDate = "04/" + maxDays + "/" + year;
					break;
				case "May":
					c.set(Calendar.MONTH, Calendar.MAY);
					minDays = c.getActualMinimum(Calendar.DAY_OF_MONTH);
					maxDays = c.getActualMaximum(Calendar.DAY_OF_MONTH);
					fromDate = "05/" + minDays + "/" + year;
					toDate = "05/" + maxDays + "/" + year;
					break;
				case "Jun":
					c.set(Calendar.MONTH, Calendar.JUNE);
					minDays = c.getActualMinimum(Calendar.DAY_OF_MONTH);
					maxDays = c.getActualMaximum(Calendar.DAY_OF_MONTH);
					fromDate = "06/" + minDays + "/" + year;
					toDate = "06/" + maxDays + "/" + year;
					break;
				case "Jul":
					c.set(Calendar.MONTH, Calendar.JULY);
					minDays = c.getActualMinimum(Calendar.DAY_OF_MONTH);
					maxDays = c.getActualMaximum(Calendar.DAY_OF_MONTH);
					fromDate = "07/" + minDays + "/" + year;
					toDate = "07/" + maxDays + "/" + year;
					break;
				case "Aug":
					c.set(Calendar.MONTH, Calendar.AUGUST);
					minDays = c.getActualMinimum(Calendar.DAY_OF_MONTH);
					maxDays = c.getActualMaximum(Calendar.DAY_OF_MONTH);
					fromDate = "08/" + minDays + "/" + year;
					toDate = "08/" + maxDays + "/" + year;
					break;
				case "Sep":
					c.set(Calendar.MONTH, Calendar.SEPTEMBER);
					minDays = c.getActualMinimum(Calendar.DAY_OF_MONTH);
					maxDays = c.getActualMaximum(Calendar.DAY_OF_MONTH);
					fromDate = "09/" + minDays + "/" + year;
					toDate = "09/" + maxDays + "/" + year;
					break;
				case "Oct":
					c.set(Calendar.MONTH, Calendar.OCTOBER);
					minDays = c.getActualMinimum(Calendar.DAY_OF_MONTH);
					maxDays = c.getActualMaximum(Calendar.DAY_OF_MONTH);
					fromDate = "10/" + minDays + "/" + year;
					toDate = "10/" + maxDays + "/" + year;
					break;
				case "Nov":
					c.set(Calendar.MONTH, Calendar.NOVEMBER);
					minDays = c.getActualMinimum(Calendar.DAY_OF_MONTH);
					maxDays = c.getActualMaximum(Calendar.DAY_OF_MONTH);
					fromDate = "11/" + minDays + "/" + year;
					toDate = "11/" + maxDays + "/" + year;
					break;
				case "Dec":
					c.set(Calendar.MONTH, Calendar.DECEMBER);
					minDays = c.getActualMinimum(Calendar.DAY_OF_MONTH);
					maxDays = c.getActualMaximum(Calendar.DAY_OF_MONTH);
					fromDate = "12/" + minDays + "/" + year;
					toDate = "12/" + maxDays + "/" + year;
					break;
				}
				Calendar gc = Calendar.getInstance();

				if (c.get(Calendar.MONTH) <= gc.get(Calendar.MONTH)) {
					String startWeek = Utility.getWeekEnding(fromDate);
					String endWeek = Utility.getWeekEnding(toDate);
					String startDay = (new SimpleDateFormat("EEE")).format(sdformat.parse(fromDate));
					String endDay = (new SimpleDateFormat("EEE")).format(sdformat.parse(toDate));

					userUtilizationReportList = daoManager.getUserUtilizationDao().getResourceUtilization(userId,
							selectedMonth, startWeek, endWeek, startDay, endDay, pagination, descrepencyFlag);
					List<ResourceUtilization> descrepencyUserList = new LinkedList<ResourceUtilization>();
					userUtilizationReportList.addAll(daoManager.getUserUtilizationDao()
							.getResourceUtilizationPendingEss(startWeek, endWeek, userId, selectedMonth, pagination));

					for (ResourceUtilization resourceUtilization : userUtilizationReportList) {
						Double targetHours = resourceUtilization.getTargetHours();
						if (resourceUtilization.getTargetHours() == null) {
							targetHours = loc.getTargetHrs();
						}
						// String tarHrs = String.format("%1.1f", targetHours);

						Double essHours = resourceUtilization.getEssHrs() == null ? 0.0d
								: resourceUtilization.getEssHrs();
						String acthrs = String.format("%1.1f", resourceUtilization.getActualHours());
						String esshrs = String.format("%1.1f", essHours);
						String diffHrs = String.format("%1.1f", (essHours - resourceUtilization.getActualHours()));
						String differencePtsHrs = String.format("%1.1f",
								(targetHours - resourceUtilization.getActualHours()));
						Double percentage = (resourceUtilization.getActualHours() / targetHours) * 100;

						String per = String.format("%1.1f", percentage);

						resourceUtilization.setActualHours(Double.valueOf(acthrs));
						resourceUtilization.setEssHrs(Double.valueOf(esshrs));
						// resourceUtilization.setTargetHours(Double.valueOf(tarHrs));
						resourceUtilization.setTargetHours(Double.valueOf(targetHours));
						resourceUtilization.setDifferenceHrs(Double.valueOf(diffHrs));
						resourceUtilization.setDifferencePtsHrs(Double.valueOf(differencePtsHrs));
						resourceUtilization.setPercentage(Double.valueOf(per));

						if (descrepencyFlag && essHours - resourceUtilization.getActualHours() > 0.0f) {
							descrepencyUserList.add(resourceUtilization);
						} else if (descrepencyFlag && essHours - resourceUtilization.getActualHours() < 0.0f) {
							descrepencyUserList.add(resourceUtilization);
						}

					}
					if (descrepencyFlag) {
						if (userUtilizationReportSummary.getEnitities() != null) {
							userUtilizationReportSummary.getEnitities().addAll(descrepencyUserList);
						} else {
							userUtilizationReportSummary.setEnitities(descrepencyUserList);
						}
					} else {
						if (userUtilizationReportSummary.getEnitities() != null) {
							userUtilizationReportSummary.getEnitities().addAll(userUtilizationReportList);
						} else {
							userUtilizationReportSummary.setEnitities(userUtilizationReportList);
						}
					}

					userUtilizationReportSummary.setTotalRecords(userUtilizationReportSummary.getTotalRecords()
							+ daoManager.getUserUtilizationDao().getResourceUtilizationCount(userId, selectedMonth,
									startWeek, endWeek, startDay, endDay));

				}

			}
		} catch (

		Throwable th) {
			throw th;
		}

		return userUtilizationReportSummary;
	}

	public SummaryResponse<ResourceUtilization> getResourceUtilizatoinDetailsForSummary(Long userId, int year,
			String selectedMonth, Pagination pagination, boolean descrepencyFlag) throws Throwable {
		SummaryResponse<ResourceUtilization> userUtilizationReportSummary = new SummaryResponse<ResourceUtilization>();
		List<ResourceUtilization> userUtilizationReportList = new ArrayList<ResourceUtilization>();
		try {
			List<LocationUserCount> locationUserCntList = daoManager.getUserUtilizationDao().getLocationUserCntNew1(
					userId, false, Utility.monthToNoMap.get(selectedMonth), year, null, null, null);
			if (locationUserCntList != null) {
				LocationUserCount loc = locationUserCntList.get(0);
				if (loc.getUserCnt() == 0) {
					loc.setUserCnt(1);
				}
				SimpleDateFormat sdformat = new SimpleDateFormat("MM/dd/yyyy");
				Calendar c = new GregorianCalendar();
				c.set(Calendar.YEAR, year);
				c.set(Calendar.DAY_OF_MONTH, 1);
				String monthtargetHrs = "";
				int minDays = 0;
				int maxDays = 0;
				String fromDate = "";
				String toDate = "";
				switch (selectedMonth) {
				case "Jan":
					c.set(Calendar.MONTH, Calendar.JANUARY);
					minDays = c.getActualMinimum(Calendar.DAY_OF_MONTH);
					maxDays = c.getActualMaximum(Calendar.DAY_OF_MONTH);
					fromDate = "01/" + minDays + "/" + year;
					toDate = "01/" + maxDays + "/" + year;
					monthtargetHrs = String.format("%1.1f", loc.getTargetHrs());
					break;
				case "Feb":
					c.set(Calendar.MONTH, Calendar.FEBRUARY);
					minDays = c.getActualMinimum(Calendar.DAY_OF_MONTH);
					maxDays = c.getActualMaximum(Calendar.DAY_OF_MONTH);
					fromDate = "02/" + minDays + "/" + year;
					toDate = "02/" + maxDays + "/" + year;
					monthtargetHrs = String.format("%1.1f", loc.getTargetHrs());
					break;
				case "Mar":
					c.set(Calendar.MONTH, Calendar.MARCH);
					minDays = c.getActualMinimum(Calendar.DAY_OF_MONTH);
					maxDays = c.getActualMaximum(Calendar.DAY_OF_MONTH);
					fromDate = "03/" + minDays + "/" + year;
					toDate = "03/" + maxDays + "/" + year;
					monthtargetHrs = String.format("%1.1f", loc.getTargetHrs());
					break;
				case "Apr":
					c.set(Calendar.MONTH, Calendar.APRIL);
					minDays = c.getActualMinimum(Calendar.DAY_OF_MONTH);
					maxDays = c.getActualMaximum(Calendar.DAY_OF_MONTH);
					fromDate = "04/" + minDays + "/" + year;
					toDate = "04/" + maxDays + "/" + year;
					monthtargetHrs = String.format("%1.1f", loc.getTargetHrs());
					break;
				case "May":
					c.set(Calendar.MONTH, Calendar.MAY);
					minDays = c.getActualMinimum(Calendar.DAY_OF_MONTH);
					maxDays = c.getActualMaximum(Calendar.DAY_OF_MONTH);
					fromDate = "05/" + minDays + "/" + year;
					toDate = "05/" + maxDays + "/" + year;
					monthtargetHrs = String.format("%1.1f", loc.getTargetHrs());
					break;
				case "Jun":
					c.set(Calendar.MONTH, Calendar.JUNE);
					minDays = c.getActualMinimum(Calendar.DAY_OF_MONTH);
					maxDays = c.getActualMaximum(Calendar.DAY_OF_MONTH);
					fromDate = "06/" + minDays + "/" + year;
					toDate = "06/" + maxDays + "/" + year;
					monthtargetHrs = String.format("%1.1f", loc.getTargetHrs());
					break;
				case "Jul":
					c.set(Calendar.MONTH, Calendar.JULY);
					minDays = c.getActualMinimum(Calendar.DAY_OF_MONTH);
					maxDays = c.getActualMaximum(Calendar.DAY_OF_MONTH);
					fromDate = "07/" + minDays + "/" + year;
					toDate = "07/" + maxDays + "/" + year;
					monthtargetHrs = String.format("%1.1f", loc.getTargetHrs());
					break;
				case "Aug":
					c.set(Calendar.MONTH, Calendar.AUGUST);
					minDays = c.getActualMinimum(Calendar.DAY_OF_MONTH);
					maxDays = c.getActualMaximum(Calendar.DAY_OF_MONTH);
					fromDate = "08/" + minDays + "/" + year;
					toDate = "08/" + maxDays + "/" + year;
					monthtargetHrs = String.format("%1.1f", loc.getTargetHrs());
					break;
				case "Sep":
					c.set(Calendar.MONTH, Calendar.SEPTEMBER);
					minDays = c.getActualMinimum(Calendar.DAY_OF_MONTH);
					maxDays = c.getActualMaximum(Calendar.DAY_OF_MONTH);
					fromDate = "09/" + minDays + "/" + year;
					toDate = "09/" + maxDays + "/" + year;
					monthtargetHrs = String.format("%1.1f", loc.getTargetHrs());
					break;
				case "Oct":
					c.set(Calendar.MONTH, Calendar.OCTOBER);
					minDays = c.getActualMinimum(Calendar.DAY_OF_MONTH);
					maxDays = c.getActualMaximum(Calendar.DAY_OF_MONTH);
					fromDate = "10/" + minDays + "/" + year;
					toDate = "10/" + maxDays + "/" + year;
					monthtargetHrs = String.format("%1.1f", loc.getTargetHrs());
					break;
				case "Nov":
					c.set(Calendar.MONTH, Calendar.NOVEMBER);
					minDays = c.getActualMinimum(Calendar.DAY_OF_MONTH);
					maxDays = c.getActualMaximum(Calendar.DAY_OF_MONTH);
					fromDate = "11/" + minDays + "/" + year;
					toDate = "11/" + maxDays + "/" + year;
					monthtargetHrs = String.format("%1.1f", loc.getTargetHrs());
					break;
				case "Dec":
					c.set(Calendar.MONTH, Calendar.DECEMBER);
					minDays = c.getActualMinimum(Calendar.DAY_OF_MONTH);
					maxDays = c.getActualMaximum(Calendar.DAY_OF_MONTH);
					fromDate = "12/" + minDays + "/" + year;
					toDate = "12/" + maxDays + "/" + year;
					monthtargetHrs = String.format("%1.1f", loc.getTargetHrs());
					break;
				}
				Calendar gc = Calendar.getInstance();

				if (c.get(Calendar.MONTH) <= gc.get(Calendar.MONTH)) {
					String startWeek = Utility.getWeekEnding(fromDate);
					String endWeek = Utility.getWeekEnding(toDate);
					String startDay = (new SimpleDateFormat("EEE")).format(sdformat.parse(fromDate));
					String endDay = (new SimpleDateFormat("EEE")).format(sdformat.parse(toDate));
					userUtilizationReportList = daoManager.getUserUtilizationDao().getResourceUtilization(userId,
							selectedMonth, startWeek, endWeek, startDay, endDay, pagination, false);
					List<ResourceUtilization> descrepencyUserList = new LinkedList<ResourceUtilization>();
					for (ResourceUtilization resourceUtilization : userUtilizationReportList) {
						Double targetHours = resourceUtilization.getTargetHours();
						if (resourceUtilization.getTargetHours() == null
								|| (resourceUtilization.getTargetHours() != null
										&& resourceUtilization.getTargetHours() == 0.0)) {
							targetHours = loc.getTargetHrs();
						}
						// String tarHrs = String.format("%1.1f", targetHours);

						Double essHours = resourceUtilization.getEssHrs() == null ? 0.0d
								: resourceUtilization.getEssHrs();
						String acthrs = String.format("%1.1f", resourceUtilization.getActualHours());
						String esshrs = String.format("%1.1f", essHours);
						String diffHrs = String.format("%1.1f", (resourceUtilization.getActualHours() - essHours));
						String differencePtsHrs = String.format("%1.1f",
								(targetHours - resourceUtilization.getActualHours()));
						Double percentage = (resourceUtilization.getActualHours() / targetHours) * 100;

						String per = String.format("%1.1f", percentage);

						resourceUtilization.setActualHours(Double.valueOf(acthrs));
						resourceUtilization.setEssHrs(Double.valueOf(esshrs));
						// resourceUtilization.setTargetHours(Double.valueOf(tarHrs));
						resourceUtilization.setTargetHours(Double.valueOf(targetHours));
						resourceUtilization.setDifferenceHrs(Double.valueOf(diffHrs));
						resourceUtilization.setDifferencePtsHrs(Double.valueOf(differencePtsHrs));
						resourceUtilization.setPercentage(Double.valueOf(per));
						if (descrepencyFlag && ((Double.parseDouble(esshrs) - Double.parseDouble(acthrs)) > 0
								|| (Double.parseDouble(esshrs) - Double.parseDouble(acthrs)) < 0)) {
							descrepencyUserList.add(resourceUtilization);
						}

					}
					if (descrepencyFlag) {
						if (userUtilizationReportSummary.getEnitities() != null) {
							userUtilizationReportSummary.getEnitities().addAll(descrepencyUserList);
						} else {
							userUtilizationReportSummary.setEnitities(descrepencyUserList);
						}
					} else {
						if (userUtilizationReportSummary.getEnitities() != null) {
							userUtilizationReportSummary.getEnitities().addAll(userUtilizationReportList);
						} else {
							userUtilizationReportSummary.setEnitities(userUtilizationReportList);
						}
					}

					/*
					 * if (selectedMonth != null && !selectedMonth.equals("") &&
					 * selectedMonth.equalsIgnoreCase(month)) {
					 */
					userUtilizationReportSummary.setTotalRecords(userUtilizationReportSummary.getTotalRecords()
							+ daoManager.getUserUtilizationDao().getResourceUtilizationCount(userId, selectedMonth,
									startWeek, endWeek, startDay, endDay));
					/*
					 * break; }
					 */
				}

			}

		} catch (Throwable th) {
			throw th;
		}

		return userUtilizationReportSummary;
	}

	@Override
	public void getLineManagersMap(Map<Long, String> supervisorMap) throws Throwable {
		supervisorMap.clear();

		for (com.egil.pts.dao.domain.User user : daoManager.getUserDao().getAllManagers(null, null)) {
			supervisorMap.put(user.getId(), (user.getPersonalInfo().getName()));
		}
	}

	@Override
	public void sendMailResourceUtilization(Long userId, int selectedYear, String selectedMonth) throws Throwable {
		String supervisorMail = daoManager.getUserDao().getMailByUserId(userId);
		SummaryResponse<ResourceUtilization> summary = getResourceUtilizatoinDetails(userId, selectedYear,
				selectedMonth, null, true);
		List<ResourceUtilization> descrepencyUserList = new LinkedList<ResourceUtilization>();
		Map<String, List<ResourceUtilization>> managerMaResUtilMap = new LinkedHashMap<String, List<ResourceUtilization>>();
		String html = "<html><head>" + "<title>" + "Resource Utilization" + "</title>" + "</head>"
				+ "<LINK REL='stylesheet' HREF='stylesheet/fac_css.css' TYPE='text/css'>" + "<body>"
				+ "<table    border='1'>" + "<tr><td class ='text12' ><br>Discrepency Report for the month "
				+ selectedMonth + "/" + selectedYear + ". Please Correct the Discrepencies:</td></tr><tr>"
				+ "<td ></td></tr>" + "<tr><td></td></tr>" + "<tr><td ></td></tr>" + "<tr><td><table border='1'       >"
				+ "<tr  class='centerheading' >" + "<td  align=\"center\"><b>Resource Name</b></td>"
				+ "<td  align=\"center\"><b>Target Hours</b></td>"
				+ "<td  align=\"center\"><b>ESS Charged Hours</b></td>"
				+ "<td  align=\"center\"><b>PTS Charged Hours</b></td>" + "<td  align=\"center\"><b>Difference</b></td>"
				+ "</tr>";
		String tmpHtml = "";
		if (summary != null && summary.getEnitities().size() > 0) {
			for (ResourceUtilization resUtil : summary.getEnitities()) {

				if (userId != resUtil.getUserId()) {
					if (managerMaResUtilMap.get(resUtil.getSupervisorMail()) != null
							&& managerMaResUtilMap.get(resUtil.getSupervisorMail()).size() > 0) {
						managerMaResUtilMap.get(resUtil.getSupervisorMail()).add(resUtil);
					} else {
						descrepencyUserList = new LinkedList<ResourceUtilization>();
						descrepencyUserList.add(resUtil);
						managerMaResUtilMap.put(resUtil.getSupervisorMail(), descrepencyUserList);
					}
				} else {
					supervisorMail = resUtil.getSupervisorMail();
				}
				tmpHtml = tmpHtml + "<tr>" + "<td  >" + resUtil.getResourceName() + "</b></td>"
						+ "<td  align=\"center\">" + resUtil.getTargetHours() + "</td>" + "<td  align=\"center\">"
						+ resUtil.getEssHrs() + "</td>" + "<td  align=\"center\">" + resUtil.getActualHours() + "</td>"
						+ "<td  align=\"center\">" + resUtil.getDifferenceHrs() + "</td>" + "</tr>";

			}

			if (StringHelper.isNotEmpty(supervisorMail)) {
				GenericMail.sendMailResourceUtilization(supervisorMail, "", html + tmpHtml, selectedMonth);
			}
		}
		if (managerMaResUtilMap.size() > 0) {
			for (String subSuper : managerMaResUtilMap.keySet()) {
				if (!subSuper.equalsIgnoreCase(supervisorMail)) {
					tmpHtml = "";
					for (ResourceUtilization resUtil : managerMaResUtilMap.get(subSuper)) {
						tmpHtml = tmpHtml + "<tr>" + "<td  >" + resUtil.getResourceName() + "</b></td>"
								+ "<td  align=\"center\">" + resUtil.getTargetHours() + "</td>"
								+ "<td  align=\"center\">" + resUtil.getEssHrs() + "</td>" + "<td  align=\"center\">"
								+ resUtil.getActualHours() + "</td>" + "<td  align=\"center\">"
								+ resUtil.getDifferenceHrs() + "</td>" + "</tr>";
					}
					GenericMail.sendMailResourceUtilization(subSuper, "", html + tmpHtml, selectedMonth);
				}
			}

		}
	}

	@Override
	public List<Announcements> getDashboardAnnouncementDetails() throws Throwable {
		return daoManager.getAnnouncementsDao().getDashboardDetails();
	}

	@Override
	public List<UserAccounts> getUserAccounts(String user) throws Throwable {
		return daoManager.getUserDao().getUserAccounts(user);
	}

	@Override
	public User getUser(String user, boolean ldartsManaged) throws Throwable {
		User modalUser = new User();
		com.egil.pts.dao.domain.User domainUser = daoManager.getUserDao().getUser(user, ldartsManaged);
		convertDomainToModal(modalUser, domainUser, -1L);
		return modalUser;
	}

	@Override
	public void getUserAccountList(Map<Long, String> accountMap, Map<Long, String> selectedAccountMap,
			List<Long> selectedAccounts, Long userId, Long supervisorId) throws Throwable {

		if (userId != null) {
			List<CustomerAccounts> userAccounts = daoManager.getUserAccountsDao().getUserCustomerAccounts(userId, null);
			if (userAccounts != null && userAccounts.size() > 0) {
				selectedAccountMap.clear();
				selectedAccounts.clear();
				for (CustomerAccounts ca : userAccounts) {
					selectedAccountMap.put(ca.getId(), ca.getAccountName());
					selectedAccounts.add(ca.getId());
				}
			}
		}
		List<CustomerAccounts> userNonAccounts = null;
		if (supervisorId != null && supervisorId != 0l) {
			userNonAccounts = daoManager.getUserAccountsDao().getUserCustomerAccounts(supervisorId,
					((selectedAccounts != null && selectedAccounts.size() > 0) ? selectedAccounts : null));
		}

		if (userNonAccounts == null || (userNonAccounts != null && userNonAccounts.size() == 0)) {
			userNonAccounts = daoManager.getCustomerAccountDao().getUserNonCustomerAccounts(
					(selectedAccountMap != null && selectedAccountMap.size() > 0) ? selectedAccountMap.keySet() : null);
		}

		if (userNonAccounts != null && userNonAccounts.size() > 0) {
			accountMap.clear();
			for (CustomerAccounts ca : userNonAccounts) {
				accountMap.put(ca.getId(), ca.getAccountName());
			}
		}

	}

	public void getUserAccountList(Map<Long, String> accountMap, Map<Long, String> selectedAccountMap,
			List<Long> selectedAccounts, Long valueOf) throws Throwable {

		List<CustomerAccounts> userNonAccounts = null;

		if (userNonAccounts == null || (userNonAccounts != null && userNonAccounts.size() == 0)) {
			userNonAccounts = daoManager.getCustomerAccountDao().getUserNonCustomerAccounts(
					(selectedAccountMap != null && selectedAccountMap.size() > 0) ? selectedAccountMap.keySet() : null);
		}

		if (userNonAccounts != null && userNonAccounts.size() > 0) {
			accountMap.clear();
			for (CustomerAccounts ca : userNonAccounts) {
				accountMap.put(ca.getId(), ca.getAccountName());
			}
		}

	}

	@Override
	public List<PtsHolidays> getHolidayList(PtsHolidays holidays, SearchSortContainer sortContainer) {
		return daoManager.getUserDao().getHolidayList(holidays, sortContainer);
	}

	@Override
	public List<PtsHolidays> manageHolidays(PtsHolidays holidays, SearchSortContainer sortContainer) {
		return daoManager.getUserDao().manageHolidayList(holidays, sortContainer);
	}

	@Override
	public boolean resetpassword(String userName, String email) {
		return daoManager.getUserDao().resetPassword(userName, email);
	}

	@Override
	public void getAllSupervisorList(Map<Long, String> userList, Map<Long, String> userStreamList, Long userId)
			throws Throwable {
		if (userList == null) {
			userList = new LinkedHashMap<Long, String>();
		} else {
			userList.clear();
		}
		List<User> domainUserList = daoManager.getUserDao().getAllSupervisors(userId);

		for (User subordinates : domainUserList) {
			userList.put(subordinates.getId(), subordinates.getName());
		}
		for (User subordinates : domainUserList) {
			userStreamList.put(subordinates.getStream(), subordinates.getStreamName());
		}
	}

	@Override
	public void getAllResources(Map<Long, String> resourceMapObj, Long supervisor) {
		List<User> domainUserList = daoManager.getUserDao().getAllResources(supervisor);
		for (User user : domainUserList) {
			resourceMapObj.put(user.getId(), user.getName());
		}
	}

	@Override
	public BulkResponse exportUsersUtilizationResults(Long year, Long supervisor, Long release, Long resource,
			String fileName, boolean isAdmin, LinkedHashMap<String, String> usersColHeaders, Long pillar,
			String reportype, Integer selectedMonth) {

		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		List<UserCapacity> userCapacity = daoManager.getUserDao().GenerateNWUtilizationDate(
				(year != null) ? year.intValue() : Calendar.getInstance().getWeekYear(), pillar, reportype,
				selectedMonth, supervisor);
		BulkResponse response = new BulkResponse();
		try {

			GenericExcel excel = new GenericExcel(fileName);
			String[][] userRecords = new String[userCapacity.size() + 4][usersColHeaders.size()];
			int colCount = 0;
			for (String colHeader : usersColHeaders.values()) {
				userRecords[0][colCount++] = colHeader;
			}

			for (int i = 0; i < userCapacity.size(); i++) {
				UserCapacity user = userCapacity.get(i);
				if (user.getTFSEpic() != null)
					userRecords[i + 1][0] = user.getTFSEpic() + "";
				else
					userRecords[i + 1][0] = "";

				if (user.getRELEASETYPE() != null)
					userRecords[i + 1][1] = user.getRELEASETYPE() + "";
				else
					userRecords[i + 1][1] = "";

				if (user.getNETWORKCODE() != null)
					userRecords[i + 1][2] = user.getNETWORKCODE() + "";
				else
					userRecords[i + 1][2] = "";

				if (user.getPROJECT() != null)
					userRecords[i + 1][3] = user.getPROJECT() + "";
				else
					userRecords[i + 1][3] = "";

				if (user.getSUPERVISORNAME() != null)
					userRecords[i + 1][4] = user.getSUPERVISORNAME() + "";
				else
					userRecords[i + 1][4] = "";

				if (user.getSTATUS() != null)
					userRecords[i + 1][5] = user.getSTATUS() + "";
				else
					userRecords[i + 1][5] = "";

				if (user.getTOTALCAPACITY() != null)
					userRecords[i + 1][6] = user.getTOTALCAPACITY() + "";
				else
					userRecords[i + 1][6] = "";

				if (user.getSUMMATION() != null)
					userRecords[i + 1][7] = user.getSUMMATION() + "";
				else
					userRecords[i + 1][7] = "";

				if (user.getMONTH() != null)
					userRecords[i + 1][8] = user.getMONTH() + "";
				else
					userRecords[i + 1][8] = "";

				if (user.getYEAR() != null)
					userRecords[i + 1][9] = user.getYEAR() + "";
				else
					userRecords[i + 1][9] = "";

				if (user.getIMPLEMENTATIONDATE() != null)
					userRecords[i + 1][10] = format.format(user.getIMPLEMENTATIONDATE()) + "";
				else
					userRecords[i + 1][10] = "";
			}
			Double d = 0.0d;
			for (UserCapacity s : userCapacity) {
				d += s.getSUMMATION();
			}
			// userRecords[userCapacity.size() + 2][1] = "Total Effort";
			// userRecords[userCapacity.size() + 2][7] = d + "";
			excel.createSheet("Employee Details", userRecords);
			excel.setCustomRowStyleForHeader(0, 0, HSSFColor.GREY_40_PERCENT.index, 30);
			excel.setRowFont(0, 0, 1);
			boolean status = excel.writeWorkbook();
			if (status) {
				response.setStatus("SUCCESS");
			}
			logger.info("userCapacity: " + d + " YEAR: " + year + "  Result : " + status + " FileName: " + fileName);
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {

			GenericExcel excel = new GenericExcel(fileName);
			String[][] userRecords = new String[userCapacity.size() + 4][usersColHeaders.size()];
			int colCount = 0;
			for (String colHeader : usersColHeaders.values()) {
				userRecords[0][colCount++] = colHeader;
			}

			for (int i = 0; i < userCapacity.size(); i++) {
				UserCapacity user = userCapacity.get(i);
				if (user.getTFSEpic() != null)
					userRecords[i + 1][0] = user.getTFSEpic() + "";
				else
					userRecords[i + 1][0] = "";

				if (user.getRELEASETYPE() != null)
					userRecords[i + 1][1] = user.getRELEASETYPE() + "";
				else
					userRecords[i + 1][1] = "";

				if (user.getNETWORKCODE() != null)
					userRecords[i + 1][2] = user.getNETWORKCODE() + "";
				else
					userRecords[i + 1][2] = "";

				if (user.getPROJECT() != null)
					userRecords[i + 1][3] = user.getPROJECT() + "";
				else
					userRecords[i + 1][3] = "";

				if (user.getSUPERVISORNAME() != null)
					userRecords[i + 1][4] = user.getSUPERVISORNAME() + "";
				else
					userRecords[i + 1][4] = "";

				if (user.getSTATUS() != null)
					userRecords[i + 1][5] = user.getSTATUS() + "";
				else
					userRecords[i + 1][5] = "";

				if (user.getTOTALCAPACITY() != null)
					userRecords[i + 1][6] = user.getTOTALCAPACITY() + "";
				else
					userRecords[i + 1][6] = "";

				if (user.getSUMMATION() != null)
					userRecords[i + 1][7] = user.getSUMMATION() + "";
				else
					userRecords[i + 1][7] = "";

				if (user.getMONTH() != null)
					userRecords[i + 1][8] = user.getMONTH() + "";
				else
					userRecords[i + 1][8] = "";

				if (user.getYEAR() != null)
					userRecords[i + 1][9] = user.getYEAR() + "";
				else
					userRecords[i + 1][9] = "";

				if (user.getIMPLEMENTATIONDATE() != null)
					userRecords[i + 1][10] = format.format(user.getIMPLEMENTATIONDATE()) + "";
				else
					userRecords[i + 1][10] = "";
			}
			Double d = 0.0d;
			for (UserCapacity s : userCapacity) {
				d += s.getSUMMATION();
			}
			// userRecords[userCapacity.size() + 2][1] = "Total Effort";
			// userRecords[userCapacity.size() + 2][7] = d + "";
			excel.createSheet("Employee Details", userRecords);
			excel.setCustomRowStyleForHeader(0, 0, HSSFColor.GREY_40_PERCENT.index, 30);
			excel.setRowFont(0, 0, 1);
			boolean status = excel.writeWorkbook();
			if (status) {
				response.setStatus("SUCCESS");
			}
			logger.info("userCapacity: " + d + " YEAR: " + year + "  Result : " + status + " FileName: " + fileName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}

	@Override
	public boolean checkAndDeleteUserIntern(Long id) {
		return daoManager.getUserNetworkCodeDao().checkAndDeleteUserNW(id);
	}

	@Override
	public int saveUserStableTeams(List<StableTeams> data, Long userId) {
		return daoManager.getUserNetworkCodeDao().saveUserStableTeams(data, userId);

	}

	@Override
	public Map<String, String> getTimeSheetTypeMap() {
		List<Object[]> data = daoManager.getUserNetworkCodeDao().getTimeSheetTypeMap();
		Map<String, String> map = new LinkedHashMap<String, String>();
		for (Object[] a : data) {
			map.put(a[0] + "", a[0] + "");
		}

		return map;
	}

	public String getPassword(String currentUserName) {
		return daoManager.getUserDao().getPassword(currentUserName);
	}

	public void saveUserLineManager(User userObj) {
		daoManager.getUserDao().saveUserLineManager(userObj);
	}

	public Integer getUserLineManager(Long id) {
		return daoManager.getUserDao().getUserLineManager(id);
	}

	public void editUserLineManager(User userObj) {
		daoManager.getUserDao().editUserLineManager(userObj);
	}
}
