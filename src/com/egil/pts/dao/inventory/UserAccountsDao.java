package com.egil.pts.dao.inventory;

import java.util.List;

import com.egil.pts.dao.common.GenericDao;
import com.egil.pts.dao.domain.CustomerAccounts;
import com.egil.pts.dao.domain.UserAccounts;

public interface UserAccountsDao extends GenericDao<UserAccounts, Long> {

	public void saveUserAccounts(List<UserAccounts> userAccountsList)
			throws Throwable;

	public int removeUserAccounts(Long userId, List<Long> userAccountsList)
			throws Throwable;

	public List<CustomerAccounts> getUserCustomerAccounts(Long userId, List<Long> accountIds) throws Throwable;
}
