package com.egil.pts.dao.inventory;

import java.util.List;

import com.egil.pts.dao.common.GenericDao;
import com.egil.pts.dao.domain.Location;

public interface LocationDao extends GenericDao<Location, Long>{

	public List<Location> getLocations();
}
