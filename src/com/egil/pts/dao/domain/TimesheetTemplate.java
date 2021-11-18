package com.egil.pts.dao.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "PTS_USER_TIMESHEET_TEMPLATE")
@org.hibernate.annotations.Table(appliesTo = "PTS_USER_TIMESHEET_TEMPLATE")
public class TimesheetTemplate implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Long id;

	@Column(name = "user_id")
	private Long userId;

	@Column(name = "NETWORK_CODE_ID")
	private Long networkCodeId;

	@Column(name = "ACTIVITY_CODE_ID")
	private Long activityCodeId;

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

	@Column(name = "ACTIVITY_TYPE")
	private String activityType;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getNetworkCodeId() {
		return networkCodeId;
	}

	public void setNetworkCodeId(Long networkCodeId) {
		this.networkCodeId = networkCodeId;
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

	public String getActivityType() {
		return activityType;
	}

	public void setActivityType(String activityType) {
		this.activityType = activityType;
	}

	public Long getActivityCodeId() {
		return activityCodeId;
	}

	public void setActivityCodeId(Long activityCodeId) {
		this.activityCodeId = activityCodeId;
	}

	
}
