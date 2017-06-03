package com.zhuanquan.app.common.view.vo.author;


import java.io.Serializable;

import com.zhuanquan.app.common.view.bo.author.AuthorBaseInfoBo;

public class SuggestAuthorUnit extends AuthorBaseInfoBo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 670077217836892670L;
	

	
	/**
	 * 默认就是关注的，根据第三方登录同步过来的关注列表，如果作者也在我们平台，默认就是关注的
	 */
	private int isDefaultFollow;


	public int getIsDefaultFollow() {
		return isDefaultFollow;
	}

	public void setIsDefaultFollow(int isDefaultFollow) {
		this.isDefaultFollow = isDefaultFollow;
	}
	

}