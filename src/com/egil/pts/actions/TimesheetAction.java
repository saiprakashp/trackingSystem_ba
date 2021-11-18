package com.egil.pts.actions;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.interceptor.validation.SkipValidation;
import org.hibernate.internal.util.StringHelper;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.egil.pts.modal.ActivityCodesNew;
import com.egil.pts.modal.CapacityPlanning;
import com.egil.pts.modal.EssDetails;
import com.egil.pts.modal.NetworkCodes;
import com.egil.pts.modal.Pagination;
import com.egil.pts.modal.Project;
import com.egil.pts.modal.SearchSortContainer;
import com.egil.pts.modal.TimeSheetRecord;
import com.egil.pts.modal.TimeSheetType;
import com.egil.pts.modal.TimesheetActivity;
import com.egil.pts.modal.TimesheetActivityWfm;
import com.egil.pts.modal.UserStableTeams;
import com.egil.pts.util.Utility;

@Controller("timesheetAction")
@Scope("prototype")
public class TimesheetAction extends PTSAction {

	private static final long serialVersionUID = 1L;
	private Map<Long, String> employeeList = new LinkedHashMap<Long, String>();
	private Map<Long, String> userStreamList = new LinkedHashMap<Long, String>();
	private Long selectedEmployee;
	private Long selectedStream;
	private String additionalProjects;
	private String removeAdditional;
	private List<TimesheetActivity> activityList = new ArrayList<TimesheetActivity>();
	private List<TimesheetActivity> activityListProjects = new ArrayList<TimesheetActivity>();
	private List<TimesheetActivity> activityListToSave = new ArrayList<TimesheetActivity>();
	private List<TimesheetActivity> activityListProductsToSave = new ArrayList<TimesheetActivity>();
	private List<TimesheetActivityWfm> activityListToSaveWfm = new ArrayList<TimesheetActivityWfm>();
	private TimeSheetRecord timeSheetRecord;
	private TimeSheetRecord timeSheetRecord1;
	private List<ActivityCodesNew> activityNew = new ArrayList<ActivityCodesNew>();
	private boolean getUserProjectMap;
	private Map<Long, String> selectedNetworkCodesMap = new LinkedHashMap<Long, String>();
	private Map<Long, String> selectedNetworkCodesMapDeleted = new LinkedHashMap<Long, String>();
	private Map<Long, String> activityCodesMap = new LinkedHashMap<Long, String>();
	private Map<Long, String> activityCodesPtlMap = new LinkedHashMap<Long, String>();
	private Map<Long, String> activityCodesProjectMap = new LinkedHashMap<Long, String>();
	private Map<Long, String> activityCodesManagementMap = new LinkedHashMap<Long, String>();
	private Map<Long, Long> userCapacityByRelease = new LinkedHashMap<Long, Long>();
	private Map<Long, String> projectAssignement = new LinkedHashMap<Long, String>();

	private List<Integer> totalMerger = new ArrayList<>();
	private List<Long> selectedEmployees = new ArrayList<Long>();
	private String showAllWfm;
	private String showAllSubRes;
	private int activityListCount = 0;
	private String removedIds;
	private String removedIdsactivity;
	private String removedTemplateIds;
	// for new table in timesheet start
	private Map<Long, String> activityMapNew = new LinkedHashMap<Long, String>();
	// for new table in timesheet end

	private float totMonSum = 0.0f, totTueSum = 0.0f, totWedSum = 0.0f, totThuSum = 0.0f, totFriSum = 0.0f,
			totSatSum = 0.0f, totSunSum = 0.0f, totSummation = 0.0f;

	private String remainingHrs = "0.0";
	private Long selectedNetworkCodeId;
	private String timesheetType;

	private Long tempActivityCode;
	private String tempType;

	protected String approvalType;

	private boolean weekOffFlag = false;

	private File uploadFile;

	private String uploadFileContentType;

	private String uploadFileFileName;

	private List<CapacityPlanning> plannedCapacityList;

	private Date minStartDate;

	private boolean showAllPen;

	private String filename;

	private InputStream inputStream;

	private long contentLength;

	private String wfmDownloaderOk;
	private String totalSummation;

	private List<String> monthlist;
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

	private List<EssDetails> essFeedModal;

	private String vacationType;

	private boolean projectSupportEnabled;

	private boolean feasibilityEnabled;

	private boolean ptlEnabled;

	private boolean securityEnabled;

	@Override
	@SkipValidation
	public String execute() {
		try {
			clearSession("supervisorMap", "userToBackFillMap", "privilegesMap", "rolesMap", "streamsMap",
					"platformsMap", "userTypesMap", "technologiesMap");
			selectedEmployee = (Long) session.get("userId");
			Calendar calendarStart = Calendar.getInstance();
			calendarStart.set(Calendar.YEAR, 2020);
			calendarStart.set(Calendar.MONTH, 0);
			calendarStart.set(Calendar.DAY_OF_MONTH, 1);
			minStartDate = calendarStart.getTime();
			selectedDate = (selectedDate != null && !selectedDate.isEmpty()) ? selectedDate : "";
			serviceManager.getUserService().getAllSupervisorList(employeeList, userStreamList,
					(Long) session.get("userId"));
			clearErrorsAndMessages();
			goUserActivity();

		} catch (Throwable e) {
			e.printStackTrace();
		}

		return SUCCESS;
	}

	@SkipValidation
	public String showEssData() {
		try {
			selectedMonth = (selectedMonth != null) ? selectedMonth : Utility.monthsMap.get(new Date().getMonth() + 1);
		} catch (Throwable e) {
			e.printStackTrace();
		}

		return SUCCESS;
	}

	@SkipValidation
	public String getEssData() {
		try {
			essFeedModal = serviceManager.getUserService().getEssFeed(getSelectedMonth(), getPaginationObject());
			records = essFeedModal.size();
		} catch (Throwable e) {
			e.printStackTrace();
		}

		return SUCCESS;
	}

	protected Pagination getPaginationObject() {
		Pagination pagination = new Pagination();
		int to = (rows * page);
		int from = to - rows;
		pagination.setOffset(from);
		pagination.setSize(to);
		return pagination;
	}

