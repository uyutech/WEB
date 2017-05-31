package com.zhuanquan.app.common.view.vo.author;

import java.io.Serializable;
import java.util.List;

public class SuggestTagResponseVo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3686953644053087472L;

	/**
	 * uid
	 */
	private long uid;

	/**
	 * 这一批从第几个开始
	 */
	private int fromIndex;

	/**
	 * tag列表
	 */
	private List<SuggestTagVo> dataList;
	
	/**
	 * 限制最多查多少条
	 */
	private int limit;

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

	public List<SuggestTagVo> getDataList() {
		return dataList;
	}

	public void setDataList(List<SuggestTagVo> dataList) {
		this.dataList = dataList;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}
	
	

}