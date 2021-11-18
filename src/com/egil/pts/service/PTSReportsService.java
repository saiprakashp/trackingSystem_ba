package com.egil.pts.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.egil.pts.modal.Pagination;
import com.egil.pts.modal.ReleaseTrainDetails;
import com.egil.pts.modal.ReportQueryMapper;
import com.egil.pts.modal.ResourceEffort;
import com.egil.pts.modal.SearchSortContainer;
import com.egil.pts.modal.SummaryResponse;
import com.egil.pts.modal.TechCompetencyScore;
import com.egil.pts.modal.UserCapacity;
import com.egil.pts.modal.UserReport;

public interface PTSReportsService {

	public byte[] getNetworkCodeReport(Long networkCodeId) throws Throwable;

	public byte[] getNetworkCodesStatisticReport(Long userId, boolean isAdmin) throws Throwable;

	public SummaryResponse<TechCompetencyScore> getTechScoreRecordDetails(String region, Long techId, String priaryFlag,
			Long projectId, Pagination pagination, SearchSortContainer searchSortObj, Long selectedYear,
			String halfYear) throws Throwable;

	public SummaryResponse<TechCompetencyScore> getCompScoreRecordDetails(String ricoLocation, Long pillarId,
			Long projectId, String primaryFlag, Long selectedYear, String halfYear, Pagination paginationObject,
			SearchSortContainer searchSortObj);

	public SummaryResponse<com.egil.pts.modal.User> getContributionDetailReport(Long supervisorId,
			Pagination pagination, Long location, Long pillarId, Long stream, String region);

	public SummaryResponse<ReleaseTrainDetails> getReleaseTrainDetails(Long userId, Long platformId, Long applicationId,
			Long project, Long searchProjectManager, String projectStage, Pagination pagination, long stableTeamId);

	public void getUserContributionReport(String ricoLocation, List<UserReport> userReportList,
			Map<String, Map<String, UserReport>> contributionReportMap, Map<String, Float> platformUserContributionCnt,
			Map<String, Map<String, Float>> streamHCCnt, Map<String, Integer> streamAppMap,
			Map<String, float[]> streamCountMap, Map<String, Float> platformCountMap, Long location, Long pillarId,
			Long stream, Long supervisorId, boolean isAppBased);

	public List<Map<String, Object>> getQueryResultData(String query) throws Throwable;

	public Map<String, ReportQueryMapper> getReportMapper(String userName, Long userId);

	public boolean updateReportMapper(String queryOperation, String queryDecription,
			String selectedRepselectedReportort2, String queryNeeded);

	public List<UserCapacity> getSupervisorNWEffort(Long supervisor, Long pillar, Long project, String reportType,
			Integer month, Long year);

	public List<Map<String, Object>> getQueryResultData(String upperCase, String project, Date weekStarting,
			Date weekEndingString, String release, String type, String reportType);
}
