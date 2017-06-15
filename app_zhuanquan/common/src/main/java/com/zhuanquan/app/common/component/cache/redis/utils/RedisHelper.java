package com.zhuanquan.app.common.component.cache.redis.utils;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import org.springframework.data.redis.core.ZSetOperations;
import com.zhuanquan.app.common.component.cache.redis.GracefulRedisTemplate;
import com.zhuanquan.app.common.component.cache.redis.RedisHashOperations;
import com.zhuanquan.app.common.component.cache.redis.RedisListOperations;
import com.zhuanquan.app.common.component.cache.redis.RedisSetOperations;
import com.zhuanquan.app.common.component.cache.redis.RedisValueOperations;
import com.zhuanquan.app.common.component.cache.redis.RedisZSetOperations;
import com.zhuanquan.app.common.component.cache.redis.exception.RedisErrorCode;
import com.zhuanquan.app.common.exception.BizException;


/**
 * 
 * @author zhangjun
 */
public class RedisHelper {

	private GracefulRedisTemplate gracefulRedisTemplate;

	private RedisValueOperations<String, String> redisValueOperations;

	private RedisHashOperations<String, String, String> redisHashOperations;

	private RedisListOperations<String, String> redisListOperations;
	
	
	private RedisSetOperations<String, String> redisSetOperations;
	
	private RedisZSetOperations<String, String> redisZSetOperations;

	
	public RedisSetOperations<String, String> getRedisSetOperations() {
		return redisSetOperations;
	}

	public void setRedisSetOperations(RedisSetOperations<String, String> redisSetOperations) {
		this.redisSetOperations = redisSetOperations;
	}

	public RedisZSetOperations getRedisZSetOperations() {
		return redisZSetOperations;
	}

	public void setRedisZSetOperations(RedisZSetOperations redisZSetOperations) {
		this.redisZSetOperations = redisZSetOperations;
	}

	public RedisListOperations<String, String> getRedisListOperations() {
		return redisListOperations;
	}

	public void setRedisListOperations(RedisListOperations<String, String> redisListOperations) {
		this.redisListOperations = redisListOperations;
	}

	public RedisHashOperations<String, String, String> getRedisHashOperations() {
		return redisHashOperations;
	}

	public void setRedisHashOperations(RedisHashOperations<String, String, String> redisHashOperations) {
		this.redisHashOperations = redisHashOperations;
	}

	public GracefulRedisTemplate getGracefulRedisTemplate() {
		return gracefulRedisTemplate;
	}

	public void setGracefulRedisTemplate(GracefulRedisTemplate gracefulRedisTemplate) {
		this.gracefulRedisTemplate = gracefulRedisTemplate;
	}

	public RedisValueOperations<String, String> getRedisValueOperations() {
		return redisValueOperations;
	}

	public void setRedisValueOperations(RedisValueOperations<String, String> redisValueOperations) {
		this.redisValueOperations = redisValueOperations;
	}

	/**
	 * value结构，set操作，
	 * 
	 * 注意: 超时时间请不要设置太长！
	 * 
	 * @param key
	 * @param t
	 * @param timeout
	 * @param unit
	 */
	public <T> void valueSet(String key, String value, long timeout, TimeUnit unit) {

		try {

			redisValueOperations.set(key, value, timeout, unit);
		} catch (Exception e) {
			throw new BizException(RedisErrorCode.EX_SYS_REDIS_SET_FAIL.getCode(), e);
		}
	}

	/**
	 * value结构，，删除session中的key信息
	 * 
	 * @param key
	 */
	public void delete(String key) {

		try {
			gracefulRedisTemplate.delete(key);
		} catch (Exception e) {
			throw new BizException(RedisErrorCode.EX_SYS_REDIS_DELETE_FAIL.getCode(), e);

		}
	}

