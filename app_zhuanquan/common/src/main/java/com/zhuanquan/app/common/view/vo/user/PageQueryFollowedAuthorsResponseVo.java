package com.zhuanquan.app.common.view.vo.user;

import java.io.Serializable;
import java.util.List;

import com.zhuanquan.app.common.view.bo.author.AuthorBaseInfoBo;
import com.zhuanquan.app.common.view.bo.author.AuthorBriefInfoBo;

/**
 * 分页查询用户关注的 作者列表信息页
 * @author zhangjun
 *
 */
public class PageQueryFollowedAuthorsResponseVo implements Serializable {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6705334103240459062L;

	/**
	 * 页数
	 */
	private int fromIndex;
	
	/**
	 * 用户id
	 */
	private long uid;
	
	/**
	 * 查多少条
	 */
	private long limit;
	
	
	/**
	 * 查询关注的作者的基本信息
	 */
	private List<AuthorBriefInfoBo> authorList;


	public int getFromIndex() {
		return fromIndex;
	}


	public void setFromIndex(int fromIndex) {
		this.fromIndex = fromIndex;
	}


	public long getUid() {
		return uid;
	}


	public void setUid(long uid) {
		this.uid = uid;
	}


	public long getLimit() {
		return limit;
	}


	public void setLimit(long limit) {
		this.limit = limit;
	}


	public List<AuthorBriefInfoBo> getAuthorList() {
		return authorList;
	}


	public void setAuthorList(List<AuthorBriefInfoBo> authorList) {
		this.authorList = authorList;
	}


	

	
}