package com.egil.pts.service.impl;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.internal.util.StringHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.egil.pts.modal.BulkResponse;
import com.egil.pts.modal.NetworkCodeEffort;
import com.egil.pts.modal.Pagination;
import com.egil.pts.modal.ReleaseTrainDetails;
import com.egil.pts.modal.ReportQueryMapper;
import com.egil.pts.modal.ResourceEffort;
import com.egil.pts.modal.SearchSortContainer;
import com.egil.pts.modal.SummaryResponse;
import com.egil.pts.modal.TechCompetencyScore;
import com.egil.pts.modal.UserCapacity;
import com.egil.pts.modal.UserReport;
import com.egil.pts.service.NetworkCodesService;
import com.egil.pts.service.PTSReportsService;
import com.egil.pts.service.common.BaseUIService;
import com.egil.pts.service.common.QueryReportFactory;
import com.egil.pts.util.ChartOptions;
import com.egil.pts.util.GenericJFreeChart;

@Service("ptsReportsService")
public class PTSReportsServiceImpl extends BaseUIService implements PTSReportsService {

	@Autowired(required = true)
	@Qualifier("networkCodesService")
	private NetworkCodesService networkCodesService;

	@Override
	public byte[] getNetworkCodeReport(Long networkCodeId) throws Throwable {
		List<ChartOptions> chartOptionsList = new ArrayList<ChartOptions>();
		List<NetworkCodeEffort> nteffortList = daoManager.getUserUtilizationDao().getNetworkCodeReport(networkCodeId);
		double effort = 0d;
		String networkCode = "";
		for (NetworkCodeEffort nceffort : nteffortList) {
			if (networkCode.equals("")) {
				networkCode = nceffort.getNETWORKCODE();
			}
			ChartOptions chartOption = new ChartOptions();
			effort = effort + nceffort.getSUMMATION();
			chartOption.setValue(effort);
			chartOption.setComparableColumnKey(nceffort.getWEEK());
			chartOption.setComparableRowKey(networkCode);
			chartOptionsList.add(chartOption);
		}

		GenericJFreeChart gjf = new GenericJFreeChart(networkCode + " effort", "Weeks", "Hours");
		File f = gjf.createChart(networkCode + " Effort", "lineChart", chartOptionsList);
		byte[] imgBytes = new byte[(int) f.length()];
		DataInputStream imageDataStream = new DataInputStream(new FileInputStream(f));
		imageDataStream.readFully(imgBytes);
		imageDataStream.close();
		return imgBytes;
	}

	public byte[] getNetworkCodesStatisticReport(Long userId, boolean isAdmin) throws Throwable {
		byte[] imgBytes = null;
		List<ChartOptions> chartOptionsList = new ArrayList<ChartOptions>();
		List<NetworkCodeEffort> nteffortList = networkCodesService.getDashBoardDetails(userId, isAdmin);
		for (NetworkCodeEffort nceffort : nteffortList) {
			ChartOptions chartOption = new ChartOptions();
			chartOption.setValue(nceffort.getSUMMATION() == null ? 0.0 : nceffort.getSUMMATION());
			chartOption.setComparableColumnKey(nceffort.getNETWORKCODE());
			chartOption.setComparableRowKey("Hours Consumed");
			chartOptionsList.add(chartOption);

			chartOption = new ChartOptions();
			chartOption.setValue(nceffort.getEFFORT());
			chartOption.setComparableColumnKey(nceffort.getNETWORKCODE());
			chartOption.setComparableRowKey("Total Hours");
			chartOptionsList.add(chartOption);
		}
		if (chartOptionsList != null && chartOptionsList.size() > 0) {
			GenericJFreeChart gjf = new GenericJFreeChart("Network Codes effort", "Network Codes", "Hours");
			File f = gjf.createChart("Effort", "layeredBarChart", chartOptionsList);
			imgBytes = new byte[(int) f.length()];
			DataInputStream imageDataStream = new DataInputStream(new FileInputStream(f));
			imageDataStream.readFully(imgBytes);
			imageDataStream.close();
		}
		return imgBytes;

	}

