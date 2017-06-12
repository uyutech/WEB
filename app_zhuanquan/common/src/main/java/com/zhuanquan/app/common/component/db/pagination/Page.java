package com.zhuanquan.app.common.component.db.pagination;

import java.util.List;

/**
 * page 对象
 * 
 * @author zhangjun
 *
 */
public class Page<T> {

	/**
	 * 结果数据
	 */
	private List<T> data;

	/**
	 * 总数量
	 */
	private long totalCount;


	/**
	 * 每页的数量
	 */
	private int pageLength;

	/**
	 * 当前page数
	 */
	private int pageIndex;

	public List<T> getData() {
		return data;
	}

	public void setData(List<T> data) {
		this.data = data;
	}

	public long getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(long totalCount) {
		this.totalCount = totalCount;
	}


	public int getPageLength() {
		return pageLength;
	}

	public void setPageLength(int pageLength) {
		this.pageLength = pageLength;
	}

	public int getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}

}