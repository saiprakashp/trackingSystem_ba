package com.egil.pts.actions;

import java.time.Year;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.struts2.interceptor.validation.SkipValidation;
import org.hibernate.internal.util.StringHelper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.egil.pts.modal.CapacityPlanning;
import com.egil.pts.modal.CapacityPlanningByType;
import com.egil.pts.modal.Pagination;
import com.egil.pts.modal.SearchSortContainer;
import com.egil.pts.modal.SummaryResponse;
import com.egil.pts.modal.User;
import com.egil.pts.util.Utility;

@Controller("capacityPlanningAction")
@Scope("prototype")
public class CapacityPlanningAction extends PTSAction {

	private static final long serialVersionUID = 1L;
	private Long selectedEmployee;

	private List<CapacityPlanning> subGridModel = new ArrayList<CapacityPlanning>();
	private List<CapacityPlanning> ncGridModel = new ArrayList<CapacityPlanning>();
	private String searchUserName;
	private Map<Long, String> supervisorMap = new LinkedHashMap<Long, String>();
	private Map<Long, String> technologiesMap = new LinkedHashMap<Long, String>();
	private Map<Long, String> pillarsMapObj = new LinkedHashMap<Long, String>();
	private Map<Long, String> projectsMapObj = new LinkedHashMap<Long, String>();
	private Map<Long, String> userNetworkCodesMap = new LinkedHashMap<Long, String>();
	private Map<Long, String> usersMap = new LinkedHashMap<Long, String>();
	private Map<Long, String> streamsMap = new LinkedHashMap<Long, String>();
	List<CapacityPlanningByType> cptyPlanDetByTypeList = new ArrayList<CapacityPlanningByType>();
	private List<User> unPlancptyList = new ArrayList<User>();
	private List<User> unPlancptySunList = new ArrayList<User>();

	private Long userId;
	private Long supervisorId;
	private Long networkCode;
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
	private String createdBy;
	private Date createdDate;

	private String searchSupervisor;
	private String searchTechnology;
	private String searchStatus;
	private String searchStream;
	private boolean allReporteesFlag;

	private String networkId;
	private String selectedUserId;
	private String reportType;
	// Your result List
	private List<User> gridModel = new ArrayList<User>();
	private List<com.egil.pts.modal.CapacityPlanning> capacityReportDetailsgridModel = new ArrayList<com.egil.pts.modal.CapacityPlanning>();

	private String ricoLocation;
	private String project;
	private String pillar;

	private Long year;
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

	private String rowid;

	private boolean bySupervisor;

	@Value("${rico.user.egil.capcapty.limit}")
	private String egilTotCapLimit;

	@Value("${rico.user.mana.capcapty.limit}")
	private String manaTotCapLimit;

	public void setRowid(String rowid) {
		this.rowid = rowid;
	}

	public String getRowid() {
		return rowid;
	}

