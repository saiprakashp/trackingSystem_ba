
package com.egil.pts.dao.inventory.impl;

import java.io.Serializable;

import org.springframework.stereotype.Repository;

import com.egil.pts.dao.common.hibernate.HibernateGenericDao;
import com.egil.pts.dao.domain.DummyTest;
import com.egil.pts.dao.inventory.DummyTestDao;

@Repository("dummyTestDao")
public class HibernateTestDao extends HibernateGenericDao<DummyTest, Serializable> implements DummyTestDao{

	@Override
	public void createObj(DummyTest testObj) throws Throwable{
		save(testObj);
		
	}

	@Override
	public void modifyObj(DummyTest testObj) throws Throwable{
		update(testObj);
		
	}

}
