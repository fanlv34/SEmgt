package com.semgt.base;

import java.util.LinkedList;
import java.util.List;

public class TokenList {
	private LinkedList<Token> tokenList;
	private int maxListLength;
	private int delayTime = 60000;
	
	public TokenList() {
		this.maxListLength = 10;
		this.tokenList = new LinkedList<Token>();
	}
	
	public void addToken(Token token) {
		synchronized (tokenList) {
			tokenList.addLast(token);
			if(tokenList.size() > maxListLength) {
				tokenList.removeFirst();
			}
		}
	}
	
	public Token getToken(String tokenName) {
		int i = 0;
		long currentTime = System.currentTimeMillis();
		while (i < tokenList.size()) {
			Token token = tokenList.get(i);
			// 判断token是否超时
			if(currentTime - token.getCreateTime() > delayTime) {
				tokenList.remove(i);
				continue;
			}
			// 判断是否为需要取出的token
			if(token.getTokenName().equals(tokenName)) {
				return token;
			}
			i++;
		}
		return null;
	}
}
