package com.zhuanquan.app.server.service;

import java.util.List;

import com.zhuanquan.app.common.component.sesssion.UserSession;
import com.zhuanquan.app.common.view.vo.user.OpenApiRegisterRequestVo;
import com.zhuanquan.app.common.view.vo.user.RegisterRequestVo;
import com.zhuanquan.app.common.view.vo.user.RegisterResponseVo;

/**
 * 注册
 * 
 * @author zhangjun
 *
 */
public interface RegisterService {
	
	
	
	/**
	 * 手机注册
	 * @param vo
	 * @return
	 */
	RegisterResponseVo mobileRegister(RegisterRequestVo vo); 
	
	
	/**
	 * 第三方注册
	 * @param vo
	 * @return
	 */
	RegisterResponseVo openIdRegister(OpenApiRegisterRequestVo vo);
	

	
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
	 * @param uid 登录的uid
	 * @param mobile 手机号
	 * @param verifycode 手机验证码
	 * @param persistMobileAccount 是否保留手机账号的数据 1-保留 0-不保留
	 */
	void mergeMobileAccount(long uid, String mobile, String verifycode,
			boolean persistMobileAccount);

	
	
	/**
	 * 注册引导第一步设置昵称
	 * 
	 * @param uid
	 * @param nickName
	 */
	void setNickNameOnRegisterStep1(UserSession session, String nickName);
	
	
	/**
	 * 设置关注的话题标签 以及 作品的分类
	 * @param uid 用户id
	 * @param topicTags 话题标签
	 * @param workCategries 作品分类
	 */
	void setFollowTagOnRegisterStep2(UserSession session,List<Long> topicTags,List<Long> workCategries);
	
	
	/**
	 * 设置关注的作者
	 * @param uid
	 * @param authors
	 */
	void setFollowAuthorsOnRegisterStep3(UserSession session,List<Long> authors);

	
	
}