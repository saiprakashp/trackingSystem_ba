package com.egil.pts.dao.inventory;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface PtsQueryUIDao {

	public List<Map<String, Object>> getQueryResultData(String query) throws Throwable;

	public List<Map<String, Object>> getQueryResultData(String query, String project, Date weekStarting,
			Date weekEndingString, String release, String type, String reportType);

}