	@SkipValidation
	public String getInitlogTimesheetWFM() {
		try {
			setWfmDownloaderOk("FALSE");
			clearSession("supervisorMap", "userToBackFillMap", "privilegesMap", "rolesMap", "streamsMap",
					"platformsMap", "userTypesMap", "technologiesMap");
			selectedEmployee = (Long) session.get("userId");
			Calendar calendarStart = Calendar.getInstance();
			calendarStart.set(Calendar.YEAR, 2020);
			calendarStart.set(Calendar.MONTH, 0);
			calendarStart.set(Calendar.DAY_OF_MONTH, 1);
			activityListToSave = new ArrayList<TimesheetActivity>();
			DateFormat format = new SimpleDateFormat("MM/dd/yyyy");
			if (activityListToSaveWfm != null)
				activityListToSaveWfm.clear();
			activityListToSaveWfm = serviceManager.getUserEffortService().getTimeSheetWFM(selectedEmployee,
					getWeekEnding((selectedDate != null && !selectedDate.isEmpty()) ? selectedDate
							: format.format(new Date())));
			minStartDate = calendarStart.getTime();
			selectedDate = (selectedDate != null && !selectedDate.isEmpty()) ? selectedDate : "";
			serviceManager.getUserService().getAllSupervisorList(employeeList, userStreamList,
					(Long) session.get("userId"));
			clearErrorsAndMessages();

		} catch (Throwable e) {
			e.printStackTrace();
		}

		return SUCCESS;
	}

	@SkipValidation
	public String showTimesheetWFM() {
		try {
			if (((String) session.get("role")).equalsIgnoreCase("ADMIN")) {
				setShowAllSubRes("ALL");
			}
			clearSession("supervisorMap", "userToBackFillMap", "privilegesMap", "rolesMap", "streamsMap",
					"platformsMap", "userTypesMap", "technologiesMap");
			selectedEmployee = (selectedEmployee != null && selectedEmployee != -1) ? selectedEmployee
					: (Long) session.get("userId");
			Calendar calendarStart = Calendar.getInstance();
			calendarStart.set(Calendar.YEAR, 2020);
			calendarStart.set(Calendar.MONTH, 0);
			calendarStart.set(Calendar.DAY_OF_MONTH, 1);
			activityListToSaveWfm = new ArrayList<TimesheetActivityWfm>();
			DateFormat format = new SimpleDateFormat("MM/dd/yyyy");
			minStartDate = calendarStart.getTime();
			if (showAllWfm != null && !showAllWfm.isEmpty() && showAllWfm.equalsIgnoreCase("true")) {
				selectedEmployee = (Long) session.get("userId");
			}
			selectedDate = (selectedDate != null && !selectedDate.isEmpty()) ? selectedDate : "";
			activityListToSaveWfm = serviceManager.getUserEffortService().getTimeSheetWFM(selectedEmployee,
					getWeekEnding((selectedDate != null && !selectedDate.isEmpty()) ? selectedDate
							: format.format(new Date())),
					showAllWfm, showAllSubRes, getSelectedStream());
			serviceManager.getUserService().getAllSupervisorList(employeeList, userStreamList,
					(Long) session.get("userId"));
			clearErrorsAndMessages();
			setWfmDownloaderOk("FALSE");
			totalSummation = getWFMWeekTotal();
		} catch (Throwable e) {
			e.printStackTrace();
		}

		return SUCCESS;
	}

