package com.egil.pts.dao.inventory;

import java.util.List;

import com.egil.pts.dao.common.GenericDao;
import com.egil.pts.dao.domain.ReportQueryMapper;
import com.egil.pts.modal.Pagination;
import com.egil.pts.modal.ResourceEffort;

public interface ReportQueryMapperDao extends GenericDao<ReportQueryMapper, Long> {
	public List<ReportQueryMapper> getReportMapper();

	public boolean saveUpdateReportMapper(String queryOperation, String queryDecription, String selectedReport,
			String queryNeeded);

}
