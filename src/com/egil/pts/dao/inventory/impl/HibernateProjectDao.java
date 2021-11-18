package com.egil.pts.dao.inventory.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.transform.Transformers;
import org.hibernate.type.FloatType;
import org.hibernate.type.LongType;
import org.hibernate.type.StringType;
import org.springframework.stereotype.Repository;

import com.egil.pts.dao.common.hibernate.HibernateGenericDao;
import com.egil.pts.dao.domain.Project;
import com.egil.pts.dao.inventory.ProjectDao;
import com.egil.pts.modal.Pagination;
import com.egil.pts.modal.SearchSortContainer;
import com.egil.pts.modal.UserContribution;

@Repository("projectDao")
public class HibernateProjectDao extends HibernateGenericDao<Project, Long>
		implements ProjectDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<Project> getProjects(Pagination pagination,
			SearchSortContainer searchSortObj) throws Throwable {

		StringBuilder queryString = new StringBuilder();
		queryString.append("from Project p where p.status=1 ");
		if (searchSortObj != null) {
			if (searchSortObj.getSearchField() != null
					&& !searchSortObj.getSearchField().equals("")
					&& searchSortObj.getSearchString() != null
					&& !searchSortObj.getSearchString().equals("")) {
				if (searchSortObj.getSearchField().equalsIgnoreCase(
						"projectName")) {
					queryString.append(" and p.projectName like '%"
							+ searchSortObj.getSearchString() + "%'");
				} else if (searchSortObj.getSearchField().equalsIgnoreCase(
						"pillarName")) {
					queryString.append(" and p.pillar.pillarName like '%"
							+ searchSortObj.getSearchString() + "%'");
				} else if (searchSortObj.getSearchField().equalsIgnoreCase(
						"customerName")) {
					queryString.append(" and p.pillar.customer.customerName like '%"
							+ searchSortObj.getSearchString() + "%'");
				} else if (searchSortObj.getSearchField().equalsIgnoreCase(
						"pillarId")) {
					queryString.append(" and p.pillar.id = "
							+ searchSortObj.getSearchString() + "");
				}
			}

			if (searchSortObj.getSidx() != null
					&& !searchSortObj.getSidx().equals("")
					&& searchSortObj.getSord() != null
					&& !searchSortObj.getSord().equals("")) {
				if (searchSortObj.getSidx().equalsIgnoreCase("projectName")) {
					queryString.append(" order by p.projectName "
							+ searchSortObj.getSord());
				} else if (searchSortObj.getSearchField().equalsIgnoreCase(
						"pillarName")) {
					queryString.append(" order by p.pillar.pillarName "
							+ searchSortObj.getSord());
				} else if (searchSortObj.getSidx()
						.equalsIgnoreCase("createdBy")) {
					queryString.append(" order by p.createdBy "
							+ searchSortObj.getSord());
				} else if (searchSortObj.getSidx().equalsIgnoreCase(
						"createdDate")) {
					queryString.append(" order by p. "
							+ searchSortObj.getSord());
				} else if (searchSortObj.getSidx()
						.equalsIgnoreCase("updatedBy")) {
					queryString.append(" order by p.updatedBy "
							+ searchSortObj.getSord());
				} else if (searchSortObj.getSidx().equalsIgnoreCase(
						"updatedDate")) {
					queryString.append(" order by p.updatedDate "
							+ searchSortObj.getSord());
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
	public Integer deleteProjects(List<Long> projectIdList) throws Throwable {
		Query query = getSession().createQuery(
				"update Project c set c.status = 0 where c.id in (:id)");
		query.setParameterList("id", projectIdList);
		return query.executeUpdate();
	}

	@Override
	public int getProjectsCount(SearchSortContainer searchSortObj)
			throws Throwable {
		int count = 0;
		StringBuilder queryString = new StringBuilder();
		queryString.append("select count (*) from Project p where p.status=1 ");
		if (searchSortObj != null) {
			if (searchSortObj.getSearchField() != null
					&& !searchSortObj.getSearchField().equals("")
					&& searchSortObj.getSearchString() != null
					&& !searchSortObj.getSearchString().equals("")) {
				if (searchSortObj.getSearchField().equalsIgnoreCase(
						"projectName")) {
					queryString.append(" and p.projectName like '%"
							+ searchSortObj.getSearchString() + "'%");
				} else if (searchSortObj.getSearchField().equalsIgnoreCase(
						"pillarName")) {
					queryString.append(" and p.pillar.pillarName like '%"
							+ searchSortObj.getSearchString() + "%'");
				} else if (searchSortObj.getSearchField().equalsIgnoreCase(
						"customerName")) {
					queryString.append(" and p.pillar.customer.customerName like '%"
							+ searchSortObj.getSearchString() + "%'");
				} else if (searchSortObj.getSearchField().equalsIgnoreCase(
						"pillarId")) {
					queryString.append(" and p.pillar.id = "
							+ searchSortObj.getSearchString() + "");
				}
			}
		}
		Query query = getSession().createQuery(queryString.toString());
		Long obj = (Long) query.uniqueResult();
		if (obj != null) {
			count = obj.intValue();
		}
		return count;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<UserContribution> getProjectsByAccount(List<Long> selectedAccountIds, Long userId) throws Throwable {

		String queryStr = "select pil.id pillarId, pil.pillar_name pillarName, pil.account_id accountId, acct.ACCOUNT_NAME accountName, pro.project_name appName, pro.id appId";

		if (userId != null) {
			queryStr = queryStr + ", ifnull(ua.contribution, 0) contribution ";
		}
		queryStr = queryStr + " from PTS_PILLAR pil, PTS_CUSTOMER_ACCOUNTS acct, PTS_PROJECT pro ";

		if (userId != null) {
			queryStr = queryStr + "left outer join PTS_USER_APPS ua on ua.project_id=pro.id and ua.user_id=:userId ";
		}

		queryStr = queryStr
				+ "where pro.status =1 and pil.status=1 and pil.ACCOUNT_ID in (:accountIds) and pil.account_id=acct.id and pil.id=pro.pillar_id order by pil.pillar_name, pro.order_num ";

		Query query = null;
		if (userId != null) {
			query = getSession().createSQLQuery(queryStr).addScalar("accountId", new LongType())
					.addScalar("accountName", new StringType()).addScalar("pillarId", new LongType())
					.addScalar("pillarName", new StringType()).addScalar("appId", new LongType())
					.addScalar("appName", new StringType()).addScalar("contribution", new FloatType());
		} else {
			query = getSession().createSQLQuery(queryStr).addScalar("accountId", new LongType())
					.addScalar("accountName", new StringType()).addScalar("pillarId", new LongType())
					.addScalar("pillarName", new StringType()).addScalar("appId", new LongType())
					.addScalar("appName", new StringType());
		}

		if (userId != null) {
			query.setLong("userId", userId);
		}
		if (selectedAccountIds != null) {
			query.setParameterList("accountIds", selectedAccountIds);
		}

		List<UserContribution> userContributionList = query
				.setResultTransformer(Transformers.aliasToBean(UserContribution.class)).list();

		return userContributionList;
	}

	@Override
	public Long getPillarByProject(Long i) {

		StringBuilder queryString = new StringBuilder();
		queryString.append("select p.pillar.id from Project p where p.id="+i);
		Query query = getSession().createQuery(queryString.toString());
		Long obj = (Long) query.uniqueResult();
		if (obj != null) {
			return obj.longValue();
		}
		return null;
	}

}
