package com.zhuanquan.app.common.component.cache.clean;

/**
 * 缓存清理事件枚举
 * 
 * @author zhangjun
 *
 */
public enum CacheCleanEventTypeEnum {

	
	

	
	;

	/**
	 * 表名
	 */
	private String tabName;

	/**
	 * 类型
	 */
	private String desc;

	/**
	 * 事件类型
	 */
	private int code;

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getTabName() {
		return tabName;
	}

	public void setTabName(String tabName) {
		this.tabName = tabName;
	}

	private CacheCleanEventTypeEnum(String tabName, int code, String desc) {

		this.tabName = tabName;

		this.code = code;

		this.desc = desc;

	}

}