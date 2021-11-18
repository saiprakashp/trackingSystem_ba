package com.egil.pts.service;

import java.util.List;

import com.egil.pts.dao.domain.Category;

public interface CategoryService {

	public List<Category> getCategories();

	public String getJSONStringOfCategories();
}
