package com.egil.pts.dao.inventory.impl;

import java.math.BigInteger;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.ScrollableResults;
import org.hibernate.internal.util.StringHelper;
import org.hibernate.transform.Transformers;
import org.hibernate.type.DateType;
import org.hibernate.type.DoubleType;
import org.hibernate.type.FloatType;
import org.hibernate.type.IntegerType;
import org.hibernate.type.LongType;
import org.hibernate.type.StringType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.egil.pts.dao.common.hibernate.HibernateGenericDao;
import com.egil.pts.dao.domain.User;
import com.egil.pts.dao.domain.UserAccounts;
import com.egil.pts.dao.domain.UserPlatforms;
import com.egil.pts.dao.domain.UserStableTeams;
import com.egil.pts.dao.domain.UserSupervisor;
import com.egil.pts.dao.inventory.UserDao;
import com.egil.pts.modal.EssDetails;
import com.egil.pts.modal.Pagination;
import com.egil.pts.modal.PtsHolidays;
import com.egil.pts.modal.SearchSortContainer;
import com.egil.pts.modal.TechCompetencyScore;
import com.egil.pts.modal.UserCapacity;
import com.egil.pts.modal.UserReport;

@Repository("userDao")
public class HibernateUserDao extends HibernateGenericDao<User, Long> implements UserDao {
	protected static Logger logger = LogManager.getLogger("PTSPUI");

	@Value("${dashboard.indetail.users.dropdown.based}")
	private String dashBoardAllList;

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<User> getUsers(Pagination pagination, SearchSortContainer searchSortObj) throws Throwable {
		StringBuilder queryString = new StringBuilder();
		queryString.append("from User u where status is not null ");

		if (searchSortObj != null) {

			if (searchSortObj.isCapacityScrnFlag()) {
				queryString.append(" and u.status not in ('OffBoard', 'No Show', 'Deleted') ");
			}

			if (searchSortObj.getSearchUserName() != null && !searchSortObj.getSearchUserName().equalsIgnoreCase("")) {
				queryString.append(" and u.personalInfo.name like '%" + searchSortObj.getSearchUserName() + "%'");
			}

			if (searchSortObj.getSearchSupervisor() != null
					&& !searchSortObj.getSearchSupervisor().equalsIgnoreCase("")) {
				queryString
						.append(" and (u.userSupervisor.supervisor.id = " + searchSortObj.getSearchSupervisor() + ") ");
			}
			if (searchSortObj.getSearchStatus() != null && !searchSortObj.getSearchStatus().equalsIgnoreCase("")) {
				queryString.append(" and u.status like '%" + searchSortObj.getSearchStatus() + "%'");
			}

			if (searchSortObj.getSearchDOJ() != null && !searchSortObj.getSearchDOJ().equalsIgnoreCase("")) {
				queryString.append(" and u.doj like '%" + searchSortObj.getSearchDOJ() + "%'");
			}

			if (searchSortObj.getSearchLocation() != null && !searchSortObj.getSearchLocation().equalsIgnoreCase("")) {
				queryString.append(" and u.location.id = " + searchSortObj.getSearchLocation() + "");
			}

			if (searchSortObj.getSearchStream() != null && !searchSortObj.getSearchStream().equalsIgnoreCase("")) {
				queryString.append(" and u.stream.streamName like '%" + searchSortObj.getSearchStream() + "%'");
			}

			if (searchSortObj.getSearchUserType() != null && !searchSortObj.getSearchUserType().equalsIgnoreCase("")) {
				queryString
						.append(" and u.userRole.userType.userType like '%" + searchSortObj.getSearchUserType() + "%'");
			}

			queryString.append(" and u.credentials.userName not like '%Admin%'");

			if (searchSortObj.getSearchTechnology() != null
					&& !searchSortObj.getSearchTechnology().equalsIgnoreCase("")) {
				queryString.append(" and u.userSkills.technology.technologyName like '%"
						+ searchSortObj.getSearchTechnology() + "%' and u.userSkills.technology.primaryFlag = 'Y' ");
			}

			if (searchSortObj.getSearchDOBilling() != null
					&& !searchSortObj.getSearchDOBilling().equalsIgnoreCase("")) {
				queryString.append(" and u.dateOfBilling  >= STR_TO_DATE('" + searchSortObj.getSearchDOBilling()
						+ "', '%Y-%m-%d')");
			}
			if (searchSortObj.isSupervisorMapFlag()) {
				queryString.append(" and u.userRole.role.roleName <> 'USER'");
			} else {
				queryString.append(" and u.userRole.role.roleName <> 'ADMIN'");
			}

			if (searchSortObj.getSidx() != null && !searchSortObj.getSidx().equals("")
					&& searchSortObj.getSord() != null && !searchSortObj.getSord().equals("")) {
				if (searchSortObj.getSidx().equalsIgnoreCase("name")) {
					queryString.append(" order by u.personalInfo.name " + searchSortObj.getSord());
				} else if (searchSortObj.getSidx().equalsIgnoreCase("firstName")) {
					queryString.append(" order by u.personalInfo.firstName " + searchSortObj.getSord());
				} else if (searchSortObj.getSidx().equalsIgnoreCase("lastName")) {
					queryString.append(" order by u.personalInfo.lastName " + searchSortObj.getSord());
				} else if (searchSortObj.getSidx().equalsIgnoreCase("email")) {
					queryString.append(" order by u.personalInfo.email " + searchSortObj.getSord());
				} else if (searchSortObj.getSidx().equalsIgnoreCase("supervisor")) {
					queryString.append(
							" order by u.userSupervisor.supervisor.personalInfo.name " + searchSortObj.getSord());
				} else if (searchSortObj.getSidx().equalsIgnoreCase("createdBy")) {
					queryString.append(" order by u.createdBy " + searchSortObj.getSord());
				} else if (searchSortObj.getSidx().equalsIgnoreCase("createdDate")) {
					queryString.append(" order by u.createdDate " + searchSortObj.getSord());
				} else if (searchSortObj.getSidx().equalsIgnoreCase("updatedBy")) {
					queryString.append(" order by u.updatedBy " + searchSortObj.getSord());
				} else if (searchSortObj.getSidx().equalsIgnoreCase("updatedDate")) {
					queryString.append(" order by u.updatedDate " + searchSortObj.getSord());
				} else if (searchSortObj.getSidx().equalsIgnoreCase("status")) {
					queryString.append(" order by u.status " + searchSortObj.getSord());
				} else if (searchSortObj.getSidx().equalsIgnoreCase("location")) {
					queryString.append(" order by u.location.name " + searchSortObj.getSord());
				} else if (searchSortObj.getSidx().equalsIgnoreCase("backFillOff")) {
					queryString.append(" order by u.backFillOf " + searchSortObj.getSord());
				} else if (searchSortObj.getSidx().equalsIgnoreCase("streamName")) {
					queryString.append(" order by u.stream.streamName " + searchSortObj.getSord());
				} else if (searchSortObj.getSidx().equalsIgnoreCase("role")) {
					queryString.append(" order by u.userRole.role.roleName " + searchSortObj.getSord());
				} else if (searchSortObj.getSidx().equalsIgnoreCase("userType")) {
					queryString.append(" order by u.userRole.userType.userType " + searchSortObj.getSord());
				} else if (searchSortObj.getSidx().equalsIgnoreCase("userName")) {
					queryString.append(" order by u.credentials.userName " + searchSortObj.getSord());
				}
			}
		}
		Query query = getSession().createQuery(queryString.toString());
		if (pagination != null) {
			if (pagination.getOffset() > 0)
				query.setFirstResult(pagination.getOffset());
			if (pagination.getSize() > 0)
				query.setMaxResults(pagination.getSize());
		}
		return query.list();
	}

	@Override
	public List<EssDetails> getEssFeed(String month, Pagination pagination) throws Throwable {
		String essFeed = "select id,signum,year,month,TARGET_HRS as 'targetHrs',CHARGED_HRS'chargedHrs' from PTS.ESS_DETAILS where month=:month";

		Query query = getSession().createQuery(essFeed);
		query.setString("month", month);
		if (pagination != null) {
			if (pagination.getOffset() > 0)
				query.setFirstResult(pagination.getOffset());
			if (pagination.getSize() > 0)
				query.setMaxResults(pagination.getSize());
		}
		return query.setResultTransformer(Transformers.aliasToBean(com.egil.pts.modal.EssDetails.class)).list();
	}

	@Override
	public int getUsersCount(SearchSortContainer searchSortObj) throws Throwable {

		int count = 0;
		StringBuilder queryString = new StringBuilder();
		queryString.append("select count (*) from User u where status is not null ");

		if (searchSortObj != null) {

			if (searchSortObj.isCapacityScrnFlag()) {
				queryString.append(" and u.status not in ('OffBoard', 'No Show', 'Deleted') ");
			}

			/*
			 * if (searchSortObj.getSearchField() != null &&
			 * !searchSortObj.getSearchField().equals("") && searchSortObj.getSearchString()
			 * != null && !searchSortObj.getSearchString().equals("")) { if
			 * (searchSortObj.getSearchField().equalsIgnoreCase("name")) {
			 * queryString.append(" and u.personalInfo.name like '%" +
			 * searchSortObj.getSearchString() + "%'"); } else if
			 * (searchSortObj.getSearchField().equalsIgnoreCase( "firstName")) {
			 * queryString.append(" and u.personalInfo.firstName like '%" +
			 * searchSortObj.getSearchString() + "%'"); } else if
			 * (searchSortObj.getSearchField().equalsIgnoreCase( "lastName")) {
			 * queryString.append(" and u.personalInfo.lastName like '%" +
			 * searchSortObj.getSearchString() + "%'"); } else if
			 * (searchSortObj.getSearchField().equalsIgnoreCase( "email")) {
			 * queryString.append(" and u.personalInfo.email like '%" +
			 * searchSortObj.getSearchString() + "%'"); } else if
			 * (searchSortObj.getSearchField().equalsIgnoreCase( "userName")) {
			 * queryString.append(" and u.credentials.userName like '%" +
			 * searchSortObj.getSearchString() + "%'"); } else if
			 * (searchSortObj.getSearchField().equalsIgnoreCase( "supervisor")) {
			 * queryString
			 * .append(" and (u.userSupervisor.supervisor.personalInfo.name like '%" +
			 * searchSortObj.getSearchString() + "%') "); } else if
			 * (searchSortObj.getSearchField().equalsIgnoreCase( "skillName")) { queryString
			 * .append(
			 * " and u.id in (select us.user.id from UserSkills us where us.technology.technologyName like '%"
			 * + searchSortObj.getSearchString() + "%') "); } else if
			 * (searchSortObj.getSearchField().equalsIgnoreCase( "projectName")) {
			 * queryString .append(
			 * " and u.id in (select unc.user.id from UserNetworkCodes unc where unc.networkCodes.status = 1 and unc.networkCodes.project.projectName like '%"
			 * + searchSortObj.getSearchString() + "%') "); } else if
			 * (searchSortObj.getSearchField().equalsIgnoreCase( "status")) {
			 * queryString.append(" and u.status like '%" + searchSortObj.getSearchString()
			 * + "%'"); } else if (searchSortObj.getSearchField().equalsIgnoreCase(
			 * "location")) { queryString.append(" and u.location like '%" +
			 * searchSortObj.getSearchString() + "%'"); } else if
			 * (searchSortObj.getSearchField().equalsIgnoreCase( "doj")) {
			 * queryString.append(" and u.doj like '%" + searchSortObj.getSearchString() +
			 * "%'"); } }
			 */

			if (searchSortObj.getSearchUserName() != null && !searchSortObj.getSearchUserName().equalsIgnoreCase("")) {
				queryString.append(" and u.personalInfo.name like '%" + searchSortObj.getSearchUserName() + "%'");
			}
			queryString.append(" and u.credentials.userName not like '%Admin%'");
			if (searchSortObj.getSearchSupervisor() != null
					&& !searchSortObj.getSearchSupervisor().equalsIgnoreCase("")) {
				queryString.append(
						" and (u.userSupervisor.supervisor.id = " + searchSortObj.getSearchSupervisor() + " ) ");
			}
			if (searchSortObj.getSearchStatus() != null && !searchSortObj.getSearchStatus().equalsIgnoreCase("")) {
				queryString.append(" and u.status like '%" + searchSortObj.getSearchStatus() + "%'");
			}

			if (searchSortObj.getSearchDOJ() != null && !searchSortObj.getSearchDOJ().equalsIgnoreCase("")) {
				queryString.append(" and u.doj like '%" + searchSortObj.getSearchDOJ() + "%'");
			}

			if (searchSortObj.getSearchLocation() != null && !searchSortObj.getSearchLocation().equalsIgnoreCase("")) {
				queryString.append(" and u.location.id = " + searchSortObj.getSearchLocation() + "");
			}

			if (searchSortObj.getSearchDOBilling() != null
					&& !searchSortObj.getSearchDOBilling().equalsIgnoreCase("")) {
				queryString.append(" and u.dateOfBilling  >= STR_TO_DATE('" + searchSortObj.getSearchDOBilling()
						+ "', '%Y-%m-%d')");
			}

			if (searchSortObj.getSearchStream() != null && !searchSortObj.getSearchStream().equalsIgnoreCase("")) {
				queryString.append(" and u.stream.streamName like '%" + searchSortObj.getSearchStream() + "%'");
			}

			if (searchSortObj.getSearchUserType() != null && !searchSortObj.getSearchUserType().equalsIgnoreCase("")) {
				queryString
						.append(" and u.userRole.userType.userType like '%" + searchSortObj.getSearchUserType() + "%'");
			}

			if (searchSortObj.getSearchTechnology() != null
					&& !searchSortObj.getSearchTechnology().equalsIgnoreCase("")) {
				queryString.append(" and u.userSkills.technology.technologyName like '%"
						+ searchSortObj.getSearchTechnology() + "%' and u.userSkills.technology.primaryFlag = 'Y' ");
			}

			if (searchSortObj.isSupervisorMapFlag()) {
				queryString.append(" and u.userRole.role.roleName <> 'USER'");
			} else {
				queryString.append(" and u.userRole.role.roleName <> 'ADMIN'");
			}
		}
		Query query = getSession().createQuery(queryString.toString());
		Long obj = (Long) query.uniqueResult();
		if (obj != null) {
			count = obj.intValue();
		}
		return count;

	}

