package com.soft.util;

import java.util.*;

import org.springframework.data.redis.connection.*;
import org.springframework.data.redis.core.*;

public class Redis {

	/**
	 * 验证ID是否存在
	 * @param auth
	 * @return
	 */
	public static boolean checkAuth(final RedisTemplate<String, Object> redis, final String auth) {
		return redis.execute(new RedisCallback<Boolean>() {
			public Boolean doInRedis(RedisConnection connection) {
				byte[] key = redis.getStringSerializer().serialize(auth);
				if (connection.exists(key))
					return true;
				return false;
			}
		});
	}

	/**
	 * 添加Map
	 * @param auth
	 * @return
	 */
	public static boolean add(final RedisTemplate<String, Object> redis, final String auth, final Map<String, String> map) {
		return redis.execute(new RedisCallback<Boolean>() {
			public Boolean doInRedis(RedisConnection connection) {
				BoundHashOperations<String, String, String> ops = redis.boundHashOps(auth);
				
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
	 * @param auth
	 * @return
	 */
	public static Map<String, String> getMap(final RedisTemplate<String, Object> redis, final String auth, final String... keys) {
		return redis.execute(new RedisCallback<Map<String, String>>() {
			public Map<String, String> doInRedis(RedisConnection connection) {
				byte[] bkey = redis.getStringSerializer().serialize(auth);
				if (connection.exists(bkey)) {
					byte[][] params = new byte[keys.length][];
					for (int i = 0; i < keys.length; i++)
						params[i] = redis.getStringSerializer().serialize(keys[i]);

					List<byte[]> value = connection.hMGet(bkey, params);

					Map<String, String> map = new HashMap<String, String>();

					for (int i = 0; i < keys.length; i++)
						map.put(keys[i], redis.getStringSerializer().deserialize(value.get(i)));
					return map;
				}
				return null;
			}
		});
	}
}