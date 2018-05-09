package com.lion.redis.dao;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
public abstract class BaseRedisDao {

	protected abstract RedisTemplate<Serializable, Serializable> getRedisTemplate();
	
	final protected <K extends Serializable,V extends Serializable> void set(final K key,final V value){
		getRedisTemplate().opsForValue().set(key, value);
	}
	
	@SuppressWarnings("unchecked")
	final protected <K extends Serializable,V extends Serializable> V get(final K key){
		return (V) getRedisTemplate().opsForValue().get(key);
	}
	final protected <K extends Serializable> Set<Serializable> keys(final K key){
		return getRedisTemplate().keys(key);
	}
	
	final protected <K extends Serializable,V extends Serializable> void setList(final K key, V...ks){
		if (ks != null) {
			getRedisTemplate().opsForList().rightPushAll(key, ks);
		}
	}
	
	@SuppressWarnings("unchecked")
	final protected <K extends Serializable,V extends Serializable> V getList(final K key){
		return (V) getRedisTemplate().opsForList().range(key, 0, -1);
	}
	
	final protected <K extends Serializable,V extends Serializable> void setSet(final K key, V...ks){
		if (ks != null) {
			getRedisTemplate().opsForSet().add(key, ks);
		}
	}
	final protected <K extends Serializable> void getSet(final K key){
		getRedisTemplate().opsForSet().members(key);
	}
	
//	final protected <K extends Serializable,V extends Serializable> void setSortSet(final K key, V...ks){
//		if (ks != null) {
//			getRedisTemplate().opsForZSet().
//		}
//	}
//	final protected <K extends Serializable> void getSortSet(final K key){
//		getRedisTemplate().opsForZSet().
//	}
	
	final protected <K extends Serializable, F, V> void mapPut(final K key, final F field, final V value){
		if (key != null && field != null && value != null) {
			getRedisTemplate().opsForHash().put(key, field, value);
		}
	}
	final protected <K extends Serializable> void mapPutAll(final K key, Map<String, String> data){
		if (key != null && data != null) {
			Map<String, String> map = new HashMap<String, String>();
			for (Entry<String, String> en : data.entrySet()) {
				if (en.getKey() != null && en.getValue() != null) {
					map.put(en.getKey(), en.getValue());
				}
			}
			getRedisTemplate().opsForHash().putAll(key, map);
			data.put("redisKey", key.toString());
		}
	}
	@SuppressWarnings("unchecked")
	final protected <K extends Serializable, F, V> V mapGet(final K key,final F field){
		return (V) getRedisTemplate().opsForHash().get(key, field);
	}
	@SuppressWarnings("unchecked")
	final protected <K extends Serializable, F extends Collection<Object>, V extends List<Object>> V mapMultiGet(final K key,final F field){
		return (V) getRedisTemplate().opsForHash().multiGet(key, field);
	}
	final protected Boolean hasKey(final String key){
		if (key == null) {
			return false;
		}
		return getRedisTemplate().hasKey(key);
	}
	
}
