package com.egil.pts.dao.inventory;

import com.egil.pts.dao.common.GenericDao;
import com.egil.pts.dao.domain.PTSWorkingDays;

public interface PTSWorkingDaysDao extends GenericDao<PTSWorkingDays, Long> {
	
	public PTSWorkingDays getWorkingDaysDetails(int year) throws Throwable;

}
