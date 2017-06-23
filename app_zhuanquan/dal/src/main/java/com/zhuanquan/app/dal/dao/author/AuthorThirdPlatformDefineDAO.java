package com.zhuanquan.app.dal.dao.author;

import java.util.List;

import com.zhuanquan.app.common.model.author.AuthorThirdPlatformDefine;

/**
 * 第三方平台定义
 * @author zhangjun
 *
 */
public interface AuthorThirdPlatformDefineDAO {
	
	
	
	/**
	 * 查询所有的信息
	 * @return
	 */
	List<AuthorThirdPlatformDefine> queryAllInfo();
	
	
}