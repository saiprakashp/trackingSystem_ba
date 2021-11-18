package com.egil.pts.dao.inventory;

import java.util.List;

import com.egil.pts.dao.common.GenericDao;
import com.egil.pts.dao.domain.Roles;

public interface RolesDao extends GenericDao<Roles, Long> {

	public List<Roles> getRoles();

}