	/**
	 * value结构，get操作
	 * 
	 * @param key
	 * @param targetClass
	 * @return
	 */
	public String valueGet(String key) {

		try {
			String jsonObj = redisValueOperations.get(key);

			return jsonObj;

		} catch (Exception e) {
			throw new BizException(RedisErrorCode.EX_SYS_REDIS_GET_FAIL.getCode(), e);

		}

	}
	
	
	/**
	 * value结构，get操作
	 * 
	 * @param key
	 * @param targetClass
	 * @return
	 */
	public long valueGetLong(String key) {
		try {
			
			Long obj = (Long) gracefulRedisTemplate.opsForValue().get(key);

			return obj == null?0:obj;

		} catch (Exception e) {
			throw new BizException(RedisErrorCode.EX_SYS_REDIS_GET_FAIL.getCode(), e);

		}

	}
	
	
	/**
	 * 数值增加
	 * @param key
	 * @param delta
	 */
	public void increase(String key,long delta) {
		try {
			gracefulRedisTemplate.opsForValue().increment(key,delta);
		} catch (Exception e) {
			throw new BizException(RedisErrorCode.EX_SYS_REDIS_SET_FAIL.getCode(), e);

		}

	}
	
	
	
	/**
	 * 数值减少
	 * @param key
	 * @param delta
	 */
	public void decrease(String key,long delta) {
		try {
			gracefulRedisTemplate.opsForValue().increment(key, -delta);
		} catch (Exception e) {
			throw new BizException(RedisErrorCode.EX_SYS_REDIS_SET_FAIL.getCode(), e);

		}

	}
	
	
	
	
	

	/**
	 * value结构，可用作锁的场景。
	 * 
	 * 记住，这里没有设置超时时间，请慎重使用此方法，后面请设置超时时间
	 * 
	 * @param key
	 * @param t
	 * @return
	 */
	public Boolean valueSetIfAbsent(String key, String value) {

		try {

			return redisValueOperations.setIfAbsent(key, value);
		} catch (Exception e) {
			throw new BizException(RedisErrorCode.EX_SYS_REDIS_SET_FAIL.getCode(), e);
		}

	}

	/**
	 * value型数据结构 : 批量set 请慎重使用，这里没有设置超时时间，请慎重使用此方法，后面请设置超时时间
	 * 
	 * @param m
	 */
	public void valueMultiSet(Map<String, String> m) {
		try {
			redisValueOperations.multiSet(m);
		} catch (Exception e) {
			throw new BizException(RedisErrorCode.EX_SYS_REDIS_SET_FAIL.getCode(), e);

		}
	}

	/**
	 * value型数据结构 : 批量获取
	 * 
	 * @param keys
	 * @return
	 */
	public List<String> valueMultiGet(Collection<String> keys) {
		try {
			return redisValueOperations.multiGet(keys);
		} catch (Exception e) {
			throw new BizException(RedisErrorCode.EX_SYS_REDIS_GET_FAIL.getCode(), e);

		}
	}

	/**
	 * hash类型: 数据结果，批量删除
	 * 
	 * @param key
	 * @param hashKeys
	 *            string类型的
	 */
	public void hashDelete(String key, String... hashKeys) {
		try {
			redisHashOperations.delete(key, hashKeys);
		} catch (Exception e) {
			throw new BizException(RedisErrorCode.EX_SYS_REDIS_DELETE_FAIL.getCode(), e);

		}
	}

	/**
	 * hash类型，set方法 请慎重使用，这里没有设置超时时间，会导致key永久存在。 请慎重使用此方法， 1.
	 * 后面设置超时时间，超时时间只能针对key设置。
	 * 
	 * @param key
	 * @param hashKey
	 * @param value
	 */
	public void hashSet(String key, String hashKey, String value) {

		try {
			redisHashOperations.put(key, hashKey, value);

		} catch (Exception e) {
			throw new BizException(RedisErrorCode.EX_SYS_REDIS_SET_FAIL.getCode(), e);
		}
	}

	/**
	 * hash结构的 get
	 * 
	 * @param key
	 * @param hashKey
	 * @return
	 */
	public String hashGet(String key, String hashKey) {

		try {
			return redisHashOperations.get(key, hashKey);

		} catch (Exception e) {
			throw new BizException(RedisErrorCode.EX_SYS_REDIS_GET_FAIL.getCode(), e);
		}
	}

