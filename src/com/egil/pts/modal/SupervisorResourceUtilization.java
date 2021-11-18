package com.egil.pts.modal;

public class SupervisorResourceUtilization {

	private Long id;

	private Long resourceCount;

	private Long month;

	private Long locationId;

	private String locationName;

	private Long year;

	private Float egiworkingHours;

	private Float manaworkingHours;

	private Double workingdays;

	private Long userId;
	
	private Float targetHrs;

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

	public Long getYear() {
		return year;
	}

	public void setYear(Long year) {
		this.year = year;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
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

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Double getWorkingdays() {
		return workingdays;
	}

	public void setWorkingdays(Double workingdays) {
		this.workingdays = workingdays;
	}

	@Override
	public String toString() {
		return "SupervisorResourceUtilization [id=" + id + ", resourceCount=" + resourceCount + ", month=" + month
				+ ", locationId=" + locationId + ", locationName=" + locationName + ", year=" + year
				+ ", egiworkingHours=" + egiworkingHours + ", manaworkingHours=" + manaworkingHours + ", workingdays="
				+ workingdays + ", userId=" + userId + "targetHrs="+targetHrs+"]";
	}

	public Float getTargetHrs() {
		return targetHrs;
	}

	public void setTargetHrs(Float targetHrs) {
		this.targetHrs = targetHrs;
	}

}
