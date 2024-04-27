package com.mimi.core.common.superpackage.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import java.io.Serializable;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Component
public class RedisUtil{

	private static final String RELEASE_LOCK_SCRIPT = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";


	@SuppressWarnings("rawtypes")
	@Autowired
	private RedisTemplate redisTemplate;
	
	/**
	* 批量删除对应的value
	*
	* @param keys
	*/
	public void remove(final String... keys) {
		for (String key : keys) {
			remove(key);
		}
	}
	
	/**
	* 批量删除key
	*
	* @param pattern
	*/
	@SuppressWarnings("unchecked")
	public void removePattern(final String pattern) {
		Set<String> keys = redisTemplate.keys(pattern);
		if (keys.size() > 0){
			redisTemplate.delete(keys);
		}
	}
	
	@SuppressWarnings("unchecked")
	public Set<String> pattern(final String pattern) {
		Set<String> keys = redisTemplate.keys(pattern);
		return keys;
	}
	
	/**
	* 删除对应的value
	*
	* @param key
	*/
	@SuppressWarnings("unchecked")
	public void remove(final String key) {
		if (exists(key)) {
			redisTemplate.delete(key);
		}
	}
	
	/**
	* 判断缓存中是否有对应的value
	*
	* @param key
	* @return
	*/
	@SuppressWarnings("unchecked")
	public boolean exists(final String key) {
		return redisTemplate.hasKey(key);
	}
	
	/**
	* 读取缓存
	*
	* @param key
	* @return
	*/
	@SuppressWarnings("unchecked")
	public Object get(final String key) {
		Object result = null;
		ValueOperations<String, Object> operations = redisTemplate.opsForValue();
		result = operations.get(key);
		return result;
	}
	
	/**
	* 写入缓存
	*
	* @param key
	* @param value
	* @return
	*/
	@SuppressWarnings("unchecked")
	public boolean set(final String key, Object value) {
		boolean result = false;
		try {
			ValueOperations<String, Object> operations = redisTemplate.opsForValue();
			operations.set(key, value);
			result = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	* 写入缓存
	*
	* @param key
	* @param value
	* @return
	*/
	@SuppressWarnings("unchecked")
	public boolean set(final String key, Object value, Long expireTime) {
		boolean result = false;
		try {
			ValueOperations<String, Object> operations = redisTemplate.opsForValue();
			operations.set(key, value);
			redisTemplate.expire(key, expireTime, TimeUnit.SECONDS);
			result = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public void setHash(String key, Map<String, Object> map) {
		HashOperations<String, String, Object> vo = redisTemplate.opsForHash();
		vo.putAll(key, map);
	}

	public void setHashValue(String key, String field, Object value) {
		HashOperations<String, String, Object> vo = redisTemplate.opsForHash();
		vo.put(key, field, value);
	}

	public Map<String, Object> getHash(String key) {
		HashOperations<String, String, Object> vo = redisTemplate.opsForHash();
		return vo.entries(key);
	}

	public Object getHashValue(String key, String field) {
		HashOperations<String, String, Object> vo = redisTemplate.opsForHash();
		return vo.get(key, field);
	}
	
	public boolean existsHashField(String key, String field) {
		HashOperations<String, String, Object> vo = redisTemplate.opsForHash();
		return vo.hasKey(key, field);
	}
	
	public void setSetValue(String key,Object... o) {
		SetOperations so = redisTemplate.opsForSet();
		so.add(key, o);
	}
	
	public Set<Object> getSetValue(String key) {
		SetOperations so = redisTemplate.opsForSet();
		return so.members(key);
	}

	public void expire(String key,long expireTime){
		redisTemplate.expire(key,expireTime,TimeUnit.SECONDS);
	}

	public boolean setLock(String key,String clientId, long expire) {
		try {
			RedisCallback<Boolean> callback = (connection) -> {
				Jedis jedis = (Jedis) connection.getNativeConnection();
				String result = jedis.set(key, clientId, "nx", "ex", expire);
				if ("OK".equals(result)) {
					return Boolean.TRUE;
				}
				String rs = jedis.get(key);
				if(rs!=null&&rs.equals(clientId)) {
					jedis.expire(key, (int) expire);
					return Boolean.TRUE;
				}
				return Boolean.FALSE;
			};
			return (boolean) redisTemplate.execute(callback);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean releaseLock(String key,String clientId) {
		return (boolean) redisTemplate.execute((RedisCallback<Boolean>) redisConnection -> {
			Jedis jedis = (Jedis) redisConnection.getNativeConnection();
			Object result = jedis.eval(RELEASE_LOCK_SCRIPT, Collections.singletonList(key),
					Collections.singletonList(clientId));
			if(result instanceof Long) {
				long r = ((Long)result).longValue();
				return r==1;
			} else if(result instanceof Integer) {
				int r = ((Integer)result).intValue();
				return r==1;
			}else if ("OK".equals(result)) {
				return Boolean.TRUE;
			}
			return Boolean.FALSE;
		});
	}

}