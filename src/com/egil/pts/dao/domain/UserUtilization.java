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
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "PTS_USER_NETWORK_CODE_UTILIZATION")
@org.hibernate.annotations.Table(appliesTo = "PTS_USER_NETWORK_CODE_UTILIZATION")
public class UserUtilization  implements Serializable{

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Long id;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "NETWORK_CODE_ID")
	private NetworkCodes networkCode;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ACTIVITY_CODE_ID")
	private ActivityCodes activityCode;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CATEGORY_ID")
	private Category category;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "WBS_ID")
	private WBS wbs;
	
	@Column(name = "WEEK", nullable = false)
	protected String week;
	
	@Column(name = "EFFORT")
	private Float effort;
	
	@Column(name = "ASSIGNED_DATE", nullable = false)
	protected Date assignedDate;
	
	@Column(name = "ASSIGNED_BY", nullable = false)
	protected String assignedBy;

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

	public String getWeek() {
		return week;
	}

	public void setWeek(String week) {
		this.week = week;
	}

	public Float getEffort() {
		return effort;
	}

	public void setEffort(Float effort) {
		this.effort = effort;
	}

	public Date getAssignedDate() {
		return assignedDate;
	}

	public void setAssignedDate(Date assignedDate) {
		this.assignedDate = assignedDate;
	}

	public String getAssignedBy() {
		return assignedBy;
	}

	public void setAssignedBy(String assignedBy) {
		this.assignedBy = assignedBy;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public WBS getWbs() {
		return wbs;
	}

	public void setWbs(WBS wbs) {
		this.wbs = wbs;
	}
	
}
