package com.egil.pts.dao.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "PTS_WORKING_DAYS")
@org.hibernate.annotations.Table(appliesTo = "PTS_WORKING_DAYS")
public class PTSWorkingDays {

	@Id
	@Column(name = "id", nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@JoinColumn(name = "YEAR")
	private Long year;

	@Column(name = "JAN_WORKING_DAYS")
	private Float janWorkingDays;

	@Column(name = "FEB_WORKING_DAYS")
	private Float febWorkingDays;

	@Column(name = "MAR_WORKING_DAYS")
	private Float marWorkingDays;

	@Column(name = "APR_WORKING_DAYS")
	private Float aprWorkingDays;

	@Column(name = "MAY_WORKING_DAYS")
	private Float mayWorkingDays;

	@Column(name = "JUN_WORKING_DAYS")
	private Float junWorkingDays;

	@Column(name = "JUL_WORKING_DAYS")
	private Float julWorkingDays;

	@Column(name = "AUG_WORKING_DAYS")
	private Float augWorkingDays;

	@Column(name = "SEP_WORKING_DAYS")
	private Float sepWorkingDays;

	@Column(name = "OCT_WORKING_DAYS")
	private Float octWorkingDays;

	@Column(name = "NOV_WORKING_DAYS")
	private Float novWorkingDays;

	@Column(name = "DEC_WORKING_DAYS")
	private Float decWorkingDays;
	
	@Column(name = "HRS_PER_DAY")
	private Float hrsPerDay;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getYear() {
		return year;
	}

	public void setYear(Long year) {
		this.year = year;
	}

	public Float getJanWorkingDays() {
		return janWorkingDays;
	}

	public void setJanWorkingDays(Float janWorkingDays) {
		this.janWorkingDays = janWorkingDays;
	}

	public Float getFebWorkingDays() {
		return febWorkingDays;
	}

	public void setFebWorkingDays(Float febWorkingDays) {
		this.febWorkingDays = febWorkingDays;
	}

	public Float getMarWorkingDays() {
		return marWorkingDays;
	}

	public void setMarWorkingDays(Float marWorkingDays) {
		this.marWorkingDays = marWorkingDays;
	}

	public Float getAprWorkingDays() {
		return aprWorkingDays;
	}

	public void setAprWorkingDays(Float aprWorkingDays) {
		this.aprWorkingDays = aprWorkingDays;
	}

	public Float getMayWorkingDays() {
		return mayWorkingDays;
	}

	public void setMayWorkingDays(Float mayWorkingDays) {
		this.mayWorkingDays = mayWorkingDays;
	}

	public Float getJunWorkingDays() {
		return junWorkingDays;
	}

	public void setJunWorkingDays(Float junWorkingDays) {
		this.junWorkingDays = junWorkingDays;
	}

	public Float getJulWorkingDays() {
		return julWorkingDays;
	}

	public void setJulWorkingDays(Float julWorkingDays) {
		this.julWorkingDays = julWorkingDays;
	}

	public Float getAugWorkingDays() {
		return augWorkingDays;
	}

	public void setAugWorkingDays(Float augWorkingDays) {
		this.augWorkingDays = augWorkingDays;
	}

	public Float getSepWorkingDays() {
		return sepWorkingDays;
	}

	public void setSepWorkingDays(Float sepWorkingDays) {
		this.sepWorkingDays = sepWorkingDays;
	}

	public Float getOctWorkingDays() {
		return octWorkingDays;
	}

	public void setOctWorkingDays(Float octWorkingDays) {
		this.octWorkingDays = octWorkingDays;
	}

	public Float getNovWorkingDays() {
		return novWorkingDays;
	}

	public void setNovWorkingDays(Float novWorkingDays) {
		this.novWorkingDays = novWorkingDays;
	}

	public Float getDecWorkingDays() {
		return decWorkingDays;
	}

	public void setDecWorkingDays(Float decWorkingDays) {
		this.decWorkingDays = decWorkingDays;
	}

	public Float getHrsPerDay() {
		return hrsPerDay;
	}

	public void setHrsPerDay(Float hrsPerDay) {
		this.hrsPerDay = hrsPerDay;
	}

}
