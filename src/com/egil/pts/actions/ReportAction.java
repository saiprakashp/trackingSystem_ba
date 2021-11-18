package com.egil.pts.actions;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.servlet.ServletOutputStream;

import org.apache.commons.io.FileUtils;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.struts2.interceptor.validation.SkipValidation;
import org.hibernate.internal.util.StringHelper;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.egil.pts.constants.ExcelHeaderConstants;
import com.egil.pts.modal.Pagination;
import com.egil.pts.modal.ResourceEffort;
import com.egil.pts.modal.SearchSortContainer;
import com.egil.pts.modal.SummaryResponse;
import com.egil.pts.modal.TechCompetencyScore;
import com.egil.pts.modal.User;
import com.egil.pts.modal.UserReport;
import com.egil.pts.util.GenericExcel;

@Controller("reportAction")
@Scope("prototype")
public class ReportAction extends PTSAction {

	private static final long serialVersionUID = 1L;
	List<ResourceEffort> activityList = new ArrayList<ResourceEffort>();
	private String selectedEmployee;
	private String weekendingDate;
	private Long networkCodeId;

	private String capacityStream;
	private String capacityStreamImage;
	private File photoUpload; // The actual file
	private String photoUploadContentType; // The content type of the file
	private String photoUploadFileName; // The uploaded file name and path

	private String ricoLocation;

	List<UserReport> userReportList = new ArrayList<UserReport>();
	Map<String, Map<String, UserReport>> contributionReportMap = new LinkedHashMap<String, Map<String, UserReport>>();
	Map<String, Float> platformUserContributionCnt = new LinkedHashMap<String, Float>();
	Map<String, Map<String, Float>> streamHCCnt = new LinkedHashMap<String, Map<String, Float>>();
	private Map<Long, String> technologiesMap = new LinkedHashMap<Long, String>();
	private Map<Long, String> projectsMap = new LinkedHashMap<Long, String>();
	private Map<Long, String> platformsMap = new LinkedHashMap<Long, String>();
	private Map<Long, String> supervisorMap = new LinkedHashMap<Long, String>();
	private Map<Long, String> locationsMap = new LinkedHashMap<Long, String>();
	private Map<Long, String> streamsMap = new LinkedHashMap<Long, String>();
	private Map<String, Integer> streamsAppLinkMap = new LinkedHashMap<String, Integer>();

	private List<TechCompetencyScore> gridModel = new ArrayList<TechCompetencyScore>();

	private List<User> contributiongridModel = new ArrayList<User>();
	private Long skillId;

	private Long projectId;
	private Long pillarId;

	private String primaryFlag;
	private String halfYear;

	private String searchSupervisor;
	private boolean allReporteesFlag;

	private Long searchLocation;

	private Long searchStream;

	private boolean advancedSearch = false;

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
	private Map<String, Float> platformCountMap = new LinkedHashMap<String, Float>();
	private Map<String, float[]> streamCountMap = new LinkedHashMap<String, float[]>();
	private Map<String, Float> platformCountMapApp = new LinkedHashMap<String, Float>();
	private Map<String, float[]> streamCountMapApp = new LinkedHashMap<String, float[]>();
	private Map<String, Map<String, UserReport>> contributionReportMapApp = new LinkedHashMap<String, Map<String, UserReport>>();
	private Map<String, Float> platformUserContributionCntApp = new LinkedHashMap<String, Float>();
	private Map<String, Integer> streamsAppLinkMapApp = new LinkedHashMap<String, Integer>();
	private Map<String, Map<String, Float>> streamHCCntApp = new LinkedHashMap<String, Map<String, Float>>();

