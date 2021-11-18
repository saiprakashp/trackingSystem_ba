package com.egil.pts.modal;

public class EssDetails {

	private Long id;
	private String resourceName;
	private String signum;
	private Long year;
	private String month;
	private Double targetHrs;
	private Double chargedHrs;
	private Long headCount;

	public String getSignum() {
		return signum;
	}

	public void setSignum(String signum) {
		this.signum = signum;
	}

	public Long getYear() {
		return year;
	}

	public void setYear(Long year) {
		this.year = year;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public Double getTargetHrs() {
		return targetHrs;
	}

	public void setTargetHrs(Double targetHrs) {
		this.targetHrs = targetHrs;
	}

	public Double getChargedHrs() {
		return chargedHrs;
	}

	public void setChargedHrs(Double chargedHrs) {
		this.chargedHrs = chargedHrs;
	}

	public String getResourceName() {
		return resourceName;
	}

	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}

	public Long getHeadCount() {
		return headCount;
	}

	public void setHeadCount(Long headCount) {
		this.headCount = headCount;
	}

	@Override
	public String toString() {
		return "EssDetails [resourceName=" + resourceName + ", signum=" + signum + ", year=" + year + ", month=" + month
				+ ", targetHrs=" + targetHrs + ", chargedHrs=" + chargedHrs + ", headCount=" + headCount + "]";
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	
}
