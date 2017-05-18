package com.zhuanquan.app.common.view.vo.author;

import java.io.Serializable;
import java.util.List;

/**
 * 推荐作者信息
 * 
 * @author zhangjun
 *
 */
public class SuggestAuthorResponseVo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -894898949202472548L;

	/**
	 * 推荐的author
	 */
	private List<SuggestAuthorUnit> suggestAuthors;
	
	/**
	 * 页数
	 */
	private int page;
	
	/**
	 * 用户id
	 */
	private long uid;

	public List<SuggestAuthorUnit> getSuggestAuthors() {
		return suggestAuthors;
	}

	public void setSuggestAuthors(List<SuggestAuthorUnit> suggestAuthors) {
		this.suggestAuthors = suggestAuthors;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public long getUid() {
		return uid;
	}

	public void setUid(long uid) {
		this.uid = uid;
	}
	

}