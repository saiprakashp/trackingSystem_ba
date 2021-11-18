package com.egil.pts.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.egil.pts.dao.domain.Customer;
import com.egil.pts.dao.domain.CustomerAccounts;
import com.egil.pts.modal.Pagination;
import com.egil.pts.modal.Pillar;
import com.egil.pts.modal.SearchSortContainer;
import com.egil.pts.modal.Status;
import com.egil.pts.modal.SummaryResponse;
import com.egil.pts.service.PillarService;
import com.egil.pts.service.common.BaseUIService;

@Service("pillarService")
public class PillarServiceImpl extends BaseUIService implements PillarService {

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true)
	public SummaryResponse<Pillar> getPillarsSummary(Pagination pagination,
			SearchSortContainer searchSortObj) throws Throwable {
		SummaryResponse<Pillar> summary = new SummaryResponse<Pillar>();
		summary.setTotalRecords(daoManager.getPillarDao().getPillarsCount(
				searchSortObj));
		summary.setEnitities(getPillars(pagination, searchSortObj));
		return summary;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true)
	public List<Pillar> getPillars(Pagination pagination,
			SearchSortContainer searchSortContainer) throws Throwable {
		List<Pillar> modalPillarsList = new ArrayList<Pillar>();
		List<com.egil.pts.dao.domain.Pillar> domainPillarsList = daoManager
				.getPillarDao().getPillars(pagination, searchSortContainer);
		Pillar modalPillar = null;
		for (com.egil.pts.dao.domain.Pillar domainPillar : domainPillarsList) {
			modalPillar = new Pillar();
			convertDomainToModal(modalPillar, domainPillar);
			modalPillarsList.add(modalPillar);
		}
		return modalPillarsList;
	}

	@Override
	public String getJSONStringOfPillars() throws Throwable {
		String returnValue = "";
		returnValue = -1 + ":" + "Please Select" + ";";
		for (Pillar pillar : getPillars(null, null)) {
			returnValue = returnValue + pillar.getId() + ":"
					+ pillar.getPillarName() + ";";
		}
		return returnValue.substring(0, returnValue.length() - 1);
	}

	@Override
	@Transactional
	public void createPillar(Pillar modalPillar) throws Throwable {
		com.egil.pts.dao.domain.Pillar domainPillar = new com.egil.pts.dao.domain.Pillar();
		convertModalToDomain(modalPillar, domainPillar);
		daoManager.getPillarDao().save(domainPillar);
	}

	@Override
	@Transactional
	public void modifyPillar(Pillar modalPillar) throws Throwable {
		com.egil.pts.dao.domain.Pillar domainPillar = new com.egil.pts.dao.domain.Pillar();
		convertModalToDomain(modalPillar, domainPillar);
		daoManager.getPillarDao().save(domainPillar);
		daoManager.getPillarDao().flush();
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public Integer deletePillars(List<Long> pillarsIdList) throws Throwable {
		return daoManager.getPillarDao().deletePillars(pillarsIdList);
	}

	private void convertModalToDomain(Pillar modalPillar,
			com.egil.pts.dao.domain.Pillar domainPillar) {
		if (modalPillar != null && domainPillar != null) {
			if (modalPillar.getId() != null) {
				domainPillar.setId(modalPillar.getId());
			}
			if (modalPillar.getPillarName() != null) {
				domainPillar.setPillarName(modalPillar.getPillarName());
			}
			if (modalPillar.getCustomer() != null
					&& !modalPillar.getCustomer().endsWith("-1")) {
				CustomerAccounts customerAccount = new CustomerAccounts();
				customerAccount.setId(Long.valueOf(modalPillar.getCustomer()));
				domainPillar.setCustomerAccount(customerAccount);
			}
			
			if (modalPillar.getServiceAssurance() != null) {
				domainPillar.setServiceAssurance(modalPillar
						.getServiceAssurance());
			}
			if (modalPillar.getDescription() != null) {
				domainPillar.setDescription(modalPillar.getDescription());
			}
			if (modalPillar.getStatus() != null) {
				domainPillar.setStatus(Status.getBooleanValueOf(modalPillar
						.getStatus()));
			}
			if (modalPillar.getCreatedBy() != null) {
				domainPillar.setCreatedBy(modalPillar.getCreatedBy());
			}

			if (modalPillar.getCreatedDate() != null) {
				domainPillar.setCreatedDate(modalPillar.getCreatedDate());
			}

			if (modalPillar.getUpdatedBy() != null) {
				domainPillar.setUpdatedBy(modalPillar.getUpdatedBy());
			}
			if (modalPillar.getUpdatedDate() != null) {
				domainPillar.setUpdatedDate(modalPillar.getUpdatedDate());
			}
		}
	}

	private void convertDomainToModal(Pillar modalPillar,
			com.egil.pts.dao.domain.Pillar domainPillar) {
		if (modalPillar != null && domainPillar != null) {
			if (domainPillar.getId() != null) {
				modalPillar.setId(domainPillar.getId());
			}
			if (domainPillar.getPillarName() != null) {
				modalPillar.setPillarName(domainPillar.getPillarName());
			}
			if (domainPillar.getCustomerAccount() != null
					&& domainPillar.getCustomerAccount().getId() != null) {
				modalPillar.setCustomer(domainPillar.getCustomerAccount()
						.getAccountName());
			}
			if (domainPillar.getServiceAssurance() != null) {
				modalPillar.setServiceAssurance(domainPillar
						.getServiceAssurance());
			}
			if (domainPillar.getDescription() != null) {
				modalPillar.setDescription(domainPillar.getDescription());
			}

			if (domainPillar.getCreatedBy() != null) {
				modalPillar.setCreatedBy(domainPillar.getCreatedBy());
			}

			if (domainPillar.getCreatedDate() != null) {
				modalPillar.setCreatedDate(domainPillar.getCreatedDate());
			}

			if (domainPillar.getUpdatedBy() != null) {
				modalPillar.setUpdatedBy(domainPillar.getUpdatedBy());
			}
			if (domainPillar.getUpdatedDate() != null) {
				modalPillar.setUpdatedDate(domainPillar.getUpdatedDate());
			}
		}
	}

	@Override
	public void getPillarsMap(Map<Long, String> pillarsMap, String customerId) throws Throwable {
		
		SearchSortContainer searchSortContainer = null;
		if(customerId != null) {
		searchSortContainer = new SearchSortContainer();
		searchSortContainer.setSearchField("customerId");
		searchSortContainer.setSearchString(customerId);
		}
		
		for (Pillar pillar : getPillars(null, searchSortContainer)) {
			pillarsMap.put(pillar.getId(), pillar.getPillarName());
		}
	}
	
	@Override
	public String loadPillars(String customerId) throws Throwable {
		SearchSortContainer searchSortObj = null;
		if (customerId != null && !customerId.equalsIgnoreCase("")) {
			searchSortObj = new SearchSortContainer();
			searchSortObj.setSearchField("customerId");
			searchSortObj.setSearchString(customerId);
		}
		String returnValue = "";
		returnValue = "<option value='-1'>Please Select</option>\n";
		for (Pillar pillar : getPillars(null, searchSortObj)) {
			returnValue = returnValue + "<option value='"+ pillar.getId()+"'>"
					+ pillar.getPillarName() + "</option>\n";
		}
		return returnValue;
	}
}
