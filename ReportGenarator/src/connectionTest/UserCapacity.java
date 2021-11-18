/*     */ package connectionTest;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.util.Date;
/*     */ 
/*     */ public class UserCapacity
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   private Long USERID;
/*     */   private String USERNAME;
/*     */   private Long NETWORKCODEID;
/*     */   private String NETWORKCODE;
/*     */   private Long ACTIVITYCODEID;
/*     */   private String ACTIVITYCODE;
/*     */   private Float CHARGEDHRS;
/*     */   private String MONTH;
/*     */   private String YEAR;
/*     */   private Long id;
/*     */   private Long TFSEpic;
/*     */   private Double SUMMATION;
/*     */   private Double TOTALCAPACITY;
/*     */   private String RELEASETYPE;
/*     */   private String STATUS;
/*     */   private String SUPERVISORNAME;
/*     */   private String PROJECT;
/*     */   private Date IMPLEMENTATIONDATE;
/*     */   
/*     */   public Long getNETWORKCODEID() {
/*  30 */     return this.NETWORKCODEID;
/*     */   }
/*     */   
/*     */   public void setNETWORKCODEID(Long nETWORKCODEID) {
/*  34 */     this.NETWORKCODEID = nETWORKCODEID;
/*     */   }
/*     */   
/*     */   public String getNETWORKCODE() {
/*  38 */     return this.NETWORKCODE;
/*     */   }
/*     */   
/*     */   public void setNETWORKCODE(String nETWORKCODE) {
/*  42 */     this.NETWORKCODE = nETWORKCODE;
/*     */   }
/*     */   
/*     */   public Long getUSERID() {
/*  46 */     return this.USERID;
/*     */   }
/*     */   
/*     */   public void setUSERID(Long uSERID) {
/*  50 */     this.USERID = uSERID;
/*     */   }
/*     */   
/*     */   public String getUSERNAME() {
/*  54 */     return this.USERNAME;
/*     */   }
/*     */   
/*     */   public void setUSERNAME(String uSERNAME) {
/*  58 */     this.USERNAME = uSERNAME;
/*     */   }
/*     */   
/*     */   public Long getACTIVITYCODEID() {
/*  62 */     return this.ACTIVITYCODEID;
/*     */   }
/*     */   
/*     */   public void setACTIVITYCODEID(Long aCTIVITYCODEID) {
/*  66 */     this.ACTIVITYCODEID = aCTIVITYCODEID;
/*     */   }
/*     */   
/*     */   public String getACTIVITYCODE() {
/*  70 */     return this.ACTIVITYCODE;
/*     */   }
/*     */   
/*     */   public void setACTIVITYCODE(String aCTIVITYCODE) {
/*  74 */     this.ACTIVITYCODE = aCTIVITYCODE;
/*     */   }
/*     */   
/*     */   public Float getCHARGEDHRS() {
/*  78 */     return this.CHARGEDHRS;
/*     */   }
/*     */   
/*     */   public void setCHARGEDHRS(Float cHARGEDHRS) {
/*  82 */     this.CHARGEDHRS = cHARGEDHRS;
/*     */   }
/*     */   
/*     */   public String getMONTH() {
/*  86 */     return this.MONTH;
/*     */   }
/*     */   
/*     */   public void setMONTH(String mONTH) {
/*  90 */     this.MONTH = mONTH;
/*     */   }
/*     */   
/*     */   public String getYEAR() {
/*  94 */     return this.YEAR;
/*     */   }
/*     */   
/*     */   public void setYEAR(String yEAR) {
/*  98 */     this.YEAR = yEAR;
/*     */   }
/*     */   
/*     */   public Long getTFSEpic() {
/* 102 */     return this.TFSEpic;
/*     */   }
/*     */   
/*     */   public void setTFSEpic(Long tFSEpic) {
/* 106 */     this.TFSEpic = tFSEpic;
/*     */   }
/*     */   
/*     */   public Long getId() {
/* 110 */     return this.id;
/*     */   }
/*     */   
/*     */   public void setId(Long id) {
/* 114 */     this.id = id;
/*     */   }
/*     */   
/*     */   public Double getSUMMATION() {
/* 118 */     return this.SUMMATION;
/*     */   }
/*     */   
/*     */   public void setSUMMATION(Double sUMMATION) {
/* 122 */     this.SUMMATION = sUMMATION;
/*     */   }
/*     */   
/*     */   public Double getTOTALCAPACITY() {
/* 126 */     return this.TOTALCAPACITY;
/*     */   }
/*     */   
/*     */   public void setTOTALCAPACITY(Double tOTALCAPACITY) {
/* 130 */     this.TOTALCAPACITY = tOTALCAPACITY;
/*     */   }
/*     */   
/*     */   public String getRELEASETYPE() {
/* 134 */     return this.RELEASETYPE;
/*     */   }
/*     */   
/*     */   public void setRELEASETYPE(String rELEASETYPE) {
/* 138 */     this.RELEASETYPE = rELEASETYPE;
/*     */   }
/*     */   
/*     */   public Date getIMPLEMENTATIONDATE() {
/* 142 */     return this.IMPLEMENTATIONDATE;
/*     */   }
/*     */   
/*     */   public void setIMPLEMENTATIONDATE(Date iMPLEMENTATIONDATE) {
/* 146 */     this.IMPLEMENTATIONDATE = iMPLEMENTATIONDATE;
/*     */   }
/*     */   
/*     */   public String getSUPERVISORNAME() {
/* 150 */     return this.SUPERVISORNAME;
/*     */   }
/*     */   
/*     */   public void setSUPERVISORNAME(String sUPERVISORNAME) {
/* 154 */     this.SUPERVISORNAME = sUPERVISORNAME;
/*     */   }
/*     */   
/*     */   public String getSTATUS() {
/* 158 */     return this.STATUS;
/*     */   }
/*     */   
/*     */   public void setSTATUS(String sTATUS) {
/* 162 */     this.STATUS = sTATUS;
/*     */   }
/*     */   
/*     */   public String getPROJECT() {
/* 166 */     return this.PROJECT;
/*     */   }
/*     */   
/*     */   public void setPROJECT(String pROJECT) {
/* 170 */     this.PROJECT = pROJECT;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 175 */     return "UserCapacity [USERID=" + this.USERID + ", USERNAME=" + this.USERNAME + ", NETWORKCODEID=" + this.NETWORKCODEID + 
/* 176 */       ", NETWORKCODE=" + this.NETWORKCODE + ", ACTIVITYCODEID=" + this.ACTIVITYCODEID + ", ACTIVITYCODE=" + 
/* 177 */       this.ACTIVITYCODE + ", CHARGEDHRS=" + this.CHARGEDHRS + ", MONTH=" + this.MONTH + ", YEAR=" + this.YEAR + ", id=" + this.id + 
/* 178 */       ", TFSEpic=" + this.TFSEpic + ", SUMMATION=" + this.SUMMATION + ", TOTALCAPACITY=" + this.TOTALCAPACITY + 
/* 179 */       ", RELEASETYPE=" + this.RELEASETYPE + ", STATUS=" + this.STATUS + ", SUPERVISORNAME=" + this.SUPERVISORNAME + 
/* 180 */       ", PROJECT=" + this.PROJECT + ", IMPLEMENTATIONDATE=" + this.IMPLEMENTATIONDATE + "]";
/*     */   }
/*     */ }


/* Location:              C:\SAI\Workspace\rico_pts\rico_pts_current\reportGenerator\Reports.jar!\connectionTest\UserCapacity.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */