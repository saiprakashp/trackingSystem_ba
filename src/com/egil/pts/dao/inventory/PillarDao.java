package com.egil.pts.dao.inventory;

import java.util.List;

import com.egil.pts.dao.common.GenericDao;
import com.egil.pts.dao.domain.Pillar;
import com.egil.pts.modal.Pagination;
import com.egil.pts.modal.SearchSortContainer;

public interface PillarDao extends GenericDao<Pillar, Long> {
	public List<Pillar> getPillars(Pagination pagination,
			SearchSortContainer searchSortObj) throws Throwable;

	public int getPillarsCount(SearchSortContainer searchSortObj)
			throws Throwable;

	public Integer deletePillars(List<Long> pillarIdList)
			throws Throwable;
}
