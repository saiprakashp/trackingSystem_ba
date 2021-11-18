package com.egil.pts.service.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.poi.hssf.util.HSSFColor;
import org.hibernate.annotations.common.util.StringHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.egil.pts.dao.domain.CustomerAccounts;
import com.egil.pts.dao.domain.Pillar;
import com.egil.pts.dao.domain.ProductOwner;
import com.egil.pts.dao.domain.Project;
import com.egil.pts.dao.domain.User;
import com.egil.pts.dao.domain.UserNetworkCodes;
import com.egil.pts.modal.BulkResponse;
import com.egil.pts.modal.NetworkCodeEffort;
import com.egil.pts.modal.NetworkCodes;
import com.egil.pts.modal.Pagination;
import com.egil.pts.modal.ProjectCategory;
import com.egil.pts.modal.ProjectSubCategory;
import com.egil.pts.modal.SearchSortContainer;
import com.egil.pts.modal.StableTeams;
import com.egil.pts.modal.Status;
import com.egil.pts.modal.SummaryResponse;
import com.egil.pts.service.NetworkCodesService;
import com.egil.pts.service.common.BaseUIService;
import com.egil.pts.util.GenericExcel;

@Service("networkCodesService")
public class NetworkCodesServiceImpl extends BaseUIService implements NetworkCodesService {
	private static Map<String, Integer> monthMap = new HashMap<>();
	static {
		monthMap.put("Jan", 0);
		monthMap.put("Feb", 1);
		monthMap.put("Mar", 2);
		monthMap.put("Apr", 3);
		monthMap.put("May", 4);
		monthMap.put("Jun", 5);
		monthMap.put("Jul", 6);
		monthMap.put("Aug", 7);
		monthMap.put("Sep", 8);
		monthMap.put("Oct", 9);
		monthMap.put("Nov", 10);
		monthMap.put("Dec", 11);
	}

	@Override
	@Transactional
	public SummaryResponse<NetworkCodes> getNetworkCodesSummary(Pagination pagination,
			SearchSortContainer searchSortObj) throws Throwable {
		SummaryResponse<NetworkCodes> summary = new SummaryResponse<NetworkCodes>();
		if (searchSortObj != null && searchSortObj.getLoggedInId() != null
				&& !searchSortObj.getLoggedInId().equals("")) {
			summary.setTotalRecords(daoManager.getNetworkCodesDao().getResourceNetworkCodesCount(searchSortObj));
		} else {
			summary.setTotalRecords(daoManager.getNetworkCodesDao().getNetworkCodesCount(searchSortObj));
		}
		summary.setEnitities(getNetworkCodes(pagination, searchSortObj, null, null, null));
		return summary;
	}

	private List<NetworkCodes> getNetworkCodes(Pagination pagination, SearchSortContainer searchSortObj,
			String searchValue, Set<Long> idList, List<Integer> stableList) throws Throwable {
		List<NetworkCodes> modalNetworkCodesList = new ArrayList<NetworkCodes>();
		List<com.egil.pts.dao.domain.NetworkCodes> domainNetworkCodeList = new ArrayList<com.egil.pts.dao.domain.NetworkCodes>();
		if (searchSortObj != null && searchSortObj.getLoggedInId() != null
				&& !searchSortObj.getLoggedInId().equals("")) {
			domainNetworkCodeList = daoManager.getNetworkCodesDao().getResourceNetworkCodes(pagination, searchSortObj,
					searchValue, idList);
		} else {
			domainNetworkCodeList = daoManager.getNetworkCodesDao().getNetworkCodes(pagination, searchSortObj,
					searchValue, idList, stableList);
		}
		NetworkCodes modalNetworkCode = null;
		for (com.egil.pts.dao.domain.NetworkCodes domainNetworkCode : domainNetworkCodeList) {
			modalNetworkCode = new NetworkCodes();
			convertDomainToModal(modalNetworkCode, domainNetworkCode);
			modalNetworkCodesList.add(modalNetworkCode);
		}
		return modalNetworkCodesList;
	}

	@Override
	@Transactional
	public void createNetworkCode(NetworkCodes modalNetworkCode) throws Throwable {
		com.egil.pts.dao.domain.NetworkCodes domainNetworkCode = new com.egil.pts.dao.domain.NetworkCodes();
		convertModalToDomain(modalNetworkCode, domainNetworkCode);

		domainNetworkCode = daoManager.getNetworkCodesDao().save(domainNetworkCode);

		daoManager.getNetworkCodesDao().saveUserContributions(domainNetworkCode.getId());
		modalNetworkCode.setId(domainNetworkCode.getId());
	}

