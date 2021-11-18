package com.egil.pts.dao.inventory.impl;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.hibernate.type.DoubleType;
import org.hibernate.type.FloatType;
import org.hibernate.type.IntegerType;
import org.hibernate.type.LongType;
import org.hibernate.type.StringType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.egil.pts.dao.common.hibernate.HibernateGenericDao;
import com.egil.pts.dao.domain.SupervisorResourceUtilization;
import com.egil.pts.dao.domain.UserUtilization;
import com.egil.pts.dao.inventory.UserUtilizationDao;
import com.egil.pts.modal.EssDetails;
import com.egil.pts.modal.LocationUserCount;
import com.egil.pts.modal.NetworkCodeEffort;
import com.egil.pts.modal.Pagination;
import com.egil.pts.modal.ResourceEffort;
import com.egil.pts.modal.ResourceUtilization;
import com.egil.pts.modal.SearchSortContainer;
import com.egil.pts.modal.UserCapacity;
import com.egil.pts.modal.Utilization;

@Repository("userUtilizationDa1o")
public class HibernateUserUtilizationDao2 extends HibernateGenericDao<UserUtilization, Long>
		implements UserUtilizationDao {
	@Value("${manager.based}")
	private String managerUtilBased;
	@Value("${dashboard.indetail.manager.based}")
	private String dashManagerUtilBased;

	@SuppressWarnings("unchecked")
	@Override
	public List<NetworkCodeEffort> getUsersUtilizationDetailsSummary(long userId, List<String> weekList,
			Long searchValue) throws Throwable {
		/*
		 * Query query = getSession().createQuery(
		 * "select utilization.user.id as USERID, concat(utilization.user.personalInfo.firstName, concat(' ', utilization.user.personalInfo.lastName)) as USERNAME, utilization.week as WEEK, ROUND((sum(utilization.effort)/42.5 *100), 2) as EFFORTPERCENTAGE"
		 * + " from UserUtilization utilization " + " where " +
		 * "utilization.user.userSupervisor.supervisor.id = :id and utilization.week in (:week) group by (utilization.user.id, utilization.week)"
		 * );
		 */
		String queryStr = "";
		queryStr = "select ROUND((sum(uu.effort)/42.5 *100), 2) as EFFORTPERCENTAGE, "
				+ " week as WEEK, uu.user_id as USERID, concat(u.first_name, concat(' ', u.last_name)) as USERNAME "
				+ " from PTS_USER_NETWORK_CODE_UTILIZATION uu, PTS_USER u, "
				+ " PTS_USER_SUPERVISOR us where u.id = uu.user_id and "
				+ " uu.user_id=us.user_id and uu.week in (:week) ";
		if (searchValue != null && searchValue != 0l) {
			queryStr = queryStr + " and (us.supervisor_id= :id and uu.user_id= :searchValue) ";
		} else {
			queryStr = queryStr + " and (us.supervisor_id= :id or u.id = :id) ";
		}
		queryStr = queryStr + " group by uu.user_id, uu.week ";
		Query query = getSession().createSQLQuery(queryStr).addScalar("EFFORTPERCENTAGE", new FloatType())
				.addScalar("WEEK", new StringType()).addScalar("USERID", new LongType())
				.addScalar("USERNAME", new StringType());

		query.setLong("id", userId);
		if (searchValue != null && searchValue != 0l) {
			query.setLong("searchValue", searchValue);
		}
		query.setParameterList("week", weekList);

		List<NetworkCodeEffort> networkCodeEffortList = query
				.setResultTransformer(Transformers.aliasToBean(NetworkCodeEffort.class)).list();
		return networkCodeEffortList;

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<UserUtilization> getUserUtilizationDetails(List<Long> userId, List<String> weekList) throws Throwable {
		Query query = getSession().createQuery(" from UserUtilization utilization "
				+ " where utilization.user.id in (:id) and utilization.week in (:week)");
		query.setParameterList("id", userId);
		query.setParameterList("week", weekList);
		return query.list();
	}

	@Override
	public void saveUsetUtilization(List<UserUtilization> userEffortList) throws Throwable {
		int count = 0;
		if (userEffortList != null && userEffortList.size() > 0) {
			for (UserUtilization userUtilization : userEffortList) {
				save(userUtilization);
				if (count % JDBC_BATCH_SIZE == 0) {
					getSession().flush();
					getSession().clear();
				}
				count++;
			}
		}

	}

	@Override
	public int removeUserUtilization(List<Long> userId, List<String> weekList) throws Throwable {
		Query query = getSession().createQuery("delete from UserUtilization utilization "
				+ " where utilization.user.id in (:id) and utilization.week in (:week)");
		query.setParameterList("id", userId);
		query.setParameterList("week", weekList);
		return (Integer) query.executeUpdate();
	}

	/*
	 * @SuppressWarnings("unchecked")
	 * 
	 * @Override public List<NetworkCodeEffort> getDashBoardDetails(Long userId,
	 * boolean isAdmin) throws Throwable { String queryString =
	 * "select concat(nc.network_code_id, ' - ' , nc.network_code_name) as NETWORKCODE, sum(unu.effort) as SUMMATION, nc.effort EFFORT"
	 * +
	 * " from PTS_USER_NETWORK_CODES un left outer join PTS_USER_NETWORK_CODE_UTILIZATION unu on un.network_code_id= unu.network_code_id "
	 * + " inner join PTS_NETWORK_CODES nc on un.network_code_id=nc.id "; if
	 * (isAdmin) { queryString = queryString + " group by un.network_code_id"; }
	 * else { queryString = queryString +
	 * " where un.user_id = :id group by un.network_code_id"; }
	 * 
	 * Query query = getSession().createSQLQuery(queryString)
	 * .addScalar("SUMMATION", new FloatType()) .addScalar("EFFORT", new
	 * FloatType()) .addScalar("NETWORKCODE", new StringType()); if (!isAdmin) {
	 * query.setLong("id", userId); } List<NetworkCodeEffort> networkCodeEffortList
	 * = query .setResultTransformer(
	 * Transformers.aliasToBean(NetworkCodeEffort.class)) .list(); return
	 * networkCodeEffortList; }
	 */

	@SuppressWarnings("unchecked")
	@Override
	public List<NetworkCodeEffort> getDashBoardDetails(Long userId, boolean isAdmin) throws Throwable {
		String queryString = "";
		if (isAdmin) {
			queryString = "select distinct CASE WHEN nc.network_code_id is null THEN concat(nc.RELEASE_ID, ' - ',nc.RELEASE_NAME) else concat(nc.network_code_id, ' - ' , nc.RELEASE_ID, ' - ',nc.RELEASE_NAME) END as NETWORKCODE, (sum(t.mon_hrs)+ sum(t.tue_hrs) + sum(t.wed_hrs) + sum(t.thu_hrs)+ sum(t.fri_hrs)+ sum(t.sat_hrs)+ sum(t.sun_hrs)) as SUMMATION, nc.effort EFFORT"
					+ " from PTS_NETWORK_CODES nc left outer join PTS_USER_TIMESHEET t on nc.id= t.network_code_id ";
			// +
			// " inner join PTS_NETWORK_CODES nc on un.network_code_id=nc.id ";

			queryString = queryString + " group by nc.RELEASE_ID";
		} else {
			queryString = "select distinct CASE WHEN nc.network_code_id is null THEN concat(nc.RELEASE_ID, ' - ',nc.RELEASE_NAME) else concat(nc.network_code_id, ' - ' , nc.RELEASE_ID, ' - ',nc.RELEASE_NAME) END as NETWORKCODE, (sum(t.mon_hrs)+ sum(t.tue_hrs) + sum(t.wed_hrs) + sum(t.thu_hrs)+ sum(t.fri_hrs)+ sum(t.sat_hrs)+ sum(t.sun_hrs)) as SUMMATION, nc.effort EFFORT"
					+ " from PTS_USER_NETWORK_CODES un left outer join PTS_USER_TIMESHEET t on un.network_code_id= t.network_code_id "
					+ " inner join PTS_NETWORK_CODES nc on un.network_code_id=nc.id ";
			queryString = queryString + " where un.user_id = :id group by un.network_code_id";
		}

		Query query = getSession().createSQLQuery(queryString).addScalar("SUMMATION", new FloatType())
				.addScalar("EFFORT", new FloatType()).addScalar("NETWORKCODE", new StringType());
		if (!isAdmin) {
			query.setLong("id", userId);
		}
		List<NetworkCodeEffort> networkCodeEffortList = query
				.setResultTransformer(Transformers.aliasToBean(NetworkCodeEffort.class)).list();
		return networkCodeEffortList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<NetworkCodeEffort> getUserDashBoardDetails(Long userId) throws Throwable {
		String queryString = "";

		queryString = " select t.id,nc.id,pac.ACTIVITY_CODE_NAME 'ACTIVITYCODE',(sum(t.mon_hrs)+ sum(t.tue_hrs) + sum(t.wed_hrs) + sum(t.thu_hrs)+ sum(t.fri_hrs)+ sum(t.sat_hrs)+ sum(t.sun_hrs)) as SUMMATION,0 EFFORT ,	concat( nc.RELEASE_ID, ' - ', nc.RELEASE_NAME) as 'NETWORKCODE', "
				+ " (nc.ORIGINAL_DESIGN_LOE + nc.ORIGINAL_DEV_LOE + nc.ORIGINAL_TEST_LOE + nc.ORIGINAL_PROJ_MGMT_LOE + nc.ORIGINAL_IMPL_LOE) TOTALCAPACITY,nc.local_dev_loe 'localDevLoe',nc.local_test_loe 'localTestLoe',nc.global_dev_loe 'globalDevLoe',nc.global_test_loe 'globalTestLoe' "
				+ " from PTS_USER_TIMESHEET t,	PTS_NETWORK_CODES nc,	PTS_ACTIVITY_CODES pac where t.NETWORK_CODE_ID = nc.id	and t.user_id =:id and pac.id=t.ACTIVITY_CODE_ID group by t.id having (sum(t.mon_hrs)+ sum(t.tue_hrs) + sum(t.wed_hrs) + sum(t.thu_hrs)+ sum(t.fri_hrs)+ sum(t.sat_hrs)+ sum(t.sun_hrs)) > 0 order by 	t.WEEKENDING_DATE desc";
		// limit 5

		Query query = getSession().createSQLQuery(queryString).addScalar("SUMMATION", new FloatType())
				.addScalar("ACTIVITYCODE", new StringType()).addScalar("EFFORT", new FloatType())
				.addScalar("NETWORKCODE", new StringType()).addScalar("localDevLoe", new LongType())
				.addScalar("localTestLoe", new LongType()).addScalar("globalDevLoe", new LongType())
				.addScalar("globalTestLoe", new LongType());

		query.setLong("id", userId);

		List<NetworkCodeEffort> networkCodeEffortList = query
				.setResultTransformer(Transformers.aliasToBean(NetworkCodeEffort.class)).list();
		return networkCodeEffortList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<NetworkCodeEffort> getNetworkCodeReport(Long networkCodeId) throws Throwable {

		String queryString = "select distinct CASE WHEN nc.network_code_id is null THEN concat(nc.RELEASE_ID, ' - ',nc.RELEASE_NAME) else concat(nc.network_code_id, ' - ' , nc.RELEASE_ID, ' - ',nc.RELEASE_NAME) END as NETWORKCODE, "
				+ " sum(unu.effort) as SUMMATION, " + " nc.effort EFFORT, unu.week WEEK "
				+ " from  PTS_USER_NETWORK_CODE_UTILIZATION unu inner join PTS_NETWORK_CODES nc on unu.network_code_id=nc.id where unu.network_code_id=:networkCodeId  and nc.`STATUS` not in ('DELETED','Completed','COMPLETED','In-Active','Cancelled','Implemented') group by unu.week";
		Query query = getSession().createSQLQuery(queryString).addScalar("SUMMATION", new FloatType())
				.addScalar("EFFORT", new FloatType()).addScalar("NETWORKCODE", new StringType())
				.addScalar("WEEK", new StringType());
		query.setLong("networkCodeId", networkCodeId);
		List<NetworkCodeEffort> networkCodeEffortList = query
				.setResultTransformer(Transformers.aliasToBean(NetworkCodeEffort.class)).list();
		return networkCodeEffortList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Utilization> getUserUtilizationSummary(Long supervisorId, String fromDate, String toDate, Long userId,
			String reporteeType, Pagination pagination, boolean delinquentEntries, List<String> weeksList,
			long stableTeamid, Long networkCodeId, Long projectManagerId) throws Throwable {

		String queryString = "  SELECT resourceType, id,(select ACTIVITY_CODE_NAME from PTS_ACTIVITY_CODES where id=ACTIVITY_CODE_ID) 'activityCodeName' ,coalesce(DATE_FORMAT(WEEKDATE, '%d%m%Y')) parseWeekEndingDate,DATE_FORMAT(WEEKDATE, '%m/%d/%Y') as weekEndingDate,NETWORK_CODE_ID,signum,COALESCE(effort,0.0) 'effort',status,resourceName,networkCodeName,nwStatus,email,supervisorId,(select name from PTS_USER S where S.id = supervisorId) supervisorName FROM ( "
				+ " (SELECT U.id as id,(select USER_TYPE from PTS_USER_ROLE r,PTS_USER_TYPES t where r.TYPE_ID =t.id and r.USER_ID =U.id) resourceType,U.USERNAME 'signum', U.email as email, U.NAME 'resourceName',U.status,S.SUPERVISOR_ID 'supervisorId' FROM PTS_USER U JOIN PTS_USER_SUPERVISOR S ON S.USER_ID=U.ID AND U.status not in ('OffBoard','No Show','Open','Selected','Deleted','Induction'))A "
				+ " LEFT OUTER JOIN  "
				+ " (SELECT N.STATUS 'nwStatus',concat(N.RELEASE_ID, ' - ', N.RELEASE_NAME)'networkCodeName',T.ACTIVITY_CODE_ID,T.WEEKENDING_DATE 'WEEKDATE',T.NETWORK_CODE_ID,coalesce(sum((T.mon_hrs + T.tue_hrs + T.wed_hrs + T.thu_hrs + T.fri_hrs + T.sat_hrs + T.sun_hrs)),0.0) as effort,T.USER_ID FROM  PTS_USER_TIMESHEET T,PTS_NETWORK_CODES N WHERE T.NETWORK_CODE_ID=N.ID GROUP BY  T.NETWORK_CODE_ID,T.USER_ID,T.WEEKENDING_DATE ) TIM "
				+ " ON A.id=TIM.USER_ID "
				+ " )WHERE  UPPER(signum) NOT LIKE '%ADMIN%' AND status not in ('OffBoard','No Show','Open','Selected','Deleted','Induction') &super &user &tmNw &tmStab &projectManager &weekCond &del "
				+ " GROUP BY  networkCodeName,ID,WEEKDATE   ";

		if (supervisorId != null) {
			queryString = queryString.replaceAll("&super", " and supervisorId=:supervisorId ");

		} else {
			queryString = queryString.replaceAll("&super", " ");
		}

		if (userId != null && userId > 0) {
			queryString = queryString.replaceAll("&user", " and id=:userId ");
		} else {
			queryString = queryString.replaceAll("&user", " ");
		}

		if (networkCodeId != null && networkCodeId.longValue() > 0) {
			queryString = queryString.replaceAll("&tmNw", "and NETWORK_CODE_ID =:nw");
		} else {
			queryString = queryString.replaceAll("&tmNw", " ");
		}
		if (stableTeamid > 0) {
			queryString = queryString.replaceAll("&tmStab",
					"and NETWORK_CODE_ID in (select id from PTS_NETWORK_CODES where stable_teams =:stable)");
		} else {
			queryString = queryString.replaceAll("&tmStab", " ");
		}
		if (projectManagerId != null && projectManagerId > 0) {
			queryString = queryString.replaceAll("&projectManager",
					"and NETWORK_CODE_ID in (select id from PTS_NETWORK_CODES where project_manager=:projectmanager) ");
		} else {
			queryString = queryString.replaceAll("&projectManager", " ");
		}

		if (stableTeamid > 0) {
			queryString = queryString.replaceAll("&stable", " and stable_teams =:stable ");
		} else {
			queryString = queryString.replaceAll("&stable", " ");
		}

		queryString = queryString.replaceAll("&weekCond", " and WEEKDATE in (:week) ");

		/*
		 * if (supervisorId == null && (userId != null && userId > 0)) { queryString =
		 * queryString.replaceAll("&sp1",
		 * "(select supervisor_id from PTS_USER_SUPERVISOR where USER_ID=:userId)");
		 * 
		 * } else if (supervisorId != null && (userId == null || (userId != null &&
		 * userId <= 0))) { queryString = queryString.replaceAll("&sp1",
		 * ":supervisorId"); }
		 * 
		 * if (supervisorId == null && (userId != null && userId > 0)) { queryString =
		 * queryString.replaceAll("&sp2", "  and u.id=:userId");
		 * 
		 * } else if (supervisorId != null && (userId == null || (userId != null &&
		 * userId <= 0))) { queryString = queryString.replaceAll("&sp2", " "); }
		 */

		if (delinquentEntries) {
			queryString = queryString.replaceAll("&del", "   and effort <= 0  ");
		} else {
			queryString = queryString.replaceAll("&del", " ");
		}

		Query query = getSession().createSQLQuery(queryString).addScalar("id", new StringType())
				.addScalar("weekEndingDate", new StringType()).addScalar("signum", new StringType())
				.addScalar("parseWeekEndingDate", new StringType()).addScalar("effort", new FloatType())
				.addScalar("resourceName", new StringType()).addScalar("networkCodeName", new StringType())
				.addScalar("activityCodeName", new StringType()).addScalar("supervisorName", new StringType())
				.addScalar("email", new StringType()).addScalar("supervisorId", new LongType())
				.addScalar("resourceType", new StringType()).addScalar("nwStatus", new StringType());
		if (projectManagerId != null && projectManagerId > 0) {
			query.setLong("projectmanager", projectManagerId);
		}

		if (stableTeamid > 0) {
			query.setLong("stable", stableTeamid);
		}
		if (networkCodeId != null && networkCodeId > 0) {
			query.setLong("nw", networkCodeId);
		}
		/*
		 * if (fromDate != null && !fromDate.equals("")) {
		 * query.setString("selectedDate", fromDate); } if (toDate != null &&
		 * !toDate.equals("")) { query.setString("selectedToDate", toDate); }
		 */
		if (userId != null && userId > 0) {
			query.setLong("userId", userId);
		}
		if (supervisorId != null) {
			query.setLong("supervisorId", supervisorId);
		}
		query.setParameterList("week", weeksList);
		if (pagination != null) {
			if (pagination.getOffset() > 0)
				query.setFirstResult(pagination.getOffset());
			if (pagination.getSize() > 0)
				query.setMaxResults(pagination.getSize());
		}
		List<Utilization> networkCodeEffortList = query
				.setResultTransformer(Transformers.aliasToBean(Utilization.class)).list();
		return networkCodeEffortList;
	}

	@Override
	public int getUserUtilizationCount(Long supervisorId, String fromDate, String toDate, Long userId,
			String reporteeType, boolean delinquentEntries, List<String> weeksList, long stableTeamid,
			Long networkCodeId, Long projectManagerId) throws Throwable {

		String queryString = "  select * from ( select 	(select name from PTS_USER where id=&sp1) supervisorName,u.USERNAME 'signum',(select USER_TYPE from PTS_USER_ROLE r,PTS_USER_TYPES t where r.TYPE_ID =t.id and r.USER_ID =u.id) resourceType, us.SUPERVISOR_ID supervisorId, coalesce(DATE_FORMAT(ts.WEEKENDING_DATE, '%m/%d/%Y') , DATE_FORMAT(:selectedDate, '%m/%d/%Y')) as weekEndingDate,u.id as id,	u.email as email,coalesce(DATE_FORMAT(ts.WEEKENDING_DATE, '%d%m%Y') , DATE_FORMAT(:selectedDate, '%d%m%Y')) as parseWeekEndingDate,	coalesce(sum((ts.mon_hrs + ts.tue_hrs + ts.wed_hrs + ts.thu_hrs + ts.fri_hrs + ts.sat_hrs + ts.sun_hrs)), 0.0) as effort,u.name as resourceName ,(select p.PROJECT_NAME from PTS_PROJECT p where p.id=nc.PROJECT_ID) 'application', nc.STATUS 'nwStatus' ,concat(nc.RELEASE_ID, ' - ', nc.RELEASE_NAME)'networkCodeName' ,(select ACTIVITY_CODE_NAME from PTS_ACTIVITY_CODES where id=ts.ACTIVITY_CODE_ID) 'activityCodeName' "
				+ "  from PTS_USER u	inner join PTS_USER_SUPERVISOR us on us.user_id = u.id  &sp2 ";
		if (supervisorId == null && (userId == null || (userId != null && userId <= 0))) {
			queryString = "  select * from ( select 	(select name from PTS_USER where id=us.SUPERVISOR_ID) supervisorName,u.USERNAME 'signum',(select USER_TYPE from PTS_USER_ROLE r,PTS_USER_TYPES t where r.TYPE_ID =t.id and r.USER_ID =u.id) resourceType, us.SUPERVISOR_ID supervisorId, coalesce(DATE_FORMAT(ts.WEEKENDING_DATE, '%m/%d/%Y') , DATE_FORMAT(:selectedDate, '%m/%d/%Y')) as weekEndingDate,u.id as id,	u.email as email,coalesce(DATE_FORMAT(ts.WEEKENDING_DATE, '%d%m%Y') , DATE_FORMAT(:selectedDate, '%d%m%Y')) as parseWeekEndingDate,	coalesce(sum((ts.mon_hrs + ts.tue_hrs + ts.wed_hrs + ts.thu_hrs + ts.fri_hrs + ts.sat_hrs + ts.sun_hrs)), 0.0) as effort,u.name as resourceName,nc.STATUS 'nwStatus',(select p.PROJECT_NAME from PTS_PROJECT p where p.id=nc.PROJECT_ID) 'application',  concat(nc.RELEASE_ID, ' - ', nc.RELEASE_NAME)'networkCodeName' ,(select	ACTIVITY_CODE_NAME	from	PTS_ACTIVITY_CODES	where		id = ts.ACTIVITY_CODE_ID) 'activityCodeName' "
					+ "  from PTS_USER u	inner join PTS_USER_SUPERVISOR us on us.user_id = u.id   ";
		}
		if (supervisorId != null) {
			queryString += " and us.SUPERVISOR_ID=:supervisorId ";
		}
		queryString += " and u.status not in ('OffBoard', 'No Show', 'Open', 'Selected', 'Deleted', 'Induction')	and u.id not in (select USER_ID from PTS_USER_ROLE  where TYPE_ID =6) left outer join PTS_USER_TIMESHEET ts on 	ts.USER_ID =u.id and ts.USER_ID = us.USER_ID 	 "
				+ " and (ts.WEEKENDING_DATE >= :selectedDate	and ts.WEEKENDING_DATE <= :selectedToDate) &tmNw &tmStab &projectManager "
				+ " and ts.USER_ID not in (select USER_ID from PTS_USER_ROLE  where TYPE_ID =6) 	left outer join PTS_NETWORK_CODES nc on	ts.NETWORK_CODE_ID = nc.id  &nw &stable ";

		if (userId != null && userId > 0) {
			queryString += " and u.id=:userId ";
		}
		if (networkCodeId != null && networkCodeId.longValue() > 0) {
			queryString = queryString.replaceAll("&tmNw", "and ts.NETWORK_CODE_ID =:nw");
		} else {
			queryString = queryString.replaceAll("&tmNw", " ");
		}
		if (stableTeamid > 0) {
			queryString = queryString.replaceAll("&tmStab",
					"and ts.NETWORK_CODE_ID in (select id from PTS_NETWORK_CODES where stable_teams =:stable)");
		} else {
			queryString = queryString.replaceAll("&tmStab", " ");
		}
		if (projectManagerId != null && projectManagerId > 0) {
			queryString = queryString.replaceAll("&projectManager",
					"and ts.NETWORK_CODE_ID in (select id from PTS_NETWORK_CODES where project_manager=:projectmanager) ");
		} else {
			queryString = queryString.replaceAll("&projectManager", " ");
		}
		if (networkCodeId != null && networkCodeId.longValue() > 0) {
			queryString = queryString.replaceAll("&nw", "and nc.id =:nw");
		} else {
			queryString = queryString.replaceAll("&nw", " ");
		}
		if (stableTeamid > 0) {
			queryString = queryString.replaceAll("&stable", " and stable_teams =:stable ");
		} else {
			queryString = queryString.replaceAll("&stable", " ");
		}

		if (weeksList.size() >= 2) {
			queryString = queryString.replaceAll(":selectedDate", "'" + weeksList.get(0) + "'");
			queryString = queryString.replaceAll(":selectedToDate", "'" + weeksList.get(weeksList.size() - 1) + "'");

		} else {
			queryString = queryString.replaceAll(":selectedDate", "'" + weeksList.get(0) + "'");
			queryString = queryString.replaceAll(":selectedToDate", "'" + weeksList.get(0) + "'");
		}

		queryString += "  group by ts.WEEKENDING_DATE, u.id,ts.NETWORK_CODE_ID  order by us.SUPERVISOR_ID,resourceName, weekEndingDate	)T  ";

		if (supervisorId == null && userId != null && userId > 0) {
			queryString = queryString.replaceAll("&sp1",
					"(select supervisor_id from PTS_USER_SUPERVISOR where USER_ID=:userId)");

		} else if (supervisorId != null && (userId == null || (userId != null && userId <= 0))) {
			queryString = queryString.replaceAll("&sp1", ":supervisorId");
		}

		if (supervisorId == null && userId != null && userId > 0) {
			queryString = queryString.replaceAll("&sp2", "  and u.id=:userId");

		} else if (supervisorId != null && (userId == null || (userId != null && userId <= 0))) {
			queryString = queryString.replaceAll("&sp2", " ");
		}

		if (delinquentEntries) {
			queryString += "  where T.effort=0.0";
		}

		if (!delinquentEntries) {
			queryString += " where  T.effort > 0 ";
		}

		Query query = getSession().createSQLQuery(queryString).addScalar("id", new StringType())
				.addScalar("weekEndingDate", new StringType()).addScalar("signum", new StringType())
				.addScalar("parseWeekEndingDate", new StringType()).addScalar("effort", new FloatType())
				.addScalar("resourceName", new StringType()).addScalar("networkCodeName", new StringType())
				.addScalar("activityCodeName", new StringType()).addScalar("supervisorName", new StringType())
				.addScalar("email", new StringType()).addScalar("supervisorId", new LongType())
				.addScalar("resourceType", new StringType()).addScalar("nwStatus", new StringType());
		if (projectManagerId != null && projectManagerId > 0) {
			query.setLong("projectmanager", projectManagerId);
		}
		if (stableTeamid > 0) {
			query.setLong("stable", stableTeamid);
		}
		if (networkCodeId != null && networkCodeId > 0) {
			query.setLong("nw", networkCodeId);
		}
		/*
		 * if (fromDate != null && !fromDate.equals("")) {
		 * query.setString("selectedDate", fromDate); } if (toDate != null &&
		 * !toDate.equals("")) { query.setString("selectedToDate", toDate); }
		 */
		if (userId != null && userId > 0) {
			query.setLong("userId", userId);
		}
		if (supervisorId != null) {
			query.setLong("supervisorId", supervisorId);
		}

		List<Utilization> networkCodeEffortList = query
				.setResultTransformer(Transformers.aliasToBean(Utilization.class)).list();

		return networkCodeEffortList.size();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ResourceEffort> getResourceUtilizationDetails(long supervisorId, String fromDate, String toDate,
			Long userId, String reporteeType) throws Throwable {

		String queryString = "select COALESCE((ts.mon_hrs+ts.tue_hrs+ts.wed_hrs+ts.thu_hrs+ts.fri_hrs+ts.sat_hrs+ts.sun_hrs),0.0)   as networkCodeHrs, "
				+ "nc.NETWORK_CODE_ID as networkCodeName, concat(nc.RELEASE_ID ,' ', nc.RELEASE_NAME) as releaseName, "
				+ "COALESCE(DATE_FORMAT(ts.WEEKENDING_DATE, '%m/%d/%Y') ,DATE_FORMAT(:selectedDate, '%m/%d/%Y')) as weekEndingDate, "
				+ "u.name as resourceName, ut.USER_TYPE as resourceType, "
				+ "(select name from PTS_USER where id=us.SUPERVISOR_ID) as supervisorName , "
				+ "ac.ACTIVITY_CODE_ID as activityCode, ac.ACTIVITY_CODE_NAME as activityCodeName, ts.type as type "
				+ "from "
				+ "PTS_USER u inner join PTS_USER_SUPERVISOR us on us.user_id=u.id and us.SUPERVISOR_ID=:supervisorId "
				+ "INNER JOIN PTS_USER_ROLE ur on u.id= ur.user_id  INNER JOIN PTS_USER_TYPES ut on ur.TYPE_ID=ut.id  LEFT OUTER JOIN PTS_USER_TIMESHEET ts on ts.user_id=u.id and (ts.WEEKENDING_DATE >=:selectedDate and  ts.WEEKENDING_DATE <=:selectedToDate) "
				+ "LEFT OUTER JOIN PTS_NETWORK_CODES nc on ts.NETWORK_CODE_ID=nc.id LEFT OUTER JOIN  PTS_ACTIVITY_CODES ac ON ac.id=ts.ACTIVITY_CODE_ID "
				+ "WHERE " + "u.status not in ('OffBoard','No Show', 'Open','Selected','Induction','Deleted') ";

		/*
		 * f(selectedDate != null && !selectedDate.equals("")) { queryString =
		 * queryString +
		 * " and (ts.WEEKENDING_DATE=:selectedDate || ts.WEEKENDING_DATE is null) " ; }
		 */
		if (userId != null) {
			queryString = queryString + " and u.id=:userId ";
		}
		queryString = "select * from (" + queryString + ") temptable order by resourceName, weekEndingDate";

		Query query = getSession().createSQLQuery(queryString).addScalar("networkCodeName", new StringType())
				.addScalar("activityCode", new StringType()).addScalar("activityCodeName", new StringType())
				.addScalar("releaseName", new StringType()).addScalar("type", new StringType())
				.addScalar("resourceName", new StringType()).addScalar("resourceType", new StringType())
				.addScalar("networkCodeHrs", new FloatType()).addScalar("supervisorName", new StringType())
				.addScalar("type", new StringType()).addScalar("weekEndingDate", new StringType());

		query.setLong("supervisorId", supervisorId);
		if (fromDate != null && !fromDate.equals("")) {
			query.setString("selectedDate", fromDate);
		}
		if (toDate != null && !toDate.equals("")) {
			query.setString("selectedToDate", toDate);
		}
		if (userId != null) {
			query.setLong("userId", userId);
		}
		List<ResourceEffort> networkCodeEffortList = query
				.setResultTransformer(Transformers.aliasToBean(ResourceEffort.class)).list();
		return networkCodeEffortList;
	}

	@Override
	public int getNCUtilizationCount(long userId, Long networkId, String fromDate, String toDate,
			String nextWeekOfFromDate, String prevWeekOfToDate, String startDay, String endDay, boolean isAdmin)
			throws Throwable {
		int count = 0;
		String queryString = "";
		if ((fromDate != null && !fromDate.equals("")) && (toDate == null || (toDate != null && toDate.equals("")))
				&& (startDay != null && !startDay.equals(""))) {
			queryString = queryString + " select count(*) "
					+ " from PTS_NETWORK_CODES nc inner  join PTS_USER_TIMESHEET t on nc.id= t.network_code_id  "
					+ " inner  join PTS_ACTIVITY_CODES ac on ac.id=t.activity_code_id where nc.status <> 'DELETED' and   t.WEEKENDING_DATE = '"
					+ fromDate + "' ";
			if (startDay != null && !startDay.equals("")) {
				queryString = queryString + " and t." + startDay + "_hrs <> 0.0 ";
			}
			if (endDay != null && !endDay.equals("")) {

			}
		}
		if ((nextWeekOfFromDate != null && !nextWeekOfFromDate.equals(""))
				&& (prevWeekOfToDate != null && !prevWeekOfToDate.equals(""))) {
			queryString = queryString + " select count(*) "
					+ " from PTS_NETWORK_CODES nc inner  join PTS_USER_TIMESHEET t on nc.id= t.network_code_id  "
					+ " inner  join PTS_ACTIVITY_CODES ac on ac.id=t.activity_code_id where nc.status <> 'DELETED' "
					+ " and t.WEEKENDING_DATE >=:selectedDate and  t.WEEKENDING_DATE <=:selectedToDate ";
		}

		if (networkId != null && networkId != 0l) {
			queryString = queryString + " and nc.id =:nid ";
		}

		Query query = getSession().createSQLQuery(queryString);

		if ((nextWeekOfFromDate != null && !nextWeekOfFromDate.equals(""))
				&& (prevWeekOfToDate != null && !prevWeekOfToDate.equals(""))) {
			query.setString("selectedDate", nextWeekOfFromDate);
			query.setString("selectedToDate", prevWeekOfToDate);
		}
		if (networkId != null && networkId != 0l) {
			query.setLong("id", networkId);
		}

		BigInteger obj = (BigInteger) query.uniqueResult();
		if (obj != null) {
			count = obj.intValue();
		}
		return count;

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<NetworkCodeEffort> getNCUtilizationSummary(long userId, Long networkId, String fromDate, String toDate,
			String nextWeekOfFromDate, String prevWeekOfToDate, String startDay, String endDay, Pagination pagination,
			boolean isAdmin) throws Throwable {

		String queryString = "";

		queryString = queryString
				+ "select distinct nc.id as id, CASE WHEN nc.network_code_id is null THEN concat(nc.RELEASE_ID, ' - ',nc.RELEASE_NAME) else concat(nc.network_code_id, ' - ' , nc.RELEASE_ID, ' - ',nc.RELEASE_NAME) END as NETWORKCODE, concat(ac.ACTIVITY_CODE_ID, ' - ' ,ac.ACTIVITY_CODE_NAME) as ACTIVITYCODE, "
				+ " nc.effort EFFORT, (sum(t.mon_hrs)+ sum(t.tue_hrs) + sum(t.wed_hrs) + sum(t.thu_hrs)+ sum(t.fri_hrs)+ sum(t.sat_hrs)+ sum(t.sun_hrs)) as SUMMATION "
				+ " from PTS_NETWORK_CODES nc inner  join PTS_USER_TIMESHEET t on nc.id= t.network_code_id  "
				+ " inner  join PTS_ACTIVITY_CODES ac on ac.id=t.activity_code_id "
				+ " where t.WEEKENDING_DATE >=:selectedDate and  t.WEEKENDING_DATE <=:selectedToDate "
				+ " group by t.network_code_id, ac.ACTIVITY_CODE_ID";
		Query query = getSession().createSQLQuery(queryString).addScalar("SUMMATION", new FloatType())
				.addScalar("EFFORT", new FloatType()).addScalar("NETWORKCODE", new StringType())
				.addScalar("id", new StringType());

		if (nextWeekOfFromDate != null && !nextWeekOfFromDate.equals("")) {
			query.setString("selectedDate", nextWeekOfFromDate);
		}
		if (prevWeekOfToDate != null && !prevWeekOfToDate.equals("")) {
			query.setString("selectedToDate", prevWeekOfToDate);
		}
		if (pagination != null) {
			if (pagination.getOffset() > 0)
				query.setFirstResult(pagination.getOffset());
			if (pagination.getSize() > 0)
				query.setMaxResults(pagination.getSize());
		}
		List<NetworkCodeEffort> networkCodeEffortList = query
				.setResultTransformer(Transformers.aliasToBean(NetworkCodeEffort.class)).list();
		return networkCodeEffortList;

	}

	@SuppressWarnings("unchecked")
	@Override
	public void getMonthlyNCUsageByUser(String startDay, String endDay, String startWeek, String endWeek, Long userId,
			String month, int year, List<UserCapacity> userCapacity, Long ncId) throws Throwable {

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

	/*
	 * select tmp.name, tmp.NETWORKCODE, tmp.ACTIVITYCODE,sum(tmp.SUMMATION) as
	 * CHARGED_HRS , 'February' as MONTH, tmp.Supervisor from (select distinct
	 * u.name, concat(nc.network_code_id, ' - ' , nc.RELEASE_ID, ' -
	 * ',nc.RELEASE_NAME) as NETWORKCODE, concat(ac.ACTIVITY_CODE_ID, ' - '
	 * ,ac.ACTIVITY_CODE_NAME) as ACTIVITYCODE, (sum(t.thu_hrs)+ sum(t.fri_hrs)+
	 * sum(t.sat_hrs)+ sum(t.sun_hrs)) as SUMMATION , t.WEEKENDING_DATE, t.user_id,
	 * t.network_code_id, (select name from PTS_USER where id=us.SUPERVISOR_ID) as
	 * Supervisor from PTS_NETWORK_CODES nc inner join PTS_USER_TIMESHEET t on
	 * nc.id= t.network_code_id inner join PTS_ACTIVITY_CODES ac on
	 * ac.id=t.activity_code_id inner join PTS_USER u on u.id=t.user_id inner join
	 * PTS_USER_SUPERVISOR us on u.id=us.user_id where t.WEEKENDING_DATE =
	 * '2018-02-02' group by t.user_id, t.network_code_id, t.WEEKENDING_DATE union
	 * select distinct u.name, concat(nc.network_code_id, ' - ' , nc.RELEASE_ID, ' -
	 * ',nc.RELEASE_NAME) as NETWORKCODE, concat(ac.ACTIVITY_CODE_ID, ' - '
	 * ,ac.ACTIVITY_CODE_NAME) as ACTIVITYCODE, (sum(t.mon_hrs)+ sum(t.tue_hrs) +
	 * sum(t.wed_hrs) + sum(t.thu_hrs)+ sum(t.fri_hrs)+ sum(t.sat_hrs)+
	 * sum(t.sun_hrs)) as SUMMATION , t.WEEKENDING_DATE, t.user_id,
	 * t.network_code_id, (select name from PTS_USER where id=us.SUPERVISOR_ID) as
	 * Supervisor from PTS_NETWORK_CODES nc inner join PTS_USER_TIMESHEET t on
	 * nc.id= t.network_code_id inner join PTS_ACTIVITY_CODES ac on
	 * ac.id=t.activity_code_id inner join PTS_USER u on u.id=t.user_id inner join
	 * PTS_USER_SUPERVISOR us on u.id=us.user_id where t.WEEKENDING_DATE >
	 * '2018-02-02' and t.WEEKENDING_DATE < '2018-03-02' group by t.user_id,
	 * t.network_code_id, t.WEEKENDING_DATE union select distinct u.name,
	 * concat(nc.network_code_id, ' - ' , nc.RELEASE_ID, ' - ',nc.RELEASE_NAME) as
	 * NETWORKCODE, concat(ac.ACTIVITY_CODE_ID, ' - ' ,ac.ACTIVITY_CODE_NAME) as
	 * ACTIVITYCODE, (sum(t.mon_hrs)+ sum(t.tue_hrs) + sum(t.wed_hrs)) as SUMMATION
	 * , t.WEEKENDING_DATE, t.user_id, t.network_code_id, (select name from PTS_USER
	 * where id=us.SUPERVISOR_ID) as Supervisor from PTS_NETWORK_CODES nc inner join
	 * PTS_USER_TIMESHEET t on nc.id= t.network_code_id inner join
	 * PTS_ACTIVITY_CODES ac on ac.id=t.activity_code_id inner join PTS_USER u on
	 * u.id=t.user_id inner join PTS_USER_SUPERVISOR us on u.id=us.user_id where
	 * t.WEEKENDING_DATE = '2018-03-02' group by t.user_id, t.network_code_id,
	 * t.WEEKENDING_DATE) tmp group by tmp.user_id, tmp.network_code_id order by
	 * tmp.name, tmp.WEEKENDING_DATE
	 */

	@Override
	public int getRICOUserUtilizationCount(boolean userFlag, Long userId, String startWeek, String endWeek,
			String startDay, String endDay, Long projectId) throws Throwable {
		int count = 0;
		String queryStr = "";
		String startDayQryStr = "";
		String endDayQryStr = "";
		List<Long> userIdList = new ArrayList<Long>();
		if (userFlag && userId != null) {
			Query queryProc = getSession().createSQLQuery("CALL user_id_heirarchy_proc(:id)").setParameter("id",
					userId);

			queryProc.executeUpdate();

			Query tmpQry = getSession().createSQLQuery("SELECT node FROM _result");
			userIdList = tmpQry.list();

		} else {
			userIdList.add(userId);
		}

		if (startWeek.equalsIgnoreCase(endWeek)) {
			queryStr = "select  count(*) from " + " ( "
					+ " select :startsummation, user_id, WEEKENDING_DATE, NETWORK_CODE_ID, ACTIVITY_CODE_ID, type from PTS_USER_TIMESHEET where  &nwCond  WEEKENDING_DATE = '"
					+ startWeek + "'  ) t inner join PTS_USER u on t.user_id=u.id "
					+ " where t.effort <> 0 :userIdCond order by u.name, WEEKENDING_DATE asc";
			if (startDay.equalsIgnoreCase(endDay)) {
				switch (startDay) {
				case "Mon":
					startDayQryStr = "(mon_hrs) as EFFORT ";
					break;
				case "Tue":
					startDayQryStr = "(tue_hrs) as EFFORT ";
					break;
				case "Wed":
					startDayQryStr = "(wed_hrs) as EFFORT ";
					break;
				case "Thu":
					startDayQryStr = "(thu_hrs) as EFFORT ";
					break;
				case "Fri":
					startDayQryStr = "(fri_hrs) as EFFORT ";
					break;
				case "Sat":
					startDayQryStr = "(sat_hrs) as EFFORT ";
					break;
				case "Sun":
					startDayQryStr = "(sun_hrs) as EFFORT ";
					break;
				}
			} else {
				switch (startDay) {
				case "Mon":
					if (endDay.equalsIgnoreCase("Tue")) {
						startDayQryStr = "(mon_hrs+ tue_hrs) as EFFORT ";
					} else if (endDay.equalsIgnoreCase("Wed")) {
						startDayQryStr = "(mon_hrs+ tue_hrs + wed_hrs) as EFFORT ";
					} else if (endDay.equalsIgnoreCase("Thu")) {
						startDayQryStr = "(mon_hrs+ tue_hrs + wed_hrs + thu_hrs) as EFFORT ";
					} else if (endDay.equalsIgnoreCase("Fri")) {
						startDayQryStr = "(mon_hrs+ tue_hrs + wed_hrs + thu_hrs + fri_hrs) as EFFORT ";
					} else if (endDay.equalsIgnoreCase("Sat")) {
						startDayQryStr = "(mon_hrs+ tue_hrs + wed_hrs + thu_hrs + fri_hrs + sat_hrs) as EFFORT ";
					} else if (endDay.equalsIgnoreCase("Sun")) {
						startDayQryStr = "(mon_hrs+ tue_hrs + wed_hrs + thu_hrs + fri_hrs + sat_hrs + sun_hrs) as EFFORT ";
					}
					break;
				case "Tue":
					if (endDay.equalsIgnoreCase("Wed")) {
						startDayQryStr = "(tue_hrs + wed_hrs) as EFFORT ";
					} else if (endDay.equalsIgnoreCase("Thu")) {
						startDayQryStr = "(tue_hrs + wed_hrs + thu_hrs) as EFFORT ";
					} else if (endDay.equalsIgnoreCase("Fri")) {
						startDayQryStr = "(tue_hrs + wed_hrs + thu_hrs + fri_hrs) as EFFORT ";
					} else if (endDay.equalsIgnoreCase("Sat")) {
						startDayQryStr = "(tue_hrs + wed_hrs + thu_hrs + fri_hrs + sat_hrs) as EFFORT ";
					} else if (endDay.equalsIgnoreCase("Sun")) {
						startDayQryStr = "(tue_hrs + wed_hrs + thu_hrs + fri_hrs + sat_hrs + sun_hrs) as EFFORT ";
					}
					break;
				case "Wed":
					if (endDay.equalsIgnoreCase("Thu")) {
						startDayQryStr = "(wed_hrs + thu_hrs) as EFFORT ";
					} else if (endDay.equalsIgnoreCase("Fri")) {
						startDayQryStr = "(wed_hrs + thu_hrs + fri_hrs) as EFFORT ";
					} else if (endDay.equalsIgnoreCase("Sat")) {
						startDayQryStr = "(wed_hrs + thu_hrs + fri_hrs + sat_hrs) as EFFORT ";
					} else if (endDay.equalsIgnoreCase("Sun")) {
						startDayQryStr = "(wed_hrs + thu_hrs + fri_hrs + sat_hrs + sun_hrs) as EFFORT ";
					}
					break;
				case "Thu":
					if (endDay.equalsIgnoreCase("Fri")) {
						startDayQryStr = "(thu_hrs + fri_hrs) as EFFORT ";
					} else if (endDay.equalsIgnoreCase("Sat")) {
						startDayQryStr = "(thu_hrs + fri_hrs + sat_hrs) as EFFORT ";
					} else if (endDay.equalsIgnoreCase("Sun")) {
						startDayQryStr = "(thu_hrs + fri_hrs + sat_hrs + sun_hrs) as EFFORT ";
					}
					break;
				case "Fri":
					if (endDay.equalsIgnoreCase("Sat")) {
						startDayQryStr = "(fri_hrs + sat_hrs) as EFFORT ";
					} else if (endDay.equalsIgnoreCase("Sun")) {
						startDayQryStr = "(fri_hrs + sat_hrs + sun_hrs) as EFFORT ";
					}
					break;
				case "Sat":
					if (endDay.equalsIgnoreCase("Sun")) {
						startDayQryStr = "(sat_hrs + sun_hrs) as EFFORT ";
					}
					break;
				}
			}
		} else {
			queryStr = "select  count(*) from " + " ( "
					+ " select :startsummation, user_id, WEEKENDING_DATE, NETWORK_CODE_ID, ACTIVITY_CODE_ID, type from PTS_USER_TIMESHEET where  &nwCond  WEEKENDING_DATE = '"
					+ startWeek + "' " + " union all "
					+ " select (MON_HRS+TUE_HRS+WED_HRS+THU_HRS+FRI_HRS+SAT_HRS+SUN_HRS) EFFORT, user_id, WEEKENDING_DATE, NETWORK_CODE_ID, ACTIVITY_CODE_ID, type from PTS_USER_TIMESHEET where &nwCond WEEKENDING_DATE > '"
					+ startWeek + "'  and WEEKENDING_DATE < '" + endWeek + "' " + " union all "
					+ " select :endsummation, user_id, WEEKENDING_DATE, NETWORK_CODE_ID, ACTIVITY_CODE_ID, type from PTS_USER_TIMESHEET where  &nwCond  WEEKENDING_DATE = '"
					+ endWeek + "' ) t inner join PTS_USER u on t.user_id=u.id "
					+ " where t.effort <> 0 :userIdCond order by u.name, WEEKENDING_DATE asc";

			switch (startDay) {
			case "Mon":
				startDayQryStr = "(mon_hrs+ tue_hrs + wed_hrs + thu_hrs+ fri_hrs+ sat_hrs+ sun_hrs) as EFFORT ";
				break;
			case "Tue":
				startDayQryStr = "(tue_hrs + wed_hrs + thu_hrs+ fri_hrs+ sat_hrs+ sun_hrs) as EFFORT ";
				break;
			case "Wed":
				startDayQryStr = "(wed_hrs + thu_hrs+ fri_hrs+ sat_hrs+ sun_hrs) as EFFORT ";
				break;
			case "Thu":
				startDayQryStr = "(thu_hrs+ fri_hrs+ sat_hrs+ sun_hrs) as EFFORT ";
				break;
			case "Fri":
				startDayQryStr = "(fri_hrs+ sat_hrs+ sun_hrs) as EFFORT ";
				break;
			case "Sat":
				startDayQryStr = "(sat_hrs+ sun_hrs) as EFFORT ";
				break;
			case "Sun":
				startDayQryStr = "(sun_hrs) as EFFORT ";
				break;
			}

			switch (endDay) {
			case "Mon":
				endDayQryStr = "(mon_hrs) as EFFORT ";
				break;
			case "Tue":
				endDayQryStr = "(mon_hrs+ tue_hrs) as EFFORT ";
				break;
			case "Wed":
				endDayQryStr = "(mon_hrs+ tue_hrs + wed_hrs) as EFFORT ";
				break;
			case "Thu":
				endDayQryStr = "(mon_hrs+ tue_hrs + wed_hrs + thu_hrs) as EFFORT ";
				break;
			case "Fri":
				endDayQryStr = "(mon_hrs+ tue_hrs + wed_hrs + thu_hrs+ fri_hrs) as EFFORT ";
				break;
			case "Sat":
				endDayQryStr = "(mon_hrs+ tue_hrs + wed_hrs + thu_hrs+ fri_hrs+ sat_hrs) as EFFORT ";
				break;
			case "Sun":
				endDayQryStr = "(mon_hrs+ tue_hrs + wed_hrs + thu_hrs+ fri_hrs+ sat_hrs+ sun_hrs) as EFFORT ";
				break;
			}
		}

		if (projectId != null && projectId > 0) {
			queryStr = queryStr.replaceAll("&nwCond",
					"  NETWORK_CODE_ID in (select id from PTS_NETWORK_CODES where project_manager=" + projectId
							+ ") and ");
		} else {
			queryStr = queryStr.replaceAll("&nwCond", "  ");
		}

		String userIdCond = "";
		if (userId != null && userId != 0l) {
			userIdCond = " and u.id in(:users) ";
		}
		String finalQryStr = queryStr.replaceAll(":startsummation", startDayQryStr)
				.replaceAll(":endsummation", endDayQryStr).replaceAll(":userIdCond", userIdCond);
		Query query = getSession().createSQLQuery(finalQryStr);
		if (userId != null && userId != 0l) {
			query.setParameterList("users", userIdList);
		}
		BigInteger obj = (BigInteger) query.uniqueResult();
		if (obj != null) {
			count = obj.intValue();
		}
		return count;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Utilization> getRICOUserUtilizationSummary(boolean userFlag, Long userId, String startWeek,
			String endWeek, String startDay, String endDay, Pagination pagination, Long projectId) {
		String queryStr = "";
		String startDayQryStr = "";
		String endDayQryStr = "";
		List<Long> userIdList = new ArrayList<Long>();
		if (userFlag && userId != null) {
			Query queryProc = getSession().createSQLQuery("CALL user_id_heirarchy_proc(:id)").setParameter("id",
					userId);

			queryProc.executeUpdate();

			Query tmpQry = getSession().createSQLQuery("SELECT node FROM _result");
			userIdList = tmpQry.list();

		} else {
			userIdList.add(userId);
		}

		if (startWeek.equalsIgnoreCase(endWeek)) {
			queryStr = "select  DATE_FORMAT(WEEKENDING_DATE, '%m/%d/%Y') weekEndingDate,u.name resourceName, u.USERNAME signum, "
					+ " (select name from PTS_USER where id in (select distinct tus.supervisor_id from PTS_USER_SUPERVISOR tus where tus.user_id=t.user_id)) supervisorName,(select  (select name from PTS_USER where id=project_manager) from PTS_NETWORK_CODES n where n.id=t.NETWORK_CODE_ID) 'projectManager' , "
					+ " (select CASE WHEN network_code_id is null THEN concat(RELEASE_ID, ' - ',RELEASE_NAME) else concat(network_code_id, ' - ' , RELEASE_ID, ' - ',RELEASE_NAME) END  from PTS_NETWORK_CODES where id=t.NETWORK_CODE_ID) networkCodeName, "
					+ " (select ACTIVITY_CODE_NAME from PTS_ACTIVITY_CODES where id=t.ACTIVITY_CODE_ID) activityCodeName,   type type, t.STATUS timesheetStatus, "
					+ " EFFORT from " + " ( "
					+ " select :startsummation, user_id, WEEKENDING_DATE, NETWORK_CODE_ID, ACTIVITY_CODE_ID, type, STATUS from PTS_USER_TIMESHEET where  &nwCond WEEKENDING_DATE = '"
					+ startWeek + "' ) t inner join PTS_USER u on t.user_id=u.id "
					+ " where t.effort <> 0 :userIdCond order by u.name, WEEKENDING_DATE asc";

			if (startDay.equalsIgnoreCase(endDay)) {
				switch (startDay) {
				case "Mon":
					startDayQryStr = "(mon_hrs) as EFFORT ";
					break;
				case "Tue":
					startDayQryStr = "(tue_hrs) as EFFORT ";
					break;
				case "Wed":
					startDayQryStr = "(wed_hrs) as EFFORT ";
					break;
				case "Thu":
					startDayQryStr = "(thu_hrs) as EFFORT ";
					break;
				case "Fri":
					startDayQryStr = "(fri_hrs) as EFFORT ";
					break;
				case "Sat":
					startDayQryStr = "(sat_hrs) as EFFORT ";
					break;
				case "Sun":
					startDayQryStr = "(sun_hrs) as EFFORT ";
					break;
				}
			} else {
				switch (startDay) {
				case "Mon":
					if (endDay.equalsIgnoreCase("Tue")) {
						startDayQryStr = "(mon_hrs+ tue_hrs) as EFFORT ";
					} else if (endDay.equalsIgnoreCase("Wed")) {
						startDayQryStr = "(mon_hrs+ tue_hrs + wed_hrs) as EFFORT ";
					} else if (endDay.equalsIgnoreCase("Thu")) {
						startDayQryStr = "(mon_hrs+ tue_hrs + wed_hrs + thu_hrs) as EFFORT ";
					} else if (endDay.equalsIgnoreCase("Fri")) {
						startDayQryStr = "(mon_hrs+ tue_hrs + wed_hrs + thu_hrs + fri_hrs) as EFFORT ";
					} else if (endDay.equalsIgnoreCase("Sat")) {
						startDayQryStr = "(mon_hrs+ tue_hrs + wed_hrs + thu_hrs + fri_hrs + sat_hrs) as EFFORT ";
					} else if (endDay.equalsIgnoreCase("Sun")) {
						startDayQryStr = "(mon_hrs+ tue_hrs + wed_hrs + thu_hrs + fri_hrs + sat_hrs + sun_hrs) as EFFORT ";
					}
					break;
				case "Tue":
					if (endDay.equalsIgnoreCase("Wed")) {
						startDayQryStr = "(tue_hrs + wed_hrs) as EFFORT ";
					} else if (endDay.equalsIgnoreCase("Thu")) {
						startDayQryStr = "(tue_hrs + wed_hrs + thu_hrs) as EFFORT ";
					} else if (endDay.equalsIgnoreCase("Fri")) {
						startDayQryStr = "(tue_hrs + wed_hrs + thu_hrs + fri_hrs) as EFFORT ";
					} else if (endDay.equalsIgnoreCase("Sat")) {
						startDayQryStr = "(tue_hrs + wed_hrs + thu_hrs + fri_hrs + sat_hrs) as EFFORT ";
					} else if (endDay.equalsIgnoreCase("Sun")) {
						startDayQryStr = "(tue_hrs + wed_hrs + thu_hrs + fri_hrs + sat_hrs + sun_hrs) as EFFORT ";
					}
					break;
				case "Wed":
					if (endDay.equalsIgnoreCase("Thu")) {
						startDayQryStr = "(wed_hrs + thu_hrs) as EFFORT ";
					} else if (endDay.equalsIgnoreCase("Fri")) {
						startDayQryStr = "(wed_hrs + thu_hrs + fri_hrs) as EFFORT ";
					} else if (endDay.equalsIgnoreCase("Sat")) {
						startDayQryStr = "(wed_hrs + thu_hrs + fri_hrs + sat_hrs) as EFFORT ";
					} else if (endDay.equalsIgnoreCase("Sun")) {
						startDayQryStr = "(wed_hrs + thu_hrs + fri_hrs + sat_hrs + sun_hrs) as EFFORT ";
					}
					break;
				case "Thu":
					if (endDay.equalsIgnoreCase("Fri")) {
						startDayQryStr = "(thu_hrs + fri_hrs) as EFFORT ";
					} else if (endDay.equalsIgnoreCase("Sat")) {
						startDayQryStr = "(thu_hrs + fri_hrs + sat_hrs) as EFFORT ";
					} else if (endDay.equalsIgnoreCase("Sun")) {
						startDayQryStr = "(thu_hrs + fri_hrs + sat_hrs + sun_hrs) as EFFORT ";
					}
					break;
				case "Fri":
					if (endDay.equalsIgnoreCase("Sat")) {
						startDayQryStr = "(fri_hrs + sat_hrs) as EFFORT ";
					} else if (endDay.equalsIgnoreCase("Sun")) {
						startDayQryStr = "(fri_hrs + sat_hrs + sun_hrs) as EFFORT ";
					}
					break;
				case "Sat":
					if (endDay.equalsIgnoreCase("Sun")) {
						startDayQryStr = "(sat_hrs + sun_hrs) as EFFORT ";
					}
					break;
				}
			}
		} else {
			queryStr = "select  DATE_FORMAT(WEEKENDING_DATE, '%m/%d/%Y') weekEndingDate,u.name resourceName, u.USERNAME signum, "
					+ " (select name from PTS_USER where id in (select distinct tus.supervisor_id from PTS_USER_SUPERVISOR tus where tus.user_id=t.user_id)) supervisorName,(select  (select name from PTS_USER where id=project_manager) from PTS_NETWORK_CODES n where n.id=t.NETWORK_CODE_ID) 'projectManager' , "
					+ " (select CASE WHEN network_code_id is null THEN concat(RELEASE_ID, ' - ',RELEASE_NAME) else concat(network_code_id, ' - ' , RELEASE_ID, ' - ',RELEASE_NAME) END  from PTS_NETWORK_CODES where id=t.NETWORK_CODE_ID) networkCodeName, "
					+ " (select ACTIVITY_CODE_NAME from PTS_ACTIVITY_CODES where id=t.ACTIVITY_CODE_ID) activityCodeName,   type type, t.STATUS timesheetStatus, "
					+ " EFFORT from " + " ( "
					+ " select :startsummation, user_id, WEEKENDING_DATE, NETWORK_CODE_ID, ACTIVITY_CODE_ID, type, STATUS from PTS_USER_TIMESHEET where  &nwCond WEEKENDING_DATE = '"
					+ startWeek + "' " + " union all "
					+ " select (MON_HRS+TUE_HRS+WED_HRS+THU_HRS+FRI_HRS+SAT_HRS+SUN_HRS) EFFORT, user_id, WEEKENDING_DATE, NETWORK_CODE_ID, ACTIVITY_CODE_ID, type, STATUS from PTS_USER_TIMESHEET where &nwCond WEEKENDING_DATE > '"
					+ startWeek + "'  and WEEKENDING_DATE < '" + endWeek + "' " + " union all "
					+ " select :endsummation, user_id, WEEKENDING_DATE, NETWORK_CODE_ID, ACTIVITY_CODE_ID, type, STATUS from PTS_USER_TIMESHEET where  &nwCond  WEEKENDING_DATE = '"
					+ endWeek + "' ) t inner join PTS_USER u on t.user_id=u.id "
					+ " where t.effort <> 0 :userIdCond order by u.name, WEEKENDING_DATE asc";

			switch (startDay) {
			case "Mon":
				startDayQryStr = "(mon_hrs+ tue_hrs + wed_hrs + thu_hrs+ fri_hrs+ sat_hrs+ sun_hrs) as EFFORT ";
				break;
			case "Tue":
				startDayQryStr = "(tue_hrs + wed_hrs + thu_hrs+ fri_hrs+ sat_hrs+ sun_hrs) as EFFORT ";
				break;
			case "Wed":
				startDayQryStr = "(wed_hrs + thu_hrs+ fri_hrs+ sat_hrs+ sun_hrs) as EFFORT ";
				break;
			case "Thu":
				startDayQryStr = "(thu_hrs+ fri_hrs+ sat_hrs+ sun_hrs) as EFFORT ";
				break;
			case "Fri":
				startDayQryStr = "(fri_hrs+ sat_hrs+ sun_hrs) as EFFORT ";
				break;
			case "Sat":
				startDayQryStr = "(sat_hrs+ sun_hrs) as EFFORT ";
				break;
			case "Sun":
				startDayQryStr = "(sun_hrs) as EFFORT ";
				break;
			}

			switch (endDay) {
			case "Mon":
				endDayQryStr = "(mon_hrs) as EFFORT ";
				break;
			case "Tue":
				endDayQryStr = "(mon_hrs+ tue_hrs) as EFFORT ";
				break;
			case "Wed":
				endDayQryStr = "(mon_hrs+ tue_hrs + wed_hrs) as EFFORT ";
				break;
			case "Thu":
				endDayQryStr = "(mon_hrs+ tue_hrs + wed_hrs + thu_hrs) as EFFORT ";
				break;
			case "Fri":
				endDayQryStr = "(mon_hrs+ tue_hrs + wed_hrs + thu_hrs+ fri_hrs) as EFFORT ";
				break;
			case "Sat":
				endDayQryStr = "(mon_hrs+ tue_hrs + wed_hrs + thu_hrs+ fri_hrs+ sat_hrs) as EFFORT ";
				break;
			case "Sun":
				endDayQryStr = "(mon_hrs+ tue_hrs + wed_hrs + thu_hrs+ fri_hrs+ sat_hrs+ sun_hrs) as EFFORT ";
				break;
			}
		}

		String userIdCond = "";
		if (userId != null && userId != 0l) {
			userIdCond = " and u.id in(:users) ";
		}
		if (projectId != null && projectId > 0) {
			queryStr = queryStr.replaceAll("&nwCond",
					"   NETWORK_CODE_ID in (select id from PTS_NETWORK_CODES where project_manager=" + projectId
							+ ") and ");
		} else {
			queryStr = queryStr.replaceAll("&nwCond", "  ");
		}
		String finalQryStr = queryStr.replaceAll(":startsummation", startDayQryStr)
				.replaceAll(":endsummation", endDayQryStr).replaceAll(":userIdCond", userIdCond);
		Query query = getSession().createSQLQuery(finalQryStr).addScalar("networkCodeName", new StringType())
				.addScalar("activityCodeName", new StringType()).addScalar("type", new StringType())
				.addScalar("resourceName", new StringType()).addScalar("signum", new StringType())
				.addScalar("supervisorName", new StringType()).addScalar("effort", new FloatType())
				.addScalar("projectManager", new StringType()).addScalar("timesheetStatus", new StringType())
				.addScalar("weekEndingDate", new StringType());

		if (userId != null && userId != 0l) {
			query.setParameterList("users", userIdList);
		}
		if (pagination != null) {
			if (pagination.getOffset() > 0)
				query.setFirstResult(pagination.getOffset());
			if (pagination.getSize() > 0)
				query.setMaxResults(pagination.getSize());
		}
		List<Utilization> tmpUserCapacity = query.setResultTransformer(Transformers.aliasToBean(Utilization.class))
				.list();
		return tmpUserCapacity;
	}

	@Override
	public String getUnApprovedHours(Long supervisorId, String startWeek) throws Throwable {

		String queryString = "select sum(MON_HRS + TUE_HRS + WED_HRS+ THU_HRS+FRI_HRS+SAT_HRS+SUN_HRS) as unapprovedhrs from PTS_USER_TIMESHEET ut,  PTS_USER_SUPERVISOR us,PTS_USER u ,PTS_USER_ROLE ur  where  u.id=ut.user_id and  u.id=ut.user_id and us.SUPERVISOR_ID=:id and us.user_id=ut.user_id and ur.USER_ID=ut.user_id and ut.user_id= us.user_id and u.status  not in ('Others', 'No Show','OffBoard', 'Deleted')  and ut.STATUS = 'Pending' and ur.TYPE_ID <> 6 and ut.NETWORK_CODE_ID is not null and ut.WEEKENDING_DATE >= '"
				+ startWeek + "'";

		Query query = getSession().createSQLQuery(queryString);

		query.setLong("id", supervisorId);

		Double obj = (Double) query.uniqueResult();
		return obj == null ? "0.00" : String.format("%1.2f", obj);

	}

	@Override
	@SuppressWarnings("unchecked")
	public List<LocationUserCount> getLocationUserCnt(Long supervisorId, boolean teamFlag, String status) {
		if (managerUtilBased.equalsIgnoreCase("TRUE")) {
			SQLQuery checkManagerProc = getSession()
					.createSQLQuery("SELECT COUNT(*) FROM PTS_USER_SUPERVISOR S WHERE S.SUPERVISOR_ID=" + supervisorId);
			List test = checkManagerProc.list();
			if (test != null && test.size() > 0) {
				teamFlag = true;
			}

		}
		if (teamFlag) {
			Query queryProc = getSession().createSQLQuery("CALL user_id_heirarchy_proc(:id)").setParameter("id",
					supervisorId);

			queryProc.executeUpdate();
		}

		String qryStr = "select l.name locationName,l.id locationId, count(*) userCnt "
				+ " from PTS_USER u, PTS_USER_SUPERVISOR us, PTS_LOCATION l "
				+ " where l.id=u.location_id and u.ID=us.user_id ";

		if (status != null && !status.equalsIgnoreCase("")) {
			if (!status.equalsIgnoreCase("All")) {
				qryStr = qryStr + " and  u.status='" + status + "' ";
			}
		} else {
			qryStr = qryStr + " and status not in ('OffBoard', 'No Show', 'Deleted', 'Others', 'Notice Period') ";
		}

		if (teamFlag) {
			qryStr = qryStr + " and  u.id in (SELECT node FROM _result) group by  location_id";
		} else {
			qryStr = qryStr + " and  u.id =" + supervisorId + " group by  location_id";
		}

		Query query = getSession().createSQLQuery(qryStr).addScalar("locationName", new StringType())
				.addScalar("locationId", new LongType()).addScalar("userCnt", new IntegerType());

		List<LocationUserCount> locationUserCount = query
				.setResultTransformer(Transformers.aliasToBean(LocationUserCount.class)).list();

		if (managerUtilBased.equalsIgnoreCase("TRUE") && locationUserCount != null && locationUserCount.size() > 0) {
			locationUserCount.get(0).setTeamFlag(true);
		}

		return locationUserCount;

	}

	@Override
	@SuppressWarnings("unchecked")
	public List<LocationUserCount> getLocationUserCntNew(Long supervisorId, boolean teamFlag, int monthSelected,
			int year, String status, SearchSortContainer searchSortContainer, String role) {
		String qryStr = null;
		String resCont = "sum(resourceCount)";
		Query query = null;
		String targetSql = " sum(egiworkingHours*IF(u.resourceCount=0,1,u.resourceCount)*u.workingDays) ";
		if (searchSortContainer != null && searchSortContainer.isDetailLevel()) {
			SQLQuery checkManagerProc = getSession()
					.createSQLQuery("SELECT COUNT(*) FROM PTS_USER_SUPERVISOR S WHERE S.SUPERVISOR_ID=" + supervisorId);
			List<BigInteger> test = checkManagerProc.list();
			if (test != null && test.size() > 0 && test.get(0).longValue() > 0) {
				teamFlag = true;
				if (dashManagerUtilBased != null && dashManagerUtilBased.equalsIgnoreCase("TRUE")) {
					resCont = " sum(resourceCount)+1 ";
					targetSql = " sum(egiworkingHours*(u.resourceCount+1)*u.workingDays) ";
				}
			} else {
				teamFlag = false;
			}

		}
		if (role == null || (role != null && !role.equalsIgnoreCase("USER"))) {
			if (teamFlag) {
				qryStr = "SELECT userId,month,year, workingdays,   egiworkingHours,manaworkingHours," + resCont
						+ " userCnt , " + targetSql + " targetHrs FROM pts_manager_util u WHERE MONTH <="
						+ monthSelected + " AND YEAR=" + year + " AND userId=" + supervisorId
						+ " group by userId,month";
				query = getSession().createSQLQuery(qryStr).addScalar("userId", new LongType())
						.addScalar("userCnt", new IntegerType()).addScalar("month", new LongType())
						// .addScalar("locationId", new LongType()).addScalar("locationName", new
						// StringType())
						.addScalar("year", new LongType()).addScalar("workingdays", new DoubleType())
						.addScalar("egiworkingHours", new FloatType()).addScalar("manaworkingHours", new FloatType())
						.addScalar("targetHrs", new DoubleType());

			} else {
				qryStr = "SELECT   (SELECT l.name " + "FROM PTS_USER u, PTS_USER_SUPERVISOR us, PTS_LOCATION l  "
						+ "WHERE l.id=u.location_id AND u.ID=us.user_id AND STATUS NOT IN ('OffBoard', 'No Show', 'Deleted', 'Others', 'Notice Period')"
						+ "   and u.id =" + supervisorId + " ) locationName,(SELECT l.id   "
						+ "FROM PTS_USER u, PTS_USER_SUPERVISOR us, PTS_LOCATION l "
						+ "WHERE l.id=u.location_id AND u.ID=us.user_id AND STATUS NOT IN ('OffBoard', 'No Show', 'Deleted', 'Others','Induction')"
						+ "   and u.id =" + supervisorId + ")locationId,"
						+ "man.month, man.year, man.workingdays, man.egiworkingHours,man.manaworkingHours,"
						+ " 1 AS userCnt, sum(man.egiworkingHours*1*man.workingDays) targetHrs "
						+ "FROM pts_manager_util man WHERE man.userId=" + supervisorId + "  AND man.month ="
						+ monthSelected
						+ " AND man.locationId= (SELECT l.id   FROM PTS_USER u, PTS_USER_SUPERVISOR us, PTS_LOCATION l "
						+ " WHERE l.id=u.location_id AND u.ID=us.user_id AND STATUS NOT IN ('OffBoard', 'No Show', 'Deleted', 'Others','Induction') and u.id ="
						+ supervisorId + ")";

				query = getSession().createSQLQuery(qryStr).addScalar("userCnt", new IntegerType())
						.addScalar("month", new LongType())
						// .addScalar("locationId", new LongType()).addScalar("locationName", new
						// StringType())
						.addScalar("year", new LongType()).addScalar("workingdays", new DoubleType())
						.addScalar("egiworkingHours", new FloatType()).addScalar("manaworkingHours", new FloatType())
						.addScalar("targetHrs", new DoubleType());
			}
		} else {

			qryStr = "SELECT userId,month,year, workingdays,   egiworkingHours,manaworkingHours,1 userCnt , (egiworkingHours*1*u.workingDays) targetHrs FROM pts_manager_util u WHERE MONTH ="
					+ monthSelected + " AND YEAR=" + year
					+ " AND userId=  (SELECT supervisor_id FROM PTS_USER_SUPERVISOR WHERE user_id=" + supervisorId
					+ ") " + " group by userId,month";
			query = getSession().createSQLQuery(qryStr).addScalar("userId", new LongType())
					.addScalar("userCnt", new IntegerType()).addScalar("month", new LongType())
					// .addScalar("locationId", new LongType()).addScalar("locationName", new
					// StringType())
					.addScalar("year", new LongType()).addScalar("workingdays", new DoubleType())
					.addScalar("egiworkingHours", new FloatType()).addScalar("manaworkingHours", new FloatType())
					.addScalar("targetHrs", new DoubleType());

		}

		List<LocationUserCount> locationUserCount = query
				.setResultTransformer(Transformers.aliasToBean(LocationUserCount.class)).list();
		if (locationUserCount != null && locationUserCount.size() > 0) {
			locationUserCount.get(0).setTeamFlag(teamFlag);
		}
		return locationUserCount;

	}

	@Override
	@SuppressWarnings("unchecked")
	public List<LocationUserCount> getLocationUserCntNew1(Long supervisorId, boolean teamFlag, int monthSelected,
			int year, String status, SearchSortContainer searchSortContainer, String role) {
		String qryStr = null;
		String resCont = "sum(resourceCount)";
		Query query = null;
		String targetSql = " sum(egiworkingHours*IF(u.resourceCount=0,1,u.resourceCount)*u.workingDays) ";
		if (searchSortContainer != null && searchSortContainer.isDetailLevel()) {
			SQLQuery checkManagerProc = getSession()
					.createSQLQuery("SELECT COUNT(*) FROM PTS_USER_SUPERVISOR S WHERE S.SUPERVISOR_ID=" + supervisorId);
			List<BigInteger> test = checkManagerProc.list();
			if (test != null && test.size() > 0 && test.get(0).longValue() > 0) {
				teamFlag = true;
				if (dashManagerUtilBased != null && dashManagerUtilBased.equalsIgnoreCase("TRUE")) {
					resCont = " sum(resourceCount)+1 ";
					targetSql = " sum(egiworkingHours*(u.resourceCount+1)*u.workingDays) ";
				}
			} else {
				teamFlag = false;
			}

		}
		if (role == null || (role != null && !role.equalsIgnoreCase("USER"))) {
			if (teamFlag) {
				qryStr = "SELECT userId,month,year, workingdays,   egiworkingHours,manaworkingHours," + resCont
						+ " userCnt , " + targetSql + " targetHrs FROM pts_manager_util u WHERE MONTH <="
						+ monthSelected + " AND YEAR=" + year + " AND userId=" + supervisorId
						+ " group by userId,month";
				query = getSession().createSQLQuery(qryStr).addScalar("userId", new LongType())
						.addScalar("userCnt", new IntegerType()).addScalar("month", new LongType())
						// .addScalar("locationId", new LongType()).addScalar("locationName", new
						// StringType())
						.addScalar("year", new LongType()).addScalar("workingdays", new DoubleType())
						.addScalar("egiworkingHours", new FloatType()).addScalar("manaworkingHours", new FloatType())
						.addScalar("targetHrs", new DoubleType());

			} else {
				qryStr = "SELECT   (SELECT l.name " + "FROM PTS_USER u, PTS_USER_SUPERVISOR us, PTS_LOCATION l  "
						+ "WHERE l.id=u.location_id AND u.ID=us.user_id AND STATUS NOT IN ('OffBoard', 'No Show', 'Deleted', 'Others', 'Notice Period')"
						+ "   and u.id =" + supervisorId + " ) locationName,(SELECT l.id   "
						+ "FROM PTS_USER u, PTS_USER_SUPERVISOR us, PTS_LOCATION l "
						+ "WHERE l.id=u.location_id AND u.ID=us.user_id AND STATUS NOT IN ('OffBoard', 'No Show', 'Deleted', 'Others','Induction')"
						+ "   and u.id =" + supervisorId + ")locationId,"
						+ "man.month, man.year, man.workingdays, man.egiworkingHours,man.manaworkingHours,"
						+ " 1 AS userCnt, sum(man.egiworkingHours*1*man.workingDays) targetHrs "
						+ "FROM pts_manager_util man WHERE man.userId=" + supervisorId + "  AND man.month ="
						+ monthSelected
						+ " AND man.locationId= (SELECT l.id   FROM PTS_USER u, PTS_USER_SUPERVISOR us, PTS_LOCATION l "
						+ " WHERE l.id=u.location_id AND u.ID=us.user_id AND STATUS NOT IN ('OffBoard', 'No Show', 'Deleted', 'Others','Induction') and u.id ="
						+ supervisorId + ")";

				query = getSession().createSQLQuery(qryStr).addScalar("userCnt", new IntegerType())
						.addScalar("month", new LongType())
						// .addScalar("locationId", new LongType()).addScalar("locationName", new
						// StringType())
						.addScalar("year", new LongType()).addScalar("workingdays", new DoubleType())
						.addScalar("egiworkingHours", new FloatType()).addScalar("manaworkingHours", new FloatType())
						.addScalar("targetHrs", new DoubleType());
			}
		} else {

			qryStr = " select 	userId,	month,	year,	workingdays,	egiworkingHours,	manaworkingHours,	1 userCnt ,(	case when (u.locationId = 14  ) THEN (u.egiworkingHours*1*u.workingDays) 	when (u.locationId = 15  ) THEN (u.egiworkingHours*1*u.workingDays)	when (u.locationId = 16  ) THEN (u.egiworkingHours*1*u.workingDays)	else (u.manaworkingHours *1*u.workingDays)  end)targetHrs from	pts_manager_util u, PTS_USER pu where month <=:month and year =:year	and userId = (	select	supervisor_id from 	PTS_USER_SUPERVISOR	where user_id =:user)	and pu.ID =:user group by	userId,	month ";
			query = getSession().createSQLQuery(qryStr).addScalar("userId", new LongType())
					.addScalar("userCnt", new IntegerType()).addScalar("month", new LongType())
					// .addScalar("locationId", new LongType()).addScalar("locationName", new
					// StringType())
					.addScalar("year", new LongType()).addScalar("workingdays", new DoubleType())
					.addScalar("egiworkingHours", new FloatType()).addScalar("manaworkingHours", new FloatType())
					.addScalar("targetHrs", new DoubleType());
			query.setLong("year", year);
			query.setLong("user", supervisorId);
			query.setLong("month", monthSelected);

		}

		List<LocationUserCount> locationUserCount = query
				.setResultTransformer(Transformers.aliasToBean(LocationUserCount.class)).list();
		if (locationUserCount != null && locationUserCount.size() > 0) {
			locationUserCount.get(0).setTeamFlag(teamFlag);
		}
		return locationUserCount;

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Double> getDashboardEffortDetails_New(Long supervisorId, String startWeek, String endWeek,
			String startDay, String endDay, Long locationId) {
		String startDayQryStr = "";
		String endDayQryStr = "";

		String queryStr = "select sum(effort) from ( "
				+ " (select :startsummation, t.user_id, t.WEEKENDING_DATE from PTS_USER_TIMESHEET t, PTS_USER u, PTS_USER_SUPERVISOR us where "
				+ " WEEKENDING_DATE = '" + startWeek
				+ "' and t.user_id=u.id and u.id=us.user_id and us.supervisor_id=:supervisorId and u.location_id=:locationId group by t.user_id, t.WEEKENDING_DATE, NETWORK_CODE_ID, ACTIVITY_CODE_ID, type) "
				+ " union all "
				+ " (select (mon_hrs+ tue_hrs + wed_hrs + thu_hrs+ fri_hrs+ sat_hrs+ sun_hrs) as EFFORT, t.user_id, t.WEEKENDING_DATE from PTS_USER_TIMESHEET t, PTS_USER u, PTS_USER_SUPERVISOR us where "
				+ " WEEKENDING_DATE > '" + startWeek + "' and WEEKENDING_DATE < '" + endWeek
				+ "' and t.user_id=u.id and u.id=us.user_id and us.supervisor_id=:supervisorId and u.location_id=:locationId group by t.user_id, t.WEEKENDING_DATE, NETWORK_CODE_ID, ACTIVITY_CODE_ID, type) "
				+ " union all "
				+ " (select :endsummation, t.user_id, t.WEEKENDING_DATE from PTS_USER_TIMESHEET t, PTS_USER u, PTS_USER_SUPERVISOR us where "
				+ " WEEKENDING_DATE = '" + endWeek
				+ "' and t.user_id=u.id and u.id=us.user_id and us.supervisor_id=:supervisorId and u.location_id=:locationId group by t.user_id, t.WEEKENDING_DATE, NETWORK_CODE_ID, ACTIVITY_CODE_ID, type) "
				+ " union all "
				+ " (select :startsummation, t.user_id, t.WEEKENDING_DATE from PTS_USER_TIMESHEET t, PTS_USER u where "
				+ " WEEKENDING_DATE = '" + startWeek
				+ "' and t.user_id=u.id and u.id=:supervisorId and u.location_id=:locationId group by t.user_id, t.WEEKENDING_DATE, NETWORK_CODE_ID, ACTIVITY_CODE_ID, type) "
				+ " union all "
				+ " (select (mon_hrs+ tue_hrs + wed_hrs + thu_hrs+ fri_hrs+ sat_hrs+ sun_hrs) as EFFORT, t.user_id, t.WEEKENDING_DATE from PTS_USER_TIMESHEET t, PTS_USER u where "
				+ " WEEKENDING_DATE > '" + startWeek + "' and WEEKENDING_DATE < '" + endWeek
				+ "' and t.user_id=u.id and u.id=:supervisorId and u.location_id=:locationId group by t.user_id, t.WEEKENDING_DATE, NETWORK_CODE_ID, ACTIVITY_CODE_ID, type) "
				+ " union all "
				+ " (select :endsummation, t.user_id, t.WEEKENDING_DATE from PTS_USER_TIMESHEET t, PTS_USER u where "
				+ " WEEKENDING_DATE = '" + endWeek
				+ "' and t.user_id=u.id and u.id=:supervisorId and u.location_id=:locationId group by t.user_id, t.WEEKENDING_DATE, NETWORK_CODE_ID, ACTIVITY_CODE_ID, type) "
				+ " ) t group by t.user_id ";

		switch (startDay) {
		case "Mon":
			startDayQryStr = "(mon_hrs+ tue_hrs + wed_hrs + thu_hrs+ fri_hrs+ sat_hrs+ sun_hrs) as EFFORT ";
			break;
		case "Tue":
			startDayQryStr = "(tue_hrs + wed_hrs + thu_hrs+ fri_hrs+ sat_hrs+ sun_hrs) as EFFORT ";
			break;
		case "Wed":
			startDayQryStr = "(wed_hrs + thu_hrs+ fri_hrs+ sat_hrs+ sun_hrs) as EFFORT ";
			break;
		case "Thu":
			startDayQryStr = "(thu_hrs+ fri_hrs+ sat_hrs+ sun_hrs) as EFFORT ";
			break;
		case "Fri":
			startDayQryStr = "(fri_hrs+ sat_hrs+ sun_hrs) as EFFORT ";
			break;
		case "Sat":
			startDayQryStr = "(sat_hrs+ sun_hrs) as EFFORT ";
			break;
		case "Sun":
			startDayQryStr = "(sun_hrs) as EFFORT ";
			break;
		}

		switch (endDay) {
		case "Mon":
			endDayQryStr = "(mon_hrs) as EFFORT ";
			break;
		case "Tue":
			endDayQryStr = "(mon_hrs+ tue_hrs) as EFFORT ";
			break;
		case "Wed":
			endDayQryStr = "(mon_hrs+ tue_hrs + wed_hrs) as EFFORT ";
			break;
		case "Thu":
			endDayQryStr = "(mon_hrs+ tue_hrs + wed_hrs + thu_hrs) as EFFORT ";
			break;
		case "Fri":
			endDayQryStr = "(mon_hrs+ tue_hrs + wed_hrs + thu_hrs+ fri_hrs) as EFFORT ";
			break;
		case "Sat":
			endDayQryStr = "(mon_hrs+ tue_hrs + wed_hrs + thu_hrs+ fri_hrs+ sat_hrs) as EFFORT ";
			break;
		case "Sun":
			endDayQryStr = "(mon_hrs+ tue_hrs + wed_hrs + thu_hrs+ fri_hrs+ sat_hrs+ sun_hrs) as EFFORT ";
			break;
		}
		String finalQryStr = queryStr.replaceAll(":startsummation", startDayQryStr).replaceAll(":endsummation",
				endDayQryStr);
		Query query = getSession().createSQLQuery(finalQryStr);
		query.setLong("supervisorId", supervisorId);
		query.setLong("locationId", locationId);

		return query.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Double getDashboardEffortDetails(Long supervisorId, String startWeek, String endWeek, String startDay,
			String endDay, boolean teamFlag, boolean detail) {
		String startDayQryStr = "";
		String endDayQryStr = "";
		List<Long> userIdList = new ArrayList<Long>();
		if (teamFlag) {
			Query queryProc = getSession().createSQLQuery("CALL user_id_heirarchy_proc(:id)").setParameter("id",
					supervisorId);

			queryProc.executeUpdate();

			Query tmpQry = getSession().createSQLQuery("SELECT node FROM _result");
			userIdList = tmpQry.list();

		} else {
			userIdList.add(supervisorId);
		}

		String queryStr = "select  sum(EFFORT) from " + " ( "
				+ " select sum(EFFORT) EFFORT from ( select :startsummation, user_id, WEEKENDING_DATE, t.NETWORK_CODE_ID, ACTIVITY_CODE_ID, type  from "
				+ " PTS_USER_TIMESHEET t, PTS_USER U,PTS_NETWORK_CODES N where  WEEKENDING_DATE = '" + startWeek
				+ "' and U.id = t.USER_ID and U.status in ('Onboard', 	 'Notice Period', 'LTA') and N.RELEASE_NAME  not in ('Vacation','Training Ericsson') and N.id=t.NETWORK_CODE_ID and t.user_id in (:userIdList) ) tmp1 "
				+ " union all "
				+ " select sum(EFFORT) EFFORT from ( select (MON_HRS+TUE_HRS+WED_HRS+THU_HRS+FRI_HRS+SAT_HRS+SUN_HRS) EFFORT, user_id, WEEKENDING_DATE, t.NETWORK_CODE_ID, ACTIVITY_CODE_ID, type  from "
				+ " PTS_USER_TIMESHEET t, PTS_USER U,PTS_NETWORK_CODES N where WEEKENDING_DATE > '" + startWeek
				+ "' and WEEKENDING_DATE < '" + endWeek
				+ "' and U.id = t.USER_ID and U.status in ('Onboard', 'Notice Period', 'LTA') and N.RELEASE_NAME  not in ('Vacation','Training Ericsson') and N.id=t.NETWORK_CODE_ID  and t.user_id in (:userIdList)) tmp2 "
				+ " union all "
				+ " select sum(EFFORT) EFFORT from ( select :endsummation, user_id, WEEKENDING_DATE, t.NETWORK_CODE_ID, ACTIVITY_CODE_ID, type  from "
				+ " PTS_USER_TIMESHEET t, PTS_USER U,PTS_NETWORK_CODES N where  WEEKENDING_DATE = '" + endWeek
				+ "' and U.id = t.USER_ID and U.status in ('Onboard',	 'Notice Period', 'LTA') and N.RELEASE_NAME  not in ('Vacation','Training Ericsson') and N.id=t.NETWORK_CODE_ID  and t.user_id in (:userIdList) ) tmp3 ) t ";

		switch (startDay) {
		case "Mon":
			startDayQryStr = "(mon_hrs+ tue_hrs + wed_hrs + thu_hrs+ fri_hrs+ sat_hrs+ sun_hrs) as EFFORT ";
			break;
		case "Tue":
			startDayQryStr = "(tue_hrs + wed_hrs + thu_hrs+ fri_hrs+ sat_hrs+ sun_hrs) as EFFORT ";
			break;
		case "Wed":
			startDayQryStr = "(wed_hrs + thu_hrs+ fri_hrs+ sat_hrs+ sun_hrs) as EFFORT ";
			break;
		case "Thu":
			startDayQryStr = "(thu_hrs+ fri_hrs+ sat_hrs+ sun_hrs) as EFFORT ";
			break;
		case "Fri":
			startDayQryStr = "(fri_hrs+ sat_hrs+ sun_hrs) as EFFORT ";
			break;
		case "Sat":
			startDayQryStr = "(sat_hrs+ sun_hrs) as EFFORT ";
			break;
		case "Sun":
			startDayQryStr = "(sun_hrs) as EFFORT ";
			break;
		}

		switch (endDay) {
		case "Mon":
			endDayQryStr = "(mon_hrs) as EFFORT ";
			break;
		case "Tue":
			endDayQryStr = "(mon_hrs+ tue_hrs) as EFFORT ";
			break;
		case "Wed":
			endDayQryStr = "(mon_hrs+ tue_hrs + wed_hrs) as EFFORT ";
			break;
		case "Thu":
			endDayQryStr = "(mon_hrs+ tue_hrs + wed_hrs + thu_hrs) as EFFORT ";
			break;
		case "Fri":
			endDayQryStr = "(mon_hrs+ tue_hrs + wed_hrs + thu_hrs+ fri_hrs) as EFFORT ";
			break;
		case "Sat":
			endDayQryStr = "(mon_hrs+ tue_hrs + wed_hrs + thu_hrs+ fri_hrs+ sat_hrs) as EFFORT ";
			break;
		case "Sun":
			endDayQryStr = "(mon_hrs+ tue_hrs + wed_hrs + thu_hrs+ fri_hrs+ sat_hrs+ sun_hrs) as EFFORT ";
			break;
		}
		String finalQryStr = queryStr.replaceAll(":startsummation", startDayQryStr).replaceAll(":endsummation",
				endDayQryStr);
		Query query = getSession().createSQLQuery(finalQryStr);
		if (userIdList != null && userIdList.size() > 0) {
			query.setParameterList("userIdList", userIdList);
		}
		return (Double) query.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<EssDetails> getDashboardEffortESSDetails(Long supervisorId, int year, String month, boolean teamFlag,
			String endDateOfMonth, boolean detail) {
		List<Long> userIdList = new ArrayList<Long>();
		if (teamFlag) {
			Query queryProc = getSession().createSQLQuery("CALL user_id_heirarchy_proc(:id)").setParameter("id",
					supervisorId);

			queryProc.executeUpdate();

			Query tmpQry = getSession().createSQLQuery("SELECT node FROM _result");
			userIdList = tmpQry.list();

		} else {
			userIdList.add(supervisorId);
		}

		String queryStr = "select sum(TARGET_HRS) targetHrs, sum(CHARGED_HRS)  chargedHrs, count(*) headCount from ESS_DETAILS essdet where "
				+ " essdet.month=:month and year=:year  and essdet.signum in (select username from PTS_USER where id in (:userIdList) and status not in ('OffBoard','No Show', 'Open','Selected','Deleted','Induction') and (DOJ is null or DOJ <= :endDateOfMonth) )  group by  month";

		Query query = getSession().createSQLQuery(queryStr).addScalar("targetHrs", new DoubleType())
				.addScalar("chargedHrs", new DoubleType()).addScalar("headCount", new LongType());

		if (userIdList != null && userIdList.size() > 0) {
			query.setParameterList("userIdList", userIdList);
		}
		query.setLong("year", year);
		query.setString("month", month);
		query.setString("endDateOfMonth", endDateOfMonth);

		List<EssDetails> dashboardEffortESSDetails = query
				.setResultTransformer(Transformers.aliasToBean(com.egil.pts.modal.EssDetails.class)).list();

		return dashboardEffortESSDetails;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ResourceUtilization> getResourceUtilization(Long userId, String month, String startWeek, String endWeek,
			String startDay, String endDay, Pagination pagination, boolean descrepencyFlag) throws Throwable {
		String startDayQryStr = "";
		String endDayQryStr = "";
		List<Long> userIdList = new ArrayList<Long>();
		if (userId != null && userId != 0l) {
			Query query = getSession().createSQLQuery("CALL user_id_heirarchy_proc(:id)");
			query.setParameter("id", userId);
			query.executeUpdate();

			Query tmpQry = getSession().createSQLQuery("SELECT node FROM _result");
			userIdList = tmpQry.list();
		}
		String queryStr = "select 	* from	(select ptsdet.*, "
				+ "(SELECT CHARGED_HRS FROM ESS_DETAILS WHERE SIGNUM=ptsdet.signum AND YEAR= :year  AND MONTH='" + month
				+ "' )  as essHrs from (select (SELECT   case when (egiworkingHours*1*u.workingDays) = 0 then 176  ELSE  (egiworkingHours*1*u.workingDays)  END  targetHrs FROM pts_manager_util u WHERE userId="
				+ userId + " AND MONTH='" + endWeek.split("-")[1]
				+ "' AND u.locationId=tLoc AND YEAR= :year )as targetHours, '" + month
				+ "' month, name resourceName, userId userId, email supervisorMail, SUPERVISOR supervisorName, ifnull(sum(EFFORT), 0) actualHours, location_id locationId, signum,tLoc from "
				+ " (select u.name name, u.id userId, (select email from PTS_USER where id in (select distinct tus.supervisor_id from PTS_USER_SUPERVISOR tus where tus.user_id=u.id)) email,"
				+ "(SELECT location_id FROM PTS_USER WHERE id in (SELECT DISTINCT tus.supervisor_id FROM PTS_USER_SUPERVISOR tus WHERE tus.user_id=u.id)) tLoc, "
				+ " (select name from PTS_USER where id in (select distinct tus.supervisor_id from PTS_USER_SUPERVISOR tus where tus.user_id=u.id)) SUPERVISOR, u.location_id, u.username signum, "
				+ " EFFORT from  " + " ( "
				+ " select :startsummation, user_id, WEEKENDING_DATE, t.NETWORK_CODE_ID, ACTIVITY_CODE_ID, type   from PTS_USER_TIMESHEET t,PTS_NETWORK_CODES n  where  WEEKENDING_DATE = '"
				+ startWeek + "' :userIdString "
				+ " and  n.id=t.NETWORK_CODE_ID 	and n.RELEASE_NAME  not in ('Vacation','Training Ericsson')   union all "
				+ " select (MON_HRS+TUE_HRS+WED_HRS+THU_HRS+FRI_HRS+SAT_HRS+SUN_HRS) EFFORT, user_id, WEEKENDING_DATE, t.NETWORK_CODE_ID, ACTIVITY_CODE_ID, type  from PTS_USER_TIMESHEET t,PTS_NETWORK_CODES n  where WEEKENDING_DATE > '"
				+ startWeek + "' and WEEKENDING_DATE < '" + endWeek + "' :userIdString "
				+ " and  n.id=t.NETWORK_CODE_ID 	and n.RELEASE_NAME  not in ('Vacation','Training Ericsson')   union all "
				+ " select :endsummation, user_id, WEEKENDING_DATE, t.NETWORK_CODE_ID, ACTIVITY_CODE_ID, type  from PTS_USER_TIMESHEET t,PTS_NETWORK_CODES n  where  WEEKENDING_DATE = '"
				+ endWeek
				+ "' :userIdString and  n.id=t.NETWORK_CODE_ID 	and n.RELEASE_NAME  not in ('Vacation','Training Ericsson')  ) t "
				+ " right outer join PTS_USER u on t.user_id=u.id where u.status in ('Onboard',   'Notice Period', 'LTA') :userIdString and u.id not in (select user_id from PTS_USER_ROLE where role_id=1)) temp1 "
				+ " group by name) ptsdet left outer join ESS_DETAILS essdet on ptsdet.month=essdet.month and ptsdet.signum=essdet.signum and essdet.year= :year";

		switch (startDay) {
		case "Mon":
			startDayQryStr = "(mon_hrs+ tue_hrs + wed_hrs + thu_hrs+ fri_hrs+ sat_hrs+ sun_hrs) as EFFORT ";
			break;
		case "Tue":
			startDayQryStr = "(tue_hrs + wed_hrs + thu_hrs+ fri_hrs+ sat_hrs+ sun_hrs) as EFFORT ";
			break;
		case "Wed":
			startDayQryStr = "(wed_hrs + thu_hrs+ fri_hrs+ sat_hrs+ sun_hrs) as EFFORT ";
			break;
		case "Thu":
			startDayQryStr = "(thu_hrs+ fri_hrs+ sat_hrs+ sun_hrs) as EFFORT ";
			break;
		case "Fri":
			startDayQryStr = "(fri_hrs+ sat_hrs+ sun_hrs) as EFFORT ";
			break;
		case "Sat":
			startDayQryStr = "(sat_hrs+ sun_hrs) as EFFORT ";
			break;
		case "Sun":
			startDayQryStr = "(sun_hrs) as EFFORT ";
			break;
		}

		switch (endDay) {
		case "Mon":
			endDayQryStr = "(mon_hrs) as EFFORT ";
			break;
		case "Tue":
			endDayQryStr = "(mon_hrs+ tue_hrs) as EFFORT ";
			break;
		case "Wed":
			endDayQryStr = "(mon_hrs+ tue_hrs + wed_hrs) as EFFORT ";
			break;
		case "Thu":
			endDayQryStr = "(mon_hrs+ tue_hrs + wed_hrs + thu_hrs) as EFFORT ";
			break;
		case "Fri":
			endDayQryStr = "(mon_hrs+ tue_hrs + wed_hrs + thu_hrs+ fri_hrs) as EFFORT ";
			break;
		case "Sat":
			endDayQryStr = "(mon_hrs+ tue_hrs + wed_hrs + thu_hrs+ fri_hrs+ sat_hrs) as EFFORT ";
			break;
		case "Sun":
			endDayQryStr = "(mon_hrs+ tue_hrs + wed_hrs + thu_hrs+ fri_hrs+ sat_hrs+ sun_hrs) as EFFORT ";
			break;
		}
		String userIdCond = "";
		if (userIdList != null && userIdList.size() > 0) {
			userIdCond = " and user_id in (:userIdList) ";
		}
		queryStr += " order by userId ) t ";
		/*
		if(descrepencyFlag)
		queryStr += " where 	t.userId not in (select	user_id	from PTS_USER_WEEK_OFF	where WEEK_OFF = true and WEEKENDING_DATE > '"
				+ startWeek + "' and WEEKENDING_DATE < '" + endWeek + "')";
		*/
		String finalQryStr = queryStr.replaceAll(":startsummation", startDayQryStr)
				.replaceAll(":endsummation", endDayQryStr).replaceAll(":userIdString", userIdCond);
		Query query = getSession().createSQLQuery(finalQryStr).addScalar("userId", new LongType())
				.addScalar("supervisorMail", new StringType()).addScalar("month", new StringType())
				.addScalar("resourceName", new StringType()).addScalar("supervisorName", new StringType())
				.addScalar("signum", new StringType()).addScalar("actualHours", new DoubleType())
				.addScalar("targetHours", new DoubleType()).addScalar("essHrs", new DoubleType())
				// .addScalar("targetHrsNew", new DoubleType())
				.addScalar("locationId", new LongType());
		if (userIdList != null && userIdList.size() > 0) {
			query.setParameterList("userIdList", userIdList);
		}
		query.setLong("year", Long.parseLong(endWeek.split("-")[0]));

		if (pagination != null) {
			if (pagination.getOffset() > 0)
				query.setFirstResult(pagination.getOffset());
			if (pagination.getSize() > 0)
				query.setMaxResults(pagination.getSize());
		}
		List<ResourceUtilization> resourceUtilizationList = query
				.setResultTransformer(Transformers.aliasToBean(ResourceUtilization.class)).list();
		return resourceUtilizationList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public int getResourceUtilizationCount(Long userId, String month, String startWeek, String endWeek, String startDay,
			String endDay) throws Throwable {
		int count = 0;
		String startDayQryStr = "";
		String endDayQryStr = "";
		List<Long> userIdList = new ArrayList<Long>();
		if (userId != null && userId != 0l) {
			Query query = getSession().createSQLQuery("CALL user_id_heirarchy_proc(:id)");
			query.setParameter("id", userId);
			query.executeUpdate();

			Query tmpQry = getSession().createSQLQuery("SELECT node FROM _result");
			userIdList = tmpQry.list();
		}
		String queryStr = "select count(*) from (select name from " + " (select u.name name, "
				+ " (select name from PTS_USER where id in (select distinct tus.supervisor_id from PTS_USER_SUPERVISOR tus where tus.user_id=u.id)) SUPERVISOR, u.location_id, u.username signum, "
				+ " EFFORT from  " + " ( "
				+ " select :startsummation, user_id, WEEKENDING_DATE, NETWORK_CODE_ID, ACTIVITY_CODE_ID, type   from PTS_USER_TIMESHEET where  WEEKENDING_DATE = '"
				+ startWeek + "' :userIdString " + " union all "
				+ " select (MON_HRS+TUE_HRS+WED_HRS+THU_HRS+FRI_HRS+SAT_HRS+SUN_HRS) EFFORT, user_id, WEEKENDING_DATE, NETWORK_CODE_ID, ACTIVITY_CODE_ID, type  from PTS_USER_TIMESHEET where WEEKENDING_DATE > '"
				+ startWeek + "' and WEEKENDING_DATE < '" + endWeek + "' :userIdString " + " union all "
				+ " select :endsummation, user_id, WEEKENDING_DATE, NETWORK_CODE_ID, ACTIVITY_CODE_ID, type from PTS_USER_TIMESHEET where  WEEKENDING_DATE = '"
				+ endWeek + "' :userIdString ) t "
				+ " right outer join PTS_USER u on t.user_id=u.id where u.status in ('Onboard',  'Notice Period', 'LTA') :userIdString and u.id not in (select user_id from PTS_USER_ROLE where role_id=1)) temp1 "
				+ " group by name) temp123";

		switch (startDay) {
		case "Mon":
			startDayQryStr = "(mon_hrs+ tue_hrs + wed_hrs + thu_hrs+ fri_hrs+ sat_hrs+ sun_hrs) as EFFORT ";
			break;
		case "Tue":
			startDayQryStr = "(tue_hrs + wed_hrs + thu_hrs+ fri_hrs+ sat_hrs+ sun_hrs) as EFFORT ";
			break;
		case "Wed":
			startDayQryStr = "(wed_hrs + thu_hrs+ fri_hrs+ sat_hrs+ sun_hrs) as EFFORT ";
			break;
		case "Thu":
			startDayQryStr = "(thu_hrs+ fri_hrs+ sat_hrs+ sun_hrs) as EFFORT ";
			break;
		case "Fri":
			startDayQryStr = "(fri_hrs+ sat_hrs+ sun_hrs) as EFFORT ";
			break;
		case "Sat":
			startDayQryStr = "(sat_hrs+ sun_hrs) as EFFORT ";
			break;
		case "Sun":
			startDayQryStr = "(sun_hrs) as EFFORT ";
			break;
		}

		switch (endDay) {
		case "Mon":
			endDayQryStr = "(mon_hrs) as EFFORT ";
			break;
		case "Tue":
			endDayQryStr = "(mon_hrs+ tue_hrs) as EFFORT ";
			break;
		case "Wed":
			endDayQryStr = "(mon_hrs+ tue_hrs + wed_hrs) as EFFORT ";
			break;
		case "Thu":
			endDayQryStr = "(mon_hrs+ tue_hrs + wed_hrs + thu_hrs) as EFFORT ";
			break;
		case "Fri":
			endDayQryStr = "(mon_hrs+ tue_hrs + wed_hrs + thu_hrs+ fri_hrs) as EFFORT ";
			break;
		case "Sat":
			endDayQryStr = "(mon_hrs+ tue_hrs + wed_hrs + thu_hrs+ fri_hrs+ sat_hrs) as EFFORT ";
			break;
		case "Sun":
			endDayQryStr = "(mon_hrs+ tue_hrs + wed_hrs + thu_hrs+ fri_hrs+ sat_hrs+ sun_hrs) as EFFORT ";
			break;
		}
		String userIdCond = "";
		if (userIdList != null && userIdList.size() > 0) {
			userIdCond = " and user_id in (:userIdList) ";
		}

		String finalQryStr = queryStr.replaceAll(":startsummation", startDayQryStr)
				.replaceAll(":endsummation", endDayQryStr).replaceAll(":userIdString", userIdCond);
		Query query = getSession().createSQLQuery(finalQryStr);
		if (userIdList != null && userIdList.size() > 0) {
			query.setParameterList("userIdList", userIdList);
		}
		BigInteger obj = (BigInteger) query.uniqueResult();
		if (obj != null) {
			count = obj.intValue();
		}
		return count;
	}

	@Override
	public void saveUserUtilization(List<SupervisorResourceUtilization> dashbordDetails) {
		for (SupervisorResourceUtilization a : dashbordDetails) {
			try {
				List temp = getSession()
						.createSQLQuery("SELECT p.id FROM pts_manager_util p"
								+ "	WHERE p.userId= :userId AND p.locationId= :locationId and p.MONTH= :month ")
						.setLong("userId", a.getUserId()).setLong("locationId", a.getLocationId())
						.setLong("month", a.getMonth()).list();
				getSession().flush();
				getSession().clear();
				String sql = "";
				if (temp != null && temp.size() > 0) {
					sql = "UPDATE pts_manager_util set userId = :userId,resourceCount = :resourceCount,month = :month,locationId = :locationId,locationName = :locationName,"
							+ "year = :year,workingdays = :workingdays,egiworkingHours = :egiworkingHours,targetHrs= :targetHrs"
							+ ",manaworkingHours = :manaworkingHours where userId = :userId and locationId = :locationId and MONTH= :month ";
				} else {
					sql = "INSERT INTO pts_manager_util(userId,resourceCount,month,locationId,locationName,year,workingdays,"
							+ "egiworkingHours,manaworkingHours,targetHrs) VALUES ( :userId,:resourceCount, :month, :locationId, :locationName,"
							+ " :year, :workingdays, :egiworkingHours, :manaworkingHours, :targetHrs) ";
				}

				int p = getSession().createSQLQuery(sql).setLong("userId", a.getUserId())
						.setLong("resourceCount", (a.getResourceCount() == null) ? 0 : a.getResourceCount())
						.setLong("month", a.getMonth()).setLong("locationId", a.getLocationId())
						.setString("locationName", a.getLocationName()).setLong("year", a.getYear())
						.setParameter("workingdays", (a.getWorkingDays() == null) ? 0 : a.getWorkingDays())
						.setFloat("egiworkingHours", (a.getEgiworkingHours() == null) ? 0 : a.getEgiworkingHours())
						.setFloat("manaworkingHours", (a.getManaworkingHours() == null) ? 0 : a.getManaworkingHours())
						.setFloat("targetHrs", (a.getTargetHrs() == null) ? 0 : a.getTargetHrs()).executeUpdate();
				getSession().flush();
				getSession().clear();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	@Override
	public List<com.egil.pts.modal.SupervisorResourceUtilization> getUserUtilization(Long user, Long selectedYear,
			Long selectedMonth) {
		String sql = "SELECT id,targetHrs,userId,resourceCount,month,locationId,locationName,year,workingdays,egiworkingHours,manaworkingHours FROM pts_manager_util  WHERE userId="
				+ user + " AND YEAR=" + selectedYear + " AND MONTH=" + selectedMonth + "";
		SQLQuery a = getSession().createSQLQuery(sql).addScalar("id", new LongType())
				.addScalar("userId", new LongType()).addScalar("resourceCount", new LongType())
				.addScalar("month", new LongType()).addScalar("locationId", new LongType())
				.addScalar("locationName", new StringType()).addScalar("year", new LongType())
				.addScalar("workingdays", new DoubleType()).addScalar("egiworkingHours", new FloatType())
				.addScalar("manaworkingHours", new FloatType()).addScalar("targetHrs", new FloatType());

		List<com.egil.pts.modal.SupervisorResourceUtilization> b = a
				.setResultTransformer(Transformers.aliasToBean(com.egil.pts.modal.SupervisorResourceUtilization.class))
				.list();
		return b;
	}

	@Override
	public void saveUserUtilizationForMonth() {
		int currentMonth = new Date().getMonth() + 1;
		try {
			String sql = "SELECT id, targetHrs,userId,resourceCount,month,locationId,locationName,year,workingdays,egiworkingHours,manaworkingHours FROM pts_manager_util where month="
					+ (currentMonth) + " ORDER BY userId asc";

			SQLQuery a = getSession().createSQLQuery(sql).addScalar("id", new LongType())
					.addScalar("userId", new LongType()).addScalar("resourceCount", new LongType())
					.addScalar("month", new LongType()).addScalar("locationId", new LongType())
					.addScalar("locationName", new StringType()).addScalar("year", new LongType())
					.addScalar("workingdays", new DoubleType()).addScalar("egiworkingHours", new FloatType())
					.addScalar("manaworkingHours", new FloatType()).addScalar("targetHrs", new FloatType());

			List<com.egil.pts.modal.SupervisorResourceUtilization> utilData = a.setResultTransformer(
					Transformers.aliasToBean(com.egil.pts.modal.SupervisorResourceUtilization.class)).list();
			getSession().flush();
			getSession().clear();
			if (utilData != null && utilData.size() <= 0) {
				sql = "SELECT  id,targetHrs,userId,resourceCount,month,locationId,locationName,year,workingdays,egiworkingHours,manaworkingHours FROM pts_manager_util where month="
						+ (currentMonth - 1) + " ORDER BY userId asc";

				a = getSession().createSQLQuery(sql).addScalar("id", new LongType()).addScalar("userId", new LongType())
						.addScalar("resourceCount", new LongType()).addScalar("month", new LongType())
						.addScalar("locationId", new LongType()).addScalar("locationName", new StringType())
						.addScalar("year", new LongType()).addScalar("workingdays", new DoubleType())
						.addScalar("egiworkingHours", new FloatType()).addScalar("manaworkingHours", new FloatType())
						.addScalar("targetHrs", new FloatType());

				utilData = a
						.setResultTransformer(
								Transformers.aliasToBean(com.egil.pts.modal.SupervisorResourceUtilization.class))
						.list();
				getSession().flush();
				getSession().clear();
			}
			currentMonth = new Date().getMonth() + 1;
			for (com.egil.pts.modal.SupervisorResourceUtilization supRes : utilData) {
				if (supRes.getMonth() <= new Date().getMonth() + 1) {
					sql = "	INSERT INTO pts_manager_util ( userId, targetHrs, resourceCount, month, locationId, locationName, year, workingDays, egiworkingHours, manaworkingHours ) VALUES "
							+ "	 ( :userId, :targetHrs, :resourceCount, :month , :locationId , :locationName, :year , :workingDays , :egiworkingHours , :manaworkingHours ) ";
					getSession().createSQLQuery(sql).setLong("userId", supRes.getUserId())
							.setDouble("targetHrs", supRes.getTargetHrs())
							.setLong("resourceCount", supRes.getResourceCount()).setLong("month", currentMonth + 1)
							.setLong("locationId", supRes.getLocationId())
							.setString("locationName", supRes.getLocationName()).setLong("year", supRes.getYear())
							.setDouble("workingDays", supRes.getWorkingdays())
							.setDouble("egiworkingHours", supRes.getEgiworkingHours())
							.setDouble("manaworkingHours", supRes.getManaworkingHours()).executeUpdate();
					getSession().flush();
					getSession().clear();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void saveUserUtilizationForYear() {
		int currentMonth = new Date().getMonth() + 1;
		try {
			String sql = "SELECT  id,targetHrs,userId,resourceCount,month,locationId,locationName,year,workingdays,egiworkingHours,manaworkingHours FROM pts_manager_util where month="
					+ (currentMonth) + " ORDER BY userId asc";
			SQLQuery a = getSession().createSQLQuery(sql).addScalar("id", new LongType())
					.addScalar("userId", new LongType()).addScalar("resourceCount", new LongType())
					.addScalar("month", new LongType()).addScalar("locationId", new LongType())
					.addScalar("locationName", new StringType()).addScalar("year", new LongType())
					.addScalar("workingdays", new DoubleType()).addScalar("egiworkingHours", new FloatType())
					.addScalar("manaworkingHours", new FloatType()).addScalar("targetHrs", new FloatType());

			List<com.egil.pts.modal.SupervisorResourceUtilization> utilData = a.setResultTransformer(
					Transformers.aliasToBean(com.egil.pts.modal.SupervisorResourceUtilization.class)).list();
			getSession().flush();
			getSession().clear();

			if (utilData != null && utilData.size() <= 0) {
				sql = "SELECT  id,targetHrs,userId,resourceCount,month,locationId,locationName,year,workingdays,egiworkingHours,manaworkingHours FROM pts_manager_util where month="
						+ (currentMonth) + " ORDER BY userId asc";
				a = getSession().createSQLQuery(sql).addScalar("id", new LongType()).addScalar("userId", new LongType())
						.addScalar("resourceCount", new LongType()).addScalar("month", new LongType())
						.addScalar("locationId", new LongType()).addScalar("locationName", new StringType())
						.addScalar("year", new LongType()).addScalar("workingdays", new DoubleType())
						.addScalar("egiworkingHours", new FloatType()).addScalar("manaworkingHours", new FloatType())
						.addScalar("targetHrs", new FloatType());

				utilData = a
						.setResultTransformer(
								Transformers.aliasToBean(com.egil.pts.modal.SupervisorResourceUtilization.class))
						.list();
				getSession().flush();
				getSession().clear();
			}

			boolean flag = false;
			for (com.egil.pts.modal.SupervisorResourceUtilization supRes : utilData) {
				flag = false;
				long curMonth = supRes.getMonth() + 1;
				if (supRes.getMonth() <= currentMonth) {
					flag = true;
				}
				if (flag) {
					for (long i = curMonth; i <= 12; i++) {
						sql = "	INSERT INTO pts_manager_util ( userId, targetHrs, resourceCount, month, locationId, locationName, year, workingDays, egiworkingHours, manaworkingHours ) VALUES "
								+ "	 ( :userId, :targetHrs, :resourceCount, :month , :locationId , :locationName, :year , :workingDays , :egiworkingHours , :manaworkingHours ) ";
						getSession().createSQLQuery(sql).setLong("userId", supRes.getUserId())
								.setDouble("targetHrs", supRes.getTargetHrs())
								.setLong("resourceCount", supRes.getResourceCount()).setLong("month", currentMonth + 1)
								.setLong("locationId", supRes.getLocationId())
								.setString("locationName", supRes.getLocationName()).setLong("year", i)
								.setDouble("workingDays", supRes.getWorkingdays())
								.setDouble("egiworkingHours", supRes.getEgiworkingHours())
								.setDouble("manaworkingHours", supRes.getManaworkingHours()).executeUpdate();
						getSession().flush();
						getSession().clear();
					}
				}
			}
		} catch (

		Exception e) {
			e.printStackTrace();
		}
	}

	public String getManagerUtilBased() {
		return managerUtilBased;
	}

	public void setManagerUtilBased(String managerUtilBased) {
		this.managerUtilBased = managerUtilBased;
	}

	public String getDashManagerUtilBased() {
		return dashManagerUtilBased;
	}

	public void setDashManagerUtilBased(String dashManagerUtilBased) {
		this.dashManagerUtilBased = dashManagerUtilBased;
	}

	@Override
	public List<NetworkCodeEffort> getUserDashBoardLoeDetails(Long userId, boolean showAllMyContributions) {

		String queryString = "select (select case WHEN  (sum(t.mon_hrs)+ sum(t.tue_hrs) + sum(t.wed_hrs) + sum(t.thu_hrs)+ sum(t.fri_hrs)+ sum(t.sat_hrs)+ sum(t.sun_hrs)) is not null then (sum(t.mon_hrs)+ sum(t.tue_hrs) + sum(t.wed_hrs) + sum(t.thu_hrs)+ sum(t.fri_hrs)+ sum(t.sat_hrs)+ sum(t.sun_hrs)) ELSE 0 end from PTS_USER_TIMESHEET t where t.USER_ID=pst.user_id and t.NETWORK_CODE_ID=nc.id ) SUMMATION , "
				+ "(select case WHEN  (sum(t.mon_hrs)+ sum(t.tue_hrs) + sum(t.wed_hrs) + sum(t.thu_hrs)+ sum(t.fri_hrs)+ sum(t.sat_hrs)+ sum(t.sun_hrs)) is not null then (sum(t.mon_hrs)+ sum(t.tue_hrs) + sum(t.wed_hrs) + sum(t.thu_hrs)+ sum(t.fri_hrs)+ sum(t.sat_hrs)+ sum(t.sun_hrs)) ELSE 0 end from PTS_USER_TIMESHEET t where  t.NETWORK_CODE_ID=nc.id ) TOTALSUMMATION , "
				+ "concat( nc.RELEASE_ID, ' - ', nc.RELEASE_NAME) as 'NETWORKCODE',nc.id,pst.contribution ,nc.totalLOE, nc.local_dev_loe 'localDevLoe',nc.local_test_loe 'localTestLoe',nc.global_dev_loe 'globalDevLoe',nc.global_test_loe 'globalTestLoe' "
				+ "from PTS_NETWORK_CODES nc, PTS_USER_STABLE_TEAMS pst ,PTS_STABLE_TEAMS ps where nc.stable_teams = ps.id  and ps.id=pst.stable_team_id and  user_id=:id and pst.contribution  >0 &nw ";
		if (userId == -1L) {
			queryString = "select 0 SUMMATION ,nc.totalLOE,0 contribution,(select  (sum(t.mon_hrs)+ sum(t.tue_hrs) + sum(t.wed_hrs) + sum(t.thu_hrs)+ sum(t.fri_hrs)+ sum(t.sat_hrs)+ sum(t.sun_hrs) ) from PTS_USER_TIMESHEET t where  t.NETWORK_CODE_ID=nc.id ) 'TOTALSUMMATION'  , "
					+ "concat( nc.RELEASE_ID, ' - ', nc.RELEASE_NAME) as 'NETWORKCODE',nc.id, nc.local_dev_loe 'localDevLoe',nc.local_test_loe 'localTestLoe',nc.global_dev_loe 'globalDevLoe',nc.global_test_loe 'globalTestLoe'  "
					+ "from PTS_NETWORK_CODES nc where   nc.stable_teams is not null  &nw ";
		}
		if (!showAllMyContributions) {
			queryString = queryString.replaceAll("&nw",
					" and nc.STATUS  not in ('DELETED','COMPLETED','Completed','EarlyStart','Packaging','In-Active','Cancelled','Implemented')  ");
		} else {
			queryString = queryString.replaceAll("&nw",
					" and nc.STATUS  not in ('DELETED', 'In-Active','Cancelled','EarlyStart','Packaging' )  ");
		}
		Query query = getSession().createSQLQuery(queryString).addScalar("SUMMATION", new FloatType())
				.addScalar("TOTALSUMMATION", new FloatType()).addScalar("NETWORKCODE", new StringType())
				.addScalar("contribution", new FloatType()).addScalar("localDevLoe", new LongType())
				.addScalar("localTestLoe", new LongType()).addScalar("TOTALLOE", new LongType())
				.addScalar("globalDevLoe", new LongType()).addScalar("globalTestLoe", new LongType());
		if (userId != -1L)
			query.setLong("id", userId);

		List<NetworkCodeEffort> networkCodeEffortList = query
				.setResultTransformer(Transformers.aliasToBean(NetworkCodeEffort.class)).list();
		return networkCodeEffortList;
	}

	public List<ResourceUtilization> getResourceUtilizationPendingEss(String startDate, String endDate, Long userId,
			String selectedMonth, Pagination pagination) {
		List<Long> userIdList = new ArrayList<Long>();
		if (userId != null && userId != 0l) {
			Query query = getSession().createSQLQuery("CALL user_id_heirarchy_proc(:id)");
			query.setParameter("id", userId);
			query.executeUpdate();

			Query tmpQry = getSession().createSQLQuery("SELECT node FROM _result");
			userIdList = tmpQry.list();
		}
		String finalQryStr = " select (select  	( case when u.location_id =14 then	case	when (egiworkingHours*1*m.workingDays) = 0 then 176		else (egiworkingHours*1*m.workingDays)	end "
				+ "when u.location_id =15 then	case	when (egiworkingHours*1*m.workingDays) = 0 then 176		else (egiworkingHours*1*m.workingDays)	end 	 "
				+ "when u.location_id =16 then	case	when (egiworkingHours*1*m.workingDays) = 0 then 176		else (egiworkingHours*1*m.workingDays)	end 	 "
				+ "else (case	when (manaworkingHours *1*m.workingDays) = 0 then 176		else (manaworkingHours*1*m.workingDays)	end 	) end			) "
				+ "from pts_manager_util m where userId =sp.SUPERVISOR_ID and locationId = (case when u.location_id=14 then 14 when u.location_id=15 then 15 when u.location_id=16 then 16 else 1 end) and `year` =:year and `month` =:month\r\n"
				+ ") targethours,	e.`MONTH` 'month',	u.NAME 'resourceName',	u.ID 'userId',CHARGED_HRS essHrs,	(select s.EMAIL from PTS_USER s where s.id = sp.SUPERVISOR_ID) 'supervisorMail',(select s.NAME from PTS_USER s where s.id = sp.SUPERVISOR_ID) 'supervisorName', 0 'actualHours', u.location_id 'locationId',	e.SIGNUM 'signum',	(select location_id from PTS_USER where id = sp.SUPERVISOR_ID) 'tLoc' from ESS_DETAILS e, 	PTS_USER u,	PTS_USER_SUPERVISOR sp where	sp.USER_ID = u.id	and e.SIGNUM = u.USERNAME 	and u.id in  (:userIdList) 	and year =:year	and u.ID not in (select   user_id from PTS_USER_TIMESHEET put where  USER_ID in (:userIdList) and WEEKENDING_DATE >:startDate and WEEKENDING_DATE <:endDate  group by USER_ID) 	and month =:month1 ";
		finalQryStr += " order by user_id ";
		Query query = getSession().createSQLQuery(finalQryStr).addScalar("userId", new LongType())
				.addScalar("supervisorMail", new StringType()).addScalar("month", new StringType())
				.addScalar("resourceName", new StringType()).addScalar("supervisorName", new StringType())
				.addScalar("signum", new StringType()).addScalar("actualHours", new DoubleType())
				.addScalar("targetHours", new DoubleType()).addScalar("essHrs", new DoubleType())
				.addScalar("locationId", new LongType());

		query.setString("startDate", startDate);
		query.setString("endDate", endDate);
		query.setParameterList("userIdList", userIdList);
		query.setLong("month", Long.parseLong(endDate.split("-")[1]));
		query.setString("month1", selectedMonth);
		query.setLong("year", Long.parseLong(endDate.split("-")[0]));

		if (pagination != null) {
			if (pagination.getOffset() > 0)
				query.setFirstResult(pagination.getOffset());
			if (pagination.getSize() > 0)
				query.setMaxResults(pagination.getSize());
		}
		List<ResourceUtilization> resourceUtilizationList = query
				.setResultTransformer(Transformers.aliasToBean(ResourceUtilization.class)).list();
		return resourceUtilizationList;

	}

	public List<Utilization> getUserUtilizationSummaryAddUnCharged(Long supervisorId, String fromDate, String toDate,
			Long searchValue, String reporteeType, Pagination pagination, boolean delinquentEntries,
			List<String> weeksList, long stableTeamid, Long networkCodeId, Long projectManagerId) {
		List<Long> userIdList = new ArrayList<Long>();
		List<Utilization> networkCodeEffortList = new ArrayList<Utilization>();
		if (supervisorId != null) {

			Query queryProc = getSession().createSQLQuery("CALL user_id_heirarchy_proc(:id)").setParameter("id",
					supervisorId);

			queryProc.executeUpdate();

			Query tmpQry = getSession().createSQLQuery("SELECT node FROM _result");
			userIdList = tmpQry.list();
			for (String week : weeksList) {
				String queryStr="select  DATE_FORMAT(:week , '%m/%d/%Y') as weekEndingDate,id,name resourceName, username signum ,coalesce(DATE_FORMAT(:week ,  '%d%m%Y')) parseWeekEndingDate,	(select	name from PTS_USER where id =:supervisor) supervisorName from PTS_USER where UPPER(username) not like '%ADMIN%' and id in (:useres) and  status not in ('OffBoard','No Show','Open','Selected','Deleted','Induction')    and id not in (select distinct USER_ID from PTS_USER_TIMESHEET put where user_id in (:useres) and WEEKENDING_DATE =:week) ";
				if(delinquentEntries)
					queryStr += " and id not in (select	user_id	from PTS_USER_WEEK_OFF	where WEEK_OFF = true and WEEKENDING_DATE=:week ) ";
				
				Query query = getSession().createSQLQuery(queryStr)
						.addScalar("id", new StringType()).addScalar("resourceName", new StringType())
						.addScalar("signum", new StringType()).addScalar("supervisorName", new StringType())
						.addScalar("parseWeekEndingDate", new StringType())
						.addScalar("weekEndingDate", new StringType());
				query.setParameterList("useres", userIdList);
				query.setString("week", week);
				query.setLong("supervisor", supervisorId);
				if (pagination != null) {
					if (pagination.getOffset() > 0)
						query.setFirstResult(pagination.getOffset());
					if (pagination.getSize() > 0)
						query.setMaxResults(pagination.getSize());
				}
				networkCodeEffortList
						.addAll(query.setResultTransformer(Transformers.aliasToBean(Utilization.class)).list());
//					query.setString("week", week);

//					networkCodeEffortList
//							.addAll(query.setResultTransformer(Transformers.aliasToBean(Utilization.class)).list());

			}
		}

		return networkCodeEffortList;

	}

	public int getUserUtilizationSummaryAddUnChargedCount(Long supervisorId, String fromDate, String toDate,
			Long searchValue, String reporteeType, Pagination pagination, boolean delinquentEntries,
			List<String> weeksList, long stableTeamid, Long networkCodeId, Long projectManagerId) {
		if (delinquentEntries) {

			StringBuffer queryString = new StringBuffer();
			for (String date : weeksList) {
				queryString.append("     select distinct DATE_FORMAT('" + date
						+ "' , '%m/%d/%Y') as weekEndingDate,	id,	name resourceName,	USERNAME signum,	coalesce(DATE_FORMAT('"
						+ date
						+ "', '%d%m%Y')) parseWeekEndingDate,	(select	name from PTS_USER where id = s.id) supervisorName from	PTS_USER s "
						+ "	where s.id not in (select distinct T.USER_ID from PTS_USER_TIMESHEET T where &users T.WEEKENDING_DATE in ('"
						+ date
						+ "') ) and status not in ('OffBoard','No Show','Open','Selected','Deleted','Induction') and upper(NAME) not like '%ADMIN%' ");
				if (supervisorId != null && supervisorId > 0)
					queryString.append(
							" and id in ( select user_id from 	PTS_USER_SUPERVISOR	where		SUPERVISOR_ID =  "
									+ supervisorId + " )");
				queryString.append("union all");
			}
			String s = null;
			if (supervisorId != null && supervisorId > 0) {
				s = (queryString.toString().replaceAll("&users",
						" T.user_id in (select id from PTS_USER_SUPERVISOR where SUPERVISOR_ID =" + supervisorId
								+ ") and "));
			} else {
				s = (queryString.toString().replaceAll("&users", " "));
			}
			Query query = getSession().createSQLQuery(s.substring(0, s.lastIndexOf("union all")))
					.addScalar("id", new StringType()).addScalar("resourceName", new StringType())
					.addScalar("signum", new StringType()).addScalar("supervisorName", new StringType())
					.addScalar("parseWeekEndingDate", new StringType()).addScalar("weekEndingDate", new StringType());
			if (pagination != null) {
				if (pagination.getOffset() > 0)
					query.setFirstResult(pagination.getOffset());
				if (pagination.getSize() > 0)
					query.setMaxResults(pagination.getSize());
			}
			List<Utilization> networkCodeEffortList = query
					.setResultTransformer(Transformers.aliasToBean(Utilization.class)).list();
			return networkCodeEffortList.size();
		} else
			return 0;

	}
}
