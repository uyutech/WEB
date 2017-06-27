package com.zhuanquan.app.common.view.vo.author;

import java.io.Serializable;
import java.util.List;

import com.zhuanquan.app.common.view.bo.work.AuthorWorkInfoBo;
import com.zhuanquan.app.common.view.bo.work.WorkBriefInfoBo;

/**
 * 分页查询作者的 作品信息
 * @author zhangjun
 *
 */
public class AuthorWorksPageQueryVo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4882105495140151044L;
	
	
	/**
	 * 作者id
	 */
	private long authorId;
	
	/**
	 * 从哪个开始
	 */
	private int fromIndex;
	
	/**
	 * 一次查几个
	 */
	private int limit;
	
	
	/**
	 * 作者的作品列表
	 */
	private List<WorkBriefInfoBo> worksList;


	public long getAuthorId() {
		return authorId;
	}


	public void setAuthorId(long authorId) {
		this.authorId = authorId;
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


	public List<WorkBriefInfoBo> getWorksList() {
		return worksList;
	}


	public void setWorksList(List<WorkBriefInfoBo> worksList) {
		this.worksList = worksList;
	}


}