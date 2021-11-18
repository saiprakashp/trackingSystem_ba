package com.egil.pts.actions;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.interceptor.validation.SkipValidation;
import org.hibernate.internal.util.StringHelper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import com.egil.pts.dao.domain.Announcements;
import com.egil.pts.dao.domain.UserAccounts;
import com.egil.pts.modal.DashboardUtilizationReport;
import com.egil.pts.modal.NetworkCodeEffort;
import com.egil.pts.modal.PtsHolidays;
import com.egil.pts.modal.SearchSortContainer;
import com.egil.pts.modal.User;
import com.egil.pts.util.DesEncrypter;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;

@Controller("loginAction")
@Scope("prototype")
public class LoginAction extends PTSAction {

	private static final long serialVersionUID = 1L;
	private String username;
	private String password;
	private String fullName;
	private String userType;
	private List<NetworkCodeEffort> dashboardNCList = new ArrayList<NetworkCodeEffort>();
	private List<NetworkCodeEffort> dashboardUserNCList = new ArrayList<NetworkCodeEffort>();
	private List<NetworkCodeEffort> dashboardUserNCLOEList = new ArrayList<NetworkCodeEffort>();
	List<DashboardUtilizationReport> dashboardUtilizationReportList = new ArrayList<DashboardUtilizationReport>();

	List<Announcements> dashboardAnnouncementList = new ArrayList<Announcements>();

	List<PtsHolidays> egiHolidayList = new ArrayList<PtsHolidays>();
	List<PtsHolidays> manaHolidayList = new ArrayList<PtsHolidays>();

	private Map<Long, String> customerContracts = new LinkedHashMap<Long, String>();
	private Map<Long, String> employeeList = new LinkedHashMap<Long, String>();
	private String employeeId;

	private String unapprovedHrs = "0.00";
	private Map<String, String> headCntMap = new LinkedHashMap<String, String>();
	private Integer custometId;
	String onboardCnt = "0";
	String inductionCnt = "0";
	String selectedCnt = "0";
	String openCnt = "0";
	String ltaCnt = "0";
	String noticePeriodCnt = "0";
	private String Interns = "0";

	@Value("${rico.ldaps.enabled}")
	private boolean ladapsEnable;
	@Value("${rico.admin.check.enable}")
	private boolean adminLoginEnable;
	@Value("${rico.user.image.path}")
	private String filePath;
	@Value("${pts.showall.loe.dahsboard}")
	private String showAllNwLoe;
	@Value("${ldarts.retry}")
	// inital loop starts with 1 retry at null pointer only
	private int ldartsRetry;

	private String detailSubRes;

	@Value("${manager.new.logic.based}")
	private boolean newlogic;

	private boolean showAllMyContributions;

	
	public List<NetworkCodeEffort> getDashboardUserNCList() {
		return dashboardUserNCList;
	}

	public void setDashboardUserNCList(List<NetworkCodeEffort> dashboardUserNCList) {
		this.dashboardUserNCList = dashboardUserNCList;
	}

