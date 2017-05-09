package com.zhuanquan.app.common.component.event.redis;


import java.util.Map;

import com.zhuanquan.app.common.component.event.CommonEvent;
import com.zhuanquan.app.common.component.event.EventTypeEnum;


public class CacheClearEvent extends CommonEvent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 914411817999713085L;

	/**
	 * cache的枚举
	 */
	private RedisCacheEnum cacheEnum;
	
	/**
	 * 会用到的参数map
	 */
	private Map<String,String> parmMap ;
	
	public CacheClearEvent(String name,RedisCacheEnum cacheEnum,Map<String,String> map) {
		super(name);
		
		this.cacheEnum = cacheEnum;
		
		this.parmMap = map;
	}



	public RedisCacheEnum getCacheEnum() {
		return cacheEnum;
	}



	public Map<String,String> getParmMap() {
		return parmMap;
	}



	public void setParmMap(Map<String,String> parmMap) {
		this.parmMap = parmMap;
	}



	public void setCacheEnum(RedisCacheEnum cacheEnum) {
		this.cacheEnum = cacheEnum;
	}



	@Override
	public EventTypeEnum getEventType() {
		return EventTypeEnum.CACHE_CLEAN_EVENT;
	}
	
}