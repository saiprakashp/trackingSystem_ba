package com.egil.pts.dao.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.egil.pts.modal.ProjectCategory;
import com.egil.pts.modal.ProjectSubCategory;
import com.egil.pts.modal.Status;

@Entity
@Table(name = "PTS_NETWORK_CODES")
@org.hibernate.annotations.Table(appliesTo = "PTS_NETWORK_CODES")
public class NetworkCodes extends TransactionInfo {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id", nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "network_code_id")
	private String networkCodeId;

	@Column(name = "network_code_name")
	private String networkCodeName;

	@Column(name = "description")
	private String description;

	@Column(name = "effort")
	private Long effort;

	@Enumerated(EnumType.STRING)
	@Column(name = "status")
	private Status status;
	@NotFound(action = NotFoundAction.IGNORE)
	@Column(name = "project_id")
	private Long projectId;
	
	@NotFound(action = NotFoundAction.IGNORE)
	@Column(name = "nw_order")
	private Long order;
	
	@Column(name = "pillar_id")
	private Long pillarId;

	@Column(name = "project_level")
	private String projectLevel;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ACCOUNT_ID")
	private CustomerAccounts account;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PRODUCT_OWNER_ID")
	private ProductOwner productOwner;

	@Column(name = "start_date")
	private Date startDate;

	@Column(name = "end_date")
	private Date endDate;

	@Column(name = "global_design_loe")
	private Long globalDesignLOE;

	@Column(name = "global_dev_loe")
	private Long globalDevLOE;

	@Column(name = "global_test_loe")
	private Long globalTestLOE;

	@Column(name = "global_impl_loe")
	private Long globalImplementationLOE;

	@Column(name = "global_opr_hand_off_loe")
	private Long globalOperationsHandOffLOE;

	@Column(name = "global_proj_mgmt_loe")
	private Long globalProjectManagementLOE;

	@Column(name = "global_others_loe")
	private Long globalOthersLOE;

	@Column(name = "GLOBAL_KIT_LOE")
	private Long globalKitLOE;

	@Column(name = "LOCAL_KIT_LOE")
	private Long localKitLOE;

	@Column(name = "local_design_loe")
	private Long localDesignLOE;

	@Column(name = "local_dev_loe")
	private Long localDevLOE;

	@Column(name = "local_test_loe")
	private Long localTestLOE;

	@Column(name = "local_impl_loe")
	private Long localImplementationLOE;

	@Column(name = "local_opr_hand_off_loe")
	private Long localOperationsHandOffLOE;

	@Column(name = "local_proj_mgmt_loe")
	private Long localProjectManagementLOE;

	@Column(name = "local_others_loe")
	private Long localOthersLOE;

	@Column(name = "ORIGINAL_KIT_LOE")
	private Long OriginalKitLOE;

	@Column(name = "ORIGINAL_DESIGN_LOE")
	private Long originalDesignLOE;

	@Column(name = "ORIGINAL_DEV_LOE")
	private Long originalDevLOE;

	@Column(name = "ORIGINAL_TEST_LOE")
	private Long originalTestLOE;

	@Column(name = "ORIGINAL_PROJ_MGMT_LOE")
	private Long originalProjectManagementLOE;

	@Column(name = "ORIGINAL_IMPL_LOE")
	private Long originalImplementationLOE;

	@Column(name = "ORIGINAL_OPR_HAND_OFF_LOE")
	private Long originalOperationsHandOffLOE;

	@Enumerated(EnumType.STRING)
	@Column(name = "project_category")
	private ProjectCategory projectCategory;

	@Enumerated(EnumType.STRING)
	@Column(name = "project_sub_category")
	private ProjectSubCategory projectSubCategory;

	@Column(name = "design_planned_date")
	private Date plannedDesignDate;

	@Column(name = "design_actual_date")
	private Date actualDesignDate;

	@Column(name = "dev_planned_date")
	private Date plannedDevDate;

