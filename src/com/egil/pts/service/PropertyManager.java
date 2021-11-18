package com.egil.pts.service;

public interface PropertyManager {

	public String getLdartyRetry();

	public void setLdartyRetry(String ldartyRetry);

	public String getLdartsEnable();

	public void setLdartsEnable(String ldartsEnable);

	public String getImagePath();

	public void setImagePath(String imagePath);

	public String isAdminEnable();

	public void setAdminEnable(String adminEnable);

	public String getUserImagePath();

	public void setUserImagePath(String userImagePath);

	public String getLdapsDn();

	public void setLdapsDn(String ldapsDn);

	public String isLdapsPass();

	public void setLdapsPass(String ldapsPass);

	public String getLdapsBaseDn();

	public void setLdapsBaseDn(String ldapsBaseDn);

	public String getRequiredType();

	public void setRequiredType(String requiredType);
}
