package com.egil.pts.dao.inventory;

import java.util.List;
import java.util.Set;

import com.egil.pts.dao.common.GenericDao;
import com.egil.pts.dao.domain.NetworkCodes;
import com.egil.pts.modal.Pagination;
import com.egil.pts.modal.ReleaseTrainDetails;
import com.egil.pts.modal.ResourceEffort;
import com.egil.pts.modal.SearchSortContainer;
import com.egil.pts.modal.StableTeams;

public interface NetworkCodesDao extends GenericDao<NetworkCodes, Long> {
	public List<NetworkCodes> getNetworkCodes(Pagination pagination, SearchSortContainer searchSortObj,
			String searchValue, Set<Long> idList, List<Integer> stableList) throws Throwable;

	public Integer deleteNetworkCodes(List<Long> networkCodeIdList) throws Throwable;

	public int getNetworkCodesCount(SearchSortContainer searchSortObj) throws Throwable;

	public NetworkCodes getNetworkCode(Long id) throws Throwable;

	public List<NetworkCodes> getResourceNetworkCodes(Pagination pagination, SearchSortContainer searchSortObj,
			String searchValue, Set<Long> idList) throws Throwable;

	public int getResourceNetworkCodesCount(SearchSortContainer searchSortObj) throws Throwable;

	public List<ReleaseTrainDetails> getReleaseTrainDetails(Long userId, Long platformId, Long applicationId,
			Long project, Long projectManager, String projectStage, Pagination pagination, long stableTeamId);

	public int getReleaseTrainDetailsCount(Long userId, Long platformId, Long applicationId, Long projectId,
			Long projectManager, String projectStage);

	public int updatePMProjectPhase(com.egil.pts.modal.NetworkCodes ntw, String comments) throws Throwable;

	public void updateProjectTFData(List<com.egil.pts.modal.NetworkCodes> projectMap);

	public List<ResourceEffort> getSupervisorNWEffort(Long supervisor, Long pillar, Long project, String reportType,
			Integer month);

	public List<StableTeams> getStableTeams();

	public Long getStableTeamByNw(String networkCodeId);

	public List<com.egil.pts.modal.NetworkCodes> getMislaniousNWCodes();

	public List<StableTeams> getStableTeamsByUser(Long user);

	public void saveStableTeamsByUser(List<StableTeams> data);

	public List<Integer> getStableTeams(Long userId);

	public List<StableTeams> getStableTeamsForUser();

	public List<StableTeams> getNonStableTeamsForUser(long supervisorId);

	public List<com.egil.pts.modal.NetworkCodes> getAllUserEffort(String fromDate, String toDate);

	public List<com.egil.pts.modal.NetworkCodes>  getUnchargedUserEffort(long userid);

	public List<StableTeams> getStableTeamsForUser(Long userid);

	public void saveUserContributions(Long id);
}
