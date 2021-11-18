package com.egil.pts.dao.inventory.impl;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.egil.pts.dao.common.hibernate.HibernateGenericDao;
import com.egil.pts.dao.domain.UserSkillScore;
import com.egil.pts.dao.inventory.UserSkillScoreDao;

@Repository("userSkillScoreDao")
public class HibernateUserSkillScoreDao extends
		HibernateGenericDao<UserSkillScore, Long> implements UserSkillScoreDao {

	@Override
	public void saveUserSkillScore(List<UserSkillScore> userSkillsList)
			throws Throwable {
		int count = 0;
		if (userSkillsList != null && userSkillsList.size() > 0) {
			for (UserSkillScore userSkill : userSkillsList) {
				save(userSkill);
				if (count % JDBC_BATCH_SIZE == 0) {
					getSession().flush();
					getSession().clear();
				}
				count++;
			}
		}
	}

	@Override
	public UserSkillScore getUserSkillScore(Long userId, Long skillId,
			Long year, String yearHalf) throws Throwable {
		StringBuilder queryString = new StringBuilder();
		queryString
				.append("select uss from UserSkillScore uss, UserSkills us where uss.userSkill.id=us.id "
						+ " and us.technology.id=:skillId and us.user.id=:userId and uss.year=:year and uss.yearHalf=:yearHalf ");
		Query query = getSession().createQuery(queryString.toString());
		query.setLong("userId", userId);
		query.setLong("skillId", skillId);
		query.setLong("year", year);
		query.setString("yearHalf", yearHalf);
		return (UserSkillScore) query.uniqueResult();
	}

}
