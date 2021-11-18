package com.egil.pts.service.impl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.egil.pts.dao.domain.Pillar;
import com.egil.pts.modal.Pagination;
import com.egil.pts.modal.Project;
import com.egil.pts.modal.SearchSortContainer;
import com.egil.pts.modal.StableTeams;
import com.egil.pts.modal.Status;
import com.egil.pts.modal.SummaryResponse;
import com.egil.pts.modal.UserAcctPillarAppContribution;
import com.egil.pts.modal.UserAppContribution;
import com.egil.pts.modal.UserContribution;
import com.egil.pts.modal.UserPillarAppContribution;
import com.egil.pts.service.ProjectService;
import com.egil.pts.service.common.BaseUIService;

@Service("projectService")
public class ProjectServiceImpl extends BaseUIService implements ProjectService {

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true)
	public SummaryResponse<Project> getProjectsSummary(Pagination pagination, SearchSortContainer searchSortObj)
			throws Throwable {
		SummaryResponse<Project> summary = new SummaryResponse<Project>();
		summary.setTotalRecords(daoManager.getProjectDao().getProjectsCount(searchSortObj));
		summary.setEnitities(getProjects(pagination, searchSortObj));
		return summary;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true)
	public List<Project> getProjects(Pagination pagination, SearchSortContainer searchSortContainer) throws Throwable {
		List<Project> modalProjectsList = new ArrayList<Project>();
		List<com.egil.pts.dao.domain.Project> domainProjectsList = daoManager.getProjectDao().getProjects(pagination,
				searchSortContainer);
		Project modalProject = null;
		for (com.egil.pts.dao.domain.Project domainProject : domainProjectsList) {
			modalProject = new Project();
			convertDomainToModal(modalProject, domainProject);
			modalProjectsList.add(modalProject);
		}
		return modalProjectsList;
	}

	@Override
	public String getJSONStringOfProjects() throws Throwable {
		String returnValue = "";
		returnValue = -1 + ":" + "Please Select" + ";";
		for (Project Project : getProjects(null, null)) {
			returnValue = returnValue + Project.getId() + ":" + Project.getProjectName() + ";";
		}
		return returnValue.substring(0, returnValue.length() - 1);
	}

	@Override
	@Transactional
	public void createProject(Project modalProject) throws Throwable {
		com.egil.pts.dao.domain.Project domainProject = new com.egil.pts.dao.domain.Project();
		convertModalToDomain(modalProject, domainProject);
		daoManager.getProjectDao().save(domainProject);
	}

	@Override
	@Transactional
	public void modifyProject(Project modalProject) throws Throwable {
		com.egil.pts.dao.domain.Project domainProject = new com.egil.pts.dao.domain.Project();
		convertModalToDomain(modalProject, domainProject);
		daoManager.getProjectDao().save(domainProject);
		daoManager.getProjectDao().flush();
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public Integer deleteProjects(List<Long> ProjectsIdList) throws Throwable {
		return daoManager.getProjectDao().deleteProjects(ProjectsIdList);
	}

	private void convertModalToDomain(Project modalProject, com.egil.pts.dao.domain.Project domainProject) {
		if (modalProject != null && domainProject != null) {
			if (modalProject.getId() != null) {
				domainProject.setId(modalProject.getId());
			}
			if (modalProject.getProjectName() != null) {
				domainProject.setProjectName(modalProject.getProjectName());
			}
			if (modalProject.getPillar() != null && !modalProject.getPillar().endsWith("-1")) {
				Pillar pillar = new Pillar();
				pillar.setId(Long.valueOf(modalProject.getPillar()));
				domainProject.setPillar(pillar);
			}
			if (modalProject.getDescription() != null) {
				domainProject.setDescription(modalProject.getDescription());
			}
			if (modalProject.getStatus() != null) {
				domainProject.setStatus(Status.getBooleanValueOf(modalProject.getStatus()));
			}
			if (modalProject.getOrderNum() != null) {
				domainProject.setOrderNum(modalProject.getOrderNum());
			}
			if (modalProject.getCreatedBy() != null) {
				domainProject.setCreatedBy(modalProject.getCreatedBy());
			}

			if (modalProject.getCreatedDate() != null) {
				domainProject.setCreatedDate(modalProject.getCreatedDate());
			}

			if (modalProject.getUpdatedBy() != null) {
				domainProject.setUpdatedBy(modalProject.getUpdatedBy());
			}
			if (modalProject.getUpdatedDate() != null) {
				domainProject.setUpdatedDate(modalProject.getUpdatedDate());
			}
		}
	}

	private void convertDomainToModal(Project modalProject, com.egil.pts.dao.domain.Project domainProject) {
		if (modalProject != null && domainProject != null) {
			if (domainProject.getId() != null) {
				modalProject.setId(domainProject.getId());
			}
			if (domainProject.getProjectName() != null) {
				modalProject.setProjectName(domainProject.getProjectName());
			}
			if (domainProject.getPillar() != null && domainProject.getPillar().getId() != null) {
				modalProject.setPillar(domainProject.getPillar().getPillarName());
				if (domainProject.getPillar().getCustomerAccount() != null
						&& domainProject.getPillar().getCustomerAccount().getId() != null) {
					modalProject.setCustomer(domainProject.getPillar().getCustomerAccount().getAccountName());
				}
			}
			if (domainProject.getDescription() != null) {
				modalProject.setDescription(domainProject.getDescription());
			}

			if (domainProject.getCreatedBy() != null) {
				modalProject.setCreatedBy(domainProject.getCreatedBy());
			}

			if (domainProject.getCreatedDate() != null) {
				modalProject.setCreatedDate(domainProject.getCreatedDate());
			}
			if (domainProject.getOrderNum() != null) {
				modalProject.setOrderNum(domainProject.getOrderNum());
			}
			if (domainProject.getUpdatedBy() != null) {
				modalProject.setUpdatedBy(domainProject.getUpdatedBy());
			}
			if (domainProject.getUpdatedDate() != null) {
				modalProject.setUpdatedDate(domainProject.getUpdatedDate());
			}
		}
	}

	@Override
	public void getProjectsMap(Map<Long, String> projectsMap, String pillarId) throws Throwable {
		SearchSortContainer searchSortContainer = null;
		if (pillarId != null) {
			searchSortContainer = new SearchSortContainer();
			searchSortContainer.setSearchField("pillarId");
			searchSortContainer.setSearchString(pillarId);
		}
		for (Project project : getProjects(null, searchSortContainer)) {
			projectsMap.put(project.getId(), project.getProjectName());
		}
	}

	@Override
	public String loadProjects(String pillarId) throws Throwable {
		SearchSortContainer searchSortObj = null;
		if (pillarId != null && !pillarId.equalsIgnoreCase("")) {
			searchSortObj = new SearchSortContainer();
			searchSortObj.setSearchField("pillarId");
			searchSortObj.setSearchString(pillarId);
		}
		String returnValue = "";
		returnValue = "<option value='-1'>Please Select</option>\n";
		for (Project project : getProjects(null, searchSortObj)) {
			returnValue = returnValue + "<option value='" + project.getId() + "'>" + project.getProjectName()
					+ "</option>\n";
		}
		return returnValue;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true)
	public SummaryResponse<Project> getUserProjects(Pagination pagination, SearchSortContainer searchSortContainer,
			boolean isAdmin) {
		SummaryResponse<Project> summary = new SummaryResponse<Project>();
		summary.setTotalRecords(daoManager.getUserProjectsDao().getUserProjectsCount(searchSortContainer, isAdmin));
		summary.setEnitities(daoManager.getUserProjectsDao().getUserProjects(pagination, searchSortContainer, isAdmin));
		return summary;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true)
	public List<UserAcctPillarAppContribution> getProjectsByAccount(List<Long> selectedAccountIds, Long userId)
			throws Throwable {

		List<UserAcctPillarAppContribution> userAcctPillarAppContributionList = new LinkedList<UserAcctPillarAppContribution>();
		List<UserPillarAppContribution> userPillarAppContributionList = new ArrayList<UserPillarAppContribution>();
		List<UserAppContribution> userAppContributionList = null;
		List<UserContribution> userContributionList = daoManager.getProjectDao()
				.getProjectsByAccount(selectedAccountIds, userId);

		Long accountId = 0l;
		Long pillarId = 0l;
		UserPillarAppContribution userPillarAppContribution = null;
		UserAppContribution userAppContribution = null;
		Map<Long, List<UserAppContribution>> pilAppMap = new LinkedHashMap<Long, List<UserAppContribution>>();
		Map<Long, String> pillarIdNameMap = new LinkedHashMap<Long, String>();
		Map<Long, Float> pillarIdContributionMap = new LinkedHashMap<Long, Float>();

		Map<Long, String> acctIdNameMap = new LinkedHashMap<Long, String>();
		Map<Long, List<Long>> acctPillarMap = new LinkedHashMap<Long, List<Long>>();
		List<Long> pillarList = null;

		for (UserContribution uc : userContributionList) {
			uc.setContribution((uc.getContribution() == null) ? 0.0f : uc.getContribution());
			if (accountId != uc.getAccountId()) {
				accountId = uc.getAccountId();
				if (uc.getPillarId() != pillarId) {

					if (acctPillarMap.containsKey(uc.getAccountId())) {
						acctPillarMap.get(uc.getAccountId()).add(uc.getPillarId());
					} else {

						pillarList = new ArrayList<Long>();
						pillarList.add(uc.getPillarId());

						acctPillarMap.put(uc.getAccountId(), pillarList);
						acctIdNameMap.put(uc.getAccountId(), uc.getAccountName());
					}

					pillarId = uc.getPillarId();
					userAppContribution = new UserAppContribution();
					userAppContribution.setAppId(uc.getAppId());
					userAppContribution.setAppName(uc.getAppName());
					userAppContribution.setAppContribution(uc.getContribution());

					if (pilAppMap.containsKey(pillarId)) {
						pilAppMap.get(pillarId).add(userAppContribution);
					} else {
						userAppContributionList = new ArrayList<UserAppContribution>();
						userAppContributionList.add(userAppContribution);
					}

					pilAppMap.put(pillarId, userAppContributionList);
					pillarIdNameMap.put(pillarId, uc.getPillarName());
					pillarIdContributionMap.put(pillarId, uc.getContribution());
				} else {
					userAppContribution = new UserAppContribution();
					userAppContribution.setAppId(uc.getAppId());
					userAppContribution.setAppName(uc.getAppName());
					userAppContribution.setAppContribution(uc.getContribution());

					pilAppMap.get(uc.getPillarId()).add(userAppContribution);
					pillarIdContributionMap.put(uc.getPillarId(),
							(pillarIdContributionMap.get(uc.getPillarId()) + uc.getContribution()));
				}
			} else {
				if (uc.getPillarId() != pillarId) {

					if (acctPillarMap.containsKey(uc.getAccountId())) {
						acctPillarMap.get(uc.getAccountId()).add(uc.getPillarId());
					} else {
						pillarList = new ArrayList<Long>();
						pillarList.add(uc.getPillarId());

						acctPillarMap.put(uc.getAccountId(), pillarList);
						acctIdNameMap.put(uc.getAccountId(), uc.getAccountName());
					}

					pillarId = uc.getPillarId();
					userAppContribution = new UserAppContribution();
					userAppContribution.setAppId(uc.getAppId());
					userAppContribution.setAppName(uc.getAppName());
					userAppContribution.setAppContribution(uc.getContribution());

					if (pilAppMap.containsKey(pillarId)) {
						pilAppMap.get(pillarId).add(userAppContribution);
					} else {
						userAppContributionList = new ArrayList<UserAppContribution>();
						userAppContributionList.add(userAppContribution);
					}

					pilAppMap.put(pillarId, userAppContributionList);
					pillarIdNameMap.put(pillarId, uc.getPillarName());
					pillarIdContributionMap.put(pillarId, uc.getContribution());
				} else {
					userAppContribution = new UserAppContribution();
					userAppContribution.setAppId(uc.getAppId());
					userAppContribution.setAppName(uc.getAppName());
					userAppContribution.setAppContribution(uc.getContribution());

					pilAppMap.get(uc.getPillarId()).add(userAppContribution);
					pillarIdContributionMap.put(uc.getPillarId(),
							(pillarIdContributionMap.get(uc.getPillarId()) + uc.getContribution()));
				}
			}

		}

		if (pillarIdNameMap != null) {
			for (Long acctId : acctPillarMap.keySet()) {
				UserAcctPillarAppContribution userAcctPillarAppContribution = new UserAcctPillarAppContribution();
				userPillarAppContributionList = new LinkedList<UserPillarAppContribution>();
				userAcctPillarAppContribution.setAccountId(acctId);
				userAcctPillarAppContribution.setAccountName(acctIdNameMap.get(acctId));
				for (Long pillId : acctPillarMap.get(acctId)) {
					userPillarAppContribution = new UserPillarAppContribution();

					userPillarAppContribution.setPillarId(pillId);
					userPillarAppContribution.setPillarName(pillarIdNameMap.get(pillId));
					userPillarAppContribution.setPillarContribution(pillarIdContributionMap.get(pillId));
					userPillarAppContribution.setUserACList(pilAppMap.get(pillId));

					userPillarAppContributionList.add(userPillarAppContribution);

				}
				userAcctPillarAppContribution.getUserPACList().addAll(userPillarAppContributionList);
				userAcctPillarAppContributionList.add(userAcctPillarAppContribution);
			}
		}
		return userAcctPillarAppContributionList;
	}

}
