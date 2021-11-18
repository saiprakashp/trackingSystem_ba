package com.egil.pts.dao.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

@Entity
@Table(name = "PTS_USER_TIMESHEET")
@org.hibernate.annotations.Table(appliesTo = "PTS_USER_TIMESHEET")
public class UserTimesheet implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Long id;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id")
	private User user;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "NETWORK_CODE_ID")
	private NetworkCodes networkCode;

	@ManyToOne(fetch = FetchType.EAGER)
	@NotFound(action = NotFoundAction.IGNORE)
	@JoinColumn(name = "ACTIVITY_CODE_ID")
	private ActivityCodes activityCode;

	@Column(name = "WEEKENDING_DATE", nullable = false)
	protected Date weekendingDate;

	@Column(name = "CREATED_DATE", nullable = false)
	protected Date createdDate;

	@Column(name = "CREATED_BY", nullable = false)
	protected String createdBy;

	@Column(name = "UPDATED_DATE", nullable = false)
	protected Date updatedDate;

	@Column(name = "UPDATED_BY", nullable = false)
	protected String updatedBy;

	@Column(name = "MON_HRS")
	private Float monHrs;

	@Column(name = "TUE_HRS")
	private Float tueHrs;

	@Column(name = "WED_HRS")
	private Float wedHrs;

	@Column(name = "THU_HRS")
	private Float thuHrs;

	@Column(name = "FRI_HRS")
	private Float friHrs;

	@Column(name = "SAT_HRS")
	private Float satHrs;

	@Column(name = "SUN_HRS")
	private Float sunHrs;

	@Column(name = "type")
	private String type;

	@Column(name = "status")
	private String status;

	@Column(name = "activityType")
	private String activityType;

	@NotFound(action = NotFoundAction.IGNORE)
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ACTIVITY_CODE_ID",insertable =false, updatable = false)
	private ProjectTypeNew projectsNew;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public NetworkCodes getNetworkCode() {
		return networkCode;
	}

	public void setNetworkCode(NetworkCodes networkCode) {
		this.networkCode = networkCode;
	}

	public ActivityCodes getActivityCode() {
		return activityCode;
	}

	public void setActivityCode(ActivityCodes activityCode) {
		this.activityCode = activityCode;
	}

	public Date getWeekendingDate() {
		return weekendingDate;
	}

	public void setWeekendingDate(Date weekendingDate) {
		this.weekendingDate = weekendingDate;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Float getMonHrs() {
		return monHrs;
	}

	public void setMonHrs(Float monHrs) {
		this.monHrs = monHrs;
	}

	public Float getTueHrs() {
		return tueHrs;
	}

	public void setTueHrs(Float tueHrs) {
		this.tueHrs = tueHrs;
	}

	public Float getWedHrs() {
		return wedHrs;
	}

	public void setWedHrs(Float wedHrs) {
		this.wedHrs = wedHrs;
	}

	public Float getThuHrs() {
		return thuHrs;
	}

	public void setThuHrs(Float thuHrs) {
		this.thuHrs = thuHrs;
	}

	public Float getFriHrs() {
		return friHrs;
	}

	public void setFriHrs(Float friHrs) {
		this.friHrs = friHrs;
	}

	public Float getSatHrs() {
		return satHrs;
	}

	public void setSatHrs(Float satHrs) {
		this.satHrs = satHrs;
	}

	public Float getSunHrs() {
		return sunHrs;
	}

	public void setSunHrs(Float sunHrs) {
		this.sunHrs = sunHrs;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "UserTimesheet [id=" + id + ", user=" + user.getId() + ", networkCode=" + "" + ", activityCode="
				+ activityCode.getActivityCodeId() + ", weekendingDate=" + weekendingDate + ", createdDate="
				+ createdDate + ", createdBy=" + createdBy + ", updatedDate=" + updatedDate + ", updatedBy=" + updatedBy
				+ ", monHrs=" + monHrs + ", tueHrs=" + tueHrs + ", wedHrs=" + wedHrs + ", thuHrs=" + thuHrs
				+ ", friHrs=" + friHrs + ", satHrs=" + satHrs + ", sunHrs=" + sunHrs + ", type=" + type + ", status="
				+ status + "]";
	}

	public String getActivityType() {
		return activityType;
	}

	public void setActivityType(String activityType) {
		this.activityType = activityType;
	}

	public float getTotal() {
		return monHrs + tueHrs + wedHrs + thuHrs + friHrs + satHrs + sunHrs;
	}

	public ProjectTypeNew getProjectsNew() {
		return projectsNew;
	}

	public void setProjectsNew(ProjectTypeNew projectsNew) {
		this.projectsNew = projectsNew;
	}
}