	/**
	 * hash类型，set方法 请慎重使用， 1. 这里虽然设置了超时时间，但是不能保证操作的原子性，有可能setexpire失败，导致key永远存在
	 * 2. 设置超时是针对key操作的。 hash结构是多个hashkey公用一个key，这个操作会不断刷新整个key下面所有的数据的超时时间
	 * 
	 * @param key
	 * @param hashKey
	 * @param value
	 */
	public void hashSetEx(String key, String hashKey, String value, long timeout, TimeUnit unit) {

		try {
			redisHashOperations.put(key, hashKey, value);

			expire(key, timeout, unit);

		} catch (BizException e) {
			throw e;
		} catch (Exception e) {
			throw new BizException(RedisErrorCode.EX_SYS_REDIS_SET_FAIL.getCode(), e);
		}
	}

	/**
	 * hash结构: 批量get
	 * 
	 * @param key
	 * @param hashKeys
	 * @return
	 */
	public List<String> hashMultiGet(String key, Collection<String> hashKeys) {
		try {
			return redisHashOperations.multiGet(key, hashKeys);
		} catch (Exception e) {
			throw new BizException(RedisErrorCode.EX_SYS_REDIS_GET_FAIL.getCode(), e);

		}
	}

	/**
	 * hash结构: 批量set 请慎重使用，这里没有设置超时时间，会导致key永久存在。
	 * 请慎重使用此方法，后面设置超时时间，超时时间只能针对key设置。
	 * 
	 * @param key
	 * @param m
	 */
	public void putAll(String key, Map<String, String> m) {

		try {
			redisHashOperations.putAll(key, m);

		} catch (Exception e) {
			throw new BizException(RedisErrorCode.EX_SYS_REDIS_SET_FAIL.getCode(), e);
		}
	}

	/**
	 * hash结构: 获取key下的所有的hashkey，和value
	 * 
	 * @param key
	 * @return
	 */
	public Map<String, String> hashGetByKey(String key) {

		try {
			return redisHashOperations.entries(key);

		} catch (Exception e) {
			throw new BizException(RedisErrorCode.EX_SYS_REDIS_GET_FAIL.getCode(), e);

		}

	}

	/**
	 * 设置key的超时时间。 注意: 不要设置过长的超时时间。 针对单个key设置超时时间 spring redis
	 * 设置超长时间expire，会调用twemproxy不支持的time命令，导致报错。
	 * 
	 * @param key
	 * @param timeout
	 * @param unit
	 * @return
	 */
	public Boolean expire(String key, long timeout, TimeUnit unit) {
		try {
			return gracefulRedisTemplate.longExpire(key, timeout, unit);
		} catch (Exception e) {
			throw new BizException(RedisErrorCode.EX_SYS_REDIS_SET_FAIL.getCode(), e);

		}
	}

	/**
	 * 
	 * @param key
	 * @param start
	 * @param end
	 * @return
	 */
	public List<String> lrange(String key, long start, long end) {

		try {
			return redisListOperations.range(key, start, end);

		} catch (Exception e) {

			throw new BizException(RedisErrorCode.EX_SYS_REDIS_GET_FAIL.getCode(), e);

		}
	}

	/**
	 * 截取list结构中的一段
	 * 
	 * @param key
	 * @param start
	 * @param end
	 */
	public void ltrim(String key, long start, long end) {

		try {
			redisListOperations.trim(key, start, end);
		} catch (Exception e) {
			throw new BizException(RedisErrorCode.EX_SYS_REDIS_SET_FAIL.getCode(), e);
		}
	}

	/**
	 * left push
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public Long leftPush(String key, String value) {
		try {
			return redisListOperations.leftPush(key, value);

		} catch (Exception e) {
			throw new BizException(RedisErrorCode.EX_SYS_REDIS_SET_FAIL.getCode(), e);
		}
	}

	/**
	 * left push all
	 * 
	 * @param key
	 * @param values
	 * @return
	 */
	public Long leftPushAll(String key, String... values) {
		try {
			return redisListOperations.leftPushAll(key, values);
		} catch (Exception e) {
			throw new BizException(RedisErrorCode.EX_SYS_REDIS_SET_FAIL.getCode(), e);

		}
	}

