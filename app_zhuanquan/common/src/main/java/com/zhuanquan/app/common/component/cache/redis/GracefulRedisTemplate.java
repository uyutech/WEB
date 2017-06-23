package com.zhuanquan.app.common.component.cache.redis;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.TimeoutUtils;

import com.alibaba.fastjson.JSON;
import com.zhuanquan.app.common.component.cache.redis.serializer.SerializationUtil;

/**
 *
 * 重写RedisTemplate,支持读写分离,支持long expire
 *
 */
public class GracefulRedisTemplate<K, V> extends RedisTemplate<K, V> {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	private static final Charset CHARSET_UTF_8 = Charset.forName("UTF-8");

	private static final String EXEC_SUCESS = "1";

	/**
	 * spring redis 设置超长时间expire，会调用twemproxy不支持的time命令，导致报错。
	 * 
	 * @param key
	 * @param timeout
	 * @param unit
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Boolean longExpire(K key, final long timeout, final TimeUnit unit) {

		try {
			final byte[] rawKey = String.valueOf(key).getBytes("UTF-8");

			return (Boolean) this.execute(new RedisCallback() {
				@Override
				public Boolean doInRedis(RedisConnection connection) {

					return connection.expire(rawKey, TimeoutUtils.toSeconds(timeout, unit));
				}
			}, true);
		} catch (UnsupportedEncodingException e) {
			logger.warn("unsupport this encoding :{}", e);
		}

		return false;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void hSetObject(String key, String hashKey, Object value) {

		try {
			final byte[] rawKey = String.valueOf(key).getBytes("UTF-8");

			final byte[] field = String.valueOf(hashKey).getBytes("UTF-8");

			final byte[] hashvalue = SerializationUtil.serialize(value);

			this.execute(new RedisCallback() {

				@Override
				public Object doInRedis(RedisConnection connection) throws DataAccessException {
					return connection.hSet(rawKey, field, SerializationUtil.serialize(hashvalue));
				}
			}, true);

		} catch (UnsupportedEncodingException e) {
			logger.warn("unsupport this encoding :{}", e);
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Object hGetObject(String key, String hashKey) {

		try {
			final byte[] rawKey = String.valueOf(key).getBytes("UTF-8");

			final byte[] field = String.valueOf(hashKey).getBytes("UTF-8");

			return this.execute(new RedisCallback() {

				@Override
				public Object doInRedis(RedisConnection connection) throws DataAccessException {

					byte[] reuslt = connection.hGet(rawKey, field);

					return SerializationUtil.deserizlize(reuslt);
				}
			});

		} catch (UnsupportedEncodingException e) {
			logger.warn("unsupport this encoding :{}", e);

			return null;
		}

	}

	@Override
	public void delete(K key) {

		super.delete(key);

	}

	@Override
	public void delete(Collection<K> keys) {

		super.delete(keys);

	}

	/**
	 * 这个方法，不触发同步操作，业务模块不允许调用
	 * 
	 * @param keys
	 * @param isNeedCheckCacheDelete
	 */
	public void deleteWithoutSyncDelete(Collection<K> keys) {

		super.delete(keys);
	}

	/**
	 * set 结构的 批量 move key中的元素到另外个key理
	 * 
	 * @param key
	 *            原来的key
	 * @param members
	 *            元素
	 * @param destKey
	 *            迁移的key
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public boolean batchMove(String key, final Set<String> members, String destKey) {

		try {
			final byte[] rawKey = key.getBytes("UTF-8");
			final byte[] rawDestKey = destKey.getBytes("UTF-8");

			// final byte[] rawValue = rawValue(value);

			this.executePipelined(new RedisCallback() {

				@Override
				public List<Object> doInRedis(RedisConnection connection) throws DataAccessException {
					for (String item : members) {
						try {
							connection.sMove(rawKey, rawDestKey, item.getBytes("UTF-8"));
						} catch (UnsupportedEncodingException e) {
							e.printStackTrace();
						}
					}
					return null;
				}
			});

		} catch (Exception e) {
			logger.warn("unsupport this encoding :{}", e);

			return false;
		}

		return true;

	}

	/**
	 * zset 结构模拟pop
	 * 
	 * @param key
	 * @return
	 */
	public String zpop(String key) {

		return zsetPop(key, 0);
	}

	/**
	 * zset 结构模拟pop
	 * 
	 * @param key
	 * @return
	 */
	public String zRevpop(String key) {
		return zsetPop(key, -1);

	}