	public String execute() {
		try {
			boolean authenticateUser = false;
			User user = null;
			clearSession("supervisorMap", "userToBackFillMap", "privilegesMap", "rolesMap", "streamsMap",
					"platformsMap", "userTypesMap", "technologiesMap", "customerContracts", "currentCustomer",
					"currentCustomerId");
			clearErrorsAndMessages();

			user = serviceManager.getUserService().getUser(username, DesEncrypter.encrypt(password));

			if (user == null
					|| (user != null && (user.getId() == null || user.getId() != null && user.getId() == 0l))) {
				addActionError("Invalid user credentials.");
				return ERROR;
			}
			if (user.getStatus().contains("Interns")) {
				serviceManager.getUserService().checkAndDeleteUserIntern(user.getId());
			}
			Date date = new Date();
			SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
			logger.info("USER LOGGED IN  SIGNUM   " + user.getName() + "      TIME:   " + format.format(date));

			// request.getSession().getServletContext().getRealPath("/");
			// filePath = filePath + "/usersImage/";
			boolean check = new File(new File(filePath), username + ".png").exists();
			if (check) {
				session.put("userPhoto", username + ".png");
			} else {
				session.put("userPhoto", "user1.png");
			}
			session.put("firstTime", "true");
			session.put("userId", user.getId());
			session.put("userStatus", user.getStatus());
			session.put("username", username);
			fullName = user.getName();
			session.put("fullName", fullName);
			session.put("role", user.getRole());
			session.put("userType", user.getUserType());
			session.put("userStream", user.getStream());
			session.put("userStreamName", user.getStreamName());
			session.put("email", user.getEmail());
			session.put("selectedAccountId", "0");
			session.put("lineManagerId", user.getLineManagerId());
			session.put("currentCustomer", "Please Select");
			session.replace("currentCustomerId", "0");
			getCustomerNames();
			showDashboard();
		} catch (Throwable e) {
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}

	public String goSetCUstomer() {
		Map<Integer, String> m = (Map<Integer, String>) session.get("customerContracts");
		session.replace("currentCustomer", m.get(getCustometId()));
		session.replace("currentCustomerId", getCustometId());
		return SUCCESS;
	}

	public void getCustomerNames() {
		try {
			List<UserAccounts> userAccounts = serviceManager.getUserService()
					.getUserAccounts(session.get("userId") + "");
			if (userAccounts != null && userAccounts.size() > 0) {
				if (userAccounts.size() == 1) {
					customerContracts.put(userAccounts.get(0).getAccount().getId(),
							userAccounts.get(0).getAccount().getAccountName());
					session.replace("currentCustomer", userAccounts.get(0).getAccount().getAccountName());
					session.replace("currentCustomerId", userAccounts.get(0).getAccount().getId());
				} else {
					customerContracts.put(0l, "All Applications");
					for (UserAccounts customerName : userAccounts) {
						customerContracts.put(customerName.getAccount().getId(),
								customerName.getAccount().getAccountName());
					}
				}

			}
			session.put("customerContracts", customerContracts);
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

	public String setCustomerName() {
		@SuppressWarnings("unchecked")
		Map<Integer, String> m = (Map<Integer, String>) session.get("customerContracts");

		session.replace("currentCustomer", m.get(getCustometId()));
		session.replace("currentCustomerId", getCustometId());
		return SUCCESS;
	}

	@SkipValidation
	public String showDashboard() {
		try {

		 
			dashboardUserNCList = serviceManager.getNetworkCodesService().getUserDashBoardDetails(
					Long.valueOf((session.get("userId") == null || session.get("userId").toString().equals("")) ? "0"
							: session.get("userId").toString()));
			Long userid = (showAllNwLoe != null && showAllNwLoe.equalsIgnoreCase("Yes") && session.get("role") != null
					&& ((String) session.get("role")).equalsIgnoreCase("LINE MANAGER"))?
					-1L : Long.valueOf(session.get("userId").toString());

			dashboardUserNCLOEList = serviceManager.getNetworkCodesService().getUserDashBoardLoeDetails(userid,showAllMyContributions);
			egiHolidayList = serviceManager.getUserService().getEGIHolidayList();
			manaHolidayList = serviceManager.getUserService().getMANAHolidayList();

			if (session.get("role") != null && ((String) session.get("role")).equalsIgnoreCase("LINE MANAGER")) {
				unapprovedHrs = serviceManager.getUserUtilizationService()
						.getUnApprovedHours((Long) session.get("userId"));

				serviceManager.getUserService().getHeadCnt((Long) session.get("userId"), headCntMap);

				for (String status : headCntMap.keySet()) {
					String value = headCntMap.get(status);
					if (status.equalsIgnoreCase("Onboard")) {
						onboardCnt = value;
					} else if (status.equalsIgnoreCase("Induction")) {
						inductionCnt = value;
					} else if (status.equalsIgnoreCase("Selected")) {
						selectedCnt = value;
					} else if (status.equalsIgnoreCase("Open")) {
						openCnt = value;
					} else if (status.equalsIgnoreCase("LTA")) {
						ltaCnt = value;
					} else if (status.equalsIgnoreCase("Notice Period")) {
						noticePeriodCnt = value;
					} else if (status.contains("Intern")) {
						Interns = value;
					}
				}
				serviceManager.getUserService().getUserListNew((Long) session.get("userId"), employeeList);
				employeeList.put((Long) session.get("userId"), (String) session.get("fullName"));
				employeeList.put((new Long(-1)), "Team");
				employeeId = "-1";
			}
			goGetDashboardAnnouncements();
			getDashboardUtilizationHrefId();
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	@SkipValidation
	public String getDashboardUtilizationHrefId() {
		Long empId = 0l;
		SearchSortContainer searchContainer = new SearchSortContainer();
		boolean teamFlag = false;
		if (!StringHelper.isEmpty(employeeId) && !employeeId.equals("-1")) {
			empId = Long.parseLong(employeeId);
		} else {
			empId = (Long) session.get("userId");
			teamFlag = true;
		}
		String role = (String) session.get("role");

		if (detailSubRes != null && detailSubRes.equalsIgnoreCase("TRUE")) {
			searchContainer.setDetailLevel(true);
		} else {
			searchContainer.setDetailLevel(false);
		}

		dashboardUtilizationReportList = serviceManager.getUserService().getDashboardUtilizatoinDetails_New(empId, 12,
				teamFlag, Calendar.getInstance().getWeekYear(), searchContainer, role);
		return SUCCESS;
	}

	@SkipValidation
	public String goGetDashboardAnnouncements() {
		try {
			dashboardAnnouncementList = serviceManager.getUserService().getDashboardAnnouncementDetails();
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	@SkipValidation
	public String exitApplication() {
		try {
			ActionContext.getContext().getSession().clear();
			sessionCtx.invalidate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	public String getUsername() {
		return username;
	}

	@RequiredStringValidator(message = "Please enter username.")
	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	@RequiredStringValidator(message = "Please enter password.")
	public void setPassword(String password) {
		this.password = password;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public List<NetworkCodeEffort> getDashboardNCList() {
		return dashboardNCList;
	}

	public void setDashboardNCList(List<NetworkCodeEffort> dashboardNCList) {
		this.dashboardNCList = dashboardNCList;
	}

	public List<PtsHolidays> getEgiHolidayList() {
		return egiHolidayList;
	}

	public void setEgiHolidayList(List<PtsHolidays> egiHolidayList) {
		this.egiHolidayList = egiHolidayList;
	}

	public List<PtsHolidays> getManaHolidayList() {
		return manaHolidayList;
	}

	public void setManaHolidayList(List<PtsHolidays> manaHolidayList) {
		this.manaHolidayList = manaHolidayList;
	}

	public String getUnapprovedHrs() {
		return unapprovedHrs;
	}

	public void setUnapprovedHrs(String unapprovedHrs) {
		this.unapprovedHrs = unapprovedHrs;
	}

	public Map<String, String> getHeadCntMap() {
		return headCntMap;
	}

	public void setHeadCntMap(Map<String, String> headCntMap) {
		this.headCntMap = headCntMap;
	}

	public String getOnboardCnt() {
		return onboardCnt;
	}

	public void setOnboardCnt(String onboardCnt) {
		this.onboardCnt = onboardCnt;
	}

	public String getInductionCnt() {
		return inductionCnt;
	}

	public void setInductionCnt(String inductionCnt) {
		this.inductionCnt = inductionCnt;
	}

	public String getSelectedCnt() {
		return selectedCnt;
	}

	public void setSelectedCnt(String selectedCnt) {
		this.selectedCnt = selectedCnt;
	}

	public String getOpenCnt() {
		return openCnt;
	}

	public void setOpenCnt(String openCnt) {
		this.openCnt = openCnt;
	}

	public String getLtaCnt() {
		return ltaCnt;
	}

	public void setLtaCnt(String ltaCnt) {
		this.ltaCnt = ltaCnt;
	}

	public String getNoticePeriodCnt() {
		return noticePeriodCnt;
	}

	public void setNoticePeriodCnt(String noticePeriodCnt) {
		this.noticePeriodCnt = noticePeriodCnt;
	}

	public Map<Long, String> getEmployeeList() {
		return employeeList;
	}

	public void setEmployeeList(Map<Long, String> employeeList) {
		this.employeeList = employeeList;
	}

	public String getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}

	public List<DashboardUtilizationReport> getDashboardUtilizationReportList() {
		return dashboardUtilizationReportList;
	}

	public void setDashboardUtilizationReportList(List<DashboardUtilizationReport> dashboardUtilizationReportList) {
		this.dashboardUtilizationReportList = dashboardUtilizationReportList;
	}

	public int getTotalHeadCnt() {
		return Integer.parseInt(onboardCnt) + Integer.parseInt(openCnt) + Integer.parseInt(getInterns())
				+ Integer.parseInt(selectedCnt) + Integer.parseInt(inductionCnt) + Integer.parseInt(ltaCnt);
	}

	public List<Announcements> getDashboardAnnouncementList() {
		return dashboardAnnouncementList;
	}

	public void setDashboardAnnouncementList(List<Announcements> dashboardAnnouncementList) {
		this.dashboardAnnouncementList = dashboardAnnouncementList;
	}

	public Map<Long, String> getCustomerContracts() {
		return customerContracts;
	}

	public void setCustomerContracts(Map<Long, String> customerContracts) {
		this.customerContracts = customerContracts;
	}

	public Integer getCustometId() {
		return custometId;
	}

	public void setCustometId(Integer custometId) {
		this.custometId = custometId;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public boolean isNewlogic() {
		return newlogic;
	}

	public void setNewlogic(boolean newlogic) {
		this.newlogic = newlogic;
	}

	public String getDetailSubRes() {
		return detailSubRes;
	}

	public void setDetailSubRes(String detailSubRes) {
		this.detailSubRes = detailSubRes;
	}

	public String getInterns() {
		return Interns;
	}

	public void setInterns(String interns) {
		Interns = interns;
	}

	public List<NetworkCodeEffort> getDashboardUserNCLOEList() {
		return dashboardUserNCLOEList;
	}

	public void setDashboardUserNCLOEList(List<NetworkCodeEffort> dashboardUserNCLOEList) {
		this.dashboardUserNCLOEList = dashboardUserNCLOEList;
	}

	public boolean isShowAllMyContributions() {
		return showAllMyContributions;
	}

	public void setShowAllMyContributions(boolean showAllMyContributions) {
		this.showAllMyContributions = showAllMyContributions;
	}

}
