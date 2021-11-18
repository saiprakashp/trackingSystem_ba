package com.egil.pts.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.egil.pts.dao.domain.Announcements;
import com.egil.pts.modal.Pagination;
import com.egil.pts.modal.SearchSortContainer;
import com.egil.pts.modal.SummaryResponse;
import com.egil.pts.service.AnnouncementService;
import com.egil.pts.service.common.BaseUIService;

@Service("announcementService")
public class AnnouncementServiceImpl extends BaseUIService implements AnnouncementService {

	@Override
	public SummaryResponse<Announcements> getAnnouncementSummary(Pagination paginationObject,
			SearchSortContainer searchSortBean) throws Throwable {
		SummaryResponse<Announcements> summary = new SummaryResponse<Announcements>();
		summary.setTotalRecords(daoManager.getAnnouncementsDao()
				.getAnnouncementsCount(searchSortBean));
		summary.setEnitities(daoManager.getAnnouncementsDao()
				.getAnnouncements(paginationObject, searchSortBean));
		
		return summary;
	}

	@Override
	public Integer deleteAnnouncements(List<Long> listFromCommaSeparated) throws Throwable {
		return daoManager.getAnnouncementsDao().deleteAnnouncements(listFromCommaSeparated);
		
	}
	
	@Override
	@Transactional
	public void saveAnnouncement(Announcements announcement)
			throws Throwable {
		daoManager.getAnnouncementsDao().save(announcement);
	}

	@Override
	public Announcements getAnnouncementDetails(Long valueOf) throws Throwable {
		return daoManager.getAnnouncementsDao().get(valueOf);
	}


}
