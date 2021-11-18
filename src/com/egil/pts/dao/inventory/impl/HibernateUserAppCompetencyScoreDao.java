package com.egil.pts.dao.inventory.impl;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.egil.pts.dao.common.hibernate.HibernateGenericDao;
import com.egil.pts.dao.domain.UserPlatformCompetencyScore;
import com.egil.pts.dao.inventory.UserAppCompetencyScoreDao;

@Repository("userAppCompetencyScore")
public class HibernateUserAppCompetencyScoreDao extends
		HibernateGenericDao<UserPlatformCompetencyScore, Long> implements
		UserAppCompetencyScoreDao {

	@Override
	public void saveUserPlatformCompetencyScore(
			List<UserPlatformCompetencyScore> userSkillsList) throws Throwable {
		int count = 0;
		if (userSkillsList != null && userSkillsList.size() > 0) {
			for (UserPlatformCompetencyScore userSkill : userSkillsList) {
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
	public UserPlatformCompetencyScore getUserCompScore(Long userId,
			Long platformId, Long year, String yearHalf) throws Throwable {
		StringBuilder queryString = new StringBuilder();
		queryString
				.append("select uacs from UserPlatformCompetencyScore uacs, UserPlatforms up where uacs.userPlatform.id=up.id "
						+ " and up.platform.id=:platformId and up.user.id=:userId and uacs.year=:year and uacs.yearHalf=:yearHalf ");
		Query query = getSession().createQuery(queryString.toString());
		query.setLong("userId", userId);
		query.setLong("platformId", platformId);
		query.setLong("year", year);
		query.setString("yearHalf", yearHalf);
		return (UserPlatformCompetencyScore) query.uniqueResult();
	}

}
