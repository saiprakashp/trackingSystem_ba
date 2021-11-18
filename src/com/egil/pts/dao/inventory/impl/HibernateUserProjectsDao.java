package com.egil.pts.dao.inventory.impl;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Query;
import org.hibernate.internal.util.StringHelper;
import org.hibernate.transform.Transformers;
import org.hibernate.type.LongType;
import org.hibernate.type.StringType;
import org.springframework.stereotype.Repository;

import com.egil.pts.dao.common.hibernate.HibernateGenericDao;
import com.egil.pts.dao.domain.UserProjects;
import com.egil.pts.dao.inventory.UserProjectsDao;
import com.egil.pts.modal.Pagination;
import com.egil.pts.modal.SearchSortContainer;

@Repository("userProjectsDao")
public class HibernateUserProjectsDao extends HibernateGenericDao<UserProjects, Long> implements UserProjectsDao {
	@Transactional
	public void saveUserProjects(List<UserProjects> userProjectsList) throws Throwable {
		int count = 0;
		if ((userProjectsList != null) && (userProjectsList.size() > 0)) {
			for (UserProjects userProject : userProjectsList) {
				save(userProject);
				if (count % JDBC_BATCH_SIZE == 0) {
					getSession().flush();
					getSession().clear();
				}
				count++;
			}
		}
	}

	@Transactional
	public int removeUserProjects(Long userId, List<Long> userProjectsList) throws Throwable {
		Query query = getSession().createQuery(
				"delete from UserProjects up  where up.user.id in (:id) and up.project.id in (:userProjectsList)");

		query.setLong("id", userId.longValue());
		query.setParameterList("userProjectsList", userProjectsList);
		return Integer.valueOf(query.executeUpdate()).intValue();
	}

	@Override
	@Transactional
	public int getUserProjectsCount(SearchSortContainer searchSortContainer, boolean isAdmin) {
		int count = 0;
		StringBuilder queryString = new StringBuilder();

		queryString.append("select count(distinct p.project_name) from PTS_USER_NETWORK_CODES unc, "
				+ " PTS_NETWORK_CODES nc, PTS_PROJECT p " + " where unc.network_code_id=nc.id and nc.project_id=p.id ");
		if (!isAdmin) {
			Query queryProc = getSession().createSQLQuery("CALL user_id_heirarchy_proc(:id)").setParameter("id",
					Long.parseLong(searchSortContainer.getLoggedInId()));

			queryProc.executeUpdate();
			queryString.append(" and user_id in (SELECT node FROM _result) ");
		}
		Query query = getSession().createSQLQuery(queryString.toString());
		BigInteger obj = (BigInteger) query.uniqueResult();
		if (obj != null) {
			count = obj.intValue();
		}
		return count;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<com.egil.pts.modal.Project> getUserProjects(Pagination pagination,
			SearchSortContainer searchSortContainer, boolean isAdmin) {
		StringBuilder queryString = new StringBuilder();
		List<Long> userIdList = new ArrayList<Long>();
		queryString.append(
				"select distinct p.id id, p.project_name projectName, pillar.pillar_name pillar, pillar.id as pillarId "
						+ " from PTS_USER_NETWORK_CODES unc, PTS_NETWORK_CODES nc, PTS_PROJECT p , PTS_PILLAR pillar "
						+ " where unc.network_code_id=nc.id and nc.project_id=p.id and p.PILLAR_ID=pillar.id ");
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
		if (StringHelper.isNotEmpty(searchSortContainer.getSearchField())
				&& StringHelper.isNotEmpty(searchSortContainer.getSearchString())) {
			if (searchSortContainer.getSearchField().equalsIgnoreCase("pillarid")) {
				queryString.append(" and pillar.id=" + searchSortContainer.getSearchString() + " ");
			}
		}
		queryString.append(" order by p.project_name, pillar.pillar_name ");
		Query query = getSession().createSQLQuery(queryString.toString()).addScalar("id", new LongType())
				.addScalar("projectName", new StringType()).addScalar("pillar", new StringType())
				.addScalar("pillarId", new LongType());
		if (userIdList != null && userIdList.size() > 0) {
			query.setParameterList("userIdList", userIdList);
		}
		if (pagination != null) {
			if (pagination.getOffset() > 0)
				query.setFirstResult(pagination.getOffset());
			if (pagination.getSize() > 0)
				query.setMaxResults(pagination.getSize());
		}
		List<com.egil.pts.modal.Project> userProjectList = query
				.setResultTransformer(Transformers.aliasToBean(com.egil.pts.modal.Project.class)).list();

		return userProjectList;
	}

	@Transactional
	@SuppressWarnings("unchecked")
	@Override
	public List<UserProjects> getUserProjects(Long userId, Long projectId) {
		StringBuilder queryString = new StringBuilder();
		queryString.append(" from UserProjects up where up.user.id=:userId ");
		if (projectId != null) {
			queryString.append(" and up.project.id=:projectId ");
		}
		Query query = getSession().createQuery(queryString.toString());
		query.setLong("userId", userId);
		if (projectId != null) {
			query.setLong("projectId", projectId);
		}
		return (List<UserProjects>) query.list();
	}
}
