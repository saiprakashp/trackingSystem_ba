package com.egil.pts.service.common;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.egil.pts.dao.common.DaoManager;


@Service("baseUIService")
public abstract class BaseUIService {
	
	protected Logger logger = LogManager.getLogger("PTSCORE");
	
	protected static final int STATUS_SUCCESS = 0;
	
	protected static final int STATUS_FAILURE = 1;
	
	protected static final int DROPDOWN_ROWNUMBER = 4;

	@Autowired(required = true)
	@Qualifier("daoManager")
	protected DaoManager daoManager;
	
	@Autowired(required = true)
	@Qualifier("serviceManager")
	protected ServiceManager serviceManager;

	public DaoManager getDaoManager() {
		return daoManager;
	}

	public void setDaoManager(DaoManager daoManager) {
		this.daoManager = daoManager;
	}

	public ServiceManager getServiceManager() {
		return serviceManager;
	}

	public void setServiceManager(ServiceManager serviceManager) {
		this.serviceManager = serviceManager;
	}

	
}
