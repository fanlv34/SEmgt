package com.semgt.Init;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.semgt.model.Rule;
import com.semgt.service.IRuleService;

public class InitSemgtData implements InitializingBean {
	
	@Resource(name="ruleService")
	private IRuleService ruleService;
	
	@Resource(name="redisTemplate")
	private RedisTemplate redisTemplate;
	
	private static Logger log = LoggerFactory.getLogger(InitSemgtData.class);
	
	@Transactional
	public void afterPropertiesSet() throws Exception {
		final List<Rule> rulelist = ruleService.qryAll();
		
		redisTemplate.executePipelined(new RedisCallback<Boolean>() {
			public Boolean doInRedis(RedisConnection connection)
					throws DataAccessException {
				RedisSerializer<Rule> ruleSerializer = (RedisSerializer<Rule>) redisTemplate.getValueSerializer();
				RedisSerializer<String> stringSerializer = redisTemplate.getStringSerializer();
				for (Rule rule : rulelist) {
					byte[] ruleType = stringSerializer.serialize(rule.getRuleType());
					byte[] ruleId = stringSerializer.serialize(rule.getRuleId());
					byte[] ruleInstance = ruleSerializer.serialize(rule);
					connection.hSet(ruleType,ruleId, ruleInstance);
				}
				return null;
			}});
	}

}
