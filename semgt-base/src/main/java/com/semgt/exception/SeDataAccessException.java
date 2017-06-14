package com.semgt.exception;

import org.springframework.dao.DataAccessException;

public class SeDataAccessException extends DataAccessException {
	private Message message;

	public SeDataAccessException(String msg) {
		super(msg);
		message.setErrorMessage(msg);
	}
	
	public String getErrorMessage() {
		return message.getErrorMessage();
	}

}
