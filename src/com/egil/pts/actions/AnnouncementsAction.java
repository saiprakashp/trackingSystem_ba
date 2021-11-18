package com.egil.pts.actions;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.struts2.interceptor.validation.SkipValidation;
import org.hibernate.internal.util.StringHelper;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.egil.pts.dao.domain.Announcements;
import com.egil.pts.modal.Pagination;
import com.egil.pts.modal.SearchSortContainer;
import com.egil.pts.modal.SummaryResponse;
import com.egil.pts.util.Utility;

@Controller("announcementsAction")
@Scope("prototype")
public class AnnouncementsAction extends PTSAction  {

	
	private static final long serialVersionUID = 1L;
	
	
	private List<Announcements> gridModel = new ArrayList<Announcements>();
	
	private String announcementType;
	private String announcementSubject;
	private String announcementBy;
	private String announcementDesc;
	
	private String announcementDate;
	private String expiryDate;
	
	private Date announcedDate;
	private Date expiresOn;
	private String searchStatus;
	
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

	public String execute() {
		try {
			
		} catch(Throwable th) {
			th.printStackTrace();
		}
		return SUCCESS;
	}
	
	@SkipValidation
	public String goManageAnnouncements() {
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
			SummaryResponse<Announcements> summary = serviceManager.getAnnouncementService()
					.getAnnouncementSummary(getPaginationObject(), getSearchSortBean());
			gridModel = summary.getEnitities();

			records = summary.getTotalRecords();

			// calculate the total pages for the query
			total =(rows!=null && gridModel!=null)? (int) Math.ceil((double) records / (double) rows) : 0;

		} catch (Throwable e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	public String saveAnnouncement() {
		try {
			Announcements announcementObj = new Announcements();
			if (id != null & !id.equalsIgnoreCase(""))  {
				announcementObj.setId(Long.valueOf(id));
				announcementObj.setCreatedBy(createdBy);
				announcementObj.setCreatedDate(createdDate);
				announcementObj.setUpdatedBy((String) session.get("username"));
				announcementObj.setUpdatedDate(new Date());
				
			} else {
				announcementObj.setCreatedBy((String) session.get("username"));
				announcementObj.setCreatedDate(new Date());
				announcementObj.setUpdatedBy((String) session.get("username"));
				announcementObj.setUpdatedDate(new Date());
			}
			announcementObj.setAnnouncementType(announcementType);
			announcementObj.setSubject(announcementSubject);
			announcementObj.setAnnouncedBy(announcementBy);
			announcementObj.setDescription(announcementDesc);
			announcementObj.setAnnouncedDate(getAnnouncedDate());
			announcementObj.setExpiresOn(getExpiresOn());
			announcementObj.setStatus(true);
			
			serviceManager.getAnnouncementService().saveAnnouncement(announcementObj);
		} catch (Throwable e) {
			e.printStackTrace();
			if (id != null & !id.equalsIgnoreCase(""))  {
				goEditAnnouncement();
			} else {
				goCreateAnnouncement();
			}
			addActionMessage("Invalid input...");
			return ERROR;
		}
		return SUCCESS;
	}

	public String goCreateAnnouncement() {
		return SUCCESS;
		
	}

	public String goEditAnnouncement() {
		try {
			if (id != null && !id.equalsIgnoreCase("")) {
				Announcements announcementObj = serviceManager.getAnnouncementService().getAnnouncementDetails(Long.valueOf(id));
				announcementType = announcementObj.getAnnouncementType();
				announcementSubject = announcementObj.getSubject();
				announcementBy = announcementObj.getAnnouncedBy();
				announcementDesc = announcementObj.getDescription();
				DateFormat format = new SimpleDateFormat("MM/dd/yyyy");
				announcementDate = announcementObj.getAnnouncedDate() == null ? "" : format.format(announcementObj.getAnnouncedDate());
				expiryDate = announcementObj.getExpiresOn() == null ? "" : format.format(announcementObj.getExpiresOn());
				createdBy = announcementObj.getCreatedBy();
				createdDate = announcementObj.getCreatedDate();
			}
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	@SkipValidation
	public String announcementMACDOperation() {
		try {
			if (oper.equalsIgnoreCase("del")) {
				if (id != null && !id.equalsIgnoreCase("")) {
					serviceManager.getAnnouncementService().deleteAnnouncements(
							Utility.getListFromCommaSeparated(id));
				}
			}
		} catch (Throwable e) {
			e.printStackTrace();
		}

		return SUCCESS;
	}
	
	public String getAnnouncementType() {
		return announcementType;
	}

	public void setAnnouncementType(String announcementType) {
		this.announcementType = announcementType;
	}

	public String getAnnouncementSubject() {
		return announcementSubject;
	}

	public void setAnnouncementSubject(String announcementSubject) {
		this.announcementSubject = announcementSubject;
	}

	public String getAnnouncementBy() {
		return announcementBy;
	}

	public void setAnnouncementBy(String announcementBy) {
		this.announcementBy = announcementBy;
	}

	public String getAnnouncementDesc() {
		return announcementDesc;
	}

	public void setAnnouncementDesc(String announcementDesc) {
		this.announcementDesc = announcementDesc;
	}

	public String getAnnouncementDate() {
		return announcementDate;
	}

	public void setAnnouncementDate(String announcementDate) {
		this.announcementDate = announcementDate;
	}

	public String getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(String expiryDate) {
		this.expiryDate = expiryDate;
	}

	public Date getAnnouncedDate() {
		try {
			DateFormat format = new SimpleDateFormat("MM/dd/yyyy");
			if (StringHelper.isNotEmpty(announcementDate))
				announcedDate = format.parse(announcementDate);
			else
				announcedDate = null;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return announcedDate;
	}

	public void setAnnouncedDate(Date announcedDate) {
		this.announcedDate = announcedDate;
	}

	public Date getExpiresOn() {
		try {
			DateFormat format = new SimpleDateFormat("MM/dd/yyyy");
			if (StringHelper.isNotEmpty(expiryDate))
				expiresOn = format.parse(expiryDate);
			else
				expiresOn = null;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return expiresOn;
	}

	public void setExpiresOn(Date expiresOn) {
		this.expiresOn = expiresOn;
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

		if (searchStatus != null && searchStatus.equalsIgnoreCase("All")) {
			searchSortObj.setSearchStatus("");
		} else {
			searchSortObj.setSearchStatus(searchStatus);
		}


		return searchSortObj;
	}

	public List<Announcements> getGridModel() {
		return gridModel;
	}

	public void setGridModel(List<Announcements> gridModel) {
		this.gridModel = gridModel;
	}

	public String getSearchStatus() {
		return searchStatus;
	}

	public void setSearchStatus(String searchStatus) {
		this.searchStatus = searchStatus;
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
}
