package com.zhuanquan.app.server.service;

import com.zhuanquan.app.common.view.vo.sync.ImportWorkInfoVo;

/**
 * 数据同步服务
 * @author zhangjun
 *
 */
public interface SyncDataService {
	
	/**
	 * 导入作品信息
	 * @param vo
	 */
	void importWorkInfo(ImportWorkInfoVo vo); 
	
	
	
	
	
}