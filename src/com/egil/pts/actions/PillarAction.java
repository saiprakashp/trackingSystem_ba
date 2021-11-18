package com.egil.pts.actions;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.struts2.interceptor.validation.SkipValidation;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.egil.pts.modal.Pagination;
import com.egil.pts.modal.Pillar;
import com.egil.pts.modal.SearchSortContainer;
import com.egil.pts.modal.Status;
import com.egil.pts.modal.SummaryResponse;
import com.egil.pts.util.Utility;

@Controller("pillarAction")
@Scope("prototype")
public class PillarAction extends PTSAction {

	private static final long serialVersionUID = 1L;
	private List<Pillar> pillarGridModel = new ArrayList<Pillar>();
	private String pillarName;
	private String customer;
	private String serviceAssurance;
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

	private String customersMap;

	private String selectedCustomer;

	private String pillarData;

	@SkipValidation
	public String execute() {
		try {
			clearSession("supervisorMap", "userToBackFillMap", "privilegesMap",
					"rolesMap", "streamsMap", "platformsMap", "userTypesMap",
					"technologiesMap");
			customersMap = serviceManager.getCustomerService()
					.getJSONStringOfAccounts();
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	@SkipValidation
	public String goManagePillars() {

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
			SummaryResponse<Pillar> summary = serviceManager.getPillarService()
					.getPillarsSummary(getPaginationObject(),
							getSearchSortBean());
			pillarGridModel = summary.getEnitities();
			records = summary.getTotalRecords();
			total = (int) Math.ceil((double) records / (double) rows);
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	@SkipValidation
	public String pillarMACDOperation() {
		Pillar pillarObj = new Pillar();
		try {
			if (oper.equalsIgnoreCase("add")) {
				pillarObj.setPillarName(pillarName);
				pillarObj.setCustomer(customer);
				pillarObj.setServiceAssurance(serviceAssurance);
				pillarObj.setDescription(description);
				pillarObj.setStatus(Status.ACTIVE);
				pillarObj.setCreatedBy((String) session.get("username"));
				pillarObj.setUpdatedBy((String) session.get("username"));
				pillarObj.setCreatedDate(new Date());
				pillarObj.setUpdatedDate(new Date());
				serviceManager.getPillarService().createPillar(pillarObj);

			} else if (oper.equalsIgnoreCase("edit")) {
				pillarObj.setId(Long.valueOf(id));
				pillarObj.setPillarName(pillarName);
				pillarObj.setCustomer(customer);
				pillarObj.setServiceAssurance(serviceAssurance);
				pillarObj.setDescription(description);
				pillarObj.setStatus(Status.ACTIVE);
				pillarObj.setCreatedBy(createdBy);
				pillarObj.setCreatedDate(createdDate);
				pillarObj.setUpdatedBy((String) session.get("username"));
				pillarObj.setUpdatedDate(new Date());
				serviceManager.getPillarService().modifyPillar(pillarObj);
			} else if (oper.equalsIgnoreCase("del")) {
				if (id != null & !id.equalsIgnoreCase("")) {
					serviceManager.getPillarService().deletePillars(
							Utility.getListFromCommaSeparated(id));
				}
			}
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	@SkipValidation
	public String loadPillars() {
		try {
			pillarData = serviceManager.getPillarService().loadPillars(
					selectedCustomer);
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	public String getPillarName() {
		return pillarName;
	}

	public void setPillarName(String pillarName) {
		this.pillarName = pillarName;
	}

	public String getCustomer() {
		return customer;
	}

	public void setCustomer(String customer) {
		this.customer = customer;
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

	public List<Pillar> getPillarGridModel() {
		return pillarGridModel;
	}

	public void setPillarGridModel(List<Pillar> pillarGridModel) {
		this.pillarGridModel = pillarGridModel;
	}

	public String getCustomersMap() {
		return customersMap;
	}

	public void setCustomersMap(String customersMap) {
		this.customersMap = customersMap;
	}

	public String getSelectedCustomer() {
		return selectedCustomer;
	}

	public void setSelectedCustomer(String selectedCustomer) {
		this.selectedCustomer = selectedCustomer;
	}

	public String getPillarData() {
		return pillarData;
	}

	public void setPillarData(String pillarData) {
		this.pillarData = pillarData;
	}

	public String getServiceAssurance() {
		return serviceAssurance;
	}

	public void setServiceAssurance(String serviceAssurance) {
		this.serviceAssurance = serviceAssurance;
	}

}
