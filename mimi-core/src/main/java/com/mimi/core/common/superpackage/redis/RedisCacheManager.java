package com.mimi.core.common.superpackage.redis;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Component
public class RedisCacheManager implements CacheManager {
	
	@Autowired
    private RedisUtil redisUtil;

	
	@Override
	public Object getValue(String key) {
		return redisUtil.get(key);
	}

	@Override
	public void setValue(String key,Object value,long expire) {
		redisUtil.set(key, value, expire);
	}

	@Override
	public void setValue(String key, Object value) {
		redisUtil.set(key, value);
	}

	@Override
	public void remove(String key) {
		if(exists(key)){
			redisUtil.remove(key);
		}
	}

	@Override
	public boolean exists(String key) {
		return redisUtil.exists(key);
	}

	@Override
	public void removePattern(String pattern) {
		redisUtil.removePattern(pattern);
	}

	@Override
	public List<String> pattern(String keys, List<String> filterSuffix) {
		Set<String> ls =redisUtil.pattern(keys);
		List<String> result = new ArrayList<String>();
		
		for(String key:ls) {
			boolean hasAdd=true;
			if(filterSuffix!=null) {
				for(String fs:filterSuffix) {
					if(key.endsWith(fs)) {
						hasAdd=false;
						break;
					}
				}
			}
			if(hasAdd) {
				result.add(key);
			}
		}
		ls.clear();
		return result;
	}

	@Override
	public void setHash(String key, Map<String, Object> map) {
		redisUtil.remove(key);
		redisUtil.setHash(key, map);
	}

	//@Override
	public void setHashValue(String key, String field, Object value) {
		redisUtil.setHashValue(key, field, value);
	}

	@Override
	public Map<String, Object> getHash(String key) {
		return redisUtil.getHash(key);
	}

	@Override
	public Object getHashValue(String key, String field) {
		return redisUtil.getHashValue(key, field);
	}

	@Override
	public boolean existsHashField(String key, String field) {
		return redisUtil.existsHashField(key, field);
	}

	@Override
	public void setHashValue(String key, String field, Object value, ISetHashValueAfter after) {
		setHashValue(key,field,value);
		if(after!=null) {
			after.execute(key, field, value);
		}
	}

	@Override
	public void setSetValue(String key, Object... o) {
		redisUtil.setSetValue(key, o);
	}

	@Override
	public Set<Object> getSetValue(String key) {
		return redisUtil.getSetValue(key);
	}
}