	@Override
	public void getUserContributionReport(String ricoLocation, List<UserReport> userReportList,
			Map<String, Map<String, UserReport>> contributionReportMap, Map<String, Float> platformUserContributionCnt,
			Map<String, Map<String, Float>> streamHCCnt, Map<String, Integer> streamAppMap,
			Map<String, float[]> streamCountMap, Map<String, Float> platformCountMap, Long location, Long pillarId,
			Long stream, Long supervisorId, boolean isAppBased) {
		DecimalFormat df = new DecimalFormat();
		df.setMaximumFractionDigits(2);
		if (isAppBased) {
			streamAppMap.clear();
			List<UserReport> userContributionReportList = new ArrayList<UserReport>();
			userContributionReportList.addAll(this.daoManager.getUserDao().getLocationUserContributionReport(
					ricoLocation, location, pillarId, stream, supervisorId, isAppBased));
			Map<String, UserReport> platformDetMap = new LinkedHashMap<String, UserReport>();

			Map<String, Float> streamUserCnt = new LinkedHashMap<String, Float>();
			int locationTot = 0;
			String projName = userContributionReportList.get(0).getProjectName();
			for (UserReport ur : userContributionReportList) {
				if (projName.equalsIgnoreCase(ur.getProjectName())) {
					locationTot += 1;
				} else {
					break;
				}
			}
			for (UserReport ur : userContributionReportList) {
				// for platform header in jsp
				if (streamAppMap != null && streamAppMap.containsKey(ur.getPlatform())) {
					streamAppMap.put(ur.getPlatform(), (streamAppMap.get(ur.getPlatform()) + 1));
				} else {
					streamAppMap.put(ur.getPlatform(), 1);
				}

				streamHCCnt.put(ur.getProjectName(), streamUserCnt);
				if (platformUserContributionCnt.containsKey(ur.getPlatform())) {
					if (ur.getPlatform().equalsIgnoreCase("Admin")) {
						platformUserContributionCnt.put(ur.getPlatform(),
								platformUserContributionCnt.get(ur.getPlatform()) + ur.getAdminUserCnt());
					} else {
						platformUserContributionCnt.put(ur.getPlatform(),
								platformUserContributionCnt.get(ur.getPlatform()) + ur.getTotalLocUserCnt());
					}
				} else {
					if (ur.getPlatform().equalsIgnoreCase("Admin")) {
						platformUserContributionCnt.put(ur.getPlatform(), ur.getAdminUserCnt());
					} else {
						platformUserContributionCnt.put(ur.getPlatform(), ur.getTotalLocUserCnt());
					}
				}

				if (contributionReportMap.containsKey(ur.getLocation())) {
					contributionReportMap.get(ur.getLocation()).put(ur.getProjectName(), ur);
				} else {
					platformDetMap = new LinkedHashMap<String, UserReport>();
					platformDetMap.put(ur.getProjectName(), ur);
					contributionReportMap.put(ur.getLocation(), platformDetMap);
				}

				if (ur.getPlatform().equalsIgnoreCase("Admin")) {
					streamUserCnt = new LinkedHashMap<String, Float>();
					streamUserCnt.put("Admin", ur.getAdminUserCnt());
					streamUserCnt.put("AdminTotal", ur.getAdminUserCnt());
					streamHCCnt.put(ur.getProjectName(), streamUserCnt);
				} else {
					streamUserCnt = new LinkedHashMap<String, Float>();
					streamUserCnt.put("Test", ur.getTestUserCnt());
					streamUserCnt.put("Dev", ur.getDevUserCnt());
					streamUserCnt.put("PM", ur.getPmUserCnt());
					streamUserCnt.put("SE", ur.getSeUserCnt());
					streamUserCnt.put("PTL", ur.getPtlUserCnt());
					streamUserCnt.put("Total", ur.getTotalLocUserCnt());
				}
			}
			int totalSiz = streamAppMap.size();
			int i = 0;
			for (String key : streamAppMap.keySet()) {
				if (i == totalSiz) {
					streamAppMap.put(key, streamAppMap.get(key) / locationTot + 1);// to get platform header map
				} else {
					streamAppMap.put(key, streamAppMap.get(key) / locationTot);// to get platform header map
				}
				i++;
			}

		}
		// non app logic
		else {
			userReportList.clear();
			userReportList.addAll(this.daoManager.getUserDao().getLocationUserCount(ricoLocation, null, null, null));
			UserReport totRec = new UserReport();
			totRec.setLocation("Total");
			int testUsrCnt = 0;
			int devUsrCnt = 0;
			int pmUsrCnt = 0;
			int seUsrCnt = 0;
			int ptlUsrCnt = 0;
			int adminUsrCnt = 0;
			int totUsrCnt = 0;
			for (UserReport usrRpt : userReportList) {
				testUsrCnt += usrRpt.getTestUserCnt();
				devUsrCnt += usrRpt.getDevUserCnt();
				pmUsrCnt += usrRpt.getPmUserCnt();
				seUsrCnt += usrRpt.getSeUserCnt();
				ptlUsrCnt += usrRpt.getPtlUserCnt();
				adminUsrCnt += usrRpt.getAdminUserCnt();
				totUsrCnt += usrRpt.getTotalLocUserCnt();
			}
			totRec.setTestUserCnt(Float.valueOf(testUsrCnt));
			totRec.setDevUserCnt(Float.valueOf(devUsrCnt));
			totRec.setPmUserCnt(Float.valueOf(pmUsrCnt));
			totRec.setSeUserCnt(Float.valueOf(seUsrCnt));
			totRec.setPtlUserCnt(Float.valueOf(ptlUsrCnt));
			totRec.setAdminUserCnt(Float.valueOf(adminUsrCnt));
			totRec.setTotalLocUserCnt(Float.valueOf(totUsrCnt));
			userReportList.add(totRec);

			List<UserReport> userContributionReportList = new ArrayList<UserReport>();
			userContributionReportList.addAll(this.daoManager.getUserDao().getLocationUserContributionReport(
					ricoLocation, location, pillarId, stream, supervisorId, isAppBased));
			Map<String, UserReport> platformDetMap = new LinkedHashMap<String, UserReport>();

			Map<String, Float> streamUserCnt = new LinkedHashMap<String, Float>();

			for (UserReport ur : userContributionReportList) {

				if (platformUserContributionCnt.containsKey(ur.getPlatform())) {
					if (ur.getPlatform().equalsIgnoreCase("Admin")) {
						platformUserContributionCnt.put(ur.getPlatform(), Float.parseFloat(
								df.format(platformUserContributionCnt.get(ur.getPlatform()) + ur.getAdminUserCnt())));
					} else {
						platformUserContributionCnt.put(ur.getPlatform(), Float.parseFloat(df
								.format(platformUserContributionCnt.get(ur.getPlatform()) + ur.getTotalLocUserCnt())));
					}
				} else {
					if (ur.getPlatform().equalsIgnoreCase("Admin")) {
						platformUserContributionCnt.put(ur.getPlatform(),
								Float.parseFloat(df.format(ur.getAdminUserCnt())));
					} else {
						platformUserContributionCnt.put(ur.getPlatform(),
								Float.parseFloat(df.format(ur.getTotalLocUserCnt())));
					}
				}

				if (contributionReportMap.containsKey(ur.getLocation())) {
					contributionReportMap.get(ur.getLocation()).put(ur.getPlatform(), ur);
				} else {
					platformDetMap = new LinkedHashMap<String, UserReport>();
					platformDetMap.put(ur.getPlatform(), ur);
					contributionReportMap.put(ur.getLocation(), platformDetMap);
				}

				if (streamHCCnt.containsKey(ur.getPlatform())) {

					if (ur.getPlatform().equalsIgnoreCase("Admin")) {
						streamUserCnt = new LinkedHashMap<String, Float>();
						if (streamHCCnt.get(ur.getPlatform()).containsKey("Admin")) {
							streamUserCnt.put("Admin",
									((Float) streamHCCnt.get(ur.getPlatform()).get("Admin") + ur.getAdminUserCnt()));
						}
						if (streamHCCnt.get(ur.getPlatform()).containsKey("AdminTotal")) {
							streamUserCnt.put("AdminTotal", ((Float) streamHCCnt.get(ur.getPlatform()).get("AdminTotal")
									+ ur.getAdminUserCnt()));
						}
						streamHCCnt.put(ur.getPlatform(), streamUserCnt);
					} else {
						streamUserCnt = new LinkedHashMap<String, Float>();
						if (streamHCCnt.get(ur.getPlatform()).containsKey("Test")) {
							streamUserCnt.put("Test", (Float.parseFloat(
									df.format(streamHCCnt.get(ur.getPlatform()).get("Test") + ur.getTestUserCnt()))));
						}
						if (streamHCCnt.get(ur.getPlatform()).containsKey("Dev")) {
							streamUserCnt.put("Dev", (Float.parseFloat(
									df.format(streamHCCnt.get(ur.getPlatform()).get("Dev") + ur.getDevUserCnt()))));
						}
						if (streamHCCnt.get(ur.getPlatform()).containsKey("PM")) {
							streamUserCnt.put("PM", (Float.parseFloat(
									df.format(streamHCCnt.get(ur.getPlatform()).get("PM") + ur.getPmUserCnt()))));
						}
						if (streamHCCnt.get(ur.getPlatform()).containsKey("SE")) {
							streamUserCnt.put("SE", (Float.parseFloat(
									df.format(streamHCCnt.get(ur.getPlatform()).get("SE") + ur.getSeUserCnt()))));
						}
						if (streamHCCnt.get(ur.getPlatform()).containsKey("PTL")) {
							streamUserCnt.put("PTL", (Float.parseFloat(
									df.format(streamHCCnt.get(ur.getPlatform()).get("PTL") + ur.getPtlUserCnt()))));
						}
						if (streamHCCnt.get(ur.getPlatform()).containsKey("Total")) {
							streamUserCnt.put("Total", (Float.parseFloat(df.format(
									streamHCCnt.get(ur.getPlatform()).get("Total") + ur.getTotalLocUserCnt()))));
						}
						streamHCCnt.put(ur.getPlatform(), streamUserCnt);
					}
				} else {
					if (ur.getPlatform().equalsIgnoreCase("Admin")) {
						streamUserCnt = new LinkedHashMap<String, Float>();
						streamUserCnt.put("Admin", (Float.parseFloat(df.format(ur.getAdminUserCnt()))));
						streamUserCnt.put("AdminTotal", (Float.parseFloat(df.format(ur.getAdminUserCnt()))));
						streamHCCnt.put(ur.getPlatform(), streamUserCnt);
					} else {
						streamUserCnt = new LinkedHashMap<String, Float>();
						streamUserCnt.put("Test", Float.parseFloat(df.format(ur.getTestUserCnt())));
						streamUserCnt.put("Dev", Float.parseFloat(df.format(ur.getDevUserCnt())));
						streamUserCnt.put("PM", Float.parseFloat(df.format(ur.getPmUserCnt())));
						streamUserCnt.put("SE", Float.parseFloat(df.format(ur.getSeUserCnt())));
						streamUserCnt.put("PTL", Float.parseFloat(df.format(ur.getPtlUserCnt())));
						streamUserCnt.put("Total", Float.parseFloat(df.format(ur.getTotalLocUserCnt())));
						streamHCCnt.put(ur.getPlatform(), streamUserCnt);
					}
				}
			}
		}

		if (isAppBased) {
			contributionReportMap.forEach((loc, projMap) -> {
				projMap.forEach((projNam, userObj) -> {
					if (platformCountMap.containsKey(projNam)) {
						platformCountMap.put(projNam,
								Float.valueOf(df.format(platformCountMap.get(projNam) + userObj.getTotalLocUserCnt())));
					} else {
						platformCountMap.put(projNam, Float.valueOf(df.format(userObj.getTotalLocUserCnt())));
					}
					if (streamCountMap.containsKey(projNam)) {
						streamCountMap.put(projNam, new float[] {
								Float.valueOf(df.format(streamCountMap.get(projNam)[0] + userObj.getTestUserCnt())),
								Float.valueOf(df.format(streamCountMap.get(projNam)[1] + userObj.getDevUserCnt())),
								Float.valueOf(df.format(streamCountMap.get(projNam)[2] + userObj.getPmUserCnt())),
								Float.valueOf(df.format(streamCountMap.get(projNam)[3] + userObj.getSeUserCnt())),
								Float.valueOf(df.format(streamCountMap.get(projNam)[4] + userObj.getPtlUserCnt())),
								Float.valueOf(
										df.format(streamCountMap.get(projNam)[5] + userObj.getTotalLocUserCnt())) });
					} else {
						streamCountMap.put(projNam,
								new float[] { Float.valueOf(df.format(userObj.getTestUserCnt())),
										Float.valueOf(df.format(userObj.getDevUserCnt())),
										Float.valueOf(df.format(userObj.getPmUserCnt())),
										Float.valueOf(df.format(userObj.getSeUserCnt())),
										Float.valueOf(df.format(userObj.getPtlUserCnt())),
										Float.valueOf(df.format(userObj.getTotalLocUserCnt())) });
					}

				});
			});
		}
	}

