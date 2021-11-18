package com.egil.pts.modal;

public class LocationUserCount {

	private Long id;

	private Long resourceCount;

	private Long month;

	private Long locationId;

	private String locationName;

	private Long year;

	private Float egiworkingHours;

	private Float manaworkingHours;

	private Double workingdays;

	private Double targetHrs;

	private Long userId;
	
	private Integer userCnt;
	
	private boolean teamFlag;

	public String getLocationName() {
		return locationName;
	}

	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}

	public Long getLocationId() {
		return locationId;
	}

	public void setLocationId(Long locationId) {
		this.locationId = locationId;
	}

	public Integer getUserCnt() {
		return userCnt;
	}

	public void setUserCnt(Integer userCnt) {
		this.userCnt = userCnt;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getResourceCount() {
		return resourceCount;
	}

	public void setResourceCount(Long resourceCount) {
		this.resourceCount = resourceCount;
	}

	public Long getMonth() {
		return month;
	}

	public void setMonth(Long month) {
		this.month = month;
	}

	public Long getYear() {
		return year;
	}

	public void setYear(Long year) {
		this.year = year;
	}

	public Float getEgiworkingHours() {
		return egiworkingHours;
	}

	public void setEgiworkingHours(Float egiworkingHours) {
		this.egiworkingHours = egiworkingHours;
	}

	public Float getManaworkingHours() {
		return manaworkingHours;
	}

	public void setManaworkingHours(Float manaworkingHours) {
		this.manaworkingHours = manaworkingHours;
	}

	public Double getWorkingdays() {
		return workingdays;
	}

	public void setWorkingdays(Double workingdays) {
		this.workingdays = workingdays;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Double getTargetHrs() {
		return targetHrs;
	}

	public void setTargetHrs(Double targetHrs) {
		this.targetHrs = targetHrs;
	}

	@Override
	public String toString() {
		return "LocationUserCount [id=" + id + ", resourceCount=" + resourceCount + ", month=" + month + ", locationId="
				+ locationId + ", locationName=" + locationName + ", year=" + year + ", egiworkingHours="
				+ egiworkingHours + ", manaworkingHours=" + manaworkingHours + ", workingdays=" + workingdays
				+ ", targetHrs=" + targetHrs + ", userId=" + userId + ", userCnt=" + userCnt + "]";
	}

	public boolean isTeamFlag() {
		return teamFlag;
	}

	public void setTeamFlag(boolean teamFlag) {
		this.teamFlag = teamFlag;
	}

}
