package com.egil.pts.modal;

public class TechCompetencyScore {

	private String userName;
	private String supervisorName;
	private String technologyName;
	private String platformName;
	private String projectName;
	private int techScore;
	private int compScore;
	private int year;
	private String halfYear;
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getSupervisorName() {
		return supervisorName;
	}
	public void setSupervisorName(String supervisorName) {
		this.supervisorName = supervisorName;
	}
	public String getTechnologyName() {
		return technologyName;
	}
	public void setTechnologyName(String technologyName) {
		this.technologyName = technologyName;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public int getTechScore() {
		return techScore;
	}
	public void setTechScore(int techScore) {
		this.techScore = techScore;
	}
	public int getCompScore() {
		return compScore;
	}
	public void setCompScore(int compScore) {
		this.compScore = compScore;
	}
	public String getPlatformName() {
		return platformName;
	}
	public void setPlatformName(String platformName) {
		this.platformName = platformName;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public String getHalfYear() {
		return halfYear;
	}
	public void setHalfYear(String halfYear) {
		this.halfYear = halfYear;
	}
}
