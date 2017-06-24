package com.zhuanquan.app.common.component.cache;

import com.zhuanquan.app.common.constants.AsyncUpdateType;

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
		
		return "gw:reg:mobile:sms:"+mobile;
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
	
	
	
	
	/**
	 * 通用的热点tag key推荐
	 * @param mobile
	 * @return
	 */
	public static String getPublicHotTagsSuggestKey() {
		
		return "gw:tag:hot:suggest:public";
	}
	

	/**
	 * 个人的个性化热点tag key推荐
	 * @param mobile
	 * @return
	 */
	public static String getPrivateHotTagsSuggestKey(long uid) {
		
		return "gw:tag:hot:suggest:private:uid:"+uid;
	}	
	
	
	
	
	/**
	 * 根据tagid批量查询
	 * @param mobile
	 * @return
	 */
	public static String getTagsIdsKey() {
		
		return "gw:tag:ids:query";
	}
	
	
	
	/**
	 * 全局的top 作者热度排行key
	 * 
	 *  zset 结构
	 * @return
	 */
	public static String getGlobalHotAuthorKey() {
		return "gw:author:hot:global";
	}
	
	
	
	/**
	 * 全局的top 作品热度排行key
	 * 
	 *  zset 结构
	 * @return
	 */
	public static String getGlobalHotWorkKey() {
		return "gw:work:hot:global";
	}
	
	
	
	
	/**
	 * 用户注册给推荐的作者
	 * @param uid
	 * @return
	 */
	public static String getSuggestHotAuthorKey(long uid) {
		return "gw:reg:sug:author:uid:"+uid;
	}
	
	
	
	/**
	 * 从第三方同步的作者的key的
	 * @return
	 */
	public static String getOpenAccountSyncFollowAuthorKey(int channelType,long uid) {
		
		return "gw:openapi:syncfollowauth:channel:"+channelType+":uid:"+uid;
	}
	
	
	
	
	
	/**
	 * author列表的base 数据
	 * @param mobile
	 * @return
	 */
	public static String getAuthorBaseKey() {
		
		return "gw:author:baseinfo";
	}
	
	
	
	/**
	 * 防止点赞过于频繁的锁
	 * @param uid
	 * @param workId
	 * @return
	 */
	public static String upvoteTooManyTimesLock(long uid,long workId) {
		
		return "gw:upvote:timeslimit:uid:"+uid+":workId="+workId;
	}
	
	
	
	/**
	 * 防止收藏过于频繁的锁
	 * @param uid
	 * @param workId
	 * @return
	 */
	public static String favTooManyTimesLock(long uid,long workId) {
		
		return "gw:fav:timeslimit:uid:"+uid+":workId="+workId;
	}
	
	
	
	
	/**
	 * 防止关注点击过于频繁的锁
	 * @param uid
	 * @param workId
	 * @return
	 */
	public static String followAuthorTooManyTimesLock(long uid,long authorId) {
		
		return "gw:follow:timeslimit:uid:"+uid+":authorId="+authorId;
	}
	
	
	/**
	 * 异步更新的list队列的key，比如点赞总数，收藏总数这种的需要异步更新的队列
	 * @param type
	 * @return
	 */
	public static String getAsyncUpateTotalNumListKey(AsyncUpdateType type) {
		
		return "gw:async:"+type.getDesc()+":changedsets";
	}
	
	/**
	 * 获取总数key，比如 点赞总数，作者粉丝总数这些
	 * @param type
	 * @param targetId
	 * @return
	 */
	public static String getTargetIdTotalNumKey(AsyncUpdateType type,long targetId){

		return "gw:"+type.getDesc()+":total:targetid:"+targetId;
	}
	
	/**
	 * hash结构的 key，用来标注属性哪个用户，比如 用户的所有的点赞记录里的 key，用户所有的收藏记录里的key
	 * @return
	 */
	public static String getIsEnableKeyByUidWithHash(AsyncUpdateType type,long uid) {
		
		return "gw:"+type.getDesc()+":isenable:uid:"+uid;
	}
	
	
	/**
	 * 对用户操作太频繁的限制，比如对某个作品不停点赞，不停的关注取消关注这些
	 * @param type
	 * @param uid
	 * @param targetId
	 * @return
	 */
	public static String getOperTooManyTimesLockKey(AsyncUpdateType type,long uid,long targetId) {
		return "gw:"+type.getDesc()+":timeslimit:uid"+uid+":targetid:"+targetId;
	}	
	

	
	
	
	
	
	
