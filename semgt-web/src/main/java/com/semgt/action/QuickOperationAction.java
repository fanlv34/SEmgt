package com.semgt.action;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.semgt.base.BaseAction;
import com.semgt.base.Model;
import com.semgt.base.annotation.ReqField;
import com.semgt.base.annotation.ReqFields;
import com.semgt.base.annotation.RespField;
import com.semgt.exception.SeException;
import com.semgt.model.User;
import com.semgt.service.ISeriesService;

@Component("quickOperation")
@ReqFields({
	@ReqField(name = "seriesId", length = 11, pattern = "^[0-9]{1,11}$"),
	@ReqField(name = "target")
})
//@RespField("changeResult")
public class QuickOperationAction extends BaseAction {
	@Resource
	private ISeriesService seriesService;

	@Override
	public void doTrs(Model model) throws SeException {
		User user = (User) model.getSessionAttr("_User");
		Map dataMap = new HashMap();
		dataMap.put("userId", user.getUserId());
		dataMap.put("target", model.getData("target"));
		dataMap.put("seriesId", model.getData("seriesId"));
		dataMap.put("operation", model.getData("operation"));
		seriesService.quickOperation(dataMap);
	}
	
}
