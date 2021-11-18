package com.egil.pts.modal;

import java.util.LinkedHashMap;
import java.util.Map;

public class WeekEffort {

	private Long categoryId;
	private String category;
	private Long wbsId;
	private String wbs;
	Map<String, Long> utilIdWeekMap = new LinkedHashMap<String, Long>();
	Map<String, Float> weekEffortMap = new LinkedHashMap<String, Float>();

	public Map<String, Float> getWeekEffortMap() {
		return weekEffortMap;
	}

	public void addWeekEffortToMap(String week, Float weekEffort) {
		this.weekEffortMap.put(week, weekEffort);
	}

	public Map<String, Long> getUtilIdWeekMap() {
		return utilIdWeekMap;
	}

	public void addUtilIdWeekToMap(String week, Long utilId) {
		this.utilIdWeekMap.put(week, utilId);
	}

	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public Long getWbsId() {
		return wbsId;
	}

	public void setWbsId(Long wbsId) {
		this.wbsId = wbsId;
	}

	public String getWbs() {
		return wbs;
	}

	public void setWbs(String wbs) {
		this.wbs = wbs;
	}

}
