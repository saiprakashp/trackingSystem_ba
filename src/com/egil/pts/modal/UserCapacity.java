package com.egil.pts.modal;

import java.io.Serializable;
import java.util.Date;

public class UserCapacity implements Serializable {

	private static final long serialVersionUID = 1L;
	private Long USERID;
	private String USERNAME;
	private Long NETWORKCODEID;
	private String NETWORKCODE;
	private Long ACTIVITYCODEID;
	private String ACTIVITYCODE;
	private Float CHARGEDHRS;
	private String MONTH;
	private String YEAR;

	private Long id;
	private Long TFSEpic;
	private Double SUMMATION;
	private Double TOTALCAPACITY;
	private String RELEASETYPE;
	private String STATUS;
	private String SUPERVISORNAME;
	private String PROJECT;
	private Date IMPLEMENTATIONDATE;

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

	public Float getCHARGEDHRS() {
		return CHARGEDHRS;
	}

	public void setCHARGEDHRS(Float cHARGEDHRS) {
		CHARGEDHRS = cHARGEDHRS;
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

	public Long getTFSEpic() {
		return TFSEpic;
	}

	public void setTFSEpic(Long tFSEpic) {
		TFSEpic = tFSEpic;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Double getSUMMATION() {
		return SUMMATION;
	}

	public void setSUMMATION(Double sUMMATION) {
		SUMMATION = sUMMATION;
	}

	public Double getTOTALCAPACITY() {
		return TOTALCAPACITY;
	}

	public void setTOTALCAPACITY(Double tOTALCAPACITY) {
		TOTALCAPACITY = tOTALCAPACITY;
	}

	public String getRELEASETYPE() {
		return RELEASETYPE;
	}

	public void setRELEASETYPE(String rELEASETYPE) {
		RELEASETYPE = rELEASETYPE;
	}

	public Date getIMPLEMENTATIONDATE() {
		return IMPLEMENTATIONDATE;
	}

	public void setIMPLEMENTATIONDATE(Date iMPLEMENTATIONDATE) {
		IMPLEMENTATIONDATE = iMPLEMENTATIONDATE;
	}

	public String getSUPERVISORNAME() {
		return SUPERVISORNAME;
	}

	public void setSUPERVISORNAME(String sUPERVISORNAME) {
		SUPERVISORNAME = sUPERVISORNAME;
	}

	public String getSTATUS() {
		return STATUS;
	}

	public void setSTATUS(String sTATUS) {
		STATUS = sTATUS;
	}

	public String getPROJECT() {
		return PROJECT;
	}

	public void setPROJECT(String pROJECT) {
		PROJECT = pROJECT;
	}

	@Override
	public String toString() {
		return "UserCapacity [USERID=" + USERID + ", USERNAME=" + USERNAME + ", NETWORKCODEID=" + NETWORKCODEID
				+ ", NETWORKCODE=" + NETWORKCODE + ", ACTIVITYCODEID=" + ACTIVITYCODEID + ", ACTIVITYCODE="
				+ ACTIVITYCODE + ", CHARGEDHRS=" + CHARGEDHRS + ", MONTH=" + MONTH + ", YEAR=" + YEAR + ", id=" + id
				+ ", TFSEpic=" + TFSEpic + ", SUMMATION=" + SUMMATION + ", TOTALCAPACITY=" + TOTALCAPACITY
				+ ", RELEASETYPE=" + RELEASETYPE + ", STATUS=" + STATUS + ", SUPERVISORNAME=" + SUPERVISORNAME
				+ ", PROJECT=" + PROJECT + ", IMPLEMENTATIONDATE=" + IMPLEMENTATIONDATE + "]";
	}

}
