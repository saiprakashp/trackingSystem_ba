package com.egil.pts.dao.inventory.impl;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.hibernate.Query;
import org.hibernate.internal.util.StringHelper;
import org.hibernate.transform.Transformers;
import org.hibernate.type.DoubleType;
import org.hibernate.type.FloatType;
import org.hibernate.type.LongType;
import org.hibernate.type.StringType;
import org.springframework.stereotype.Repository;

import com.egil.pts.dao.common.hibernate.HibernateGenericDao;
import com.egil.pts.dao.domain.NetworkCodes;
import com.egil.pts.dao.inventory.NetworkCodesDao;
import com.egil.pts.modal.Pagination;
import com.egil.pts.modal.ReleaseTrainDetails;
import com.egil.pts.modal.ResourceEffort;
import com.egil.pts.modal.SearchSortContainer;
import com.egil.pts.modal.StableTeams;

@Repository("networkCodesDao")
public class HibernateNetworkCodesDao extends HibernateGenericDao<NetworkCodes, Long> implements NetworkCodesDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<NetworkCodes> getNetworkCodes(Pagination pagination, SearchSortContainer searchSortObj,
			String searchValue, Set<Long> idList, List<Integer> stableList) throws Throwable {
		StringBuilder queryString = new StringBuilder();
		queryString.append("from NetworkCodes where status !='DELETED' ");
		if (idList != null && idList.size() > 0) {
			queryString.append(" and id not in (:idList) ");
		}

		if (StringHelper.isNotEmpty(searchValue)) {
			queryString.append(" and (lower(networkCodeId) like '%" + searchValue.toLowerCase()
					+ "%'  or lower(networkCodeName) like '%" + searchValue.toLowerCase()
					+ "%'  or lower(releaseId) like '%" + searchValue.toLowerCase()
					+ "%'  or lower(releaseName) like '%" + searchValue.toLowerCase() + "%') ");
		}

		if (searchSortObj != null) {
			if (searchSortObj.getSearchField() != null && !searchSortObj.getSearchField().equals("")
					&& searchSortObj.getSearchString() != null && !searchSortObj.getSearchString().equals("")) {
				if (searchSortObj.getSearchField().equalsIgnoreCase("networkCodeId")) {
					queryString.append(" and networkCodeId like '%" + searchSortObj.getSearchString() + "%' ");
				} else if (searchSortObj.getSearchField().equalsIgnoreCase("networkCode")) {
					queryString.append(" and networkCodeName like '% " + searchSortObj.getSearchString() + "%' ");
				}
			}
			if (searchSortObj.getTFSEpic() != null && !searchSortObj.getTFSEpic().equalsIgnoreCase("")) {
				queryString.append(" and TFSEpic like '%" + searchSortObj.getTFSEpic() + "%'");
			}
			if (searchSortObj.isSearchByStable()) {
				queryString.append(" and stableTeam.id in (:stables)  ");
			}

			if (searchSortObj.getSearchNetworkId() != null
					&& !searchSortObj.getSearchNetworkId().equalsIgnoreCase("")) {
				queryString.append(" and (networkCodeName like '%" + searchSortObj.getSearchNetworkId()
						+ "%' or networkCodeId like '%" + searchSortObj.getSearchNetworkId() + "%' ) ");
			}

			if (searchSortObj.getSearchReleaseId() != null
					&& !searchSortObj.getSearchReleaseId().equalsIgnoreCase("")) {
				queryString.append(" and releaseId like '%" + searchSortObj.getSearchReleaseId() + "%'");
			}

			if (searchSortObj.getSearchReleaseName() != null
					&& !searchSortObj.getSearchReleaseName().equalsIgnoreCase("")) {
				queryString.append(" and releaseName like '%" + searchSortObj.getSearchReleaseName() + "%'");
			}

			if (searchSortObj.getSearchPM() != null && !searchSortObj.getSearchPM().equalsIgnoreCase("")) {
				queryString
						.append(" and projectManager.personalInfo.name like '%" + searchSortObj.getSearchPM() + "%'");
			}

			if (searchSortObj.getSearchStatus() != null && !searchSortObj.getSearchStatus().equalsIgnoreCase("")) {
				queryString.append(" and status like '%" + searchSortObj.getSearchStatus() + "%'");
			}

			if (searchSortObj.getSidx() != null && !searchSortObj.getSidx().equals("")
					&& searchSortObj.getSord() != null && !searchSortObj.getSord().equals("")) {
				if (searchSortObj.getSidx().equalsIgnoreCase("releaseId")) {
					queryString.append(" order by releaseId " + searchSortObj.getSord());
				} else if (searchSortObj.getSidx().equalsIgnoreCase("releaseName")) {
					queryString.append(" order by releaseName " + searchSortObj.getSord());
				} else if (searchSortObj.getSidx().equalsIgnoreCase("pillarName")) {
					queryString.append(" order by project.pillar.pillarName " + searchSortObj.getSord());
				} else if (searchSortObj.getSidx().equalsIgnoreCase("projectName")) {
					queryString.append(" order by project.projectName " + searchSortObj.getSord());
				} else if (searchSortObj.getSidx().equalsIgnoreCase("createdBy")) {
					queryString.append(" order by createdBy " + searchSortObj.getSord());
				} else if (searchSortObj.getSidx().equalsIgnoreCase("createdDate")) {
					queryString.append(" order by createdDate " + searchSortObj.getSord());
				} else if (searchSortObj.getSidx().equalsIgnoreCase("updatedBy")) {
					queryString.append(" order by updatedBy " + searchSortObj.getSord());
				} else if (searchSortObj.getSidx().equalsIgnoreCase("updatedDate")) {
					queryString.append(" order by updatedDate " + searchSortObj.getSord());
				} else {
					queryString.append(" order by createdDate desc ");
				}
			} else {
				queryString.append(" order by releaseName asc  ");
			}
		} else {
			queryString.append(" order by releaseName asc  ");
		}
		Query query = getSession().createQuery(queryString.toString());
		if (idList != null && idList.size() > 0) {
			query.setParameterList("idList", idList);
		}
		if (pagination != null) {
			if (pagination.getOffset() > 0)
				query.setFirstResult(pagination.getOffset());
			if (pagination.getSize() > 0)
				query.setMaxResults(pagination.getSize());
		}
		if (stableList!=null && searchSortObj.isSearchByStable()) {

			List<Long> d = new ArrayList<Long>();
			for (Integer i : stableList) {
				d.add(i.longValue());
			}
			query.setParameterList("stables", d);
		}
		return query.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<NetworkCodes> getResourceNetworkCodes(Pagination pagination, SearchSortContainer searchSortObj,
			String searchValue, Set<Long> idList) throws Throwable {

		if (StringHelper.isNotEmpty(searchValue)) {
			searchValue = "%" + searchValue.trim().toLowerCase() + "%";
		} else {
			searchValue = "%";
		}

		StringBuilder queryString = new StringBuilder();
		queryString.append("select un.networkCodes from UserNetworkCodes un where"
				+ " un.user.id = :id and un.networkCodes.status !='DELETED' ");

		if (idList != null && idList.size() > 0) {
			queryString.append(" and un.networkCodes.id not in (:idList) ");
		}

		if (StringHelper.isNotEmpty(searchValue)) {
			queryString.append(" and (lower(un.networkCodes.networkCodeId) like '%" + searchValue.toLowerCase()
					+ "%'  or lower(un.networkCodes.networkCodeName) like '%" + searchValue.toLowerCase()
					+ "%'  or lower(un.networkCodes.releaseId) like '%" + searchValue.toLowerCase()
					+ "%'  or lower(un.networkCodes.releaseName) like '%" + searchValue.toLowerCase() + "%') ");
		}

		if (searchSortObj != null) {
			if (searchSortObj.getSearchField() != null && !searchSortObj.getSearchField().equals("")
					&& searchSortObj.getSearchString() != null && !searchSortObj.getSearchString().equals("")) {
				if (searchSortObj.getSearchField().equalsIgnoreCase("networkCodeId")) {
					queryString.append(
							" and un.networkCodes.networkCodeId like '%" + searchSortObj.getSearchString() + "%' ");
				} else if (searchSortObj.getSearchField().equalsIgnoreCase("networkCode")) {
					queryString.append(
							" and un.networkCodes.networkCodeName like '% " + searchSortObj.getSearchString() + "%' ");
				}
			}

			if (searchSortObj.getSearchNetworkId() != null
					&& !searchSortObj.getSearchNetworkId().equalsIgnoreCase("")) {
				queryString.append(" and (un.networkCodes.networkCodeName like '%" + searchSortObj.getSearchNetworkId()
						+ "%' or un.networkCodes.networkCodeId like '%" + searchSortObj.getSearchNetworkId() + "%' ) ");
			}

			if (searchSortObj.getSearchReleaseId() != null
					&& !searchSortObj.getSearchReleaseId().equalsIgnoreCase("")) {
				queryString
						.append(" and un.networkCodes.releaseId like '%" + searchSortObj.getSearchReleaseId() + "%'");
			}

			if (searchSortObj.getSearchReleaseName() != null
					&& !searchSortObj.getSearchReleaseName().equalsIgnoreCase("")) {
				queryString.append(
						" and un.networkCodes.releaseName like '%" + searchSortObj.getSearchReleaseName() + "%'");
			}

			if (searchSortObj.getSearchPM() != null && !searchSortObj.getSearchPM().equalsIgnoreCase("")) {
				queryString.append(" and un.networkCodes.projectManager.personalInfo.name like '%"
						+ searchSortObj.getSearchPM() + "%'");
			}

			if (searchSortObj.getSearchStatus() != null && !searchSortObj.getSearchStatus().equalsIgnoreCase("")) {
				queryString.append(" and un.networkCodes.status like '%" + searchSortObj.getSearchStatus() + "%'");
			}
			if (searchSortObj.getTFSEpic() != null && !searchSortObj.getTFSEpic().equalsIgnoreCase("")) {
				queryString.append(" and un.networkCodes.TFSEpic like '%" + searchSortObj.getTFSEpic() + "%'");
			}
			if (searchSortObj.getSidx() != null && !searchSortObj.getSidx().equals("")
					&& searchSortObj.getSord() != null && !searchSortObj.getSord().equals("")) {
				if (searchSortObj.getSidx().equalsIgnoreCase("releaseName")) {
					queryString.append(" order by un.networkCodes.releaseName " + searchSortObj.getSord());
				} else if (searchSortObj.getSidx().equalsIgnoreCase("releaseId")) {
					queryString.append(" order by un.networkCodes.releaseId " + searchSortObj.getSord());
				} else if (searchSortObj.getSidx().equalsIgnoreCase("pillarName")) {
					queryString
							.append(" order by un.networkCodes.project.pillar.pillarName " + searchSortObj.getSord());
				} else if (searchSortObj.getSidx().equalsIgnoreCase("projectName")) {
					queryString.append(" order by un.networkCodes.project.projectName " + searchSortObj.getSord());
				} else if (searchSortObj.getSidx().equalsIgnoreCase("createdBy")) {
					queryString.append(" order by un.networkCodes.createdBy " + searchSortObj.getSord());
				} else if (searchSortObj.getSidx().equalsIgnoreCase("createdDate")) {
					queryString.append(" order by un.networkCodes.createdDate " + searchSortObj.getSord());
				} else if (searchSortObj.getSidx().equalsIgnoreCase("updatedBy")) {
					queryString.append(" order by un.networkCodes.updatedBy " + searchSortObj.getSord());
				} else if (searchSortObj.getSidx().equalsIgnoreCase("updatedDate")) {
					queryString.append(" order by un.networkCodes.updatedDate " + searchSortObj.getSord());
				} else {
					queryString.append(" order by un.networkCodes.createdDate desc ");
				}
			} else {
				queryString.append(" order by un.networkCodes.createdDate desc ");
			}
		} else {
			queryString.append(" order by un.networkCodes.createdDate desc  ");
		}
		Query query = getSession().createQuery(queryString.toString());
		query.setLong("id", Integer.parseInt(searchSortObj.getLoggedInId()));
		if (idList != null && idList.size() > 0) {
			query.setParameterList("idList", idList);
		}
		if (pagination != null) {
			if (pagination.getOffset() > 0)
				query.setFirstResult(pagination.getOffset());
			if (pagination.getSize() > 0)
				query.setMaxResults(pagination.getSize());
		}
		return query.list();
	}

	@Override
	public Integer deleteNetworkCodes(List<Long> networkCodeIdList) throws Throwable {
		Query query = getSession().createQuery("update NetworkCodes nc set nc.status='DELETED' where nc.id in (:id)");
		query.setParameterList("id", networkCodeIdList);
		return query.executeUpdate();
	}

	@Override
	public int getNetworkCodesCount(SearchSortContainer searchSortObj) throws Throwable {
		int count = 0;
		StringBuilder queryString = new StringBuilder();
		queryString.append("select count (*) from NetworkCodes where status !='DELETED' ");
		if (searchSortObj != null) {
			if (searchSortObj.getSearchField() != null && !searchSortObj.getSearchField().equals("")
					&& searchSortObj.getSearchString() != null && !searchSortObj.getSearchString().equals("")) {
				if (searchSortObj.getSearchField().equalsIgnoreCase("networkCodeId")) {
					queryString.append(" and networkCodeId = " + searchSortObj.getSearchString());
				} else if (searchSortObj.getSearchField().equalsIgnoreCase("networkCode")) {
					queryString.append(" and networkCodeName = " + searchSortObj.getSearchString());
				}
			}

			if (searchSortObj.getSearchNetworkId() != null
					&& !searchSortObj.getSearchNetworkId().equalsIgnoreCase("")) {
				queryString.append(" and (networkCodeName like '%" + searchSortObj.getSearchNetworkId()
						+ "%' or networkCodeId like '%" + searchSortObj.getSearchNetworkId() + "%' ) ");
			}

			if (searchSortObj.getSearchReleaseId() != null
					&& !searchSortObj.getSearchReleaseId().equalsIgnoreCase("")) {
				queryString.append(" and releaseId like '%" + searchSortObj.getSearchReleaseId() + "%'");
			}

			if (searchSortObj.getSearchReleaseName() != null
					&& !searchSortObj.getSearchReleaseName().equalsIgnoreCase("")) {
				queryString.append(" and releaseName like '%" + searchSortObj.getSearchReleaseName() + "%'");
			}

			if (searchSortObj.getSearchStatus() != null && !searchSortObj.getSearchStatus().equalsIgnoreCase("")) {
				queryString.append(" and status like '%" + searchSortObj.getSearchStatus() + "%'");
			}

			if (searchSortObj.getSearchPM() != null && !searchSortObj.getSearchPM().equalsIgnoreCase("")) {
				queryString
						.append(" and projectManager.personalInfo.name like '%" + searchSortObj.getSearchPM() + "%'");
			}
			if (searchSortObj.getTFSEpic() != null && !searchSortObj.getTFSEpic().equalsIgnoreCase("")) {
				queryString.append(" and TFSEpic like '%" + searchSortObj.getTFSEpic() + "%'");
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
	public int getResourceNetworkCodesCount(SearchSortContainer searchSortObj) throws Throwable {
		int count = 0;
		StringBuilder queryString = new StringBuilder();
		queryString.append("select count(*) from UserNetworkCodes un where"
				+ " un.user.id = :id and un.networkCodes.status !='DELETED' ");
		if (searchSortObj != null) {
			if (searchSortObj.getSearchField() != null && !searchSortObj.getSearchField().equals("")
					&& searchSortObj.getSearchString() != null && !searchSortObj.getSearchString().equals("")) {
				if (searchSortObj.getSearchField().equalsIgnoreCase("networkCodeId")) {
					queryString.append(" and un.networkCodes.networkCodeId = " + searchSortObj.getSearchString());
				} else if (searchSortObj.getSearchField().equalsIgnoreCase("networkCode")) {
					queryString.append(" and un.networkCodes.networkCodeName = " + searchSortObj.getSearchString());
				}
			}

			if (searchSortObj.getSearchNetworkId() != null
					&& !searchSortObj.getSearchNetworkId().equalsIgnoreCase("")) {
				queryString.append(" and (un.networkCodes.networkCodeName like '%" + searchSortObj.getSearchNetworkId()
						+ "%' or un.networkCodes.networkCodeId like '%" + searchSortObj.getSearchNetworkId() + "%' ) ");
			}

			if (searchSortObj.getSearchReleaseId() != null
					&& !searchSortObj.getSearchReleaseId().equalsIgnoreCase("")) {
				queryString
						.append(" and un.networkCodes.releaseId like '%" + searchSortObj.getSearchReleaseId() + "%'");
			}

			if (searchSortObj.getSearchReleaseName() != null
					&& !searchSortObj.getSearchReleaseName().equalsIgnoreCase("")) {
				queryString.append(
						" and un.networkCodes.releaseName like '%" + searchSortObj.getSearchReleaseName() + "%'");
			}

			if (searchSortObj.getSearchStatus() != null && !searchSortObj.getSearchStatus().equalsIgnoreCase("")) {
				queryString.append(" and un.networkCodes.status like '%" + searchSortObj.getSearchStatus() + "%'");
			}

			if (searchSortObj.getSearchPM() != null && !searchSortObj.getSearchPM().equalsIgnoreCase("")) {
				queryString.append(" and un.networkCodes.projectManager.personalInfo.name like '%"
						+ searchSortObj.getSearchPM() + "%'");
			}
			if (searchSortObj.getTFSEpic() != null && !searchSortObj.getTFSEpic().equalsIgnoreCase("")) {
				queryString.append(" and un.networkCodes.TFSEpic like '%" + searchSortObj.getTFSEpic() + "%'");
			}

		}
		Query query = getSession().createQuery(queryString.toString());
		query.setLong("id", Integer.parseInt(searchSortObj.getLoggedInId()));
		Long obj = (Long) query.uniqueResult();
		if (obj != null) {
			count = obj.intValue();
		}
		return count;
	}

	@Override
	public NetworkCodes getNetworkCode(Long id) throws Throwable {
		Query query = getSession().createQuery("from NetworkCodes nc where nc.id = :id");
		query.setLong("id", id);
		return (NetworkCodes) query.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ReleaseTrainDetails> getReleaseTrainDetails(Long userId, Long platformId, Long applicationId,
			Long projectId, Long projectManager, String projectStageCol, Pagination pagination, long stableTeamId) {
		String queryString = "";

		int count = 0;
		if (userId != null) {
			String tmpStr = "select count(*) from PTS_USER_PLATFORMS where user_id=:userId and platform_id in (select id from PTS_PILLAR where pillar_name='Admin') and contribution > 0";

			Query querytmp = getSession().createSQLQuery(tmpStr);
			if (userId != null) {
				querytmp.setLong("userId", userId);
			}

			BigInteger obj = (BigInteger) querytmp.uniqueResult();
			if (obj != null) {
				count = obj.intValue();
			}
			List<Long> userIdList = new ArrayList<Long>();
			if (count > 0) {
				queryString = "select CASE WHEN Phase = 'Feasibility' then concat(Project,'~~~~~',case when comments is null then '' else comments end, '~~~~~',case when description is null then '' else description end,'____',CASE WHEN PROJECT_PHASE_COLOR IS NULL THEN 'black' ELSE PROJECT_PHASE_COLOR END) else null end as feasibility, "
						+ "CASE WHEN Phase = 'Development' then concat(Project,'~~~~~',case when comments is null then '' else comments end, '~~~~~',case when description is null then '' else description end,'____',CASE WHEN PROJECT_PHASE_COLOR IS NULL THEN 'black' ELSE PROJECT_PHASE_COLOR END) else null end as development, "
						+ "CASE WHEN Phase = 'System Test' then concat(Project,'~~~~~',case when comments is null then '' else comments end, '~~~~~',case when description is null then '' else description end,'____',CASE WHEN PROJECT_PHASE_COLOR IS NULL THEN 'black' ELSE PROJECT_PHASE_COLOR END) else null end as systemTest, "
						+ "CASE WHEN Phase = 'Deployment' then concat(Project,'~~~~~',case when comments is null then '' else comments end, '~~~~~',case when description is null then '' else description end,'____',CASE WHEN PROJECT_PHASE_COLOR IS NULL THEN 'black' ELSE PROJECT_PHASE_COLOR END) else null end as deployment, "
						+ "CASE WHEN Phase = 'Hold' then concat(Project,'~~~~~',case when comments is null then '' else comments end, '~~~~~',case when description is null then '' else description end,'____',CASE WHEN PROJECT_PHASE_COLOR IS NULL THEN 'black' ELSE PROJECT_PHASE_COLOR END) else null end as hold, "
						+ "CASE WHEN Phase = 'Completed' then concat(Project,'~~~~~',case when comments is null then '' else comments end, '~~~~~',case when description is null then '' else description end,'____',CASE WHEN PROJECT_PHASE_COLOR IS NULL THEN 'black' ELSE PROJECT_PHASE_COLOR END) else null end as completed "
						+ " ,CASE WHEN Phase = 'None' THEN CONCAT(Project,'~~~~~', CASE WHEN comments IS NULL THEN '' ELSE comments END, '~~~~~', CASE WHEN description IS NULL THEN '' ELSE description END,'____', CASE WHEN PROJECT_PHASE_COLOR IS NULL THEN 'black' ELSE PROJECT_PHASE_COLOR END) ELSE NULL END AS none "
						+ " from "
						+ " (select project_stage Phase,n.PROJECT_PHASE_COLOR, concat(n.id, '|', n.project_stage, '|', n.release_id, ' - ', n.release_name, ', ', ifnull(DATE_FORMAT(n.impl_planned_date, '%d-%b-%Y'),'')) Project, n.comments, n.description "
						+ " from PTS_NETWORK_CODES n ,PTS_PROJECT p where "
						+ " n.project_id=p.id and project_stage <> '' and project_stage <> 'Completed' and n.status not in ('Completed','Implemented', 'In-Active') &condStmt group by n.id) tmp";
			} else {

				if (userId != null && userId != 0l) {
					Query query = getSession().createSQLQuery("CALL user_id_heirarchy_proc(:id)");
					query.setParameter("id", userId);
					query.executeUpdate();

					Query tmpQry = getSession().createSQLQuery("SELECT node FROM _result");
					userIdList = tmpQry.list();
				}
				queryString = "select CASE WHEN Phase = 'Feasibility' then concat(Project,'~~~~~',case when comments is null then '' else comments end, '~~~~~',case when description is null then '' else description end,'____',CASE WHEN PROJECT_PHASE_COLOR IS NULL THEN 'black' ELSE PROJECT_PHASE_COLOR END) else null end as feasibility, "
						+ "CASE WHEN Phase = 'Development' then concat(Project,'~~~~~',case when comments is null then '' else comments end, '~~~~~',case when description is null then '' else description end,'____',CASE WHEN PROJECT_PHASE_COLOR IS NULL THEN 'black' ELSE PROJECT_PHASE_COLOR END) else null end as development, "
						+ "CASE WHEN Phase = 'System Test' then concat(Project,'~~~~~',case when comments is null then '' else comments end, '~~~~~',case when description is null then '' else description end,'____',CASE WHEN PROJECT_PHASE_COLOR IS NULL THEN 'black' ELSE PROJECT_PHASE_COLOR END) else null end as systemTest, "
						+ "CASE WHEN Phase = 'Deployment' then concat(Project,'~~~~~',case when comments is null then '' else comments end, '~~~~~',case when description is null then '' else description end,'____',CASE WHEN PROJECT_PHASE_COLOR IS NULL THEN 'black' ELSE PROJECT_PHASE_COLOR END) else null end as deployment, "
						+ "CASE WHEN Phase = 'Hold' then concat(Project,'~~~~~',case when comments is null then '' else comments end, '~~~~~',case when description is null then '' else description end,'____',CASE WHEN PROJECT_PHASE_COLOR IS NULL THEN 'black' ELSE PROJECT_PHASE_COLOR END) else null end as hold, "
						+ "CASE WHEN Phase = 'Completed' then concat(Project,'~~~~~',case when comments is null then '' else comments end, '~~~~~',case when description is null then '' else description end,'____',CASE WHEN PROJECT_PHASE_COLOR IS NULL THEN 'black' ELSE PROJECT_PHASE_COLOR END) else null end as completed "
						+ ", CASE WHEN Phase = 'None' THEN CONCAT(Project,'~~~~~', CASE WHEN comments IS NULL THEN '' ELSE comments END, '~~~~~', CASE WHEN description IS NULL THEN '' ELSE description END,'____', CASE WHEN PROJECT_PHASE_COLOR IS NULL THEN 'black' ELSE PROJECT_PHASE_COLOR END) ELSE NULL END AS none"
						+ " from "
						+ " (select project_stage Phase,n.PROJECT_PHASE_COLOR, concat(n.id, '|', n.project_stage, '|', n.release_id, ' - ', n.release_name, ', ', ifnull(DATE_FORMAT(n.impl_planned_date, '%d-%b-%Y'),'')) Project, n.comments, n.description "
						+ " from PTS_NETWORK_CODES n, PTS_USER_NETWORK_CODES un, PTS_PROJECT p, PTS_USER_PLATFORMS up &userAppTbl where "
						+ " n.project_id=p.id and p.pillar_id=up.platform_id &contributionStmt and up.user_id=un.user_id "
						+ " and project_stage <> '' and project_stage <> 'Completed' and n.status not in ('Completed','Implemented', 'In-Active') &stableTeams  and n.project_id=p.id and un.network_code_id=n.id &condStmt group by n.id) tmp";

				queryString = queryString.replaceAll("&userAppTbl", ", PTS_USER_APPS ua ");
				if (userIdList != null && userIdList.size() > 0) {
					queryString = queryString.replaceAll("&condStmt", " and un.user_id in (:userId) &condStmt ");
				} else {
					queryString = queryString.replaceAll("&condStmt", " and un.user_id = :userId &condStmt ");
				}
				queryString = queryString.replaceAll("&contributionStmt",
						" and up.user_id=un.user_id and up.contribution > 0 and up.user_id=ua.user_id and p.id=ua.project_id ");
			}

			if (platformId != null) {
				queryString = queryString.replaceAll("&condStmt", " and p.pillar_id = :platformId &condStmt ");
			}

			if (applicationId != null) {
				queryString = queryString.replaceAll("&condStmt", " and p.id = :applicationId &condStmt ");
			}

			if (projectId != null) {
				queryString = queryString.replaceAll("&condStmt", " and n.id = :projectId &condStmt ");
			}
			if (projectManager != null) {
				queryString = queryString.replaceAll("&condStmt",
						" and (n.product_manager = :projectManager or n.project_manager = :projectManager) &condStmt ");
			}

			if (StringHelper.isNotEmpty(projectStageCol)) {
				queryString = queryString.replaceAll("&condStmt",
						" and n.PROJECT_PHASE_COLOR = :projectStage &condStmt ");
			}

			if (queryString.contains("&condStmt")) {
				queryString = queryString.replaceAll("&condStmt", " ");
			}

			if (stableTeamId > 0) {
				queryString = queryString.replaceAll("&stableTeams", "and n.stable_teams=:stableTeams");
			} else {
				queryString = queryString.replaceAll("&stableTeams", " ");
			}

			Query query = getSession().createSQLQuery(queryString).addScalar("feasibility", new StringType())
					.addScalar("development", new StringType()).addScalar("systemTest", new StringType())
					.addScalar("deployment", new StringType()).addScalar("hold", new StringType())
					.addScalar("completed", new StringType()).addScalar("none", new StringType());

			if (count == 0 && userId != null) {
				if (userIdList != null && userIdList.size() > 0) {
					query.setParameterList("userId", userIdList);
				} else {
					query.setLong("userId", userId);
				}
			}
			if (platformId != null) {
				query.setLong("platformId", platformId);
			}
			if (applicationId != null) {
				query.setLong("applicationId", applicationId);
			}
			if (projectId != null) {
				query.setLong("projectId", projectId);
			}
			if (projectManager != null) {
				query.setLong("projectManager", projectManager);
			}

			if (StringHelper.isNotEmpty(projectStageCol)) {
				query.setString("projectStage", projectStageCol);
			}
			if (stableTeamId > 0) {
				query.setLong("stableTeams", stableTeamId);
			}
			if (pagination != null) {
				if (pagination.getOffset() > 0)
					query.setFirstResult(pagination.getOffset());
				if (pagination.getSize() > 0)
					query.setMaxResults(pagination.getSize());
			}

			List<ReleaseTrainDetails> releaseTrainDetailList = query
					.setResultTransformer(Transformers.aliasToBean(ReleaseTrainDetails.class)).list();
			return releaseTrainDetailList;
		} else {
			return null;
		}
	}

	@Override
	public int getReleaseTrainDetailsCount(Long userId, Long platformId, Long applicationId, Long projectId,
			Long projectManager, String projectStage) {
		int count = 0;
		String queryString = "select count(*) from (select CASE WHEN Phase = 'Feasibility' then Project else null end as feasibility, "
				+ " CASE WHEN Phase = 'Development' then Project else null end as development, "
				+ " CASE WHEN Phase = 'System Test' then Project else null end as systemTest, "
				+ " CASE WHEN Phase = 'Deployment' then Project else null end as deployment, "
				+ " CASE WHEN Phase = 'Hold' then Project else null end as hold, "
				+ " CASE WHEN Phase = 'Completed' then Project else null end as completed " + " from "
				+ " (select project_stage Phase, concat(release_id, ' - ', release_name, ', ', ifnull(n.impl_planned_date,'')) Project "
				+ " from PTS_NETWORK_CODES n, PTS_USER_NETWORK_CODES un, PTS_PROJECT p, PTS_USER_PLATFORMS up where "
				+ " n.project_id=p.id and p.pillar_id=up.platform_id &contributionStmt and up.user_id=un.user_id "
				+ " and project_stage <> '' and project_stage <> 'Completed' and n.status not in ('Completed','Implemented', 'In-Active')   and n.project_id=p.id and un.network_code_id=n.id &condStmt group by n.id) tmp) tmp1";

		if (userId != null) {
			String tmpStr = "select count(*) from PTS_USER_PLATFORMS where user_id=:userId and platform_id in (select id from PTS_PILLAR where pillar_name='Admin') and contribution > 0";

			Query querytmp = getSession().createSQLQuery(tmpStr);
			if (userId != null) {
				querytmp.setLong("userId", userId);
			}
			int counttmp = 0;
			BigInteger obj = (BigInteger) querytmp.uniqueResult();
			if (obj != null) {
				counttmp = obj.intValue();
			}
			if (counttmp > 0) {
				queryString = queryString.replaceAll("&contributionStmt", " ");
			} else {
				queryString = queryString.replaceAll("&contributionStmt", " and up.contribution > 0 ");
			}
			queryString = queryString.replaceAll("&condStmt", " and un.user_id = :userId &condStmt ");
		}

		if (platformId != null) {
			queryString = queryString.replaceAll("&condStmt", " and p.pillar_id = :platformId &condStmt ");
		}

		if (applicationId != null) {
			queryString = queryString.replaceAll("&condStmt", " and p.id = :applicationId &condStmt ");
		}

		if (projectId != null) {
			queryString = queryString.replaceAll("&condStmt", " and n.id = :projectId &condStmt ");
		}

		if (projectManager != null) {
			queryString = queryString.replaceAll("&condStmt",
					" and (n.product_manager = :projectManager or n.project_manager = :projectManager) &condStmt ");
		}

		if (projectStage != null) {
			queryString = queryString.replaceAll("&condStmt", " and n.project_stage = :projectStage &condStmt ");
		}

		if (queryString.contains("&condStmt")) {
			queryString = queryString.replaceAll("&condStmt", " ");
		}

		Query query = getSession().createSQLQuery(queryString);
		if (userId != null) {
			query.setLong("userId", userId);
		}
		if (platformId != null) {
			query.setLong("platformId", platformId);
		}
		if (applicationId != null) {
			query.setLong("applicationId", applicationId);
		}
		if (projectId != null) {
			query.setLong("projectId", projectId);
		}
		if (projectManager != null) {
			query.setLong("projectManager", projectManager);
		}
		if (projectStage != null) {
			query.setString("projectStage", projectStage);
		}

		BigInteger obj = (BigInteger) query.uniqueResult();
		if (obj != null) {
			count = obj.intValue();
		}
		return count;
	}

	@Override
	public int updatePMProjectPhase(com.egil.pts.modal.NetworkCodes networkCodes, String comments) throws Throwable {
		NetworkCodes daoNetworkCodes = getNetworkCode(networkCodes.getId());
		getSession().flush();
		getSession().clear();
		if (comments != null && !comments.isEmpty())
			daoNetworkCodes.setComments(comments);

		daoNetworkCodes.setUpdatedDate(new Date());

		daoNetworkCodes.setUpdatedBy(networkCodes.getUpdatedBy());
		if (networkCodes.getProjectStage() != null && !networkCodes.getProjectStage().isEmpty())
			daoNetworkCodes.setProjectStage(networkCodes.getProjectStage());

		if (networkCodes.getDescription() != null && !networkCodes.getDescription().isEmpty())
			daoNetworkCodes.setDescription(networkCodes.getDescription());

		if (networkCodes.getLocalDesignLOE() != null && networkCodes.getLocalDesignLOE() != -1)
			daoNetworkCodes.setLocalDesignLOE(networkCodes.getLocalDesignLOE());

		if (networkCodes.getLocalDevLOE() != null && networkCodes.getLocalDevLOE() != -1)
			daoNetworkCodes.setLocalDevLOE(networkCodes.getLocalDevLOE());

		if (networkCodes.getStableTeam() != null && networkCodes.getStableTeam() != -1) {
			com.egil.pts.dao.domain.StableTeams stableTeam = new com.egil.pts.dao.domain.StableTeams();
			stableTeam.setId(networkCodes.getStableTeam());
			daoNetworkCodes.setStableTeam(stableTeam);
		}

		if (networkCodes.getLocalTestLOE() != null && networkCodes.getLocalTestLOE() != -1)
			daoNetworkCodes.setLocalTestLOE(networkCodes.getLocalTestLOE());

		if (networkCodes.getLocalImplementationLOE() != null && networkCodes.getLocalImplementationLOE() != -1)
			daoNetworkCodes.setLocalImplementationLOE(networkCodes.getLocalImplementationLOE());

		if (networkCodes.getLocalProjectManagementLOE() != null && networkCodes.getLocalProjectManagementLOE() != -1)
			daoNetworkCodes.setLocalProjectManagementLOE(networkCodes.getLocalProjectManagementLOE());

		if (networkCodes.getLocalKitLOE() != null && networkCodes.getLocalKitLOE() != -1)
			daoNetworkCodes.setLocalKitLOE(networkCodes.getLocalKitLOE());

		if (networkCodes.getGlobalDesignLOE() != null && networkCodes.getGlobalDesignLOE() != -1)
			daoNetworkCodes.setGlobalDesignLOE(networkCodes.getGlobalDesignLOE());

		if (networkCodes.getGlobalDevLOE() != null && networkCodes.getGlobalDevLOE() != -1)
			daoNetworkCodes.setGlobalDevLOE(networkCodes.getGlobalDevLOE());

		if (networkCodes.getGlobalTestLOE() != null && networkCodes.getGlobalTestLOE() != -1)
			daoNetworkCodes.setGlobalTestLOE(networkCodes.getGlobalTestLOE());

		if (networkCodes.getGlobalImplementationLOE() != null && networkCodes.getGlobalImplementationLOE() != -1)
			daoNetworkCodes.setGlobalImplementationLOE(networkCodes.getGlobalImplementationLOE());

		if (networkCodes.getGlobalProjectManagementLOE() != null && networkCodes.getGlobalProjectManagementLOE() != -1)
			daoNetworkCodes.setGlobalProjectManagementLOE(networkCodes.getGlobalProjectManagementLOE());

		if (networkCodes.getGlobalKitLOE() != null && networkCodes.getGlobalKitLOE() != -1)
			daoNetworkCodes.setGlobalKitLOE(networkCodes.getGlobalKitLOE());
		if (networkCodes.getTFSEpic() != null)
			daoNetworkCodes.setTFSEpic(networkCodes.getTFSEpic());

		if (networkCodes.getTotalLOE() != null)
			daoNetworkCodes.setTotalLOE(networkCodes.getTotalLOE());

		if (networkCodes.getStatus() != null)
			daoNetworkCodes.setStatus(networkCodes.getStatus());

		daoNetworkCodes.setProjectStageCol(networkCodes.getProjectStageCol());
		update(daoNetworkCodes);

		return 1;
	}

	@Override
	public void updateProjectTFData(List<com.egil.pts.modal.NetworkCodes> projectData) {

		for (com.egil.pts.modal.NetworkCodes n : projectData) {
			StringBuffer sb = new StringBuffer("update PTS_NETWORK_CODES set   ");
			if (n.getReleaseId() != null) {
				sb.append("  RELEASE_ID= '" + n.getReleaseId() + "' ");
			}
			if (n.getReleaseName() != null) {
				sb.append(" , RELEASE_NAME= '" + n.getReleaseName() + "' ");
			}

			if (n.getStartDate() != null) {
				sb.append(" , START_DATE= :startdate  ");
			}
			if (n.getPlannedImplDate() != null) {
				sb.append(" , impl_planned_date= :implDate  ");
			}
			if (n.getTotalLOE() != null) {
				sb.append(" , totalLOE= " + n.getTotalLOE() + "  ");
			}
			sb.append(" ,UPDATED_DATE=CURRENT_DATE where TFSEpic= :tfs ");

			Query querytmp = getSession().createSQLQuery(sb.toString());

			if (n.getPlannedImplDate() != null) {
				querytmp.setDate("implDate", n.getPlannedImplDate());
			}
			if (n.getStartDate() != null) {
				querytmp.setDate("startdate", n.getStartDate());
			}
			if (n.getTotalLOE() != null) {
				sb.append("  totalLOE= '" + n.getTotalLOE() + "' ");
			}
			querytmp.setLong("tfs", Long.parseLong(n.getTFSEpic()));
			int x = querytmp.executeUpdate();
			getSession().flush();
			getSession().clear();
		}

	}

	@Override
	public List<ResourceEffort> getSupervisorNWEffort(Long supervisor, Long pillar, Long project, String reportType,
			Integer month) {
		String queryString = "SELECT n.TFSEpic 'tfsId',p.PROJECT_NAME PROJECT,(select name from PTS_USER where id=u.USER_ID ) 'resourceName',"
				+ "(select PILLAR_NAME FROM PTS_PILLAR WHERE id =(select PILLAR_ID FROM PTS_PROJECT WHERE id = n.PROJECT_ID)) PILLAR,"
				+ "(select name from pts_user where id= (select s.SUPERVISOR_ID from pts_user_supervisor s where USER_ID =u.USER_ID ))'supervisorName' ,"
				+ "n.RELEASE_TYPE TYPE, CONCAT(n.RELEASE_ID,n.RELEASE_NAME) 'networkCodeName' , n.impl_planned_date 'Implementation Date',"
				+ "(SELECT NAME FROM PTS_USER WHERE id=n.project_manager) 'supervisorName', n.`STATUS` , n.CREATED_DATE,"
				+ "(n.ORIGINAL_DESIGN_LOE +n.ORIGINAL_DEV_LOE	+n.ORIGINAL_TEST_LOE +n.ORIGINAL_PROJ_MGMT_LOE +n.ORIGINAL_IMPL_LOE) 'totalCapacity',"
				+ "ROUND((sum(IFNULL(mon_hrs,0))+sum(ifnull(tue_hrs,0))+sum(ifnull(wed_hrs,0))+sum(ifnull(thu_hrs,0))"
				+ "+sum(ifnull(fri_hrs,0))+sum(ifnull(sat_hrs,0))+sum(ifnull(sun_hrs,0))),2)  'totalhrs' , ( SELECT "
				+ " ROUND((sum(IFNULL(tm.mon_hrs,0))+sum(ifnull(tm.tue_hrs,0))+sum(ifnull(tm.wed_hrs,0))+ sum(ifnull(tm.thu_hrs,0))"
				+ " +sum(ifnull(tm.fri_hrs,0))+sum(ifnull(tm.sat_hrs,0))+sum(ifnull(tm.sun_hrs,0))),2)"
				+ " FROM PTS_USER_TIMESHEET_2019 tm WHERE   	date_format(tm.WEEKENDING_DATE ,'%Y')=2019 and tm.NETWORK_CODE_ID=n.id GROUP BY tm.NETWORK_CODE_ID ORDER BY tm.NETWORK_CODE_ID ) 'prevTotalHrs', "
				+ " ( select  round((sum(ifnull(ucp.JAN_CPTY,0)) + sum(ifnull(ucp.FEB_CPTY,0))  +  sum(ifnull(ucp.MAR_CPTY,0)) +  sum(ifnull(ucp.APR_CPTY,0)) "
				+ " +  sum(ifnull(ucp.MAY_CPTY,0))  + sum(ifnull(ucp.JUN_CPTY,0)) +  "
				+ " sum(ifnull(ucp.JUL_CPTY,0))  + sum(ifnull(ucp.AUG_CPTY,0))  +  sum(ifnull(ucp.SEP_CPTY,0))  + sum(ifnull(ucp.OCT_CPTY,0))  +   "
				+ "  sum(ifnull(ucp.NOV_CPTY,0)) + sum(ifnull(ucp.DEC_CPTY,0)) )) "
				+ "  from pts_user_capacity_planning   ucp where ucp.USER_ID =u.USER_ID  and ucp.NETWORK_CODE_ID =u.NETWORK_CODE_ID )'plannedCapacity' "
				+ " FROM PTS_NETWORK_CODES n ,PTS_USER_TIMESHEET u,PTS_PROJECT p,PTS_USER us where p.id= n.PROJECT_ID and us.status not in ('OffBoard', 'No Show', 'Deleted') and us.ID =u.USER_ID 	  &application   and n.`STATUS` NOT IN ('Cancelled','Implemented','IN_ACTIVE') AND u.NETWORK_CODE_ID=n.id &users GROUP by n.id,u.USER_ID ORDER BY n.id ";
		List<Long> userIdList = null;
		if (supervisor != null && supervisor != -1) {
			Query query = getSession().createSQLQuery("CALL user_id_heirarchy_proc(:id)");
			query.setParameter("id", supervisor);
			query.executeUpdate();

			Query tmpQry = getSession().createSQLQuery("SELECT node FROM _result");
			userIdList = tmpQry.list();
		}
		if (supervisor != null && supervisor != -1) {
			queryString = queryString.replaceAll("&users", " and u.USER_ID  in (:users) ");
		} else {
			queryString = queryString.replaceAll("&users", "   ");
		}

		if (pillar != null && pillar != -1) {
			queryString = queryString.replaceAll("&application", " and p.id  =:pid ");
		} else {
			queryString = queryString.replaceAll("&application", "   ");
		}

		Query query = getSession().createSQLQuery(queryString).addScalar("resourceName", new StringType())
				.addScalar("supervisorName", new StringType()).addScalar("networkCodeName", new StringType())
				.addScalar("plannedCapacity", new FloatType()).addScalar("totalCapacity", new FloatType())
				.addScalar("totalhrs", new FloatType()).addScalar("prevTotalHrs", new FloatType())
				.addScalar("tfsId", new FloatType());
		if (supervisor != null && supervisor != -1) {
			query.setParameterList("users", userIdList);
		}

		if (pillar != null && pillar != -1) {
			query.setLong("pid", pillar);
		}
		if (project != null && project != -1) {
			query.setLong("app", project);
		}

		List<ResourceEffort> releaseTrainDetailList = query
				.setResultTransformer(Transformers.aliasToBean(ResourceEffort.class)).list();
		return releaseTrainDetailList;
	}

	@Override
	public List<StableTeams> getStableTeams() {

		Query query = getSession().createSQLQuery(
				"select id,teamName 'teamName',project_id 'project',0.0 'value' from PTS_STABLE_TEAMS where status =1 order by teamName asc  ")
				.addScalar("project", new LongType()).addScalar("id", new LongType())
				.addScalar("value", new DoubleType()).addScalar("teamName", new StringType());
		return query.setResultTransformer(Transformers.aliasToBean(StableTeams.class)).list();
	}

	@Override
	public List<StableTeams> getStableTeamsByUser(Long user) {

		Query query = getSession().createSQLQuery(
				"select US.id,teamName 'teamName',project_id 'project',US.contribution 'value' from PTS_USER_STABLE_TEAMS US,PTS_STABLE_TEAMS S where S.status =1 and S.id=US.stable_team_id and US.user_id =:user order by  S.teamName asc  ")
				.addScalar("project", new LongType()).addScalar("id", new LongType())
				.addScalar("value", new DoubleType()).addScalar("teamName", new StringType());
		query.setLong("user", user);
		return query.setResultTransformer(Transformers.aliasToBean(StableTeams.class)).list();
	}

	@Override
	public void saveStableTeamsByUser(List<StableTeams> data) {
		for (StableTeams s : data) {
			getSession().createSQLQuery(" UPDATE PTS_USER_STABLE_TEAMS SET contribution=:contribution WHERE id=:id")
					.setDouble("contribution", s.getValue()).setLong("id", s.getId()).executeUpdate();
			getSession().flush();
		}

	}

	@Override
	public List<StableTeams> getStableTeamsForUser() {
		Query query = getSession().createSQLQuery(
				"select US.id,u.NAME 'name',u.USERNAME 'signum',teamName 'teamName',project_id 'project',US.contribution 'value' from PTS_USER_STABLE_TEAMS US,PTS_STABLE_TEAMS S,PTS_USER u where u.NAME not like '%Admin%' and US.contribution >0 and u.status not in ('OffBoard', 'No Show', 'Open', 'Selected', 'Deleted', 'Induction') and u.id=US.user_id and S.status =1 and S.id=US.stable_team_id   order by  US.user_id, S.teamName asc ")
				.addScalar("project", new LongType()).addScalar("id", new LongType())
				.addScalar("value", new DoubleType()).addScalar("teamName", new StringType())
				.addScalar("signum", new StringType()).addScalar("name", new StringType());
		return query.setResultTransformer(Transformers.aliasToBean(StableTeams.class)).list();
	}

	@Override
	public Long getStableTeamByNw(String release) {
		Query query = getSession()
				.createSQLQuery("select id  from PTS_STABLE_TEAMS where status =1 order by , 'order' asc");
		return (Long) query.list().get(0);
	}

	@Override
	public List<com.egil.pts.modal.NetworkCodes> getMislaniousNWCodes() {
		Query query = getSession().createSQLQuery(
				"select ID, RELEASE_ID 'releaseId',RELEASE_NAME 'releaseName',PROJECT_LEVEL  from PTS_NETWORK_CODES pnc where PROJECT_LEVEL ='ACCOUNT' and PILLAR_ID =12 and STATUS not in ('DELETED','Completed','COMPLETED','In-Active','Cancelled','Implemented')")
				.addScalar("id", new LongType()).addScalar("releaseName", new StringType())
				.addScalar("releaseId", new StringType());
		return query.setResultTransformer(Transformers.aliasToBean(com.egil.pts.modal.NetworkCodes.class)).list();
	}

	@Override
	public List<Integer> getStableTeams(Long userId) {
		Query query = getSession().createSQLQuery(
				"select stable_team_id  from PTS_USER_STABLE_TEAMS where  contribution>0 and user_id=" + userId);
		return query.list();
	}

	@Override
	public List<StableTeams> getNonStableTeamsForUser(long userId) {
		List<Long> userIdList = new ArrayList<Long>();
		String sql = "select U.NAME 'name',U.status ,U.USERNAME 'signum' from PTS_USER U where U.id not in (select distinct USER_ID from PTS_USER_STABLE_TEAMS s)  and U.id in (:userIds)  AND U.status not in ('OffBoard', 'No Show', 'Open', 'Selected', 'Deleted', 'Induction')";

		if (userId > 0) {
			Query query = getSession().createSQLQuery("CALL user_id_heirarchy_proc(:id)");
			query.setParameter("id", userId);
			query.executeUpdate();

			Query tmpQry = getSession().createSQLQuery("SELECT node FROM _result");
			userIdList = tmpQry.list();
		}
		if (userId > 0) {
			sql = sql.replaceAll(" and U.id in (:userIds) ", " ");
		}
		Query query = getSession().createSQLQuery(sql).addScalar("signum", new StringType())
				.addScalar("name", new StringType()).addScalar("status", new StringType());

		if (userId > 0)
			query.setParameterList("userIds", userIdList);
		return query.setResultTransformer(Transformers.aliasToBean(StableTeams.class)).list();
	}

	public List<com.egil.pts.modal.NetworkCodes> getAllUserEffort(String fromDate, String toDate) {
		String sql = "  select A.* from (SELECT u.username 'signum', u.name 'name',concat(n.RELEASE_ID,'-',n.RELEASE_NAME) 'releaseName', (sum(t.mon_hrs)+ sum(t.tue_hrs) + sum(t.wed_hrs) + sum(t.thu_hrs)+ sum(t.fri_hrs)+ sum(t.sat_hrs)+ sum(t.sun_hrs))'chargedHours',n.STATUS 'nwStatus',t.WEEKENDING_DATE 'weekDate',(select p.PROJECT_NAME from PTS_PROJECT p where p.id=n.PROJECT_ID) 'application',(select st.teamName from PTS_USER_STABLE_TEAMS us,PTS_STABLE_TEAMS st where st.id =us.stable_team_id and us.contribution >0 and us.user_id=u.id and n.stable_teams=st.id and n.stable_teams=us.stable_team_id) 'stableTeamName', (select us.contribution from PTS_USER_STABLE_TEAMS us,PTS_STABLE_TEAMS st where st.id =us.stable_team_id and us.contribution >0 and us.user_id=u.id and n.stable_teams=st.id and n.stable_teams=us.stable_team_id) 'stableTeam' from PTS_USER_TIMESHEET t ,PTS_USER u , PTS_NETWORK_CODES n where t.WEEKENDING_DATE > '2020-01-01' and u.id=t.USER_ID and u.status not in ('OffBoard', 'No Show', 'Open', 'Selected', 'Deleted', 'Induction') and n.id=t.NETWORK_CODE_ID  group by t.NETWORK_CODE_ID ,t.USER_ID ,t.WEEKENDING_DATE order by t.WEEKENDING_DATE desc ) A ";
		if (!fromDate.equalsIgnoreCase(toDate)) {
			sql = "  select A.* from (SELECT u.username 'signum', u.name 'name',concat(n.RELEASE_ID,'-',n.RELEASE_NAME) 'releaseName', (sum(t.mon_hrs)+ sum(t.tue_hrs) + sum(t.wed_hrs) + sum(t.thu_hrs)+ sum(t.fri_hrs)+ sum(t.sat_hrs)+ sum(t.sun_hrs))'chargedHours',n.STATUS 'nwStatus',t.WEEKENDING_DATE 'weekDate',(select p.PROJECT_NAME from PTS_PROJECT p where p.id=n.PROJECT_ID) 'application',(select st.teamName from PTS_USER_STABLE_TEAMS us,PTS_STABLE_TEAMS st where st.id =us.stable_team_id and us.contribution >0 and us.user_id=u.id and n.stable_teams=st.id and n.stable_teams=us.stable_team_id) 'stableTeamName', (select us.contribution from PTS_USER_STABLE_TEAMS us,PTS_STABLE_TEAMS st where st.id =us.stable_team_id and us.contribution >0 and us.user_id=u.id and n.stable_teams=st.id and n.stable_teams=us.stable_team_id) 'stableTeam' from PTS_USER_TIMESHEET t ,PTS_USER u , PTS_NETWORK_CODES n where t.WEEKENDING_DATE > '"
					+ fromDate + " ' and t.WEEKENDING_DATE <= '" + toDate
					+ " ' and u.id=t.USER_ID and u.status not in ('OffBoard', 'No Show', 'Open', 'Selected', 'Deleted', 'Induction') and n.id=t.NETWORK_CODE_ID  group by t.NETWORK_CODE_ID ,t.USER_ID ,t.WEEKENDING_DATE order by t.WEEKENDING_DATE desc ) A ";
		}

		Query query = getSession().createSQLQuery(sql).addScalar("signum", new StringType())
				.addScalar("name", new StringType()).addScalar("releaseName", new StringType())
				.addScalar("chargedHours", new DoubleType()).addScalar("nwStatus", new StringType())
				.addScalar("weekDate", new StringType()).addScalar("application", new StringType())
				.addScalar("stableTeamName", new StringType()).addScalar("stableTeam", new LongType());

		return query.setResultTransformer(Transformers.aliasToBean(com.egil.pts.modal.NetworkCodes.class)).list();
	}

	public List<com.egil.pts.modal.NetworkCodes> getUnchargedUserEffort(long userId) {
		List<Long> userIdList = new ArrayList<Long>();
		if (userId > 0) {
			Query query = getSession().createSQLQuery("CALL user_id_heirarchy_proc(:id)");
			query.setParameter("id", userId);
			query.executeUpdate();

			Query tmpQry = getSession().createSQLQuery("SELECT node FROM _result");
			userIdList = tmpQry.list();
		}

		String sql = " select	name,	USERNAME 'signum'from	PTS_USER where	id not in  (select distinct t.USER_ID from PTS_USER_TIMESHEET t  where t.WEEKENDING_DATE >'"
				+ Calendar.getInstance().getWeekYear() + "-"
				+ ((new Date().getMonth() > 2) ? (new Date().getMonth() - 2) + "" : "1")
				+ "-01')  and id in (:userIds) and USERNAME  not like '%Admin%' and  status not in ('OffBoard','No Show','Open','Selected','Deleted','Induction')";

		if (userId > 0) {
			sql = sql.replaceAll(" and id in (:userIds) ", " ");
		}
		Query query = getSession().createSQLQuery(sql).addScalar("signum", new StringType()).addScalar("name",
				new StringType());
		if (userId > 0)
			query.setParameterList("userIds", userIdList);
		return query.setResultTransformer(Transformers.aliasToBean(com.egil.pts.modal.NetworkCodes.class)).list();
	}

	public void saveUserContributions(Long project) {
		getSession().createSQLQuery(
				"INSERT INTO PTS_USER_NETWORK_CODES_CONTRIBUTION (  user_id, network_id, contribution) select user_id,project_id,0.0 from PTS_USER_STABLE_TEAMS ust,PTS_STABLE_TEAMS s where contribution  > 0 and s.id =ust.stable_team_id and project_id=:project  and status=1 ")
				.setLong("project", project).executeUpdate();
	}

	public List<StableTeams> getStableTeamsForUser(Long userId) {
		List<Long> userIdList = new ArrayList<Long>();
		if (userId != null && userId != 0l) {
			Query query = getSession().createSQLQuery("CALL user_id_heirarchy_proc(:id)");
			query.setParameter("id", userId);
			query.executeUpdate();

			Query tmpQry = getSession().createSQLQuery("SELECT node FROM _result");
			userIdList = tmpQry.list();
		}
		String sql = "select US.id,u.NAME 'name',u.USERNAME 'signum',teamName 'teamName',project_id 'project',US.contribution 'value' from PTS_USER_STABLE_TEAMS US,PTS_STABLE_TEAMS S,PTS_USER u where u.NAME not like '%Admin%' and US.contribution >0 and u.status not in ('OffBoard', 'No Show', 'Open', 'Selected', 'Deleted', 'Induction') and u.id=US.user_id and S.status =1 and S.id=US.stable_team_id and US.user_id in (:userIds)  order by  US.user_id, S.teamName asc ";
		if (userId == null) {
			sql = sql.replaceAll(" and US.user_id in (:userIds) ", " ");
		}
		Query query = getSession().createSQLQuery(sql).addScalar("project", new LongType())
				.addScalar("id", new LongType()).addScalar("value", new DoubleType())
				.addScalar("teamName", new StringType()).addScalar("signum", new StringType())
				.addScalar("name", new StringType());
		if (userId != null && userId > 0)
			query.setParameterList("userIds", userIdList);
		return query.setResultTransformer(Transformers.aliasToBean(StableTeams.class)).list();

	}
}
