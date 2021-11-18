package com.egil.pts.dao.inventory.impl;

import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Query;
import org.hibernate.transform.Transformers;
import org.hibernate.type.DateType;
import org.hibernate.type.DoubleType;
import org.hibernate.type.FloatType;
import org.hibernate.type.LongType;
import org.hibernate.type.StringType;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.egil.pts.dao.common.hibernate.HibernateGenericDao;
import com.egil.pts.dao.domain.CapacityPlanning;
import com.egil.pts.dao.inventory.CapacityPlanningDao;
import com.egil.pts.modal.Pagination;
import com.egil.pts.modal.SearchSortContainer;
import com.egil.pts.util.Utility;

@Repository("capacityPlanningDao")
public class HibernateCapacityPlanningDao extends HibernateGenericDao<CapacityPlanning, Long>
		implements CapacityPlanningDao {

	private static final String MONTHS[] = { "JAN_CPTY is NULL and ", " FEB_CPTY is NULL and ",
			" MAR_CPTY is NULL and ", " APR_CPTY is NULL and ", " MAY_CPTY is NULL and ", " JUN_CPTY is NULL and ",
			" JUL_CPTY is NULL and ", " AUG_CPTY is NULL and ", " SEP_CPTY is NULL and ", " OCT_CPTY is NULL and ",
			" NOV_CPTY is NULL and ", " DEC_CPTY is NULL and " };

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true)
	public List<CapacityPlanning> getCapacityPlanningDetails(Long id, Long year, Pagination pagination) {
		StringBuilder queryString = new StringBuilder();

		/*
		 * select cp.id, u.id, unc.NETWORK_CODE_ID, u.name, concat(nc.network_code_id, '
		 * - ' , nc.RELEASE_ID, ' - ',nc.RELEASE_NAME) as NETWORKCODE, cp.year,
		 * cp.jan_cpty, cp.feb_cpty, cp.mar_cpty, cp.apr_cpty, cp.may_cpty, cp.jun_cpty,
		 * cp.jul_cpty, cp.aug_cpty, cp.sep_cpty, cp.oct_cpty, cp.nov_cpty, cp.dec_cpty
		 * from PTS_USER_CAPACITY_PLANNING cp right outer join PTS_USER u on
		 * cp.user_id=u.id right outer join PTS_USER_NETWORK_CODES unc on
		 * u.id=unc.user_id, PTS_NETWORK_CODES nc where u.id in (SELECT node FROM
		 * _result) and nc.id=unc.NETWORK_CODE_ID and u.name like 'Krishna Reddy'
		 */
		queryString.append("from CapacityPlanning cp where cp.user.id=:userId and cp.year=:year ");

		Query q = getSession().createQuery(queryString.toString());
		q.setLong("userId", id);
		q.setLong("year", year);

		if (pagination != null) {
			if (pagination.getOffset() > 0)
				q.setFirstResult(pagination.getOffset());
			if (pagination.getSize() > 0)
				q.setMaxResults(pagination.getSize());
		}

		return q.list();
	}

	@Override
	public int getUserCapacityPlanningCount(Long id, Long year) throws Throwable {
		int count = 0;
		StringBuilder queryString = new StringBuilder();
		queryString.append(
				"select count (*) from PTS_USER_CAPACITY_PLANNING cp where cp.user_id=:userId and cp.year=:year ");
		Query q = getSession().createSQLQuery(queryString.toString());
		q.setLong("userId", id);
		q.setLong("year", year);
		BigInteger obj = (BigInteger) q.uniqueResult();
		if (obj != null) {
			count = obj.intValue();
		}
		return count;
	}

	@Override
	public int deleteCapacityPlanningDetails(List<Long> ids) throws Throwable {
		Query query = getSession().createQuery("delete from CapacityPlanning cp  where cp.id in (:id)");
		query.setParameterList("id", ids);
		return query.executeUpdate();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Set<Long> getNetworkCodesOfUser(Long id, Long year) throws Throwable {
		StringBuilder queryString = new StringBuilder();
		queryString.append(
				"select NETWORK_CODE_ID from PTS_USER_CAPACITY_PLANNING cp where cp.user_id=:userId and cp.year=:year ");

		Query q = getSession().createSQLQuery(queryString.toString());
		q.setLong("userId", id);
		q.setLong("year", year);

		List<Integer> listIds = q.list();
		Set<Long> entps = new HashSet<Long>();
		if (listIds != null) {
			for (Integer entId : listIds) {
				entps.add(entId.longValue());
			}
		}
		return entps;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Set<Long> getUsersOfNetworkCode(Long id, Long year) throws Throwable {
		StringBuilder queryString = new StringBuilder();
		queryString.append(
				"select user_ID from PTS_USER_CAPACITY_PLANNING cp where cp.NETWORK_CODE_ID=:networkId and cp.year=:year ");

		Query q = getSession().createSQLQuery(queryString.toString());
		q.setLong("networkId", id);
		q.setLong("year", year);

		List<Integer> listIds = q.list();
		Set<Long> entps = new HashSet<Long>();
		if (listIds != null) {
			for (Integer entId : listIds) {
				entps.add(entId.longValue());
			}
		}
		return entps;
	}

	@Override
	public int getUserCapacityPlanningReportCount(SearchSortContainer searchSortObj, Long year) throws Throwable {
		int count = 0;
		if (searchSortObj.getSearchSupervisor() != null && !searchSortObj.getSearchSupervisor().equalsIgnoreCase("")) {
			Query query = getSession().createSQLQuery(
					"CALL CAPACITY_SUMMARY_PROC_NEW(:id, :year, :status, :pillarId, :projectId, :technologyId, :region, :stream)");
			query.setParameter("id", searchSortObj.getSearchSupervisor());
			query.setParameter("year", year);
			query.setParameter("status", searchSortObj.getSearchStatus());
			query.setParameter("pillarId", searchSortObj.getPillarId());
			query.setParameter("projectId", searchSortObj.getProjectId());
			query.setParameter("technologyId", searchSortObj.getTechnologyId());
			query.setParameter("region", searchSortObj.getRegion());
			query.setParameter("stream", searchSortObj.getStreamId());
			query.executeUpdate();

			Query q = getSession().createSQLQuery("SELECT count(*) FROM temp_capacity_summary");
			BigInteger obj = (BigInteger) q.uniqueResult();
			if (obj != null) {
				count = obj.intValue();
			}
		}
		return count;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<com.egil.pts.modal.CapacityPlanning> getUserCapacityPlanningReportDetails(
			SearchSortContainer searchSortObj, Long year, Pagination pagination) throws Throwable {
		List<com.egil.pts.modal.CapacityPlanning> resourceUtilizationList = new ArrayList<com.egil.pts.modal.CapacityPlanning>();
		if (searchSortObj.getSearchSupervisor() != null && !searchSortObj.getSearchSupervisor().equalsIgnoreCase("")) {
			Query query = getSession().createSQLQuery(
					"CALL CAPACITY_SUMMARY_PROC_NEW(:id, :year, :status, :pillarId, :projectId, :technologyId, :region, :stream)");
			query.setParameter("id", searchSortObj.getSearchSupervisor());
			query.setParameter("year", year);
			query.setParameter("status", searchSortObj.getSearchStatus());
			query.setParameter("pillarId", searchSortObj.getPillarId());
			query.setParameter("projectId", searchSortObj.getProjectId());
			query.setParameter("technologyId", searchSortObj.getTechnologyId());
			query.setParameter("region", searchSortObj.getRegion());
			query.setParameter("stream", searchSortObj.getStreamId());
			query.executeUpdate();

			Query tmpQry = getSession().createSQLQuery(
					"select userName, supervisorName, headCount, targetHrs,  ifnull(ROUND((targetHrs - janCapacity)),0) janCapacity,ifnull(ROUND( (targetHrs - febCapacity)),0) febCapacity, "
							+ " ifnull(ROUND((targetHrs - marCapacity)),0) marCapacity, ifnull(ROUND((targetHrs - aprCapacity)),0) aprCapacity, ifnull(ROUND((targetHrs - mayCapacity)),0) mayCapacity, "
							+ " ifnull(ROUND((targetHrs - junCapacity)),0) junCapacity, ifnull(ROUND((targetHrs - julCapacity)),0) julCapacity, ifnull(ROUND((targetHrs - augCapacity)),0) augCapacity, "
							+ " ifnull(ROUND((targetHrs - sepCapacity)),0) sepCapacity, ifnull(ROUND((targetHrs - octCapacity)),0) octCapacity, ifnull(ROUND((targetHrs - novCapacity)),0) novCapacity, "
							+ " ifnull(ROUND((targetHrs - decCapacity)),0) decCapacity from "
							+ "	(SELECT userName, supervisorName, headCount, ROUND(targetHrs,2) targetHrs, "
							+ " ifnull(janCapacity,0) janCapacity, ifnull(febCapacity,0) febCapacity, ifnull(marCapacity,0) marCapacity, "
							+ " ifnull(aprCapacity,0) aprCapacity, ifnull(mayCapacity,0) mayCapacity, ifnull(junCapacity,0) junCapacity, "
							+ " ifnull(julCapacity,0) julCapacity, ifnull(augCapacity,0) augCapacity, ifnull(sepCapacity,0) sepCapacity, "
							+ " ifnull(octCapacity,0) octCapacity, ifnull(novCapacity,0) novCapacity, ifnull(decCapacity,0) decCapacity "
							+ " FROM temp_capacity_summary order by createdDate asc) t")
					.addScalar("userName", new StringType()).addScalar("supervisorName", new StringType())
					.addScalar("headCount", new LongType()).addScalar("targetHrs", new DoubleType())
					.addScalar("janCapacity", new FloatType()).addScalar("febCapacity", new FloatType())
					.addScalar("marCapacity", new FloatType()).addScalar("aprCapacity", new FloatType())
					.addScalar("mayCapacity", new FloatType()).addScalar("junCapacity", new FloatType())
					.addScalar("julCapacity", new FloatType()).addScalar("augCapacity", new FloatType())
					.addScalar("sepCapacity", new FloatType()).addScalar("octCapacity", new FloatType())
					.addScalar("novCapacity", new FloatType()).addScalar("decCapacity", new FloatType());
			if (pagination != null) {
				if (pagination.getOffset() > 0)
					query.setFirstResult(pagination.getOffset());
				if (pagination.getSize() > 0)
					query.setMaxResults(pagination.getSize());
			}

			resourceUtilizationList = tmpQry
					.setResultTransformer(Transformers.aliasToBean(com.egil.pts.modal.CapacityPlanning.class)).list();
		}

		return resourceUtilizationList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public int getUserCapacityPlanningDetailReportCount(SearchSortContainer searchSortObj, Long year) throws Throwable {
		int count = 0;
		StringBuilder queryString = new StringBuilder();
		List<Long> userIdList = new ArrayList<Long>();
		if (searchSortObj.getSearchSupervisor() != null && !searchSortObj.getSearchSupervisor().equalsIgnoreCase("")) {
			Query query = getSession().createSQLQuery("CALL user_id_heirarchy_proc(:id)");
			query.setParameter("id", searchSortObj.getSearchSupervisor());
			query.executeUpdate();

			Query tmpQry = getSession().createSQLQuery("SELECT node FROM _result");
			userIdList = tmpQry.list();
		}
		queryString.append(
				" select count(*) from (select user_id from PTS_USER_CAPACITY_PLANNING cp, PTS_USER u where cp.user_id=u.id and u.status  not in ('Others', 'No Show', 'Deleted') and cp.year=:year");
		if (userIdList != null && userIdList.size() > 0) {
			queryString.append("  and cp.user_id in (:userIdList) group by cp.user_id) t ");
		} else {
			queryString.append("  group by cp.user_id) t ");
		}
		Query q = getSession().createSQLQuery(queryString.toString());
		q.setLong("year", year);

		if (userIdList != null && userIdList.size() > 0) {
			q.setParameterList("userIdList", userIdList);
		}

		BigInteger obj = (BigInteger) q.uniqueResult();
		if (obj != null) {
			count = obj.intValue();
		}
		return count;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<com.egil.pts.modal.CapacityPlanning> getUserCapacityPlanningDetailReportDetails(
			SearchSortContainer searchSortObj, Long year, Pagination pagination) throws Throwable {
		StringBuilder queryString = new StringBuilder();
		List<Long> userIdList = new ArrayList<Long>();
		if (searchSortObj.getSearchSupervisor() != null && !searchSortObj.getSearchSupervisor().equalsIgnoreCase("")) {
			Query query = getSession().createSQLQuery("CALL user_id_heirarchy_proc(:id)");
			query.setParameter("id", searchSortObj.getSearchSupervisor());
			query.executeUpdate();

			Query tmpQry = getSession().createSQLQuery("SELECT node FROM _result");
			userIdList = tmpQry.list();
		}
		queryString.append(" select * from (select u.id id, u.name 'userName', t.TECHNOLOGY_NAME primarySkill, "
				+ " (select name from PTS_USER where id in (select distinct tus.supervisor_id from PTS_USER_SUPERVISOR tus where tus.user_id=u.id)) supervisorName, "
				+ " sum(cp.JAN_CPTY) 'janCapacity', sum(cp.FEB_CPTY) 'febCapacity', "
				+ " sum(cp.MAR_CPTY) 'marCapacity', sum(cp.APR_CPTY) 'aprCapacity', "
				+ " sum(cp.MAY_CPTY) 'mayCapacity', sum(cp.JUN_CPTY) 'junCapacity', "
				+ " sum(cp.JUL_CPTY) 'julCapacity', sum(cp.AUG_CPTY) 'augCapacity', "
				+ " sum(cp.SEP_CPTY) 'sepCapacity', sum(cp.OCT_CPTY) 'octCapacity', "
				+ " sum(cp.NOV_CPTY) 'novCapacity', sum(cp.DEC_CPTY) 'decCapacity' "
				+ " from PTS_USER_CAPACITY_PLANNING cp, PTS_USER u, PTS_USER_SKILLS us, PTS_TECHNOLOGIES t where cp.year="
				+ year
				+ " and u.id=us.user_id and u.status  not in ('Others', 'No Show', 'Deleted') and us.primary_flag='Y' and us.TECHNOLOGY_ID = t.id and cp.user_id=u.id ");

		if (userIdList != null && userIdList.size() > 0) {
			queryString.append(" and cp.user_id in (:userIdList) ");
		}
		if (searchSortObj.getSearchUserName() != null && !searchSortObj.getSearchUserName().equalsIgnoreCase("")) {
			queryString.append(" and u.name like '%" + searchSortObj.getSearchUserName() + "%'");
		}

		queryString.append(" group by cp.user_id ) t ");

		if (searchSortObj.getSidx() != null && !searchSortObj.getSidx().equals("") && searchSortObj.getSord() != null
				&& !searchSortObj.getSord().equals("")) {
			if (searchSortObj.getSidx().equalsIgnoreCase("userName")) {
				queryString.append(" order by userName " + searchSortObj.getSord());
			} else if (searchSortObj.getSidx().equalsIgnoreCase("supervisorName")) {
				queryString.append(" order by supervisorName " + searchSortObj.getSord());
			}
		} else {
			queryString.append(" order by userName " + searchSortObj.getSord());
		}

		Query query = getSession().createSQLQuery(queryString.toString()).addScalar("id", new LongType())
				.addScalar("userName", new StringType()).addScalar("supervisorName", new StringType())
				.addScalar("janCapacity", new FloatType()).addScalar("febCapacity", new FloatType())
				.addScalar("marCapacity", new FloatType()).addScalar("aprCapacity", new FloatType())
				.addScalar("mayCapacity", new FloatType()).addScalar("junCapacity", new FloatType())
				.addScalar("julCapacity", new FloatType()).addScalar("augCapacity", new FloatType())
				.addScalar("sepCapacity", new FloatType()).addScalar("octCapacity", new FloatType())
				.addScalar("novCapacity", new FloatType()).addScalar("decCapacity", new FloatType())
				.addScalar("primarySkill", new StringType());

		if (userIdList != null && userIdList.size() > 0) {
			query.setParameterList("userIdList", userIdList);
		}

		if (pagination != null) {
			if (pagination.getOffset() > 0)
				query.setFirstResult(pagination.getOffset());
			if (pagination.getSize() > 0)
				query.setMaxResults(pagination.getSize());
		}

		List<com.egil.pts.modal.CapacityPlanning> resourceUtilizationList = query
				.setResultTransformer(Transformers.aliasToBean(com.egil.pts.modal.CapacityPlanning.class)).list();
		return resourceUtilizationList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<com.egil.pts.modal.CapacityPlanning> getNetworkCodesList(SearchSortContainer searchSortObj, Long year,
			Pagination pagination, boolean showPrevYearTot) throws Throwable {
		String queryString = "";
		List<Long> userIdList = new ArrayList<Long>();
		if (searchSortObj.getSearchSupervisor() != null && !searchSortObj.getSearchSupervisor().equalsIgnoreCase("")) {
			Query query = getSession().createSQLQuery("CALL user_id_heirarchy_proc(:id)");
			query.setParameter("id", searchSortObj.getSearchSupervisor());
			query.executeUpdate();

			Query tmpQry = getSession().createSQLQuery("SELECT node FROM _result");
			userIdList = tmpQry.list();
		}
		/*
		 * queryString
		 * .append(" select * from (select cp.NETWORK_CODE_ID 'id',cp.NETWORK_CODE_ID as 'networkCodeId', concat(nc.release_id, '-', nc.release_name) 'networkCode', "
		 * +
		 * " (select project_name from PTS_PROJECT where id in (select distinct tus.project_id from PTS_NETWORK_CODES tus where tus.id=nc.id)) appName, "
		 * +" (nc.ORIGINAL_DESIGN_LOE +nc.ORIGINAL_DEV_LOE	+nc.ORIGINAL_TEST_LOE +nc.ORIGINAL_PROJ_MGMT_LOE +nc.ORIGINAL_IMPL_LOE )  'totalLoe', "
		 * +" ORIGINAL_PROJ_MGMT_LOE 'pmProjectLoe', " +
		 * " sum(cp.JAN_CPTY) 'janCapacity', sum(cp.FEB_CPTY) 'febCapacity', " +
		 * " sum(cp.MAR_CPTY) 'marCapacity', sum(cp.APR_CPTY) 'aprCapacity', " +
		 * " sum(cp.MAY_CPTY) 'mayCapacity', sum(cp.JUN_CPTY) 'junCapacity', " +
		 * " sum(cp.JUL_CPTY) 'julCapacity', sum(cp.AUG_CPTY) 'augCapacity', " +
		 * " sum(cp.SEP_CPTY) 'sepCapacity', sum(cp.OCT_CPTY) 'octCapacity', " +
		 * " sum(cp.NOV_CPTY) 'novCapacity', sum(cp.DEC_CPTY) 'decCapacity', " +
		 * " nc.original_dev_loe  'devLoe',nc.original_test_loe  'testLoe' " +
		 * " from PTS_USER_CAPACITY_PLANNING cp, PTS_NETWORK_CODES nc,PTS_PROJECT pj   "
		 * + " where nc.id=cp.NETWORK_CODE_ID  and pj.id=nc.project_id and cp.year=" +
		 * year + " ");
		 */

		/*
		 * queryString = queryString +
		 * " select * from (select cp.NETWORK_CODE_ID 'id',cp.NETWORK_CODE_ID as 'networkCodeId', concat(nc.release_id, '-', nc.release_name) 'networkCode', "
		 * +
		 * " (select project_name from PTS_PROJECT where id in (select distinct tus.project_id from PTS_NETWORK_CODES tus where tus.id=nc.id)) appName, "
		 * +
		 * " (nc.ORIGINAL_DESIGN_LOE +nc.ORIGINAL_DEV_LOE	+nc.ORIGINAL_TEST_LOE +nc.ORIGINAL_PROJ_MGMT_LOE +nc.ORIGINAL_IMPL_LOE )  'totalLoe', "
		 * +
		 * " ORIGINAL_PROJ_MGMT_LOE 'pmProjectLoe',  nc.global_dev_loe  'devLoe',nc.global_test_loe  'testLoe'  , "
		 * +
		 * " (select  round((sum(ifnull(ucp.JAN_CPTY,0)) + sum(ifnull(ucp.FEB_CPTY,0))  +  sum(ifnull(ucp.MAR_CPTY,0)) + "
		 * +
		 * " sum(ifnull(ucp.APR_CPTY,0))  +  sum(ifnull(ucp.MAY_CPTY,0))  + sum(ifnull(ucp.JUN_CPTY,0)) +  "
		 * +
		 * " sum(ifnull(ucp.JUL_CPTY,0))  + sum(ifnull(ucp.AUG_CPTY,0))  +  sum(ifnull(ucp.SEP_CPTY,0))  + sum(ifnull(ucp.OCT_CPTY,0))  +   "
		 * +
		 * " sum(ifnull(ucp.NOV_CPTY,0)) + sum(ifnull(ucp.DEC_CPTY,0)) )) totalManagerCapacity from PTS_USER_CAPACITY_PLANNING ucp where nc.id=ucp.NETWORK_CODE_ID &userIdListCond &yearCond) totalManagerCapacity, "
		 * +
		 * " (select  round((sum(ifnull(mon_hrs,0))+sum(ifnull(tue_hrs,0))+sum(ifnull(wed_hrs,0))+sum(ifnull(thu_hrs,0))+sum(ifnull(fri_hrs,0))+sum(ifnull(sat_hrs,0))+sum(ifnull(sun_hrs,0))),2)  "
		 * + " from PTS_USER_TIMESHEET t where " +
		 * " t.user_id in (:userIdList)  and t.network_code_id=cp.NETWORK_CODE_ID) totalTimeSheetCap "
		 * +
		 * " from PTS_USER_NETWORK_CODES cp, PTS_NETWORK_CODES nc,PTS_PROJECT pj, PTS_USER u  "
		 * +
		 * " where nc.id=cp.NETWORK_CODE_ID  and pj.id=nc.project_id and u.id=cp.user_id and u.status not in ('OffBoard', 'No Show', 'Deleted') "
		 * ;
		 */

		queryString = queryString
				+ " select * from (select cp.NETWORK_CODE_ID 'id',cp.NETWORK_CODE_ID as 'networkCodeId', concat(nc.release_id, '-', nc.release_name) 'networkCode', "
				+ " (select project_name from PTS_PROJECT where id in (select distinct tus.project_id from PTS_NETWORK_CODES tus where tus.id=nc.id)) appName, "
				+ " (nc.ORIGINAL_DESIGN_LOE +nc.ORIGINAL_DEV_LOE	+nc.ORIGINAL_TEST_LOE +nc.ORIGINAL_PROJ_MGMT_LOE +nc.ORIGINAL_IMPL_LOE )  'totalLoe', "
				+ " ORIGINAL_PROJ_MGMT_LOE 'pmProjectLoe',  nc.global_dev_loe  'devLoe',nc.global_test_loe  'testLoe'  , "
				+ " (select  round((sum(ifnull(ucp.JAN_CPTY,0)) + sum(ifnull(ucp.FEB_CPTY,0))  +  sum(ifnull(ucp.MAR_CPTY,0)) + "
				+ " sum(ifnull(ucp.APR_CPTY,0))  +  sum(ifnull(ucp.MAY_CPTY,0))  + sum(ifnull(ucp.JUN_CPTY,0)) +  "
				+ " sum(ifnull(ucp.JUL_CPTY,0))  + sum(ifnull(ucp.AUG_CPTY,0))  +  sum(ifnull(ucp.SEP_CPTY,0))  + sum(ifnull(ucp.OCT_CPTY,0))  +   "
				+ " sum(ifnull(ucp.NOV_CPTY,0)) + sum(ifnull(ucp.DEC_CPTY,0)) )) totalManagerCapacity from PTS_USER_CAPACITY_PLANNING ucp where nc.id=ucp.NETWORK_CODE_ID &userIdListCond &yearCond) totalManagerCapacity, "
				+ " (&timesheetquery) totalTimeSheetCap "
				+ " from PTS_USER_NETWORK_CODES cp, PTS_NETWORK_CODES nc,PTS_PROJECT pj, PTS_USER u  "
				+ " where nc.id=cp.NETWORK_CODE_ID and nc.status not in ('Completed','Implemented', 'In-Active') and pj.id=nc.project_id and u.id=cp.user_id and u.status not in ('OffBoard', 'No Show', 'Deleted') ";
		if (showPrevYearTot) {
			queryString = "SELECT *,(t.TOT_2019_CHRGD+ IFNULL(t.totalTimeSheetCap,0)) 'totCharg', (t.TOT_2019_CPTY+ IFNULL(t.totalManagerCapacity,0)) 'totCap' " + 
					"FROM ( " + 
					"SELECT   SUM(IFNULL(cp.TOT_2019_CPTY,0)) 'TOT_2019_CPTY',SUM(IFNULL(cp.TOT_2019_CHRGD,0)) 'TOT_2019_CHRGD',(SUM(IFNULL(cp.TOT_2019_LOE,0))+nc.ORIGINAL_DESIGN_LOE +nc.ORIGINAL_DEV_LOE	+nc.ORIGINAL_TEST_LOE +nc.ORIGINAL_PROJ_MGMT_LOE +nc.ORIGINAL_IMPL_LOE) 'totLoe', SUM(IFNULL(cp.TOT_2019_DEV_LOE,0))+nc.global_dev_loe 'totDevLoe', SUM(IFNULL(cp.TOT_2019_TEST_LOE,0))+global_test_loe 'totTestLoe',SUM(IFNULL(cp.TOT_2019_PTL_LOE,0)) 'totPtlLoe', SUM(IFNULL(cp.TOT_2019_PM_LOE,0))+ORIGINAL_PROJ_MGMT_LOE 'totPmLoe', SUM(IFNULL(cp.TOT_2019_SE_LOE,0)) 'totSeLoe',cp.NETWORK_CODE_ID 'id',cp.NETWORK_CODE_ID AS 'networkCodeId', CONCAT(nc.release_id, '-', nc.release_name) 'networkCode', ( " + 
					"SELECT project_name " + 
					"FROM PTS_PROJECT " + 
					"WHERE id IN ( " + 
					"SELECT DISTINCT tus.project_id " + 
					"FROM PTS_NETWORK_CODES tus " + 
					"WHERE tus.id=nc.id)) appName, (nc.ORIGINAL_DESIGN_LOE +nc.ORIGINAL_DEV_LOE	+nc.ORIGINAL_TEST_LOE +nc.ORIGINAL_PROJ_MGMT_LOE +nc.ORIGINAL_IMPL_LOE) 'totalLoe', ORIGINAL_PROJ_MGMT_LOE 'pmProjectLoe', nc.global_dev_loe 'devLoe',nc.global_test_loe 'testLoe', ( " + 
					"SELECT IFNULL(ROUND((SUM(IFNULL(ucp.JAN_CPTY,0)) + SUM(IFNULL(ucp.FEB_CPTY,0)) + SUM(IFNULL(ucp.MAR_CPTY,0)) + SUM(IFNULL(ucp.APR_CPTY,0)) + SUM(IFNULL(ucp.MAY_CPTY,0)) + SUM(IFNULL(ucp.JUN_CPTY,0)) + SUM(IFNULL(ucp.JUL_CPTY,0)) + SUM(IFNULL(ucp.AUG_CPTY,0)) + SUM(IFNULL(ucp.SEP_CPTY,0)) + SUM(IFNULL(ucp.OCT_CPTY,0)) + SUM(IFNULL(ucp.NOV_CPTY,0)) + SUM(IFNULL(ucp.DEC_CPTY,0)))),0) totalManagerCapacity " + 
					"FROM PTS_USER_CAPACITY_PLANNING ucp " + 
					"WHERE nc.id=ucp.NETWORK_CODE_ID &userIdListCond &yearCond) totalManagerCapacity, (&timesheetquery) totalTimeSheetCap " + 
					"FROM PTS_USER_NETWORK_CODES cp, PTS_NETWORK_CODES nc,PTS_PROJECT pj, PTS_USER u " + 
					"WHERE nc.id=cp.NETWORK_CODE_ID AND nc.status NOT IN ('Completed','Implemented', 'In-Active') AND pj.id=nc.project_id AND u.id=cp.user_id AND u.status NOT IN ('OffBoard', 'No Show', 'Deleted')   ";
		}
		if (userIdList != null && userIdList.size() > 0) {
			queryString = queryString + " and cp.user_id in (:userIdList) ";
			queryString = queryString.replaceAll("&userIdListCond", " and ucp.user_id in (:userIdList) ");
		} else {
			queryString = queryString.replaceAll("&userIdListCond", " ");
		}
		if (year != null && year != 0l) {
			String fromDate = "01/01/" + year;

			String endDate = "12/31/" + year;

			String startWeek = Utility.getWeekEnding(fromDate);

			String endWeek = Utility.getWeekEnding(endDate);

			SimpleDateFormat sdformat = new SimpleDateFormat("MM/dd/yyyy");

			String startDay = (new SimpleDateFormat("EEE")).format(sdformat.parse(fromDate));
			String endDay = (new SimpleDateFormat("EEE")).format(sdformat.parse(endDate));

			String startDayQryStr = "";
			String endDayQryStr = "";

			switch (startDay) {
			case "Mon":
				startDayQryStr = " round((sum(ifnull(mon_hrs,0))+sum(ifnull(tue_hrs,0))+sum(ifnull(wed_hrs,0))+sum(ifnull(thu_hrs,0))+sum(ifnull(fri_hrs,0))+sum(ifnull(sat_hrs,0))+sum(ifnull(sun_hrs,0))),2)  effort ";
				break;
			case "Tue":
				startDayQryStr = " round((sum(ifnull(tue_hrs,0))+sum(ifnull(wed_hrs,0))+sum(ifnull(thu_hrs,0))+sum(ifnull(fri_hrs,0))+sum(ifnull(sat_hrs,0))+sum(ifnull(sun_hrs,0))),2)  effort ";
				break;
			case "Wed":
				startDayQryStr = " round((sum(ifnull(wed_hrs,0))+sum(ifnull(thu_hrs,0))+sum(ifnull(fri_hrs,0))+sum(ifnull(sat_hrs,0))+sum(ifnull(sun_hrs,0))),2)  effort ";
				break;
			case "Thu":
				startDayQryStr = " round((sum(ifnull(thu_hrs,0))+sum(ifnull(fri_hrs,0))+sum(ifnull(sat_hrs,0))+sum(ifnull(sun_hrs,0))),2)  effort ";
				break;
			case "Fri":
				startDayQryStr = " round((sum(ifnull(fri_hrs,0))+sum(ifnull(sat_hrs,0))+sum(ifnull(sun_hrs,0))),2)  effort ";
				break;
			case "Sat":
				startDayQryStr = " round((sum(ifnull(sat_hrs,0))+sum(ifnull(sun_hrs,0))),2)  effort ";
				break;
			case "Sun":
				startDayQryStr = " round((sum(ifnull(sun_hrs,0))),2)  effort ";
				break;
			}

			switch (endDay) {
			case "Mon":
				endDayQryStr = " round((sum(ifnull(mon_hrs,0))),2)  effort ";
				break;
			case "Tue":
				endDayQryStr = " round((sum(ifnull(mon_hrs,0))+sum(ifnull(tue_hrs,0))),2)  effort ";
				break;
			case "Wed":
				endDayQryStr = " round((sum(ifnull(mon_hrs,0))+sum(ifnull(tue_hrs,0))+sum(ifnull(wed_hrs,0))),2)  effort ";
				break;
			case "Thu":
				endDayQryStr = " round((sum(ifnull(mon_hrs,0))+sum(ifnull(tue_hrs,0))+sum(ifnull(wed_hrs,0))+sum(ifnull(thu_hrs,0))),2)  effort ";
				break;
			case "Fri":
				endDayQryStr = " round((sum(ifnull(mon_hrs,0))+sum(ifnull(tue_hrs,0))+sum(ifnull(wed_hrs,0))+sum(ifnull(thu_hrs,0))+sum(ifnull(fri_hrs,0))),2)  effort ";
				break;
			case "Sat":
				endDayQryStr = " round((sum(ifnull(mon_hrs,0))+sum(ifnull(tue_hrs,0))+sum(ifnull(wed_hrs,0))+sum(ifnull(thu_hrs,0))+sum(ifnull(fri_hrs,0))+sum(ifnull(sat_hrs,0))),2)  effort ";
				break;
			case "Sun":
				endDayQryStr = " round((sum(ifnull(mon_hrs,0))+sum(ifnull(tue_hrs,0))+sum(ifnull(wed_hrs,0))+sum(ifnull(thu_hrs,0))+sum(ifnull(fri_hrs,0))+sum(ifnull(sat_hrs,0))+sum(ifnull(sun_hrs,0))),2)  effort ";
				break;
			}

			String timesheetQuery = "select IFNULL(sum(ifnull(effort,0)),0) from (select :startsummation, t.network_code_id from PTS_USER_TIMESHEET t where t.user_id in (:userIdList)  and weekending_date='"
					+ startWeek + "' group by t.network_code_id " + "union all "
					+ "select  round((sum(ifnull(mon_hrs,0))+sum(ifnull(tue_hrs,0))+sum(ifnull(wed_hrs,0))+sum(ifnull(thu_hrs,0))+sum(ifnull(fri_hrs,0))+sum(ifnull(sat_hrs,0))+sum(ifnull(sun_hrs,0))),2)  effort, t.network_code_id from PTS_USER_TIMESHEET t where t.user_id in (:userIdList) and weekending_date > '"
					+ startWeek + "' and WEEKENDING_DATE < '" + endWeek + "' group by t.network_code_id " + "union all "
					+ "select :endsummation, t.network_code_id from PTS_USER_TIMESHEET t where t.user_id in (:userIdList) and weekending_date='"
					+ endWeek + "' group by t.network_code_id) time1 where time1.network_code_id=cp.network_code_id ";
			String finalQryStr = timesheetQuery.replaceAll(":startsummation", startDayQryStr)
					.replaceAll(":endsummation", endDayQryStr);

			queryString = queryString.replaceAll("&timesheetquery", finalQryStr);
			queryString = queryString.replaceAll("&yearCond", " and ucp.year=:year ");
		} else {
			String timesheetQuery = " select  round((sum(ifnull(mon_hrs,0))+sum(ifnull(tue_hrs,0))+sum(ifnull(wed_hrs,0))+sum(ifnull(thu_hrs,0))+sum(ifnull(fri_hrs,0))+sum(ifnull(sat_hrs,0))+sum(ifnull(sun_hrs,0))),2)  "
					+ " from PTS_USER_TIMESHEET t where "
					+ " t.user_id in (:userIdList)  and t.network_code_id=cp.NETWORK_CODE_ID ";

			queryString = queryString.replaceAll("&timesheetquery", timesheetQuery);
			queryString = queryString.replaceAll("&yearCond", " ");
		}
		if (searchSortObj.isActiveFlag()) {
			queryString = queryString + " and project_stage is not null and project_stage <> 'Completed' ";
		}
//networkCode appName
		if (searchSortObj.getSearchProject() != null && !searchSortObj.getSearchProject().equalsIgnoreCase("")) {
			queryString = queryString + " and concat(nc.release_id, '-', nc.release_name) like '%"
					+ searchSortObj.getSearchProject() + "%'";
		}
		if (searchSortObj.getSearchProjectName() != null
				&& !searchSortObj.getSearchProjectName().equalsIgnoreCase("")) {
			queryString = queryString + " and pj.project_name like '%" + searchSortObj.getSearchProjectName() + "%'";
		}
		if (searchSortObj.getSearchUserName() != null && !searchSortObj.getSearchUserName().equalsIgnoreCase("")) {
			queryString = queryString + " and u.name like '%" + searchSortObj.getSearchUserName() + "%'";
		}
		queryString = queryString + " group by cp.NETWORK_CODE_ID ";

		if (searchSortObj.getOrderBy() != null && !searchSortObj.getOrderBy().equalsIgnoreCase("")) {
			queryString = queryString + " order by " + searchSortObj.getOrderBy() + "  "
					+ (searchSortObj.isDesc() ? "desc" : "asc");
		}
		queryString = queryString + " ) t ";
		
		Query query = null;
		if (showPrevYearTot) {
			query = 	getSession().createSQLQuery(queryString).addScalar("networkCodeId", new LongType())
					.addScalar("networkCode", new StringType()).addScalar("appName", new StringType())
					.addScalar("id", new LongType())
					.addScalar("devLoe", new LongType()).addScalar("testLoe", new LongType())
					.addScalar("totalManagerCapacity", new FloatType()).addScalar("totalTimeSheetCap", new FloatType())
					.addScalar("pmProjectLoe", new LongType()).addScalar("totCharg", new FloatType())
					.addScalar("totCap", new FloatType()).addScalar("totSeLoe", new FloatType())
					.addScalar("totPmLoe", new FloatType()).addScalar("totPtlLoe", new FloatType())
					.addScalar("totTestLoe", new FloatType()).addScalar("totDevLoe", new FloatType())
					.addScalar("totLoe", new FloatType());
		}else {
			getSession().createSQLQuery(queryString).addScalar("networkCodeId", new LongType())
			.addScalar("networkCode", new StringType()).addScalar("appName", new StringType())
			.addScalar("id", new LongType())
			/*
			 * .addScalar("janCapacity", new FloatType()).addScalar("febCapacity", new
			 * FloatType()) .addScalar("marCapacity", new
			 * FloatType()).addScalar("aprCapacity", new FloatType())
			 * .addScalar("mayCapacity", new FloatType()).addScalar("junCapacity", new
			 * FloatType()) .addScalar("julCapacity", new
			 * FloatType()).addScalar("augCapacity", new FloatType())
			 * .addScalar("sepCapacity", new FloatType()).addScalar("octCapacity", new
			 * FloatType()) .addScalar("novCapacity", new
			 * FloatType()).addScalar("decCapacity", new FloatType())
			 */
			.addScalar("devLoe", new LongType()).addScalar("testLoe", new LongType())
			.addScalar("totalManagerCapacity", new FloatType()).addScalar("totalTimeSheetCap", new FloatType())
			.addScalar("pmProjectLoe", new LongType());// .addScalar("totalLoe",new LongType());

		}
		if (year != null && year != 0l) {
			query.setLong("year", year);
		}
		if (userIdList != null && userIdList.size() > 0) {
			query.setParameterList("userIdList", userIdList);
		}

		if (pagination != null) {
			if (pagination.getOffset() > 0)
				query.setFirstResult(pagination.getOffset());
			if (pagination.getSize() > 0)
				query.setMaxResults(pagination.getSize());
		}
		List<com.egil.pts.modal.CapacityPlanning> resourceUtilizationList = query
				.setResultTransformer(Transformers.aliasToBean(com.egil.pts.modal.CapacityPlanning.class)).list();
		return resourceUtilizationList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public int getNetworkCodesListCount(SearchSortContainer searchSortObj, Long year) throws Throwable {
		int count = 0;
		StringBuilder queryString = new StringBuilder();
		List<Long> userIdList = new ArrayList<Long>();
		if (searchSortObj.getSearchSupervisor() != null && !searchSortObj.getSearchSupervisor().equalsIgnoreCase("")) {
			Query query = getSession().createSQLQuery("CALL user_id_heirarchy_proc(:id)");
			query.setParameter("id", searchSortObj.getSearchSupervisor());
			query.executeUpdate();

			Query tmpQry = getSession().createSQLQuery("SELECT node FROM _result");
			userIdList = tmpQry.list();
		}
		queryString.append(
				" select count(*) from (select cp.NETWORK_CODE_ID from PTS_NETWORK_CODES nc, PTS_USER_NETWORK_CODES cp, PTS_PROJECT pj, PTS_USER u  "
						+ " where nc.id=cp.NETWORK_CODE_ID and nc.status not in ('Completed','Implemented', 'In-Active') and pj.id=nc.project_id and u.id=cp.user_id and u.status not in ('OffBoard', 'No Show', 'Deleted') ");

		if (userIdList != null && userIdList.size() > 0) {
			queryString.append(" and cp.user_id in (:userIdList) ");
		}
		if (searchSortObj.isActiveFlag()) {
			queryString.append(" and project_stage  is not null and project_stage <> 'Completed' ");
		}
		if (searchSortObj.getSearchProject() != null && !searchSortObj.getSearchProject().equalsIgnoreCase("")) {
			queryString.append(" and concat(nc.release_id, '-', nc.release_name) like '%"
					+ searchSortObj.getSearchProject() + "%'");
		}
		if (searchSortObj.getSearchProjectName() != null
				&& !searchSortObj.getSearchProjectName().equalsIgnoreCase("")) {
			queryString.append(" and pj.project_name like '%" + searchSortObj.getSearchProjectName() + "%'");
		}
		if (searchSortObj.getSearchUserName() != null && !searchSortObj.getSearchUserName().equalsIgnoreCase("")) {
			queryString.append(" and u.name like '%" + searchSortObj.getSearchUserName() + "%'");
		}
		queryString.append(" group by cp.NETWORK_CODE_ID ) t ");
		Query q = getSession().createSQLQuery(queryString.toString());

		if (userIdList != null && userIdList.size() > 0) {
			q.setParameterList("userIdList", userIdList);
		}

		BigInteger obj = (BigInteger) q.uniqueResult();
		if (obj != null) {
			count = obj.intValue();
		}
		return count;
	}

	@SuppressWarnings("unchecked")
	@Override
	public int getNetworkCodesUserListCount(SearchSortContainer searchSortObj, Long year, Long networkcodeId)
			throws Throwable {
		int count = 0;
		StringBuilder queryString = new StringBuilder();
		List<Long> userIdList = new ArrayList<Long>();
		if (searchSortObj.getSearchSupervisor() != null && !searchSortObj.getSearchSupervisor().equalsIgnoreCase("")) {
			Query query = getSession().createSQLQuery("CALL user_id_heirarchy_proc(:id)");
			query.setParameter("id", searchSortObj.getSearchSupervisor());
			query.executeUpdate();

			Query tmpQry = getSession().createSQLQuery("SELECT node FROM _result");
			userIdList = tmpQry.list();
		}
		queryString.append(
				" select count(*) from (select NETWORK_CODE_ID from PTS_USER_CAPACITY_PLANNING cp where cp.year=:year ");

		if (networkcodeId != null && networkcodeId != 0l) {
			queryString.append(" and cp.NETWORK_CODE_ID =:networkcodeId ");
		}
		if (userIdList != null && userIdList.size() > 0) {
			queryString.append(" and cp.user_id in (:userIdList) ");
		}
		queryString.append(" group by cp.NETWORK_CODE_ID, cp.user_id ) t ");
		Query q = getSession().createSQLQuery(queryString.toString());
		q.setLong("year", year);
		if (networkcodeId != null && networkcodeId != 0l) {
			q.setLong("networkcodeId", networkcodeId);
		}
		if (userIdList != null && userIdList.size() > 0) {
			q.setParameterList("userIdList", userIdList);
		}

		BigInteger obj = (BigInteger) q.uniqueResult();
		if (obj != null) {
			count = obj.intValue();
		}
		return count;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<com.egil.pts.modal.CapacityPlanning> getNetworkCodesUsersCapacityList(SearchSortContainer searchSortObj,
			Long year, Pagination pagination, Long networkcodeId) throws Throwable {
		StringBuilder queryString = new StringBuilder();
		List<Long> userIdList = new ArrayList<Long>();
		if (searchSortObj.getSearchSupervisor() != null && !searchSortObj.getSearchSupervisor().equalsIgnoreCase("")) {
			Query query = getSession().createSQLQuery("CALL user_id_heirarchy_proc(:id)");
			query.setParameter("id", searchSortObj.getSearchSupervisor());
			query.executeUpdate();

			Query tmpQry = getSession().createSQLQuery("SELECT node FROM _result");
			userIdList = tmpQry.list();
		}
		queryString.append(
				" select * from (select u.name 'userName',u.name 'capUserId', cp.year 'year', cp.NETWORK_CODE_ID as 'networkCode',cp.NETWORK_CODE_ID as 'networkCodeId',"
						+ " cp.id id,cp.user_id as userId, "
						+ " (select name from PTS_USER where id in (select distinct tus.supervisor_id from PTS_USER_SUPERVISOR tus where tus.user_id=u.id)) supervisorName, "
						+ " sum(cp.JAN_CPTY) 'janCapacity', sum(cp.FEB_CPTY) 'febCapacity', "
						+ " sum(cp.MAR_CPTY) 'marCapacity', sum(cp.APR_CPTY) 'aprCapacity', "
						+ " sum(cp.MAY_CPTY) 'mayCapacity', sum(cp.JUN_CPTY) 'junCapacity', "
						+ " sum(cp.JUL_CPTY) 'julCapacity', sum(cp.AUG_CPTY) 'augCapacity', "
						+ " sum(cp.SEP_CPTY) 'sepCapacity', sum(cp.OCT_CPTY) 'octCapacity', "
						+ " sum(cp.NOV_CPTY) 'novCapacity', sum(cp.DEC_CPTY) 'decCapacity', "
						+ " cp.created_by createdBy, cp.created_date createdDate "
						+ " from PTS_USER_CAPACITY_PLANNING cp, PTS_USER u where cp.year=" + year
						+ " and cp.user_id=u.id ");

		if (networkcodeId != null && networkcodeId != 0l) {
			queryString.append(" and cp.NETWORK_CODE_ID =:networkcodeId ");
		}

		if (userIdList != null && userIdList.size() > 0) {
			queryString.append(" and cp.user_id in (:userIdList) ");
		}

		queryString.append(" group by cp.NETWORK_CODE_ID, cp.user_id ) t ");

		if (searchSortObj.getSearchUserName() != null && !searchSortObj.getSearchUserName().equalsIgnoreCase("")) {
			queryString.append(" and u.name like '%" + searchSortObj.getSearchUserName() + "%'");
		}

		Query query = getSession().createSQLQuery(queryString.toString()).addScalar("id", new LongType())
				.addScalar("year", new LongType()).addScalar("userId", new LongType())
				.addScalar("userName", new StringType()).addScalar("capUserId", new StringType())
				.addScalar("supervisorName", new StringType()).addScalar("janCapacity", new FloatType())
				.addScalar("febCapacity", new FloatType()).addScalar("marCapacity", new FloatType())
				.addScalar("aprCapacity", new FloatType()).addScalar("mayCapacity", new FloatType())
				.addScalar("junCapacity", new FloatType()).addScalar("julCapacity", new FloatType())
				.addScalar("augCapacity", new FloatType()).addScalar("sepCapacity", new FloatType())
				.addScalar("octCapacity", new FloatType()).addScalar("novCapacity", new FloatType())
				.addScalar("decCapacity", new FloatType()).addScalar("networkCode", new StringType())
				.addScalar("networkCodeId", new LongType()).addScalar("createdBy", new StringType())
				.addScalar("createdDate", new DateType());
		if (networkcodeId != null && networkcodeId != 0l) {
			query.setLong("networkcodeId", networkcodeId);
		}
		if (userIdList != null && userIdList.size() > 0) {
			query.setParameterList("userIdList", userIdList);
		}

		if (pagination != null) {
			if (pagination.getOffset() > 0)
				query.setFirstResult(pagination.getOffset());
			if (pagination.getSize() > 0)
				query.setMaxResults(pagination.getSize());
		}

		List<com.egil.pts.modal.CapacityPlanning> resourceUtilizationList = query
				.setResultTransformer(Transformers.aliasToBean(com.egil.pts.modal.CapacityPlanning.class)).list();
		return resourceUtilizationList;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<com.egil.pts.modal.CapacityPlanning> getCapacityDetailsByProjectType(SearchSortContainer searchSortObj,
			Long year, Pagination pagination, Long networkcodeId) throws Throwable {
		StringBuilder queryString = new StringBuilder();
		List<Long> userIdList = new ArrayList<Long>();
		if (searchSortObj.getSearchSupervisor() != null && !searchSortObj.getSearchSupervisor().equalsIgnoreCase("")) {
			Query query = getSession().createSQLQuery("CALL user_id_heirarchy_proc(:id)");
			query.setParameter("id", searchSortObj.getSearchSupervisor());
			query.executeUpdate();

			Query tmpQry = getSession().createSQLQuery("SELECT node FROM _result");
			userIdList = tmpQry.list();
		}
		queryString.append(
				"select tmp2.projectType projectType, tmp1.janCapacity janCapacity, tmp1.febCapacity febCapacity, "
						+ " tmp1.marCapacity marCapacity, tmp1.aprCapacity aprCapacity, "
						+ " tmp1.mayCapacity mayCapacity, tmp1.junCapacity junCapacity, "
						+ " tmp1.julCapacity julCapacity, tmp1.augCapacity augCapacity, "
						+ " tmp1.sepCapacity sepCapacity, tmp1.octCapacity octCapacity, "
						+ " tmp1.novCapacity novCapacity, tmp1.decCapacity decCapacity "
						+ " from ((select ifnull(nc.project_type, 'Project') projectType, sum(cp.JAN_CPTY) 'janCapacity', sum(cp.FEB_CPTY) 'febCapacity', "
						+ " sum(cp.MAR_CPTY) 'marCapacity', sum(cp.APR_CPTY) 'aprCapacity', "
						+ " sum(cp.MAY_CPTY) 'mayCapacity', sum(cp.JUN_CPTY) 'junCapacity', "
						+ " sum(cp.JUL_CPTY) 'julCapacity', sum(cp.AUG_CPTY) 'augCapacity', "
						+ " sum(cp.SEP_CPTY) 'sepCapacity', sum(cp.OCT_CPTY) 'octCapacity', "
						+ " sum(cp.NOV_CPTY) 'novCapacity', sum(cp.DEC_CPTY) 'decCapacity'  from "
						+ " PTS_USER_CAPACITY_PLANNING  cp, PTS_NETWORK_CODES nc "
						+ " where cp.NETWORK_CODE_ID=nc.id and cp.user_id in (:userIdList) &statusCond and cp.year=:year group by nc.project_type) tmp1 "
						+ " right outer join "
						+ " (select distinct ifnull(project_type, 'Project') projectType, null 'janCapacity', "
						+ " null 'febCapacity',null 'marCapacity',null 'aprCapacity',null 'mayCapacity', "
						+ " null 'junCapacity',null 'julCapacity',null 'augCapacity',null 'sepCapacity', "
						+ " null 'octCapacity',null 'novCapacity',null 'decCapacity' "
						+ " from PTS_NETWORK_CODES) tmp2 on tmp1.projectType = tmp2.projectType)");

		String tempStr = queryString.toString();
		if (searchSortObj.getSearchStatus() != null && !searchSortObj.getSearchStatus().equalsIgnoreCase("")) {
			if (searchSortObj.getSearchStatus().equalsIgnoreCase("All")) {
				tempStr = tempStr.replaceAll("&statusCond", " ");
			} else {
				tempStr = tempStr.replaceAll("&statusCond",
						" and cp.user_id in (select id from PTS_USER u where u.status=:status and id in (:userIdList)) ");
			}
		}
		Query query = getSession().createSQLQuery(tempStr).addScalar("projectType", new StringType())
				.addScalar("janCapacity", new FloatType()).addScalar("febCapacity", new FloatType())
				.addScalar("marCapacity", new FloatType()).addScalar("aprCapacity", new FloatType())
				.addScalar("mayCapacity", new FloatType()).addScalar("junCapacity", new FloatType())
				.addScalar("julCapacity", new FloatType()).addScalar("augCapacity", new FloatType())
				.addScalar("sepCapacity", new FloatType()).addScalar("octCapacity", new FloatType())
				.addScalar("novCapacity", new FloatType()).addScalar("decCapacity", new FloatType());

		if (userIdList != null && userIdList.size() > 0) {
			query.setParameterList("userIdList", userIdList);
		}

		if (searchSortObj.getSearchStatus() != null && !searchSortObj.getSearchStatus().equalsIgnoreCase("")) {
			if (!searchSortObj.getSearchStatus().equalsIgnoreCase("All")) {
				query.setString("status", searchSortObj.getSearchStatus());
			}
		}

		query.setLong("year", year);

		if (pagination != null) {
			if (pagination.getOffset() > 0)
				query.setFirstResult(pagination.getOffset());
			if (pagination.getSize() > 0)
				query.setMaxResults(pagination.getSize());
		}

		List<com.egil.pts.modal.CapacityPlanning> resourceUtilizationList = query
				.setResultTransformer(Transformers.aliasToBean(com.egil.pts.modal.CapacityPlanning.class)).list();
		return resourceUtilizationList;
	}

	@SuppressWarnings("unchecked")
	public com.egil.pts.modal.CapacityPlanning getCapacityModalById(Long id, Long year) throws Throwable {

		StringBuilder queryString = new StringBuilder();
		queryString.append(
				"select id 'id',year 'year',USER_ID 'userId' ,CREATED_DATE 'createdDate' ,CREATED_BY 'createdBy' from PTS_USER_CAPACITY_PLANNING where id=:id ");
		if (year != null && year != -1) {
			queryString.append(" and year=:year ");
		}
		/*
		 * createdBy=cp.getCreatedBy(); createdDate=cp.getCreatedDate();
		 * userId=cp.getUserId();
		 */

		Query query = getSession().createSQLQuery(queryString.toString()).addScalar("id", new LongType())
				.addScalar("year", new LongType()).addScalar("userId", new LongType())
				.addScalar("createdDate", new DateType()).addScalar("createdBy", new StringType());
		if (year != null && year != -1) {
			query.setParameter("year", year);
		}
		query.setParameter("id", id);
		List<com.egil.pts.modal.CapacityPlanning> resourceUtilizationList = query
				.setResultTransformer(Transformers.aliasToBean(com.egil.pts.modal.CapacityPlanning.class)).list();
		return resourceUtilizationList.get(0);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<com.egil.pts.modal.CapacityPlanning> getCapacityNyNetworkCode(List<Long> networkCode, Long year)
			throws Throwable {

		StringBuilder queryString = new StringBuilder();
		queryString
				.append(" select nc.id 'networkCodeId', sum(cp.JAN_CPTY) 'janCapacity', sum(cp.FEB_CPTY) 'febCapacity',"
						+ "  sum(cp.MAR_CPTY) 'marCapacity', sum(cp.APR_CPTY) 'aprCapacity', "
						+ "  sum(cp.MAY_CPTY) 'mayCapacity', sum(cp.JUN_CPTY) 'junCapacity', "
						+ "  sum(cp.JUL_CPTY) 'julCapacity', sum(cp.AUG_CPTY) 'augCapacity', "
						+ "  sum(cp.SEP_CPTY) 'sepCapacity', sum(cp.OCT_CPTY) 'octCapacity', "
						+ "  sum(cp.NOV_CPTY) 'novCapacity', sum(cp.DEC_CPTY) 'decCapacity', "
						+ "  SUM(ORIGINAL_DESIGN_LOE) 'originalDesLOE', SUM(ORIGINAL_DEV_LOE) 'originalDevLoe',"
						+ "  SUM(ORIGINAL_TEST_LOE) 'originalTestLoe', SUM(ORIGINAL_PROJ_MGMT_LOE) 'originalPmLoe',"
						+ "   SUM(ORIGINAL_IMPL_LOE ) 'originalImplLoe'"
						+ "   ,nc.status , sum(nc.global_dev_loe) 'globalDevLoe',sum(global_test_loe) 'globalTestLoe',sum(local_dev_loe) 'localDevLoe',"
						+ "	sum(local_test_loe) 'localTestLoe'"
						+ "	  from PTS_USER_CAPACITY_PLANNING cp, PTS_NETWORK_CODES nc "
						+ " 	  where nc.id=cp.NETWORK_CODE_ID and  nc.id in (:nwId)  ");
		if (year != null && year != -1) {
			queryString.append(" and year= :year ");
		}
		queryString.append(" group by nc.id   ");

		Query query = getSession().createSQLQuery(queryString.toString()).addScalar("status", new StringType())
				.addScalar("globalDevLoe", new LongType()).addScalar("globalTestLoe", new LongType())
				.addScalar("localDevLoe", new LongType()).addScalar("localTestLoe", new LongType())
				.addScalar("originalDevLoe", new LongType()).addScalar("originalTestLoe", new LongType())
				.addScalar("janCapacity", new FloatType()).addScalar("febCapacity", new FloatType())
				.addScalar("marCapacity", new FloatType()).addScalar("aprCapacity", new FloatType())
				.addScalar("mayCapacity", new FloatType()).addScalar("junCapacity", new FloatType())
				.addScalar("julCapacity", new FloatType()).addScalar("augCapacity", new FloatType())
				.addScalar("sepCapacity", new FloatType()).addScalar("octCapacity", new FloatType())
				.addScalar("novCapacity", new FloatType()).addScalar("decCapacity", new FloatType())
				.addScalar("networkCodeId", new LongType()).addScalar("originalPmLoe", new LongType())
				.addScalar("originalImplLoe", new LongType()).addScalar("originalDesLOE", new LongType());

		if (year != null && year != -1) {
			query.setParameter("year", year);
		}
		query.setParameterList("nwId", networkCode);
		return query.setResultTransformer(Transformers.aliasToBean(com.egil.pts.modal.CapacityPlanning.class)).list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<com.egil.pts.modal.CapacityPlanning> getUserCapacityByName(SearchSortContainer search) {
		try {
			StringBuilder queryString = new StringBuilder();
			String month[] = { "", "JAN_CPTY", "FEB_CPTY", "MAR_CPTY", "APR_CPTY", "MAY_CPTY", "JUN_CPTY", "JUL_CPTY",
					"AUG_CPTY", "SEP_CPTY", "OCT_CPTY", "NOV_CPTY", "DEC_CPTY" };
			queryString.append("select  concat(nc.release_id, '-', nc.release_name) 'appName' ,  ");

			SimpleDateFormat sdformat = new SimpleDateFormat("MM/dd/yyyy");
			Calendar c = new GregorianCalendar();
			c.set(Calendar.YEAR, search.getSearchYear().intValue());
			c.set(Calendar.MONTH, search.getSearchMonth() - 1);
			c.set(Calendar.DAY_OF_MONTH, 1);
			String startDayQryStr = "";
			String endDayQryStr = "";
			String fromDate = "";
			String toDate = "";
			int minDays = 0;
			int maxDays = 0;
			minDays = c.getActualMinimum(Calendar.DAY_OF_MONTH);
			maxDays = c.getActualMaximum(Calendar.DAY_OF_MONTH);
			fromDate = search.getSearchMonth() + "/" + minDays + "/" + search.getSearchYear();
			toDate = search.getSearchMonth() + "/" + maxDays + "/" + search.getSearchYear();
			String startWeek = Utility.getWeekEnding(fromDate);
			String endWeek = Utility.getWeekEnding(toDate);
			String startDay = (new SimpleDateFormat("EEE")).format(sdformat.parse(fromDate));
			String endDay = (new SimpleDateFormat("EEE")).format(sdformat.parse(toDate));

			switch (search.getSearchMonth()) {
			case 1:
				queryString.append(" (cp." + month[search.getSearchMonth()] + ") 'targetHrs' ");

				break;
			case 2:
				queryString.append(" (cp." + month[search.getSearchMonth()] + ") 'targetHrs' ");
				break;
			case 3:
				queryString.append(" (cp." + month[search.getSearchMonth()] + ") 'targetHrs' ");
				break;
			case 4:
				queryString.append(" (cp." + month[search.getSearchMonth()] + ") 'targetHrs' ");
				break;
			case 5:
				queryString.append(" (cp." + month[search.getSearchMonth()] + ") 'targetHrs' ");
				break;
			case 6:
				queryString.append(" (cp." + month[search.getSearchMonth()] + ") 'targetHrs' ");
				break;
			case 7:
				queryString.append(" (cp." + month[search.getSearchMonth()] + ") 'targetHrs' ");
				break;
			case 8:
				queryString.append(" (cp." + month[search.getSearchMonth()] + ") 'targetHrs' ");
				break;
			case 9:
				queryString.append(" (cp." + month[search.getSearchMonth()] + ") 'targetHrs' ");
				break;
			case 10:
				queryString.append(" (cp." + month[search.getSearchMonth()] + ") 'targetHrs' ");
				break;
			case 11:
				queryString.append(" (cp." + month[search.getSearchMonth()] + ") 'targetHrs' ");
				break;
			case 12:
				queryString.append(" (cp." + month[search.getSearchMonth()] + ") 'targetHrs' ");
				break;
			}

			String chargedHrsQry = " (select ifnull(round(sum(effort),2),0) from (select :startsummation, user_id, network_code_id, WEEKENDING_DATE  from PTS_USER_TIMESHEET where user_id=:userId and WEEKENDING_DATE = '"
					+ startWeek + "' " + " union all "
					+ " select (MON_HRS+TUE_HRS+WED_HRS+THU_HRS+FRI_HRS+SAT_HRS+SUN_HRS) EFFORT, user_id, network_code_id, WEEKENDING_DATE from PTS_USER_TIMESHEET where user_id=:userId and WEEKENDING_DATE > '"
					+ startWeek + "' and WEEKENDING_DATE < '" + endWeek + "' " + " union all "
					+ " select :endsummation, user_id, network_code_id, WEEKENDING_DATE from PTS_USER_TIMESHEET where  user_id=:userId and WEEKENDING_DATE = '"
					+ endWeek + "' ) t where network_code_id=nc.id group by network_code_id) 'chargedHrs'";

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

			String finalChargedHrsQryStr = chargedHrsQry.replaceAll(":startsummation", startDayQryStr)
					.replaceAll(":endsummation", endDayQryStr);
			queryString.append(" , " + finalChargedHrsQryStr);
			queryString.append(
					"  from PTS_USER_CAPACITY_PLANNING cp, PTS_NETWORK_CODES nc , PTS_USER_NETWORK_CODES un  where "
							+ month[search.getSearchMonth()]
							+ " is not null and nc.id=un.NETWORK_CODE_ID   and nc.id=cp.NETWORK_CODE_ID  and cp.user_id=un.user_id   and cp.year=:year  and  cp.user_id=:userId ");

			Query query = getSession().createSQLQuery(queryString.toString()).addScalar("appName", new StringType())
					.addScalar("targetHrs", new DoubleType()).addScalar("chargedHrs", new FloatType());

			query.setParameter("year", search.getSearchYear());
			query.setParameter("userId", search.getUserId());

			return (List<com.egil.pts.modal.CapacityPlanning>) query
					.setResultTransformer(Transformers.aliasToBean(com.egil.pts.modal.CapacityPlanning.class)).list();
		} catch (ParseException e) {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<com.egil.pts.modal.User> getUnPlannedCapacityBySupervisor(SearchSortContainer search,
			boolean bySupervisor) throws Throwable {

		String queryString = "select distinct(concat(nc.id,'-', nc.release_id, '-', nc.release_name,'-') ) 'applicationName' "
				+ " from PTS_USER_NETWORK_CODES un, PTS_NETWORK_CODES nc where un.user_id=:userId "
				+ " and un.NETWORK_CODE_ID=nc.id and  nc.status not in ('Completed','Implemented', 'In-Active') "
				+ " and (nc.impl_actual_date is  null or (nc.impl_actual_date is not null and (month(impl_actual_date)) > (month(current_date)) "
				+ " and year(impl_actual_date) >=  year(current_date))) and  un.NETWORK_CODE_ID not in (select NETWORK_CODE_ID from PTS_USER_CAPACITY_PLANNING where user_id=:userId)";

		Query query = getSession().createSQLQuery(queryString).addScalar("applicationName", new StringType());
		query.setParameter("userId", search.getSearchSupervisor());

		List<com.egil.pts.modal.User> a = query
				.setResultTransformer(Transformers.aliasToBean(com.egil.pts.modal.User.class)).list();
		return a;
	}

	private static String getMonth(int mon) {
		String s = "";
		for (int i = mon - 1; i < MONTHS.length; i++) {
			s += MONTHS[i];
		}
		s = s.substring(0, s.length() - 4);
		return s;
	}

}
