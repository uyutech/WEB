package com.zhuanquan.app.server.openapi;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.collections.MapUtils;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class OpenApiAdapter implements ApplicationContextAware {

	
	private Map<String,OpenApiConnector> connectorMap = new HashMap<String,OpenApiConnector>();
	
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {

		Map<String, OpenApiConnector> map = applicationContext.getBeansOfType(OpenApiConnector.class);
		
		if(MapUtils.isEmpty(map)) {
			return;
		}
		
		//注册
		for(Entry<String, OpenApiConnector> entry:map.entrySet()) {
			connectorMap.put(entry.getValue().getChannelType()+"", entry.getValue());
		}
		
	}
	
	/**
	 * 获取实例
	 * @param channelType
	 * @return
	 */
	public OpenApiConnector getConnectorInstance(int channelType) {
		
		return connectorMap.get(String.valueOf(channelType));
	}
	
}