package com.egil.pts.service.impl;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.poi.ss.usermodel.IndexedColors;
import org.hibernate.internal.util.StringHelper;
import org.springframework.stereotype.Service;

import com.egil.pts.constants.ExcelHeaderConstants;
import com.egil.pts.dao.domain.User;
import com.egil.pts.dao.domain.UserStableTeams;
import com.egil.pts.modal.NetworkCodes;
import com.egil.pts.modal.Pagination;
import com.egil.pts.modal.ResourceEffort;
import com.egil.pts.modal.StableTeams;
import com.egil.pts.modal.SummaryResponse;
import com.egil.pts.modal.SupervisorResourceUtilization;
import com.egil.pts.modal.TimesheetActivityWfm;
import com.egil.pts.modal.Utilization;
import com.egil.pts.service.UserUtilizationService;
import com.egil.pts.service.common.BaseUIService;
import com.egil.pts.util.GenericExcel;
import com.egil.pts.util.GenericMail;
import com.egil.pts.util.Utility;

@Service("userUtilizationService")
public class UserUtilizationServiceImpl extends BaseUIService implements UserUtilizationService {

	@Override
	public SummaryResponse<Utilization> getUserUtilizationSummary(Long supervisorId, String fromDate, String toDate,
			Long searchValue, String reporteeType, Pagination pagination, boolean delinquentEntries,
			List<String> weeksList, long stableTeamid, Long networkCodeId, Long projectManagerId) throws Throwable {
		List<Utilization> utils = null;
		SummaryResponse<Utilization> summary = new SummaryResponse<Utilization>();
		if (!delinquentEntries) {
			summary.setTotalRecords(daoManager.getUserUtilizationDao().getUserUtilizationCount(supervisorId, fromDate,
					toDate, searchValue, reporteeType, delinquentEntries, weeksList, stableTeamid, networkCodeId,
					projectManagerId));
			utils = daoManager.getUserUtilizationDao().getUserUtilizationSummary(supervisorId, fromDate,
					toDate, searchValue, reporteeType, pagination, delinquentEntries, weeksList, stableTeamid,
					networkCodeId, projectManagerId);
		} else {

			utils = daoManager.getUserUtilizationDao().getUserUtilizationSummaryAddUnCharged(supervisorId, fromDate,
					toDate, searchValue, reporteeType, pagination, delinquentEntries, weeksList, stableTeamid,
					networkCodeId, projectManagerId);
			summary.setTotalRecords(daoManager.getUserUtilizationDao().getUserUtilizationCount(supervisorId, fromDate,
					toDate, searchValue, reporteeType, delinquentEntries, weeksList, stableTeamid, networkCodeId,
					projectManagerId) + utils.size());
		}

		summary.setEnitities(utils);
		return summary;
	}

	@SuppressWarnings("unused")
	private List<String> getNumberofMondays(String d1, String d2) throws Exception {
		DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		Date date1 = format.parse(d1);
		Date date2 = format.parse(d2);
		Calendar c1 = Calendar.getInstance();
		c1.setTime(date1);
		c1.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		Calendar c2 = Calendar.getInstance();
		c2.setTime(date2);
		List<String> mondayDatesList = new ArrayList<String>();
		while (c2.after(c1)) {
			if (c1.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY) {
				mondayDatesList.add(format.format(c1.getTime()));
			}
			c1.add(Calendar.DATE, 1);
		}
		return mondayDatesList;
	}

	@Override
	public List<ResourceEffort> getResourceUtilizationDetails(long supervisorId, String fromDate, String toDate,
			Long selectedEmployee, String reporteeType, boolean showMyRecord) throws Throwable {
		List<ResourceEffort> activityList = daoManager.getUserUtilizationDao()
				.getResourceUtilizationDetails(supervisorId, fromDate, toDate, selectedEmployee, reporteeType);
		return activityList;
	}

