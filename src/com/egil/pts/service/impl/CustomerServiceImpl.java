package com.egil.pts.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.egil.pts.dao.domain.CustomerAccounts;
import com.egil.pts.modal.Customer;
import com.egil.pts.modal.Pagination;
import com.egil.pts.modal.SearchSortContainer;
import com.egil.pts.modal.Status;
import com.egil.pts.modal.SummaryResponse;
import com.egil.pts.service.CustomerService;
import com.egil.pts.service.common.BaseUIService;

@Service("customerService")
public class CustomerServiceImpl extends BaseUIService implements
CustomerService {

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true)
	public SummaryResponse<Customer> getCustomersSummary(
			Pagination pagination, SearchSortContainer searchSortObj)
			throws Throwable {
		SummaryResponse<Customer> summary = new SummaryResponse<Customer>();
		summary.setTotalRecords(daoManager.getCustomerDao()
				.getCustomersCount(searchSortObj));
		summary.setEnitities(getCustomers(pagination, searchSortObj));
		return summary;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true)
	public List<Customer> getCustomers(Pagination pagination,
			SearchSortContainer searchSortContainer) throws Throwable {
		List<Customer> modalCustomersList = new ArrayList<Customer>();
		List<com.egil.pts.dao.domain.Customer> domainCustomersList = daoManager
				.getCustomerDao().getCustomers(pagination,
						searchSortContainer);
		Customer modalCustomer = null;
		for (com.egil.pts.dao.domain.Customer domainCustomer : domainCustomersList) {
			modalCustomer = new Customer();
			convertDomainToModal(modalCustomer, domainCustomer);
			modalCustomersList.add(modalCustomer);
		}
		return modalCustomersList;
	}
	
	@Override
	public String getJSONStringOfCustomers() throws Throwable {
		String returnValue = "";
		returnValue = -1 + ":" + "Please Select" + ";";
		for (Customer customer : getCustomers(null, null)) {
			returnValue = returnValue + customer.getId() + ":"
					+ customer.getCustomerName() + ";";
		}
		return returnValue.substring(0, returnValue.length() - 1);
	}

	@Override
	@Transactional
	public void createCustomer(Customer modalCustomer)
			throws Throwable {
		com.egil.pts.dao.domain.Customer domainCustomer = new com.egil.pts.dao.domain.Customer();
		convertModalToDomain(modalCustomer, domainCustomer);
		daoManager.getCustomerDao().save(domainCustomer);
	}

	@Override
	@Transactional
	public void modifyCustomer(Customer modalCustomer)
			throws Throwable {
		com.egil.pts.dao.domain.Customer domainCustomer = new com.egil.pts.dao.domain.Customer();
		convertModalToDomain(modalCustomer, domainCustomer);
		daoManager.getCustomerDao().update(domainCustomer);
		daoManager.getCustomerDao().flush();
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public Integer deleteCustomers(List<Long> customersIdList)
			throws Throwable {
		return daoManager.getCustomerDao().deleteCustomers(
				customersIdList);
	}

	private void convertModalToDomain(Customer modalCustomer,
			com.egil.pts.dao.domain.Customer domainCustomer) {
		if (modalCustomer != null && domainCustomer != null) {
			if (modalCustomer.getId() != null) {
				domainCustomer.setId(modalCustomer.getId());
			}
			if (modalCustomer.getCustomerName() != null) {
				domainCustomer.setCustomerName(modalCustomer
						.getCustomerName());
			}
			if (modalCustomer.getDescription() != null) {
				domainCustomer.setDescription(modalCustomer
						.getDescription());
			}
			if (modalCustomer.getStatus() != null) {
				domainCustomer.setStatus(Status
						.getBooleanValueOf(modalCustomer.getStatus()));
			}
			if (modalCustomer.getCreatedBy() != null) {
				domainCustomer.setCreatedBy(modalCustomer
						.getCreatedBy());
			}

			if (modalCustomer.getCreatedDate() != null) {
				domainCustomer.setCreatedDate(modalCustomer
						.getCreatedDate());
			}

			if (modalCustomer.getUpdatedBy() != null) {
				domainCustomer.setUpdatedBy(modalCustomer
						.getUpdatedBy());
			}
			if (modalCustomer.getUpdatedDate() != null) {
				domainCustomer.setUpdatedDate(modalCustomer
						.getUpdatedDate());
			}
		}
	}

	private void convertDomainToModal(Customer modalCustomer,
			com.egil.pts.dao.domain.Customer domainCustomer) {
		if (modalCustomer != null && domainCustomer != null) {
			if (domainCustomer.getId() != null) {
				modalCustomer.setId(domainCustomer.getId());
			}
			if (domainCustomer.getCustomerName() != null) {
				modalCustomer.setCustomerName(domainCustomer
						.getCustomerName());
			}
			if (domainCustomer.getDescription() != null) {
				modalCustomer.setDescription(domainCustomer
						.getDescription());
			}

			if (domainCustomer.getCreatedBy() != null) {
				modalCustomer.setCreatedBy(domainCustomer
						.getCreatedBy());
			}

			if (domainCustomer.getCreatedDate() != null) {
				modalCustomer.setCreatedDate(domainCustomer
						.getCreatedDate());
			}

			if (domainCustomer.getUpdatedBy() != null) {
				modalCustomer.setUpdatedBy(domainCustomer
						.getUpdatedBy());
			}
			if (domainCustomer.getUpdatedDate() != null) {
				modalCustomer.setUpdatedDate(domainCustomer
						.getUpdatedDate());
			}
		}
	}

	@Override
	public void getCustomersMap(Map<Long, String> customersMap)
			throws Throwable {
		for (Customer customer : getCustomers(null, null)) {
			customersMap.put(
					customer.getId(),
					customer.getCustomerName());
		}
	}
	
	@Override
	public void getAccountsMap(Map<Long, String> accountsMap) throws Throwable {
		List<CustomerAccounts> customerAccList = daoManager.getCustomerAccountDao().getCustomerAccounts(null, null);
		for (CustomerAccounts account : customerAccList) {
			accountsMap.put(account.getId(), account.getAccountName());
		}
	}
	
	@Override
	public String getJSONStringOfAccounts() throws Throwable {
		String returnValue = "";
		List<CustomerAccounts> customerAccList = daoManager.getCustomerAccountDao().getCustomerAccounts(null, null);
		returnValue = -1 + ":" + "Please Select" + ";";
		for (CustomerAccounts customer : customerAccList) {
			returnValue = returnValue + customer.getId() + ":"
					+ customer.getAccountName() + ";";
		}
		return returnValue.substring(0, returnValue.length() - 1);
	}
}


