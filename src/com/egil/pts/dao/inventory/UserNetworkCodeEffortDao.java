package com.egil.pts.dao.inventory;

import java.util.Date;
import java.util.List;
import java.util.Set;

import com.egil.pts.dao.common.GenericDao;
import com.egil.pts.dao.domain.UserTimesheet;
import com.egil.pts.modal.ActivityCodesNew;
import com.egil.pts.modal.NetworkCodeEffort;
import com.egil.pts.modal.TimesheetActivityWfm;

public interface UserNetworkCodeEffortDao extends GenericDao<UserTimesheet, Long> {

	public List<UserTimesheet> getEffortDetailsOfWeek(String weekEndingFromDate, String weekEndingToDate,
			Set<Long> userId, String approvalType, boolean showOld) throws Throwable;

	public void assignEffortToUser(List<UserTimesheet> userEffortList) throws Throwable;

	public void delete(List<Long> removedIds);

	public List<NetworkCodeEffort> getNetworkCodesEffortOfProject(int year, Long projectId, Long ncId,
			Long supervisorId, boolean isAdmin, String type, String satus, String fromDate, String toDate)
			throws Throwable;

	public List<NetworkCodeEffort> getUserProjectNC(int year, Long projectId, Long supervisorId, boolean isAdmin)
			throws Throwable;

	public void assignEffortToUser(long userId, String status, String updatedBy, Date updtDate) throws Throwable;

	public void deletewfm(List<Long> removedIds);

	public void assignEffortToUserWfm(List<TimesheetActivityWfm> userEffortList, Long userId, Date weekendingDate);

	public List<TimesheetActivityWfm> getEffortDetailsOfWeekWfm(Long userId, Date weekendingDate);

	public List<TimesheetActivityWfm> getEffortDetailsOfWeekWfm(Long selectedEmployee, Date weekEnding,
			String showAllWfm, String showAll, Long stream);

	public void saveActivityEffort(boolean weekoff,ActivityCodesNew data,Long userId,String name,Date date);

	public void assignEffortToUserNew(List<UserTimesheet> userEffortList) throws Throwable;

}
