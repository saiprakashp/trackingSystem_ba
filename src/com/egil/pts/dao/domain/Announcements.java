package com.egil.pts.dao.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "PTS_ANNOUNCEMENTS")
@org.hibernate.annotations.Table(appliesTo = "PTS_ANNOUNCEMENTS")
public class Announcements extends TransactionInfo{

	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "id", nullable = false)
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "SUBJECT")
	private String subject;
	
	@Column(name = "ANNOUNCED_TYPE")
	private String announcementType;
	
	@Column(name = "ANNOUNCED_BY")
	private String announcedBy;
	
	@Column(name = "DESCRIPTION")
	private String description;
	
	@Column(name = "ANNOUNCED_DATE")
	private Date announcedDate;
	
	@Column(name = "EXPIRES_ON")
	private Date expiresOn;
	
	@Column(name = "status")
	private Boolean status;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getAnnouncementType() {
		return announcementType;
	}

	public void setAnnouncementType(String announcementType) {
		this.announcementType = announcementType;
	}

	public String getAnnouncedBy() {
		return announcedBy;
	}

	public void setAnnouncedBy(String announcedBy) {
		this.announcedBy = announcedBy;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getAnnouncedDate() {
		return announcedDate;
	}

	public void setAnnouncedDate(Date announcedDate) {
		this.announcedDate = announcedDate;
	}

	public Date getExpiresOn() {
		return expiresOn;
	}

	public void setExpiresOn(Date expiresOn) {
		this.expiresOn = expiresOn;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	
}