	@Override
	@Transactional
	public void modifyNetworkCode(NetworkCodes modalNetworkCode) throws Throwable {
		com.egil.pts.dao.domain.NetworkCodes domainNetworkCode = new com.egil.pts.dao.domain.NetworkCodes();
		convertModalToDomain(modalNetworkCode, domainNetworkCode);
		daoManager.getNetworkCodesDao().save(domainNetworkCode);
		daoManager.getNetworkCodesDao().flush();
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public Integer deleteNetworkCodes(List<Long> networkCodeIdList) throws Throwable {
		return daoManager.getNetworkCodesDao().deleteNetworkCodes(networkCodeIdList);
	}

	private void convertModalToDomain(NetworkCodes modalNetworkCode,
			com.egil.pts.dao.domain.NetworkCodes domainNetworkCode) throws Throwable {
		if (modalNetworkCode != null && domainNetworkCode != null) {
			if (modalNetworkCode.getId() != null) {
				domainNetworkCode.setId(modalNetworkCode.getId());
			}
			if (modalNetworkCode.getNetworkCodeId() != null) {
				domainNetworkCode.setNetworkCodeId(modalNetworkCode.getNetworkCodeId());
			}
			if (modalNetworkCode.getNetworkCode() != null) {
				domainNetworkCode.setNetworkCodeName(modalNetworkCode.getNetworkCode());
			}
			domainNetworkCode.setIsAccount(modalNetworkCode.isForInterns());

			com.egil.pts.dao.domain.StableTeams team = new com.egil.pts.dao.domain.StableTeams();
			team.setId(modalNetworkCode.getStableTeam());
			domainNetworkCode.setStableTeam(team);

			if (modalNetworkCode.getCustomerId() != null) {
				CustomerAccounts cusAcct = new CustomerAccounts();
				cusAcct.setId(modalNetworkCode.getCustomerId());
				domainNetworkCode.setAccount(cusAcct);
			}
			if (modalNetworkCode.getProject() != null && !modalNetworkCode.getProject().equals("")
					&& !modalNetworkCode.getProject().equals("null") && !modalNetworkCode.getProject().equals("-1")) {
				/*
				 * Project project = new Project();
				 * project.setId(Long.valueOf(modalNetworkCode.getProject()));
				 */
				domainNetworkCode.setProjectId(Long.valueOf(modalNetworkCode.getProject()));
				domainNetworkCode.setProjectLevel("APPLICATION");
			}

			if (!(modalNetworkCode.getProject() != null && !modalNetworkCode.getProject().equals("")
					&& !modalNetworkCode.getProject().equals("null") && !modalNetworkCode.getProject().equals("-1"))
					&& !(modalNetworkCode.getPillar() != null && !modalNetworkCode.getPillar().equals("")
							&& !modalNetworkCode.getPillar().equals("null")
							&& !modalNetworkCode.getPillar().equals("-1"))) {
				domainNetworkCode.setProjectLevel("ACCOUNT");
			} else if (!(modalNetworkCode.getProject() != null && !modalNetworkCode.getProject().equals("")
					&& !modalNetworkCode.getProject().equals("null") && !modalNetworkCode.getProject().equals("-1"))
					&& (modalNetworkCode.getPillar() != null && !modalNetworkCode.getPillar().equals("")
							&& !modalNetworkCode.getPillar().equals("null")
							&& !modalNetworkCode.getPillar().equals("-1"))) {
				domainNetworkCode.setProjectLevel("PILLAR");
			}

			if (modalNetworkCode.getProductOwner() != null && !modalNetworkCode.getProductOwner().equals("")
					&& !modalNetworkCode.getProductOwner().equals("null")
					&& !modalNetworkCode.getProductOwner().equals("-1")) {
				ProductOwner productOwner = new ProductOwner();
				productOwner.setId(Long.valueOf(modalNetworkCode.getProductOwner()));
				domainNetworkCode.setProductOwner(productOwner);
			}

			if (domainNetworkCode.getProjectLevel().equalsIgnoreCase("APPLICATION")) {
				modalNetworkCode.setPillar(
						daoManager.getProjectDao().getPillarByProject(Long.valueOf(modalNetworkCode.getProject()))
								+ "");
				daoManager.getProjectDao().flush();
				daoManager.getProjectDao().clear();
			}

			if (modalNetworkCode.getPillar() != null && !modalNetworkCode.getPillar().equals("")
					&& !modalNetworkCode.getPillar().equals("null") && !modalNetworkCode.getPillar().equals("-1")) {
				/*
				 * Project project = new Project();
				 * project.setId(Long.valueOf(modalNetworkCode.getProject()));
				 */
				domainNetworkCode.setPillarId(Long.valueOf(modalNetworkCode.getPillar()));
			}

			if (modalNetworkCode.getProjectManager() != null && !modalNetworkCode.getProjectManager().equals("")
					&& !modalNetworkCode.getProjectManager().equals("null")
					&& !modalNetworkCode.getProjectManager().equals("-1")) {
				User productOwner = new User();
				productOwner.setId(Long.valueOf(modalNetworkCode.getProjectManager()));
				domainNetworkCode.setProjectManager(productOwner);
			}

			if (modalNetworkCode.getProgramManager() != null && !modalNetworkCode.getProgramManager().equals("")
					&& !modalNetworkCode.getProgramManager().equals("null")
					&& !modalNetworkCode.getProgramManager().equals("-1")) {
				User productOwner = new User();
				productOwner.setId(Long.valueOf(modalNetworkCode.getProgramManager()));
				domainNetworkCode.setProgramManager(productOwner);
			}

			if (modalNetworkCode.getPriority() != null) {
				domainNetworkCode.setPriority(modalNetworkCode.getPriority());
			}

			if (modalNetworkCode.getDescription() != null) {
				domainNetworkCode.setDescription(modalNetworkCode.getDescription());
			}

			if (modalNetworkCode.getEffort() != null) {
				domainNetworkCode.setEffort(modalNetworkCode.getEffort());
			}

			if (modalNetworkCode.getStatus() != null) {
				domainNetworkCode.setStatus(modalNetworkCode.getStatus());
			}

			if (modalNetworkCode.getProjectStage() != null) {
				domainNetworkCode.setProjectStage(modalNetworkCode.getProjectStage());
			}

			if (modalNetworkCode.getCreatedBy() != null) {
				domainNetworkCode.setCreatedBy(modalNetworkCode.getCreatedBy());
			}

			if (modalNetworkCode.getCreatedDate() != null) {
				domainNetworkCode.setCreatedDate(modalNetworkCode.getCreatedDate());
			}

			if (modalNetworkCode.getUpdatedBy() != null) {
				domainNetworkCode.setUpdatedBy(modalNetworkCode.getUpdatedBy());
			}
			if (modalNetworkCode.getUpdatedDate() != null) {
				domainNetworkCode.setUpdatedDate(modalNetworkCode.getUpdatedDate());
			}

			if (modalNetworkCode.getStartDate() != null) {
				domainNetworkCode.setStartDate(modalNetworkCode.getStartDate());
			}
			if (modalNetworkCode.getEndDate() != null) {
				domainNetworkCode.setEndDate(modalNetworkCode.getEndDate());
			}

			if (modalNetworkCode.getProjectCategory() != null && !modalNetworkCode.getProjectCategory().equals("")
					&& !modalNetworkCode.getProjectCategory().equals("null")
					&& !modalNetworkCode.getProjectCategory().equals("-1")) {
				domainNetworkCode.setProjectCategory(ProjectCategory.valueOf(modalNetworkCode.getProjectCategory()));
			}

			if (modalNetworkCode.getProjectSubCategory() != null && !modalNetworkCode.getProjectSubCategory().equals("")
					&& !modalNetworkCode.getProjectSubCategory().equals("null")
					&& !modalNetworkCode.getProjectSubCategory().equals("-1")) {
				domainNetworkCode
						.setProjectSubCategory(ProjectSubCategory.valueOf(modalNetworkCode.getProjectSubCategory()));
			}

			if (modalNetworkCode.getGlobalDesignLOE() != null) {
				domainNetworkCode.setGlobalDesignLOE(modalNetworkCode.getGlobalDesignLOE());
			}

			if (modalNetworkCode.getGlobalDevLOE() != null) {
				domainNetworkCode.setGlobalDevLOE(modalNetworkCode.getGlobalDevLOE());
			}

			if (modalNetworkCode.getGlobalTestLOE() != null) {
				domainNetworkCode.setGlobalTestLOE(modalNetworkCode.getGlobalTestLOE());
			}

			if (modalNetworkCode.getGlobalImplementationLOE() != null) {
				domainNetworkCode.setGlobalImplementationLOE(modalNetworkCode.getGlobalImplementationLOE());
			}

			if (modalNetworkCode.getGlobalOperationsHandOffLOE() != null) {
				domainNetworkCode.setGlobalOperationsHandOffLOE(modalNetworkCode.getGlobalOperationsHandOffLOE());
			}

			if (modalNetworkCode.getGlobalProjectManagementLOE() != null) {
				domainNetworkCode.setGlobalProjectManagementLOE(modalNetworkCode.getGlobalProjectManagementLOE());
			}

			if (modalNetworkCode.getGlobalOthersLOE() != null) {
				domainNetworkCode.setGlobalOthersLOE(modalNetworkCode.getGlobalOthersLOE());
			}

			if (modalNetworkCode.getLocalDesignLOE() != null) {
				domainNetworkCode.setLocalDesignLOE(modalNetworkCode.getLocalDesignLOE());
			}

			if (modalNetworkCode.getLocalDevLOE() != null) {
				domainNetworkCode.setLocalDevLOE(modalNetworkCode.getLocalDevLOE());
			}

			if (modalNetworkCode.getLocalTestLOE() != null) {
				domainNetworkCode.setLocalTestLOE(modalNetworkCode.getLocalTestLOE());
			}

			if (modalNetworkCode.getLocalImplementationLOE() != null) {
				domainNetworkCode.setLocalImplementationLOE(modalNetworkCode.getLocalImplementationLOE());
			}

			if (modalNetworkCode.getLocalOperationsHandOffLOE() != null) {
				domainNetworkCode.setLocalOperationsHandOffLOE(modalNetworkCode.getLocalOperationsHandOffLOE());
			}

			if (modalNetworkCode.getLocalProjectManagementLOE() != null) {
				domainNetworkCode.setLocalProjectManagementLOE(modalNetworkCode.getLocalProjectManagementLOE());
			}

			if (modalNetworkCode.getLocalOthersLOE() != null) {
				domainNetworkCode.setLocalOthersLOE(modalNetworkCode.getLocalOthersLOE());
			}

			if (modalNetworkCode.getSreqNo() != null) {
				domainNetworkCode.setSreqNo(modalNetworkCode.getSreqNo());
			}

			if (modalNetworkCode.getPlannedDesignDate() != null) {
				domainNetworkCode.setPlannedDesignDate(modalNetworkCode.getPlannedDesignDate());
			}
			if (modalNetworkCode.getActualDesignDate() != null) {
				domainNetworkCode.setActualDesignDate(modalNetworkCode.getActualDesignDate());
			}

			if (modalNetworkCode.getPlannedDevDate() != null) {
				domainNetworkCode.setPlannedDevDate(modalNetworkCode.getPlannedDevDate());
			}
			if (modalNetworkCode.getActualDevDate() != null) {
				domainNetworkCode.setActualDevDate(modalNetworkCode.getActualDevDate());
			}

			if (modalNetworkCode.getPlannedTestDate() != null) {
				domainNetworkCode.setPlannedTestDate(modalNetworkCode.getPlannedTestDate());
			}
			if (modalNetworkCode.getActualTestDate() != null) {
				domainNetworkCode.setActualTestDate(modalNetworkCode.getActualTestDate());
			}

			if (modalNetworkCode.getPlannedImplDate() != null) {
				domainNetworkCode.setPlannedImplDate(modalNetworkCode.getPlannedImplDate());
			}
			if (modalNetworkCode.getActualImplDate() != null) {
				domainNetworkCode.setActualImplDate(modalNetworkCode.getActualImplDate());
			}

			if (modalNetworkCode.getPlannedOprHandoffDate() != null) {
				domainNetworkCode.setPlannedOprHandoffDate(modalNetworkCode.getPlannedOprHandoffDate());
			}
			if (modalNetworkCode.getActualOprHandoffDate() != null) {
				domainNetworkCode.setActualOprHandoffDate(modalNetworkCode.getActualOprHandoffDate());
			}

			if (modalNetworkCode.getOriginalDesignLOE() != null) {
				domainNetworkCode.setOriginalDesignLOE(modalNetworkCode.getOriginalDesignLOE());
			}

			if (modalNetworkCode.getOriginalDevLOE() != null) {
				domainNetworkCode.setOriginalDevLOE(modalNetworkCode.getOriginalDevLOE());
			}

			if (modalNetworkCode.getOriginalTestLOE() != null) {
				domainNetworkCode.setOriginalTestLOE(modalNetworkCode.getOriginalTestLOE());
			}

			if (modalNetworkCode.getOriginalImplementationLOE() != null) {
				domainNetworkCode.setOriginalImplementationLOE(modalNetworkCode.getOriginalImplementationLOE());
			}

			if (modalNetworkCode.getOriginalOperationsHandOffLOE() != null) {
				domainNetworkCode.setOriginalOperationsHandOffLOE(modalNetworkCode.getOriginalOperationsHandOffLOE());
			}

			if (modalNetworkCode.getOriginalProjectManagementLOE() != null) {
				domainNetworkCode.setOriginalProjectManagementLOE(modalNetworkCode.getOriginalProjectManagementLOE());
			}
			if (modalNetworkCode.getOriginalDesignDate() != null) {
				domainNetworkCode.setOriginalDesignDate(modalNetworkCode.getOriginalDesignDate());
			}

			if (modalNetworkCode.getOriginalDevDate() != null) {
				domainNetworkCode.setOriginalDevDate(modalNetworkCode.getOriginalDevDate());
			}

			if (modalNetworkCode.getOriginalTestDate() != null) {
				domainNetworkCode.setOriginalTestDate(modalNetworkCode.getOriginalTestDate());
			}

			if (modalNetworkCode.getOriginalImplDate() != null) {
				domainNetworkCode.setOriginalImplDate(modalNetworkCode.getOriginalImplDate());
			}

			if (modalNetworkCode.getOriginalOprHandoffDate() != null) {
				domainNetworkCode.setOriginalOprHandoffDate(modalNetworkCode.getOriginalOprHandoffDate());
			}

			if (modalNetworkCode.getOriginalWarrantyCompletionDate() != null) {
				domainNetworkCode
						.setOriginalWarrantyCompletionDate(modalNetworkCode.getOriginalWarrantyCompletionDate());
			}

			if (modalNetworkCode.getPlannedWarrantyCompletionDate() != null) {
				domainNetworkCode.setPlannedWarrantyCompletionDate(modalNetworkCode.getPlannedWarrantyCompletionDate());
			}

			if (modalNetworkCode.getScopeCloseDate() != null) {
				domainNetworkCode.setScopeCloseDate(modalNetworkCode.getScopeCloseDate());
			}

			if (modalNetworkCode.getChangeControlDate() != null) {
				domainNetworkCode.setChangeControlDate(modalNetworkCode.getChangeControlDate());
			}

			if (modalNetworkCode.getRollbackDate() != null) {
				domainNetworkCode.setRollbackDate(modalNetworkCode.getRollbackDate());
			}

			if (modalNetworkCode.getReleaseId() != null) {
				domainNetworkCode.setReleaseId(modalNetworkCode.getReleaseId());
			}

			if (modalNetworkCode.getReleaseName() != null) {
				domainNetworkCode.setReleaseName(modalNetworkCode.getReleaseName());
			}

			if (modalNetworkCode.getReleaseType() != null) {
				domainNetworkCode.setReleaseType(modalNetworkCode.getReleaseType());
			}

			if (modalNetworkCode.getChangeControl() != null) {
				domainNetworkCode.setChangeControl(modalNetworkCode.getChangeControl());
			}

			if (modalNetworkCode.getChangeControlNo() != null) {
				domainNetworkCode.setChangeControlNo(modalNetworkCode.getChangeControlNo());
			}

			if (modalNetworkCode.getChangeControlStatus() != null) {
				domainNetworkCode.setChangeControlStatus(modalNetworkCode.getChangeControlStatus());
			}

			if (modalNetworkCode.getChangeControlImpact() != null) {
				domainNetworkCode.setChangeControlImpact(modalNetworkCode.getChangeControlImpact());
			}

			if (modalNetworkCode.getRollback() != null) {
				domainNetworkCode.setRollback(modalNetworkCode.getRollback());
			}

			if (modalNetworkCode.getRollbackNo() != null) {
				domainNetworkCode.setRollbackNo(modalNetworkCode.getRollbackNo());
			}

			if (modalNetworkCode.getRollbackReason() != null) {
				domainNetworkCode.setRollbackReason(modalNetworkCode.getRollbackReason());
			}

			if (modalNetworkCode.getScheduleVariance() != null) {
				domainNetworkCode.setScheduleVariance(modalNetworkCode.getScheduleVariance());
			}

			if (modalNetworkCode.getRollbackVolume() != null) {
				domainNetworkCode.setRollbackVolume(modalNetworkCode.getRollbackVolume());
			}

			if (modalNetworkCode.getDevTestEffectiveness() != null) {
				domainNetworkCode.setDevTestEffectiveness(modalNetworkCode.getDevTestEffectiveness());
			}
			if (modalNetworkCode.getTFSEpic() != null) {
				domainNetworkCode.setTFSEpic(modalNetworkCode.getTFSEpic());
			}
			if (modalNetworkCode.getTotalGlobalLOE() != null) {
				domainNetworkCode.setTotalGlobalLOE(modalNetworkCode.getTotalGlobalLOE());
			}
			if (modalNetworkCode.getTotalLocalLOE() != null) {
				domainNetworkCode.setTotalLocalLOE(modalNetworkCode.getTotalLocalLOE());
			}

			if (modalNetworkCode.getTFSEpic() != null) {
				domainNetworkCode.setTFSEpic(modalNetworkCode.getTFSEpic());
			}
			if (modalNetworkCode.getTotalLOE() != null) {
				domainNetworkCode.setTotalLOE(modalNetworkCode.getTotalLOE());
			}
		}
	}

	private void convertDomainToModal(NetworkCodes modalNetworkCode,
			com.egil.pts.dao.domain.NetworkCodes domainNetworkCode) {
		if (modalNetworkCode != null && domainNetworkCode != null) {
			if (domainNetworkCode.getEditFlag() != null) {
				modalNetworkCode.setEditFlag(domainNetworkCode.getEditFlag());
			} else {
				modalNetworkCode.setEditFlag(0);
			}
			if (domainNetworkCode.getId() != null) {
				modalNetworkCode.setId(domainNetworkCode.getId());
			}
			modalNetworkCode.setForInterns(
					(domainNetworkCode.getIsAccount() == null) ? false : domainNetworkCode.getIsAccount());
			if (domainNetworkCode.getNetworkCodeId() != null) {
				modalNetworkCode.setNetworkCodeId(domainNetworkCode.getNetworkCodeId());
			}
			if (domainNetworkCode.getNetworkCodeName() != null) {
				modalNetworkCode.setNetworkCode(domainNetworkCode.getNetworkCodeName());
			}
			if (domainNetworkCode.getStableTeam() != null) {
				com.egil.pts.dao.domain.StableTeams s = domainNetworkCode.getStableTeam();

				modalNetworkCode.setStableTeam(s.getId());
				modalNetworkCode.setStableTeamName(s.getTeamName());
			}

			if (domainNetworkCode.getProjectId() != null) {
				Project project = daoManager.getProjectDao().get(domainNetworkCode.getProjectId());
				modalNetworkCode.setProjectId(project.getId());
				modalNetworkCode.setProject(project.getProjectName());
				if (project.getPillar() != null && project.getPillar().getId() != null) {
					modalNetworkCode.setPillarId(project.getPillar().getId());
					modalNetworkCode.setPillar(project.getPillar().getPillarName());
					if (project.getPillar().getCustomerAccount() != null
							&& project.getPillar().getCustomerAccount().getId() != null) {
						modalNetworkCode.setCustomerId(project.getPillar().getCustomerAccount().getId());
						modalNetworkCode.setCustomer(project.getPillar().getCustomerAccount().getAccountName());
					}
				}

			} else {
				if (domainNetworkCode.getPillarId() != null) {
					Pillar pillar = daoManager.getPillarDao().get(domainNetworkCode.getPillarId());
					modalNetworkCode.setPillarId(pillar.getId());
					modalNetworkCode.setPillar(pillar.getPillarName());
					if (pillar.getCustomerAccount() != null && pillar.getCustomerAccount().getId() != null) {
						modalNetworkCode.setCustomerId(pillar.getCustomerAccount().getId());
						modalNetworkCode.setCustomer(pillar.getCustomerAccount().getAccountName());
					}
				} else {
					modalNetworkCode.setCustomerId(domainNetworkCode.getAccount().getId());
					modalNetworkCode.setCustomer(domainNetworkCode.getAccount().getAccountName());
				}
			}
			if (domainNetworkCode.getStatus() != null) {
				modalNetworkCode.setStatus(domainNetworkCode.getStatus());
			}
			if (domainNetworkCode.getProjectLevel() != null) {
				modalNetworkCode.setProjectLevel(domainNetworkCode.getProjectLevel());
			}
			if (domainNetworkCode.getProjectStage() != null) {
				modalNetworkCode.setProjectStage(domainNetworkCode.getProjectStage());
			}

			if (domainNetworkCode.getProjectType() != null) {
				modalNetworkCode.setProjectType(domainNetworkCode.getProjectType());
			}

			if (domainNetworkCode.getProductOwner() != null && domainNetworkCode.getProductOwner().getId() != null) {
				modalNetworkCode.setProductOwnerId(domainNetworkCode.getProductOwner().getId());
				modalNetworkCode.setProductOwner(domainNetworkCode.getProductOwner().getOwnerName());
			}

			if (domainNetworkCode.getProjectManager() != null
					&& domainNetworkCode.getProjectManager().getId() != null) {
				modalNetworkCode.setProjectManagerId(domainNetworkCode.getProjectManager().getId());
				modalNetworkCode.setProjectManager(domainNetworkCode.getProjectManager().getPersonalInfo().getName());
			}

			if (domainNetworkCode.getProgramManager() != null
					&& domainNetworkCode.getProgramManager().getId() != null) {
				modalNetworkCode.setProgramManagerId(domainNetworkCode.getProgramManager().getId());
				modalNetworkCode.setProgramManager(domainNetworkCode.getProgramManager().getPersonalInfo().getName());
			}

			if (domainNetworkCode.getDescription() != null) {
				modalNetworkCode.setDescription(domainNetworkCode.getDescription());
			}

			if (domainNetworkCode.getPriority() != null) {
				modalNetworkCode.setPriority(domainNetworkCode.getPriority());
			}

			if (domainNetworkCode.getProjectCategory() != null) {
				modalNetworkCode.setProjectCategory(domainNetworkCode.getProjectCategory().toString());
			}

			if (domainNetworkCode.getProjectSubCategory() != null) {
				modalNetworkCode.setProjectSubCategory(domainNetworkCode.getProjectSubCategory().toString());
			}

			if (domainNetworkCode.getGlobalDesignLOE() != null) {
				modalNetworkCode.setGlobalDesignLOE(domainNetworkCode.getGlobalDesignLOE());
			}

			if (domainNetworkCode.getGlobalKitLOE() != null) {
				modalNetworkCode.setGlobalKitLOE(domainNetworkCode.getGlobalKitLOE());
			}

			if (domainNetworkCode.getGlobalDevLOE() != null) {
				modalNetworkCode.setGlobalDevLOE(domainNetworkCode.getGlobalDevLOE());
			}

			if (domainNetworkCode.getGlobalTestLOE() != null) {
				modalNetworkCode.setGlobalTestLOE(domainNetworkCode.getGlobalTestLOE());
			}

			if (domainNetworkCode.getGlobalImplementationLOE() != null) {
				modalNetworkCode.setGlobalImplementationLOE(domainNetworkCode.getGlobalImplementationLOE());
			}

			if (domainNetworkCode.getGlobalOperationsHandOffLOE() != null) {
				modalNetworkCode.setGlobalOperationsHandOffLOE(domainNetworkCode.getGlobalOperationsHandOffLOE());
			}

			if (domainNetworkCode.getGlobalProjectManagementLOE() != null) {
				modalNetworkCode.setGlobalProjectManagementLOE(domainNetworkCode.getGlobalProjectManagementLOE());
			}

			if (domainNetworkCode.getGlobalOthersLOE() != null) {
				modalNetworkCode.setGlobalOthersLOE(domainNetworkCode.getGlobalOthersLOE());
			}

			if (domainNetworkCode.getLocalKitLOE() != null) {
				modalNetworkCode.setLocalKitLOE(domainNetworkCode.getLocalKitLOE());
			}

			if (domainNetworkCode.getLocalDesignLOE() != null) {
				modalNetworkCode.setLocalDesignLOE(domainNetworkCode.getLocalDesignLOE());
			}

			if (domainNetworkCode.getLocalDevLOE() != null) {
				modalNetworkCode.setLocalDevLOE(domainNetworkCode.getLocalDevLOE());
			}

			if (domainNetworkCode.getLocalTestLOE() != null) {
				modalNetworkCode.setLocalTestLOE(domainNetworkCode.getLocalTestLOE());
			}

			if (domainNetworkCode.getLocalImplementationLOE() != null) {
				modalNetworkCode.setLocalImplementationLOE(domainNetworkCode.getLocalImplementationLOE());
			}

			if (domainNetworkCode.getLocalOperationsHandOffLOE() != null) {
				modalNetworkCode.setLocalOperationsHandOffLOE(domainNetworkCode.getLocalOperationsHandOffLOE());
			}

			if (domainNetworkCode.getLocalProjectManagementLOE() != null) {
				modalNetworkCode.setLocalProjectManagementLOE(domainNetworkCode.getLocalProjectManagementLOE());
			}

			if (domainNetworkCode.getLocalOthersLOE() != null) {
				modalNetworkCode.setLocalOthersLOE(domainNetworkCode.getLocalOthersLOE());
			}

			if (domainNetworkCode.getEffort() != null) {
				modalNetworkCode.setEffort(domainNetworkCode.getEffort());
			}

			if (domainNetworkCode.getSreqNo() != null) {
				modalNetworkCode.setSreqNo(domainNetworkCode.getSreqNo());
			}

			if (domainNetworkCode.getCreatedBy() != null) {
				modalNetworkCode.setCreatedBy(domainNetworkCode.getCreatedBy());
			}

			if (domainNetworkCode.getCreatedDate() != null) {
				modalNetworkCode.setCreatedDate(domainNetworkCode.getCreatedDate());
			}

			if (domainNetworkCode.getUpdatedBy() != null) {
				modalNetworkCode.setUpdatedBy(domainNetworkCode.getUpdatedBy());
			}
			if (domainNetworkCode.getUpdatedDate() != null) {
				modalNetworkCode.setUpdatedDate(domainNetworkCode.getUpdatedDate());
			}
			if (domainNetworkCode.getStartDate() != null) {
				modalNetworkCode.setStartDate(domainNetworkCode.getStartDate());
			}
			if (domainNetworkCode.getEndDate() != null) {
				modalNetworkCode.setEndDate(domainNetworkCode.getEndDate());
			}

			if (domainNetworkCode.getPlannedDesignDate() != null) {
				modalNetworkCode.setPlannedDesignDate(domainNetworkCode.getPlannedDesignDate());
			}
			if (domainNetworkCode.getActualDesignDate() != null) {
				modalNetworkCode.setActualDesignDate(domainNetworkCode.getActualDesignDate());
			}

			if (domainNetworkCode.getPlannedDevDate() != null) {
				modalNetworkCode.setPlannedDevDate(domainNetworkCode.getPlannedDevDate());
			}
			if (domainNetworkCode.getActualDevDate() != null) {
				modalNetworkCode.setActualDevDate(domainNetworkCode.getActualDevDate());
			}

			if (domainNetworkCode.getPlannedTestDate() != null) {
				modalNetworkCode.setPlannedTestDate(domainNetworkCode.getPlannedTestDate());
			}
			if (domainNetworkCode.getActualTestDate() != null) {
				modalNetworkCode.setActualTestDate(domainNetworkCode.getActualTestDate());
			}

			if (domainNetworkCode.getPlannedImplDate() != null) {
				modalNetworkCode.setPlannedImplDate(domainNetworkCode.getPlannedImplDate());
			}
			if (domainNetworkCode.getActualImplDate() != null) {
				modalNetworkCode.setActualImplDate(domainNetworkCode.getActualImplDate());
			}

			if (domainNetworkCode.getPlannedOprHandoffDate() != null) {
				modalNetworkCode.setPlannedOprHandoffDate(domainNetworkCode.getPlannedOprHandoffDate());
			}
			if (domainNetworkCode.getActualOprHandoffDate() != null) {
				modalNetworkCode.setActualOprHandoffDate(domainNetworkCode.getActualOprHandoffDate());
			}

			if (domainNetworkCode.getOriginalKitLOE() != null) {
				modalNetworkCode.setOriginalKitLOE(domainNetworkCode.getOriginalKitLOE());
			}

			if (domainNetworkCode.getOriginalDesignLOE() != null) {
				modalNetworkCode.setOriginalDesignLOE(domainNetworkCode.getOriginalDesignLOE());
			}

			if (domainNetworkCode.getOriginalDevLOE() != null) {
				modalNetworkCode.setOriginalDevLOE(domainNetworkCode.getOriginalDevLOE());
			}

			if (domainNetworkCode.getOriginalTestLOE() != null) {
				modalNetworkCode.setOriginalTestLOE(domainNetworkCode.getOriginalTestLOE());
			}

			if (domainNetworkCode.getOriginalImplementationLOE() != null) {
				modalNetworkCode.setOriginalImplementationLOE(domainNetworkCode.getOriginalImplementationLOE());
			}

			if (domainNetworkCode.getOriginalOperationsHandOffLOE() != null) {
				modalNetworkCode.setOriginalOperationsHandOffLOE(domainNetworkCode.getOriginalOperationsHandOffLOE());
			}

			if (domainNetworkCode.getOriginalProjectManagementLOE() != null) {
				modalNetworkCode.setOriginalProjectManagementLOE(domainNetworkCode.getOriginalProjectManagementLOE());
			}

			if (domainNetworkCode.getOriginalDesignDate() != null) {
				modalNetworkCode.setOriginalDesignDate(domainNetworkCode.getOriginalDesignDate());
			}

			if (domainNetworkCode.getOriginalDevDate() != null) {
				modalNetworkCode.setOriginalDevDate(domainNetworkCode.getOriginalDevDate());
			}

			if (domainNetworkCode.getOriginalTestDate() != null) {
				modalNetworkCode.setOriginalTestDate(domainNetworkCode.getOriginalTestDate());
			}

			if (domainNetworkCode.getOriginalImplDate() != null) {
				modalNetworkCode.setOriginalImplDate(domainNetworkCode.getOriginalImplDate());
			}

			if (domainNetworkCode.getOriginalOprHandoffDate() != null) {
				modalNetworkCode.setOriginalOprHandoffDate(domainNetworkCode.getOriginalOprHandoffDate());
			}

			if (domainNetworkCode.getOriginalWarrantyCompletionDate() != null) {
				modalNetworkCode
						.setOriginalWarrantyCompletionDate(domainNetworkCode.getOriginalWarrantyCompletionDate());
			}

			if (domainNetworkCode.getPlannedWarrantyCompletionDate() != null) {
				modalNetworkCode.setPlannedWarrantyCompletionDate(domainNetworkCode.getPlannedWarrantyCompletionDate());
			}

			if (domainNetworkCode.getScopeCloseDate() != null) {
				modalNetworkCode.setScopeCloseDate(domainNetworkCode.getScopeCloseDate());
			}

			if (domainNetworkCode.getChangeControlDate() != null) {
				modalNetworkCode.setChangeControlDate(domainNetworkCode.getChangeControlDate());
			}

			if (domainNetworkCode.getRollbackDate() != null) {
				modalNetworkCode.setRollbackDate(domainNetworkCode.getRollbackDate());
			}

			if (domainNetworkCode.getReleaseId() != null) {
				modalNetworkCode.setReleaseId(domainNetworkCode.getReleaseId());
			}

			if (domainNetworkCode.getReleaseName() != null) {
				modalNetworkCode.setReleaseName(domainNetworkCode.getReleaseName());
			}

			if (domainNetworkCode.getReleaseType() != null) {
				modalNetworkCode.setReleaseType(domainNetworkCode.getReleaseType());
			}

			if (domainNetworkCode.getChangeControl() != null) {
				modalNetworkCode.setChangeControl(domainNetworkCode.getChangeControl());
			}

			if (domainNetworkCode.getChangeControlNo() != null) {
				modalNetworkCode.setChangeControlNo(domainNetworkCode.getChangeControlNo());
			}

			if (domainNetworkCode.getChangeControlStatus() != null) {
				modalNetworkCode.setChangeControlStatus(domainNetworkCode.getChangeControlStatus());
			}

			if (domainNetworkCode.getChangeControlImpact() != null) {
				modalNetworkCode.setChangeControlImpact(domainNetworkCode.getChangeControlImpact());
			}

			if (domainNetworkCode.getRollback() != null) {
				modalNetworkCode.setRollback(domainNetworkCode.getRollback());
			}

			if (domainNetworkCode.getRollbackNo() != null) {
				modalNetworkCode.setRollbackNo(domainNetworkCode.getRollbackNo());
			}

			if (domainNetworkCode.getRollbackReason() != null) {
				modalNetworkCode.setRollbackReason(domainNetworkCode.getRollbackReason());
			}
			if (domainNetworkCode.getScheduleVariance() != null) {
				modalNetworkCode.setScheduleVariance(domainNetworkCode.getScheduleVariance());
			}

			if (domainNetworkCode.getRollbackVolume() != null) {
				modalNetworkCode.setRollbackVolume(domainNetworkCode.getRollbackVolume());
			}

			if (domainNetworkCode.getDevTestEffectiveness() != null) {
				modalNetworkCode.setDevTestEffectiveness(domainNetworkCode.getDevTestEffectiveness());
			}
			if (domainNetworkCode.getTFSEpic() != null) {
				modalNetworkCode.setTFSEpic(domainNetworkCode.getTFSEpic());
			}
			if (domainNetworkCode.getTotalGlobalLOE() != null) {
				modalNetworkCode.setTotalGlobalLOE(domainNetworkCode.getTotalGlobalLOE());
			}
			if (domainNetworkCode.getTotalLocalLOE() != null) {
				modalNetworkCode.setTotalLocalLOE(domainNetworkCode.getTotalLocalLOE());
			}
			if (domainNetworkCode.getTotalLOE() != null) {
				modalNetworkCode
						.setTotalLOE((domainNetworkCode.getTotalLOE() != null) ? domainNetworkCode.getTotalLOE() : 0);
			}
		}
	}

	@Override
	public void getNetworkCodesMap(Map<Long, String> networkCodesMap, String searchValue, Set<Long> idList)
			throws Throwable {
		if (networkCodesMap == null) {
			networkCodesMap = new LinkedHashMap<Long, String>();
		} else {
			networkCodesMap.clear();
		}
		for (NetworkCodes networkCode : getNetworkCodes(null, null, searchValue, idList, null)) {
			networkCodesMap.put(networkCode.getId(),
					(networkCode.getNetworkCodeId() == null ? "" : networkCode.getNetworkCodeId() + " - ")
							+ networkCode.getReleaseId() + " - " + networkCode.getReleaseName());
		}
	}

	@Override
	@Transactional
	public void getResourceNetworkCodes(Long userId, Map<Long, String> assignedNetworkCodesMap, String searchValue,
			Set<Long> idList) throws Throwable {
		if (assignedNetworkCodesMap == null) {
			assignedNetworkCodesMap = new LinkedHashMap<Long, String>();
		} else {
			assignedNetworkCodesMap.clear();
		}
		if (userId != null && userId != 0l) {
			List<com.egil.pts.dao.domain.NetworkCodes> userNetworkCodeList = daoManager.getUserNetworkCodeDao()
					.getResourceNetworkCodes(userId, searchValue, idList);
			if (userNetworkCodeList != null && userNetworkCodeList.size() > 0) {
				for (com.egil.pts.dao.domain.NetworkCodes userNetworkCode : userNetworkCodeList) {
					assignedNetworkCodesMap.put(userNetworkCode.getId(),
							(userNetworkCode.getReleaseId() + " - " + userNetworkCode.getReleaseName()));
				}
			}
		}
	}

	@Override
	public void getNetworkCodeResources(boolean showAllEmp, Long superisorId, Long networkCodeId,
			Map<Long, String> selectedEmployeesMap) throws Throwable {
		// Gives all Network Selected Employees
		if (selectedEmployeesMap == null) {
			selectedEmployeesMap = new LinkedHashMap<Long, String>();
		} else {
			selectedEmployeesMap.clear();
		}
		if (networkCodeId != null && networkCodeId != 0l) {
			{
				List<User> domainUserList = daoManager.getUserNetworkCodeDao().getNetworkCodeResources(superisorId,
						networkCodeId);
				for (User user : domainUserList) {
					selectedEmployeesMap.put(user.getId(), user.getPersonalInfo().getName());
				}
			}

		}
	}

	@Override
	public void getNetworkCodeResources(Long superisorId, Long networkCodeId, Map<Long, String> selectedEmployeesMap,
			String searchValue, Set<Long> idList) throws Throwable {
		if (selectedEmployeesMap == null) {
			selectedEmployeesMap = new LinkedHashMap<Long, String>();
		} else {
			selectedEmployeesMap.clear();
		}
		if (networkCodeId != null && networkCodeId != 0l) {
			{
				List<com.egil.pts.modal.User> domainUserList = daoManager.getUserNetworkCodeDao()
						.getNetworkCodeResources(superisorId, networkCodeId, searchValue, idList);
				for (com.egil.pts.modal.User user : domainUserList) {
					selectedEmployeesMap.put(user.getId(), user.getName());
				}
			}
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public void mapNetworkCodeToResources(Long superisorId, Long selectedNetworkCodeId, List<Long> selectedEmployeeIds)
			throws Throwable {

		List<UserNetworkCodes> userNetworkCodesList = new ArrayList<UserNetworkCodes>();

		List<Long> existingUsersList = daoManager.getUserNetworkCodeDao().getUserIds(selectedNetworkCodeId,
				superisorId);

		List<Long> addedList = new ArrayList<Long>();
		addedList.addAll(selectedEmployeeIds);

		addedList.removeAll(existingUsersList);

		UserNetworkCodes un = null;
		addedList = addedList.stream().distinct().collect(Collectors.toList());
		for (Long userId : addedList) {
			un = new UserNetworkCodes();
			User user = new User();
			user.setId(userId);
			un.setUser(user);
			com.egil.pts.dao.domain.NetworkCodes nc = new com.egil.pts.dao.domain.NetworkCodes();
			nc.setId(selectedNetworkCodeId);
			un.setNetworkCodes(nc);
			un.setLastAssignedManager(superisorId);
			userNetworkCodesList.add(un);
		}
		if (userNetworkCodesList.size() > 0) {
			daoManager.getUserNetworkCodeDao().saveUserNetworkCodes(userNetworkCodesList);
		}
		List<Long> removedList = new ArrayList<Long>();
		if (existingUsersList != null && existingUsersList.size() > 0) {
			removedList.addAll(existingUsersList);
			removedList.removeAll(selectedEmployeeIds);
		}

		if (removedList.size() > 0) {
			daoManager.getUserNetworkCodeDao().deleteUserNetworkCodes(null, selectedNetworkCodeId, superisorId,
					removedList, null);
		}
	}

	@Override
	public List<NetworkCodeEffort> getDashBoardDetails(Long userId, boolean isAdmin) throws Throwable {
		return daoManager.getUserUtilizationDao().getDashBoardDetails(userId, isAdmin);
	}

	@Override
	public List<NetworkCodeEffort> getUserDashBoardDetails(Long userId) throws Throwable {
		return daoManager.getUserUtilizationDao().getUserDashBoardDetails(userId);
	}

	@Override
	public String getJSONStringOfStatus() throws Throwable {
		String returnValue = "";
		returnValue = "" + ":" + "Please Select" + ";" + Status.ACTIVE.toString() + ":" + "Active" + ";"
				+ Status.COMPLETED.toString() + ":" + "Completed" + ";" + Status.HOLD.toString() + ":" + "Hold" + ";"
				+ Status.IN_ACTIVE.toString() + ":" + "In-Active";

		return returnValue;
	}

	@Override
	public String getJSONStringOfProjectCategory() throws Throwable {
		String returnValue = "";
		returnValue = "" + ":" + "Please Select" + ";" + ProjectCategory.NEW_REVENUE.toString() + ":" + "New Revenue"
				+ ";" + ProjectCategory.REVENUE_RETENTION.toString() + ":" + "Revenue Retention";

		return returnValue;
	}

	@Override
	public String getJSONStringOfProjectSubCategory(String selectedProjectCategory) throws Throwable {
		String returnValue = "";

		if (selectedProjectCategory.isEmpty()) {
			returnValue = "" + ":" + "Please Select";
		} else if (selectedProjectCategory != null && selectedProjectCategory.equalsIgnoreCase("REVENUE_RETENTION")) {
			returnValue = "<option value='-1'>Please Select</option>\n<option value='"
					+ ProjectSubCategory.EOL.toString() + "'>End of Life</option>\n<option value='"
					+ ProjectSubCategory.INFRASTRUCTURE.toString() + "'>Infrastructure</option>\n<option value='"
					+ ProjectSubCategory.PRODUCTION_SUPPORT.toString()
					+ "'>Production support</option>\n<option value='" + ProjectSubCategory.SECURITY_MANDATE.toString()
					+ "'>Security Mandate</option>";

			/*
			 * returnValue = returnValue + ";" + ProjectSubCategory.EOL.toString() + ":" +
			 * "End of Life" + ";" + ProjectSubCategory.INFRASTRUCTURE.toString() + ":" +
			 * "Infrastructure" + ";" + ProjectSubCategory.PRODUCTION_SUPPORT.toString() +
			 * ":" + "Production support" + ";" +
			 * ProjectSubCategory.SECURITY_MANDATE.toString() + ":" + "Security Mandate";
			 */
		} else {
			returnValue = "<option value='-1'>Please Select</option>";
		}

		return returnValue;
	}

	@Override
	public void getNetworkCodesMap(Map<Long, String> networkCodesMap) throws Throwable {
		if (networkCodesMap == null) {
			networkCodesMap = new LinkedHashMap<Long, String>();
		} else {
			networkCodesMap.clear();
		}
		for (NetworkCodes networkCode : getNetworkCodes(null, null, null, null, null)) {
			networkCodesMap.put(networkCode.getId(), networkCode.getReleaseId() + " - " + networkCode.getReleaseName());
		}
	}

	@Override
	public void getNetworkCodesStablesMap(Long userId, Map<Long, String> networkCodesMap) throws Throwable {
		if (networkCodesMap == null) {
			networkCodesMap = new LinkedHashMap<Long, String>();
		} else {
			networkCodesMap.clear();
		}
		networkCodesMap.put(-1l, "Please Select");
		SearchSortContainer search = new SearchSortContainer();
		search.setSidx("releaseName");
		search.setSearchByStable(true);
		search.setSupervisorId(userId);
		List<Integer> stableIds = new ArrayList<>();
		stableIds = getStableCodes(userId);
		for (NetworkCodes networkCode : getNetworkCodes(null, search, null, null, stableIds)) {
			if (networkCode.getStableTeam() != null && networkCode.getStableTeam() > 0)
				networkCodesMap.put(networkCode.getId(),
						networkCode.getReleaseId() + " - " + networkCode.getReleaseName());
		}
	}

	private List<Integer> getStableCodes(Long userId) {
		return daoManager.getNetworkCodesDao().getStableTeams(userId);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true)
	public void getAssignedNetworkCodesMap(Long userId, Map<Long, String> assignedNetworkCodesMap, Date date,
			String role) throws Throwable {
		if (assignedNetworkCodesMap == null) {
			assignedNetworkCodesMap = new LinkedHashMap<Long, String>();
		} else {
			assignedNetworkCodesMap.clear();
		}

		List<NetworkCodes> userNetworkCodes = daoManager.getUserNetworkCodeDao().getResourceNetworkCodesDetails(userId,
				date, role);

		if (userNetworkCodes != null) {
			for (com.egil.pts.modal.NetworkCodes un : userNetworkCodes) {
				assignedNetworkCodesMap.put(Long.parseLong(un.getNetworkCodeId()),
						(un.getReleaseId() + " - " + un.getReleaseName()));
			}
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true)
	public String getRemainingHrsForNetworkCode(Long networkCodeId, String userType, String stream) {
		return daoManager.getUserNetworkCodeDao().getRemainingHrsForNetworkCode(networkCodeId, userType, stream);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true)
	public String getJSONStringOfProductOwners() throws Throwable {

		List<ProductOwner> domainProductOwnersList = daoManager.getProductOwnerDao().getProductOwners();
		String returnValue = "";
		returnValue = -1 + ":" + "Please Select" + ";";
		for (ProductOwner productOwner : domainProductOwnersList) {
			returnValue = returnValue + productOwner.getId() + ":" + productOwner.getOwnerName() + ";";
		}
		return returnValue.substring(0, returnValue.length() - 1);
	}

	@Override
	public void getProjectCategoryMap(Map<String, String> projectCategoryMap) throws Throwable {
		projectCategoryMap.put("", "Please Select");
		projectCategoryMap.put(ProjectCategory.NEW_REVENUE.toString(), "New Revenue");
		projectCategoryMap.put(ProjectCategory.REVENUE_RETENTION.toString(), "Revenue Retention");

	}

	@Override
	public void getProjectSubCategoryMap(Map<String, String> projectSubCategoryMap) throws Throwable {
		projectSubCategoryMap.put("", "Please Select");
		projectSubCategoryMap.put(ProjectSubCategory.EOL.toString(), "End of Life");
		projectSubCategoryMap.put(ProjectSubCategory.INFRASTRUCTURE.toString(), "Infrastructure");
		projectSubCategoryMap.put(ProjectSubCategory.PRODUCTION_SUPPORT.toString(), "Production support");
		projectSubCategoryMap.put(ProjectSubCategory.SECURITY_MANDATE.toString(), "Security Mandate");
	}

	@Override
	public void getStatusMap(Map<String, String> statusMap) throws Throwable {
		statusMap.put("", "Please Select");
		statusMap.put(Status.EarlyStart.toString(), "Early Start");
		statusMap.put(Status.Execution.toString(), "Execution");
		statusMap.put(Status.Warranty.toString(), "Warranty");
		statusMap.put(Status.Cancelled.toString(), "Cancelled");
		statusMap.put(Status.Implemented.toString(), "Implemented");
	}

	@Override
	public Map<Long, String> getProductOwnersMap() throws Throwable {
		Map<Long, String> productOwnerMapObj = new LinkedHashMap<Long, String>();
		List<ProductOwner> domainProductOwnersList = daoManager.getProductOwnerDao().getProductOwners();
		for (ProductOwner productOwner : domainProductOwnersList) {
			productOwnerMapObj.put(productOwner.getId(), productOwner.getOwnerName());
		}
		return productOwnerMapObj;

	}

	@Override
	public NetworkCodes getNetworkCodeById(Long id) throws Throwable {
		NetworkCodes modalNetworkCodes = new NetworkCodes();
		com.egil.pts.dao.domain.NetworkCodes domainNetworkCodes = daoManager.getNetworkCodesDao().getNetworkCode(id);
		convertDomainToModal(modalNetworkCodes, domainNetworkCodes);
		return modalNetworkCodes;
	}

	@Override
	public String getJSONStringOfNetworkCodes(Long userId) throws Throwable {
		String jsonStr = "";
		jsonStr = -1 + ":" + "Please Select" + ";";
		if (userId != null && userId != 0l) {
			List<com.egil.pts.dao.domain.NetworkCodes> userNetworkCodeList = daoManager.getUserNetworkCodeDao()
					.getResourceNetworkCodes(userId, "", null);
			if (userNetworkCodeList != null && userNetworkCodeList.size() > 0) {
				for (com.egil.pts.dao.domain.NetworkCodes userNetworkCode : userNetworkCodeList) {
					jsonStr = jsonStr
							+ (userNetworkCode.getId() + ":"
									+ (userNetworkCode.getReleaseId() + " - " + userNetworkCode.getReleaseName()))
							+ ";";
				}
			}
			jsonStr = jsonStr.substring(0, jsonStr.length() - 1);
		}

		return jsonStr;
	}

	@Override
	public BulkResponse downloadNetworkCodes(String userId, HashMap<String, String> ncColHeaders,
			SearchSortContainer searchSortBean, String fileName) throws Throwable {
		DateFormat format = new SimpleDateFormat("dd-MMM-yyyy");
		BulkResponse response = new BulkResponse();
		try {

			List<NetworkCodes> ncList = new ArrayList<NetworkCodes>();
			ncList = getNetworkCodes(null, searchSortBean, null, null, null);

			if (ncList == null || ncColHeaders == null || StringHelper.isEmpty(userId)) {
				logger.error("Invalid input..! Either usersList or pbxColHeaders or userId is/are null");
				response.setErrorMessage("Invalid input..!");
				return response;
			}

			GenericExcel excel = new GenericExcel(fileName);
			String[][] userRecords = new String[ncList.size() + 1][ncColHeaders.size()];
			int colCount = 0;
			for (String colHeader : ncColHeaders.values()) {
				userRecords[0][colCount++] = colHeader;

			}
			for (int i = 0; i < ncList.size(); i++) {
				NetworkCodes nectorkCode = ncList.get(i);
				if (nectorkCode.getReleaseId() != null)
					userRecords[i + 1][0] = nectorkCode.getReleaseId();
				else
					userRecords[i + 1][0] = "";
				if (nectorkCode.getReleaseName() != null)
					userRecords[i + 1][1] = nectorkCode.getReleaseName();
				else
					userRecords[i + 1][1] = "";
				if (nectorkCode.getPillar() != null)
					userRecords[i + 1][2] = nectorkCode.getPillar();
				else
					userRecords[i + 1][2] = "";

				if (nectorkCode.getProject() != null)
					userRecords[i + 1][3] = nectorkCode.getProject();
				else
					userRecords[i + 1][3] = "";

				if (nectorkCode.getProgramManager() != null)
					userRecords[i + 1][4] = nectorkCode.getProgramManager();
				else
					userRecords[i + 1][4] = "";

				if (nectorkCode.getProjectManager() != null)
					userRecords[i + 1][5] = nectorkCode.getProjectManager();
				else
					userRecords[i + 1][5] = "";

				if (nectorkCode.getProductOwner() != null)
					userRecords[i + 1][6] = nectorkCode.getProductOwner();
				else
					userRecords[i + 1][6] = "";

				userRecords[i + 1][7] = nectorkCode.getStartDate() == null ? ""
						: format.format(nectorkCode.getStartDate());

				userRecords[i + 1][8] = nectorkCode.getEndDate() == null ? "" : format.format(nectorkCode.getEndDate());

				if (nectorkCode.getStatus() != null)
					userRecords[i + 1][9] = nectorkCode.getStatus() + "";
				else
					userRecords[i + 1][9] = "";

				if (nectorkCode.getTotalLocalLoe() != null)
					userRecords[i + 1][10] = nectorkCode.getTotalLocalLoe() + "";
				else
					userRecords[i + 1][10] = "";
				if (nectorkCode.getTotalGlobalLoe() != null)
					userRecords[i + 1][11] = nectorkCode.getTotalGlobalLoe() + "";
				else
					userRecords[i + 1][11] = "";

				if (nectorkCode.getReleaseType() != null)
					userRecords[i + 1][12] = nectorkCode.getReleaseType();
				else
					userRecords[i + 1][12] = "";

				if (nectorkCode.getProjectCategory() != null)
					userRecords[i + 1][13] = nectorkCode.getProjectCategory();
				else
					userRecords[i + 1][13] = "";

				if (nectorkCode.getProjectSubCategory() != null)
					userRecords[i + 1][14] = nectorkCode.getProjectSubCategory();
				else
					userRecords[i + 1][14] = "";

				userRecords[i + 1][15] = nectorkCode.getScopeCloseDate() == null ? ""
						: format.format(nectorkCode.getScopeCloseDate());

				if (nectorkCode.getSreqNo() != null)
					userRecords[i + 1][16] = nectorkCode.getSreqNo();
				else
					userRecords[i + 1][16] = "";

				String totalOriginalLOE = "";
				Long originalDesignLOE = nectorkCode.getOriginalDesignLOE() != null ? nectorkCode.getOriginalDesignLOE()
						: 0l;
				Long originalDevLOE = nectorkCode.getOriginalDevLOE() != null ? nectorkCode.getOriginalDevLOE() : 0l;
				Long originalTestLOE = nectorkCode.getOriginalTestLOE() != null ? nectorkCode.getOriginalTestLOE() : 0l;
				Long originalProjectManagementLOE = nectorkCode.getOriginalProjectManagementLOE() != null
						? nectorkCode.getOriginalProjectManagementLOE()
						: 0l;
				Long originalImplementationLOE = nectorkCode.getOriginalImplementationLOE() != null
						? nectorkCode.getOriginalImplementationLOE()
						: 0l;
				totalOriginalLOE = originalDesignLOE + originalDevLOE + originalTestLOE + originalProjectManagementLOE
						+ originalImplementationLOE + "";
				userRecords[i + 1][17] = totalOriginalLOE;

				userRecords[i + 1][18] = nectorkCode.getPlannedImplDate() == null ? ""
						: format.format(nectorkCode.getPlannedImplDate());
				userRecords[i + 1][19] = nectorkCode.getActualImplDate() == null ? ""
						: format.format(nectorkCode.getActualImplDate());

				if (nectorkCode.getTFSEpic() != null)
					userRecords[i + 1][20] = nectorkCode.getTFSEpic();
				else
					userRecords[i + 1][20] = "";

				if (nectorkCode.getTotalLOE() != null)
					userRecords[i + 1][20] = nectorkCode.getTotalLOE() + "";
				else
					userRecords[i + 1][20] = "";

			}

			excel.createSheet("Employee Details", userRecords);
			excel.setCustomRowStyleForHeader(0, 0, HSSFColor.GREY_40_PERCENT.index, 30);
			// excel.setRowFont(0,0,1);
			boolean status = excel.writeWorkbook();
			if (!status) {
				logger.error("Not able to create excelsheet..");
				response.setErrorMessage("Not able to create excelsheet..");
				response.setStatus("FAILURE");
				return response;
			}
			response.setStatus("SUCCESS");
			response.setFilePath(fileName);
		} catch (Throwable throwable) {
			logger.error(throwable);
		}
		if (logger.isInfoEnabled()) {
			logger.info("Manage Users Group Export" + " Table successfully processed");
		}
		return response;
	}

	@Override
	public String getJSONStringOfPhase() throws Throwable {
		String returnValue = "";
		returnValue = "" + ":" + "Please Select" + ";" + "Completed" + ":" + "Completed" + ";" + "Deployment" + ":"
				+ "Deployment" + ";" + "Development" + ":" + "Development" + ";" + "Feasibility" + ":" + "Feasibility"
				+ ";" + "Hold" + ":" + "Hold" + ";" + "System Test" + ":" + "System Test";

		return returnValue;
	}

	@Override
	@Transactional
	public void updateProjectPhase(String updatedBy, String projectStageCol, Long id, String projectStage,
			String description, String comments) throws Throwable {
		try {
			com.egil.pts.dao.domain.NetworkCodes nc = daoManager.getNetworkCodesDao().get(id);
			nc.setProjectStage(projectStage);
			nc.setUpdatedBy(updatedBy);
			nc.setUpdatedDate(new Date());
			nc.setDescription(description);
			nc.setProjectStageCol(projectStageCol);
			nc.setComments(comments);
			daoManager.getNetworkCodesDao().save(nc);
			daoManager.getNetworkCodesDao().flush();
		} catch (Throwable throwable) {
			logger.error(throwable);
		}

	}

	@Override
	@Transactional
	public void updateProject(com.egil.pts.modal.NetworkCodes networkCodes, String comments) throws Throwable {
		try {
			/*
			 * com.egil.pts.dao.domain.NetworkCodes nc =
			 * daoManager.getNetworkCodesDao().get(networkCodes.getId());
			 * 
			 * nc.setComments(comments); nc.setUpdatedDate(new Date());
			 * 
			 * nc.setProjectStage(networkCodes.getProjectStage());
			 * nc.setUpdatedBy(networkCodes.getUpdatedBy());
			 * nc.setDescription(networkCodes.getDescription());
			 * nc.setLocalDesignLOE(networkCodes.getLocalDesignLOE());
			 * nc.setLocalDevLOE(networkCodes.getLocalDevLOE());
			 * nc.setLocalTestLOE(networkCodes.getLocalTestLOE());
			 * nc.setLocalImplementationLOE(networkCodes.getLocalImplementationLOE());
			 * nc.setLocalProjectManagementLOE(networkCodes.getLocalProjectManagementLOE());
			 * nc.setLocalKitLOE(networkCodes.getLocalKitLOE());
			 * 
			 * nc.setGlobalDesignLOE(networkCodes.getLocalDesignLOE());
			 * nc.setGlobalDevLOE(networkCodes.getLocalDevLOE());
			 * nc.setGlobalTestLOE(networkCodes.getLocalTestLOE());
			 * nc.setGlobalImplementationLOE(networkCodes.getLocalImplementationLOE());
			 * nc.setGlobalProjectManagementLOE(networkCodes.getLocalProjectManagementLOE())
			 * ; nc.setGlobalKitLOE(networkCodes.getGlobalKitLOE());
			 */

			daoManager.getNetworkCodesDao().updatePMProjectPhase(networkCodes, comments);

			daoManager.getNetworkCodesDao().flush();
			daoManager.getNetworkCodesDao().clear();
		} catch (Throwable throwable) {
			throwable.printStackTrace();
			logger.error(throwable);
		}

	}

	@Override
	public void updateInsertProjectData(String uploadFileFileName) {

		List<NetworkCodes> projectDatas = new ArrayList<>();
		try {
			GenericExcel excel = new GenericExcel("/applications/tomcat/project/" + uploadFileFileName);
			String[][] excelContents = excel.readSheet(1);
			NetworkCodes nw = null;
			String[] excelHeaders = excelContents[0];

			for (int row = 1; row < excelContents.length; row++) {
				try {

					nw = new NetworkCodes();
					int col = 0;
					for (String colStr : excelHeaders) {

						if (colStr.contains("TFS Epic")) {
							if (!excelContents[row][col].equalsIgnoreCase("#N/A")) {
								Long.parseLong(excelContents[row][col]);
								nw.setTFSEpic(excelContents[row][col]);
							}
						}
						if (colStr.contains("TFS Release ID")) {
							if (!excelContents[row][col + 2].equalsIgnoreCase("#N/A"))
								nw.setReleaseId(excelContents[row][col + 2]);
						}
						if (colStr.contains("TFS Project Name")) {
							if (!excelContents[row][col + 4].equalsIgnoreCase("#N/A"))
								nw.setReleaseName(excelContents[row][col + 4]);
						}
						if (colStr.contains("TFS Pillar")) {
							if (!excelContents[row][col + 6].equalsIgnoreCase("#N/A"))
								nw.setPillar(excelContents[row][col + 6]);
						}
						if (colStr.contains("TFS Platform")) {
							if (!excelContents[row][col + 8].equalsIgnoreCase("#N/A"))
								nw.setProjectName(excelContents[row][col + 8]);
						}
						if (colStr.contains("TFS Project Manager")) {
							if (!excelContents[row][col + 11].equalsIgnoreCase("#N/A"))
								nw.setProjectManager(excelContents[row][col + 11]);
						}

						if (colStr.contains("TFS Start date")) {
							if (!excelContents[row][col + 13].contains("#N/A")) {
								Calendar c = Calendar.getInstance();
								String[] dateSplit = excelContents[row][col + 13].split("-");
								c.set(Integer.parseInt(dateSplit[2]), monthMap.get(dateSplit[1]),
										Integer.parseInt(dateSplit[0]));
								nw.setStartDate(c.getTime());
							}
						}
						if (colStr.contains("TFS Planned Implementation date")) {
							if (!excelContents[row][col + 15].contains("#N/A")) {
								Calendar c = Calendar.getInstance();
								String[] dateSplit = excelContents[row][col + 15].split("-");
								c.set(Integer.parseInt(dateSplit[2]), monthMap.get(dateSplit[1]),
										Integer.parseInt(dateSplit[0]));
								nw.setPlannedImplDate(c.getTime());
							}
						}
						if (colStr.contains("TFS Status")) {
							if (!excelContents[row][col + 17].equalsIgnoreCase("#N/A"))
								nw.setStatusTemp(excelContents[row][col + 17]);
						}
						if (colStr.contains("Approved Total LOE")) {
							if (!excelContents[row][col + 18].equalsIgnoreCase("#N/A"))
								nw.setTotalLOE(Long.parseLong(excelContents[row][col + 18]));
						}

					}
					projectDatas.add(nw);
				} catch (Exception e) {
					continue;
				}
			}
			daoManager.getNetworkCodesDao().updateProjectTFData(projectDatas);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void convertDaoToMap(Map<Long, String> map, List<Object[]> dao) {
		for (Object[] daoData : dao) {
			map.put(Long.valueOf((Integer) daoData[0]), (String) daoData[2]);
		}
	}

	@Override
	public void getStableTeamsMap(Map<Long, String> stableTeamsmap) {
		List<StableTeams> teams = daoManager.getNetworkCodesDao().getStableTeams();
		for (StableTeams data : teams)
			stableTeamsmap.put(data.getId(), data.getTeamName());
	}

	@Override
	public List<StableTeams> getStableTeams(List<StableTeams> stableTeams) {
		return daoManager.getNetworkCodesDao().getStableTeams();

	}

	@Override
	public Long getStableTeamByNW(String networkCodeId) {
		return daoManager.getNetworkCodesDao().getStableTeamByNw(networkCodeId);
	}

	@Override
	public List<NetworkCodes> getMislaneiousProjects() {
		return daoManager.getNetworkCodesDao().getMislaniousNWCodes();

	}

	@Override
	public List<StableTeams> getUserStableTeams(Long user) {
		return daoManager.getNetworkCodesDao().getStableTeamsByUser(user);
	}

	@Override
	public void saveUserStableTeams(List<StableTeams> data) {
		daoManager.getNetworkCodesDao().saveStableTeamsByUser(data);
	}

	@Override
	public List<NetworkCodeEffort> getUserDashBoardLoeDetails(Long userId, boolean showAllMyContributions) {
		return daoManager.getUserUtilizationDao().getUserDashBoardLoeDetails(userId, showAllMyContributions);
	}

	public boolean addUpdateUserNwStableContribution(List<com.egil.pts.modal.NetworkCodes> codes,
			SearchSortContainer searchSortContainer, Long totalStoryPoints) {
		return daoManager.getUserNetworkCodeDao().addUpdateUserNwStableContribution(codes, searchSortContainer,
				totalStoryPoints);
	}

	public List<NetworkCodes> getUserNwStableContribution(Pagination paginationObject,
			List<NetworkCodes> networkGridModel, String release) {
		return daoManager.getUserNetworkCodeDao().getUserNwStableContribution(paginationObject, release);
	}

	@Override
	public void getAssignedNetworkCodesMapDeleted(Long selectedEmployee, Map<Long, String> assignedNetworkCodesMap,
			Date fromDate) throws Throwable {

		if (assignedNetworkCodesMap == null) {
			assignedNetworkCodesMap = new LinkedHashMap<Long, String>();
		} else {
			assignedNetworkCodesMap.clear();
		}

		List<NetworkCodes> userNetworkCodes = daoManager.getUserNetworkCodeDao()
				.getResourceNetworkCodesDetailsDeleted(selectedEmployee, fromDate);

		if (userNetworkCodes != null) {
			for (com.egil.pts.modal.NetworkCodes un : userNetworkCodes) {
				assignedNetworkCodesMap.put(Long.parseLong(un.getNetworkCodeId()),
						(un.getReleaseId() + " - " + un.getReleaseName()));
			}
		}

	}

	@Override
	public void getUserCapacityByRelease(Map<Long, Long> userCapacityByRelease, Long selectedEmployee) {
		List<Object[]> contribution = daoManager.getUserNetworkCodeDao().getUserCapacityByRelease(selectedEmployee);
		for (Object[] a : contribution) {
			userCapacityByRelease.put((Long) a[0], (Long) a[1]);
		}

	}
}
