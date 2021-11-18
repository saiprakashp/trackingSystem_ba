package com.egil.pts.modal;

import java.io.Serializable;

public class SearchSortContainer implements Serializable{
	
	private static final long serialVersionUID = 4538419126947001697L;
	
	private String searchUserName;
	private String searchSupervisor;
	private String searchStream;
	private String searchUserType;
	private String searchStatus;
	private String searchLocation;
	private String searchCountry;
	private String searchDOJ;
	private Long supervisorId;
	private Long lineManagerId;
	private boolean searchByStable;
	private boolean assignDefaultNwStableToUser;
	private String searchDOBilling;
	private String searchTechnology;
	private Integer appId;
	private String searchNetworkId;
	private String searchReleaseId;
	private String searchReleaseName;
	private String searchPM;
	private String searchProject;
	private String searchProjectName;
	private boolean supervisorMapFlag;
	private boolean detailLevel;
	protected String searchField;
	private String type;

	protected String searchString;
	
	protected String sord;
	
	protected String sidx;
	
	private String loggedInFullName;
	
	private String loggedInId;
	
	private boolean allReporteesFlag;
	
	private boolean capacityScrnFlag;
	
	private Long searchYear;
	
	private String orderBy;
	
	private boolean desc;
	
	private int pillarId;
	
	private long userId;
	
	private long stableTeamId;
	
	private int projectId;
	
	private int technologyId;
	
	private int streamId;
	
	private String region;
	
	private boolean activeFlag;
	
	private int searchMonth;
		
	private String TFSEpic;
	
	public String getLoggedInFullName() {
		return loggedInFullName;
	}

	public void setLoggedInFullName(String loggedInFullName) {
		this.loggedInFullName = loggedInFullName;
	}

	private String loggedInUserRole;

	

	public String getLoggedInUserRole() {
		return loggedInUserRole;
	}

	public void setLoggedInUserRole(String loggedInUserRole) {
		this.loggedInUserRole = loggedInUserRole;
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

	public boolean isSupervisorMapFlag() {
		return supervisorMapFlag;
	}

	public void setSupervisorMapFlag(boolean supervisorMapFlag) {
		this.supervisorMapFlag = supervisorMapFlag;
	}

	public String getLoggedInId() {
		return loggedInId;
	}

	public void setLoggedInId(String loggedInId) {
		this.loggedInId = loggedInId;
	}

	public boolean isAllReporteesFlag() {
		return allReporteesFlag;
	}

	public void setAllReporteesFlag(boolean allReporteesFlag) {
		this.allReporteesFlag = allReporteesFlag;
	}

	public String getSearchStream() {
		return searchStream;
	}

	public void setSearchStream(String searchStream) {
		this.searchStream = searchStream;
	}

	public String getSearchUserType() {
		return searchUserType;
	}

	public void setSearchUserType(String searchUserType) {
		this.searchUserType = searchUserType;
	}

	public String getSearchNetworkId() {
		return searchNetworkId;
	}

	public void setSearchNetworkId(String searchNetworkId) {
		this.searchNetworkId = searchNetworkId;
	}

	public String getSearchReleaseId() {
		return searchReleaseId;
	}

	public void setSearchReleaseId(String searchReleaseId) {
		this.searchReleaseId = searchReleaseId;
	}

	public String getSearchReleaseName() {
		return searchReleaseName;
	}

	public void setSearchReleaseName(String searchReleaseName) {
		this.searchReleaseName = searchReleaseName;
	}

	public String getSearchPM() {
		return searchPM;
	}

	public void setSearchPM(String searchPM) {
		this.searchPM = searchPM;
	}

	public Long getSearchYear() {
		return searchYear;
	}

	public void setSearchYear(Long searchYear) {
		this.searchYear = searchYear;
	}

	public boolean isCapacityScrnFlag() {
		return capacityScrnFlag;
	}

	public void setCapacityScrnFlag(boolean capacityScrnFlag) {
		this.capacityScrnFlag = capacityScrnFlag;
	}

	public String getSearchTechnology() {
		return searchTechnology;
	}

	public void setSearchTechnology(String searchTechnology) {
		this.searchTechnology = searchTechnology;
	}

	public String getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	public boolean isDesc() {
		return desc;
	}

	public void setDesc(boolean desc) {
		this.desc = desc;
	}

	public String getSearchProject() {
		return searchProject;
	}

	public void setSearchProject(String searchProject) {
		this.searchProject = searchProject;
	}

	public String getSearchProjectName() {
		return searchProjectName;
	}

	public void setSearchProjectName(String searchProjectName) {
		this.searchProjectName = searchProjectName;
	}

	public int getPillarId() {
		return pillarId;
	}

	public void setPillarId(int pillarId) {
		this.pillarId = pillarId;
	}

	public int getProjectId() {
		return projectId;
	}

	public void setProjectId(int projectId) {
		this.projectId = projectId;
	}

	public int getTechnologyId() {
		return technologyId;
	}

	public void setTechnologyId(int technologyId) {
		this.technologyId = technologyId;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public int getStreamId() {
		return streamId;
	}

	public void setStreamId(int streamId) {
		this.streamId = streamId;
	}

	public boolean isActiveFlag() {
		return activeFlag;
	}

	public void setActiveFlag(boolean activeFlag) {
		this.activeFlag = activeFlag;
	}
	
	public long getUserId() {
		return userId;
	}
	
	public void setUserId(long userId) {
		this.userId = userId;
	}
	
	public int getSearchMonth() {
		return searchMonth;
	}
	
	public void setSearchMonth(int searchMonth) {
		this.searchMonth = searchMonth;
	}

	public Integer getAppId() {
		return appId;
	}

	public void setAppId(Integer appId) {
		this.appId = appId;
	}

	public String getSearchCountry() {
		return searchCountry;
	}

	public void setSearchCountry(String searchCountry) {
		this.searchCountry = searchCountry;
	}

	public boolean isDetailLevel() {
		return detailLevel;
	}

	public void setDetailLevel(boolean detailLevel) {
		this.detailLevel = detailLevel;
	}

	public String getTFSEpic() {
		return TFSEpic;
	}

	public void setTFSEpic(String tFSEpic) {
		TFSEpic = tFSEpic;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public long getStableTeamId() {
		return stableTeamId;
	}

	public void setStableTeamId(long stableTeamId) {
		this.stableTeamId = stableTeamId;
	}

	public boolean isSearchByStable() {
		return searchByStable;
	}

	public void setSearchByStable(boolean searchByStable) {
		this.searchByStable = searchByStable;
	}

	public Long getSupervisorId() {
		return supervisorId;
	}

	public void setSupervisorId(Long supervisorId) {
		this.supervisorId = supervisorId;
	}

	public Long getLineManagerId() {
		return lineManagerId;
	}

	public void setLineManagerId(Long lineManagerId) {
		this.lineManagerId = lineManagerId;
	}

	public boolean isAssignDefaultNwStableToUser() {
		return assignDefaultNwStableToUser;
	}

	public void setAssignDefaultNwStableToUser(boolean assignDefaultNwStableToUser) {
		this.assignDefaultNwStableToUser = assignDefaultNwStableToUser;
	}
	
}
