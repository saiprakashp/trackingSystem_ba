package com.egil.pts.actions;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.interceptor.validation.SkipValidation;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.egil.pts.dao.domain.UserAccounts;
import com.egil.pts.modal.Pagination;
import com.egil.pts.modal.Project;
import com.egil.pts.modal.SearchSortContainer;
import com.egil.pts.modal.Status;
import com.egil.pts.modal.SummaryResponse;
import com.egil.pts.modal.SupervisorResourceUtilization;
import com.egil.pts.util.Utility;

@Controller("projectAction")
@Scope("prototype")
public class ProjectAction extends PTSAction {

	private static final long serialVersionUID = 1L;
	private List<Project> projectGridModel = new ArrayList<Project>();
	private String projectName;
	private String pillar;
	private String customer;
	private String description;
	private String createdBy;
	private Date createdDate;
	private Long orderNum;
	private Map<Long, String> supervisorMap = new LinkedHashMap<Long, String>();
	private Map<Integer, Integer> yearMap = new LinkedHashMap<Integer, Integer>();
	private Map<Integer, String> monthMap = new LinkedHashMap<Integer, String>();
	private List<SupervisorResourceUtilization> dashbordDetails = new ArrayList<SupervisorResourceUtilization>();
	private Long selectedSupervisor;
	private Float egiWorkingHours;
	private Float manaWorkingHours;

	private Long HyderabadresourceCount;
	private Double Hyderabadworkingdays;
	private String Hyderabadlocation;
	private boolean HyderabadInsert;

	private Long BangloreresourceCount;
	private Double Bangloreworkingdays;
	private String Banglorelocation;
	private boolean BangloreInsert;

	private Long ChennairesourceCount;
	private Double Chennaiworkingdays;
	private String Chennailocation;
	private boolean ChennaiInsert;

	private Long ManaresourceCount;
	private Double Manaworkingdays;
	private String Manalocation;
	private boolean ManaInsert;

	// get how many rows we want to have into the grid - rowNum attribute in the
	// grid
	private Integer rows = 0;

	// Get the requested page. By default grid sets this to 1.
	private Integer page = 0;

	// sorting order - asc or desc
	private String sord;

	// get index row - i.e. user click to sort.
	private String sidx;

	// Search Field
	private String searchField;

	// The Search String
	private String searchString;

	// Limit the result when using local data, value form attribute rowTotal
	private Integer totalrows;

	// he Search Operation
	// ['eq','ne','lt','le','gt','ge','bw','bn','in','ni','ew','en','cn','nc']
	private String searchOper;

	private String oper;

	// Your Total Pages
	private Integer total = 0;

	// All Records
	private Integer records = 0;

	private boolean loadonce = false;

	private String pillarsMap;
	private String customersMap;

	private String selectedPillar;

	private String projectData;

	private List<UserAccounts> userAccounts = new ArrayList<UserAccounts>();
	private SearchSortContainer searchSortContainer;
	private Float manaTargetHrs;
	private Float chenTargetHrs;
	private Float bangTargetHrs;
	private Float hydTargetHrs;

