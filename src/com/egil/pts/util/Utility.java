package com.egil.pts.util;

import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

public class Utility {
	public static Map<String, Integer> monthToNoMap = new HashMap<>();
	public static Map<Integer, String> monthsMap = new HashMap<>();
	public static Map<Integer, String> dayMap = new HashMap<>();
	static {
		monthToNoMap.put("Jan", 1);
		monthToNoMap.put("Feb", 2);
		monthToNoMap.put("Mar", 3);
		monthToNoMap.put("Apr", 4);
		monthToNoMap.put("May", 5);
		monthToNoMap.put("Jun", 6);
		monthToNoMap.put("Jul", 7);
		monthToNoMap.put("Aug", 8);
		monthToNoMap.put("Sep", 9);
		monthToNoMap.put("Oct", 10);
		monthToNoMap.put("Nov", 11);
		monthToNoMap.put("Dec", 12);

		monthsMap.put(1, "Jan");
		monthsMap.put(2, "Feb");
		monthsMap.put(3, "Mar");
		monthsMap.put(4, "Apr");
		monthsMap.put(5, "May");
		monthsMap.put(6, "Jun");
		monthsMap.put(7, "Jul");
		monthsMap.put(8, "Aug");
		monthsMap.put(9, "Sep");
		monthsMap.put(10, "Oct");
		monthsMap.put(11, "Nov");
		monthsMap.put(12, "Dec");

		dayMap.put(1, "MON");
		dayMap.put(2, "TUE");
		dayMap.put(3, "WED");
		dayMap.put(4, "THU");
		dayMap.put(5, "FRI");
		dayMap.put(6, "SAT");
		dayMap.put(7, "SUN");

	}

	public static boolean isStringEmpty(String data) {
		if (data == null) {
			return true;
		}
		return (data != null && data.isEmpty());

	}

	public static List<Long> getListFromCommaSeparated(String commaSeparatedString) {

		List<Long> list = null;
		StringTokenizer st = null;

		if (commaSeparatedString == null) {
			return null;
		}

		st = new StringTokenizer(commaSeparatedString, ",");
		list = new ArrayList<Long>();

		while (st.hasMoreTokens()) {
			String s = st.nextToken().trim();
			if (s != null && !s.equals("")) {
				list.add(Long.valueOf(s));
			}
		}

		return list;

	}

	public static String generateRandomShortString(int length) {
		char[] pw = new char[length];
		int c = 'A';
		int r1 = 0;
		for (int i = 0; i < length; i++) {
			r1 = (int) (Math.random() * 3);
			switch (r1) {
			case 0:
				c = '0' + (int) (Math.random() * 10);
				break;
			case 1:
				c = 'a' + (int) (Math.random() * 26);
				break;
			case 2:
				c = 'A' + (int) (Math.random() * 26);
				break;
			}
			pw[i] = (char) c;
		}
		return new String(pw);
	}

	public static String getWeekEnding(String selectedDate) {
		Date weekEnd = null;
		String weekEndStr = "";
		try {
			// Get calendar set to current date and time
			Calendar c = Calendar.getInstance();
			DateFormat f = new SimpleDateFormat("yyyy-MM-dd");
			// Get calendar set to current date and time
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
			weekEndStr = f.format(weekEnd);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return weekEndStr;
	}

	public static void getMonths(int year, Map<String, Integer> monthMap) {
		String[] shortMonths = new DateFormatSymbols().getShortMonths();
		String[] tempMonths = new String[new Date().getMonth() + 1];
		for (int i = 0; i <= new Date().getMonth(); i++) {
			tempMonths[i] = new String(shortMonths[i]);
		}
		int i = 0;
		int noOfWorkingDays = 0;
		Calendar c = new GregorianCalendar();
		c.set(Calendar.YEAR, year);
		for (String shortMonth : tempMonths) {
			if (shortMonth != null && !shortMonth.equals("")) {
				noOfWorkingDays = 0;
				c.set(Calendar.MONTH, i);
				c.set(Calendar.DAY_OF_MONTH, 1);
				i++;
				int days = c.getActualMaximum(Calendar.DAY_OF_MONTH);
				for (int j = 1; j <= days; j++) {
					c.set(Calendar.DAY_OF_MONTH, j);
					if (!(c.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY
							|| c.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY)) {
						noOfWorkingDays++;
					}
				}

				monthMap.put(shortMonth, noOfWorkingDays);
			}
		}
	}

	public static Date getWeekEndingDate(String selectedDate) {
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

	public static String getDateAmPMVal(String val) {
		String t = "0.00";
		if (val.toLowerCase().contains("am")) {
			t = ((val.toLowerCase().split("am")[0].replace(":", ".")));
		} else if (val.toLowerCase().contains("pm")) {
			t = ((val.toLowerCase().split("pm")[0].replace(":", ".")));
		}
		return t;
	}
}
