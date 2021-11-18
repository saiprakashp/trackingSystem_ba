package com.egil.pts.dao.inventory;

import java.util.List;

import com.egil.pts.dao.common.GenericDao;
import com.egil.pts.dao.domain.Privilege;

public interface PrivilegeDao extends GenericDao<Privilege, Long>{

	public List<Privilege> getPrivileges();
}
