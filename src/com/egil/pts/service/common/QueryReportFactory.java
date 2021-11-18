package com.egil.pts.service.common;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.egil.pts.modal.ReportQueryMapper;

public class QueryReportFactory<T extends QueryReportFactory<T>> {
	private Map<String, ReportQueryMapper> mapperData = new LinkedHashMap<String, ReportQueryMapper>();

	protected T getRef() {
		return (T) this;
	}

	public QueryReportFactory<T> getMappedObj(List<com.egil.pts.dao.domain.ReportQueryMapper> list, String user,
			Long userId) {
		ReportQueryMapper queryData = null;
		if (list == null || (list != null && list.size() <= 0)) {
			return getRef();
		}
		for (com.egil.pts.dao.domain.ReportQueryMapper mapper : list) {
			queryData = new ReportQueryMapper();
			String accessAllowed = mapper.getAccess();
			queryData.setQuery(mapper.getReportQuery());
			queryData.setDescription(mapper.getDescription());
			queryData.setKeywoard(mapper.getReportName());
			queryData.setAllowdResources(Arrays.asList(mapper.getAllowedusers().split(",")));
			if (accessAllowed.equalsIgnoreCase("N") && !mapper.getAllowedusers().contains(user)) {
				mapperData.put(mapper.getReportName(), queryData);
			} else if (accessAllowed.equalsIgnoreCase("Y")) {
				mapperData.put(mapper.getReportName(), queryData);
			}
		}
		return getRef();
	}

	public Map<String, ReportQueryMapper> build() {
		return mapperData;
	}

}
