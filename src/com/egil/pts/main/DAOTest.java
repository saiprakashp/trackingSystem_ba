/**
 * 
 */
package com.egil.pts.main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.SharedSessionContract;
import org.hibernate.cfg.Configuration;
import org.hibernate.transform.Transformers;
import org.hibernate.type.DateType;
import org.hibernate.type.DoubleType;
import org.hibernate.type.FloatType;
import org.hibernate.type.LongType;
import org.hibernate.type.StringType;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.egil.pts.modal.TimesheetActivityWfm;
import com.egil.pts.modal.UserCapacity;
import com.egil.pts.util.Utility;

public class DAOTest {
	private static Session session;
	private static SessionFactory sessionFactory = null;
	static {
		try {
			sessionFactory = new Configuration().configure().buildSessionFactory();

		} catch (Throwable ex) {
			System.err.println("Initial SessionFactory creation failed." + ex);
			throw new ExceptionInInitializerError(ex);
		}
	}

	public static void main(String[] args) {
		DecimalFormat dformat = new DecimalFormat("##.00");
		int startYear = Calendar.getInstance().getWeekYear();
		List<UserCapacity> userCapacity = new ArrayList<UserCapacity>();
		Calendar c = Calendar.getInstance();
		int currentYear = 2020;
		try {

			for (int j = 0; j <= 11; j++) {
				session = sessionFactory.openSession();

				Calendar gc = Calendar.getInstance();
				gc.set(Calendar.YEAR, currentYear);
				gc.set(Calendar.MONTH, j);
				gc.set(Calendar.DAY_OF_MONTH, 1);
				Date monthStart = gc.getTime();
				gc = Calendar.getInstance();
				gc.set(Calendar.YEAR, currentYear);
				gc.set(Calendar.MONTH, j);
				gc.set(Calendar.DAY_OF_MONTH, gc.getActualMaximum(Calendar.DAY_OF_MONTH));

				Date monthEnd = gc.getTime();
				SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
				String startWeek = getWeekEnding(format.format(monthStart));
				String endWeek = getWeekEnding(format.format(monthEnd));
				String startDay = (new SimpleDateFormat("EEE")).format(monthStart);
				String endDay = (new SimpleDateFormat("EEE")).format(monthEnd);
				String month = (new SimpleDateFormat("MMMM")).format(monthStart);

				/*
				 * System.out.println(startDay + " " + endDay + " " + startWeek + " " + endWeek
				 * + " " + month + " " + currentYear + " ");
				 */
				getMonthlyNCUsageByUser(startDay, endDay, startWeek, endWeek, null, month, currentYear, userCapacity,
						null);
				session.close();
			}

		} catch (Throwable th) {
			th.printStackTrace();
		} finally {
			sessionFactory.close();
		}
		Double d = 0.0d;
		for (UserCapacity s : userCapacity) {
			d += s.getSUMMATION();
		}
	}

