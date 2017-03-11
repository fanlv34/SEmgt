package com.semgt.validator;

import com.semgt.base.Model;
import com.semgt.exception.SeException;

public interface Validator {
	public void validate(FieldStyleConfig fsc, Model model) throws SeException;
}
