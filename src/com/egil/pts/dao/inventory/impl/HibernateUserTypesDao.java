package com.egil.pts.dao.inventory.impl;

import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.egil.pts.dao.common.hibernate.HibernateGenericDao;
import com.egil.pts.dao.domain.MhrCategoryTypes;
import com.egil.pts.dao.domain.UserTypes;
import com.egil.pts.dao.inventory.UserTypesDao;

@Repository("userTypesDao")
public class HibernateUserTypesDao extends HibernateGenericDao<UserTypes, Long>
		implements UserTypesDao {

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true)
	public List<UserTypes> getUserTypes() {
		Query q = getSession().createQuery("from UserTypes");
		return q.list();
	}
	
	@Override
	public List<MhrCategoryTypes> getmhrCategories() {
		Query q = getSession().createQuery("from MhrCategoryTypes");
		return q.list();
	}

}
