package com.egil.pts.actions;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.struts2.interceptor.validation.SkipValidation;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.egil.pts.modal.ActivityCodes;
import com.egil.pts.modal.Pagination;
import com.egil.pts.modal.SearchSortContainer;
import com.egil.pts.modal.Status;
import com.egil.pts.modal.SummaryResponse;
import com.egil.pts.util.Utility;


@Controller("activityCodesAction")
@Scope("prototype")
public class ActivityCodesAction extends PTSAction {

	private static final long serialVersionUID = 1L;
	private List<ActivityCodes> activityGridModel = new ArrayList<ActivityCodes>();
	private String activityCode;
	private String activity;
	private String description;
	private String createdBy;
	private Date createdDate;
	
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

	@SkipValidation
	public String execute() {
		clearSession("supervisorMap", "userToBackFillMap", "privilegesMap",
				"rolesMap", "streamsMap", "platformsMap", "userTypesMap",
				"technologiesMap");
		return SUCCESS;
	}

	@SkipValidation
	public String goManageActivityCodes() {

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
			clearSession("supervisorMap", "userToBackFillMap", "privilegesMap",
					"rolesMap", "streamsMap", "platformsMap", "userTypesMap",
					"technologiesMap");
			SummaryResponse<ActivityCodes> summary = serviceManager
					.getActivityCodesService().getActivityCodesSummary(
							getPaginationObject(), getSearchSortBean());
			activityGridModel = summary.getEnitities();
			records = summary.getTotalRecords();
			total = (int) Math.ceil((double) records / (double) rows);
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	@SkipValidation
	public String activityMACDOperation() {
		ActivityCodes activityCodeObj = new ActivityCodes();
		try {
			if (oper.equalsIgnoreCase("add")) {

				activityCodeObj.setActivityCode(activityCode);
				activityCodeObj.setActivity(activity);
				activityCodeObj.setDescription(description);
				activityCodeObj.setStatus(Status.ACTIVE);
				activityCodeObj.setCreatedBy((String) session.get("username"));
				activityCodeObj.setUpdatedBy((String) session.get("username"));
				activityCodeObj.setCreatedDate(new Date());
				activityCodeObj.setUpdatedDate(new Date());
				serviceManager.getActivityCodesService().createActivityCode(
						activityCodeObj);

			} else if (oper.equalsIgnoreCase("edit")) {
				activityCodeObj.setId(Long.valueOf(id));
				activityCodeObj.setActivityCode(activityCode);
				activityCodeObj.setActivity(activity);
				activityCodeObj.setDescription(description);
				activityCodeObj.setStatus(Status.ACTIVE);
				activityCodeObj.setCreatedBy(createdBy);
				activityCodeObj.setCreatedDate(createdDate);
				activityCodeObj.setUpdatedBy((String) session.get("username"));
				activityCodeObj.setUpdatedDate(new Date());
				serviceManager.getActivityCodesService().modifyActivityCode(
						activityCodeObj);
			} else if (oper.equalsIgnoreCase("del")) {
				if (id != null & !id.equalsIgnoreCase("")) {
					serviceManager.getActivityCodesService().deleteActivityCodes(
							Utility.getListFromCommaSeparated(id));
				}
			}
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	public List<ActivityCodes> getActivityGridModel() {
		return activityGridModel;
	}

	public void setActivityGridModel(List<ActivityCodes> activityGridModel) {
		this.activityGridModel = activityGridModel;
	}

	public String getActivityCode() {
		return activityCode;
	}

	public void setActivityCode(String activityCode) {
		this.activityCode = activityCode;
	}

	public String getActivity() {
		return activity;
	}

	public void setActivity(String activity) {
		this.activity = activity;
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
			this.total = (int) Math.ceil((double) this.records
					/ (double) this.rows);
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
}
