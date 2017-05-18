package com.zhuanquan.app.common.view.vo.author;

import java.io.Serializable;
import java.util.List;

/**
 * 推荐作者的请求参数
 * @author zhangjun
 *
 */
public class SuggestAuthorRequestVo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1687235272081827456L;
	
	
	/**
	 * 第三方已经关注的作者的openid
	 */
	private List<String> followOpenIds;
	
	/**
	 * 登录的渠道  0-手机登录 1-微博登录
	 * @see com.zhuanquan.app.common.constants.LoginType
	 */
	private int channelType;
	
	
	/**
	 * 登录的uid
	 * 
	 */
	private long uid;
	
	/**
	 * 页数
	 */
	private int page;


	public List<String> getFollowOpenIds() {
		return followOpenIds;
	}


	public void setFollowOpenIds(List<String> followOpenIds) {
		this.followOpenIds = followOpenIds;
	}


	public int getChannelType() {
		return channelType;
	}


	public void setChannelType(int channelType) {
		this.channelType = channelType;
	}


	public long getUid() {
		return uid;
	}


	public void setUid(long uid) {
		this.uid = uid;
	}


	public int getPage() {
		return page;
	}


	public void setPage(int page) {
		this.page = page;
	}
	
	
}