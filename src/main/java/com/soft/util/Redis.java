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
	 * 添加Map
	 * @param key
	 * @return
	 */
	public static boolean add(final RedisTemplate<String, Object> redisTemplate, final String key, final Map<String, String> map) {
		return redisTemplate.execute(new RedisCallback<Boolean>() {
			public Boolean doInRedis(RedisConnection connection) {
				BoundHashOperations<String, String, String> ops = redisTemplate.boundHashOps(key);
				
				try{
					ops.putAll(map);
					return true;
				}catch(Exception e){
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
	public static Map<String, String> getMap(final RedisTemplate<String, Object> redisTemplate, final String key, final String... keys) {
		return redisTemplate.execute(new RedisCallback<Map<String, String>>() {
			public Map<String, String> doInRedis(RedisConnection connection) {
				byte[] bkey = redisTemplate.getStringSerializer().serialize(key);
				if (connection.exists(bkey)) {
					byte[][] params = new byte[keys.length][];
					for (int i = 0; i < keys.length; i++)
						params[i] = redisTemplate.getStringSerializer().serialize(keys[i]);

					List<byte[]> value = connection.hMGet(bkey, params);

					Map<String, String> map = new HashMap<String, String>();

					for (int i = 0; i < keys.length; i++)
						map.put(keys[i], redisTemplate.getStringSerializer().deserialize(value.get(i)));
					return map;
				}
				return null;
			}
		});
	}
}