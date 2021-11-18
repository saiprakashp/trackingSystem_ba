package com.egil.pts.dao.inventory;

import java.util.List;

import com.egil.pts.dao.common.GenericDao;
import com.egil.pts.dao.domain.UserPlatformCompetencyScore;

public interface UserAppCompetencyScoreDao extends GenericDao<UserPlatformCompetencyScore, Long> {

	public void saveUserPlatformCompetencyScore(List<UserPlatformCompetencyScore> UserAppsList)
			throws Throwable;

	public UserPlatformCompetencyScore getUserCompScore(Long userId, Long userAppId,
			Long year, String yearHalf) throws Throwable;

}
