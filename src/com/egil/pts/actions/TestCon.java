package com.egil.pts.actions;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.hssf.util.HSSFColor;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.transform.Transformers;
import org.hibernate.type.DateType;
import org.hibernate.type.DoubleType;
import org.hibernate.type.LongType;
import org.hibernate.type.StringType;

import com.egil.pts.modal.UserCapacity;
import com.egil.pts.util.GenericExcel;

public class TestCon {
	protected static Logger logger = LogManager.getLogger("PTSPUI");
	private static Session session;
	private static SessionFactory sessionFactory = null;
	static {
	}

	public static boolean process(String[] args) {
		boolean status = false;
		Session session = null;
		SessionFactory sessionFactory = null;
		try {

			try {
				sessionFactory =   new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
				session = sessionFactory.openSession();
			} catch (Throwable ex) {
				System.err.println("Initial SessionFactory creation failed." + ex);
				throw new ExceptionInInitializerError(ex);
			}

			DecimalFormat dformat = new DecimalFormat("##.00");
			List<UserCapacity> userCapacity = new ArrayList<UserCapacity>();
			HashMap<String, String> usersColHeaders = populateUsersColHeadersMap();
			Calendar c = Calendar.getInstance();
			int year = (args[0] != null && !args[0].isEmpty()) ? Integer.parseInt(args[0])
					: Calendar.getInstance().getWeekYear();
			Long pillar = null;

			if (args[2] != null && !args[2].isEmpty()) {
				pillar = Long.parseLong(args[2]);
			}
			if (args[1] == null || (args[1] != null && args[1].equalsIgnoreCase("MONTHLY"))) {
				String filename = "/applications/tomcat/webapps/pts/summaryexport/RICO_NW_UTILIZATION_REPORT_MONTHLY_"
						+ year + ".xlsx";

				if (new java.io.File(filename).exists()) {
					new java.io.File(filename).delete();
				}
				try {

					for (int j = 0; j < 12; j++) {

						Calendar gc = Calendar.getInstance();
						gc.set(Calendar.YEAR, year);
						gc.set(Calendar.MONTH, j);
						gc.set(Calendar.DAY_OF_MONTH, 1);
						Date monthStart = gc.getTime();
						gc = Calendar.getInstance();
						gc.set(Calendar.YEAR, year);
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
						getMonthlyNCUsageByUser(startDay, endDay, startWeek, endWeek, null, month, year, userCapacity,
								null, pillar, session);

					}

					DateFormat format = new SimpleDateFormat("yyyy-MM-dd");

					try {

						GenericExcel excel = new GenericExcel(filename);
						String[][] userRecords = new String[userCapacity.size() + 4][usersColHeaders.size()];
						int colCount = 0;
						for (String colHeader : usersColHeaders.values()) {
							userRecords[0][colCount++] = colHeader;
						}

						for (int i = 0; i < userCapacity.size(); i++) {
							UserCapacity user = userCapacity.get(i);
							if (user.getTFSEpic() != null)
								userRecords[i + 1][0] = user.getTFSEpic() + "";
							else
								userRecords[i + 1][0] = "";

							if (user.getRELEASETYPE() != null)
								userRecords[i + 1][1] = user.getRELEASETYPE() + "";
							else
								userRecords[i + 1][1] = "";

							if (user.getNETWORKCODE() != null)
								userRecords[i + 1][2] = user.getNETWORKCODE() + "";
							else
								userRecords[i + 1][2] = "";

							if (user.getPROJECT() != null)
								userRecords[i + 1][3] = user.getPROJECT() + "";
							else
								userRecords[i + 1][3] = "";

							if (user.getSUPERVISORNAME() != null)
								userRecords[i + 1][4] = user.getSUPERVISORNAME() + "";
							else
								userRecords[i + 1][4] = "";

							if (user.getSTATUS() != null)
								userRecords[i + 1][5] = user.getSTATUS() + "";
							else
								userRecords[i + 1][5] = "";

							if (user.getTOTALCAPACITY() != null)
								userRecords[i + 1][6] = user.getTOTALCAPACITY() + "";
							else
								userRecords[i + 1][6] = "";

							if (user.getSUMMATION() != null)
								userRecords[i + 1][7] = user.getSUMMATION() + "";
							else
								userRecords[i + 1][7] = "";

							if (user.getMONTH() != null)
								userRecords[i + 1][8] = user.getMONTH() + "";
							else
								userRecords[i + 1][8] = "";

							if (user.getYEAR() != null)
								userRecords[i + 1][9] = user.getYEAR() + "";
							else
								userRecords[i + 1][9] = "";

							if (user.getIMPLEMENTATIONDATE() != null)
								userRecords[i + 1][10] = format.format(user.getIMPLEMENTATIONDATE()) + "";
							else
								userRecords[i + 1][10] = "";
						}
						Double d = 0.0d;
						for (UserCapacity s : userCapacity) {
							d += s.getSUMMATION();
						}
						// userRecords[userCapacity.size() + 2][1] = "Total Effort";
						// userRecords[userCapacity.size() + 2][7] = d + "";
						excel.createSheet("Employee Details", userRecords);
						excel.setCustomRowStyleForHeader(0, 0, HSSFColor.GREY_40_PERCENT.index, 30);
						// excel.setRowFont(0,0,1);
						status = excel.writeWorkbook();
						logger.info("Generating Report File: " + filename + "  Status " + status);
					} catch (Exception e) {
						e.printStackTrace();
					}
				} catch (Throwable th) {
					th.printStackTrace();
				} finally {
					if (session != null && session.isConnected()) {
						session.close();
					}
					if (sessionFactory != null && !sessionFactory.isClosed())
						sessionFactory.close();
				}
			} else {
				String filename = "/applications/tomcat/webapps/pts/summaryexport/RICO_NW_UTILIZATION_REPORT_YEARLY_"
						+ year + ".xlsx";
				try {
					if (new java.io.File(filename).exists()) {
						new java.io.File(filename).delete();
					}
					{

						/*
						 * System.out.println(startDay + " " + endDay + " " + startWeek + " " + endWeek
						 * + " " + month + " " + currentYear + " ");
						 */
						getYearlyNCUsageByUser(year, userCapacity, null, pillar, session);
						session.close();
					}

					DateFormat format = new SimpleDateFormat("yyyy-MM-dd");

					try {

						if (userCapacity == null) {
							System.err.println("Invalid input..! Either effort is null or supervisor is/are null");
						}

						GenericExcel excel = new GenericExcel(filename);
						String[][] userRecords = new String[userCapacity.size() + 4][usersColHeaders.size()];
						int colCount = 0;
						for (String colHeader : usersColHeaders.values()) {
							userRecords[0][colCount++] = colHeader;
						}

						for (int i = 0; i < userCapacity.size(); i++) {
							UserCapacity user = userCapacity.get(i);
							if (user.getTFSEpic() != null)
								userRecords[i + 1][0] = user.getTFSEpic() + "";
							else
								userRecords[i + 1][0] = "";

							if (user.getRELEASETYPE() != null)
								userRecords[i + 1][1] = user.getRELEASETYPE() + "";
							else
								userRecords[i + 1][1] = "";

							if (user.getNETWORKCODE() != null)
								userRecords[i + 1][2] = user.getNETWORKCODE() + "";
							else
								userRecords[i + 1][2] = "";

							if (user.getPROJECT() != null)
								userRecords[i + 1][3] = user.getPROJECT() + "";
							else
								userRecords[i + 1][3] = "";

							if (user.getSUPERVISORNAME() != null)
								userRecords[i + 1][4] = user.getSUPERVISORNAME() + "";
							else
								userRecords[i + 1][4] = "";

							if (user.getSTATUS() != null)
								userRecords[i + 1][5] = user.getSTATUS() + "";
							else
								userRecords[i + 1][5] = "";

							if (user.getTOTALCAPACITY() != null)
								userRecords[i + 1][6] = user.getTOTALCAPACITY() + "";
							else
								userRecords[i + 1][6] = "";

							if (user.getSUMMATION() != null)
								userRecords[i + 1][7] = user.getSUMMATION() + "";
							else
								userRecords[i + 1][7] = "";

							if (user.getMONTH() != null)
								userRecords[i + 1][8] = user.getMONTH() + "";
							else
								userRecords[i + 1][8] = "";

							if (user.getYEAR() != null)
								userRecords[i + 1][9] = user.getYEAR() + "";
							else
								userRecords[i + 1][9] = "";

							if (user.getIMPLEMENTATIONDATE() != null)
								userRecords[i + 1][10] = format.format(user.getIMPLEMENTATIONDATE()) + "";
							else
								userRecords[i + 1][10] = "";
						}
						Double d = 0.0d;
						for (UserCapacity s : userCapacity) {
							d += s.getSUMMATION();
						}
						// userRecords[userCapacity.size() + 2][1] = "Total Effort";
						// userRecords[userCapacity.size() + 2][7] = d + "";
						excel.createSheet("Employee Details", userRecords);
						excel.setCustomRowStyleForHeader(0, 0, HSSFColor.GREY_40_PERCENT.index, 30);
						// excel.setRowFont(0,0,1);
						status = excel.writeWorkbook();
						logger.info("Generating Report File: " + filename + "  Status " + status);
					} catch (Exception e) {
						e.printStackTrace();
					}
				} catch (Throwable th) {
					th.printStackTrace();
				} finally {
					if (session != null && session.isConnected()) {
						session.close();
					}
					if (sessionFactory != null && !sessionFactory.isClosed())
						sessionFactory.close();
				}
			}
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return status;

	}

	private static void getYearlyNCUsageByUser(int year, List<UserCapacity> userCapacity, Object object2, Long pillar,
			Session session2) {
		String finalStr = " select 	nc.id,	nc.RELEASE_TYPE RELEASETYPE,	nc.impl_planned_date IMPLEMENTATIONDATE ,	(	select		PROJECT_NAME	from		PTS_PROJECT	where		id = nc.project_id) PROJECT,	(	select		NAME	from		PTS_USER	where		id = nc.project_manager ) 'SUPERVISORNAME',	nc.STATUS STATUS,	nc.TFSEpic,	(nc.ORIGINAL_DESIGN_LOE + nc.ORIGINAL_DEV_LOE + nc.ORIGINAL_TEST_LOE + nc.ORIGINAL_PROJ_MGMT_LOE + nc.ORIGINAL_IMPL_LOE) TOTALCAPACITY ,	'NA' month,	date_format( t.WEEKENDING_DATE, '%Y') year,	concat(nc.RELEASE_ID, ' - ', nc.RELEASE_NAME) NETWORKCODE,	ROUND((sum(t.MON_HRS ) +sum(t.tue_hrs) + sum(t.wed_hrs) + sum(t.thu_hrs)+ sum(t.fri_hrs)+ sum(t.sat_hrs)+ sum(t.sun_hrs)), 2) as SUMMATION ,	t.WEEKENDING_DATE from	PTS_NETWORK_CODES nc inner join PTS_USER_TIMESHEET t on	nc.id = t.network_code_id where	"
				+ ((pillar != null && pillar != -1) ? " nc.project_id = " + pillar + "	and  " : "")
				+ " date_format(WEEKENDING_DATE ,'%Y')=" + year
				+ " and  nc.STATUS not in ('DELETED', 'IN_ACTIVE')  group by t.network_code_id ";
		// System.out.println(finalStr);
		if (year < Calendar.getInstance().getWeekYear()) {
			finalStr = finalStr.replaceAll("PTS_USER_TIMESHEET", "PTS_USER_TIMESHEET_2019");
		}
		Query query = session2.createSQLQuery(finalStr).addScalar("TFSEpic", new LongType())
				.addScalar("RELEASETYPE", new StringType()).addScalar("IMPLEMENTATIONDATE", new DateType())
				.addScalar("PROJECT", new StringType()).addScalar("SUPERVISORNAME", new StringType())
				.addScalar("STATUS", new StringType()).addScalar("TOTALCAPACITY", new DoubleType())
				.addScalar("YEAR", new StringType()).addScalar("MONTH", new StringType())
				.addScalar("NETWORKCODE", new StringType()).addScalar("SUMMATION", new DoubleType());

		List<UserCapacity> tmpUserCapacity = query.setResultTransformer(Transformers.aliasToBean(UserCapacity.class))
				.list();
		userCapacity.addAll(tmpUserCapacity);

	}

	private static HashMap<String, String> populateUsersColHeadersMap() {
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
			Long userId, String month, int year, List<UserCapacity> userCapacity, Long ncId, Long pillar,
			Session session) {

		String qryStr = "select  nc.id,nc.RELEASE_TYPE RELEASETYPE,nc.impl_planned_date IMPLEMENTATIONDATE ,(SELECT PROJECT_NAME FROM PTS_PROJECT WHERE id=nc.project_id) PROJECT,(select NAME from PTS_USER where id=nc.project_manager ) 'SUPERVISORNAME',nc.STATUS STATUS,nc.TFSEpic,(nc.ORIGINAL_DESIGN_LOE +nc.ORIGINAL_DEV_LOE	+nc.ORIGINAL_TEST_LOE +nc.ORIGINAL_PROJ_MGMT_LOE +nc.ORIGINAL_IMPL_LOE) TOTALCAPACITY ,'"
				+ month
				+ "' MONTH,date_format( t.WEEKENDING_DATE,'%Y') year, concat(nc.RELEASE_ID, ' - ',nc.RELEASE_NAME) NETWORKCODE, :summation "
				+ ",  t.WEEKENDING_DATE  "
				+ " from PTS_NETWORK_CODES nc inner  join PTS_USER_TIMESHEET t on nc.id= t.network_code_id  "
				+ "   where :whereClause and  nc.STATUS not in ('DELETED','IN_ACTIVE')   "
				+ " group by   t.network_code_id";

		String startDayQryStr = "";
		String endDayQryStr = "";

		switch (startDay) {
		case "Mon":
			startDayQryStr = "ROUND((sum(t.mon_hrs)+ sum(t.tue_hrs) + sum(t.wed_hrs) + sum(t.thu_hrs)+ sum(t.fri_hrs)+ sum(t.sat_hrs)+ sum(t.sun_hrs)),2) as SUMMATION ";
			break;
		case "Tue":
			startDayQryStr = "ROUND((sum(t.tue_hrs) + sum(t.wed_hrs) + sum(t.thu_hrs)+ sum(t.fri_hrs)+ sum(t.sat_hrs)+ sum(t.sun_hrs)),2) as SUMMATION ";
			break;
		case "Wed":
			startDayQryStr = "ROUND((sum(t.wed_hrs) + sum(t.thu_hrs)+ sum(t.fri_hrs)+ sum(t.sat_hrs)+ sum(t.sun_hrs)),2) as SUMMATION ";
			break;
		case "Thu":
			startDayQryStr = "ROUND((sum(t.thu_hrs)+ sum(t.fri_hrs)+ sum(t.sat_hrs)+ sum(t.sun_hrs)),2) as SUMMATION ";
			break;
		case "Fri":
			startDayQryStr = "ROUND((sum(t.fri_hrs)+ sum(t.sat_hrs)+ sum(t.sun_hrs)),2) as SUMMATION ";
			break;
		case "Sat":
			startDayQryStr = "ROUND((sum(t.sat_hrs)+ sum(t.sun_hrs)),2) as SUMMATION ";
			break;
		case "Sun":
			startDayQryStr = "ROUND((sum(t.sun_hrs)),2) as SUMMATION ";
			break;
		}

		switch (endDay) {
		case "Mon":
			endDayQryStr = "ROUND((sum(t.mon_hrs)),2) as SUMMATION ";
			break;
		case "Tue":
			endDayQryStr = "ROUND((sum(t.mon_hrs)+ sum(t.tue_hrs)),2) as SUMMATION ";
			break;
		case "Wed":
			endDayQryStr = "ROUND((sum(t.mon_hrs)+ sum(t.tue_hrs) + sum(t.wed_hrs)),2) as SUMMATION ";
			break;
		case "Thu":
			endDayQryStr = "ROUND((sum(t.mon_hrs)+ sum(t.tue_hrs) + sum(t.wed_hrs) + sum(t.thu_hrs)),2) as SUMMATION ";
			break;
		case "Fri":
			endDayQryStr = "ROUND((sum(t.mon_hrs)+ sum(t.tue_hrs) + sum(t.wed_hrs) + sum(t.thu_hrs)+ sum(t.fri_hrs)),2) as SUMMATION ";
			break;
		case "Sat":
			endDayQryStr = "ROUND((sum(t.mon_hrs)+ sum(t.tue_hrs) + sum(t.wed_hrs) + sum(t.thu_hrs)+ sum(t.fri_hrs)+ sum(t.sat_hrs)),2) as SUMMATION ";
			break;
		case "Sun":
			endDayQryStr = "ROUND((sum(t.mon_hrs)+ sum(t.tue_hrs) + sum(t.wed_hrs) + sum(t.thu_hrs)+ sum(t.fri_hrs)+ sum(t.sat_hrs)+ sum(t.sun_hrs)),2) as SUMMATION ";
			break;
		}

		String stQryStr = qryStr.replaceAll(":summation", startDayQryStr).replaceAll(":whereClause",
				" t.WEEKENDING_DATE = '" + startWeek + "' " + (userId == null ? " " : " and t.user_id =" + userId)
						+ (ncId == null ? " " : " and t.network_code_id =" + ncId)
						+ ((pillar != null && pillar != -1) ? " and  n.PROJECT_ID=pillar " : " "));
		String endQryStr = qryStr.replaceAll(":summation", endDayQryStr).replaceAll(":whereClause",
				" t.WEEKENDING_DATE = '" + endWeek + "' " + (userId == null ? " " : " and t.user_id =" + userId)
						+ (ncId == null ? " " : " and t.network_code_id =" + ncId)
						+ ((pillar != null && pillar != -1) ? " and  n.PROJECT_ID=pillar " : " "));
		String midQryStr = qryStr.replaceAll(":summation",
				"ROUND(sum(t.mon_hrs)+ sum(t.tue_hrs) + sum(t.wed_hrs) + sum(t.thu_hrs)+ sum(t.fri_hrs)+ sum(t.sat_hrs)+ sum(t.sun_hrs),2) as SUMMATION ")
				.replaceAll(":whereClause",
						" t.WEEKENDING_DATE > '" + startWeek + "' and t.WEEKENDING_DATE < '" + endWeek + "' "
								+ (userId == null ? " " : " and t.user_id =" + userId)
								+ (ncId == null ? " " : " and t.network_code_id =" + ncId)
								+ ((pillar != null && pillar != -1) ? " and n.PROJECT_ID=pillar " : " "));

		String finalStr = " " + stQryStr + " union all " + midQryStr + " union all " + endQryStr + " ";

		if (year < Calendar.getInstance().getWeekYear()) {
			finalStr = finalStr.replaceAll("PTS_USER_TIMESHEET", "PTS_USER_TIMESHEET_2019");
		}
		// System.out.println(finalStr);
		Query query = session.createSQLQuery(finalStr).addScalar("TFSEpic", new LongType())
				.addScalar("RELEASETYPE", new StringType()).addScalar("IMPLEMENTATIONDATE", new DateType())
				.addScalar("PROJECT", new StringType()).addScalar("SUPERVISORNAME", new StringType())
				.addScalar("STATUS", new StringType()).addScalar("TOTALCAPACITY", new DoubleType())
				.addScalar("YEAR", new StringType()).addScalar("MONTH", new StringType())
				.addScalar("NETWORKCODE", new StringType()).addScalar("SUMMATION", new DoubleType());

		List<UserCapacity> tmpUserCapacity = query.setResultTransformer(Transformers.aliasToBean(UserCapacity.class))
				.list();

		userCapacity.addAll(tmpUserCapacity);

	}

	private static Session getSession() {
		return session;
	}

}
