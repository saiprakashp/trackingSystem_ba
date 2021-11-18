package com.egil.pts.dao.inventory.impl;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.egil.pts.dao.common.hibernate.HibernateGenericDao;
import com.egil.pts.dao.domain.Location;
import com.egil.pts.dao.inventory.LocationDao;

@Repository("locationDao")
public class HibernateLocationDao extends HibernateGenericDao<Location, Long>
		implements LocationDao {

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true)
	public List<Location> getLocations() {
		Query q = getSession().createQuery("from Location order by name ");
		return q.list();
	}

}
