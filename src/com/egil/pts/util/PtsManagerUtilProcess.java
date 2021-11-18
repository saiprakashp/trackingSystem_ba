package com.egil.pts.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.egil.pts.actions.DaoReportAction;
import com.egil.pts.service.common.ServiceManager;

@Configuration
@EnableScheduling
@Component
public class PtsManagerUtilProcess {
	@Autowired(required = true)
	@Qualifier("serviceManager")
	private ServiceManager serviceManager;

//* * 25 * *
	@Scheduled(cron = "* * * * * ?")
	public void updateManagerUtil() {
		
	}

	public ServiceManager getServiceManager() {
		return serviceManager;
	}

	public void setServiceManager(ServiceManager serviceManager) {
		this.serviceManager = serviceManager;
	}

}