	@Override
	public String generateExcel(long supervisorId, Map<String, String> excelColHeaders, String fromDate, String toDate,
			Long searchValue, String reporteeType, String fileName, SummaryResponse<Utilization> res) throws Throwable {

		Map<String, String> shhet1headers = new LinkedHashMap<String, String>();
		Map<String, String> shhet2headers = new LinkedHashMap<String, String>();
		Map<String, String> shhet3headers = new LinkedHashMap<String, String>();
		Map<String, String> shhet4headers = new LinkedHashMap<String, String>();

		shhet1headers.put("SIGNUM", "SIGNUM");
		shhet1headers.put("Name", "Name");
		shhet1headers.put("Project", "Project");
		shhet1headers.put("CHARGED_HOURS", "CHARGED HOURS");
		shhet1headers.put("STATUS", "STATUS");
		shhet1headers.put("WEEKENDING_DATE", "WEEKENDING DATE");
		shhet1headers.put("Application", "Application");

		shhet2headers.put("SIGNUM", "SIGNUM");
		shhet2headers.put("NAME", "NAME");
		shhet2headers.put("Contribution", "Contribution");
		shhet2headers.put("TeamName", "TeamName");

		shhet3headers.put("SIGNUM", "SIGNUM");
		shhet3headers.put("NAME", "NAME");
		shhet3headers.put("Status", "Status");

		shhet4headers.put("SIGNUM", "SIGNUM");
		shhet4headers.put("NAME", "NAME");

		GenericExcel excel = new GenericExcel(fileName, true);

		// List<NetworkCodes> data1 =
		// daoManager.getNetworkCodesDao().getAllUserEffort(fromDate, toDate);

		List<StableTeams> data2 = daoManager.getNetworkCodesDao().getStableTeamsForUser(supervisorId);
		List<StableTeams> data3 = daoManager.getNetworkCodesDao().getNonStableTeamsForUser(supervisorId);
		List<NetworkCodes> data4 = daoManager.getNetworkCodesDao().getUnchargedUserEffort(supervisorId);

		String[][] activityRecords1 = new String[res.getEnitities().size() + 1][shhet1headers.size()];
		String[][] activityRecords2 = new String[data2.size() + 1][shhet2headers.size()];
		String[][] activityRecords3 = new String[data3.size() + 1][shhet3headers.size()];
		String[][] activityRecords4 = new String[data4.size() + 1][shhet4headers.size()];

		if (activityRecords1.length <= 0) {
			activityRecords1 = new String[1][shhet1headers.size()];
		}
		if (activityRecords2.length <= 0) {
			activityRecords2 = new String[1][shhet1headers.size()];
		}
		if (activityRecords3.length <= 0) {
			activityRecords3 = new String[1][shhet1headers.size()];
		}
		if (activityRecords4.length <= 0) {
			activityRecords4 = new String[1][shhet1headers.size()];
		}
		int i = 0;
		for (String d : shhet1headers.values()) {
			activityRecords1[0][i] = d;
			i += 1;
		}
		i = 0;
		for (String d : shhet2headers.values()) {
			activityRecords2[0][i] = d;
			i += 1;
		}
		i = 0;
		for (String d : shhet3headers.values()) {
			activityRecords3[0][i] = d;
			i += 1;
		}
		i = 0;
		for (String d : shhet4headers.values()) {
			activityRecords4[0][i] = d;
			i += 1;
		}

		i = 1;
		for (Utilization s : res.getEnitities()) {
			activityRecords1[i][0] = s.getSignum();
			activityRecords1[i][1] = s.getResourceName();
			activityRecords1[i][2] = s.getNetworkCodeName();
			activityRecords1[i][3] = s.getEffort() + "";
			activityRecords1[i][4] = s.getNwStatus();
			activityRecords1[i][5] = s.getWeekEndingDate();
			activityRecords1[i][6] = s.getApplication();
			i += 1;
		}
		i = 1;
		for (StableTeams s : data2) {
			activityRecords2[i][0] = s.getSignum();
			activityRecords2[i][1] = s.getName();
			activityRecords2[i][2] = s.getValue() + "";
			activityRecords2[i][3] = s.getTeamName();
			i += 1;
		}
		i = 1;
		for (StableTeams s : data3) {
			activityRecords3[i][0] = s.getSignum();
			activityRecords3[i][1] = s.getName();
			activityRecords3[i][2] = s.getStatus();
			i += 1;
		}
		i = 1;
		for (NetworkCodes s : data4) {
			activityRecords4[i][0] = s.getSignum();
			activityRecords4[i][1] = s.getName();

			i += 1;
		}

		excel.createSheettoWorkbook("Charged Hrs 2020 till " + Calendar.getInstance().getWeekYear(), activityRecords1);
		excel.createSheettoWorkbook("User Stable team constributions", activityRecords2);
		excel.createSheettoWorkbook("User with no stable teams", activityRecords3);
		excel.createSheettoWorkbook("Users did not charged in 2 Months", activityRecords4);

		excel.setCustomRowStyleForHeader(0, 0, IndexedColors.GREY_25_PERCENT.index, 15);
		excel.setCustomRowStyleForHeader(1, 0, IndexedColors.GREY_25_PERCENT.index, 15);
		excel.setCustomRowStyleForHeader(2, 0, IndexedColors.GREY_25_PERCENT.index, 15);
		excel.setCustomRowStyleForHeader(3, 0, IndexedColors.GREY_25_PERCENT.index, 15);

		boolean status = excel.writeWorkbook();
		if (!status) {
			logger.error("Error In Generating Excel ", fileName);
		}

		return fileName;
	}

