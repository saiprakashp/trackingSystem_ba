package com.egil.pts.service;

import java.util.List;
import java.util.Map;

import com.egil.pts.modal.ActivityCodes;
import com.egil.pts.modal.Pagination;
import com.egil.pts.modal.Project;
import com.egil.pts.modal.SearchSortContainer;
import com.egil.pts.modal.SummaryResponse;

public interface ActivityCodesService {

	public SummaryResponse<ActivityCodes> getActivityCodesSummary(Pagination pagination,
			SearchSortContainer searchSortContainer) throws Throwable;

	public List<ActivityCodes> getActivityCodes(Pagination pagination, SearchSortContainer searchSortContainer)
			throws Throwable;

	public void createActivityCode(ActivityCodes activityCode) throws Throwable;

	public void modifyActivityCode(ActivityCodes activityCode) throws Throwable;

	public Integer deleteActivityCodes(List<Long> activityCodeIdList) throws Throwable;

	public void getActivityCodesMap(Map<Long, String> activityCodesMap) throws Throwable;

	public List<ActivityCodes> getActivityCodesPtl(Pagination pagination, SearchSortContainer searchSortContainer)
			throws Throwable;

	public List<ActivityCodes> getActivityCodesProject(Pagination pagination, SearchSortContainer searchSortContainer)
			throws Throwable;

	public void getActivityCodesMapPtl(Map<Long, String> activityCodesPtlMap) throws Throwable;

	public void getActivityCodesMapProject(Map<Long, String> activityCodesProjectMap) throws Throwable;

	public void getProjectMapNew(Map<Long, String> projectAssignement);

	public Long getActivityCodeIds(String type, String activityType);

	public List<Project> getProjectMapList();

	public List<ActivityCodes> getActivityCodesManagement(Pagination pagination,
			SearchSortContainer searchSortContainer) throws Throwable;

	public void getActivityCodesManagement(Map<Long, String> activityCodesManagementMap) throws Throwable;

}
