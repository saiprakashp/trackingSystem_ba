package com.egil.pts.dao.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "PTS_USER_CAPACITY_PLANNING")
@org.hibernate.annotations.Table(appliesTo = "PTS_USER_CAPACITY_PLANNING")
public class CapacityPlanning extends TransactionInfo {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id", nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id")
	private User user;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "NETWORK_CODE_ID")
	private NetworkCodes networkCode;

	@JoinColumn(name = "YEAR")
	private Long year;

	@Column(name = "JAN_CPTY")
	private Float janCapacity;

	@Column(name = "FEB_CPTY")
	private Float febCapacity;

	@Column(name = "MAR_CPTY")
	private Float marCapacity;

	@Column(name = "APR_CPTY")
	private Float aprCapacity;

	@Column(name = "MAY_CPTY")
	private Float mayCapacity;

	@Column(name = "JUN_CPTY")
	private Float junCapacity;

	@Column(name = "JUL_CPTY")
	private Float julCapacity;

	@Column(name = "AUG_CPTY")
	private Float augCapacity;

	@Column(name = "SEP_CPTY")
	private Float sepCapacity;

	@Column(name = "OCT_CPTY")
	private Float octCapacity;

	@Column(name = "NOV_CPTY")
	private Float novCapacity;

	@Column(name = "DEC_CPTY")
	private Float decCapacity;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public NetworkCodes getNetworkCode() {
		return networkCode;
	}

	public void setNetworkCode(NetworkCodes networkCode) {
		this.networkCode = networkCode;
	}

	public Long getYear() {
		return year;
	}

	public void setYear(Long year) {
		this.year = year;
	}

	public Float getJanCapacity() {
		return janCapacity;
	}

	public void setJanCapacity(Float janCapacity) {
		this.janCapacity = janCapacity;
	}

	public Float getFebCapacity() {
		return febCapacity;
	}

	public void setFebCapacity(Float febCapacity) {
		this.febCapacity = febCapacity;
	}

	public Float getMarCapacity() {
		return marCapacity;
	}

	public void setMarCapacity(Float marCapacity) {
		this.marCapacity = marCapacity;
	}

	public Float getAprCapacity() {
		return aprCapacity;
	}

	public void setAprCapacity(Float aprCapacity) {
		this.aprCapacity = aprCapacity;
	}

	public Float getMayCapacity() {
		return mayCapacity;
	}

	public void setMayCapacity(Float mayCapacity) {
		this.mayCapacity = mayCapacity;
	}

	public Float getJunCapacity() {
		return junCapacity;
	}

	public void setJunCapacity(Float junCapacity) {
		this.junCapacity = junCapacity;
	}

	public Float getJulCapacity() {
		return julCapacity;
	}

	public void setJulCapacity(Float julCapacity) {
		this.julCapacity = julCapacity;
	}

	public Float getAugCapacity() {
		return augCapacity;
	}

	public void setAugCapacity(Float augCapacity) {
		this.augCapacity = augCapacity;
	}

	public Float getSepCapacity() {
		return sepCapacity;
	}

	public void setSepCapacity(Float sepCapacity) {
		this.sepCapacity = sepCapacity;
	}

	public Float getOctCapacity() {
		return octCapacity;
	}

	public void setOctCapacity(Float octCapacity) {
		this.octCapacity = octCapacity;
	}

	public Float getNovCapacity() {
		return novCapacity;
	}

	public void setNovCapacity(Float novCapacity) {
		this.novCapacity = novCapacity;
	}

	public Float getDecCapacity() {
		return decCapacity;
	}

	public void setDecCapacity(Float decCapacity) {
		this.decCapacity = decCapacity;
	}

}
