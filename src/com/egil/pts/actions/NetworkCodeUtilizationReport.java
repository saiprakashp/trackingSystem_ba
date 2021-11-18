package com.egil.pts.actions;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.struts2.interceptor.validation.SkipValidation;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;

import com.egil.pts.modal.BulkResponse;
import com.egil.pts.modal.NetworkCodeEffort;
import com.egil.pts.modal.Pagination;
import com.egil.pts.modal.Project;
import com.egil.pts.modal.ReleaseTrainDetails;
import com.egil.pts.modal.SearchSortContainer;
import com.egil.pts.modal.StableTeams;
import com.egil.pts.modal.Status;
import com.egil.pts.modal.SummaryResponse;
import com.egil.pts.modal.UserCapacity;
import com.egil.pts.util.GenericExcel;
import com.egil.pts.util.Utility;
import com.opensymphony.xwork2.ActionContext;

@Controller("networkCodeUtilizationReport")
@Scope("prototype")
public class NetworkCodeUtilizationReport extends PTSAction {
	private static final long serialVersionUID = 1L;

	private List<Project> projectGridModel = new ArrayList<Project>();

	private List<NetworkCodeEffort> ncGridModel = new ArrayList<NetworkCodeEffort>();

	private List<NetworkCodeEffort> userNCGridModel = new ArrayList<NetworkCodeEffort>();

	private List<UserCapacity> userEffortGridModel = new ArrayList<UserCapacity>();

	private List<ReleaseTrainDetails> releaseTrainGridModel = new ArrayList<ReleaseTrainDetails>();
	private List<UserCapacity> supervisorNwList = new ArrayList<UserCapacity>();
	private String searchUserName;
	private String searchSupervisor;
	private static String prevsearchSupervisor;
	private String searchFlag;
	private String searchProjectManager;
	private boolean allReporteesFlag;
	protected String project;
	protected String pillar;
	protected String release;
	private String projectNew;
	private String pillarNew;
	private String releaseNew;
	private String reportType;
	private String filename = "";
	private Map<Long, String> pillarsMapObj = new LinkedHashMap<Long, String>();
	private Map<Long, String> resourceMapObj = new LinkedHashMap<Long, String>();
	private Map<Long, String> projectsMapObj = new LinkedHashMap<Long, String>();
	private Map<Long, String> releasesMapObj = new LinkedHashMap<Long, String>();
	private Map<Long, String> stableTeamsMapobj = new LinkedHashMap<Long, String>();
	private Map<Long, String> supervisorMap = new LinkedHashMap<Long, String>();
	private Map<Long, String> projectManagersMapObj = new LinkedHashMap<Long, String>();
	private Integer monthSelected;
	private Map<Integer, String> monthMap;
	private String status;
	private Map<String, String> networkStatusMap = new LinkedHashMap<String, String>();

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

	private Long searchResource;

	// All Records
	private Integer records = 0;

	private String rowid;

