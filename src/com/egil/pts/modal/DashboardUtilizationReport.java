package com.egil.pts.modal;

public class DashboardUtilizationReport {

	private String month;
	private int headCount;
	private Double targetHrs;
	private Double actualHrs;
	private Double essActualHrs;
	private Double ptspercentage;
	private Double esspercentage;
	private Double percentage;
	private String utilization;
	private String essPtsDiff;

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public int getHeadCount() {
		return headCount;
	}

	public void setHeadCount(int headCount) {
		this.headCount = headCount;
	}

	public Double getTargetHrs() {
		return targetHrs;
	}

	public void setTargetHrs(Double targetHrs) {
		this.targetHrs = targetHrs;
	}

	public Double getActualHrs() {
		return actualHrs;
	}

	public void setActualHrs(Double actualHrs) {
		this.actualHrs = actualHrs;
	}

	public Double getPercentage() {
		return percentage;
	}

	public void setPercentage(Double percentage) {
		this.percentage = percentage;
	}

	public Double getEssActualHrs() {
		return essActualHrs;
	}

	public void setEssActualHrs(Double essActualHrs) {
		this.essActualHrs = essActualHrs;
	}

	@Override
	public String toString() {
		return "DashboardUtilizationReport [month=" + month + ", headCount=" + headCount + ", targetHrs=" + targetHrs
				+ ", actualHrs=" + actualHrs + ", essActualHrs=" + essActualHrs + ", percentage=" + percentage + "]";
	}

	public Double getEsspercentage() {
		return esspercentage;
	}

	public void setEsspercentage(Double esspercentage) {
		this.esspercentage = esspercentage;
	}

	public Double getPtspercentage() {
		return ptspercentage;
	}

	public void setPtspercentage(Double ptspercentage) {
		this.ptspercentage = ptspercentage;
	}

	public String getEssPtsDiff() {
		return essPtsDiff;
	}

	public void setEssPtsDiff(String essPtsDiff) {
		this.essPtsDiff = essPtsDiff;
	}

	public String getUtilization() {
		return utilization;
	}

	public void setUtilization(String utilization) {
		this.utilization = utilization;
	}

}
