package com.egil.pts.dao.inventory.impl;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.egil.pts.dao.common.hibernate.HibernateGenericDao;
import com.egil.pts.dao.domain.Category;
import com.egil.pts.dao.inventory.CategoryDao;

@Repository("categoryDao")
public class HibernateCategoryDao extends HibernateGenericDao<Category, Long>
		implements CategoryDao {

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true)
	public List<Category> getCategories() {
		Query q = getSession().createQuery("from Category");
		return q.list();
	}

}
