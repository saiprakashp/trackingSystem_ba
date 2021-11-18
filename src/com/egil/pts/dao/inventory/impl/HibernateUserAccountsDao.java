package com.egil.pts.dao.inventory.impl;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.egil.pts.dao.common.hibernate.HibernateGenericDao;
import com.egil.pts.dao.domain.CustomerAccounts;
import com.egil.pts.dao.domain.UserAccounts;
import com.egil.pts.dao.inventory.UserAccountsDao;

@Repository("userAccountsDao")
public class HibernateUserAccountsDao extends
		HibernateGenericDao<UserAccounts, Long> implements UserAccountsDao {

	@Override
	public void saveUserAccounts(List<UserAccounts> userAccountsList)
			throws Throwable {
		int count = 0;
		if (userAccountsList != null && userAccountsList.size() > 0) {
			for (UserAccounts userAccount : userAccountsList) {
				save(userAccount);
				if (count % JDBC_BATCH_SIZE == 0) {
					getSession().flush();
					getSession().clear();
				}
				count++;
			}
		}

	}

	@Override
	public int removeUserAccounts(Long userId, List<Long> userAccountsList)
			throws Throwable {
		Query query = getSession()
				.createQuery(
						"delete from UserAccounts account "
								+ " where account.user.id in (:id) and account.account.id in (:userAccountsList)");
		query.setLong("id", userId);
		query.setParameterList("userAccountsList", userAccountsList);
		return (Integer) query.executeUpdate();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CustomerAccounts> getUserCustomerAccounts(Long userId, List<Long> accountIds) throws Throwable {
		StringBuilder queryString = new StringBuilder();
		queryString.append(" select up.account from UserAccounts up where up.user.id=:userId ");
		if (accountIds != null && accountIds.size() > 0) {
			queryString.append(" and up.account.id not in (:accountIds) ");
		}
		Query query = getSession().createQuery(queryString.toString());
		query.setLong("userId", userId);
		if (accountIds != null) {
			query.setParameterList("accountIds", accountIds);
		}
		return (List<CustomerAccounts>) query.list();
	}

}
