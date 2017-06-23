package com.zhuanquan.app.common.model.work;

/**
 * 工作职种角色定义
 * @author zhangjun
 *
 */
public class WorkRoleDefine {
	
    /**
     * 角色id
     */
	private Long roleId;
	
	/**
	 * 职种角色描述
	 */
	private String roleDesc;
	
	/**
	 * 父节点id，-1的表示是 1级节点
	 */
	private Long fatherId;
	
	/**
	 * 是否还启用 0-失效 1-启用
	 */
	private Integer status;

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public String getRoleDesc() {
		return roleDesc;
	}

	public void setRoleDesc(String roleDesc) {
		this.roleDesc = roleDesc;
	}

	public Long getFatherId() {
		return fatherId;
	}

	public void setFatherId(Long fatherId) {
		this.fatherId = fatherId;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	

	
	
	
	
	
}