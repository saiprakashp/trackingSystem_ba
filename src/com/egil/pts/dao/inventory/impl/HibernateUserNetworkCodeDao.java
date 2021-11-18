package com.egil.pts.dao.inventory.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.hibernate.Query;
import org.hibernate.ScrollableResults;
import org.hibernate.internal.util.StringHelper;
import org.hibernate.transform.Transformers;
import org.hibernate.type.DoubleType;
import org.hibernate.type.LongType;
import org.hibernate.type.StringType;
import org.springframework.stereotype.Repository;

import com.egil.pts.dao.common.hibernate.HibernateGenericDao;
import com.egil.pts.dao.domain.NetworkCodes;
import com.egil.pts.dao.domain.UserNetworkCodes;
import com.egil.pts.dao.inventory.UserNetworkCodeDao;
import com.egil.pts.modal.Pagination;
import com.egil.pts.modal.SearchSortContainer;
import com.egil.pts.modal.StableTeams;
import com.egil.pts.modal.User;

@Repository("userNetworkCodeDao")
public class HibernateUserNetworkCodeDao extends HibernateGenericDao<UserNetworkCodes, Long>
		implements UserNetworkCodeDao {

	@Override
	public void saveUserNetworkCodes(List<UserNetworkCodes> userNetworkCodesList) throws Throwable {
		int count = 0;
		if (userNetworkCodesList != null && userNetworkCodesList.size() > 0) {
			for (UserNetworkCodes userNetworkCode : userNetworkCodesList) {
				save(userNetworkCode);
				getSession().flush();
				count++;
			}
		}

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<NetworkCodes> getResourceNetworkCodes(Long userId, String searchValue, Set<Long> idList)
			throws Throwable {

		if (StringHelper.isNotEmpty(searchValue)) {
			searchValue = "%" + searchValue.trim().toLowerCase() + "%";
		} else {
			searchValue = "%";
		}

		String queryStr = "select un.networkCodes from UserNetworkCodes un where"
				+ " un.user.id = :id and un.networkCodes.status NOT IN ('DELETED','Completed','COMPLETED','In-Active','Cancelled','Implemented') ";

		if (idList != null && idList.size() > 0) {
			queryStr = queryStr + " and un.networkCodes.id not in (:idList) ";
		}
		if (StringHelper.isNotEmpty(searchValue)) {
			queryStr = queryStr + " and (lower(un.networkCodes.releaseId) like :searchValue or "
					+ " lower(un.networkCodes.releaseName) like :searchValue)";
		}
		queryStr = queryStr + " order by un.networkCodes.createdDate desc ";
		Query query = getSession().createQuery(queryStr);
		query.setLong("id", userId);
		if (idList != null && idList.size() > 0) {
			query.setParameterList("idList", idList);
		}
		if (StringHelper.isNotEmpty(searchValue)) {
			query.setString("searchValue", searchValue);
		}
		return query.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<com.egil.pts.dao.domain.User> getNetworkCodeResources(Long supervisorId, Long networkCodeId)
			throws Throwable {

		String queryStr = "select un.user from UserNetworkCodes un where un.user.status not in ('OffBoard', 'No Show', 'Deleted') and "
				+ "lower(un.networkCodes.id) = :networkCodeId ";
		queryStr = queryStr
				+ " and ( un.user.userSupervisor.supervisor.id = :supervisorId  or un.lastAssignedManager =   :supervisorId) order by un.user.personalInfo.name ";
		Query query = getSession().createQuery(queryStr);
		query.setLong("networkCodeId", networkCodeId);
		query.setLong("supervisorId", supervisorId);
		return query.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<User> getNetworkCodeResources(Long supervisorId, Long networkCodeId, String searchValue,
			Set<Long> idList) throws Throwable {
		List<Long> userIdList = new ArrayList<Long>();
		if (supervisorId != null && supervisorId != 0l) {
			Query query = getSession().createSQLQuery("CALL user_id_heirarchy_proc(:id)");
			query.setParameter("id", supervisorId);
			query.executeUpdate();

			Query tmpQry = getSession().createSQLQuery("SELECT node FROM _result");
			userIdList = tmpQry.list();
		}
		if (StringHelper.isNotEmpty(searchValue)) {
			searchValue = "%" + searchValue.trim().toLowerCase() + "%";
		} else {
			searchValue = "%";
		}
		String queryStr = "select user1_.id id, user1_.name name from PTS_USER_NETWORK_CODES usernetwor0_ inner join PTS_USER user1_ on usernetwor0_.user_id=user1_.id where"
				+ " user1_.status not in  ('OffBoard' , 'No Show' , 'Deleted') "
				+ " and usernetwor0_.network_code_id = :networkCodeId ";
		queryStr = queryStr + " and usernetwor0_.user_id in (:userIdList) ";
		if (idList != null && idList.size() > 0) {
			queryStr = queryStr + " and usernetwor0_.user_id not in (:idList) ";
		}
		if (StringHelper.isNotEmpty(searchValue)) {
			queryStr = queryStr + " and lower(user1_.name) like :searchValue ";
		}
		queryStr = queryStr + " order by user1_.name ";
		Query query = getSession().createSQLQuery(queryStr).addScalar("id", new LongType()).addScalar("name",
				new StringType());

		query.setLong("networkCodeId", networkCodeId);

		if (userIdList != null && userIdList.size() > 0) {
			query.setParameterList("userIdList", userIdList);
		} else {
			userIdList.add(supervisorId);
			query.setParameterList("userIdList", userIdList);
		}

		if (idList != null && idList.size() > 0) {
			query.setParameterList("idList", idList);
		}
		if (StringHelper.isNotEmpty(searchValue)) {
			query.setString("searchValue", searchValue);
		}
		List<User> userList = query.setResultTransformer(Transformers.aliasToBean(User.class)).list();
		return userList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<com.egil.pts.modal.NetworkCodes> getResourceNetworkCodesDetails(Long userId, Date date, String role) {

		String sql = "SELECT CAST(N.id  as CHAR(50)) as 'networkCodeId' ,N.RELEASE_ID as 'releaseId' ,N.RELEASE_NAME 'releaseName' FROM PTS_NETWORK_CODES N JOIN PTS_USER_STABLE_TEAMS ST on ST.user_id =:userId and N.stable_teams =ST.stable_team_id  WHERE N.isAccount = (	select	case when status like '%INTERN%' then true else false	end	from PTS_USER where	id= :userId)   AND  N.`STATUS` NOT IN ('DELETED','Completed','COMPLETED','In-Active','Cancelled','Implemented' ) and ST.contribution >0 ORDER BY N.priority desc,N.CREATED_DATE DESC";

		Query query = getSession().createSQLQuery(sql).addScalar("releaseId").addScalar("releaseName")
				.addScalar("networkCodeId").setLong("userId", userId);
		List<com.egil.pts.modal.NetworkCodes> userNWList = query
				.setResultTransformer(Transformers.aliasToBean(com.egil.pts.modal.NetworkCodes.class)).list();

		if (role != null && role.contains("Intern")) {
			sql = "select 	cast(N.id as CHAR(50)) as 'networkCodeId' ,N.RELEASE_ID as 'releaseId' ,N.RELEASE_NAME 'releaseName' from	PTS_NETWORK_CODES N join PTS_USER_NETWORK_CODES ST on ST.user_id =:userId and N.ID = ST.NETWORK_CODE_ID 	and N.RELEASE_NAME like '%Intern%' and N.`STATUS` not in ('DELETED',	'Completed',	'COMPLETED',	'In-Active',	'Cancelled',	'Implemented' ) order by	N.priority desc,	N.CREATED_DATE desc";
			query = getSession().createSQLQuery(sql).addScalar("releaseId").addScalar("releaseName")
					.addScalar("networkCodeId").setLong("userId", userId);
			userNWList.addAll(
					query.setResultTransformer(Transformers.aliasToBean(com.egil.pts.modal.NetworkCodes.class)).list());
		}

		return userNWList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<com.egil.pts.modal.NetworkCodes> getResourceNetworkCodesDetailsDeleted(Long userId, Date date) {

		String sql = "SELECT CAST(N.id  as CHAR(50)) as 'networkCodeId' ,N.RELEASE_ID as 'releaseId' ,N.RELEASE_NAME 'releaseName' FROM PTS_NETWORK_CODES N JOIN PTS_USER_STABLE_TEAMS ST on ST.user_id =:userId and N.stable_teams =ST.stable_team_id  WHERE N.isAccount = (	select	case when status like '%INTERN%' then true else false	end	from PTS_USER where	id= :userId)   AND  N.`STATUS` NOT IN ('DELETED', 'In-Active','Cancelled' ) and ST.contribution >0 ORDER BY N.priority desc,N.CREATED_DATE DESC";

		Query query = getSession().createSQLQuery(sql).addScalar("releaseId").addScalar("releaseName")
				.addScalar("networkCodeId").setLong("userId", userId);
		List<com.egil.pts.modal.NetworkCodes> userNWList = query
				.setResultTransformer(Transformers.aliasToBean(com.egil.pts.modal.NetworkCodes.class)).list();

		return userNWList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<com.egil.pts.modal.NetworkCodes> getUserNwStableContribution(Pagination pagination, String release) {
		String sql = "select ncc.id,ncc.totalStoryPoints,ncc.pmStableContribution,u.id 'userId',(select ST.contribution from PTS_USER_STABLE_TEAMS ST where ST.stable_team_id =n.stable_teams and ST.user_id =u.id and contribution  > 0) 'stableContribution',contribution 'nccStableContribution',u.name,network_id 'nwId', concat(n.release_id, '-', n.release_name) 'networkCode' from PTS_USER_NETWORK_CODES_CONTRIBUTION ncc,PTS_USER u, PTS_NETWORK_CODES n where n.id=ncc.network_id and ncc.user_id =u.id and network_id=:release and  n.STATUS  not in ('DELETED',	'Completed',	'COMPLETED',	'In-Active',	'Cancelled',	'Implemented' )  ";

		Query query = getSession().createSQLQuery(sql).addScalar("id", new LongType())
				.addScalar("name", new StringType()).addScalar("networkCode", new StringType())
				.addScalar("totalStoryPoints", new LongType()).addScalar("stableContribution", new DoubleType())
				.addScalar("pmStableContribution", new DoubleType())
				.addScalar("nccStableContribution", new DoubleType()).addScalar("nwId", new LongType())
				.addScalar("userId", new LongType()).setLong("release", Long.parseLong(release));
		List<com.egil.pts.modal.NetworkCodes> userNWList = query
				.setResultTransformer(Transformers.aliasToBean(com.egil.pts.modal.NetworkCodes.class)).list();
		if (pagination != null) {
			if (pagination.getOffset() > 0)
				query.setFirstResult(pagination.getOffset());
			if (pagination.getSize() > 0)
				query.setMaxResults(pagination.getSize());
		}
		return userNWList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean addUpdateUserNwStableContribution(List<com.egil.pts.modal.NetworkCodes> codes,
			SearchSortContainer searchSortContainer, Long totalStoryPoints) {
		if (searchSortContainer.isAssignDefaultNwStableToUser()) {
			String sql = "update PTS_USER_NETWORK_CODES_CONTRIBUTION set  totalStoryPoints=:totalStoryPoints,pmStableContribution=:pmStableContribution,network_id =:nw,contribution=:contribution where id=:id";
			for (com.egil.pts.modal.NetworkCodes code : codes)
				getSession().createSQLQuery(sql).setLong("nw", code.getNwId())
						.setLong("totalStoryPoints", totalStoryPoints)
						.setDouble("contribution", code.getNccStableContribution().doubleValue())
						.setDouble("pmStableContribution", code.getPmStableContribution()).setLong("id", code.getId())
						.executeUpdate();
		} else {
			String sql = "update PTS_USER_NETWORK_CODES_CONTRIBUTION set  totalStoryPoints=:totalStoryPoints,pmStableContribution=:pmStableContribution,network_id =:nw,contribution=:contribution where id=:id";
			for (com.egil.pts.modal.NetworkCodes code : codes)
				getSession().createSQLQuery(sql).setLong("totalStoryPoints", totalStoryPoints)
						.setLong("nw", code.getNwId()).setDouble("pmStableContribution", code.getPmStableContribution())
						.setDouble("contribution", code.getNccStableContribution()).setLong("id", code.getId())
						.executeUpdate();
		}

		return true;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<UserNetworkCodes> getNetworkCodeResourcesDetails(Long supervisorId, Long networkCodeId) {
		Query query = getSession()
				.createQuery("from UserNetworkCodes un where lower(un.networkCodes.id) = :networkCodeId "
						+ " and un.user.userSupervisor.supervisor.id = :supervisorId ");
		query.setLong("networkCodeId", networkCodeId);
		query.setLong("supervisorId", supervisorId);
		return query.list();
	}

	@Override
	public Integer deleteUserNetworkCodes(Long userId, Long networkCodeId, Long supervisorId, List<Long> userIdList,
			List<Long> networkCodeIdList) throws Throwable {

		String queryString = null;
		Query query = null;

		queryString = "delete from UserNetworkCodes un ";
		if (networkCodeId != null) {
			if (userIdList != null && userIdList.size() > 0) {
				queryString = queryString + " where un.networkCodes.id = :networkCodeId ";
				queryString = queryString
						+ " and un.user.id in (select us.user.id from UserSupervisor us where us.supervisor.id = :supervisorId and us.user.id in (:userIdList)) ";
			} else if ((userIdList == null || (userIdList != null && userIdList.size() == 0))) {
				queryString = queryString
						+ " where un.user.id in (select us.user.id from UserSupervisor us where us.supervisor.id = :supervisorId) ";
			}
		} else if (userId != null) {
			if (networkCodeIdList != null && networkCodeIdList.size() > 0) {
				queryString = queryString
						+ " where un.user.id = :userId and un.networkCodes.id  in (:networkCodeIdList)";
			} else if ((networkCodeIdList == null || (networkCodeIdList != null && networkCodeIdList.size() == 0))) {
				queryString = queryString + " where un.user.id = :userId ";
			}
		}
		query = getSession().createQuery(queryString);
		if (networkCodeId != null) {
			if (userIdList != null && userIdList.size() > 0) {
				query.setLong("networkCodeId", networkCodeId);
				query.setParameterList("userIdList", userIdList);
				query.setLong("supervisorId", supervisorId);
				return query.executeUpdate();
			} else if ((userIdList == null || (userIdList != null && userIdList.size() == 0))) {
				query.setLong("supervisorId", supervisorId);
				return query.executeUpdate();
			}
		} else if (userId != null) {
			if (networkCodeIdList != null && networkCodeIdList.size() > 0) {
				query.setLong("userId", userId);
				query.setParameterList("networkCodeIdList", networkCodeIdList);
				return query.executeUpdate();
			} else if ((networkCodeIdList == null || (networkCodeIdList != null && networkCodeIdList.size() == 0))) {
				query.setLong("userId", userId);
				return query.executeUpdate();
			}
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Long> getNetworkCodeIds(Long userId) {
		Query query = getSession()
				.createQuery("select un.networkCodes.id from UserNetworkCodes un where un.user.id =:userId");
		query.setLong("userId", userId);
		return query.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Long> getUserIds(Long networkCodeId, Long supervisorId) {
		Query query = getSession().createQuery(
				"select un.user.id from UserNetworkCodes un where lower(un.networkCodes.id) = :networkCodeId "
						+ " and un.user.userSupervisor.supervisor.id = :supervisorId ");
		query.setLong("networkCodeId", networkCodeId);
		query.setLong("supervisorId", supervisorId);
		return query.list();
	}

	@Override
	public String getRemainingHrsForNetworkCode(Long networkCodeId, String userType, String stream) {
		double remainingHrs = 0.0d;
		int totalEffort = 0;
		String quertStr = "";

		if (StringHelper.isNotEmpty(userType) && userType.contains("Global")) {
			if (StringHelper.isNotEmpty(stream) && stream.equalsIgnoreCase("Development")) {
				quertStr = "select (nc.global_dev_loe - (sum(t.mon_hrs)+ sum(t.tue_hrs) + sum(t.wed_hrs) + sum(t.thu_hrs)+ sum(t.fri_hrs)+ sum(t.sat_hrs)+ sum(t.sun_hrs))), nc.effort effort  from "
						+ "PTS_NETWORK_CODES nc, PTS_USER_TIMESHEET t where t.network_code_id=nc.id and nc.id= :networkCodeId group by nc.id";
			} else if (StringHelper.isNotEmpty(stream) && stream.equalsIgnoreCase("Testing")) {
				quertStr = "select (nc.global_test_loe - (sum(t.mon_hrs)+ sum(t.tue_hrs) + sum(t.wed_hrs) + sum(t.thu_hrs)+ sum(t.fri_hrs)+ sum(t.sat_hrs)+ sum(t.sun_hrs))), nc.effort effort  from "
						+ "PTS_NETWORK_CODES nc, PTS_USER_TIMESHEET t where t.network_code_id=nc.id and nc.id= :networkCodeId group by nc.id";
			} else if (StringHelper.isNotEmpty(stream) && stream.equalsIgnoreCase("Line Manager")) {
				quertStr = "select (nc.global_proj_mgmt_loe - (sum(t.mon_hrs)+ sum(t.tue_hrs) + sum(t.wed_hrs) + sum(t.thu_hrs)+ sum(t.fri_hrs)+ sum(t.sat_hrs)+ sum(t.sun_hrs))), nc.effort effort  from "
						+ "PTS_NETWORK_CODES nc, PTS_USER_TIMESHEET t where t.network_code_id=nc.id and nc.id= :networkCodeId group by nc.id";
			} else if (StringHelper.isNotEmpty(stream) && stream.equalsIgnoreCase("Project Manager Operations")) {
				quertStr = "select (nc.global_opr_hand_off_loe - (sum(t.mon_hrs)+ sum(t.tue_hrs) + sum(t.wed_hrs) + sum(t.thu_hrs)+ sum(t.fri_hrs)+ sum(t.sat_hrs)+ sum(t.sun_hrs))), nc.effort effort  from "
						+ "PTS_NETWORK_CODES nc, PTS_USER_TIMESHEET t where t.network_code_id=nc.id and nc.id= :networkCodeId group by nc.id";
			} else {
				quertStr = "select (nc.global_others_loe - (sum(t.mon_hrs)+ sum(t.tue_hrs) + sum(t.wed_hrs) + sum(t.thu_hrs)+ sum(t.fri_hrs)+ sum(t.sat_hrs)+ sum(t.sun_hrs))), nc.effort effort  from "
						+ "PTS_NETWORK_CODES nc, PTS_USER_TIMESHEET t where t.network_code_id=nc.id and nc.id= :networkCodeId group by nc.id";
			}

		} else if (StringHelper.isNotEmpty(userType) && userType.contains("Local")) {
			if (StringHelper.isNotEmpty(stream) && stream.equalsIgnoreCase("Development")) {
				quertStr = "select (nc.local_dev_loe - (sum(t.mon_hrs)+ sum(t.tue_hrs) + sum(t.wed_hrs) + sum(t.thu_hrs)+ sum(t.fri_hrs)+ sum(t.sat_hrs)+ sum(t.sun_hrs))), nc.effort effort  from "
						+ "PTS_NETWORK_CODES nc, PTS_USER_TIMESHEET t where t.network_code_id=nc.id and nc.id= :networkCodeId group by nc.id";
			} else if (StringHelper.isNotEmpty(stream) && stream.equalsIgnoreCase("Testing")) {
				quertStr = "select (nc.local_test_loe - (sum(t.mon_hrs)+ sum(t.tue_hrs) + sum(t.wed_hrs) + sum(t.thu_hrs)+ sum(t.fri_hrs)+ sum(t.sat_hrs)+ sum(t.sun_hrs))), nc.effort effort  from "
						+ "PTS_NETWORK_CODES nc, PTS_USER_TIMESHEET t where t.network_code_id=nc.id and nc.id= :networkCodeId group by nc.id";
			} else if (StringHelper.isNotEmpty(stream) && stream.equalsIgnoreCase("Line Manager")) {
				quertStr = "select (nc.local_proj_mgmt_loe - (sum(t.mon_hrs)+ sum(t.tue_hrs) + sum(t.wed_hrs) + sum(t.thu_hrs)+ sum(t.fri_hrs)+ sum(t.sat_hrs)+ sum(t.sun_hrs))), nc.effort effort  from "
						+ "PTS_NETWORK_CODES nc, PTS_USER_TIMESHEET t where t.network_code_id=nc.id and nc.id= :networkCodeId group by nc.id";
			} else if (StringHelper.isNotEmpty(stream) && stream.equalsIgnoreCase("Project Manager Operations")) {
				quertStr = "select (nc.local_opr_hand_off_loe - (sum(t.mon_hrs)+ sum(t.tue_hrs) + sum(t.wed_hrs) + sum(t.thu_hrs)+ sum(t.fri_hrs)+ sum(t.sat_hrs)+ sum(t.sun_hrs))), nc.effort effort  from "
						+ "PTS_NETWORK_CODES nc, PTS_USER_TIMESHEET t where t.network_code_id=nc.id and nc.id= :networkCodeId group by nc.id";
			} else {
				quertStr = "select (nc.local_others_loe - (sum(t.mon_hrs)+ sum(t.tue_hrs) + sum(t.wed_hrs) + sum(t.thu_hrs)+ sum(t.fri_hrs)+ sum(t.sat_hrs)+ sum(t.sun_hrs))), nc.effort effort  from "
						+ "PTS_NETWORK_CODES nc, PTS_USER_TIMESHEET t where t.network_code_id=nc.id and nc.id= :networkCodeId group by nc.id";
			}
		}
		Query query = getSession().createSQLQuery(quertStr);
		query.setLong("networkCodeId", networkCodeId);
		ScrollableResults rs = query.scroll();
		if (rs != null) {
			while (rs.next()) {
				Object[] obj = rs.get();
				remainingHrs = (((Double) obj[0]));
				totalEffort = (((Integer) obj[1]));
			}
		}
		return remainingHrs + " (" + totalEffort + ")";
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<com.egil.pts.modal.NetworkCodes> getUserNetworkCodes(Pagination pagination,
			SearchSortContainer searchSortContainer, boolean isAdmin, String byStatus) {
		StringBuilder queryString = new StringBuilder();
		String status = "'DELETED','Completed','COMPLETED','In-Active','Cancelled','Implemented'";
		if (byStatus != null && !byStatus.isEmpty())
			status = byStatus;
		List<Long> userIdList = new ArrayList<Long>();
		queryString.append("select distinct nc.id id, nc.release_id releaseId, nc.release_name releaseName "
				+ " from PTS_USER_NETWORK_CODES unc, PTS_NETWORK_CODES nc, PTS_PROJECT p  "
				+ " where unc.network_code_id=nc.id and nc.project_id=p.id and nc.status NOT IN (" + status + ") ");
		if (!isAdmin) {
			Query queryProc = getSession().createSQLQuery("CALL user_id_heirarchy_proc(:id)").setParameter("id",
					Long.parseLong(searchSortContainer.getSearchSupervisor()));

			queryProc.executeUpdate();
			Query tmpQry = getSession().createSQLQuery("SELECT node FROM _result");
			userIdList = tmpQry.list();
			if (userIdList != null && userIdList.size() > 0) {
				queryString.append(" and unc.user_id in (:userIdList) ");
			}
		}
		if (searchSortContainer.getSearchField() != null && searchSortContainer.getSearchString() != null
				&& !searchSortContainer.getSearchString().equals("")) {
			if (searchSortContainer.getSearchField().equalsIgnoreCase("projectid")) {
				queryString.append(" and p.id=" + searchSortContainer.getSearchString() + " ");
			}
		}
		queryString.append(" order by nc.created_date desc ");
		Query query = getSession().createSQLQuery(queryString.toString()).addScalar("id", new LongType())
				.addScalar("releaseId", new StringType()).addScalar("releaseName", new StringType());
		if (userIdList != null && userIdList.size() > 0) {
			query.setParameterList("userIdList", userIdList);
		}
		if (pagination != null) {
			if (pagination.getOffset() > 0)
				query.setFirstResult(pagination.getOffset());
			if (pagination.getSize() > 0)
				query.setMaxResults(pagination.getSize());
		}
		List<com.egil.pts.modal.NetworkCodes> userProjectList = query
				.setResultTransformer(Transformers.aliasToBean(com.egil.pts.modal.NetworkCodes.class)).list();

		return userProjectList;
	}

	@Override
	public List<Object[]> getUserNetworkUnliked(boolean flag, Long networkCodeId, Long supervisorId) {
		String sql = "";
		if (flag) {
			sql = "select u.id,u.name from PTS_USER u,PTS_USER_ROLE r where u.id not in (select user_id from PTS_USER_SUPERVISOR where SUPERVISOR_ID=:sup ) "
					+ " and u.id not in (select USER_ID  from PTS_USER_NETWORK_CODES where last_managet_assigned=:sup )  "
					+ "and u.status not in  ('OffBoard' , 'No Show' , 'Deleted') and u.status not like '%Intern%'  and u.id=r.USER_ID and r.ROLE_ID=3 ";
			return getSession().createSQLQuery(sql).setLong("sup", supervisorId).list();
		}
		if (networkCodeId == null) {
			sql = sql.replaceAll("&nw", " ");
			return getSession().createSQLQuery(sql).setLong("sup", supervisorId).list();
		} else {
			sql = sql.replaceAll("&nw", "and t.network_id =:nw");
			return getSession().createSQLQuery(sql).setLong("sup", supervisorId).setLong("nw", networkCodeId).list();
		}

	}

	@Override
	public boolean addNWDataforinterns(Long id) {
		/*
		 * int success = getSession()
		 * .createSQLQuery("insert into PTS_USER_NETWORK_CODES(USER_ID,NETWORK_CODE_ID)  SELECT "
		 * + id + " ,id FROM PTS_NETWORK_CODES n WHERE  n.PROJECT_LEVEL='ACCOUNT' and  "
		 * +
		 * " isAccount=false and STATUS not in ('Completed','DELETED','Cancelled','Implemented','HOLD')"
		 * ) .executeUpdate(); return success > 0;
		 */
		return true;
	}

	@Override
	public boolean checkAndDeleteUserNW(Long id) {
		int success = -1;
		success = getSession().createSQLQuery("delete from PTS_USER_NETWORK_CODES  where USER_ID =" + id
				+ " and NETWORK_CODE_ID  "
				+ " in (SELECT id FROM PTS_NETWORK_CODES n WHERE  n.PROJECT_LEVEL='ACCOUNT' and isAccount=false and STATUS not in ('Completed','DELETED','Cancelled','Implemented','HOLD'))")
				.executeUpdate();

		getSession().flush();

		Query query = getSession().createSQLQuery("CALL TEST_ACCOUNT(:id)");
		query.setParameter("id", id);
		return query.executeUpdate() > 0;

	}

	@Override
	public int saveUserStableTeams(List<StableTeams> data, Long userId) {
		int success = 0;

		getSession().createSQLQuery("DELETE FROM PTS_USER_STABLE_TEAMS where user_id=:user").setLong("user", userId)
				.executeUpdate();
		getSession().flush();

		for (StableTeams s : data) {
			Query query = getSession().createSQLQuery(
					" INSERT INTO PTS_USER_STABLE_TEAMS (user_id,stable_team_id,contribution) VALUES(:user,:stabletesam,:contribution) ");
			query.setLong("user", userId);
			query.setDouble("contribution", (s.getValue() == null) ? 0.0 : s.getValue());
			query.setLong("stabletesam", s.getId());
			query.executeUpdate();
			getSession().flush();
			success += 1;
		}

		return success;
	}

	@Override
	public List getTimeSheetTypeMap() {
		return getSession().createSQLQuery("select NAME,DESCRIPTION from PTS_TYPES where STATUS =1")
				.addScalar("DESCRIPTION", new StringType()).addScalar("NAME", new StringType()).list();
	}

	@Override
	public List getUserCapacityByRelease(Long user) {
		return getSession().createSQLQuery(
				"select network_id,pmStableContribution from PTS_USER_NETWORK_CODES_CONTRIBUTION where user_id=:user")
				.addScalar("network_id", new LongType()).addScalar("pmStableContribution", new LongType())
				.setLong("user", user).list();

	}
}
