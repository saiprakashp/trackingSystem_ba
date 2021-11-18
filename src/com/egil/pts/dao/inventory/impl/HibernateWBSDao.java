package com.egil.pts.dao.inventory.impl;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.egil.pts.dao.common.hibernate.HibernateGenericDao;
import com.egil.pts.dao.domain.WBS;
import com.egil.pts.dao.inventory.WBSDao;

@Repository("wbsDao")
public class HibernateWBSDao extends HibernateGenericDao<WBS, Long>
		implements WBSDao {

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true)
	public List<WBS> getWBS() {
		Query q = getSession().createQuery("from WBS");
		return q.list();
	}

}
