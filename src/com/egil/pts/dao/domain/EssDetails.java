package com.egil.pts.dao.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ESS_DETAILS")
@org.hibernate.annotations.Table(appliesTo = "ESS_DETAILS")
public class EssDetails {

	@Id
	@Column(name = "id", nullable = false)
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "signum")
	private String signum;
	
	@Column(name = "YEAR")
	private Long year;
	
	@Column(name = "month")
	private String month;
	
	@Column(name = "TARGET_HRS")
	private Float targetHrs;
	
	@Column(name = "CHARGED_HRS")
	private Float chargedHrs;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSignum() {
		return signum;
	}

	public void setSignum(String signum) {
		this.signum = signum;
	}

	public Long getYear() {
		return year;
	}

	public void setYear(Long year) {
		this.year = year;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public Float getTargetHrs() {
		return targetHrs;
	}

	public void setTargetHrs(Float targetHrs) {
		this.targetHrs = targetHrs;
	}

	public Float getChargedHrs() {
		return chargedHrs;
	}

	public void setChargedHrs(Float chargedHrs) {
		this.chargedHrs = chargedHrs;
	}
	
	
}
