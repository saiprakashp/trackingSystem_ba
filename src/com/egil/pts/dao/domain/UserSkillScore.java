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
@Table(name = "PTS_USER_SKILL_SCORE")
@org.hibernate.annotations.Table(appliesTo = "PTS_USER_SKILL_SCORE")
public class UserSkillScore implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id", nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_skill_id", nullable = false, updatable = false)
	private UserSkills userSkill;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false, updatable = false)
	private User user;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "tech_id", nullable = false, updatable = false)
	private Technologies technology;

	@Column(name = "YEAR")
	private Long year;

	@Column(name = "YEAR_HALF")
	private String yearHalf;

	@Column(name = "TECH_SCORE")
	private Float techScore;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public UserSkills getUserSkill() {
		return userSkill;
	}

	public void setUserSkill(UserSkills userSkill) {
		this.userSkill = userSkill;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Technologies getTechnology() {
		return technology;
	}

	public void setTechnology(Technologies technology) {
		this.technology = technology;
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

	public Float getTechScore() {
		return techScore;
	}

	public void setTechScore(Float techScore) {
		this.techScore = techScore;
	}

}
