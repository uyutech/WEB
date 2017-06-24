package com.zhuanquan.app.common.view.vo.author;



import java.io.Serializable;
import java.util.List;

import com.zhuanquan.app.common.view.bo.author.AuthorBriefInfoBo;
import com.zhuanquan.app.common.view.bo.author.AuthorDynamicsBo;

/**
 * 作者动态描述
 * @author zhangjun
 *
 */
public class PageQueryAuthorDynamicsVo implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4083139742108543120L;



	private long uid;
	
	/**
	 * 作者动态信息
	 */
	private List<AuthorDynamicsBo> dynamicsList;
	
	
   /**
    * 
    */
	private int fromIndex;
	
	/**
	 * 
	 */
	private int limit;

	public long getUid() {
		return uid;
	}

	public void setUid(long uid) {
		this.uid = uid;
	}

	public List<AuthorDynamicsBo> getDynamicsList() {
		return dynamicsList;
	}

	public void setDynamicsList(List<AuthorDynamicsBo> dynamicsList) {
		this.dynamicsList = dynamicsList;
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