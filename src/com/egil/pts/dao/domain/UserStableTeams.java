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
@Table(name = "PTS_USER_STABLE_TEAMS")
@org.hibernate.annotations.Table(appliesTo = "PTS_USER_STABLE_TEAMS")
public class UserStableTeams implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id", nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "user_id")
	private Long userId;

	@Column(name = "contribution")
	private Double contribution;

	@Column(name = "stable_team_id")
	private Long stableTeamId;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "stable_team_id",insertable = false,updatable = false)
	private StableTeams stableTeam;

	
	
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

	public Double getContribution() {
		return contribution;
	}

	public void setContribution(Double contribution) {
		this.contribution = contribution;
	}

	public Long getStableTeamId() {
		return stableTeamId;
	}

	public void setStableTeamId(Long stableTeamId) {
		this.stableTeamId = stableTeamId;
	}

	public StableTeams getStableTeam() {
		return stableTeam;
	}

	public void setStableTeam(StableTeams stableTeam) {
		this.stableTeam = stableTeam;
	}

}