package com.egil.pts.modal;

import java.util.ArrayList;
import java.util.List;

public class UserPillarAppContribution {

	private Long pillarId;
	private String pillarName;
	private Float pillarContribution;
	
	private List<UserAppContribution> userACList = new ArrayList<UserAppContribution>();

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

	public Float getPillarContribution() {
		return pillarContribution;
	}

	public void setPillarContribution(Float pillarContribution) {
		this.pillarContribution = pillarContribution;
	}

	public List<UserAppContribution> getUserACList() {
		return userACList;
	}

	public void setUserACList(List<UserAppContribution> userACList) {
		this.userACList = userACList;
	}

	
}
