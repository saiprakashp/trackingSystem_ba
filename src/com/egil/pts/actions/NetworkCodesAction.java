package com.egil.pts.actions;

import java.io.File;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.interceptor.validation.SkipValidation;
import org.hibernate.internal.util.StringHelper;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;

import com.egil.pts.modal.BulkResponse;
import com.egil.pts.modal.NetworkCodes;
import com.egil.pts.modal.Pagination;
import com.egil.pts.modal.SearchSortContainer;
import com.egil.pts.modal.StableTeams;
import com.egil.pts.modal.Status;
import com.egil.pts.modal.SummaryResponse;
import com.egil.pts.util.Utility;

@Controller("networkCodesAction")
@Scope("prototype")
public class NetworkCodesAction extends PTSAction {

	private static final long serialVersionUID = 1L;
	private List<NetworkCodes> networkGridModel = new ArrayList<NetworkCodes>();
	private Long totalStoryPoints;
	private Long selectedNetworkCodeId;
	private boolean assignDefNeContribution;
	private String customer;
	private Integer editFlag;
	private String customerName;
	private String pillar;
	private String project;
	private String release;
	private String showUserNwCapacity;
	private String networkCodeId;
	private String networkCode;
	private String description;
	private String comments;
	private String effort;
	private String createdBy;
	private String projColor;
	private Date createdDate;
	private Date startDate;
	private Date endDate;
	private String status;
	private String projectCategory;
	private String projectSubCategory;
	private String selectedProjectCategory;
	private Long totalGlobalLOE;
	private Long totalLocalLOE;
	private String programManager;
	private String projectManager;
	private boolean showAllResources;
	private Date plannedDesignDate;
	private Date actualDesignDate;

	private Date plannedDevDate;
	private Date actualDevDate;

	private Date plannedTestDate;
	private Date actualTestDate;

	private Date plannedImplDate;
	private Date actualImplDate;

	private Date plannedOprHandoffDate;
	private Date actualOprHandoffDate;

	private String globalDesignLOE;
	private String globalDevLOE;
	private String globalTestLOE;
	private String globalImplementationLOE;
	private String globalOperationsHandOffLOE;
	private String globalProjectManagementLOE;
	private String globalOthersLOE;
	private String globalKitLOE;

	private String localDesignLOE;
	private String localDevLOE;
	private String localTestLOE;
	private String localImplementationLOE;
	private String localOperationsHandOffLOE;
	private String localProjectManagementLOE;
	private String localOthersLOE;
	private String localKitLOE;

	private String originalDesignLOE;
	private String originalDevLOE;
	private String originalTestLOE;
	private String originalProjectManagementLOE;
	private String originalImplementationLOE;
	private String originalOperationsHandOffLOE;
	private String originalKitLOE;

	private String productOwner;

	private String subProjectCategoryData;
	private String projectCategoryMap;
	private String subProjectCategoryMap;
	private String statusMap;
	private String projectsMap;
	private String productOwnersMap;
	private String pillarsMap;
	private String customersMap;
	private String programManagersMap;
	private String projectManagersMap;
	private String phaseMap;

	private String sreqNo;

	private String priority;

	private String searchNetworkId;
	private String searchReleaseId;
	private String searchReleaseName;
	private String searchPM;
	private String searchStatus;

	private String scheduleVariance;
	private String rollbackVolume;
	private String devTestEffectiveness;

	private String searchSupervisor;
	private Map<Long, String> stableTeamsmap = new LinkedHashMap<Long, String>();
	private String projectStage;
	private String projectType;
	private Long stableTeam;
	// get how many rows we want to have into the grid - rowNum attribute in the
	// grid
	private Integer rows = 0;

	// Get the requested page. By default grid sets this to 1.
	private Integer page = 0;

	// sorting order - asc or desc
	private String sord;

	// get index row - i.e. user click to sort.
	private String sidx;

	// Search Field
	private String searchField;

	// The Search String
	private String searchString;

	// Limit the result when using local data, value form attribute rowTotal
	private Integer totalrows;

	// he Search Operation
	// ['eq','ne','lt','le','gt','ge','bw','bn','in','ni','ew','en','cn','nc']
	private String searchOper;

	private String oper;

	// Your Total Pages
	private Integer total = 0;

	// All Records
	private Integer records = 0;

	private boolean loadonce = false;
	private Long totalLOE;
	private Map<Long, String> networkCodesMap = new LinkedHashMap<Long, String>();
	private Map<Long, String> releasesMapObj = new LinkedHashMap<Long, String>();
	private String selectedEmployee;
	private Map<Long, String> selectedEmployeesMap = new LinkedHashMap<Long, String>();
	private Map<Long, String> employeeMap = new LinkedHashMap<Long, String>();
	private List<Long> selectedEmployeeNames = new ArrayList<Long>();

	private Map<Long, String> employeeList = new LinkedHashMap<Long, String>();
	private Map<Long, String> selectedNetworkCodesMap = new LinkedHashMap<Long, String>();
	private List<Long> selectedNetworkCodeNames = new ArrayList<Long>();

	private String searchNetworkCode;

	private Map<Long, String> customerMapObj = new LinkedHashMap<Long, String>();
	private Map<Long, String> pillarsMapObj = new LinkedHashMap<Long, String>();
	private Map<Long, String> projectsMapObj = new LinkedHashMap<Long, String>();
	private Map<String, String> projectCategoryMapObj = new LinkedHashMap<String, String>();
	private Map<String, String> projectSubCategoryMapObj = new LinkedHashMap<String, String>();
	private Map<String, String> statusMapObj = new LinkedHashMap<String, String>();

	private Map<Long, String> productOwnerMapObj = new LinkedHashMap<Long, String>();
	private Map<Long, String> programManagersMapObj = new LinkedHashMap<Long, String>();
	private Map<Long, String> projectManagersMapObj = new LinkedHashMap<Long, String>();

	private String strStartDate;
	private String strEndDate;

	private String strPlannedDesignDate;
	private String strActualDesignDate;

	private String strPlannedDevDate;
	private String strActualDevDate;

	private String strPlannedTestDate;
	private String strActualTestDate;

	private String strPlannedImplDate;
	private String strActualImplDate;

	private String strPlannedOprHandoffDate;
	private String strActualOprHandoffDate;

	private Date scopeCloseDate;
	private Date changeControlDate;
	private Date rollbackDate;

	private String strScopeCloseDate;
	private String strChangeControlDate;
	private String strRollbackDate;

	private Date originalDesignDate;
	private Date originalDevDate;
	private Date originalTestDate;
	private Date originalImplDate;
	private Date originalOprHandoffDate;
	private Date originalWarrantyCompletionDate;

	private String strOriginalDesignDate;
	private String strOriginalDevDate;
	private String strOriginalTestDate;
	private String strOriginalImplDate;
	private String strOriginalOprHandoffDate;
	private String strOriginalWarrantyCompletionDate;
	private String strPlannedWarrantyCompletionDate;

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

	private Date plannedWarrantyCompletionDate;
	private String projectColor;

	private String projectLevel;
	private String TFSEpic;
	private File uploadFile;
	private String uploadFileContentType;

	private String uploadFileFileName;
	private boolean isForinterns;

