package com.semgt.exception;

public class SeException extends Exception {
	private Message message;

	public SeException() {
		message = new Message();
	}
	
	public SeException(String errorMessage) {
		message = new Message();
		message.setErrorMessage(errorMessage);
	}
	
	public SeException(String errorCode, String errorMessage) {
		message.setErrorCode(errorCode);
		message.setErrorMessage(errorMessage);
	}
	
	public String getErrorCode() {
		return message.getErrorCode();
	}
	
	public String getErrorMessage() {
		return message.getErrorMessage();
	}
}
