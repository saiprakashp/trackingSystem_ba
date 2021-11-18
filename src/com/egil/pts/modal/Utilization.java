package com.egil.pts.modal;

public class Utilization {

	private String signum;
	private String resourceName;
	private String id;
	private Long currentGridId;
	private String resourceType;
	private String supervisorName;
	private Long supervisorId;
	private float effort;
	private String weekEndingDate;
	private String parseWeekEndingDate;
	private String email;
	private String networkCodeName;
	private String activityCodeName;
	private String type;
	private String timesheetStatus;
	private String projectManager;
	private String nwStatus;
	private String application;
	private float approvedHrs;
	private float rejectedHrs;
	private float diffHrs;

	public String getSignum() {
		return signum != null ? signum.toUpperCase() : signum;
	}

	public void setSignum(String signum) {
		this.signum = signum;
	}

	public String getResourceName() {
		return resourceName;
	}

	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}

	public String getId() {
		return id + parseWeekEndingDate;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getResourceType() {
		return resourceType;
	}

	public void setResourceType(String resourceType) {
		this.resourceType = resourceType;
	}

	public String getSupervisorName() {
		return supervisorName;
	}

	public void setSupervisorName(String supervisorName) {
		this.supervisorName = supervisorName;
	}

	public float getEffort() {
		return effort;
	}

	public void setEffort(float effort) {
		this.effort = effort;
	}

	public String getWeekEndingDate() {
		return weekEndingDate;
	}

	public void setWeekEndingDate(String weekEndingDate) {
		this.weekEndingDate = weekEndingDate;
	}

	public String getParseWeekEndingDate() {
		return parseWeekEndingDate;
	}

	public void setParseWeekEndingDate(String parseWeekEndingDate) {
		this.parseWeekEndingDate = parseWeekEndingDate;
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

	public String getNetworkCodeName() {
		return networkCodeName;
	}

	public void setNetworkCodeName(String networkCodeName) {
		this.networkCodeName = networkCodeName;
	}

	public String getActivityCodeName() {
		return activityCodeName;
	}

	public void setActivityCodeName(String activityCodeName) {
		this.activityCodeName = activityCodeName;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTimesheetStatus() {
		return timesheetStatus;
	}

	public void setTimesheetStatus(String timesheetStatus) {
		this.timesheetStatus = timesheetStatus;
		if (timesheetStatus != null && timesheetStatus.equalsIgnoreCase("Approved")) {
			setApprovedHrs(getEffort());
		} else {
			setRejectedHrs(getEffort());
		}
	}

	public float getApprovedHrs() {
		return approvedHrs;
	}

	public void setApprovedHrs(float approvedHrs) {
		this.approvedHrs = approvedHrs;
	}

	public float getRejectedHrs() {
		return rejectedHrs;
	}

	public void setRejectedHrs(float rejectedHrs) {
		this.rejectedHrs = rejectedHrs;
	}

	public Long getCurrentGridId() {
		return currentGridId;
	}

	public void setCurrentGridId(Long currentGridId) {
		this.currentGridId = currentGridId;
	}

	public String getProjectManager() {
		return projectManager;
	}

	public void setProjectManager(String projectManager) {
		this.projectManager = projectManager;
	}

	public String getNwStatus() {
		return nwStatus;
	}

	public void setNwStatus(String nwStatus) {
		this.nwStatus = nwStatus;
	}

	public String getApplication() {
		return application;
	}

	public void setApplication(String application) {
		this.application = application;
	}

	public float getDiffHrs() {
		return diffHrs;
	}

	public void setDiffHrs(float diffHrs) {
		this.diffHrs = diffHrs;
	}

}