	@Override
	public void sendMail(long supervisorId, String fromDate, String toDate, Long searchValue, String reporteeType,
			String supervisorMail, List<String> weeksList) throws Throwable {
		Map<String, List<String>> toList = new LinkedHashMap<String, List<String>>();
		List<String> resourceNameList = new ArrayList<String>();
		List<String> weekList = null;
		try {
			List<Utilization> utilizationList = daoManager.getUserUtilizationDao().getUserUtilizationSummary(
					supervisorId, fromDate, toDate, searchValue, reporteeType, null, true, weeksList, -1L, null, null);
			for (Utilization u : utilizationList) {
				if ((u.getEmail() != null && !u.getEmail().trim().equals(""))) {
					if ((!u.getEmail().trim().contains("admin") && u.getEmail().trim().contains("verizon"))) {

						if (toList.containsKey(u.getEmail())) {
							toList.get(u.getEmail()).add(u.getWeekEndingDate());
						} else {
							weekList = new ArrayList<String>();
							weekList.add(u.getWeekEndingDate());
							toList.put(u.getEmail(), weekList);
						}

						resourceNameList.add(u.getResourceName());
					}
				}
			}
			if (toList != null && toList.size() > 0) {
				GenericMail.sendMail(supervisorMail, toList, resourceNameList);
			}
		} catch (Throwable e) {
			throw e;
		}
	}

	@Override
	public SummaryResponse<Utilization> getRICOUtilizationSummary(boolean userFlag, Long userId, String fromDate,
			String toDate, Pagination pagination, Long projectManagerId) throws Throwable {
		SimpleDateFormat sdformat = new SimpleDateFormat("MM/dd/yyyy");
		if (StringHelper.isEmpty(fromDate)) {
			fromDate = sdformat.format(new Date());
		}
		if (StringHelper.isEmpty(toDate)) {
			toDate = sdformat.format(new Date());
		}

		String startWeek = Utility.getWeekEnding(fromDate);
		String endWeek = Utility.getWeekEnding(toDate);
		String startDay = (new SimpleDateFormat("EEE")).format(sdformat.parse(fromDate));
		String endDay = (new SimpleDateFormat("EEE")).format(sdformat.parse(toDate));

		SummaryResponse<Utilization> summary = new SummaryResponse<Utilization>();
		summary.setTotalRecords(daoManager.getUserUtilizationDao().getRICOUserUtilizationCount(userFlag, userId,
				startWeek, endWeek, startDay, endDay, projectManagerId));
		summary.setEnitities(daoManager.getUserUtilizationDao().getRICOUserUtilizationSummary(userFlag, userId,
				startWeek, endWeek, startDay, endDay, pagination, projectManagerId));
		return summary;
	}

