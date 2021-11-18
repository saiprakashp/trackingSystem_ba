package com.egil.pts.modal;

public class Project extends TransactionInfo {

	private static final long serialVersionUID = 1L;
	private Long id;
	private Long order;
	private Long projectId;
	private String projectName;
	private String pillar;
	private String customer;
	private String type;
	private String description;
	private Status status;
	private Long pillarId;
	private Long orderNum;

	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
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

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public String getPillar() {
		return pillar;
	}

	public void setPillar(String pillar) {
		this.pillar = pillar;
	}

	public String getCustomer() {
		return customer;
	}

	public void setCustomer(String customer) {
		this.customer = customer;
	}

	public Long getPillarId() {
		return pillarId;
	}

	public void setPillarId(Long pillarId) {
		this.pillarId = pillarId;
	}

	public Long getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(Long orderNum) {
		this.orderNum = orderNum;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getOrder() {
		return order;
	}

	public void setOrder(Long order) {
		this.order = order;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "Project [id=" + id + ", order=" + order + ", projectId=" + projectId + ", projectName=" + projectName
				+ ", pillar=" + pillar + ", customer=" + customer + ", type=" + type + ", description=" + description
				+ ", status=" + status + ", pillarId=" + pillarId + ", orderNum=" + orderNum + "]";
	}

}
