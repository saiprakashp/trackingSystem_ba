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
@Table(name = "PTS_USER_WEEK_OFF")
@org.hibernate.annotations.Table(appliesTo = "PTS_USER_WEEK_OFF")
public class UserWeekOff implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "id", nullable = false)
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "week_off")
	private Boolean weekOffFlag;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;
	
	@Column(name = "WEEKENDING_DATE", nullable = false)
	protected Date weekendingDate;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Boolean getWeekOffFlag() {
		return weekOffFlag;
	}

	public void setWeekOffFlag(Boolean weekOffFlag) {
		this.weekOffFlag = weekOffFlag;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Date getWeekendingDate() {
		return weekendingDate;
	}

	public void setWeekendingDate(Date weekendingDate) {
		this.weekendingDate = weekendingDate;
	}
}
