package com.zhuanquan.app.common.model.work;

import java.util.Date;

import com.zhuanquan.app.common.view.vo.sync.MediaSourceInfoVo;

/**
 * 作品内容资源：
 * 
 * @author zhangjun
 *
 */
public class WorkContentSource {

	public static final int STAT_DISABLE = 0;

	public static final int STAT_ENABLE = 1;

	/**
	 * 内容详情id
	 */
	private Long sourceId;

	/**
	 * 作品id
	 */
	private Long workId;

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

	/**
	 * 原来的soureid，针对衍生作品
	 */
	private Long originSourceId;

	/**
	 * 状态 0-废弃 1-启用
	 */
	private Integer status;
	
	/**
	 * 排序字段
	 */
	private Integer orderNum;

	/**
	 * 创建时间
	 */
	private Date createTime;

	/**
	 * 修改时间
	 */
	private Date modifyTime;

	public Long getSourceId() {
		return sourceId;
	}

	public void setSourceId(Long sourceId) {
		this.sourceId = sourceId;
	}

	public Long getWorkId() {
		return workId;
	}

	public void setWorkId(Long workId) {
		this.workId = workId;
	}


	
	
	public Integer getPlatformId() {
		return platformId;
	}

	public void setPlatformId(Integer platformId) {
		this.platformId = platformId;
	}

	public Integer getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(Integer orderNum) {
		this.orderNum = orderNum;
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

	public Long getOriginSourceId() {
		return originSourceId;
	}

	public void setOriginSourceId(Long originSourceId) {
		this.originSourceId = originSourceId;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	
	/**
	 * 创建同人的这种的source，有 originSourceId
	 * @param workId
	 * @param sourceChannel
	 * @param sourceCategory
	 * @param sourceType
	 * @param url
	 * @param originSourceId
	 * @return
	 */
	public static WorkContentSource createInstance(long workId, int platformId, int sourceCategory, String sourceType,
			String url, long originSourceId,int orderNum) {

		WorkContentSource source = new WorkContentSource();

		Date now = new Date();

		source.setCreateTime(now);
		source.setModifyTime(now);

		source.setOriginSourceId(originSourceId);

		source.setSourceCategory(sourceCategory);
		
		source.setPlatformId(platformId);
		source.setSourceType(sourceType);
		source.setOrderNum(orderNum);

		source.setSourceVal(url);
		source.setStatus(STAT_ENABLE);
		source.setWorkId(workId);

		return source;
	}
	
	public Integer getSourceCategory() {
		return sourceCategory;
	}

	public void setSourceCategory(Integer sourceCategory) {
		this.sourceCategory = sourceCategory;
	}
	
	/**
	 * 原创资源，非同人
	 * @param workId
	 * @param sourceChannel
	 * @param sourceCategory
	 * @param sourceType
	 * @param url
	 * @param originSourceId
	 * @return
	 */
	public static WorkContentSource createInstance(long workId, int sourceChannel, int sourceCategory, String sourceType,
			String url,int orderNum) {
		return createInstance(workId, sourceChannel, sourceCategory, sourceType, url, 0,orderNum);
	}
	
	/**
	 * 
	 * @param vo
	 * @return
	 */
	public static WorkContentSource createInstance(long workId,MediaSourceInfoVo media){
		
		return WorkContentSource.createInstance(workId, media.getSourceChannel(), media.getSourceCategory(), media.getSourceType(), media.getSourceUrl(),media.getOriginSourceId(),media.getOrderNum());
	}
			

}