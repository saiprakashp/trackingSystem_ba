package com.egil.pts.dao.inventory;

import java.util.List;

import com.egil.pts.dao.common.GenericDao;
import com.egil.pts.dao.domain.MhrCategoryTypes;
import com.egil.pts.dao.domain.UserTypes;

public interface UserTypesDao extends GenericDao<UserTypes, Long>{

	public List<UserTypes> getUserTypes();

	public List<MhrCategoryTypes> getmhrCategories();
}
