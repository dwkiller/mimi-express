package com.mimi.core.common.superpackage.redis;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface CacheManager {
	
	public static final String CURRENT_USERKEY="currentUserKey";
	
	public Object getValue(String key);
	public void setValue(String key,Object value);
	public void setValue(String key,Object value,long expire);
	public void remove(String key);
	public boolean exists(String key);
	public void removePattern(String pattern);
	public List<String> pattern(String keys,List<String> filterSuffix);
	
	public void setHash(String key,Map<String,Object> map);
	//public void setHashValue(String key,String field,Object value);
	public void setHashValue(String key,String field,Object value,ISetHashValueAfter after);
	public Map<String,Object> getHash(String key);
	public Object getHashValue(String key,String field);
	
	public boolean existsHashField(String key,String field);
	
	public void setSetValue(String key,Object... o);
	
	public Set<Object> getSetValue(String key);

	public void expire(String key,long expireTime);
	
}
