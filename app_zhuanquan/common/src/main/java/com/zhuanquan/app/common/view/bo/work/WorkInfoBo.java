package com.zhuanquan.app.common.view.bo.work;


import java.io.Serializable;
import java.util.List;

import com.zhuanquan.app.common.model.work.WorkAttender;
import com.zhuanquan.app.common.model.work.WorkBase;
import com.zhuanquan.app.common.model.work.WorkBaseExtend;
import com.zhuanquan.app.common.model.work.WorkContentSource;
import com.zhuanquan.app.common.model.work.WorkTagMapping;

public class WorkInfoBo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6754766016846306294L;
	
	
	
	private WorkBase base;
	
	
	private List<WorkAttender> attenderList;
	
	
	private List<WorkBaseExtend> workBaseExtendList;
	

	private List<WorkTagMapping> tagMappingList;
	
	
	private List<WorkContentSource> contentSourceList;
	
	
	
	
	
}