package com.semgt.model;

import java.io.Serializable;

public class RuleKey implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3937054215805799510L;

	private String ruleType;

	private String ruleId;

	private String userType;

	public String getRuleType() {
		return ruleType;
	}

	public void setRuleType(String ruleType) {
		this.ruleType = ruleType == null ? null : ruleType.trim();
	}

	public String getRuleId() {
		return ruleId;
	}

	public void setRuleId(String ruleId) {
		this.ruleId = ruleId == null ? null : ruleId.trim();
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType == null ? null : userType.trim();
	}
}