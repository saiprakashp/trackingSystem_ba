package com.egil.pts.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.egil.pts.dao.domain.Technologies;
import com.egil.pts.service.TechnologiesService;
import com.egil.pts.service.common.BaseUIService;

@Service("technologiesService")
public class TechnologiesServiceImpl extends BaseUIService implements
		TechnologiesService {

	@Override
	public List<Technologies> getTechnologies() throws Throwable {
		return daoManager.getTechnologiesDao().getTechnologies();
	}

	@Override
	public String getJSONStringOfTechnologies() throws Throwable {
		String returnValue = "";
		returnValue = -1 + ":" + "Please Select" + ";";
		for (Technologies technology : getTechnologies()) {
			returnValue = returnValue + technology.getId() + ":"
					+ technology.getTechnologyName() + ";";
		}
		return returnValue.substring(0, returnValue.length() - 1);
	}

	@Override
	public void getTechnologiesMap(Map<Long, String> technologiesMap)
			throws Throwable {
		for (Technologies technology : getTechnologies()) {
			technologiesMap.put(technology.getId(),
					technology.getTechnologyName());
		}
	}
}
