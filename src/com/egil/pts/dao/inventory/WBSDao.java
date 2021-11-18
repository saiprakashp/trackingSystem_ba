package com.egil.pts.dao.inventory;

import java.util.List;

import com.egil.pts.dao.common.GenericDao;
import com.egil.pts.dao.domain.WBS;

public interface WBSDao extends GenericDao<WBS, Long>{

	public List<WBS> getWBS();
}
