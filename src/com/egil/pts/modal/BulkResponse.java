package com.egil.pts.modal;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author Deepak Kovela
 *
 */
public class BulkResponse {
	
	private String filePath;


	private String status;
	
	private String errorCode;
	
	private String errorMessage;
	
	private int recordCount;
	
	
	private Date createdDate;
	

	private String createdBy;

	private String requestType;

	private Map<Integer, List<String>> errorCodesMap;
	
	
	public Map<Integer, List<String>> getErrorCodesMap() {
		return errorCodesMap;
	}

	public void setErrorCodesMap(Map<Integer, List<String>> errorCodesMap) {
		this.errorCodesMap = errorCodesMap;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}


	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public int getRecordCount() {
		return recordCount;
	}

	public void setRecordCount(int recordCount) {
		this.recordCount = recordCount;
	}

	
	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	

	public String toString() {
		StringBuffer sb = new StringBuffer(128);
		
		sb.append("status = ");
		sb.append(status);
		sb.append(", ");
		
		sb.append("errorMessage = ");
		sb.append(errorMessage);
		sb.append(", ");
		
		sb.append("filePath = ");
		sb.append(filePath);
		sb.append(", ");
		
		
		sb.append("recordCount = ");
		sb.append(recordCount);
		sb.append(", ");
		
		
		return sb.toString();
	}


	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getRequestType() {
		return requestType;
	}

	public void setRequestType(String requestType) {
		this.requestType = requestType;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	
	
	
}
