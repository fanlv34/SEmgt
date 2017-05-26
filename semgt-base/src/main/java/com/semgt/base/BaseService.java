package com.semgt.base;

import javax.annotation.Resource;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.transaction.support.TransactionTemplate;

public class BaseService {
	@Resource(name="transactionTemplate")
	private TransactionTemplate transactionTemplate;
	
	@Resource(name = "redisTemplate")
	private RedisTemplate redisTemplate;
	
	public TransactionTemplate getTransactionTemplate() {
		return transactionTemplate;
	}

	public RedisTemplate getRedisTemplate() {
		return redisTemplate;
	}
}
