package com.egil.pts.actions;

import java.io.File;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.interceptor.validation.SkipValidation;
import org.hibernate.internal.util.StringHelper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.egil.pts.dao.domain.Pillar;
import com.egil.pts.dao.domain.Technologies;
import com.egil.pts.dao.domain.UserPlatformCompetencyScore;
import com.egil.pts.dao.domain.UserPlatforms;
import com.egil.pts.dao.domain.UserProjects;
import com.egil.pts.dao.domain.UserSkillScore;
import com.egil.pts.dao.domain.UserSkills;
import com.egil.pts.modal.BulkResponse;
import com.egil.pts.modal.Pagination;
import com.egil.pts.modal.ResourceUtilization;
import com.egil.pts.modal.SearchSortContainer;
import com.egil.pts.modal.StableTeams;
import com.egil.pts.modal.SummaryResponse;
import com.egil.pts.modal.User;
import com.egil.pts.modal.UserAcctPillarAppContribution;
import com.egil.pts.util.DesEncrypter;
import com.egil.pts.util.Utility;
import com.opensymphony.xwork2.validator.annotations.EmailValidator;
import com.opensymphony.xwork2.validator.annotations.FieldExpressionValidator;
import com.opensymphony.xwork2.validator.annotations.RegexFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;
import com.opensymphony.xwork2.validator.annotations.ValidatorType;

@Controller("userAction")
@Scope("prototype")
public class UserAction extends PTSAction {
	private static final long serialVersionUID = 1L;

	// Your result List
	private List<User> gridModel = new ArrayList<User>();

	private Map<Long, String> supervisorMap = new LinkedHashMap<Long, String>();
	private Map<Long, String> lineManagerMap = new LinkedHashMap<Long, String>();
	private Map<String, String> userToBackFillMap = new LinkedHashMap<String, String>();

	private Map<Long, String> privilegesMap = new LinkedHashMap<Long, String>();
	private Map<Long, String> rolesMap = new LinkedHashMap<Long, String>();
	private Map<Long, String> userTypesMap = new LinkedHashMap<Long, String>();
	private Map<Long, String> technologiesMap = new LinkedHashMap<Long, String>();

	private Map<Long, String> platformsMap = new LinkedHashMap<Long, String>();
	private Map<Long, String> projectsMap = new LinkedHashMap<Long, String>();
	private Map<String, String> locationsMap = new LinkedHashMap<String, String>();
	private Map<Long, String> streamsMap = new LinkedHashMap<Long, String>();

	private List<Long> selectedTechnologies = new ArrayList<Long>();
	private List<Long> selectedPlatforms = new ArrayList<Long>();
	private List<Long> selectedProjects = new ArrayList<Long>();

	private String searchUserName;
	private String searchSupervisor;
	private Long lineManagerId;
	private String searchUserType;
	private String searchStream;
	private String searchStatus;
	private String searchLocation;
	private String searchDOJ;
	private String searchDOBilling;
	private String searchTechnology;

	private String userName;
	private String firstName;
	private String lastName;
	private String middleName;
	private String name;
	private Long supervisor;
	private String supervisorName;
	private String email;
	private String privilege;
	private String role;
	private String stream;
	private String userType;
	private String password;
	private String status;
	private String createdBy;
	private Date createdDate;

	private Date doj;
	private Date dobilling;
	private Date resignDate;
	private Date releaseDate;

	private String strDoj;
	private String strDobilling;
	private String strResignDate;
	private String strReleaseDate;

	private String phoneNo;
	private String mobileNo;
	private String location;

	private String personalNo;
	private String allocation;
	private String vzid;
	private String eriProjNo;
	private String mhrCategory;

	private String certifications;
	private String comments;

	private String releaseComments;

	private String newPassword;
	private String confirmPassword;
	private String oldPassword;

	private Long privilegeId;
	private Long supervisorId;
	private Long hiddenSupervisorId;
	private String backfilloff;
	private Long roleId;
	private Long userTypeId;
	private Long streamId;

	private Integer jobStage;
	private String jobDescription;

	private Long userRoleId;
	private Long userSupervisorId;

	private Float techScore;
	private Long primarySkillId;
	private Long primaryProjectId;
	private Float cxContribution;
	private Float confContribution;
	private Float voipContribution;
	private Float nsrsContribution;
	private Float emdaContribution;
	private Float cmproxyContribution;
	private Float adminContribution;

	private File photoUpload; // The actual file
	private String photoUploadContentType; // The content type of the file
	private String photoUploadFileName; // The uploaded file name and path
	private String currentUserName;

	// get how many rows we want to have into the grid - rowNum attribute in the
	// grid
	private Integer rows = 0;

	// Get the requested page. By default grid sets this to 1.
	private Integer page = 0;

	// sorting order - asc or desc
	private String sord;

	// get index row - i.e. user click to sort.
	private String sidx;

	// Search Field
	private String searchField;

	// The Search String
	private String searchString;

	// Limit the result when using local data, value form attribute rowTotal
	private Integer totalrows;

	// he Search Operation
	// ['eq','ne','lt','le','gt','ge','bw','bn','in','ni','ew','en','cn','nc']
	private String searchOper;

	private String oper;

	// Your Total Pages
	private Integer total = 0;

	// All Records
	private Integer records = 0;

	private boolean loadonce = false;

	private boolean allReporteesFlag;

	private boolean showAllStableTeams;

	private boolean descrepencyFlag;

	private Long skillId;

	private Long userTechScoreId;

	private Long userPillarId;

	private Long userAppCompScoreId;

	private String halfYear;

	private Float competencyScore;

	private List<ResourceUtilization> userUtilizationGridModel = new ArrayList<ResourceUtilization>();

	private Map<Long, String> accountMap = new LinkedHashMap<Long, String>();
	private Map<Long, String> selectedAccountMap = new LinkedHashMap<Long, String>();
	private List<Long> selectedAccountIds = new ArrayList<Long>();

	@Value("${manager.new.res.logic.based}")
	private boolean newlogic;

	private List<StableTeams> stableTeams = new ArrayList<>();
	private Map<Long, String> stableTeamsmap = new LinkedHashMap<Long, String>();
	private long stableTeamid;

	@SkipValidation
	public String execute() {
		try {

			serviceManager.getUserTypesService().getUserTypesMap(userTypesMap);
			serviceManager.getStreamsService().getStreamsMap(streamsMap);
			serviceManager.getTechnologiesService().getTechnologiesMap(technologiesMap);

			if ((searchSupervisor == null || (searchSupervisor != null && searchSupervisor.isEmpty()))) {
				allReporteesFlag = true;
			}

			if (session.get("role") != null && ((String) session.get("role")).equalsIgnoreCase("LINE MANAGER")) {
				if (searchStatus == null || (searchStatus != null && searchStatus.isEmpty())) {
					searchStatus = "Onboard";
				}
				if ((searchSupervisor == null || (searchSupervisor != null && searchSupervisor.isEmpty()))) {
					searchSupervisor = (Long) session.get("userId") + "";
				}

			}
			getStableTeamsData();
			getAllLineManaMapForManage();
		} catch (Throwable e) {
			e.printStackTrace();
		}

		return SUCCESS;
	}

