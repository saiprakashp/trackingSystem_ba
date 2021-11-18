package com.egil.pts.dao.inventory.impl;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.egil.pts.dao.common.hibernate.HibernateGenericDao;
import com.egil.pts.dao.domain.Technologies;
import com.egil.pts.dao.inventory.TechnologiesDao;

@Repository("technologiesDao")
public class HibernateTechnologiesDao extends
		HibernateGenericDao<Technologies, Long> implements TechnologiesDao {

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true)
	public List<Technologies> getTechnologies() {
		Query q = getSession().createQuery("from Technologies");
		return q.list();
	}

}