//***************************************work cache**********************************************************
	
	
	/**
	 * key-value结构 作品基础信息
	 * @param workId
	 * @return
	 */
	public static String getWorkBaseCacheKey(long workId) {
		
		
		return "gw:work:workbase:workid:"+workId;
	}
	
	
	
	/**
	 * key-value结构 作品tag缓存
	 * @param workId
	 * @return
	 */
	public static String getWorkTagsCacheKey(long workId) {
		
		
		return "gw:work:tags:workid:"+workId;
	}
	
	
	
	/**
	 * hash结构 作品扩展信心缓存
	 * @param workId
	 * @return
	 */
	public static String getWorkBaseExtendCacheKey(long workId) {
		
		
		return "gw:work:workbaseextend:workid:"+workId;
	}
	
	/**
	 * hash结构 作品参与人缓存
	 * @param workId
	 * @return
	 */
	public static String getWorkAttenderCacheKey(long workId) {
		
		
		return "gw:work:workattender:workid:"+workId;
	}
	
	
	/**
	 * hash结构 作品内容资源
	 * 
	 * @param workId
	 * @return
	 */
	public static String getWorkContentSourceCacheKey(long workId) {
		
		return "gw:work:workcontentsource:workid:"+workId;
	}
	
	
	/**
	 * hash结构 作品内容资源扩展信息
	 * 
	 * @param sourceId
	 * @return
	 */
	public static String getWorkContentSourceExtendCacheKey(long sourceId) {
		
		return "gw:work:worksourceextend:sourceid:"+sourceId;
	}
	
	
	
	
	/**
	 * author列表的base 数据
	 * @param mobile
	 * @return
	 */
	public static String getAuthorBaseKey(long authorId) {
		
		return "gw:author:baseinfo:authorId:"+authorId;
	}
	
	
	
	/**
	 * author列表的base 数据
	 * @param mobile
	 * @return
	 */
	public static String getAuthorBaseExtendKey(long authorId) {
		
		return "gw:author:baseextend:authorId:"+authorId;
	}
	
	
	
	
	/**
	 * 作者的热度信息
	 * @param mobile
	 * @return
	 */
	public static String getAuthorHotIndexKey(long authorId) {
		
		return "gw:author:hotindex:authorId:"+authorId;
	}
	
	/**
	 * 所有的第三方平台信息
	 * @param mobile
	 * @return
	 */
	public static String getPlatformDefineKey() {
		
		return "gw:author:platdefine:all";
	}
	
	
	
	
	/**
	 * 作者的第三方平台信息
	 * @param mobile
	 * @return
	 */
	public static String getAuthorOtherPlatformInfoKey(long authorId) {
		
		return "gw:author:otherplatinfo:authorId:"+authorId;
	}
	
	
	
	
	
	/**
	 * 组合基本信息
	 * @param mobile
	 * @return
	 */
	public static String getAuthorGroupBaseInfoKey(long groupId) {
		
		return "gw:author:groupbase:groupId:"+groupId;
	}
	
	
	
	
	/**
	 * discovery 作者热度排行key
	 * 
	 *  zset 结构
	 * @return
	 */
	public static String getDiscoverHotAuthorKey() {
		return "gw:author:hot:discover";
	}
	
	
	
	
	/**
	 * discovery 作品热度排行key
	 * 
	 *  zset 结构
	 * @return
	 */
	public static String getDiscoverHotWorkKey() {
		return "gw:work:hot:discover";
	}
	
	
	
	
	
	
	
	
	/**
	 * 第三方平台的信息，hash结构的key
	 * @param mobile
	 * @return
	 */
	public static String getAuthorThirdplatformInfoKey() {
		
		return "gw:authorthirdplat:all";
	}
	
	
	
	
}


