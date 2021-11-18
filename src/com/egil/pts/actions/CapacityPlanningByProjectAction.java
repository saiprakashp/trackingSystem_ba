package com.egil.pts.actions;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.validation.SkipValidation;
import org.hibernate.internal.util.StringHelper;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.egil.pts.modal.CapacityPlanning;
import com.egil.pts.modal.Pagination;
import com.egil.pts.modal.SearchSortContainer;
import com.egil.pts.modal.SummaryResponse;
import com.egil.pts.modal.TimesheetActivity;
import com.egil.pts.modal.User;
import com.egil.pts.util.Utility;

@Controller("capacityPlanningByProjectAction")
@Scope("prototype")
public class CapacityPlanningByProjectAction extends PTSAction {

	private static final long serialVersionUID = 1L;
	private Long selectedEmployee;
	private String searchProjectName;
	private Map<String, String> projectNames = new LinkedHashMap<String, String>();
	private List<CapacityPlanning> subGridModel = new ArrayList<CapacityPlanning>();
	private static List<CapacityPlanning> subGridModelTemp = new ArrayList<CapacityPlanning>();
	private List<CapacityPlanning> ncGridModel = new ArrayList<CapacityPlanning>();
	private List<CapacityPlanning> tempModel = new ArrayList<CapacityPlanning>();
	private String searchUserName;
	private Map<Long, String> supervisorMap = new LinkedHashMap<Long, String>();
	private Map<Long, String> userNetworkCodesMap = new LinkedHashMap<Long, String>();
	// Gives networkCodeId in case of project seach
	private Long networkCodeId;
	private Long capUserId;
	
	private Long networkCode;
	private Float janCapacity;
	private Float febCapacity;
	private Float marCapacity;
	private Float aprCapacity;
	private Float mayCapacity;
	private Float junCapacity;
	private Float julCapacity;
	private Float augCapacity;
	private Float sepCapacity;
	private Float octCapacity;
	private Float novCapacity;
	private Float decCapacity;
	private String createdBy;
	private Date createdDate;
	private String isfrmCapPlanMen;
	private String searchSupervisor;
	private boolean allReporteesFlag;
	private String searchProject;
	private String networkId;
	private String reportType;
	private boolean isProjectList;
	private String userName;
	
	private Boolean activeFlag;
	
	
	private String ricoLocation;
	private String project;
	private String pillar;
	private String searchTechnology;
	// Your result List
	private List<User> gridModel = new ArrayList<User>();
	private List<com.egil.pts.modal.CapacityPlanning> capacityReportDetailsgridModel = new ArrayList<com.egil.pts.modal.CapacityPlanning>();

	private Long year;
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

	private String rowid;

	public void setRowid(String rowid) {
		this.rowid = rowid;
	}

	public String getRowid() {
		return rowid;
	}

	private String id;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSearchProject() {
		return searchProject;
	}

	public void setSearchProject(String searchProject) {
		this.searchProject = searchProject;
	}

