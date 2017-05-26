package com.semgt.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Component;

import com.semgt.base.BaseService;
import com.semgt.exception.SeDataAccessException;
import com.semgt.exception.SeException;
import com.semgt.model.Rule;
import com.semgt.util.SeUtil;

@Component("redisUtilService")
public class RedisUtilService extends BaseService {

	public List getRuleList(String ruleType, String ruleIdPrefix)
			throws SeException {
		final String fRuleType = ruleType;
		// 如果不传ruleIdPrefix 则根据ruleType自动解析prefix
		if (SeUtil.isNullOrEmpty(ruleIdPrefix)) {
			ruleIdPrefix = autoPrefix(ruleType);
		}
		final String fRuleIdPrefix = ruleIdPrefix;
		final RedisTemplate fRedisRemplate = this.getRedisTemplate();
		return (List) fRedisRemplate.execute(new RedisCallback<List>() {
			public List doInRedis(RedisConnection connection)
					throws DataAccessException {
				RedisSerializer<Rule> ruleSerializer = (RedisSerializer<Rule>) fRedisRemplate
						.getValueSerializer();
				RedisSerializer<String> stringSerializer = fRedisRemplate
						.getStringSerializer();
				List<Rule> ruleList = new ArrayList();

				// 循环list化downloadUrl数据
				for (int i = 0; i < connection.hLen(stringSerializer
						.serialize(fRuleType)); i++) {
					byte[] ruleInstance = connection.hGet(
							stringSerializer.serialize(fRuleType),
							stringSerializer.serialize(fRuleIdPrefix
									+ SeUtil.zeroize("" + i, 3, true)));
					if (ruleInstance == null) {
						throw new SeDataAccessException(
								"Can NOT find ruleInstance. T_T");
					}
					ruleList.add(ruleSerializer.deserialize(ruleInstance));
				}
				return ruleList;
			}
		});
	}

	// 自动计算prefix，规则：ruleType首字符加大写字母 转小写
	private String autoPrefix(String ruleType) throws SeException {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < ruleType.length(); i++) {
			char ch = ruleType.charAt(i);
			if (0 == i) {
				// 首字母小写
				sb.append(ch);
			} else if (Character.isUpperCase(ch)) {
				// 大写字母变小写后追加
				sb.append(ch);
			}
		}
		return sb.toString().toLowerCase();
	}
}
