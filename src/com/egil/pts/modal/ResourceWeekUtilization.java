package com.egil.pts.modal;

import java.io.Serializable;

public class ResourceWeekUtilization implements Serializable {

	private static final long serialVersionUID = 1L;
	private Long networkId;
	private String networkCode;
	private String networkIdDesc;
	private Long activityId;
	private String activityCode;
	private String activityDesc;
	private Long categoryId;
	private String category;
	private Long wbsId;
	private String wbs;

	private WeekEndingEffort weekEndingEffort;

	public Long getNetworkId() {
		return networkId;
	}

	public void setNetworkId(Long networkId) {
		this.networkId = networkId;
	}

	public String getNetworkIdDesc() {
		return networkIdDesc;
	}

	public void setNetworkIdDesc(String networkIdDesc) {
		this.networkIdDesc = networkIdDesc;
	}

	public String getNetworkCode() {
		return networkCode;
	}

	public void setNetworkCode(String networkCode) {
		this.networkCode = networkCode;
	}

	public Long getActivityId() {
		return activityId;
	}

	public void setActivityId(Long activityId) {
		this.activityId = activityId;
	}

	public String getActivityCode() {
		return activityCode;
	}

	public void setActivityCode(String activityCode) {
		this.activityCode = activityCode;
	}

	public String getActivityDesc() {
		return activityDesc;
	}

	public void setActivityDesc(String activityDesc) {
		this.activityDesc = activityDesc;
	}

	public WeekEndingEffort getWeekEndingEffort() {
		return weekEndingEffort;
	}

	public void setWeekEndingEffort(WeekEndingEffort weekEndingEffort) {
		this.weekEndingEffort = weekEndingEffort;
	}

	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public Long getWbsId() {
		return wbsId;
	}

	public void setWbsId(Long wbsId) {
		this.wbsId = wbsId;
	}

	public String getWbs() {
		return wbs;
	}

	public void setWbs(String wbs) {
		this.wbs = wbs;
	}
}
