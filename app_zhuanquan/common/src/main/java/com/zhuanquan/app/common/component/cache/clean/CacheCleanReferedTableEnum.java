package com.zhuanquan.app.common.component.cache.clean;


/**
 * 缓存清理 关联的表
 * @author zhangjun
 *
 */
public enum CacheCleanReferedTableEnum{
	
	

	
	;
	
	private String tabName;
	

	public String getTabName() {
		return tabName;
	}



	public void setTabName(String tabName) {
		this.tabName = tabName;
	}



	private CacheCleanReferedTableEnum(String tabName) {
		this.tabName = tabName;
	}
	
	
	
	
}