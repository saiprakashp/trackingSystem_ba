package com.egil.pts.dao.domain;

import java.io.Serializable;

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
@Table(name = "PTS_USER_PLATFORM_COMP_SCORE")
@org.hibernate.annotations.Table(appliesTo = "PTS_USER_PLATFORM_COMP_SCORE")
public class UserPlatformCompetencyScore implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id", nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "USER_PLATFORM_ID", nullable = false, updatable = false)
	private UserPlatforms userPlatform;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false, updatable = false)
	private User user;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PLATFORM_ID", nullable = false, updatable = false)
	private Pillar platform;

	@Column(name = "YEAR")
	private Long year;

	@Column(name = "YEAR_HALF")
	private String yearHalf;

	@Column(name = "COMPETENCY_SCORE")
	private Float compScore;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}


	public Long getYear() {
		return year;
	}

	public void setYear(Long year) {
		this.year = year;
	}

	public String getYearHalf() {
		return yearHalf;
	}

	public void setYearHalf(String yearHalf) {
		this.yearHalf = yearHalf;
	}

	public UserPlatforms getUserPlatform() {
		return userPlatform;
	}

	public void setUserPlatform(UserPlatforms userPlatform) {
		this.userPlatform = userPlatform;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Pillar getPlatform() {
		return platform;
	}

	public void setPlatform(Pillar platform) {
		this.platform = platform;
	}

	public Float getCompScore() {
		return compScore;
	}

	public void setCompScore(Float compScore) {
		this.compScore = compScore;
	}

}
