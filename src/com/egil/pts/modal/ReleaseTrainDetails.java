package com.egil.pts.modal;

import java.io.Serializable;

public class ReleaseTrainDetails implements Serializable {

	private static final long serialVersionUID = 1L;

	private String feasibility;
	private String development;
	private String systemTest;
	private String deployment;
	private String hold;
	private String completed;
	private String none;

	private int currentProjStat;

	public String getFeasibility() {
		return feasibility;
	}

	public void setFeasibility(String feasibility) {
		this.feasibility = feasibility;
	}

	public String getDevelopment() {
		return development;
	}

	public void setDevelopment(String development) {
		this.development = development;
	}

	public String getSystemTest() {
		return systemTest;
	}

	public void setSystemTest(String systemTest) {
		this.systemTest = systemTest;
	}

	public String getDeployment() {
		return deployment;
	}

	public void setDeployment(String deployment) {
		this.deployment = deployment;
	}

	public String getHold() {
		return hold;
	}

	public void setHold(String hold) {
		this.hold = hold;
	}

	public String getCompleted() {
		return completed;
	}

	public void setCompleted(String completed) {
		this.completed = completed;
	}

	public int getCurrentProjStat() {
		return currentProjStat;
	}

	public void setCurrentProjStat(int currentProjStat) {
		this.currentProjStat = currentProjStat;
	}

	public String getNone() {
		return none;
	}

	public void setNone(String none) {
		this.none = none;
	}

 

}
