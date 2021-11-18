package com.egil.pts.dao.inventory.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.transform.AliasToEntityMapResultTransformer;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.egil.pts.dao.common.hibernate.HibernateGenericDao;
import com.egil.pts.dao.inventory.PtsQueryUIDao;

@Repository("ptsQueryUIDao")
public class HibernatePtsUiDao extends HibernateGenericDao<PtsQueryUIDao, Long> implements PtsQueryUIDao {
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<Map<String, Object>> getQueryResultData(String query) throws Throwable {
		Query q = getSession().createSQLQuery(query);
		q.setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);
		return q.list();
	}

	@Override
	public List<Map<String, Object>> getQueryResultData(String query, String project, Date weekStarting,
			Date weekEnding, String release, String type, String reportType) {

		if (type != null && type.equalsIgnoreCase("MONTH")) {
			query = query.replaceAll("&TYPE", "   ,monthname( u.WEEKENDING_DATE) MONTH   ");
			// u.USER_ID
			if (reportType != null && reportType.equalsIgnoreCase("USER")) {
				query = query.replaceAll("&GROUP", "  monthname( u.WEEKENDING_DATE) ,u.USER_ID  ");
			} else {
				query = query.replaceAll("&GROUP", "  monthname( u.WEEKENDING_DATE)  ");
			}

		} else if (type != null && type.equalsIgnoreCase("WEEKEND_DATE")) {
			query = query.replaceAll("&TYPE", "    ,( u.WEEKENDING_DATE)     ");
			if (reportType != null && reportType.equalsIgnoreCase("USER")) {
				query = query.replaceAll("&GROUP", " u.WEEKENDING_DATE  ,u.USER_ID  ");
			} else {
				query = query.replaceAll("&GROUP", " u.WEEKENDING_DATE ");
			}

		} else {
			query = query.replaceAll("&TYPE", " ");
			if (reportType != null && reportType.equalsIgnoreCase("USER")) {
				query = query.replaceAll("&GROUP", " n.id ,u.USER_ID ");
			} else {
				query = query.replaceAll("&GROUP", " n.id ");
			}

		}

		if (weekStarting != null && weekEnding != null) {
			query=		query.replaceAll("&WEEK", "  and  (u.WEEKENDING_DATE >=:statDate  or u.WEEKENDING_DATE <=:endDate )  ");
		}else {
			query=	query.replaceAll("&WEEK", "   ");
		}
		if (project != null) {
			query=	query.replaceAll("&PROJECT", "  and   n.PROJECT_ID =:project  ");
		}else {
			query=	query.replaceAll("&PROJECT", " ");
		}
		if (release != null) {
			query=	query.replaceAll("&RELEASE", "  and   n.id =:release  ");
		}else {
			query=	query.replaceAll("&RELEASE", " ");
		}

		Query q = getSession().createSQLQuery(query);
		if (query.contains(":statDate") &&weekStarting != null && weekEnding != null) {
			q.setDate("statDate", weekStarting);
			q.setDate("endDate", weekEnding);
		}
		if (query.contains(":project") &&project != null) {
			q.setString("project", project);
		}
		if (query.contains(":release") &&release != null) {
			q.setString("release", release);
		}

		q.setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);
		return q.list();
	}
}
