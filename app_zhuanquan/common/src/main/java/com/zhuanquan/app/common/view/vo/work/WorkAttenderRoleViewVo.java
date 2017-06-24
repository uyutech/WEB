package com.zhuanquan.app.common.view.vo.work;

import java.io.Serializable;
import java.util.List;

import com.zhuanquan.app.common.view.bo.author.AuthorBriefInfoBo;

/**
 * 
 * 
 * 作品参与者的角色视图，
 * 
 * 以角色为维度，统计角色下有哪些人
 * @author zhangjun
 *
 */
public class WorkAttenderRoleViewVo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2223873563288118832L;
	
	/**
	 *  角色类型
	 */
	private int roleType;
	
	/**
	 * 角色名称
	 */
	private String roleName;
	
	
	
	/**
	 * 参与者
	 */
	private List<AuthorBriefInfoBo> attenders;



	public int getRoleType() {
		return roleType;
	}



	public void setRoleType(int roleType) {
		this.roleType = roleType;
	}



	public String getRoleName() {
		return roleName;
	}



	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}



	public List<AuthorBriefInfoBo> getAttenders() {
		return attenders;
	}



	public void setAttenders(List<AuthorBriefInfoBo> attenders) {
		this.attenders = attenders;
	}
	
	
	
	
	
	
}