	@Override
	public String generateRICOUtilizationSummaryExcel(Long userId, String fromDate, String toDate, String fileName,
			Map<String, String> excelColHeaders) throws Throwable {
		SimpleDateFormat sdformat = new SimpleDateFormat("MM/dd/yyyy");
		if (StringHelper.isEmpty(fromDate)) {
			fromDate = sdformat.format(new Date());
		}
		if (StringHelper.isEmpty(toDate)) {
			toDate = sdformat.format(new Date());
		}

		String startWeek = Utility.getWeekEnding(fromDate);
		String endWeek = Utility.getWeekEnding(toDate);
		String startDay = (new SimpleDateFormat("EEE")).format(sdformat.parse(fromDate));
		String endDay = (new SimpleDateFormat("EEE")).format(sdformat.parse(toDate));
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
		List<Utilization> utilizationList = new ArrayList<Utilization>();
		utilizationList = daoManager.getUserUtilizationDao().getRICOUserUtilizationSummary(true, userId, startWeek,
				endWeek, startDay, endDay, null, null);
		GenericExcel excel = new GenericExcel(fileName);
		int rowSize = utilizationList.size();
		String[][] activityRecords = new String[rowSize + 1][colHeaderSize];
		activityRecords[0] = colHeaders;
		int row = 1, col = 0;
		for (Utilization utilizationObj : utilizationList) {
			for (col = 0; col < colHeaders.length; col++) {
				switch (colHeaderKeys[col]) {
				case ExcelHeaderConstants.SIGNUM:
					activityRecords[row][col] = utilizationObj.getSignum();
					break;
				case ExcelHeaderConstants.WEEKENDING:
					activityRecords[row][col] = utilizationObj.getWeekEndingDate();
					break;
				case ExcelHeaderConstants.RESOURCE_NAME:
					activityRecords[row][col] = utilizationObj.getResourceName();
					break;
				case ExcelHeaderConstants.MANAGER:
					activityRecords[row][col] = utilizationObj.getSupervisorName();
					break;
				case ExcelHeaderConstants.NETWORK_ID:
					activityRecords[row][col] = utilizationObj.getNetworkCodeName();
					break;
				case ExcelHeaderConstants.ACTIVITY_CODE:
					activityRecords[row][col] = utilizationObj.getActivityCodeName();
					break;
				case ExcelHeaderConstants.TIME_TYPE:
					activityRecords[row][col] = utilizationObj.getType();
					break;
				case ExcelHeaderConstants.HOURS:
					activityRecords[row][col] = utilizationObj.getEffort() + "";
					break;
				}
			}
			row++;
		}
		excel.writeSheet("Utilization Report", activityRecords);
		excel.setCustomRowStyleForHeader(0, 0, IndexedColors.GREY_40_PERCENT.index, 15);

		boolean status = excel.writeWorkbook();
		if (!status) {
			System.out.println("Error In Generating Excel");
		}

		return fileName;
	}

	@Override
	public String getUnApprovedHours(Long supervisorId) throws Throwable {
		Calendar c = Calendar.getInstance();
		String fromDate = "01/01/" + c.get(Calendar.YEAR);

		String startWeek = Utility.getWeekEnding(fromDate);
		return daoManager.getUserUtilizationDao().getUnApprovedHours(supervisorId, startWeek);
	}

	@Override
	public void saveUserUtilization(List<SupervisorResourceUtilization> dashbordDetails) {
		List<com.egil.pts.dao.domain.SupervisorResourceUtilization> dashbordDetailsDao = new ArrayList<com.egil.pts.dao.domain.SupervisorResourceUtilization>();
		convertModalToDao(dashbordDetails, dashbordDetailsDao);
		daoManager.getUserUtilizationDao().saveUserUtilization(dashbordDetailsDao);
	}

