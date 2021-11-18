package com.egil.pts.modal;

public class ResourceUtilization {

	private Long userId;
	private String supervisorMail;
	private String month;
	private String resourceName;
	private String supervisorName;
	private Double actualHours;
	private Double targetHours;
	private Double differenceHrs;
	private Double percentage;
	private Long locationId;
	private String signum;
	private Double essHrs;
	private Double differencePtsHrs;
	private Double targetHrsNew;
	private Boolean weekOff;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getSupervisorMail() {
		return supervisorMail;
	}

	public void setSupervisorMail(String supervisorMail) {
		this.supervisorMail = supervisorMail;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public String getResourceName() {
		return resourceName;
	}

	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}

	public String getSupervisorName() {
		return supervisorName;
	}

	public void setSupervisorName(String supervisorName) {
		this.supervisorName = supervisorName;
	}

	public Double getActualHours() {
		return actualHours;
	}

	public void setActualHours(Double actualHours) {
		this.actualHours = actualHours;
	}

	public Double getTargetHours() {
		return targetHours;
	}

	public void setTargetHours(Double targetHours) {
		this.targetHours = targetHours;
	}

	public Double getDifferenceHrs() {
		return differenceHrs;
	}

	public void setDifferenceHrs(Double differenceHrs) {
		this.differenceHrs = differenceHrs;
	}

	public Double getPercentage() {
		return percentage;
	}

	public void setPercentage(Double percentage) {
		this.percentage = percentage;
	}

	public Long getLocationId() {
		return locationId;
	}

	public void setLocationId(Long locationId) {
		this.locationId = locationId;
	}

	public String getSignum() {
		return signum;
	}

	public void setSignum(String signum) {
		this.signum = signum;
	}

	public Double getEssHrs() {
		return essHrs;
	}

	public void setEssHrs(Double essHrs) {
		this.essHrs = essHrs;
	}

	public Double getDifferencePtsHrs() {
		return differencePtsHrs;
	}

	public void setDifferencePtsHrs(Double differencePtsHrs) {
		this.differencePtsHrs = differencePtsHrs;
	}

	public Double getTargetHrsNew() {
		return targetHrsNew;
	}

	public void setTargetHrsNew(Double targetHrsNew) {
		this.targetHrsNew = targetHrsNew;
	}

	@Override
	public String toString() {
		return "ResourceUtilization [userId=" + userId + ", supervisorMail=" + supervisorMail + ", month=" + month
				+ ", resourceName=" + resourceName + ", supervisorName=" + supervisorName + ", actualHours="
				+ actualHours + ", targetHours=" + targetHours + ", differenceHrs=" + differenceHrs + ", percentage="
				+ percentage + ", locationId=" + locationId + ", signum=" + signum + ", essHrs=" + essHrs
				+ ", differencePtsHrs=" + differencePtsHrs + ", targetHrsNew=" + targetHrsNew + "]";
	}

	public Boolean getWeekOff() {
		return weekOff;
	}

	public void setWeekOff(Boolean weekOff) {
		this.weekOff = weekOff;
	}

}