	/**
	 * zset 结构模拟pop
	 * 
	 * @param key
	 * @param position
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private String zsetPop(String key, int position) {

		try {

			final byte[] rawKey = key.getBytes("UTF-8");

			final int pos = position;

			Set<byte[]> setsResult = this.execute(new RedisCallback() {
				@Override
				public Set<byte[]> doInRedis(RedisConnection connection) {

					connection.watch(rawKey);

					Set<byte[]> sets = connection.zRange(rawKey, pos, pos);

					if (sets == null || sets.size() == 0) {
						return null;
					}

					connection.multi();
					connection.zRemRange(rawKey, pos, pos);

					// 执行结果
					List<Object> list = connection.exec();

					if (CollectionUtils.isEmpty(list)) {
						return null;
					} else {
						Object result = list.get(0);
						if (result.toString().equals(EXEC_SUCESS)) {
							return sets;
						}

						return null;
					}

				}
			}, true);

			if (CollectionUtils.isEmpty(setsResult)) {
				return null;
			}

			Iterator<byte[]> its = setsResult.iterator();

			while (its.hasNext()) {
				byte[] bytes = its.next();
				if (bytes != null) {
					return new String(bytes);
				}
			}

			return null;

		} catch (UnsupportedEncodingException e) {
			logger.warn("unsupport this encoding :{}", e);
		} catch (Exception e) {
			logger.warn("unexpected error :{}", e);

		}

		return null;
	}

	/**
	 * left batch pop for list
	 * 
	 * @param key
	 * @param batchLimit
	 * @return
	 */
	public List<String> listBatchLeftPop(String key, int batchLimit) {
		return listBatchPop(key, batchLimit, true);
	}

	/**
	 * right batch pop for list
	 * 
	 * @param key
	 * @param batchLimit
	 * @return
	 */
	public List<String> listBatchRightPop(String key, int batchLimit) {
		return listBatchPop(key, batchLimit, false);
	}

	
	/**
	 *  list，批量左边push 
	 * @param key
	 * @param list
	 * @return
	 */
	public boolean listBatchLeftPush(String key, List<String> list) {
		return listBatchPush(key, list, true);
	}
	
	
	/**
	 * list，批量右边push 
	 * @param key
	 * @param list
	 * @return
	 */
	public boolean listBatchRightPush(String key, List<String> list) {
		return listBatchPush(key, list, false);
	}	
	
	
	
	
	/**
	 * list结构，批量pop
	 * 
	 * @param key
	 * @param members
	 * @param destKey
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	private List<String> listBatchPop(String key, int batchLimit, boolean isLeft) {

		try {
			final byte[] rawKey = key.getBytes("UTF-8");

			final int limit = batchLimit;

			final boolean isLeftPop = isLeft;

			List<String> resultList = new ArrayList<>();

			List<Object> list = this.executePipelined(new RedisCallback() {

				@Override
				public List<String> doInRedis(RedisConnection connection) throws DataAccessException {

					for (int i = 0; i < limit; i++) {
						if (isLeftPop) {
							connection.lPop(rawKey);
						} else {
							connection.rPop(rawKey);

						}
					}

					return null;
				}
			});

			if (CollectionUtils.isEmpty(list)) {
				return null;
			}

			for (Object obj : list) {
				if (obj == null) {
					continue;
				}

				resultList.add((String) obj);
			}

			return resultList;

		} catch (Exception e) {

			logger.warn("unsupport this encoding :{}", e);

			return null;
		}
	}

	
	
	
	
	
	
	/**
	 * list结构，批量pop
	 * 
	 * @param key
	 * @param members
	 * @param destKey
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	private boolean listBatchPush(String key, List<String> list, boolean isLeftPush) {

		if (CollectionUtils.isEmpty(list)) {
			return true;
		}

		try {
			final byte[] rawKey = key.getBytes("UTF-8");

			final List<String> targetList = list;

			final boolean isLPush = isLeftPush;

			this.executePipelined(new RedisCallback() {

				@Override
				public Object doInRedis(RedisConnection connection) throws DataAccessException {

					try {
						for (String record : targetList) {
							if (isLPush) {
								connection.lPush(rawKey, record.getBytes("UTF-8"));
							} else {
								connection.rPush(rawKey, record.getBytes("UTF-8"));
							}
						}
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					}

					return null;
				}
			});

			
		} catch (Exception e) {
			logger.error("listBatchPush exception :[key]:" + key + ",[list]:" + JSON.toJSONString(list), e);

			return false;
		}
		
		
		return true;
	}

}