	@SkipValidation
	public String goManageUser() {
		try {
			clearSession("supervisorMap", "lineManagerMap", "userToBackFillMap", "privilegesMap", "rolesMap",
					"streamsMap", "platformsMap", "userTypesMap", "projectsMap", "technologiesMap", "userSkillSet",
					"userPlatformsSet");
			boolean isAdmin = false;
			if (session.get("role") != null && ((String) session.get("role")).equalsIgnoreCase("ADMIN")) {
				isAdmin = true;
			}
			if (session.get("rowNum") == null) {
				session.put("rowNum", rows);
			} else {
				if (rows != null && (rows.intValue() != ((Integer) session.get("rowNum")).intValue())) {
					session.put("rowNum", rows);
				}
			}
			SummaryResponse<User> summary = serviceManager.getUserService().getUserSummary(getPaginationObject(),
					getSearchSortBean(), isAdmin);
			gridModel = summary.getEnitities();

			records = summary.getTotalRecords();

			// calculate the total pages for the query
			total = (int) Math.ceil((double) records / (double) rows);

		} catch (Throwable e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	@SkipValidation
	public String goCreateUser() {
		try {
			getSelectBoxMap();
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	private void getSelectBoxMap() throws Throwable {
		clearSession("supervisorMap", "lineManagerMap", "userToBackFillMap", "privilegesMap", "rolesMap", "streamsMap",
				"platformsMap", "userTypesMap", "projectsMap", "technologiesMap", "userSkillSet", "userPlatformsSet");

		serviceManager.getUserService().getSupervisorMap(supervisorMap, (Long) session.get("userId"), false);
		serviceManager.getUserService().getLineManagersMap(lineManagerMap);
		if (lineManagerId == null || lineManagerId == 0l) {
			lineManagerId = (Long) session.get("lineManagerId");
		}
		if (session.get("role") != null && ((String) session.get("role")).equalsIgnoreCase("LINE MANAGER")) {
			supervisorMap.put(((Long) session.get("userId")), ((String) session.get("fullName")));
			if (supervisorId == null || supervisorId == 0l) {
				supervisorId = (Long) session.get("userId");
				hiddenSupervisorId = supervisorId;
			}

		}

		serviceManager.getUserService().getUsersToBackFill(userToBackFillMap);
		serviceManager.getPrivilegeService().getPrivilegesMap(privilegesMap);
		serviceManager.getRolesService().getRolesMap(rolesMap);
		serviceManager.getStreamsService().getStreamsMap(streamsMap);
		serviceManager.getPillarService().getPillarsMap(platformsMap, null);
		serviceManager.getUserTypesService().getUserTypesMap(userTypesMap);
		serviceManager.getTechnologiesService().getTechnologiesMap(technologiesMap);
		serviceManager.getProjectService().getProjectsMap(projectsMap, null);
		stableTeams = serviceManager.getNetworkCodesService().getStableTeams(stableTeams);

		session.put("supervisorMap", supervisorMap);
		session.put("lineManagerMap", lineManagerMap);
		session.put("userToBackFillMap", userToBackFillMap);
		session.put("privilegesMap", privilegesMap);
		session.put("rolesMap", rolesMap);
		session.put("streamsMap", streamsMap);
		session.put("platformsMap", platformsMap);
		session.put("userTypesMap", userTypesMap);
		session.put("technologiesMap", technologiesMap);
		session.put("projectsMap", projectsMap);

		goAssignedAccounts();
		goGetpillarAppsForContribution();
	}

	@SkipValidation
	public String getStableTeamsData() {
		stableTeamsmap.clear();
		stableTeamsmap.put(-1L, "All");
		stableTeams = serviceManager.getNetworkCodesService().getStableTeams(null);
		for (StableTeams s : stableTeams)
			stableTeamsmap.put(s.getId(), s.getTeamName());
		return SUCCESS;
	}

	@SkipValidation
	public String getUserStableTeamsData() {
		stableTeams.clear();
		stableTeams = serviceManager.getNetworkCodesService().getUserStableTeams((Long) session.get("userId"));
		if (stableTeams.size() <= 0)
			stableTeams = serviceManager.getNetworkCodesService().getStableTeams(null);
		return SUCCESS;
	}

	@SkipValidation
	public String saveUserStableTeamsData() {
		// serviceManager.getNetworkCodesService().saveUserStableTeams(stableTeams);
		try {
			if (userAPACToSave.get(0).getStableTeams() != null && userAPACToSave.get(0).getStableTeams().get(0) != null
					&& userAPACToSave.get(0).getStableTeams().get(0).getId() == null) {
				stableTeams = serviceManager.getNetworkCodesService().getStableTeams(null);
				List<StableTeams> data = userAPACToSave.get(0).getStableTeams();
				int i = 0;
				for (StableTeams s : data) {
					s.setId(stableTeams.get(i).getId());
					s.setProject(stableTeams.get(i).getProject());
					s.setTeamName(stableTeams.get(i).getTeamName());
					i += 1;
				}

				serviceManager.getUserService().saveUserStableTeams(data, (Long) session.get("userId"));
			} else {
				serviceManager.getNetworkCodesService().saveUserStableTeams(stableTeams);
			}

			if (stableTeams.size() <= 0)
				addActionMessage("User Contribution Saved !!");
		} catch (Exception e) {
			addActionMessage("Unable to save stable team data");
		}

		return SUCCESS;
	}

	@SkipValidation
	public String goEditUser() {
		try {
			getSelectBoxMap();

			User modalUser = serviceManager.getUserService().getUser(Long.valueOf(id));
			stableTeams = modalUser.getStableTeams();
			if (stableTeams.size() <= 0) {
				stableTeams = serviceManager.getNetworkCodesService().getStableTeams(null);
				Collections.sort(stableTeams, Collections.reverseOrder());
			}

			Collections.sort(stableTeams);
			lineManagerId = Long.parseLong(serviceManager.getUserService().getUserLineManager(modalUser.getId()) + "");
			id = modalUser.getId() + "";
			name = modalUser.getName();
			firstName = modalUser.getFirstName();
			middleName = modalUser.getMiddleName();
			lastName = modalUser.getLastName();
			userName = modalUser.getUserName();
			email = modalUser.getEmail();
			supervisorId = modalUser.getSupervisorId();
			roleId = modalUser.getRoleId();
			privilegeId = modalUser.getPrivilegeId();
			userTypeId = modalUser.getUserTypeId();
			status = modalUser.getStatus().toString();

			jobStage = modalUser.getJobStage();
			jobDescription = modalUser.getJobDescription();

			phoneNo = modalUser.getPhone();
			mobileNo = modalUser.getMobile();

			personalNo = modalUser.getPersonalNo();
			eriProjNo = modalUser.getEriProjNo();
			allocation = modalUser.getAllocation();
			vzid = modalUser.getVzid();
			mhrCategory = modalUser.getMhrCategory();

			DateFormat format = new SimpleDateFormat("MM/dd/yyyy");
			strDoj = modalUser.getDoj() == null ? "" : format.format(modalUser.getDoj());
			strDobilling = modalUser.getDateOfBilling() == null ? "" : format.format(modalUser.getDateOfBilling());
			strReleaseDate = modalUser.getReleseDate() == null ? "" : format.format(modalUser.getReleseDate());
			strResignDate = modalUser.getResignDate() == null ? "" : format.format(modalUser.getResignDate());

			certifications = modalUser.getCertifications();
			streamId = modalUser.getStream();
			backfilloff = modalUser.getBackFillOff();

			selectedPlatforms.clear();
			selectedPlatforms.addAll(modalUser.getUserPlatforms());

			primaryProjectId = modalUser.getPrimaryProjectId();
			selectedProjects.clear();
			selectedProjects.addAll(modalUser.getUserProjects());
			competencyScore = modalUser.getCompetencyScore();

			primarySkillId = modalUser.getPrimarySkillId();
			selectedTechnologies.clear();
			selectedTechnologies.addAll(modalUser.getUserTechnologies());
			techScore = modalUser.getTechScore();

			cxContribution = modalUser.getCxContribution();
			confContribution = modalUser.getConfContribution();
			voipContribution = modalUser.getVoipContribution();
			nsrsContribution = modalUser.getNsrsContribution();
			emdaContribution = modalUser.getEmdaContribution();
			cmproxyContribution = modalUser.getCmproxyContribution();
			adminContribution = modalUser.getAdminContribution();

			location = modalUser.getLocationId() + "";

			List<UserSkills> userSkillSet = serviceManager.getUserService().getUserSkill(Long.parseLong(id), null);
			if (userSkillSet != null && userSkillSet.size() > 0) {
				Map<Long, String> userSkillsMap = new LinkedHashMap<Long, String>();
				for (UserSkills us : userSkillSet) {
					userSkillsMap.put(us.getTechnology().getId(), us.getTechnology().getTechnologyName());
				}
				session.put("userSkillSet", userSkillsMap);
			} else {
				session.put("userSkillSet", new ArrayList<UserSkills>());
			}

			List<UserPlatforms> userProjectSet = serviceManager.getUserService().getUserPlatforms(Long.parseLong(id),
					null);
			if (userProjectSet != null && userProjectSet.size() > 0) {
				Map<Long, String> userPlatformsMap = new LinkedHashMap<Long, String>();
				for (UserPlatforms up : userProjectSet) {
					userPlatformsMap.put(up.getPlatform().getId(), up.getPlatform().getPillarName());
				}
				session.put("userPlatformsSet", userPlatformsMap);
			} else {
				session.put("userPlatformsSet", new ArrayList<UserProjects>());
			}
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	@SkipValidation
	public String goTechScore() {
		try {
			if (skillId != null && skillId != -1) {
				selectedYear = Long.valueOf(Calendar.getInstance().get(Calendar.YEAR));
				halfYear = (Calendar.getInstance().get(Calendar.MONTH) > 5) ? "2H" : "1H";
				goGetUserTechScore();
			}
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	@SkipValidation
	public String goGetUserTechScore() {
		try {
			techScore = null;
			if (skillId != null && skillId != -1) {
				UserSkillScore us = serviceManager.getUserService().getUserSkillScore(Long.parseLong(id), skillId,
						selectedYear, halfYear);
				if (us != null) {
					userTechScoreId = us.getId();
					techScore = us.getTechScore();
				}
			}
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	@SkipValidation
	public String saveTechScore() {
		try {
			UserSkillScore uss = new UserSkillScore();
			if (techScore != null && !techScore.isNaN()) {
				if (userTechScoreId != null && userTechScoreId != 0l) {
					uss.setId(userTechScoreId);
				}
				List<UserSkills> usList = serviceManager.getUserService().getUserSkill(Long.parseLong(id), skillId);
				com.egil.pts.dao.domain.User u = new com.egil.pts.dao.domain.User();
				u.setId(Long.parseLong(id));
				Technologies t = new Technologies();
				t.setId(skillId);
				if (usList == null) {
					UserSkills us = new UserSkills();
					us.setUser(u);
					us.setTechnology(t);
					uss.setUserSkill(us);
				} else {
					uss.setUserSkill(usList.get(0));
				}
				uss.setUser(u);
				uss.setTechnology(t);
				uss.setYear(selectedYear);
				uss.setYearHalf(halfYear);
				uss.setTechScore(techScore);

				serviceManager.getUserService().saveUserSkillScore(uss);
				message = "Saved Successfully...";
			}
		} catch (Throwable e) {
			e.printStackTrace();
			message = "Save Unsuccessful...";
		}
		return SUCCESS;
	}

	@SkipValidation
	public String goCompetencyScore() {
		try {
			if (userPillarId != null && userPillarId != -1) {
				selectedYear = Long.valueOf(Calendar.getInstance().get(Calendar.YEAR));
				halfYear = (Calendar.getInstance().get(Calendar.MONTH) > 5) ? "2H" : "1H";
				goGetUserAppCompScore();
			}
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	@SkipValidation
	public String goGetUserAppCompScore() {
		try {
			competencyScore = null;
			if (userPillarId != null && userPillarId != -1) {
				UserPlatformCompetencyScore us = serviceManager.getUserService()
						.getUserPlatformCompScore(Long.parseLong(id), userPillarId, selectedYear, halfYear);
				if (us != null) {
					userAppCompScoreId = us.getId();
					competencyScore = us.getCompScore();
				}
			}
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	@SkipValidation
	public String saveCompetencyScore() {
		try {
			if (competencyScore != null && !competencyScore.isNaN()) {
				UserPlatformCompetencyScore uss = new UserPlatformCompetencyScore();
				if (userAppCompScoreId != null && userAppCompScoreId != 0l) {
					uss.setId(userAppCompScoreId);
				}
				List<UserPlatforms> usList = serviceManager.getUserService().getUserPlatforms(Long.parseLong(id),
						userPillarId);
				com.egil.pts.dao.domain.User u = new com.egil.pts.dao.domain.User();
				u.setId(Long.parseLong(id));
				Pillar t = new Pillar();
				t.setId(userPillarId);
				if (usList == null) {
					UserPlatforms us = new UserPlatforms();
					us.setUser(u);
					us.setPlatform(t);
					uss.setUserPlatform(us);
				} else {
					uss.setUserPlatform(usList.get(0));
				}
				uss.setUser(u);
				uss.setPlatform(t);
				uss.setYear(selectedYear);
				uss.setYearHalf(halfYear);
				uss.setCompScore(competencyScore);

				serviceManager.getUserService().saveUserPlatformCompScore(uss);
				message = "Saved Successfully...";
			}
		} catch (Throwable e) {
			e.printStackTrace();
			message = "Save Unsuccessful...";
		}
		return SUCCESS;
	}

	@Validations(requiredStrings = {
			@RequiredStringValidator(type = ValidatorType.FIELD, fieldName = "name", message = "Please enter Name.", shortCircuit = true),
			@RequiredStringValidator(type = ValidatorType.FIELD, fieldName = "userName", message = "Please enter SIGNUM.", shortCircuit = true),
			@RequiredStringValidator(type = ValidatorType.FIELD, fieldName = "status", message = "Please select Status.", shortCircuit = true) }, requiredFields = {
					@RequiredFieldValidator(type = ValidatorType.FIELD, fieldName = "supervisorId", message = "Please select Supervisor.", shortCircuit = true),
					@RequiredFieldValidator(type = ValidatorType.FIELD, fieldName = "privilegeId", message = "Please select Privilege.", shortCircuit = true),
					@RequiredFieldValidator(type = ValidatorType.FIELD, fieldName = "roleId", message = "Please select Role.", shortCircuit = true),
					@RequiredFieldValidator(type = ValidatorType.FIELD, fieldName = "userTypeId", message = "Please select Employee Type.", shortCircuit = true) })
	// , emails = { @EmailValidator(type = ValidatorType.FIELD, fieldName =
	// "email", message = "Please enter a valid e-mail address.", shortCircuit =
	// true) }
	public String addUser() {
		try {
			stableTeams = serviceManager.getNetworkCodesService().getStableTeams(null);
			List<StableTeams> data = userAPACToSave.get(0).getStableTeams();
			int i = 0;
			for (StableTeams s : data) {
				s.setId(stableTeams.get(i).getId());
				s.setProject(stableTeams.get(i).getProject());
				s.setTeamName(stableTeams.get(i).getTeamName());
				i += 1;
			}

			User userObj = new User();
			setUserObject(userObj);
			userObj.setCreatedBy((String) session.get("username"));
			userObj.setCreatedDate(new Date());
			serviceManager.getUserService().createUser(userObj);
			userObj = serviceManager.getUserService().getUser(userObj.getUserName(), true);
			if (lineManagerId != null)
				serviceManager.getUserService().saveUserLineManager(userObj);
			serviceManager.getUserService().saveUserStableTeams(data, userObj.getId());

		} catch (Throwable e) {
			e.printStackTrace();
			goCreateUser();
			if (e.getMessage() != null && e.getMessage().equalsIgnoreCase("DUP_USER")) {
				addActionMessage("User Already Exists");
				return ERROR;
			}
			addActionMessage("Invalid input...");
			return ERROR;
		}
		return SUCCESS;
	}

	@Validations(requiredStrings = {
			@RequiredStringValidator(type = ValidatorType.FIELD, fieldName = "name", message = "Please enter Name.", shortCircuit = true),
			@RequiredStringValidator(type = ValidatorType.FIELD, fieldName = "userName", message = "Please enter SIGNUM.", shortCircuit = true),
			@RequiredStringValidator(type = ValidatorType.FIELD, fieldName = "status", message = "Please select Status.", shortCircuit = true) }, requiredFields = {
					@RequiredFieldValidator(type = ValidatorType.FIELD, fieldName = "supervisorId", message = "Please select Supervisor.", shortCircuit = true),
					@RequiredFieldValidator(type = ValidatorType.FIELD, fieldName = "privilegeId", message = "Please select Privilege.", shortCircuit = true),
					@RequiredFieldValidator(type = ValidatorType.FIELD, fieldName = "roleId", message = "Please select Role.", shortCircuit = true),
					@RequiredFieldValidator(type = ValidatorType.FIELD, fieldName = "userTypeId", message = "Please select Employee Type.", shortCircuit = true) })
	public String modifyUser() {
		try {
			User userObj = new User();
			setUserObject(userObj);
			serviceManager.getUserService().modifyUser(userObj);

			stableTeams = serviceManager.getNetworkCodesService().getStableTeams(null);

			serviceManager.getUserService().saveUserLineManager(userObj);

			List<StableTeams> data = userAPACToSave.get(0).getStableTeams();
			int i = 0;
			for (StableTeams s : data) {

				s.setId(stableTeams.get(i).getId());
				s.setProject(stableTeams.get(i).getProject());
				s.setTeamName(stableTeams.get(i).getTeamName());
				i += 1;
			}
			serviceManager.getUserService().saveUserStableTeams(data, userObj.getId());

		} catch (Throwable e) {
			e.printStackTrace();
			goEditUser();
			addActionMessage("Invalid input...");
			return ERROR;
		}
		return SUCCESS;
	}

	private void setUserObject(User userObj) {
		if (StringHelper.isNotEmpty(id)) {
			userObj.setId(Long.valueOf(id));
		}
		userObj.setLineManagerId(lineManagerId);
		if (StringHelper.isNotEmpty(name)) {
			userObj.setName(name);
		} else {
			userObj.setName(firstName + " " + lastName);
		}
		userObj.setFirstName(firstName);
		userObj.setMiddleName(middleName);
		userObj.setLastName(lastName);
		userObj.setUserName(userName);
		userObj.setEmail(email);
		userObj.setSupervisorId(supervisorId);
		if (roleId == null) {
			userObj.setRoleId(3l);
		} else {
			userObj.setRoleId(roleId);
		}
		if (privilegeId == null) {
			userObj.setPrivilegeId(1l);
		} else {
			userObj.setPrivilegeId(privilegeId);
		}
		userObj.setUserTypeId(userTypeId);
		userObj.setUpdatedBy((String) session.get("username"));
		userObj.setUpdatedDate(new Date());

		userObj.setUserPlatforms(selectedPlatforms);

		if ((primarySkillId != null) && (primarySkillId.longValue() != -1L)) {
			userObj.setPrimarySkillId(primarySkillId);
		}
		userObj.setUserTechnologies(selectedTechnologies);

		if ((primarySkillId != null) && (primarySkillId.longValue() != -1L)) {
			userObj.getUserTechnologies().add(primarySkillId);
		}
		userObj.setTechScore(techScore);

		/*
		 * if ((primaryProjectId != null) && (primaryProjectId.longValue() != -1L)) {
		 * userObj.setPrimaryProjectId(primaryProjectId); }
		 * userObj.setUserProjects(selectedProjects); if ((primaryProjectId != null) &&
		 * (primaryProjectId.longValue() != -1L)) {
		 * userObj.getUserProjects().add(primaryProjectId); }
		 */
		// userObj.setCompetencyScore(competencyScore);
		userObj.setCertifications(certifications);
		userObj.setDoj(getDoj());
		userObj.setDateOfBilling(getDobilling());
		userObj.setReleseDate(getReleaseDate());
		userObj.setResignDate(getResignDate());
		userObj.setLocationId((location != null && !location.isEmpty()) ? Long.parseLong(location) : 17l);
		userObj.setPhone(phoneNo);
		userObj.setMobile(mobileNo);

		userObj.setComments(comments);
		userObj.setReleaseComments(releaseComments);

		userObj.setPersonalNo(personalNo);
		userObj.setAllocation(allocation);
		userObj.setVzid(vzid);
		userObj.setLineManagerId(lineManagerId);
		userObj.setEriProjNo(eriProjNo);

		userObj.setJobStage(jobStage);
		userObj.setJobDescription(jobDescription);

		if (status != null && status != "-1") {
			userObj.setStatus(status);
		}

		// userObj.setPlatformId(platform);
		userObj.setStream(streamId);
		userObj.setBackFillOff(backfilloff);

		userObj.setUserAcctPillarAppContribution(userAPACToSave);

		userObj.setUserAccounts(selectedAccountIds);
		userObj.setLineManagerId(lineManagerId);
		/*
		 * userObj.setCxContribution(cxContribution);
		 * userObj.setConfContribution(confContribution);
		 * userObj.setVoipContribution(voipContribution);
		 * userObj.setNsrsContribution(nsrsContribution);
		 * userObj.setEmdaContribution(emdaContribution);
		 * userObj.setCmproxyContribution(cmproxyContribution);
		 * userObj.setAdminContribution(adminContribution);
		 */

		userObj.setTimesheetFlag("Y");

		userObj.setMhrCategory(mhrCategory);

	}

	@SkipValidation
	public String userMACDOperation() {
		try {
			if (oper.equalsIgnoreCase("del")) {
				if (id != null & !id.equalsIgnoreCase("")) {
					serviceManager.getUserService().deleteUsers(Utility.getListFromCommaSeparated(id));
				}
			}
		} catch (Throwable e) {
			e.printStackTrace();
		}

		return SUCCESS;
	}

	@SkipValidation
	public String goViewProfile() {
		try {
			serviceManager.getTechnologiesService().getTechnologiesMap(technologiesMap);
			Long userid = (Long) session.get("userId");
			User modalUser = serviceManager.getUserService().getUser(userid);
			id = modalUser.getId() + "";
			name = modalUser.getName();
			firstName = modalUser.getFirstName();
			middleName = modalUser.getMiddleName();
			lastName = modalUser.getLastName();
			userName = modalUser.getUserName();
			supervisor = modalUser.getUserSupervisorId();
			supervisorName = modalUser.getSupervisor();
			vzid = modalUser.getVzid();
			mhrCategory = modalUser.getMhrCategory();
			phoneNo = modalUser.getPhone();
			personalNo = modalUser.getPersonalNo();
			mobileNo = modalUser.getMobile();
			email = modalUser.getEmail();
			role = modalUser.getRole();
			userRoleId = modalUser.getUserRoleId();
			stream = modalUser.getStreamName();
			streamId = modalUser.getStream();
			privilege = modalUser.getPrivilege();
			userType = modalUser.getUserType();
			userTypeId = modalUser.getUserTypeId();

			selectedPlatforms.clear();
			selectedPlatforms.addAll(modalUser.getUserPlatforms());

			primarySkillId = modalUser.getPrimarySkillId();
			selectedTechnologies.clear();
			selectedTechnologies.addAll(modalUser.getUserTechnologies());

			session.put("technologiesMap", technologiesMap);

		} catch (Throwable e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	@Validations(requiredStrings = {
			@RequiredStringValidator(type = ValidatorType.FIELD, fieldName = "name", message = "Please enter name.", shortCircuit = true),
			@RequiredStringValidator(type = ValidatorType.FIELD, fieldName = "vzid", message = "Please enter VZID.", shortCircuit = true),
			@RequiredStringValidator(type = ValidatorType.FIELD, fieldName = "email", message = "Please enter e-mail address.", shortCircuit = true) }, emails = {
					@EmailValidator(type = ValidatorType.FIELD, fieldName = "email", message = "Please enter a valid e-mail address.", shortCircuit = true) })
	public String saveMyProfile() {
		try {
			User userObj = serviceManager.getUserService().getUser(Long.valueOf(id));
			userObj.setName(name);
			userObj.setFirstName(firstName);
			userObj.setMiddleName(middleName);
			userObj.setLastName(lastName);
			userObj.setEmail(email);
			userObj.setVzid(vzid);
			userObj.setPersonalNo(personalNo);
			userObj.setPhone(phoneNo);
			userObj.setMobile(mobileNo);
			userObj.setUpdatedBy((String) session.get("username"));
			userObj.setUpdatedDate(new Date());
			// userObj.setUserPlatforms(selectedPlatforms);
			userObj.setPrimarySkillId(primarySkillId);
			userObj.setUserTechnologies(selectedTechnologies);
			if ((primarySkillId != null) && (primarySkillId.longValue() != -1L)) {
				userObj.getUserTechnologies().add(primarySkillId);
			}
			userObj.setTimesheetFlag("Y");
			serviceManager.getUserService().modifyUser(userObj);
			if (photoUpload != null) {
				String filePath = "/applications/tomcat/usersImage/"; // request.getSession().getServletContext().getRealPath("/");
				File fileToCreate = new File(filePath, (String) session.get("username") + ".png");

				FileUtils.copyFile(photoUpload, fileToCreate);
			}
			serviceManager.getTechnologiesService().getTechnologiesMap(technologiesMap);
			addActionMessage("Successfully saved..");
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	@SkipValidation
	public String goResetPassword() {
		return SUCCESS;
	}

	@Validations(requiredStrings = {
			@RequiredStringValidator(type = ValidatorType.FIELD, fieldName = "oldPassword", message = "Please enter Old Password.", shortCircuit = true),
			@RequiredStringValidator(type = ValidatorType.FIELD, fieldName = "newPassword", message = "Please enter New Password.", shortCircuit = true),
			@RequiredStringValidator(type = ValidatorType.FIELD, fieldName = "confirmPassword", message = "Please enter Confirm Password.", shortCircuit = true) }, fieldExpressions = {
					@FieldExpressionValidator(message = "", fieldName = "newPassword", expression = "!(oldPassword.equals(newPassword))", key = "pts.reset.password.validation") }, regexFields = {
							@RegexFieldValidator(type = ValidatorType.FIELD, fieldName = "newPassword", message = "", key = "pts.password.validate", regexExpression = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)\\w(\\w|[?\\\\/|!@#$%^&*()_+{};':,\"=\\`\\~\\[\\]-]){7,78}$") })
	public String resetPassword() {
		try {
			serviceManager.getUserService().resetPassword((String) session.get("username"),
					DesEncrypter.encrypt(newPassword), DesEncrypter.encrypt(oldPassword));
			newPassword = "";
			addActionMessage("Successfully saved..");
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	public String gomasterResetPassword() {

		try {
			if (getCurrentUserName() != null && !getCurrentUserName().isEmpty()) {
				serviceManager.getUserService().resetPassword(getCurrentUserName(), DesEncrypter.encrypt(newPassword),
						null);
				newPassword = "";
				addActionMessage("Successfully Reset New Password.");
			} else {
				setCurrentUserName("");
				setNewPassword("Testing123");
			}
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return SUCCESS;

	}

	public String goGetPasswordmaster() {
		try {
			if (getCurrentUserName() != null && !getCurrentUserName().isEmpty()) {
				newPassword = serviceManager.getUserService().getPassword(getCurrentUserName());
				if (newPassword != null && newPassword.length() > 1) {
					DesEncrypter d = new DesEncrypter();
					newPassword = d.decrypt(newPassword);
				}
			} else {
				setCurrentUserName("");
			}
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	public String changePassword() {
		try {
			if (userName == null) {
				return INITIAL;
			}
			boolean result = serviceManager.getUserService().resetpassword(userName, email);
			if (result) {
				addActionError("New Passowrd Generated");
				return SUCCESS;
			} else {
				addActionError("Invalid UserName or Email ");
				return ERROR;
			}

		} catch (Throwable e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	@SkipValidation
	public String userUtilization() {
		if (selectedMonth == null || (selectedMonth != null && selectedMonth.equals(""))) {
			selectedMonth = new SimpleDateFormat("MMM").format(Calendar.getInstance().getTime());
		}
		if (selectedYear == null) {
			selectedYear = Long.valueOf(Calendar.getInstance().get(Calendar.YEAR));
		}
		if (session.get("role") != null && ((String) session.get("role")).equalsIgnoreCase("LINE MANAGER")) {
			if ((searchSupervisor == null || (searchSupervisor != null && searchSupervisor.isEmpty()))) {
				searchSupervisor = (Long) session.get("userId") + "";
			}

		}
		return SUCCESS;
	}

	@SkipValidation
	public String goUserUtilization() {
		Long userId = null;
		try {
			if (session.get("rowNum") == null) {
				session.put("rowNum", rows);
			} else {
				if (rows != null && (rows.intValue() != ((Integer) session.get("rowNum")).intValue())) {
					session.put("rowNum", rows);
				}
			}
			if (selectedYear == null) {
				selectedYear = Long.valueOf(Calendar.getInstance().get(Calendar.YEAR));
			}
			if (selectedMonth == null || (selectedMonth != null && selectedMonth.equals(""))) {
				selectedMonth = new SimpleDateFormat("MMM").format(Calendar.getInstance().getTime());
			}
			/*
			 * if (selectedMonth == null || (selectedMonth != null &&
			 * selectedMonth.equals(""))) { selectedMonth = new
			 * SimpleDateFormat("MMM").format(Calendar.getInstance().getTime()); }
			 */
			if (session.get("role") != null && ((String) session.get("role")).equalsIgnoreCase("LINE MANAGER")) {
				userId = (Long) session.get("userId");
			}
			if (searchSupervisor != null && !searchSupervisor.isEmpty()) {
				userId = Long.parseLong(searchSupervisor);
			}
			SummaryResponse<ResourceUtilization> summary = null;
			if (userId != null && userId != -1) {
				summary = serviceManager.getUserService().getResourceUtilizatoinDetailsNew(userId,
						selectedYear.intValue(), selectedMonth, null, descrepencyFlag);
				userUtilizationGridModel = summary.getEnitities();

				records = userUtilizationGridModel.size();
			}

			rows = records;
			// calculate the total pages for the query
			total = (int) Math.ceil((double) records / (double) records);
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	@SkipValidation
	public String sendMailResourceUtilization() {
		try {
			Long userId = null;
			if (selectedYear == null) {
				selectedYear = Long.valueOf(Calendar.getInstance().get(Calendar.YEAR));
			}
			if (selectedMonth == null || (selectedMonth != null && selectedMonth.equals(""))) {
				selectedMonth = new SimpleDateFormat("MMM").format(Calendar.getInstance().getTime());
			}
			if (session.get("role") != null && ((String) session.get("role")).equalsIgnoreCase("LINE MANAGER")) {
				userId = (Long) session.get("userId");
			}
			if (searchSupervisor != null && !searchSupervisor.isEmpty()) {
				userId = Long.parseLong(searchSupervisor);
			}

			serviceManager.getUserService().sendMailResourceUtilization(userId, selectedYear.intValue(), selectedMonth);

			message = "Email sent Successfully...";
		} catch (Throwable e) {
			e.printStackTrace();
			message = "Email not sent...";
		}

		return SUCCESS;
	}

	@SkipValidation
	public String exportResourceStableDetails() {
		try {
			Pagination pagination = new Pagination();
			page = Integer.parseInt(session.get("currentPage") + "");
			if (page == 0)
				page = 1;
			int to = (Integer.parseInt(session.get("rowNum") + "") * page);
			int from = to - (Integer.parseInt(session.get("rowNum") + ""));
			pagination.setOffset(from);
			pagination.setSize(to);

			SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
			String timeStamp = df.format(new Date());
			String filePath = getText("rico.summary.export.path");
			File dir = new File(filePath);
			if (!dir.exists()) {
				@SuppressWarnings("unused")
				boolean result = dir.mkdirs();
			}
			Long userId = (Long) session.get("userId");
			if (session.get("role") != null && (((String) session.get("role")).equalsIgnoreCase("ADMIN"))) {
				userId = null;
			}
			SummaryResponse<User> summary = serviceManager.getUserService().getUserSummary(pagination,
					getSearchSortBean(), false);

			String fileName = filePath + getText("rico.summary.export.users.stable.file.name") + timeStamp + ".xlsx";
			DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			filePath = serviceManager.getUserUtilizationService().generateUserStableExcel(summary, fileName, false,
					(stableTeamid > 0 || showAllStableTeams));
			fileName = getFileName(filePath);
			pushFileToClient(filePath, fileName);
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return null;
	}

	@SkipValidation
	public String exportAllResourceStableDetails() {
		try {

			SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
			String timeStamp = df.format(new Date());
			String filePath = getText("rico.summary.export.path");
			File dir = new File(filePath);
			if (!dir.exists()) {
				@SuppressWarnings("unused")
				boolean result = dir.mkdirs();
			}
			Long userId = (Long) session.get("userId");
			if (session.get("role") != null && (((String) session.get("role")).equalsIgnoreCase("ADMIN"))) {
				userId = null;
			}
			String fileName = filePath + getText("rico.summary.export.users.stable.file.name") + timeStamp + ".xlsx";

			SummaryResponse<User> summary = serviceManager.getUserService().getUserSummary(getPaginationObject(),
					getSearchSortBean(), false);

			filePath = serviceManager.getUserUtilizationService().generateUserStableExcel(summary, fileName, true,
					(stableTeamid > 0 || showAllStableTeams));
			fileName = getFileName(filePath);
			pushFileToClient(filePath, fileName);
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return null;
	}

	public List<User> getGridModel() {
		return gridModel;
	}

	public void setGridModel(List<User> gridModel) {
		this.gridModel = gridModel;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public Long getSupervisor() {
		return supervisor;
	}

	public void setSupervisor(Long supervisor) {
		this.supervisor = supervisor;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPrivilege() {
		return privilege;
	}

	public void setPrivilege(String privilege) {
		this.privilege = privilege;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Long getUserRoleId() {
		return userRoleId;
	}

	public void setUserRoleId(Long userRoleId) {
		this.userRoleId = userRoleId;
	}

	public Long getUserSupervisorId() {
		return userSupervisorId;
	}

	public void setUserSupervisorId(Long userSupervisorId) {
		this.userSupervisorId = userSupervisorId;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	public String getSupervisorName() {
		return supervisorName;
	}

	public void setSupervisorName(String supervisorName) {
		this.supervisorName = supervisorName;
	}

	protected Pagination getPaginationObject() {
		Pagination pagination = new Pagination();
		int to = (rows * page);
		session.put("currentPage", page);
		int from = to - rows;
		pagination.setOffset(from);
		pagination.setSize(to);
		return pagination;
	}

	protected SearchSortContainer getSearchSortBean() {
		SearchSortContainer searchSortObj = new SearchSortContainer();
		searchSortObj.setSearchField(searchField);
		searchSortObj.setSearchString(searchString);
		searchSortObj.setSidx(sidx);
		searchSortObj.setSord(sord);

		searchSortObj.setSearchUserName(searchUserName);
		searchSortObj.setSearchSupervisor(searchSupervisor);
		if (searchStatus != null && searchStatus.equalsIgnoreCase("All")) {
			searchSortObj.setSearchStatus("");
		} else {
			searchSortObj.setSearchStatus(searchStatus);
		}
		searchSortObj.setSearchByStable(showAllStableTeams);
		searchSortObj.setSearchDOJ(searchDOJ);
		searchSortObj.setSearchDOBilling(searchDOBilling);
		searchSortObj.setSearchLocation(searchLocation);
		searchSortObj.setLoggedInFullName((String) session.get("fullName"));
		searchSortObj.setLoggedInId((Long) session.get("userId") + "");
		searchSortObj.setLoggedInUserRole((String) session.get("role"));

		searchSortObj.setAllReporteesFlag(allReporteesFlag);

		searchSortObj.setSearchStream(searchStream);
		searchSortObj.setSearchUserType(searchUserType);
		searchSortObj.setSearchTechnology(searchTechnology);
		searchSortObj.setStableTeamId(stableTeamid);
		searchSortObj.setLineManagerId(lineManagerId == null ? (Long) session.get("lineManagerId") : lineManagerId);
		return searchSortObj;
	}

	public Integer getRows() {
		return rows;
	}

	public void setRows(Integer rows) {
		this.rows = rows;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public String getSord() {
		return sord;
	}

	public void setSord(String sord) {
		this.sord = sord;
	}

	public String getSidx() {
		return sidx;
	}

	public void setSidx(String sidx) {
		this.sidx = sidx;
	}

	public String getSearchField() {
		return searchField;
	}

	public void setSearchField(String searchField) {
		this.searchField = searchField;
	}

	public String getSearchString() {
		return searchString;
	}

	public void setSearchString(String searchString) {
		this.searchString = searchString;
	}

	public Integer getTotalrows() {
		return totalrows;
	}

	public void setTotalrows(Integer totalrows) {
		this.totalrows = totalrows;
	}

	public String getSearchOper() {
		return searchOper;
	}

	public void setSearchOper(String searchOper) {
		this.searchOper = searchOper;
	}

	public String getOper() {
		return oper;
	}

	public void setOper(String oper) {
		this.oper = oper;
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public Integer getRecords() {
		return records;
	}

	public void setRecords(Integer records) {
		this.records = records;
		if (this.records > 0 && this.rows > 0) {
			this.total = (int) Math.ceil((double) this.records / (double) this.rows);
		} else {
			this.total = 0;
		}
	}

	public boolean isLoadonce() {
		return loadonce;
	}

	public void setLoadonce(boolean loadonce) {
		this.loadonce = loadonce;
	}

	public File getPhotoUpload() {
		return photoUpload;
	}

	public void setPhotoUpload(File photoUpload) {
		this.photoUpload = photoUpload;
	}

	public String getPhotoUploadContentType() {
		return photoUploadContentType;
	}

	public void setPhotoUploadContentType(String photoUploadContentType) {
		this.photoUploadContentType = photoUploadContentType;
	}

	public String getPhotoUploadFileName() {
		return photoUploadFileName;
	}

	public void setPhotoUploadFileName(String photoUploadFileName) {
		this.photoUploadFileName = photoUploadFileName;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public Long getUserTypeId() {
		return userTypeId;
	}

	public void setUserTypeId(Long userTypeId) {
		this.userTypeId = userTypeId;
	}

	public Map<Long, String> getSupervisorMap() {
		return supervisorMap;
	}

	public void setSupervisorMap(Map<Long, String> supervisorMap) {
		this.supervisorMap = supervisorMap;
	}

	public Map<Long, String> getPrivilegesMap() {
		return privilegesMap;
	}

	public void setPrivilegesMap(Map<Long, String> privilegesMap) {
		this.privilegesMap = privilegesMap;
	}

	public Map<Long, String> getRolesMap() {
		return rolesMap;
	}

	public void setRolesMap(Map<Long, String> rolesMap) {
		this.rolesMap = rolesMap;
	}

	public Map<Long, String> getUserTypesMap() {
		return userTypesMap;
	}

	public void setUserTypesMap(Map<Long, String> userTypesMap) {
		this.userTypesMap = userTypesMap;
	}

	public Long getPrivilegeId() {
		return privilegeId;
	}

	public void setPrivilegeId(Long privilegeId) {
		this.privilegeId = privilegeId;
	}

	public Long getSupervisorId() {
		return supervisorId;
	}

	public void setSupervisorId(Long supervisorId) {
		this.supervisorId = supervisorId;
	}

	public Long getHiddenSupervisorId() {
		return hiddenSupervisorId;
	}

	public void setHiddenSupervisorId(Long hiddenSupervisorId) {
		this.hiddenSupervisorId = hiddenSupervisorId;
	}

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public Map<Long, String> getTechnologiesMap() {
		return technologiesMap;
	}

	public void setTechnologiesMap(Map<Long, String> technologiesMap) {
		this.technologiesMap = technologiesMap;
	}

	public List<Long> getSelectedPlatforms() {
		return selectedPlatforms;
	}

	public void setSelectedPlatforms(List<Long> selectedPlatforms) {
		this.selectedPlatforms = selectedPlatforms;
	}

	public void setDoj(Date doj) {
		this.doj = doj;
	}

	public void setDobilling(Date dobilling) {
		this.dobilling = dobilling;
	}

	public void setResignDate(Date resignDate) {
		this.resignDate = resignDate;
	}

	public void setReleaseDate(Date releaseDate) {
		this.releaseDate = releaseDate;
	}

	public List<Long> getSelectedTechnologies() {
		return selectedTechnologies;
	}

	public void setSelectedTechnologies(List<Long> selectedTechnologies) {
		this.selectedTechnologies = selectedTechnologies;
	}

	public Map<Long, String> getPlatformsMap() {
		return platformsMap;
	}

	public void setPlatformsMap(Map<Long, String> platformsMap) {
		this.platformsMap = platformsMap;
	}

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public Long getStreamId() {
		return streamId;
	}

	public void setStreamId(Long streamId) {
		this.streamId = streamId;
	}

	public Map<String, String> getLocationsMap() {
		return locationsMap;
	}

	public void setLocationsMap(Map<String, String> locationsMap) {
		this.locationsMap = locationsMap;
	}

	public Map<Long, String> getStreamsMap() {
		return streamsMap;
	}

	public void setStreamsMap(Map<Long, String> streamsMap) {
		this.streamsMap = streamsMap;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public Map<String, String> getStatusMap() {
		Map<String, String> statusMap = new LinkedHashMap<String, String>();
		statusMap.put("All", "All");
		statusMap.put("LTA", "LTA");
		statusMap.put("Open", "Open");
		statusMap.put("Selected", "Selected");
		statusMap.put("Induction", "Induction");
		statusMap.put("Onboard", "Onboard");
		statusMap.put("Notice Period", "Notice Period");
		statusMap.put("OffBoard", "OffBoard");
		statusMap.put("Interns/ GET", "Interns/ GET");
		statusMap.put("Non Billable", "Non Billable");
		statusMap.put("No Show", "No Show");
		statusMap.put("Others", "Others");
		return statusMap;
	}

	public Map<Long, String> getSupervisorMapForManage() {
		try {

			serviceManager.getUserService().getSupervisorMap(supervisorMap, (Long) session.get("userId"), false);
			if (session.get("role") != null && ((String) session.get("role")).equalsIgnoreCase("LINE MANAGER")) {
				supervisorMap.put(((Long) session.get("userId")), ((String) session.get("fullName")));
				searchSupervisor = (Long) session.get("userId") + "";
			}

		} catch (Throwable e) {
			e.printStackTrace();
		}
		return supervisorMap;
	}

	public Map<Long, String> getAllLineManaMapForManage() {
		try {
			serviceManager.getUserService().getLineManagersMap(lineManagerMap);
			if (lineManagerId != null || (lineManagerId != null && lineManagerId <= 1L)) {
				lineManagerId = (Long) session.get("lineManagerId");
			}

		} catch (Throwable e) {
			e.printStackTrace();
		}
		return lineManagerMap;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getBackfilloff() {
		return backfilloff;
	}

	public void setBackfilloff(String backfilloff) {
		this.backfilloff = backfilloff;
	}

	public String getStrDoj() {
		return strDoj;
	}

	public void setStrDoj(String strDoj) {
		this.strDoj = strDoj;
	}

	public String getStrDobilling() {
		return strDobilling;
	}

	public void setStrDobilling(String strDobilling) {
		this.strDobilling = strDobilling;
	}

	public String getStrResignDate() {
		return strResignDate;
	}

	public void setStrResignDate(String strResignDate) {
		this.strResignDate = strResignDate;
	}

	public String getStrReleaseDate() {
		return strReleaseDate;
	}

	public void setStrReleaseDate(String strReleaseDate) {
		this.strReleaseDate = strReleaseDate;
	}

	public Date getDoj() {
		try {
			DateFormat format = new SimpleDateFormat("MM/dd/yyyy");
			if (StringHelper.isNotEmpty(strDoj))
				doj = format.parse(strDoj);
			else
				doj = null;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return doj;
	}

	public Date getDobilling() {
		try {
			DateFormat format = new SimpleDateFormat("MM/dd/yyyy");
			if (StringHelper.isNotEmpty(strDobilling))
				dobilling = format.parse(strDobilling);
			else
				dobilling = null;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return dobilling;
	}

	public Date getResignDate() {
		try {
			DateFormat format = new SimpleDateFormat("MM/dd/yyyy");
			if (StringHelper.isNotEmpty(strResignDate))
				resignDate = format.parse(strResignDate);
			else
				resignDate = null;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return resignDate;
	}

	public Date getReleaseDate() {
		try {
			DateFormat format = new SimpleDateFormat("MM/dd/yyyy");
			if (StringHelper.isNotEmpty(strReleaseDate))
				releaseDate = format.parse(strReleaseDate);
			else
				releaseDate = null;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return releaseDate;
	}

	@SkipValidation
	public String downloadUsers() {
		try {
			HashMap<String, String> usersColHeaders = populateUsersColHeadersMap();
			SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
			String timeStamp = df.format(new Date());
			String filePath = getText("rico.summary.export.path");
			File dir = new File(filePath);
			if (!dir.exists()) {
				@SuppressWarnings("unused")
				boolean result = dir.mkdirs();
			}

			String fileName = filePath + getText("rico.summary.export.users.file.name") + timeStamp + ".xlsx";
			boolean isAdmin = false;
			if (session.get("role") != null && ((String) session.get("role")).equalsIgnoreCase("ADMIN")) {
				isAdmin = true;
			}
			BulkResponse bulkResponse = serviceManager.getUserService().exportUsersSearchResults(
					(String) session.get("username"), usersColHeaders, getSearchSortBean(), fileName, isAdmin);
			if (bulkResponse.getStatus().equalsIgnoreCase("SUCCESS")) {
				String url = bulkResponse.getFilePath();

				fileName = getFileName(url);
				pushFileToClient(url, fileName);
			} else {
				addActionError("");
				return ERROR;
			}
		} catch (Throwable throwable) {
			throwable.printStackTrace();
			return ERROR;
		}
		return null;
	}

	public String getCertifications() {
		return certifications;
	}

	public void setCertifications(String certifications) {
		this.certifications = certifications;
	}

	public Map<String, String> getUserToBackFillMap() {
		return userToBackFillMap;
	}

	public void setUserToBackFillMap(Map<String, String> userToBackFillMap) {
		this.userToBackFillMap = userToBackFillMap;
	}

	public String getPersonalNo() {
		return personalNo;
	}

	public void setPersonalNo(String personalNo) {
		this.personalNo = personalNo;
	}

	public String getAllocation() {
		return allocation;
	}

	public void setAllocation(String allocation) {
		this.allocation = allocation;
	}

	public String getEriProjNo() {
		return eriProjNo;
	}

	public void setEriProjNo(String eriProjNo) {
		this.eriProjNo = eriProjNo;
	}

	public String getReleaseComments() {
		return releaseComments;
	}

	public void setReleaseComments(String releaseComments) {
		this.releaseComments = releaseComments;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getJobStage() {
		return jobStage;
	}

	public void setJobStage(Integer jobStage) {
		this.jobStage = jobStage;
	}

	public String getJobDescription() {
		return jobDescription;
	}

	public void setJobDescription(String jobDescription) {
		this.jobDescription = jobDescription;
	}

	public String getSearchUserName() {
		return searchUserName;
	}

	public void setSearchUserName(String searchUserName) {
		this.searchUserName = searchUserName;
	}

	public String getSearchSupervisor() {
		return searchSupervisor;
	}

	public void setSearchSupervisor(String searchSupervisor) {
		this.searchSupervisor = searchSupervisor;
	}

	public String getSearchStatus() {
		return searchStatus;
	}

	public void setSearchStatus(String searchStatus) {
		this.searchStatus = searchStatus;
	}

	public String getSearchLocation() {
		return searchLocation;
	}

	public void setSearchLocation(String searchLocation) {
		this.searchLocation = searchLocation;
	}

	public String getSearchDOJ() {
		return searchDOJ;
	}

	public void setSearchDOJ(String searchDOJ) {
		this.searchDOJ = searchDOJ;
	}

	public String getSearchDOBilling() {
		return searchDOBilling;
	}

	public void setSearchDOBilling(String searchDOBilling) {
		this.searchDOBilling = searchDOBilling;
	}

	public String getStream() {
		return stream;
	}

	public void setStream(String stream) {
		this.stream = stream;
	}

	public Map<String, String> getAllocationoMap() {
		Map<String, String> allocationoMap = new LinkedHashMap<String, String>();
		allocationoMap.put("2015-Transformation", "2015-Transformation");
		allocationoMap.put("2016-Transformation", "2016-Transformation");
		allocationoMap.put("2017-Transformation", "2017-Transformation");
		allocationoMap.put("2018-Transformation", "2018-Transformation");
		allocationoMap.put("2019-Transformation", "2019-Transformation");
		allocationoMap.put("2017-Additional HC", "2017-Additional HC");
		allocationoMap.put("2018-Additional HC", "2018-Additional HC");
		allocationoMap.put("2019-Additional HC", "2019-Additional HC");
		allocationoMap.put("Others", "Others");

		return allocationoMap;
	}

	public Map<Long, String> getLocationMap() {
		Map<Long, String> locationMap = new LinkedHashMap<Long, String>();
		try {
			serviceManager.getLocationService().getLocationsMap(locationMap);
		} catch (Throwable e) {
			e.printStackTrace();
		}

		return locationMap;
	}

	public List<Integer> getJobStageList() {
		List<Integer> jobStageList = new ArrayList<Integer>();
		for (int i = 1; i <= 11; i++) {
			jobStageList.add(i);
		}

		return jobStageList;
	}

	public Map<String, String> getJobDescriptionMap() {
		Map<String, String> jobDescriptionMap = new LinkedHashMap<String, String>();
		try {
			serviceManager.getStreamsService().getJobDescrMap(jobDescriptionMap);
		} catch (Throwable e) {
			e.printStackTrace();
		}

		return jobDescriptionMap;
	}

	private HashMap<String, String> populateUsersColHeadersMap() {
		HashMap<String, String> userColHeaders = new LinkedHashMap<String, String>();
		userColHeaders.put("NAME", getText("pts.user.name"));
		userColHeaders.put("SIGNUM", getText("pts.user.signum"));
		userColHeaders.put("EMAIL", getText("pts.user.email"));
		userColHeaders.put("SUPERVISOR", getText("pts.user.supervisor"));

		userColHeaders.put("DOJ", getText("pts.user.doj"));
		userColHeaders.put("DOB", getText("pts.user.dateofbilling"));
		userColHeaders.put("JOB_STAGE", getText("pts.user.job.stage"));
		userColHeaders.put("JOB_DESCR", getText("pts.user.job.description"));

		userColHeaders.put("BACK_FILL_OFF", getText("pts.user.backfilloff"));
		userColHeaders.put("LOCATION", getText("pts.user.location"));
		userColHeaders.put("ALLOCATION", getText("pts.user.allocation"));
		userColHeaders.put("ERIPROJR", getText("pts.user.eriproj.no"));

		userColHeaders.put("PHONE_NO", getText("pts.user.phoneno"));
		userColHeaders.put("PERSONAL_NO", getText("pts.user.personal.no"));
		userColHeaders.put("MOBILE_NO", getText("pts.user.mobile"));
		userColHeaders.put("EMPLOYEE_TYPE", getText("pts.user.type"));

		userColHeaders.put("ROLE", getText("pts.user.role"));
		userColHeaders.put("STREAM", getText("pts.user.stream"));
		userColHeaders.put("STATUS", getText("pts.user.status"));
		userColHeaders.put("PLATFORMS", getText("pts.user.platform"));

		userColHeaders.put("SKILLS", getText("pts.user.skills"));
		userColHeaders.put("CERTIFICATES", getText("pts.user.certificate"));
		userColHeaders.put("COMMENTS", getText("pts.user.comments"));
		userColHeaders.put("RELEASE_COMMENTS", getText("pts.user.release.comments"));

		return userColHeaders;
	}

	public Map<String, String> getMhrCategoryMap() {

		Map<String, String> mhrCategoryMap = new LinkedHashMap<String, String>();
		try {
			serviceManager.getUserTypesService().getMhrMap(mhrCategoryMap);
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return mhrCategoryMap;
	}

	public String getVzid() {
		return vzid;
	}

	public void setVzid(String vzid) {
		this.vzid = vzid;
	}

	public boolean isAllReporteesFlag() {
		return allReporteesFlag;
	}

	public void setAllReporteesFlag(boolean allReporteesFlag) {
		this.allReporteesFlag = allReporteesFlag;
	}

	public String getSearchUserType() {
		return searchUserType;
	}

	public void setSearchUserType(String searchUserType) {
		this.searchUserType = searchUserType;
	}

	public String getSearchStream() {
		return searchStream;
	}

	public void setSearchStream(String searchStream) {
		this.searchStream = searchStream;
	}

	public Map<Long, String> getProjectsMap() {
		return projectsMap;
	}

	public void setProjectsMap(Map<Long, String> projectsMap) {
		this.projectsMap = projectsMap;
	}

	public List<Long> getSelectedProjects() {
		return selectedProjects;
	}

	public void setSelectedProjects(List<Long> selectedProjects) {
		this.selectedProjects = selectedProjects;
	}

	public Float getTechScore() {
		return techScore;
	}

	public void setTechScore(Float techScore) {
		this.techScore = techScore;
	}

	public Long getPrimarySkillId() {
		return primarySkillId;
	}

	public void setPrimarySkillId(Long primarySkillId) {
		this.primarySkillId = primarySkillId;
	}

	public Long getPrimaryProjectId() {
		return primaryProjectId;
	}

	public void setPrimaryProjectId(Long primaryProjectId) {
		this.primaryProjectId = primaryProjectId;
	}

	public Float getCxContribution() {
		return cxContribution;
	}

	public void setCxContribution(Float cxContribution) {
		this.cxContribution = cxContribution;
	}

	public Float getConfContribution() {
		return confContribution;
	}

	public void setConfContribution(Float confContribution) {
		this.confContribution = confContribution;
	}

	public Float getVoipContribution() {
		return voipContribution;
	}

	public void setVoipContribution(Float voipContribution) {
		this.voipContribution = voipContribution;
	}

	public Float getEmdaContribution() {
		return emdaContribution;
	}

	public void setEmdaContribution(Float emdaContribution) {
		this.emdaContribution = emdaContribution;
	}

	public Float getCmproxyContribution() {
		return cmproxyContribution;
	}

	public void setCmproxyContribution(Float cmproxyContribution) {
		this.cmproxyContribution = cmproxyContribution;
	}

	public Float getAdminContribution() {
		return adminContribution;
	}

	public void setAdminContribution(Float adminContribution) {
		this.adminContribution = adminContribution;
	}

	public Long getSkillId() {
		return skillId;
	}

	public void setSkillId(Long skillId) {
		this.skillId = skillId;
	}

	public String getHalfYear() {
		return halfYear;
	}

	public void setHalfYear(String halfYear) {
		this.halfYear = halfYear;
	}

	public Long getUserTechScoreId() {
		return userTechScoreId;
	}

	public void setUserTechScoreId(Long userTechScoreId) {
		this.userTechScoreId = userTechScoreId;
	}

	public Long getUserPillarId() {
		return userPillarId;
	}

	public void setUserPillarId(Long userPillarId) {
		this.userPillarId = userPillarId;
	}

	public Long getUserAppCompScoreId() {
		return userAppCompScoreId;
	}

	public void setUserAppCompScoreId(Long userAppCompScoreId) {
		this.userAppCompScoreId = userAppCompScoreId;
	}

	public Float getCompetencyScore() {
		return competencyScore;
	}

	public void setCompetencyScore(Float competencyScore) {
		this.competencyScore = competencyScore;
	}

	public List<ResourceUtilization> getUserUtilizationGridModel() {
		return userUtilizationGridModel;
	}

	public void setUserUtilizationGridModel(List<ResourceUtilization> userUtilizationGridModel) {
		this.userUtilizationGridModel = userUtilizationGridModel;
	}

	public String getMhrCategory() {
		return mhrCategory;
	}

	public void setMhrCategory(String mhrCategory) {
		this.mhrCategory = mhrCategory;
	}

	public Float getNsrsContribution() {
		return nsrsContribution;
	}

	public void setNsrsContribution(Float nsrsContribution) {
		this.nsrsContribution = nsrsContribution;
	}

	public String getSearchTechnology() {
		return searchTechnology;
	}

	public void setSearchTechnology(String searchTechnology) {
		this.searchTechnology = searchTechnology;
	}

	public boolean isDescrepencyFlag() {
		return descrepencyFlag;
	}

	public void setDescrepencyFlag(boolean descrepencyFlag) {
		this.descrepencyFlag = descrepencyFlag;
	}

	public Map<Long, String> getAccountMap() {
		return accountMap;
	}

	public void setAccountMap(Map<Long, String> accountMap) {
		this.accountMap = accountMap;
	}

	public Map<Long, String> getSelectedAccountMap() {
		return selectedAccountMap;
	}

	public void setSelectedAccountMap(Map<Long, String> selectedAccountMap) {
		this.selectedAccountMap = selectedAccountMap;
	}

	public List<Long> getSelectedAccountIds() {
		return selectedAccountIds;
	}

	public void setSelectedAccountIds(List<Long> selectedAccountIds) {
		this.selectedAccountIds = selectedAccountIds;
	}

	@SkipValidation
	public String goAssignedAccounts() {
		try {
			selectedAccountMap = new LinkedHashMap<Long, String>();
			accountMap = new LinkedHashMap<Long, String>();
			Long supervisorId = null;
			if (session.get("role") != null && ((String) session.get("role")).equalsIgnoreCase("LINE MANAGER")) {
				supervisorId = (Long) session.get("userId");

			}
			if (this.supervisorId != null && this.supervisorId != 0) {
				supervisorId = this.supervisorId;
			}
			Long userId = null;
			if (hiddenSupervisorId == supervisorId) {
				userId = StringHelper.isEmpty(id) ? null : Long.valueOf(id);
			}
			hiddenSupervisorId = supervisorId;
			serviceManager.getUserService().getUserAccountList(accountMap, selectedAccountMap, selectedAccountIds,
					userId, supervisorId);

		} catch (Throwable e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	private List<UserAcctPillarAppContribution> userAcctPillarAppContribution = new ArrayList<UserAcctPillarAppContribution>();;

	private List<UserAcctPillarAppContribution> userAPACToSave = new ArrayList<UserAcctPillarAppContribution>();

	@SkipValidation
	public String goGetpillarAppsForContribution() {
		try {
			if (selectedAccountIds != null && selectedAccountIds.size() > 0) {
				userAcctPillarAppContribution = serviceManager.getProjectService()
						.getProjectsByAccount(selectedAccountIds, StringHelper.isEmpty(id) ? null : Long.valueOf(id));

			}
		} catch (Throwable th) {
			th.printStackTrace();
		}

		return SUCCESS;
	}

	public List<UserAcctPillarAppContribution> getUserAcctPillarAppContribution() {
		return userAcctPillarAppContribution;
	}

	public void setUserAcctPillarAppContribution(List<UserAcctPillarAppContribution> userAcctPillarAppContribution) {
		this.userAcctPillarAppContribution = userAcctPillarAppContribution;
	}

	public List<UserAcctPillarAppContribution> getUserAPACToSave() {
		return userAPACToSave;
	}

	public void setUserAPACToSave(List<UserAcctPillarAppContribution> userAPACToSave) {
		this.userAPACToSave = userAPACToSave;
	}

	public boolean isNewlogic() {
		return newlogic;
	}

	public void setNewlogic(boolean newlogic) {
		this.newlogic = newlogic;
	}

	public String getCurrentUserName() {
		return currentUserName;
	}

	public void setCurrentUserName(String currentUserName) {
		this.currentUserName = currentUserName;
	}

	public List<StableTeams> getStableTeams() {
		return stableTeams;
	}

	public void setStableTeams(List<StableTeams> stableTeams) {
		this.stableTeams = stableTeams;
	}

	public Map<Long, String> getStableTeamsmap() {
		return stableTeamsmap;
	}

	public void setStableTeamsmap(Map<Long, String> stableTeamsmap) {
		this.stableTeamsmap = stableTeamsmap;
	}

	public Long getStableTeamid() {
		return stableTeamid;
	}

	public void setStableTeamid(Long stableTeamid) {
		this.stableTeamid = stableTeamid;
	}

	public boolean isShowAllStableTeams() {
		return showAllStableTeams;
	}

	public void setShowAllStableTeams(boolean showAllStableTeams) {
		this.showAllStableTeams = showAllStableTeams;
	}

	public Long getLineManagerId() {
		return lineManagerId;
	}

	public void setLineManagerId(Long lineManagerId) {
		this.lineManagerId = lineManagerId;
	}

	public Map<Long, String> getLineManagerMap() {
		return lineManagerMap;
	}

	public void setLineManagerMap(Map<Long, String> lineManagerMap) {
		this.lineManagerMap = lineManagerMap;
	}

}
