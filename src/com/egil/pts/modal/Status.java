package com.egil.pts.modal;

public enum Status {
	ACTIVE, IN_ACTIVE, HOLD, COMPLETED, DELETED, Backlog, Cancelled, Implemented, Execution, Packaging, Warranty,
	Completed, ImpactAnalysis, EarlyStart;

	public static Status getEnumValueOf(Boolean status) {
		if (!status) {
			return Status.IN_ACTIVE;
		} else {
			return Status.ACTIVE;
		}
	}

	public static Boolean getBooleanValueOf(Status status) {
		switch (status) {
		case ACTIVE:
			return true;
		case IN_ACTIVE:
			return false;
		case COMPLETED:
			return false;
		case Completed:
			return false;
		case Cancelled:
			return false;
		case Implemented:
			return false;
		case EarlyStart:
			return true;
		case Execution:
			return true;
		case Warranty:
			return true;
		default:
			return false;
		}
	}

	public static Status getEnumValueOf(String status) {
		if (status != null && status.equalsIgnoreCase("IN_ACTIVE")) {
			return Status.IN_ACTIVE;
		} else if (status != null && status.equalsIgnoreCase("ACTIVE")) {
			return Status.ACTIVE;
		} else if (status != null && status.equalsIgnoreCase("HOLD")) {
			return Status.HOLD;
		} else if (status != null && status.equalsIgnoreCase("COMPLETED")) {
			return Status.COMPLETED;
		} else if (status != null && status.equalsIgnoreCase("DELETED")) {
			return Status.COMPLETED;
		} else if (status != null && status.equalsIgnoreCase("Backlog")) {
			return Status.Backlog;
		} else if (status != null && status.equalsIgnoreCase("Cancelled")) {
			return Status.Cancelled;
		} else if (status != null && status.equalsIgnoreCase("Implemented")) {
			return Status.Implemented;
		} else if (status != null && status.equalsIgnoreCase("Execution")) {
			return Status.Execution;
		} else if (status != null && status.equalsIgnoreCase("Packaging")) {
			return Status.Packaging;
		} else if (status != null && status.equalsIgnoreCase("Warranty")) {
			return Status.Warranty;
		} else if (status != null && status.equalsIgnoreCase("ImpactAnalysis")) {
			return Status.ImpactAnalysis;
		} else if (status != null && status.equalsIgnoreCase("EarlyStart")) {
			return Status.EarlyStart;
		}

		return ACTIVE;
	}
}
