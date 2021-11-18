package com.egil.pts.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.egil.pts.dao.domain.MhrCategoryTypes;
import com.egil.pts.dao.domain.UserTypes;
import com.egil.pts.service.UserTypesService;
import com.egil.pts.service.common.BaseUIService;

@Service("userTypesService")
public class UserTypesServiceImpl extends BaseUIService implements
		UserTypesService {

	@Override
	public List<UserTypes> getUserTypes() throws Throwable {
		return daoManager.getUserTypesDao().getUserTypes();
	}

	@Override
	public String getJSONStringOfUserTypes() throws Throwable {
		String returnValue = "";
		returnValue = -1 + ":" + "Please Select" + ";";
		for (UserTypes userType : getUserTypes()) {
			returnValue = returnValue + userType.getId() + ":"
					+ userType.getUserType() + ";";
		}
		return returnValue.substring(0, returnValue.length() - 1);
	}

	public void getUserTypesMap(Map<Long, String> userTypesMap)
			throws Throwable {
		for (UserTypes userType : getUserTypes()) {
			userTypesMap.put(userType.getId(), userType.getUserType());
		}
	}

	@Override
	public void getMhrMap(Map<String, String> mhrCategoryMap) throws Throwable{
		List<MhrCategoryTypes> categoryTypes= daoManager.getUserTypesDao().getmhrCategories();
		if(categoryTypes!=null)
			for(MhrCategoryTypes mhrCategoryTypes : categoryTypes) {
				mhrCategoryMap.put(mhrCategoryTypes.getDescription(), mhrCategoryTypes.getValue());
			}
	}

}
