package com.egil.pts.modal;

public class ErrorStatus {

	private int errorCode;
	private String errorText;
	
	public ErrorStatus(int errorCode, String errorText) {
		this.errorCode = errorCode;
		this.errorText = errorText;
	}
	public ErrorStatus() {
	
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorText(String errorText) {
		this.errorText = errorText;
	}

	public String getErrorText() {
		return errorText;
	}

}
