package com.egil.pts.modal;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class NetworkCodes extends TransactionInfo {

	private static final long serialVersionUID = 1L;
	private String networkCodeId;
	private String application;
	private String networkCode;
	private String description;
	private Long effort;
	private Long totalStoryPoints;
	private Long nwId;
	private Long userId;
	private Status status;
	private String nwStatus;
	private String statusTemp;
	private Long customerId;
	private Long pillarId;
	private Long projectId;
	private Long stableTeam;
	private Double pmStableContribution;
	private Double stableContribution;
	private Double nccStableContribution;
	private String stableTeamName;
	private String signum;
	private String name;
	private boolean isForInterns;

	private String customer;
	private String pillar;
	private String project;
	private String weekDate;

	protected Date startDate;
	protected Date endDate;

	private Date plannedDesignDate;
	private Date actualDesignDate;
	private Long order;

	private Date plannedDevDate;
	private Date actualDevDate;

	private Date plannedTestDate;
	private Date actualTestDate;

	private Date plannedImplDate;
	private Date actualImplDate;

	private Date plannedOprHandoffDate;
	private Date actualOprHandoffDate;
	private Double chargedHours;

	private Long globalDesignLOE;
	private Long globalDevLOE;
	private Long globalTestLOE;
	private Long globalImplementationLOE;
	private Long globalOperationsHandOffLOE;
	private Long globalProjectManagementLOE;
	private Long globalOthersLOE;
	private Long totalGlobalLOE;
	private Long totalLocalLOE;
	private Integer editFlag;
	private Long localDesignLOE;
	private Long localDevLOE;
	private Long localTestLOE;
	private Long localImplementationLOE;
	private Long localOperationsHandOffLOE;
	private Long localProjectManagementLOE;
	private Long localOthersLOE;
	private Long totalLOE;
	private String projectCategory;
	private String projectSubCategory;
	private String selectedProjectCategory;

	@SuppressWarnings("unused")
	private String formattedStartDate;
	@SuppressWarnings("unused")
	private String formattedEndDate;

	private Long productOwnerId;
	private String productOwner;

	private String sreqNo;

	private String priority;

	private Long programManagerId;
	private String programManager;

	private Long projectManagerId;
	private String projectManager;

	private String releaseId;
	private String releaseName;
	private String releaseType;

	private String changeControl;
	private String changeControlNo;
	private String changeControlStatus;
	private String changeControlImpact;

	private String rollback;
	private String rollbackNo;
	private String rollbackReason;

	private String scheduleVariance;
	private String rollbackVolume;
	private String devTestEffectiveness;

	private Date scopeCloseDate;
	private Date changeControlDate;
	private Date rollbackDate;

	private Date originalDesignDate;
	private Date originalDevDate;
	private Date originalTestDate;
	private Date originalImplDate;
	private Date originalOprHandoffDate;
	private Date originalWarrantyCompletionDate;

	private Long originalDesignLOE;
	private Long originalDevLOE;
	private Long originalTestLOE;
	private Long originalProjectManagementLOE;
	private Long originalImplementationLOE;
	private Long originalOperationsHandOffLOE;
	private Date plannedWarrantyCompletionDate;

	private String projectStage;
	private String projectType;
	private Long originalKitLOE;
	private Long localKitLOE;
	private Long globalKitLOE;
	private String projectStageCol;
	private String projectLevel;
	private String TFSEpic;
	private String tfStatus;
	private String projectName;

	public String getNetworkCodeId() {
		return networkCodeId;
	}

	public void setNetworkCodeId(String networkCodeId) {
		this.networkCodeId = networkCodeId;
	}

	public String getNetworkCode() {
		return networkCode;
	}

	public void setNetworkCode(String networkCode) {
		this.networkCode = networkCode;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Long getEffort() {
		return effort;
	}

	public void setEffort(Long effort) {
		this.effort = effort;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public String getProject() {
		return project;
	}

	public void setProject(String project) {
		this.project = project;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getFormattedStartDate() {
		DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
		return startDate != null ? df.format(startDate) : "";
	}

	public void setFormattedStartDate(String formattedStartDate) {
		this.formattedStartDate = formattedStartDate;
	}

	public String getFormattedEndDate() {
		DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
		return endDate != null ? df.format(endDate) : "";
	}

	public void setFormattedEndDate(String formattedEndDate) {
		this.formattedEndDate = formattedEndDate;
	}

	public String getCustomer() {
		return customer;
	}

	public void setCustomer(String customer) {
		this.customer = customer;
	}

	public String getPillar() {
		return pillar;
	}

	public void setPillar(String pillar) {
		this.pillar = pillar;
	}

	public Date getPlannedDesignDate() {
		return plannedDesignDate;
	}

	public void setPlannedDesignDate(Date plannedDesignDate) {
		this.plannedDesignDate = plannedDesignDate;
	}

	public Date getActualDesignDate() {
		return actualDesignDate;
	}

	public void setActualDesignDate(Date actualDesignDate) {
		this.actualDesignDate = actualDesignDate;
	}

	public Date getPlannedDevDate() {
		return plannedDevDate;
	}

	public void setPlannedDevDate(Date plannedDevDate) {
		this.plannedDevDate = plannedDevDate;
	}

	public Date getActualDevDate() {
		return actualDevDate;
	}

	public void setActualDevDate(Date actualDevDate) {
		this.actualDevDate = actualDevDate;
	}

	public Date getPlannedTestDate() {
		return plannedTestDate;
	}

	public void setPlannedTestDate(Date plannedTestDate) {
		this.plannedTestDate = plannedTestDate;
	}

	public Date getActualTestDate() {
		return actualTestDate;
	}

	public void setActualTestDate(Date actualTestDate) {
		this.actualTestDate = actualTestDate;
	}

	public Date getPlannedImplDate() {
		return plannedImplDate;
	}

	public void setPlannedImplDate(Date plannedImplDate) {
		this.plannedImplDate = plannedImplDate;
	}

	public Date getActualImplDate() {
		return actualImplDate;
	}

	public void setActualImplDate(Date actualImplDate) {
		this.actualImplDate = actualImplDate;
	}

	public Date getPlannedOprHandoffDate() {
		return plannedOprHandoffDate;
	}

	public void setPlannedOprHandoffDate(Date plannedOprHandoffDate) {
		this.plannedOprHandoffDate = plannedOprHandoffDate;
	}

	public Date getActualOprHandoffDate() {
		return actualOprHandoffDate;
	}

	public void setActualOprHandoffDate(Date actualOprHandoffDate) {
		this.actualOprHandoffDate = actualOprHandoffDate;
	}

	public Long getGlobalDevLOE() {
		return globalDevLOE;
	}

	public void setGlobalDevLOE(Long globalDevLOE) {
		this.globalDevLOE = globalDevLOE;
	}

	public Long getGlobalTestLOE() {
		return globalTestLOE;
	}

	public void setGlobalTestLOE(Long globalTestLOE) {
		this.globalTestLOE = globalTestLOE;
	}

	public Long getGlobalImplementationLOE() {
		return globalImplementationLOE;
	}

	public void setGlobalImplementationLOE(Long globalImplementationLOE) {
		this.globalImplementationLOE = globalImplementationLOE;
	}

	public Long getGlobalOperationsHandOffLOE() {
		return globalOperationsHandOffLOE;
	}

	public void setGlobalOperationsHandOffLOE(Long globalOperationsHandOffLOE) {
		this.globalOperationsHandOffLOE = globalOperationsHandOffLOE;
	}

	public Long getGlobalProjectManagementLOE() {
		return globalProjectManagementLOE;
	}

	public void setGlobalProjectManagementLOE(Long globalProjectManagementLOE) {
		this.globalProjectManagementLOE = globalProjectManagementLOE;
	}

	public Long getGlobalOthersLOE() {
		return globalOthersLOE;
	}

	public void setGlobalOthersLOE(Long globalOthersLOE) {
		this.globalOthersLOE = globalOthersLOE;
	}

	public Long getLocalDevLOE() {
		return localDevLOE;
	}

	public void setLocalDevLOE(Long localDevLOE) {
		this.localDevLOE = localDevLOE;
	}

	public Long getLocalTestLOE() {
		return localTestLOE;
	}

	public void setLocalTestLOE(Long localTestLOE) {
		this.localTestLOE = localTestLOE;
	}

	public Long getLocalImplementationLOE() {
		return localImplementationLOE;
	}

	public void setLocalImplementationLOE(Long localImplementationLOE) {
		this.localImplementationLOE = localImplementationLOE;
	}

	public Long getLocalOperationsHandOffLOE() {
		return localOperationsHandOffLOE;
	}

	public void setLocalOperationsHandOffLOE(Long localOperationsHandOffLOE) {
		this.localOperationsHandOffLOE = localOperationsHandOffLOE;
	}

	public Long getLocalProjectManagementLOE() {
		return localProjectManagementLOE;
	}

	public void setLocalProjectManagementLOE(Long localProjectManagementLOE) {
		this.localProjectManagementLOE = localProjectManagementLOE;
	}

	public Long getLocalOthersLOE() {
		return localOthersLOE;
	}

	public void setLocalOthersLOE(Long localOthersLOE) {
		this.localOthersLOE = localOthersLOE;
	}

	public String getSreqNo() {
		return sreqNo;
	}

	public void setSreqNo(String sreqNo) {
		this.sreqNo = sreqNo;
	}

	public String getProjectCategory() {
		return projectCategory;
	}

	public void setProjectCategory(String projectCategory) {
		this.projectCategory = projectCategory;
	}

	public String getProjectSubCategory() {
		return projectSubCategory;
	}

	public void setProjectSubCategory(String projectSubCategory) {
		this.projectSubCategory = projectSubCategory;
	}

	public String getSelectedProjectCategory() {
		return selectedProjectCategory;
	}

	public void setSelectedProjectCategory(String selectedProjectCategory) {
		this.selectedProjectCategory = selectedProjectCategory;
	}

	public String getProductOwner() {
		return productOwner;
	}

	public void setProductOwner(String productOwner) {
		this.productOwner = productOwner;
	}

	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	public String getProgramManager() {
		return programManager;
	}

	public void setProgramManager(String programManager) {
		this.programManager = programManager;
	}

	public String getProjectManager() {
		return projectManager;
	}

	public void setProjectManager(String projectManager) {
		this.projectManager = projectManager;
	}

	public Long getGlobalDesignLOE() {
		return globalDesignLOE;
	}

	public void setGlobalDesignLOE(Long globalDesignLOE) {
		this.globalDesignLOE = globalDesignLOE;
	}

	public Long getLocalDesignLOE() {
		return localDesignLOE;
	}

	public void setLocalDesignLOE(Long localDesignLOE) {
		this.localDesignLOE = localDesignLOE;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public Long getPillarId() {
		return pillarId;
	}

	public void setPillarId(Long pillarId) {
		this.pillarId = pillarId;
	}

	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

	public Long getProductOwnerId() {
		return productOwnerId;
	}

	public void setProductOwnerId(Long productOwnerId) {
		this.productOwnerId = productOwnerId;
	}

	public Long getProgramManagerId() {
		return programManagerId;
	}

	public void setProgramManagerId(Long programManagerId) {
		this.programManagerId = programManagerId;
	}

	public Long getProjectManagerId() {
		return projectManagerId;
	}

	public void setProjectManagerId(Long projectManagerId) {
		this.projectManagerId = projectManagerId;
	}

	public String getReleaseId() {
		return releaseId;
	}

	public void setReleaseId(String releaseId) {
		this.releaseId = releaseId;
	}

	public String getReleaseName() {
		return releaseName;
	}

	public void setReleaseName(String releaseName) {
		this.releaseName = releaseName;
	}

	public String getReleaseType() {
		return releaseType;
	}

	public void setReleaseType(String releaseType) {
		this.releaseType = releaseType;
	}

	public String getChangeControl() {
		return changeControl;
	}

	public void setChangeControl(String changeControl) {
		this.changeControl = changeControl;
	}

	public String getChangeControlNo() {
		return changeControlNo;
	}

	public void setChangeControlNo(String changeControlNo) {
		this.changeControlNo = changeControlNo;
	}

	public String getChangeControlStatus() {
		return changeControlStatus;
	}

	public void setChangeControlStatus(String changeControlStatus) {
		this.changeControlStatus = changeControlStatus;
	}

	public String getChangeControlImpact() {
		return changeControlImpact;
	}

	public void setChangeControlImpact(String changeControlImpact) {
		this.changeControlImpact = changeControlImpact;
	}

	public String getRollback() {
		return rollback;
	}

	public void setRollback(String rollback) {
		this.rollback = rollback;
	}

	public String getRollbackNo() {
		return rollbackNo;
	}

	public void setRollbackNo(String rollbackNo) {
		this.rollbackNo = rollbackNo;
	}

	public String getRollbackReason() {
		return rollbackReason;
	}

	public void setRollbackReason(String rollbackReason) {
		this.rollbackReason = rollbackReason;
	}

	public Date getScopeCloseDate() {
		return scopeCloseDate;
	}

	public void setScopeCloseDate(Date scopeCloseDate) {
		this.scopeCloseDate = scopeCloseDate;
	}

	public Date getChangeControlDate() {
		return changeControlDate;
	}

	public void setChangeControlDate(Date changeControlDate) {
		this.changeControlDate = changeControlDate;
	}

	public Date getRollbackDate() {
		return rollbackDate;
	}

	public void setRollbackDate(Date rollbackDate) {
		this.rollbackDate = rollbackDate;
	}

	public Date getOriginalDesignDate() {
		return originalDesignDate;
	}

	public void setOriginalDesignDate(Date originalDesignDate) {
		this.originalDesignDate = originalDesignDate;
	}

	public Date getOriginalDevDate() {
		return originalDevDate;
	}

	public void setOriginalDevDate(Date originalDevDate) {
		this.originalDevDate = originalDevDate;
	}

	public Date getOriginalTestDate() {
		return originalTestDate;
	}

	public void setOriginalTestDate(Date originalTestDate) {
		this.originalTestDate = originalTestDate;
	}

	public Date getOriginalImplDate() {
		return originalImplDate;
	}

	public void setOriginalImplDate(Date originalImplDate) {
		this.originalImplDate = originalImplDate;
	}

	public Date getOriginalOprHandoffDate() {
		return originalOprHandoffDate;
	}

	public void setOriginalOprHandoffDate(Date originalOprHandoffDate) {
		this.originalOprHandoffDate = originalOprHandoffDate;
	}

	public Long getOriginalDesignLOE() {
		return originalDesignLOE;
	}

	public void setOriginalDesignLOE(Long originalDesignLOE) {
		this.originalDesignLOE = originalDesignLOE;
	}

	public Long getOriginalDevLOE() {
		return originalDevLOE;
	}

	public void setOriginalDevLOE(Long originalDevLOE) {
		this.originalDevLOE = originalDevLOE;
	}

	public Long getOriginalTestLOE() {
		return originalTestLOE;
	}

	public void setOriginalTestLOE(Long originalTestLOE) {
		this.originalTestLOE = originalTestLOE;
	}

	public Long getOriginalProjectManagementLOE() {
		return originalProjectManagementLOE;
	}

	public void setOriginalProjectManagementLOE(Long originalProjectManagementLOE) {
		this.originalProjectManagementLOE = originalProjectManagementLOE;
	}

	public Long getOriginalImplementationLOE() {
		return originalImplementationLOE;
	}

	public void setOriginalImplementationLOE(Long originalImplementationLOE) {
		this.originalImplementationLOE = originalImplementationLOE;
	}

	public Long getOriginalOperationsHandOffLOE() {
		return originalOperationsHandOffLOE;
	}

	public void setOriginalOperationsHandOffLOE(Long originalOperationsHandOffLOE) {
		this.originalOperationsHandOffLOE = originalOperationsHandOffLOE;
	}

	public Date getOriginalWarrantyCompletionDate() {
		return originalWarrantyCompletionDate;
	}

	public void setOriginalWarrantyCompletionDate(Date originalWarrantyCompletionDate) {
		this.originalWarrantyCompletionDate = originalWarrantyCompletionDate;
	}

	public Date getPlannedWarrantyCompletionDate() {
		return plannedWarrantyCompletionDate;
	}

	public void setPlannedWarrantyCompletionDate(Date plannedWarrantyCompletionDate) {
		this.plannedWarrantyCompletionDate = plannedWarrantyCompletionDate;
	}

	public String getScheduleVariance() {
		return scheduleVariance;
	}

	public void setScheduleVariance(String scheduleVariance) {
		this.scheduleVariance = scheduleVariance;
	}

	public String getRollbackVolume() {
		return rollbackVolume;
	}

	public void setRollbackVolume(String rollbackVolume) {
		this.rollbackVolume = rollbackVolume;
	}

	public String getDevTestEffectiveness() {
		return devTestEffectiveness;
	}

	public void setDevTestEffectiveness(String devTestEffectiveness) {
		this.devTestEffectiveness = devTestEffectiveness;
	}

	public String getProjectStage() {
		return projectStage;
	}

	public void setProjectStage(String projectStage) {
		this.projectStage = projectStage;
	}

	public String getProjectType() {
		return projectType;
	}

	public void setProjectType(String projectType) {
		this.projectType = projectType;
	}

	public void setGlobalKitLOE(Long globalKitLOE) {
		this.globalKitLOE = globalKitLOE;
	}

	public Long getGlobalKitLOE() {
		return globalKitLOE;
	}

	public Long getLocalKitLOE() {
		return localKitLOE;
	}

	public void setLocalKitLOE(Long localKitLOE) {
		this.localKitLOE = localKitLOE;
	}

	public Long getOriginalKitLOE() {
		return originalKitLOE;
	}

	public void setOriginalKitLOE(Long originalKitLOE) {
		this.originalKitLOE = originalKitLOE;
	}

	public String getProjectStageCol() {
		return projectStageCol;
	}

	public void setProjectStageCol(String projectStageCol) {
		this.projectStageCol = projectStageCol;
	}

	public String getTotalGlobalLoe() {
		return "" + ((getGlobalDesignLOE() != null ? getGlobalDesignLOE() : 0)
				+ (getGlobalDevLOE() != null ? getGlobalDevLOE() : 0)
				+ (getGlobalTestLOE() != null ? getGlobalTestLOE() : 0)
				+ (getGlobalProjectManagementLOE() != null ? getGlobalProjectManagementLOE() : 0)
				+ (getGlobalKitLOE() != null ? getGlobalKitLOE() : 0)
				+ (getGlobalImplementationLOE() != null ? getGlobalImplementationLOE() : 0));
	}

	public String getTotalLocalLoe() {
		return "" + ((getLocalDesignLOE() != null ? getLocalDesignLOE() : 0)
				+ (getLocalDevLOE() != null ? getLocalDevLOE() : 0)
				+ (getLocalTestLOE() != null ? getLocalTestLOE() : 0)
				+ (getLocalProjectManagementLOE() != null ? getLocalProjectManagementLOE() : 0)
				+ (getLocalKitLOE() != null ? getLocalKitLOE() : 0)
				+ (getLocalImplementationLOE() != null ? getLocalImplementationLOE() : 0));
	}

	public String getProjectLevel() {
		return projectLevel;
	}

	public void setProjectLevel(String projectLevel) {
		this.projectLevel = projectLevel;
	}

	public Integer getEditFlag() {
		return editFlag;
	}

	public void setEditFlag(Integer editFlag) {
		this.editFlag = editFlag;
	}

	public String getTFSEpic() {
		return TFSEpic;
	}

	public void setTFSEpic(String tFSEpic) {
		TFSEpic = tFSEpic;
	}

	public Long getTotalGlobalLOE() {
		return totalGlobalLOE;
	}

	public void setTotalGlobalLOE(Long totalGlobalLOE) {
		this.totalGlobalLOE = totalGlobalLOE;
	}

	public Long getTotalLocalLOE() {
		return totalLocalLOE;
	}

	public void setTotalLocalLOE(Long totalLocalLOE) {
		this.totalLocalLOE = totalLocalLOE;
	}

	public String getTfStatus() {
		return tfStatus;
	}

	public void setTfStatus(String tfStatus) {
		this.tfStatus = tfStatus;
	}

	public Long getTotalLOE() {
		return totalLOE;
	}

	public void setTotalLOE(Long totalLOE) {
		this.totalLOE = totalLOE;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getStatusTemp() {
		return statusTemp;
	}

	public void setStatusTemp(String statusTemp) {
		this.statusTemp = statusTemp;
	}

	public boolean isForInterns() {
		return isForInterns;
	}

	public void setForInterns(boolean isForInterns) {
		this.isForInterns = isForInterns;
	}

	public Long getStableTeam() {
		return stableTeam;
	}

	public void setStableTeam(Long stableTeam) {
		this.stableTeam = stableTeam;
	}

	public String getStableTeamName() {
		return stableTeamName;
	}

	public void setStableTeamName(String stableTeamName) {
		this.stableTeamName = stableTeamName;
	}

	public Long getOrder() {
		return order;
	}

	public void setOrder(Long order) {
		this.order = order;
	}

	public String getSignum() {
		return signum;
	}

	public void setSignum(String signum) {
		this.signum = signum;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getChargedHours() {
		return chargedHours;
	}

	public void setChargedHours(Double chargedHours) {
		this.chargedHours = chargedHours;
	}

	public String getNwStatus() {
		return nwStatus;
	}

	public void setNwStatus(String nwStatus) {
		this.nwStatus = nwStatus;
	}

	public String getWeekDate() {
		return weekDate;
	}

	public void setWeekDate(String weekDate) {
		this.weekDate = weekDate;
	}

	public String getApplication() {
		return application;
	}

	public void setApplication(String application) {
		this.application = application;
	}

	public Double getStableContribution() {
		return stableContribution;
	}

	public void setStableContribution(Double stableContribution) {
		this.stableContribution = stableContribution;
	}

	public Long getNwId() {
		return nwId;
	}

	public void setNwId(Long nwId) {
		this.nwId = nwId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Double getNccStableContribution() {
		return nccStableContribution;
	}

	public void setNccStableContribution(Double nccStableContribution) {
		this.nccStableContribution = nccStableContribution;
	}

	public Double getPmStableContribution() {
		return pmStableContribution;
	}

	public void setPmStableContribution(Double pmStableContribution) {
		this.pmStableContribution = pmStableContribution;
	}

	public Long getTotalStoryPoints() {
		return totalStoryPoints;
	}

	public void setTotalStoryPoints(Long totalStoryPoints) {
		this.totalStoryPoints = totalStoryPoints;
	}

}
