package com.egil.pts.dao.inventory;

import java.util.List;
import java.util.Set;

import com.egil.pts.dao.common.GenericDao;
import com.egil.pts.dao.domain.CapacityPlanning;
import com.egil.pts.modal.Pagination;
import com.egil.pts.modal.SearchSortContainer;
import com.egil.pts.modal.User;

public interface CapacityPlanningDao extends GenericDao<CapacityPlanning, Long> {

	public List<CapacityPlanning> getCapacityPlanningDetails(Long id, Long year, Pagination pagination);

	public int getUserCapacityPlanningCount(Long id, Long year) throws Throwable;

	public int deleteCapacityPlanningDetails(List<Long> ids) throws Throwable;

	public Set<Long> getNetworkCodesOfUser(Long id, Long year) throws Throwable;
	
	public Set<Long> getUsersOfNetworkCode(Long id, Long year) throws Throwable;

	public int getUserCapacityPlanningReportCount(SearchSortContainer searchSortObj, Long year) throws Throwable;

	public List<com.egil.pts.modal.CapacityPlanning> getUserCapacityPlanningReportDetails(
			SearchSortContainer searchSortObj, Long year, Pagination pagination) throws Throwable;

	public int getNetworkCodesListCount(SearchSortContainer searchSortObj, Long year) throws Throwable;

	public List<com.egil.pts.modal.CapacityPlanning> getNetworkCodesList(SearchSortContainer searchSortObj, Long year,
			Pagination pagination,boolean showPrevYearTot) throws Throwable;

	public int getNetworkCodesUserListCount(SearchSortContainer searchSortObj, Long year, Long networkcodeId)
			throws Throwable;

	public List<com.egil.pts.modal.CapacityPlanning> getNetworkCodesUsersCapacityList(SearchSortContainer searchSortObj,
			Long year, Pagination pagination, Long networkcodeId) throws Throwable;
	
	public List<com.egil.pts.modal.CapacityPlanning> getUserCapacityPlanningDetailReportDetails(
			SearchSortContainer searchSortObj, Long year, Pagination pagination) throws Throwable;
	
	public int getUserCapacityPlanningDetailReportCount(SearchSortContainer searchSortObj, Long year) throws Throwable;
	
	public List<com.egil.pts.modal.CapacityPlanning> getCapacityDetailsByProjectType(SearchSortContainer searchSortObj,
			Long year, Pagination pagination, Long networkcodeId) throws Throwable;
	
	public com.egil.pts.modal.CapacityPlanning getCapacityModalById(Long id,
			Long year) throws Throwable;

	public List<com.egil.pts.modal.CapacityPlanning> getCapacityNyNetworkCode(
			List<Long> networkCode, Long year) throws Throwable;
	
	public List<com.egil.pts.modal.CapacityPlanning> getUserCapacityByName(SearchSortContainer search);

	public List<User> getUnPlannedCapacityBySupervisor(SearchSortContainer search,boolean bySupervisor)		throws Throwable;

}
