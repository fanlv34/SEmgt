package com.semgt.base;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.semgt.exception.SeException;
import com.semgt.util.SeUtil;

public abstract class PageableBaseAction extends BaseAction{
	
	private IPageableService pageableService;

	@Override
	public void doTrs(Model model) throws SeException {
		int pageSize = (Integer) model.getData("pageSize");
		// 计算起始数据和查询跨度
		if(SeUtil.isNullOrEmpty(model.getData("currentPage")) 
				|| SeUtil.isNullOrEmpty(model.getData("pageSize"))) {
			model.setData("startNum", 0);
			model.setData("step", 10);
		} else {
			int currentPage = (Integer) model.getData("currentPage");
			int startNum = (currentPage - 1) * pageSize;
			model.setData("startNum", startNum);
			model.setData("step", pageSize);
		}
		
		Map pageableData = (Map) doPageableQuery(model, pageableService);
		
		//计算总页数
		int totalCount = (int) pageableData.get("totalCount");
		int totalPage = (totalCount + pageSize - 1) / pageSize;
		pageableData.put("totalPage", totalPage);
		
		model.setData("pageableData", pageableData);
		
		super.doTrs(model);
	}

	public Object doPageableQuery(Model model, IPageableService service) throws SeException {
		Map pageableData = new HashMap();
		List pageableList = (List) service.qryPageableData(model.getDataMap());
		if(pageableList.size()<=0) {
			throw new SeException("No Record!");
		}
		pageableData.put("pageableList", pageableList);
		pageableData.put("totalCount", service.qryPagebleCount(model.getDataMap()));
		return pageableData;
	}
	
	//setters and getters
	public void setPageableService(IPageableService pageableService) {
		this.pageableService = pageableService;
	}
	
}
