package com.soft.util;

import java.util.*;

import org.springframework.data.redis.connection.*;
import org.springframework.data.redis.core.*;

public class Redis {

	/**
	 * 验证key是否存在
	 * @param key
	 * @return
	 */
	public static boolean checkAuth(final RedisTemplate<String, Object> redisTemplate, final String key) {
		return redisTemplate.execute(new RedisCallback<Boolean>() {
			public Boolean doInRedis(RedisConnection connection) {
				byte[] bkey = redisTemplate.getStringSerializer().serialize(key);
				if (connection.exists(bkey))
					return true;
				return false;
			}
		});
	}

	/**
	 * 保存Map
	 * @param key
	 * @return
	 */
	public static boolean save(final RedisTemplate<String, Object> redisTemplate, final String key, final Map<String, String> map) {
		return redisTemplate.execute(new RedisCallback<Boolean>() {
			public Boolean doInRedis(RedisConnection connection) {
				BoundHashOperations<String, String, String> ops = redisTemplate.boundHashOps(key);

				try {
					ops.putAll(map);
					delay(redisTemplate, key);
					return true;
				} catch (Exception e) {
					e.printStackTrace();
					return false;
				}
			}
		});
	}

	/**
	 * 获取Map
	 * @param key
	 * @return
	 */
	public static Map<String, String> getMap(final RedisTemplate<String, Object> redisTemplate, final String key) {
		return redisTemplate.execute(new RedisCallback<Map<String, String>>() {
			public Map<String, String> doInRedis(RedisConnection connection) {
				BoundHashOperations<String, String, String> ops = redisTemplate.boundHashOps(key);

				return ops.entries();
			}
		});
	}

	/**
	 * 删除元素
	 * @param key
	 * @return
	 */
	public static boolean delete(final RedisTemplate<String, Object> redisTemplate, final String key) {
		return redisTemplate.execute(new RedisCallback<Boolean>() {
			public Boolean doInRedis(RedisConnection connection) {
				try {
					connection.del(redisTemplate.getStringSerializer().serialize(key));
					return true;
				} catch (Exception e) {
					return false;
				}
			}
		});
	}
	
	/**
	 * 续期凭证
	 * @param key
	 * @return
	 */
	public static boolean delay(final RedisTemplate<String, Object> redisTemplate, final String key) {
		return redisTemplate.execute(new RedisCallback<Boolean>() {
			public Boolean doInRedis(RedisConnection connection) {
				BoundHashOperations<String, String, String> ops = redisTemplate.boundHashOps(key);

				try {
					ops.expireAt(Time.d().dd(+90).val());
					return true;
				} catch (Exception e) {
					return false;
				}
			}
		});
	}
}