	@Override
	public Integer deleteUsers(List<Long> userIdList) throws Throwable {
		Query query = getSession().createQuery("update User u set u.status ='Deleted' where u.id in (:id)");
		query.setParameterList("id", userIdList);
		return query.executeUpdate();
	}

	@Override
	@Transactional
	public User getUser(Long userId) throws Throwable {
		Query query = getSession().createQuery("from User u where " + "u.id = :id");
		query.setLong("id", userId);
		return (User) query.uniqueResult();
	}

	@Override
	public User getUser(String userName, String password) throws Throwable {
		Query query = getSession().createQuery("from User u where u.credentials.userName = :userName and"
				+ " u.credentials.password = :password and u.status is not null");
		query.setString("userName", userName);
		query.setString("password", password);
		return (User) query.uniqueResult();
	}

	@Override
	public User getUser(String userName, boolean ldartsManaged) throws Throwable {
		Query query = getSession()
				.createQuery("from User u where u.credentials.userName = :userName and  u.status is not null");
		query.setString("userName", userName);
		return (User) query.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<com.egil.pts.modal.User> getSubordinates(Long id, Set<Long> idList, String searchValue)
			throws Throwable {
		if (StringHelper.isNotEmpty(searchValue)) {
			searchValue = "%" + searchValue.trim().toLowerCase() + "%";
		} else {
			searchValue = "%";
		}
		/*
		 * String queryStr = "from UserSupervisor u where u.supervisor.id = :id"; if
		 * (StringHelper.isNotEmpty(searchValue)) { queryStr = queryStr +
		 * " and (u.user.personalInfo.name like :searchValue) "; } if (idList != null &&
		 * idList.size() > 0) { queryStr = queryStr +
		 * " and u.user.id not in (:idList) "; }
		 */
		String queryStr = " select * from ( select u.ID as id,	u.name as name,	s.SUPERVISOR_ID 'supervisorId' ,(select	name from PTS_USER	where id = s.SUPERVISOR_ID) 'supervisor' from PTS_USER u, PTS_USER_SUPERVISOR s where s.USER_ID = u.id and u.status not in ('OffBoard',	'No Show','Open','Selected','Deleted') and name not like '%Admin%' "
				+ " &sp ";
		if (id != null) {
			queryStr = queryStr.replaceAll("&sp",
					" and s.supervisor_id =:supervisorId union all select distinct us.ID as id,	us.name as name,	us.id 'supervisorId' ,us.name 'supervisor' from PTS_USER us where id=:supervisorId ");
		} else {
			queryStr = queryStr.replaceAll("&sp", " ");
		}

		queryStr = queryStr + "  ) T &wr ";
		if (StringHelper.isNotEmpty(searchValue)) {
			queryStr = queryStr + "  (name like :searchValue) ";
		}
		if (idList != null && idList.size() > 0) {
			queryStr = queryStr + " and  id not in (:idList) ";
		}
		if (StringHelper.isNotEmpty(searchValue) || (idList != null && idList.size() > 0)) {
			queryStr = queryStr.replaceAll("&wr", " where ");
		} else {
			queryStr = queryStr.replaceAll("", " ");
		}

		queryStr = queryStr + " order by T.name asc ";
		Query query = getSession().createSQLQuery(queryStr).addScalar("id", new LongType())
				.addScalar("name", new StringType()).addScalar("supervisorId", new LongType())
				.addScalar("supervisor", new StringType());

		if (id != null)
			query.setLong("supervisorId", id);
		if (idList != null && idList.size() > 0) {
			query.setParameterList("idList", idList);
		}
		if (StringHelper.isNotEmpty(searchValue)) {
			query.setString("searchValue", searchValue);
		}

		return query.setResultTransformer(Transformers.aliasToBean(com.egil.pts.modal.User.class)).list();
	}

	@Override
	public Integer resetPassword(String userName, String newPassword, String oldPassword) throws Throwable {
		String queryStr = "update User u set u.credentials.password = :password where u.credentials.userName = :userName ";
		if (oldPassword != null && !oldPassword.isEmpty()) {
			queryStr = queryStr + " and u.credentials.password = :oldPassword ";
		}
		Query query = getSession().createQuery(queryStr);
		query.setString("userName", userName);
		query.setString("password", newPassword);
		if (oldPassword != null && !oldPassword.isEmpty()) {
			query.setString("oldPassword", oldPassword);
		}

		return query.executeUpdate();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<User> getAllManagers(Set<Long> idList, String searchValue) throws Throwable {
		if (StringHelper.isNotEmpty(searchValue)) {
			searchValue = "%" + searchValue.trim().toLowerCase() + "%";
		} else {
			searchValue = "%";
		}
		String queryStr = "from User u where u.userRole.role.roleName = 'LINE MANAGER' and u.status not in ('No Show', 'DELETED', 'Others', 'OffBoard')";
		if (StringHelper.isNotEmpty(searchValue)) {
			queryStr = queryStr + " and (u.personalInfo.name like :searchValue) ";
		}
		if (idList != null && idList.size() > 0) {
			queryStr = queryStr + " and u.id not in (:idList) ";
		}
		Query query = getSession().createQuery(queryStr);
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
	public List<User> getUsersToBackFill() throws Throwable {
		StringBuilder queryString = new StringBuilder();
		queryString.append(
				"from User u where u.resignDate is not null or u.releseDate is not null or u.status = 'Notice Period' or u.status='LTA'");
		Query query = getSession().createQuery(queryString.toString());
		return query.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<UserSupervisor> getSubordinates(Long id) throws Throwable {
		String sql = (id != null && id != -1)
				? "from UserSupervisor u where u.user.status not in ('OffBoard', 'No Show', 'Open','Selected', 'Deleted') and "
						+ "u.supervisor.id = :id"
				: "from UserSupervisor u where u.user.status not in ('OffBoard', 'No Show', 'Open','Selected', 'Deleted') ";
		Query query = getSession().createQuery(sql);
		if ((id != null && id != -1))
			query.setLong("id", id);
		return query.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<UserSupervisor> getSuperviosrs() throws Throwable {
		Query query = getSession().createQuery(
				"from UserSupervisor u where u.user.status not in ('OffBoard', 'No Show', 'Open','Selected', 'Deleted') ");
		return query.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<com.egil.pts.modal.User> getSubordinatesNew(Long id) throws Throwable {
		List<Long> userIdList = null;
		userIdList = new ArrayList<Long>();
		Query queryProc = getSession().createSQLQuery("CALL user_id_heirarchy_proc(:id)").setParameter("id", id);

		queryProc.executeUpdate();

		Query tmpQry = getSession().createSQLQuery("SELECT node FROM _result");
		userIdList = tmpQry.list();
		Query query = null;
		if (dashBoardAllList != null && dashBoardAllList.equalsIgnoreCase("TRUE")) {
			java.math.BigInteger q = (BigInteger) getSession()
					.createSQLQuery(
							"SELECT COUNT(id) FROM PTS_USER_ROLE r WHERE  r.user_id IN (:userIdList) AND r.role_id=2 ")
					.setParameterList("userIdList", userIdList).uniqueResult();

			if (q != null && q.intValue() > 1) {
				query = getSession().createSQLQuery(
						"SELECT u.name,u.id   from PTS_USER u join PTS_USER_ROLE r on r.USER_ID = u.id WHERE u.id IN (:userIdList) AND r.ROLE_ID=2")
						.addScalar("id", new LongType()).addScalar("name", new StringType())
						.setParameterList("userIdList", userIdList);
			} else {
				query = getSession().createSQLQuery(
						"select id,name from PTS_USER where status not in ('OffBoard', 'No Show', 'Open','Selected', 'Deleted') and"
								+ " id in (:userIdList) ")
						.addScalar("id", new LongType()).addScalar("name", new StringType())
						.setParameterList("userIdList", userIdList);
			}
		} else {
			query = getSession().createSQLQuery(
					"select id,name from PTS_USER where status not in ('OffBoard', 'No Show', 'Open','Selected', 'Deleted') and"
							+ " id in (:userIdList) ")
					.addScalar("id", new LongType()).addScalar("name", new StringType())
					.setParameterList("userIdList", userIdList);
		}
		List<com.egil.pts.modal.User> a = query
				.setResultTransformer(Transformers.aliasToBean(com.egil.pts.modal.User.class)).list();
		return a;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<User> getProgramManagers() throws Throwable {
		String queryStr = "from User u where u.userRole.role.id = 4 ";
		Query q = getSession().createQuery(queryStr);
		return q.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<User> getProjectManagers() throws Throwable {
		String queryStr = "from User u where u.userRole.role.id = 5 ";
		Query q = getSession().createQuery(queryStr);
		return q.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<UserSupervisor> getUserListToAssignNetworkCode(Long id, Set<Long> idList, String searchValue,
			Long selectedNetworkCodeId) throws Throwable {
		if (StringHelper.isNotEmpty(searchValue)) {
			searchValue = "%" + searchValue.trim().toLowerCase() + "%";
		} else {
			searchValue = "%";
		}
		String queryStr = " select distinct u from UserSupervisor u, UserPlatforms up, NetworkCodes nc where u.user.status not in ('OffBoard', 'No Show', 'Deleted') and "
				+ " u.supervisor.id = :id and u.user.id = up.user.id and up.platform.id = nc.pillarId ";
		if (StringHelper.isNotEmpty(searchValue)) {
			queryStr = queryStr + " and (u.user.personalInfo.name like :searchValue ) ";
		}
		if (idList != null && idList.size() > 0) {
			queryStr = queryStr + " and u.user.id not in (:idList) ";
		}
		if (selectedNetworkCodeId != null && selectedNetworkCodeId != 0) {
			queryStr = queryStr + " and nc.id=:selectedNetworkCodeId";
		}
		queryStr = queryStr + " order by u.user.personalInfo.name ";
		Query query = getSession().createQuery(queryStr);
		query.setLong("id", id);
		if (idList != null && idList.size() > 0) {
			query.setParameterList("idList", idList);
		}
		if (StringHelper.isNotEmpty(searchValue)) {
			query.setString("searchValue", searchValue);
		}

		if (selectedNetworkCodeId != null && selectedNetworkCodeId != 0) {
			query.setLong("selectedNetworkCodeId", selectedNetworkCodeId);
		}

		return query.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<com.egil.pts.modal.User> getProgramManagers(String pillarId) throws Throwable {
		// String queryStr =
		// "from UserPlatforms up where up.user.userRole.role.id = 4 ";
		String queryStr = "select UST.contribution,U.id,U.name,ST.project_id from PTS_USER U, PTS_USER_STABLE_TEAMS UST,PTS_STABLE_TEAMS ST,PTS_PILLAR P where P.ID=ST.project_id and  ST.ID=UST.stable_team_id and UST.user_id =U.ID and UST.contribution >0 and U.STREAM in (select id from PTS_STREAMS where STREAM_NAME in ('Program Manager') )";

		if (pillarId != null) {
			queryStr = queryStr + " and P.ID = " + pillarId;
		}
		Query q = getSession().createSQLQuery(queryStr).addScalar("id", new LongType()).addScalar("name",
				new StringType());
		return q.setResultTransformer(Transformers.aliasToBean(com.egil.pts.modal.User.class)).list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<com.egil.pts.modal.User> getProjectManagers(String pillarId) throws Throwable {
		String queryStr = "select UST.contribution,U.id,U.name,ST.project_id from PTS_USER U, PTS_USER_STABLE_TEAMS UST,PTS_STABLE_TEAMS ST,PTS_PILLAR P where P.ID=ST.project_id and  ST.ID=UST.stable_team_id and UST.user_id =U.ID and UST.contribution >0 and U.STREAM in (select id from PTS_STREAMS where STREAM_NAME in ('Program Manager', 'Project Manager','PM') )";
		if (pillarId != null) {
			queryStr = queryStr + " and P.ID  = " + pillarId;
		}
		Query q = getSession().createSQLQuery(queryStr).addScalar("id", new LongType()).addScalar("name",
				new StringType());
		return q.setResultTransformer(Transformers.aliasToBean(com.egil.pts.modal.User.class)).list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Long> getLineManagersIds() throws Throwable {
		String queryStr = "select user.id from User user, UserRole urole, Roles role"
				+ " where user.id=urole.user.id and role.id=urole.role.id"
				+ " and role.roleName='LINE MANAGER' and user.status not in ('OffBoard', 'No Show', 'Deleted') ";
		Query q = getSession().createQuery(queryStr);
		return q.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Long> getResourcesToAddProjects() throws Throwable {
		String queryStr = "select user.id from User user, UserRole urole, Roles role"
				+ " where user.id=urole.user.id and role.id=urole.role.id"
				+ " and (role.roleName in ('LINE MANAGER', 'PROGRAM MANAGER', 'PROJECT MANAGER', 'PM') or "
				+ " user.stream.streamName in ('Line Manager', 'Program Manager', 'Project Manager','PM')) "
				+ " and user.status not in ('OffBoard', 'No Show', 'Deleted') ";
		Query q = getSession().createQuery(queryStr);
		return q.list();
	}

	@SuppressWarnings("unchecked")
	@Transactional
	private List<com.egil.pts.modal.User> getUsersSummaryOld(Pagination pagination, SearchSortContainer searchSortObj)
			throws Throwable {
		StringBuilder queryString = new StringBuilder();
		queryString.append(
				"select id, name, supervisor, supervisorId, streamName, role, userType, userName, backFillOff, status, location, dateOfBilling, doj from "
						+ " (SELECT u.ID as id ,u.name as name, (select name from PTS_USER where id=us.SUPERVISOR_ID) as supervisor,us.SUPERVISOR_ID as supervisorId, "
						+ "s.stream_name as streamName, r.role_name as role, ut.user_type as userType, u.username as userName, "
						+ "u.back_fill_of as backFillOff, u.status as status, u.location_id as location, "
						+ " u.DATE_OF_BILLING as dateOfBilling, u.DOJ as doj"
						+ " from PTS_USER u inner join PTS_USER_SUPERVISOR us on us.user_id=u.id and "
						+ " u.id in (SELECT T1.user_id FROM PTS_USER_SUPERVISOR AS T1  INNER JOIN (SELECT user_id FROM "
						+ " PTS_USER_SUPERVISOR WHERE supervisor_id = " + searchSortObj.getSearchSupervisor() + " ) "
						+ " AS T2 ON T2.user_id = T1.supervisor_id OR T1.supervisor_id = "
						+ searchSortObj.getSearchSupervisor() + "  GROUP BY T1.id)  "
						+ " INNER JOIN PTS_USER_TYPES ut on u.USER_TYPE_ID=ut.id "
						+ " INNER JOIN PTS_USER_ROLE ur on ur.USER_ID=u.id "
						+ " INNER JOIN PTS_ROLES r on ur.ROLE_ID=r.id " + " INNER JOIN PTS_STREAMS s on u.STREAM=s.id "
						+ " ) tmp where  tmp.status is not null  and tmp.status not in  ('OffBoard', 'No Show', 'Open', 'Selected','Interns/ GET', 'Deleted', 'Induction')   ");
		if (searchSortObj != null) {

			if (searchSortObj.getSearchUserName() != null && !searchSortObj.getSearchUserName().equalsIgnoreCase("")) {
				queryString.append(" and tmp.name like '%" + searchSortObj.getSearchUserName() + "%'");
			}

			if (searchSortObj.getSearchStatus() != null && !searchSortObj.getSearchStatus().equalsIgnoreCase("")) {
				queryString.append(" and tmp.status like '%" + searchSortObj.getSearchStatus() + "%'");
			}

			if (searchSortObj.getSearchDOJ() != null && !searchSortObj.getSearchDOJ().equalsIgnoreCase("")) {
				queryString.append(" and tmp.doj like '%" + searchSortObj.getSearchDOJ() + "%'");
			}

			if (searchSortObj.getSearchLocation() != null && !searchSortObj.getSearchLocation().equalsIgnoreCase("")) {
				queryString.append(" and tmp.location = " + searchSortObj.getSearchLocation() + "");
			}

			if (searchSortObj.getSearchDOBilling() != null
					&& !searchSortObj.getSearchDOBilling().equalsIgnoreCase("")) {
				queryString.append(" and tmp.dateOfBilling  >= STR_TO_DATE('" + searchSortObj.getSearchDOBilling()
						+ "', '%Y-%m-%d')");
			}

			if (searchSortObj.getSidx() != null && !searchSortObj.getSidx().equals("")
					&& searchSortObj.getSord() != null && !searchSortObj.getSord().equals("")) {
				if (searchSortObj.getSidx().equalsIgnoreCase("name")) {
					queryString.append(" order by tmp.name " + searchSortObj.getSord());
				} else if (searchSortObj.getSidx().equalsIgnoreCase("supervisor")) {
					queryString.append(" order by tmp.supervisor " + searchSortObj.getSord());
				} else if (searchSortObj.getSidx().equalsIgnoreCase("status")) {
					queryString.append(" order by tmp.status " + searchSortObj.getSord());
				} else if (searchSortObj.getSidx().equalsIgnoreCase("location")) {
					queryString.append(" order by tmp.location " + searchSortObj.getSord());
				} else if (searchSortObj.getSidx().equalsIgnoreCase("backFillOff")) {
					queryString.append(" order by tmp.backFillOff " + searchSortObj.getSord());
				} else if (searchSortObj.getSidx().equalsIgnoreCase("streamName")) {
					queryString.append(" order by tmp.streamName " + searchSortObj.getSord());
				} else if (searchSortObj.getSidx().equalsIgnoreCase("role")) {
					queryString.append(" order by tmp.role " + searchSortObj.getSord());
				} else if (searchSortObj.getSidx().equalsIgnoreCase("userType")) {
					queryString.append(" order by tmp.userType " + searchSortObj.getSord());
				} else if (searchSortObj.getSidx().equalsIgnoreCase("userName")) {
					queryString.append(" order by tmp.userName " + searchSortObj.getSord());
				}
			}
		}
		Query query = getSession().createSQLQuery(queryString.toString()).addScalar("id", new LongType())
				.addScalar("name", new StringType()).addScalar("supervisor", new StringType())
				.addScalar("supervisorId", new LongType()).addScalar("streamName", new StringType())
				.addScalar("role", new StringType()).addScalar("userType", new StringType())
				.addScalar("userName", new StringType()).addScalar("backFillOff", new StringType())
				.addScalar("locationId", new LongType()).addScalar("status", new StringType());
		if (pagination != null) {
			if (pagination.getOffset() > 0)
				query.setFirstResult(pagination.getOffset());
			if (pagination.getSize() > 0)
				query.setMaxResults(pagination.getSize());
		}
		return query.setResultTransformer(Transformers.aliasToBean(com.egil.pts.modal.User.class)).list();
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<com.egil.pts.modal.User> getUsersSummary(Pagination pagination, SearchSortContainer searchSortObj)
			throws Throwable {
		Query query = null;
		if (searchSortObj.getStableTeamId() > 0 || searchSortObj.isSearchByStable()) {
			query = getSession().createSQLQuery("CALL user_heirarchy_proc_new(:id)");
		} else {
			query = getSession().createSQLQuery("CALL user_heirarchy_proc(:id)");
		}

		if (searchSortObj != null) {
			if (searchSortObj.getSearchSupervisor() == null || (searchSortObj.getSearchSupervisor() != null
					&& searchSortObj.getSearchSupervisor().trim().isEmpty())) {
				query.setParameter("id", Long.valueOf(searchSortObj.getLoggedInId()));
			} else {
				if (searchSortObj.isAllReporteesFlag()) {
					query.setParameter("id", Long.valueOf(searchSortObj.getSearchSupervisor()));
				} else {
					query.setParameter("id", Long.valueOf(searchSortObj.getLoggedInId()));
				}
			}
		}
		query.executeUpdate();
		StringBuilder queryString = new StringBuilder();

		if (searchSortObj.getStableTeamId() > 0 || searchSortObj.isSearchByStable()) {
			queryString.append(
					"select * from ( select tmp.id as id, tmp.name as name,tmp.stableTeam ,tmp.stableTeamName,tmp.contribution 'stableContribution', tmp.supervisor as supervisor, tmp.supervisorId as supervisorId,(select supervisor_id from PTS_USER_LINE_MANAGER where user_id=tmp.id) lineManagerId,	(select name from PTS_USER where id=(select supervisor_id from PTS_USER_LINE_MANAGER where user_id=tmp.id)) lineManager, "
							+ "tmp.streamName as streamName, tmp.role as role, tmp.userType as userType, tmp.userName as userName, tmp.backFillOff as backFillOff, "
							+ "tmp.status as status, tmp.location as location, tmp.location_id as locationId, tmp.location_name as locationName, tmp.region as region, tmp.dateOfBilling as dateOfBilling, tmp.doj as doj, tmp.primarySkill as primarySkillName"
							+ " from temp_user_summary tmp where tmp.status is not null and tmp.id != 1  ");
		} else {
			queryString.append(
					"select * from ( select tmp.id as id, tmp.name as name, tmp.supervisor as supervisor, tmp.supervisorId as supervisorId,(select supervisor_id from PTS_USER_LINE_MANAGER where user_id=tmp.id) lineManagerId,	(select name from PTS_USER where id=(select supervisor_id from PTS_USER_LINE_MANAGER where user_id=tmp.id)) lineManager, "
							+ "tmp.streamName as streamName, tmp.role as role, tmp.userType as userType, tmp.userName as userName, tmp.backFillOff as backFillOff, "
							+ "tmp.status as status, tmp.location as location, tmp.location_id as locationId, tmp.location_name as locationName, tmp.region as region, tmp.dateOfBilling as dateOfBilling, tmp.doj as doj, tmp.primarySkill as primarySkillName"
							+ " from temp_user_summary tmp where tmp.status is not null and tmp.id != 1  ");
		}

		if (searchSortObj != null) {

			if (searchSortObj.getSearchUserName() != null && !searchSortObj.getSearchUserName().equalsIgnoreCase("")) {
				queryString.append(" and tmp.name like '%" + searchSortObj.getSearchUserName() + "%'");
			}
			if (!searchSortObj.isAllReporteesFlag()) {
				if (searchSortObj.getSearchSupervisor() != null
						&& !searchSortObj.getSearchSupervisor().equalsIgnoreCase("")
						&& !searchSortObj.getSearchSupervisor().equalsIgnoreCase("-1")) {
					queryString.append(" and tmp.supervisorId = " + searchSortObj.getSearchSupervisor());
				}
			}

			if (searchSortObj.getSearchStatus() != null && !searchSortObj.getSearchStatus().equalsIgnoreCase("")
					&& !searchSortObj.getSearchStatus().equalsIgnoreCase("-1")) {
				queryString.append(" and tmp.status like '%" + searchSortObj.getSearchStatus() + "%'");
			}

			if (searchSortObj.getSearchDOJ() != null && !searchSortObj.getSearchDOJ().equalsIgnoreCase("")) {
				queryString.append(" and tmp.doj like '%" + searchSortObj.getSearchDOJ() + "%'");
			}

			if (searchSortObj.getSearchLocation() != null && !searchSortObj.getSearchLocation().equalsIgnoreCase("")) {
				queryString.append(" and tmp.location_id = " + searchSortObj.getSearchLocation() + "");
			}

			if (searchSortObj.getSearchDOBilling() != null
					&& !searchSortObj.getSearchDOBilling().equalsIgnoreCase("")) {
				queryString.append(" and tmp.dateOfBilling  >= STR_TO_DATE('" + searchSortObj.getSearchDOBilling()
						+ "', '%Y-%m-%d')");
			}

			if (searchSortObj.getSearchStream() != null && !searchSortObj.getSearchStream().equalsIgnoreCase("")) {
				queryString.append(" and tmp.streamName like '%" + searchSortObj.getSearchStream() + "%'");
			}

			if (searchSortObj.getSearchUserType() != null && !searchSortObj.getSearchUserType().equalsIgnoreCase("")) {
				queryString.append(" and tmp.userType like '%" + searchSortObj.getSearchUserType() + "%'");
			}

			if (searchSortObj.getSearchTechnology() != null
					&& !searchSortObj.getSearchTechnology().equalsIgnoreCase("")) {
				queryString.append(" and tmp.primarySkill like '%" + searchSortObj.getSearchTechnology() + "%'");
			}

			queryString.append(" and tmp.role <> 'ADMIN'");

			if (searchSortObj.getStableTeamId() > 0) {
				queryString.append(" and   tmp.stableTeam=:stableId  and tmp.contribution > 0 ");
			}
			queryString.append(" ) tmp ");
			if (searchSortObj != null) {
				if (searchSortObj.getLineManagerId() != null && searchSortObj.getLineManagerId() > 0) {
					queryString.append(" and   tmp.lineManagerId=" + searchSortObj.getLineManagerId());
				}
			}
			if (searchSortObj.getSidx() != null && !searchSortObj.getSidx().equals("")
					&& searchSortObj.getSord() != null && !searchSortObj.getSord().equals("")) {
				if (searchSortObj.getSidx().equalsIgnoreCase("name")) {
					queryString.append(" order by tmp.name " + searchSortObj.getSord());
				} else if (searchSortObj.getSidx().equalsIgnoreCase("supervisor")) {
					queryString.append(" order by tmp.supervisor " + searchSortObj.getSord());
				} else if (searchSortObj.getSidx().equalsIgnoreCase("status")) {
					queryString.append(" order by tmp.status " + searchSortObj.getSord());
				} else if (searchSortObj.getSidx().equalsIgnoreCase("location")) {
					queryString.append(" order by tmp.location_name " + searchSortObj.getSord());
				} else if (searchSortObj.getSidx().equalsIgnoreCase("backFillOff")) {
					queryString.append(" order by tmp.backFillOff " + searchSortObj.getSord());
				} else if (searchSortObj.getSidx().equalsIgnoreCase("streamName")) {
					queryString.append(" order by tmp.streamName " + searchSortObj.getSord());
				} else if (searchSortObj.getSidx().equalsIgnoreCase("role")) {
					queryString.append(" order by tmp.role " + searchSortObj.getSord());
				} else if (searchSortObj.getSidx().equalsIgnoreCase("userType")) {
					queryString.append(" order by tmp.userType " + searchSortObj.getSord());
				} else if (searchSortObj.getSidx().equalsIgnoreCase("userName")) {
					queryString.append(" order by tmp.userName " + searchSortObj.getSord());
				}
			} else {
				queryString.append(" order by tmp.name ");
			}
		}

		if (searchSortObj.getStableTeamId() > 0 || searchSortObj.isSearchByStable()) {
			query = getSession().createSQLQuery(queryString.toString()).addScalar("id", new LongType())
					.addScalar("name", new StringType()).addScalar("supervisor", new StringType())
					.addScalar("supervisorId", new LongType()).addScalar("streamName", new StringType())
					.addScalar("role", new StringType()).addScalar("userType", new StringType())
					.addScalar("userName", new StringType()).addScalar("backFillOff", new StringType())
					.addScalar("location", new StringType()).addScalar("locationId", new LongType())
					.addScalar("stableContribution", new FloatType()).addScalar("stableTeamName", new StringType())
					.addScalar("locationName", new StringType()).addScalar("primarySkillName", new StringType())
					.addScalar("lineManager", new StringType()).addScalar("status", new StringType())
					.addScalar("region", new StringType());
		} else {
			query = getSession().createSQLQuery(queryString.toString()).addScalar("id", new LongType())
					.addScalar("name", new StringType()).addScalar("supervisor", new StringType())
					.addScalar("supervisorId", new LongType()).addScalar("streamName", new StringType())
					.addScalar("role", new StringType()).addScalar("userType", new StringType())
					.addScalar("userName", new StringType()).addScalar("backFillOff", new StringType())
					.addScalar("location", new StringType()).addScalar("locationId", new LongType())
					.addScalar("locationName", new StringType()).addScalar("primarySkillName", new StringType())
					.addScalar("lineManager", new StringType()).addScalar("status", new StringType())
					.addScalar("region", new StringType());
		}
		if (searchSortObj.getStableTeamId() > 0) {
			query.setLong("stableId", searchSortObj.getStableTeamId());
		}
		if (pagination != null) {
			if (pagination.getOffset() > 0)
				query.setFirstResult(pagination.getOffset());
			if (pagination.getSize() > 0)
				query.setMaxResults(pagination.getSize());
		}
		return query.setResultTransformer(Transformers.aliasToBean(com.egil.pts.modal.User.class)).list();
	}

	@Override
	@Transactional
	public int getUsersSummaryCount(SearchSortContainer searchSortObj) throws Throwable {
		Query query = getSession().createSQLQuery("CALL user_heirarchy_proc(:id)");
		if (searchSortObj != null) {
			if (searchSortObj.getSearchSupervisor() == null || (searchSortObj.getSearchSupervisor() != null
					&& searchSortObj.getSearchSupervisor().trim().isEmpty())) {
				query.setParameter("id", Long.valueOf(searchSortObj.getLoggedInId()));
			} else {
				if (searchSortObj.isAllReporteesFlag()) {
					query.setParameter("id", Long.valueOf(searchSortObj.getSearchSupervisor()));
				} else {
					query.setParameter("id", Long.valueOf(searchSortObj.getLoggedInId()));
				}
			}
		}

		query.executeUpdate();
		StringBuilder queryString = new StringBuilder();
		queryString.append("select count(*)" + " from temp_user_summary tmp where tmp.status is not null ");
		if (searchSortObj != null) {

			if (searchSortObj.isCapacityScrnFlag()) {
				queryString.append(" and tmp.status not in ('OffBoard', 'No Show', 'Deleted') ");
			}

			if (searchSortObj.getSearchUserName() != null && !searchSortObj.getSearchUserName().equalsIgnoreCase("")) {
				queryString.append(" and tmp.name like '%" + searchSortObj.getSearchUserName() + "%'");
			}

			if (!searchSortObj.isAllReporteesFlag()) {
				if (searchSortObj.getSearchSupervisor() != null
						&& !searchSortObj.getSearchSupervisor().equalsIgnoreCase("")) {
					queryString.append(" and tmp.supervisorId = " + searchSortObj.getSearchSupervisor());
				}
			}

			if (searchSortObj.getSearchStatus() != null && !searchSortObj.getSearchStatus().equalsIgnoreCase("")) {
				queryString.append(" and tmp.status like '%" + searchSortObj.getSearchStatus() + "%'");
			}

			if (searchSortObj.getSearchDOJ() != null && !searchSortObj.getSearchDOJ().equalsIgnoreCase("")) {
				queryString.append(" and tmp.doj like '%" + searchSortObj.getSearchDOJ() + "%'");
			}

			if (searchSortObj.getSearchLocation() != null && !searchSortObj.getSearchLocation().equalsIgnoreCase("")) {
				queryString.append(" and tmp.location_id = " + searchSortObj.getSearchLocation() + "");
			}

			if (searchSortObj.getSearchDOBilling() != null
					&& !searchSortObj.getSearchDOBilling().equalsIgnoreCase("")) {
				queryString.append(" and tmp.dateOfBilling  >= STR_TO_DATE('" + searchSortObj.getSearchDOBilling()
						+ "', '%Y-%m-%d')");
			}

			if (searchSortObj.getSearchStream() != null && !searchSortObj.getSearchStream().equalsIgnoreCase("")) {
				queryString.append(" and tmp.streamName like '%" + searchSortObj.getSearchStream() + "%'");
			}

			if (searchSortObj.getSearchUserType() != null && !searchSortObj.getSearchUserType().equalsIgnoreCase("")) {
				queryString.append(" and tmp.userType like '%" + searchSortObj.getSearchUserType() + "%'");
			}

			if (searchSortObj.getSearchTechnology() != null
					&& !searchSortObj.getSearchTechnology().equalsIgnoreCase("")) {
				queryString.append(" and tmp.primarySkill like '%" + searchSortObj.getSearchTechnology() + "%'");
			}

			queryString.append(" and tmp.role <> 'ADMIN'");
		}
		query = getSession().createSQLQuery(queryString.toString());
		int count = 0;
		BigInteger obj = (BigInteger) query.uniqueResult();
		if (obj != null) {
			count = obj.intValue();
		}
		return count;
	}

	@Override
	public String getMailByUserName(String username) {
		Query query = getSession().createQuery(
				"select personalInfo.email from User u where u.credentials.userName = :userName and u.status is not null");
		query.setString("userName", username);
		return (String) query.uniqueResult();
	}

	@Override
	public String getMailByUserId(Long userId) {
		Query query = getSession()
				.createQuery("select personalInfo.email from User u where u.id = :userId and u.status is not null");
		query.setLong("userId", userId);
		return (String) query.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<UserReport> getLocationUserCount(String region, Long location, Long stream, Long supervisorId) {
		String qryStr = "select Location as location, Test as testUserCnt, Dev as devUserCnt,  PM as pmUserCnt, SE as seUserCnt, PTL as ptlUserCnt, Admin as adminUserCnt, "
				+ " (Dev+SE+PM+Test+PTL+Admin) as totalLocUserCnt from (select location Location,   SUM(CASE WHEN stream = 'Testing' THEN count ELSE 0 END) AS Test, "
				+ " SUM(CASE WHEN stream = 'Development' THEN count ELSE 0 END) AS Dev,  SUM(CASE WHEN stream = 'Project Manager' or stream = 'Program Manager' or stream = 'PM'  THEN count ELSE 0 END) AS PM, "
				+ " SUM(CASE WHEN stream = 'System Engineer' THEN count ELSE 0 END) AS SE, "
				+ " SUM(CASE WHEN stream = 'PTL/ System Engineer' THEN count ELSE 0 END) AS PTL, "
				+ " SUM(CASE WHEN stream = 'Admin' THEN count ELSE 0 END) AS Admin "
				+ " from (select sum(tmp.count) count, tmp.stream_name stream, tmp.location from "
				+ " (select count(u.id) count,str.stream_name, if(l.code='',l.name,concat(l.name, ', ',l.code)) as location from PTS_USER u "
				+ " inner join  PTS_STREAMS str on u.stream=str.id inner join PTS_LOCATION l on l.id=u.location_id "
				+ " where &location  "
				+ " status not in ('OffBoard', 'Deleted', 'Others', 'No Show') &advancedSearch group by str.id, u.location_id) tmp "
				+ " group by tmp.stream_name, tmp.location order by tmp.location) tmp1 group by location) tmp2";
		if ((region != null) && (!region.equals(""))) {
			if (region.equalsIgnoreCase("MANA")) {
				qryStr = qryStr.replaceAll("&location", " l.region ='MANA' and ");
			} else if (region.equalsIgnoreCase("EGIL")) {
				qryStr = qryStr.replaceAll("&location", " l.region ='EGIL' and ");
			} else {
				qryStr = qryStr.replaceAll("&location", "");
			}
		} else {
			qryStr = qryStr.replaceAll("&location", "");
		}

		String advSearchStr = "";
		List<Long> userIdList = null;
		if (supervisorId != null && supervisorId != 0l) {
			userIdList = new ArrayList<Long>();
			Query queryProc = getSession().createSQLQuery("CALL user_id_heirarchy_proc(:id)").setParameter("id",
					supervisorId);

			queryProc.executeUpdate();

			Query tmpQry = getSession().createSQLQuery("SELECT node FROM _result");
			userIdList = tmpQry.list();
			if (userIdList != null && userIdList.size() > 0) {
				advSearchStr = advSearchStr + " and u.id in (:userIdList) ";
			}
		}

		if (location != null) {
			advSearchStr = advSearchStr + "and l.id =:location ";
		}
		if (stream != null) {
			advSearchStr = advSearchStr + " and str.id =:stream ";
		}

		if (StringHelper.isNotEmpty(advSearchStr)) {
			qryStr = qryStr.replaceAll("&advancedSearch", advSearchStr);
		} else {
			qryStr = qryStr.replaceAll("&advancedSearch", "");
		}

		Query query = getSession().createSQLQuery(qryStr).addScalar("location", new StringType())
				.addScalar("testUserCnt", new FloatType()).addScalar("devUserCnt", new FloatType())
				.addScalar("pmUserCnt", new FloatType()).addScalar("seUserCnt", new FloatType())
				.addScalar("ptlUserCnt", new FloatType()).addScalar("adminUserCnt", new FloatType())
				.addScalar("totalLocUserCnt", new FloatType());
		if (location != null) {
			query.setLong("location", location);
		}
		if (stream != null) {
			query.setLong("stream", stream);
		}
		if (userIdList != null && userIdList.size() > 0) {
			query.setParameterList("userIdList", userIdList);
		}
		List<UserReport> userReportList = query.setResultTransformer(Transformers.aliasToBean(UserReport.class)).list();

		return userReportList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<UserReport> getLocationUserContributionReport(String ricoLocation, Long location, Long pillarId,
			Long stream, Long supervisorId, boolean isAppBased) {
		if (isAppBased) {

			String qryStr = "select *, (testUserCnt+devUserCnt+pmUserCnt+seUserCnt+ptlUserCnt+adminUserCnt) as totalLocUserCnt from(select   ln location,pn projectName,pln platform,SUM(CASE WHEN stream = 'Testing' THEN count ELSE 0 END) AS testUserCnt,  SUM(CASE WHEN stream = 'Development' THEN count ELSE 0 END) AS devUserCnt,"
					+ " SUM(CASE WHEN stream = 'Project Manager'  or stream = 'Program Manager' or stream = 'PM' THEN count ELSE 0 END) AS pmUserCnt,  SUM(CASE WHEN stream = 'System Engineer' THEN count ELSE 0 END) AS seUserCnt,  SUM(CASE WHEN stream = 'PTL/ System Engineer' THEN count ELSE 0 END) AS ptlUserCnt,  "
					+ " SUM(CASE WHEN stream = 'Admin' THEN count ELSE 0 END) AS adminUserCnt from ( select * from (select PL.pillar_name pln,P.PROJECT_NAME pn from PTS_PILLAR PL, PTS_PROJECT P where pillar_name not in ('Release Manager','Service Assurance') AND PL.ID=P.PILLAR_ID) t1  cross join  (select distinct if(l.code='',l.name,concat(l.name, ', ',l.code)) "
					+ " as ln from PTS_LOCATION l &location1 ) t2 )t3 left outer join ( SELECT  IFNULL(SUM(ROUND(UA.CONTRIBUTION,2)),0) count,P.PROJECT_NAME,IF(L.code='',L.name, CONCAT(L.name, ', ',L.code)) location ,STR.stream_name stream,PL.pillar_name pillar FROM PTS_USER_APPS UA,  PTS_USER U,PTS_LOCATION L, PTS_STREAMS STR  ,PTS_PROJECT P ,PTS_PILLAR PL WHERE "
					+ " U.ID=UA.USER_ID  AND  U.STREAM=STR.ID AND U.status in ('Onboard','Open','Notice Period') &advancedSearch  and P.ID=UA.PROJECT_ID AND U.LOCATION_ID=L.ID &location AND  PL.ID=P.PILLAR_ID   group by P.id,  U.location_id,  STR.stream_name)t4   on t3.pn=t4.PROJECT_NAME and t3.ln = t4.location and t3.pln=t4.pillar group by ln,pn  )t  ORDER BY platform";

			if ((ricoLocation != null) && (!ricoLocation.equals(""))) {
				if (ricoLocation.equalsIgnoreCase("MANA")) {
					qryStr = qryStr.replaceAll("&location1", " where l.region ='MANA'");
				} else if (ricoLocation.equalsIgnoreCase("EGIL")) {
					qryStr = qryStr.replaceAll("&location1", " where l.region ='EGIL'");
				} else {
					qryStr = qryStr.replaceAll("&location1", "");
				}
			} else {
				qryStr = qryStr.replaceAll("&location1", "");
			}

			if ((ricoLocation != null) && (!ricoLocation.equals(""))) {
				if (ricoLocation.equalsIgnoreCase("MANA")) {
					qryStr = qryStr.replaceAll("&location", " and L.region ='MANA'");
				} else if (ricoLocation.equalsIgnoreCase("EGIL")) {
					qryStr = qryStr.replaceAll("&location", " and L.region ='EGIL'");
				} else {
					qryStr = qryStr.replaceAll("&location", "");
				}
			} else {
				qryStr = qryStr.replaceAll("&location", "");
			}

			String advSearchStr = "";
			List<Long> userIdList = null;
			if (supervisorId != null && supervisorId != 0l) {
				userIdList = new ArrayList<Long>();
				Query queryProc = getSession().createSQLQuery("CALL user_id_heirarchy_proc(:id)").setParameter("id",
						supervisorId);

				queryProc.executeUpdate();

				Query tmpQry = getSession().createSQLQuery("SELECT node FROM _result");
				userIdList = tmpQry.list();
				if (userIdList != null && userIdList.size() > 0) {
					advSearchStr = advSearchStr + " and u.id in (:userIdList) ";
				}
			}

			if (location != null) {
				advSearchStr = advSearchStr + "and L.id =:location ";
			}
			if (pillarId != null) {
				advSearchStr = advSearchStr + " and PL.id =:pillarId ";
			}
			if (stream != null) {
				advSearchStr = advSearchStr + " and STR.id =:stream ";
			}

			if (StringHelper.isNotEmpty(advSearchStr)) {
				qryStr = qryStr.replaceAll("&advancedSearch", advSearchStr);
			} else {
				qryStr = qryStr.replaceAll("&advancedSearch", "");
			}
			Query query = getSession().createSQLQuery(qryStr).addScalar("location", new StringType())
					.addScalar("platform", new StringType()).addScalar("projectName", new StringType())
					.addScalar("testUserCnt", new FloatType()).addScalar("devUserCnt", new FloatType())
					.addScalar("pmUserCnt", new FloatType()).addScalar("seUserCnt", new FloatType())
					.addScalar("ptlUserCnt", new FloatType()).addScalar("adminUserCnt", new FloatType())
					.addScalar("totalLocUserCnt", new FloatType());
			if (location != null) {
				query.setLong("location", location);
			}
			if (pillarId != null) {
				query.setLong("pillarId", pillarId);
			}
			if (stream != null) {
				query.setLong("stream", stream);
			}
			if (userIdList != null && userIdList.size() > 0) {
				query.setParameterList("userIdList", userIdList);
			}

			List<UserReport> userReportList = query.setResultTransformer(Transformers.aliasToBean(UserReport.class))
					.list();
			return userReportList;
		} else {
			String qryStr = "select *, (testUserCnt+devUserCnt+pmUserCnt+seUserCnt+ptlUserCnt+adminUserCnt) as totalLocUserCnt from(select   ln location,pn projectName,pln platform,SUM(CASE WHEN stream = 'Testing' THEN count ELSE 0 END) AS testUserCnt,  SUM(CASE WHEN stream = 'Development' THEN count ELSE 0 END) AS devUserCnt,"
					+ " SUM(CASE WHEN stream = 'Project Manager'  or stream = 'Program Manager'  or stream = 'PM' THEN count ELSE 0 END) AS pmUserCnt,  SUM(CASE WHEN stream = 'System Engineer' THEN count ELSE 0 END) AS seUserCnt,  SUM(CASE WHEN stream = 'PTL/ System Engineer' THEN count ELSE 0 END) AS ptlUserCnt,  "
					+ " SUM(CASE WHEN stream = 'Admin' THEN count ELSE 0 END) AS adminUserCnt from ( select * from (select PL.pillar_name pln,P.PROJECT_NAME pn from PTS_PILLAR PL, PTS_PROJECT P where pillar_name not in ('Release Manager','Service Assurance') AND PL.ID=P.PILLAR_ID) t1  cross join  (select distinct if(l.code='',l.name,concat(l.name, ', ',l.code)) "
					+ " as ln from PTS_LOCATION l ) t2 )t3 left outer join ( SELECT  IFNULL(SUM(ROUND(UA.CONTRIBUTION,2)),0) count,P.PROJECT_NAME,IF(L.code='',L.name, CONCAT(L.name, ', ',L.code)) location ,STR.stream_name stream,PL.pillar_name pillar FROM PTS_USER_APPS UA,  PTS_USER U,PTS_LOCATION L, PTS_STREAMS STR  ,PTS_PROJECT P ,PTS_PILLAR PL WHERE "
					+ " U.ID=UA.USER_ID  AND  U.STREAM=STR.ID AND U.status in ('Onboard','Open','Notice Period') &advancedSearch  and P.ID=UA.PROJECT_ID AND U.LOCATION_ID=L.ID &location AND  PL.ID=P.PILLAR_ID   group by P.id,  U.location_id,  STR.stream_name)t4   on t3.pn=t4.PROJECT_NAME and t3.ln = t4.location and t3.pln=t4.pillar group by ln,pn  )t ";

			if ((ricoLocation != null) && (!ricoLocation.equals(""))) {
				if (ricoLocation.equalsIgnoreCase("MANA")) {
					qryStr = qryStr.replaceAll("&location", " and L.region ='MANA'");
				} else if (ricoLocation.equalsIgnoreCase("EGIL")) {
					qryStr = qryStr.replaceAll("&location", " and L.region ='EGIL'");
				} else {
					qryStr = qryStr.replaceAll("&location", "");
				}
			} else {
				qryStr = qryStr.replaceAll("&location", "");
			}
			String advSearchStr = "";
			List<Long> userIdList = null;
			if (supervisorId != null && supervisorId != 0l) {
				userIdList = new ArrayList<Long>();
				Query queryProc = getSession().createSQLQuery("CALL user_id_heirarchy_proc(:id)").setParameter("id",
						supervisorId);

				queryProc.executeUpdate();

				Query tmpQry = getSession().createSQLQuery("SELECT node FROM _result");
				userIdList = tmpQry.list();
				if (userIdList != null && userIdList.size() > 0) {
					advSearchStr = advSearchStr + " and u.id in (:userIdList) ";
				}
			}

			if (location != null) {
				advSearchStr = advSearchStr + "and l.id =:location ";
			}
			if (pillarId != null) {
				advSearchStr = advSearchStr + " and p.id =:pillarId ";
			}
			if (stream != null) {
				advSearchStr = advSearchStr + " and str.id =:stream ";
			}

			if (StringHelper.isNotEmpty(advSearchStr)) {
				qryStr = qryStr.replaceAll("&advancedSearch", advSearchStr);
			} else {
				qryStr = qryStr.replaceAll("&advancedSearch", "");
			}
			Query query = getSession().createSQLQuery(qryStr).addScalar("location", new StringType())
					.addScalar("platform", new StringType()).addScalar("testUserCnt", new FloatType())
					.addScalar("devUserCnt", new FloatType()).addScalar("pmUserCnt", new FloatType())
					.addScalar("seUserCnt", new FloatType()).addScalar("ptlUserCnt", new FloatType())
					.addScalar("adminUserCnt", new FloatType()).addScalar("totalLocUserCnt", new FloatType());
			if (location != null) {
				query.setLong("location", location);
			}
			if (pillarId != null) {
				query.setLong("pillarId", pillarId);
			}
			if (stream != null) {
				query.setLong("stream", stream);
			}
			if (userIdList != null && userIdList.size() > 0) {
				query.setParameterList("userIdList", userIdList);
			}
			List<UserReport> userReportList = query.setResultTransformer(Transformers.aliasToBean(UserReport.class))
					.list();

			return userReportList;
		}
	}

	@Override
	public int getTechScoreRecordCount(String region, Long techId, String priaryFlag, Long projectId, Long selectedYear,
			String halfYear) {
		int count = 0;
		String qryStr = "select count(*) from  "
				+ " PTS_USER u inner join PTS_USER_SUPERVISOR usup on usup.user_id=u.id inner join PTS_LOCATION l on l.id=u.location_id,"
				+ " PTS_TECHNOLOGIES s,  PTS_USER_SKILLS us, PTS_USER_SKILL_SCORE uss, PTS_USER_APPS ua, PTS_PROJECT p "
				+ "where &location ua.user_id=u.id and p.id=ua.project_id and u.id=us.user_id and s.id=us.technology_id "
				+ " and us.id=uss.USER_SKILL_ID and ua.PRIMARY_FLAG='Y'  and u.status not in ('OffBoard', 'Deleted', 'Others') ";
		// + " order by u.name, p.project_name, s.technology_name";
		if ((region != null) && (!region.equals(""))) {
			if (region.equalsIgnoreCase("MANA")) {
				qryStr = qryStr.replaceAll("&location", " l.region ='MANA' and ");
			} else if (region.equalsIgnoreCase("EGIL")) {
				qryStr = qryStr.replaceAll("&location", " l.region ='EGIL' and ");
			} else {
				qryStr = qryStr.replaceAll("&location", "");
			}
		} else {
			qryStr = qryStr.replaceAll("&location", "");
		}

		if (techId != null && techId.longValue() > 0) {
			qryStr = qryStr + " and s.id = " + techId;
		}
		if (projectId != null && projectId.longValue() > 0) {
			qryStr = qryStr + " and p.id = " + projectId;
		}
		if (selectedYear != null && selectedYear.longValue() > 0) {
			qryStr = qryStr + " and uss.YEAR = " + selectedYear;
		}

		if (halfYear != null && !halfYear.equals("")) {
			qryStr = qryStr + " and uss.YEAR_HALF = '" + halfYear + "' ";
		}
		if (priaryFlag != null) {
			if (priaryFlag.equalsIgnoreCase("Primary")) {
				qryStr = qryStr + " and us.PRIMARY_FLAG = 'Y' ";
			} else if (priaryFlag.equalsIgnoreCase("Secondary")) {
				qryStr = qryStr + " and us.PRIMARY_FLAG is null ";
			}
		}

		Query query = getSession().createSQLQuery(qryStr);
		BigInteger obj = (BigInteger) query.uniqueResult();
		if (obj != null) {
			count = obj.intValue();
		}
		return count;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TechCompetencyScore> getTechScoreRecordDetails(String region, Long techId, String priaryFlag,
			Long projectId, Pagination pagination, SearchSortContainer searchSortObj, Long selectedYear,
			String halfYear) {
		String qryStr = "select u.name as userName, (select name from PTS_USER where id=usup.SUPERVISOR_ID) as supervisorName, "
				+ " s.technology_name as technologyName, p.project_name as projectName, uss.tech_score as techScore, uss.YEAR as year, uss.YEAR_HALF as halfYear from  "
				+ " PTS_USER u inner join PTS_USER_SUPERVISOR usup on usup.user_id=u.id inner join PTS_LOCATION l on l.id=u.location_id,"
				+ " PTS_TECHNOLOGIES s,  PTS_USER_SKILLS us, PTS_USER_SKILL_SCORE uss, PTS_USER_APPS ua, PTS_PROJECT p "
				+ "where &location ua.user_id=u.id and p.id=ua.project_id and u.id=us.user_id and s.id=us.technology_id "
				+ " and us.id=uss.USER_SKILL_ID and ua.PRIMARY_FLAG='Y'  and u.status not in ('OffBoard', 'Deleted', 'Others') ";
		if ((region != null) && (!region.equals(""))) {
			if (region.equalsIgnoreCase("MANA")) {
				qryStr = qryStr.replaceAll("&location", " l.region ='MANA' and ");
			} else if (region.equalsIgnoreCase("EGIL")) {
				qryStr = qryStr.replaceAll("&location", " l.region ='EGIL' and ");
			} else {
				qryStr = qryStr.replaceAll("&location", "");
			}
		} else {
			qryStr = qryStr.replaceAll("&location", "");
		}

		if (techId != null && techId.longValue() > 0) {
			qryStr = qryStr + " and s.id = " + techId;
		}
		if (projectId != null && projectId.longValue() > 0) {
			qryStr = qryStr + " and p.id = " + projectId;
		}

		if (selectedYear != null && selectedYear.longValue() > 0) {
			qryStr = qryStr + " and uss.YEAR = " + selectedYear;
		}

		if (halfYear != null && !halfYear.equals("")) {
			qryStr = qryStr + " and uss.YEAR_HALF = '" + halfYear + "' ";
		}

		if (priaryFlag != null) {
			if (priaryFlag.equalsIgnoreCase("Primary")) {
				qryStr = qryStr + " and us.PRIMARY_FLAG = 'Y' ";
			} else if (priaryFlag.equalsIgnoreCase("Secondary")) {
				qryStr = qryStr + " and us.PRIMARY_FLAG is null ";
			}
		}

		if (searchSortObj.getSidx() != null && !searchSortObj.getSidx().equals("") && searchSortObj.getSord() != null
				&& !searchSortObj.getSord().equals("")) {
			if (searchSortObj.getSidx().equalsIgnoreCase("userName")) {
				qryStr = qryStr + " order by u.name " + searchSortObj.getSord();
			} else if (searchSortObj.getSidx().equalsIgnoreCase("technologyName")) {
				qryStr = qryStr + " order by s.technology_name " + searchSortObj.getSord();
			} else if (searchSortObj.getSidx().equalsIgnoreCase("projectName")) {
				qryStr = qryStr + " order by p.project_name " + searchSortObj.getSord();
			}
		} else {
			qryStr = qryStr + " order by u.name, p.project_name, s.technology_name";
		}

		Query query = getSession().createSQLQuery(qryStr).addScalar("userName", new StringType())
				.addScalar("supervisorName", new StringType()).addScalar("technologyName", new StringType())
				.addScalar("projectName", new StringType()).addScalar("year", new IntegerType())
				.addScalar("halfYear", new StringType()).addScalar("techScore", new IntegerType());

		if (pagination != null) {
			if (pagination.getOffset() > 0)
				query.setFirstResult(pagination.getOffset());
			if (pagination.getSize() > 0)
				query.setMaxResults(pagination.getSize());
		}

		List<TechCompetencyScore> userReportList = query
				.setResultTransformer(Transformers.aliasToBean(TechCompetencyScore.class)).list();

		return userReportList;

	}

	@Override
	public int getCompScoreRecordCount(String region, Long pillarId, Long projectId, String primaryFlag,
			Long selectedYear, String halfYear) {
		int count = 0;
		String qryStr = "select count(*) from  "
				+ " PTS_USER u inner join PTS_USER_SUPERVISOR usup on usup.user_id=u.id inner join PTS_LOCATION l on l.id=u.location_id,  "
				+ " PTS_PILLAR pillar, PTS_USER_PLATFORMS ua, PTS_USER_PLATFORM_COMP_SCORE uacs where &location u.status not in ('OffBoard', 'Deleted', 'Others') and  "
				+ " ua.user_id=u.id and pillar.id=ua.platform_id and ua.id=uacs.USER_PLATFORM_ID  ";

		if ((region != null) && (!region.equals(""))) {
			if (region.equalsIgnoreCase("MANA")) {
				qryStr = qryStr.replaceAll("&location", " l.region ='MANA' and ");
			} else if (region.equalsIgnoreCase("EGIL")) {
				qryStr = qryStr.replaceAll("&location", " l.region ='EGIL' and ");
			} else {
				qryStr = qryStr.replaceAll("&location", "");
			}
		} else {
			qryStr = qryStr.replaceAll("&location", "");
		}

		if (pillarId != null && pillarId.longValue() > 0) {
			qryStr = qryStr + " and pillar.id = " + pillarId;
		}
		if (selectedYear != null && selectedYear.longValue() > 0) {
			qryStr = qryStr + " and uacs.YEAR = " + selectedYear;
		}

		if (halfYear != null && !halfYear.equals("")) {
			qryStr = qryStr + " and uacs.YEAR_HALF = '" + halfYear + "' ";
		}

		Query query = getSession().createSQLQuery(qryStr);
		BigInteger obj = (BigInteger) query.uniqueResult();
		if (obj != null) {
			count = obj.intValue();
		}
		return count;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TechCompetencyScore> getCompScoreRecordDetails(String region, Long pillarId, Long projectId,
			String primaryFlag, Pagination pagination, SearchSortContainer searchSortObj, Long selectedYear,
			String halfYear) {
		String qryStr = "select u.name as userName, (select name from PTS_USER where id=usup.SUPERVISOR_ID) as supervisorName, "
				+ " pillar.pillar_name as platformName,  uacs.COMPETENCY_SCORE as compScore, uacs.YEAR as year, uacs.YEAR_HALF as halfYear from  "
				+ " PTS_USER u inner join PTS_USER_SUPERVISOR usup on usup.user_id=u.id inner join PTS_LOCATION l on l.id=u.location_id,  "
				+ "  PTS_PILLAR pillar, PTS_USER_PLATFORMS ua, PTS_USER_PLATFORM_COMP_SCORE uacs where &location u.status not in ('OffBoard', 'Deleted', 'Others') and  "
				+ " ua.user_id=u.id and pillar.id=ua.platform_id and ua.id=uacs.USER_PLATFORM_ID ";
		;

		if ((region != null) && (!region.equals(""))) {
			if (region.equalsIgnoreCase("MANA")) {
				qryStr = qryStr.replaceAll("&location", " l.region ='MANA' and ");
			} else if (region.equalsIgnoreCase("EGIL")) {
				qryStr = qryStr.replaceAll("&location", " l.region ='EGIL' and ");
			} else {
				qryStr = qryStr.replaceAll("&location", "");
			}
		} else {
			qryStr = qryStr.replaceAll("&location", "");
		}

		if (pillarId != null && pillarId.longValue() > 0) {
			qryStr = qryStr + " and pillar.id = " + pillarId;
		}

		if (selectedYear != null && selectedYear.longValue() > 0) {
			qryStr = qryStr + " and uacs.YEAR = " + selectedYear;
		}

		if (halfYear != null && !halfYear.equals("")) {
			qryStr = qryStr + " and uacs.YEAR_HALF = '" + halfYear + "' ";
		}

		if (searchSortObj.getSidx() != null && !searchSortObj.getSidx().equals("") && searchSortObj.getSord() != null
				&& !searchSortObj.getSord().equals("")) {
			if (searchSortObj.getSidx().equalsIgnoreCase("userName")) {
				qryStr = qryStr + " order by u.name " + searchSortObj.getSord();
			} else if (searchSortObj.getSidx().equalsIgnoreCase("platformName")) {
				qryStr = qryStr + " order by pillar.pillar_name " + searchSortObj.getSord();
			}
		} else {
			qryStr = qryStr + " order by u.name, pillar.pillar_name";
		}

		Query query = getSession().createSQLQuery(qryStr).addScalar("userName", new StringType())
				.addScalar("supervisorName", new StringType()).addScalar("platformName", new StringType())
				.addScalar("year", new IntegerType()).addScalar("halfYear", new StringType())
				.addScalar("compScore", new IntegerType());

		if (pagination != null) {
			if (pagination.getOffset() > 0)
				query.setFirstResult(pagination.getOffset());
			if (pagination.getSize() > 0)
				query.setMaxResults(pagination.getSize());
		}

		List<TechCompetencyScore> userReportList = query
				.setResultTransformer(Transformers.aliasToBean(TechCompetencyScore.class)).list();

		return userReportList;

	}

	@SuppressWarnings("unchecked")
	@Override
	public void getHeadCnt(Long supervisorId, Map<String, String> headCntMap) {

		List<Long> userIdList = new ArrayList<Long>();
		Query queryProc = getSession().createSQLQuery("CALL user_id_heirarchy_proc(:id)").setParameter("id",
				supervisorId);

		queryProc.executeUpdate();

		Query tmpQry = getSession().createSQLQuery("SELECT node FROM _result");
		userIdList = tmpQry.list();

		String queryString = "select status, count(*) count from PTS_USER u  "
				+ " where u.ID in (:userIdList) and status not in ('No Show', 'DELETED', 'Others', 'OffBoard') group by status";

		Query query = getSession().createSQLQuery(queryString);
		if (userIdList != null && userIdList.size() > 0) {
			query.setParameterList("userIdList", userIdList);
		}
		ScrollableResults rs = query.scroll();
		if (rs != null) {
			while (rs.next()) {
				Object[] obj = rs.get();
				headCntMap.put(obj[0].toString(), obj[1].toString());
			}
		}

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<com.egil.pts.modal.User> getContributionDetailReport(Long supervisorId, Pagination pagination,
			Long location, Long pillarId, Long stream, String region) {
		String queryString = "";
		List<Long> userIdList = new ArrayList<Long>();
		if (supervisorId != null && supervisorId != 0l) {
			Query queryProc = getSession().createSQLQuery("CALL user_id_heirarchy_proc(:id)").setParameter("id",
					supervisorId);

			queryProc.executeUpdate();

			Query tmpQry = getSession().createSQLQuery("SELECT node FROM _result");
			userIdList = tmpQry.list();
		}
		queryString = " select * from (select u.id id, u.name name, p.pillar_name platformName, ROUND(sum(ROUND(up.contribution,2))*100,0) contribution , "
				+ " if(l.code='',l.name,concat(l.name, ', ',l.code)) as location , "
				+ " str.stream_name streamName, (select name from PTS_USER where id=us.SUPERVISOR_ID) as supervisor, u.status status, u.USERNAME userName "
				+ " from PTS_USER u, PTS_USER_PLATFORMS up, PTS_PILLAR p, PTS_LOCATION l, PTS_STREAMS str, PTS_USER_SUPERVISOR us where &location "
				+ " u.id=up.user_id and u.status not in ('OffBoard', 'Deleted', 'Others', 'No Show') "
				+ " and up.platform_id=p.id " + " and up.contribution >0 "
				+ " and l.id=u.location_id and u.stream=str.id and us.user_id=u.id ";

		if ((region != null) && (!region.equals(""))) {
			if (region.equalsIgnoreCase("MANA")) {
				queryString = queryString.replaceAll("&location", " l.region ='MANA' and ");
			} else if (region.equalsIgnoreCase("EGIL")) {
				queryString = queryString.replaceAll("&location", " l.region ='EGIL' and ");
			} else {
				queryString = queryString.replaceAll("&location", "");
			}
		} else {
			queryString = queryString.replaceAll("&location", "");
		}

		if (userIdList != null && userIdList.size() > 0) {
			queryString = queryString + " and u.id in (:userIdList) ";
		}
		if (location != null) {
			queryString = queryString + " and l.id =:location ";
		}

		if (pillarId != null) {
			queryString = queryString + " and p.id =:pillarId ";
		}

		if (stream != null) {
			queryString = queryString + " and str.id =:stream ";
		}

		queryString = queryString + " group by up.user_id,up.platform_id) t order by name ";

		Query query = getSession().createSQLQuery(queryString).addScalar("id", new LongType())
				.addScalar("name", new StringType()).addScalar("platformName", new StringType())
				.addScalar("location", new StringType()).addScalar("streamName", new StringType())
				.addScalar("status", new StringType()).addScalar("userName", new StringType())
				.addScalar("supervisor", new StringType()).addScalar("contribution", new FloatType());
		if (location != null) {
			query.setLong("location", location);
		}
		if (pillarId != null) {
			query.setLong("pillarId", pillarId);
		}
		if (stream != null) {
			query.setLong("stream", stream);
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

		List<com.egil.pts.modal.User> resourceUtilizationList = query
				.setResultTransformer(Transformers.aliasToBean(com.egil.pts.modal.User.class)).list();
		return resourceUtilizationList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public int getContributionDetailReportCount(Long supervisorId, Long location, Long pillarId, Long stream,
			String region) {
		String queryString = "";
		List<Long> userIdList = new ArrayList<Long>();
		int count = 0;
		if (supervisorId != null && supervisorId != 0l) {
			Query queryProc = getSession().createSQLQuery("CALL user_id_heirarchy_proc(:id)").setParameter("id",
					supervisorId);

			queryProc.executeUpdate();

			Query tmpQry = getSession().createSQLQuery("SELECT node FROM _result");
			userIdList = tmpQry.list();
		}
		queryString = " select count(*) from (select u.id id, u.name name, p.pillar_name platformName, ROUND(sum(ROUND(up.contribution,2))*100,0) contribution , "
				+ " if(l.code='',l.name,concat(l.name, ', ',l.code)) as location , "
				+ " str.stream_name stream, (select name from PTS_USER where id=us.SUPERVISOR_ID) as supervisor, u.status status, u.USERNAME userName "
				+ " from PTS_USER u, PTS_USER_PLATFORMS up, PTS_PILLAR p, PTS_LOCATION l, PTS_STREAMS str, PTS_USER_SUPERVISOR us where &location "
				+ " u.id=up.user_id and u.status not in ('OffBoard', 'Deleted', 'Others', 'No Show') "
				+ " and up.platform_id=p.id " + " and up.contribution >0 "
				+ " and l.id=u.location_id  and u.stream=str.id and us.user_id=u.id ";

		if ((region != null) && (!region.equals(""))) {
			if (region.equalsIgnoreCase("MANA")) {
				queryString = queryString.replaceAll("&location", " l.region ='MANA' and ");
			} else if (region.equalsIgnoreCase("EGIL")) {
				queryString = queryString.replaceAll("&location", " l.region ='EGIL' and ");
			} else {
				queryString = queryString.replaceAll("&location", "");
			}
		} else {
			queryString = queryString.replaceAll("&location", "");
		}

		if (userIdList != null && userIdList.size() > 0) {
			queryString = queryString + " and u.id in (:userIdList) ";
		}

		if (location != null) {
			queryString = queryString + "and l.id =:location ";
		}
		if (pillarId != null) {
			queryString = queryString + " and p.id =:pillarId ";
		}
		if (stream != null) {
			queryString = queryString + " and str.id =:stream ";
		}
		queryString = queryString + " group by up.user_id, up.platform_id) t ";

		Query query = getSession().createSQLQuery(queryString);
		if (location != null) {
			query.setLong("location", location);
		}
		if (pillarId != null) {
			query.setLong("pillarId", pillarId);
		}
		if (stream != null) {
			query.setLong("stream", stream);
		}
		if (userIdList != null && userIdList.size() > 0) {
			query.setParameterList("userIdList", userIdList);
		}
		BigInteger obj = (BigInteger) query.uniqueResult();
		if (obj != null) {
			count = obj.intValue();
		}
		return count;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<UserAccounts> getUserAccounts(String userId) throws Throwable {
		String queryStr = " FROM UserAccounts ua ";
		if (userId != null && !userId.isEmpty()) {
			queryStr = queryStr + " where ua.user.id=:userId ";
		}
		Query query = getSession().createQuery(" FROM UserAccounts ua where ua.user.id=:userId ");
		if (userId != null && !userId.isEmpty()) {
			query.setInteger("userId", Integer.parseInt(userId));
		}
		return query.list();
	}

	@Override
	public List<PtsHolidays> getHolidayList(PtsHolidays holidays, SearchSortContainer sortContainer) {
		return null;
	}

	@Override
	public List<PtsHolidays> manageHolidayList(PtsHolidays holidays, SearchSortContainer sortContainer) {
		return null;
	}

	@Override
	public boolean resetPassword(String userName, String email) {
		String queryStr = "update PTS_USER u set u.PASSWORD \r\n"
				+ "= 'qFTTf27hsSMRpZ7quGWRig==' where LOWER(u.USERNAME) = LOWER(:userName) and LOWER(u.EMAIL)= LOWER(:email) ";

		Query query = getSession().createSQLQuery(queryStr);
		query.setString("userName", userName);
		query.setString("email", email);

		return (query.executeUpdate() > 0) ? true : false;
	}

	public String getDashBoardAllList() {
		return dashBoardAllList;
	}

	public void setDashBoardAllList(String dashBoardAllList) {
		this.dashBoardAllList = dashBoardAllList;
	}

	@Override
	public List<com.egil.pts.modal.User> getAllSupervisors(Long id) {
		List<Long> userIdList = null;
		userIdList = new ArrayList<Long>();
		Query queryProc = getSession().createSQLQuery("CALL user_id_heirarchy_proc(:id)").setParameter("id", id);

		queryProc.executeUpdate();

		Query tmpQry = getSession().createSQLQuery("SELECT node FROM _result");
		userIdList = tmpQry.list();

		Query query = getSession().createSQLQuery(
				"SELECT u.ID id,u.NAME name,u.STREAM stream,s.STREAM_NAME streamName FROM PTS_USER u JOIN PTS_STREAMS s where u.ID IN(:userIds) AND u.NAME NOT LIKE '%Admin%' AND u.STREAM=s.ID ORDER BY u.NAME")
				.addScalar("stream", new LongType()).addScalar("streamName", new StringType())
				.addScalar("id", new LongType()).addScalar("name", new StringType())
				.setParameterList("userIds", userIdList);
		List<com.egil.pts.modal.User> resourceUtilizationList = query
				.setResultTransformer(Transformers.aliasToBean(com.egil.pts.modal.User.class)).list();
		return resourceUtilizationList;
	}

	@Override
	public List<com.egil.pts.modal.User> getAllResources(Long supervisor) {
		Query query = getSession().createSQLQuery(
				"select u.name,u.id from PTS_USER u join PTS_USER_SUPERVISOR s on u.id=s.USER_ID where s.SUPERVISOR_ID =:sup order by u.NAME ")
				.addScalar("id", new LongType()).addScalar("name", new StringType()).setLong("sup", supervisor);
		List<com.egil.pts.modal.User> resourceUtilizationList = query
				.setResultTransformer(Transformers.aliasToBean(com.egil.pts.modal.User.class)).list();
		return resourceUtilizationList;
	}

	public List<UserCapacity> GenerateNWUtilizationDate(int year, Long pillar, String reportype, Integer selectedMonth,
			Long userId) {
		List<UserCapacity> userCapacity = new ArrayList<UserCapacity>();

		if (reportype == null || (reportype != null && reportype.equalsIgnoreCase("MONTHLY"))) {
			try {
				if (selectedMonth == null || (selectedMonth != null && selectedMonth <= -1)) {
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
								null, pillar);
					}
				} else {

					Calendar gc = Calendar.getInstance();
					gc.set(Calendar.YEAR, year);
					gc.set(Calendar.MONTH, selectedMonth - 1);
					gc.set(Calendar.DAY_OF_MONTH, 1);
					Date monthStart = gc.getTime();
					gc = Calendar.getInstance();
					gc.set(Calendar.YEAR, year);
					gc.set(Calendar.MONTH, selectedMonth - 1);
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
					getMonthlyNCUsageByUser(startDay, endDay, startWeek, endWeek, null, month, year, userCapacity, null,
							pillar);

				}

			} catch (Throwable th) {
				th.printStackTrace();
			} finally {
			}
		} else {
			String filename = "RICO_NW_UTILIZATION_REPORT_YEARLY_" + year + ".xlsx";
			try {

				{
					getYearlyNCUsageByUser(year, userCapacity, null, pillar);
				}

			} catch (Throwable th) {
				th.printStackTrace();
			} finally {
			}
		}
		return userCapacity;

	}

	private void getYearlyNCUsageByUser(int year, List<UserCapacity> userCapacity, Object object2, Long pillar) {
		String finalStr = " select 	nc.id,	nc.RELEASE_TYPE RELEASETYPE,	nc.impl_planned_date IMPLEMENTATIONDATE ,	(	select		PROJECT_NAME	from		PTS_PROJECT	where		id = nc.project_id) PROJECT,	(	select		NAME	from		PTS_USER	where		id = nc.project_manager ) 'SUPERVISORNAME',	nc.STATUS STATUS,	nc.TFSEpic,	(nc.ORIGINAL_DESIGN_LOE + nc.ORIGINAL_DEV_LOE + nc.ORIGINAL_TEST_LOE + nc.ORIGINAL_PROJ_MGMT_LOE + nc.ORIGINAL_IMPL_LOE) TOTALCAPACITY ,	'NA' month,	date_format( t.WEEKENDING_DATE, '%Y') year,	concat(nc.RELEASE_ID, ' - ', nc.RELEASE_NAME) NETWORKCODE,	ROUND((sum(t.MON_HRS ) +sum(t.tue_hrs) + sum(t.wed_hrs) + sum(t.thu_hrs)+ sum(t.fri_hrs)+ sum(t.sat_hrs)+ sum(t.sun_hrs)), 2) as SUMMATION ,	t.WEEKENDING_DATE from	PTS_NETWORK_CODES nc inner join PTS_USER_TIMESHEET t on	nc.id = t.network_code_id where	"
				+ ((pillar != null && pillar != -1) ? " nc.project_id = " + pillar + "	and  " : "")
				+ " date_format(WEEKENDING_DATE ,'%Y')=" + year
				+ " and  nc.STATUS not in ('DELETED','Cancelled','Implemented','IN_ACTIVE') group by t.network_code_id ";
		// System.out.println(finalStr);
		if (year < Calendar.getInstance().getWeekYear()) {
			finalStr = finalStr.replaceAll("PTS_USER_TIMESHEET", "PTS_USER_TIMESHEET_2019");
		}
		Query query = getSession().createSQLQuery(finalStr).addScalar("TFSEpic", new LongType())
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

	private void getMonthlyNCUsageByUser(String startDay, String endDay, String startWeek, String endWeek, Long userId,
			String month, int year, List<UserCapacity> userCapacity, Long ncId, Long pillar) {

		String qryStr = "select  nc.id,nc.RELEASE_TYPE RELEASETYPE,nc.impl_planned_date IMPLEMENTATIONDATE ,(SELECT PROJECT_NAME FROM PTS_PROJECT WHERE id=nc.project_id) PROJECT,(select NAME from PTS_USER where id=nc.project_manager ) 'SUPERVISORNAME',nc.STATUS STATUS,nc.TFSEpic,(nc.ORIGINAL_DESIGN_LOE +nc.ORIGINAL_DEV_LOE	+nc.ORIGINAL_TEST_LOE +nc.ORIGINAL_PROJ_MGMT_LOE +nc.ORIGINAL_IMPL_LOE) TOTALCAPACITY ,'"
				+ month
				+ "' MONTH,date_format( t.WEEKENDING_DATE,'%Y') year, concat(nc.RELEASE_ID, ' - ',nc.RELEASE_NAME) NETWORKCODE, :summation "
				+ ",  t.WEEKENDING_DATE  "
				+ " from PTS_NETWORK_CODES nc inner  join PTS_USER_TIMESHEET t on nc.id= t.network_code_id  "
				+ "   where :whereClause and nc.STATUS not in ('DELETED','Cancelled','Implemented','IN_ACTIVE')"
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
						+ ((pillar != null && pillar != -1) ? " and  nc.PROJECT_ID=" + pillar + " " : " "));
		String endQryStr = qryStr.replaceAll(":summation", endDayQryStr).replaceAll(":whereClause",
				" t.WEEKENDING_DATE = '" + endWeek + "' " + (userId == null ? " " : " and t.user_id =" + userId)
						+ (ncId == null ? " " : " and t.network_code_id =" + ncId)
						+ ((pillar != null && pillar != -1) ? " and  nc.PROJECT_ID=" + pillar + " " : " "));
		String midQryStr = qryStr.replaceAll(":summation",
				"ROUND(sum(t.mon_hrs)+ sum(t.tue_hrs) + sum(t.wed_hrs) + sum(t.thu_hrs)+ sum(t.fri_hrs)+ sum(t.sat_hrs)+ sum(t.sun_hrs),2) as SUMMATION ")
				.replaceAll(":whereClause",
						" t.WEEKENDING_DATE > '" + startWeek + "' and t.WEEKENDING_DATE < '" + endWeek + "' "
								+ (userId == null ? " " : " and t.user_id =" + userId)
								+ (ncId == null ? " " : " and t.network_code_id =" + ncId)
								+ ((pillar != null && pillar != -1) ? " and nc.PROJECT_ID= " + pillar + " " : " "));

		String finalStr = " " + stQryStr + " union all " + midQryStr + " union all " + endQryStr + " ";

		if (year < Calendar.getInstance().getWeekYear()) {
			finalStr = finalStr.replaceAll("PTS_USER_TIMESHEET", "PTS_USER_TIMESHEET_2019");
		}
		// System.out.println(finalStr);
		Query query = getSession().createSQLQuery(finalStr).addScalar("TFSEpic", new LongType())
				.addScalar("RELEASETYPE", new StringType()).addScalar("IMPLEMENTATIONDATE", new DateType())
				.addScalar("PROJECT", new StringType()).addScalar("SUPERVISORNAME", new StringType())
				.addScalar("STATUS", new StringType()).addScalar("TOTALCAPACITY", new DoubleType())
				.addScalar("YEAR", new StringType()).addScalar("MONTH", new StringType())
				.addScalar("NETWORKCODE", new StringType()).addScalar("SUMMATION", new DoubleType());

		List<UserCapacity> tmpUserCapacity = query.setResultTransformer(Transformers.aliasToBean(UserCapacity.class))
				.list();

		userCapacity.addAll(tmpUserCapacity);
		getSession().flush();
		getSession().clear();

	}

	public String getPassword(String currentUserName) {
		List data = getSession().createSQLQuery("select PASSWORD from PTS_USER where USERNAME =:usr")
				.setString("usr", currentUserName).list();
		if (data.size() > 0) {
			return data.get(0) + "";
		} else {
			return "";
		}
	}

	public List<com.egil.pts.modal.UserStableTeams> getUserStableTeams(Long userId) {
		Query query = getSession().createSQLQuery(
				"select ust.id 'id',st.teamName 'stableTeam',ust.contribution 'stableContribution' from PTS_USER_STABLE_TEAMS ust,PTS_STABLE_TEAMS st where st.id =ust.stable_team_id  and ust.user_id=:userId")
				.addScalar("id", new LongType()).addScalar("stableTeam", new StringType())
				.addScalar("stableContribution", new FloatType());
		query.setLong("userId", userId);
		return query.setResultTransformer(Transformers.aliasToBean(com.egil.pts.modal.UserStableTeams.class)).list();
	}

	public void saveUserLineManager(com.egil.pts.modal.User userObj) {
		getSession().createSQLQuery(" insert into PTS.PTS_USER_LINE_MANAGER (USER_ID,SUPERVISOR_ID) values(:id,:lm) ON DUPLICATE KEY update   SUPERVISOR_ID=:lm,USER_ID=:id")
				.setLong("id", userObj.getId()).setLong("lm", userObj.getLineManagerId()).executeUpdate();
	}

	public Integer getUserLineManager(Long id) {
		List data = getSession()
				.createSQLQuery("select  SUPERVISOR_ID from  PTS.PTS_USER_LINE_MANAGER where USER_ID=:id")
				.setLong("id", id) .list();
		if (data.size() > 0) {
			return (Integer) data.get(0) ;
		} else {
			return -1;
		}
	}

	public void editUserLineManager(com.egil.pts.modal.User userObj) {
		getSession().createSQLQuery(" insert into PTS.PTS_USER_LINE_MANAGER (USER_ID,SUPERVISOR_ID) values(:id,:lm) ON DUPLICATE KEY update   SUPERVISOR_ID=:lm,USER_ID=:id")
				.setLong("id", userObj.getUserId()).setLong("lm", userObj.getLineManagerId()).executeUpdate();
		
	}

}