	@Override
	public SummaryResponse<TechCompetencyScore> getTechScoreRecordDetails(String ricoLocation, Long techId,
			String primaryFlag, Long projectId, Pagination pagination, SearchSortContainer searchSortObj,
			Long selectedYear, String halfYear) throws Throwable {
		SummaryResponse<TechCompetencyScore> summary = new SummaryResponse<TechCompetencyScore>();

		summary.setTotalRecords(daoManager.getUserDao().getTechScoreRecordCount(ricoLocation, techId, primaryFlag,
				projectId, selectedYear, halfYear));
		summary.setEnitities(daoManager.getUserDao().getTechScoreRecordDetails(ricoLocation, techId, primaryFlag,
				projectId, pagination, searchSortObj, selectedYear, halfYear));

		return summary;

	}

	@Override
	public SummaryResponse<TechCompetencyScore> getCompScoreRecordDetails(String ricoLocation, Long pillarId,
			Long projectId, String primaryFlag, Long selectedYear, String halfYear, Pagination pagination,
			SearchSortContainer searchSortObj) {
		SummaryResponse<TechCompetencyScore> summary = new SummaryResponse<TechCompetencyScore>();

		summary.setTotalRecords(daoManager.getUserDao().getCompScoreRecordCount(ricoLocation, pillarId, projectId,
				primaryFlag, selectedYear, halfYear));
		summary.setEnitities(daoManager.getUserDao().getCompScoreRecordDetails(ricoLocation, pillarId, projectId,
				primaryFlag, pagination, searchSortObj, selectedYear, halfYear));

		return summary;
	}

