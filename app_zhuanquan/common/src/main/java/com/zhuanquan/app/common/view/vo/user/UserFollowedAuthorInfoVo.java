package com.zhuanquan.app.common.view.vo.user;

import java.io.Serializable;
import java.util.List;

import com.zhuanquan.app.common.view.bo.author.AuthorBaseInfoBo;

/**
 * 用户关注的作者信息
 * @author zhangjun
 *
 */
public class UserFollowedAuthorInfoVo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2353561818548588235L;
	
	
	
	/**
	 * 用户id
	 */
	private long uid;
	
	
	/**
	 * 关注的作者分组
	 */
	private List<AuthorBaseInfoBo> followList;


	public long getUid() {
		return uid;
	}


	public void setUid(long uid) {
		this.uid = uid;
	}


	public List<AuthorBaseInfoBo> getFollowList() {
		return followList;
	}


	public void setFollowList(List<AuthorBaseInfoBo> followList) {
		this.followList = followList;
	}
	

	
}