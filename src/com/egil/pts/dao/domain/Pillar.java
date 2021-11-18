package com.egil.pts.dao.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;


@Entity
@Table(name = "PTS_PILLAR")
@org.hibernate.annotations.Table(appliesTo = "PTS_PILLAR")
public class Pillar   extends TransactionInfo{

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id", nullable = false)
	@GeneratedValue
	private Long id;
	
	@Column(name = "pillar_name")
	private String pillarName;
	
	@Column(name = "description")
	private String description;
	
	@Column(name = "status")
	private Boolean status;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ACCOUNT_ID")
	private CustomerAccounts customerAccount;
	
	@Column(name = "service_assurance")
	private String serviceAssurance;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public String getServiceAssurance() {
		return serviceAssurance;
	}

	public void setServiceAssurance(String serviceAssurance) {
		this.serviceAssurance = serviceAssurance;
	}

	public CustomerAccounts getCustomerAccount() {
		return customerAccount;
	}

	public void setCustomerAccount(CustomerAccounts customerAccount) {
		this.customerAccount = customerAccount;
	}

}
