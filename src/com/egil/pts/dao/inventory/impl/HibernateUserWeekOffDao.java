package com.egil.pts.dao.inventory.impl;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.egil.pts.dao.common.hibernate.HibernateGenericDao;
import com.egil.pts.dao.domain.UserWeekOff;
import com.egil.pts.dao.inventory.UserWeekOffDao;

@Repository("userWeekOffDao")
public class HibernateUserWeekOffDao extends HibernateGenericDao<UserWeekOff, Long> implements UserWeekOffDao {

	@Override
	public void saveUserWeekOff(List<UserWeekOff> userWeekOffList) throws Throwable {
		int count = 0;
		if (userWeekOffList != null && userWeekOffList.size() > 0) {
			for (UserWeekOff userSkill : userWeekOffList) {
				save(userSkill);
				if (count % JDBC_BATCH_SIZE == 0) {
					getSession().flush();
					getSession().clear();
				}
				count++;
			}
		}

	}

	@SuppressWarnings("unchecked")
	@Override
	public UserWeekOff getUserWeekOff(Long userId, String weekendingDate) throws Throwable {
		Query query = getSession().createQuery(
				" from UserWeekOff weekoff " + " where weekoff.user.id=:id and weekoff.weekendingDate=:week");
		query.setLong("id", userId);
		query.setString("week", weekendingDate);

		List<UserWeekOff> userWeekOffList = query.list();
		if (userWeekOffList != null && userWeekOffList.size() > 0) {
			return userWeekOffList.get(0);
		} else {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Long> getUserWeekOff(String from, String to) throws Throwable {
		Query query = getSession().createSQLQuery(
				"select user_id from PTS_USER_WEEK_OFF  where WEEK_OFF=true and WEEKENDING_DATE>=:from and WEEKENDING_DATE<=:to");
		query.setString("from", from);
		query.setString("to", to);

		return query.list();
	}

	@Override
	public void delete(List<Long> removedIds) {
		if (removedIds != null && removedIds.size() > 0) {
			Query query = getSession().createQuery("delete from UserWeekOff f where " + "f.id in (:id)");
			query.setParameterList("id", removedIds);
			query.executeUpdate();
		}
	}
}