	@Column(name = "dev_actual_date")
	private Date actualDevDate;

	@Column(name = "test_planned_date")
	private Date plannedTestDate;

	@Column(name = "test_actual_date")
	private Date actualTestDate;

	@Column(name = "impl_planned_date")
	private Date plannedImplDate;

	@Column(name = "impl_actual_date")
	private Date actualImplDate;

	@Column(name = "opr_handoff_planned_date")
	private Date plannedOprHandoffDate;

	@Column(name = "opr_handoff_actual_date")
	private Date actualOprHandoffDate;

	@Column(name = "sreq_no")
	private String sreqNo;

	@Column(name = "priority")
	private String priority;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "product_manager")
	private User programManager;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "project_manager")
	private User projectManager;

	@Column(name = "RELEASE_ID")
	private String releaseId;

	@Column(name = "RELEASE_NAME")
	private String releaseName;

	@Column(name = "RELEASE_TYPE")
	private String releaseType;

	@Column(name = "CHANGE_CONTROL")
	private String changeControl;

	@Column(name = "CHANGE_CONTROL_NUMBER")
	private String changeControlNo;

	@Column(name = "CHANGE_CONTROL_STATUS")
	private String changeControlStatus;

	@Column(name = "CHANGE_CONTROL_IMPACT")
	private String changeControlImpact;

	@Column(name = "ROLL_BACK")
	private String rollback;

	@Column(name = "ROLL_BACK_NUMBER")
	private String rollbackNo;

	@Column(name = "ROLL_BACK_REASON")
	private String rollbackReason;

	@Column(name = "SCOPE_CLOSE_DATE")
	private Date scopeCloseDate;

	@Column(name = "CHANGE_CONTROL_DATE")
	private Date changeControlDate;

	@Column(name = "ROLL_BACK_DATE")
	private Date rollbackDate;

	@Column(name = "ORIGINAL_DESIGN_DATE")
	private Date originalDesignDate;

	@Column(name = "ORIGINAL_DEV_DATE")
	private Date originalDevDate;

	@Column(name = "ORIGINAL_TESTING_DATE")
	private Date originalTestDate;

	@Column(name = "ORIGINAL_IMPL_DATE")
	private Date originalImplDate;

	@Column(name = "ORIGINAL_OPR_HANDOFF_DATE")
	private Date originalOprHandoffDate;

	@Column(name = "PLANNED_WARRANTY_COMPLETION_DATE")
	private Date plannedWarrantyCompletionDate;

	@Column(name = "ORIGINAL_WARRANTY_COMPLETION_DATE")
	private Date originalWarrantyCompletionDate;

	@Column(name = "SCHEDULE_VARIANCE")
	private String scheduleVariance;

	@Column(name = "ROLLBACK_VOLUME")
	private String rollbackVolume;

	@Column(name = "DEV_TEST_EFFECTIVENESS")
	private String devTestEffectiveness;

	@Column(name = "project_stage")
	private String projectStage;

	@Column(name = "project_Type")
	private String projectType;

	@Column(name = "comments")
	private String comments;

	@Column(name = "PROJECT_PHASE_COLOR")
	private String projectStageCol;

	@Column(name = "EDIT_FLAG")
	private Integer editFlag;

	@Column(name = "TFSEpic")
	private String TFSEpic;

	@Column(name = "totalGlobalLOE")
	private Long totalGlobalLOE;

	@Column(name = "totalLocalLOE")
	private Long totalLocalLOE;

	@Column(name = "totalLOE")
	private Long totalLOE;

	@Column(name = "isAccount")
	private Boolean isAccount;

	@OneToOne
	@JoinColumn(name = "stable_teams",nullable = true)
	private StableTeams stableTeam;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNetworkCodeId() {
		return networkCodeId;
	}

	public void setNetworkCodeId(String networkCodeId) {
		this.networkCodeId = networkCodeId;
	}

	public String getNetworkCodeName() {
		return networkCodeName;
	}

	public void setNetworkCodeName(String networkCodeName) {
		this.networkCodeName = networkCodeName;
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

	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

	public Long getPillarId() {
		return pillarId;
	}

	public void setPillarId(Long pillarId) {
		this.pillarId = pillarId;
	}

	public String getProjectLevel() {
		return projectLevel;
	}

	public void setProjectLevel(String projectLevel) {
		this.projectLevel = projectLevel;
	}

	public CustomerAccounts getAccount() {
		return account;
	}

	public void setAccount(CustomerAccounts account) {
		this.account = account;
	}

	public ProductOwner getProductOwner() {
		return productOwner;
	}

	public void setProductOwner(ProductOwner productOwner) {
		this.productOwner = productOwner;
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

	public Long getGlobalDesignLOE() {
		return globalDesignLOE;
	}

	public void setGlobalDesignLOE(Long globalDesignLOE) {
		this.globalDesignLOE = globalDesignLOE;
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

	public Long getGlobalKitLOE() {
		return globalKitLOE;
	}

	public void setGlobalKitLOE(Long globalKitLOE) {
		this.globalKitLOE = globalKitLOE;
	}

	public Long getLocalKitLOE() {
		return localKitLOE;
	}

	public void setLocalKitLOE(Long localKitLOE) {
		this.localKitLOE = localKitLOE;
	}

	public Long getLocalDesignLOE() {
		return localDesignLOE;
	}

	public void setLocalDesignLOE(Long localDesignLOE) {
		this.localDesignLOE = localDesignLOE;
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

	public Long getOriginalKitLOE() {
		return OriginalKitLOE;
	}

	public void setOriginalKitLOE(Long originalKitLOE) {
		OriginalKitLOE = originalKitLOE;
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

	public ProjectCategory getProjectCategory() {
		return projectCategory;
	}

	public void setProjectCategory(ProjectCategory projectCategory) {
		this.projectCategory = projectCategory;
	}

	public ProjectSubCategory getProjectSubCategory() {
		return projectSubCategory;
	}

	public void setProjectSubCategory(ProjectSubCategory projectSubCategory) {
		this.projectSubCategory = projectSubCategory;
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

	public String getSreqNo() {
		return sreqNo;
	}

	public void setSreqNo(String sreqNo) {
		this.sreqNo = sreqNo;
	}

	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	public User getProgramManager() {
		return programManager;
	}

	public void setProgramManager(User programManager) {
		this.programManager = programManager;
	}

	public User getProjectManager() {
		return projectManager;
	}

	public void setProjectManager(User projectManager) {
		this.projectManager = projectManager;
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

	public Date getPlannedWarrantyCompletionDate() {
		return plannedWarrantyCompletionDate;
	}

	public void setPlannedWarrantyCompletionDate(Date plannedWarrantyCompletionDate) {
		this.plannedWarrantyCompletionDate = plannedWarrantyCompletionDate;
	}

	public Date getOriginalWarrantyCompletionDate() {
		return originalWarrantyCompletionDate;
	}

	public void setOriginalWarrantyCompletionDate(Date originalWarrantyCompletionDate) {
		this.originalWarrantyCompletionDate = originalWarrantyCompletionDate;
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

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getProjectStageCol() {
		return projectStageCol;
	}

	public void setProjectStageCol(String projectStageCol) {
		this.projectStageCol = projectStageCol;
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

	public Long getTotalLOE() {
		return totalLOE;
	}

	public void setTotalLOE(Long totalLOE) {
		this.totalLOE = totalLOE;
	}

	public Boolean getIsAccount() {
		return isAccount;
	}

	public void setIsAccount(Boolean isAccount) {
		this.isAccount = isAccount;
	}

	public StableTeams getStableTeam() {
		return stableTeam;
	}

	public void setStableTeam(StableTeams stableTeam) {
		this.stableTeam = stableTeam;
	}

	public Long getOrder() {
		return order;
	}

	public void setOrder(Long order) {
		this.order = order;
	}


}
