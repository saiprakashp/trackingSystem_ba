package com.egil.pts.dao.inventory.impl;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.egil.pts.dao.common.hibernate.HibernateGenericDao;
import com.egil.pts.dao.domain.Pillar;
import com.egil.pts.dao.inventory.PillarDao;
import com.egil.pts.modal.Pagination;
import com.egil.pts.modal.SearchSortContainer;

@Repository("pillarDao")
public class HibernatePillarDao extends HibernateGenericDao<Pillar, Long>
		implements PillarDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<Pillar> getPillars(Pagination pagination,
			SearchSortContainer searchSortObj) throws Throwable {

		StringBuilder queryString = new StringBuilder();
		queryString.append("from Pillar p where p.status=1 ");
		if (searchSortObj != null) {
			if (searchSortObj.getSearchField() != null
					&& !searchSortObj.getSearchField().equals("")
					&& searchSortObj.getSearchString() != null
					&& !searchSortObj.getSearchString().equals("")) {
				if (searchSortObj.getSearchField().equalsIgnoreCase(
						"pillarName")) {
					queryString.append(" and p.pillarName like '%"
							+ searchSortObj.getSearchString() + "%'");
				} else if (searchSortObj.getSearchField().equalsIgnoreCase(
						"customerName")) {
					queryString.append(" and p.customer.customerName like '%"
							+ searchSortObj.getSearchString() + "%'");
				} else if (searchSortObj.getSearchField().equalsIgnoreCase(
						"customerId")) {
					queryString.append(" and p.customerAccount.customer.id = "
							+ searchSortObj.getSearchString() + "");
				}
			}

			if (searchSortObj.getSidx() != null
					&& !searchSortObj.getSidx().equals("")
					&& searchSortObj.getSord() != null
					&& !searchSortObj.getSord().equals("")) {
				if (searchSortObj.getSidx().equalsIgnoreCase("pillarName")) {
					queryString.append(" order by p.pillarName "
							+ searchSortObj.getSord());
				} else if (searchSortObj.getSearchField().equalsIgnoreCase(
						"customerName")) {
					queryString.append(" order by p.customer.customerName "
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
	public Integer deletePillars(List<Long> customerIdList) throws Throwable {
		Query query = getSession().createQuery(
				"update Pillar c set ac.status = 0 where c.id in (:id)");
		query.setParameterList("id", customerIdList);
		return query.executeUpdate();
	}

	@Override
	public int getPillarsCount(SearchSortContainer searchSortObj)
			throws Throwable {
		int count = 0;
		StringBuilder queryString = new StringBuilder();
		queryString.append("select count (*) from Pillar p where p.status=1 ");
		if (searchSortObj != null) {
			if (searchSortObj.getSearchField() != null
					&& !searchSortObj.getSearchField().equals("")
					&& searchSortObj.getSearchString() != null
					&& !searchSortObj.getSearchString().equals("")) {
				if (searchSortObj.getSearchField().equalsIgnoreCase(
						"pillarName")) {
					queryString.append(" and p.pillarName like '%"
							+ searchSortObj.getSearchString() + "'%");
				} else if (searchSortObj.getSearchField().equalsIgnoreCase(
						"customerName")) {
					queryString.append(" and p.customer.customerName like '%"
							+ searchSortObj.getSearchString() + "%'");
				} else if (searchSortObj.getSearchField().equalsIgnoreCase(
						"customerId")) {
					queryString.append(" and p.customer.id = "
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

}
