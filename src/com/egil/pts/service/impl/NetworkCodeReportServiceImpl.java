package com.egil.pts.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.egil.pts.modal.NetworkCodeEffort;
import com.egil.pts.modal.NetworkCodes;
import com.egil.pts.modal.Pagination;
import com.egil.pts.modal.Project;
import com.egil.pts.modal.SearchSortContainer;
import com.egil.pts.modal.SummaryResponse;
import com.egil.pts.modal.UserCapacity;
import com.egil.pts.service.NetworkCodeReportService;
import com.egil.pts.service.common.BaseUIService;
import com.egil.pts.util.Utility;

@Service("networkCodeReportService")
public class NetworkCodeReportServiceImpl extends BaseUIService implements NetworkCodeReportService {

	@Override
	public SummaryResponse<NetworkCodeEffort> getNetworkCodeUtilizationSummary(long userId, long networkId,
			String fromDate, String toDate, String nextWeekOfFromDate, String prevWeekOfToDate, String startDay,
			String endDay, Pagination pagination, boolean isAdmin) throws Throwable {

		SummaryResponse<NetworkCodeEffort> summary = new SummaryResponse<NetworkCodeEffort>();
		summary.setTotalRecords(daoManager.getUserUtilizationDao().getNCUtilizationCount(userId, networkId, fromDate,
				toDate, nextWeekOfFromDate, prevWeekOfToDate, startDay, endDay, isAdmin));
		summary.setEnitities(daoManager.getUserUtilizationDao().getNCUtilizationSummary(userId, networkId, fromDate,
				toDate, nextWeekOfFromDate, prevWeekOfToDate, startDay, endDay, pagination, isAdmin));
		return summary;
	}

	@Override
	public List<UserCapacity> getMonthlyNCUsageByUser(int year, Long userId, Long ncId) throws Throwable {
		int startYear = 2015;
		List<UserCapacity> userCapacity = new ArrayList<UserCapacity>();
		Calendar c = Calendar.getInstance();
		int currentYear = c.get(Calendar.YEAR);
		if (year == 0) {
			for (int i = startYear; i < currentYear; i++) {
				if (i == startYear) {
					for (int j = 5; j <= 11; j++) {
						Calendar gc = new GregorianCalendar();
						gc.set(Calendar.YEAR, i);
						gc.set(Calendar.MONTH, j);
						gc.set(Calendar.DAY_OF_MONTH, 1);
						Date monthStart = gc.getTime();
						gc.add(Calendar.MONTH, 1);
						gc.add(Calendar.DAY_OF_MONTH, -1);
						Date monthEnd = gc.getTime();
						SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

						String startWeek = Utility.getWeekEnding(format.format(monthStart));
						String endWeek = Utility.getWeekEnding(format.format(monthEnd));
						String startDay = (new SimpleDateFormat("EEE")).format(monthStart);
						String endDay = (new SimpleDateFormat("EEE")).format(monthEnd);
						String month = (new SimpleDateFormat("MMMM")).format(monthStart);
						daoManager.getUserUtilizationDao().getMonthlyNCUsageByUser(startDay, endDay, startWeek, endWeek,
								userId, month, i, userCapacity, ncId);
					}
				} else {
					for (int j = 0; j <= 11; j++) {
						Calendar gc = new GregorianCalendar();
						gc.set(Calendar.YEAR, i);
						gc.set(Calendar.MONTH, j);
						gc.set(Calendar.DAY_OF_MONTH, 1);
						Date monthStart = gc.getTime();
						gc.add(Calendar.MONTH, 1);
						gc.add(Calendar.DAY_OF_MONTH, -1);
						Date monthEnd = gc.getTime();
						SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

						String startWeek = Utility.getWeekEnding(format.format(monthStart));
						String endWeek = Utility.getWeekEnding(format.format(monthEnd));
						String startDay = (new SimpleDateFormat("EEE")).format(monthStart);
						String endDay = (new SimpleDateFormat("EEE")).format(monthEnd);
						String month = (new SimpleDateFormat("MMMM")).format(monthStart);
						daoManager.getUserUtilizationDao().getMonthlyNCUsageByUser(startDay, endDay, startWeek, endWeek,
								userId, month, i, userCapacity, ncId);
					}
				}
			}
		}
		for (int i = 0; i <= c.get(Calendar.MONTH); i++) {
			Calendar gc = new GregorianCalendar();
			gc.set(Calendar.YEAR, currentYear);
			gc.set(Calendar.MONTH, i);
			gc.set(Calendar.DAY_OF_MONTH, 1);
			Date monthStart = gc.getTime();
			gc.add(Calendar.MONTH, 1);
			gc.add(Calendar.DAY_OF_MONTH, -1);
			Date monthEnd = gc.getTime();
			SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

			String startWeek = Utility.getWeekEnding(format.format(monthStart));
			String endWeek = Utility.getWeekEnding(format.format(monthEnd));
			String startDay = (new SimpleDateFormat("EEE")).format(monthStart);
			String endDay = (new SimpleDateFormat("EEE")).format(monthEnd);
			String month = (new SimpleDateFormat("MMMM")).format(monthStart);
			daoManager.getUserUtilizationDao().getMonthlyNCUsageByUser(startDay, endDay, startWeek, endWeek, userId,
					month, currentYear, userCapacity, ncId);

		}
		return userCapacity;

	}

