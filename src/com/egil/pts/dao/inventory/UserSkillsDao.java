package com.egil.pts.dao.inventory;

import java.util.List;

import com.egil.pts.dao.common.GenericDao;
import com.egil.pts.dao.domain.UserSkills;

public interface UserSkillsDao extends GenericDao<UserSkills, Long> {

	public void saveUserSkills(List<UserSkills> userEffortList)
			throws Throwable;

	public int removeUserSkills(Long userId, List<Long> userSkillsList)
			throws Throwable;

	public List<UserSkills> getUserSkill(Long userId, Long skillId) throws Throwable;
}
