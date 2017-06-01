package com.zhuanquan.app.common.view.vo.author;

import java.io.Serializable;
import java.util.List;

/**
 * 推荐作者的请求参数
 * 
 * @author zhangjun
 *
 */
public class SuggestAuthorRequestVo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1687235272081827456L;


	/**
	 * 登录的渠道 0-手机登录 1-微博登录
	 * 
	 * @see com.zhuanquan.app.common.constants.LoginType
	 */
	private int channelType;

	/**
	 * 登录的uid
	 * 
	 */
	private long uid;

	/**
	 * 从多少个开始
	 */
	private int fromIndex;

	/**
	 * 
	 */
	private int limit;


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

	public int getFromIndex() {
		return fromIndex;
	}

	public void setFromIndex(int fromIndex) {
		this.fromIndex = fromIndex;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

}