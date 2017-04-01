package com.zhuanquan.app.server.service;

import com.zhuanquan.app.common.view.vo.user.RegisterRequestVo;
import com.zhuanquan.app.common.view.vo.user.RegisterResponseVo;

/**
 * 事务处理的service
 * @author zhangjun
 *
 */
public interface TransactionService {
	
	/**
	 * 手机注册
	 * @param vo
	 * @return
	 */
	RegisterResponseVo registerMobile(RegisterRequestVo vo) ;
	
}