package com.zhuanquan.app.common.model.work;

import com.zhuanquan.app.common.view.vo.sync.MediaSourceReleatedAuthorVo;

/**
 * 作品参与者
 * @author zhangjun
 *
 */
public class WorkAttender {
	
	public static final int STAT_ENABLE = 1;
	
	public static final int STAT_DISABLE = 0;
	
    /**
     * 作品id
     */
	private Long workId;
	
	/**
	 * 内容资源id，WorkContentSource里的主键
	 */
	private Long mediaSourceId;
	
	/**
	 * 多媒体资源类型: 音频，视频，还是图片.
	 * 
	 * 当职种为策划/出品人时，这个属性没有意义。因为这2个是针对整个作品的，而不是针对部分资源的
	 * 
	 * @see com.zhuanquan.app.common.constants.WorkSourceCategoryConstants
	 */
	private Integer sourceCategory;
	
	/**
	 * 职种角色
	 */
	private Integer roleType;
	
	/**
	 * 作者id
	 */
	private Long authorId;
	
	/**
	 * 排序字段
	 */
	private Integer orderNum;
	
    /**
     * 灵感
     */
	private String inspiration;
	
	/**
	 * 1-enable 0-disable
	 */
	private Integer status;

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Long getWorkId() {
		return workId;
	}

	public void setWorkId(Long workId) {
		this.workId = workId;
	}

	public Integer getRoleType() {
		return roleType;
	}

	public void setRoleType(Integer roleType) {
		this.roleType = roleType;
	}

	public Long getAuthorId() {
		return authorId;
	}

	public void setAuthorId(Long authorId) {
		this.authorId = authorId;
	}



	public Integer getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(Integer orderNum) {
		this.orderNum = orderNum;
	}

	public String getInspiration() {
		return inspiration;
	}

	public void setInspiration(String inspiration) {
		this.inspiration = inspiration;
	}

	public Long getMediaSourceId() {
		return mediaSourceId;
	}

	public void setMediaSourceId(Long mediaSourceId) {
		this.mediaSourceId = mediaSourceId;
	}

	public Integer getSourceCategory() {
		return sourceCategory;
	}

	public void setSourceCategory(Integer sourceCategory) {
		this.sourceCategory = sourceCategory;
	}

	/**
	 * 创建记录
	 * @return
	 */
   public static WorkAttender createInstance(long workId,long sourceId,int sourceCategory,int roleType,long authorId,int orderNum,String inspiration){
	   
	   WorkAttender record = new WorkAttender();
	   
	   record.setAuthorId(authorId);
	   record.setInspiration(inspiration);
	   record.setMediaSourceId(sourceId);
	   record.setOrderNum(orderNum);
	   record.setRoleType(roleType);
	   record.setSourceCategory(sourceCategory);
	   record.setStatus(STAT_ENABLE);
	   record.setWorkId(workId);
	   return record; 
   }
	
   

}