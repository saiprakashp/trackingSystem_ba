package com.egil.pts.modal;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TransactionInfo  implements Serializable{

	private static final long serialVersionUID = 1L;
	private Long id;
	protected String createdBy;
	protected String updatedBy;
	protected Date createdDate;
	protected Date updatedDate;
	
	protected String formattedCreatedDate;
	protected String formattedUpdatedDate;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
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
	public String getFormattedCreatedDate() {
		DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
		return createdDate!=null?df.format(createdDate):"";
	}
	public String getFormattedUpdatedDate() {
		DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
		return updatedDate!= null?df.format(updatedDate):"";
	}
	
}
