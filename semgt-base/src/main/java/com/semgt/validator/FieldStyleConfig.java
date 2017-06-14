package com.semgt.validator;

public class FieldStyleConfig {
	private String name;
	private int length;
	private boolean option;
	private String pattern;
	private String validator;

	public int getLength() {
		return length;
	}

	public String getName() {
		return name;
	}

	public String getPattern() {
		return pattern;
	}

	public String getValidator() {
		return validator;
	}

	public boolean isOption() {
		return option;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setOption(boolean option) {
		this.option = option;
	}

	public void setPattern(String pattern) {
		this.pattern = pattern;
	}

	public void setValidator(String validator) {
		this.validator = validator;
	}

}