	@SkipValidation
	public String execute() {
		try {
			clearSession("supervisorMap", "userToBackFillMap", "privilegesMap", "rolesMap", "streamsMap",
					"platformsMap", "userTypesMap", "technologiesMap");
			pillarsMap = serviceManager.getPillarService().getJSONStringOfPillars();
			customersMap = serviceManager.getCustomerService().getJSONStringOfCustomers();
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	@SkipValidation
	public String goManageProjects() {

		try {
			if (session.get("rowNum") == null) {
				session.put("rowNum", rows);
			} else {
				if (rows != null && (rows.intValue() != ((Integer) session.get("rowNum")).intValue())) {
					session.put("rowNum", rows);
				}
			}
			SummaryResponse<Project> summary = serviceManager.getProjectService()
					.getProjectsSummary(getPaginationObject(), getSearchSortBean());
			projectGridModel = summary.getEnitities();
			records = summary.getTotalRecords();
			total = (int) Math.ceil((double) records / (double) rows);
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	public String getPlannedUserUtilization() {
		supervisorMap.clear();
		updateYearMonthMap();
		Long user = (Long) session.get("userId");
		if (selectedSupervisor != user) {
			if (selectedSupervisor != null && selectedSupervisor != -1) {
				user = selectedSupervisor;
			}
			try {
				serviceManager.getUserService().getSupervisorMap(supervisorMap, user, false);
				dashbordDetails = serviceManager.getUserUtilizationService().getUserUtilization(dashbordDetails, user,
						selectedYear, Long.parseLong(selectedMonth));
				if (dashbordDetails != null && dashbordDetails.size() > 0) {

					for (SupervisorResourceUtilization s : dashbordDetails) {
						if (s.getLocationName().equalsIgnoreCase("Hyderabad")) {
							HyderabadresourceCount = s.getResourceCount();
							Hyderabadworkingdays = s.getWorkingdays();
							Hyderabadlocation = s.getLocationName();

						} else if (s.getLocationName().equalsIgnoreCase("Bangalore")) {
							BangloreresourceCount = s.getResourceCount();
							Bangloreworkingdays = s.getWorkingdays();
							Banglorelocation = s.getLocationName();
						} else if (s.getLocationName().equalsIgnoreCase("Chennai")) {
							ChennairesourceCount = s.getResourceCount();
							Chennaiworkingdays = s.getWorkingdays();
							Chennailocation = s.getLocationName();
						} else if (s.getLocationName().equalsIgnoreCase("Mana")) {
							ManaresourceCount = s.getResourceCount();
							Manaworkingdays = s.getWorkingdays();
							Manalocation = s.getLocationName();
						}
					}

					setEgiWorkingHours(dashbordDetails.get(0).getEgiworkingHours());
					setManaWorkingHours(dashbordDetails.get(0).getManaworkingHours());
					total = dashbordDetails.size();
				}

			} catch (Throwable e) {
				e.printStackTrace();
			}
		}
		return SUCCESS;
	}

	public String saveorupdatePlannedUserUtilization() {
		SupervisorResourceUtilization s = null;
		dashbordDetails.clear();
		s = new SupervisorResourceUtilization();
		s.setResourceCount(HyderabadresourceCount);
		s.setWorkingdays(Hyderabadworkingdays);
		s.setLocationName("Hyderabad");
		s.setEgiworkingHours(egiWorkingHours);
		s.setManaworkingHours(manaWorkingHours);
		s.setLocationId(16L);
		s.setUserId(selectedSupervisor);
		s.setYear(selectedYear);
		s.setMonth(Long.parseLong(selectedMonth));
		s.setTargetHrs(getHydTargetHrs());
		dashbordDetails.add(s);
		s = new SupervisorResourceUtilization();
		s.setResourceCount(BangloreresourceCount);
		s.setWorkingdays(Bangloreworkingdays);
		s.setLocationName("Bangalore");
		s.setEgiworkingHours(egiWorkingHours);
		s.setManaworkingHours(manaWorkingHours);
		s.setLocationId(14L);
		s.setUserId(selectedSupervisor);
		s.setYear(selectedYear);
		s.setMonth(Long.parseLong(selectedMonth));
		s.setTargetHrs(getBangTargetHrs());
		dashbordDetails.add(s);
		s = new SupervisorResourceUtilization();
		s.setResourceCount(ChennairesourceCount);
		s.setWorkingdays(Chennaiworkingdays);
		s.setLocationName("Chennai");
		s.setEgiworkingHours(egiWorkingHours);
		s.setManaworkingHours(manaWorkingHours);
		s.setLocationId(15L);
		s.setUserId(selectedSupervisor);
		s.setYear(selectedYear);
		s.setMonth(Long.parseLong(selectedMonth));
		s.setTargetHrs(getChenTargetHrs());
		dashbordDetails.add(s);
		s = new SupervisorResourceUtilization();
		s.setResourceCount(ManaresourceCount);
		s.setWorkingdays(Manaworkingdays);
		s.setLocationName("Mana");
		s.setEgiworkingHours(egiWorkingHours);
		s.setManaworkingHours(manaWorkingHours);
		s.setLocationId(1L);
		s.setUserId(selectedSupervisor);
		s.setYear(selectedYear);
		s.setMonth(Long.parseLong(selectedMonth));
		s.setTargetHrs(getManaTargetHrs());
		dashbordDetails.add(s);
		serviceManager.getUserUtilizationService().saveUserUtilization(dashbordDetails);
		return SUCCESS;
	}

	private void updateYearMonthMap() {
		yearMap.clear();
		monthMap.clear();
		int tempyear = Calendar.getInstance().getWeekYear();
		if (tempyear == Calendar.getInstance().getWeekYear()) {
			yearMap.put(tempyear, tempyear);
			yearMap.put(tempyear + 1, tempyear + 1);

		} else {
			yearMap.put(tempyear - 1, tempyear - 1);
			yearMap.put(tempyear, tempyear);
			yearMap.put(tempyear + 1, tempyear + 1);
		}

		monthMap.put(1, "Jan");
		monthMap.put(2, "Feb");
		monthMap.put(3, "Mar");
		monthMap.put(4, "Apr");
		monthMap.put(5, "May");
		monthMap.put(6, "Jun");
		monthMap.put(7, "Jul");
		monthMap.put(8, "Aug");
		monthMap.put(9, "Sep");
		monthMap.put(10, "Oct");
		monthMap.put(11, "Nov");
		monthMap.put(12, "Dec");

		selectedYear = (selectedYear != null && selectedYear != 0) ? selectedYear
				: Calendar.getInstance().getWeekYear();
		selectedMonth = (selectedMonth != null && !selectedMonth.isEmpty()) ? selectedMonth
				: (Calendar.getInstance().getTime().getMonth() + 1 + "");

	}

	@SkipValidation
	public String projectMACDOperation() {
		Project projectObj = new Project();
		try {
			if (oper.equalsIgnoreCase("add")) {
				projectObj.setProjectName(projectName);
				projectObj.setPillar(pillar);
				projectObj.setDescription(description);
				projectObj.setStatus(Status.ACTIVE);
				projectObj.setCreatedBy((String) session.get("username"));
				projectObj.setUpdatedBy((String) session.get("username"));
				projectObj.setCreatedDate(new Date());
				projectObj.setUpdatedDate(new Date());
				projectObj.setOrderNum(orderNum);
				serviceManager.getProjectService().createProject(projectObj);

			} else if (oper.equalsIgnoreCase("edit")) {
				projectObj.setId(Long.valueOf(id));
				projectObj.setProjectName(projectName);
				projectObj.setPillar(pillar);
				projectObj.setDescription(description);
				projectObj.setStatus(Status.ACTIVE);
				projectObj.setCreatedBy(createdBy);
				projectObj.setCreatedDate(createdDate);
				projectObj.setUpdatedBy((String) session.get("username"));
				projectObj.setOrderNum(orderNum);
				projectObj.setUpdatedDate(new Date());
				serviceManager.getProjectService().modifyProject(projectObj);
			} else if (oper.equalsIgnoreCase("del")) {
				if (id != null & !id.equalsIgnoreCase("")) {
					serviceManager.getProjectService().deleteProjects(Utility.getListFromCommaSeparated(id));
				}
			}
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	@SkipValidation
	public String loadProjects() {
		try {
			projectData = serviceManager.getProjectService().loadProjects(selectedPillar);
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	public String getAllAccounts() {
		return SUCCESS;
	}

	public String ajaxManageAccounts() {
		try {
			userAccounts = serviceManager.getUserService().getUserAccounts(null);
		} catch (Throwable e) {
			e.printStackTrace();
			return ERROR;

		}
		return SUCCESS;
	}

	public String manageAccounts() {
		return SUCCESS;
	}

	public List<Project> getProjectGridModel() {
		return projectGridModel;
	}

	public void setProjectGridModel(List<Project> projectGridModel) {
		this.projectGridModel = projectGridModel;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getPillar() {
		return pillar;
	}

	public void setPillar(String pillar) {
		this.pillar = pillar;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	protected Pagination getPaginationObject() {
		Pagination pagination = new Pagination();
		int to = (rows * page);
		int from = to - rows;
		pagination.setOffset(from);
		pagination.setSize(to);
		return pagination;
	}

	protected SearchSortContainer getSearchSortBean() {
		SearchSortContainer searchSortObj = new SearchSortContainer();
		searchSortObj.setSearchField(searchField);
		searchSortObj.setSearchString(searchString);
		searchSortObj.setSidx(sidx);
		searchSortObj.setSord(sord);

		return searchSortObj;
	}

	public Integer getRows() {
		return rows;
	}

	public void setRows(Integer rows) {
		this.rows = rows;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public String getSord() {
		return sord;
	}

	public void setSord(String sord) {
		this.sord = sord;
	}

	public String getSidx() {
		return sidx;
	}

	public void setSidx(String sidx) {
		this.sidx = sidx;
	}

	public String getSearchField() {
		return searchField;
	}

	public void setSearchField(String searchField) {
		this.searchField = searchField;
	}

	public String getSearchString() {
		return searchString;
	}

	public void setSearchString(String searchString) {
		this.searchString = searchString;
	}

	public Integer getTotalrows() {
		return totalrows;
	}

	public void setTotalrows(Integer totalrows) {
		this.totalrows = totalrows;
	}

	public String getSearchOper() {
		return searchOper;
	}

	public void setSearchOper(String searchOper) {
		this.searchOper = searchOper;
	}

	public String getOper() {
		return oper;
	}

	public void setOper(String oper) {
		this.oper = oper;
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public Integer getRecords() {
		return records;
	}

	public void setRecords(Integer records) {
		this.records = records;
		if (this.records > 0 && this.rows > 0) {
			this.total = (int) Math.ceil((double) this.records / (double) this.rows);
		} else {
			this.total = 0;
		}
	}

	public boolean isLoadonce() {
		return loadonce;
	}

	public void setLoadonce(boolean loadonce) {
		this.loadonce = loadonce;
	}

	public String getPillarsMap() {
		return pillarsMap;
	}

	public void setPillarsMap(String pillarsMap) {
		this.pillarsMap = pillarsMap;
	}

	public String getCustomersMap() {
		return customersMap;
	}

	public void setCustomersMap(String customersMap) {
		this.customersMap = customersMap;
	}

	public String getCustomer() {
		return customer;
	}

	public void setCustomer(String customer) {
		this.customer = customer;
	}

	public String getSelectedPillar() {
		return selectedPillar;
	}

	public void setSelectedPillar(String selectedPillar) {
		this.selectedPillar = selectedPillar;
	}

	public String getProjectData() {
		return projectData;
	}

	public void setProjectData(String projectData) {
		this.projectData = projectData;
	}

	public Long getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(Long orderNum) {
		this.orderNum = orderNum;
	}

	public List<UserAccounts> getUserAccounts() {
		return userAccounts;
	}

	public void setUserAccounts(List<UserAccounts> userAccounts) {
		this.userAccounts = userAccounts;
	}

	public Map<Long, String> getSupervisorMap() {
		return supervisorMap;
	}

	public void setSupervisorMap(Map<Long, String> supervisorMap) {
		this.supervisorMap = supervisorMap;
	}

	public String getSelectedMonth() {
		return selectedMonth;
	}

	public void setSelectedMonth(String selectedMonth) {
		this.selectedMonth = selectedMonth;
	}

	public Long getSelectedSupervisor() {
		return selectedSupervisor;
	}

	public void setSelectedSupervisor(Long selectedSupervisor) {
		this.selectedSupervisor = selectedSupervisor;
	}

	public Map<Integer, String> getMonthMap() {
		return monthMap;
	}

	public void setMonthMap(Map<Integer, String> monthMap) {
		this.monthMap = monthMap;
	}

	public List<SupervisorResourceUtilization> getDashbordDetails() {
		return dashbordDetails;
	}

	public void setDashbordDetails(List<SupervisorResourceUtilization> dashbordDetails) {
		this.dashbordDetails = dashbordDetails;
	}

	public Float getManaWorkingHours() {
		return manaWorkingHours;
	}

	public void setManaWorkingHours(Float manaWorkingHours) {
		this.manaWorkingHours = manaWorkingHours;
	}

	public Float getEgiWorkingHours() {
		return egiWorkingHours;
	}

	public void setEgiWorkingHours(Float egiWorkingHours) {
		this.egiWorkingHours = egiWorkingHours;
	}

	public Long getHyderabadresourceCount() {
		return HyderabadresourceCount;
	}

	public void setHyderabadresourceCount(Long hyderabadresourceCount) {
		HyderabadresourceCount = hyderabadresourceCount;
	}

	public Double getHyderabadworkingdays() {
		return Hyderabadworkingdays;
	}

	public void setHyderabadworkingdays(Double hyderabadworkingdays) {
		Hyderabadworkingdays = hyderabadworkingdays;
	}

	public Long getBangloreresourceCount() {
		return BangloreresourceCount;
	}

	public void setBangloreresourceCount(Long bangloreresourceCount) {
		BangloreresourceCount = bangloreresourceCount;
	}

	public Double getBangloreworkingdays() {
		return Bangloreworkingdays;
	}

	public void setBangloreworkingdays(Double bangloreworkingdays) {
		Bangloreworkingdays = bangloreworkingdays;
	}

	public Long getChennairesourceCount() {
		return ChennairesourceCount;
	}

	public void setChennairesourceCount(Long chennairesourceCount) {
		ChennairesourceCount = chennairesourceCount;
	}

	public Double getChennaiworkingdays() {
		return Chennaiworkingdays;
	}

	public void setChennaiworkingdays(Double chennaiworkingdays) {
		Chennaiworkingdays = chennaiworkingdays;
	}

	public Long getManaresourceCount() {
		return ManaresourceCount;
	}

	public void setManaresourceCount(Long manaresourceCount) {
		ManaresourceCount = manaresourceCount;
	}

	public Double getManaworkingdays() {
		return Manaworkingdays;
	}

	public void setManaworkingdays(Double manaworkingdays) {
		Manaworkingdays = manaworkingdays;
	}

	public String getHyderabadlocation() {
		return Hyderabadlocation;
	}

	public void setHyderabadlocation(String hyderabadlocation) {
		Hyderabadlocation = hyderabadlocation;
	}

	public String getBanglorelocation() {
		return Banglorelocation;
	}

	public void setBanglorelocation(String banglorelocation) {
		Banglorelocation = banglorelocation;
	}

	public String getChennailocation() {
		return Chennailocation;
	}

	public void setChennailocation(String chennailocation) {
		Chennailocation = chennailocation;
	}

	public String getManalocation() {
		return Manalocation;
	}

	public void setManalocation(String manalocation) {
		Manalocation = manalocation;
	}

	public boolean isHyderabadInsert() {
		return HyderabadInsert;
	}

	public void setHyderabadInsert(boolean hyderabadInsert) {
		HyderabadInsert = hyderabadInsert;
	}

	public boolean isBangloreInsert() {
		return BangloreInsert;
	}

	public void setBangloreInsert(boolean bangloreInsert) {
		BangloreInsert = bangloreInsert;
	}

	public boolean isChennaiInsert() {
		return ChennaiInsert;
	}

	public void setChennaiInsert(boolean chennaiInsert) {
		ChennaiInsert = chennaiInsert;
	}

	public boolean isManaInsert() {
		return ManaInsert;
	}

	public void setManaInsert(boolean manaInsert) {
		ManaInsert = manaInsert;
	}

	public SearchSortContainer getSearchSortContainer() {
		return searchSortContainer;
	}

	public void setSearchSortContainer(SearchSortContainer searchSortContainer) {
		this.searchSortContainer = searchSortContainer;
	}

	public Float getHydTargetHrs() {
		return hydTargetHrs;
	}

	public void setHydTargetHrs(Float hydTargetHrs) {
		this.hydTargetHrs = hydTargetHrs;
	}

	public Float getBangTargetHrs() {
		return bangTargetHrs;
	}

	public void setBangTargetHrs(Float bangTargetHrs) {
		this.bangTargetHrs = bangTargetHrs;
	}

	public Float getManaTargetHrs() {
		return manaTargetHrs;
	}

	public void setManaTargetHrs(Float manaTargetHrs) {
		this.manaTargetHrs = manaTargetHrs;
	}

	public Float getChenTargetHrs() {
		return chenTargetHrs;
	}

	public void setChenTargetHrs(Float chenTargetHrs) {
		this.chenTargetHrs = chenTargetHrs;
	}

}