	@SkipValidation
	public String execute() {
		try {
			if ((searchSupervisor == null || (searchSupervisor != null && searchSupervisor.isEmpty()))) {
				allReporteesFlag = true;
			}

			if (session.get("role") != null && ((String) session.get("role")).equalsIgnoreCase("LINE MANAGER")) {
				if ((searchSupervisor == null || (searchSupervisor != null && searchSupervisor.isEmpty()))) {
					searchSupervisor = (Long) session.get("userId") + "";
				}

			}

			if (searchStatus == null || (searchStatus != null && searchStatus.isEmpty())) {
				searchStatus = "Onboard";
			}
			if (StringHelper.isEmpty(reportType)) {
				reportType = "Resource-Wise";
			}
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	@SkipValidation
	public String goManageUser() {
		try {
			clearSession("supervisorMap", "userToBackFillMap", "privilegesMap", "rolesMap", "streamsMap",
					"platformsMap", "userTypesMap", "technologiesMap");
			boolean isAdmin = false;
			if (session.get("role") != null && ((String) session.get("role")).equalsIgnoreCase("ADMIN")) {
				isAdmin = true;
			}
			if (session.get("rowNum") == null) {
				session.put("rowNum", rows);
			} else {
				if (rows != null && (rows.intValue() != ((Integer) session.get("rowNum")).intValue())) {
					session.put("rowNum", rows);
				}
			}
			SummaryResponse<User> summary = serviceManager.getUserService().getUserSummary(getPaginationObject(),
					getSearchSortBean(), isAdmin);
			gridModel = summary.getEnitities();

			records = summary.getTotalRecords();

			// calculate the total pages for the query
			total = (int) Math.ceil((double) records / (double) rows);

		} catch (Throwable e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	@SkipValidation
	public String goGetCapacityUserDetailsReport() {
		try {
			clearSession("supervisorMap", "userToBackFillMap", "privilegesMap", "rolesMap", "streamsMap",
					"platformsMap", "userTypesMap", "technologiesMap");
			boolean isAdmin = false;
			if (session.get("role") != null && ((String) session.get("role")).equalsIgnoreCase("ADMIN")) {
				isAdmin = true;
			}
			if (session.get("rowNum") == null) {
				session.put("rowNum", rows);
			} else {
				if (rows != null && (rows.intValue() != ((Integer) session.get("rowNum")).intValue())) {
					session.put("rowNum", rows);
				}
			}
			if (StringHelper.isEmpty(reportType)
					|| (reportType != null && reportType.equalsIgnoreCase("Resource-Wise"))) {
				reportType = "Resource-Wise";
				SummaryResponse<CapacityPlanning> summary = serviceManager.getCapacityPlanningService()
						.getUserCapacityPlanningDetailReportDetails(getSearchSortBean(), selectedYear,
								getPaginationObject());
				capacityReportDetailsgridModel = summary.getEnitities();

				records = summary.getTotalRecords();
			} else {
				goManageProjectNC();
			}

			// calculate the total pages for the query
			total = (int) Math.ceil((double) records / (double) rows);

		} catch (Throwable e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	private String goManageProjectNC() {
		try {
			clearSession("supervisorMap", "userToBackFillMap", "privilegesMap", "rolesMap", "streamsMap",
					"platformsMap", "userTypesMap", "technologiesMap");
			boolean isAdmin = false;
			if (session.get("role") != null && ((String) session.get("role")).equalsIgnoreCase("ADMIN")) {
				isAdmin = true;
			}
			if ((searchSupervisor == null || (searchSupervisor != null && searchSupervisor.isEmpty()))) {
				searchSupervisor = (Long) session.get("userId") + "";
			}
			SummaryResponse<CapacityPlanning> summary = serviceManager.getCapacityPlanningService()
					.getNetworkCodesList(getSearchSortBean(), selectedYear, getPaginationObject());
			if (summary != null && summary.getEnitities() != null && summary.getEnitities().size() > 0) {
				ncGridModel = summary.getEnitities();
			}
			records =summary.getTotalRecords();
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	@SkipValidation
	public String goCapacityPlanning() {
		try {
			clearSession("networkCodesMap");
			if (session.get("rowNum") == null) {
				session.put("rowNum", rows);
			} else {
				if (rows != null && (rows.intValue() != ((Integer) session.get("rowNum")).intValue())) {
					session.put("rowNum", rows);
				}
			}
			if (id != null && id.contains(", ")) {
				id = id.trim().substring(0, id.indexOf(','));
			}

			// session.put("networkCodesMap", networkCodesMap);
			SummaryResponse<CapacityPlanning> summary = serviceManager.getCapacityPlanningService()
					.getCapacityPlanningDetails(Long.parseLong(id), selectedYear, getPaginationObject());
			if (summary != null && summary.getEnitities() != null && summary.getEnitities().size() > 0) {
				subGridModel = summary.getEnitities();
			}
			records = summary.getTotalRecords();
			total = (int) Math.ceil((double) records / (double) rows);
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	@SkipValidation
	public String goCapacityPlanningNCUserDetailsReport() {
		try {
			if (session.get("rowNum") == null) {
				session.put("rowNum", rows);
			} else {
				if (rows != null && (rows.intValue() != ((Integer) session.get("rowNum")).intValue())) {
					session.put("rowNum", rows);
				}
			}
			if (id != null && id.contains(", ")) {
				id = id.trim().substring(0, id.indexOf(','));
			}

			// session.put("networkCodesMap", networkCodesMap);
			SummaryResponse<CapacityPlanning> summary = serviceManager.getCapacityPlanningService()
					.getNetworkCodesUsersCapacityList(getSearchSortBean(), selectedYear, getPaginationObject(),
							Long.parseLong(id));
			if (summary != null && summary.getEnitities() != null && summary.getEnitities().size() > 0) {
				subGridModel = summary.getEnitities();
			}
			records = summary.getTotalRecords();
			total = (int) Math.ceil((double) records / (double) rows);
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	@SkipValidation
	public String capacityPlanningMACD() {
		CapacityPlanning capacityPlanning = new CapacityPlanning();
		try {
			if (oper.equalsIgnoreCase("add")) {
				if (networkCode != null && networkCode != -1) {
					setCapacityPlanObjToSave(capacityPlanning);
					capacityPlanning.setCreatedBy((String) session.get("username"));
					capacityPlanning.setUpdatedBy((String) session.get("username"));
					capacityPlanning.setCreatedDate(new Date());
					capacityPlanning.setUpdatedDate(new Date());
					serviceManager.getCapacityPlanningService().saveCapacityPlanningDetails(capacityPlanning);
				}
			} else if (oper.equalsIgnoreCase("edit")) {
				if (networkCode != null && networkCode != -1) {
					capacityPlanning.setId(Long.valueOf(id));
					setCapacityPlanObjToSave(capacityPlanning);
					capacityPlanning.setCreatedBy(createdBy);
					capacityPlanning.setCreatedDate(createdDate);
					capacityPlanning.setUpdatedBy((String) session.get("username"));
					capacityPlanning.setUpdatedDate(new Date());
					serviceManager.getCapacityPlanningService().saveCapacityPlanningDetails(capacityPlanning);
				}
			} else if (oper.equalsIgnoreCase("del")) {
				if (id != null & !id.equalsIgnoreCase("")) {
					serviceManager.getCapacityPlanningService()
							.deleteCapacityPlanningDetails(Utility.getListFromCommaSeparated(id));
				}
			}
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	private void setCapacityPlanObjToSave(CapacityPlanning capacityPlanning) {
		capacityPlanning.setUserId(userId);
		capacityPlanning.setNetworkCodeId(networkCode);
		capacityPlanning.setJanCapacity(janCapacity);
		capacityPlanning.setFebCapacity(febCapacity);
		capacityPlanning.setMarCapacity(marCapacity);
		capacityPlanning.setAprCapacity(aprCapacity);
		capacityPlanning.setMayCapacity(mayCapacity);
		capacityPlanning.setJunCapacity(junCapacity);
		capacityPlanning.setJulCapacity(julCapacity);
		capacityPlanning.setAugCapacity(augCapacity);
		capacityPlanning.setSepCapacity(sepCapacity);
		capacityPlanning.setOctCapacity(octCapacity);
		capacityPlanning.setNovCapacity(novCapacity);
		capacityPlanning.setDecCapacity(decCapacity);
		capacityPlanning.setYear(year);
	}

	@SkipValidation
	public String getUserNetworkCodes() {
		try {
			userNetworkCodesMap = new LinkedHashMap<Long, String>();
			if (rowid != null && !rowid.isEmpty()) {
				Set<Long> ncids = null;
				if (selectedYear != null && selectedYear != -1 && (id != null && id.contains("jqg"))) {
					ncids = serviceManager.getCapacityPlanningService().getResourceNetworkCodes(Long.valueOf(rowid),
							selectedYear);
				}
				if (networkId != null && (!networkId.equals("-1") && !networkId.equals(""))) {
					if (networkId.indexOf(" - ") != -1) {

						serviceManager.getNetworkCodesService().getResourceNetworkCodes(Long.valueOf(rowid),
								userNetworkCodesMap, networkId.split(" - ")[0], null);
					}
				} else {
					serviceManager.getNetworkCodesService().getResourceNetworkCodes(Long.valueOf(rowid),
							userNetworkCodesMap, null, ncids);
				}
				userId = Long.valueOf(rowid);
			}
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	@SkipValidation
	public String goGetUsers() {
		try {
			usersMap = new LinkedHashMap<Long, String>();
			if (rowid != null && !rowid.isEmpty()) {
				Set<Long> ncids = null;
				if (selectedYear != null && selectedYear != -1 && (id != null && id.contains("jqg"))) {
					ncids = serviceManager.getCapacityPlanningService().getNetworkCodeResources(Long.valueOf(rowid),
							selectedYear);
				}
				if (selectedUserId != null && (!selectedUserId.equals("-1") && !selectedUserId.equals(""))) {
					serviceManager.getNetworkCodesService().getNetworkCodeResources(supervisorId, Long.valueOf(rowid),
							usersMap, selectedUserId, null);
				} else {
					serviceManager.getNetworkCodesService().getNetworkCodeResources(supervisorId, Long.valueOf(rowid),
							usersMap, null, ncids);
				}
				networkCode = Long.valueOf(rowid);
			}
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	@SkipValidation
	public String getParentRowId() {
		try {
			if (rowid != null && !rowid.isEmpty()) {
				userId = Long.valueOf(rowid);
			}
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	@SkipValidation
	public String getParentNetworkRowId() {
		try {
			if (rowid != null && !rowid.isEmpty()) {
				networkCode = Long.valueOf(rowid);
			}
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	protected SearchSortContainer getSearchSortBean() {
		SearchSortContainer searchSortObj = new SearchSortContainer();
		searchSortObj.setSearchField(searchField);
		searchSortObj.setSearchString(searchString);
		searchSortObj.setSidx(sidx);
		searchSortObj.setSord(sord);
		searchSortObj.setCapacityScrnFlag(true);

		searchSortObj.setSearchUserName(searchUserName);
		searchSortObj.setSearchSupervisor(searchSupervisor);
		searchSortObj.setLoggedInFullName((String) session.get("fullName"));
		searchSortObj.setLoggedInId((Long) session.get("userId") + "");
		if (isBySupervisor()) {
			searchSortObj.setLoggedInId(id);
		}
		searchSortObj.setLoggedInUserRole((String) session.get("role"));
		searchSortObj.setSearchStatus(StringHelper.isEmpty(searchStatus) ? "Onboard" : searchStatus);
		searchSortObj.setSearchYear(year);
		searchSortObj.setAllReporteesFlag(true);

		searchSortObj.setRegion(StringHelper.isEmpty(ricoLocation) ? "ALL" : ricoLocation);

		searchSortObj.setPillarId(StringHelper.isEmpty(pillar) ? 0 : Integer.parseInt(pillar));
		searchSortObj.setProjectId(StringHelper.isEmpty(project) ? 0 : Integer.parseInt(project));
		searchSortObj.setTechnologyId(StringHelper.isEmpty(searchTechnology) ? 0 : Integer.parseInt(searchTechnology));
		searchSortObj.setStreamId(StringHelper.isEmpty(searchStream) ? 0 : Integer.parseInt(searchStream));

		return searchSortObj;
	}

	protected Pagination getPaginationObject() {
		Pagination pagination = new Pagination();
		int to = (rows * page);
		int from = to - rows;
		pagination.setOffset(from);
		pagination.setSize(to);
		return pagination;
	}

	public Long getSelectedEmployee() {
		return selectedEmployee;
	}

	public void setSelectedEmployee(Long selectedEmployee) {
		this.selectedEmployee = selectedEmployee;
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
	}

	public List<CapacityPlanning> getSubGridModel() {
		return subGridModel;
	}

	public void setSubGridModel(List<CapacityPlanning> subGridModel) {
		this.subGridModel = subGridModel;
	}

	public String getSearchUserName() {
		return searchUserName;
	}

	public void setSearchUserName(String searchUserName) {
		this.searchUserName = searchUserName;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getNetworkCode() {
		return networkCode;
	}

	public void setNetworkCode(Long networkCode) {
		this.networkCode = networkCode;
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

	public Map<Long, String> getUserNetworkCodesMap() {
		return userNetworkCodesMap;
	}

	public void setUserNetworkCodesMap(Map<Long, String> userNetworkCodesMap) {
		this.userNetworkCodesMap = userNetworkCodesMap;
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

	public Long getYear() {
		return year;
	}

	public void setYear(Long year) {
		this.year = year;
	}

	public String getSearchSupervisor() {
		return searchSupervisor;
	}

	public void setSearchSupervisor(String searchSupervisor) {
		this.searchSupervisor = searchSupervisor;
	}

	public boolean isAllReporteesFlag() {
		return allReporteesFlag;
	}

	public void setAllReporteesFlag(boolean allReporteesFlag) {
		this.allReporteesFlag = allReporteesFlag;
	}

	public List<User> getGridModel() {
		return gridModel;
	}

	public void setGridModel(List<User> gridModel) {
		this.gridModel = gridModel;
	}

	public String getNetworkId() {
		return networkId;
	}

	public void setNetworkId(String networkId) {
		this.networkId = networkId;
	}

	public Map<Long, String> getSupervisorMapForManage() {
		try {
			/*
			 * if (session.get("role") != null && ((String) session.get("role"))
			 * .equalsIgnoreCase("LINE MANAGER")) {
			 * serviceManager.getUserService().getSupervisorMap(supervisorMap, (Long)
			 * session.get("userId"), false); } else if (session.get("role") != null &&
			 * ((String) session.get("role")).equalsIgnoreCase("ADMIN")) {
			 * serviceManager.getUserService().getSupervisorMap(supervisorMap, (Long)
			 * session.get("userId"), false); }
			 */
			/*
			 * if (session.get("role") != null && ((String)
			 * session.get("role")).equalsIgnoreCase("ADMIN")) {
			 * serviceManager.getUserService().getSupervisorMap(supervisorMap, (Long)
			 * session.get("userId")); } else
			 */
			/*
			 * if (session.get("role") != null && ((String) session.get("role"))
			 * .equalsIgnoreCase("LINE MANAGER")) { supervisorMap.put(((Long)
			 * session.get("userId")), ((String) session.get("fullName"))); searchSupervisor
			 * = (Long) session.get("userId") + ""; }
			 */

			serviceManager.getUserService().getLineManagersMap(supervisorMap);

		} catch (Throwable e) {
			e.printStackTrace();
		}
		return supervisorMap;
	}

	public Map<Long, String> getSupervisorMap() {
		return supervisorMap;
	}

	public void setSupervisorMap(Map<Long, String> supervisorMap) {
		this.supervisorMap = supervisorMap;
	}

	@SkipValidation
	public String capacityPlanningReport() {
		try {
			if ((searchSupervisor == null || (searchSupervisor != null && searchSupervisor.isEmpty()))) {
				allReporteesFlag = true;
			}
			if (searchStatus == null || (searchStatus != null && searchStatus.isEmpty())) {
				searchStatus = "Onboard";
			}

			if (session.get("role") != null && ((String) session.get("role")).equalsIgnoreCase("LINE MANAGER")) {
				if ((searchSupervisor == null || (searchSupervisor != null && searchSupervisor.isEmpty()))) {
					searchSupervisor = (Long) session.get("userId") + "";
				}

			}
			if (ricoLocation == null || (ricoLocation != null && ricoLocation.equals(""))) {
				ricoLocation = "ALL";
			}

			goPillarsMap();
			goProjectsMap();
			serviceManager.getStreamsService().getStreamsMap(streamsMap);
			serviceManager.getTechnologiesService().getTechnologiesMap(technologiesMap);

		} catch (Throwable e) {
			e.printStackTrace();
		}

		return SUCCESS;
	}

	@SkipValidation
	public String goPillarsMap() {
		try {
			boolean isAdmin = false;
			if (session.get("role") != null && ((String) session.get("role")).equalsIgnoreCase("ADMIN")) {
				isAdmin = true;
			}
			if ((searchSupervisor == null || (searchSupervisor != null && searchSupervisor.isEmpty()))) {
				searchSupervisor = (Long) session.get("userId") + "";
			}
			serviceManager.getNetworkCodeReportService().getPillarsMap(pillarsMapObj, searchSupervisor, isAdmin);

		} catch (Throwable e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	@SkipValidation
	public String goProjectsMap() {
		try {
			boolean isAdmin = false;
			if (session.get("role") != null && ((String) session.get("role")).equalsIgnoreCase("ADMIN")) {
				isAdmin = true;
			}
			if ((searchSupervisor == null || (searchSupervisor != null && searchSupervisor.isEmpty()))) {
				searchSupervisor = (Long) session.get("userId") + "";
			}
			serviceManager.getNetworkCodeReportService().getProjectsMap(projectsMapObj, pillar, searchSupervisor,
					isAdmin);

		} catch (Throwable e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	@SkipValidation
	public String goGetCapacityPlanningReportDetails() {
		try {
			clearSession("supervisorMap", "userToBackFillMap", "privilegesMap", "rolesMap", "streamsMap",
					"platformsMap", "userTypesMap", "technologiesMap");
			if (session.get("rowNum") == null) {
				session.put("rowNum", rows);
			} else {
				if (rows != null && (rows.intValue() != ((Integer) session.get("rowNum")).intValue())) {
					session.put("rowNum", rows);
				}
			}
			SummaryResponse<com.egil.pts.modal.CapacityPlanning> summary = serviceManager.getCapacityPlanningService()
					.getUserCapacityPlanningReportDetails(getSearchSortBean(), selectedYear, getPaginationObject());
			capacityReportDetailsgridModel = summary.getEnitities();

			records = summary.getTotalRecords();

			// calculate the total pages for the query
			total = (int) Math.ceil((double) records / (double) rows);

		} catch (Throwable e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	@SkipValidation
	public String goGetUnPlannedCapacitySupname() {
		try {
			unPlancptyList.clear();
			if (year == null) {
				year = (long) Year.now().getValue();
			}

			unPlancptyList = serviceManager.getCapacityPlanningService()
					.getUnPlannedCapacityBySupervisor(getSearchSortBean(), isBySupervisor(), getPaginationObject());
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	public String redirectUnplannedCap() {
		if (year == null) {
			year = (long) Year.now().getValue();
		}
		if ((searchSupervisor == null || (searchSupervisor != null && searchSupervisor.isEmpty()))) {
			searchSupervisor = (Long) session.get("userId") + "";
		}
		return SUCCESS;

	}

	@SkipValidation
	public String goGetUnPlannedCapacityBySup() {
		try {
			unPlancptySunList.clear();
			if (year == null) {
				year = (long) Year.now().getValue();
			}
			setBySupervisor(true);
			unPlancptySunList = serviceManager.getCapacityPlanningService()
					.getUnPlannedCapacityBySupervisor(getSearchSortBean(), isBySupervisor(), getPaginationObject());
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	@SkipValidation
	public String goGetCapacityDetailsByProjectType() {
		try {
			clearSession("supervisorMap", "userToBackFillMap", "privilegesMap", "rolesMap", "streamsMap",
					"platformsMap", "userTypesMap", "technologiesMap");
			if (session.get("rowNum") == null) {
				session.put("rowNum", rows);
			} else {
				if (rows != null && (rows.intValue() != ((Integer) session.get("rowNum")).intValue())) {
					session.put("rowNum", rows);
				}
			}
			/*
			 * SummaryResponse<com.egil.pts.modal.CapacityPlanning> summary =
			 * serviceManager.getCapacityPlanningService()
			 * .getCapacityDetailsByProjectType(getSearchSortBean(), selectedYear,
			 * getPaginationObject(), null);
			 */
			// capacityReportDetailsgridModel = summary.getEnitities();
			cptyPlanDetByTypeList = serviceManager.getCapacityPlanningService()
					.getCapacityDetailsByProjectType(getSearchSortBean(), selectedYear, getPaginationObject(), null);
			// records = summary.getTotalRecords();

			// calculate the total pages for the query
			// total = (int) Math.ceil((double) records / (double) rows);

		} catch (Throwable e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	public Map<String, String> getStatusMap() {
		Map<String, String> statusMap = new LinkedHashMap<String, String>();
		statusMap.put("All", "All");
		statusMap.put("LTA", "LTA");
		statusMap.put("Open", "Open");
		statusMap.put("Selected", "Selected");
		statusMap.put("Induction", "Induction");
		statusMap.put("Onboard", "Onboard");
		statusMap.put("Notice Period", "Notice Period");
		statusMap.put("OffBoard", "OffBoard");
		statusMap.put("Interns/ GET", "Interns/ GET");
		statusMap.put("Non Billable", "Non Billable");
		statusMap.put("No Show", "No Show");
		statusMap.put("Others", "Others");
		return statusMap;
	}

	public List<com.egil.pts.modal.CapacityPlanning> getCapacityReportDetailsgridModel() {
		return capacityReportDetailsgridModel;
	}

	public void setCapacityReportDetailsgridModel(
			List<com.egil.pts.modal.CapacityPlanning> capacityReportDetailsgridModel) {
		this.capacityReportDetailsgridModel = capacityReportDetailsgridModel;
	}

	public String getReportType() {
		return reportType;
	}

	public void setReportType(String reportType) {
		this.reportType = reportType;
	}

	public List<CapacityPlanning> getNcGridModel() {
		return ncGridModel;
	}

	public void setNcGridModel(List<CapacityPlanning> ncGridModel) {
		this.ncGridModel = ncGridModel;
	}

	public String getSearchStatus() {
		return searchStatus;
	}

	public void setSearchStatus(String searchStatus) {
		this.searchStatus = searchStatus;
	}

	public List<CapacityPlanningByType> getCptyPlanDetByTypeList() {
		return cptyPlanDetByTypeList;
	}

	public void setCptyPlanDetByTypeList(List<CapacityPlanningByType> cptyPlanDetByTypeList) {
		this.cptyPlanDetByTypeList = cptyPlanDetByTypeList;
	}

	public String getRicoLocation() {
		return ricoLocation;
	}

	public void setRicoLocation(String ricoLocation) {
		this.ricoLocation = ricoLocation;
	}

	public Map<Long, String> getPillarsMapObj() {
		return pillarsMapObj;
	}

	public void setPillarsMapObj(Map<Long, String> pillarsMapObj) {
		this.pillarsMapObj = pillarsMapObj;
	}

	public Map<Long, String> getProjectsMapObj() {
		return projectsMapObj;
	}

	public void setProjectsMapObj(Map<Long, String> projectsMapObj) {
		this.projectsMapObj = projectsMapObj;
	}

	public Map<Long, String> getTechnologiesMap() {
		return technologiesMap;
	}

	public void setTechnologiesMap(Map<Long, String> technologiesMap) {
		this.technologiesMap = technologiesMap;
	}

	public String getSearchTechnology() {
		return searchTechnology;
	}

	public void setSearchTechnology(String searchTechnology) {
		this.searchTechnology = searchTechnology;
	}

	public String getProject() {
		return project;
	}

	public void setProject(String project) {
		this.project = project;
	}

	public String getPillar() {
		return pillar;
	}

	public void setPillar(String pillar) {
		this.pillar = pillar;
	}

	public Map<Long, String> getStreamsMap() {
		return streamsMap;
	}

	public void setStreamsMap(Map<Long, String> streamsMap) {
		this.streamsMap = streamsMap;
	}

	public String getSearchStream() {
		return searchStream;
	}

	public void setSearchStream(String searchStream) {
		this.searchStream = searchStream;
	}

	public Map<Long, String> getUsersMap() {
		return usersMap;
	}

	public void setUsersMap(Map<Long, String> usersMap) {
		this.usersMap = usersMap;
	}

	public Long getSupervisorId() {
		return supervisorId;
	}

	public void setSupervisorId(Long supervisorId) {
		this.supervisorId = supervisorId;
	}

	public String getSelectedUserId() {
		return selectedUserId;
	}

	public void setSelectedUserId(String selectedUserId) {
		this.selectedUserId = selectedUserId;
	}

	public List<User> getUnPlancptyList() {
		return unPlancptyList;
	}

	public void setUnPlancptyList(List<User> unPlancptyList) {
		this.unPlancptyList = unPlancptyList;
	}

	public boolean isBySupervisor() {
		return bySupervisor;
	}

	public void setBySupervisor(boolean bySupervisor) {
		this.bySupervisor = bySupervisor;
	}

	public List<User> getUnPlancptySunList() {
		return unPlancptySunList;
	}

	public void setUnPlancptySunList(List<User> unPlancptySunList) {
		this.unPlancptySunList = unPlancptySunList;
	}

	public String getEgilTotCapLimit() {
		return egilTotCapLimit;
	}

	public void setEgilTotCapLimit(String egilTotCapLimit) {
		this.egilTotCapLimit = egilTotCapLimit;
	}

	public String getManaTotCapLimit() {
		return manaTotCapLimit;
	}

	public void setManaTotCapLimit(String manaTotCapLimit) {
		this.manaTotCapLimit = manaTotCapLimit;
	}

}
