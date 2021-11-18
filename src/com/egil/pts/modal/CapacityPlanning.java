package com.egil.pts.modal;

import java.util.Date;

public class CapacityPlanning extends TransactionInfo {

	private static final long serialVersionUID = 1L;

	private Long id;

	private Long userId;

	private Long networkCodeId;

	private Long year;

	private Float janCapacity;

	private Float febCapacity;

	private Float marCapacity;

	private Float aprCapacity;

	private Float mayCapacity;

	private Float junCapacity;

	private Float julCapacity;

	private Float augCapacity;

	private Float sepCapacity;

	private Float octCapacity;

	private Float novCapacity;

	private Float decCapacity;

	private String networkCode;

	private String appName;

	private String userName;

	private String supervisorName;

	private Long headCount;

	private Double targetHrs;

	private String primarySkill;

	private String projectType;

	private Long devLoe;

	private Long testLoe;

	private Date createdDate;

	private String createdBy;

	private Long totalLoe;

	private long pmProjectLoe;

	private String status;

	private Long globalDevLoe;

	private Long globalTestLoe;

	private Long localDevLoe;

	private Long localTestLoe;

	private Long originalDevLoe;

	private Long originalTestLoe;

	private Float totalNWCapacity;

	private Float totalTimeSheetCap;

	private Float totalManagerCapacity;

	private Long originalDesLOE;

	private Long originalPmLoe;

	private Long originalImplLoe;

	private Float totalOriginalLoe;

	private String capUserId;

	private Float chargedHrs;

	private Double remainingHrs;

	private Float totCharg;
	private Float totLoe;
	private Float totCap;
	private Float totDevLoe;
	private Float totTestLoe;
	private Float totPtlLoe;
	private Float totSeLoe;
	private Float totPmLoe;

	public Float getTotCap() {
		return totCap;
	}

	public void setTotCap(Float totCap) {
		this.totCap = totCap;
	}

	public Float getTotDevLoe() {
		return totDevLoe;
	}

	public void setTotDevLoe(Float totDevLoe) {
		this.totDevLoe = totDevLoe;
	}

	public Float getTotTestLoe() {
		return totTestLoe;
	}

	public void setTotTestLoe(Float totTestLoe) {
		this.totTestLoe = totTestLoe;
	}

	public Float getTotPtlLoe() {
		return totPtlLoe;
	}

	public void setTotPtlLoe(Float totPtlLoe) {
		this.totPtlLoe = totPtlLoe;
	}

	public Float getTotSeLoe() {
		return totSeLoe;
	}

	public void setTotSeLoe(Float totSeLoe) {
		this.totSeLoe = totSeLoe;
	}

	public Float getTotPmLoe() {
		return totPmLoe;
	}

	public void setTotPmLoe(Float totPmLoe) {
		this.totPmLoe = totPmLoe;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getNetworkCodeId() {
		return networkCodeId;
	}

	public void setNetworkCodeId(Long networkCodeId) {
		this.networkCodeId = networkCodeId;
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

	public String getNetworkCode() {
		return networkCode;
	}

	public void setNetworkCode(String networkCode) {
		this.networkCode = networkCode;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getSupervisorName() {
		return supervisorName;
	}

	public void setSupervisorName(String supervisorName) {
		this.supervisorName = supervisorName;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public Long getHeadCount() {
		return headCount;
	}

	public void setHeadCount(Long headCount) {
		this.headCount = headCount;
	}

	public Double getTargetHrs() {
		return targetHrs == null ? 0.0d : targetHrs;
	}

	public void setTargetHrs(Double targetHrs) {
		this.targetHrs = targetHrs;
	}

	public String getPrimarySkill() {
		return primarySkill;
	}

	public void setPrimarySkill(String primarySkill) {
		this.primarySkill = primarySkill;
	}

	public String getProjectType() {
		return projectType;
	}

	public void setProjectType(String projectType) {
		this.projectType = projectType;
	}

	public Long getDevLoe() {
		return devLoe;
	}

	public void setDevLoe(Long devLoe) {
		this.devLoe = devLoe;
	}

	public Long getTestLoe() {
		return testLoe;
	}

	public void setTestLoe(Long testLoe) {
		this.testLoe = testLoe;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Long getTotalLoe() {
		return totalLoe;
	}

	public void setTotalLoe(Long totalLoe) {
		this.totalLoe = totalLoe;
	}

	public long getPmProjectLoe() {
		return pmProjectLoe;
	}

	public void setPmProjectLoe(long pmProjectLoe) {
		this.pmProjectLoe = pmProjectLoe;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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

	public Long getOriginalDevLoe() {
		return originalDevLoe;
	}

	public void setOriginalDevLoe(Long originalDevLoe) {
		this.originalDevLoe = originalDevLoe;
	}

	public Long getOriginalTestLoe() {
		return originalTestLoe;
	}

	public void setOriginalTestLoe(Long originalTestLoe) {
		this.originalTestLoe = originalTestLoe;
	}

	public Float getTotalNWCapacity() {
		return totalNWCapacity;
	}

	public void setTotalNWCapacity(Float totalNWCapacity) {
		this.totalNWCapacity = totalNWCapacity;
	}

	public Float getTotalTimeSheetCap() {
		return totalTimeSheetCap;
	}

	public void setTotalTimeSheetCap(Float totalTimeSheetCap) {
		this.totalTimeSheetCap = totalTimeSheetCap;
	}

	public Float getTotalManagerCapacity() {
		return totalManagerCapacity;
	}

	public void setTotalManagerCapacity(Float totalManagerCapacity) {
		this.totalManagerCapacity = totalManagerCapacity;
	}

	public Long getOriginalDesLOE() {
		return originalDesLOE;
	}

	public void setOriginalDesLOE(Long originalDesLOE) {
		this.originalDesLOE = originalDesLOE;
	}

	public Long getOriginalPmLoe() {
		return originalPmLoe;
	}

	public void setOriginalPmLoe(Long originalPmLoe) {
		this.originalPmLoe = originalPmLoe;
	}

	public Long getOriginalImplLoe() {
		return originalImplLoe;
	}

	public void setOriginalImplLoe(Long originalImplLoe) {
		this.originalImplLoe = originalImplLoe;
	}

	public Float getTotalOriginalLoe() {
		return totalOriginalLoe;
	}

	public void setTotalOriginalLoe(Float totalOriginalLoe) {
		this.totalOriginalLoe = totalOriginalLoe;
	}

	public String getCapUserId() {
		return capUserId;
	}

	public void setCapUserId(String capUserId) {
		this.capUserId = capUserId;
	}

	public Float getChargedHrs() {
		return chargedHrs == null ? 0.0f : chargedHrs;
	}

	public void setChargedHrs(Float chargedHrs) {
		this.chargedHrs = chargedHrs;
	}

	public Double getRemainingHrs() {
		return ((targetHrs == null ? 0.0d : targetHrs) - (chargedHrs == null ? 0.0f : chargedHrs));
	}

	public void setRemainingHrs(Double remainingHrs) {
		this.remainingHrs = remainingHrs;
	}

	public Float getTotCharg() {
		return totCharg;
	}

	public void setTotCharg(Float totCharg) {
		this.totCharg = totCharg;
	}

	public Float getTotLoe() {
		return totLoe;
	}

	public void setTotLoe(Float totLoe) {
		this.totLoe = totLoe;
	}

}
