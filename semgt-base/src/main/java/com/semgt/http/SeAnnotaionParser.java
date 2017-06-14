package com.semgt.http;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.semgt.base.annotation.ReqField;
import com.semgt.base.annotation.ReqFields;
import com.semgt.base.annotation.RespField;
import com.semgt.exception.SeException;
import com.semgt.validator.FieldStyleConfig;

@Component("seAnnotaionParser")
public class SeAnnotaionParser {
	protected Logger log = LoggerFactory.getLogger(this.getClass());
	
	//sample @ReqFiled(name="aaa" options="true" length="5" pattern="^[0-9]+$")
	public List reqAnnoParser(Object o) {
		Map confMap = null;
		List fieldList = null;
		// 取得Annotation
		Class clazz = o.getClass();
		// 先获取外层重复注解
		ReqFields reqFields = (ReqFields) clazz.getAnnotation(ReqFields.class);
		if(null != reqFields) {
			// 再获取重复注解中注解字段
			ReqField[] rfs = ((ReqFields) clazz.getAnnotation(ReqFields.class)).value();
			if(null != rfs) {
				fieldList = new ArrayList();
				for (ReqField reqField : rfs) {
					// 取得Annotation设置的value
					FieldStyleConfig fsc = new FieldStyleConfig();
					fsc.setName(reqField.name());
					fsc.setLength(reqField.length());
					fsc.setOption(reqField.option());
					fsc.setPattern(reqField.pattern());
					fsc.setValidator(reqField.validator());
//					Map fieldParam = new HashMap();
//					fieldParam.put("name", reqField.name());
//					fieldParam.put("length", reqField.length());
//					fieldParam.put("option", reqField.option());
//					fieldParam.put("pattern", reqField.pattern());
//					fieldParam.put("validator", reqField.validator());
					fieldList.add(fsc);
				}
			}
		}
		return fieldList;
	}
	
	public String[] respAnnoParser(Object o) {
		// 根据传进来的实例 取得Class
		Class clazz = o.getClass();
		// 取得Annotation
		RespField rf = (RespField) clazz.getAnnotation(RespField.class);
		// 如果Annotation不为空 说明有注释
		if(rf!=null) {
			String fieldConfigs = rf.value();
			return fieldConfigs.split(",");
		} else {
			return null;
		}
	}
}
