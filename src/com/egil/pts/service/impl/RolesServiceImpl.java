package com.egil.pts.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.egil.pts.dao.domain.Roles;
import com.egil.pts.service.RolesService;
import com.egil.pts.service.common.BaseUIService;

@Service("rolesService")
public class RolesServiceImpl extends BaseUIService implements RolesService {

	@Override
	public List<Roles> getRoles() throws Throwable {
		return daoManager.getRolesDao().getRoles();
	}

	@Override
	public String getJSONStringOfRoles() throws Throwable {
		String returnValue = "";
		returnValue = -1 + ":" + "Please Select" + ";";
		for (Roles role : getRoles()) {
			returnValue = returnValue + role.getId() + ":" + role.getRoleName()
					+ ";";
		}
		return returnValue.substring(0, returnValue.length() - 1);
	}

	@Override
	public void getRolesMap(Map<Long, String> rolesMap) throws Throwable {
		for (Roles role : getRoles()) {
			rolesMap.put(role.getId(), role.getRoleName());
		}
	}
}