	/**
	 * leftPushAll
	 * 
	 * @param key
	 * @param values
	 * @return
	 */
	public Long leftPushAll(String key, Collection<String> values) {

		try {
			return redisListOperations.leftPushAll(key, values);
		} catch (Exception e) {
			throw new BizException(RedisErrorCode.EX_SYS_REDIS_SET_FAIL.getCode(), e);

		}
	}

	/**
	 * rightPush
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public Long rightPush(String key, String value) {

		try {
			return redisListOperations.rightPush(key, value);
		} catch (Exception e) {
			throw new BizException(RedisErrorCode.EX_SYS_REDIS_SET_FAIL.getCode(), e);

		}

	}

	/**
	 * rightPushAll
	 * 
	 * @param key
	 * @param values
	 * @return
	 */
	public Long rightPushAll(String key, String... values) {

		try {
			return redisListOperations.rightPushAll(key, values);
		} catch (Exception e) {
			throw new BizException(RedisErrorCode.EX_SYS_REDIS_SET_FAIL.getCode(), e);

		}
	}

	/**
	 * rightPushAll
	 * 
	 * @param key
	 * @param values
	 * @return
	 */
	public Long rightPushAll(String key, Collection<String> values) {

		try {
			return redisListOperations.rightPushAll(key, values);

		} catch (Exception e) {
			throw new BizException(RedisErrorCode.EX_SYS_REDIS_SET_FAIL.getCode(), e);

		}
	}

	/**
	 * leftPop
	 * 
	 * @param key
	 * @return
	 */
	public String leftPop(String key) {

		try {
			return redisListOperations.leftPop(key);
		} catch (Exception e) {
			throw new BizException(RedisErrorCode.EX_SYS_REDIS_SET_FAIL.getCode(), e);

		}
	}

	/**
	 * leftPop
	 * 
	 * @param key
	 * @param timeout
	 * @param unit
	 * @return
	 */
	public String leftPop(String key, long timeout, TimeUnit unit) {

		try {
			return redisListOperations.leftPop(key, timeout, unit);

		} catch (Exception e) {
			throw new BizException(RedisErrorCode.EX_SYS_REDIS_SET_FAIL.getCode(), e);

		}
	}

	/**
	 * rightPop
	 * 
	 * @param key
	 * @return
	 */
	public String rightPop(String key) {

		try {
			return redisListOperations.rightPop(key);
		} catch (Exception e) {
			throw new BizException(RedisErrorCode.EX_SYS_REDIS_SET_FAIL.getCode(), e);

		}
	}

	/**
	 * rightPop
	 * 
	 * @param key
	 * @param timeout
	 * @param unit
	 * @return
	 */
	public String rightPop(String key, long timeout, TimeUnit unit) {

		try {
			return redisListOperations.rightPop(key, timeout, unit);
		} catch (Exception e) {
			throw new BizException(RedisErrorCode.EX_SYS_REDIS_SET_FAIL.getCode(), e);

		}
	}

	/**
	 * rightPopAndLeftPush
	 * 
	 * @param sourceKey
	 * @param destinationKey
	 * @return
	 */
	public String rightPopAndLeftPush(String sourceKey, String destinationKey) {

		try {
			return redisListOperations.rightPopAndLeftPush(sourceKey, destinationKey);
		} catch (Exception e) {
			throw new BizException(RedisErrorCode.EX_SYS_REDIS_SET_FAIL.getCode(), e);

		}

	}

	/**
	 * rightPopAndLeftPush
	 * 
	 * @param sourceKey
	 * @param destinationKey
	 * @param timeout
	 * @param unit
	 * @return
	 */
	public String rightPopAndLeftPush(String sourceKey, String destinationKey, long timeout, TimeUnit unit) {

		try {
			return redisListOperations.rightPopAndLeftPush(sourceKey, destinationKey, timeout, unit);
		} catch (Exception e) {
			throw new BizException(RedisErrorCode.EX_SYS_REDIS_SET_FAIL.getCode(), e);

		}
	}

