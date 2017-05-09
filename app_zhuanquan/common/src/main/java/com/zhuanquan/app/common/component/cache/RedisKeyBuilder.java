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
		
		return "gw:register:mobile:sms:"+mobile;
	}
	
	
	
	/**
	 * 登录的ip限制 key
	 * @param mobile
	 * @return
	 */
	public static String getLoginIpLimitKey(String ip) {
		
		return "gw:login:iplimit:"+ip;
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
	 * 登录的图片验证码
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
				
		return buildUserOpenAccountKey(openId,channelType+"");
	}
	
	
	/**
	 * 构建 openaccount的key
	 * @param openId
	 * @param channelType
	 * @return
	 */
	public static String buildUserOpenAccountKey(String openId,String channelType) {
				
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
	 * @return
	 */
	public static String getUpvoteWorkChangedUidsKey() {
		
		return "gw:upvotework:changed:uids";
	}
	
	/**
	 * 点赞状态变动过的用户ids，这个key是定时任务专用
	 * @return
	 */
	public static String getUpvoteWorkChangedDstUidsKey() {
		
		return "gw:upvotework:changed:dst:uids";
	}
	
	
	/**
	 * 点赞状态变动过的用户的work id的key
	 * @return
	 */
	public static String getUpvoteWorkChangedWorkIdsKey() {
		
		return "gw:upvotework:changed:workids";
	}
	
	
	
	/**
	 * 点赞状态变动过的workid，这个key是定时任务专用
	 * @return
	 */
	public static String getUpvoteWorkChangedWorkIdsDstKey() {
		
		return "gw:upvotework:changed:dst:workids";
	}
	
	
	
	
	/**
	 * 作品被点赞的总数
	 * @param workId
	 * @return
	 */
	public static String getWorkUpvoteTotalNumKey(long workId) {
		
		return "gw:workupvot:totalnum:workid:"+workId;
	}
	
	
	
	/**
	 * 作品被点赞的总数
	 * @param workId
	 * @return
	 */
	public static String getWorkUpvoteTotalNumKey(String workId) {
		
		return "gw:workupvot:totalnum:workid:"+workId;
	}
	
	
	
	
	/**
	 * 执行点赞数异步更新的task job的key
	 * @param workId
	 * @return
	 */
	public static String getExecUpvoteTaskLock() {
		
		return "gw:lock:upvoteworktask:exec";

	}
	
	
	
	/**
	 * 用户收藏的 作品的 redis  key
	 * @param workId
	 * @return
	 */
	public static String getUserFavKey(long uid) {
		
		return "gw:fav:all:uid:"+uid;

	}
}


