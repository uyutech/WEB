package com.zhuanquan.app.common.component.db.pagination;


/**
 * 分页查询条件
 * @author zhangjun
 *
 */
public class Pagination {
	
	
	/**
	 *  是否需要count
	 */
	private boolean needCount = true;
	
	/**
	 * 每页的数量
	 */
	private int pageLength = 10;

	/**
	 * 当前page数
	 */
	private int pageIndex = 1;

	public boolean isNeedCount() {
		return needCount;
	}

	public void setNeedCount(boolean needCount) {
		this.needCount = needCount;
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