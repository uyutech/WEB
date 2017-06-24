package com.zhuanquan.app.common.view.vo.discovery;


import java.io.Serializable;
import java.util.List;

import com.zhuanquan.app.common.view.bo.work.WorkSourceTypeInfoBo;


/**
 * 发现页 ，推荐的搜索条件里的 多媒体资源类型
 * 。
 * @author zhangjun
 *
 */
public class DiscoverySuggestSourceTypeVo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7241158020195749049L;
	
	
	/**
	 * 发现页 推荐的可选内容类型
	 */
	private List<WorkSourceTypeInfoBo> sourceType;


	public List<WorkSourceTypeInfoBo> getSourceType() {
		return sourceType;
	}


	public void setSourceType(List<WorkSourceTypeInfoBo> sourceType) {
		this.sourceType = sourceType;
	}
	
	
	
	
	
	
}