	public String getWFMWeekTotal() {
		DecimalFormat df = new DecimalFormat("0.00");
		SimpleDateFormat format = new SimpleDateFormat("HH:mm");
		float maxTotal = 0.0f;
		try {

			for (TimesheetActivityWfm w : activityListToSaveWfm) {
				float hrs = 0;
				float total = (0.0f);
				for (int i = 0; i < 7; i++) {

					String in = "00:00";
					String out = "00:00";
					switch (i) {
					case 0:
						if (!w.getMonHrsOn().isEmpty() && !w.getMonHrsOff().isEmpty()) {
							in = w.getMonHrsOn();
							out = w.getMonHrsOff();
							total = calciluateTotalHrs(in, out, false, total, hrs, w, format);
						} else {
							continue;
						}
						break;

					case 1:
						if (!w.getTueHrsOn().isEmpty() && !w.getTueHrsOff().isEmpty()) {
							in = w.getTueHrsOn();
							out = w.getTueHrsOff();
							total = calciluateTotalHrs(in, out, false, total, hrs, w, format);
						} else {
							continue;
						}
						break;

					case 2:
						if (!w.getWedHrsOn().isEmpty() && !w.getWedHrsOff().isEmpty()) {
							in = w.getWedHrsOn();
							out = w.getWedHrsOff();
							total = calciluateTotalHrs(in, out, false, total, hrs, w, format);
						} else {
							continue;
						}
						break;

					case 3:
						if (!w.getThuHrsOn().isEmpty() && !w.getThuHrsOff().isEmpty()) {
							in = w.getThuHrsOn();
							out = w.getThuHrsOff();
							total = calciluateTotalHrs(in, out, false, total, hrs, w, format);
						} else {
							continue;
						}
						break;

					case 4:
						if (!w.getFriHrsOn().isEmpty() && !w.getFriHrsOff().isEmpty()) {
							in = w.getFriHrsOn();
							out = w.getFriHrsOff();
							total = calciluateTotalHrs(in, out, false, total, hrs, w, format);
						} else {
							continue;
						}
						break;

					case 5:
						if (!w.getSatHrsOn().isEmpty() && !w.getSatHrsOff().isEmpty()) {
							in = w.getSatHrsOn();
							out = w.getSatHrsOff();
							total = calciluateTotalHrs(in, out, true, total, hrs, w, format);
						} else {
							continue;
						}
						break;

					case 6:
						if (!w.getSunHrsOn().isEmpty() && !w.getSunHrsOff().isEmpty()) {
							in = w.getSunHrsOn();
							out = w.getSunHrsOff();
							total = calciluateTotalHrs(in, out, true, total, hrs, w, format);
						} else {
							continue;
						}
						break;

					}

				}
				w.setTotalhrs(df.format(total));
				maxTotal += total;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return df.format(maxTotal);
	}

	public static float calciluateTotalHrs(String in, String out, boolean weekend, float total, float hrs,
			TimesheetActivityWfm w, SimpleDateFormat format) throws ParseException {

		if (!Pattern.matches("[a-zA-Z]", in) && !Pattern.matches("[a-zA-Z]", out) && in.contains(":")
				&& out.contains(":")) {

			if (out.contains("AM")) {
				String[] tmp = out.toUpperCase().split(":");
				out = ((Integer.parseInt(tmp[0].trim()) == 12) ? 0 : Integer.parseInt(tmp[0].trim())) + ":"
						+ tmp[1].trim();
			} else if (out.contains("PM")) {
				String[] tmp = out.toUpperCase().split(":");
				out = (Integer.parseInt(tmp[0].trim())) + ":" + tmp[1].trim();
			}

			if (in.contains("AM")) {
				String[] tmp = in.toUpperCase().split(":");
				in = ((Integer.parseInt(tmp[0].trim()) == 12) ? 0 : Integer.parseInt(tmp[0].trim())) + ":"
						+ tmp[1].trim();
			} else if (in.contains("PM")) {
				String[] tmp = in.toUpperCase().split(":");
				in = (Integer.parseInt(tmp[0].trim())) + ":" + tmp[1].trim();
			}
			if (weekend) {
				if (in.contains("AM")) {
					in = in.toUpperCase().split(" ")[0];
					String[] tmp = in.toUpperCase().split(":");
					in = (Integer.parseInt(tmp[0].trim())) + ":" + tmp[1].trim();
					if (out.contains("AM")) {
						out = out.toUpperCase().split(" ")[0].trim();
						tmp = out.toUpperCase().split(":");
						out = (Integer.parseInt(tmp[0])) + ":" + tmp[1].trim();
					} else if (out.contains("PM")) {
						out = out.toUpperCase().split(" ")[0].trim();
						tmp = out.toUpperCase().split(":");
						out = (Integer.parseInt(tmp[0]) + 12) + ":" + tmp[1].trim();
					}
				} else if (in.contains("PM")) {
					in = in.toUpperCase().split(" ")[0].trim();
					String[] tmp = in.toUpperCase().split(":");
					in = (Integer.parseInt(tmp[0]) + 12) + ":" + tmp[1].trim();

					out = out.toUpperCase().split(" ")[0].trim();
					tmp = out.toUpperCase().split(":");
					out = (Integer.parseInt(tmp[0].trim()) + 24) + ":" + tmp[1].trim();

				}
			} else {
				if (in.contains("AM")) {
					in = in.toUpperCase().split(" ")[0];
					String[] tmp = in.toUpperCase().split(":");
					in = (Integer.parseInt(tmp[0].trim())) + ":" + tmp[1].trim();
					if (out.contains("AM")) {
						out = out.toUpperCase().split(" ")[0].trim();
						tmp = out.toUpperCase().split(":");
						out = (Integer.parseInt(tmp[0]) + 24) + ":" + tmp[1].trim();
					} else if (out.contains("PM")) {
						out = out.toUpperCase().split(" ")[0].trim();
						tmp = out.toUpperCase().split(":");
						out = (Integer.parseInt(tmp[0]) + 12) + ":" + tmp[1].trim();
					}
				} else if (in.contains("PM")) {
					in = in.toUpperCase().split(" ")[0].trim();
					String[] tmp = in.toUpperCase().split(":");
					in = (Integer.parseInt(tmp[0].trim()) + 12) + ":" + tmp[1].trim();

					out = out.toUpperCase().split(" ")[0].trim();
					tmp = out.toUpperCase().split(":");
					out = (Integer.parseInt(tmp[0].trim()) + 24) + ":" + tmp[1].trim();

				}
			}

			Date date1 = format.parse(in);
			Date date2 = format.parse(out);
			long diffMinutes = (date2.getTime() - date1.getTime()) / (60000);
			hrs += ((float) diffMinutes / 60);
			total += hrs;
		}
		return total;
	}

	@SkipValidation
	public String downloadTimesheetWFM() {
		try {
			String filePath = getText("rico.summary.export.path");
			File dir = new File(filePath);
			if (!dir.exists()) {
				@SuppressWarnings("unused")
				boolean result = dir.mkdirs();
			}
			if (((String) session.get("role")).equalsIgnoreCase("ADMIN")) {
				setShowAllSubRes("ALL");
			}
			String filaeName = getText("rico.summary.export.path") + getText("pts.menu.reportee.utilization.report.whm")
					+ "_" + (Long) session.get("userId") + ".xlsx";
			File fileToDownload = new File(filaeName);
			fileToDownload.delete();
			Map<String, String> excelColHeaders = new LinkedHashMap<String, String>();
			setColumnHeaders(excelColHeaders);
			DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			filePath = serviceManager.getUserUtilizationService().generateExcelWFM((Long) session.get("userId"),
					excelColHeaders,
					getWeekEnding((selectedDate != null && !selectedDate.isEmpty()) ? selectedDate
							: format.format(new Date())),
					selectedEmployee, null, filaeName, showAllWfm, showAllSubRes, getSelectedStream());
			setWfmDownloaderOk("TRUE");
			serviceManager.getUserService().getAllSupervisorList(employeeList, userStreamList,
					(Long) session.get("userId"));
			// pushFileToClient(filePath,
			// getText("pts.menu.reportee.utilization.report.whm") + "_"
			// + (Long) session.get("userId") + ".xlsx");
		} catch (Throwable e) {
			e.printStackTrace();
		}

		return SUCCESS;
	}

// after saving file this is called again
	public String downloadTimesheetWFMNow() throws Throwable {
		try {
			String filaeName = getText("rico.summary.export.path") + getText("pts.menu.reportee.utilization.report.whm")
					+ "_" + (Long) session.get("userId") + ".xlsx";

			File f = new File(filaeName);
			setInputStream(new FileInputStream(f));
			setContentLength(f.length());
			serviceManager.getUserService().getAllSupervisorList(employeeList, userStreamList,
					(Long) session.get("userId"));
			// pushFileToClient(getText("rico.summary.export.path") + tempfileName,
			// tempfileName);
			setWfmDownloaderOk("FALSE");
		} catch (IOException e) {
			addActionError("Some Thing Went Wrong!!!!!!");
			e.printStackTrace();
		}
		return SUCCESS;
	}

	private void setColumnHeaders(Map<String, String> excelColHeaders) {
		excelColHeaders.put("NAME", "Name");
		excelColHeaders.put("SIGNUM", "Signum");
		excelColHeaders.put("WEEKEND_DATE", "Date");
		excelColHeaders.put("MON_HRS_ON", "MondayOn Time");
		excelColHeaders.put("MON_HRS_OFF", "MondayOff Time");
		excelColHeaders.put("TUE_HRS_ON", "TuesdayOn Time");
		excelColHeaders.put("TUE_HRS_OFF", "TuesdayOff Time");
		excelColHeaders.put("WED_HRS_ON", "WednesdayOn Time");
		excelColHeaders.put("WED_HRS_OFF", "WednesdayOff Time");
		excelColHeaders.put("THU_HRS_ON", "ThursdayOn Time");
		excelColHeaders.put("THU_HRS_OFF", "ThursdayOff Time");
		excelColHeaders.put("FRI_HRS_ON", "FridayOn Time");
		excelColHeaders.put("FRI_HRS_OFF", "FridayOff Time");
		excelColHeaders.put("SAT_HRS_ON", "SaturdayOn Time");
		excelColHeaders.put("SAT_HRS_OFF", "SaturdayOff Time");
		excelColHeaders.put("SUN_HRS_ON", "SundayOn Time");
		excelColHeaders.put("SUN_HRS_OFF", "SundayOff Time");
		excelColHeaders.put("TOTAL_HRS", "Total Hours");
	}

	@SuppressWarnings("deprecation")
	private void getUserCapacity() throws Throwable {
		// HashMap<String, Float> daoplannedCapacityMap = new HashMap<String, Float>();
		Date d = new Date();
		int month = (d.getMonth() + 1);
		long year = d.getYear();
		if (getSelectedDate() != null) {
			month = Integer.parseInt(getSelectedDate().split("/")[0]);
			year = Long.parseLong(getSelectedDate().split("/")[2]);
		}
		plannedCapacityList = serviceManager.getCapacityPlanningService()
				.getUserCapacityByName(setSearchSortContainer(year, month));

	}

	@SuppressWarnings("deprecation")
	@SkipValidation
	private String getUserNwCapacity() {
		try {
			serviceManager.getNetworkCodesService().getUserCapacityByRelease(userCapacityByRelease, selectedEmployee);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	private SearchSortContainer setSearchSortContainer(long year, int searchMonth) {
		SearchSortContainer s = new SearchSortContainer();
		s.setSearchYear(year);
		s.setSearchMonth(searchMonth);
		s.setUserId((Long) session.get("userId"));
		return s;
	}

	@SkipValidation
	public String goUserActivity() {
		try {
			serviceManager.getNetworkCodesService().getUserCapacityByRelease(userCapacityByRelease, selectedEmployee);
			{
				removedIds = "";
				removedTemplateIds = "";
				if (session.get("userStreamName") != null
						&& ((String) session.get("userStreamName")).equalsIgnoreCase("Development")) {
					tempActivityCode = 7l;
				} else if (session.get("userStreamName") != null
						&& ((String) session.get("userStreamName")).equalsIgnoreCase("Testing")) {
					tempActivityCode = 5l;
				} else {
					tempActivityCode = -1l;
				}
				DateFormat format = new SimpleDateFormat("MM/dd/yyyy");
				fromDate = getWeekEnding(selectedDate);
				/*
				 * if (selectedDate == null || (selectedDate != null &&
				 * selectedDate.equals(""))) { fromDate = new Date(fromDate.getTime() -
				 * TimeUnit.DAYS.toMillis(7)); }
				 */
				selectedDate = format.format(fromDate);
				selectedHiddenDate = format.format(fromDate);
				tempType = TimeSheetType.Productive.toString();
				selectedNetworkCodesMap = new LinkedHashMap<Long, String>();
				selectedNetworkCodesMapDeleted = new LinkedHashMap<Long, String>();
				activityCodesMap = new LinkedHashMap<Long, String>();

				// these two are used for getting activity of 2nd table
				activityCodesPtlMap = new LinkedHashMap<Long, String>();
				activityCodesProjectMap = new LinkedHashMap<Long, String>();
				activityCodesProjectMap = new LinkedHashMap<Long, String>();
				serviceManager.getActivityCodesService().getActivityCodesMapPtl(activityCodesPtlMap);
				serviceManager.getActivityCodesService().getActivityCodesMapProject(activityCodesProjectMap);
				serviceManager.getActivityCodesService().getActivityCodesManagement(activityCodesManagementMap);

				serviceManager.getNetworkCodesService().getAssignedNetworkCodesMap(selectedEmployee,
						selectedNetworkCodesMap, fromDate, session.get("userStatus") + "");

				serviceManager.getNetworkCodesService().getAssignedNetworkCodesMapDeleted(selectedEmployee,
						selectedNetworkCodesMapDeleted, fromDate);
				userCapacityByRelease.clear();
				// these two are used for getting activity of 2nd table
				serviceManager.getNetworkCodesService().getUserCapacityByRelease(userCapacityByRelease,
						selectedEmployee);
				serviceManager.getActivityCodesService().getActivityCodesMap(activityCodesMap);
				serviceManager.getActivityCodesService().getProjectMapNew(projectAssignement);
				List<Project> projects = serviceManager.getActivityCodesService().getProjectMapList();

				ptlEnabled = false;
				feasibilityEnabled = false;
				projectSupportEnabled = false;
				securityEnabled = false;

				List<UserStableTeams> userStableTeams = serviceManager.getUserService()
						.getUserStableTeams((java.lang.Long) session.get("userId"));
				for (UserStableTeams a : userStableTeams) {
					if (a.getStableTeam() != null && a.getStableTeam().equalsIgnoreCase("PTL/ Security")
							&& a.getStableContribution() > 0) {
						ptlEnabled = true;
						securityEnabled = true;
					}
					if (a.getStableTeam() != null && a.getStableTeam().equalsIgnoreCase("Feasibility")
							&& a.getStableContribution() > 0) {
						feasibilityEnabled = true;
					}
					if (a.getStableTeam() != null && a.getStableTeam().equalsIgnoreCase("Production Support")
							&& a.getStableContribution() > 0) {
						projectSupportEnabled = true;
					}
				}
				activityListToSave = new ArrayList<TimesheetActivity>();
				activityListProductsToSave = new ArrayList<TimesheetActivity>();

				activityList = new ArrayList<TimesheetActivity>();
				activityListProjects = new ArrayList<TimesheetActivity>();

				weekOffFlag = serviceManager.getUserService().getUserWeekOff(selectedEmployee, fromDate);

				if (!weekOffFlag) {
					Set<Long> userSet = new HashSet<Long>();
					userSet.add(selectedEmployee);
					serviceManager.getUserEffortService().getEffortDetailsForWeekForUsers(activityList, fromDate, null,
							userSet, "All");

					for (TimesheetActivity a : activityList) {
						if (a.getActivityType() != null && (a.getActivityType().equalsIgnoreCase("P")
								|| a.getActivityType().equalsIgnoreCase("PTL"))) {
							activityListProjects.add(a);
						}
					}

					activityList.removeAll(activityListProjects);

					activityListCount = activityList.size();
				}
				List<NetworkCodes> network = serviceManager.getNetworkCodesService().getMislaneiousProjects();

				serviceManager.getUserEffortService().getTimeTemplate(activityList, activityListProjects,
						selectedEmployee);

				List<TimesheetActivity> activityListProjectsTmp = new ArrayList<TimesheetActivity>();

				for (TimesheetActivity a : activityListProjects) {
					for (NetworkCodes n : network) {
						if (n.getId().longValue() == a.getNetworkId().longValue()) {
							a.setNetworkIdDesc(n.getReleaseName());
						}
					}
				}
				for (Project project : projects) {
					boolean available = false;
					TimesheetActivity a = new TimesheetActivity();
					for (TimesheetActivity data : activityListProjects) {
						if (data.getNetworkIdDesc().equalsIgnoreCase(project.getProjectName())) {
							a = new TimesheetActivity();
							available = true;
							a.setId(data.getId());
							a.setWeekendingDate(fromDate);
							a.setMonHrs(data.getMonHrs());
							a.setTueHrs(data.getTueHrs());
							a.setWedHrs(data.getWedHrs());
							a.setThuHrs(data.getThuHrs());
							a.setFriHrs(data.getFriHrs());
							a.setSatHrs(data.getSatHrs());
							a.setSunHrs(data.getSunHrs());
							a.setType(project.getProjectName());
							a.setApprovalStatus("");
							a.setActivityCode(data.getActivityCode());
							a.setActivityDesc(project.getProjectName());
							a.setProjectAssignmentId(project.getProjectId());
							a.setActivityType(
									(project.getType() != null && project.getType().equalsIgnoreCase("PTL")) ? "PTL"
											: "P");
							a.setNetworkId(data.getNetworkId());
							a.setNetworkIdDesc(project.getProjectName());
							a.setAddCheckFlag(true);
							activityListProjectsTmp.add(a);
						}

					}
					if (!available) {

						if (project.getProjectName().contains("Vacation")
								|| project.getProjectName().contains("Training")) {
							a.setAddCheckFlag(true);
						}
						a.setWeekendingDate(fromDate);
						a.setMonHrs(0.0F);
						a.setTueHrs(0.0F);
						a.setWedHrs(0.0F);
						a.setThuHrs(0.0F);
						a.setFriHrs(0.0F);
						a.setSatHrs(0.0F);
						a.setSunHrs(0.0F);
						a.setType(project.getProjectName());
						a.setApprovalStatus("");
						a.setActivityDesc(project.getProjectName());
						a.setProjectAssignmentId(project.getProjectId());
						a.setActivityType(
								(project.getType() != null && project.getType().equalsIgnoreCase("PTL")) ? "PTL" : "P");

						for (NetworkCodes n : network) {
							if (n.getReleaseName().contains(project.getProjectName())) {
								a.setNetworkId(n.getId());
								a.setNetworkIdDesc(project.getProjectName());
								break;
							}
						}
						activityListProjectsTmp.add(a);
					}
				}

				activityListProjects = activityListProjectsTmp;

				Set<Long> userId = new HashSet<>();
				userId.add((java.lang.Long) session.get("userId"));
				activityListProductsToSave.clear();
				activityListProductsToSave.addAll(activityListProjectsTmp);
				timeSheetRecord = new TimeSheetRecord();
				timeSheetRecord.setActivityListProductsToSave(activityListProductsToSave);
				timeSheetRecord.setActivityListToSave(activityListToSave);

				getSummation();
				getUserCapacity();
			}

		} catch (Throwable e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	@SkipValidation
	public String getRemainingHrsForNetworkCode() {

		remainingHrs = serviceManager.getNetworkCodesService().getRemainingHrsForNetworkCode(selectedNetworkCodeId,
				(String) session.get("userType"), (String) session.get("userStream"));
		return SUCCESS;
	}

	@SkipValidation
	public String goDeleteTimeSheet() {
		System.out.println(id);
		return SUCCESS;
	}

	@SkipValidation
	public String saveUserActivity() {
		try {
			// these two are used for getting activity of 2nd table activityCodesPtlMap =
			new LinkedHashMap<Long, String>();
			activityCodesProjectMap = new LinkedHashMap<Long, String>();

			serviceManager.getActivityCodesService().getActivityCodesMapPtl(activityCodesPtlMap);
			serviceManager.getActivityCodesService().getActivityCodesMapProject(activityCodesProjectMap);

			serviceManager.getActivityCodesService().getProjectMapNew(projectAssignement);

			timeSheetRecord.getActivityListToSave().addAll(timeSheetRecord.getActivityListProductsToSave());

			if (weekOffFlag) {
				selectedEmployees.clear();
				selectedEmployees.add(selectedEmployee);
				serviceManager.getUserService().saveUserWeekOff(selectedEmployees, getWeekEnding(selectedDate),
						weekOffFlag);
			} else {
				selectedEmployees.clear();
				selectedEmployees = new ArrayList<Long>();
				selectedEmployees.add(selectedEmployee);
				serviceManager.getUserService().saveUserWeekOff(selectedEmployees, getWeekEnding(selectedDate), false);
				serviceManager.getUserEffortService().saveTimeSheet(timeSheetRecord.getActivityListToSave(),
						(String) session.get("username"), selectedEmployee, getWeekEnding(selectedDate), removedIds);
			}
			Calendar calendarStart = Calendar.getInstance();
			calendarStart.set(Calendar.YEAR, 2020);
			calendarStart.set(Calendar.MONTH, 0);
			calendarStart.set(Calendar.DAY_OF_MONTH, 1);
			minStartDate = calendarStart.getTime();
			removedIds = "";

			addActionMessage("Successfully saved..");
		} catch (Throwable th) {
			addActionMessage("Timesheet save unsuccessfull, please try again...");
			th.printStackTrace();
		} finally {
			goUserActivity();
		}
		return SUCCESS;
	}

	@SkipValidation
	public String saveUserActivityWFM() {
		try {
			selectedEmployees.clear();
			selectedEmployees = new ArrayList<Long>();
			selectedEmployees.add(selectedEmployee);

			activityListToSaveWfm.get(0).setUserName(session.get("username") + "");
			serviceManager.getUserEffortService().saveTimeSheetWFM(activityListToSaveWfm,
					(java.lang.Long) session.get("userId"), selectedEmployee, getWeekEnding(selectedDate), removedIds);
			Calendar calendarStart = Calendar.getInstance();
			calendarStart.set(Calendar.YEAR, 2020);
			calendarStart.set(Calendar.MONTH, 0);
			calendarStart.set(Calendar.DAY_OF_MONTH, 1);
			minStartDate = calendarStart.getTime();
			removedIds = "";

			addActionMessage("Successfully saved..");
		} catch (Throwable th) {
			addActionMessage("Timesheet save unsuccessfull, please try again...");
			th.printStackTrace();
		} finally {

		}
		return SUCCESS;
	}

	@SkipValidation
	public String saveTemplate() {
		try {
			List<NetworkCodes> network = serviceManager.getNetworkCodesService().getMislaneiousProjects();
			List<Project> projects = serviceManager.getActivityCodesService().getProjectMapList();
			timeSheetRecord.getActivityListToSave().addAll(timeSheetRecord.getActivityListProductsToSave());

			serviceManager.getUserEffortService().saveTemplate(timeSheetRecord.getActivityListToSave(),
					(Long) session.get("userId"), getAdditionalProjects(), removeAdditional, projects, network);
			removedTemplateIds = "";
			addActionMessage("Template saved successfully...");
		} catch (Throwable th) {
			th.printStackTrace();
			addActionMessage("Template save unsuccessfull...");
		} finally {
			goUserActivity();
		}
		return SUCCESS;
	}

	@SkipValidation
	public String weekOff() {
		try {
			DateFormat format = new SimpleDateFormat("MM/dd/yyyy");
			fromDate = getWeekEnding(selectedDate);
			selectedDate = format.format(fromDate);
			serviceManager.getUserService().getUserList((Long) session.get("userId"), employeeList, null, null);
			employeeList.put((Long) session.get("userId"), (String) session.get("fullName"));
		} catch (Throwable e) {
			e.printStackTrace();
		}

		return SUCCESS;
	}

	@SkipValidation
	public String saveUserWeekOff() {
		try {
			serviceManager.getUserService().getUserList((Long) session.get("userId"), employeeList, null, null);
			employeeList.put((Long) session.get("userId"), (String) session.get("fullName"));
			selectedEmployees.add(selectedEmployee);
			serviceManager.getUserService().saveUserWeekOff(selectedEmployees, getWeekEnding(selectedDate),
					weekOffFlag);
			addActionMessage("Saved successfully ..");
		} catch (Throwable e) {
			e.printStackTrace();
			addActionMessage("Save unsuccessfull...");
		}

		return SUCCESS;
	}

	@SkipValidation
	public String goApproveTimesheet() {
		try {
			activityList.clear();
			serviceManager.getUserService().getUserList((Long) session.get("userId"), employeeList);
			Set<Long> userSet = new HashSet<Long>();
			if (selectedEmployee != null && !selectedEmployee.equals("")) {
				userSet.add(selectedEmployee);
			} else {
				userSet.addAll(employeeList.keySet());
			}
			if (StringHelper.isEmpty(approvalType)) {
				approvalType = "Pending";
			}
			if (selectedDate == null) {
				fromDate = getStartWeekEnding();
			} else {
				fromDate = getWeekEnding(selectedDate);
			}

			toDate = getWeekEnding(selectedToDate);
			if (showAllPen) {
				// to show all pending from start of year
				fromDate = null;
				toDate = null;
			}
			if (userSet != null && userSet.size() > 0) {
				serviceManager.getUserEffortService().getEffortDetailsForWeek(activityList, fromDate, toDate, userSet,
						approvalType);

				if (activityList.size() > 0 && activityList.get(0).getType().equalsIgnoreCase("Vacation")
						&& activityList.get(0).getActivitySummation() <= 0.0F) {
					activityList.remove(0);
				}

			}
			if (selectedDate == null) {
				fromDate = getStartWeekEnding();
			}
		} catch (Throwable e) {
			e.printStackTrace();
		}

		return SUCCESS;
	}

	@SkipValidation
	public String approveTimesheet() {
		try {
			serviceManager.getUserEffortService().approveTimeSheet(activityListToSave, (String) session.get("username"),
					getWeekEnding(selectedDate));
			goApproveTimesheet();
			addActionMessage("Successfully saved..");
		} catch (Throwable e) {
			e.printStackTrace();
			addActionMessage("Save unsuccessfull...");
		}
		return SUCCESS;
	}

	@SkipValidation
	public String goUploadEssData() {
		return SUCCESS;
	}

	@SkipValidation
	public String uploadEssData() {
		try {
			String filePath = "/applications/tomcat/capacity/";

			File fileToCreate = new File(filePath, uploadFileFileName);
			FileUtils.copyFile(uploadFile, fileToCreate);
			serviceManager.getUserEffortService().uploadEssData(uploadFileFileName);
			addActionMessage("Upload Success..");
		} catch (Throwable e) {
			e.printStackTrace();
			addActionMessage("Upload Failed..");
			return INPUT;

		}
		return SUCCESS;
	}

	public Map<Long, String> getEmployeeList() {
		return employeeList;
	}

	public void setEmployeeList(Map<Long, String> employeeList) {
		this.employeeList = employeeList;
	}

	public Long getSelectedEmployee() {
		return selectedEmployee;
	}

	public void setSelectedEmployee(Long selectedEmployee) {
		this.selectedEmployee = selectedEmployee;
	}

	public List<TimesheetActivity> getActivityList() {
		return activityList;
	}

	public void setActivityList(List<TimesheetActivity> activityList) {
		this.activityList = activityList;
	}

	public Map<Long, String> getSelectedNetworkCodesMap() {
		return selectedNetworkCodesMap;
	}

	public void setSelectedNetworkCodesMap(Map<Long, String> selectedNetworkCodesMap) {
		this.selectedNetworkCodesMap = selectedNetworkCodesMap;
	}

	public Map<Long, String> getActivityCodesMap() {
		return activityCodesMap;
	}

	public void setActivityCodesMap(Map<Long, String> activityCodesMap) {
		this.activityCodesMap = activityCodesMap;
	}

	public float getTotMonSum() {
		return totMonSum;
	}

	public void setTotMonSum(float totMonSum) {
		this.totMonSum = totMonSum;
	}

	public float getTotTueSum() {
		return totTueSum;
	}

	public void setTotTueSum(float totTueSum) {
		this.totTueSum = totTueSum;
	}

	public float getTotWedSum() {
		return totWedSum;
	}

	public void setTotWedSum(float totWedSum) {
		this.totWedSum = totWedSum;
	}

	public float getTotThuSum() {
		return totThuSum;
	}

	public void setTotThuSum(float totThuSum) {
		this.totThuSum = totThuSum;
	}

	public float getTotFriSum() {
		return totFriSum;
	}

	public void setTotFriSum(float totFriSum) {
		this.totFriSum = totFriSum;
	}

	public float getTotSatSum() {
		return totSatSum;
	}

	public void setTotSatSum(float totSatSum) {
		this.totSatSum = totSatSum;
	}

	public float getTotSunSum() {
		return totSunSum;
	}

	public void setTotSunSum(float totSunSum) {
		this.totSunSum = totSunSum;
	}

	public float getTotSummation() {
		return totSummation;
	}

	public void setTotSummation(float totSummation) {
		this.totSummation = totSummation;
	}

	public int getActivityListCount() {
		return activityListCount;
	}

	public void setActivityListCount(int activityListCount) {
		this.activityListCount = activityListCount;
	}

	public String getRemovedIds() {
		return removedIds;
	}

	public void setRemovedIds(String removedIds) {
		this.removedIds = removedIds;
	}

	public List<TimesheetActivity> getActivityListToSave() {
		return activityListToSave;
	}

	public void setActivityListToSave(List<TimesheetActivity> activityListToSave) {
		this.activityListToSave = activityListToSave;
	}

	public String getRemainingHrs() {
		return remainingHrs;
	}

	public void setRemainingHrs(String remainingHrs) {
		this.remainingHrs = remainingHrs;
	}

	public Long getSelectedNetworkCodeId() {
		return selectedNetworkCodeId;
	}

	public void setSelectedNetworkCodeId(Long selectedNetworkCodeId) {
		this.selectedNetworkCodeId = selectedNetworkCodeId;
	}

	public String getTimesheetType() {
		return timesheetType;
	}

	public void setTimesheetType(String timesheetType) {
		this.timesheetType = timesheetType;
	}

	public String getRemovedTemplateIds() {
		return removedTemplateIds;
	}

	public void setRemovedTemplateIds(String removedTemplateIds) {
		this.removedTemplateIds = removedTemplateIds;
	}

	public Long getTempActivityCode() {
		return tempActivityCode;
	}

	public void setTempActivityCode(Long tempActivityCode) {
		this.tempActivityCode = tempActivityCode;
	}

	public String getTempType() {
		return tempType;
	}

	public void setTempType(String tempType) {
		this.tempType = tempType;
	}

	public boolean isWeekOffFlag() {
		return weekOffFlag;
	}

	public void setWeekOffFlag(boolean weekOffFlag) {
		this.weekOffFlag = weekOffFlag;
	}

	public List<Long> getSelectedEmployees() {
		return selectedEmployees;
	}

	public void setSelectedEmployees(List<Long> selectedEmployees) {
		this.selectedEmployees = selectedEmployees;
	}

	private void getSummation() {
		totMonSum = 0.0f;
		totTueSum = 0.0f;
		totWedSum = 0.0f;
		totThuSum = 0.0f;
		totFriSum = 0.0f;
		totSatSum = 0.0f;
		totSunSum = 0.0f;
		totSummation = 0.0f;
		for (TimesheetActivity activity : activityList) {
			totSummation = totSummation + activity.getActivitySummation();
			totMonSum = totMonSum + (activity.getMonHrs() == null ? 0.0f : activity.getMonHrs());
			totTueSum = totTueSum + (activity.getTueHrs() == null ? 0.0f : activity.getTueHrs());
			totWedSum = totWedSum + (activity.getWedHrs() == null ? 0.0f : activity.getWedHrs());
			totThuSum = totThuSum + (activity.getThuHrs() == null ? 0.0f : activity.getThuHrs());
			totFriSum = totFriSum + (activity.getFriHrs() == null ? 0.0f : activity.getFriHrs());
			totSatSum = totSatSum + (activity.getSatHrs() == null ? 0.0f : activity.getSatHrs());
			totSunSum = totSunSum + (activity.getSunHrs() == null ? 0.0f : activity.getSunHrs());
		}
	}

	public Map<String, String> getTimeSheetTypeMap() {
		Map<String, String> timeSheetTypeMap = new LinkedHashMap<String, String>();

		timeSheetTypeMap = (serviceManager.getUserService().getTimeSheetTypeMap());

		/*
		 * timeSheetTypeMap.put(TimeSheetType.DepartmentalWork.toString(),
		 * "Departmental Work"); timeSheetTypeMap.put(TimeSheetType.Overtime.toString(),
		 * "Overtime"); timeSheetTypeMap.put(TimeSheetType.Productive.toString(),
		 * "Productive Hours");
		 * timeSheetTypeMap.put(TimeSheetType.TrainingOrCertification.toString(),
		 * "Training/ Certification");
		 * 
		 * 
		 * timeSheetTypeMap.put(TimeSheetType.Leave.toString(), "Leave");
		 * timeSheetTypeMap.put(TimeSheetType.Holiday.toString(), "Holiday");
		 */
		return timeSheetTypeMap;
	}

	public String getApprovalType() {
		return approvalType;
	}

	public void setApprovalType(String approvalType) {
		this.approvalType = approvalType;
	}

	public List<CapacityPlanning> getPlannedCapacityList() {
		return plannedCapacityList;
	}

	public void setPlannedCapacityList(List<CapacityPlanning> plannedCapacityList) {
		this.plannedCapacityList = plannedCapacityList;
	}

	public Date getMinStartDate() {
		return minStartDate;
	}

	public void setMinStartDate(Date minStartDate) {
		this.minStartDate = minStartDate;
	}

	public List<Integer> getTotalMerger() {
		return totalMerger;
	}

	public void setTotalMerger(List<Integer> totalMerger) {
		this.totalMerger = totalMerger;
	}

	public boolean isShowAllPen() {
		return showAllPen;
	}

	public void setShowAllPen(boolean showAllPen) {
		this.showAllPen = showAllPen;
	}

	public List<TimesheetActivityWfm> getActivityListToSaveWfm() {
		return activityListToSaveWfm;
	}

	public void setActivityListToSaveWfm(List<TimesheetActivityWfm> activityListToSaveWfm) {
		this.activityListToSaveWfm = activityListToSaveWfm;
	}

	public String getShowAllWfm() {
		return showAllWfm;
	}

	public void setShowAllWfm(String showAllWfm) {
		this.showAllWfm = showAllWfm;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getShowAllSubRes() {
		return showAllSubRes;
	}

	public void setShowAllSubRes(String showAllSubRes) {
		this.showAllSubRes = showAllSubRes;
	}

	public InputStream getInputStream() {
		filename = getText("pts.menu.reportee.utilization.report.whm") + "_" + (Long) session.get("userId") + ".xlsx";
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

	public String getWfmDownloaderOk() {
		return wfmDownloaderOk;
	}

	public void setWfmDownloaderOk(String wfmDownloaderOk) {
		this.wfmDownloaderOk = wfmDownloaderOk;
	}

	public String getTotalSummation() {
		return totalSummation;
	}

	public void setTotalSummation(String totalSummation) {
		this.totalSummation = totalSummation;
	}

	public Map<Long, String> getUserStreamList() {
		return userStreamList;
	}

	public void setUserStreamList(Map<Long, String> userStreamList) {
		this.userStreamList = userStreamList;
	}

	public Long getSelectedStream() {
		return selectedStream;
	}

	public void setSelectedStream(Long selectedStream) {
		this.selectedStream = selectedStream;
	}

	public List<String> getMonthlist() {
		monthlist = getMonthList();
		return monthlist;
	}

	public void setMonthlist(List<String> monthlist) {
		this.monthlist = monthlist;
	}

	public Integer getRecords() {
		return records;
	}

	public void setRecords(Integer records) {
		this.records = records;
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public String getOper() {
		return oper;
	}

	public void setOper(String oper) {
		this.oper = oper;
	}

	public String getSearchOper() {
		return searchOper;
	}

	public void setSearchOper(String searchOper) {
		this.searchOper = searchOper;
	}

	public Integer getTotalrows() {
		return totalrows;
	}

	public void setTotalrows(Integer totalrows) {
		this.totalrows = totalrows;
	}

	public String getSearchString() {
		return searchString;
	}

	public void setSearchString(String searchString) {
		this.searchString = searchString;
	}

	public String getSearchField() {
		return searchField;
	}

	public void setSearchField(String searchField) {
		this.searchField = searchField;
	}

	public String getSidx() {
		return sidx;
	}

	public void setSidx(String sidx) {
		this.sidx = sidx;
	}

	public String getSord() {
		return sord;
	}

	public void setSord(String sord) {
		this.sord = sord;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Integer getRows() {
		return rows;
	}

	public void setRows(Integer rows) {
		this.rows = rows;
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

	public List<EssDetails> getEssFeedModal() {
		return essFeedModal;
	}

	public void setEssFeedModal(List<EssDetails> essFeedModal) {
		this.essFeedModal = essFeedModal;
	}

	public List<ActivityCodesNew> getActivityNew() {
		return activityNew;
	}

	public void setActivityNew(List<ActivityCodesNew> activityNew) {
		this.activityNew = activityNew;
	}

	public Map<Long, String> getActivityMapNew() {
		return activityMapNew;
	}

	public void setActivityMapNew(Map<Long, String> activityMapNew) {
		this.activityMapNew = activityMapNew;
	}

	public String getRemovedIdsactivity() {
		return removedIdsactivity;
	}

	public void setRemovedIdsactivity(String removedIdsactivity) {
		this.removedIdsactivity = removedIdsactivity;
	}

	public String getVacationType() {
		return vacationType;
	}

	public void setVacationType(String vacationType) {
		this.vacationType = vacationType;
	}

	public Map<Long, String> getActivityCodesPtlMap() {
		return activityCodesPtlMap;
	}

	public void setActivityCodesPtlMap(Map<Long, String> activityCodesPtlMap) {
		this.activityCodesPtlMap = activityCodesPtlMap;
	}

	public Map<Long, String> getActivityCodesProjectMap() {
		return activityCodesProjectMap;
	}

	public void setActivityCodesProjectMap(Map<Long, String> activityCodesProjectMap) {
		this.activityCodesProjectMap = activityCodesProjectMap;
	}

	public List<TimesheetActivity> getActivityListProductsToSave() {
		return activityListProductsToSave;
	}

	public void setActivityListProductsToSave(List<TimesheetActivity> activityListProductsToSave) {
		this.activityListProductsToSave = activityListProductsToSave;
	}

	public List<TimesheetActivity> getActivityListProjects() {
		return activityListProjects;
	}

	public void setActivityListProjects(List<TimesheetActivity> activityListProjects) {
		this.activityListProjects = activityListProjects;
	}

	public TimeSheetRecord getTimeSheetRecord() {
		return timeSheetRecord;
	}

	public void setTimeSheetRecord(TimeSheetRecord timeSheetRecord) {
		this.timeSheetRecord = timeSheetRecord;
	}

	public Map<Long, String> getProjectAssignement() {
		return projectAssignement;
	}

	public void setProjectAssignement(Map<Long, String> projectAssignement) {
		this.projectAssignement = projectAssignement;
	}

	public static Long getKey(Map<Long, String> mapref, String value) {
		Long key = -1L;
		for (Map.Entry<Long, String> map : mapref.entrySet()) {
			if (map.getValue().toString().equals(value)) {
				key = map.getKey();
			}
		}
		return key;
	}

	public String getAdditionalProjects() {
		return additionalProjects;
	}

	public void setAdditionalProjects(String additionalProjects) {
		this.additionalProjects = additionalProjects;
	}

	public String getRemoveAdditional() {
		return removeAdditional;
	}

	public void setRemoveAdditional(String removeAdditional) {
		this.removeAdditional = removeAdditional;
	}

	public TimeSheetRecord getTimeSheetRecord1() {
		return timeSheetRecord1;
	}

	public void setTimeSheetRecord1(TimeSheetRecord timeSheetRecord1) {
		this.timeSheetRecord1 = timeSheetRecord1;
	}

	public Map<Long, String> getActivityCodesManagementMap() {
		return activityCodesManagementMap;
	}

	public void setActivityCodesManagementMap(Map<Long, String> activityCodesManagementMap) {
		this.activityCodesManagementMap = activityCodesManagementMap;
	}

	public Map<Long, String> getSelectedNetworkCodesMapDeleted() {
		return selectedNetworkCodesMapDeleted;
	}

	public void setSelectedNetworkCodesMapDeleted(Map<Long, String> selectedNetworkCodesMapDeleted) {
		this.selectedNetworkCodesMapDeleted = selectedNetworkCodesMapDeleted;
	}

	public boolean isProjectSupportEnabled() {
		return projectSupportEnabled;
	}

	public void setProjectSupportEnabled(boolean projectSupportEnabled) {
		this.projectSupportEnabled = projectSupportEnabled;
	}

	public boolean isFeasibilityEnabled() {
		return feasibilityEnabled;
	}

	public void setFeasibilityEnabled(boolean feasibilityEnabled) {
		this.feasibilityEnabled = feasibilityEnabled;
	}

	public boolean isPtlEnabled() {
		return ptlEnabled;
	}

	public void setPtlEnabled(boolean ptlEnabled) {
		this.ptlEnabled = ptlEnabled;
	}

	public boolean isSecurityEnabled() {
		return securityEnabled;
	}

	public void setSecurityEnabled(boolean securityEnabled) {
		this.securityEnabled = securityEnabled;
	}

	public Map<Long, Long> getUserCapacityByRelease() {
		return userCapacityByRelease;
	}

	public void setUserCapacityByRelease(Map<Long, Long> userCapacityByRelease) {
		this.userCapacityByRelease = userCapacityByRelease;
	}

	public boolean isGetUserProjectMap() {
		return getUserProjectMap;
	}

	public void setGetUserProjectMap(boolean getUserProjectMap) {
		this.getUserProjectMap = getUserProjectMap;
	}

}
