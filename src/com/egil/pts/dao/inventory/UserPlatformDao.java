package com.egil.pts.dao.inventory;

import java.util.List;

import com.egil.pts.dao.common.GenericDao;
import com.egil.pts.dao.domain.UserPlatforms;

public interface UserPlatformDao extends GenericDao<UserPlatforms, Long> {
	
	public void saveUserPlatforms(List<UserPlatforms> userPlatformsList)
			throws Throwable;

	public int removeUserPlatforms(Long userId, List<Long> userPlatformsList)
			throws Throwable;
	
	public List<UserPlatforms> getUserPlatforms(Long userId, Long pillarId)
			throws Throwable;

}

