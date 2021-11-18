package com.egil.pts.modal;

import java.util.ArrayList;
import java.util.List;

public class TimeSheetRecord {
	private List<TimesheetActivity> activityListToSave = new ArrayList<TimesheetActivity>();
	private List<TimesheetActivity> activityListProductsToSave = new ArrayList<TimesheetActivity>();

	public List<TimesheetActivity> getActivityListProductsToSave() {
		return activityListProductsToSave;
	}

	public void setActivityListProductsToSave(List<TimesheetActivity> activityListProductsToSave) {
		this.activityListProductsToSave = activityListProductsToSave;
	}

	public List<TimesheetActivity> getActivityListToSave() {
		return activityListToSave;
	}

	public void setActivityListToSave(List<TimesheetActivity> activityListToSave) {
		this.activityListToSave = activityListToSave;
	}
}
