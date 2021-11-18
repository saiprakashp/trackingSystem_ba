package com.egil.pts.dao.inventory;

import java.io.Serializable;

import com.egil.pts.dao.common.GenericDao;
import com.egil.pts.dao.domain.DummyTest;

public interface DummyTestDao extends GenericDao<DummyTest, Serializable>{
	
	public void createObj(DummyTest testObj) throws Throwable;
	public void modifyObj(DummyTest testObj) throws Throwable;

}
