package com.egil.pts.service;

import java.util.List;
import java.util.Map;

import com.egil.pts.modal.Customer;
import com.egil.pts.modal.Pagination;
import com.egil.pts.modal.SearchSortContainer;
import com.egil.pts.modal.SummaryResponse;

public interface CustomerService {

	public SummaryResponse<Customer> getCustomersSummary(
			Pagination pagination, SearchSortContainer searchSortContainer)
			throws Throwable;

	public List<Customer> getCustomers(Pagination pagination,
			SearchSortContainer searchSortContainer) throws Throwable;

	public void createCustomer(Customer customer) throws Throwable;

	public void modifyCustomer(Customer customer) throws Throwable;

	public Integer deleteCustomers(List<Long> customerIdList)
			throws Throwable;

	public void getCustomersMap(Map<Long, String> customersMap)
			throws Throwable;
	
	public String getJSONStringOfCustomers() throws Throwable;
	
	public void getAccountsMap(Map<Long, String> accountsMap) throws Throwable;
	
	public String getJSONStringOfAccounts() throws Throwable ;
}
