package com.egil.pts.dao.inventory.impl;

import java.io.Serializable;
import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.egil.pts.dao.common.hibernate.HibernateGenericDao;
import com.egil.pts.dao.domain.ReportQueryMapper;
import com.egil.pts.dao.inventory.ReportQueryMapperDao;

@Repository("reportQueryMapperDao")
public class HiberanteReportsQueryDao extends HibernateGenericDao<ReportQueryMapper, Long>
		implements ReportQueryMapperDao {
	@Transactional
	@Override
	public List<ReportQueryMapper> getReportMapper() {
		Serializable serialize = 1L;
		return getSession().createQuery("from ReportQueryMapper").list();
	}

	@Override
	public boolean saveUpdateReportMapper(String queryOperation, String queryDecription, String selectedReport,
			String queryNeeded) {
		if (queryOperation != null && queryOperation.equalsIgnoreCase("D")) {
			return (getSession().createSQLQuery("delete from PTS_REPORT_QUERY_MAPPER where REPORT_NAME=:report")
					.setString("report", selectedReport).executeUpdate() > 0) ? true : false;
		} else {
			return (getSession().createSQLQuery(
					"INSERT INTO PTS_REPORT_QUERY_MAPPER (REPORT_NAME,REPORT_QUERY,DESCRIPTION) VALUES (:key, :query, :desc) "
							+ " ON DUPLICATE KEY UPDATE DESCRIPTION=:desc  , REPORT_QUERY =:query , REPORT_NAME=:key")
					.setString("desc", queryDecription).setString("key", selectedReport).setString("query", queryNeeded)
					.executeUpdate() > 0) ? true : false;
		}
	}
}
