package com.zhuanquan.app.common.view.bo.author;

/**
 * 
 * @author zhangjun
 *
 */
public class AuthorRoleInfoBo {
	
	/**
	 * 角色编码
	 */
	private String roleCode;
	
	/**
	 * 角色描述
	 */
	private String roleDesc;
	

	/**
	 * 第几层
	 */
	private Integer lv;
	
	
	

	public Integer getLv() {
		return lv;
	}


	public void setLv(Integer lv) {
		this.lv = lv;
	}


	public String getRoleCode() {
		return roleCode;
	}


	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}


	public String getRoleDesc() {
		return roleDesc;
	}


	public void setRoleDesc(String roleDesc) {
		this.roleDesc = roleDesc;
	}



	
	
	
	
}