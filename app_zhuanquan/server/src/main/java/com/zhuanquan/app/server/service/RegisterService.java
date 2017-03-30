package com.zhuanquan.app.server.service;

import com.zhuanquan.app.server.view.ApiResponse;
import com.zhuanquan.app.server.view.user.RegisterRequestVo;
import com.zhuanquan.app.server.view.user.RegisterResponseVo;

/**
 * 注册
 * 
 * @author zhangjun
 *
 */
public interface RegisterService {
	
	
	
	/**
	 * 注册
	 * @param vo
	 * @return
	 */
	RegisterResponseVo register(RegisterRequestVo vo); 
	
	
	/**
	 * 如果手机号没有被注册过，给用户绑定手机号
	 * @param uid 用户id
	 * @param mobile 手机号
	 * @param password 设置密码
	 * @param verifycode 绑定前的验证码
	 */
	void bindUnregisterMobile(long uid,String mobile, String password,String verifycode) ;
	

	
	/**
	 * 如果手机号已经被注册过，用户需要选择当前保留哪个账号的数据.
	 * @param openId 第三方openid
	 * @param channelType 第三方渠道类型
	 * @param mobile 手机号
	 * @param verifycode 手机验证码
	 * @param persistMobileAccount 是否保留手机账号的数据 1-保留 0-不保留
	 */
	void mergeMobileAccount(String openId,int channelType, String mobile, String verifycode,
			boolean persistMobileAccount);

	
	
}