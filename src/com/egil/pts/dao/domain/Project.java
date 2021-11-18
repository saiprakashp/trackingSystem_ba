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
@Table(name = "PTS_PROJECT")
@org.hibernate.annotations.Table(appliesTo = "PTS_PROJECT")
public class Project   extends TransactionInfo{

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id", nullable = false)
	@GeneratedValue
	private Long id;
	
	@Column(name = "project_name")
	private String projectName;
	
	@Column(name = "description")
	private String description;
	
	@Column(name = "status")
	private Boolean status;
	
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "pillar_id")
	private Pillar pillar;
	
	@Column(name = "order_num", nullable = false)
	private Long orderNum;


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getProjectName() {
		return projectName;
	}


	public void setProjectName(String projectName) {
		this.projectName = projectName;
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


	public Pillar getPillar() {
		return pillar;
	}


	public void setPillar(Pillar pillar) {
		this.pillar = pillar;
	}


	public Long getOrderNum() {
		return orderNum;
	}


	public void setOrderNum(Long orderNum) {
		this.orderNum = orderNum;
	}
	
}
