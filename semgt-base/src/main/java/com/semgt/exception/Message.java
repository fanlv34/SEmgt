package com.semgt.exception;

public class Message {
	private String errorCode = null;
	private String errorMessage = null;
	
	public Message() {
		setErrorCode("9999");
		setErrorMessage("unkown exception");
	}

	// setters and getters
	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
}
