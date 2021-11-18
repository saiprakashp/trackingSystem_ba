package com.egil.pts.dao.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "PTS_REPORT_QUERY_MAPPER")
@org.hibernate.annotations.Table(appliesTo = "PTS_REPORT_QUERY_MAPPER")
public class ReportQueryMapper implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID", nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "REPORT_NAME")
	private String reportName;

	@Column(name = "REPORT_QUERY")
	private String reportQuery;

	@Column(name = "DESCRIPTION")
	private String description;

	@Column(name = "ACCESS")
	private String access;

	@Column(name = "ALLOWED_USERS")
	private String allowedusers;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getReportName() {
		return reportName;
	}

	public void setReportName(String reportName) {
		this.reportName = reportName;
	}

	public String getReportQuery() {
		return reportQuery;
	}

	public void setReportQuery(String reportQuery) {
		this.reportQuery = reportQuery;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getAccess() {
		return access;
	}

	public void setAccess(String access) {
		this.access = access;
	}

	public String getAllowedusers() {
		return allowedusers;
	}

	public void setAllowedusers(String allowedusers) {
		this.allowedusers = allowedusers;
	}

}
