package com.egil.pts.modal;

public class Pillar extends TransactionInfo{

	private static final long serialVersionUID = 1L;
	private String pillarName;
	private String serviceAssurance;
	private String customer;
	private String description;
	private Status status;
	
	public String getPillarName() {
		return pillarName;
	}
	public void setPillarName(String pillarName) {
		this.pillarName = pillarName;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}
	public String getCustomer() {
		return customer;
	}
	public void setCustomer(String customer) {
		this.customer = customer;
	}
	public String getServiceAssurance() {
		return serviceAssurance;
	}
	public void setServiceAssurance(String serviceAssurance) {
		this.serviceAssurance = serviceAssurance;
	}
	
}
