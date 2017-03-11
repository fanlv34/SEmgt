package com.semgt.base;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.util.UrlPathHelper;

import com.semgt.exception.SeException;
import com.semgt.util.SeUtil;

public class Model {
	protected Logger log = LoggerFactory.getLogger(com.semgt.base.Model.class);

	private HttpServletRequest request;
	private HttpServletResponse response;
	private Map data;
	private String actionId;
	private UrlPathHelper urlPathHelper;
	private HttpSession session;

	public Model(HttpServletRequest request, HttpServletResponse response,
			Map data) {
		super();
		this.request = request;
		this.response = response;
		this.data = data;
		this.urlPathHelper = new UrlPathHelper();
		String lookupPath = urlPathHelper.getLookupPathForRequest(request);
		this.actionId = resolveActionId(lookupPath, request);
		this.setSession(request.getSession());
	}

	public void logData() throws SeException {
		StringBuffer logModel = new StringBuffer("model data - { ");
		for (Iterator iterator = this.data.entrySet().iterator(); iterator
				.hasNext();) {
			Map.Entry entry = (Map.Entry) iterator.next();
			if (entry.getValue() instanceof List) {
				StringBuffer subLogModel = new StringBuffer();
				List valueList = (List) entry.getValue();
				for (int i = 0; i < valueList.size(); i++) {
					subLogModel.append(constructModelLog(valueList.get(i)));
					if(i!=valueList.size()-1) {
						subLogModel.append(", ");
					}
				}
				logModel.append(entry.getKey() + "=" + subLogModel);
			} else {
				logModel.append(entry.getKey() + "=" + entry.getValue());
			}
			if(iterator.hasNext()) {
				logModel.append(", ");
			}
		}
		logModel.append(" }");
		log.info(logModel.toString());
	}

	private String constructModelLog(Object object) throws SeException {
		StringBuffer modellog = new StringBuffer("{");
		// 获取实体类的所有属性，返回Field数组
		Field[] field = object.getClass().getDeclaredFields();
		// 遍历所有属性
		for (int j = 0; j < field.length; j++) {
			// 获取属性的名字
			String name = field[j].getName();
			modellog.append(name+"=");
			// 将属性的首字符大写，方便构造get，set方法
			name = SeUtil.toUpperCaseFirstOne(name);
			// 获取属性的类型
			String type = field[j].getGenericType().toString();
			try {
				// 如果type是类类型，则前面包含"class "，后面跟类名
				if (type.equals("class java.lang.String")) {
					Method m = object.getClass().getMethod("get" + name);
					// 调用getter方法获取属性值
					String value = (String) m.invoke(object);
					if (value != null) {
						modellog.append(value);
					}
				}
				if (type.equals("class java.lang.Integer")) {
					Method m = object.getClass().getMethod("get" + name);
					Integer value = (Integer) m.invoke(object);
					if (value != null) {
						modellog.append(value);
					}
				}
				if (type.equals("class java.lang.Short")) {
					Method m = object.getClass().getMethod("get" + name);
					Short value = (Short) m.invoke(object);
					if (value != null) {
						modellog.append(value);
					}
				}
				if (type.equals("class java.lang.Double")) {
					Method m = object.getClass().getMethod("get" + name);
					Double value = (Double) m.invoke(object);
					if (value != null) {
						modellog.append(value);
					}
				}
				if (type.equals("class java.lang.Boolean")) {
					Method m = object.getClass().getMethod("get" + name);
					Boolean value = (Boolean) m.invoke(object);
					if (value != null) {
						modellog.append(value);
					}
				}
				if (type.equals("class java.util.Date")) {
					Method m = object.getClass().getMethod("get" + name);
					Date value = (Date) m.invoke(object);
					if (value != null) {
						modellog.append(value.toLocaleString());
					}
				}
				if(j!=field.length-1) {
					modellog.append(",");
				}
			} catch(Exception e) {
				log.error(e.getMessage());
			}
		}
		return modellog.append("}").toString();
	}

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
	
	public void setSessionAttr(String key, Object value) {
		session.setAttribute(key, value);
	}
	
	public Object getSessionAttr(String key) {
		return session.getAttribute(key);
	}

	public String getActionId() {
		return actionId;
	}

	public Object getData(String key) {
		return data.get(key);
	}

	public Map getDataMap() {
		return data;
	}

	public HttpServletRequest getRequest() {
		return request;
	}

	public HttpServletResponse getResponse() {
		return response;
	}

	public void setActionId(String actionId) {
		this.actionId = actionId;
	}

	public void setData(String key, Object value) {
		this.data.put(key, value);
	}

	public void setDataMap(Map data) {
		this.data.putAll(data);
	}

	public HttpSession getSession() {
		return session;
	}

	public void setSession(HttpSession session) {
		this.session = session;
	}

}
