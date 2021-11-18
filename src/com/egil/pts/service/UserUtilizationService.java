package com.egil.pts.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.egil.pts.modal.Pagination;
import com.egil.pts.modal.ResourceEffort;
import com.egil.pts.modal.SummaryResponse;
import com.egil.pts.modal.SupervisorResourceUtilization;
import com.egil.pts.modal.User;
import com.egil.pts.modal.Utilization;

public interface UserUtilizationService {

	public SummaryResponse<Utilization> getUserUtilizationSummary(Long userId, String fromDate, String toDate,
			Long searchValue, String reporteeType, Pagination pagination, boolean delinquentEntries,
			List<String> weeksList, long stableTeamid, Long networkCodeId, Long projectManagerId) throws Throwable;

	public List<ResourceEffort> getResourceUtilizationDetails(long supervisorId, String fromDate, String toDate,
			Long selectedEmployee, String reporteeType, boolean showMyRecord) throws Throwable;

	public String generateExcel(long supervisorId, Map<String, String> excelColHeaders, String fromDate, String toDate,
			Long searchValue, String reporteeType, String fileName, SummaryResponse<Utilization> res) throws Throwable;

	public void sendMail(long supervisorId, String fromDate, String toDate, Long searchValue, String reporteeType,
			String supervisorMail, List<String> weeksList) throws Throwable;

	public SummaryResponse<Utilization> getRICOUtilizationSummary(boolean userFlag, Long userId, String fromDate, String toDate,
			Pagination pagination, Long projectManagerId) throws Throwable;

	public String generateRICOUtilizationSummaryExcel(Long userId, String fromDate, String toDate, String fileName,
			Map<String, String> excelColHeaders) throws Throwable;

	public String getUnApprovedHours(Long supervisorId) throws Throwable;

	public void saveUserUtilization(List<SupervisorResourceUtilization> dashbordDetails);

	public void saveUserUtilizationForMonth();

	public void saveUserUtilizationForYear();

	public List<SupervisorResourceUtilization> getUserUtilization(List<SupervisorResourceUtilization> dashbordDetails,
			Long user, Long selectedYear, Long selectedMonth);

	public String generateExcelWFM(Long userId, Map<String, String> excelColHeaders, Date date, Long selectedEmployee,
			Long selectedEmp, String fileName, String showAllWfm, String showAllSubRes, Long stream);

	public String generateUserStableExcel(Long long1, Pagination object, String format, String format2,
			Long selectedEmployee, String reporteeType, String fileName, boolean all);

	public String generateUserStableExcel(SummaryResponse<User> summary, String fileName, boolean all,
			boolean stableTeams);

}