	private InputStream inputStream;
	private String fileName;
	private long contentLength;
	private long stableTeamId;
	private String type;

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
			selectedYear = Long.valueOf(Calendar.getInstance().get(Calendar.YEAR));
			goProjectManagersMap();
			goPillarsMap();
			goProjectsMap();
			gostatusMap();
			goReleaseMap();
			goStableTeamMap();
			if (type == null) {
				type = "DEFAULT";
			}
			if (reportType == null) {
				reportType = "DEFAULT";
			}
			if (status == null) {
				status = "-1";
			}

		} catch (Throwable e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	private void goStableTeamMap() {
		stableTeamsMapobj.clear();
		stableTeamsMapobj.put(-1L, "Please Select");
		List<StableTeams> stableTeams = serviceManager.getNetworkCodesService().getStableTeams(null);
		for (StableTeams s : stableTeams)
			stableTeamsMapobj.put(s.getId(), s.getTeamName());

	}

	public String gostatusMap() {

		getNetworkStatusMap().put(Status.EarlyStart.name(), Status.EarlyStart.name());
		getNetworkStatusMap().put(Status.Execution.name(), Status.Execution.name());
		getNetworkStatusMap().put(Status.Warranty.name(), Status.Warranty.name());
		getNetworkStatusMap().put(Status.Implemented.name(), Status.Implemented.name());
		getNetworkStatusMap().put(Status.ImpactAnalysis.name(), Status.ImpactAnalysis.name());
		getNetworkStatusMap().put(Status.Packaging.name(), Status.Packaging.name());
		getNetworkStatusMap().put(Status.Backlog.name(), Status.Backlog.name());
		getNetworkStatusMap().put(Status.IN_ACTIVE.name(), Status.IN_ACTIVE.name());
		getNetworkStatusMap().put(Status.Cancelled.name(), Status.Cancelled.name());
		getNetworkStatusMap().put("ALL", "ALL");

		return SUCCESS;
	}

	@SkipValidation
	public String goUserNWUtilization() {

		try {
			if ((searchSupervisor == null || (searchSupervisor != null && searchSupervisor.isEmpty()))) {
				allReporteesFlag = true;
			}
			if (searchSupervisor != null && prevsearchSupervisor != null
					&& !searchSupervisor.equalsIgnoreCase(prevsearchSupervisor)) {
				prevsearchSupervisor = searchSupervisor;
				searchResource = null;
			} else if (prevsearchSupervisor == null) {
				prevsearchSupervisor = searchSupervisor;
				searchResource = null;
			}
			if (session.get("role") != null && ((String) session.get("role")).equalsIgnoreCase("LINE MANAGER")) {
				if ((searchSupervisor == null || (searchSupervisor != null && searchSupervisor.isEmpty()))) {
					searchSupervisor = (Long) session.get("userId") + "";
					searchResource = null;
				}

			}
			if (reportType == null && (reportType != null && reportType.isEmpty())) {
				reportType = "MONTHLY";
			}
			monthMap = Utility.monthsMap;
			monthMap.put(-1, "All");
			if (selectedYear == null)
				selectedYear = Long.valueOf(Calendar.getInstance().get(Calendar.YEAR));

			goProjectManagersMap();
			goPillarsMap();
			goProjectsMap();
			goReleaseMap();
			goUsersMap();
			if (monthSelected == null)
				monthSelected = -1;
			releasesMapObj.put(1L, "All");
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	private void goUsersMap() {

		serviceManager.getUserService().getAllResources(resourceMapObj,
				(searchSupervisor == null || (searchSupervisor != null && searchSupervisor.isEmpty())) ? null
						: Long.valueOf(searchSupervisor));
	}

	@SkipValidation
	public String goGetUserNWUtilization() {
		try {
			supervisorNwList.clear();
			if (session.get("rowNum") == null) {
				session.put("rowNum", rows);
			} else {
				if (rows != null && (rows.intValue() != ((Integer) session.get("rowNum")).intValue())) {
					session.put("rowNum", rows);
				}
			}
			Long userId = null;
			String role = session.get("role") + "";
			if (role.equalsIgnoreCase("ADMIN")) {
				userId = (searchSupervisor != null && searchSupervisor.isEmpty()) ? null
						: Long.valueOf(searchSupervisor);
			}

			SearchSortContainer searchSortObj = new SearchSortContainer();
			searchSortObj.setSidx(sidx);
			searchSortObj.setSord(sord);
			List<UserCapacity> summary = serviceManager.getPtsReportsService().getSupervisorNWEffort(userId,
					(pillar == null || (pillar != null && pillar.isEmpty())) ? null : Long.parseLong(pillar),
					((project != null && project.isEmpty()) || project == null) ? null : Long.parseLong(project),
					reportType, getMonthSelected() != null ? getMonthSelected() : new Date().getMonth() + 1,
					selectedYear);

			supervisorNwList.addAll(summary);
			records = summary.size();
			// calculate the total pages for the query
			total = (int) Math.ceil((double) records / (double) rows);

		} catch (Throwable e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	public String downloadUserNWUtilization() {

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
			List<NetworkCodeEffort> summary = null;
			if (projectNew != null && !projectNew.equals("")) {
				Long ncId = null;
				if (releaseNew != null && !releaseNew.equals("")) {
					ncId = Long.parseLong(releaseNew);
				}

				summary = serviceManager.getNetworkCodeReportService().getNetworkCodesEffortOfProject(
						selectedYear.intValue(), Long.parseLong(projectNew), ncId, Long.parseLong(searchSupervisor),
						isAdmin, getType(), reportType, getWeekEndingString(selectedDate),
						getWeekEndingString(selectedToDate));
				float tot = 0.0f;
				for (NetworkCodeEffort a : summary) {
					tot += a.getSUMMATION();
				}
				SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
				String timeStamp = df.format(new Date());
				String filePath = getText("rico.summary.export.path");
				File dir = new File(filePath);
				if (!dir.exists()) {
					@SuppressWarnings("unused")
					boolean result = dir.mkdirs();
				}
				String Fname = "RICO_USER_NW_REPORT" + timeStamp + ".xlsx";
				// TestCon.GenerateReport(2020);
				String fileName = filePath + Fname;
				if (session.get("role") != null && ((String) session.get("role")).equalsIgnoreCase("ADMIN")) {
					isAdmin = true;
				}

				LinkedHashMap<String, String> excelColHeaders = new LinkedHashMap<String, String>();

				excelColHeaders.put("RELEASE", "RELEASE");
				if (reportType != null && reportType.equalsIgnoreCase("USER"))
					excelColHeaders.put("USER", "USER");
				excelColHeaders.put("EFFORT", "EFFORT");
				if (type != null && type.equalsIgnoreCase("MONTH")) {
					excelColHeaders.put("MONTH", "MONTH");
				} else if (type != null && type.equalsIgnoreCase("WEEKEND_DATE")) {
					excelColHeaders.put("WEEKEND_DATE", "WEEKEND_DATE");
				}
				SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
				String[] colHeaders = new String[excelColHeaders.size()];
				String[] colHeaderKeys = new String[excelColHeaders.size()];
				int colHeaderSize = excelColHeaders.size();
				int colCount = 0;
				Iterator<String> colIterator = excelColHeaders.keySet().iterator();

				while (colIterator.hasNext()) {
					colHeaderKeys[colCount] = colIterator.next();
					colHeaders[colCount] = excelColHeaders.get(colHeaderKeys[colCount]);
					colCount++;
				}
				File file = new File(fileName);
				if (!file.exists()) {
					file.createNewFile();
				}
				GenericExcel excel = new GenericExcel(fileName);

				int rowSize = summary.size();
				String[][] activityRecords = new String[rowSize + 1][colHeaderSize];

				int row = 0, col = 0;

				for (String header : excelColHeaders.values()) {
					activityRecords[row][col] = header;
					col++;
				}
				row++;
				for (NetworkCodeEffort s : summary) {
					col = 0;

					activityRecords[row][col] = s.getNETWORKCODE() + "";
					col++;
					if (reportType != null && reportType.equalsIgnoreCase("USER")) {
						activityRecords[row][col] = s.getUSER() + "";
						col++;
					}
					activityRecords[row][col] = s.getSUMMATION() + "";
					col++;

					if (type != null && type.equalsIgnoreCase("MONTH")) {
						activityRecords[row][col] = s.getMONTH() + "";
						col++;
					} else if (type != null && type.equalsIgnoreCase("WEEKEND_DATE")) {
						activityRecords[row][col] = format.format(s.getWEEKENDING_DATE());
						col++;
					}

					row++;
				}
				excel.writeSheet(Fname, activityRecords);
				excel.setCustomRowStyleForHeader(0, 0, IndexedColors.GREY_40_PERCENT.index, 30);

				boolean status = excel.writeWorkbook();

				if (status) {
					pushFileToClient((fileName), fileName);
				} else {
					addActionError("");
					return ERROR;
				}
			}

		} catch (Throwable throwable) {
			throwable.printStackTrace();
			return ERROR;
		}
		return null;

	}

	@SkipValidation
	public String goProjectManagersMap() {
		try {
			serviceManager.getUserService().getProjectManagersMap(projectManagersMapObj, null);
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
			/*
			 * if (pillar == null || (pillar != null && pillar.trim().isEmpty())) { if
			 * (pillarsMapObj != null && pillarsMapObj.size() > 0) { Map.Entry<Long, String>
			 * entry = pillarsMapObj.entrySet() .iterator().next(); pillar = entry.getKey()
			 * + ""; } }
			 */
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
			/*
			 * if (project == null || (project != null && project.trim().isEmpty())) { if
			 * (projectsMapObj != null && projectsMapObj.size() > 0) { Map.Entry<Long,
			 * String> entry = projectsMapObj.entrySet() .iterator().next(); project =
			 * entry.getKey() + ""; } }
			 */
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	@SkipValidation
	public String goProjectsMapNew() {
		try {
			boolean isAdmin = false;
			if (session.get("role") != null && ((String) session.get("role")).equalsIgnoreCase("ADMIN")) {
				isAdmin = true;
			}
			if ((searchSupervisor == null || (searchSupervisor != null && searchSupervisor.isEmpty()))) {
				searchSupervisor = (Long) session.get("userId") + "";
			}
			serviceManager.getNetworkCodeReportService().getProjectsMap(projectsMapObj, pillar, searchSupervisor, true);
			/*
			 * if (project == null || (project != null && project.trim().isEmpty())) { if
			 * (projectsMapObj != null && projectsMapObj.size() > 0) { Map.Entry<Long,
			 * String> entry = projectsMapObj.entrySet() .iterator().next(); project =
			 * entry.getKey() + ""; } }
			 */
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	@SkipValidation
	public String goReleaseMap() {
		try {
			goProjectsMap();
			Iterator<Long> keyIt = projectsMapObj.keySet().iterator();
			if (!StringUtils.isEmpty(project))
				while (keyIt.hasNext()) {
					Long key = keyIt.next()  ;
					if (projectsMapObj.get(key).equalsIgnoreCase(project)) {
						project = key+"";
						break;
					}
				}
			
			boolean isAdmin = false;
			if (session.get("role") != null && ((String) session.get("role")).equalsIgnoreCase("ADMIN")) {
				isAdmin = true;
			}
			if ((searchSupervisor == null || (searchSupervisor != null && searchSupervisor.isEmpty()))) {
				searchSupervisor = (Long) session.get("userId") + "";
			}
			// Project manager searchProjectManager
			// Product pillar
			// Application project
			// System.out.println(releasesMapObj + " " + project + " " +
			// searchProjectManager + " " + searchSupervisor
			// + " " + isAdmin+" "+pillar);

			pillar = (pillar != null && pillar.equalsIgnoreCase("-1")) ? null : pillar;
			project = (project != null && project.equalsIgnoreCase("-1")) ? null : project;
			searchProjectManager = (searchProjectManager != null && searchProjectManager.equalsIgnoreCase("-1")) ? null
					: searchProjectManager;

			serviceManager.getNetworkCodeReportService().getReleaseMap(releasesMapObj, project, searchSupervisor,
					isAdmin, pillar, searchProjectManager, status);
			/*
			 * if (release == null || (release != null && release.trim().isEmpty())) { if
			 * (releasesMapObj != null && releasesMapObj.size() > 0) { Map.Entry<Long,
			 * String> entry = releasesMapObj.entrySet() .iterator().next(); release =
			 * entry.getKey() + ""; } }
			 */
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	@SkipValidation
	public String goReleaseMapNew() {
		try {
			boolean isAdmin = false;
			if (session.get("role") != null && ((String) session.get("role")).equalsIgnoreCase("ADMIN")) {
				isAdmin = true;
			}
			if ((searchSupervisor == null || (searchSupervisor != null && searchSupervisor.isEmpty()))) {
				searchSupervisor = (Long) session.get("userId") + "";
			}
			// Project manager searchProjectManager
			// Product pillar
			// Application project
			// System.out.println(releasesMapObj + " " + project + " " +
			// searchProjectManager + " " + searchSupervisor
			// + " " + isAdmin+" "+pillar);

			pillar = (pillar != null && pillar.equalsIgnoreCase("-1")) ? null : pillar;
			project = (project != null && project.equalsIgnoreCase("-1")) ? null : project;
			searchProjectManager = (searchProjectManager != null && searchProjectManager.equalsIgnoreCase("-1")) ? null
					: searchProjectManager;
			releasesMapObj.put(-1L, "Please Select");
			serviceManager.getNetworkCodeReportService().getReleaseMap(releasesMapObj, project, searchSupervisor, true,
					pillar, searchProjectManager, status);
			/*
			 * if (release == null || (release != null && release.trim().isEmpty())) { if
			 * (releasesMapObj != null && releasesMapObj.size() > 0) { Map.Entry<Long,
			 * String> entry = releasesMapObj.entrySet() .iterator().next(); release =
			 * entry.getKey() + ""; } }
			 */
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	@SkipValidation
	public String goManageProjects() {
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
			SummaryResponse<Project> summary = serviceManager.getProjectService().getUserProjects(getPaginationObject(),
					getSearchSortBean(), isAdmin);
			projectGridModel = summary.getEnitities();

			records = summary.getTotalRecords();

			// calculate the total pages for the query
			total = (int) Math.ceil((double) records / (double) rows);
		} catch (Throwable e) {
			e.printStackTrace();
		}

		return SUCCESS;
	}

	@SkipValidation
	public String goDownloadManageProjectNC() {
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
			List<NetworkCodeEffort> summary = null;
			if (projectNew != null && !projectNew.equals("")) {
				Long ncId = null;
				if (releaseNew != null && !releaseNew.equals("")) {
					ncId = Long.parseLong(releaseNew);
				}

				summary = serviceManager.getNetworkCodeReportService().getNetworkCodesEffortOfProject(
						selectedYear.intValue(), Long.parseLong(projectNew), ncId, Long.parseLong(searchSupervisor),
						isAdmin, null, null, null, null);
				if (summary != null && summary.size() > 0) {
					ncGridModel.addAll(summary);
				}
				records = summary.size();
				total = (int) Math.ceil((double) records / (double) rows);

			}
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	@SkipValidation
	public String goManageProjectNC() {
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
			List<NetworkCodeEffort> summary = null;
			if (projectNew != null && !projectNew.equals("")) {
				Long ncId = null;
				if (releaseNew != null && !releaseNew.equals("")) {
					ncId = Long.parseLong(releaseNew);
				}
				summary = serviceManager.getNetworkCodeReportService().getNetworkCodesEffortOfProject(
						selectedYear.intValue(), Long.parseLong(projectNew), ncId, Long.parseLong(searchSupervisor),
						isAdmin, type, reportType, getWeekEndingString(selectedDate),
						getWeekEndingString(selectedToDate));
				if (summary != null && summary.size() > 0) {
					ncGridModel.addAll(summary);
				}

				records = summary.size();
				total = (int) Math.ceil((double) records / (double) rows);

			}
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	@SkipValidation
	public String goManageUserProjectNC() {
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
			List<NetworkCodeEffort> summary = serviceManager.getNetworkCodeReportService().getUserProjectNC(
					selectedYear.intValue(), Long.parseLong(id), Long.parseLong(searchSupervisor), isAdmin, status);
			if (summary != null && summary.size() > 0) {
				userNCGridModel.addAll(summary);
			}
			records = summary.size();
			total = (int) Math.ceil((double) records / (double) rows);

		} catch (Throwable e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	@SkipValidation
	public String manageNCUtilizationDetails() {
		try {
			clearSession("supervisorMap", "userToBackFillMap", "privilegesMap", "rolesMap", "streamsMap",
					"platformsMap", "userTypesMap", "technologiesMap");
			String userId = "";
			String ncId = "";
			if (id != null && id.indexOf("-") != -1) {
				userId = id.split("-")[0];
				ncId = id.split("-")[1];
			}
			List<UserCapacity> summary = serviceManager.getNetworkCodeReportService()
					.getMonthlyNCUsageByUser(selectedYear.intValue(), Long.parseLong(userId), Long.parseLong(ncId));
			if (summary != null && summary.size() > 0) {
				userEffortGridModel.addAll(summary);
			}
			records = summary.size();
			total = (int) Math.ceil((double) records / (double) rows);

		} catch (Throwable e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	@SkipValidation
	public String goGetReleaseTrainDetails() {
		try {
			if (session.get("rowNum") == null) {
				session.put("rowNum", rows);
			} else {
				if (rows != null && (rows.intValue() != ((Integer) session.get("rowNum")).intValue())) {
					session.put("rowNum", rows);
				}
			}
			SearchSortContainer searchSortObj = new SearchSortContainer();
			searchSortObj.setSidx(sidx);
			searchSortObj.setSord(sord);
			SummaryResponse<ReleaseTrainDetails> summary = serviceManager.getPtsReportsService().getReleaseTrainDetails(
					(searchSupervisor == null || (searchSupervisor != null && searchSupervisor.isEmpty())) ? null
							: Long.valueOf(searchSupervisor),
					(pillar == null || (pillar != null && pillar.isEmpty()) || (pillar != null && pillar.contains(",")))
							? null
							: Long.parseLong(pillar),
					(project == null || (project != null && project.isEmpty())
							|| (project != null && project.contains(","))) ? null : Long.parseLong(project),
					(release == null || (release != null && release.isEmpty())
							|| (release != null && release.contains(","))) ? null : Long.parseLong(release),
					(searchProjectManager == null || (searchProjectManager != null && searchProjectManager.isEmpty()))
							? null
							: Long.valueOf(searchProjectManager),
					searchFlag, null, stableTeamId);
			releaseTrainGridModel = summary.getEnitities();

			records = summary.getTotalRecords();
			// calculate the total pages for the query
			total = (int) Math.ceil((double) records / (double) rows);

		} catch (Throwable e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	private LinkedHashMap<String, String> populateUsersColHeadersMap() {
		LinkedHashMap<String, String> headers = new LinkedHashMap<String, String>();
		headers.put("TFS_ID", "TFS Epic Id");
		headers.put("TYPE", "TYPE");
		headers.put("RELEASE", "RELEASE");
		headers.put("PROJECT", "PROJECT");
		headers.put("MANAGER_NAME", "MANAGER NAME");
		headers.put("STATUS", "STATUS");
		headers.put("TOTAL_CAPACITY", "TOTAL CAPACITY");
		headers.put("EFFORT", "EFFORT");
		headers.put("MONTH", "MONTH");
		headers.put("YEAR", "YEAR");
		headers.put("IMPLEMENTATION_DATE", "IMPLEMENTATION DATE");

		return headers;
	}

	public Map<Long, String> getSupervisorMapForManage() {
		/*
		 * try { if (session.get("role") != null && ((String) session.get("role"))
		 * .equalsIgnoreCase("LINE MANAGER")) {
		 * serviceManager.getUserService().getSupervisorMap(supervisorMap, (Long)
		 * session.get("userId"), true); } else if (session.get("role") != null &&
		 * ((String) session.get("role")).equalsIgnoreCase("ADMIN")) {
		 * serviceManager.getUserService().getSupervisorMap(supervisorMap, (Long)
		 * session.get("userId"), false); } if (session.get("role") != null && ((String)
		 * session.get("role")) .equalsIgnoreCase("LINE MANAGER")) {
		 * supervisorMap.put(((Long) session.get("userId")), ((String)
		 * session.get("fullName"))); searchSupervisor = (Long) session.get("userId") +
		 * ""; }
		 * 
		 * } catch (Throwable e) { e.printStackTrace(); }
		 */
		try {
			serviceManager.getUserService().getLineManagersMap(supervisorMap);
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return supervisorMap;
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
		searchSortObj.setLoggedInUserRole((String) session.get("role"));

		searchSortObj.setAllReporteesFlag(true);

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

	public List<Project> getProjectGridModel() {
		return projectGridModel;
	}

	public void setProjectGridModel(List<Project> projectGridModel) {
		this.projectGridModel = projectGridModel;
	}

	public List<NetworkCodeEffort> getNcGridModel() {
		return ncGridModel;
	}

	public void setNcGridModel(List<NetworkCodeEffort> ncGridModel) {
		this.ncGridModel = ncGridModel;
	}

	public List<NetworkCodeEffort> getUserNCGridModel() {
		return userNCGridModel;
	}

	public void setUserNCGridModel(List<NetworkCodeEffort> userNCGridModel) {
		this.userNCGridModel = userNCGridModel;
	}

	public String getSearchUserName() {
		return searchUserName;
	}

	public void setSearchUserName(String searchUserName) {
		this.searchUserName = searchUserName;
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

	public List<UserCapacity> getUserEffortGridModel() {
		return userEffortGridModel;
	}

	public void setUserEffortGridModel(List<UserCapacity> userEffortGridModel) {
		this.userEffortGridModel = userEffortGridModel;
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

	public String getRelease() {
		return release;
	}

	public void setRelease(String release) {
		this.release = release;
	}

	public Map<Long, String> getReleasesMapObj() {
		return releasesMapObj;
	}

	public void setReleasesMapObj(Map<Long, String> releasesMapObj) {
		this.releasesMapObj = releasesMapObj;
	}

	public List<Long> getReportYearList() {
		List<Long> yearList = new ArrayList<Long>();

		Calendar c = Calendar.getInstance();
		int startYear = Calendar.getInstance().getWeekYear();
		int endYear = c.get(Calendar.YEAR);

		for (int i = startYear; i <= endYear; i++) {
			yearList.add(Long.parseLong(i + ""));
		}

		return yearList;
	}

	public List<ReleaseTrainDetails> getReleaseTrainGridModel() {
		return releaseTrainGridModel;
	}

	public void setReleaseTrainGridModel(List<ReleaseTrainDetails> releaseTrainGridModel) {
		this.releaseTrainGridModel = releaseTrainGridModel;
	}

	public Map<Long, String> getProjectManagersMapObj() {
		return projectManagersMapObj;
	}

	public void setProjectManagersMapObj(Map<Long, String> projectManagersMapObj) {
		this.projectManagersMapObj = projectManagersMapObj;
	}

	public String getSearchProjectManager() {
		return searchProjectManager;
	}

	public void setSearchProjectManager(String searchProjectManager) {
		this.searchProjectManager = searchProjectManager;
	}

	public String getSearchFlag() {
		return searchFlag;
	}

	public void setSearchFlag(String searchFlag) {
		this.searchFlag = searchFlag;
	}

	public String getProjectNew() {
		return projectNew;
	}

	public void setProjectNew(String projectNew) {
		this.projectNew = projectNew;
	}

	public String getPillarNew() {
		return pillarNew;
	}

	public void setPillarNew(String pillarNew) {
		this.pillarNew = pillarNew;
	}

	public String getReleaseNew() {
		return releaseNew;
	}

	public void setReleaseNew(String releaseNew) {
		this.releaseNew = releaseNew;
	}

	public List<UserCapacity> getSupervisorNwList() {
		return supervisorNwList;
	}

	public void setSupervisorNwList(List<UserCapacity> summary) {
		this.supervisorNwList = summary;
	}

	public Map<Long, String> getResourceMapObj() {
		return resourceMapObj;
	}

	public void setResourceMapObj(Map<Long, String> resourceMapObj) {
		this.resourceMapObj = resourceMapObj;
	}

	public Long getSearchResource() {
		return searchResource;
	}

	public void setSearchResource(Long searchResource) {
		this.searchResource = searchResource;
	}

	public String getPrevsearchSupervisor() {
		return prevsearchSupervisor;
	}

	public void setPrevsearchSupervisor(String prevsearchSupervisor) {
		this.prevsearchSupervisor = prevsearchSupervisor;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getReportType() {
		return reportType;
	}

	public void setReportType(String reportType) {
		this.reportType = reportType;
	}

	public Integer getMonthSelected() {
		return monthSelected;
	}

	public void setMonthSelected(Integer monthSelected) {
		this.monthSelected = monthSelected;
	}

	public Map<Integer, String> getMonthMap() {
		return monthMap;
	}

	public void setMonthMap(Map<Integer, String> monthMap) {
		this.monthMap = monthMap;
	}

	public void setRowid(String rowid) {
		this.rowid = rowid;
	}

	public String getRowid() {
		return rowid;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	public long getContentLength() {
		return contentLength;
	}

	public void setContentLength(long contentLength) {
		this.contentLength = contentLength;
	}

	public Map<String, String> getNetworkStatusMap() {
		return networkStatusMap;
	}

	public void setNetworkStatusMap(Map<String, String> networkStatusMap) {
		this.networkStatusMap = networkStatusMap;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Map<Long, String> getStableTeamsMapobj() {
		return stableTeamsMapobj;
	}

	public void setStableTeamsMapobj(Map<Long, String> stableTeamsMapobj) {
		this.stableTeamsMapobj = stableTeamsMapobj;
	}

	public long getStableTeamId() {
		return stableTeamId;
	}

	public void setStableTeamId(long stableTeamId) {
		this.stableTeamId = stableTeamId;
	}

}
