package com.egil.pts.dao.inventory;

import java.util.List;

import com.egil.pts.dao.common.GenericDao;
import com.egil.pts.dao.domain.Category;

public interface CategoryDao extends GenericDao<Category, Long>{

	public List<Category> getCategories();
}
