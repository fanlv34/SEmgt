package com.semgt.model;

import java.io.Serializable;

public class Rule extends RuleKey implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4556536276216304956L;

	private String ruleDef;

	private String ruleScript;

	public String getRuleDef() {
		return ruleDef;
	}

	public void setRuleDef(String ruleDef) {
		this.ruleDef = ruleDef == null ? null : ruleDef.trim();
	}

	public String getRuleScript() {
		return ruleScript;
	}

	public void setRuleScript(String ruleScript) {
		this.ruleScript = ruleScript == null ? null : ruleScript.trim();
	}
}