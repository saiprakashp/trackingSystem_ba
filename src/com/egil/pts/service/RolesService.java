package com.egil.pts.service;

import java.util.List;
import java.util.Map;

import com.egil.pts.dao.domain.Roles;

public interface RolesService {
	
	public List<Roles> getRoles() throws Throwable;
	
	public String getJSONStringOfRoles() throws Throwable;
	
	public void getRolesMap(Map<Long, String> rolesMap) throws Throwable;
}
