package com.egil.pts.modal;

public class UserContribution {

	private Long accountId;

	private String accountName;

	private Long pillarId;

	private String pillarName;

	private Long appId;

	private String appName;

	private Float contribution;

	public Long getAccountId() {
		return accountId;
	}

	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public Long getPillarId() {
		return pillarId;
	}

	public void setPillarId(Long pillarId) {
		this.pillarId = pillarId;
	}

	public String getPillarName() {
		return pillarName;
	}

	public void setPillarName(String pillarName) {
		this.pillarName = pillarName;
	}

	public Long getAppId() {
		return appId;
	}

	public void setAppId(Long appId) {
		this.appId = appId;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public Float getContribution() {
		return contribution;
	}

	public void setContribution(Float contribution) {
		this.contribution = contribution;
	}

	@Override
	public String toString() {
		return "UserContribution [accountId=" + accountId + ", accountName=" + accountName + ", pillarId=" + pillarId
				+ ", pillarName=" + pillarName + ", appId=" + appId + ", appName=" + appName + ", contribution="
				+ contribution + "]";
	}

}
