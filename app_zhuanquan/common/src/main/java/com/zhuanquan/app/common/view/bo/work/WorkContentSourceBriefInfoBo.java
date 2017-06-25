package com.zhuanquan.app.common.view.bo.work;


import java.io.Serializable;


/**
 * 作品内容的简要信息
 * @author zhangjun
 *
 */
public class  WorkContentSourceBriefInfoBo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1389134983379987568L;
	

	/**
	 * 内容详情id
	 */
	private long sourceId;
	
	
	/**
	 * 大类区分：100-音频 101-视频 102-图片 103-文字
	 * 
	 * @see com.zhuanquan.app.common.constants.WorkSourceCategoryConstants
	 * 
	 */
	private Integer sourceCategory;


	/**
	 * 多媒体资源渠道 站内资源/5sing/本地资源等等
	 */
	private Integer platformId;

	/**
	 * 具体资源类型 原创,改编,翻填,翻唱
	 */
	private String sourceType;

	/**
	 * 音频视频图片的url
	 */
	private String sourceVal;

	public long getSourceId() {
		return sourceId;
	}

	public void setSourceId(long sourceId) {
		this.sourceId = sourceId;
	}

	public Integer getSourceCategory() {
		return sourceCategory;
	}

	public void setSourceCategory(Integer sourceCategory) {
		this.sourceCategory = sourceCategory;
	}

	public Integer getPlatformId() {
		return platformId;
	}

	public void setPlatformId(Integer platformId) {
		this.platformId = platformId;
	}

	public String getSourceType() {
		return sourceType;
	}

	public void setSourceType(String sourceType) {
		this.sourceType = sourceType;
	}

	public String getSourceVal() {
		return sourceVal;
	}

	public void setSourceVal(String sourceVal) {
		this.sourceVal = sourceVal;
	}
	
	
	
	
	
	
	
}