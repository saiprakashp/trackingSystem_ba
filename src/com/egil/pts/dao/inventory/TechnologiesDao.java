package com.egil.pts.dao.inventory;

import java.util.List;

import com.egil.pts.dao.common.GenericDao;
import com.egil.pts.dao.domain.Technologies;

public interface TechnologiesDao extends GenericDao<Technologies, Long> {

	public List<Technologies> getTechnologies();
}
