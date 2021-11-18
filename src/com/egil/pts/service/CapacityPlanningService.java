package com.egil.pts.service;

import java.util.List;
import java.util.Set;

import com.egil.pts.modal.CapacityPlanning;
import com.egil.pts.modal.CapacityPlanningByType;
import com.egil.pts.modal.Pagination;
import com.egil.pts.modal.SearchSortContainer;
import com.egil.pts.modal.SummaryResponse;
import com.egil.pts.modal.User;

public interface CapacityPlanningService {

	public SummaryResponse<CapacityPlanning> getCapacityPlanningDetails(Long id, Long year, Pagination pagination);

	public int saveCapacityPlanningDetails(CapacityPlanning capacityPlanning);

	public int deleteCapacityPlanningDetails(List<Long> ids) throws Throwable;

	public Set<Long> getResourceNetworkCodes(Long userId, Long year) throws Throwable;
	
	public Set<Long> getNetworkCodeResources(Long networkId, Long year) throws Throwable;

	public SummaryResponse<com.egil.pts.modal.CapacityPlanning> getUserCapacityPlanningReportDetails(
			SearchSortContainer searchSortObj, Long year, Pagination pagination) throws Throwable;

	public SummaryResponse<com.egil.pts.modal.CapacityPlanning> getNetworkCodesList(SearchSortContainer searchSortObj,
			Long year, Pagination pagination) throws Throwable;

	public SummaryResponse<com.egil.pts.modal.CapacityPlanning> getNetworkCodesUsersCapacityList(SearchSortContainer searchSortObj,
			Long year, Pagination pagination, Long networkcodeId) throws Throwable;
	
	public SummaryResponse<com.egil.pts.modal.CapacityPlanning> getUserCapacityPlanningDetailReportDetails(SearchSortContainer searchSortObj,
			Long year, Pagination pagination) throws Throwable;
	
	public List<CapacityPlanningByType> getCapacityDetailsByProjectType(SearchSortContainer searchSortObj,
			Long year, Pagination pagination, Long networkcodeId) throws Throwable;
	
	public CapacityPlanning getCapacityDetails(Long id);
	
	public List<CapacityPlanning> getCapacityNyNetworkCode(List<Long> networkCode, Long year) throws Throwable ;
	
	public List<com.egil.pts.modal.CapacityPlanning> getUserCapacityByName(SearchSortContainer search) throws Throwable;
	
	public List<com.egil.pts.modal.User> getUnPlannedCapacityBySupervisor(SearchSortContainer search,boolean bySupervisor,Pagination pagination)		throws Throwable;
}
