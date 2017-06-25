package com.zhuanquan.app.server.service.impl;


import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.zhuanquan.app.common.view.vo.sync.ImportWorkInfoVo;
import com.zhuanquan.app.common.view.vo.work.WorkDetailInfoVo;
import com.zhuanquan.app.server.cache.WorksCache;
import com.zhuanquan.app.server.service.TransactionService;
import com.zhuanquan.app.server.service.WorkService;


@Service
public class WorkServiceImpl implements WorkService {

	@Resource
	private TransactionService transactionService;
	
	@Resource
	private WorksCache worksCache;
	
	@Override
	public void uploadWorkInfo(ImportWorkInfoVo vo) {
	
		transactionService.doImportWork(vo);
		
	}

	@Override
	public WorkDetailInfoVo queryWorkDetail(long workId) {
		return worksCache.queryWorkDetail(workId);
	}

	
}