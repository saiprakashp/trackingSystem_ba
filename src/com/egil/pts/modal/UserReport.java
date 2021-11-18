package com.egil.pts.modal;

public class UserReport {

	private Integer userCount;
	private String stream;
	private String location;
	private Float testUserCnt;
	private Float devUserCnt;
	private Float pmUserCnt;
	private Float seUserCnt;
	private Float ptlUserCnt;
	private Float adminUserCnt;
	private Float totalLocUserCnt;
	private String platform;
	private String projectName;

	public Integer getUserCount() {
		return userCount;
	}

	public void setUserCount(Integer userCount) {
		this.userCount = userCount;
	}

	public String getStream() {
		return stream;
	}

	public void setStream(String stream) {
		this.stream = stream;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public Float getTestUserCnt() {
		return testUserCnt;
	}

	public void setTestUserCnt(Float testUserCnt) {
		this.testUserCnt = testUserCnt;
	}

	public Float getDevUserCnt() {
		return devUserCnt;
	}

	public void setDevUserCnt(Float devUserCnt) {
		this.devUserCnt = devUserCnt;
	}

	public Float getPmUserCnt() {
		return pmUserCnt;
	}

	public void setPmUserCnt(Float pmUserCnt) {
		this.pmUserCnt = pmUserCnt;
	}

	public Float getSeUserCnt() {
		return seUserCnt;
	}

	public void setSeUserCnt(Float seUserCnt) {
		this.seUserCnt = seUserCnt;
	}

	public Float getAdminUserCnt() {
		return adminUserCnt;
	}

	public void setAdminUserCnt(Float adminUserCnt) {
		this.adminUserCnt = adminUserCnt;
	}

	public Float getTotalLocUserCnt() {
		return totalLocUserCnt;
	}

	public void setTotalLocUserCnt(Float totalLocUserCnt) {
		this.totalLocUserCnt = totalLocUserCnt;
	}

	public String getPlatform() {
		return platform;
	}

	public void setPlatform(String platform) {
		this.platform = platform;
	}

	public Float getPtlUserCnt() {
		return ptlUserCnt;
	}

	public void setPtlUserCnt(Float ptlUserCnt) {
		this.ptlUserCnt = ptlUserCnt;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	@Override
	public String toString() {
		return "UserReport [userCount=" + userCount + ", stream=" + stream + ", location=" + location + ", testUserCnt="
				+ testUserCnt + ", devUserCnt=" + devUserCnt + ", pmUserCnt=" + pmUserCnt + ", seUserCnt=" + seUserCnt
				+ ", ptlUserCnt=" + ptlUserCnt + ", adminUserCnt=" + adminUserCnt + ", totalLocUserCnt="
				+ totalLocUserCnt + ", platform=" + platform + ", projectName=" + projectName + "]";
	}

}
