package com.zhuanquan.app.server.service;

import com.zhuanquan.app.common.model.user.UserProfile;
import com.zhuanquan.app.common.view.vo.user.LoginByOpenIdRequestVo;
import com.zhuanquan.app.common.view.vo.user.MobileRegisterRequestVo;
import com.zhuanquan.app.common.view.vo.user.RegisterResponseVo;

/**
 * 事务处理的service
 * 
 * @author zhangjun
 *
 */
public interface TransactionService {

	/**
	 * 手机注册
	 * 
	 * @param vo
	 * @return
	 */
	RegisterResponseVo registerMobile(MobileRegisterRequestVo vo);

	/**
	 * 第三方普通账户注册，非大v注册，大v都是预先注册好的
	 * 
	 * @return
	 */
	UserProfile normalOpenAccountRegister(LoginByOpenIdRequestVo vo);

}