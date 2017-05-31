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
	private List<SuggestAuthorUnit> dataList;
	
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


	public List<SuggestAuthorUnit> getDataList() {
		return dataList;
	}

	public void setDataList(List<SuggestAuthorUnit> dataList) {
		this.dataList = dataList;
	}

	public long getUid() {
		return uid;
	}

	public void setUid(long uid) {
		this.uid = uid;
	}

	public int getFromIndex() {
		return fromIndex;
	}

	public void setFromIndex(int fromIndex) {
		this.fromIndex = fromIndex;
	}

	public long getLimit() {
		return limit;
	}

	public void setLimit(long limit) {
		this.limit = limit;
	}
	
	
	

}