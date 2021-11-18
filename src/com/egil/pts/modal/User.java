package com.egil.pts.modal;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.egil.pts.modal.StableTeams;

public class User extends TransactionInfo {

	private static final long serialVersionUID = 1L;
	private String firstName;
	private String lastName;
	private String middleName;
	private String name;
	private String userName;
	private String supervisor;
	private Long supervisorId;
	private String email;
	private String role;
	private Long roleId;
	private String userType;
	private String privilege;
	private Long privilegeId;
	private String status;
	private Date lastLogin;
	private String password;

	private String comments;
	private String releaseComments;

	private Long userRoleId;
	private Long userTypeId;
	private Long userSupervisorId;

	private String backFillOff;
	private Date doj;
	private Date dateOfBilling;
	private Date releseDate;
	private Date resignDate;

	private String location;
	private Long locationId;
	private String locationName;
	private String region;
	private String phone;
	private String mobile;
	private Long stream;
	private Long lineManagerId;
	private String lineManager;
	private String streamName;
	private String certifications;

	private String personalNo;
	private String allocation;
	private String eriProjNo;

	private Integer jobStage;
	private String jobDescription;

	private String skillName;
	
	private String primarySkillName;

	private String platformName;

	private String projectName;
	
	private String applicationName;
	
	List<Long> userTechnologies = new ArrayList<Long>();

	List<Long> removedUserTechnologies = new ArrayList<Long>();

	List<Long> userPlatforms = new ArrayList<Long>();

	List<Long> removedUserPlatforms = new ArrayList<Long>();
	
	List<Long> userProjects = new ArrayList<Long>();
	
	List<Long> removedUserProjects = new ArrayList<Long>();
	
	List<Long> userAccounts = new ArrayList<Long>();
	
	List<Long> removedUserAccounts = new ArrayList<Long>();
	
	private List<StableTeams> stableTeams;
	private String stableTeamName;
	List<UserAcctPillarAppContribution> userAcctPillarAppContribution = new ArrayList<UserAcctPillarAppContribution>();

	private Float techScore;
	private Float competencyScore;
	private Long primarySkillId;
	private Long primaryProjectId;
	private Float cxContribution;
	private Float confContribution;
	private Float voipContribution;
	private Float nsrsContribution;
	private Float emdaContribution;
	private Float cmproxyContribution;
	private Float adminContribution;

	private String vzid;
	
	private String timesheetFlag;
	
	private String mhrCategory;
	
	private Float contribution;
	private Float stableContribution;
	
	private String supervisorName;
	
	private Long userId;
	
	private Long id;
	
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

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getSupervisor() {
		return supervisor;
	}

	public void setSupervisor(String supervisor) {
		this.supervisor = supervisor;
	}

	public Long getSupervisorId() {
		return supervisorId;
	}

