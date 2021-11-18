package com.egil.pts.modal;

import org.hibernate.internal.util.StringHelper;

public class WeekEndingEffort {

	private String weekEffort0;
	private String weekEffort1;
	private String weekEffort2;
	private String weekEffort3;
	private String weekEffort4;
	private String weekEffort5;

	public String getWeekEffort0() {
		return StringHelper.isEmpty(weekEffort0) ? "0.0" : weekEffort0;
	}

	public void setWeekEffort0(String weekEffort0) {
		this.weekEffort0 = weekEffort0;
	}

	public String getWeekEffort1() {
		return StringHelper.isEmpty(weekEffort1) ? "0.0" : weekEffort1;
	}

	public void setWeekEffort1(String weekEffort1) {
		this.weekEffort1 = weekEffort1;
	}

	public String getWeekEffort2() {
		return StringHelper.isEmpty(weekEffort2) ? "0.0" : weekEffort2;
	}

	public void setWeekEffort2(String weekEffort2) {
		this.weekEffort2 = weekEffort2;
	}

	public String getWeekEffort3() {
		return StringHelper.isEmpty(weekEffort3) ? "0.0" : weekEffort3;
	}

	public void setWeekEffort3(String weekEffort3) {
		this.weekEffort3 = weekEffort3;
	}

	public String getWeekEffort4() {
		return StringHelper.isEmpty(weekEffort4) ? "0.0" : weekEffort4;
	}

	public void setWeekEffort4(String weekEffort4) {
		this.weekEffort4 = weekEffort4;
	}

	public String getWeekEffort5() {
		return StringHelper.isEmpty(weekEffort5) ? "0.0" : weekEffort5;
	}

	public void setWeekEffort5(String weekEffort5) {
		this.weekEffort5 = weekEffort5;
	}
}