	@SkipValidation
	public String execute() {
		try {
			if ((searchSupervisor == null || (searchSupervisor != null && searchSupervisor
					.isEmpty()))) {
				allReporteesFlag = true;
			}

			if (session.get("role") != null
					&& ((String) session.get("role"))
							.equalsIgnoreCase("LINE MANAGER")) {
				if ((searchSupervisor == null || (searchSupervisor != null && searchSupervisor
						.isEmpty()))) {
					searchSupervisor = (Long) session.get("userId") + "";
				}

			}
			// setAllProjectNames();

			if (!StringHelper.isEmpty(reportType)
					&& reportType.equalsIgnoreCase("Project-Wise-Detailed")) {
				setIsfrmCapPlanMen("false");
			}
			reportType = "Project-Wise-Detailed";
			// .out.println("Execute reportType " + reportType);
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	private void setAllProjectNames() throws Throwable {

		SummaryResponse<CapacityPlanning> s = serviceManager
				.getCapacityPlanningService()
				.getUserCapacityPlanningDetailReportDetails(
						getSearchSortBean(), selectedYear, null);
		if (s.getEnitities() != null && !s.getEnitities().isEmpty())
			s.getEnitities().forEach(
					cp -> projectNames.put(cp.getNetworkCode(),
							cp.getNetworkCode()));
	}

	@SkipValidation
	public String editableReport() {
		try {
			isProjectList = true;
			if ((searchSupervisor == null || (searchSupervisor != null && searchSupervisor
					.isEmpty()))) {
				allReporteesFlag = true;
			}

			if (session.get("role") != null
					&& ((String) session.get("role"))
							.equalsIgnoreCase("LINE MANAGER")) {
				if ((searchSupervisor == null || (searchSupervisor != null && searchSupervisor
						.isEmpty()))) {
					searchSupervisor = (Long) session.get("userId") + "";
				}

			}
			// setAllProjectNames();
			if ((searchSupervisor == null || (searchSupervisor != null && searchSupervisor
					.isEmpty()))) {
				allReporteesFlag = true;
			}
			if(activeFlag == null) {
				activeFlag = true;
			}
			if (session.get("role") != null
					&& ((String) session.get("role"))
							.equalsIgnoreCase("LINE MANAGER")) {
				if ((searchSupervisor == null || (searchSupervisor != null && searchSupervisor
						.isEmpty()))) {
					searchSupervisor = (Long) session.get("userId") + "";
				}

			}
			if (StringHelper.isEmpty(reportType))
				reportType = "Project-Wise-Editable";
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	@SkipValidation
	public String goManageUser() {
		try {
			clearSession("supervisorMap", "userToBackFillMap", "privilegesMap",
					"rolesMap", "streamsMap", "platformsMap", "userTypesMap",
					"technologiesMap");
			boolean isAdmin = false;
			if (session.get("role") != null
					&& ((String) session.get("role")).equalsIgnoreCase("ADMIN")) {
				isAdmin = true;
			}
			if (session.get("rowNum") == null) {
				session.put("rowNum", rows);
			} else {
				if (rows != null
						&& (rows.intValue() != ((Integer) session.get("rowNum"))
								.intValue())) {
					session.put("rowNum", rows);
				}
			}
			SummaryResponse<CapacityPlanning> summary = serviceManager
					.getCapacityPlanningService().getNetworkCodesList(
							getSearchSortBean(), selectedYear,
							getPaginationObject());
			if (summary != null && summary.getEnitities() != null
					&& summary.getEnitities().size() > 0) {
				ncGridModel = summary.getEnitities();
			}

			records = summary.getTotalRecords();
			// calculate the total pages for the query
			total = (int) Math.ceil((double) records / (double) rows);

		} catch (Throwable e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	private void setAllNetworkHourDetails(List<CapacityPlanning> tempModel,
			Long selectedYear) {
		List<Long> nwCodes = new ArrayList<>();
		if (tempModel != null && !tempModel.isEmpty()) {
			tempModel.forEach(p -> nwCodes.add(p.getNetworkCodeId()));
			try {
				List<CapacityPlanning> tempModel1 = serviceManager
						.getCapacityPlanningService().getCapacityNyNetworkCode(
								nwCodes, selectedYear); // Total Planning
				List<TimesheetActivity> timesheetActivities = serviceManager
						.getUserEffortService().getActualChargedHoursByNW(
								nwCodes,session.get("userStream")+"",session.get("userId")+"");// TOTal Claimed
				
				if (tempModel1 != null && !tempModel1.isEmpty()
						&& timesheetActivities != null
						&& !timesheetActivities.isEmpty()) {
					Map<Long, float[]> hoursDesc = new LinkedHashMap<>();

					for (CapacityPlanning c : tempModel1) {
						for (TimesheetActivity tm : timesheetActivities) {
							float totalNWhrs = (
										  (c.getJanCapacity()==null?0.0f:c.getJanCapacity())
										+ (c.getFebCapacity()==null?0.0f :c.getFebCapacity())
										+ (c.getMarCapacity()==null?0.0f:c.getMarCapacity())
										+ (c.getAprCapacity()==null?0.0f:c.getAprCapacity())
										+ (c.getMayCapacity()==null?0.0f:c.getMayCapacity())
										+ (c.getJunCapacity()==null?0.0f:c.getJunCapacity())
										+ (c.getJulCapacity()==null?0.0f:c.getJulCapacity())
										+ (c.getAugCapacity()==null?0.0f:c.getAugCapacity())
										+ (c.getSepCapacity()==null?0.0f:c.getSepCapacity())
										+ (c.getOctCapacity()==null?0.0f:c.getOctCapacity())
										+ (c.getNovCapacity()==null?0.0f:c.getNovCapacity()) 
										+ (c.getDecCapacity()==null?0.0f:c.getDecCapacity())
										);
								float totalClaim = (tm.getMonHrs()==null?0.0f:tm.getMonHrs())
										+ (tm.getTueHrs()==null?0.0f:tm.getTueHrs()) + (tm.getWedHrs()==null?0.0f:tm.getWedHrs())
										+ (tm.getThuHrs()==null?0.0f: tm.getThuHrs())+ (tm.getFriHrs()==null?0.0f:tm.getFriHrs())
										+ (tm.getSatHrs()==null?0.0f:tm.getSatHrs()) + (tm.getSunHrs()==null?0.0f:tm.getSunHrs());
								hoursDesc.put(c.getNetworkCodeId(),
										new float[] { totalNWhrs, totalClaim,(c.getOriginalDevLoe()==null?0.0F:c.getOriginalDevLoe())
													,(c.getOriginalTestLoe()==null?0.0f:c.getOriginalTestLoe())
													,(c.getOriginalImplLoe()==null?0.0f:c.getOriginalImplLoe())
													,(c.getOriginalPmLoe()==null?0.0f:c.getOriginalPmLoe())
								
											});
								break;
							}
						}
					}
					/*tempModel.forEach(con ->

					{
						float[] thrs = hoursDesc.get(con.getNetworkCodeId());
						if (thrs != null) {
							con.setTotalNWCapacity(thrs[0]);
							con.setTotalTimeSheetCap(thrs[1]);
							con.setTotalManagerCapacity(
									  (con.getJanCapacity()==null?0:con.getJanCapacity())
									+ (con.getFebCapacity()==null?0:con.getFebCapacity())
									+ (con.getMarCapacity()==null?0:con.getMarCapacity())
									+ (con.getAprCapacity()==null?0:con.getAprCapacity())
									+ (con.getMayCapacity()==null?0:con.getMayCapacity())
									+ (con.getJunCapacity()==null?0:con.getJunCapacity())
									+ (con.getJulCapacity()==null?0:con.getJulCapacity())
									+ (con.getAugCapacity()==null?0:con.getAugCapacity())
									+ (con.getSepCapacity()==null?0:con.getSepCapacity())
									+ (con.getOctCapacity()==null?0:con.getOctCapacity())
									+ (con.getNovCapacity()==null?0:con.getNovCapacity())
									+ (con.getDecCapacity()==null?0:con.getDecCapacity())
									);
							con.setOriginalDevLoe((long)thrs[2]);
							con.setOriginalTestLoe((long)thrs[3]);
							con.setOriginalImplLoe((long)thrs[4]);
							con.setOriginalPmLoe((long)thrs[5]);
							
						}
					}
					);*/
			} catch (Throwable e) {
				e.printStackTrace();
			}
			ncGridModel = tempModel;
		}
	}
	
	private static float totalJan=0;
	private static float totalFeb=0;
	private static float totalMar=0;
	private static float totalApr=0;
	private static float totalMay=0;
	private static float totalJun=0;
	private static float totalJul=0;
	private static float totalAug=0;
	private static float totalSep=0;
	private static float totalOct=0;
	private static float totalNov=0;
	private static float totalDec=0;
	private static float totalDev=0;
	private static float totalLoc=0;
	
	@SkipValidation
	public String goCapacityPlanning() {
		try {
			clearSession("networkCodesMap");
			if (session.get("rowNum") == null) {
				session.put("rowNum", rows);
			} else {
				if (rows != null
						&& (rows.intValue() != ((Integer) session.get("rowNum"))
								.intValue())) {
					session.put("rowNum", rows);
				}
			}
			if (id != null && id.contains(", ")) {
				id = id.trim().substring(0, id.indexOf(','));
			}

			// session.put("networkCodesMap", networkCodesMap);
			SummaryResponse<CapacityPlanning> summary = serviceManager
					.getCapacityPlanningService().getCapacityPlanningDetails(
							Long.parseLong(id), selectedYear,
							getPaginationObject());
			if (summary != null && summary.getEnitities() != null
					&& summary.getEnitities().size() > 0) {
				subGridModel = summary.getEnitities();
			}
			records = summary.getTotalRecords();
			total = (int) Math.ceil((double) records / (double) rows);
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	@SkipValidation
	public String goCapacityPlanningNCUserDetailsReport() {
		try {
			if (session.get("rowNum") == null) {
				session.put("rowNum", rows);
			} else {
				if (rows != null
						&& (rows.intValue() != ((Integer) session.get("rowNum"))
								.intValue())) {
					session.put("rowNum", rows);
				}
			}
			if (id != null && id.contains(", ")) {
				id = id.trim().substring(0, id.indexOf(','));
			}
			SummaryResponse<CapacityPlanning> summary = serviceManager
					.getCapacityPlanningService()
					.getNetworkCodesUsersCapacityList(getSearchSortBean(),
							selectedYear, getPaginationObject(),
							Long.parseLong(id));
			if (summary != null && summary.getEnitities() != null
					&& summary.getEnitities().size() > 0) {
				subGridModel = summary.getEnitities();
				subGridModel.forEach(p -> 
				{
				totalJan+=(p.getJanCapacity()==null?0:p.getJanCapacity());
				totalFeb +=(p.getFebCapacity()==null?0:p.getFebCapacity());
				totalMar+=(p.getMarCapacity()==null?0:p.getMarCapacity());
				totalApr+=(p.getAprCapacity()==null?0:p.getAprCapacity());
				totalMay+=(p.getMayCapacity()==null?0:p.getMayCapacity());
				totalJun+=(p.getJunCapacity()==null?0:p.getJunCapacity());
				totalJul+=(p.getJulCapacity()==null?0:p.getJulCapacity());
				totalAug+=(p.getAugCapacity()==null?0:p.getAugCapacity());
				totalSep+= (p.getSepCapacity()==null?0:p.getSepCapacity());
				totalOct+= (p.getOctCapacity()==null?0:p.getOctCapacity());
				totalNov+= (p.getNovCapacity()==null?0:p.getNovCapacity());
				totalDec+=(p.getDecCapacity()==null?0:p.getDecCapacity());
				});
				
				CapacityPlanning c=	new CapacityPlanning();
				c.setId(99999L);
				c.setUserName("Total: ");
				c.setJanCapacity(totalJan);
				c.setFebCapacity(totalFeb);
				c.setMarCapacity(totalMar);
				c.setAprCapacity(totalApr);
				c.setMayCapacity(totalMay);
				c.setJunCapacity(totalJun);
				c.setJulCapacity(totalJul);
				c.setAugCapacity(totalAug);
				c.setSepCapacity(totalSep);
				c.setOctCapacity(totalOct);
				c.setNovCapacity(totalNov);
				c.setDecCapacity(totalDec);
				subGridModel.add(c);
				totalJan=0;totalFeb=0;totalMar=0;totalApr=0;totalMay=0;totalJun=0;totalJul=0;totalAug=0;totalSep=0;totalOct=0;totalNov=0;totalDec=0;
				subGridModelTemp = subGridModel;
			}
			records = summary.getTotalRecords();
			total = (int) Math.ceil((double) records / (double) rows);
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	@SkipValidation
	public String capacityPlanningMACD() {
		CapacityPlanning capacityPlanning = new CapacityPlanning();
		try {
			if (oper.equalsIgnoreCase("add")) {
				if (networkCode != null && networkCode != -1) {
					setCapacityPlanObjToSave(capacityPlanning);
					capacityPlanning.setCreatedBy((String) session.get("username"));
					capacityPlanning.setUpdatedBy((String) session.get("username"));
					capacityPlanning.setCreatedDate(new Date());
					capacityPlanning.setUpdatedDate(new Date());
					serviceManager.getCapacityPlanningService().saveCapacityPlanningDetails(capacityPlanning);
				}
			} else if (oper.equalsIgnoreCase("edit")) {
				if (networkCode != null && networkCode != -1) {
					capacityPlanning.setId(Long.valueOf(id));
					setCapacityPlanObjToSave(capacityPlanning);
					capacityPlanning.setCreatedBy(createdBy);
					capacityPlanning.setCreatedDate(createdDate);
					capacityPlanning.setUpdatedBy((String) session.get("username"));
					capacityPlanning.setUpdatedDate(new Date());
					serviceManager.getCapacityPlanningService().saveCapacityPlanningDetails(capacityPlanning);
				}
			} else if (oper.equalsIgnoreCase("del")) {
				if (id != null & !id.equalsIgnoreCase("")) {
					serviceManager.getCapacityPlanningService()
							.deleteCapacityPlanningDetails(Utility.getListFromCommaSeparated(id));
				}
			}
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	private void setCapacityPlanObjToSave(CapacityPlanning capacityPlanning) {
		capacityPlanning.setUserId(capUserId);
		capacityPlanning.setNetworkCodeId(networkCode);
		capacityPlanning.setJanCapacity(janCapacity);
		capacityPlanning.setFebCapacity(febCapacity);
		capacityPlanning.setMarCapacity(marCapacity);
		capacityPlanning.setAprCapacity(aprCapacity);
		capacityPlanning.setMayCapacity(mayCapacity);
		capacityPlanning.setJunCapacity(junCapacity);
		capacityPlanning.setJulCapacity(julCapacity);
		capacityPlanning.setAugCapacity(augCapacity);
		capacityPlanning.setSepCapacity(sepCapacity);
		capacityPlanning.setOctCapacity(octCapacity);
		capacityPlanning.setNovCapacity(novCapacity);
		capacityPlanning.setDecCapacity(decCapacity);
		capacityPlanning.setYear(year);
	}

	@SkipValidation
	public String getUserNetworkCodes() {
		try {
			userNetworkCodesMap = new LinkedHashMap<Long, String>();
			if (rowid != null && !rowid.isEmpty()) {
				Set<Long> ncids = null;
				if (selectedYear != null && selectedYear != -1
						&& (id != null && id.contains("jqg"))) {
					ncids = serviceManager.getCapacityPlanningService()
							.getResourceNetworkCodes(Long.valueOf(rowid),
									selectedYear);
				}
				if (networkId != null
						&& (!networkId.equals("-1") && !networkId.equals(""))) {
					if (networkId.indexOf(" - ") != -1) {

						serviceManager.getNetworkCodesService()
								.getResourceNetworkCodes(Long.valueOf(rowid),
										userNetworkCodesMap,
										networkId.split(" - ")[0], null);
					}
				} else {
					serviceManager.getNetworkCodesService()
							.getResourceNetworkCodes(Long.valueOf(rowid),
									userNetworkCodesMap, null, ncids);
				}
				capUserId = Long.valueOf(rowid);
			}
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	@SkipValidation
	public String getParentRowId() {
		try {
			if (rowid != null && !rowid.isEmpty()) {
				capUserId = Long.valueOf(rowid);
			}
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	protected SearchSortContainer getSearchSortBean() {
		SearchSortContainer searchSortObj = new SearchSortContainer();
		searchSortObj.setSearchField(searchField);
		searchSortObj.setSearchString(searchString);
		searchSortObj.setSidx(sidx);
		searchSortObj.setSord(sord);
		searchSortObj.setSearchProjectName(searchProjectName);
		searchSortObj.setCapacityScrnFlag(true);
		searchSortObj.setSearchProject(searchProject);
		searchSortObj.setSearchUserName(searchUserName);
		searchSortObj.setSearchSupervisor(searchSupervisor);
		searchSortObj.setLoggedInFullName((String) session.get("fullName"));
		searchSortObj.setLoggedInId((Long) session.get("userId") + "");
		searchSortObj.setLoggedInUserRole((String) session.get("role"));
		if (isProjectList) {
			searchSortObj.setOrderBy("cp.year,networkCode");
			searchSortObj.setSord("desc");
		}
		searchSortObj.setAllReporteesFlag(true);
		
		searchSortObj.setRegion(StringHelper.isEmpty(ricoLocation)?"ALL":ricoLocation);
		
		searchSortObj.setPillarId(StringHelper.isEmpty(pillar)?0:Integer.parseInt(pillar));
		searchSortObj.setProjectId(StringHelper.isEmpty(project)?0:Integer.parseInt(project));
		searchSortObj.setTechnologyId(StringHelper.isEmpty(searchTechnology)?0:Integer.parseInt(searchTechnology));
		
		searchSortObj.setActiveFlag(activeFlag);


		return searchSortObj;
	}

	protected Pagination getPaginationObject() {
		Pagination pagination = new Pagination();
		int to = (rows * page);
		int from = to - rows;
		pagination.setOffset(from);
		pagination.setSize(to);
		return pagination;
	}

	public Long getSelectedEmployee() {
		return selectedEmployee;
	}

	public void setSelectedEmployee(Long selectedEmployee) {
		this.selectedEmployee = selectedEmployee;
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
	}

	public List<CapacityPlanning> getSubGridModel() {
		return subGridModel;
	}

	public void setSubGridModel(List<CapacityPlanning> subGridModel) {
		this.subGridModel = subGridModel;
	}

	public String getSearchUserName() {
		return searchUserName;
	}

	public void setSearchUserName(String searchUserName) {
		this.searchUserName = searchUserName;
	}

	public Long getCapUserId() {
		return capUserId;
	}

	public void setCapUserId(Long capUserId) {
		this.capUserId = capUserId;
	}

	public Long getNetworkCode() {
		return networkCode;
	}

	public void setNetworkCode(Long networkCode) {
		this.networkCode = networkCode;
	}

	public Float getJanCapacity() {
		return janCapacity;
	}

	public void setJanCapacity(Float janCapacity) {
		this.janCapacity = janCapacity;
	}

	public Float getFebCapacity() {
		return febCapacity;
	}

	public void setFebCapacity(Float febCapacity) {
		this.febCapacity = febCapacity;
	}

	public Float getMarCapacity() {
		return marCapacity;
	}

	public void setMarCapacity(Float marCapacity) {
		this.marCapacity = marCapacity;
	}

	public Float getAprCapacity() {
		return aprCapacity;
	}

	public void setAprCapacity(Float aprCapacity) {
		this.aprCapacity = aprCapacity;
	}

	public Float getMayCapacity() {
		return mayCapacity;
	}

	public void setMayCapacity(Float mayCapacity) {
		this.mayCapacity = mayCapacity;
	}

	public Float getJunCapacity() {
		return junCapacity;
	}

	public void setJunCapacity(Float junCapacity) {
		this.junCapacity = junCapacity;
	}

	public Float getJulCapacity() {
		return julCapacity;
	}

	public void setJulCapacity(Float julCapacity) {
		this.julCapacity = julCapacity;
	}

	public Float getAugCapacity() {
		return augCapacity;
	}

	public void setAugCapacity(Float augCapacity) {
		this.augCapacity = augCapacity;
	}

	public Float getSepCapacity() {
		return sepCapacity;
	}

	public void setSepCapacity(Float sepCapacity) {
		this.sepCapacity = sepCapacity;
	}

	public Float getOctCapacity() {
		return octCapacity;
	}

	public void setOctCapacity(Float octCapacity) {
		this.octCapacity = octCapacity;
	}

	public Float getNovCapacity() {
		return novCapacity;
	}

	public void setNovCapacity(Float novCapacity) {
		this.novCapacity = novCapacity;
	}

	public Float getDecCapacity() {
		return decCapacity;
	}

	public void setDecCapacity(Float decCapacity) {
		this.decCapacity = decCapacity;
	}

	public Map<Long, String> getUserNetworkCodesMap() {
		return userNetworkCodesMap;
	}

	public void setUserNetworkCodesMap(Map<Long, String> userNetworkCodesMap) {
		this.userNetworkCodesMap = userNetworkCodesMap;
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

	public Long getYear() {
		return year;
	}

	public void setYear(Long year) {
		this.year = year;
	}

	public String getSearchSupervisor() {
		return searchSupervisor;
	}

	public void setSearchSupervisor(String searchSupervisor) {
		this.searchSupervisor = searchSupervisor;
	}

	public boolean isAllReporteesFlag() {
		return allReporteesFlag;
	}

	public void setAllReporteesFlag(boolean allReporteesFlag) {
		this.allReporteesFlag = allReporteesFlag;
	}

	public List<User> getGridModel() {
		return gridModel;
	}

	public void setGridModel(List<User> gridModel) {
		this.gridModel = gridModel;
	}

	public String getNetworkId() {
		return networkId;
	}

	public void setNetworkId(String networkId) {
		this.networkId = networkId;
	}

	public Map<Long, String> getSupervisorMapForManage() {
		try {
			/*
			 * if (session.get("role") != null && ((String) session.get("role"))
			 * .equalsIgnoreCase("LINE MANAGER")) {
			 * serviceManager.getUserService().getSupervisorMap(supervisorMap,
			 * (Long) session.get("userId"), false); } else if
			 * (session.get("role") != null && ((String)
			 * session.get("role")).equalsIgnoreCase("ADMIN")) {
			 * serviceManager.getUserService().getSupervisorMap(supervisorMap,
			 * (Long) session.get("userId"), false); }
			 */
			/*
			 * if (session.get("role") != null && ((String)
			 * session.get("role")).equalsIgnoreCase("ADMIN")) {
			 * serviceManager.getUserService().getSupervisorMap(supervisorMap,
			 * (Long) session.get("userId")); } else
			 */
			/*
			 * if (session.get("role") != null && ((String) session.get("role"))
			 * .equalsIgnoreCase("LINE MANAGER")) { supervisorMap.put(((Long)
			 * session.get("userId")), ((String) session.get("fullName")));
			 * searchSupervisor = (Long) session.get("userId") + ""; }
			 */

			serviceManager.getUserService().getLineManagersMap(supervisorMap);

		} catch (Throwable e) {
			e.printStackTrace();
		}
		return supervisorMap;
	}

	public Map<Long, String> getSupervisorMap() {
		return supervisorMap;
	}

	public void setSupervisorMap(Map<Long, String> supervisorMap) {
		this.supervisorMap = supervisorMap;
	}

	@SkipValidation
	public String capacityPlanningReport() {
		if ((searchSupervisor == null || (searchSupervisor != null && searchSupervisor
				.isEmpty()))) {
			allReporteesFlag = true;
		}

		if (session.get("role") != null
				&& ((String) session.get("role"))
						.equalsIgnoreCase("LINE MANAGER")) {
			if ((searchSupervisor == null || (searchSupervisor != null && searchSupervisor
					.isEmpty()))) {
				searchSupervisor = (Long) session.get("userId") + "";
			}

		}
		return SUCCESS;
	}

	@SkipValidation
	public String goGetCapacityPlanningReportDetails() {
		try {
			clearSession("supervisorMap", "userToBackFillMap", "privilegesMap",
					"rolesMap", "streamsMap", "platformsMap", "userTypesMap",
					"technologiesMap");
			if (session.get("rowNum") == null) {
				session.put("rowNum", rows);
			} else {
				if (rows != null
						&& (rows.intValue() != ((Integer) session.get("rowNum"))
								.intValue())) {
					session.put("rowNum", rows);
				}
			}
			SummaryResponse<com.egil.pts.modal.CapacityPlanning> summary = serviceManager
					.getCapacityPlanningService()
					.getUserCapacityPlanningReportDetails(getSearchSortBean(),
							selectedYear, getPaginationObject());
			capacityReportDetailsgridModel = summary.getEnitities();

			records = summary.getTotalRecords();

			// calculate the total pages for the query
			total = (int) Math.ceil((double) records / (double) rows);

		} catch (Throwable e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	public List<com.egil.pts.modal.CapacityPlanning> getCapacityReportDetailsgridModel() {
		return capacityReportDetailsgridModel;
	}

	public void setCapacityReportDetailsgridModel(
			List<com.egil.pts.modal.CapacityPlanning> capacityReportDetailsgridModel) {
		this.capacityReportDetailsgridModel = capacityReportDetailsgridModel;
	}

	public String getReportType() {
		return reportType;
	}

	public void setReportType(String reportType) {
		this.reportType = reportType;
	}

	public List<CapacityPlanning> getNcGridModel() {
		return ncGridModel;
	}

	public void setNcGridModel(List<CapacityPlanning> ncGridModel) {
		this.ncGridModel = ncGridModel;
	}

	public String getIsfrmCapPlanMen() {
		return isfrmCapPlanMen;
	}

	public void setIsfrmCapPlanMen(String isfrmCapPlanMen) {
		this.isfrmCapPlanMen = isfrmCapPlanMen;
	}

	public Map<String, String> getProjectNames() {
		return projectNames;
	}

	public void setProjectNames(Map<String, String> projectNames) {
		this.projectNames = projectNames;
	}

	public String getSearchProjectName() {
		return searchProjectName;
	}

	public void setSearchProjectName(String searchProjectName) {
		this.searchProjectName = searchProjectName;
	}

	public boolean isProjectList() {
		return isProjectList;
	}

	public void setProjectList(boolean isProjectList) {
		this.isProjectList = isProjectList;
	}

	public Long getNetworkCodeId() {
		return networkCodeId;
	}

	public void setNetworkCodeId(Long networkCodeId) {
		this.networkCodeId = networkCodeId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getRicoLocation() {
		return ricoLocation;
	}

	public void setRicoLocation(String ricoLocation) {
		this.ricoLocation = ricoLocation;
	}

	public String getProject() {
		return project;
	}

	public void setProject(String project) {
		this.project = project;
	}

	public String getPillar() {
		return pillar;
	}

	public void setPillar(String pillar) {
		this.pillar = pillar;
	}

	public String getSearchTechnology() {
		return searchTechnology;
	}

	public void setSearchTechnology(String searchTechnology) {
		this.searchTechnology = searchTechnology;
	}

	public Boolean getActiveFlag() {
		return activeFlag;
	}

	public void setActiveFlag(Boolean activeFlag) {
		this.activeFlag = activeFlag;
	}

	
}
