package com.egil.pts.service;

import java.util.List;
import java.util.Map;

import com.egil.pts.dao.domain.Location;

public interface LocationService {
	
	public List<Location> getLocations() throws Throwable;
	
	public String getJSONStringOfLocations() throws Throwable;
	
	public void getLocationsMap(Map<Long, String> locationMap) throws Throwable;
}
