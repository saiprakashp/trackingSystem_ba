package com.egil.pts.actions;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts2.interceptor.validation.SkipValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.egil.pts.modal.FeedBack;
import com.egil.pts.modal.Pagination;
import com.egil.pts.modal.PtsHolidays;
import com.egil.pts.modal.Response;
import com.egil.pts.modal.SearchSortContainer;
import com.egil.pts.service.common.ServiceManager;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Controller("utilAction")
@Scope("prototype")
public class UtilAction extends PTSAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Autowired(required = true)
	@Qualifier("serviceManager")
	private ServiceManager serviceManager;

	private List<PtsHolidays> holidayList = new ArrayList<>();

	private String jsonString;

	private SearchSortContainer sortContainer;
	private String status;
	private Long feedId;
	private PtsHolidays ptsHolidays;
	private String id;
	private String date;
	private String locationName;
	private String holiday;
	private String day;
	// nt req
	private String bangalore;
	private String chennai;
	private String hyderabad;
	private String us;
	private String country;
	private String comments;
	private boolean showAllFeedBacks;
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

	private String title;
	private String description;
	private String reporter;
	private String priority;
	private List<FeedBack> feedbackList = new ArrayList<FeedBack>();

	@Override
	public String execute() throws Exception {
		clearSession("supervisorMap", "userToBackFillMap", "privilegesMap", "rolesMap", "streamsMap", "platformsMap",
				"userTypesMap", "technologiesMap");
		return SUCCESS;
	}

	public String getHolidays() {
		return SUCCESS;
	}

	public String loadHolidays() {
		if (oper != null) {
			doChanges();
		}
		return SUCCESS;
	}

	public String feedback() {

		try {
			Long userId = Long.parseLong((session.get("userId") == null) ? "-1" : session.get("userId") + "");
			if (session.get("role") != null && "ADMIN".equalsIgnoreCase(session.get("role") + ""))
				userId = -1L;

			feedbackList = serviceManager.getUtilService().getFeedback(null, null, null, userId, getPaginationObject());
			total = feedbackList.size();
		} catch (Throwable e) {
			e.printStackTrace();
			addActionMessage("Unable to load FeedBack...");
		}
		return SUCCESS;
	}

	protected Pagination getPaginationObject() {
		Pagination pagination = new Pagination();
		int to = (rows * page);
		int from = to - rows;
		pagination.setOffset(from);
		pagination.setSize(to);
		return pagination;
	}

	@SkipValidation
	public String goGetfeedback() {
		return SUCCESS;
	}

	public String goAjaxManagefeedback() {
		try {
			Long userId = Long.parseLong((session.get("userId") == null) ? "-1" : session.get("userId") + "");

			if (oper.equalsIgnoreCase("add")) {
				serviceManager.getUtilService().manangeFeedback(title, description, priority, reporter,
						session.get("fullName") + "", userId);
			} else if (oper.equalsIgnoreCase("edit")) {
				serviceManager.getUtilService().updateFeedBackStatus(id, title, description, priority, comments,status);
			} else if (oper.equalsIgnoreCase("del")) {
				if (id != null & !id.equalsIgnoreCase("")) {
					serviceManager.getUtilService().deleteFeedBackStatus(id);
				}
			}
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	@SkipValidation
	public String goAjaxGetfeedback() {
		clearErrorsAndMessages();

		try {
			Long userId = Long.parseLong((session.get("userId") == null) ? "-1" : session.get("userId") + "");
			feedbackList.clear();
			String user = "Admin";
			if (session.get("role") != null && (session.get("role") + "").equalsIgnoreCase("USER")) {
				user = session.get("fullName") + "";
			}
			String str = (session.get("str") != null) ? session.get("str") + "" : "FALSE";
 
			// session.get("fullName") + ""
			feedbackList = serviceManager.getUtilService().getFeedback(user, null, null, userId, getPaginationObject());
			if (session.get("rowNum") == null) {
				session.put("rowNum", rows);
			} else {
				if (rows != null && (rows.intValue() != ((Integer) session.get("rowNum")).intValue())) {
					session.put("rowNum", rows);
				}
			}
			records = serviceManager.getUtilService().getFeedbackCount(user, null, null, userId, getPaginationObject());
			total = (int) Math.ceil((double) records / (double) rows);
			addActionMessage("FeedBack Saved succesfully");
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	public String savefeedback() {
		clearErrorsAndMessages();
		GsonBuilder builder = new GsonBuilder();
		Gson gson = builder.create();
		try {
			serviceManager.getUtilService().manangeFeedback(title, description, priority, reporter,
					session.get("userName") + "", Long.parseLong(session.get("userId") + ""));
			addActionMessage("FeedBack Saved succesfully");
		} catch (Throwable e) {
			e.printStackTrace();
			jsonString = gson.toJson(new Response("500", "Failed"));
		}
		jsonString = gson.toJson(new Response("200", "Success"));
		return SUCCESS;
	}

	private void doChanges() {
		if (oper.equalsIgnoreCase("add")) {
			ptsHolidays = new PtsHolidays();
			ptsHolidays.setBangalore(bangalore);
			ptsHolidays.setChennai(chennai);
			ptsHolidays.setCountry(country);
			ptsHolidays.setDate(date);
			ptsHolidays.setHoliday(holiday);
			ptsHolidays.setLocationName(locationName);
			ptsHolidays.setUs(us);
		} else if (oper.equalsIgnoreCase("edit")) {
			ptsHolidays = new PtsHolidays();
			ptsHolidays.setId(Long.parseLong(id));
			ptsHolidays.setBangalore(bangalore);
			ptsHolidays.setChennai(chennai);
			ptsHolidays.setCountry(country);
			ptsHolidays.setDate(date);
			ptsHolidays.setHoliday(holiday);
			ptsHolidays.setLocationName(locationName);
			ptsHolidays.setUs(us);
		} else if (oper.equalsIgnoreCase("delete")) {
			ptsHolidays = new PtsHolidays();
			ptsHolidays.setId(Long.parseLong(id));
		}
		try {
			// serviceManager.getUtilService().manageHolidays(oper, ptsHolidays);
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String manageHolidays() {
		setPtsHolidaysData();
		holidayList = getServiceManager().getUserService().manageHolidays(ptsHolidays, null);
		toString();
		return SUCCESS;
	}

	private void setPtsHolidaysData() {
		ptsHolidays = new PtsHolidays();
	}

	private void setSearchFields() {
		sortContainer = new SearchSortContainer();
		sortContainer.setSearchLocation(locationName);

	}

	public String ajaxManageHolidays() {
		setSearchFields();
		try {
			holidayList = getServiceManager().getUserService().getHolidayList(null, sortContainer);
			toString();
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}

	public List<PtsHolidays> getHolidayList() {
		return holidayList;
	}

	public void setHolidayList(List<PtsHolidays> holidayList) {
		this.holidayList = holidayList;
	}

	public SearchSortContainer getSortContainer() {
		return sortContainer;
	}

	public void setSortContainer(SearchSortContainer sortContainer) {
		this.sortContainer = sortContainer;
	}

	public PtsHolidays getPtsHolidays() {
		return ptsHolidays;
	}

	public void setPtsHolidays(PtsHolidays ptsHolidays) {
		this.ptsHolidays = ptsHolidays;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getLocationName() {
		return locationName;
	}

	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}

	public String getHoliday() {
		return holiday;
	}

	public void setHoliday(String holiday) {
		this.holiday = holiday;
	}

	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}

	public String getBangalore() {
		return bangalore;
	}

	public void setBangalore(String bangalore) {
		this.bangalore = bangalore;
	}

	public String getChennai() {
		return chennai;
	}

	public void setChennai(String chennai) {
		this.chennai = chennai;
	}

	public String getHyderabad() {
		return hyderabad;
	}

	public void setHyderabad(String hyderabad) {
		this.hyderabad = hyderabad;
	}

	public String getUs() {
		return us;
	}

	public void setUs(String us) {
		this.us = us;
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

	@Override
	public String toString() {
		return "UtilAction [holidayList=" + holidayList + ", sortContainer=" + sortContainer + ", ptsHolidays="
				+ ptsHolidays + ", id=" + id + ", date=" + date + ", locationName=" + locationName + ", holiday="
				+ holiday + ", day=" + day + ", bangalore=" + bangalore + ", chennai=" + chennai + ", hyderabad="
				+ hyderabad + ", us=" + us + ", rows=" + rows + ", page=" + page + ", sord=" + sord + ", sidx=" + sidx
				+ ", searchField=" + searchField + ", searchString=" + searchString + ", totalrows=" + totalrows
				+ ", searchOper=" + searchOper + ", oper=" + oper + ", total=" + total + ", records=" + records + "]";
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public ServiceManager getServiceManager() {
		return serviceManager;
	}

	public void setServiceManager(ServiceManager serviceManager) {
		this.serviceManager = serviceManager;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<FeedBack> getFeedbackList() {
		return feedbackList;
	}

	public void setFeedbackList(List<FeedBack> feedbackList) {
		this.feedbackList = feedbackList;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Long getFeedId() {
		return feedId;
	}

	public void setFeedId(Long feedId) {
		this.feedId = feedId;
	}

	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	public String getReporter() {
		return reporter;
	}

	public void setReporter(String reporter) {
		this.reporter = reporter;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getJsonString() {
		return jsonString;
	}

	public void setJsonString(String jsonString) {
		this.jsonString = jsonString;
	}

	public String getStatusMap() {
		String returnValue = "";
		returnValue += "Pending" + ":" + "Pending" + ";";
		returnValue += "Accepted" + ":" + "Accepted" + ";";
		returnValue += "Completed" + ":" + "Completed" + ";";
		returnValue += "Rejected" + ":" + "Rejected";
		return returnValue;
	}

	public String getPriorityMap() {

		String returnValue = "";
		returnValue += "1" + ":" + "1" + ";";
		returnValue += "2" + ":" + "2" + ";";
		returnValue += "3" + ":" + "3" + ";";
		returnValue += "4" + ":" + "4";

		return returnValue;
	}

	public boolean isShowAllFeedBacks() {
		return showAllFeedBacks;
	}

	public void setShowAllFeedBacks(boolean showAllFeedBacks) {
		this.showAllFeedBacks = showAllFeedBacks;
	}

}