	private static String getWeekEnding(String selectedDate) {
		Date weekEnd = null;
		String weekEndStr = "";
		try {
			// Get calendar set to current date and time
			Calendar c = Calendar.getInstance();
			DateFormat f = new SimpleDateFormat("yyyy-MM-dd");
			// Get calendar set to current date and time
			DateFormat weekEndFormat = new SimpleDateFormat("dd/MM/yyyy");
			if (selectedDate != null && !selectedDate.equals("")) {
				c.setTime(weekEndFormat.parse(selectedDate));
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

	private static void getMonthlyNCUsageByUser(String startDay, String endDay, String startWeek, String endWeek,
			Long userId, String month, int year, List<UserCapacity> userCapacity, Long ncId) {

		String qryStr = "select  nc.id,nc.TFSEpic,(nc.ORIGINAL_DESIGN_LOE +nc.ORIGINAL_DEV_LOE	+nc.ORIGINAL_TEST_LOE +nc.ORIGINAL_PROJ_MGMT_LOE +nc.ORIGINAL_IMPL_LOE) TOTALCAPACITY ,'"
				+ month + "' MONTH, concat(nc.RELEASE_ID, ' - ',nc.RELEASE_NAME) NETWORKCODE, :summation "
				+ ",  t.WEEKENDING_DATE  "
				+ " from PTS_NETWORK_CODES nc inner  join PTS_USER_TIMESHEET t on nc.id= t.network_code_id  "
				+ "   where :whereClause and nc.STATUS not in ('DELETED','Cancelled','Implemented','IN_ACTIVE')"
				+ " group by   t.network_code_id";

		String startDayQryStr = "";
		String endDayQryStr = "";

		switch (startDay) {
		case "Mon":
			startDayQryStr = "ROUND(sum(t.mon_hrs)+ sum(t.tue_hrs) + sum(t.wed_hrs) + sum(t.thu_hrs)+ sum(t.fri_hrs)+ sum(t.sat_hrs)+ sum(t.sun_hrs)) as SUMMATION ";
			break;
		case "Tue":
			startDayQryStr = "ROUND(sum(t.tue_hrs) + sum(t.wed_hrs) + sum(t.thu_hrs)+ sum(t.fri_hrs)+ sum(t.sat_hrs)+ sum(t.sun_hrs)) as SUMMATION ";
			break;
		case "Wed":
			startDayQryStr = "ROUND(sum(t.wed_hrs) + sum(t.thu_hrs)+ sum(t.fri_hrs)+ sum(t.sat_hrs)+ sum(t.sun_hrs)) as SUMMATION ";
			break;
		case "Thu":
			startDayQryStr = "ROUND(sum(t.thu_hrs)+ sum(t.fri_hrs)+ sum(t.sat_hrs)+ sum(t.sun_hrs)) as SUMMATION ";
			break;
		case "Fri":
			startDayQryStr = "ROUND(sum(t.fri_hrs)+ sum(t.sat_hrs)+ sum(t.sun_hrs)) as SUMMATION ";
			break;
		case "Sat":
			startDayQryStr = "ROUND(sum(t.sat_hrs)+ sum(t.sun_hrs)) as SUMMATION ";
			break;
		case "Sun":
			startDayQryStr = "ROUND(sum(t.sun_hrs)) as SUMMATION ";
			break;
		}

		switch (endDay) {
		case "Mon":
			endDayQryStr = "ROUND(sum(t.mon_hrs)) as SUMMATION ";
			break;
		case "Tue":
			endDayQryStr = "ROUND(sum(t.mon_hrs)+ sum(t.tue_hrs)) as SUMMATION ";
			break;
		case "Wed":
			endDayQryStr = "ROUND(sum(t.mon_hrs)+ sum(t.tue_hrs) + sum(t.wed_hrs)) as SUMMATION ";
			break;
		case "Thu":
			endDayQryStr = "ROUND(sum(t.mon_hrs)+ sum(t.tue_hrs) + sum(t.wed_hrs) + sum(t.thu_hrs)) as SUMMATION ";
			break;
		case "Fri":
			endDayQryStr = "ROUND(sum(t.mon_hrs)+ sum(t.tue_hrs) + sum(t.wed_hrs) + sum(t.thu_hrs)+ sum(t.fri_hrs)) as SUMMATION ";
			break;
		case "Sat":
			endDayQryStr = "ROUND(sum(t.mon_hrs)+ sum(t.tue_hrs) + sum(t.wed_hrs) + sum(t.thu_hrs)+ sum(t.fri_hrs)+ sum(t.sat_hrs)) as SUMMATION ";
			break;
		case "Sun":
			endDayQryStr = "ROUND(sum(t.mon_hrs)+ sum(t.tue_hrs) + sum(t.wed_hrs) + sum(t.thu_hrs)+ sum(t.fri_hrs)+ sum(t.sat_hrs)+ sum(t.sun_hrs)) as SUMMATION ";
			break;
		}

		String stQryStr = qryStr.replaceAll(":summation", startDayQryStr).replaceAll(":whereClause",
				" t.WEEKENDING_DATE = '" + startWeek + "' " + (userId == null ? " " : " and t.user_id =" + userId)
						+ (ncId == null ? " " : " and t.network_code_id =" + ncId));
		String endQryStr = qryStr.replaceAll(":summation", endDayQryStr).replaceAll(":whereClause",
				" t.WEEKENDING_DATE = '" + endWeek + "' " + (userId == null ? " " : " and t.user_id =" + userId)
						+ (ncId == null ? " " : " and t.network_code_id =" + ncId));
		String midQryStr = qryStr.replaceAll(":summation",
				"(sum(t.mon_hrs)+ sum(t.tue_hrs) + sum(t.wed_hrs) + sum(t.thu_hrs)+ sum(t.fri_hrs)+ sum(t.sat_hrs)+ sum(t.sun_hrs)) as SUMMATION ")
				.replaceAll(":whereClause",
						" t.WEEKENDING_DATE > '" + startWeek + "' and t.WEEKENDING_DATE < '" + endWeek + "' "
								+ (userId == null ? " " : " and t.user_id =" + userId)
								+ (ncId == null ? " " : " and t.network_code_id =" + ncId));

		String finalStr = " " + stQryStr + " union all " + midQryStr + " union all " + endQryStr + " ";
		Query query = getSession().createSQLQuery(finalStr).addScalar("id", new LongType())
				.addScalar("TFSEpic", new LongType()).addScalar("MONTH", new StringType())
				.addScalar("NETWORKCODE", new StringType()).addScalar("SUMMATION", new DoubleType());

		List<UserCapacity> tmpUserCapacity = query.setResultTransformer(Transformers.aliasToBean(UserCapacity.class))
				.list();
		userCapacity.addAll(tmpUserCapacity);

	}

	private static void getMonthlyNCUsageByUserBKP(String startDay, String endDay, String startWeek, String endWeek,
			Long userId, String month, int year, List<UserCapacity> userCapacity, Long ncId) {

		String qryStr = "select distinct u.name, CASE WHEN nc.network_code_id is null THEN concat(nc.RELEASE_ID, ' - ',nc.RELEASE_NAME) else concat(nc.network_code_id, ' - ' , nc.RELEASE_ID, ' - ',nc.RELEASE_NAME) END as NETWORKCODE, "
				+ " concat(ac.ACTIVITY_CODE_ID, ' - ' ,ac.ACTIVITY_CODE_NAME) as ACTIVITYCODE, :summation "
				+ ",  t.WEEKENDING_DATE, t.user_id, t.network_code_id, (select name from PTS_USER where id=us.SUPERVISOR_ID) as Supervisor "
				+ " from PTS_NETWORK_CODES nc inner  join PTS_USER_TIMESHEET t on nc.id= t.network_code_id  "
				+ " inner  join PTS_ACTIVITY_CODES ac on ac.id=t.activity_code_id "
				+ " inner  join PTS_USER u on u.id=t.user_id "
				+ " inner  join PTS_USER_SUPERVISOR us on u.id=us.user_id " + " where :whereClause "
				+ " group by t.user_id, t.network_code_id, t.WEEKENDING_DATE";

		String startDayQryStr = "";
		String endDayQryStr = "";

		switch (startDay) {
		case "Mon":
			startDayQryStr = "(sum(t.mon_hrs)+ sum(t.tue_hrs) + sum(t.wed_hrs) + sum(t.thu_hrs)+ sum(t.fri_hrs)+ sum(t.sat_hrs)+ sum(t.sun_hrs)) as SUMMATION ";
			break;
		case "Tue":
			startDayQryStr = "(sum(t.tue_hrs) + sum(t.wed_hrs) + sum(t.thu_hrs)+ sum(t.fri_hrs)+ sum(t.sat_hrs)+ sum(t.sun_hrs)) as SUMMATION ";
			break;
		case "Wed":
			startDayQryStr = "(sum(t.wed_hrs) + sum(t.thu_hrs)+ sum(t.fri_hrs)+ sum(t.sat_hrs)+ sum(t.sun_hrs)) as SUMMATION ";
			break;
		case "Thu":
			startDayQryStr = "(sum(t.thu_hrs)+ sum(t.fri_hrs)+ sum(t.sat_hrs)+ sum(t.sun_hrs)) as SUMMATION ";
			break;
		case "Fri":
			startDayQryStr = "(sum(t.fri_hrs)+ sum(t.sat_hrs)+ sum(t.sun_hrs)) as SUMMATION ";
			break;
		case "Sat":
			startDayQryStr = "(sum(t.sat_hrs)+ sum(t.sun_hrs)) as SUMMATION ";
			break;
		case "Sun":
			startDayQryStr = "(sum(t.sun_hrs)) as SUMMATION ";
			break;
		}

		switch (endDay) {
		case "Mon":
			endDayQryStr = "(sum(t.mon_hrs)) as SUMMATION ";
			break;
		case "Tue":
			endDayQryStr = "(sum(t.mon_hrs)+ sum(t.tue_hrs)) as SUMMATION ";
			break;
		case "Wed":
			endDayQryStr = "(sum(t.mon_hrs)+ sum(t.tue_hrs) + sum(t.wed_hrs)) as SUMMATION ";
			break;
		case "Thu":
			endDayQryStr = "(sum(t.mon_hrs)+ sum(t.tue_hrs) + sum(t.wed_hrs) + sum(t.thu_hrs)) as SUMMATION ";
			break;
		case "Fri":
			endDayQryStr = "(sum(t.mon_hrs)+ sum(t.tue_hrs) + sum(t.wed_hrs) + sum(t.thu_hrs)+ sum(t.fri_hrs)) as SUMMATION ";
			break;
		case "Sat":
			endDayQryStr = "(sum(t.mon_hrs)+ sum(t.tue_hrs) + sum(t.wed_hrs) + sum(t.thu_hrs)+ sum(t.fri_hrs)+ sum(t.sat_hrs)) as SUMMATION ";
			break;
		case "Sun":
			endDayQryStr = "(sum(t.mon_hrs)+ sum(t.tue_hrs) + sum(t.wed_hrs) + sum(t.thu_hrs)+ sum(t.fri_hrs)+ sum(t.sat_hrs)+ sum(t.sun_hrs)) as SUMMATION ";
			break;
		}

		String stQryStr = qryStr.replaceAll(":summation", startDayQryStr).replaceAll(":whereClause",
				" t.WEEKENDING_DATE = '" + startWeek + "' " + (userId == null ? " " : " and t.user_id =" + userId)
						+ (ncId == null ? " " : " and t.network_code_id =" + ncId));
		String endQryStr = qryStr.replaceAll(":summation", endDayQryStr).replaceAll(":whereClause",
				" t.WEEKENDING_DATE = '" + endWeek + "' " + (userId == null ? " " : " and t.user_id =" + userId)
						+ (ncId == null ? " " : " and t.network_code_id =" + ncId));
		String midQryStr = qryStr.replaceAll(":summation",
				"(sum(t.mon_hrs)+ sum(t.tue_hrs) + sum(t.wed_hrs) + sum(t.thu_hrs)+ sum(t.fri_hrs)+ sum(t.sat_hrs)+ sum(t.sun_hrs)) as SUMMATION ")
				.replaceAll(":whereClause",
						" t.WEEKENDING_DATE > '" + startWeek + "' and t.WEEKENDING_DATE < '" + endWeek + "' "
								+ (userId == null ? " " : " and t.user_id =" + userId)
								+ (ncId == null ? " " : " and t.network_code_id =" + ncId));

		String finalStr = "select tmp.name as USERNAME, tmp.NETWORKCODE as NETWORKCODE, tmp.ACTIVITYCODE as ACTIVITYCODE, sum(tmp.SUMMATION) as CHARGEDHRS , '"
				+ month + "' as MONTH, '" + year + "' as YEAR, " + " tmp.Supervisor from (" + stQryStr + " union all "
				+ midQryStr + " union all " + endQryStr
				+ " ) tmp where tmp.SUMMATION <> 0 group by tmp.user_id, tmp.network_code_id, tmp.ACTIVITYCODE order by tmp.name, tmp.WEEKENDING_DATE";
		Query query = getSession().createSQLQuery(finalStr).addScalar("USERNAME", new StringType())
				.addScalar("CHARGEDHRS", new FloatType()).addScalar("MONTH", new StringType())
				.addScalar("YEAR", new StringType()).addScalar("NETWORKCODE", new StringType())
				.addScalar("ACTIVITYCODE", new StringType());

		List<UserCapacity> tmpUserCapacity = query.setResultTransformer(Transformers.aliasToBean(UserCapacity.class))
				.list();
		userCapacity.addAll(tmpUserCapacity);

	}

	private static Session getSession() {
		return session;
	}

}