package com.egil.pts.dao.inventory.impl;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.egil.pts.dao.common.hibernate.HibernateGenericDao;
import com.egil.pts.dao.domain.Announcements;
import com.egil.pts.dao.inventory.AnnouncementsDao;
import com.egil.pts.modal.Pagination;
import com.egil.pts.modal.SearchSortContainer;

@Repository("announcementsDao")
public class HibernateAnnouncementsDao extends
		HibernateGenericDao<Announcements, Long> implements AnnouncementsDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<Announcements> getAnnouncements(Pagination pagination,
			SearchSortContainer searchSortObj) throws Throwable {

		StringBuilder queryString = new StringBuilder();
		queryString.append("from Announcements where status=1 ");
		if (searchSortObj != null) {
			if (searchSortObj.getSearchField() != null
					&& !searchSortObj.getSearchField().equals("")
					&& searchSortObj.getSearchString() != null
					&& !searchSortObj.getSearchString().equals("")) {
				if (searchSortObj.getSearchField().equalsIgnoreCase(
						"subject")) {
					queryString.append(" and subject like '%"
							+ searchSortObj.getSearchString()+"%'");
				} else if (searchSortObj.getSearchField().equalsIgnoreCase(
						"announcementType")) {
					queryString.append(" and announcementType like '%"
							+ searchSortObj.getSearchString()+"%'");
				}
			}
			
			if(searchSortObj.getSidx() != null
					&& !searchSortObj.getSidx().equals("")
					&& searchSortObj.getSord() != null
					&& !searchSortObj.getSord().equals("")){
				if (searchSortObj.getSidx().equalsIgnoreCase(
						"subject")) {
					queryString.append(" order by subject "
							+ searchSortObj.getSord());
				} else if (searchSortObj.getSidx().equalsIgnoreCase(
						"announcementType")) {
					queryString.append(" order by announcementType "
							+ searchSortObj.getSord());
				} else if (searchSortObj.getSidx().equalsIgnoreCase(
						"createdBy")) {
					queryString.append(" order by createdBy "
							+ searchSortObj.getSord());
				} else if (searchSortObj.getSidx().equalsIgnoreCase(
						"createdDate")) {
					queryString.append(" order by createdDate "
							+ searchSortObj.getSord());
				} else if (searchSortObj.getSidx().equalsIgnoreCase(
						"updatedBy")) {
					queryString.append(" order by updatedBy "
							+ searchSortObj.getSord());
				} else if (searchSortObj.getSidx().equalsIgnoreCase(
						"updatedDate")) {
					queryString.append(" order by updatedDate "
							+ searchSortObj.getSord());
				}
			}
		}
		Query query = getSession().createQuery(queryString.toString());
		if (pagination != null) {
			if (pagination.getOffset() > 0)
				query.setFirstResult(pagination.getOffset());
			if (pagination.getSize() > 0)
				query.setMaxResults(pagination.getSize());
		}
		return query.list();
	
	}

	@Override
	public Integer deleteAnnouncements(List<Long> announcementIdList)
			throws Throwable {
		Query query = getSession().createQuery(
				"update Announcements ac set ac.status = 0 where ac.id in (:id)");
		query.setParameterList("id", announcementIdList);
		return query.executeUpdate();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Announcements> getDashboardDetails()
			throws Throwable {
		Query query = getSession().createQuery("from Announcements where current_date >= announced_date and current_date<=EXPIRES_ON ");
		return query.list();
	}
	
	@Override
	public int getAnnouncementsCount(SearchSortContainer searchSortObj)
			throws Throwable {
		int count = 0;
		StringBuilder queryString = new StringBuilder();
		queryString.append("select count (*) from Announcements where status=1 ");
		if (searchSortObj != null) {
			if (searchSortObj.getSearchField() != null
					&& !searchSortObj.getSearchField().equals("")
					&& searchSortObj.getSearchString() != null
					&& !searchSortObj.getSearchString().equals("")) {
				if (searchSortObj.getSearchField().equalsIgnoreCase(
						"subject")) {
					queryString.append(" and subject like '%"
							+ searchSortObj.getSearchString()+"%'");
				} else if (searchSortObj.getSearchField().equalsIgnoreCase(
						"announcementType")) {
					queryString.append(" and announcementType like '%"
							+ searchSortObj.getSearchString()+"%'");
				}
			}
		}
		Query query = getSession().createQuery(queryString.toString());
		Long obj = (Long)query.uniqueResult();
		if (obj != null) {
			count = obj.intValue();
		}
		return count;
	}

}
