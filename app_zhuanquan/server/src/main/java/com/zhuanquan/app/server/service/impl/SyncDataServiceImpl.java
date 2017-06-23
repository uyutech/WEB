package com.zhuanquan.app.server.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.zhuanquan.app.common.view.vo.sync.ImportWorkInfoVo;
import com.zhuanquan.app.server.service.SyncDataService;
import com.zhuanquan.app.server.service.WorkService;


@Service
public class SyncDataServiceImpl implements SyncDataService {

	
	@Resource
	private WorkService workService;
	
	
	@Override
	public void importWorkInfo(ImportWorkInfoVo vo) {
		
		
		
	}
	

}