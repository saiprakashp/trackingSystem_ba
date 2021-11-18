package com.egil.pts.dao.inventory.impl;

import java.util.List;
import java.util.Set;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.egil.pts.dao.common.hibernate.HibernateGenericDao;
import com.egil.pts.dao.domain.CustomerAccounts;
import com.egil.pts.dao.inventory.CustomerAccountDao;
import com.egil.pts.modal.Pagination;
import com.egil.pts.modal.SearchSortContainer;

@Repository("customerAccountDao")
public class HibernateCustomerAccountDao extends
		HibernateGenericDao<CustomerAccounts, Long> implements CustomerAccountDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<CustomerAccounts> getCustomerAccounts(Pagination pagination,
			SearchSortContainer searchSortObj) throws Throwable {

		StringBuilder queryString = new StringBuilder();
		queryString.append("from CustomerAccounts where status=1 ");
		if (searchSortObj != null) {
			if (searchSortObj.getSearchField() != null
					&& !searchSortObj.getSearchField().equals("")
					&& searchSortObj.getSearchString() != null
					&& !searchSortObj.getSearchString().equals("")) {
				if (searchSortObj.getSearchField().equalsIgnoreCase(
						"accountName")) {
					queryString.append(" and accountName like '%"
							+ searchSortObj.getSearchString()+"%'");
				}
			}
			
			if(searchSortObj.getSidx() != null
					&& !searchSortObj.getSidx().equals("")
					&& searchSortObj.getSord() != null
					&& !searchSortObj.getSord().equals("")){
				if (searchSortObj.getSidx().equalsIgnoreCase(
						"accountName")) {
					queryString.append(" order by accountName "
							+ searchSortObj.getSord());
				} else if (searchSortObj.getSidx().equalsIgnoreCase(
						"createdBy")) {
					queryString.append(" order by createdBy "
							+ searchSortObj.getSord());
				} else if (searchSortObj.getSidx().equalsIgnoreCase(
						"createdDate")) {
					queryString.append(" order by createdDate "
							+ searchSortObj.getSord());
				} else if (searchSortObj.getSidx().equalsIgnoreCase(
						"updatedBy")) {
					queryString.append(" order by updatedBy "
							+ searchSortObj.getSord());
				} else if (searchSortObj.getSidx().equalsIgnoreCase(
						"updatedDate")) {
					queryString.append(" order by updatedDate "
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
	public Integer deleteCustomerAccounts(List<Long> customerIdList)
			throws Throwable {
		Query query = getSession().createQuery(
				"update CustomerAccounts c set c.status = 0 where c.id in (:id)");
		query.setParameterList("id", customerIdList);
		return query.executeUpdate();
	}

	@Override
	public int getCustomerAccountsCount(SearchSortContainer searchSortObj)
			throws Throwable {
		int count = 0;
		StringBuilder queryString = new StringBuilder();
		queryString.append("select count (*) from CustomerAccounts where status=1 ");
		if (searchSortObj != null) {
			if (searchSortObj.getSearchField() != null
					&& !searchSortObj.getSearchField().equals("")
					&& searchSortObj.getSearchString() != null
					&& !searchSortObj.getSearchString().equals("")) {
				if (searchSortObj.getSearchField().equalsIgnoreCase(
						"accountName")) {
					queryString.append(" and accountName like '%"
							+ searchSortObj.getSearchString()+"%'");
				}
			}
		}
		Query query = getSession().createQuery(queryString.toString());
		Long obj = (Long)query.uniqueResult();
		if (obj != null) {
			count = obj.intValue();
		}
		return count;
	}

	@Override
	public List<CustomerAccounts> getUserNonCustomerAccounts(Set<Long> accountIds) throws Throwable {
		StringBuilder queryString = new StringBuilder();
		queryString.append(" from CustomerAccounts up where status=1 ");
		if (accountIds != null && accountIds.size() > 0) {
			queryString.append(" and up.id  not in (:accountIds) ");
		}
		Query query = getSession().createQuery(queryString.toString());
		if (accountIds != null) {
			query.setParameterList("accountIds", accountIds);
		}
		return (List<CustomerAccounts>) query.list();
	}

}
