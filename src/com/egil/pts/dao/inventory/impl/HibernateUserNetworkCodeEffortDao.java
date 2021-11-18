package com.egil.pts.dao.inventory.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.internal.util.StringHelper;
import org.hibernate.transform.Transformers;
import org.hibernate.type.BooleanType;
import org.hibernate.type.DateType;
import org.hibernate.type.DoubleType;
import org.hibernate.type.FloatType;
import org.hibernate.type.LongType;
import org.hibernate.type.StringType;
import org.springframework.stereotype.Repository;

import com.egil.pts.dao.common.hibernate.HibernateGenericDao;
import com.egil.pts.dao.domain.UserTimesheet;
import com.egil.pts.dao.inventory.UserNetworkCodeEffortDao;
import com.egil.pts.modal.ActivityCodesNew;
import com.egil.pts.modal.NetworkCodeEffort;
import com.egil.pts.modal.TimesheetActivityWfm;
import com.mysql.jdbc.StringUtils;

@Repository("userNetworkCodeEffortDao")
public class HibernateUserNetworkCodeEffortDao extends HibernateGenericDao<UserTimesheet, Long>
		implements UserNetworkCodeEffortDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<UserTimesheet> getEffortDetailsOfWeek(String weekEndingFromDate, String weekEndingToDate,
			Set<Long> userId, String approvalType, boolean showOld) throws Throwable {
		String querystring = "from UserTimesheet f where f.user.id in (:userId) and f.user.timesheetFlag='Y' ";
		if (weekEndingFromDate == null && weekEndingToDate == null) {
			querystring = querystring + " and f.weekendingDate >= :weekendingFromDate";
		} else if (StringHelper.isEmpty(weekEndingToDate) || (weekEndingFromDate != null && weekEndingToDate != null
				&& weekEndingFromDate.equals(weekEndingToDate))) {
			querystring = querystring + " and f.weekendingDate = :weekendingFromDate ";
		} else {
			querystring = querystring
					+ " and f.weekendingDate >= :weekendingFromDate and f.weekendingDate <= :weekendingToDate ";
		}
		if (StringHelper.isNotEmpty(approvalType) && approvalType.equalsIgnoreCase("Pending")) {
			querystring = querystring + " and  f.status = :approvalType";
		}

		querystring = querystring
				+ " and f.user not in ('Induction','No Show','Deleted','OffBoard') and f.user.userRole.id!=6  order by f.networkCode.order desc  ";
		Query query = getSession().createQuery(querystring);
		query.setParameterList("userId", userId);
		if (weekEndingFromDate == null && weekEndingToDate == null) {
			query.setString("weekendingFromDate", Calendar.getInstance().getWeekYear() + "-01-01");
		} else if (StringHelper.isEmpty(weekEndingToDate) || (weekEndingFromDate != null && weekEndingToDate != null
				&& weekEndingFromDate.equals(weekEndingToDate))) {
			query.setString("weekendingFromDate", weekEndingFromDate);
		} else {
			query.setString("weekendingFromDate", weekEndingFromDate);
			query.setString("weekendingToDate", weekEndingToDate);
		}

		if (StringHelper.isNotEmpty(approvalType) && approvalType.equalsIgnoreCase("Pending")) {
			query.setString("approvalType", approvalType);
		}
		return query.list();
	}

	@Override
	public void assignEffortToUser(List<UserTimesheet> userEffortList) throws Throwable {
		int count = 0;
		if (userEffortList != null) {
			for (UserTimesheet userEffort : userEffortList) {
				save(userEffort);
				// if (count % JDBC_BATCH_SIZE == 0) {
				getSession().flush();
				getSession().clear();
				// }
				count++;
			}
		}

	}

	@Override
	public void assignEffortToUser(long timesheetId, String status, String updatedBy, Date updtDate) throws Throwable {

		String qryStr = "UPDATE pts_user_timesheet ut SET ut.STATUS=:status ,ut.UPDATED_DATE=:updtDate ,ut.UPDATED_BY=:updtBy  WHERE id=:id";
		SQLQuery query = getSession().createSQLQuery(qryStr);
		query.setDate("updtDate", updtDate);
		query.setString("updtBy", updatedBy);
		query.setString("status", status);
		query.setLong("id", timesheetId);

		query.executeUpdate();
	}

	@Override
	public void delete(List<Long> removedIds) {
		if (removedIds != null && removedIds.size() > 0) {
			Query query = getSession().createQuery("delete from UserTimesheet f where " + "f.id in (:id)");
			query.setParameterList("id", removedIds);
			query.executeUpdate();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<NetworkCodeEffort> getNetworkCodesEffortOfProject(int year, Long projectId, Long ncId,
			Long supervisorId, boolean isAdmin, String type, String satus, String fromDate, String toDate)
			throws Throwable {
		String queryString = null;
		Query queryProc = null;
		Query query = null;
		List<Long> userIdList = new ArrayList<Long>();
		queryString = "select " + "	nc.id, "
				+ "	round( sum(t.mon_hrs)+ sum(t.tue_hrs) + sum(t.wed_hrs) + sum(t.thu_hrs)+ sum(t.fri_hrs)+ sum(t.sat_hrs)+ sum(t.sun_hrs) ) SUMMATION  &userName, "
				+ "	   &week	 " + "	concat(nc.RELEASE_ID , '-', nc.RELEASE_NAME ) 'NETWORKCODE' " + "from "
				+ "	PTS_USER_NETWORK_CODES unc, " + "	PTS_NETWORK_CODES nc, " + "	PTS_USER_TIMESHEET t " + "where "
				+ "	t.STATUS = 'Approved' " + "	and t.NETWORK_CODE_ID = nc.id "
				+ "	and t.NETWORK_CODE_ID = unc.NETWORK_CODE_ID " + "	and unc.network_code_id = nc.id "
				+ "	and nc.project_id =:projectId " + "	and unc.user_id in (:userIdList) &nid  &dateendDateCond "
				+ "group by " + "	&weekgroup  &userGroupby " + "order by" + "	unc.network_code_id";

		if (type != null && type.contains("WEEKEND_DATE")) {
			queryString = queryString.replaceAll("&weekgroup", " t.WEEKENDING_DATE ").replaceAll("&week",
					" t.WEEKENDING_DATE, ");
		} else if (type != null && type.contains("MONTH")) {
			queryString = queryString.replaceAll("&weekgroup", " monthname( t.WEEKENDING_DATE) ").replaceAll("&week",
					"  monthname( t.WEEKENDING_DATE) MONTH  , ");
		} else {
			queryString = queryString.replaceAll("&weekgroup", " t.NETWORK_CODE_ID  ").replaceAll("&week", "");
		}
		if (!StringUtils.isNullOrEmpty(fromDate) && !StringUtils.isNullOrEmpty(toDate)
				&& !toDate.equalsIgnoreCase(fromDate)) {
			queryString = queryString
					.replaceAll("&dateendDateCond",
							"  and t.WEEKENDING_DATE  >= &fromDate and  t.WEEKENDING_DATE  <= &toDate ")
					.replaceAll("&fromDate", fromDate).replaceAll("&toDate", toDate);
		} else {
			queryString = queryString.replaceAll("&dateendDateCond", "   ");
		}

		if (satus != null && satus.contains("USER")) {
			queryString = queryString.replaceAll("&userGroupby", " ,t.USER_ID ").replaceAll("&userName",
					" ,(select	name from PTS_USER	where id = unc.USER_ID ) 'USER' ");
		} else {
			queryString = queryString.replaceAll("&userGroupby", " ").replaceAll("&userName", " , 'USER' ");
		}
		if (ncId != null) {
			queryString = queryString.replaceAll("&nid", " and nc.id=:ncId ");
		} else {
			queryString = queryString.replaceAll("&nid", " ");
		}

		if (type != null && type.contains("WEEKEND_DATE")) {
			query = getSession().createSQLQuery(queryString).addScalar("NETWORKCODE", new StringType())
					.addScalar("id", new StringType()).addScalar("SUMMATION", new FloatType())
					.addScalar("WEEKENDING_DATE", new DateType()).addScalar("USER", new StringType());
		} else if (type != null && type.contains("MONTH")) {
			query = getSession().createSQLQuery(queryString).addScalar("NETWORKCODE", new StringType())
					.addScalar("id", new StringType()).addScalar("SUMMATION", new FloatType())
					.addScalar("MONTH", new StringType()).addScalar("USER", new StringType());
		} else {
			query = getSession().createSQLQuery(queryString).addScalar("NETWORKCODE", new StringType())
					.addScalar("id", new StringType()).addScalar("SUMMATION", new FloatType())
					.addScalar("USER", new StringType());
		}
		queryProc = getSession().createSQLQuery("CALL user_id_heirarchy_proc(:id)").setParameter("id", supervisorId);
		queryProc.executeUpdate();
		Query tmpQry = getSession().createSQLQuery("SELECT node FROM _result");
		userIdList = tmpQry.list();

		query.setLong("projectId", projectId);
		if (ncId != null) {
			query.setLong("ncId", ncId);
		}
		if (userIdList != null && userIdList.size() > 0) {
			query.setParameterList("userIdList", userIdList);
		}

		List<NetworkCodeEffort> ncEffortList = query
				.setResultTransformer(Transformers.aliasToBean(NetworkCodeEffort.class)).list();

		return ncEffortList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<NetworkCodeEffort> getUserProjectNC(int year, Long ncId, Long supervisorId, boolean isAdmin)
			throws Throwable {
		StringBuilder queryString = new StringBuilder();
		queryString.append("select distinct u.name as USERNAME, concat(u.id,'-', t.network_code_id) as id, "
				+ "(sum(t.mon_hrs)+ sum(t.tue_hrs) + sum(t.wed_hrs) + sum(t.thu_hrs)+ sum(t.fri_hrs)+ sum(t.sat_hrs)+ sum(t.sun_hrs)) as SUMMATION, "
				+ " (select su.name from PTS_USER su where su.id=us.supervisor_id) as SUPERVISOR "
				+ " from  PTS_USER u, PTS_USER_SUPERVISOR us, PTS_USER_TIMESHEET t "
				+ " where u.id=us.user_id and t.user_id=u.id and t.STATUS='Approved' "
				+ " and t.network_code_id=:ncId ");
		if (!isAdmin) {
			Query queryProc = getSession().createSQLQuery("CALL user_id_heirarchy_proc(:id)").setParameter("id",
					supervisorId);

			queryProc.executeUpdate();
			queryString.append(" and u.id in (SELECT node FROM _result) ");
		}
		queryString.append(" group by t.user_id ");

		Query query = getSession().createSQLQuery(queryString.toString()).addScalar("id", new StringType())
				.addScalar("USERNAME", new StringType()).addScalar("SUPERVISOR", new StringType())
				.addScalar("SUMMATION", new FloatType());
		query.setLong("ncId", ncId);

		List<NetworkCodeEffort> ncEffortList = query
				.setResultTransformer(Transformers.aliasToBean(NetworkCodeEffort.class)).list();

		return ncEffortList;
	}

	@Override
	public void assignEffortToUserWfm(List<TimesheetActivityWfm> userEffortList, Long userId, Date weekendingDate) {
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String sql = "INSERT INTO PTS_USER_TIMESHEET_WFM("
				+ "NAME, MON_HRS_ON,MON_HRS_OFF,TUE_HRS_ON,TUE_HRS_OFF,WED_HRS_ON,WED_HRS_OFF,THU_HRS_ON,THU_HRS_OFF,"
				+ "FRI_HRS_ON,FRI_HRS_OFF,SAT_HRS_ON,SAT_HRS_OFF,SUN_HRS_ON,SUN_HRS_OFF,WEEKENDING_DATE,UPDATED_BY,UPDATED_DATE,CREATED_BY,CREATED_DATE,USER_ID)"
				+ "values("
				+ ":NAME, :MON_HRS_ON, :MON_HRS_OFF, :TUE_HRS_ON, :TUE_HRS_OFF, :WED_HRS_ON, :WED_HRS_OFF, :THU_HRS_ON, :THU_HRS_OFF,"
				+ " :FRI_HRS_ON, :FRI_HRS_OFF, :SAT_HRS_ON, :SAT_HRS_OFF, :SUN_HRS_ON, :SUN_HRS_OFF, :WEEKENDING_DATE, :UPDATED_BY, :UPDATED_DATE, :CREATED_BY, :CREATED_DATE, :USER_ID"
				+ ")";
		if (userEffortList != null) {
			for (TimesheetActivityWfm userEffort : userEffortList) {
				String dqlSql = "DELETE FROM   PTS_USER_TIMESHEET_WFM   WHERE WEEKENDING_DATE='"
						+ format.format(weekendingDate) + "' and USER_ID=" + userId + "";
				getSession().createSQLQuery(dqlSql).executeUpdate();
				getSession().flush();
				getSession().clear();
				getSession().createSQLQuery(sql).setLong("USER_ID", userId).setString("UPDATED_BY", userId + "")
						.setString("CREATED_BY", userId + "").setDate("CREATED_DATE", new Date())
						.setDate("UPDATED_DATE", new Date()).setString("NAME", userEffort.getUserName())
						.setString("MON_HRS_ON", userEffort.getMonHrsOn())
						.setString("MON_HRS_OFF", userEffort.getMonHrsOff())
						.setString("TUE_HRS_ON", userEffort.getTueHrsOn())
						.setString("TUE_HRS_OFF", userEffort.getTueHrsOff())
						.setString("WED_HRS_ON", userEffort.getWedHrsOn())
						.setString("WED_HRS_OFF", userEffort.getWedHrsOff())
						.setString("THU_HRS_ON", userEffort.getThuHrsOn())
						.setString("THU_HRS_OFF", userEffort.getThuHrsOff())
						.setString("FRI_HRS_ON", userEffort.getFriHrsOn())
						.setString("FRI_HRS_OFF", userEffort.getFriHrsOff())
						.setString("SAT_HRS_ON", userEffort.getSatHrsOn())
						.setString("SAT_HRS_OFF", userEffort.getSatHrsOff())
						.setString("SUN_HRS_ON", userEffort.getSunHrsOn())
						.setString("SUN_HRS_OFF", userEffort.getSunHrsOff()).setDate("WEEKENDING_DATE", weekendingDate)

						.executeUpdate();
				getSession().flush();
				getSession().clear();
			}
		}

	}

	@Override
	public void deletewfm(List<Long> removedIds) {
		if (removedIds != null && removedIds.size() > 0) {
			getSession().createSQLQuery("delete   PTS_USER_TIMESHEET_WFM f where " + "f.id in (:id)")
					.setParameterList("id", removedIds);
		}
	}

	@Override
	public List<TimesheetActivityWfm> getEffortDetailsOfWeekWfm(Long userId, Date weekendingDate) {
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");

		String sql = "select ID id,NAME userName,USER_ID userId,MON_HRS_ON monHrsOn,MON_HRS_OFF monHrsOff,TUE_HRS_ON tueHrsOn,TUE_HRS_OFF tueHrsOff,"
				+ "WED_HRS_ON wedHrsOn,WED_HRS_OFF wedHrsOff,THU_HRS_ON thuHrsOn,THU_HRS_OFF thuHrsOff,FRI_HRS_ON friHrsOn,FRI_HRS_OFF friHrsOff,"
				+ "	SAT_HRS_ON satHrsOn,SAT_HRS_OFF satHrsOff ,SUN_HRS_ON sunHrsOn,SUN_HRS_OFF sunHrsOff,WEEKENDING_DATE weekendingDate,"
				+ "	CREATED_BY createdBy,CREATED_DATE createdDate FROM PTS_USER_TIMESHEET_WFM w where w.USER_ID="
				+ userId + " AND w.WEEKENDING_DATE='" + format.format(weekendingDate) + "' order by w.USER_ID";

		Query query = getSession().createSQLQuery(sql).addScalar("userId", new LongType())
				.addScalar("weekendingDate", new DateType()).addScalar("userName", new StringType())
				.addScalar("monHrsOn", new StringType()).addScalar("monHrsOff", new StringType())
				.addScalar("tueHrsOn", new StringType()).addScalar("tueHrsOff", new StringType())
				.addScalar("wedHrsOn", new StringType()).addScalar("wedHrsOff", new StringType())
				.addScalar("thuHrsOn", new StringType()).addScalar("thuHrsOff", new StringType())
				.addScalar("friHrsOn", new StringType()).addScalar("friHrsOff", new StringType())
				.addScalar("satHrsOn", new StringType()).addScalar("satHrsOff", new StringType())
				.addScalar("sunHrsOn", new StringType()).addScalar("sunHrsOff", new StringType());
		return query.setResultTransformer(Transformers.aliasToBean(TimesheetActivityWfm.class)).list();
	}

	@Override
	public List<TimesheetActivityWfm> getEffortDetailsOfWeekWfm(Long selectedEmployee, Date weekEnding,
			String showAllWfm, String showAll, Long stream) {
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String sql = "select  (SELECT NAME name from PTS_USER WHERE USERNAME=w.NAME)name, NAME userName,USER_ID userId,MON_HRS_ON monHrsOn,MON_HRS_OFF monHrsOff,TUE_HRS_ON tueHrsOn,TUE_HRS_OFF tueHrsOff,"
				+ "WED_HRS_ON wedHrsOn,WED_HRS_OFF wedHrsOff,THU_HRS_ON thuHrsOn,THU_HRS_OFF thuHrsOff,FRI_HRS_ON friHrsOn,FRI_HRS_OFF friHrsOff,"
				+ "	SAT_HRS_ON satHrsOn,SAT_HRS_OFF satHrsOff ,SUN_HRS_ON sunHrsOn,SUN_HRS_OFF sunHrsOff,WEEKENDING_DATE weekendingDate,"
				+ "	CREATED_BY createdBy,CREATED_DATE createdDate FROM PTS_USER_TIMESHEET_WFM w where id !=0 &user &week order by w.USER_ID";
		List<Integer> userIdList = new ArrayList<Integer>();

		if (showAll != null && showAll.equalsIgnoreCase("TRUE")) {
			Query queryProc = getSession().createSQLQuery("CALL user_id_heirarchy_proc(:id)").setParameter("id",
					selectedEmployee);
			queryProc.executeUpdate();
			Query tmpQry = getSession().createSQLQuery("SELECT node FROM _result");
			userIdList = tmpQry.list();
			getSession().flush();
			getSession().clear();

			sql = sql.replaceAll("&user", " and w.USER_ID in(:user)");
		} else if (showAll != null && showAll.equalsIgnoreCase("ALL")) {
			sql = sql.replaceAll("&user", "");
		} else {
			sql = sql.replaceAll("&user", " and w.USER_ID in(:user)");
		}
		if (stream != null && stream != -1) {
			Query query = getSession()
					.createSQLQuery("SELECT id FROM pts_user WHERE STREAM=:streams AND id IN (:users)")
					.setParameterList("users", userIdList).setLong("streams", stream);
			userIdList.clear();
			userIdList = new ArrayList<Integer>();
			getSession().flush();
			getSession().clear();
		}
		if (showAllWfm != null && !showAllWfm.isEmpty() && showAllWfm.equalsIgnoreCase("true")) {
			sql = sql.replaceAll("&week", "");
		} else {
			sql = sql.replaceAll("&week", " AND w.WEEKENDING_DATE='" + format.format(weekEnding) + "'");
		}

		Query query = getSession().createSQLQuery(sql).addScalar("userId", new LongType())
				.addScalar("name", new StringType()).addScalar("weekendingDate", new DateType())
				.addScalar("userName", new StringType()).addScalar("monHrsOn", new StringType())
				.addScalar("monHrsOff", new StringType()).addScalar("tueHrsOn", new StringType())
				.addScalar("tueHrsOff", new StringType()).addScalar("wedHrsOn", new StringType())
				.addScalar("wedHrsOff", new StringType()).addScalar("thuHrsOn", new StringType())
				.addScalar("thuHrsOff", new StringType()).addScalar("friHrsOn", new StringType())
				.addScalar("friHrsOff", new StringType()).addScalar("satHrsOn", new StringType())
				.addScalar("satHrsOff", new StringType()).addScalar("sunHrsOn", new StringType())
				.addScalar("sunHrsOff", new StringType());
		if (showAll != null && showAll.equalsIgnoreCase("TRUE")) {
			query.setParameterList("user", userIdList);
		} else if (showAll != null && showAll.equalsIgnoreCase("ALL")) {
		} else {
			query.setParameter("user", selectedEmployee);
		}
		return query.setResultTransformer(Transformers.aliasToBean(TimesheetActivityWfm.class)).list();
	}

	@Override
	public void saveActivityEffort(boolean weekoff, ActivityCodesNew data, Long userId, String name, Date date) {
		try {
			if (weekoff) {
				getSession().createSQLQuery(
						"update PTS_USER_ACTIVITY_TIMESHEET set weekoff=1  where WEEKENDING_DATE=:date and USER_ID=:userId")
						.setDate("date", date).setLong("userId", userId).executeUpdate();
			} else {

				getSession().createSQLQuery(
						"delete from PTS_USER_ACTIVITY_TIMESHEET where WEEKENDING_DATE=:date and USER_ID=:userId")
						.setDate("date", date).setLong("userId", userId).executeUpdate();
				getSession().flush();
				String querystring = " INSERT INTO PTS_USER_ACTIVITY_TIMESHEET"
						+ " (USER_ID, NETWORK_CODE_ID, ACTIVITY_CODE_ID, FESABILITY, AD_HOC, `4TH_LEVEL_SUPPORT`, `2ND_LEVEL_SUPPORT`, WEEKENDING_DATE, CREATED_BY, CREATED_DATE, `type`, STATUS, UPDATED_BY, UPDATED_DATE)"
						+ " values (:userId, :nw, :actCodeId, :fesability, :adhoc, :frthlvl, :2ndlvl, :date, :name, current_date, :type, :status, :name, current_date) ";
				Query query = getSession().createSQLQuery(querystring).setLong("userId", userId)
						.setLong("nw", (data.getNetworkCodeId() != null) ? data.getNetworkCodeId() : -1L)
						.setLong("actCodeId", (data.getActivityCodeId() != null) ? data.getActivityCodeId() : -1L)
						.setDouble("fesability", (data.getFESABILITY() != null) ? data.getFESABILITY() : 0.0)
						.setDouble("adhoc", (data.getAD_HOC() != null) ? data.getAD_HOC() : 0.0)
						.setDouble("frthlvl",
								(data.getFOURTH_LEVEL_SUPPORT() != null) ? data.getFOURTH_LEVEL_SUPPORT() : 0.0)
						.setDouble("2ndlvl",
								(data.getSECONDND_LEVEL_SUPPORT() != null) ? data.getSECONDND_LEVEL_SUPPORT() : 0.0)
						.setDate("date", date).setString("name", name)
						.setString("type", (data.getType() != null) ? data.getType() : "")
						.setString("status", (data.getSTATUS() != null) ? data.getSTATUS() : "Pending");
				query.executeUpdate();

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void assignEffortToUserNew(List<UserTimesheet> userEffortList) throws Throwable {
		int count = 0;
		if (userEffortList != null) {
			for (UserTimesheet userEffort : userEffortList) {
				Query query = getSession()
						.createSQLQuery("update  PTS.PTS_USER_TIMESHEET set STATUS=:status where id=:id")
						.setLong("id", userEffort.getId()).setString("status", userEffort.getStatus());
				query.executeUpdate();
				// if (count % JDBC_BATCH_SIZE == 0) {
				getSession().flush();
				getSession().clear();
				// }
				count++;
			}
		}

	}
}
