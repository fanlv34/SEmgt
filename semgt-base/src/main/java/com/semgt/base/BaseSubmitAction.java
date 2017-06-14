package com.semgt.base;

import javax.annotation.Resource;

import com.semgt.exception.SeException;

public class BaseSubmitAction extends BaseAction {
	
	@Resource(name="tokenManager")
	private TokenManager tokenManager;
	
	public void tokenCheck(Model model) throws SeException {
		tokenManager.verifyToken(model);
	}
	
	@Override
	public void doTrsFlow(Model model) throws SeException {
		tokenCheck(model);
		doTrsPre(model);
		doTrs(model);
		doTrsAft(model);
	}
}
