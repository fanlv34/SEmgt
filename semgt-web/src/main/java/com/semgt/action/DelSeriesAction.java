package com.semgt.action;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.semgt.base.BaseSubmitAction;
import com.semgt.base.Model;
import com.semgt.base.annotation.ReqField;
import com.semgt.base.annotation.ReqFields;
import com.semgt.exception.SeException;
import com.semgt.model.User;
import com.semgt.service.ISeriesService;

@Component("delSeries")
@ReqFields({
	@ReqField(name = "seriesId", pattern = "^[0-9]+$")
})
public class DelSeriesAction extends BaseSubmitAction {
	@Resource
	private ISeriesService seriesService;
	
	@Override
	public void doTrs(Model model) throws SeException {
		super.doTrs(model);
		User user = (User) model.getSessionAttr("_User");
		model.setData("userId", user.getUserId());
		seriesService.delSeries(model.getDataMap());
	}
}
