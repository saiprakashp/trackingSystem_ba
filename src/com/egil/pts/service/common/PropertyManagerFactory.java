package com.egil.pts.service.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.egil.pts.service.impl.PropertyManagerImpl;

@Service()
@Scope("prototype")
public class PropertyManagerFactory {

	@Autowired(required = true)
	private PropertyManagerImpl propertyManager;

	public PropertyManagerImpl getPropertyManager() {
		return propertyManager;
	}

	public void setPropertyManager(PropertyManagerImpl propertyManager) {
		this.propertyManager = propertyManager;
	}

}
