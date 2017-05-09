package com.zhuanquan.app.common.component.event.redis;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.context.ApplicationListener;

import com.zhuanquan.app.common.component.event.redis.CacheClearEvent;
import com.zhuanquan.app.common.component.event.redis.RedisCacheEnum;


/**
 * 监听cache变化，及时清理
 * @author zhangjun
 *
 */
public abstract class CacheChangedListener implements ApplicationListener<CacheClearEvent> {
	
	
	@Override
	public void onApplicationEvent(CacheClearEvent event) {
		
		//忽略
		if(event == null || event.getCacheEnum() == null) {
			return;
		}
		
		
		List<RedisCacheEnum> monitorEnums = getMonitorRedisCache();
		if(CollectionUtils.isEmpty(monitorEnums)) {
			return;
		}

		RedisCacheEnum nowEnum = event.getCacheEnum();
		
        //匹配看看哪个事件匹配上了
		for(RedisCacheEnum record:monitorEnums) {
			
			if(nowEnum.getTable().equals(record.getTable())) {
			       //执行清理逻辑
				doProcessCacheCleanEvent(event);
				
				break;
			}
			
		}
	
		
	}
	
	
	/**
	 * 判断当前监听的是什么事件
	 * @return
	 */
	public abstract List<RedisCacheEnum> getMonitorRedisCache() ;

	
	/**
	 * 执行缓存清理逻辑
	 * @param event
	 */
	public abstract void doProcessCacheCleanEvent(CacheClearEvent event);

	
}