package com.egil.pts.modal;

import java.io.Serializable;
import java.util.Date;

public class TimeDuplicateTemplates implements Serializable {

	private static final long serialVersionUID = 1L;
	private Long id;
	private Long networkId;
	private Long totalCount;
	private String networkIdDesc;
	private Long activityCode;
	private String activityDesc;
	private Float monHrs;
	private Float tueHrs;
	private Float wedHrs;
	private Float thuHrs;
	private Float friHrs;
	private Float satHrs;
	private Float sunHrs;
	private String totalHrs;
	private String userName;
	private Long userId;
	private Long templateId;
	private Long projectAssignmentId;
	private String type;
	private String approvalStatus;
	private boolean approvedStatusFlag;
	private boolean rejectedStatusFlag;
	private String activityType;
	private String createdBy;
	private Date createdDate;
	private Date weekendingDate;
	private boolean addCheckFlag;
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getTemplateId() {
		return templateId;
	}

	public void setTemplateId(Long templateId) {
		this.templateId = templateId;
	}

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

	public Long getActivityCode() {
		return activityCode;
	}

	public void setActivityCode(Long activityCode) {
		this.activityCode = activityCode;
	}

	public String getActivityDesc() {
		return activityDesc;
	}

	public void setActivityDesc(String activityDesc) {
		this.activityDesc = activityDesc;
	}

	public Float getMonHrs() {
		return (monHrs==null)?0.0F: monHrs;
	}

	public void setMonHrs(Float monHrs) {
		this.monHrs = monHrs;
	}

	public Float getTueHrs() {
		return (tueHrs==null)?0.0F: tueHrs;
	}

	public void setTueHrs(Float tueHrs) {
		this.tueHrs = tueHrs;
	}

	public Float getWedHrs() {
		return (wedHrs==null)?0.0F: wedHrs;
	}

	public void setWedHrs(Float wedHrs) {
		this.wedHrs = wedHrs;
	}

	public Float getThuHrs() {
		return (thuHrs==null)?0.0F: thuHrs;
	}

	public void setThuHrs(Float thuHrs) {
		this.thuHrs = thuHrs;
	}

	public Float getFriHrs() {
		return (friHrs==null)?0.0F: friHrs;
	}

	public void setFriHrs(Float friHrs) {
		this.friHrs = friHrs;
	}

	public Float getSatHrs() {
		return (satHrs==null)?0.0F: satHrs;
	}

	public void setSatHrs(Float satHrs) {
		this.satHrs = satHrs;
	}

	public Float getSunHrs() {
		return (sunHrs==null)?0.0F: sunHrs;
	}

	public void setSunHrs(Float sunHrs) {
		this.sunHrs = sunHrs;
	}

	public Float getActivitySummation() {
		return ((monHrs == null ? 0.0f : monHrs) + (tueHrs == null ? 0.0f : tueHrs) + (wedHrs == null ? 0.0f : wedHrs)
				+ (thuHrs == null ? 0.0f : thuHrs) + (friHrs == null ? 0.0f : friHrs) + (satHrs == null ? 0.0f : satHrs)
				+ (sunHrs == null ? 0.0f : sunHrs));
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getApprovalStatus() {
		return approvalStatus;
	}

	public void setApprovalStatus(String approvalStatus) {
		this.approvalStatus = approvalStatus;
	}

	public boolean getApprovedStatusFlag() {
		return approvedStatusFlag;
	}

	public void setApprovedStatusFlag(boolean approvedStatusFlag) {
		this.approvedStatusFlag = approvedStatusFlag;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public boolean getRejectedStatusFlag() {
		return rejectedStatusFlag;
	}

	public void setRejectedStatusFlag(boolean rejectedStatusFlag) {
		this.rejectedStatusFlag = rejectedStatusFlag;
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

	public Date getWeekendingDate() {
		return weekendingDate;
	}

	public void setWeekendingDate(Date weekendingDate) {
		this.weekendingDate = weekendingDate;
	}

	public String getTotalHrs() {
		return totalHrs;
	}

	public void setTotalHrs(String totalHrs) {
		this.totalHrs = totalHrs;
	}

	public String getActivityType() {
		return activityType;
	}

	public void setActivityType(String activityType) {
		this.activityType = activityType;
	}

	@Override
	public String toString() {
		return "TimesheetActivity [id=" + id + ", networkId=" + networkId + ", networkIdDesc=" + networkIdDesc
				+ ", activityCode=" + activityCode + ", activityDesc=" + activityDesc + ", monHrs=" + monHrs
				+ ", tueHrs=" + tueHrs + ", wedHrs=" + wedHrs + ", thuHrs=" + thuHrs + ", friHrs=" + friHrs
				+ ", satHrs=" + satHrs + ", sunHrs=" + sunHrs + ", totalHrs=" + totalHrs + ", userName=" + userName
				+ ", userId=" + userId + ", templateId=" + templateId + ", projectAssignmentId=" + projectAssignmentId
				+ ", type=" + type + ", approvalStatus=" + approvalStatus + ", approvedStatusFlag=" + approvedStatusFlag
				+ ", rejectedStatusFlag=" + rejectedStatusFlag + ", activityType=" + activityType + ", createdBy="
				+ createdBy + ", createdDate=" + createdDate + ", weekendingDate=" + weekendingDate + ", addCheckFlag="
				+ addCheckFlag + "]";
	}

	public Long getProjectAssignmentId() {
		return projectAssignmentId;
	}

	public void setProjectAssignmentId(Long projectAssignmentId) {
		this.projectAssignmentId = projectAssignmentId;
	}

	public float getTotal() {
		monHrs=(monHrs==null)?0.0F:monHrs;
		tueHrs=(tueHrs==null)?0.0F:tueHrs;
		wedHrs=(wedHrs==null)?0.0F:wedHrs;
		thuHrs=(thuHrs==null)?0.0F:thuHrs;
		friHrs=(friHrs==null)?0.0F:friHrs;
		satHrs=(satHrs==null)?0.0F:satHrs;
		sunHrs=(sunHrs==null)?0.0F:sunHrs;
		return monHrs + tueHrs + wedHrs + thuHrs + friHrs + satHrs + sunHrs;
	}

	public boolean isAddCheckFlag() {
		return addCheckFlag;
	}

	public void setAddCheckFlag(boolean addCheckFlag) {
		this.addCheckFlag = addCheckFlag;
	}

	public Long getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(Long totalCount) {
		this.totalCount = totalCount;
	}
}
