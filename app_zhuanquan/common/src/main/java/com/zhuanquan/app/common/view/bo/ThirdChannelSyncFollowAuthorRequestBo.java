package com.zhuanquan.app.common.view.bo;

import java.io.Serializable;

/**
 * 同步第三方关注的作者列表的请求，用作在注册的时候推荐
 * 
 * @author zhangjun
 *
 */
public class ThirdChannelSyncFollowAuthorRequestBo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4904258808232891502L;

	/**
	 * 用户id
	 */
	private long uid;

	/**
	 * token
	 */
	private String token;

	/**
	 * 第三方openid
	 */
	private String openId;

	/**
	 * 渠道类型 1-微博
	 * 
	 * @see com.zhuanquan.app.common.constants.LoginType
	 */
	private int channelType;

	public long getUid() {
		return uid;
	}

	public void setUid(long uid) {
		this.uid = uid;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public int getChannelType() {
		return channelType;
	}

	public void setChannelType(int channelType) {
		this.channelType = channelType;
	}

}