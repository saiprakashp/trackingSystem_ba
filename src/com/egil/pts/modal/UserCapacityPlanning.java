package com.egil.pts.modal;

import java.util.ArrayList;
import java.util.List;

public class UserCapacityPlanning {

	private String userId;
	
	private String networkCodesMap;
	
	List<CapacityPlanning> capacityPlanning = new ArrayList<CapacityPlanning>();

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public List<CapacityPlanning> getCapacityPlanning() {
		return capacityPlanning;
	}

	public void setCapacityPlanning(List<CapacityPlanning> capacityPlanning) {
		this.capacityPlanning = capacityPlanning;
	}

	public String getNetworkCodesMap() {
		return networkCodesMap;
	}

	public void setNetworkCodesMap(String networkCodesMap) {
		this.networkCodesMap = networkCodesMap;
	}
	
	
}
