package com.egil.pts.dao.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

@Entity
@Table(name = "PTS_USER_NETWORK_CODES")
@org.hibernate.annotations.Table(appliesTo = "PTS_USER_NETWORK_CODES")
public class UserNetworkCodes implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "id", nullable = false)
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
		
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "network_code_id")
	@NotFound(action =   NotFoundAction.IGNORE)
	private NetworkCodes networkCodes;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;
	
/*	@Column(name = "TOT_2019_CPTY")
	private Double tot2019Capacity;
	
	@Column(name = "TOT_2019_CHRGD")
	private Double tot2019ChrgdHrs;
	*/
	@Column(name = "last_managet_assigned")
	private Long lastAssignedManager;
	
	@Column(name = "stable_contribution")
	private Double nwUserContribution;
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public NetworkCodes getNetworkCodes() {
		return networkCodes;
	}

	public void setNetworkCodes(NetworkCodes networkCodes) {
		this.networkCodes = networkCodes;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Long getLastAssignedManager() {
		return lastAssignedManager;
	}

	public void setLastAssignedManager(Long lastAssignedManager) {
		this.lastAssignedManager = lastAssignedManager;
	}

	public Double getNwUserContribution() {
		return nwUserContribution;
	}

	public void setNwUserContribution(Double nwUserContribution) {
		this.nwUserContribution = nwUserContribution;
	}

	/*public Float getTotCapacity() {
		return totCapacity;
	}

	public void setTot2019Capacity(Double tot2019Capacity) {
		this.tot2019Capacity = tot2019Capacity;
	}

	public Double getTot2019ChrgdHrs() {
		return tot2019ChrgdHrs;
	}

	public void setTot2019ChrgdHrs(Double tot2019ChrgdHrs) {
		this.tot2019ChrgdHrs = tot2019ChrgdHrs;
	}
	
*/	
}
