package com.egil.pts.modal;

import java.util.ArrayList;
import java.util.List;

public class UserAcctPillarAppContribution {

	private Long accountId;
	private String accountName;
	private List<StableTeams> stableTeams = new ArrayList<StableTeams>();

	private List<UserPillarAppContribution> userPACList = new ArrayList<UserPillarAppContribution>();

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

	public List<UserPillarAppContribution> getUserPACList() {
		return userPACList;
	}

	public void setUserPACList(List<UserPillarAppContribution> userPACList) {
		this.userPACList = userPACList;
	}

	public List<StableTeams> getStableTeams() {
		return stableTeams;
	}

	public void setStableTeams(List<StableTeams> stableTeams) {
		this.stableTeams = stableTeams;
	}

	@Override
	public String toString() {
		return "UserAcctPillarAppContribution [accountId=" + accountId + ", accountName=" + accountName
				+ ", stableTeams=" + getStableTeams() + ", userPACList=" + userPACList + "]";
	}

}
