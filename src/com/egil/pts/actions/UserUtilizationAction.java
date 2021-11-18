package com.egil.pts.actions;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.interceptor.validation.SkipValidation;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.egil.pts.constants.ExcelHeaderConstants;
import com.egil.pts.modal.Pagination;
import com.egil.pts.modal.StableTeams;
import com.egil.pts.modal.SummaryResponse;
import com.egil.pts.modal.Utilization;

@Controller("userUtilizationAction")
@Scope("prototype")
public class UserUtilizationAction extends PTSAction {
	private static final long serialVersionUID = 1L;

	private List<Utilization> gridModel = new ArrayList<Utilization>();
	private boolean showAllRes;
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

	private Map<Long, String> employeeList = new LinkedHashMap<Long, String>();
	private Map<Long, String> projectManagers = new LinkedHashMap<Long, String>();

	private Map<Long, String> selectedNetworkCodesMap = new LinkedHashMap<Long, String>();
	private Map<Long, String> activityCodesMap = new LinkedHashMap<Long, String>();
	private Long selectedEmployee;
	private Long projectManagerId;
	private String reporteeType;

	private boolean userFlag = false;

	public String getReporteeType() {
		return reporteeType;
	}

	public void setReporteeType(String reporteeType) {
		this.reporteeType = reporteeType;
	}

	private Long activityCodeId;
	private Long networkCodeId;
	private float effortPerWeek;
	private String startWeek;
	private String endWeek;

	private boolean delinquentEntries;

	private List<StableTeams> stableTeams = new ArrayList<>();
	private Map<Long, String> stableTeamsmap = new LinkedHashMap<Long, String>();
	private Map<Long, String> networkCodesMap = new LinkedHashMap<Long, String>();
	private long stableTeamid;

