package com.egil.pts.dao.inventory;

import java.util.List;

import com.egil.pts.dao.common.GenericDao;
import com.egil.pts.dao.domain.EssDetails;
import com.egil.pts.modal.Pagination;
import com.egil.pts.modal.SearchSortContainer;

public interface ESSDetailsDao extends GenericDao<EssDetails, Long> {
	public List<EssDetails> getEssDetails(Pagination pagination, SearchSortContainer searchSortObj) throws Throwable;

	public void saveESSDetails(List<EssDetails> essDetailsList) throws Throwable;
	
	public int removeESSDetails(Long year, String month) throws Throwable;

}
