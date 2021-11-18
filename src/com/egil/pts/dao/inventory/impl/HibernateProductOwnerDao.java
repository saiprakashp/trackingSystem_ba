package com.egil.pts.dao.inventory.impl;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.egil.pts.dao.common.hibernate.HibernateGenericDao;
import com.egil.pts.dao.domain.ProductOwner;
import com.egil.pts.dao.inventory.ProductOwnerDao;

@Repository("productOwnerDao")
public class HibernateProductOwnerDao extends HibernateGenericDao<ProductOwner, Long>
		implements ProductOwnerDao {

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true)
	public List<ProductOwner> getProductOwners() {
		Query q = getSession().createQuery("from ProductOwner");
		return q.list();
	}

}
