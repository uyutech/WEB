package com.zhuanquan.app.common.view.vo.user;


import java.io.Serializable;
import java.util.List;

import com.zhuanquan.app.common.view.bo.author.AuthorBaseInfoBo;

/**
 * 查询用户关注的作者的返回值
 * @author zhangjun
 *
 */
public class QueryUserFollowAuthorsResponseVo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3521604663228438722L;
	
	/**
	 * 用户id
	 */
	private long uid;
	
	
    /**
     *  关注的作者
     */
	private List<AuthorBaseInfoBo> followAuthors;


	public long getUid() {
		return uid;
	}


	public void setUid(long uid) {
		this.uid = uid;
	}


	public List<AuthorBaseInfoBo> getFollowAuthors() {
		return followAuthors;
	}


	public void setFollowAuthors(List<AuthorBaseInfoBo> followAuthors) {
		this.followAuthors = followAuthors;
	}
	
	
	
	
	
}