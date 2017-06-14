package com.semgt.base;

import org.springframework.stereotype.Component;

import com.semgt.exception.SeException;
import com.semgt.util.SeUtil;

@Component("tokenManager")
public class TokenManager {
	private static volatile TokenManager tokenManager;
	private final int TOKENNAME_LENGTH = 8;
	private final String TOKEN_LIST_NAME = "_tokenListName";
	private final String chars = "abcdefghijklmnopqrstuvwxyz0123456789";

	private TokenManager() {
	}

	// 单例tokenManager
	private static TokenManager getInstance() {
		if (null == tokenManager) {
			synchronized (TokenManager.class) {
				tokenManager = new TokenManager();
			}
		}
		return tokenManager;
	}

	// 创建token 8位tokenName
	public String createToken(Model model) {
		TokenList tokenList = null;
		if(SeUtil.isNullOrEmpty(model.getSessionAttr(TOKEN_LIST_NAME))) {
			tokenList = new TokenList();
		} else {
			tokenList = (TokenList) model.getSessionAttr(TOKEN_LIST_NAME);
		}
		
		String tokenName = SeUtil.randStr(TOKENNAME_LENGTH, chars);
		Token token = new Token(tokenName);
		
		tokenList.addToken(token);
		model.setSessionAttr(TOKEN_LIST_NAME, tokenList);
		return tokenName;
	}

	public boolean verifyToken(Model model) throws SeException {
		// 判断是否上送了tokenName
		String tokenName = (String) model.getData("_tokenName");
		if(SeUtil.isNullOrEmpty(tokenName)) {
			throw new SeException("tokenName is empty!");
		}
		TokenList tokenList = (TokenList) model.getSessionAttr(TOKEN_LIST_NAME);
		// 取token
		Token token = tokenList.getToken(tokenName);
		if(null == token) {// token为空时 说明已超时或不存在
			throw new SeException("Token was abandoned or was not exist!");
		} else {
			model.setSessionAttr(TOKEN_LIST_NAME, tokenList);
			return true;
		}
	}
	
}
