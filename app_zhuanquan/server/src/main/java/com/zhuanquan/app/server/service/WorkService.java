package com.zhuanquan.app.server.service;

import com.zhuanquan.app.common.view.vo.sync.ImportWorkInfoVo;

/**
 * 作品服务
 * @author zhangjun
 *
 */
public interface WorkService {
	
	
	/**
	 * 导入作品信息
	 * @param vo
	 */
	void uploadWorkInfo(ImportWorkInfoVo vo); 
	
	
}