	/**
	 * listSize
	 * 
	 * @param key
	 * @return
	 */
	public Long listSize(String key) {

		try {
			return redisListOperations.size(key);
		} catch (Exception e) {
			throw new BizException(RedisErrorCode.EX_SYS_REDIS_GET_FAIL.getCode(), e);

		}

	}


	
    /**
     * hash put all
     * @param key
     * @param map
     */
	public void hashPutAll(String key, Map<String, String> map) {

		redisHashOperations.putAll(key, map);
	}
	
	
	/**
	 * 返回指定score 区段之间元素
	 * @param key
	 * @param min
	 * @param max
	 * @return
	 */
	public Set<String> zsetRangeByScore(String key,double min,double max){
		
		return redisZSetOperations.rangeByScore(key, min, max);
	}
	
	
	/**
	 *  返回区间指定元素）,集合中元素是按score从小到大排序的
	 * @param key
	 * @param start 最小下标
	 * @param end 最大下标
	 * @return
	 */
	public Set<String> zsetRange(String key,long start,long end){
		
		return redisZSetOperations.range(key, start, end);
	}	
	
	
	/**
	 * 返回区间指定元素,集合中元素是按score从大到小排序的，
	 * @param key
	 * @param start
	 * @param end
	 * @return
	 */
	public Set<String> zsetRevrange(String key,long start,long end){
		
		return redisZSetOperations.reverseRange(key, start, end);
	}	
		

	/**
	 * 返回区间指定元素的数量
	 * @param key
	 * @param min
	 * @param max
	 * @return
	 */
	public long zsetCountByScore(String key,double start,double end){
		
		return redisZSetOperations.count(key, start, end);
		
	}		
	
	
	/**
	 * 获取member对应的score
	 * @param key
	 * @param member
	 * @return
	 */
	public double zsetScore(String key,String member) {
		
		return redisZSetOperations.score(key, member);
	}
	
	
	/**
	 * 删除指定index区间的元素
	 * @param key
	 * @param start
	 * @param end
	 * @return
	 */
	public long zsetRemrangebyrank(String key,int start,int end){
		
		return redisZSetOperations.removeRange(key, start, end);
		
	}
	
	
	/**
	 * 删除指定score区间的元素
	 * @param key
	 * @param min
	 * @param max
	 * @return
	 */
	public long zsetRemrangebyscore(String key,double min,double max){
		
		return redisZSetOperations.removeRangeByScore(key, min, max);
		
	}	
	
	
	
	/**
	 * 添加元素
	 * @param key
	 * @param score
	 * @param member
	 */
	public void zsetAddMember(String key,double score,String member) {

		redisZSetOperations.add(key, member, score);
	}
	
	
	/**
	 * 删除
	 * @param key
	 * @param member
	 */
	public void zsetRemoveMember(String key,String member) {
		redisZSetOperations.remove(key, member);
	}
	
	
	/**
	 * 给指定score增加值incr
	 * @param key
	 * @param incr
	 * @param member
	 */
	public void zsetIncrby(String key,double incr,String member) {
		
		redisZSetOperations.incrementScore(key, member, incr);
	}
	
	
	

	public Long zsetAdd(String key, Set<ZSetOperations.TypedTuple<String>> typedTuples) {
		
		return redisZSetOperations.add(key, typedTuples);
	}
	
	
	
	/**
	 * zset模拟pop
	 * @param key
	 * @return
	 */
	public String zpop(String key) {

		return gracefulRedisTemplate.zpop(key);
	}
	
	/**
	 * zset模拟 pop
	 * @param key
	 * @return
	 */
	public String zRevpop(String key) {
		return gracefulRedisTemplate.zRevpop(key);

	}

	
	
	/**
	 * left batch pop  for list
	 * @param key
	 * @param batchLimit
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<String> lBatchLeftPop(String key, int batchLimit) {
		return gracefulRedisTemplate.listBatchLeftPop(key, batchLimit);
	}
	
	
	/**
	 * right batch pop  for list
	 * @param key
	 * @param batchLimit
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<String> lBatchRightPop(String key, int batchLimit) {
		return gracefulRedisTemplate.listBatchRightPop(key, batchLimit);
	}
		


}