package com.egil.pts.dao.inventory.impl;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.egil.pts.dao.common.hibernate.HibernateGenericDao;
import com.egil.pts.dao.domain.EssDetails;
import com.egil.pts.dao.inventory.ESSDetailsDao;
import com.egil.pts.modal.Pagination;
import com.egil.pts.modal.SearchSortContainer;

@Repository("essDetailsDao")
public class HibernateESSDetailsDao extends HibernateGenericDao<EssDetails, Long> implements ESSDetailsDao {

	@Override
	public List<EssDetails> getEssDetails(Pagination pagination, SearchSortContainer searchSortObj) throws Throwable {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void saveESSDetails(List<EssDetails> essDetailsList) throws Throwable {
		int count = 0;
		if (essDetailsList != null && essDetailsList.size() > 0) {
			for (EssDetails essDet : essDetailsList) {
				save(essDet);
				if (count % JDBC_BATCH_SIZE == 0) {
					getSession().flush();
					getSession().clear();
				}
				count++;
			}
		}
	}
	
	@Override
	public int removeESSDetails(Long year, String month) throws Throwable {
		StringBuilder queryString = new StringBuilder();
		queryString.append("delete from ESS_DETAILS where year=:year and month=:month ");
		Query query = getSession().createSQLQuery(queryString.toString());
		query.setLong("year", year);
		query.setString("month", month);
		
		return query.executeUpdate();
	}

}
