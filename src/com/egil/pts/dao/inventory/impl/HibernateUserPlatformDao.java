package com.egil.pts.dao.inventory.impl;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.egil.pts.dao.common.hibernate.HibernateGenericDao;
import com.egil.pts.dao.domain.UserPlatforms;
import com.egil.pts.dao.inventory.UserPlatformDao;

@Repository("userPlatformDao")
public class HibernateUserPlatformDao extends
		HibernateGenericDao<UserPlatforms, Long> implements
		UserPlatformDao {
	
	@Override
	public void saveUserPlatforms(List<UserPlatforms> userPlatformsList)
			throws Throwable {
		int count = 0;
		if (userPlatformsList != null && userPlatformsList.size() > 0) {
			for (UserPlatforms userPlatform : userPlatformsList) {
				save(userPlatform);
				if (count % JDBC_BATCH_SIZE == 0) {
					getSession().flush();
					getSession().clear();
				}
				count++;
			}
		}

	}

	@Override
	public int removeUserPlatforms(Long userId, List<Long> userPlatformsList)
			throws Throwable {
		Query query = getSession()
				.createQuery(
						"delete from UserPlatforms platform "
								+ " where platform.user.id in (:id) and platform.platform.id in (:userPlatformsList)");
		query.setLong("id", userId);
		query.setParameterList("userPlatformsList", userPlatformsList);
		return (Integer) query.executeUpdate();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<UserPlatforms> getUserPlatforms(Long userId, Long pillarId) {
		StringBuilder queryString = new StringBuilder();
		queryString.append(" from UserPlatforms up where up.user.id=:userId ");
		if (pillarId != null) {
			queryString.append(" and up.platform.id=:pillarId ");
		}
		Query query = getSession().createQuery(queryString.toString());
		query.setLong("userId", userId);
		if (pillarId != null) {
			query.setLong("pillarId", pillarId);
		}
		return (List<UserPlatforms>) query.list();
	}

}