	@SkipValidation
	public String execute() {
		try {
			if (oper != null && !oper.isEmpty()) {
				TFSEpic = "";
			}
			clearSession("supervisorMap", "userToBackFillMap", "privilegesMap", "rolesMap", "streamsMap",
					"platformsMap", "userTypesMap", "technologiesMap");

			serviceManager.getNetworkCodesService().getStatusMap(statusMapObj);

			phaseMap = serviceManager.getNetworkCodesService().getJSONStringOfPhase();

			/*
			 * customersMap = serviceManager.getCustomerService()
			 * .getJSONStringOfCustomers(); pillarsMap = serviceManager.getPillarService()
			 * .getJSONStringOfPillars(); projectsMap = serviceManager.getProjectService()
			 * .getJSONStringOfProjects();
			 * 
			 * statusMap = serviceManager.getNetworkCodesService() .getJSONStringOfStatus();
			 * projectCategoryMap = serviceManager.getNetworkCodesService()
			 * .getJSONStringOfProjectCategory(); subProjectCategoryMap =
			 * serviceManager.getNetworkCodesService()
			 * .getJSONStringOfProjectSubCategory("");
			 * 
			 * productOwnersMap = serviceManager.getNetworkCodesService()
			 * .getJSONStringOfProductOwners();
			 * 
			 * projectManagersMap = serviceManager.getUserService()
			 * .getProjectManagersMap();
			 * 
			 * programManagersMap = serviceManager.getUserService()
			 * .getProgramManagersMap();
			 */

		} catch (Throwable e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	@SkipValidation
	public String manageUserNetworkContribution() {
		try {
			if (!StringUtils.isEmpty(getRelease())) {
				goGetUserNwMap();
			} else {
				networkGridModel.clear();
				releasesMapObj = new LinkedHashMap<Long, String>();
				if (session.get("role") != null && StringHelper.isNotEmpty((String) session.get("role"))
						&& ((String) session.get("role")).equalsIgnoreCase("ADMIN")) {
					serviceManager.getNetworkCodesService().getNetworkCodesMap(releasesMapObj, null, null);
				} else {
					serviceManager.getNetworkCodesService().getResourceNetworkCodes((Long) session.get("userId"),
							releasesMapObj, null, null);
					searchSupervisor = (Long) session.get("userId") + "";
				}
				goAssignedResources();
				goPillarsMap();
			}

		} catch (Throwable e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	@SkipValidation
	public String goDownloadUsernwContribution() {
		try {
			goGetUserNwMap();
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	@SkipValidation
	public String addUserNetworkContribution() {
		try {
			serviceManager.getNetworkCodesService().addUpdateUserNwStableContribution(networkGridModel,
					getSearchSortBean(),totalStoryPoints);
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	public void goGetUserNwMap() {
		try {
			String release = getRelease();
			// networkGridModel
			releasesMapObj = new LinkedHashMap<Long, String>();
			if (session.get("role") != null && StringHelper.isNotEmpty((String) session.get("role"))
					&& ((String) session.get("role")).equalsIgnoreCase("ADMIN")) {
				serviceManager.getNetworkCodesService().getNetworkCodesMap(releasesMapObj, null, null);
			} else {

				serviceManager.getNetworkCodesService().getResourceNetworkCodes((Long) session.get("userId"),
						releasesMapObj, null, null);

				searchSupervisor = (Long) session.get("userId") + "";
			}

			Iterator<Long> colIterator = releasesMapObj.keySet().iterator();

			while (colIterator.hasNext()) {
				Long releasId = colIterator.next();
				if (releasesMapObj.get(releasId).contains(release)) {
					release = releasId + "";
					break;
				}
			}
			
			networkGridModel = serviceManager.getNetworkCodesService()
					.getUserNwStableContribution(getPaginationObject(), networkGridModel, release);
			
			if(networkGridModel.size() > 0) {
				totalStoryPoints = (networkGridModel.get(0).getTotalStoryPoints());
			}
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

	@SkipValidation
	public String loadProjectSubCategory() {
		try {
			subProjectCategoryData = serviceManager.getNetworkCodesService()
					.getJSONStringOfProjectSubCategory(selectedProjectCategory);
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	@SkipValidation
	public String goManageNetworkCodes() {
		try {
			if (session.get("rowNum") == null) {
				session.put("rowNum", rows);
			} else {
				if (rows != null && (rows.intValue() != ((Integer) session.get("rowNum")).intValue())) {
					session.put("rowNum", rows);
				}
			}

			SummaryResponse<NetworkCodes> summary = serviceManager.getNetworkCodesService()
					.getNetworkCodesSummary(getPaginationObject(), getSearchSortBean());
			networkGridModel = summary.getEnitities();
			setRecords(summary.getTotalRecords());
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	@SkipValidation
	public String networkMACDOperation() {
		NetworkCodes networkCodeObj = new NetworkCodes();
		try {
			if (oper.equalsIgnoreCase("add")) {
				setNetworkObjToSave(networkCodeObj);
				networkCodeObj.setCreatedBy((String) session.get("username"));
				networkCodeObj.setUpdatedBy((String) session.get("username"));
				networkCodeObj.setCreatedDate(new Date());
				networkCodeObj.setUpdatedDate(new Date());
				serviceManager.getNetworkCodesService().createNetworkCode(networkCodeObj);

				selectedEmployeeNames = new ArrayList<Long>();
				selectedEmployeeNames = serviceManager.getUserService().getResourcesToAddProjects();
				/*
				 * if (session.get("role") != null && StringHelper.isNotEmpty((String)
				 * session.get("role")) && !((String)
				 * session.get("role")).equalsIgnoreCase("ADMIN")) {
				 * selectedEmployeeNames.add((Long) session.get("userId")); }
				 */

				serviceManager.getNetworkCodesService().mapNetworkCodeToResources((Long) session.get("userId"),
						networkCodeObj.getId(), selectedEmployeeNames);

			} else if (oper.equalsIgnoreCase("edit")) {
				networkCodeObj.setId(Long.valueOf(id));
				setNetworkObjToSave(networkCodeObj);
				networkCodeObj.setCreatedBy(createdBy);
				networkCodeObj.setCreatedDate(createdDate);
				networkCodeObj.setUpdatedBy((String) session.get("username"));
				networkCodeObj.setUpdatedDate(new Date());
				serviceManager.getNetworkCodesService().modifyNetworkCode(networkCodeObj);
			} else if (oper.equalsIgnoreCase("del")) {
				if (id != null & !id.equalsIgnoreCase("")) {
					serviceManager.getNetworkCodesService().deleteNetworkCodes(Utility.getListFromCommaSeparated(id));
				}
			}

		} catch (Throwable e) {
			e.printStackTrace();
		}

		return SUCCESS;
	}

	@SkipValidation
	public String updateProjectPhase() {
		try {
			if (id != null & !id.equalsIgnoreCase("")) {
				com.egil.pts.modal.NetworkCodes networkCodes = new com.egil.pts.modal.NetworkCodes();
				networkCodes.setUpdatedBy((String) session.get("username"));
				networkCodes.setProjectStage(projectStage);
				networkCodes.setDescription(description);
				networkCodes.setStableTeam(stableTeam);
				networkCodes.setLocalDesignLOE((localDesignLOE != null) ? (Long.parseLong(localDesignLOE)) : -1);
				networkCodes.setLocalDevLOE((localDevLOE != null) ? (Long.parseLong(localDevLOE)) : -1);
				networkCodes.setLocalTestLOE((localTestLOE != null) ? (Long.parseLong(localTestLOE)) : -1);
				networkCodes.setLocalImplementationLOE(
						(localImplementationLOE != null) ? (Long.parseLong(localImplementationLOE)) : -1);
				networkCodes.setLocalProjectManagementLOE(
						(localProjectManagementLOE != null) ? (Long.parseLong(localProjectManagementLOE)) : -1);
				networkCodes.setLocalKitLOE((localKitLOE != null) ? (Long.parseLong(localKitLOE)) : -1);

				networkCodes.setGlobalDesignLOE((globalDesignLOE != null) ? (Long.parseLong(globalDesignLOE)) : -1);
				networkCodes.setGlobalDevLOE((globalDevLOE != null) ? (Long.parseLong(globalDevLOE)) : -1);
				networkCodes.setGlobalImplementationLOE(
						(globalImplementationLOE != null) ? (Long.parseLong(globalImplementationLOE)) : -1);
				networkCodes.setGlobalProjectManagementLOE(
						(globalProjectManagementLOE != null) ? (Long.parseLong(globalProjectManagementLOE)) : -1);
				networkCodes.setGlobalTestLOE((globalTestLOE != null) ? (Long.parseLong(globalTestLOE)) : -1);
				networkCodes.setGlobalKitLOE((globalKitLOE != null) ? (Long.parseLong(globalKitLOE)) : -1);
				networkCodes.setProjectStageCol(
						(projectColor == null || (projectColor != null && projectColor.isEmpty()) ? "black"
								: projectColor));
				networkCodes.setId(Long.parseLong(id));
				networkCodes.setTFSEpic((TFSEpic != null && !TFSEpic.isEmpty()) ? TFSEpic : "0");
				networkCodes.setTotalGlobalLOE((totalGlobalLOE != null) ? totalGlobalLOE : 0L);
				networkCodes.setTotalLocalLOE((totalLocalLOE != null) ? totalLocalLOE : 0L);
				networkCodes.setTotalLOE(totalLOE);
				networkCodes.setStatus(Status.getEnumValueOf(status));
				serviceManager.getNetworkCodesService().updateProject(networkCodes, comments);
			}
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	@SkipValidation
	public String goMapResourcesToNetworkCode() {
		try {
			releasesMapObj = new LinkedHashMap<Long, String>();
			if (session.get("role") != null && StringHelper.isNotEmpty((String) session.get("role"))
					&& ((String) session.get("role")).equalsIgnoreCase("ADMIN")) {
				serviceManager.getNetworkCodesService().getNetworkCodesMap(releasesMapObj, null, null);
			} else {
				serviceManager.getNetworkCodesService().getResourceNetworkCodes((Long) session.get("userId"),
						releasesMapObj, null, null);
				searchSupervisor = (Long) session.get("userId") + "";
			}
			goAssignedResources();
			goPillarsMap();
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	@SkipValidation
	public String goAssignedResources() {
		try {
			selectedEmployeesMap = new LinkedHashMap<Long, String>();
			serviceManager.getNetworkCodesService().getNetworkCodeResources(showAllResources,
					(Long) session.get("userId"), selectedNetworkCodeId, selectedEmployeesMap);
			employeeMap = new LinkedHashMap<Long, String>();
			serviceManager.getUserService().getUserListToAssignNetworkCode(showAllResources,
					(Long) session.get("userId"), employeeMap,
					(selectedEmployeesMap != null && selectedEmployeesMap.size() > 0) ? selectedEmployeesMap.keySet()
							: null,
					selectedEmployee, selectedNetworkCodeId);

		} catch (Throwable e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	@SkipValidation
	public String addResourcesToNetworkCodes() {
		try {

			serviceManager.getNetworkCodesService().mapNetworkCodeToResources((Long) session.get("userId"),
					selectedNetworkCodeId, selectedEmployeeNames);
			goMapResourcesToNetworkCode();
			releasesMapObj.clear();
			projectsMapObj.clear();
			boolean isAdmin = false;
			if (session.get("role") != null && ((String) session.get("role")).equalsIgnoreCase("ADMIN")) {
				isAdmin = true;
			}
			serviceManager.getNetworkCodeReportService().getProjectsMap(projectsMapObj, pillar, searchSupervisor,
					isAdmin);
			serviceManager.getNetworkCodeReportService().getReleaseMap(releasesMapObj, project, searchSupervisor,
					isAdmin, null, null, null);
			addActionMessage("Successfully saved..");
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	@SkipValidation
	public String goMapNetworkCodes() {
		try {
			/*
			 * serviceManager.getUserService().getUserList( (Long) session.get("userId"),
			 * employeeList, null, null);
			 */
			goAssignNetworkCodes();
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	@SkipValidation
	public String goAssignNetworkCodes() {
		try {
			selectedNetworkCodesMap = new LinkedHashMap<Long, String>();
			serviceManager.getNetworkCodesService().getResourceNetworkCodes(Long.valueOf(
					StringHelper.isEmpty(selectedEmployee) ? (Long) session.get("userId") + "" : selectedEmployee),
					selectedNetworkCodesMap, null, null);
			networkCodesMap = new LinkedHashMap<Long, String>();
			/*
			 * if (session.get("role") != null && StringHelper.isNotEmpty((String)
			 * session.get("role")) && ((String)
			 * session.get("role")).equalsIgnoreCase("ADMIN")) {
			 */
			serviceManager.getNetworkCodesService().getNetworkCodesMap(networkCodesMap, searchNetworkCode,
					(selectedNetworkCodesMap != null && selectedNetworkCodesMap.size() > 0)
							? selectedNetworkCodesMap.keySet()
							: null);
			/*
			 * } else { serviceManager .getNetworkCodesService() .getResourceNetworkCodes(
			 * (Long) session.get("userId"), networkCodesMap, searchNetworkCode,
			 * (selectedNetworkCodesMap != null && selectedNetworkCodesMap .size() > 0) ?
			 * selectedNetworkCodesMap .keySet() : null); }
			 */
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	@SkipValidation
	public String addNetworkCodesToUser() {
		try {
			serviceManager.getUserService().mapResourceNetworkCodes(Long.valueOf(
					StringHelper.isEmpty(selectedEmployee) ? (Long) session.get("userId") + "" : selectedEmployee),
					selectedNetworkCodeNames);
			goMapNetworkCodes();
			addActionMessage("Successfully saved..");
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	@SkipValidation
	public String goCreateNetworkCode() {
		try {
			// serviceManager.getCustomerService().getCustomersMap(customerMapObj);
			customer = "1";
			customerName = "Verizon";
			goPillarsMap();
			serviceManager.getNetworkCodesService().getProjectCategoryMap(projectCategoryMapObj);
			serviceManager.getNetworkCodesService().getProjectSubCategoryMap(projectSubCategoryMapObj);
			serviceManager.getNetworkCodesService().getStatusMap(statusMapObj);
			productOwnerMapObj = serviceManager.getNetworkCodesService().getProductOwnersMap();

			getStableTeamsmap().clear();
			List<StableTeams> stableTeams = serviceManager.getNetworkCodesService().getStableTeams(null);

			for (StableTeams s : stableTeams)
				getStableTeamsmap().put(s.getId(), s.getTeamName());

			goProgramManagersMap();
			status = Status.ACTIVE.toString();
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	@SkipValidation
	public String goEditNetworkCode() {
		try {
			// serviceManager.getCustomerService().getCustomersMap(customerMapObj);
			goPillarsMap();
			serviceManager.getNetworkCodesService().getProjectCategoryMap(projectCategoryMapObj);
			serviceManager.getNetworkCodesService().getProjectSubCategoryMap(projectSubCategoryMapObj);
			serviceManager.getNetworkCodesService().getStatusMap(statusMapObj);
			productOwnerMapObj = serviceManager.getNetworkCodesService().getProductOwnersMap();
			NetworkCodes nc = serviceManager.getNetworkCodesService().getNetworkCodeById(Long.valueOf(id));
			id = nc.getId() + "";
			stableTeam = nc.getStableTeam();

			getStableTeamsmap().clear();
			List<StableTeams> stableTeams = serviceManager.getNetworkCodesService().getStableTeams(null);

			for (StableTeams s : stableTeams)
				getStableTeamsmap().put(s.getId(), s.getTeamName());

			isForinterns = nc.isForInterns();
			networkCodeId = nc.getNetworkCodeId();
			networkCode = nc.getNetworkCode();
			description = nc.getDescription();
			customer = nc.getCustomerId() != null ? nc.getCustomerId() + "" : "";
			customerName = nc.getCustomer();
			goPillarsMap();
			pillar = nc.getPillarId() != null ? nc.getPillarId() + "" : "";
			goProjectsMap();
			project = nc.getProjectId() != null ? nc.getProjectId() + "" : "";
			priority = nc.getPriority();
			projectCategory = nc.getProjectCategory();
			projectSubCategory = nc.getProjectSubCategory();
			status = nc.getStatus() != null ? nc.getStatus().toString() : "";
			productOwner = nc.getProductOwnerId() + "";
			goProgramManagersMap();
			programManager = nc.getProgramManagerId() + "";
			goProjectManagersMap();
			projectManager = nc.getProjectManagerId() + "";
			startDate = nc.getStartDate();
			endDate = nc.getEndDate();
			sreqNo = nc.getSreqNo();

			plannedDesignDate = nc.getPlannedDesignDate();
			plannedDevDate = nc.getPlannedDevDate();
			plannedTestDate = nc.getPlannedTestDate();
			plannedImplDate = nc.getPlannedImplDate();
			plannedOprHandoffDate = nc.getPlannedOprHandoffDate();

			actualDesignDate = nc.getActualDesignDate();
			actualDevDate = nc.getActualDevDate();
			actualTestDate = nc.getActualTestDate();
			actualImplDate = nc.getActualImplDate();
			actualOprHandoffDate = nc.getActualOprHandoffDate();

			globalDesignLOE = nc.getGlobalDesignLOE() != null ? nc.getGlobalDesignLOE() + "" : "0";
			globalDevLOE = nc.getGlobalDevLOE() != null ? nc.getGlobalDevLOE() + "" : "0";
			globalTestLOE = nc.getGlobalTestLOE() != null ? nc.getGlobalTestLOE() + "" : "0";
			globalImplementationLOE = nc.getGlobalImplementationLOE() != null ? nc.getGlobalImplementationLOE() + ""
					: "0";
			globalOperationsHandOffLOE = nc.getGlobalOperationsHandOffLOE() != null
					? nc.getGlobalOperationsHandOffLOE() + ""
					: "0";
			globalProjectManagementLOE = nc.getGlobalProjectManagementLOE() != null
					? nc.getGlobalProjectManagementLOE() + ""
					: "0";
			globalOthersLOE = nc.getGlobalOthersLOE() != null ? nc.getGlobalOthersLOE() + "" : "0";
			globalKitLOE = nc.getGlobalKitLOE() != null ? nc.getGlobalKitLOE() + "" : "0";

			localDesignLOE = nc.getLocalDesignLOE() != null ? nc.getLocalDesignLOE() + "" : "0";
			localDevLOE = nc.getLocalDevLOE() != null ? nc.getLocalDevLOE() + "" : "0";
			localTestLOE = nc.getLocalTestLOE() != null ? nc.getLocalTestLOE() + "" : "0";
			localImplementationLOE = nc.getLocalImplementationLOE() != null ? nc.getLocalImplementationLOE() + "" : "0";
			localOperationsHandOffLOE = nc.getLocalOperationsHandOffLOE() != null
					? nc.getLocalOperationsHandOffLOE() + ""
					: "0";
			localProjectManagementLOE = nc.getLocalProjectManagementLOE() != null
					? nc.getLocalProjectManagementLOE() + ""
					: "0";
			localOthersLOE = nc.getLocalOthersLOE() != null ? nc.getLocalOthersLOE() + "" : "0";
			localKitLOE = nc.getLocalKitLOE() != null ? nc.getLocalKitLOE() + "" : "0";

			originalDesignLOE = nc.getOriginalDesignLOE() != null ? nc.getOriginalDesignLOE() + "" : "0";
			originalDevLOE = nc.getOriginalDevLOE() != null ? nc.getOriginalDevLOE() + "" : "0";
			originalTestLOE = nc.getOriginalTestLOE() != null ? nc.getOriginalTestLOE() + "" : "0";
			originalProjectManagementLOE = nc.getOriginalProjectManagementLOE() != null
					? nc.getOriginalProjectManagementLOE() + ""
					: "0";
			originalImplementationLOE = nc.getOriginalImplementationLOE() != null
					? nc.getOriginalImplementationLOE() + ""
					: "0";
			originalOperationsHandOffLOE = nc.getOriginalOperationsHandOffLOE() != null
					? nc.getOriginalOperationsHandOffLOE() + ""
					: "0";
			originalKitLOE = nc.getOriginalKitLOE() != null ? nc.getOriginalKitLOE() + "" : "0";

			createdBy = nc.getCreatedBy();
			createdDate = nc.getCreatedDate();

			releaseId = nc.getReleaseId();
			releaseName = nc.getReleaseName();
			releaseType = nc.getReleaseType();

			changeControl = nc.getChangeControl();
			changeControlNo = nc.getChangeControlNo();
			changeControlStatus = nc.getChangeControlStatus();
			changeControlImpact = nc.getChangeControlImpact();

			rollback = nc.getRollback();
			rollbackNo = nc.getRollbackNo();
			rollbackReason = nc.getRollbackReason();

			scopeCloseDate = nc.getScopeCloseDate();
			changeControlDate = nc.getChangeControlDate();
			rollbackDate = nc.getRollbackDate();

			originalDesignDate = nc.getOriginalDesignDate();
			originalDevDate = nc.getOriginalDevDate();
			originalTestDate = nc.getOriginalTestDate();
			originalImplDate = nc.getOriginalImplDate();
			originalOprHandoffDate = nc.getOriginalOprHandoffDate();
			originalWarrantyCompletionDate = nc.getOriginalWarrantyCompletionDate();
			plannedWarrantyCompletionDate = nc.getPlannedWarrantyCompletionDate();

			scheduleVariance = nc.getScheduleVariance();
			rollbackVolume = nc.getRollbackVolume();
			devTestEffectiveness = nc.getDevTestEffectiveness();

			projectStage = nc.getProjectStage();
			projectType = nc.getProjectType();
			projectLevel = nc.getProjectLevel();

			TFSEpic = nc.getTFSEpic();
			totalGlobalLOE = nc.getTotalGlobalLOE();
			totalLocalLOE = nc.getTotalLocalLOE();
			totalLOE = nc.getTotalLOE();
			setEditFlag(nc.getEditFlag());
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	@SkipValidation
	public String addNetworkCode() {
		try {
			oper = "add";
			networkMACDOperation();
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	@SkipValidation
	public String modifyNetworkCode() {
		try {
			oper = "edit";
			networkMACDOperation();

		} catch (Throwable e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	@SkipValidation
	public String goPillarsMap() {
		try {
			serviceManager.getPillarService().getPillarsMap(pillarsMapObj, customer);
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	@SkipValidation
	public String goProjectsMap() {
		try {
			serviceManager.getProjectService().getProjectsMap(projectsMapObj, pillar);
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	@SkipValidation
	public String goProgramManagersMap() {
		try {
			serviceManager.getUserService().getProgramManagersMap(programManagersMapObj, pillar);
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	@SkipValidation
	public String goProjectManagersMap() {
		try {
			serviceManager.getUserService().getProjectManagersMap(projectManagersMapObj, pillar);
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	@SkipValidation
	public String uploadProjectTFSMapper() {

		try {
			String filePath = "/applications/tomcat/project/";

			System.out.println("Server path:" + filePath);
			File fileToCreate = new File(filePath, getUploadFileFileName());
			FileUtils.copyFile(getUploadFile(), fileToCreate);
			serviceManager.getNetworkCodesService().updateInsertProjectData(getUploadFileFileName());
			addActionMessage("Upload Success..");
		} catch (Throwable e) {
			e.printStackTrace();
			addActionMessage("Upload Failed..");
			return INPUT;
		}

		return SUCCESS;
	}

	@SkipValidation
	public String initialProjectTFSMapper() {
		return SUCCESS;
	}

	@SkipValidation
	public String downloadNetworkCodes() {
		try {
			HashMap<String, String> ncColHeaders = populateNetworkCodesColHeadersMap();
			SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
			String timeStamp = df.format(new Date());
			String filePath = getText("rico.summary.export.path");
			File dir = new File(filePath);
			if (!dir.exists()) {
				@SuppressWarnings("unused")
				boolean result = dir.mkdirs();
			}

			String fileName = filePath + getText("rico.summary.export.projects.file.name") + timeStamp + ".xlsx";
			BulkResponse bulkResponse = serviceManager.getNetworkCodesService().downloadNetworkCodes(
					(String) session.get("username"), ncColHeaders, getSearchSortBean(), fileName);
			if (bulkResponse.getStatus().equalsIgnoreCase("SUCCESS")) {
				String url = bulkResponse.getFilePath();

				fileName = getFileName(url);
				pushFileToClient(url, fileName);
			} else {
				addActionError("");
				return ERROR;
			}
		} catch (Throwable throwable) {
			throwable.printStackTrace();
			return ERROR;
		}
		return null;
	}

	private HashMap<String, String> populateNetworkCodesColHeadersMap() {
		HashMap<String, String> userColHeaders = new LinkedHashMap<String, String>();
		userColHeaders.put("RELEASEID", getText("pts.networkcode.release.id"));
		userColHeaders.put("RELEASENAME", getText("pts.networkcode.release.name"));
		userColHeaders.put("PILLAR", getText("pts.networkcode.pillar.name"));
		userColHeaders.put("APPLICATION", getText("pts.networkcode.project"));

		userColHeaders.put("PROGRAMMANAGER", getText("pts.networkcode.program.manager"));
		userColHeaders.put("PROJECTMANAGER", getText("pts.networkcode.project.manager"));
		userColHeaders.put("PRODUCTOWNER", getText("pts.networkcode.product.owner"));
		userColHeaders.put("STARTDATE", getText("pts.networkcode.start.date"));
		userColHeaders.put("ENDDATE", getText("pts.networkcode.end.date"));

		userColHeaders.put("STATUS", getText("pts.networkcode.status"));
		userColHeaders.put("LOCALLOE", getText("pts.networkcode.local.loe"));
		userColHeaders.put("GLOBALLOE", getText("pts.networkcode.global.loe"));
		userColHeaders.put("METHODOLOGY", getText("pts.networkcode.release.type"));
		userColHeaders.put("CATEGORY", getText("pts.networkcode.project.category"));
		userColHeaders.put("SUBCATEGORY", getText("pts.networkcode.project.sub.category"));

		userColHeaders.put("SCOPECLOSSEDATE", getText("pts.networkcode.scope.close.date"));
		userColHeaders.put("NOOFSREQS", getText("pts.networkcode.no.of.sreqs"));
		userColHeaders.put("TOTALORIGINALLOE", getText("pts.networkcode.total.original.loe"));
		userColHeaders.put("PLANNEDIMPLDATE", "Planned " + getText("pts.networkcode.impl.date"));

		userColHeaders.put("ACTUALIMPLDATE", "Actual" + getText("pts.networkcode.impl.date"));
		userColHeaders.put("TFSEPIC", getText("pts.networkcode.no.of.TFSEpic"));
		userColHeaders.put("TOTALLOE", getText("pts.networkcode.total.loe"));

		return userColHeaders;
	}

	public List<NetworkCodes> getNetworkGridModel() {
		return networkGridModel;
	}

	public void setNetworkGridModel(List<NetworkCodes> networkGridModel) {
		this.networkGridModel = networkGridModel;
	}

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

	public String getEffort() {
		return effort;
	}

	public void setEffort(String effort) {
		this.effort = effort;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	protected Pagination getPaginationObject() {
		Pagination pagination = new Pagination();
		int to = (rows * page);
		int from = to - rows;
		pagination.setOffset(from);
		pagination.setSize(to);
		return pagination;
	}

	protected SearchSortContainer getSearchSortBean() {
		SearchSortContainer searchSortObj = new SearchSortContainer();
		searchSortObj.setSearchField(searchField);
		searchSortObj.setSearchString(searchString);

		searchSortObj.setSearchNetworkId(searchNetworkId);
		searchSortObj.setSearchReleaseId(searchReleaseId);
		searchSortObj.setSearchReleaseName(searchReleaseName);
		searchSortObj.setSearchPM(searchPM);
		searchSortObj.setSearchStatus(searchStatus);
		searchSortObj.setTFSEpic(TFSEpic);
		searchSortObj.setSidx(sidx);
		searchSortObj.setSord(sord);
		searchSortObj.setAssignDefaultNwStableToUser(assignDefNeContribution);

		if (session.get("role") != null && StringHelper.isNotEmpty((String) session.get("role"))
				&& !((String) session.get("role")).equalsIgnoreCase("ADMIN")) {
			searchSortObj.setLoggedInId("" + session.get("userId"));
		}

		return searchSortObj;
	}

	public Integer getRows() {
		return rows;
	}

	public void setRows(Integer rows) {
		this.rows = rows;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public String getSord() {
		return sord;
	}

	public void setSord(String sord) {
		this.sord = sord;
	}

	public String getSidx() {
		return sidx;
	}

	public void setSidx(String sidx) {
		this.sidx = sidx;
	}

	public String getSearchField() {
		return searchField;
	}

	public void setSearchField(String searchField) {
		this.searchField = searchField;
	}

	public String getSearchString() {
		return searchString;
	}

	public void setSearchString(String searchString) {
		this.searchString = searchString;
	}

	public Integer getTotalrows() {
		return totalrows;
	}

	public void setTotalrows(Integer totalrows) {
		this.totalrows = totalrows;
	}

	public String getSearchOper() {
		return searchOper;
	}

	public void setSearchOper(String searchOper) {
		this.searchOper = searchOper;
	}

	public String getOper() {
		return oper;
	}

	public void setOper(String oper) {
		this.oper = oper;
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public Integer getRecords() {
		return records;
	}

	public void setRecords(Integer records) {
		this.records = records;
		if (this.records > 0 && this.rows > 0) {
			this.total = (int) Math.ceil((double) this.records / (double) this.rows);
		} else {
			this.total = 0;
		}
	}

	public boolean isLoadonce() {
		return loadonce;
	}

	public void setLoadonce(boolean loadonce) {
		this.loadonce = loadonce;
	}

	public Map<Long, String> getNetworkCodesMap() {
		return networkCodesMap;
	}

	public void setNetworkCodesMap(Map<Long, String> networkCodesMap) {
		this.networkCodesMap = networkCodesMap;
	}

	public String getSelectedEmployee() {
		return selectedEmployee;
	}

	public void setSelectedEmployee(String selectedEmployee) {
		this.selectedEmployee = selectedEmployee;
	}

	public Map<Long, String> getSelectedEmployeesMap() {
		return selectedEmployeesMap;
	}

	public void setSelectedEmployeesMap(Map<Long, String> selectedEmployeesMap) {
		this.selectedEmployeesMap = selectedEmployeesMap;
	}

	public Map<Long, String> getEmployeeMap() {
		return employeeMap;
	}

	public void setEmployeeMap(Map<Long, String> employeeMap) {
		this.employeeMap = employeeMap;
	}

	public List<Long> getSelectedEmployeeNames() {
		return selectedEmployeeNames;
	}

	public void setSelectedEmployeeNames(List<Long> selectedEmployeeNames) {
		this.selectedEmployeeNames = selectedEmployeeNames;
	}

	public Long getSelectedNetworkCodeId() {
		return selectedNetworkCodeId;
	}

	public void setSelectedNetworkCodeId(Long selectedNetworkCodeId) {
		this.selectedNetworkCodeId = selectedNetworkCodeId;
	}

	public Date getStartDate() {
		try {
			DateFormat format = new SimpleDateFormat("MM/dd/yyyy");
			if (StringHelper.isNotEmpty(strStartDate))
				startDate = format.parse(strStartDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		try {
			DateFormat format = new SimpleDateFormat("MM/dd/yyyy");
			if (StringHelper.isNotEmpty(strEndDate))
				endDate = format.parse(strEndDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getProject() {
		return project;
	}

	public void setProject(String project) {
		this.project = project;
	}

	public String getProjectsMap() {
		return projectsMap;
	}

	public void setProjectsMap(String projectsMap) {
		this.projectsMap = projectsMap;
	}

	public Map<Long, String> getEmployeeList() {
		return employeeList;
	}

	public void setEmployeeList(Map<Long, String> employeeList) {
		this.employeeList = employeeList;
	}

	public Map<Long, String> getSelectedNetworkCodesMap() {
		return selectedNetworkCodesMap;
	}

	public void setSelectedNetworkCodesMap(Map<Long, String> selectedNetworkCodesMap) {
		this.selectedNetworkCodesMap = selectedNetworkCodesMap;
	}

	public List<Long> getSelectedNetworkCodeNames() {
		return selectedNetworkCodeNames;
	}

	public void setSelectedNetworkCodeNames(List<Long> selectedNetworkCodeNames) {
		this.selectedNetworkCodeNames = selectedNetworkCodeNames;
	}

	public String getSearchNetworkCode() {
		return searchNetworkCode;
	}

	public void setSearchNetworkCode(String searchNetworkCode) {
		this.searchNetworkCode = searchNetworkCode;
	}

	public String getPillarsMap() {
		return pillarsMap;
	}

	public void setPillarsMap(String pillarsMap) {
		this.pillarsMap = pillarsMap;
	}

	public String getCustomersMap() {
		return customersMap;
	}

	public void setCustomersMap(String customersMap) {
		this.customersMap = customersMap;
	}

	public String getSelectedProjectCategory() {
		return selectedProjectCategory;
	}

	public void setSelectedProjectCategory(String selectedProjectCategory) {
		this.selectedProjectCategory = selectedProjectCategory;
	}

	public String getGlobalDevLOE() {
		return globalDevLOE;
	}

	public void setGlobalDevLOE(String globalDevLOE) {
		this.globalDevLOE = globalDevLOE;
	}

	public String getGlobalTestLOE() {
		return globalTestLOE;
	}

	public void setGlobalTestLOE(String globalTestLOE) {
		this.globalTestLOE = globalTestLOE;
	}

	public String getGlobalImplementationLOE() {
		return globalImplementationLOE;
	}

	public void setGlobalImplementationLOE(String globalImplementationLOE) {
		this.globalImplementationLOE = globalImplementationLOE;
	}

	public String getGlobalOperationsHandOffLOE() {
		return globalOperationsHandOffLOE;
	}

	public void setGlobalOperationsHandOffLOE(String globalOperationsHandOffLOE) {
		this.globalOperationsHandOffLOE = globalOperationsHandOffLOE;
	}

	public String getGlobalProjectManagementLOE() {
		return globalProjectManagementLOE;
	}

	public void setGlobalProjectManagementLOE(String globalProjectManagementLOE) {
		this.globalProjectManagementLOE = globalProjectManagementLOE;
	}

	public String getGlobalOthersLOE() {
		return globalOthersLOE;
	}

	public void setGlobalOthersLOE(String globalOthersLOE) {
		this.globalOthersLOE = globalOthersLOE;
	}

	public String getLocalDevLOE() {
		return localDevLOE;
	}

	public void setLocalDevLOE(String localDevLOE) {
		this.localDevLOE = localDevLOE;
	}

	public String getLocalTestLOE() {
		return localTestLOE;
	}

	public void setLocalTestLOE(String localTestLOE) {
		this.localTestLOE = localTestLOE;
	}

	public String getLocalImplementationLOE() {
		return localImplementationLOE;
	}

	public void setLocalImplementationLOE(String localImplementationLOE) {
		this.localImplementationLOE = localImplementationLOE;
	}

	public String getLocalOperationsHandOffLOE() {
		return localOperationsHandOffLOE;
	}

	public void setLocalOperationsHandOffLOE(String localOperationsHandOffLOE) {
		this.localOperationsHandOffLOE = localOperationsHandOffLOE;
	}

	public String getLocalProjectManagementLOE() {
		return localProjectManagementLOE;
	}

	public void setLocalProjectManagementLOE(String localProjectManagementLOE) {
		this.localProjectManagementLOE = localProjectManagementLOE;
	}

	public String getLocalOthersLOE() {
		return localOthersLOE;
	}

	public void setLocalOthersLOE(String localOthersLOE) {
		this.localOthersLOE = localOthersLOE;
	}

	public String getSreqNo() {
		return sreqNo;
	}

	public void setSreqNo(String sreqNo) {
		this.sreqNo = sreqNo;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getProjectCategoryMap() {
		return projectCategoryMap;
	}

	public void setProjectCategoryMap(String projectCategoryMap) {
		this.projectCategoryMap = projectCategoryMap;
	}

	public String getSubProjectCategoryMap() {
		return subProjectCategoryMap;
	}

	public void setSubProjectCategoryMap(String subProjectCategoryMap) {
		this.subProjectCategoryMap = subProjectCategoryMap;
	}

	public String getStatusMap() {
		return statusMap;
	}

	public void setStatusMap(String statusMap) {
		this.statusMap = statusMap;
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

	public String getSubProjectCategoryData() {
		return subProjectCategoryData;
	}

	public void setSubProjectCategoryData(String subProjectCategoryData) {
		this.subProjectCategoryData = subProjectCategoryData;
	}

	public Date getPlannedDesignDate() {
		try {
			DateFormat format = new SimpleDateFormat("MM/dd/yyyy");
			if (StringHelper.isNotEmpty(strPlannedDesignDate))
				plannedDesignDate = format.parse(strPlannedDesignDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return plannedDesignDate;
	}

	public void setPlannedDesignDate(Date plannedDesignDate) {
		this.plannedDesignDate = plannedDesignDate;
	}

	public Date getActualDesignDate() {
		try {
			DateFormat format = new SimpleDateFormat("MM/dd/yyyy");
			if (StringHelper.isNotEmpty(strActualDesignDate))
				actualDesignDate = format.parse(strActualDesignDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return actualDesignDate;
	}

	public void setActualDesignDate(Date actualDesignDate) {
		this.actualDesignDate = actualDesignDate;
	}

	public Date getPlannedDevDate() {
		try {
			DateFormat format = new SimpleDateFormat("MM/dd/yyyy");
			if (StringHelper.isNotEmpty(strPlannedDevDate))
				plannedDevDate = format.parse(strPlannedDevDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return plannedDevDate;
	}

	public void setPlannedDevDate(Date plannedDevDate) {
		this.plannedDevDate = plannedDevDate;
	}

	public Date getActualDevDate() {
		try {
			DateFormat format = new SimpleDateFormat("MM/dd/yyyy");
			if (StringHelper.isNotEmpty(strActualDevDate))
				actualDevDate = format.parse(strActualDevDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return actualDevDate;
	}

	public void setActualDevDate(Date actualDevDate) {
		this.actualDevDate = actualDevDate;
	}

	public Date getPlannedTestDate() {
		try {
			DateFormat format = new SimpleDateFormat("MM/dd/yyyy");
			if (StringHelper.isNotEmpty(strPlannedTestDate))
				plannedTestDate = format.parse(strPlannedTestDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return plannedTestDate;
	}

	public void setPlannedTestDate(Date plannedTestDate) {
		this.plannedTestDate = plannedTestDate;
	}

	public Date getActualTestDate() {
		try {
			DateFormat format = new SimpleDateFormat("MM/dd/yyyy");
			if (StringHelper.isNotEmpty(strActualTestDate))
				actualTestDate = format.parse(strActualTestDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return actualTestDate;
	}

	public void setActualTestDate(Date actualTestDate) {
		this.actualTestDate = actualTestDate;
	}

	public Date getPlannedImplDate() {
		try {
			DateFormat format = new SimpleDateFormat("MM/dd/yyyy");
			if (StringHelper.isNotEmpty(strPlannedImplDate))
				plannedImplDate = format.parse(strPlannedImplDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return plannedImplDate;
	}

	public void setPlannedImplDate(Date plannedImplDate) {
		this.plannedImplDate = plannedImplDate;
	}

	public Date getActualImplDate() {
		try {
			DateFormat format = new SimpleDateFormat("MM/dd/yyyy");
			if (StringHelper.isNotEmpty(strActualImplDate))
				actualImplDate = format.parse(strActualImplDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return actualImplDate;
	}

	public void setActualImplDate(Date actualImplDate) {
		this.actualImplDate = actualImplDate;
	}

	public Date getPlannedOprHandoffDate() {
		try {
			DateFormat format = new SimpleDateFormat("MM/dd/yyyy");
			if (StringHelper.isNotEmpty(strPlannedOprHandoffDate))
				plannedOprHandoffDate = format.parse(strPlannedOprHandoffDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return plannedOprHandoffDate;
	}

	public void setPlannedOprHandoffDate(Date plannedOprHandoffDate) {
		this.plannedOprHandoffDate = plannedOprHandoffDate;
	}

	public Date getActualOprHandoffDate() {
		try {
			DateFormat format = new SimpleDateFormat("MM/dd/yyyy");
			if (StringHelper.isNotEmpty(strActualOprHandoffDate))
				actualOprHandoffDate = format.parse(strActualOprHandoffDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return actualOprHandoffDate;
	}

	public void setActualOprHandoffDate(Date actualOprHandoffDate) {
		this.actualOprHandoffDate = actualOprHandoffDate;
	}

	public String getProductOwnersMap() {
		return productOwnersMap;
	}

	public void setProductOwnersMap(String productOwnersMap) {
		this.productOwnersMap = productOwnersMap;
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

	public String getProgramManagersMap() {
		return programManagersMap;
	}

	public void setProgramManagersMap(String programManagersMap) {
		this.programManagersMap = programManagersMap;
	}

	public String getProjectManagersMap() {
		return projectManagersMap;
	}

	public void setProjectManagersMap(String projectManagersMap) {
		this.projectManagersMap = projectManagersMap;
	}

	public Map<Long, String> getCustomerMapObj() {
		return customerMapObj;
	}

	public void setCustomerMapObj(Map<Long, String> customerMapObj) {
		this.customerMapObj = customerMapObj;
	}

	public Map<Long, String> getPillarsMapObj() {
		return pillarsMapObj;
	}

	public void setPillarsMapObj(Map<Long, String> pillarsMapObj) {
		this.pillarsMapObj = pillarsMapObj;
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

	public Map<Long, String> getProjectsMapObj() {
		return projectsMapObj;
	}

	public void setProjectsMapObj(Map<Long, String> projectsMapObj) {
		this.projectsMapObj = projectsMapObj;
	}

	public Map<String, String> getProjectCategoryMapObj() {
		return projectCategoryMapObj;
	}

	public void setProjectCategoryMapObj(Map<String, String> projectCategoryMapObj) {
		this.projectCategoryMapObj = projectCategoryMapObj;
	}

	public Map<String, String> getProjectSubCategoryMapObj() {
		return projectSubCategoryMapObj;
	}

	public void setProjectSubCategoryMapObj(Map<String, String> projectSubCategoryMapObj) {
		this.projectSubCategoryMapObj = projectSubCategoryMapObj;
	}

	public Map<String, String> getStatusMapObj() {
		return statusMapObj;
	}

	public void setStatusMapObj(Map<String, String> statusMapObj) {
		this.statusMapObj = statusMapObj;
	}

	public Map<Long, String> getProductOwnerMapObj() {
		return productOwnerMapObj;
	}

	public void setProductOwnerMapObj(Map<Long, String> productOwnerMapObj) {
		this.productOwnerMapObj = productOwnerMapObj;
	}

	public Map<Long, String> getProgramManagersMapObj() {
		return programManagersMapObj;
	}

	public void setProgramManagersMapObj(Map<Long, String> programManagersMapObj) {
		this.programManagersMapObj = programManagersMapObj;
	}

	public Map<Long, String> getProjectManagersMapObj() {
		return projectManagersMapObj;
	}

	public void setProjectManagersMapObj(Map<Long, String> projectManagersMapObj) {
		this.projectManagersMapObj = projectManagersMapObj;
	}

	public String getGlobalDesignLOE() {
		return globalDesignLOE;
	}

	public void setGlobalDesignLOE(String globalDesignLOE) {
		this.globalDesignLOE = globalDesignLOE;
	}

	public String getLocalDesignLOE() {
		return localDesignLOE;
	}

	public void setLocalDesignLOE(String localDesignLOE) {
		this.localDesignLOE = localDesignLOE;
	}

	public String getStrPlannedDesignDate() {
		return strPlannedDesignDate;
	}

	public void setStrPlannedDesignDate(String strPlannedDesignDate) {
		this.strPlannedDesignDate = strPlannedDesignDate;
	}

	public String getStrActualDesignDate() {
		return strActualDesignDate;
	}

	public void setStrActualDesignDate(String strActualDesignDate) {
		this.strActualDesignDate = strActualDesignDate;
	}

	public String getStrPlannedDevDate() {
		return strPlannedDevDate;
	}

	public void setStrPlannedDevDate(String strPlannedDevDate) {
		this.strPlannedDevDate = strPlannedDevDate;
	}

	public String getStrActualDevDate() {
		return strActualDevDate;
	}

	public void setStrActualDevDate(String strActualDevDate) {
		this.strActualDevDate = strActualDevDate;
	}

	public String getStrPlannedTestDate() {
		return strPlannedTestDate;
	}

	public void setStrPlannedTestDate(String strPlannedTestDate) {
		this.strPlannedTestDate = strPlannedTestDate;
	}

	public String getStrActualTestDate() {
		return strActualTestDate;
	}

	public void setStrActualTestDate(String strActualTestDate) {
		this.strActualTestDate = strActualTestDate;
	}

	public String getStrPlannedImplDate() {
		return strPlannedImplDate;
	}

	public void setStrPlannedImplDate(String strPlannedImplDate) {
		this.strPlannedImplDate = strPlannedImplDate;
	}

	public String getStrActualImplDate() {
		return strActualImplDate;
	}

	public void setStrActualImplDate(String strActualImplDate) {
		this.strActualImplDate = strActualImplDate;
	}

	public String getStrPlannedOprHandoffDate() {
		return strPlannedOprHandoffDate;
	}

	public void setStrPlannedOprHandoffDate(String strPlannedOprHandoffDate) {
		this.strPlannedOprHandoffDate = strPlannedOprHandoffDate;
	}

	public String getStrActualOprHandoffDate() {
		return strActualOprHandoffDate;
	}

	public void setStrActualOprHandoffDate(String strActualOprHandoffDate) {
		this.strActualOprHandoffDate = strActualOprHandoffDate;
	}

	public String getStrStartDate() {
		return strStartDate;
	}

	public void setStrStartDate(String strStartDate) {
		this.strStartDate = strStartDate;
	}

	public String getStrEndDate() {
		return strEndDate;
	}

	public void setStrEndDate(String strEndDate) {
		this.strEndDate = strEndDate;
	}

	public Date getScopeCloseDate() {
		try {
			DateFormat format = new SimpleDateFormat("MM/dd/yyyy");
			if (StringHelper.isNotEmpty(strScopeCloseDate))
				scopeCloseDate = format.parse(strScopeCloseDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return scopeCloseDate;
	}

	public void setScopeCloseDate(Date scopeCloseDate) {
		this.scopeCloseDate = scopeCloseDate;
	}

	public Date getChangeControlDate() {
		try {
			DateFormat format = new SimpleDateFormat("MM/dd/yyyy");
			if (StringHelper.isNotEmpty(strChangeControlDate))
				changeControlDate = format.parse(strChangeControlDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return changeControlDate;
	}

	public void setChangeControlDate(Date changeControlDate) {
		this.changeControlDate = changeControlDate;
	}

	public Date getRollbackDate() {
		try {
			DateFormat format = new SimpleDateFormat("MM/dd/yyyy");
			if (StringHelper.isNotEmpty(strRollbackDate))
				rollbackDate = format.parse(strRollbackDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return rollbackDate;
	}

	public void setRollbackDate(Date rollbackDate) {
		this.rollbackDate = rollbackDate;
	}

	public String getStrScopeCloseDate() {
		return strScopeCloseDate;
	}

	public void setStrScopeCloseDate(String strScopeCloseDate) {
		this.strScopeCloseDate = strScopeCloseDate;
	}

	public String getStrChangeControlDate() {
		return strChangeControlDate;
	}

	public void setStrChangeControlDate(String strChangeControlDate) {
		this.strChangeControlDate = strChangeControlDate;
	}

	public String getStrRollbackDate() {
		return strRollbackDate;
	}

	public void setStrRollbackDate(String strRollbackDate) {
		this.strRollbackDate = strRollbackDate;
	}

	public Date getOriginalDesignDate() {
		try {
			DateFormat format = new SimpleDateFormat("MM/dd/yyyy");
			if (StringHelper.isNotEmpty(strOriginalDesignDate))
				originalDesignDate = format.parse(strOriginalDesignDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return originalDesignDate;
	}

	public void setOriginalDesignDate(Date originalDesignDate) {
		this.originalDesignDate = originalDesignDate;
	}

	public Date getOriginalDevDate() {
		try {
			DateFormat format = new SimpleDateFormat("MM/dd/yyyy");
			if (StringHelper.isNotEmpty(strOriginalDevDate))
				originalDevDate = format.parse(strOriginalDevDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return originalDevDate;
	}

	public void setOriginalDevDate(Date originalDevDate) {
		this.originalDevDate = originalDevDate;
	}

	public Date getOriginalTestDate() {
		try {
			DateFormat format = new SimpleDateFormat("MM/dd/yyyy");
			if (StringHelper.isNotEmpty(strOriginalTestDate))
				originalTestDate = format.parse(strOriginalTestDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return originalTestDate;
	}

	public void setOriginalTestDate(Date originalTestDate) {
		this.originalTestDate = originalTestDate;
	}

	public Date getOriginalImplDate() {
		try {
			DateFormat format = new SimpleDateFormat("MM/dd/yyyy");
			if (StringHelper.isNotEmpty(strOriginalImplDate))
				originalImplDate = format.parse(strOriginalImplDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return originalImplDate;
	}

	public void setOriginalImplDate(Date originalImplDate) {
		this.originalImplDate = originalImplDate;
	}

	public Date getOriginalOprHandoffDate() {
		try {
			DateFormat format = new SimpleDateFormat("MM/dd/yyyy");
			if (StringHelper.isNotEmpty(strOriginalOprHandoffDate))
				originalOprHandoffDate = format.parse(strOriginalOprHandoffDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return originalOprHandoffDate;
	}

	public void setOriginalOprHandoffDate(Date originalOprHandoffDate) {
		this.originalOprHandoffDate = originalOprHandoffDate;
	}

	public Date getOriginalWarrantyCompletionDate() {
		try {
			DateFormat format = new SimpleDateFormat("MM/dd/yyyy");
			if (StringHelper.isNotEmpty(strOriginalWarrantyCompletionDate))
				originalWarrantyCompletionDate = format.parse(strOriginalWarrantyCompletionDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return originalWarrantyCompletionDate;
	}

	public void setOriginalWarrantyCompletionDate(Date originalWarrantyCompletionDate) {
		this.originalWarrantyCompletionDate = originalWarrantyCompletionDate;
	}

	public Date getPlannedWarrantyCompletionDate() {
		try {
			DateFormat format = new SimpleDateFormat("MM/dd/yyyy");
			if (StringHelper.isNotEmpty(strPlannedWarrantyCompletionDate))
				plannedWarrantyCompletionDate = format.parse(strPlannedWarrantyCompletionDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return plannedWarrantyCompletionDate;
	}

	public void setPlannedWarrantyCompletionDate(Date plannedWarrantyCompletionDate) {
		this.plannedWarrantyCompletionDate = plannedWarrantyCompletionDate;
	}

	public String getStrOriginalDesignDate() {
		return strOriginalDesignDate;
	}

	public void setStrOriginalDesignDate(String strOriginalDesignDate) {
		this.strOriginalDesignDate = strOriginalDesignDate;
	}

	public String getStrOriginalDevDate() {
		return strOriginalDevDate;
	}

	public void setStrOriginalDevDate(String strOriginalDevDate) {
		this.strOriginalDevDate = strOriginalDevDate;
	}

	public String getStrOriginalTestDate() {
		return strOriginalTestDate;
	}

	public void setStrOriginalTestDate(String strOriginalTestDate) {
		this.strOriginalTestDate = strOriginalTestDate;
	}

	public String getStrOriginalImplDate() {
		return strOriginalImplDate;
	}

	public void setStrOriginalImplDate(String strOriginalImplDate) {
		this.strOriginalImplDate = strOriginalImplDate;
	}

	public String getStrOriginalOprHandoffDate() {
		return strOriginalOprHandoffDate;
	}

	public void setStrOriginalOprHandoffDate(String strOriginalOprHandoffDate) {
		this.strOriginalOprHandoffDate = strOriginalOprHandoffDate;
	}

	public String getStrOriginalWarrantyCompletionDate() {
		return strOriginalWarrantyCompletionDate;
	}

	public void setStrOriginalWarrantyCompletionDate(String strOriginalWarrantyCompletionDate) {
		this.strOriginalWarrantyCompletionDate = strOriginalWarrantyCompletionDate;
	}

	public String getStrPlannedWarrantyCompletionDate() {
		return strPlannedWarrantyCompletionDate;
	}

	public void setStrPlannedWarrantyCompletionDate(String strPlannedWarrantyCompletionDate) {
		this.strPlannedWarrantyCompletionDate = strPlannedWarrantyCompletionDate;
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

	public String getOriginalDesignLOE() {
		return originalDesignLOE;
	}

	public void setOriginalDesignLOE(String originalDesignLOE) {
		this.originalDesignLOE = originalDesignLOE;
	}

	public String getOriginalDevLOE() {
		return originalDevLOE;
	}

	public void setOriginalDevLOE(String originalDevLOE) {
		this.originalDevLOE = originalDevLOE;
	}

	public String getOriginalTestLOE() {
		return originalTestLOE;
	}

	public void setOriginalTestLOE(String originalTestLOE) {
		this.originalTestLOE = originalTestLOE;
	}

	public String getOriginalProjectManagementLOE() {
		return originalProjectManagementLOE;
	}

	public void setOriginalProjectManagementLOE(String originalProjectManagementLOE) {
		this.originalProjectManagementLOE = originalProjectManagementLOE;
	}

	public String getOriginalImplementationLOE() {
		return originalImplementationLOE;
	}

	public void setOriginalImplementationLOE(String originalImplementationLOE) {
		this.originalImplementationLOE = originalImplementationLOE;
	}

	public String getOriginalOperationsHandOffLOE() {
		return originalOperationsHandOffLOE;
	}

	public void setOriginalOperationsHandOffLOE(String originalOperationsHandOffLOE) {
		this.originalOperationsHandOffLOE = originalOperationsHandOffLOE;
	}

	public String getSearchNetworkId() {
		return searchNetworkId;
	}

	public void setSearchNetworkId(String searchNetworkId) {
		this.searchNetworkId = searchNetworkId;
	}

	public String getSearchReleaseId() {
		return searchReleaseId;
	}

	public void setSearchReleaseId(String searchReleaseId) {
		this.searchReleaseId = searchReleaseId;
	}

	public String getSearchReleaseName() {
		return searchReleaseName;
	}

	public void setSearchReleaseName(String searchReleaseName) {
		this.searchReleaseName = searchReleaseName;
	}

	public String getSearchPM() {
		return searchPM;
	}

	public void setSearchPM(String searchPM) {
		this.searchPM = searchPM;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	private void setNetworkObjToSave(NetworkCodes networkCodeObj) {
		networkCodeObj.setNetworkCodeId(networkCodeId);
		networkCodeObj.setNetworkCode(networkCode);
		networkCodeObj.setDescription(description);

		if (StringHelper.isNotEmpty(project))
			networkCodeObj.setProject(project);
		if (StringHelper.isNotEmpty(pillar))
			networkCodeObj.setPillar(pillar);

		networkCodeObj.setStableTeam(stableTeam);

		networkCodeObj.setCustomerId(selectedAccountId);
		networkCodeObj.setForInterns(isForinterns);
		networkCodeObj.setProductOwner(productOwner);

		networkCodeObj.setProjectCategory(projectCategory);
		networkCodeObj.setProjectSubCategory(projectSubCategory);

		networkCodeObj.setPriority(priority);
		networkCodeObj.setProgramManager(programManager);
		networkCodeObj.setProjectManager(projectManager);

		networkCodeObj.setStartDate(getStartDate());
		networkCodeObj.setEndDate(getEndDate());

		networkCodeObj.setScheduleVariance(scheduleVariance);
		networkCodeObj.setRollbackVolume(rollbackVolume);
		networkCodeObj.setDevTestEffectiveness(devTestEffectiveness);

		networkCodeObj.setProjectStage(projectStage);

		networkCodeObj.setPlannedDesignDate(getPlannedDesignDate());
		networkCodeObj.setActualDesignDate(getActualDesignDate());
		networkCodeObj.setPlannedDevDate(getPlannedDevDate());
		networkCodeObj.setActualDevDate(getActualDevDate());
		networkCodeObj.setPlannedTestDate(getPlannedTestDate());
		networkCodeObj.setActualTestDate(getActualTestDate());
		networkCodeObj.setPlannedImplDate(getPlannedImplDate());
		networkCodeObj.setActualImplDate(getActualImplDate());
		networkCodeObj.setPlannedOprHandoffDate(getPlannedOprHandoffDate());
		networkCodeObj.setActualOprHandoffDate(getActualOprHandoffDate());

		networkCodeObj.setGlobalDesignLOE(
				globalDesignLOE == null || globalDesignLOE.equals("") ? 0l : Long.valueOf(globalDesignLOE));
		networkCodeObj
				.setGlobalDevLOE(globalDevLOE == null || globalDevLOE.equals("") ? 0l : Long.valueOf(globalDevLOE));
		networkCodeObj
				.setGlobalTestLOE(globalTestLOE == null || globalTestLOE.equals("") ? 0l : Long.valueOf(globalTestLOE));
		networkCodeObj
				.setGlobalImplementationLOE(globalImplementationLOE == null || globalImplementationLOE.equals("") ? 0l
						: Long.valueOf(globalImplementationLOE));
		networkCodeObj.setGlobalOperationsHandOffLOE(
				globalOperationsHandOffLOE == null || globalOperationsHandOffLOE.equals("") ? 0l
						: Long.valueOf(globalOperationsHandOffLOE));
		networkCodeObj.setGlobalProjectManagementLOE(
				globalProjectManagementLOE == null || globalProjectManagementLOE.equals("") ? 0l
						: Long.valueOf(globalProjectManagementLOE));
		networkCodeObj.setGlobalOthersLOE(
				globalOthersLOE == null || globalOthersLOE.equals("") ? 0l : Long.valueOf(globalOthersLOE));
		networkCodeObj
				.setGlobalKitLOE(globalKitLOE == null || globalKitLOE.equals("") ? 0l : Long.valueOf(globalKitLOE));
		networkCodeObj.setLocalDesignLOE(
				localDesignLOE == null || localDesignLOE.equals("") ? 0l : Long.valueOf(localDesignLOE));
		networkCodeObj.setLocalDevLOE(localDevLOE == null || localDevLOE.equals("") ? 0l : Long.valueOf(localDevLOE));
		networkCodeObj
				.setLocalTestLOE(localTestLOE == null || localTestLOE.equals("") ? 0l : Long.valueOf(localTestLOE));
		networkCodeObj.setLocalKitLOE(localKitLOE == null || localKitLOE.equals("") ? 0l : Long.valueOf(localKitLOE));
		networkCodeObj
				.setLocalImplementationLOE(localImplementationLOE == null || localImplementationLOE.equals("") ? 0l
						: Long.valueOf(localImplementationLOE));
		networkCodeObj.setLocalOperationsHandOffLOE(
				localOperationsHandOffLOE == null || localOperationsHandOffLOE.equals("") ? 0l
						: Long.valueOf(localOperationsHandOffLOE));
		networkCodeObj.setLocalProjectManagementLOE(
				localProjectManagementLOE == null || localProjectManagementLOE.equals("") ? 0l
						: Long.valueOf(localProjectManagementLOE));
		networkCodeObj.setLocalOthersLOE(
				localOthersLOE == null || localOthersLOE.equals("") ? 0l : Long.valueOf(localOthersLOE));

		networkCodeObj.setEffort(networkCodeObj.getGlobalDesignLOE() + networkCodeObj.getGlobalDevLOE()
				+ networkCodeObj.getGlobalKitLOE() + networkCodeObj.getGlobalTestLOE()
				+ networkCodeObj.getGlobalImplementationLOE() + networkCodeObj.getGlobalOperationsHandOffLOE()
				+ networkCodeObj.getGlobalProjectManagementLOE() + networkCodeObj.getGlobalOthersLOE()
				+ networkCodeObj.getLocalDesignLOE() + networkCodeObj.getLocalDevLOE() + networkCodeObj.getLocalKitLOE()
				+ networkCodeObj.getLocalTestLOE() + networkCodeObj.getLocalImplementationLOE()
				+ networkCodeObj.getLocalOperationsHandOffLOE() + networkCodeObj.getLocalProjectManagementLOE()
				+ networkCodeObj.getLocalOthersLOE());

		networkCodeObj.setSreqNo(sreqNo);
		if (status != null && status != "-1") {
			networkCodeObj.setStatus(Status.getEnumValueOf(status.split(",")[0]));
		}

		networkCodeObj.setOriginalDesignLOE(
				originalDesignLOE == null || originalDesignLOE.equals("") ? 0l : Long.valueOf(originalDesignLOE));
		networkCodeObj.setOriginalDevLOE(
				originalDevLOE == null || originalDevLOE.equals("") ? 0l : Long.valueOf(originalDevLOE));
		networkCodeObj.setOriginalTestLOE(
				originalTestLOE == null || originalTestLOE.equals("") ? 0l : Long.valueOf(originalTestLOE));
		networkCodeObj.setOriginalImplementationLOE(
				originalImplementationLOE == null || originalImplementationLOE.equals("") ? 0l
						: Long.valueOf(originalImplementationLOE));
		networkCodeObj.setOriginalOperationsHandOffLOE(
				originalOperationsHandOffLOE == null || originalOperationsHandOffLOE.equals("") ? 0l
						: Long.valueOf(originalOperationsHandOffLOE));
		networkCodeObj.setOriginalProjectManagementLOE(
				originalProjectManagementLOE == null || originalProjectManagementLOE.equals("") ? 0l
						: Long.valueOf(originalProjectManagementLOE));

		networkCodeObj.setOriginalDesignDate(getOriginalDesignDate());

		networkCodeObj.setOriginalDevDate(getOriginalDevDate());

		networkCodeObj.setOriginalTestDate(getOriginalTestDate());

		networkCodeObj.setOriginalImplDate(getOriginalImplDate());

		networkCodeObj.setOriginalOprHandoffDate(getOriginalOprHandoffDate());

		networkCodeObj.setOriginalKitLOE(
				originalKitLOE == null || originalKitLOE.equals("") ? 0l : Long.valueOf(originalKitLOE));

		networkCodeObj.setOriginalWarrantyCompletionDate(getOriginalWarrantyCompletionDate());

		networkCodeObj.setPlannedWarrantyCompletionDate(getPlannedWarrantyCompletionDate());

		networkCodeObj.setScopeCloseDate(getScopeCloseDate());

		networkCodeObj.setChangeControlDate(getChangeControlDate());

		networkCodeObj.setRollbackDate(getRollbackDate());

		networkCodeObj.setReleaseId(releaseId);
		networkCodeObj.setReleaseName(releaseName);
		networkCodeObj.setReleaseType(releaseType);

		networkCodeObj.setChangeControl(changeControl);
		networkCodeObj.setChangeControlImpact(changeControlImpact);
		networkCodeObj.setChangeControlNo(changeControlNo);
		networkCodeObj.setChangeControlStatus(changeControlStatus);

		networkCodeObj.setRollback(rollback);
		networkCodeObj.setRollbackNo(rollbackNo);
		networkCodeObj.setRollbackReason(rollbackReason);
		networkCodeObj.setTFSEpic((TFSEpic != null && !TFSEpic.isEmpty()) ? TFSEpic : "0");
		networkCodeObj.setTotalGlobalLOE((totalGlobalLOE != null) ? totalGlobalLOE : 0L);
		networkCodeObj.setTotalLocalLOE((totalLocalLOE != null) ? totalLocalLOE : 0L);
		networkCodeObj.setTotalLOE((getTotalLOE() != null) ? getTotalLOE() : 0L);
	}

	@SkipValidation
	public String getStableTeamsData() {
		getStableTeamsmap().clear();
		List<StableTeams> stableTeams = serviceManager.getNetworkCodesService().getStableTeams(null);

		for (StableTeams s : stableTeams)
			getStableTeamsmap().put(s.getId(), s.getTeamName());

		return SUCCESS;
	}

	@SkipValidation
	public String goProjectPhaseEdit() {

		return SUCCESS;
	}

	public String getSearchSupervisor() {
		return searchSupervisor;
	}

	public void setSearchSupervisor(String searchSupervisor) {
		this.searchSupervisor = searchSupervisor;
	}

	public Map<Long, String> getReleasesMapObj() {
		return releasesMapObj;
	}

	public void setReleasesMapObj(Map<Long, String> releasesMapObj) {
		this.releasesMapObj = releasesMapObj;
	}

	public String getSearchStatus() {
		return searchStatus;
	}

	public void setSearchStatus(String searchStatus) {
		this.searchStatus = searchStatus;
	}

	public String getProjectStage() {
		return projectStage;
	}

	public void setProjectStage(String projectStage) {
		this.projectStage = projectStage;
	}

	public String getPhaseMap() {
		return phaseMap;
	}

	public void setPhaseMap(String phaseMap) {
		this.phaseMap = phaseMap;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getProjectType() {
		return projectType;
	}

	public void setProjectType(String projectType) {
		this.projectType = projectType;
	}

	public String getGlobalKitLOE() {
		return globalKitLOE;
	}

	public void setGlobalKitLOE(String globalKitLOE) {
		this.globalKitLOE = globalKitLOE;
	}

	public String getLocalKitLOE() {
		return localKitLOE;
	}

	public void setLocalKitLOE(String localKitLOE) {
		this.localKitLOE = localKitLOE;
	}

	public String getOriginalKitLOE() {
		return originalKitLOE;
	}

	public void setOriginalKitLOE(String originalKitLOE) {
		this.originalKitLOE = originalKitLOE;
	}

	public String getProjColor() {
		return projColor;
	}

	public void setProjColor(String projColor) {
		this.projColor = projColor;
	}

	public String getProjectColor() {
		return projectColor;
	}

	public void setProjectColor(String projectColor) {
		this.projectColor = projectColor;
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

	public Long getTotalLocalLOE() {
		return totalLocalLOE;
	}

	public void setTotalLocalLOE(Long totalLocalLOE) {
		this.totalLocalLOE = totalLocalLOE;
	}

	public Long getTotalGlobalLOE() {
		return totalGlobalLOE;
	}

	public void setTotalGlobalLOE(Long totalGlobalLOE) {
		this.totalGlobalLOE = totalGlobalLOE;
	}

	public File getUploadFile() {
		return uploadFile;
	}

	public void setUploadFile(File uploadFile) {
		this.uploadFile = uploadFile;
	}

	public String getUploadFileContentType() {
		return uploadFileContentType;
	}

	public void setUploadFileContentType(String uploadFileContentType) {
		this.uploadFileContentType = uploadFileContentType;
	}

	public String getUploadFileFileName() {
		return uploadFileFileName;
	}

	public void setUploadFileFileName(String uploadFileFileName) {
		this.uploadFileFileName = uploadFileFileName;
	}

	public Long getTotalLOE() {
		return totalLOE;
	}

	public void setTotalLOE(Long totalLOE) {
		this.totalLOE = totalLOE;
	}

	public boolean isShowAllResources() {
		return showAllResources;
	}

	public void setShowAllResources(boolean showAllResources) {
		this.showAllResources = showAllResources;
	}

	public boolean isForinterns() {
		return isForinterns;
	}

	public void setForinterns(boolean isForinterns) {
		this.isForinterns = isForinterns;
	}

	public Map<Long, String> getStableTeamsmap() {
		return stableTeamsmap;
	}

	public void setStableTeamsmap(Map<Long, String> stableTeamsmap) {
		this.stableTeamsmap = stableTeamsmap;
	}

	public Long getStableTeam() {
		return stableTeam;
	}

	public void setStableTeam(Long stableTeam) {
		this.stableTeam = stableTeam;
	}

	public String getRelease() {
		return release;
	}

	public void setRelease(String release) {
		this.release = release;
	}

	public String getShowUserNwCapacity() {
		return showUserNwCapacity;
	}

	public void setShowUserNwCapacity(String showUserNwCapacity) {
		this.showUserNwCapacity = showUserNwCapacity;
	}

	public boolean getAssignDefNeContribution() {
		return assignDefNeContribution;
	}

	public void setAssignDefNeContribution(boolean assignDefNeContribution) {
		this.assignDefNeContribution = assignDefNeContribution;
	}

	public Long getTotalStoryPoints() {
		return totalStoryPoints;
	}

	public void setTotalStoryPoints(Long totalStoryPoints) {
		this.totalStoryPoints = totalStoryPoints;
	}

}
