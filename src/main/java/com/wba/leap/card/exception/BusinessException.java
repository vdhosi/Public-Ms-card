package com.wba.leap.card.exception;

public class BusinessException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String errorId;
	private String errorSource;
	private String errorMessage;
	private String errorType;
	public BusinessException(String errorInfo) {
		super(errorInfo);
		String[] errorDetails=errorInfo.split(":");
		if(errorDetails!=null) {
			if(errorDetails.length>=2)
				errorMessage=errorDetails[1];
			else
				errorMessage=errorDetails[0];
			if(errorDetails.length==3)
				errorSource=errorDetails[2];
			else
				errorSource="LEAP API";
			errorId=errorDetails[0];
			errorType="B";
		}
	}
	public String getErrorId() {
		return errorId;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	public String getErrorSource() {
		return errorSource;
	}
	public String getErrorType() {
		return errorType;
	}
}
