package com.egil.pts.dao.inventory.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.egil.pts.dao.common.hibernate.HibernateGenericDao;
import com.egil.pts.dao.domain.Roles;
import com.egil.pts.dao.inventory.RolesDao;

@Repository("rolesDao")
public class HibernateRolesDao extends HibernateGenericDao<Roles, Long> implements RolesDao {

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true)
	public List<Roles> getRoles() {
		Query q = getSession().createQuery("from Roles");
		return q.list();
	}

}
