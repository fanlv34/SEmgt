package com.semgt.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Component;

import com.semgt.base.Model;
import com.semgt.exception.SeException;
import com.semgt.util.SeUtil;

@Component
public class DefaultValidator implements Validator {

	public void validate(FieldStyleConfig fsc, Model model) throws SeException {
		//根据name取得model中的数据
		if(!SeUtil.isNullOrEmpty(fsc.getName())) {
			Object fieldValue = model.getData(fsc.getName());
			
			// 1.校验可选项
			if(!fsc.isOption() && SeUtil.isNullOrEmpty(fieldValue)) {// 必输 且为空 报错
				throw new SeException(fsc.getName() + " is not option, but it is null or empty!");
			} else if(fsc.isOption() && SeUtil.isNullOrEmpty(fieldValue)){// 可选 但为空 不需要进行检查
				return;
			}// 不为空 不论是否可选都需要检查
			
			// 2.校验长度
			if(fsc.getLength()>0) {
				int configLength = fsc.getLength();
				int fieldlength = fieldValue.toString().trim().length();
				if(fieldlength > configLength) {
					throw new SeException("WTF! The length of " + fsc.getName() +" is over the limit!");
				}
			}
			
			// 3.校验pattern
			if(!SeUtil.isNullOrEmpty(fsc.getPattern())) {
				Pattern p = Pattern.compile(fsc.getPattern());
				Matcher m = p.matcher(model.getData(fsc.getName()).toString());
				if(!m.matches()) {
					throw new SeException("Oh No~~! The field "+ fsc.getName() +" is NOT matche the pattern!");
				}
			}
			
			// tirm
			model.setData(fsc.getName(), model.getData(fsc.getName()).toString().trim());
		} else {
			throw new SeException("The ReqField Annotation of " + model.getActionId() + "Action do NOT contains the name element!");
		}
		
	}

}
