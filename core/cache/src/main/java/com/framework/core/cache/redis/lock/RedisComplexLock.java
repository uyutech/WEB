package com.framework.core.cache.redis.lock;

import com.framework.core.cache.redis.GracefulRedisTemplate;
import com.framework.core.cache.redis.RedisValueOperations;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

/**
 * redis 重度 分布式锁
 * 
 * @author zhangjun
 *
 */
public class RedisComplexLock {

	private static final Logger LOGGER = LoggerFactory.getLogger(RedisComplexLock.class);

	GracefulRedisTemplate<String, String> gracefulRedisTemplate;

	RedisValueOperations<String, String> redisValueOperations;

	public GracefulRedisTemplate<String, String> getGracefulRedisTemplate() {
		return gracefulRedisTemplate;
	}

	public void setGracefulRedisTemplate(GracefulRedisTemplate<String, String> gracefulRedisTemplate) {
		this.gracefulRedisTemplate = gracefulRedisTemplate;
	}

	public RedisValueOperations<String, String> getRedisValueOperations() {
		return redisValueOperations;
	}

	public void setRedisValueOperations(RedisValueOperations<String, String> redisValueOperations) {
		this.redisValueOperations = redisValueOperations;
	}


	
	/**
	 * 加锁逻辑: 使用redis点setNX命令,如果写入redis成功,返回true,如果已经存在返回false. 同时只会有一个线程\一个进程成功。
	 * 锁超时失效:如果不显示释放锁,默认 60s 失效
	 * @param key 锁的key
	 * @return pair左边是 lock结果，右边是lock的秘钥
	 */
	public Pair<Boolean,String> tryLock(String key) {

		return tryLock(key, 60, TimeUnit.SECONDS);

	}

	/**
	 * 加锁，指定锁超时时间.
	 * 
	 * 加锁逻辑: 使用redis点setNX命令,如果写入redis成功,返回true,如果已经存在返回false. 同时只会有一个线程\一个进程成功
	 * 
	 * @param key 锁的key
	 * @param timeout 锁最长的生存时间
	 * @param unit 锁的超时时间单位
	 * @return pair左边是 lock结果，右边是lock的秘钥，删除key需要带上秘钥
	 */
	public Pair<Boolean,String> tryLock(String key, long timeout, TimeUnit unit) {

		// 加锁失败, 抛出异常
		long nowTime = System.currentTimeMillis();
		if (Boolean.FALSE.equals(redisValueOperations.setIfAbsent(key, String.valueOf(nowTime)))) {
			
			//之前加锁时，设置超时时间的时候失败了，导致key变成永久的key,需要删掉
			checkIsForEver(key, timeout,unit);
			
			return Pair.of(false, String.valueOf(nowTime));
		}

		try {
			// 加锁成功,设置expire时间,默认60s
			gracefulRedisTemplate.longExpire(key, timeout, unit);

			return Pair.of(true, String.valueOf(nowTime));


		} catch (Exception e) {

			LOGGER.error("RedisComplexLock tryLock,set expire failed, key=" + key);

			try {
				gracefulRedisTemplate.delete(key);
				LOGGER.error("RedisComplexLock tryLock,set expire failed,then delete key success, key=" + key);

			} catch (Exception e1) {
				LOGGER.error("RedisComplexLock tryLock,set expire failed,then delete key failed again, key=" + key);
				// TODO
				// 添加监控上报，这里有永久的key存在
			}

		}

		return Pair.of(false, String.valueOf(nowTime));

	}
	
	/**
	 * 之前加锁时，设置超时时间的时候失败了，导致key变成永久的key,需要删掉
	 * @param key
	 * @param unit
	 */
	private void checkIsForEver(String key,long timeout,TimeUnit unit) {
		//之前加锁时，设置超时时间的时候失败了，导致key变成永久的key,需要删掉
		long expire = gracefulRedisTemplate.getExpire(key, unit);
		//-1表示 key未设置超时时间，-2表示 key不存在。
        if(expire == -1 ) {
			// 如果原来的超时时间未设置，这里设置expire时间，不能用delete，因为delete可能把高并发下别的线程新获取的锁给删掉
        	// 而设置超时，最坏的结果也只是稍微延迟下 超时时间
			gracefulRedisTemplate.longExpire(key, timeout, unit);
//        	gracefulRedisTemplate.delete(key);
        }
	}

	/**
	 * 释放锁
	 * 
	 * @param key
	 */
	public void releaseLock(String key,String encrpty) {
		
		//查询此key存的时间
		String obj = redisValueOperations.get(key);
		
		//如果obj 不存在
		// 如果秘要和存的不一致，那么不需要删除，可能这个key是别的线程插入的，自身的锁早就释放了
		if(StringUtils.isEmpty(obj)||!obj.equals(encrpty)) {
			return;
		}
		

		gracefulRedisTemplate.delete(key);
	}

}
