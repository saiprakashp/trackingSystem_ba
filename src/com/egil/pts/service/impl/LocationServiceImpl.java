package com.egil.pts.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.egil.pts.dao.domain.Location;
import com.egil.pts.service.LocationService;
import com.egil.pts.service.common.BaseUIService;

@Service("locationService")
public class LocationServiceImpl extends BaseUIService implements
		LocationService {

	@Override
	public List<Location> getLocations() throws Throwable {
		return daoManager.getLocationDao().getLocations();
	}

	@Override
	public String getJSONStringOfLocations() throws Throwable {
		String returnValue = "";
		returnValue = -1 + ":" + "Please Select" + ";";
		for (Location location : getLocations()) {
			returnValue = returnValue + location.getId() + ":"
					+ location.getName() + ";";
		}
		return returnValue.substring(0, returnValue.length() - 1);
	}

	@Override
	public void getLocationsMap(Map<Long, String> locationMap) throws Throwable {
		for (Location location : getLocations()) {
			locationMap.put(location.getId(),
					location.getName()
							+ ((location.getCode() != null && !location
									.getCode().trim().isEmpty()) ? ", "
									+ location.getCode() : ""));
		}
	}
}
