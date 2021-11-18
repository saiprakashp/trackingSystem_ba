package com.egil.pts.modal;

import java.io.Serializable;
import java.util.Date;

public class TimesheetActivityWfm implements Serializable {

	private static final long serialVersionUID = 1L;
	private Long id;
	private String monHrsOn;
	private String tueHrsOn;
	private String wedHrsOn;
	private String thuHrsOn;
	private String friHrsOn;
	private String satHrsOn;
	private String sunHrsOn;
	private String monHrsOff;
	private String tueHrsOff;
	private String wedHrsOff;
	private String thuHrsOff;
	private String friHrsOff;
	private String satHrsOff;
	private String sunHrsOff;
	private String userName;
	private String name;
	private Long userId;
	private String totalhrs;

	private String createdBy;
	private Date createdDate;
	private Date weekendingDate;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMonHrsOn() {
		return monHrsOn;
	}

	public void setMonHrsOn(String monHrsOn) {
		this.monHrsOn = monHrsOn;
	}

	public String getTueHrsOn() {
		return tueHrsOn;
	}

	public void setTueHrsOn(String tueHrsOn) {
		this.tueHrsOn = tueHrsOn;
	}

	public String getWedHrsOn() {
		return wedHrsOn;
	}

	public void setWedHrsOn(String wedHrsOn) {
		this.wedHrsOn = wedHrsOn;
	}

	public String getThuHrsOn() {
		return thuHrsOn;
	}

	public void setThuHrsOn(String thuHrsOn) {
		this.thuHrsOn = thuHrsOn;
	}

	public String getFriHrsOn() {
		return friHrsOn;
	}

	public void setFriHrsOn(String friHrsOn) {
		this.friHrsOn = friHrsOn;
	}

	public String getSatHrsOn() {
		return satHrsOn;
	}

	public void setSatHrsOn(String satHrsOn) {
		this.satHrsOn = satHrsOn;
	}

	public String getSunHrsOn() {
		return sunHrsOn;
	}

	public void setSunHrsOn(String sunHrsOn) {
		this.sunHrsOn = sunHrsOn;
	}

	public String getMonHrsOff() {
		return monHrsOff;
	}

	public void setMonHrsOff(String monHrsOff) {
		this.monHrsOff = monHrsOff;
	}

	public String getTueHrsOff() {
		return tueHrsOff;
	}

	public void setTueHrsOff(String tueHrsOff) {
		this.tueHrsOff = tueHrsOff;
	}

	public String getWedHrsOff() {
		return wedHrsOff;
	}

	public void setWedHrsOff(String wedHrsOff) {
		this.wedHrsOff = wedHrsOff;
	}

	public String getThuHrsOff() {
		return thuHrsOff;
	}

	public void setThuHrsOff(String thuHrsOff) {
		this.thuHrsOff = thuHrsOff;
	}

	public String getFriHrsOff() {
		return friHrsOff;
	}

	public void setFriHrsOff(String friHrsOff) {
		this.friHrsOff = friHrsOff;
	}

	public String getSatHrsOff() {
		return satHrsOff;
	}

	public void setSatHrsOff(String satHrsOff) {
		this.satHrsOff = satHrsOff;
	}

	public String getSunHrsOff() {
		return sunHrsOff;
	}

	public void setSunHrsOff(String sunHrsOff) {
		this.sunHrsOff = sunHrsOff;
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

	@Override
	public String toString() {
		return "TimesheetActivity [id=" + id + ", monHrsOn=" + monHrsOn + ", tueHrsOn=" + tueHrsOn + ", wedHrsOn="
				+ wedHrsOn + ", thuHrsOn=" + thuHrsOn + ", friHrsOn=" + friHrsOn + ", satHrsOn=" + satHrsOn
				+ ", sunHrsOn=" + sunHrsOn + ", monHrsOff=" + monHrsOff + ", tueHrsOff=" + tueHrsOff + ", wedHrsOff="
				+ wedHrsOff + ", thuHrsOff=" + thuHrsOff + ", friHrsOff=" + friHrsOff + ", satHrsOff=" + satHrsOff
				+ ", sunHrsOff=" + sunHrsOff + ", userName=" + userName + ", userId=" + userId + ", createdBy="
				+ createdBy + ", createdDate=" + createdDate + ", weekendingDate=" + weekendingDate + "]";
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTotalhrs() {

		return totalhrs;
	}

	public void setTotalhrs(String totalhrs) {
		this.totalhrs = totalhrs;
	}

}
