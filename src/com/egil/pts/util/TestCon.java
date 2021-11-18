package com.egil.pts.util;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.transform.Transformers;
import org.hibernate.type.FloatType;
import org.hibernate.type.StringType;

import com.egil.pts.modal.UserCapacity;

public class TestCon {
	protected Logger logger = LogManager.getLogger("PTSPUI");

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
