package com.egil.pts.dao.inventory;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.egil.pts.dao.common.GenericDao;
import com.egil.pts.dao.domain.User;
import com.egil.pts.dao.domain.UserAccounts;
import com.egil.pts.dao.domain.UserStableTeams;
import com.egil.pts.dao.domain.UserSupervisor;
import com.egil.pts.modal.EssDetails;
import com.egil.pts.modal.Pagination;
import com.egil.pts.modal.PtsHolidays;
import com.egil.pts.modal.SearchSortContainer;
import com.egil.pts.modal.TechCompetencyScore;
import com.egil.pts.modal.UserCapacity;
import com.egil.pts.modal.UserReport;

public interface UserDao extends GenericDao<User, Long> {

	public List<User> getUsers(Pagination pagination, SearchSortContainer searchSortObj) throws Throwable;

	public int getUsersCount(SearchSortContainer searchSortObj) throws Throwable;

	public List<com.egil.pts.modal.User> getSubordinates(Long id, Set<Long> idList, String searchValue)
			throws Throwable;

	public Integer deleteUsers(List<Long> userIdList) throws Throwable;

	public User getUser(Long id) throws Throwable;

	public User getUser(String userName, String password ) throws Throwable;

	public Integer resetPassword(String userName, String newPassword, String oldPassword) throws Throwable;

	public List<User> getAllManagers(Set<Long> idList, String searchValue) throws Throwable;

	public List<User> getUsersToBackFill() throws Throwable;

	public List<UserSupervisor> getSubordinates(Long id) throws Throwable;

	public List<com.egil.pts.dao.domain.User> getProgramManagers() throws Throwable;

	public List<com.egil.pts.dao.domain.User> getProjectManagers() throws Throwable;

	public List<UserSupervisor> getUserListToAssignNetworkCode(Long id, Set<Long> idList, String searchValue,
			Long selectedNetworkCodeId) throws Throwable;

	public List<com.egil.pts.modal.User> getProgramManagers(String pillarId) throws Throwable;

	public List<com.egil.pts.modal.User> getProjectManagers(String pillarId) throws Throwable;

	public List<Long> getLineManagersIds() throws Throwable;

	public List<Long> getResourcesToAddProjects() throws Throwable;

	public List<com.egil.pts.modal.User> getUsersSummary(Pagination pagination, SearchSortContainer searchSortObj)
			throws Throwable;

	public int getUsersSummaryCount(SearchSortContainer searchSortObj) throws Throwable;

	public String getMailByUserName(String username);

	public String getMailByUserId(Long userId);

	public List<UserReport> getLocationUserCount(String ricoLocation, Long location, Long stream, Long supervisorId);

	public List<UserReport> getLocationUserContributionReport(String ricoLocation, Long location, Long pillarId,
			Long stream, Long supervisorId, boolean isAppBased);

	public int getTechScoreRecordCount(String region, Long techId, String priaryFlag, Long projectId, Long selectedYear,
			String halfYear);

	public List<TechCompetencyScore> getTechScoreRecordDetails(String region, Long techId, String priaryFlag,
			Long projectId, Pagination pagination, SearchSortContainer searchSortObj, Long selectedYear,
			String halfYear);

	public int getCompScoreRecordCount(String ricoLocation, Long pillarId, Long projectId, String primaryFlag,
			Long selectedYear, String halfYear);

	public List<TechCompetencyScore> getCompScoreRecordDetails(String ricoLocation, Long pillarId, Long projectId,
			String primaryFlag, Pagination pagination, SearchSortContainer searchSortObj, Long selectedYear,
			String halfYear);

	public void getHeadCnt(Long supervisorId, Map<String, String> headCntMap);

	public int getContributionDetailReportCount(Long supervisorId, Long location, Long pillarId, Long stream,
			String region);

	public List<com.egil.pts.modal.User> getContributionDetailReport(Long supervisorId, Pagination pagination,
			Long location, Long pillarId, Long stream, String region);

	public List<UserAccounts> getUserAccounts(String user) throws Throwable;

	public User getUser(String userName, boolean ldartsManaged) throws Throwable;

	public List<PtsHolidays> manageHolidayList(PtsHolidays holidays, SearchSortContainer sortContainer);

	public List<PtsHolidays> getHolidayList(PtsHolidays holidays, SearchSortContainer sortContainer);

	public List<com.egil.pts.modal.User> getSubordinatesNew(Long id) throws Throwable;

	public boolean resetPassword(String userName, String email);

	public List<UserSupervisor> getSuperviosrs() throws Throwable;

	public List<com.egil.pts.modal.User> getAllSupervisors(Long userId);

	public List<com.egil.pts.modal.User> getAllResources(Long supervisor);

	public List<UserCapacity> GenerateNWUtilizationDate(int year, Long pillar, String reportype, Integer month, Long userId);
	public List<EssDetails> getEssFeed(String month,Pagination pagination) throws Throwable ;

	public String getPassword(String currentUserName);

	public List<com.egil.pts.modal.UserStableTeams> getUserStableTeams(Long userId);

	public void saveUserLineManager(com.egil.pts.modal.User userObj);

	public Integer getUserLineManager(Long id);

	public void editUserLineManager(com.egil.pts.modal.User userObj);
}
