package com.zhuanquan.app.common.view.vo.work;


import java.io.Serializable;
import java.util.List;

import com.zhuanquan.app.common.view.bo.work.WorkContentSourceBriefInfoBo;

/**
 * 以 多媒体资源的分类为视图，展示作品包含的多媒体资源
 * 比如，以 视频为 维度，以图片为维度这种
 * @author zhangjun
 *
 */
public class WorkMediaSourceCategoryView implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2245154748397710977L;
	
	
	/**
	 * 资源分类
	 */
	private int sourceCategory;
	
	
	/**
	 * 资源列表
	 */
	private List<WorkContentSourceBriefInfoBo> sourceList;


	public int getSourceCategory() {
		return sourceCategory;
	}


	public void setSourceCategory(int sourceCategory) {
		this.sourceCategory = sourceCategory;
	}


	public List<WorkContentSourceBriefInfoBo> getSourceList() {
		return sourceList;
	}


	public void setSourceList(List<WorkContentSourceBriefInfoBo> sourceList) {
		this.sourceList = sourceList;
	}
	
	
	
	
	
}