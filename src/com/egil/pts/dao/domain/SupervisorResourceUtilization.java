package com.egil.pts.dao.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "pts_manager_util")
public class SupervisorResourceUtilization implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id", nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "resourceCount", nullable = false)
	private Long resourceCount;

	@Column(name = "month", nullable = false)
	private Long month;

	@Column(name = "locationId", nullable = false)
	private Long locationId;

	@Column(name = "locationName", nullable = false)
	private String locationName;

	@Column(name = "year", nullable = false)
	private Long year;

	@Column(name = "egiworkingHours", nullable = false)
	private Float egiworkingHours;

	@Column(name = "targetHrs", nullable = false)
	private Float targetHrs;

	@Column(name = "manaworkingHours", nullable = false)
	private Float manaworkingHours;

	@Column(name = "workingDays", nullable = false)
	private Double workingDays;

	@Column(name = "userId", nullable = false)
	private Long userId;

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

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Double getWorkingDays() {
		return workingDays;
	}

	public void setWorkingDays(Double workingDays) {
		this.workingDays = workingDays;
	}

	@Override
	public String toString() {
		return "SupervisorResourceUtilization [id=" + id + ", resourceCount=" + resourceCount + ", month=" + month
				+ ", locationId=" + locationId + ", locationName=" + locationName + ", year=" + year
				+ ", egiworkingHours=" + egiworkingHours + ", manaworkingHours=" + manaworkingHours + ", workingDays="
				+ workingDays + ", userId=" + userId +"targetHrs="+targetHrs+ "]";
	}

	public Float getTargetHrs() {
		return targetHrs;
	}

	public void setTargetHrs(Float targetHrs) {
		this.targetHrs = targetHrs;
	}

}
