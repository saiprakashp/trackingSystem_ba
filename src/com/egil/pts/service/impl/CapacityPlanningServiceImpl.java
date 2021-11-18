package com.egil.pts.service.impl;

import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.internal.util.StringHelper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.egil.pts.dao.domain.NetworkCodes;
import com.egil.pts.dao.domain.User;
import com.egil.pts.modal.CapacityPlanning;
import com.egil.pts.modal.CapacityPlanningByType;
import com.egil.pts.modal.LocationUserCount;
import com.egil.pts.modal.Pagination;
import com.egil.pts.modal.SearchSortContainer;
import com.egil.pts.modal.SummaryResponse;
import com.egil.pts.service.CapacityPlanningService;
import com.egil.pts.service.common.BaseUIService;

@Service("capacityPlanningService")
public class CapacityPlanningServiceImpl extends BaseUIService implements
		CapacityPlanningService {
	
@Value("${rico.capacity.vzCapShow}")
private boolean vzCapShow;		
	
	@Override
	public SummaryResponse<CapacityPlanning> getCapacityPlanningDetails(
			Long id, Long year, Pagination pagination) {

		List<CapacityPlanning> modalCustomersList = new ArrayList<CapacityPlanning>();
		SummaryResponse<CapacityPlanning> summary = new SummaryResponse<CapacityPlanning>();
		try {
			summary.setTotalRecords(daoManager.getCapacityPlanningDao()
					.getUserCapacityPlanningCount(id, year));
			List<com.egil.pts.dao.domain.CapacityPlanning> domainCapacityPlanningList = daoManager
					.getCapacityPlanningDao().getCapacityPlanningDetails(id,
							year, pagination);
			CapacityPlanning modalCapacityPlanning = null;
			for (com.egil.pts.dao.domain.CapacityPlanning domainCapacityPlanning : domainCapacityPlanningList) {
				modalCapacityPlanning = new CapacityPlanning();
				convertDomainToModal(modalCapacityPlanning,
						domainCapacityPlanning);
				modalCustomersList.add(modalCapacityPlanning);
			}
			summary.setEnitities(modalCustomersList);
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return summary;
	}

	private void convertModalToDomain(CapacityPlanning modalCapacityPlanning,
			com.egil.pts.dao.domain.CapacityPlanning domainCapacityPlanning) {
		if (modalCapacityPlanning != null && domainCapacityPlanning != null) {
			if (modalCapacityPlanning.getId() != null) {
				domainCapacityPlanning.setId(modalCapacityPlanning.getId());
			}
			if (modalCapacityPlanning.getUserId() != null) {
				User user = new User();
				user.setId(modalCapacityPlanning.getUserId());
				domainCapacityPlanning.setUser(user);
			}
			if (modalCapacityPlanning.getNetworkCodeId() != null) {
				NetworkCodes nc = new NetworkCodes();
				nc.setId(modalCapacityPlanning.getNetworkCodeId());
				domainCapacityPlanning.setNetworkCode(nc);
			}
			if (modalCapacityPlanning.getJanCapacity() != null) {
				domainCapacityPlanning.setJanCapacity((modalCapacityPlanning
						.getJanCapacity()));
			}
			if (modalCapacityPlanning.getFebCapacity() != null) {
				domainCapacityPlanning.setFebCapacity((modalCapacityPlanning
						.getFebCapacity()));
			}
			if (modalCapacityPlanning.getMarCapacity() != null) {
				domainCapacityPlanning.setMarCapacity((modalCapacityPlanning
						.getMarCapacity()));
			}
			if (modalCapacityPlanning.getAprCapacity() != null) {
				domainCapacityPlanning.setAprCapacity((modalCapacityPlanning
						.getAprCapacity()));
			}
			if (modalCapacityPlanning.getMayCapacity() != null) {
				domainCapacityPlanning.setMayCapacity((modalCapacityPlanning
						.getMayCapacity()));
			}
			if (modalCapacityPlanning.getJunCapacity() != null) {
				domainCapacityPlanning.setJunCapacity((modalCapacityPlanning
						.getJunCapacity()));
			}

			if (modalCapacityPlanning.getJulCapacity() != null) {
				domainCapacityPlanning.setJulCapacity((modalCapacityPlanning
						.getJulCapacity()));
			}
			if (modalCapacityPlanning.getAugCapacity() != null) {
				domainCapacityPlanning.setAugCapacity((modalCapacityPlanning
						.getAugCapacity()));
			}
			if (modalCapacityPlanning.getSepCapacity() != null) {
				domainCapacityPlanning.setSepCapacity((modalCapacityPlanning
						.getSepCapacity()));
			}
			if (modalCapacityPlanning.getOctCapacity() != null) {
				domainCapacityPlanning.setOctCapacity((modalCapacityPlanning
						.getOctCapacity()));
			}
			if (modalCapacityPlanning.getNovCapacity() != null) {
				domainCapacityPlanning.setNovCapacity((modalCapacityPlanning
						.getNovCapacity()));
			}
			if (modalCapacityPlanning.getDecCapacity() != null) {
				domainCapacityPlanning.setDecCapacity((modalCapacityPlanning
						.getDecCapacity()));
			}

			if (modalCapacityPlanning.getCreatedBy() != null) {
				domainCapacityPlanning.setCreatedBy(modalCapacityPlanning
						.getCreatedBy());
			}

			if (modalCapacityPlanning.getCreatedDate() != null) {
				domainCapacityPlanning.setCreatedDate(modalCapacityPlanning
						.getCreatedDate());
			}

			if (modalCapacityPlanning.getUpdatedBy() != null) {
				domainCapacityPlanning.setUpdatedBy(modalCapacityPlanning
						.getUpdatedBy());
			}
			if (modalCapacityPlanning.getUpdatedDate() != null) {
				domainCapacityPlanning.setUpdatedDate(modalCapacityPlanning
						.getUpdatedDate());
			}

			if (modalCapacityPlanning.getYear() != null) {
				domainCapacityPlanning.setYear(modalCapacityPlanning.getYear());
			}
		}
	}

	private void convertDomainToModal(CapacityPlanning modalCapacityPlanning,
			com.egil.pts.dao.domain.CapacityPlanning domainCapacityPlanning) {
		if (modalCapacityPlanning != null && domainCapacityPlanning != null) {
			if (domainCapacityPlanning.getId() != null) {
				modalCapacityPlanning.setId(domainCapacityPlanning.getId());
			}
			if (domainCapacityPlanning.getUser() != null) {
				modalCapacityPlanning.setUserId(domainCapacityPlanning
						.getUser().getId());
			}

			if (domainCapacityPlanning.getYear() != null) {
				modalCapacityPlanning.setYear(domainCapacityPlanning.getYear());
			}

			if (domainCapacityPlanning.getNetworkCode() != null) {
				modalCapacityPlanning.setNetworkCodeId(domainCapacityPlanning
						.getNetworkCode().getId());
				modalCapacityPlanning.setNetworkCode(domainCapacityPlanning
						.getNetworkCode().getReleaseId()
						+ " - "
						+ domainCapacityPlanning.getNetworkCode()
								.getReleaseName());
			}

			if (domainCapacityPlanning.getJanCapacity() != null) {
				modalCapacityPlanning.setJanCapacity((domainCapacityPlanning
						.getJanCapacity()));
			}
			if (domainCapacityPlanning.getFebCapacity() != null) {
				modalCapacityPlanning.setFebCapacity((domainCapacityPlanning
						.getFebCapacity()));
			}
			if (domainCapacityPlanning.getMarCapacity() != null) {
				modalCapacityPlanning.setMarCapacity((domainCapacityPlanning
						.getMarCapacity()));
			}
			if (domainCapacityPlanning.getAprCapacity() != null) {
				modalCapacityPlanning.setAprCapacity((domainCapacityPlanning
						.getAprCapacity()));
			}
			if (domainCapacityPlanning.getMayCapacity() != null) {
				modalCapacityPlanning.setMayCapacity((domainCapacityPlanning
						.getMayCapacity()));
			}
			if (domainCapacityPlanning.getJunCapacity() != null) {
				modalCapacityPlanning.setJunCapacity((domainCapacityPlanning
						.getJunCapacity()));
			}

			if (domainCapacityPlanning.getJulCapacity() != null) {
				modalCapacityPlanning.setJulCapacity((domainCapacityPlanning
						.getJulCapacity()));
			}
			if (domainCapacityPlanning.getAugCapacity() != null) {
				modalCapacityPlanning.setAugCapacity((domainCapacityPlanning
						.getAugCapacity()));
			}
			if (domainCapacityPlanning.getSepCapacity() != null) {
				modalCapacityPlanning.setSepCapacity((domainCapacityPlanning
						.getSepCapacity()));
			}
			if (domainCapacityPlanning.getOctCapacity() != null) {
				modalCapacityPlanning.setOctCapacity((domainCapacityPlanning
						.getOctCapacity()));
			}
			if (domainCapacityPlanning.getNovCapacity() != null) {
				modalCapacityPlanning.setNovCapacity((domainCapacityPlanning
						.getNovCapacity()));
			}
			if (domainCapacityPlanning.getDecCapacity() != null) {
				modalCapacityPlanning.setDecCapacity((domainCapacityPlanning
						.getDecCapacity()));
			}

			if (domainCapacityPlanning.getCreatedBy() != null) {
				modalCapacityPlanning.setCreatedBy(domainCapacityPlanning
						.getCreatedBy());
			}

			if (domainCapacityPlanning.getCreatedDate() != null) {
				modalCapacityPlanning.setCreatedDate(domainCapacityPlanning
						.getCreatedDate());
			}

			if (domainCapacityPlanning.getUpdatedBy() != null) {
				modalCapacityPlanning.setUpdatedBy(domainCapacityPlanning
						.getUpdatedBy());
			}
			if (domainCapacityPlanning.getUpdatedDate() != null) {
				modalCapacityPlanning.setUpdatedDate(domainCapacityPlanning
						.getUpdatedDate());
			}
		}
	}

	@Override
	public int saveCapacityPlanningDetails(CapacityPlanning capacityPlanning) {
		com.egil.pts.dao.domain.CapacityPlanning domainCapacityPlanning = new com.egil.pts.dao.domain.CapacityPlanning();
		convertModalToDomain(capacityPlanning, domainCapacityPlanning);
		try {
			daoManager.getCapacityPlanningDao().save(domainCapacityPlanning);
			daoManager.getCapacityPlanningDao().flush();
		} catch (Exception e) {
			e.printStackTrace();
			return 999;
		}
		return 0;
	}

	@Override
	public int deleteCapacityPlanningDetails(List<Long> ids) throws Throwable {
		return daoManager.getCapacityPlanningDao()
				.deleteCapacityPlanningDetails(ids);
	}

	@Override
	public Set<Long> getResourceNetworkCodes(Long userId, Long year)
			throws Throwable {
		return daoManager.getCapacityPlanningDao().getNetworkCodesOfUser(
				userId, year);
	}
	
	@Override
	public Set<Long> getNetworkCodeResources(Long networkId, Long year)
			throws Throwable {
		return daoManager.getCapacityPlanningDao().getUsersOfNetworkCode(
				networkId, year);
	}

	@Override
	public SummaryResponse<com.egil.pts.modal.CapacityPlanning> getUserCapacityPlanningReportDetails(SearchSortContainer searchSortObj,
			Long year, Pagination pagination) throws Throwable {
		SummaryResponse<com.egil.pts.modal.CapacityPlanning> summary = new SummaryResponse<com.egil.pts.modal.CapacityPlanning>();
		try {
			
			summary.setTotalRecords(
					daoManager.getCapacityPlanningDao().getUserCapacityPlanningReportCount(searchSortObj, year));
			List<com.egil.pts.modal.CapacityPlanning> capacityPlanningDetails = daoManager.getCapacityPlanningDao()
					.getUserCapacityPlanningReportDetails(searchSortObj, year, pagination);
			summary.setEnitities(capacityPlanningDetails);
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return summary;

	}
	
	@Override
	public SummaryResponse<com.egil.pts.modal.CapacityPlanning> getNetworkCodesList(SearchSortContainer searchSortObj,
			Long year, Pagination pagination) throws Throwable {
		SummaryResponse<com.egil.pts.modal.CapacityPlanning> summary = new SummaryResponse<com.egil.pts.modal.CapacityPlanning>();
		try {
			summary.setTotalRecords(
					daoManager.getCapacityPlanningDao().getNetworkCodesListCount(searchSortObj, year));
			List<com.egil.pts.modal.CapacityPlanning> capacityPlanningDetails = daoManager.getCapacityPlanningDao()
					.getNetworkCodesList(searchSortObj, year, pagination,isVzCapShow());
			summary.setEnitities(capacityPlanningDetails);
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return summary;

	}
	
	@Override
	public SummaryResponse<com.egil.pts.modal.CapacityPlanning> getNetworkCodesUsersCapacityList(SearchSortContainer searchSortObj,
			Long year, Pagination pagination, Long networkcodeId) throws Throwable {
		SummaryResponse<com.egil.pts.modal.CapacityPlanning> summary = new SummaryResponse<com.egil.pts.modal.CapacityPlanning>();
		try {
			
			summary.setTotalRecords(
					daoManager.getCapacityPlanningDao().getNetworkCodesUserListCount(searchSortObj, year, networkcodeId));
			List<com.egil.pts.modal.CapacityPlanning> capacityPlanningDetails = daoManager.getCapacityPlanningDao()
					.getNetworkCodesUsersCapacityList(searchSortObj, year, pagination, networkcodeId);
			summary.setEnitities(capacityPlanningDetails);
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return summary;

	}
	
	@Override
	public SummaryResponse<com.egil.pts.modal.CapacityPlanning> getUserCapacityPlanningDetailReportDetails(SearchSortContainer searchSortObj,
			Long year, Pagination pagination) throws Throwable {
		SummaryResponse<com.egil.pts.modal.CapacityPlanning> summary = new SummaryResponse<com.egil.pts.modal.CapacityPlanning>();
		try {
			
			summary.setTotalRecords(
					daoManager.getCapacityPlanningDao().getUserCapacityPlanningDetailReportCount(searchSortObj, year));
			List<com.egil.pts.modal.CapacityPlanning> capacityPlanningDetails = daoManager.getCapacityPlanningDao()
					.getUserCapacityPlanningDetailReportDetails(searchSortObj, year, pagination);
			summary.setEnitities(capacityPlanningDetails);
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return summary;

	}
	
	@Override
	public List<CapacityPlanningByType> getCapacityDetailsByProjectType(
			SearchSortContainer searchSortObj, Long year, Pagination pagination, Long networkcodeId) throws Throwable {
		List<CapacityPlanningByType> cptyPlanDetByTypeList = new ArrayList<CapacityPlanningByType>();
		Map<String, Map<String, Float>> monthTypeMap = new LinkedHashMap<String, Map<String, Float>>();
		Map<String, Float> typeMap = new LinkedHashMap<String, Float>();
		try {
			List<LocationUserCount> locationUserCntList = daoManager
					.getUserUtilizationDao().getLocationUserCnt(Long.parseLong(searchSortObj.getSearchSupervisor()), true, searchSortObj.getSearchStatus());
			Long headCnt = 0l;
			for (LocationUserCount locationUserCntObj : locationUserCntList) {
				headCnt = headCnt + locationUserCntObj.getUserCnt();
			}

			String[] shortMonths = new DateFormatSymbols().getShortMonths();
			List<com.egil.pts.modal.CapacityPlanning> capacityPlanningDetails = daoManager.getCapacityPlanningDao()
					.getCapacityDetailsByProjectType(searchSortObj, year, pagination, networkcodeId);
			for (String month : shortMonths) {
				if (StringHelper.isNotEmpty(month)) {
					typeMap = new LinkedHashMap<String, Float>();
					monthTypeMap.put(month, typeMap);
					for (com.egil.pts.modal.CapacityPlanning cptyPlanDet : capacityPlanningDetails) {
						if (month.equalsIgnoreCase("Jan")) {
							monthTypeMap.get(month).put(cptyPlanDet.getProjectType(), cptyPlanDet.getJanCapacity());
						} else if (month.equalsIgnoreCase("Feb")) {
							monthTypeMap.get(month).put(cptyPlanDet.getProjectType(), cptyPlanDet.getFebCapacity());
						} else if (month.equalsIgnoreCase("Mar")) {
							monthTypeMap.get(month).put(cptyPlanDet.getProjectType(), cptyPlanDet.getMarCapacity());
						} else if (month.equalsIgnoreCase("Apr")) {
							monthTypeMap.get(month).put(cptyPlanDet.getProjectType(), cptyPlanDet.getAprCapacity());
						} else if (month.equalsIgnoreCase("May")) {
							monthTypeMap.get(month).put(cptyPlanDet.getProjectType(), cptyPlanDet.getMayCapacity());
						} else if (month.equalsIgnoreCase("Jun")) {
							monthTypeMap.get(month).put(cptyPlanDet.getProjectType(), cptyPlanDet.getJunCapacity());
						} else if (month.equalsIgnoreCase("Jul")) {
							monthTypeMap.get(month).put(cptyPlanDet.getProjectType(), cptyPlanDet.getJulCapacity());
						} else if (month.equalsIgnoreCase("Aug")) {
							monthTypeMap.get(month).put(cptyPlanDet.getProjectType(), cptyPlanDet.getAugCapacity());
						} else if (month.equalsIgnoreCase("Sep")) {
							monthTypeMap.get(month).put(cptyPlanDet.getProjectType(), cptyPlanDet.getSepCapacity());
						} else if (month.equalsIgnoreCase("Oct")) {
							monthTypeMap.get(month).put(cptyPlanDet.getProjectType(), cptyPlanDet.getOctCapacity());
						} else if (month.equalsIgnoreCase("Nov")) {
							monthTypeMap.get(month).put(cptyPlanDet.getProjectType(), cptyPlanDet.getNovCapacity());
						} else if (month.equalsIgnoreCase("Dec")) {
							monthTypeMap.get(month).put(cptyPlanDet.getProjectType(), cptyPlanDet.getDecCapacity());
						}
					}
				}
			}
			
			for(String month: monthTypeMap.keySet()){
				CapacityPlanningByType  cptyDet = new CapacityPlanningByType();
				cptyDet.setMonth(month);
				cptyDet.setHeadCount(headCnt);
				Float targetCapacity = (headCnt * 170.0f);
				cptyDet.setTargetCapacity(targetCapacity);
				for(String projectType: monthTypeMap.get(month).keySet()){
					if(projectType.equalsIgnoreCase("Project")){
						cptyDet.setProjectCapacity(monthTypeMap.get(month).get(projectType));
					}else if(projectType.equalsIgnoreCase("Feasibility")){
						cptyDet.setFeasibilityCapacity(monthTypeMap.get(month).get(projectType));
					}else if(projectType.equalsIgnoreCase("4th Support")){
						cptyDet.setFouthSupportCapacity(monthTypeMap.get(month).get(projectType));
					}else if(projectType.equalsIgnoreCase("Adhoc")){
						cptyDet.setAdhocCapacity(monthTypeMap.get(month).get(projectType));
					}
				}
				Float capacitydiff = targetCapacity - cptyDet.getTotalCapacity();
				cptyDet.setCapacityDiff(capacitydiff);
				
				cptyPlanDetByTypeList.add(cptyDet);
				
			}
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return cptyPlanDetByTypeList;
	}
	
	@Override
	public CapacityPlanning getCapacityDetails(Long id) {
		
		CapacityPlanning capacityPlanning = null;
		try {
			capacityPlanning = daoManager.getCapacityPlanningDao().getCapacityModalById(id,null);
		} catch (Throwable e) {
			e.printStackTrace();
		}
		
		return capacityPlanning;
	}

	@Override
	public List<CapacityPlanning> getCapacityNyNetworkCode(List<Long> networkCodes,
			Long year) throws Throwable {
		List<CapacityPlanning> capacityPlanning = null;
		try {
			capacityPlanning = daoManager.getCapacityPlanningDao().getCapacityNyNetworkCode(networkCodes,year);
		} catch (Throwable e) {
			e.printStackTrace();
		}
		
		return capacityPlanning;
	}
	@Override
	public List<com.egil.pts.modal.CapacityPlanning> getUserCapacityByName(SearchSortContainer search) throws Throwable {

		List<CapacityPlanning> capacityPlanning = null;
		try {
			capacityPlanning = daoManager.getCapacityPlanningDao().getUserCapacityByName(search);
		} catch (Throwable e) {
			e.printStackTrace();
		}
		
		return capacityPlanning;
	
	}
	
	public List<com.egil.pts.modal.User> getUnPlannedCapacityBySupervisor(SearchSortContainer search,boolean bySupervisor,Pagination pagination)		throws Throwable{
		return  daoManager.getCapacityPlanningDao().getUnPlannedCapacityBySupervisor(search,bySupervisor);
	}

	public boolean isVzCapShow() {
		return vzCapShow;
	}

	public void setVzCapShow(boolean vzCapShow) {
		this.vzCapShow = vzCapShow;
	}

	
	
}
