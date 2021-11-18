package com.egil.pts.dao.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "PTS_ACTIVITY_CODES")
@org.hibernate.annotations.Table(appliesTo = "PTS_ACTIVITY_CODES")
public class ActivityCodes  extends TransactionInfo{

	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "id", nullable = false)
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "activity_code_id")
	private String activityCodeId;
	
	@Column(name = "activity_code_name")
	private String activityCodeName;
	
	@Column(name = "description")
	private String description;
	
	@Column(name = "status")
	private Boolean status;

	@Column(name = "type")
	private String type;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getActivityCodeId() {
		return activityCodeId;
	}

	public void setActivityCodeId(String activityCodeId) {
		this.activityCodeId = activityCodeId;
	}

	public String getActivityCodeName() {
		return activityCodeName;
	}

	public void setActivityCodeName(String activityCodeName) {
		this.activityCodeName = activityCodeName;
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
