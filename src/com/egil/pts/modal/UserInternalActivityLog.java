package com.egil.pts.modal;

import java.io.Serializable;

public class UserInternalActivityLog implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private String id;
	private String systemName;
	private String type;
	private String loggedHrs;
	private String weekendDate;
	private String resourceName;
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getSystemName() {
		return systemName;
	}
	public void setSystemName(String systemName) {
		this.systemName = systemName;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getLoggedHrs() {
		return loggedHrs;
	}
	public void setLoggedHrs(String loggedHrs) {
		this.loggedHrs = loggedHrs;
	}
	public String getWeekendDate() {
		return weekendDate;
	}
	public void setWeekendDate(String weekendDate) {
		this.weekendDate = weekendDate;
	}
	public String getResourceName() {
		return resourceName;
	}
	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}
	
	

}
