package com.egil.pts.dao.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

@Entity
@Table(name = "PTS_USER")
@org.hibernate.annotations.Table(appliesTo = "PTS_USER")
public class User extends TransactionInfo implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id", nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Embedded
	private Person personalInfo;

	@Embedded
	private Credential credentials;

	@Column(name = "status")
	private String status;

	@Column(name = "comments")
	private String comments;

	@Column(name = "release_comments")
	private String releaseComments;

	@Column(name = "last_login")
	private Date lastLogin;

	@OneToOne(fetch = FetchType.LAZY, mappedBy = "user")
	@org.hibernate.annotations.Cascade({ org.hibernate.annotations.CascadeType.SAVE_UPDATE })
	private UserRole userRole = new UserRole();

	@OneToOne(fetch = FetchType.EAGER, mappedBy = "user")
	@org.hibernate.annotations.Cascade({ org.hibernate.annotations.CascadeType.SAVE_UPDATE })
	private UserSupervisor userSupervisor = new UserSupervisor();

	@OneToMany(fetch = FetchType.LAZY, orphanRemoval = true)
	@OrderBy("id ASC")
	@Fetch(org.hibernate.annotations.FetchMode.SUBSELECT)
	@JoinColumn(name = "user_id")
	private List<UserSkills> userSkills = new ArrayList<UserSkills>();

	@OneToMany(fetch = FetchType.LAZY, orphanRemoval = true)
	@OrderBy("id ASC")
	@Fetch(org.hibernate.annotations.FetchMode.SUBSELECT)
	@JoinColumn(name = "user_id")
	private List<UserNetworkCodes> userNetworkCodes = new ArrayList<UserNetworkCodes>();

	@OneToMany(fetch = FetchType.LAZY, orphanRemoval = true)
	@OrderBy("id ASC")
	@Fetch(org.hibernate.annotations.FetchMode.SUBSELECT)
	@JoinColumn(name = "user_id")
	private List<UserPlatforms> userPlatforms = new ArrayList<UserPlatforms>();

	@OneToMany(fetch = FetchType.LAZY, orphanRemoval = true)
	@OrderBy("id ASC")
	@Fetch(org.hibernate.annotations.FetchMode.SUBSELECT)
	@JoinColumn(name = "user_id")
	@NotFound(action = NotFoundAction.IGNORE)
	private List<UserProjects> userProjects = new ArrayList<UserProjects>();

	@OneToMany(fetch = FetchType.LAZY, orphanRemoval = true)
	@OrderBy("id ASC")
	@Fetch(org.hibernate.annotations.FetchMode.SUBSELECT)
	@JoinColumn(name = "user_id")
	private List<UserAccounts> userAccounts = new ArrayList<UserAccounts>();

	@Column(name = "back_fill_of")
	private String backFillOf;

	@Column(name = "doj")
	private Date doj;

	@Column(name = "release_date")
	private Date releseDate;

	@Column(name = "date_of_billing")
	private Date dateOfBilling;

	@Column(name = "resign_date")
	private Date resignDate;

	@Column(name = "location")
	private String locationName;

	@Column(name = "phone")
	private String phone;

	@Column(name = "mobile")
	private String mobile;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "stream")
	private Streams stream;

	@Column(name = "certificates")
	private String certifications;

	@Column(name = "personal_no")
	private String personalNo;

	@Column(name = "allocation")
	private String allocation;

	@Column(name = "eriproj_no")
	private String eriProjNo;

	@Column(name = "job_stage")
	private Integer jobStage;

	@Column(name = "job_description")
	private String jobDescription;

	@Column(name = "VZID")
	private String vzid;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "location_id")
	private Location location;

	@OneToMany(fetch = FetchType.EAGER )
	@JoinColumn(name = "user_id")
	private List<UserStableTeams> userStableTeams;

	@Column(name = "timesheet_flag")
	private String timesheetFlag;

	@Column(name = "mhr_category")
	private String mhrCategory;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Person getPersonalInfo() {
		return personalInfo;
	}

	public void setPersonalInfo(Person personalInfo) {
		this.personalInfo = personalInfo;
	}

	public Credential getCredentials() {
		return credentials;
	}

	public void setCredentials(Credential credentials) {
		this.credentials = credentials;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getLastLogin() {
		return lastLogin;
	}

	public void setLastLogin(Date lastLogin) {
		this.lastLogin = lastLogin;
	}

	public UserRole getUserRole() {
		return userRole;
	}

	public void setUserRole(UserRole userRole) {
		this.userRole = userRole;
	}

	public UserSupervisor getUserSupervisor() {
		return userSupervisor;
	}

	public void setUserSupervisor(UserSupervisor userSupervisor) {
		this.userSupervisor = userSupervisor;
	}

	public List<UserSkills> getUserSkills() {
		return userSkills;
	}

	public void setUserSkills(List<UserSkills> userSkills) {
		this.userSkills = userSkills;
	}

	public List<UserNetworkCodes> getUserNetworkCodes() {
		return userNetworkCodes;
	}

	public void setUserNetworkCodes(List<UserNetworkCodes> userNetworkCodes) {
		this.userNetworkCodes = userNetworkCodes;
	}

	public String getBackFillOf() {
		return backFillOf;
	}

	public void setBackFillOf(String backFillOf) {
		this.backFillOf = backFillOf;
	}

	public Date getDoj() {
		return doj;
	}

	public void setDoj(Date doj) {
		this.doj = doj;
	}

	public Date getReleseDate() {
		return releseDate;
	}

	public void setReleseDate(Date releseDate) {
		this.releseDate = releseDate;
	}

	public Date getDateOfBilling() {
		return dateOfBilling;
	}

	public void setDateOfBilling(Date dateOfBilling) {
		this.dateOfBilling = dateOfBilling;
	}

	public Date getResignDate() {
		return resignDate;
	}

	public void setResignDate(Date resignDate) {
		this.resignDate = resignDate;
	}

	public String getLocationName() {
		return locationName;
	}

	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Streams getStream() {
		return stream;
	}

	public void setStream(Streams stream) {
		this.stream = stream;
	}

	public String getCertifications() {
		return certifications;
	}

	public void setCertifications(String certifications) {
		this.certifications = certifications;
	}

	public List<UserPlatforms> getUserPlatforms() {
		return userPlatforms;
	}

	public void setUserPlatforms(List<UserPlatforms> userPlatforms) {
		this.userPlatforms = userPlatforms;
	}

	public List<UserProjects> getUserProjects() {
		return userProjects;
	}

	public void setUserProjects(List<UserProjects> userProjects) {
		this.userProjects = userProjects;
	}

	public String getPersonalNo() {
		return personalNo;
	}

	public void setPersonalNo(String personalNo) {
		this.personalNo = personalNo;
	}

	public String getAllocation() {
		return allocation;
	}

	public void setAllocation(String allocation) {
		this.allocation = allocation;
	}

	public String getEriProjNo() {
		return eriProjNo;
	}

	public void setEriProjNo(String eriProjNo) {
		this.eriProjNo = eriProjNo;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getReleaseComments() {
		return releaseComments;
	}

	public void setReleaseComments(String releaseComments) {
		this.releaseComments = releaseComments;
	}

	public Integer getJobStage() {
		return jobStage;
	}

	public void setJobStage(Integer jobStage) {
		this.jobStage = jobStage;
	}

	public String getJobDescription() {
		return jobDescription;
	}

	public void setJobDescription(String jobDescription) {
		this.jobDescription = jobDescription;
	}

	public String getVzid() {
		return vzid;
	}

	public void setVzid(String vzid) {
		this.vzid = vzid;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public String getTimesheetFlag() {
		return timesheetFlag;
	}

	public void setTimesheetFlag(String timesheetFlag) {
		this.timesheetFlag = timesheetFlag;
	}

	public String getMhrCategory() {
		return mhrCategory;
	}

	public void setMhrCategory(String mhrCategory) {
		this.mhrCategory = mhrCategory;
	}

	public List<UserAccounts> getUserAccounts() {
		return userAccounts;
	}

	public void setUserAccounts(List<UserAccounts> userAccounts) {
		this.userAccounts = userAccounts;
	}

	public List<UserStableTeams> getUserStableTeams() {
		return userStableTeams;
	}

	public void setUserStableTeams(List<UserStableTeams> userStableTeams) {
		this.userStableTeams = userStableTeams;
	}
 

}
