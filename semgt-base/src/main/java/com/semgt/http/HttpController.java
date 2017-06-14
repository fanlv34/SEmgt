package com.semgt.http;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import com.semgt.base.IAction;
import com.semgt.base.Model;
import com.semgt.exception.DefaultHandlerExceptionResolver;
import com.semgt.exception.SeException;
import com.semgt.util.SeUtil;
import com.semgt.validator.FieldStyleConfig;
import com.semgt.validator.Validator;

@Controller("httpController")
public class HttpController implements ApplicationContextAware {
	@Resource(name="jsonRequestResolver")
	private RequestResolver requestResolver;
	@Resource(name="seAnnotaionParser")
	private SeAnnotaionParser parser;
	protected ApplicationContext applicationContext;

	@ResponseBody
	@RequestMapping(value = "/*.do")
	public Map handle(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Map data = new HashMap();
		try {
			// 解析json请求数据
			if (requestResolver.accept(request)) {
				data = requestResolver.resolve(request, response);
			}
			
			// 请求数据放入model中
			Model model = new Model(request, response, data);
			
			// 根据.do的名字获取对应的action
			Object o = applicationContext.getBean(model.getActionId());
			// 如果action是自定义类型的action则进行处理
			if (o instanceof IAction) {
				IAction action = ((IAction) o);
				// 解析Annotation配置的上送字段
				List fieldList = parser.reqAnnoParser(action);
				if(null!=fieldList && fieldList.size()>0) {
					// 循环校验fieldList中的各个字段
					for (int i = 0; i < fieldList.size(); i++) {
						// 根据Annotation配置的validator取bean
						String valid = "default";
						String validConf = ((FieldStyleConfig)fieldList.get(i)).getValidator();
						if(!SeUtil.isNullOrEmpty(validConf)) {
							valid = validConf;
						}
						Validator v = (Validator) applicationContext.getBean(valid+"Validator");
						v.validate((FieldStyleConfig)fieldList.get(i), model);
					}
				}
				
				action.doTrsFlow(model);
				// 根据注解中配置的value 取得输出字段
				String[] backFields = parser.respAnnoParser(action);
				if(backFields!=null && backFields.length > 0) {
					Map backData = new HashMap();
					// 根据配置的返回字段名，循环找出model.data里的返回数据，并重新覆盖model.data
					for (String field : backFields) {
						for (Iterator iterator = model.getDataMap().entrySet().iterator(); iterator
								.hasNext();) {
							Map.Entry entry = (Map.Entry) iterator.next();
							if(field.equals((String) entry.getKey())) {
								backData.put((String) entry.getKey(), entry.getValue());
								break;
							}
						}
					}
					return backData;
				} else {
					return model.getDataMap();
				}
			} else { // action不是自定义类型的action则报错
				throw new SeException("action type error!");
			}
		} catch (Exception e) {
			HandlerExceptionResolver handlerExceptionResolver = new DefaultHandlerExceptionResolver();
			ModelAndView mv = handlerExceptionResolver.resolveException(request, response, null, e);
			e.printStackTrace();
			data.clear();
			data.put("_errorCode", mv.getModel().get("_errorCode"));
			data.put("_errorMessage", mv.getModel().get("_errorMessage"));
			return data;
		} 
	}

	// 解析action名字
	protected String resolveActionId(String path, HttpServletRequest request) {
		String actionId = request.getParameter("actionId");
		if (actionId == null) {
			int s = 1;
			int l1 = path.indexOf('/', s);
			int l2 = path.lastIndexOf('.');
			int l = -1;
			if (l1 != -1 && l2 != -1)
				l = l1 <= l2 ? l1 : l2;
			else if (l1 != -1)
				l = l1;
			else if (l2 != -1)
				l = l2;
			if (l == -1)
				l = path.length();
			actionId = path.substring(s, l);
		}
		return actionId;
	}

	public void setRequestResolver(RequestResolver requestResolver) {
		this.requestResolver = requestResolver;
	}

	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		this.applicationContext = applicationContext;
	}

	public void setParser(SeAnnotaionParser parser) {
		this.parser = parser;
	}
}
