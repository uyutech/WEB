package com.zhuanquan.app.server.service;

import java.util.List;

import com.zhuanquan.app.common.view.vo.user.MobileRegisterRequestVo;
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
	RegisterResponseVo mobileRegister(MobileRegisterRequestVo vo); 
	
	

	
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
	 * 注册引导:设置昵称
	 * 
	 * @param uid
	 * @param nickName
	 */
	void setNickNameOnRegister(long uid,String nickName);
	
	
	
	/**
	 *  注册引导:设置关注tag标签
	 * @param uid
	 * @param tagIds
	 */
	void setFollowTagsOnRegister(long uid,List<Long> tagIds);
	
//	
//	/**
//	 * 注册引导:设置关注的tag的领域
//	 * @param uid
//	 * @param tagIds
//	 */
//	void setFollowTagsFiledOnRegister(long uid,List<Long> tagIds);
//	
//	

	
	/**
	 * 设置关注的作者
	 * @param uid
	 * @param authors
	 */
	void setFollowAuthorsOnRegister(long uid,List<Long> authorIds);

	/**
	 *  忘记密码，重置密码
	 * @param mobile 手机号
	 * @param verifyCode 短信验证码
	 * @param password  密码
	 */
	void forgetPassword(String mobile, String verifyCode,String password);
	
	/**
	 * 修改密码
	 * @param verifyCode 验证码
	 * @param newPwd 新密码
	 */
	void modifyPassword(String verifyCode,String newPwd);
	
	/**
	 * 检查手机是否被绑定过了
	 * @param mobile
	 * @return
	 */
	int beforeBindCheck(String mobile);
	
	
	/**
	 * 发送注册短信
	 * @param mobile
	 */
	void sendRegisterSms(String mobile) ;
	
	
	
}