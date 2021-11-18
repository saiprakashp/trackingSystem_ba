package com.egil.pts.dao.inventory;

import java.util.List;
import java.util.Set;

import com.egil.pts.dao.common.GenericDao;
import com.egil.pts.dao.domain.CustomerAccounts;
import com.egil.pts.modal.Pagination;
import com.egil.pts.modal.SearchSortContainer;

public interface CustomerAccountDao extends GenericDao<CustomerAccounts, Long> {
	public List<CustomerAccounts> getCustomerAccounts(Pagination pagination, SearchSortContainer searchSortObj)
			throws Throwable;

	public int getCustomerAccountsCount(SearchSortContainer searchSortObj) throws Throwable;

	public Integer deleteCustomerAccounts(List<Long> customerAccountIdList) throws Throwable;

	public List<CustomerAccounts> getUserNonCustomerAccounts(Set<Long> accountIds) throws Throwable;
}