	@SkipValidation
	public String manageUserUtilization() {
		try {
			clearSession("supervisorMap", "userToBackFillMap", "privilegesMap", "rolesMap", "streamsMap",
					"platformsMap", "userTypesMap", "technologiesMap");

			Long userId = (Long) session.get("userId");
			if (!showAllRes) {
				userId = null;
			}
			serviceManager.getUserService().getUserList(userId, employeeList, null, null);

			employeeList.put((Long) session.get("userId"), (String) session.get("fullName"));
			stableTeamsmap.clear();
			stableTeamsmap.put(-1L, "Please Select");
			stableTeams = serviceManager.getNetworkCodesService().getStableTeams(null);

			projectManagers = serviceManager.getUserService().getProjectManagersMap(projectManagers);

			for (StableTeams s : stableTeams)
				stableTeamsmap.put(s.getId(), s.getTeamName());

			serviceManager.getNetworkCodesService().getNetworkCodesStablesMap((Long) session.get("userId"),
					getNetworkCodesMap());

		} catch (Throwable e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	@SkipValidation
	public String goManageUserUtilization() {
		try {
			if (session.get("rowNum") == null || ((Integer) session.get("rowNum") == -1)) {
				rows = 20;
				session.put("rowNum", rows);
			} else {
				if (rows != null && (rows.intValue() != ((Integer) session.get("rowNum")).intValue())) {
					session.put("rowNum", rows);
				}
			}
			DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			Date fromDate = getWeekEnding(selectedDate);
			Date toDate = getWeekEnding(selectedToDate);
			Long supervisorid = (Long) session.get("userId");
			if (!showAllRes) {
				supervisorid = null;
			}
			SummaryResponse<Utilization> summary = null;
			if (session.get("role") != null && !(((String) session.get("role")).equalsIgnoreCase("USER"))) {
				summary = serviceManager.getUserUtilizationService().getUserUtilizationSummary(supervisorid,
						format.format(fromDate), format.format(toDate), selectedEmployee, reporteeType,
						getPaginationObject(), delinquentEntries, getWeekListBetweenDates(), stableTeamid,
						networkCodeId, projectManagerId);
			} else {
				summary = serviceManager.getUserUtilizationService().getUserUtilizationSummary(null,
						format.format(fromDate), format.format(toDate), selectedEmployee, reporteeType,
						getPaginationObject(), delinquentEntries, getWeekListBetweenDates(), -1L, null,
						projectManagerId);
			}
			gridModel = summary.getEnitities();
			long currid = 1L;

			if (gridModel != null && gridModel.size() > 0) {
				for (Utilization s : gridModel) {
					s.setCurrentGridId(currid);
					currid++;
				}

			}
			records = summary.getTotalRecords();
			// calculate the total pages for the query
			total = (int) Math.ceil((double) records / (double) rows);
			getWeekListBetweenDates().clear();
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	@SkipValidation
	public String goGetNetworkUtilization() {
		try {
			clearSession("supervisorMap", "userToBackFillMap", "privilegesMap", "rolesMap", "streamsMap",
					"platformsMap", "userTypesMap", "technologiesMap");
			if (session.get("role") != null && !(((String) session.get("role")).equalsIgnoreCase("USER"))) {
				serviceManager.getUserService().getUserList((Long) session.get("userId"), employeeList, null, null);
			} else {
				selectedEmployee = (Long) session.get("userId");
			}
			employeeList.put((Long) session.get("userId"), (String) session.get("fullName"));

			if (session.get("rowNum") == null) {
				session.put("rowNum", rows);
			} else {
				if (rows != null && (rows.intValue() != ((Integer) session.get("rowNum")).intValue())) {
					session.put("rowNum", rows);
				}
			}
			DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			SummaryResponse<Utilization> summary = null;
			summary = serviceManager.getUserUtilizationService().getUserUtilizationSummary(null,
					format.format(fromDate), format.format(toDate), selectedEmployee, reporteeType,
					getPaginationObject(), delinquentEntries, getWeekListBetweenDates(), -1L, null, projectManagerId);
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
	public String goNetworkUtilization() {
		try {
			clearSession("supervisorMap", "userToBackFillMap", "privilegesMap", "rolesMap", "streamsMap",
					"platformsMap", "userTypesMap", "technologiesMap");
			if (session.get("role") != null && !(((String) session.get("role")).equalsIgnoreCase("USER"))) {
				serviceManager.getUserService().getUserList((Long) session.get("userId"), employeeList, null, null);
			} else {
				selectedEmployee = (Long) session.get("userId");
			}
			employeeList.put((Long) session.get("userId"), (String) session.get("fullName"));
			// goManageUserUtilization();
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	@SkipValidation
	public String goRICOUtilizationReport() {
		try {
			projectManagers = serviceManager.getUserService().getProjectManagersMap(projectManagers);
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	@SkipValidation
	public String goRICOUtilizationReportDetails() {
		try {
			if (session.get("rowNum") == null) {
				session.put("rowNum", rows);
			} else if (rows == -1) {
				session.put("rowNum", 20);
			} else {
				if (rows != null && (rows.intValue() != ((Integer) session.get("rowNum")).intValue())) {
					session.put("rowNum", rows);
				}
			}
			Long userId = null;
			// if (userFlag) {
			userId = (Long) session.get("userId");
			Long userStream = ((Long) session.get("userStream")).longValue();
			// }
			if (userStream == 5 || userStream == 6) {
				userId = null;
			}
			userFlag = false;
			if ((session.get("role") != null && !(((String) session.get("role")).equalsIgnoreCase("ADMIN")))
					|| (session.get("role") != null
							&& !(((String) session.get("role")).equalsIgnoreCase("LINE MANAGER")))

			) {
				userFlag = true;
			}
			// selectedNetworkCodesMap
			// serviceManager.getNetworkCodesService().getStableTeamsMap(stableTeamsmap);
			SummaryResponse<Utilization> summary = serviceManager.getUserUtilizationService().getRICOUtilizationSummary(
					userFlag, userId, selectedDate, selectedToDate, getPaginationObject(), projectManagerId);
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
	public String downloadExcel() {
		try {
			SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
			String timeStamp = df.format(new Date());
			String filePath = getText("rico.summary.export.path");
			File dir = new File(filePath);
			if (!dir.exists()) {
				@SuppressWarnings("unused")
				boolean result = dir.mkdirs();
			}
			Long supervisorid = (Long) session.get("userId");
			if (!showAllRes) {
				supervisorid = null;
			}
			Pagination pagination = new Pagination();
			page = Integer.parseInt(session.get("currentPage") + "");
			if (page == 0)
				page = 1;
			int to = (Integer.parseInt(session.get("rowNum") + "") * page);
			int from = to - (Integer.parseInt(session.get("rowNum") + ""));
			pagination.setOffset(from);
			pagination.setSize(to);

			String fileName = filePath + getText("rico.summary.export.users.file.name") + timeStamp + ".xlsx";

			DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			Date fromDate = getWeekEnding(selectedDate);
			Date toDate = getWeekEnding(selectedToDate);
			SummaryResponse<Utilization> summary = null;
			if (session.get("role") != null && !(((String) session.get("role")).equalsIgnoreCase("USER"))) {
				summary = serviceManager.getUserUtilizationService().getUserUtilizationSummary(supervisorid,
						format.format(fromDate), format.format(toDate), selectedEmployee, reporteeType, pagination,
						delinquentEntries, getWeekListBetweenDates(), stableTeamid, networkCodeId, projectManagerId);
			} else {
				summary = serviceManager.getUserUtilizationService().getUserUtilizationSummary(null,
						format.format(fromDate), format.format(toDate), selectedEmployee, reporteeType, pagination,
						delinquentEntries, getWeekListBetweenDates(), -1L, null, projectManagerId);
			}

			filePath = serviceManager.getUserUtilizationService().generateExcel((Long) session.get("userId"), null,
					format.format(getWeekEnding(selectedDate)), format.format(getWeekEnding(selectedToDate)),
					selectedEmployee, reporteeType, fileName, summary);

			fileName = getFileName(filePath);
			pushFileToClient(filePath, fileName);
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return null;
	}

	@SkipValidation
	public String downloadAllExcel() {
		try {
			SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
			String timeStamp = df.format(new Date());
			String filePath = getText("rico.summary.export.path");
			File dir = new File(filePath);
			if (!dir.exists()) {
				@SuppressWarnings("unused")
				boolean result = dir.mkdirs();
			}
			Long supervisorid = (Long) session.get("userId");
			if (!showAllRes) {
				supervisorid = null;
			}

			String fileName = filePath + getText("rico.summary.export.users.file.name") + timeStamp + ".xlsx";

			DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			Date fromDate = getWeekEnding(selectedDate);
			Date toDate = getWeekEnding(selectedToDate);
			SummaryResponse<Utilization> summary = null;
			if (session.get("role") != null && !(((String) session.get("role")).equalsIgnoreCase("USER"))) {
				summary = serviceManager.getUserUtilizationService().getUserUtilizationSummary(supervisorid,
						format.format(fromDate), format.format(toDate), selectedEmployee, reporteeType, null,
						delinquentEntries, getWeekListBetweenDates(), stableTeamid, networkCodeId, projectManagerId);
			} else {
				summary = serviceManager.getUserUtilizationService().getUserUtilizationSummary(null,
						format.format(fromDate), format.format(toDate), selectedEmployee, reporteeType, null,
						delinquentEntries, getWeekListBetweenDates(), -1L, null, projectManagerId);
			}

			filePath = serviceManager.getUserUtilizationService().generateExcel((Long) session.get("userId"), null,
					format.format(getWeekEnding(selectedDate)), format.format(getWeekEnding(selectedToDate)),
					selectedEmployee, reporteeType, fileName, summary);

			fileName = getFileName(filePath);
			pushFileToClient(filePath, fileName);
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return null;
	}

	@SkipValidation
	public String exportResourceStableDetails() {
		try {
			Pagination pagination = new Pagination();
			page = Integer.parseInt(session.get("currentPage") + "");
			if (page == 0)
				page = 1;
			int to = (Integer.parseInt(session.get("rowNum") + "") * page);
			int from = to - (Integer.parseInt(session.get("rowNum") + ""));
			pagination.setOffset(from);
			pagination.setSize(to);

			SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
			String timeStamp = df.format(new Date());
			String filePath = getText("rico.summary.export.path");
			File dir = new File(filePath);
			if (!dir.exists()) {
				@SuppressWarnings("unused")
				boolean result = dir.mkdirs();
			}
			Long userId = (Long) session.get("userId");
			if (session.get("role") != null && (((String) session.get("role")).equalsIgnoreCase("ADMIN"))) {
				userId = null;
			}
			String fileName = filePath + getText("rico.summary.export.users.stable.file.name") + timeStamp + ".xlsx";
			DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			filePath = serviceManager.getUserUtilizationService().generateUserStableExcel(userId, pagination,
					format.format(getWeekEnding(selectedDate)), format.format(getWeekEnding(selectedToDate)),
					selectedEmployee, reporteeType, fileName, false);
			fileName = getFileName(filePath);
			pushFileToClient(filePath, fileName);
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return null;
	}

	@SkipValidation
	public String exportAllResourceStableDetails() {
		try {

			SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
			String timeStamp = df.format(new Date());
			String filePath = getText("rico.summary.export.path");
			File dir = new File(filePath);
			if (!dir.exists()) {
				@SuppressWarnings("unused")
				boolean result = dir.mkdirs();
			}
			Long userId = (Long) session.get("userId");
			if (session.get("role") != null && (((String) session.get("role")).equalsIgnoreCase("ADMIN"))) {
				userId = null;
			}
			String fileName = filePath + getText("rico.summary.export.users.stable.file.name") + timeStamp + ".xlsx";
			DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			filePath = serviceManager.getUserUtilizationService().generateUserStableExcel(userId, null,
					format.format(getWeekEnding(selectedDate)), format.format(getWeekEnding(selectedToDate)),
					selectedEmployee, reporteeType, fileName, true);
			fileName = getFileName(filePath);
			pushFileToClient(filePath, fileName);
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return null;
	}

	@SkipValidation
	public String generateRICOUtilizationSummaryExcel() {
		try {

			SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
			String timeStamp = df.format(new Date());
			String filePath = getText("rico.summary.export.path");
			File dir = new File(filePath);
			if (!dir.exists()) {
				@SuppressWarnings("unused")
				boolean result = dir.mkdirs();
			}

			String fileName = filePath + getText("rico.summary.export.users.file.name") + timeStamp + ".xlsx";
			Map<String, String> excelColHeaders = new LinkedHashMap<String, String>();

			setRICOUtilizationColumnHeaders(excelColHeaders);
			Long userId = null;
			if (userFlag) {
				userId = (Long) session.get("userId");
			}
			filePath = serviceManager.getUserUtilizationService().generateRICOUtilizationSummaryExcel(userId,
					selectedDate, selectedToDate, fileName, excelColHeaders);
			fileName = getFileName(filePath);
			pushFileToClient(filePath, fileName);
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return null;
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

	public List<Utilization> getGridModel() {
		return gridModel;
	}

	public void setGridModel(List<Utilization> gridModel) {
		this.gridModel = gridModel;
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

	public Long getSelectedEmployee() {
		return selectedEmployee;
	}

	public void setSelectedEmployee(Long selectedEmployee) {
		this.selectedEmployee = selectedEmployee;
	}

	public Map<Long, String> getActivityCodesMap() {
		return activityCodesMap;
	}

	public void setActivityCodesMap(Map<Long, String> activityCodesMap) {
		this.activityCodesMap = activityCodesMap;
	}

	public Long getActivityCodeId() {
		return activityCodeId;
	}

	public void setActivityCodeId(Long activityCodeId) {
		this.activityCodeId = activityCodeId;
	}

	public Long getNetworkCodeId() {
		return networkCodeId;
	}

	public void setNetworkCodeId(Long networkCodeId) {
		this.networkCodeId = networkCodeId;
	}

	public float getEffortPerWeek() {
		return effortPerWeek;
	}

	public void setEffortPerWeek(float effortPerWeek) {
		this.effortPerWeek = effortPerWeek;
	}

	public String getStartWeek() {
		return startWeek;
	}

	public void setStartWeek(String startWeek) {
		this.startWeek = startWeek;
	}

	public String getEndWeek() {
		return endWeek;
	}

	public void setEndWeek(String endWeek) {
		this.endWeek = endWeek;
	}

	public boolean isDelinquentEntries() {
		return delinquentEntries;
	}

	public void setDelinquentEntries(boolean delinquentEntries) {
		this.delinquentEntries = delinquentEntries;
	}

	protected Pagination getPaginationObject() {
		Pagination pagination = new Pagination();
		session.put("currentPage", page);
		int to = (rows * page);
		int from = to - rows;
		pagination.setOffset(from);
		pagination.setSize(to);
		return pagination;
	}

	private void setColumnHeaders(Map<String, String> excelColHeaders) {

		excelColHeaders.put(ExcelHeaderConstants.WEEKENDING, ExcelHeaderConstants.WEEKENDING);
		excelColHeaders.put(ExcelHeaderConstants.NETWORK_ID, ExcelHeaderConstants.NETWORK_ID);
		excelColHeaders.put(ExcelHeaderConstants.RELEASE_NAME, ExcelHeaderConstants.RELEASE_NAME);
		excelColHeaders.put(ExcelHeaderConstants.ACTIVITY_CODE, ExcelHeaderConstants.ACTIVITY_CODE);
		excelColHeaders.put(ExcelHeaderConstants.ACTIVITY_DESC, ExcelHeaderConstants.ACTIVITY_DESC);
		excelColHeaders.put(ExcelHeaderConstants.TIME_TYPE, ExcelHeaderConstants.TIME_TYPE);
		excelColHeaders.put(ExcelHeaderConstants.HOURS, ExcelHeaderConstants.HOURS);
		excelColHeaders.put(ExcelHeaderConstants.RESOURCE_NAME, ExcelHeaderConstants.RESOURCE_NAME);
		excelColHeaders.put(ExcelHeaderConstants.EMPLOYEE_TYPE, ExcelHeaderConstants.EMPLOYEE_TYPE);
		excelColHeaders.put(ExcelHeaderConstants.MANAGER, ExcelHeaderConstants.MANAGER);
	}

	private void setRICOUtilizationColumnHeaders(Map<String, String> excelColHeaders) {
		excelColHeaders.put(ExcelHeaderConstants.SIGNUM, ExcelHeaderConstants.SIGNUM);
		excelColHeaders.put(ExcelHeaderConstants.WEEKENDING, ExcelHeaderConstants.WEEKENDING);
		excelColHeaders.put(ExcelHeaderConstants.RESOURCE_NAME, ExcelHeaderConstants.RESOURCE_NAME);
		excelColHeaders.put(ExcelHeaderConstants.MANAGER, ExcelHeaderConstants.MANAGER);
		excelColHeaders.put(ExcelHeaderConstants.NETWORK_ID, ExcelHeaderConstants.NETWORK_ID);
		excelColHeaders.put(ExcelHeaderConstants.ACTIVITY_CODE, ExcelHeaderConstants.ACTIVITY_CODE);
		excelColHeaders.put(ExcelHeaderConstants.TIME_TYPE, ExcelHeaderConstants.TIME_TYPE);
		excelColHeaders.put(ExcelHeaderConstants.HOURS, ExcelHeaderConstants.HOURS);
	}

	@SkipValidation
	public String sendMail() {
		try {
			DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			Date fromDate = getWeekEnding(selectedDate);
			Date toDate = getWeekEnding(selectedToDate);
			serviceManager.getUserUtilizationService().sendMail((Long) session.get("userId"), format.format(fromDate),
					format.format(toDate), selectedEmployee, reporteeType, (String) session.get("email"),
					getWeekListBetweenDates());

			message = "Email sent Successfully...";
		} catch (Throwable e) {
			e.printStackTrace();
			message = "Email not sent...";
		}

		return SUCCESS;
	}

	public boolean isUserFlag() {
		return userFlag;
	}

	public void setUserFlag(boolean userFlag) {
		this.userFlag = userFlag;
	}

	public long getStableTeamid() {
		return stableTeamid;
	}

	public void setStableTeamid(long stableTeamid) {
		this.stableTeamid = stableTeamid;
	}

	public Map<Long, String> getStableTeamsmap() {
		return stableTeamsmap;
	}

	public void setStableTeamsmap(Map<Long, String> stableTeamsmap) {
		this.stableTeamsmap = stableTeamsmap;
	}

	public List<StableTeams> getStableTeams() {
		return stableTeams;
	}

	public void setStableTeams(List<StableTeams> stableTeams) {
		this.stableTeams = stableTeams;
	}

	public Map<Long, String> getNetworkCodesMap() {
		return networkCodesMap;
	}

	public void setNetworkCodesMap(Map<Long, String> networkCodesMap) {
		this.networkCodesMap = networkCodesMap;
	}

	public boolean isShowAllRes() {
		return showAllRes;
	}

	public void setShowAllRes(boolean showAllRes) {
		this.showAllRes = showAllRes;
	}

	public Long getProjectManagerId() {
		return projectManagerId;
	}

	public void setProjectManagerId(Long projectManagerId) {
		this.projectManagerId = projectManagerId;
	}

	public Map<Long, String> getProjectManagers() {
		return projectManagers;
	}

	public void setProjectManagers(Map<Long, String> projectManagers) {
		this.projectManagers = projectManagers;
	}

}
