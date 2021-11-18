package com.egil.pts.service;

import java.util.List;
import java.util.Map;

import com.egil.pts.dao.domain.Privilege;

public interface PrivilegeService {
	public List<Privilege> getPrivileges() throws Throwable;
	
	public String getJSONStringOfPrivileges() throws Throwable;
	
	public void getPrivilegesMap(Map<Long, String> privilegesMap) throws Throwable;
}
