package com.egil.pts.service;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.egil.pts.dao.domain.Announcements;
import com.egil.pts.dao.domain.UserAccounts;
import com.egil.pts.dao.domain.UserPlatformCompetencyScore;
import com.egil.pts.dao.domain.UserPlatforms;
import com.egil.pts.dao.domain.UserProjects;
import com.egil.pts.dao.domain.UserSkillScore;
import com.egil.pts.dao.domain.UserSkills;
import com.egil.pts.modal.BulkResponse;
import com.egil.pts.modal.DashboardUtilizationReport;
import com.egil.pts.modal.EssDetails;
import com.egil.pts.modal.Pagination;
import com.egil.pts.modal.PtsHolidays;
import com.egil.pts.modal.ResourceUtilization;
import com.egil.pts.modal.SearchSortContainer;
import com.egil.pts.modal.StableTeams;
import com.egil.pts.modal.SummaryResponse;
import com.egil.pts.modal.User;

public interface UserService {

	public SummaryResponse<User> getUserSummary(Pagination pagination, SearchSortContainer searchSortObj,
			boolean isAdmin) throws Throwable;

	public List<User> getUsers(Pagination pagination, SearchSortContainer searchSortContainer) throws Throwable;

	public String getJSONStringOfUsers() throws Throwable;

	public void createUser(User user) throws Throwable;

	public void modifyUser(User user) throws Throwable;

	public Integer deleteUsers(List<Long> userIdList) throws Throwable;

	public User getUser(Long id) throws Throwable;

	public User getUser(String userName, String password) throws Throwable;

	public void mapResourceNetworkCodes(Long id, List<Long> selectedNetworkCodeNames) throws Throwable;

	public void getUserList(Long supervisorId, Map<Long, String> userList, Set<Long> idList, String searchValue)
			throws Throwable;

	public int resetPassword(String userId, String newPassword, String oldPassword) throws Throwable;

	public void getSupervisorMap(Map<Long, String> supervisorMap, Long supervisorId, boolean manageFlag)
			throws Throwable;

	public void getUsersToBackFill(Map<String, String> userToBackFillMap) throws Throwable;

	public void getUserList(Long supervisorId, Map<Long, String> userList) throws Throwable;

	public void getAllSupervisorList(Map<Long, String> userList, Map<Long, String> userStreamList, Long userId)
			throws Throwable;

	public String getProgramManagersMap() throws Throwable;

	public String getProjectManagersMap() throws Throwable;

	public void getUserListToAssignNetworkCode(boolean showAllemp, Long supervisorId, Map<Long, String> userList,
			Set<Long> idList, String searchValue, Long selectedNetworkCodeId) throws Throwable;

	public BulkResponse exportUsersSearchResults(String userId, HashMap<String, String> userColHeaders,
			SearchSortContainer searchSortObj, String filename, boolean isAdmin) throws Throwable;

	public void getProgramManagersMap(Map<Long, String> projectsMap, String pillarId) throws Throwable;

	public void getProjectManagersMap(Map<Long, String> projectsMap, String pillarId) throws Throwable;

	public List<Long> getLineManagersIds() throws Throwable;

	public void getLineManagersMap(Map<Long, String> supervisorMap) throws Throwable;

	public List<Long> getResourcesToAddProjects() throws Throwable;

	public Integer sendTemporaryPwdMail(String username) throws Throwable;

	public void saveUserWeekOff(List<Long> selectedEmployeeList, Date weekendingDate, boolean weekOffFlag)
			throws Throwable;

	public UserSkillScore getUserSkillScore(Long userId, Long skillId, Long year, String yearHalf) throws Throwable;

	public void saveUserSkillScore(UserSkillScore userSkillScore) throws Throwable;

	public List<UserSkills> getUserSkill(Long userId, Long skillId) throws Throwable;

	public UserPlatformCompetencyScore getUserPlatformCompScore(Long userId, Long projectId, Long year, String yearHalf)
			throws Throwable;

