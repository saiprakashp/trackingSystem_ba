package com.egil.pts.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.egil.pts.dao.domain.Category;
import com.egil.pts.service.CategoryService;
import com.egil.pts.service.common.BaseUIService;

@Service("categoryService")
public class CategoryServiceImpl extends BaseUIService implements
		CategoryService {

	@Override
	public List<Category> getCategories() {
		return daoManager.getCategoryDao().getCategories();
	}

	@Override
	public String getJSONStringOfCategories() {
		String returnValue = "";
		returnValue = -1 + ":" + "Please Select" + ";";
		for (Category category : getCategories()) {
			returnValue = returnValue + category.getId() + ":"
					+ category.getCategory() + ";";
		}
		return returnValue.substring(0, returnValue.length() - 1);
	}

}
