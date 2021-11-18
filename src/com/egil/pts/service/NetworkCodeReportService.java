package com.egil.pts.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.egil.pts.modal.NetworkCodeEffort;
import com.egil.pts.modal.Pagination;
import com.egil.pts.modal.SummaryResponse;
import com.egil.pts.modal.UserCapacity;

public interface NetworkCodeReportService {

	public SummaryResponse<NetworkCodeEffort> getNetworkCodeUtilizationSummary(
			long userId, long networkId, String fromDate, String toDate,
			String nextWeekOfFromDate, String prevWeekOfToDate,
			String startDay, String endDay, Pagination pagination,
			boolean isAdmin) throws Throwable;

	public List<UserCapacity> getMonthlyNCUsageByUser(int year, Long userId,
			Long ncId) throws Throwable;

	public List<NetworkCodeEffort> getNetworkCodesEffortOfProject(int year,
			Long projectId, Long ncId, Long supervisorId, boolean isAdmin,String type,String satus,String fromDate,String toDate)
			throws Throwable;

	public List<NetworkCodeEffort> getUserProjectNC(int year, long networkId,
			Long supervisorId, boolean isAdmin,String status) throws Throwable;

	public void getPillarsMap(Map<Long, String> pillarsMapObj,
			String searchSupervisor, boolean isAdmin) throws Throwable;

	public void getProjectsMap(Map<Long, String> projectsMapObj, String pillar,
			String searchSupervisor, boolean isAdmin) throws Throwable;
	
	public void getReleaseMap(Map<Long, String> releasesMapObj,
			String project, String searchSupervisor, boolean isAdmin, String pillar, String searchProjectManager,String byStatus)
			throws Throwable;
}
