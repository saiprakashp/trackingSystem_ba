package com.egil.pts.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.egil.pts.modal.BulkResponse;
import com.egil.pts.modal.NetworkCodeEffort;
import com.egil.pts.modal.NetworkCodes;
import com.egil.pts.modal.Pagination;
import com.egil.pts.modal.SearchSortContainer;
import com.egil.pts.modal.StableTeams;
import com.egil.pts.modal.SummaryResponse;

public interface NetworkCodesService {

	public SummaryResponse<NetworkCodes> getNetworkCodesSummary(Pagination pagination,
			SearchSortContainer searchSortObj) throws Throwable;

	public void createNetworkCode(NetworkCodes networkCode) throws Throwable;

	public void modifyNetworkCode(NetworkCodes networkCode) throws Throwable;

	public Integer deleteNetworkCodes(List<Long> networkCodeIdList) throws Throwable;

	public void getNetworkCodesMap(Map<Long, String> networkCodesMap, String searchValue, Set<Long> idList)
			throws Throwable;

	public void getResourceNetworkCodes(Long userId, Map<Long, String> assignedNetworkCodesMap, String searchValue,
			Set<Long> idList) throws Throwable;

	public void getNetworkCodeResources(boolean showAllResources, Long superisorId, Long networkCodeId,
			Map<Long, String> selectedEmployeesMap) throws Throwable;

	public void getNetworkCodeResources(Long superisorId, Long networkCodeId, Map<Long, String> selectedEmployeesMap,
			String searchValue, Set<Long> idList) throws Throwable;

	public void mapNetworkCodeToResources(Long superisorId, Long selectedNetworkCodeId,
			List<Long> selectedEmployeeNames) throws Throwable;

	public List<NetworkCodeEffort> getDashBoardDetails(Long userId, boolean isAdmin) throws Throwable;

	public List<NetworkCodeEffort> getUserDashBoardDetails(Long userId) throws Throwable;

	public String getJSONStringOfStatus() throws Throwable;

	public String getJSONStringOfProjectCategory() throws Throwable;

	public String getJSONStringOfProjectSubCategory(String selectedProjectCategory) throws Throwable;

	public void getAssignedNetworkCodesMap(Long userId, Map<Long, String> assignedNetworkCodesMap, Date fromDate,
			String role) throws Throwable;

	public void getNetworkCodesMap(Map<Long, String> networkCodesMap) throws Throwable;

	public String getRemainingHrsForNetworkCode(Long networkCodeId, String userType, String stream);

	public String getJSONStringOfProductOwners() throws Throwable;

	public void getProjectCategoryMap(Map<String, String> projectCategoryMap) throws Throwable;

	public void getProjectSubCategoryMap(Map<String, String> projectSubCategoryMap) throws Throwable;

	public void getStatusMap(Map<String, String> statusMap) throws Throwable;

	public Map<Long, String> getProductOwnersMap() throws Throwable;

	public NetworkCodes getNetworkCodeById(Long id) throws Throwable;

	public String getJSONStringOfNetworkCodes(Long userId) throws Throwable;

	public BulkResponse downloadNetworkCodes(String userId, HashMap<String, String> ncColHeaders,
			SearchSortContainer searchSortBean, String fileName) throws Throwable;

	public String getJSONStringOfPhase() throws Throwable;

	public void updateProjectPhase(String updatedBy, String projectStageCol, Long valueOf, String projectStage,
			String description, String comments) throws Throwable;

	public void updateProject(com.egil.pts.modal.NetworkCodes networkCodes, String comments) throws Throwable;

	public void updateInsertProjectData(String uploadFileFileName);

	public void getStableTeamsMap(Map<Long, String> stableTeamsmap);

	public List<StableTeams> getStableTeams(List<StableTeams> stableTeams);

	public Long getStableTeamByNW(String networkCodeId);

	public List<NetworkCodes> getMislaneiousProjects();

	public List<StableTeams> getUserStableTeams(Long user);

	public void saveUserStableTeams(List<StableTeams> data);

	public List<NetworkCodeEffort> getUserDashBoardLoeDetails(Long valueOf, boolean showAllMyContributions);

	public void getNetworkCodesStablesMap(Long userId, Map<Long, String> networkCodesMap) throws Throwable;

	public void getAssignedNetworkCodesMapDeleted(Long selectedEmployee,
			Map<Long, String> selectedNetworkCodesMapDeleted, Date fromDate) throws Throwable;

	public List<NetworkCodes> getUserNwStableContribution(Pagination paginationObject,
			List<NetworkCodes> networkGridModel, String release);

	public boolean addUpdateUserNwStableContribution(List<com.egil.pts.modal.NetworkCodes> codes, SearchSortContainer searchSortContainer, Long totalStoryPoints);

	public void getUserCapacityByRelease(Map<Long, Long> userCapacityByRelease, Long selectedEmployee);
}
