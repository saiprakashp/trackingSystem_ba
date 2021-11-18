package com.egil.pts.dao.inventory.impl;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.egil.pts.dao.common.hibernate.HibernateGenericDao;
import com.egil.pts.dao.domain.UserSkills;
import com.egil.pts.dao.inventory.UserSkillsDao;

@Repository("userSkillsDao")
public class HibernateUserSkillsDao extends
		HibernateGenericDao<UserSkills, Long> implements UserSkillsDao {

	@Override
	public void saveUserSkills(List<UserSkills> userSkillsList)
			throws Throwable {
		int count = 0;
		if (userSkillsList != null && userSkillsList.size() > 0) {
			for (UserSkills userSkill : userSkillsList) {
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
	public int removeUserSkills(Long userId, List<Long> userSkillsList)
			throws Throwable {
		Query query = getSession()
				.createQuery(
						"delete from UserSkills skill "
								+ " where skill.user.id in (:id) and skill.technology.id in (:userSkillsList)");
		query.setLong("id", userId);
		query.setParameterList("userSkillsList", userSkillsList);
		return (Integer) query.executeUpdate();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<UserSkills> getUserSkill(Long userId, Long skillId)
			throws Throwable {
		StringBuilder queryString = new StringBuilder();
		queryString.append(" from UserSkills us where us.user.id=:userId ");
		if (skillId != null) {
			queryString.append(" and us.technology.id=:skillId ");
		}
		Query query = getSession().createQuery(queryString.toString());
		query.setLong("userId", userId);
		if (skillId != null) {
			query.setLong("skillId", skillId);
		}
		return (List<UserSkills>) query.list();
	}

}