	public void saveUserPlatformCompScore(UserPlatformCompetencyScore userSkillScore) throws Throwable;

	public List<UserProjects> getUserProjects(Long userId, Long projectId) throws Throwable;

	public List<UserPlatforms> getUserPlatforms(Long userId, Long pillarId) throws Throwable;

	public boolean getUserWeekOff(Long userId, Date weekendingDate) throws Throwable;

	public List<PtsHolidays> getEGIHolidayList();

	public List<PtsHolidays> getMANAHolidayList();

	public void getHeadCnt(Long supervisorId, Map<String, String> headCntMap);

	public List<DashboardUtilizationReport> getDashboardUtilizatoinDetails(Long supervisorId, int year,
			boolean teamFlag);

	public SummaryResponse<ResourceUtilization> getResourceUtilizatoinDetails(Long userId, int year,
			String selectedMonth, Pagination pagination, boolean descrepencyFlag) throws Throwable;

	public void sendMailResourceUtilization(Long userId, int selectedYear, String selectedMonth) throws Throwable;

	public List<Announcements> getDashboardAnnouncementDetails() throws Throwable;

	public List<UserAccounts> getUserAccounts(String user) throws Throwable;

	public void getUserAccountList(Map<Long, String> accountMap, Map<Long, String> selectedAccountMap,
			List<Long> selectedAccounts, Long valueOf) throws Throwable;

	public void getUserAccountList(Map<Long, String> accountMap, Map<Long, String> selectedAccountMap,
			List<Long> selectedAccounts, Long userId, Long supervisorId) throws Throwable;

	public User getUser(String user, boolean ldartsManaged) throws Throwable;

	public List<PtsHolidays> manageHolidays(PtsHolidays holidays, SearchSortContainer sortContainer);

	public List<PtsHolidays> getHolidayList(PtsHolidays holidays, SearchSortContainer sortContainer);

	public List<DashboardUtilizationReport> getDashboardUtilizatoinDetails_New(Long supervisorId, int monthSelected,
			boolean teamFlag, int year, SearchSortContainer searchSortContainer, String role);

	public void SaveDashboardUtilizatoinDetail(List<DashboardUtilizationReport> dashbordDetails,
			SearchSortContainer searchSortContainer);

	public SummaryResponse<ResourceUtilization> getResourceUtilizatoinDetailsNew(Long userId, int year,
			String selectedMonth, Pagination pagination, boolean descrepencyFlag) throws Throwable;

	public void getUserListNew(Long supervisorId, Map<Long, String> userList) throws Throwable;

	public SummaryResponse<ResourceUtilization> getResourceUtilizatoinDetailsForSummary(Long userId, int year,
			String selectedMonth, Pagination pagination, boolean descrepencyFlag) throws Throwable;

	public boolean resetpassword(String userName, String email);

	public void getSuperviosrs(Map<Long, String> supervisorMap) throws Throwable;

	public void getAllResources(Map<Long, String> resourceMapObj, Long long1);

	public BulkResponse exportUsersUtilizationResults(Long year, Long supervisor, Long release, Long resource,
			String fileName, boolean isAdmin, LinkedHashMap<String, String> usersColHeaders, Long pillar,
			String reportype, Integer month);

	public boolean checkAndDeleteUserIntern(Long id);

	public List<EssDetails> getEssFeed(String month, Pagination pagination) throws Throwable;

	public int saveUserStableTeams(List<StableTeams> data, Long userId);

	public Map<String, String> getTimeSheetTypeMap();

	public String getPassword(String currentUserName);

	public Map<Long, String> getProjectManagersMap(Map<Long, String> data) throws Throwable;

	public List<com.egil.pts.modal.UserStableTeams> getUserStableTeams(Long userId) throws Throwable;

	public void saveUserLineManager(User userObj);

	public Integer getUserLineManager(Long id);

	public void editUserLineManager(User userObj);

}
