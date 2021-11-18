package com.egil.pts.service;

import java.util.Date;
import java.util.List;
import java.util.Set;

import com.egil.pts.dao.domain.UserTimesheet;
import com.egil.pts.modal.ActivityCodesNew;
import com.egil.pts.modal.NetworkCodes;
import com.egil.pts.modal.Project;
import com.egil.pts.modal.TimesheetActivity;
import com.egil.pts.modal.TimesheetActivityWfm;

public interface UserEffortService {

	public void saveTimeSheet(List<TimesheetActivity> activityList, String assignedBy, Long assignedTo,
			Date weekendingDate, String removedIds) throws Throwable;

	public void getEffortDetailsForWeek(List<TimesheetActivity> activityList, Date weekEndingFromDate,
			Date weekEndingToDate, Set<Long> userId, String approvalType) throws Throwable;

	public void getTimeTemplate(List<TimesheetActivity> activityList, List<TimesheetActivity> activityListProductsToSave, Long userId) throws Throwable;

	public void approveTimeSheet(List<TimesheetActivity> activityList, String updatedBy, Date weekendingDate)
			throws Throwable;

	public void uploadEssData(String uploadFileFileName) throws Throwable;

	public List<TimesheetActivity> getActualChargedHoursByNW(List<Long> nwIds, String userStream, String userId);

	public void approveTimeSheet(List<TimesheetActivity> activityList, String updatedBy, Date weekendingDate,
			boolean stats) throws Throwable;

	public void saveTimeSheetWFM(List<TimesheetActivityWfm> activityListToSave, Long userId, Long selectedEmployee,
			Date weekEnding, String removedIds) throws Throwable;

	public List<TimesheetActivityWfm> getTimeSheetWFM(Long userId, Date weekendingDate) throws Throwable;

	public List<TimesheetActivityWfm> getTimeSheetWFM(Long selectedEmployee, Date weekEnding, String showAllWfm, String showAllSubRes,Long stream);

	public void saveTimeSheet(List<UserTimesheet> userEffortList, String removedIds) throws Throwable;
 
	public void getEffortDetailsForWeekForUsers(List<TimesheetActivity> activityList, Date weekEndingFromDate,
			Date weekEndingToDate, Set<Long> userId, String approvalType) throws Throwable;

	public void saveActivityTimeSheet(boolean weekoff,List<ActivityCodesNew> activityNewToSave,Long userId,String name,Date date);

	public void getEffortDetailsForWeekForUsersProjects(List<TimesheetActivity> activityList, Date weekEndingFromDate,
			Date weekEndingToDate, Set<Long> userId, String approvalType) throws Throwable;

	public void saveTemplate(List<TimesheetActivity> activityList, Long userId, String additionalProjects,String removeAdditional,
			List<Project> projects, List<com.egil.pts.modal.NetworkCodes> network) throws Throwable;

}
