package com.egil.pts.service;

import java.util.List;
import java.util.Map;

import com.egil.pts.dao.domain.UserTypes;

public interface UserTypesService {
	public List<UserTypes> getUserTypes() throws Throwable;
	
	public String getJSONStringOfUserTypes() throws Throwable;
	
	public void getUserTypesMap(Map<Long, String> userTypesMap) throws Throwable;

	public void getMhrMap(Map<String, String> mhrCategoryMap) throws Throwable;
}
