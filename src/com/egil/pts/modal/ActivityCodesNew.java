package com.egil.pts.modal;

public class ActivityCodesNew extends TransactionInfo {

	private static final long serialVersionUID = 1L;
	private String activityCode;
	private Long activityCodeId;
	private String activity;
	private String description;
	private String STATUS;
	private Status status;
	private Double SECONDND_LEVEL_SUPPORT;
	private Double FOURTH_LEVEL_SUPPORT;
	private Double FESABILITY;
	private Double AD_HOC;
	private String name;
	private String type;
	private Boolean weekoff;
	private Long userId;
	private Long networkCodeId;

	public Double getSECONDND_LEVEL_SUPPORT() {
		return SECONDND_LEVEL_SUPPORT;
	}

	public void setSECONDND_LEVEL_SUPPORT(Double sECONDND_LEVEL_SUPPORT) {
		SECONDND_LEVEL_SUPPORT = sECONDND_LEVEL_SUPPORT;
	}

	public Double getFOURTH_LEVEL_SUPPORT() {
		return FOURTH_LEVEL_SUPPORT;
	}

	public void setFOURTH_LEVEL_SUPPORT(Double fOURTH_LEVEL_SUPPORT) {
		FOURTH_LEVEL_SUPPORT = fOURTH_LEVEL_SUPPORT;
	}

	public Double getFESABILITY() {
		return FESABILITY;
	}

	public void setFESABILITY(Double fESABILITY) {
		FESABILITY = fESABILITY;
	}

	public Double getAD_HOC() {
		return AD_HOC;
	}

	public void setAD_HOC(Double aD_HOC) {
		AD_HOC = aD_HOC;
	}

	public String getActivityCode() {
		return activityCode;
	}

	public void setActivityCode(String activityCode) {
		this.activityCode = activityCode;
	}

	public String getActivity() {
		return activity;
	}

	public void setActivity(String activity) {
		this.activity = activity;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Boolean getWeekoff() {
		return weekoff;
	}

	public void setWeekoff(Boolean weekoff) {
		this.weekoff = weekoff;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	@Override
	public String toString() {
		return "ActivityCodesNew [activityCode=" + activityCode + ", activityCodeId=" + getActivityCodeId()
				+ ", activity=" + activity + ", description=" + description + ", status=" + status
				+ ", SECONDND_LEVEL_SUPPORT=" + SECONDND_LEVEL_SUPPORT + ", FOURTH_LEVEL_SUPPORT="
				+ FOURTH_LEVEL_SUPPORT + ", FESABILITY=" + FESABILITY + ", AD_HOC=" + AD_HOC + ", name=" + name
				+ ", weekoff=" + weekoff + ", userId=" + userId + "]";
	}

	public String getSTATUS() {
		return STATUS;
	}

	public void setSTATUS(String sTATUS) {
		STATUS = sTATUS;
	}

	public Long getActivityCodeId() {
		return activityCodeId;
	}

	public void setActivityCodeId(Long activityCodeId) {
		this.activityCodeId = activityCodeId;
	}

	public Long getNetworkCodeId() {
		return networkCodeId;
	}

	public void setNetworkCodeId(Long networkCodeId) {
		this.networkCodeId = networkCodeId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
