package com.zhuanquan.app.server.aop;

import org.springframework.stereotype.Service;

import com.framework.core.web.common.biz.BizDataFetcher;

@Service
public class Test implements  BizDataFetcher {

	@Override
	public Long getCurTenantId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long getCurrentLoginUserId() {
		// TODO Auto-generated method stub
		return null;
	}
	
}