package com.semgt.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import com.semgt.dao.IRuleDao;
import com.semgt.model.Rule;
import com.semgt.model.RuleKey;

@Service("ruleService")
public class RuleServiceImpl implements IRuleService {
	@Resource(name = "ruleDao")
	private IRuleDao ruleDao;
	
	@Resource(name="redisTemplate")
	private StringRedisTemplate redisTemplate;
	
	public List qryAll() {
		return (List) ruleDao.selectAll();
	}

//	public List<Rule> qryRule(RuleKey ruleKey) {
//		return (List<Rule>) ruleDao.selectByPrimaryKey(ruleKey);
//	}
	
}
