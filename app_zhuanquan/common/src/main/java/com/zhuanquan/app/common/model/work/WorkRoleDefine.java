package com.zhuanquan.app.common.model.work;

/**
 * 工作职种角色定义
 * 
 *  第一级 100， 第二级别 100 001  第三级 100 001 001，类似这种模式
 *  
 * @author zhangjun
 *
 */
public class WorkRoleDefine {
	
    /**
     * 角色id
     */
	private String roleCode;
	
	/**
	 * 职种角色描述
	 */
	private String roleDesc;
	
	/**
	 * 层级
	 */
	private Integer lv;

	
	/**
	 * 是否还启用 0-失效 1-启用
	 */
	private Integer status;



	public String getRoleDesc() {
		return roleDesc;
	}

	public void setRoleDesc(String roleDesc) {
		this.roleDesc = roleDesc;
	}



	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getRoleCode() {
		return roleCode;
	}

	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}

	public Integer getLv() {
		return lv;
	}

	public void setLv(Integer lv) {
		this.lv = lv;
	}
	

	
	
	
	
	
}