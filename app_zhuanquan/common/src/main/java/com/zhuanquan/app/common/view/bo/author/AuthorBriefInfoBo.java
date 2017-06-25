package com.zhuanquan.app.common.view.bo.author;

import java.io.Serializable;

import com.zhuanquan.app.common.model.author.AuthorBase;


/**
 * 作者该要信息，作者id，作者名，作者缩略图头像
 * @author zhangjun
 *
 */
public class AuthorBriefInfoBo  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1109561941162569509L;
	
	/**
	 * 作者id
	 */
	private long authorId;
	
	/**
	 * 作者名
	 */
	private String authorName;
	
	/**
	 * 头像缩略图
	 */
	private String headSnapshot;

	public long getAuthorId() {
		return authorId;
	}

	public void setAuthorId(long authorId) {
		this.authorId = authorId;
	}

	public String getAuthorName() {
		return authorName;
	}

	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}

	public String getHeadSnapshot() {
		return headSnapshot;
	}

	public void setHeadSnapshot(String headSnapshot) {
		this.headSnapshot = headSnapshot;
	}
	
	
	/**
	 * 对象转化
	 * @param base
	 * @return
	 */
	public static AuthorBriefInfoBo getObjectFromAuthorBase(AuthorBaseInfoBo base) {
		

		AuthorBriefInfoBo bo = new AuthorBriefInfoBo();
		bo.setAuthorId(base.getAuthorId());
		bo.setAuthorName(base.getAuthorName());

		bo.setHeadSnapshot(base.getHeadUrl());
		
		return bo;
	}
	
}