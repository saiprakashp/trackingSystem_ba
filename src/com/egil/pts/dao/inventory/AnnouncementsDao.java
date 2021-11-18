package com.egil.pts.dao.inventory;

import java.util.List;

import com.egil.pts.dao.common.GenericDao;
import com.egil.pts.dao.domain.Announcements;
import com.egil.pts.modal.Pagination;
import com.egil.pts.modal.SearchSortContainer;

public interface AnnouncementsDao extends GenericDao<Announcements, Long> {
	public List<Announcements> getAnnouncements(Pagination pagination,
			SearchSortContainer searchSortObj) throws Throwable;

	public int getAnnouncementsCount(SearchSortContainer searchSortObj)
			throws Throwable;

	public Integer deleteAnnouncements(List<Long> announcementIdList)
			throws Throwable;
	
	public List<Announcements> getDashboardDetails()
			throws Throwable ;
}
