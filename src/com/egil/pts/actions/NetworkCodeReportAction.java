package com.egil.pts.actions;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.struts2.interceptor.validation.SkipValidation;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.egil.pts.constants.ExcelHeaderConstants;
import com.egil.pts.modal.NetworkCodeEffort;
import com.egil.pts.modal.Pagination;
import com.egil.pts.modal.SummaryResponse;

@Controller("networkCodeReportAction")
@Scope("prototype")
public class NetworkCodeReportAction extends PTSAction {
	private static final long serialVersionUID = 1L;

	private List<NetworkCodeEffort> gridModel = new ArrayList<NetworkCodeEffort>();

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

	private Map<Long, String> selectedNetworkCodesMap = new LinkedHashMap<Long, String>();
	private Long selectedNetworkCode = 0l;

	@SkipValidation
	public String manageNCUtilization() {
		try {
			clearSession("supervisorMap", "userToBackFillMap", "privilegesMap",
					"rolesMap", "streamsMap", "platformsMap", "userTypesMap",
					"technologiesMap");
			/*
			 * serviceManager.getUserService().getUserList( (Long)
			 * session.get("userId"), employeeList, null, null);
			 * employeeList.put((Long) session.get("userId"), (String)
			 * session.get("fullName"));
			 */
			// goManageUserUtilization();
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	@SkipValidation
	public String goManageNCUtilization() {
		try {
			

			Date fromWknddDate = getWeekEnding(selectedDate);
			Date toWkndDate = getWeekEnding(selectedToDate);
			
			DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			DateFormat df = new SimpleDateFormat("EEE");
			DateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");

			String fDate = "";
			String tDate = "";
			String startDay = "";
			String endDay = "";
			String nextWeekOfFDate = "";
			String prevWeekOfTDate = "";

			Date nextWeekOfFromDate = null;
			Date prevWeekOfToDate = null;

			if (selectedDate != null && !selectedDate.equals("")
					&& selectedToDate != null && !selectedToDate.equals("")) {
				fDate = format.format(fromWknddDate);
				tDate = format.format(toWkndDate);
				long diff = sdf.parse(selectedToDate).getTime() - sdf.parse(
						selectedDate).getTime();
				long diffDays = diff / (1000 * 60 * 60 * 24);
				if (sdf.parse(selectedDate).equals(sdf.parse(selectedToDate))) {
					startDay = df.format(sdf.parse(selectedDate));
					tDate = "";
				} else if (((diffDays) < 7)
						&& fromWknddDate.equals(toWkndDate)) {
					startDay = df.format(sdf.parse(selectedDate));
					endDay = df.format(sdf.parse(selectedToDate));
					tDate = "";
				} else if (((diffDays) < 14)
						&& !fromWknddDate.equals(toWkndDate)) {
					startDay = df.format(sdf.parse(selectedDate));
					endDay = df.format(sdf.parse(selectedToDate));
				} else {
					if (fromWknddDate != null) {
						startDay = df.format(sdf.parse(selectedDate));
						nextWeekOfFromDate = new Date(fromWknddDate.getTime()
								+ TimeUnit.DAYS.toMillis(7));
						nextWeekOfFDate = format.format(nextWeekOfFromDate);
					}

					if (toWkndDate != null) {
						endDay = df.format(sdf.parse(selectedToDate));
						prevWeekOfToDate = new Date(toWkndDate.getTime()
								- TimeUnit.DAYS.toMillis(7));
						prevWeekOfTDate = format.format(prevWeekOfToDate);
					}
				}
			}
			
			boolean isAdmin = false;
			if (session.get("role") != null
					&& ((String) session.get("role")).equalsIgnoreCase("ADMIN")) {
				isAdmin = true;
			}

			SummaryResponse<NetworkCodeEffort> summary = serviceManager
					.getNetworkCodeReportService()
					.getNetworkCodeUtilizationSummary(
							(Long) session.get("userId"), selectedNetworkCode,
							fDate, tDate,
							nextWeekOfFDate, prevWeekOfTDate, startDay, endDay,
							getPaginationObject(), isAdmin);
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

			String fileName = filePath
					+ getText("rico.summary.export.users.file.name")
					+ timeStamp + ".xlsx";
			Map<String, String> excelColHeaders = new LinkedHashMap<String, String>();

			setColumnHeaders(excelColHeaders);
			//DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			/*
			 * filePath = serviceManager.getUserUtilizationService()
			 * .generateExcel((Long) session.get("userId"), excelColHeaders,
			 * format.format(getWeekEnding(selectedDate)),
			 * format.format(getWeekEnding(selectedToDate)),
			 * selectedNetworkCode, reporteeType, fileName);
			 */
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
			this.total = (int) Math.ceil((double) this.records
					/ (double) this.rows);
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

	public List<NetworkCodeEffort> getGridModel() {
		return gridModel;
	}

	public void setGridModel(List<NetworkCodeEffort> gridModel) {
		this.gridModel = gridModel;
	}

	public Map<Long, String> getSelectedNetworkCodesMap() {
		return selectedNetworkCodesMap;
	}

	public void setSelectedNetworkCodesMap(
			Map<Long, String> selectedNetworkCodesMap) {
		this.selectedNetworkCodesMap = selectedNetworkCodesMap;
	}

	public Long getSelectedNetworkCode() {
		return selectedNetworkCode;
	}

	public void setSelectedNetworkCode(Long selectedNetworkCode) {
		this.selectedNetworkCode = selectedNetworkCode;
	}

	protected Pagination getPaginationObject() {
		Pagination pagination = new Pagination();
		int to = (rows * page);
		int from = to - rows;
		pagination.setOffset(from);
		pagination.setSize(to);
		return pagination;
	}

	private void setColumnHeaders(Map<String, String> excelColHeaders) {

		excelColHeaders.put(ExcelHeaderConstants.WEEKENDING,
				ExcelHeaderConstants.WEEKENDING);
		excelColHeaders.put(ExcelHeaderConstants.NETWORK_ID,
				ExcelHeaderConstants.NETWORK_ID);
		excelColHeaders.put(ExcelHeaderConstants.RELEASE_NAME,
				ExcelHeaderConstants.RELEASE_NAME);
		excelColHeaders.put(ExcelHeaderConstants.ACTIVITY_CODE,
				ExcelHeaderConstants.ACTIVITY_CODE);
		excelColHeaders.put(ExcelHeaderConstants.ACTIVITY_DESC,
				ExcelHeaderConstants.ACTIVITY_DESC);
		excelColHeaders.put(ExcelHeaderConstants.TIME_TYPE,
				ExcelHeaderConstants.TIME_TYPE);
		excelColHeaders.put(ExcelHeaderConstants.HOURS,
				ExcelHeaderConstants.HOURS);
		excelColHeaders.put(ExcelHeaderConstants.RESOURCE_NAME,
				ExcelHeaderConstants.RESOURCE_NAME);
		excelColHeaders.put(ExcelHeaderConstants.EMPLOYEE_TYPE,
				ExcelHeaderConstants.EMPLOYEE_TYPE);
		excelColHeaders.put(ExcelHeaderConstants.MANAGER,
				ExcelHeaderConstants.MANAGER);
	}
}
