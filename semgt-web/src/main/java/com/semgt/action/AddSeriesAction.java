package com.semgt.action;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.semgt.base.BaseAction;
import com.semgt.base.Model;
import com.semgt.base.annotation.ReqField;
import com.semgt.base.annotation.ReqFields;
import com.semgt.exception.SeException;
import com.semgt.model.User;
import com.semgt.service.ISeriesService;
import com.semgt.util.SeUtil;

@Component("addSeries")
@ReqFields({
		@ReqField(name = "seriesNameCN", length = 20, option = true, pattern = "^[a-zA-Z0-9\u4E00-\u9FA5]+$"),
		@ReqField(name = "seriesNickName", length = 20, option = true, pattern = "^[a-zA-Z0-9\u4E00-\u9FA5]+$"),
		@ReqField(name = "seriesNameOrg", length = 20, option = true, pattern = "^[a-zA-Z0-9\u4E00-\u9FA5\\s]+$"),
		@ReqField(name = "headSeason", length = 2, pattern = "^[0-9]{1,2}$"),
		@ReqField(name = "currentSeason", length = 2, pattern = "^[0-9]{1,2}$"),
		@ReqField(name = "episode", length = 3, pattern = "^[0-9]{1,3}$"),
		@ReqField(name = "updateWeekday", length = 1, pattern = "^[0-7]{1}$"),
		@ReqField(name = "isEnd", length = 1, pattern = "^[BSEN]{1}$"),
		@ReqField(name = "comingDate", option = true, pattern = "^(\\d{4})|(\\d{4}-\\d{2})|(\\d{4}-\\d{2}-\\d{2})$"),
		@ReqField(name = "fuzzyDate", option = true, length = 1, pattern = "^[NCYM]{1}$"),
		@ReqField(name = "isAbandoned", length = 1, pattern = "^[0-3]{1}$"),
		@ReqField(name = "downloadUrl", pattern = "^du[0-9]{3}$"),
		@ReqField(name = "customUrl",option=true, length=2048),
		@ReqField(name = "rating", pattern = "^[0-5]{1}$")
})
public class AddSeriesAction extends BaseAction {
	@Resource
	private ISeriesService seriesService;

	@Override
	public void doTrsPre(Model model) throws SeException {
		// 剧名、别名、原名不能同时为空
		if (SeUtil.isNullOrEmpty(model.getData("seriesNameCN"))
				&& SeUtil.isNullOrEmpty(model.getData("seriesNickName"))
				&& SeUtil.isNullOrEmpty(model.getData("seriesNameOrg"))) {
			throw new SeException("剧名、别名、原名不能同时为空");
		}
		//模糊标识未知时 清空放送时间
		if("N".equals(model.getData("fuzzyDate"))) {
			model.setData("comingDate", null);
		} else {
			if(SeUtil.isNullOrEmpty(model.getData("comingDate"))) {
				throw new SeException("放送时间不能为空");
			}
		}
		super.doTrsPre(model);
	}

	@Override
	public void doTrs(Model model) throws SeException {
		super.doTrs(model);
		// 格式化comingDate格式
		if(!SeUtil.isNullOrEmpty(model.getData("comingDate"))) {
			model.setData("comingDate", SeUtil.dateFormatSpecial((String) model.getData("comingDate")));
		}
		User user = (User) model.getSessionAttr("_User");
		model.setData("userId", user.getUserId());
		seriesService.addSeries(model.getDataMap());
	}
}
