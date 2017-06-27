package com.zhuanquan.app.common.view.vo.author;



import java.io.Serializable;
import java.util.List;

import com.zhuanquan.app.common.view.bo.author.AuthorPartnerInfoBo;

/**
 * 作者的人际关系
 * @author zhangjun
 *
 */
public class AuthorRelationshipPageQueryVo implements Serializable {
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 7890166502276134598L;


	/**
	 * 作者id
	 */
	private Long authorId;
	
	
	/**
	 * 从哪个开始
	 */
	private int fromIndex;
	
	/**
	 * 一次查几个
	 */
	private int limit;
	
	
	/**
	 * 合作信息
	 */
	private List<AuthorPartnerInfoBo> partnerList;


	public Long getAuthorId() {
		return authorId;
	}


	public void setAuthorId(Long authorId) {
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


	public List<AuthorPartnerInfoBo> getPartnerList() {
		return partnerList;
	}


	public void setPartnerList(List<AuthorPartnerInfoBo> partnerList) {
		this.partnerList = partnerList;
	}




}