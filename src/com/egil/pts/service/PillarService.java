package com.egil.pts.service;

import java.util.List;
import java.util.Map;

import com.egil.pts.modal.Pagination;
import com.egil.pts.modal.Pillar;
import com.egil.pts.modal.SearchSortContainer;
import com.egil.pts.modal.SummaryResponse;

public interface PillarService {

	public SummaryResponse<Pillar> getPillarsSummary(Pagination pagination,
			SearchSortContainer searchSortContainer) throws Throwable;

	public List<Pillar> getPillars(Pagination pagination,
			SearchSortContainer searchSortContainer) throws Throwable;

	public void createPillar(Pillar pillar) throws Throwable;

	public void modifyPillar(Pillar pillar) throws Throwable;

	public Integer deletePillars(List<Long> pillarIdList) throws Throwable;

	public void getPillarsMap(Map<Long, String> pillarsMap, String customerId) throws Throwable;

	public String getJSONStringOfPillars() throws Throwable;

	public String loadPillars(String customerId) throws Throwable;
}
