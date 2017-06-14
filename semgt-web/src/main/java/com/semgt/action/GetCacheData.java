package com.semgt.action;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.semgt.base.BaseAction;
import com.semgt.base.Model;
import com.semgt.base.annotation.ReqField;
import com.semgt.base.annotation.ReqFields;
import com.semgt.exception.SeException;
import com.semgt.service.RedisUtilService;
import com.semgt.util.SeUtil;

@Component("getCache")
@ReqFields({ @ReqField(name = "cacheName", option = false, length = 20),
		@ReqField(name = "cacheType", option = false, length = 20),
		@ReqField(name = "cachePrefix", option = true, length = 20) })
// @RespField("cacheData")
public class GetCacheData extends BaseAction {

	@Resource(name = "redisUtilService")
	private RedisUtilService redisUtilService;

	@Override
	public void doTrs(Model model) throws SeException {
		// 返回数据
		Object cacheData = null;

		// 判断CacheName存在
		if (SeUtil.isNullOrEmpty(model.getData("cacheName"))) {
			throw new SeException("CacheName is EMPTY!");
		}
		String cacheName = (String) model.getData("cacheName");

		// 判断CacheType存在，存在则根据cacheType调用不同的方法， 不存在则按照默认取Map类型
		if (!SeUtil.isNullOrEmpty(model.getData("cacheType"))) {
			String cacheType = (String) model.getData("cacheType");
			// warning 将来可修改为通过反射调用方法
			// List类型
			if ("list".equalsIgnoreCase(cacheType)) {
				// 判断是否有前缀
				if (!SeUtil.isNullOrEmpty(model.getData("cachePrefix"))) {
					cacheData = redisUtilService.getRuleList(cacheName,
							(String) model.getData("cachePrefix"));
				} else {
					cacheData = redisUtilService.getRuleList(cacheName, null);
				}
			}// warning 目前仅支持list 将来有其他类型再添加
		} else {
			// warning 默认按map取值
		}
		model.setData(cacheName, cacheData);
		super.doTrs(model);
	}

}
