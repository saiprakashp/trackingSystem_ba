package com.egil.pts.dao.inventory.impl;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.egil.pts.dao.common.hibernate.HibernateGenericDao;
import com.egil.pts.dao.domain.Privilege;
import com.egil.pts.dao.inventory.PrivilegeDao;

@Repository("privilegeDao")
public class HibernatePrivilegeDao extends HibernateGenericDao<Privilege, Long>
		implements PrivilegeDao {

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true)
	public List<Privilege> getPrivileges() {
		Query q = getSession().createQuery("from Privilege");
		return q.list();
	}

}
