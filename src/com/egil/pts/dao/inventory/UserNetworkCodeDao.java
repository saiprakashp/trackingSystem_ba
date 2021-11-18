package com.egil.pts.dao.inventory;

import java.util.Date;
import java.util.List;
import java.util.Set;

import com.egil.pts.dao.common.GenericDao;
import com.egil.pts.dao.domain.NetworkCodes;
import com.egil.pts.dao.domain.UserNetworkCodes;
import com.egil.pts.modal.Pagination;
import com.egil.pts.modal.SearchSortContainer;
import com.egil.pts.modal.StableTeams;
import com.egil.pts.modal.User;

public interface UserNetworkCodeDao extends GenericDao<UserNetworkCodes, Long> {
	public void saveUserNetworkCodes(List<UserNetworkCodes> userNetworkCodesList)
			throws Throwable;

	public List<NetworkCodes> getResourceNetworkCodes(Long userId,
			String searchValue, Set<Long> idList) throws Throwable;
	
	public List<com.egil.pts.dao.domain.User> getNetworkCodeResources(Long supervisorId,
			Long networkCodeId) throws Throwable;

	public List<User> getNetworkCodeResources(Long supervisorId,
			Long networkCodeId, String searchValue,
			Set<Long> idList) throws Throwable;

	public List<com.egil.pts.modal.NetworkCodes> getResourceNetworkCodesDetails(Long userId, Date date, String role)
			throws Throwable;

	public List<UserNetworkCodes> getNetworkCodeResourcesDetails(
			Long supervisorId, Long networkCode) throws Throwable;

	public Integer deleteUserNetworkCodes(Long userId, Long networkCodeId, Long supervisorId, 
			List<Long> userIdList, List<Long> networkCodeIdList)
			throws Throwable;
	
	public List<Long> getNetworkCodeIds(Long userId);
	
	public List<Long> getUserIds(Long networkCodeId, Long supervisorId);
	
	public List<Object[]> getUserNetworkUnliked(boolean flag,Long networkCodeId, Long supervisorId);
	
	public String getRemainingHrsForNetworkCode(Long networkCodeId, String userType, String stream);

	public List<com.egil.pts.modal.NetworkCodes> getUserNetworkCodes(
			Pagination pagination, SearchSortContainer searchSortContainer,
			boolean isAdmin,String byStatus);
	
	public boolean  addNWDataforinterns(Long id );

	boolean checkAndDeleteUserNW(Long id);

	public int saveUserStableTeams(List<StableTeams> data,Long userId);

	public List getTimeSheetTypeMap();

	public List<com.egil.pts.modal.NetworkCodes> getResourceNetworkCodesDetailsDeleted(Long userId, Date date);

	public List<com.egil.pts.modal.NetworkCodes> getUserNwStableContribution(Pagination paginationObject,
			String release);

	public boolean addUpdateUserNwStableContribution(List<com.egil.pts.modal.NetworkCodes> codes, SearchSortContainer searchSortContainer, Long totalStoryPoints);

	public List getUserCapacityByRelease(Long selectedEmployee);
	
}

