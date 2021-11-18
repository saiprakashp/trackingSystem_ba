package com.egil.pts.service;

import java.util.List;

import com.egil.pts.dao.domain.WBS;

public interface WBSService {
	
	public List<WBS> getWBS();
	
	public String getJSONStringOfWBS();
}
