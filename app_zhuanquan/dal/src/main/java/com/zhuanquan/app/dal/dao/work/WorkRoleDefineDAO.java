package com.zhuanquan.app.dal.dao.work;

import java.util.List;

import com.zhuanquan.app.common.model.work.WorkRoleDefine;

/**
 * 角色定义
 * @author zhangjun
 *
 */
public interface WorkRoleDefineDAO {
	
	
	/**
	 * 查询所有的角色定义
	 * @return
	 */
	List<WorkRoleDefine> queryAll();
}