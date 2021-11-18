package com.egil.pts.dao.inventory.impl;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.egil.pts.dao.common.hibernate.HibernateGenericDao;
import com.egil.pts.dao.domain.PTSWorkingDays;
import com.egil.pts.dao.inventory.PTSWorkingDaysDao;

@Repository("workingDaysDao")
public class HibernatePTSWorkingDaysDao extends HibernateGenericDao<PTSWorkingDays, Long> implements PTSWorkingDaysDao {

	@Override
	public PTSWorkingDays getWorkingDaysDetails(int year) throws Throwable {
		Query q = getSession().createQuery("from PTSWorkingDays where year=:year");
		q.setInteger("year", year);
		return (PTSWorkingDays) q.uniqueResult();
	}

}
