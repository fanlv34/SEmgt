package com.semgt.action;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.semgt.base.BaseAction;
import com.semgt.base.Model;
import com.semgt.base.TokenManager;
import com.semgt.base.annotation.RespField;
import com.semgt.exception.SeException;

@Component("getNewToken")
@RespField("_tokenName")
public class getNewToken extends BaseAction {
	@Resource(name="tokenManager")
	private TokenManager tm;

	@Override
	public void doTrsPre(Model model) throws SeException {
		String tokenName = tm.createToken(model);
		model.setData("_tokenName", tokenName);
	}
	
}
