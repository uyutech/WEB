package com.zhuanquan.app.common.view.vo.discovery;

import java.io.Serializable;
import java.util.List;
/**
 * 
 * @author zhangjun
 *
 */
public class DiscoveryPageQueryRequest implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8535148620243250093L;
	
	
	/**
	 * 用户id
	 */
	private long uid;
	
	/**
	 * sourcetype查询条件
	 */
	private List<Integer> sourceTypes;
	
	/**
	 * tag查询条件
	 */
	private List<Long> tags;

	
	/**
	 * 起始index
	 */
	private int fromIndex;
	
	/**
	 * 分页限制
	 */
	private int limit;
	
	
	
	
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

	public long getUid() {
		return uid;
	}

	public void setUid(long uid) {
		this.uid = uid;
	}

	public List<Integer> getSourceTypes() {
		return sourceTypes;
	}

	public void setSourceTypes(List<Integer> sourceTypes) {
		this.sourceTypes = sourceTypes;
	}

	public List<Long> getTags() {
		return tags;
	}

	public void setTags(List<Long> tags) {
		this.tags = tags;
	}
	
	
	
	
	
	
	
}