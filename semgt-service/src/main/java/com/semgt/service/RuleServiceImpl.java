package com.semgt.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.semgt.base.BaseService;
import com.semgt.dao.IRuleDao;

@Service("ruleService")
public class RuleServiceImpl extends BaseService implements IRuleService {
	@Resource(name = "ruleDao")
	private IRuleDao ruleDao;
	
	public List qryAll() {
		return (List) ruleDao.selectAll();
	}
}
