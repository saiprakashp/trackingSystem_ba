package com.egil.pts.modal;

public class StableTeams implements Comparable<StableTeams> {
	private String teamName;
	private Long id;
	private Long project;
	private Long userId;
	private Double value;
	private String signum;
	private String name;
	private String status;

	public String getTeamName() {
		return teamName;
	}

	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}

	public Double getValue() {
		return value;
	}

	public void setValue(Double value) {
		this.value = value;
	}

	public Long getProject() {
		return project;
	}

	public void setProject(Long project) {
		this.project = project;
	}

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

	@Override
	public int compareTo(StableTeams stableTeams) {
		return this.teamName.compareTo(stableTeams.teamName);
	}

	@Override
	public String toString() {
		return "StableTeams [teamName=" + teamName + ", id=" + id + ", project=" + project + ", userId=" + userId
				+ ", value=" + value + "]";
	}

	public String getSignum() {
		return signum;
	}

	public void setSignum(String signum) {
		this.signum = signum;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
