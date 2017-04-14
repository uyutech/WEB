package com.zhuanquan.app.common.component.cache;



public class RedisKeyBuilder {
	
	/**
	 * 登录的session key
	 * @param jSessionID
	 * @return
	 */
	public static String getLoginSessionKey(String jSessionID) {
		
		return "gw:login:sessionid:" + jSessionID;
	}
	
	
	/**
	 * 短信验证码的key
	 * @param mobile
	 * @return
	 */
	public static String getRegisterSmsVerfiyCodeKey(String mobile) {
		
		return "gw:register:mobile:"+mobile;
	}
	
	
	
	/**
	 * 登录的ip限制 key
	 * @param mobile
	 * @return
	 */
	public static String getLoginIpLimitKey(String ip) {
		
		return "gw:login:ipLimit:"+ip;
	}
	
	
	
	/**
	 * 登录的失败次数
	 * @param mobile
	 * @return
	 */
	public static String getLoginFailTimesKey(String userName) {
		
		return "gw:login:failtimes:"+userName;
	}
	
	
	/**
	 * 登录的短信验证码
	 * @param mobile
	 * @return
	 */
	public static String getLoginVerifyCodeKey(String userName) {
		
		return "gw:login:verifycode:"+userName;
	}
	
	
	/**
	 * 绑定手机号短信验证码的key
	 * @param mobile
	 * @return
	 */
	public static String getBindMobileSmsVerifyCodeKey(String mobile) {
		
		return "gw:bind:mobile:"+mobile;
	}
	
	
	/**
	 *  忘记密码时的 短信验证码的key
	 * @param mobile
	 * @return
	 */
	public static String getForgetPwdSmsVerifyCodeKey(String mobile) {
		
		return "gw:forgetpwd:mobile:"+mobile;

	}
	
	
	/**
	 *  修改密码时的 短信验证码的key
	 * @param mobile
	 * @return
	 */
	public static String getModifyPwdSmsVerifyCodeKey(String mobile) {
		
		return "gw:modifypwd:mobile:"+mobile;

	}
	
	
	/**
	 * 构建 openaccount的key
	 * @param openId
	 * @param channelType
	 * @return
	 */
	public static String buildUserOpenAccountKey(String openId,int channelType) {
				
		return "gw:openacc:"+openId+"_"+channelType;
	}
	
	/**
	 * 获取点赞的key
	 * 
	 * @param uid
	 * @return
	 */
	public static String getUserUpvoteWorkKey(long uid) {
		
		return "gw:upvotework:uid:"+uid;
	}
	
	/**
	 * 分布式锁 的key
	 * @param uid
	 * @param workId
	 * @return
	 */
	public static String getUserUpvoteWorkLockKey(long uid,long workId) {
		
		return "gw:lock:upvotework:"+uid+"_"+workId;
	}
	
	
	/**
	 * 点赞状态变动过的用户的uids的key
	 * @param uid
	 * @param workId
	 * @return
	 */
	public static String getUpvoteWorkChangedUidsKey() {
		
		return "gw:upvotework:changed:uids";
	}
	
	
	
	/**
	 * 作品被点赞的总数
	 * @param workId
	 * @return
	 */
	public static String getWorkUpvoteTotalNumKey(long workId) {
		
		return "gw:workupvot:totalnum:workid"+workId;
	}
	
}


