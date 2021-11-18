package com.egil.pts.actions;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.apache.struts2.interceptor.SessionAware;
import org.apache.struts2.util.ServletContextAware;
import org.hibernate.internal.util.StringHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.egil.pts.constants.ExcelHeaderConstants;
import com.egil.pts.modal.TransactionInfo;
import com.egil.pts.service.common.ServiceManager;
import com.opensymphony.xwork2.ActionSupport;

@Controller("ptsAction")
@Scope("prototype")
public class PTSAction extends ActionSupport
		implements ServletContextAware, ServletRequestAware, ServletResponseAware, SessionAware {
	protected Logger logger = LogManager.getLogger("PTS_SCHEDULING");
	private static final long serialVersionUID = 1L;

	@Autowired(required = true)
	@Qualifier("serviceManager")
	protected ServiceManager serviceManager;
	protected static final String INITIAL = "initial";
	protected ServletContext servletContext;
	protected HttpServletRequest request;
	protected HttpServletResponse response;
	protected HttpSession sessionCtx;

	protected Long selectedAccountId;
	protected String id;
	protected String selectedWeekEndingDate;
	protected String managerId;
	protected String selectedDate;
	protected String selectedToDate;
	protected String menuClick;
	protected Date fromDate;
	protected Date toDate;
	protected Long selectedYear;
	protected String selectedMonth;
	protected String message = "";
	private Map<Long,Long> yearMap = new HashMap<Long, Long>();
	protected String selectedHiddenDate;

	public Map<String, Object> session;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Map<String, Object> getSession() {
		return session;
	}

	public void setSession(Map<String, Object> session) {
		this.session = session;
	}

	public String getMenuClick() {
		return menuClick;
	}

	public void setMenuClick(String menuClick) {
		this.menuClick = menuClick;
	}

	protected void setTransactionInfo(TransactionInfo obj) {
		obj.setCreatedBy("");
		obj.setUpdatedBy("");
		obj.setCreatedDate(new Date());
		obj.setUpdatedDate(new Date());
	}

	public String getSelectedDate() {
		return selectedDate;
	}

	public void setSelectedDate(String selectedDate) {
		this.selectedDate = selectedDate;
	}

	public String getSelectedToDate() {
		return selectedToDate;
	}

	public void setSelectedToDate(String selectedToDate) {
		this.selectedToDate = selectedToDate;
	}

	public List<String> getDaysinAWeek() {
		List<String> daysinAWeek = new ArrayList<String>();
		try {
			// Get calendar set to current date and time
			DateFormat format = new SimpleDateFormat("MM/dd/yyyy");
			Calendar c = Calendar.getInstance();
			if (selectedDate != null && !selectedDate.equals("")) {
				c.setTime(format.parse(selectedDate));
			}
			// Set the calendar to monday of the current week
			c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);

			// Print dates of the current week starting on Monday
			// DateFormat df = new SimpleDateFormat("EEEE dd/MM/yyyy");
			DateFormat df = new SimpleDateFormat("EEE MM/dd");
			for (int i = 0; i < 7; i++) {
				daysinAWeek.add(df.format(c.getTime()));
				c.add(Calendar.DATE, 1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return daysinAWeek;
	}

	public List<String> getDaysinAWeekWFM() {
		List<String> daysinAWeek = new ArrayList<String>();
		try {
			// Get calendar set to current date and time
			DateFormat format = new SimpleDateFormat("MM/dd/yyyy");
			Calendar c = Calendar.getInstance();
			if (selectedDate != null && !selectedDate.equals("")) {
				c.setTime(format.parse(selectedDate));
			}
			// Set the calendar to monday of the current week
			c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);

			// Print dates of the current week starting on Monday
			// DateFormat df = new SimpleDateFormat("EEEE dd/MM/yyyy");
			DateFormat df = new SimpleDateFormat("EEE MM/dd");
			for (int i = 0; i < 7; i++) {
				daysinAWeek.add(df.format(c.getTime()) + " IN");
				daysinAWeek.add(df.format(c.getTime()) + " OUT");
				c.add(Calendar.DATE, 1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return daysinAWeek;
	}

	public Date getWeekEnding(String selectedDate) {
		Date weekEnd = null;
		try {
			// Get calendar set to current date and time
			Calendar c = Calendar.getInstance();
			DateFormat format = new SimpleDateFormat("MM/dd/yyyy");
			DateFormat weekEndFormat = new SimpleDateFormat("dd/MM/yyyy");
			if (selectedDate != null && !selectedDate.equals("")) {
				c.setTime(format.parse(selectedDate));
			}

			if (c.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
				c.add(Calendar.DATE, -1);
			}
			// Set the calendar to Friday of the current week
			c.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);

			// Print dates of the current week starting on Monday
			// DateFormat df = new SimpleDateFormat("EEEE dd/MM/yyyy");
			weekEnd = weekEndFormat.parse(weekEndFormat.format(c.getTime()));

		} catch (Exception e) {
			e.printStackTrace();
		}
		return weekEnd;
	}

	public String getWeekEndingString(String selectedDate) {
		String weekEnd = null;
		try {
			// Get calendar set to current date and time
			Calendar c = Calendar.getInstance();
			DateFormat format = new SimpleDateFormat("MM/dd/yyyy");
			DateFormat weekEndFormat = new SimpleDateFormat("yyyy-MM-dd");
			if (selectedDate != null && !selectedDate.equals("")) {
				c.setTime(format.parse(selectedDate));
			}

			if (c.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
				c.add(Calendar.DATE, -1);
			}
			// Set the calendar to Friday of the current week
			c.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);

			// Print dates of the current week starting on Monday
			// DateFormat df = new SimpleDateFormat("EEEE dd/MM/yyyy");
			weekEnd = (weekEndFormat.format(c.getTime()));

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return weekEnd;
	}

	public Date getStartWeekEnding() {
		Date weekEnd = null;
		try {
			DateFormat weekEndFormat = new SimpleDateFormat("dd/MM/yyyy");
			Calendar cal = Calendar.getInstance();
			cal.setTime(new Date());
			cal.add(Calendar.WEEK_OF_YEAR, -1);
			cal.set(Calendar.DAY_OF_WEEK, 7);
			weekEnd = weekEndFormat.parse(weekEndFormat.format(cal.getTime()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return weekEnd;
	}

	public Date getWeekStarting(String selectedDate) {
		Date weekEnd = null;
		try {
			// Get calendar set to current date and time
			Calendar c = Calendar.getInstance();
			DateFormat format = new SimpleDateFormat("MM/dd/yyyy");
			DateFormat weekEndFormat = new SimpleDateFormat("dd/MM/yyyy");
			if (selectedDate != null && !selectedDate.equals("")) {
				c.setTime(format.parse(selectedDate));
			}

			if (c.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
				c.add(Calendar.DATE, -1);
			}
			// Set the calendar to Friday of the current week
			c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);

			// Print dates of the current week starting on Monday
			// DateFormat df = new SimpleDateFormat("EEEE dd/MM/yyyy");
			weekEnd = weekEndFormat.parse(weekEndFormat.format(c.getTime()));

		} catch (Exception e) {
			e.printStackTrace();
		}
		return weekEnd;
	}

	public String getPeriodOfWeek() {
		String periodOfWeek = "";
		try {
			// Get calendar set to current date and time
			Calendar c = Calendar.getInstance();
			DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
			DateFormat format = new SimpleDateFormat("yyyy-dd-MM");
			if (selectedDate != null && !selectedDate.equals("")) {
				c.setTime(format.parse(format.format(df.parse(selectedDate))));
			}
			c.setFirstDayOfWeek(Calendar.MONDAY);
			c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);

			periodOfWeek = format.format(c.getTime());

			periodOfWeek = periodOfWeek + " to ";
			c.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);

			periodOfWeek = periodOfWeek + format.format(c.getTime());

		} catch (Exception e) {
			e.printStackTrace();
		}
		return periodOfWeek;
	}

	public List<String> getWeekList() {
		List<String> weekList = new ArrayList<String>();
		String weekStart = "";
		try {
			Calendar c = Calendar.getInstance();
			DateFormat format = new SimpleDateFormat("MM/dd/yyyy");
			DateFormat weekEndFormat = new SimpleDateFormat("dd/MM/yyyy");
			if (selectedDate != null && !selectedDate.equals("")) {
				c.setTime(format.parse(selectedDate));
			}
			c.setFirstDayOfWeek(Calendar.MONDAY);
			c.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
			weekStart = weekEndFormat.format(c.getTime());
			weekList.add(weekStart);
			for (int i = 0; i < 5; i++) {
				c.add(Calendar.DATE, 7);
				c.setFirstDayOfWeek(Calendar.SUNDAY);
				weekStart = weekEndFormat.format(c.getTime());
				weekList.add(weekStart);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return weekList;
	}

	public List<String> getWeekListBetweenDates() {
		List<String> weekList = new ArrayList<String>();
		String weekStart = "";
		try {
			Calendar c = Calendar.getInstance();
			DateFormat format = new SimpleDateFormat("MM/dd/yyyy");
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			if (selectedDate != null && !selectedDate.equals("")) {
				c.setTime(format.parse(selectedDate));
			}
			String toDate = df.format(getWeekEnding(selectedToDate));
			// c.setFirstDayOfWeek(Calendar.MONDAY);
			if (c.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
				c.add(Calendar.DATE, -1);
			}
			c.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);
			weekStart = df.format(c.getTime());
			weekList.add(weekStart);
			while (true) {
				if (weekStart.equalsIgnoreCase(toDate)) {
					break;
				}
				c.add(Calendar.DATE, 7);
				if (c.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
					c.add(Calendar.DATE, -1);
				}
				// c.setFirstDayOfWeek(Calendar.SUNDAY);
				weekStart = df.format(c.getTime());
				weekList.add(weekStart);

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return weekList;
	}

	@Override
	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}

	@Override
	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
		this.sessionCtx = request.getSession();
	}

	@Override
	public void setServletResponse(HttpServletResponse response) {
		this.response = response;
	}

	public String getSelectedWeekEndingDate() {
		return selectedWeekEndingDate;
	}

	public void setSelectedWeekEndingDate(String selectedWeekEndingDate) {
		this.selectedWeekEndingDate = selectedWeekEndingDate;
	}

	public Date getFromDate() {
		try {
			DateFormat format = new SimpleDateFormat("MM/dd/yyyy");
			if (StringHelper.isNotEmpty(selectedDate))
				fromDate = format.parse(selectedDate);
			else
				fromDate = format.parse(format.format(new Date()));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return fromDate;
	}

	public Date getToDate() {
		try {
			DateFormat format = new SimpleDateFormat("MM/dd/yyyy");
			if (StringHelper.isNotEmpty(selectedToDate))
				toDate = format.parse(selectedToDate);
			else
				toDate = format.parse(format.format(new Date()));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return toDate;
	}

	public Date getStartDateOfWeek() {
		try {
			DateFormat format = new SimpleDateFormat("MM/dd/yyyy");
			if (StringHelper.isNotEmpty(selectedDate))
				fromDate = format.parse(selectedDate);
			else
				fromDate = format.parse(format.format(getWeekStarting("")));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return fromDate;
	}

	public Date getEndDateOfWeek() {
		try {
			DateFormat format = new SimpleDateFormat("MM/dd/yyyy");
			if (StringHelper.isNotEmpty(selectedToDate))
				toDate = format.parse(selectedToDate);
			else
				toDate = format.parse(format.format(getWeekEnding("")));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return toDate;
	}

	protected void pushFileToClient(String url, String fileName) throws IOException {
		if (fileName.contains(".xlsx"))
			response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
		else
			response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-disposition", "attachment;filename=" + fileName);
		ServletOutputStream os = response.getOutputStream();
		File downloadFile = new File(url);
		/* os.write(FileUploadHelper.convertFileToByteArray(downloadFile)); */
		os.write(convertFileToByteArray(downloadFile));
		os.flush();
	}

	public byte[] convertFileToByteArray(File file) throws IOException {
		return FileUtils.readFileToByteArray(file);
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

	public String getFileName(String filePath) {
		String fileName = new File(filePath).getName();
		return fileName;
	}

	protected void clearSession(String... vars) {
		for (String var : vars) {
			session.remove(var);
		}
	}

	public String getManagerId() {
		return managerId;
	}

	public void setManagerId(String managerId) {
		this.managerId = managerId;
	}

	public Long getSelectedYear() {
		if (selectedYear == null || selectedYear == -1) {
			Calendar c = Calendar.getInstance();
			selectedYear = Long.valueOf(c.get(Calendar.YEAR));
		}
		return selectedYear;
	}

	public void setSelectedYear(Long selectedYear) {
		this.selectedYear = selectedYear;
	}

	public List<Long> getYearList() {
		List<Long> yearList = new ArrayList<Long>();
		int startYear = 2020;
		Calendar c = Calendar.getInstance();
		int tempYear = c.get(Calendar.YEAR);
		int endYear = tempYear + 1;

		for (int i = startYear; i <= endYear; i++) {
			yearList.add(Long.parseLong(i + ""));
		}

		return yearList;
	}

	public List<String> getMonthList() {
		String[] shortMonths = new DateFormatSymbols().getShortMonths();
		List<String> monthList = new ArrayList<String>();
		for (String month : shortMonths) {
			if (month != null && !month.equals("")) {
				monthList.add(month);
			}
		}
		return monthList;
	}

	public List<Long> getReportYearList() {
		List<Long> yearList = new ArrayList<Long>();

		Calendar c = Calendar.getInstance();
		int startYear = c.get(Calendar.YEAR);
		int endYear = c.get(Calendar.YEAR) + 5;

		for (int i = startYear; i <= endYear; i++) {
			yearList.add(Long.parseLong(i + ""));
		}

		return yearList;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getSelectedHiddenDate() {
		return selectedHiddenDate;
	}

	public void setSelectedHiddenDate(String selectedHiddenDate) {
		this.selectedHiddenDate = selectedHiddenDate;
	}

	public String getSelectedMonth() {
		return selectedMonth;
	}

	public void setSelectedMonth(String selectedMonth) {
		this.selectedMonth = selectedMonth;
	}

	public Long getSelectedAccountId() {
		return selectedAccountId;
	}

	public void setSelectedAccountId(Long selectedAccountId) {
		this.selectedAccountId = selectedAccountId;
	}

	public Map<Long,Long> getYearMap() {
		
		long current=Calendar.getInstance().getWeekYear();
			for(long old=2018L;old<=current;old++ ) {
				yearMap.put(old, old);
			}
		
		return yearMap;
	}

	public void setYearMap(Map<Long,Long> yearMap) {
		this.yearMap = yearMap;
	}

}
