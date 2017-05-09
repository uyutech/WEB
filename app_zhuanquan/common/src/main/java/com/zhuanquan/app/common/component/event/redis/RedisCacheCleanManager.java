package com.zhuanquan.app.common.component.event.redis;

import java.util.Map;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Component;

@Component
public class RedisCacheCleanManager implements ApplicationEventPublisherAware {

	private ApplicationEventPublisher applicationEventPublisher;

	private static final String Event_TYPE = "Redis_Cache_Clean";

	@Override
	public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
		this.applicationEventPublisher = applicationEventPublisher;
	}

	/**
	 * 发送广播事件，清理缓存
	 * 
	 * @param cache
	 */
	public void sendCleanEvent(RedisCacheEnum cache,Map<String,String> parmMap) {

		CacheClearEvent event = new CacheClearEvent(Event_TYPE, cache,parmMap);

		applicationEventPublisher.publishEvent(event);

	}

}