package com.egil.pts.service;

import java.util.List;

import com.egil.pts.dao.domain.Announcements;
import com.egil.pts.modal.Pagination;
import com.egil.pts.modal.SearchSortContainer;
import com.egil.pts.modal.SummaryResponse;

public interface AnnouncementService {

	public SummaryResponse<Announcements> getAnnouncementSummary(Pagination paginationObject,
			SearchSortContainer searchSortBean) throws Throwable;

	public Integer deleteAnnouncements(List<Long> listFromCommaSeparated) throws Throwable;
	
	public void saveAnnouncement(Announcements announcement)
			throws Throwable;

	public Announcements getAnnouncementDetails(Long valueOf) throws Throwable;
	
}
