package com.egil.pts.dao.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class TransactionInfo  implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Column(name = "created_date", nullable = false)
	protected Date createdDate;

	@Column(name = "created_by", nullable = false)
	protected String createdBy;

	@Column(name = "updated_date")
	protected Date updatedDate;

	@Column(name = "updated_by")
	protected String updatedBy;
	
	
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public String getUpdatedBy() {
		return updatedBy;
	}
	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public Date getUpdatedDate() {
		return updatedDate;
	}
	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}
	
}
