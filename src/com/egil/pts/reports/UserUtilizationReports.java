package com.egil.pts.reports;

import java.util.List;

import com.egil.pts.modal.UserReport;
import com.egil.pts.util.Specification;

public class UserUtilizationReports implements Specification<UserReport> {

	@Override
	public boolean isValid(List<UserReport> product) {
		return false;
	}

}
