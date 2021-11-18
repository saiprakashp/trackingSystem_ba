package com.egil.pts.dao.inventory.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.type.LongType;
import org.hibernate.type.StringType;
import org.springframework.stereotype.Repository;

import com.egil.pts.dao.common.hibernate.HibernateGenericDao;
import com.egil.pts.dao.domain.ActivityCodes;
import com.egil.pts.dao.inventory.ActivityCodesDao;
import com.egil.pts.modal.Pagination;
import com.egil.pts.modal.SearchSortContainer;

@Repository("activityCodesDao")
public class HibernateActivityCodesDao extends HibernateGenericDao<ActivityCodes, Long> implements ActivityCodesDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<ActivityCodes> getActivityCodes(Pagination pagination, SearchSortContainer searchSortObj)
			throws Throwable {

		StringBuilder queryString = new StringBuilder();
		queryString.append("from ActivityCodes where status=1 ");
		if (searchSortObj != null) {
			if (searchSortObj.getType().equalsIgnoreCase("ALL")) {
				queryString.append(" ");
			} else if (searchSortObj.getType()!=null) {
				queryString.append(" and type='"+searchSortObj.getType()+"' ");
			}   else {
				queryString.append(" and type='D' ");
			}

			if (searchSortObj.getSearchField() != null && !searchSortObj.getSearchField().equals("")
					&& searchSortObj.getSearchString() != null && !searchSortObj.getSearchString().equals("")) {
				if (searchSortObj.getSearchField().equalsIgnoreCase("activityCode")) {
					queryString.append(" and activityCodeId like '%" + searchSortObj.getSearchString() + "%'");
				} else if (searchSortObj.getSearchField().equalsIgnoreCase("activity")) {
					queryString.append(" and activityCodeName like '%" + searchSortObj.getSearchString() + "%'");
				}
			}

			if (searchSortObj.getSidx() != null && !searchSortObj.getSidx().equals("")
					&& searchSortObj.getSord() != null && !searchSortObj.getSord().equals("")) {
				if (searchSortObj.getSidx().equalsIgnoreCase("activityCode")) {
					queryString.append(" order by activityCodeId " + searchSortObj.getSord());
				} else if (searchSortObj.getSidx().equalsIgnoreCase("activity")) {
					queryString.append(" order by activityCodeName " + searchSortObj.getSord());
				} else if (searchSortObj.getSidx().equalsIgnoreCase("createdBy")) {
					queryString.append(" order by createdBy " + searchSortObj.getSord());
				} else if (searchSortObj.getSidx().equalsIgnoreCase("createdDate")) {
					queryString.append(" order by createdDate " + searchSortObj.getSord());
				} else if (searchSortObj.getSidx().equalsIgnoreCase("updatedBy")) {
					queryString.append(" order by updatedBy " + searchSortObj.getSord());
				} else if (searchSortObj.getSidx().equalsIgnoreCase("updatedDate")) {
					queryString.append(" order by updatedDate " + searchSortObj.getSord());
				}
			}
		} else {
			queryString.append(" and type='D' ");
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
	public Integer deleteActivityCodes(List<Long> activityIdList) throws Throwable {
		Query query = getSession().createQuery("update ActivityCodes ac set ac.status = 0 where ac.id in (:id)");
		query.setParameterList("id", activityIdList);
		return query.executeUpdate();
	}

	@Override
	public int getActivityCodesCount(SearchSortContainer searchSortObj) throws Throwable {
		int count = 0;
		StringBuilder queryString = new StringBuilder();
		queryString.append("select count (*) from ActivityCodes where status=1 ");
		if (searchSortObj != null) {
			if (searchSortObj.getSearchField() != null && !searchSortObj.getSearchField().equals("")
					&& searchSortObj.getSearchString() != null && !searchSortObj.getSearchString().equals("")) {
				if (searchSortObj.getSearchField().equalsIgnoreCase("activityCode")) {
					queryString.append(" and activityCodeId like '%" + searchSortObj.getSearchString() + "%'");
				} else if (searchSortObj.getSearchField().equalsIgnoreCase("activity")) {
					queryString.append(" and activityCodeName like '%" + searchSortObj.getSearchString() + "%'");
				}
			}
		}
		Query query = getSession().createQuery(queryString.toString());
		Long obj = (Long) query.uniqueResult();
		if (obj != null) {
			count = obj.intValue();
		}
		return count;
	}

	@Override
	public List<Object[]> getActivityCodesNew(SearchSortContainer searchSortObj) throws Throwable {
		String sql = "select a.id,a.ACTIVITY_CODE_ID ,ACTIVITY_CODE_NAME,a.DESCRIPTION from PTS_NEW_ACTIVITY a where a.STATUS =1";

		sql = (searchSortObj.getUserId() != -1)
				? " select a.id,a.ACTIVITY_CODE_ID ,ACTIVITY_CODE_NAME,a.DESCRIPTION from PTS_NEW_ACTIVITY a, PTS_USER_ACTIVITIES pua where a.ID =pua.ACTIVITY_CODE_ID  and pua.USER_ID =:user  "
				: "";

		Query query = getSession().createSQLQuery(sql);

		if (searchSortObj.getUserId() != -1) {
			query.setLong("user", searchSortObj.getUserId());
		}
		return query.list();
	}

	@Override
	public Integer deleteActivityCodesNew(List<Long> activityIds) throws Throwable {
		Query query = getSession().createQuery("update ActivityCodesNew ac set ac.status = 0 where ac.id in (:id)");
		query.setParameterList("id", activityIds);
		return query.executeUpdate();
	}

	@Override
	public List<Object[]> getProjectActivityDao() {

		String sql = "select ID, NAME,`ORDER`,`type`, DESCRIPTION, STATUS from PTS_PROJECT_ASSIGNMENT_NEW where status=1 order by `order`  asc";

		Query query = getSession().createSQLQuery(sql).addScalar("ID", new LongType())
				.addScalar("NAME", new StringType()).addScalar("ORDER", new LongType())
				.addScalar("type", new StringType()).addScalar("DESCRIPTION", new StringType());

		return query.list();

	}

	@Override
	public Long getActivityCodes(String type, String activityType) {
		// TODO Auto-generated method stub
		return -1L;
		// getSession().createSQLQuery(sql).addScalar("ID", new LongType())
		// .addScalar("NAME", new StringType()).list();
	}

}