	@Override
	public SummaryResponse<com.egil.pts.modal.User> getContributionDetailReport(Long supervisorId,
			Pagination pagination, Long location, Long pillarId, Long stream, String region) {
		SummaryResponse<com.egil.pts.modal.User> summary = new SummaryResponse<com.egil.pts.modal.User>();

		summary.setTotalRecords(daoManager.getUserDao().getContributionDetailReportCount(supervisorId, location,
				pillarId, stream, region));
		summary.setEnitities(daoManager.getUserDao().getContributionDetailReport(supervisorId, pagination, location,
				pillarId, stream, region));

		return summary;
	}

	public SummaryResponse<ReleaseTrainDetails> getReleaseTrainDetails(Long userId, Long platformId, Long applicationId,
			Long project, Long projectManager, String projectStage, Pagination pagination, long stableTeamId) {
		List<ReleaseTrainDetails> releaseTrainDetailList = new ArrayList<ReleaseTrainDetails>();
		List<String> feasibilityList = new ArrayList<String>();
		List<String> developmentList = new ArrayList<String>();
		List<String> systemTestList = new ArrayList<String>();
		List<String> deploymentList = new ArrayList<String>();
		List<String> holdList = new ArrayList<String>();
		List<String> noneList = new ArrayList<String>();

		SummaryResponse<ReleaseTrainDetails> summary = new SummaryResponse<ReleaseTrainDetails>();

		/*
		 * summary.setTotalRecords(
		 * daoManager.getNetworkCodesDao().getReleaseTrainDetailsCount(userId,
		 * platformId, applicationId, project));
		 */
		List<ReleaseTrainDetails> tempReleaseTrainDetailList = daoManager.getNetworkCodesDao().getReleaseTrainDetails(
				userId, platformId, applicationId, project, projectManager, projectStage, pagination,stableTeamId);

		for (ReleaseTrainDetails releaseTrainDetObj : tempReleaseTrainDetailList) {
			if (StringHelper.isNotEmpty(releaseTrainDetObj.getFeasibility())) {
				feasibilityList.add(releaseTrainDetObj.getFeasibility());
			}
			if (StringHelper.isNotEmpty(releaseTrainDetObj.getDevelopment())) {
				developmentList.add(releaseTrainDetObj.getDevelopment());
			}
			if (StringHelper.isNotEmpty(releaseTrainDetObj.getSystemTest())) {
				systemTestList.add(releaseTrainDetObj.getSystemTest());
			}
			if (StringHelper.isNotEmpty(releaseTrainDetObj.getDeployment())) {
				deploymentList.add(releaseTrainDetObj.getDeployment());
			}
			if (StringHelper.isNotEmpty(releaseTrainDetObj.getHold())) {
				holdList.add(releaseTrainDetObj.getHold());
			}
			if (StringHelper.isNotEmpty(releaseTrainDetObj.getNone())) {
				noneList.add(releaseTrainDetObj.getNone());
			}
		}
		int[] tempArr = { feasibilityList.size(), developmentList.size(), systemTestList.size(), deploymentList.size(),
				holdList.size(), noneList.size() };
		int maxListSize = 0;

		for (int i : tempArr) {
			maxListSize = i > maxListSize ? i : maxListSize;
		}

		for (int j = 0; j < maxListSize; j++) {
			ReleaseTrainDetails releaseTrainDetails = new ReleaseTrainDetails();
			if (feasibilityList.size() != 0 && j < feasibilityList.size()) {
				releaseTrainDetails.setFeasibility(feasibilityList.get(j));
			}
			if (developmentList.size() != 0 && j < developmentList.size()) {
				releaseTrainDetails.setDevelopment(developmentList.get(j));
			}
			if (systemTestList.size() != 0 && j < systemTestList.size()) {
				releaseTrainDetails.setSystemTest(systemTestList.get(j));
			}
			if (deploymentList.size() != 0 && j < deploymentList.size()) {
				releaseTrainDetails.setDeployment(deploymentList.get(j));
			}
			if (holdList.size() != 0 && j < holdList.size()) {
				releaseTrainDetails.setHold(holdList.get(j));
			}
			if (noneList.size() != 0 && j < noneList.size()) {
				releaseTrainDetails.setNone(noneList.get(j));
			}
			releaseTrainDetailList.add(releaseTrainDetails);
		}
		summary.setDeploymentLength(deploymentList.size());
		summary.setDevelopmentLength(developmentList.size());
		summary.setFeasibilityLength(feasibilityList.size());
		summary.setHoldLength(holdList.size());
		summary.setSystemTestLength(systemTestList.size());
		summary.setNoneLength(noneList.size());

		summary.setEnitities(releaseTrainDetailList);

		return summary;
	}

