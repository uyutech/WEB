package com.zhuanquan.app.common.view.vo.user;


import java.io.Serializable;
import java.util.List;

import com.zhuanquan.app.common.view.bo.TagInfoBo;



/**
 * 分页查询关注的标签
 * @author zhangjun
 *
 */
public class PageQueryFollowedTagsResponseVo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3909116783257551466L;
	
	
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
	 * 查询关注的标签的基本信息
	 */
	private List<TagInfoBo> tagList;


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


	public List<TagInfoBo> getTagList() {
		return tagList;
	}


	public void setTagList(List<TagInfoBo> tagList) {
		this.tagList = tagList;
	}
	
	
	
	
	
	
	
	
	
}