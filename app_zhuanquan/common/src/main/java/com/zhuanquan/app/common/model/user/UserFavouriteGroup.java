package com.zhuanquan.app.common.model.user;

import java.util.Date;

/**
 * 用户收藏的分组
 * @author zhangjun
 *
 */
public class UserFavouriteGroup {
	
	/**
	 * 收藏分组id
	 */
	private Long groupId;
	
	/**
	 * 分组名字
	 */
	private String groupName;
	
	/**
	 * 所属用户id
	 */
	private Long userId;

	 
	/**
	 * 创建时间
	 */
	private Date createTime;
	
	/**
	 * 修改时间
	 */
	private Date modifyTime;
	
	/**
	 * 分组状态  0-disable 1-enable
	 */
	private Integer status;
	

	public Long getGroupId() {
		return groupId;
	}

	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
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

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
	
	
	

	
}