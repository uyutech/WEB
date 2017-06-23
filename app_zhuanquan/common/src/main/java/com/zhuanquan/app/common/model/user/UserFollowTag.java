package com.zhuanquan.app.common.model.user;

import java.util.Date;

import com.zhuanquan.app.common.model.common.Tag;

/**
 * 用户关注的标签
 * @author zhangjun
 *
 */
public class UserFollowTag {
	
	/**
	 * disable
	 */
	public static final int STAT_DISABLE = 0;
	
	
	/**
	 *  enable
	 */
	public static final int STAT_ENABLE = 1;

	
	/**
	 * 用户id
	 */
	private Long uid;
	
	/**
	 * tag标签id
	 */
	private Long tagId;
	
	
	/**
	 * tag 类型
	 */
	private int tagType;
	
	
    /**
     * 状态  0-disable  1-enable
     */
	private Integer status;
	

	
	/**
	 * 创建时间
	 */
	private Date createTime;

	/**
	 * 修改时间
	 */
	private Date modifyTime;
	

	public Date getModifyTime() {
		return modifyTime;
	}


	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}


	public Date getCreateTime() {
		return createTime;
	}


	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}


	public Long getUid() {
		return uid;
	}


	public void setUid(Long uid) {
		this.uid = uid;
	}


	public Long getTagId() {
		return tagId;
	}


	public void setTagId(Long tagId) {
		this.tagId = tagId;
	}


	public int getTagType() {
		return tagType;
	}


	public void setTagType(int tagType) {
		this.tagType = tagType;
	}



	public Integer getStatus() {
		return status;
	}


	public void setStatus(Integer status) {
		this.status = status;
	}


	/**
	 *  
	 * @param uid
	 * @param tag
	 * @return
	 */
	public static UserFollowTag transferToMapping(long uid,Tag tag) {
		
		UserFollowTag record = new UserFollowTag();
		
		record.setTagId(tag.getTagId());
		record.setTagType(tag.getTagType());
		record.setUid(uid);
		record.setStatus(STAT_ENABLE);
		
		Date now = new Date();
		
		record.setCreateTime(now);
		record.setModifyTime(now);
		return record;
		
	}
	
}