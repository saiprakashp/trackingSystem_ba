package com.egil.pts.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.egil.pts.dao.domain.Privilege;
import com.egil.pts.service.PrivilegeService;
import com.egil.pts.service.common.BaseUIService;

@Service("privilegeService")
public class PrivilegeServiceImpl extends BaseUIService implements
		PrivilegeService {

	@Override
	public List<Privilege> getPrivileges() throws Throwable {
		return daoManager.getPrivilegeDao().getPrivileges();
	}

	@Override
	public String getJSONStringOfPrivileges() throws Throwable {
		String returnValue = "";
		returnValue = -1 + ":" + "Please Select" + ";";
		for (Privilege privilege : getPrivileges()) {
			returnValue = returnValue + privilege.getId() + ":"
					+ privilege.getPrivName() + ";";
		}
		return returnValue.substring(0, returnValue.length() - 1);
	}
	
	@Override
	public void getPrivilegesMap(Map<Long, String> privilegesMap) throws Throwable {
		privilegesMap.clear();
		for (Privilege privilege : getPrivileges()) {
			privilegesMap.put(privilege.getId(), privilege.getPrivName());
		}
	}

}
