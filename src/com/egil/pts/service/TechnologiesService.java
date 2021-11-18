package com.egil.pts.service;

import java.util.List;
import java.util.Map;

import com.egil.pts.dao.domain.Technologies;

public interface TechnologiesService {

	public List<Technologies> getTechnologies() throws Throwable;

	public String getJSONStringOfTechnologies() throws Throwable;

	public void getTechnologiesMap(Map<Long, String> technologiessMap)
			throws Throwable;
}
