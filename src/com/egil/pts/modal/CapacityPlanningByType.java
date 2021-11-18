package com.egil.pts.modal;

import java.io.Serializable;

public class CapacityPlanningByType implements Serializable {

	private static final long serialVersionUID = 1L;

	private String month;
	
	private Long headCount;
	
	private Float projectCapacity;
	
	private Float adhocCapacity;
	
	private Float feasibilityCapacity;
	
	private Float fouthSupportCapacity;
	
	private Float targetCapacity;
	
	private Float totalCapacity;
	
	private Float capacityDiff;

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public Long getHeadCount() {
		return headCount;
	}

	public void setHeadCount(Long headCount) {
		this.headCount = headCount;
	}

	public Float getProjectCapacity() {
		return projectCapacity==null?0.0f:projectCapacity;
	}

	public void setProjectCapacity(Float projectCapacity) {
		this.projectCapacity = projectCapacity;
	}

	public Float getAdhocCapacity() {
		return adhocCapacity==null?0.0f:adhocCapacity;
	}

	public void setAdhocCapacity(Float adhocCapacity) {
		this.adhocCapacity = adhocCapacity;
	}

	public Float getFeasibilityCapacity() {
		return feasibilityCapacity==null?0.0f:feasibilityCapacity;
	}

	public void setFeasibilityCapacity(Float feasibilityCapacity) {
		this.feasibilityCapacity = feasibilityCapacity;
	}

	public Float getFouthSupportCapacity() {
		return fouthSupportCapacity==null?0.0f:fouthSupportCapacity;
	}

	public void setFouthSupportCapacity(Float fouthSupportCapacity) {
		this.fouthSupportCapacity = fouthSupportCapacity;
	}

	public Float getTotalCapacity() {
		totalCapacity =  (fouthSupportCapacity == null ? 0.0f : fouthSupportCapacity)
				+ (feasibilityCapacity == null ? 0.0f : feasibilityCapacity)
				+ (adhocCapacity == null ? 0.0f : adhocCapacity) + (projectCapacity == null ? 0.0f : projectCapacity);
		return totalCapacity;
	}

	public void setTotalCapacity(Float totalCapacity) {
		this.totalCapacity = totalCapacity;
	}

	public Float getCapacityDiff() {
		return capacityDiff;
	}

	public void setCapacityDiff(Float capacityDiff) {
		this.capacityDiff = capacityDiff;
	}

	public Float getTargetCapacity() {
		return targetCapacity;
	}

	public void setTargetCapacity(Float targetCapacity) {
		this.targetCapacity = targetCapacity;
	}
}
