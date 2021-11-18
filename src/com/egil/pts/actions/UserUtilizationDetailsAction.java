package com.egil.pts.actions;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.struts2.interceptor.validation.SkipValidation;
import org.hibernate.internal.util.StringHelper;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.egil.pts.modal.ResourceEffort;

@Controller("userUtilizationDetailsAction")
@Scope("prototype")
public class UserUtilizationDetailsAction extends PTSAction {
	private static final long serialVersionUID = 1L;

	private List<ResourceEffort> subGridModel = new ArrayList<ResourceEffort>();

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
	public String manageUserUtilizationDetails() {
		try {
			clearSession("supervisorMap", "userToBackFillMap", "privilegesMap",
					"rolesMap", "streamsMap", "platformsMap", "userTypesMap",
					"technologiesMap");
			DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			long userId = 0l;
			if(StringHelper.isNotEmpty(id)){
				userId = Long.parseLong(id.substring(0,id.length()-8));
			}
			subGridModel = serviceManager.getUserUtilizationService()
					.getResourceUtilizationDetails(
							(managerId != null?Long.parseLong(managerId): 0L),
							format.format(getWeekEnding(selectedWeekEndingDate)), format.format(getWeekEnding(selectedWeekEndingDate)),
							userId, "DR", false);
			records = subGridModel.size();

			// calculate the total pages for the query
			total = (int) Math.ceil((double) records / (double) rows);

		} catch (Throwable e) {
			e.printStackTrace();
		}
		return SUCCESS;
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

	public List<ResourceEffort> getSubGridModel() {
		return subGridModel;
	}

	public void setSubGridModel(List<ResourceEffort> subGridModel) {
		this.subGridModel = subGridModel;
	}

}
