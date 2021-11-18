package com.egil.pts.modal;

import java.util.List;

public class SummaryResponse<T> {

	private ErrorStatus errorStatus;
	private int totalRecords;
	private List<T> enitities;
	private long feasibilityLength;
	private long developmentLength;
	private long systemTestLength;
	private long deploymentLength;
	private long holdLength;
	private long noneLength;

	public void setErrorStatus(ErrorStatus errorStatus) {
		this.errorStatus = errorStatus;
	}

	public ErrorStatus getErrorStatus() {
		return errorStatus;
	}

	public int getTotalRecords() {
		return totalRecords;
	}

	public void setTotalRecords(int totalRecords) {
		this.totalRecords = totalRecords;
	}

	public List<T> getEnitities() {
		return enitities;
	}

	public void setEnitities(List<T> enitities) {
		this.enitities = enitities;
	}

	public void addEnitity(T enitity) {
		this.enitities.add(enitity);
	}

	public long getFeasibilityLength() {
		return feasibilityLength;
	}

	public void setFeasibilityLength(long feasibilityLength) {
		this.feasibilityLength = feasibilityLength;
	}

	public long getDevelopmentLength() {
		return developmentLength;
	}

	public void setDevelopmentLength(long developmentLength) {
		this.developmentLength = developmentLength;
	}

	public long getSystemTestLength() {
		return systemTestLength;
	}

	public void setSystemTestLength(long systemTestLength) {
		this.systemTestLength = systemTestLength;
	}

	public long getDeploymentLength() {
		return deploymentLength;
	}

	public void setDeploymentLength(long deploymentLength) {
		this.deploymentLength = deploymentLength;
	}

	public long getHoldLength() {
		return holdLength;
	}

	public void setHoldLength(long holdLength) {
		this.holdLength = holdLength;
	}

	public long getNoneLength() {
		return noneLength;
	}

	public void setNoneLength(long noneLength) {
		this.noneLength = noneLength;
	}

	@Override
	public String toString() {
		return "SummaryResponse [errorStatus=" + errorStatus + ", totalRecords=" + totalRecords + ", enitities="
				+ enitities + ", feasibilityLength=" + feasibilityLength + ", developmentLength=" + developmentLength
				+ ", systemTestLength=" + systemTestLength + ", deploymentLength=" + deploymentLength + ", holdLength="
				+ holdLength + ", noneLength=" + noneLength + "]";
	}

}