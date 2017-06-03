package com.semgt.base;

public class Token {
	private String tokenName;
	private long createTime;

	public Token(String tokenName) {
		super();
		this.tokenName = tokenName;
		this.createTime = System.currentTimeMillis();
	}

	public String getTokenName() {
		return tokenName;
	}

	public long getCreateTime() {
		return createTime;
	}
}
