package com.egil.pts.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.egil.pts.dao.domain.WBS;
import com.egil.pts.service.WBSService;
import com.egil.pts.service.common.BaseUIService;

@Service("wbsService")
public class WBSServiceImpl extends BaseUIService implements WBSService {

	@Override
	public List<WBS> getWBS() {
		return daoManager.getWbsDao().getWBS();
	}

	@Override
	public String getJSONStringOfWBS() {
		String returnValue = "";
		returnValue = -1 + ":" + "Please Select" + ";";
		for (WBS wbs : getWBS()) {
			returnValue = returnValue + wbs.getId() + ":" + wbs.getWbs()
					+ ";";
		}
		return returnValue.substring(0, returnValue.length() - 1);
	}

}