	public void setSupervisorId(Long supervisorId) {
		this.supervisorId = supervisorId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getPrivilege() {
		return privilege;
	}

	public void setPrivilege(String privilege) {
		this.privilege = privilege;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getLastLogin() {
		return lastLogin;
	}

	public void setLastLogin(Date lastLogin) {
		this.lastLogin = lastLogin;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public Long getPrivilegeId() {
		return privilegeId;
	}

	public void setPrivilegeId(Long privilegeId) {
		this.privilegeId = privilegeId;
	}

	public String getSkillName() {
		return skillName == null ? "" : skillName;
	}

	public void setSkillName(String skillName) {
		this.skillName = skillName;
	}

	public String getProjectName() {
		return projectName == null ? "" : projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public List<Long> getUserTechnologies() {
		if(userTechnologies == null) {
			userTechnologies = new ArrayList<Long>(); 
		}
		return userTechnologies;
	}

	public void setUserTechnologies(List<Long> userTechnologies) {
		this.userTechnologies.addAll(userTechnologies);
	}

	public void addTechnologies(Long technology) {
		userTechnologies.add(technology);
	}

	public List<Long> getRemovedUserTechnologies() {
		return removedUserTechnologies;
	}

	public void setRemovedUserTechnologies(List<Long> removedUserTechnologies) {
		this.removedUserTechnologies.addAll(removedUserTechnologies);
	}

	public List<Long> getUserPlatforms() {
		if(userPlatforms == null) {
			userPlatforms = new ArrayList<Long>(); 
		}
		return userPlatforms;
	}

	public void setUserPlatforms(List<Long> userPlatforms) {
		this.userPlatforms = userPlatforms;
	}

	public void addPlatforms(Long platformId) {
		userPlatforms.add(platformId);
	}
	
	public List<Long> getUserProjects() {
		if(userProjects == null) {
			userProjects = new ArrayList<Long>();
		}
		return userProjects;
	}

	public void setUserProjects(List<Long> userProjects) {
		this.userProjects = userProjects;
	}

	public void addProjects(Long projectId) {
		userProjects.add(projectId);
	}

	public List<Long> getRemovedUserPlatforms() {
		return removedUserPlatforms;
	}

	public void setRemovedUserPlatforms(List<Long> removedUserPlatforms) {
		this.removedUserPlatforms = removedUserPlatforms;
	}
	
	public List<Long> getRemovedUserProjects() {
		return removedUserProjects;
	}

	public void setRemovedUserProjects(List<Long> removedUserProjects) {
		this.removedUserProjects = removedUserProjects;
	}

	public String getBackFillOff() {
		return backFillOff;
	}

	public void setBackFillOff(String backFillOff) {
		this.backFillOff = backFillOff;
	}

	public Date getDoj() {
		return doj;
	}

	public void setDoj(Date doj) {
		this.doj = doj;
	}

	public Date getDateOfBilling() {
		return dateOfBilling;
	}

	public void setDateOfBilling(Date dateOfBilling) {
		this.dateOfBilling = dateOfBilling;
	}

	public Date getReleseDate() {
		return releseDate;
	}

	public void setReleseDate(Date releseDate) {
		this.releseDate = releseDate;
	}

	public Date getResignDate() {
		return resignDate;
	}

	public void setResignDate(Date resignDate) {
		this.resignDate = resignDate;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Long getStream() {
		return stream;
	}

	public void setStream(Long stream) {
		this.stream = stream;
	}

	public String getCertifications() {
		return certifications;
	}

	public void setCertifications(String certifications) {
		this.certifications = certifications;
	}

	public String getPlatformName() {
		return platformName;
	}

	public void setPlatformName(String platformName) {
		this.platformName = platformName;
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

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
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

	public String getStreamName() {
		return streamName;
	}

	public void setStreamName(String streamName) {
		this.streamName = streamName;
	}

	public String getVzid() {
		return vzid;
	}

	public void setVzid(String vzid) {
		this.vzid = vzid;
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

	public Long getLocationId() {
		return locationId;
	}

	public void setLocationId(Long locationId) {
		this.locationId = locationId;
	}

	public String getLocationName() {
		return locationName;
	}

	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}

	public Float getCompetencyScore() {
		return competencyScore;
	}

	public void setCompetencyScore(Float competencyScore) {
		this.competencyScore = competencyScore;
	}

	public String getPrimarySkillName() {
		return primarySkillName;
	}

	public void setPrimarySkillName(String primarySkillName) {
		this.primarySkillName = primarySkillName;
	}

	public String getTimesheetFlag() {
		return timesheetFlag;
	}

	public void setTimesheetFlag(String timesheetFlag) {
		this.timesheetFlag = timesheetFlag;
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

	public Float getContribution() {
		return contribution;
	}

	public void setContribution(Float contribution) {
		this.contribution = contribution;
	}

	public String getSupervisorName() {
		return supervisorName;
	}

	public void setSupervisorName(String supervisorName) {
		this.supervisorName = supervisorName;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
		public Long getId() {
		return id;
	}

	public void setId(Long userId) {
		this.id = userId;
	}

	@Override
	public String toString() {
		return "User [ name=" + name
				+ ", userName=" + userName + ", id=" + id + " , projectName= "+projectName+" , applicationName= "+applicationName+" ,"
				+ "]";
	}

	public String getApplicationName() {
		return applicationName;
	}

	public void setApplicationName(String applicationName) {
		this.applicationName = applicationName;
	}

	public List<Long> getUserAccounts() {
		return userAccounts;
	}

	public void setUserAccounts(List<Long> userAccounts) {
		this.userAccounts = userAccounts;
	}

	public List<Long> getRemovedUserAccounts() {
		return removedUserAccounts;
	}

	public void setRemovedUserAccounts(List<Long> removedUserAccounts) {
		this.removedUserAccounts = removedUserAccounts;
	}

	public List<UserAcctPillarAppContribution> getUserAcctPillarAppContribution() {
		return userAcctPillarAppContribution;
	}

	public void setUserAcctPillarAppContribution(List<UserAcctPillarAppContribution> userAcctPillarAppContribution) {
		this.userAcctPillarAppContribution = userAcctPillarAppContribution;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public List<StableTeams> getStableTeams() {
		return stableTeams;
	}

	public void setStableTeams(List<StableTeams> stableTeams) {
		this.stableTeams = stableTeams;
	}

	public Float getStableContribution() {
		return stableContribution;
	}

	public void setStableContribution(Float stableContribution) {
		this.stableContribution = stableContribution;
	}

	public String getStableTeamName() {
		return stableTeamName;
	}

	public void setStableTeamName(String stableTeamName) {
		this.stableTeamName = stableTeamName;
	}

	public Long getLineManagerId() {
		return lineManagerId;
	}

	public void setLineManagerId(Long lineManagerId) {
		this.lineManagerId = lineManagerId;
	}

	public String getLineManager() {
		return lineManager;
	}

	public void setLineManager(String lineManager) {
		this.lineManager = lineManager;
	}

	
}