	private void convertModalToDao(List<SupervisorResourceUtilization> dashbordDetails,
			List<com.egil.pts.dao.domain.SupervisorResourceUtilization> dashbordDetailsDao) {
		com.egil.pts.dao.domain.SupervisorResourceUtilization su = null;
		try {
			for (SupervisorResourceUtilization s : dashbordDetails) {
				su = new com.egil.pts.dao.domain.SupervisorResourceUtilization();
				su.setEgiworkingHours(s.getEgiworkingHours());
				su.setManaworkingHours(s.getManaworkingHours());
				su.setLocationId(s.getLocationId());
				su.setLocationName(s.getLocationName());
				su.setMonth(s.getMonth());
				su.setResourceCount((s.getResourceCount() == null) ? 0 : s.getResourceCount());
				su.setUserId(s.getUserId());
				su.setWorkingDays((s.getWorkingdays() == null) ? 0 : s.getWorkingdays());
				su.setYear(s.getYear());
				su.setTargetHrs(s.getTargetHrs());
				dashbordDetailsDao.add(su);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<SupervisorResourceUtilization> getUserUtilization(List<SupervisorResourceUtilization> dashbordDetails,
			Long user, Long selectedYear, Long selectedMonth) {
		dashbordDetails = daoManager.getUserUtilizationDao().getUserUtilization(user, selectedYear, selectedMonth);
		return dashbordDetails;
	}

	@Override
	public void saveUserUtilizationForMonth() {
		daoManager.getUserUtilizationDao().saveUserUtilizationForMonth();
	}

	@Override
	public void saveUserUtilizationForYear() {
		daoManager.getUserUtilizationDao().saveUserUtilizationForYear();
	}

	@Override
	public String generateExcelWFM(Long userId, Map<String, String> excelColHeaders, Date date, Long selectedEmployee,
			Long selectedEmp, String fileName, String showAllWfm, String showAllSubRes, Long stream) {

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
		List<TimesheetActivityWfm> utilizationList = new ArrayList<TimesheetActivityWfm>();
		utilizationList = daoManager.getUserNetworkCodeEffortDao().getEffortDetailsOfWeekWfm(userId, date, "true",
				showAllSubRes, stream);
		GenericExcel excel = new GenericExcel(fileName);
		int rowSize = utilizationList.size();
		String[][] activityRecords = new String[rowSize + 1][colHeaderSize];
		activityRecords[0] = colHeaders;
		int row = 1, col = 0;

		try {
			getWFMWeekTotal(utilizationList);
		} catch (Exception e) {
			e.printStackTrace();
		}

		for (TimesheetActivityWfm utilizationObj : utilizationList) {
			for (col = 0; col < colHeaders.length; col++) {
				switch (colHeaderKeys[col]) {

				case "SIGNUM":
					activityRecords[row][col] = utilizationObj.getUserName().toUpperCase();
					break;
				case "NAME":
					activityRecords[row][col] = utilizationObj.getName();
					break;
				case "WEEKEND_DATE":
					activityRecords[row][col] = utilizationObj.getWeekendingDate() + "";
					break;
				case "MON_HRS_ON":
					activityRecords[row][col] = utilizationObj.getMonHrsOn();
					break;
				case "MON_HRS_OFF":
					activityRecords[row][col] = utilizationObj.getMonHrsOff();
					break;
				case "TUE_HRS_ON":
					activityRecords[row][col] = utilizationObj.getTueHrsOn();
					break;
				case "TUE_HRS_OFF":
					activityRecords[row][col] = utilizationObj.getTueHrsOff();
					break;
				case "WED_HRS_ON":
					activityRecords[row][col] = utilizationObj.getWedHrsOn();
					break;
				case "WED_HRS_OFF":
					activityRecords[row][col] = utilizationObj.getWedHrsOff();
					break;
				case "THU_HRS_ON":
					activityRecords[row][col] = utilizationObj.getThuHrsOn();
					break;
				case "THU_HRS_OFF":
					activityRecords[row][col] = utilizationObj.getThuHrsOff();
					break;
				case "FRI_HRS_ON":
					activityRecords[row][col] = utilizationObj.getFriHrsOn();
					break;
				case "FRI_HRS_OFF":
					activityRecords[row][col] = utilizationObj.getFriHrsOff();
					break;
				case "SAT_HRS_ON":
					activityRecords[row][col] = utilizationObj.getSatHrsOn();
					break;
				case "SAT_HRS_OFF":
					activityRecords[row][col] = utilizationObj.getSatHrsOff();
					break;
				case "SUN_HRS_ON":
					activityRecords[row][col] = utilizationObj.getSunHrsOn();
					break;
				case "SUN_HRS_OFF":
					activityRecords[row][col] = utilizationObj.getSunHrsOff();
					break;
				case "TOTAL_HRS":
					activityRecords[row][col] = utilizationObj.getTotalhrs();
					break;
				}
			}
			row++;
		}
		excel.writeSheet("Utilization Report", activityRecords);
		excel.setCustomRowStyleForHeader(0, 0, IndexedColors.GREY_40_PERCENT.index, 15);

		boolean status = excel.writeWorkbook();
		if (!status) {
			System.out.println("Error In Generating Excel");
		}

		return fileName;
	}

	public static String getWFMWeekTotal(List<TimesheetActivityWfm> activityListToSaveWfm) {
		DecimalFormat df = new DecimalFormat("0.00");
		SimpleDateFormat format = new SimpleDateFormat("HH:mm");

		try {

			for (TimesheetActivityWfm w : activityListToSaveWfm) {
				float total = (0.0f);
				float hrs = 0;
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
					total += total;
				}
				w.setTotalhrs(df.format(total));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return df.format("0.0");
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

	public String generateUserStableExcel(Long userid, Pagination pager, String fromDate, String toDate,
			Long selectedEmployee, String reporteeType, String fileName, boolean all) {

		Map<String, String> shhet1headers = new LinkedHashMap<String, String>();

		shhet1headers.put("SIGNUM", "SIGNUM");
		shhet1headers.put("Name", "Name");
		shhet1headers.put("Project", "Project");
		shhet1headers.put("CHARGED_HOURS", "CHARGED HOURS");
		shhet1headers.put("STATUS", "STATUS");
		shhet1headers.put("WEEKENDING_DATE", "WEEKENDING DATE");
		shhet1headers.put("Application", "Application");

		GenericExcel excel = new GenericExcel(fileName, true);

		List<NetworkCodes> data1 = daoManager.getNetworkCodesDao().getAllUserEffort(fromDate, toDate);

		String[][] activityRecords1 = new String[data1.size() + 1][shhet1headers.size()];

		if (activityRecords1.length <= 0) {
			activityRecords1 = new String[1][shhet1headers.size()];
		}
		int i = 0;
		for (String d : shhet1headers.values()) {
			activityRecords1[0][i] = d;
			i += 1;
		}
		i = 1;
		for (NetworkCodes s : data1) {
			activityRecords1[i][0] = s.getSignum();
			activityRecords1[i][1] = s.getName();
			activityRecords1[i][2] = s.getReleaseName();
			activityRecords1[i][3] = s.getChargedHours() + "";
			activityRecords1[i][4] = s.getNwStatus();
			activityRecords1[i][5] = s.getWeekDate();
			activityRecords1[i][6] = s.getApplication();
			i += 1;
		}
		i = 1;
		excel.createSheettoWorkbook("Charged Hrs  ", activityRecords1);

		excel.setCustomRowStyleForHeader(0, 0, IndexedColors.GREY_25_PERCENT.index, 15);

		boolean status = excel.writeWorkbook();
		if (!status) {
			logger.error("Error In Generating Excel ", fileName);
		}

		return fileName;
	}

	@Override
	public String generateUserStableExcel(SummaryResponse<com.egil.pts.modal.User> summary, String fileName,
			boolean all, boolean stableTeams) {

		Map<String, String> shhet1headers = new LinkedHashMap<String, String>();

		shhet1headers.put("Name", "Name");
		shhet1headers.put("SIGNUM", "SIGNUM");
		shhet1headers.put("Employee Type", "Employee Type");
		shhet1headers.put("Role", "Role");
		shhet1headers.put("Stream", "Stream");
		shhet1headers.put("Supervisor", "Supervisor");
		shhet1headers.put("Status", "Status");
		shhet1headers.put("Location", "Location");
		if (stableTeams) {
			shhet1headers.put("Stable Team Name", "Stable Team Name");
			shhet1headers.put("Stable Team Contribution", "Stable Team Contribution");
		}

		String[][] activityRecords1 = new String[summary.getEnitities().size() + 1][shhet1headers.size()];

		if (activityRecords1.length <= 0) {
			activityRecords1 = new String[1][shhet1headers.size()];
		}

		int i = 0;
		for (String d : shhet1headers.values()) {
			activityRecords1[0][i] = d;
			i += 1;
		}
		i = 1;
		for (com.egil.pts.modal.User s : summary.getEnitities()) {
			activityRecords1[i][0] = s.getName();
			activityRecords1[i][1] = s.getUserName();
			activityRecords1[i][2] = s.getUserType();
			activityRecords1[i][3] = s.getRole();
			activityRecords1[i][4] = s.getStreamName();
			activityRecords1[i][5] = s.getSupervisor();
			activityRecords1[i][6] = s.getStatus();
			activityRecords1[i][7] = s.getLocationName();

			if (stableTeams) {
				activityRecords1[i][8] = s.getStableTeamName();
				activityRecords1[i][9] = s.getStableContribution() + "";
			}
			i += 1;
		}

		GenericExcel excel = new GenericExcel(fileName, true);

		excel.createSheettoWorkbook("Resources  ", activityRecords1);

		excel.setCustomRowStyleForHeader(0, 0, IndexedColors.GREY_25_PERCENT.index, 15);

		boolean status = excel.writeWorkbook();
		if (!status) {
			logger.error("Error In Generating Excel ", fileName);
		}

		return fileName;

	}
}