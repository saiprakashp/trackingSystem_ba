package com.egil.pts.modal;

import java.io.Serializable;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

public class NetworkCodeEffort implements Serializable {

	private static final long serialVersionUID = 1L;
	private String id;
	private Long USERID;
	private String USERNAME;
	private Long NETWORKCODEID;
	private String NETWORKCODE;
	private Long ACTIVITYCODEID;
	private String ACTIVITYCODE;
	private Float SUMMATION;
	private Float TOTALSUMMATION;
	private Float EFFORT;
	private Float EFFORTPERCENTAGE;
	private String WEEK;
	private Map<String, Long> effortUtilizationPerWeekMap = new LinkedHashMap<String, Long>();
	private String SUPERVISOR;
	private String RELEASE;
	private String MONTH;
	private String USER;
	private String YEAR;
	private Float TEAMEFFORT;
	private Float totalTimSheet;
	private Float totalUserTimSheet;
	private Float contribution;
	private Date WEEKENDING_DATE;
	private Long localDevLoe;
	private Long localTestLoe;
	private Long globalDevLoe;
	private Long globalTestLoe;
	private Long TOTALLOE;

	public Long getLocalDevLoe() {
		return localDevLoe;
	}

	public void setLocalDevLoe(Long localDevLoe) {
		this.localDevLoe = localDevLoe;
	}

	public Long getLocalTestLoe() {
		return localTestLoe;
	}

	public void setLocalTestLoe(Long localTestLoe) {
		this.localTestLoe = localTestLoe;
	}

	public Long getGlobalDevLoe() {
		return globalDevLoe;
	}

	public void setGlobalDevLoe(Long globalDevLoe) {
		this.globalDevLoe = globalDevLoe;
	}

	public Long getGlobalTestLoe() {
		return globalTestLoe;
	}

	public void setGlobalTestLoe(Long globalTestLoe) {
		this.globalTestLoe = globalTestLoe;
	}

	public void setEffortUtilizationPerWeekMap(Map<String, Long> effortUtilizationPerWeekMap) {
		this.effortUtilizationPerWeekMap = effortUtilizationPerWeekMap;
	}

	public Long getNETWORKCODEID() {
		return NETWORKCODEID;
	}

	public void setNETWORKCODEID(Long nETWORKCODEID) {
		NETWORKCODEID = nETWORKCODEID;
	}

	public String getNETWORKCODE() {
		return NETWORKCODE;
	}

	public void setNETWORKCODE(String nETWORKCODE) {
		NETWORKCODE = nETWORKCODE;
	}

	public Float getSUMMATION() {
		return SUMMATION;
	}

	public void setSUMMATION(Float sUMMATION) {
		SUMMATION = sUMMATION;
	}

	public Float getEFFORT() {
		return EFFORT;
	}

	public void setEFFORT(Float eFFORT) {
		EFFORT = eFFORT;
	}

	public String getWEEK() {
		return WEEK;
	}

	public void setWEEK(String wEEK) {
		WEEK = wEEK;
	}

	public Map<String, Long> getEffortUtilizationPerWeekMap() {
		return effortUtilizationPerWeekMap;
	}

	public void addeffortUtilizationPerWeek(String weekDay, Long effort) {
		effortUtilizationPerWeekMap.put(weekDay, effort);
	}

	public Long getUSERID() {
		return USERID;
	}

	public void setUSERID(Long uSERID) {
		USERID = uSERID;
	}

	public String getUSERNAME() {
		return USERNAME;
	}

	public void setUSERNAME(String uSERNAME) {
		USERNAME = uSERNAME;
	}

	public Float getEFFORTPERCENTAGE() {
		return EFFORTPERCENTAGE;
	}

	public void setEFFORTPERCENTAGE(Float eFFORTPERCENTAGE) {
		EFFORTPERCENTAGE = eFFORTPERCENTAGE;
	}

	public Long getACTIVITYCODEID() {
		return ACTIVITYCODEID;
	}

	public void setACTIVITYCODEID(Long aCTIVITYCODEID) {
		ACTIVITYCODEID = aCTIVITYCODEID;
	}

	public String getACTIVITYCODE() {
		return ACTIVITYCODE;
	}

	public void setACTIVITYCODE(String aCTIVITYCODE) {
		ACTIVITYCODE = aCTIVITYCODE;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSUPERVISOR() {
		return SUPERVISOR;
	}

	public void setSUPERVISOR(String sUPERVISOR) {
		SUPERVISOR = sUPERVISOR;
	}

	public Float getTEAMEFFORT() {
		return TEAMEFFORT;
	}

	public void setTEAMEFFORT(Float tEAMEFFORT) {
		TEAMEFFORT = tEAMEFFORT;
	}

	@Override
	public String toString() {
		return "NetworkCodeEffort [id=" + id + ", USERID=" + USERID + ", USERNAME=" + USERNAME + ", NETWORKCODEID="
				+ NETWORKCODEID + ", NETWORKCODE=" + NETWORKCODE + ", ACTIVITYCODEID=" + ACTIVITYCODEID
				+ ", ACTIVITYCODE=" + ACTIVITYCODE + ", SUMMATION=" + SUMMATION + ", EFFORT=" + EFFORT
				+ ", EFFORTPERCENTAGE=" + EFFORTPERCENTAGE + ", WEEK=" + WEEK + ", effortUtilizationPerWeekMap="
				+ effortUtilizationPerWeekMap + ", SUPERVISOR=" + SUPERVISOR + ", TEAMEFFORT=" + TEAMEFFORT + "]";
	}

	public Float getTotalTimSheet() {
		return totalTimSheet;
	}

	public void setTotalTimSheet(Float totalTimSheet) {
		this.totalTimSheet = totalTimSheet;
	}

	public Float getTotalUserTimSheet() {
		return totalUserTimSheet;
	}

	public void setTotalUserTimSheet(Float totalUserTimSheet) {
		this.totalUserTimSheet = totalUserTimSheet;
	}

	public String getMONTH() {
		return MONTH;
	}

	public void setMONTH(String mONTH) {
		MONTH = mONTH;
	}

	public String getYEAR() {
		return YEAR;
	}

	public void setYEAR(String yEAR) {
		YEAR = yEAR;
	}

	public String getRELEASE() {
		return RELEASE;
	}

	public void setRELEASE(String rELEASE) {
		RELEASE = rELEASE;
	}

	public Date getWEEKENDING_DATE() {
		return WEEKENDING_DATE;
	}

	public void setWEEKENDING_DATE(Date wEEKENDING_DATE) {
		WEEKENDING_DATE = wEEKENDING_DATE;
	}

	public String getUSER() {
		return USER;
	}

	public void setUSER(String uSER) {
		USER = uSER;
	}

	public Float getContribution() {
		return contribution;
	}

	public void setContribution(Float contribution) {
		this.contribution = contribution;
	}

	public Float getTOTALSUMMATION() {
		return TOTALSUMMATION;
	}

	public void setTOTALSUMMATION(Float tOTALSUMMATION) {
		TOTALSUMMATION = tOTALSUMMATION;
	}

	public Long getTOTALLOE() {
		return TOTALLOE;
	}

	public void setTOTALLOE(Long tOTALLOE) {
		TOTALLOE = tOTALLOE;
	}

}
