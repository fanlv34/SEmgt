package com.semgt.base;

import com.semgt.exception.SeException;

public abstract class BaseAction implements IAction {
	private String respField;
	
	public void doTrsPre(Model model) throws SeException {
		model.logData();
	}
	
	public void doTrs(Model model) throws SeException {
		
	}
	
	public void doTrsAft(Model model) throws SeException {
		model.logData();
	}

	public void doTrsFlow(Model model) throws SeException {
		doTrsPre(model);
		doTrs(model);
		doTrsAft(model);
	}

	public String getRespField() {
		return respField;
	}

	public void setRespField(String respField) {
		this.respField = respField;
	}
}
