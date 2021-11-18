package com.egil.pts.dao.inventory.impl;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.egil.pts.dao.common.hibernate.HibernateGenericDao;
import com.egil.pts.dao.domain.Customer;
import com.egil.pts.dao.inventory.CustomerDao;
import com.egil.pts.modal.Pagination;
import com.egil.pts.modal.SearchSortContainer;

@Repository("customerDao")
public class HibernateCustomerDao extends
		HibernateGenericDao<Customer, Long> implements CustomerDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<Customer> getCustomers(Pagination pagination,
			SearchSortContainer searchSortObj) throws Throwable {

		StringBuilder queryString = new StringBuilder();
		queryString.append("from Customer where status=1 ");
		if (searchSortObj != null) {
			if (searchSortObj.getSearchField() != null
					&& !searchSortObj.getSearchField().equals("")
					&& searchSortObj.getSearchString() != null
					&& !searchSortObj.getSearchString().equals("")) {
				if (searchSortObj.getSearchField().equalsIgnoreCase(
						"customerName")) {
					queryString.append(" and customerName like '%"
							+ searchSortObj.getSearchString()+"%'");
				}
			}
			
			if(searchSortObj.getSidx() != null
					&& !searchSortObj.getSidx().equals("")
					&& searchSortObj.getSord() != null
					&& !searchSortObj.getSord().equals("")){
				if (searchSortObj.getSidx().equalsIgnoreCase(
						"customerName")) {
					queryString.append(" order by customerName "
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
	public Integer deleteCustomers(List<Long> customerIdList)
			throws Throwable {
		Query query = getSession().createQuery(
				"update Customer c set c.status = 0 where c.id in (:id)");
		query.setParameterList("id", customerIdList);
		return query.executeUpdate();
	}

	@Override
	public int getCustomersCount(SearchSortContainer searchSortObj)
			throws Throwable {
		int count = 0;
		StringBuilder queryString = new StringBuilder();
		queryString.append("select count (*) from Customer where status=1 ");
		if (searchSortObj != null) {
			if (searchSortObj.getSearchField() != null
					&& !searchSortObj.getSearchField().equals("")
					&& searchSortObj.getSearchString() != null
					&& !searchSortObj.getSearchString().equals("")) {
				if (searchSortObj.getSearchField().equalsIgnoreCase(
						"customerName")) {
					queryString.append(" and customerName like '%"
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

}
