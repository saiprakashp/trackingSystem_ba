package com.egil.pts.modal;

import java.util.ArrayList;
import java.util.List;

public class UserGridData {
	private List<User> gridModel = new ArrayList<User>();
	private String supervisorMap;
	private String privilegesMap;
	private String rolesMap;
	private String userTypesMap;
	
	
	public List<User> getGridModel() {
		return gridModel;
	}
	public void setGridModel(List<User> gridModel) {
		this.gridModel = gridModel;
	}
	public String getSupervisorMap() {
		return supervisorMap;
	}
	public void setSupervisorMap(String supervisorMap) {
		this.supervisorMap = supervisorMap;
	}
	public String getPrivilegesMap() {
		return privilegesMap;
	}
	public void setPrivilegesMap(String privilegesMap) {
		this.privilegesMap = privilegesMap;
	}
	public String getRolesMap() {
		return rolesMap;
	}
	public void setRolesMap(String rolesMap) {
		this.rolesMap = rolesMap;
	}
	public String getUserTypesMap() {
		return userTypesMap;
	}
	public void setUserTypesMap(String userTypesMap) {
		this.userTypesMap = userTypesMap;
	}
	
	
}
