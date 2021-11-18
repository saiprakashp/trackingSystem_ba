package com.egil.pts.dao.inventory;

import java.util.List;

import com.egil.pts.dao.common.GenericDao;
import com.egil.pts.dao.domain.ActivityCodes;
import com.egil.pts.modal.Pagination;
import com.egil.pts.modal.SearchSortContainer;

public interface ActivityCodesDao extends GenericDao<ActivityCodes, Long> {
	public List<com.egil.pts.dao.domain.ActivityCodes> getActivityCodes(Pagination pagination,
			SearchSortContainer searchSortObj) throws Throwable;

	public int getActivityCodesCount(SearchSortContainer searchSortObj) throws Throwable;

	public Integer deleteActivityCodes(List<Long> networkCodeIdList) throws Throwable;

	public List<Object[]> getActivityCodesNew(SearchSortContainer searchSortObj) throws Throwable;

	public Integer deleteActivityCodesNew(List<Long> activityIds) throws Throwable;

	public List<Object[]> getProjectActivityDao();

	public Long getActivityCodes(String type, String activityType);

}