	public void generateNetworkCodeEffortChart() {
		try {
			byte[] imgBytes = serviceManager.getPtsReportsService().getNetworkCodeReport(networkCodeId);
			response.setContentType("image/png");
			OutputStream responseOutput = response.getOutputStream();
			responseOutput.write(imgBytes);
			responseOutput.flush();
			responseOutput.close();
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

	public void generateNetworkCodesEffortStatisticsChart() {
		try {
			boolean isAdmin = false;
			if (session.get("role") != null && StringHelper.isNotEmpty((String) session.get("role"))
					&& ((String) session.get("role")).equalsIgnoreCase("ADMIN")) {
				isAdmin = true;
			}

			byte[] imgBytes = serviceManager.getPtsReportsService().getNetworkCodesStatisticReport(
					Long.valueOf((session.get("userId") == null || session.get("userId").toString().equals("")) ? "0"
							: session.get("userId").toString()),
					isAdmin);
			if (imgBytes != null) {
				response.setContentType("image/png");
				OutputStream responseOutput = response.getOutputStream();
				responseOutput.write(imgBytes);
				responseOutput.flush();
				responseOutput.close();
			}
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

	public String generateExcelReport() {
		try {
			HashMap<String, String> excelColHeaders = new LinkedHashMap<String, String>();

			setColumnHeaders(excelColHeaders);

			String[] colHeaders = new String[excelColHeaders.size()];
			String[] colHeaderKeys = new String[excelColHeaders.size()];
			int colHeaderSize = excelColHeaders.size();
			int colCount = 0;
			int networkIdColumn = -1, networkIdDescColumn = -1, activityCodeColumn = -1, activityDescColumn = -1;
			Iterator<String> colIterator = excelColHeaders.keySet().iterator();

			while (colIterator.hasNext()) {
				colHeaderKeys[colCount] = colIterator.next();
				colHeaders[colCount] = excelColHeaders.get(colHeaderKeys[colCount]);
				colCount++;
			}
			SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
			String timeStamp = df.format(new Date());
			String filePath = getText("rico.trainings.directry.structure");
			File dir = new File(filePath);
			if (!dir.exists()) {
				@SuppressWarnings("unused")
				boolean result = dir.mkdirs();
			}
			String filename = filePath + getText("rico.user.productive.hours.file.name") + timeStamp + ".xlsx";// "/applications/AH_REPORTS/Productivehrs.xls";

			GenericExcel excel = new GenericExcel(filename);
			String[][] activityRecords = new String[activityList.size() + 2][colHeaderSize];

			activityRecords[0] = colHeaders;

			for (int row = 1; row <= activityList.size(); row++) {
				@SuppressWarnings("unused")
				ResourceEffort externalActivity = activityList.get(row - 1);
				for (int col = 0; col < colHeaders.length; col++) {
					switch (colHeaderKeys[col]) {
					case ExcelHeaderConstants.RESOURCE_NAME:
						activityRecords[row][col] = session.get("fullName") + "";// activity.getResourceName();
						break;

					/*
					 * case ExcelHeaderConstants.NETWORK_ID: activityRecords[row][col] =
					 * externalActivity.getNetworkId(); networkIdColumn = col; break;
					 */
					case ExcelHeaderConstants.NETWORK_ID:
						// activityRecords[row][col] =
						// getNetworkIdMap().get(externalActivity.getWeekEffortList().getNetworkId());//activity.getNetworkIdDesc();
						networkIdDescColumn = col;
						break;
					/*
					 * case ExcelHeaderConstants.ACTIVITY_CODE: activityRecords[row][col] =
					 * externalActivity.getActivityCode(); activityCodeColumn = col; break;
					 */
					case ExcelHeaderConstants.ACTIVITY_DESC:
						// activityRecords[row][col] =
						// getActivityCodeMap().get(externalActivity.getActivityCode());
						// //activity.getActivityDesc();
						activityDescColumn = col;
						break;
					case ExcelHeaderConstants.WEEKENDING:
						activityRecords[row][col] = weekendingDate;// activity.getWeekending();
						break;
					case ExcelHeaderConstants.HOURS:
						// activityRecords[row][col] =
						// externalActivity.getEffort() + "";
						break;
					}
				}
			}

			excel.writeSheet("Prod hrs", activityRecords);
			excel.customizeRowStyle(0, 0, 0, HSSFColor.YELLOW.index);
			// excel.setRowFont(0, 0, 1);
			if (networkIdColumn > -1) {
				String[] networkIds = new String[goGetNetworkIdMap().size()];
				int i = 0;
				for (String networkId : goGetNetworkIdMap().keySet()) {
					networkIds[i++] = networkId;
				}
				excel.createLargeDropDownListtoWorkBook(0, 1, 2, networkIdColumn, networkIds, 1);
			}
			if (networkIdDescColumn > -1) {
				String[] networkIdDescs = new String[goGetNetworkIdMap().size()];
				int i = 0;
				for (String networkId : goGetNetworkIdMap().keySet()) {
					networkIdDescs[i++] = goGetNetworkIdMap().get(networkId);
				}
				excel.createLargeDropDownListtoWorkBook(0, 1, 3, networkIdDescColumn, networkIdDescs, 1);
			}

			if (activityCodeColumn > -1) {
				String[] activityCodes = new String[getActivityCodeMap().size()];
				int i = 0;
				for (String activityCode : getActivityCodeMap().keySet()) {
					activityCodes[i++] = activityCode;
				}
				excel.createLargeDropDownListtoWorkBook(0, 1, 5, activityCodeColumn, activityCodes, 1);
			}

			if (activityDescColumn > -1) {
				String[] activityCodes = new String[getActivityCodeMap().size()];
				int i = 0;
				for (String activityCode : getActivityCodeMap().keySet()) {
					activityCodes[i++] = getActivityCodeMap().get(activityCode);
				}
				excel.createLargeDropDownListtoWorkBook(0, 1, 6, activityDescColumn, activityCodes, 1);
			}

			boolean status = excel.writeWorkbook();
			if (!status) {
				System.out.println("Error In Generating Excel");
			}
			File downloadFile = new File(filePath);
			// String filename = downloadFile.getName();
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-disposition", "attachment;filename=" + filename);
			ServletOutputStream os = response.getOutputStream();

			os.write(FileUtils.readFileToByteArray(downloadFile));
			os.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@SkipValidation
	public String uploadCapacityImage() {
		try {
			if (photoUpload != null) {
				String filePath = "/applications/tomcat/capacity/"; // request.getSession().getServletContext().getRealPath("/");
				capacityStreamImage = capacityStream + ".png";
				File fileToCreate = new File(filePath, capacityStreamImage);

				FileUtils.copyFile(photoUpload, fileToCreate);
				addActionMessage("Upload Successful...");
			} else {
				capacityStream = "Dev-Capacity";
				capacityStreamImage = "Dev-Capacity.png";
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	@SkipValidation
	public String getCapacityImage() {

		String filePath = "/applications/tomcat/capacity/";// request.getSession().getServletContext().getRealPath("/");
		// filePath = filePath + "/usersImage/";
		boolean check = new File(new File(filePath), capacityStream + ".png").exists();
		if (check) {
			capacityStreamImage = capacityStream + ".png";
		} else {
			capacityStreamImage = "";
			addActionError("No Capacity image found...");
		}
		return SUCCESS;
	}

	@SuppressWarnings("unchecked")
	public HashMap<String, String> getActivityCodeMap() {
		return (LinkedHashMap<String, String>) servletContext.getAttribute("activityCodesMap");
	}

	@SuppressWarnings("unchecked")
	public HashMap<String, String> goGetNetworkIdMap() {
		HashMap<String, String> assignedNetworkCodes = new LinkedHashMap<String, String>();
		try {
			if (((LinkedHashMap<String, String>) servletContext.getAttribute("assignedNetworkIds"))
					.containsKey(session.get("username"))) {
				String commaSeparatedString = ((LinkedHashMap<String, String>) servletContext
						.getAttribute("assignedNetworkIds")).get(session.get("username"));
				StringTokenizer st = null;

				if (commaSeparatedString == null) {
					return null;
				}

				st = new StringTokenizer(commaSeparatedString, ",");

				while (st.hasMoreTokens()) {
					String token = st.nextToken().trim();
					assignedNetworkCodes.put(token,
							((LinkedHashMap<String, String>) servletContext.getAttribute("networkIdMap")).get(token));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return assignedNetworkCodes;
	}

	private void setColumnHeaders(HashMap<String, String> excelColHeaders) {
		excelColHeaders.put(ExcelHeaderConstants.RESOURCE_NAME, ExcelHeaderConstants.RESOURCE_NAME);
		excelColHeaders.put(ExcelHeaderConstants.NETWORK_ID, ExcelHeaderConstants.NETWORK_ID);
		excelColHeaders.put(ExcelHeaderConstants.NETWORK_ID, ExcelHeaderConstants.NETWORK_ID);
		excelColHeaders.put(ExcelHeaderConstants.ACTIVITY_CODE, ExcelHeaderConstants.ACTIVITY_CODE);
		excelColHeaders.put(ExcelHeaderConstants.ACTIVITY_DESC, ExcelHeaderConstants.ACTIVITY_DESC);
		excelColHeaders.put(ExcelHeaderConstants.WEEKENDING, ExcelHeaderConstants.WEEKENDING);
		excelColHeaders.put(ExcelHeaderConstants.HOURS, ExcelHeaderConstants.HOURS);
	}

	@SkipValidation
	public String getUserContributionReportApp() {
		try {
			if (ricoLocation == null || (ricoLocation != null && ricoLocation.equals(""))) {
				ricoLocation = "ALL";
			}
			serviceManager.getStreamsService().getStreamsMap(streamsMap);
			serviceManager.getPillarService().getPillarsMap(platformsMap, null);
			serviceManager.getLocationService().getLocationsMap(locationsMap);
			clearCachedMapData();
			serviceManager.getPtsReportsService().getUserContributionReport(ricoLocation, userReportList,
					contributionReportMapApp, platformUserContributionCntApp, streamHCCntApp, streamsAppLinkMapApp,
					streamCountMapApp, platformCountMapApp, searchLocation, pillarId, searchStream,
					(searchSupervisor == null || (searchSupervisor != null && searchSupervisor.isEmpty())) ? null
							: Long.valueOf(searchSupervisor),
					true);
			serviceManager.getPtsReportsService().getUserContributionReport(ricoLocation, userReportList,
					contributionReportMap, platformUserContributionCnt, streamHCCnt, streamsAppLinkMap, streamCountMap,
					platformCountMap, searchLocation, pillarId, searchStream,
					(searchSupervisor == null || (searchSupervisor != null && searchSupervisor.isEmpty())) ? null
							: Long.valueOf(searchSupervisor),
					false);

		} catch (Throwable e) {
			e.printStackTrace();
		}
		return SUCCESS;

	}

	@SkipValidation
	public String getUserContributionReport() {
		try {
			if (ricoLocation == null || (ricoLocation != null && ricoLocation.equals(""))) {
				ricoLocation = "ALL";
			}
			serviceManager.getStreamsService().getStreamsMap(streamsMap);
			serviceManager.getPillarService().getPillarsMap(platformsMap, null);
			serviceManager.getLocationService().getLocationsMap(locationsMap);

			clearCachedMapData();

			serviceManager.getPtsReportsService().getUserContributionReport(ricoLocation, userReportList,
					contributionReportMapApp, platformUserContributionCntApp, streamHCCntApp, streamsAppLinkMapApp,
					streamCountMapApp, platformCountMapApp, searchLocation, pillarId, searchStream,
					(searchSupervisor == null || (searchSupervisor != null && searchSupervisor.isEmpty())) ? null
							: Long.valueOf(searchSupervisor),
					true);
			serviceManager.getPtsReportsService().getUserContributionReport(ricoLocation, userReportList,
					contributionReportMap, platformUserContributionCnt, streamHCCnt, streamsAppLinkMap, streamCountMap,
					platformCountMap, searchLocation, pillarId, searchStream,
					(searchSupervisor == null || (searchSupervisor != null && searchSupervisor.isEmpty())) ? null
							: Long.valueOf(searchSupervisor),
					false);

		} catch (Throwable e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	private void clearCachedMapData() {
		userReportList.clear();
		contributionReportMapApp.clear();
		platformUserContributionCntApp.clear();
		streamHCCntApp.clear();
		streamsAppLinkMapApp.clear();
		streamCountMapApp.clear();
		platformCountMapApp.clear();

		contributionReportMap.clear();
		platformUserContributionCnt.clear();
		streamHCCnt.clear();
		streamsAppLinkMap.clear();
		streamCountMap.clear();
		platformCountMap.clear();

	}

	@SkipValidation
	public String techScoreReport() {
		try {
			clearSession("projectsMap", "technologiesMap");
			serviceManager.getTechnologiesService().getTechnologiesMap(technologiesMap);
			serviceManager.getProjectService().getProjectsMap(projectsMap, null);
			session.put("technologiesMap", technologiesMap);
			session.put("projectsMap", projectsMap);
			if (StringHelper.isEmpty(halfYear)) {
				halfYear = (Calendar.getInstance().get(Calendar.MONTH) > 5) ? "2H" : "1H";
			}
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	@SkipValidation
	public String goGetTechScoreReportDetails() {
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
			SummaryResponse<TechCompetencyScore> summary = serviceManager.getPtsReportsService()
					.getTechScoreRecordDetails(ricoLocation, skillId, primaryFlag, projectId, getPaginationObject(),
							searchSortObj, selectedYear, halfYear);
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
	public String compScoreReport() {
		try {
			clearSession("platformsMap", "projectsMap", "technologiesMap");
			serviceManager.getProjectService().getProjectsMap(projectsMap, null);
			serviceManager.getPillarService().getPillarsMap(platformsMap, null);
			session.put("platformsMap", platformsMap);
			session.put("projectsMap", projectsMap);
			if (StringHelper.isEmpty(halfYear)) {
				halfYear = (Calendar.getInstance().get(Calendar.MONTH) > 5) ? "2H" : "1H";
			}
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	@SkipValidation
	public String goGetCompScoreReportDetails() {
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
			SummaryResponse<TechCompetencyScore> summary = serviceManager.getPtsReportsService()
					.getCompScoreRecordDetails(ricoLocation, pillarId, projectId, primaryFlag, selectedYear, halfYear,
							getPaginationObject(), searchSortObj);
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
			if (ricoLocation == null || (ricoLocation != null && ricoLocation.equals(""))) {
				ricoLocation = "ALL";
			}
			serviceManager.getStreamsService().getStreamsMap(streamsMap);
			serviceManager.getPillarService().getPillarsMap(platformsMap, null);
			serviceManager.getLocationService().getLocationsMap(locationsMap);
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	public String goGetContributionDetailReport() {

		try {
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
			if ((searchSupervisor == null || (searchSupervisor != null && searchSupervisor.isEmpty()))) {
				searchSupervisor = (Long) session.get("userId") + "";
			}
			SummaryResponse<User> summary = serviceManager.getPtsReportsService().getContributionDetailReport(
					(searchSupervisor == null || (searchSupervisor != null && searchSupervisor.isEmpty())) ? null
							: Long.valueOf(searchSupervisor),
					getPaginationObject(), searchLocation, pillarId, searchStream, ricoLocation);

			if (summary != null && summary.getEnitities() != null && summary.getEnitities().size() > 0) {
				contributiongridModel.addAll(summary.getEnitities());
			}

			records = summary.getTotalRecords();

			// calculate the total pages for the query
			total = (int) Math.ceil((double) records / (double) rows);
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	public Map<Long, String> getSupervisorMapForManage() {
		try {
			serviceManager.getUserService().getLineManagersMap(supervisorMap);
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return supervisorMap;
	}

	public List<ResourceEffort> getActivityList() {
		return activityList;
	}

	public void setActivityList(List<ResourceEffort> activityList) {
		this.activityList = activityList;
	}

	public String getSelectedEmployee() {
		return selectedEmployee;
	}

	public void setSelectedEmployee(String selectedEmployee) {
		this.selectedEmployee = selectedEmployee;
	}

	public String getWeekendingDate() {
		return weekendingDate;
	}

	public void setWeekendingDate(String weekendingDate) {
		this.weekendingDate = weekendingDate;
	}

	public Long getNetworkCodeId() {
		return networkCodeId;
	}

	public void setNetworkCodeId(Long networkCodeId) {
		this.networkCodeId = networkCodeId;
	}

	public File getPhotoUpload() {
		return photoUpload;
	}

	public void setPhotoUpload(File photoUpload) {
		this.photoUpload = photoUpload;
	}

	public String getPhotoUploadContentType() {
		return photoUploadContentType;
	}

	public void setPhotoUploadContentType(String photoUploadContentType) {
		this.photoUploadContentType = photoUploadContentType;
	}

	public String getPhotoUploadFileName() {
		return photoUploadFileName;
	}

	public void setPhotoUploadFileName(String photoUploadFileName) {
		this.photoUploadFileName = photoUploadFileName;
	}

	public String getCapacityStream() {
		return capacityStream;
	}

	public void setCapacityStream(String capacityStream) {
		this.capacityStream = capacityStream;
	}

	public String getCapacityStreamImage() {
		return capacityStreamImage;
	}

	public void setCapacityStreamImage(String capacityStreamImage) {
		this.capacityStreamImage = capacityStreamImage;
	}

	public Map<String, String> getCapacityStreamMap() {
		Map<String, String> capacityStreamMap = new HashMap<String, String>();
		capacityStreamMap.put("Dev-Capacity", "Dev");
		capacityStreamMap.put("PM-Capacity", "PM");
		capacityStreamMap.put("SE-Capacity", "SE");
		capacityStreamMap.put("Test-Capacity", "Test");
		return capacityStreamMap;
	}

	public String getRicoLocation() {
		return ricoLocation;
	}

	public void setRicoLocation(String ricoLocation) {
		this.ricoLocation = ricoLocation;
	}

	public List<UserReport> getUserReportList() {
		return userReportList;
	}

	public void setUserReportList(List<UserReport> userReportList) {
		this.userReportList = userReportList;
	}

	public Map<String, Map<String, UserReport>> getContributionReportMap() {
		return contributionReportMap;
	}

	public void setContributionReportMap(Map<String, Map<String, UserReport>> contributionReportMap) {
		this.contributionReportMap = contributionReportMap;
	}

	public Map<String, Float> getPlatformUserContributionCnt() {
		return platformUserContributionCnt;
	}

	public void setPlatformUserContributionCnt(Map<String, Float> platformUserContributionCnt) {
		this.platformUserContributionCnt = platformUserContributionCnt;
	}

	public Map<String, Map<String, Float>> getStreamHCCnt() {
		return streamHCCnt;
	}

	public void setStreamHCCnt(Map<String, Map<String, Float>> streamHCCnt) {
		this.streamHCCnt = streamHCCnt;
	}

	protected Pagination getPaginationObject() {
		Pagination pagination = new Pagination();
		int to = (rows * page);
		int from = to - rows;
		pagination.setOffset(from);
		pagination.setSize(to);
		return pagination;
	}

	public Map<Long, String> getTechnologiesMap() {
		return technologiesMap;
	}

	public void setTechnologiesMap(Map<Long, String> technologiesMap) {
		this.technologiesMap = technologiesMap;
	}

	public Map<Long, String> getProjectsMap() {
		return projectsMap;
	}

	public void setProjectsMap(Map<Long, String> projectsMap) {
		this.projectsMap = projectsMap;
	}

	public List<TechCompetencyScore> getGridModel() {
		return gridModel;
	}

	public void setGridModel(List<TechCompetencyScore> gridModel) {
		this.gridModel = gridModel;
	}

	public Long getSkillId() {
		return skillId;
	}

	public void setSkillId(Long skillId) {
		this.skillId = skillId;
	}

	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

	public String getPrimaryFlag() {
		return primaryFlag;
	}

	public void setPrimaryFlag(String primaryFlag) {
		this.primaryFlag = primaryFlag;
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

	public Map<Long, String> getPlatformsMap() {
		return platformsMap;
	}

	public void setPlatformsMap(Map<Long, String> platformsMap) {
		this.platformsMap = platformsMap;
	}

	public Long getPillarId() {
		return pillarId;
	}

	public void setPillarId(Long pillarId) {
		this.pillarId = pillarId;
	}

	public String getHalfYear() {
		return halfYear;
	}

	public void setHalfYear(String halfYear) {
		this.halfYear = halfYear;
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

	public Map<Long, String> getSupervisorMap() {
		return supervisorMap;
	}

	public void setSupervisorMap(Map<Long, String> supervisorMap) {
		this.supervisorMap = supervisorMap;
	}

	public List<User> getContributiongridModel() {
		return contributiongridModel;
	}

	public void setContributiongridModel(List<User> contributiongridModel) {
		this.contributiongridModel = contributiongridModel;
	}

	public Map<Long, String> getLocationsMap() {
		return locationsMap;
	}

	public void setLocationsMap(Map<Long, String> locationsMap) {
		this.locationsMap = locationsMap;
	}

	public Map<Long, String> getStreamsMap() {
		return streamsMap;
	}

	public void setStreamsMap(Map<Long, String> streamsMap) {
		this.streamsMap = streamsMap;
	}

	public Long getSearchLocation() {
		return searchLocation;
	}

	public void setSearchLocation(Long searchLocation) {
		this.searchLocation = searchLocation;
	}

	public Long getSearchStream() {
		return searchStream;
	}

	public void setSearchStream(Long searchStream) {
		this.searchStream = searchStream;
	}

	public boolean isAdvancedSearch() {
		return advancedSearch;
	}

	public void setAdvancedSearch(boolean advancedSearch) {
		this.advancedSearch = advancedSearch;
	}

	public Map<String, Integer> getStreamsAppLinkMap() {
		return streamsAppLinkMap;
	}

	public void setStreamsAppLinkMap(Map<String, Integer> streamsAppLinkMap) {
		this.streamsAppLinkMap = streamsAppLinkMap;
	}

	public Map<String, Float> getPlatformCountMap() {
		return platformCountMap;
	}

	public void setPlatformCountMap(Map<String, Float> platformCountMap) {
		this.platformCountMap = platformCountMap;
	}

	public Map<String, float[]> getStreamCountMap() {
		return streamCountMap;
	}

	public void setStreamCountMap(Map<String, float[]> streamCountMap) {
		this.streamCountMap = streamCountMap;
	}

	public Map<String, Map<String, UserReport>> getContributionReportMapApp() {
		return contributionReportMapApp;
	}

	public void setContributionReportMapApp(Map<String, Map<String, UserReport>> contributionReportMapApp) {
		this.contributionReportMapApp = contributionReportMapApp;
	}

	public Map<String, Float> getPlatformUserContributionCntApp() {
		return platformUserContributionCntApp;
	}

	public void setPlatformUserContributionCntApp(Map<String, Float> platformUserContributionCntApp) {
		this.platformUserContributionCntApp = platformUserContributionCntApp;
	}

	public Map<String, Float> getPlatformCountMapApp() {
		return platformCountMapApp;
	}

	public void setPlatformCountMapApp(Map<String, Float> platformCountMapApp) {
		this.platformCountMapApp = platformCountMapApp;
	}

	public Map<String, float[]> getStreamCountMapApp() {
		return streamCountMapApp;
	}

	public void setStreamCountMapApp(Map<String, float[]> streamCountMapApp) {
		this.streamCountMapApp = streamCountMapApp;
	}

	public Map<String, Integer> getStreamsAppLinkMapApp() {
		return streamsAppLinkMapApp;
	}

	public void setStreamsAppLinkMapApp(Map<String, Integer> streamsAppLinkMapApp) {
		this.streamsAppLinkMapApp = streamsAppLinkMapApp;
	}

	public Map<String, Map<String, Float>> getStreamHCCntApp() {
		return streamHCCntApp;
	}

	public void setStreamHCCntApp(Map<String, Map<String, Float>> streamHCCntApp) {
		this.streamHCCntApp = streamHCCntApp;
	}

}
