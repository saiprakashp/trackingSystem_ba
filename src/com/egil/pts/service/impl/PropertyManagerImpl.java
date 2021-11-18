package com.egil.pts.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.egil.pts.service.PropertyManager;

@PropertySource(ignoreResourceNotFound = true, value = "classpath:pts.properties")
@Service()
@Scope("prototype")
public class PropertyManagerImpl {

	@Value("${ldarts.retry}")
	private String ldartyRetry;

	@Value("${rico.ldaps.enabled}")
	private String ldartsEnable;

	@Value("${rico.user.image.path}")
	private String imagePath;

	@Value("${rico.admin.check.enable}")
	private String adminEnable;

	@Value("${rico.user.image.path}")
	private String userImagePath;

	@Value("${rico.ldaps.DN}")
	private String ldapsDn;

	@Value("${rico.ldaps.Manager.Pass}")
	private String ldapsPass;

	@Value("${pts.manager.util.requiredType}")
	private String requiredType;

	@Value("${rico.ldaps.enabled}")
	private String ldapsBaseDn;

	public String getLdartyRetry() {
		return ldartyRetry;
	}

	public void setLdartyRetry(String ldartyRetry) {
		this.ldartyRetry = ldartyRetry;
	}

	public String getLdartsEnable() {
		return ldartsEnable;
	}

	public void setLdartsEnable(String ldartsEnable) {
		this.ldartsEnable = ldartsEnable;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public String getAdminEnable() {
		return adminEnable;
	}

	public void setAdminEnable(String adminEnable) {
		this.adminEnable = adminEnable;
	}

	public String getUserImagePath() {
		return userImagePath;
	}

	public void setUserImagePath(String userImagePath) {
		this.userImagePath = userImagePath;
	}

	public String getLdapsDn() {
		return ldapsDn;
	}

	public void setLdapsDn(String ldapsDn) {
		this.ldapsDn = ldapsDn;
	}

	public String getLdapsPass() {
		return ldapsPass;
	}

	public void setLdapsPass(String ldapsPass) {
		this.ldapsPass = ldapsPass;
	}

	public String getLdapsBaseDn() {
		return ldapsBaseDn;
	}

	public void setLdapsBaseDn(String ldapsBaseDn) {
		this.ldapsBaseDn = ldapsBaseDn;
	}

	public String getRequiredType() {
		return requiredType;
	}

	public void setRequiredType(String requiredType) {
		this.requiredType = requiredType;
	}
}