	@Override
	public List<NetworkCodeEffort> getNetworkCodesEffortOfProject(int year,
			Long projectId, Long ncId, Long supervisorId, boolean isAdmin,String type,String satus,String fromDate,String toDate) throws Throwable {

		return daoManager.getUserNetworkCodeEffortDao().getNetworkCodesEffortOfProject(year, projectId, ncId,
				supervisorId, isAdmin,type,satus,fromDate,toDate);
	}

	@Override
	public List<NetworkCodeEffort> getUserProjectNC(int year, long networkId, Long supervisorId, boolean isAdmin,String status)
			throws Throwable {
		return daoManager.getUserNetworkCodeEffortDao().getUserProjectNC(year, networkId, supervisorId, isAdmin);
	}

	@Override
	public void getPillarsMap(Map<Long, String> pillarsMapObj, String searchSupervisor, boolean isAdmin)
			throws Throwable {
		SearchSortContainer searchSortContainer = new SearchSortContainer();
		searchSortContainer.setSearchSupervisor(searchSupervisor);
		List<Project> projectList = daoManager.getUserProjectsDao().getUserProjects(null, searchSortContainer, isAdmin);
		pillarsMapObj.put(-1L, "Please Select");
		for (Project project : projectList) {
			pillarsMapObj.put(project.getPillarId(), project.getPillar());
		}

	}
@Transactional
	@Override
	public void getProjectsMap(Map<Long, String> projectsMapObj, String pillar, String searchSupervisor,
			boolean isAdmin) throws Throwable {
		SearchSortContainer searchSortContainer = new SearchSortContainer();
		searchSortContainer.setSearchSupervisor(searchSupervisor);
		searchSortContainer.setSearchField("pillarid");
		searchSortContainer.setSearchString(pillar);
		List<Project> projectList = daoManager.getUserProjectsDao().getUserProjects(null, searchSortContainer, isAdmin);
		for (Project project : projectList) {
			projectsMapObj.put(project.getId(), project.getProjectName());
		}
	}
@Transactional
	@Override
	public void getReleaseMap(Map<Long, String> releasesMapObj, String project, String searchSupervisor,
			boolean isAdmin, String pillar, String pm,String byStatus) throws Throwable {
		SearchSortContainer searchSortContainer = new SearchSortContainer();
		searchSortContainer.setSearchSupervisor(searchSupervisor);
		searchSortContainer.setSearchField("projectid");
		searchSortContainer.setSearchString(project);
		List<NetworkCodes> releaseList = daoManager.getUserNetworkCodeDao().getUserNetworkCodes(null,
				searchSortContainer, isAdmin,byStatus);

		for (NetworkCodes release : releaseList) {
			releasesMapObj.put(release.getId(), release.getReleaseId() + " - " + release.getReleaseName());
		}
	}

}