	@Override
	public List<Map<String, Object>> getQueryResultData(String query) throws Throwable {
		return daoManager.getPtsQueryUIDao().getQueryResultData(query);
	}

	@Override
	public Map<String, ReportQueryMapper> getReportMapper(String userName, Long userId) {

		return new QueryReportFactory()
				.getMappedObj(daoManager.getPtsReportQueryMapperDao().getReportMapper(), userName, userId).build();
	}

	@Override
	public boolean updateReportMapper(String queryOperation, String queryDecription, String selectedReport,
			String queryNeeded) {
		return daoManager.getPtsReportQueryMapperDao().saveUpdateReportMapper(queryOperation, queryDecription,
				selectedReport, queryNeeded);
	}

	@Override
	public List<UserCapacity> getSupervisorNWEffort(Long supervisor, Long pillar, Long project, String reportType,
			Integer month, Long year) {
		List<UserCapacity> userCapacity = daoManager.getUserDao().GenerateNWUtilizationDate(
				(year != null) ? year.intValue() : Calendar.getInstance().getWeekYear(), pillar, reportType, month,
				supervisor);

		return userCapacity;
	}

	@Override
	public List<Map<String, Object>> getQueryResultData(String query, String project, Date weekStarting,
			Date weekEndingString, String release, String type, String reportType) {
		return daoManager.getPtsQueryUIDao().getQueryResultData(query, project, weekStarting, weekEndingString, release,
				type, reportType);
	}

}