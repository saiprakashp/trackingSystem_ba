/*     */
package connectionTest;

import java.io.FileOutputStream;
import java.io.IOException;
/*     */
/*     */ import java.text.DateFormat;
/*     */ import java.text.DecimalFormat;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Calendar;
/*     */ import java.util.Date;
/*     */ import java.util.HashMap;
import java.util.Iterator;
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.hibernate.Query;
/*     */ import org.hibernate.Session;
/*     */ import org.hibernate.SessionFactory;
/*     */ import org.hibernate.cfg.Configuration;
/*     */ import org.hibernate.transform.Transformers;
/*     */ import org.hibernate.type.DateType;
/*     */ import org.hibernate.type.DoubleType;
/*     */ import org.hibernate.type.LongType;
/*     */ import org.hibernate.type.StringType;
/*     */ import org.hibernate.type.Type;

/*     */
/*     */ public class TestCon
/*     */ {
	/*     */ private static Session session;
	/* 26 */ private static SessionFactory sessionFactory = null;
	/*     */ static {
		/*     */ try {
			/* 29 */ sessionFactory = (new Configuration()).configure().buildSessionFactory();
			/*     */ }
		/* 31 */ catch (Throwable ex) {
			/* 32 */ System.err.println("Initial SessionFactory creation failed." + ex);
			/* 33 */ throw new ExceptionInInitializerError(ex);
			/*     */ }
		/*     */ }

	/*     */
	/*     */
	/*     */ public static void main(String[] args) {
				//String a[] = { "2021", "MONTHLY", "-1" };
				call(args);

		/*     */ }

	/*     */
	/*     */ public static void call(String[] args) {
		/* 43 */ DecimalFormat dformat = new DecimalFormat("##.00");
		/* 44 */ List<UserCapacity> userCapacity = new ArrayList<>();
		/* 45 */ HashMap<String, String> usersColHeaders = populateUsersColHeadersMap();
		/* 46 */ Calendar c = Calendar.getInstance();
		/* 47 */ int year = (args[0] != null && !args[0].isEmpty()) ? Integer.parseInt(args[0]) :
		/* 48 */ Calendar.getInstance().getWeekYear();
		/* 49 */ Long pillar = null;
		/* 50 */ if (args[2] != null && !args[2].isEmpty()) {
			/* 51 */ pillar = Long.valueOf(Long.parseLong(args[2]));
			/*     */ }
		/* 53 */ System.out.println("ARGUMENTS " + year + " - " + pillar + " - " + args[1]);
		/* 54 */ if (args[1] == null || (args[1] != null && args[1].equalsIgnoreCase("MONTHLY"))) {
			/* 55 */ String filename = "RICO_NW_UTILIZATION_REPORT_MONTHLY_" + year + ".xlsx";
			/*     */
			/*     */ try {
				/* 58 */ for (int j = 0; j < 12; j++) {
					/* 59 */ session = sessionFactory.openSession();
					/*     */
					/* 61 */ Calendar gc = Calendar.getInstance();
					/* 62 */ gc.set(1, year);
					/* 63 */ gc.set(2, j);
					/* 64 */ gc.set(5, 1);
					/* 65 */ Date monthStart = gc.getTime();
					/* 66 */ gc = Calendar.getInstance();
					/* 67 */ gc.set(1, year);
					/* 68 */ gc.set(2, j);
					/* 69 */ gc.set(5, gc.getActualMaximum(5));
					/*     */
					/* 71 */ Date monthEnd = gc.getTime();
					/* 72 */ SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
					/* 73 */ String startWeek = getWeekEnding(simpleDateFormat.format(monthStart));
					/* 74 */ String endWeek = getWeekEnding(simpleDateFormat.format(monthEnd));
					/* 75 */ String startDay = (new SimpleDateFormat("EEE")).format(monthStart);
					/* 76 */ String endDay = (new SimpleDateFormat("EEE")).format(monthEnd);
					/* 77 */ String month = (new SimpleDateFormat("MMMM")).format(monthStart);
					/*     */
					/*     */
					/*     */
					/*     */
					/*     */
					/* 83 */ getMonthlyNCUsageByUser(startDay, endDay, startWeek, endWeek, null, month, year,
							userCapacity, null, /* 84 */ pillar);
					/* 85 */ session.close();
					/*     */ }
				/*     */
				/* 88 */ DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
				/*     */
				/*     */
				/*     */ try {
					/* 93 */ String[][] userRecords = new String[userCapacity.size() + 4][usersColHeaders.size()];
					/* 94 */ int colCount = 0;
					/* 95 */ for (String colHeader : usersColHeaders.values()) {
						/* 96 */ userRecords[0][colCount++] = colHeader;
						/*     */ }
					/*     */
					/* 99 */ for (int i = 0; i < userCapacity.size(); i++) {
						/* 100 */ UserCapacity user = userCapacity.get(i);
						/* 101 */ if (user.getTFSEpic() != null) {
							/* 102 */ userRecords[i + 1][0] = user.getTFSEpic() + "";
							/*     */ } else {
							/* 104 */ userRecords[i + 1][0] = "";
							/*     */ }
						/* 106 */ if (user.getRELEASETYPE() != null) {
							/* 107 */ userRecords[i + 1][1] = (new StringBuilder(String.valueOf(user.getRELEASETYPE())))
									.toString();
							/*     */ } else {
							/* 109 */ userRecords[i + 1][1] = "";
							/*     */ }
						/* 111 */ if (user.getNETWORKCODE() != null) {
							/* 112 */ userRecords[i + 1][2] = (new StringBuilder(String.valueOf(user.getNETWORKCODE())))
									.toString();
							/*     */ } else {
							/* 114 */ userRecords[i + 1][2] = "";
							/*     */ }
						/* 116 */ if (user.getPROJECT() != null) {
							/* 117 */ userRecords[i + 1][3] = (new StringBuilder(String.valueOf(user.getPROJECT())))
									.toString();
							/*     */ } else {
							/* 119 */ userRecords[i + 1][3] = "";
							/*     */ }
						/* 121 */ if (user.getSUPERVISORNAME() != null) {
							/* 122 */ userRecords[i
									+ 1][4] = (new StringBuilder(String.valueOf(user.getSUPERVISORNAME()))).toString();
							/*     */ } else {
							/* 124 */ userRecords[i + 1][4] = "";
							/*     */ }
						/* 126 */ if (user.getSTATUS() != null) {
							/* 127 */ userRecords[i + 1][5] = (new StringBuilder(String.valueOf(user.getSTATUS())))
									.toString();
							/*     */ } else {
							/* 129 */ userRecords[i + 1][5] = "";
							/*     */ }
						/* 131 */ if (user.getTOTALCAPACITY() != null) {
							/* 132 */ userRecords[i + 1][6] = user.getTOTALCAPACITY() + "";
							/*     */ } else {
							/* 134 */ userRecords[i + 1][6] = "";
							/*     */ }
						/* 136 */ if (user.getSUMMATION() != null) {
							/* 137 */ userRecords[i + 1][7] = user.getSUMMATION() + "";
							/*     */ } else {
							/* 139 */ userRecords[i + 1][7] = "";
							/*     */ }
						/* 141 */ if (user.getMONTH() != null) {
							/* 142 */ userRecords[i + 1][8] = (new StringBuilder(String.valueOf(user.getMONTH())))
									.toString();
							/*     */ } else {
							/* 144 */ userRecords[i + 1][8] = "";
							/*     */ }
						/* 146 */ if (user.getYEAR() != null) {
							/* 147 */ userRecords[i + 1][9] = (new StringBuilder(String.valueOf(user.getYEAR())))
									.toString();
							/*     */ } else {
							/* 149 */ userRecords[i + 1][9] = "";
							/*     */ }
						/* 151 */ if (user.getIMPLEMENTATIONDATE() != null) {
							/* 152 */ userRecords[i + 1][10] = (new StringBuilder(
									String.valueOf(format.format(user.getIMPLEMENTATIONDATE())))).toString();
							/*     */ } else {
							/* 154 */ userRecords[i + 1][10] = "";
							/*     */ }
						/* 156 */ }
					Double d = Double.valueOf(0.0D);
					/* 157 */ for (UserCapacity s : userCapacity) {
						/* 158 */ d = Double.valueOf(d.doubleValue() + s.getSUMMATION().doubleValue());
						/*     */ }
					boolean status = generateExcel(year, filename, userRecords);
					/* 166 */ System.out
							.println("userCapacity: " + d + " YEAR: " + year + "  Result : " + status + " FileName: " +
							/* 167 */ filename);
					/* 168 */ } catch (Exception e) {
					/* 169 */ e.printStackTrace();
					/*     */ }
				/* 171 */ } catch (Throwable th) {
				/* 172 */ th.printStackTrace();
				/*     */ } finally {
				/* 174 */ sessionFactory.close();
				/*     */ }
			/*     */ } else {
			/* 177 */ String filename = "RICO_NW_UTILIZATION_REPORT_YEARLY_" + year + ".xlsx";
			/*     */
			/*     */
			/*     */ try {
				/* 181 */ session = sessionFactory.openSession();
				/*     */
				/*     */
				/*     */
				/*     */
				/*     */
				/* 187 */ getYearlyNCUsageByUser(year, userCapacity, null, pillar);
				/* 188 */ session.close();
				/*     */
				/*     */
				/* 191 */ DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
				/*     */
				/*     */
				/*     */ try {
					/* 195 */ if (userCapacity == null) {
						/* 196 */ System.err
								.println("Invalid input..! Either effort is null or supervisor is/are null");
						/*     */ }
					/*     */
					/* 199 */ GenericExcel excel = new GenericExcel(filename);
					/* 200 */ String[][] userRecords = new String[userCapacity.size() + 4][usersColHeaders.size()];
					/* 201 */ int colCount = 0;
					/* 202 */ for (String colHeader : usersColHeaders.values()) {
						/* 203 */ userRecords[0][colCount++] = colHeader;
						/*     */ }
					/*     */
					/* 206 */ for (int i = 0; i < userCapacity.size(); i++) {
						/* 207 */ UserCapacity user = userCapacity.get(i);
						/* 208 */ if (user.getTFSEpic() != null) {
							/* 209 */ userRecords[i + 1][0] = user.getTFSEpic() + "";
							/*     */ } else {
							/* 211 */ userRecords[i + 1][0] = "";
							/*     */ }
						/* 213 */ if (user.getRELEASETYPE() != null) {
							/* 214 */ userRecords[i + 1][1] = (new StringBuilder(String.valueOf(user.getRELEASETYPE())))
									.toString();
							/*     */ } else {
							/* 216 */ userRecords[i + 1][1] = "";
							/*     */ }
						/* 218 */ if (user.getNETWORKCODE() != null) {
							/* 219 */ userRecords[i + 1][2] = (new StringBuilder(String.valueOf(user.getNETWORKCODE())))
									.toString();
							/*     */ } else {
							/* 221 */ userRecords[i + 1][2] = "";
							/*     */ }
						/* 223 */ if (user.getPROJECT() != null) {
							/* 224 */ userRecords[i + 1][3] = (new StringBuilder(String.valueOf(user.getPROJECT())))
									.toString();
							/*     */ } else {
							/* 226 */ userRecords[i + 1][3] = "";
							/*     */ }
						/* 228 */ if (user.getSUPERVISORNAME() != null) {
							/* 229 */ userRecords[i
									+ 1][4] = (new StringBuilder(String.valueOf(user.getSUPERVISORNAME()))).toString();
							/*     */ } else {
							/* 231 */ userRecords[i + 1][4] = "";
							/*     */ }
						/* 233 */ if (user.getSTATUS() != null) {
							/* 234 */ userRecords[i + 1][5] = (new StringBuilder(String.valueOf(user.getSTATUS())))
									.toString();
							/*     */ } else {
							/* 236 */ userRecords[i + 1][5] = "";
							/*     */ }
						/* 238 */ if (user.getTOTALCAPACITY() != null) {
							/* 239 */ userRecords[i + 1][6] = user.getTOTALCAPACITY() + "";
							/*     */ } else {
							/* 241 */ userRecords[i + 1][6] = "";
							/*     */ }
						/* 243 */ if (user.getSUMMATION() != null) {
							/* 244 */ userRecords[i + 1][7] = user.getSUMMATION() + "";
							/*     */ } else {
							/* 246 */ userRecords[i + 1][7] = "";
							/*     */ }
						/* 248 */ if (user.getMONTH() != null) {
							/* 249 */ userRecords[i + 1][8] = (new StringBuilder(String.valueOf(user.getMONTH())))
									.toString();
							/*     */ } else {
							/* 251 */ userRecords[i + 1][8] = "";
							/*     */ }
						/* 253 */ if (user.getYEAR() != null) {
							/* 254 */ userRecords[i + 1][9] = (new StringBuilder(String.valueOf(user.getYEAR())))
									.toString();
							/*     */ } else {
							/* 256 */ userRecords[i + 1][9] = "";
							/*     */ }
						/* 258 */ if (user.getIMPLEMENTATIONDATE() != null) {
							/* 259 */ userRecords[i + 1][10] = (new StringBuilder(
									String.valueOf(format.format(user.getIMPLEMENTATIONDATE())))).toString();
							/*     */ } else {
							/* 261 */ userRecords[i + 1][10] = "";
							/*     */ }
						/* 263 */ }
					Double d = Double.valueOf(0.0D);
					/* 264 */ for (UserCapacity s : userCapacity) {
						/* 265 */ d = Double.valueOf(d.doubleValue() + s.getSUMMATION().doubleValue());
						/*     */ }
					/*     */
					/*     */

					/* 269 */ excel.createSheet("Employee Details", userRecords);
					/* 270 */ excel.setCustomRowStyleForHeader(0, 0, (short) 55, 30.0F);
					/*     */
					/* 272 */ boolean status = excel.writeWorkbook();
					/* 273 */ System.out
							.println("userCapacity: " + d + " YEAR: " + year + "  Result : " + status + " FileName: " +
							/* 274 */ filename);
					/* 275 */ } catch (Exception e) {
					/* 276 */ e.printStackTrace();
					/*     */ }
				/* 278 */ } catch (Throwable th) {
				/* 279 */ th.printStackTrace();
				/*     */ } finally {
				/* 281 */ sessionFactory.close();
				/*     */ }
			/*     */ }
		/*     */ }

	/*     */
	/*     */
	/*     */ private static void getYearlyNCUsageByUser(int year, List<UserCapacity> userCapacity, Object object2,
			Long pillar) {
		/* 288 */ String finalStr = " select \tnc.id,\tnc.RELEASE_TYPE RELEASETYPE,\tnc.impl_planned_date IMPLEMENTATIONDATE ,\t(\tselect\t\tPROJECT_NAME\tfrom\t\tPTS_PROJECT\twhere\t\tid = nc.project_id) PROJECT,\t(\tselect\t\tNAME\tfrom\t\tPTS_USER\twhere\t\tid = nc.project_manager ) 'SUPERVISORNAME',\tnc.STATUS STATUS,\tnc.TFSEpic,\t(nc.ORIGINAL_DESIGN_LOE + nc.ORIGINAL_DEV_LOE + nc.ORIGINAL_TEST_LOE + nc.ORIGINAL_PROJ_MGMT_LOE + nc.ORIGINAL_IMPL_LOE) TOTALCAPACITY ,\t'NA' month,\tdate_format( t.WEEKENDING_DATE, '%Y') year,\tconcat(nc.RELEASE_ID, ' - ', nc.RELEASE_NAME) NETWORKCODE,\tROUND((sum(t.MON_HRS ) +sum(t.tue_hrs) + sum(t.wed_hrs) + sum(t.thu_hrs)+ sum(t.fri_hrs)+ sum(t.sat_hrs)+ sum(t.sun_hrs)), 2) as SUMMATION ,\tt.WEEKENDING_DATE from\tPTS_NETWORK_CODES nc inner join PTS_USER_TIMESHEET t on\tnc.id = t.network_code_id where\t"
				+ ((
				/* 289 */ pillar != null && pillar.longValue() != -1L) ? (" nc.project_id = " + pillar + "\tand  ")
						: "")
				+
				/* 290 */ " date_format(WEEKENDING_DATE ,'%Y')=" + year +
				/* 291 */ " and  nc.STATUS not in ('DELETED', 'IN_ACTIVE') group by t.network_code_id ";
		/*     */
		/* 293 */ if (year < 2020) {
			/* 294 */ finalStr = finalStr.replaceAll("PTS_USER_TIMESHEET", "PTS_USER_TIMESHEET_2019");
			/*     */ }
		/* 296 */ Query nativeQuery = getSession().createSQLQuery(finalStr).addScalar("TFSEpic", (Type) new LongType())
				/* 297 */ .addScalar("RELEASETYPE", (Type) new StringType())
				.addScalar("IMPLEMENTATIONDATE", (Type) new DateType())
				/* 298 */ .addScalar("PROJECT", (Type) new StringType())
				.addScalar("SUPERVISORNAME", (Type) new StringType())
				/* 299 */ .addScalar("STATUS", (Type) new StringType())
				.addScalar("TOTALCAPACITY", (Type) new DoubleType())
				/* 300 */ .addScalar("YEAR", (Type) new StringType()).addScalar("MONTH", (Type) new StringType())
				/* 301 */ .addScalar("NETWORKCODE", (Type) new StringType())
				.addScalar("SUMMATION", (Type) new DoubleType());
		/*     */
		/* 303 */ List<UserCapacity> tmpUserCapacity = nativeQuery
				.setResultTransformer(Transformers.aliasToBean(UserCapacity.class))/* 304 */ .list();
		/* 305 */ userCapacity.addAll(tmpUserCapacity);
		/*     */ }

	/*     */
	/*     */
	/*     */ private static HashMap<String, String> populateUsersColHeadersMap() {
		/* 310 */ LinkedHashMap<String, String> headers = new LinkedHashMap<>();
		/* 311 */ headers.put("TFS_ID", "TFS Epic Id");
		/* 312 */ headers.put("TYPE", "TYPE");
		/* 313 */ headers.put("RELEASE", "RELEASE");
		/* 314 */ headers.put("PROJECT", "PROJECT");
		/* 315 */ headers.put("MANAGER_NAME", "MANAGER NAME");
		/* 316 */ headers.put("STATUS", "STATUS");
		/* 317 */ headers.put("TOTAL_CAPACITY", "TOTAL CAPACITY");
		/* 318 */ headers.put("EFFORT", "EFFORT");
		/* 319 */ headers.put("MONTH", "MONTH");
		/* 320 */ headers.put("YEAR", "YEAR");
		/* 321 */ headers.put("IMPLEMENTATION_DATE", "IMPLEMENTATION DATE");
		/*     */
		/* 323 */ return headers;
		/*     */ }

	/*     */
	/*     */ private static String getWeekEnding(String selectedDate) {
		/* 327 */ Date weekEnd = null;
		/* 328 */ String weekEndStr = "";
		/*     */
		/*     */ try {
			/* 331 */ Calendar c = Calendar.getInstance();
			/* 332 */ DateFormat f = new SimpleDateFormat("yyyy-MM-dd");
			/*     */
			/* 334 */ DateFormat weekEndFormat = new SimpleDateFormat("dd/MM/yyyy");
			/* 335 */ if (selectedDate != null && !selectedDate.equals("")) {
				/* 336 */ c.setTime(weekEndFormat.parse(selectedDate));
				/*     */ }
			/*     */
			/* 339 */ if (c.get(7) == 1) {
				/* 340 */ c.add(5, -1);
				/*     */ }
			/*     */
			/* 343 */ c.set(7, 6);
			/*     */
			/*     */
			/*     */
			/* 347 */ weekEnd = weekEndFormat.parse(weekEndFormat.format(c.getTime()));
			/* 348 */ weekEndStr = f.format(weekEnd);
			/* 349 */ } catch (Exception e) {
			/* 350 */ e.printStackTrace();
			/*     */ }
		/* 352 */ return weekEndStr;
		/*     */ }

	/*     */
	/*     */
	/*     */
	/*     */ private static void getMonthlyNCUsageByUser(String startDay, String endDay, String startWeek,
			String endWeek, Long userId, String month, int year, List<UserCapacity> userCapacity, Long ncId,
			Long pillar) {
		String qryStr = "select  nc.id,nc.RELEASE_TYPE RELEASETYPE,nc.impl_planned_date IMPLEMENTATIONDATE ,(SELECT PROJECT_NAME FROM PTS_PROJECT WHERE id=nc.project_id) PROJECT,(select NAME from PTS_USER where id=nc.project_manager ) 'SUPERVISORNAME',nc.STATUS STATUS,nc.TFSEpic,(nc.ORIGINAL_DESIGN_LOE +nc.ORIGINAL_DEV_LOE\t+nc.ORIGINAL_TEST_LOE +nc.ORIGINAL_PROJ_MGMT_LOE +nc.ORIGINAL_IMPL_LOE) TOTALCAPACITY ,'"
				+ month
				+ "' MONTH,date_format( t.WEEKENDING_DATE,'%Y') year, concat(nc.RELEASE_ID, ' - ',nc.RELEASE_NAME) NETWORKCODE, :summation "
				+ ",  t.WEEKENDING_DATE  "
				+ " from PTS_NETWORK_CODES nc inner  join PTS_USER_TIMESHEET t on nc.id= t.network_code_id  "
				+ "   where :whereClause and nc.STATUS not in ('DELETED','IN_ACTIVE')"
				+ " group by   t.network_code_id";
		/*     */
		/* 366 */ String startDayQryStr = "";
		/* 367 */ String endDayQryStr = "";
		/*     */ String str1;
		/* 369 */ switch ((str1 = startDay).hashCode()) {
		case 70909:
			if (!str1.equals("Fri")) {
				/*     */ break;
				/*     */ }
			/*     */
			/*     */
			/*     */
			/*     */
			/*     */
			/*     */
			/*     */
			/*     */
			/*     */
			/*     */
			/*     */
			/* 383 */ startDayQryStr = "ROUND((sum(t.fri_hrs)+ sum(t.sat_hrs)+ sum(t.sun_hrs)),2) as SUMMATION ";
			break;
		case 77548:
			if (!str1.equals("Mon"))
				/*     */ break;
			startDayQryStr = "ROUND((sum(t.mon_hrs)+ sum(t.tue_hrs) + sum(t.wed_hrs) + sum(t.thu_hrs)+ sum(t.fri_hrs)+ sum(t.sat_hrs)+ sum(t.sun_hrs)),2) as SUMMATION ";
			break;
		/*     */ case 82886:
			if (!str1.equals("Sat"))
				/* 386 */ break;
			startDayQryStr = "ROUND((sum(t.sat_hrs)+ sum(t.sun_hrs)),2) as SUMMATION ";
			break;
		/*     */ case 83500:
			/*     */ if (!str1.equals("Sun"))
				/* 389 */ break;
			startDayQryStr = "ROUND((sum(t.sun_hrs)),2) as SUMMATION ";
			break;
		case 84065:
			if (!str1.equals("Thu"))
				/*     */ break;
			startDayQryStr = "ROUND((sum(t.thu_hrs)+ sum(t.fri_hrs)+ sum(t.sat_hrs)+ sum(t.sun_hrs)),2) as SUMMATION ";
			break;
		case 84452:
			if (!str1.equals("Tue"))
				/*     */ break;
			startDayQryStr = "ROUND((sum(t.tue_hrs) + sum(t.wed_hrs) + sum(t.thu_hrs)+ sum(t.fri_hrs)+ sum(t.sat_hrs)+ sum(t.sun_hrs)),2) as SUMMATION ";
			break;
		/*     */ case 86838:
			if (!str1.equals("Wed"))
				/* 393 */ break;
			startDayQryStr = "ROUND((sum(t.wed_hrs) + sum(t.thu_hrs)+ sum(t.fri_hrs)+ sum(t.sat_hrs)+ sum(t.sun_hrs)),2) as SUMMATION ";
			break;
		}
		String str2;
		switch ((str2 = endDay).hashCode()) {
		case 70909:
			if (!str2.equals("Fri")) {
				/*     */ break;
				/*     */ }
			/*     */
			/*     */
			/*     */
			/*     */
			/*     */
			/*     */
			/*     */
			/*     */
			/*     */
			/*     */
			/*     */
			/* 407 */ endDayQryStr = "ROUND((sum(t.mon_hrs)+ sum(t.tue_hrs) + sum(t.wed_hrs) + sum(t.thu_hrs)+ sum(t.fri_hrs)),2) as SUMMATION ";
			break;
		case 77548:
			if (!str2.equals("Mon"))
				/*     */ break;
			endDayQryStr = "ROUND((sum(t.mon_hrs)),2) as SUMMATION ";
			break;
		/*     */ case 82886:
			if (!str2.equals("Sat"))
				/* 410 */ break;
			endDayQryStr = "ROUND((sum(t.mon_hrs)+ sum(t.tue_hrs) + sum(t.wed_hrs) + sum(t.thu_hrs)+ sum(t.fri_hrs)+ sum(t.sat_hrs)),2) as SUMMATION ";
			break;
		/*     */ case 83500:
			/*     */ if (!str2.equals("Sun"))
				/* 413 */ break;
			endDayQryStr = "ROUND((sum(t.mon_hrs)+ sum(t.tue_hrs) + sum(t.wed_hrs) + sum(t.thu_hrs)+ sum(t.fri_hrs)+ sum(t.sat_hrs)+ sum(t.sun_hrs)),2) as SUMMATION ";
			break;
		case 84065:
			if (!str2.equals("Thu"))
				/*     */ break;
			endDayQryStr = "ROUND((sum(t.mon_hrs)+ sum(t.tue_hrs) + sum(t.wed_hrs) + sum(t.thu_hrs)),2) as SUMMATION ";
			break;
		case 84452:
			if (!str2.equals("Tue"))
				/*     */ break;
			endDayQryStr = "ROUND((sum(t.mon_hrs)+ sum(t.tue_hrs)),2) as SUMMATION ";
			break;
		/*     */ case 86838:
			if (!str2.equals("Wed"))
				/* 417 */ break;
			endDayQryStr = "ROUND((sum(t.mon_hrs)+ sum(t.tue_hrs) + sum(t.wed_hrs)),2) as SUMMATION ";
			break;
		}
		String stQryStr = qryStr.replaceAll(":summation", startDayQryStr).replaceAll(":whereClause",
				/* 418 */ " t.WEEKENDING_DATE = '" + startWeek + "' "
						+ ((userId == null) ? " " : (" and t.user_id =" + userId)) + (
						/* 419 */ (ncId == null) ? " " : (" and t.network_code_id =" + ncId)) + ((
						/* 420 */ pillar != null && pillar.longValue() != -1L) ? " and  n.PROJECT_ID=pillar " : " "));
		/* 421 */ String endQryStr = qryStr.replaceAll(":summation", endDayQryStr).replaceAll(":whereClause",
				/* 422 */ " t.WEEKENDING_DATE = '" + endWeek + "' "
						+ ((userId == null) ? " " : (" and t.user_id =" + userId)) + (
						/* 423 */ (ncId == null) ? " " : (" and t.network_code_id =" + ncId)) + ((
						/* 424 */ pillar != null && pillar.longValue() != -1L) ? " and  n.PROJECT_ID=pillar " : " "));
		/* 425 */ String midQryStr = qryStr.replaceAll(":summation",
				/* 426 */ "ROUND(sum(t.mon_hrs)+ sum(t.tue_hrs) + sum(t.wed_hrs) + sum(t.thu_hrs)+ sum(t.fri_hrs)+ sum(t.sat_hrs)+ sum(t.sun_hrs),2) as SUMMATION ")
				/* 427 */ .replaceAll(":whereClause", /* 428 */ " t.WEEKENDING_DATE > '" + startWeek
						+ "' and t.WEEKENDING_DATE < '" + endWeek + "' " + (
						/* 429 */ (userId == null) ? " " : (" and t.user_id =" + userId)) + (
						/* 430 */ (ncId == null) ? " " : (" and t.network_code_id =" + ncId)) + ((
						/* 431 */ pillar != null && pillar.longValue() != -1L) ? " and n.PROJECT_ID=pillar " : " "));
		/*     */
		/* 433 */ String finalStr = " " + stQryStr + " union all " + midQryStr + " union all " + endQryStr + " ";
		/*     */
		/* 435 */ if (year < 2020) {
			/* 436 */ finalStr = finalStr.replaceAll("PTS_USER_TIMESHEET", "PTS_USER_TIMESHEET_2019");
			/*     */ }
		/*     */
		/* 439 */ Query nativeQuery = getSession().createSQLQuery(finalStr).addScalar("TFSEpic", (Type) new LongType())
				/* 440 */ .addScalar("RELEASETYPE", (Type) new StringType())
				.addScalar("IMPLEMENTATIONDATE", (Type) new DateType())
				/* 441 */ .addScalar("PROJECT", (Type) new StringType())
				.addScalar("SUPERVISORNAME", (Type) new StringType())
				/* 442 */ .addScalar("STATUS", (Type) new StringType())
				.addScalar("TOTALCAPACITY", (Type) new DoubleType())
				/* 443 */ .addScalar("YEAR", (Type) new StringType()).addScalar("MONTH", (Type) new StringType())
				/* 444 */ .addScalar("NETWORKCODE", (Type) new StringType())
				.addScalar("SUMMATION", (Type) new DoubleType());
		/*     */
		/* 446 */ List<UserCapacity> tmpUserCapacity = nativeQuery
				.setResultTransformer(Transformers.aliasToBean(UserCapacity.class))/* 447 */ .list();
		/*     */
		/* 449 */ userCapacity.addAll(tmpUserCapacity);
		/*     */ }

	/*     */
	/*     */
	/*     */ private static Session getSession() {
		/* 454 */ return session;
		/*     */ }

	public static boolean generateExcel(int year, String filename, String[][] userRecords) throws IOException {
		FileOutputStream fileOut = null;
		Workbook wb = null;
		try {
			wb = new XSSFWorkbook();
			Sheet sheet = wb.createSheet("Employee Details");
			int i = 0;
			Row row = sheet.createRow((short) i);
			HashMap<String, String> header = populateUsersColHeadersMap();
			int col = 0;

			Set<Entry<String, String>> mapData = header.entrySet();
			Iterator<Entry<String, String>> s = mapData.iterator();
			while (s.hasNext()) {
				row.createCell(col).setCellValue(s.next().getValue());
				col++;
			}
			i++;
			for (String[] rows : userRecords) {
				col = 0;
				row = sheet.createRow((short) i);
				for (String cols : rows) {
					row.createCell(col).setCellValue(cols);
					col++;
				}
				i++;
			}
			setCustomRowStyleForHeader(0, 0, (short) 55, 30.0F, wb);

			fileOut = new FileOutputStream(filename);
			wb.write(fileOut);
			return true;

		} catch (IOException e) {
			e.printStackTrace();
			return false;
		} finally {
			if (fileOut != null)
				fileOut.close();
		}
	}

	public static void setCustomRowStyleForHeader(int sheetNumber, int rowNumber, short foregroundColor, float height,
			Workbook wb) {
		CellStyle customStyle = wb.createCellStyle();
		customStyle.setWrapText(true);
		Font font = wb.createFont();
		customStyle.setVerticalAlignment((short) 0);
		Sheet sheet = wb.getSheetAt(sheetNumber);
		Row row = sheet.getRow(rowNumber);
		font.setBoldweight((short) 700);
		font.setFontName("Calibri");
		customStyle.setFillPattern((short) 1);
		customStyle.setFillForegroundColor(foregroundColor);
		customStyle.setBorderBottom((short) 1);
		customStyle.setBottomBorderColor(IndexedColors.GREY_25_PERCENT.index);
		customStyle.setBorderLeft((short) 1);
		customStyle.setLeftBorderColor(IndexedColors.GREY_25_PERCENT.index);
		customStyle.setBorderRight((short) 1);
		customStyle.setRightBorderColor(IndexedColors.GREY_25_PERCENT.index);
		customStyle.setBorderTop((short) 1);
		customStyle.setTopBorderColor(IndexedColors.GREY_25_PERCENT.index);
		customStyle.setFont(font);
		for (int i = 0; i < row.getLastCellNum(); i++) {
			row.getCell(i).setCellStyle(customStyle);
			sheet.autoSizeColumn((short) i);
		}
		row.setHeightInPoints(height);
	}
}
