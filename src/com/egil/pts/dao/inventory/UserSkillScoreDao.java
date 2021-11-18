package com.egil.pts.dao.inventory;

import java.util.List;

import com.egil.pts.dao.common.GenericDao;
import com.egil.pts.dao.domain.UserSkillScore;

public interface UserSkillScoreDao extends GenericDao<UserSkillScore, Long> {

	public void saveUserSkillScore(List<UserSkillScore> userSkillsList)
			throws Throwable;

	public UserSkillScore getUserSkillScore(Long userId, Long userSkillId,
			Long year, String yearHalf) throws Throwable;

}
