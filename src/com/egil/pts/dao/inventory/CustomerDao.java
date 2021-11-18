package com.egil.pts.dao.inventory;

import java.util.List;

import com.egil.pts.dao.common.GenericDao;
import com.egil.pts.dao.domain.Customer;
import com.egil.pts.modal.Pagination;
import com.egil.pts.modal.SearchSortContainer;

public interface CustomerDao extends GenericDao<Customer, Long> {
	public List<Customer> getCustomers(Pagination pagination,
			SearchSortContainer searchSortObj) throws Throwable;

	public int getCustomersCount(SearchSortContainer searchSortObj)
			throws Throwable;

	public Integer deleteCustomers(List<Long> customerIdList)
			throws Throwable;
}
