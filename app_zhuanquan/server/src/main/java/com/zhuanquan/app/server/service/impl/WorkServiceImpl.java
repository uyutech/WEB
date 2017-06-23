package com.zhuanquan.app.server.service.impl;


import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.zhuanquan.app.common.view.vo.sync.ImportWorkInfoVo;

import com.zhuanquan.app.server.service.TransactionService;
import com.zhuanquan.app.server.service.WorkService;


@Service
public class WorkServiceImpl implements WorkService {

	@Resource
	private TransactionService transactionService;
	
	@Override
	public void uploadWorkInfo(ImportWorkInfoVo vo) {
	
		transactionService.doImportWork(vo);
		
	}

	
}