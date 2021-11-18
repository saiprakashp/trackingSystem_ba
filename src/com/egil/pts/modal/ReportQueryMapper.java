package com.egil.pts.modal;

import java.io.Serializable;
import java.util.List;

import com.egil.pts.util.Access;

public class ReportQueryMapper implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;
	private String query;
	private String description;
	private String keywoard;
	private Enum<Access> access;
	private List<String> allowdResources;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getKeywoard() {
		return keywoard;
	}

	public void setKeywoard(String keywoard) {
		this.keywoard = keywoard;
	}

	public Enum getAccess() {
		return access;
	}

	public void setAccess(Enum access) {
		this.access = access;
	}

	@Override
	public String toString() {
		return "ReportQueryMapper [id=" + id + ", query=" + query + ", description=" + description + ", keywoard="
				+ keywoard + ", access=" + access + "]";
	}

	public List<String> getAllowdResources() {
		return allowdResources;
	}

	public void setAllowdResources(List<String> allowdResources) {
		this.allowdResources = allowdResources;
	}

}
