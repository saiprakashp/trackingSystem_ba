package com.egil.pts.dao.inventory;

import java.util.List;

import com.egil.pts.dao.common.GenericDao;
import com.egil.pts.dao.domain.SupervisorResourceUtilization;
import com.egil.pts.dao.domain.UserUtilization;
import com.egil.pts.modal.EssDetails;
import com.egil.pts.modal.LocationUserCount;
import com.egil.pts.modal.NetworkCodeEffort;
import com.egil.pts.modal.Pagination;
import com.egil.pts.modal.ResourceEffort;
import com.egil.pts.modal.ResourceUtilization;
import com.egil.pts.modal.SearchSortContainer;
import com.egil.pts.modal.UserCapacity;
import com.egil.pts.modal.Utilization;

public interface UserUtilizationDao extends GenericDao<UserUtilization, Long> {
	public List<NetworkCodeEffort> getUsersUtilizationDetailsSummary(long userId, List<String> weekList,
			Long searchValue) throws Throwable;

	public List<UserUtilization> getUserUtilizationDetails(List<Long> userId, List<String> weekList) throws Throwable;

	public void saveUsetUtilization(List<UserUtilization> userEffortList) throws Throwable;

	public int removeUserUtilization(List<Long> userId, List<String> weekList) throws Throwable;

	public List<NetworkCodeEffort> getDashBoardDetails(Long userId, boolean isAdmin) throws Throwable;

	public List<NetworkCodeEffort> getUserDashBoardDetails(Long userId) throws Throwable;

	public List<NetworkCodeEffort> getNetworkCodeReport(Long networkCodeId) throws Throwable;
 

	public List<Utilization> getUserUtilizationSummary(Long supervisorId, String fromDate, String toDate, Long userId,
			String reporteeType, Pagination pagination, boolean delinquentEntries, List<String> weeksList,
			long stableTeamid, Long networkCodeId, Long projectManagerId) throws Throwable ;
	
	public int getUserUtilizationCount(Long supervisorId, String fromDate, String toDate, Long userId,
			String reporteeType, boolean delinquentEntries, List<String> weeksList, long stableId, Long nwId,
			Long projectManagerId) throws Throwable;

	public List<ResourceEffort> getResourceUtilizationDetails(long supervisorId, String fromDate, String toDate,
			Long userId, String reporteeType) throws Throwable;

	public int getNCUtilizationCount(long userId, Long networkId, String fromDate, String toDate,
			String nextWeekOfFromDate, String prevWeekOfToDate, String startDay, String endDay, boolean isAdmin)
			throws Throwable;

	public List<NetworkCodeEffort> getNCUtilizationSummary(long userId, Long networkId, String fromDate, String toDate,
			String nextWeekOfFromDate, String prevWeekOfToDate, String startDay, String endDay, Pagination pagination,
			boolean isAdmin) throws Throwable;

	public void getMonthlyNCUsageByUser(String startDay, String endDay, String startWeek, String endWeek, Long userId,
			String month, int year, List<UserCapacity> userCapacity, Long ncId) throws Throwable;

	public int getRICOUserUtilizationCount(boolean userFlag, Long userId, String startWeek, String endWeek,
			String startDay, String endDay, Long projectManagerId) throws Throwable;

	public List<Utilization> getRICOUserUtilizationSummary(boolean userFlag, Long userId, String startWeek,
			String endWeek, String startDay, String endDay, Pagination pagination, Long projectManagerId);

	public String getUnApprovedHours(Long supervisorId, String startWeek) throws Throwable;

	public List<LocationUserCount> getLocationUserCnt(Long supervisorId, boolean teamFlag, String status)
			throws Throwable;

	public List<Double> getDashboardEffortDetails_New(Long supervisorId, String startWeek, String endWeek,
			String startDay, String endDay, Long locationId) throws Throwable;

	public Double getDashboardEffortDetails(Long supervisorId, String startWeek, String endWeek, String startDay,
			String endDay, boolean teamFlag, boolean detail) throws Throwable;

	public List<ResourceUtilization> getResourceUtilization(Long userId, String month, String startWeek, String endWeek,
			String startDay, String endDay, Pagination pagination, boolean descrepencyFlag) throws Throwable;

	public int getResourceUtilizationCount(Long userId, String month, String startWeek, String endWeek, String startDay,
			String endDay) throws Throwable;

	public List<EssDetails> getDashboardEffortESSDetails(Long supervisorId, int year, String month, boolean teamFlag,
			String endDateOfMonth, boolean detail);

	public List<LocationUserCount> getLocationUserCntNew(Long supervisorId, boolean teamFlag, int monthSelected,
			int year, String status, SearchSortContainer searchSortContainer, String role);

	public void saveUserUtilization(List<SupervisorResourceUtilization> dashbordDetails);

	public List<com.egil.pts.modal.SupervisorResourceUtilization> getUserUtilization(Long user, Long selectedYear,
			Long selectedMonth);

	public void saveUserUtilizationForMonth();

	public void saveUserUtilizationForYear();

	public List<LocationUserCount> getLocationUserCntNew1(Long supervisorId, boolean teamFlag, int monthSelected,
			int year, String status, SearchSortContainer searchSortContainer, String role);

	public List<NetworkCodeEffort> getUserDashBoardLoeDetails(Long userId, boolean showAllMyContributions);

	public List<ResourceUtilization> getResourceUtilizationPendingEss(String startWeek, String endWeek, Long userId,
			String selectedMonth, Pagination pagination);

	public List<Utilization> getUserUtilizationSummaryAddUnCharged(Long supervisorId, String fromDate, String toDate,
			Long searchValue, String reporteeType, Pagination pagination, boolean delinquentEntries,
			List<String> weeksList, long stableTeamid, Long networkCodeId, Long projectManagerId);

	public int getUserUtilizationSummaryAddUnChargedCount(Long supervisorId, String fromDate, String toDate,
			Long searchValue, String reporteeType, Pagination pagination, boolean delinquentEntries,
			List<String> weeksList, long stableTeamid, Long networkCodeId, Long projectManagerId);
}
