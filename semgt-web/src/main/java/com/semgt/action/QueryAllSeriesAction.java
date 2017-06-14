package com.semgt.action;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.semgt.base.BaseAction;
import com.semgt.base.IPageableService;
import com.semgt.base.Model;
import com.semgt.base.PageableBaseAction;
import com.semgt.base.annotation.ReqField;
import com.semgt.base.annotation.ReqFields;
import com.semgt.base.annotation.RespField;
import com.semgt.constant.IConstant;
import com.semgt.exception.SeException;
import com.semgt.model.Rule;
import com.semgt.model.Series;
import com.semgt.model.User;
import com.semgt.service.ISeriesService;
import com.semgt.service.RedisUtilService;
import com.semgt.util.SeUtil;

@Component("queryAllSeries")
@ReqFields({
	@ReqField(name="isEndCondition",length=1, option=true, pattern="^[BSE]{1}$"),
	@ReqField(name="isAbandoned",length=1,option=true, pattern="^[0-3]{1}$")
})
@RespField("pageableData")//返回字段有多个时，逗号分隔
public class QueryAllSeriesAction extends PageableBaseAction {
	@Resource(name="seriesService")
	private IPageableService seriesService;
	
	@Resource(name="redisUtilService")
	private RedisUtilService redisUtilService;
	
	@Override
	public void doTrs(Model model) throws SeException {
		
		User user = (User) model.getSessionAttr("_User");
		model.setData("userId", user.getUserId());
		
		this.setPageableService(seriesService);
		super.doTrs(model);
		List<Series> list = (List) ((Map) model.getData("pageableData")).get("pageableList");
		
		List<Rule> urlList = redisUtilService.getRuleList(IConstant.DOWNLOADURL, "du");
		// 拼接下载地址 及整理日期格式
		for (Series series : list) {
			String comingDate = series.getComingDate();
			if(!SeUtil.isNullOrEmpty(comingDate)) {
				series.setComingDate(SeUtil.dateFormatSpecial(comingDate));
			}
			
			for (Rule rule : urlList) {
				if(rule.getRuleId().equals(series.getUrlType())) {
					series.setDownloadUrl(rule.getRuleDef());
					break;
				}
			}
		}
		
//		model.setData("seList", list);
	}

//	@Override
//	public Object doPageableQuery(Model model, IPageableService service) throws SeException {
//		List<Series> list = ((ISeriesService)service)..qryAllSeriesPageable(model.getDataMap());
//		if(list.size()<=0) {
//			throw new SeException("No Record!");
//		}
//		
//		return list;
//	}
	
	
}
