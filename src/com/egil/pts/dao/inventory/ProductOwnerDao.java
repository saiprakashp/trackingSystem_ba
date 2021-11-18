package com.egil.pts.dao.inventory;

import java.util.List;

import com.egil.pts.dao.common.GenericDao;
import com.egil.pts.dao.domain.ProductOwner;

public interface ProductOwnerDao extends GenericDao<ProductOwner, Long>{

	public List<ProductOwner> getProductOwners();
}
