package com.zhuanquan.app.server.openapi.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.zhuanquan.app.common.exception.BizErrorCode;
import com.zhuanquan.app.common.exception.BizException;
import com.zhuanquan.app.server.openapi.OpenApiAdapter;
import com.zhuanquan.app.server.openapi.OpenApiConnector;
import com.zhuanquan.app.server.openapi.OpenApiService;

@Service
public class OpenApiServiceImpl implements OpenApiService {

	private static Logger logger = LoggerFactory.getLogger(OpenApiServiceImpl.class);

	private OpenApiAdapter adaptor;

	@Override
	public void checkToken(String accessToken, String openId, int channelType) {

		OpenApiConnector connector = adaptor.getConnectorInstance(channelType);
		
		if(connector == null) {
			throw new BizException(BizErrorCode.EX_ILLEGLE_REQUEST_PARM.getCode(), "不支持的渠道类型!");
		}

		connector.checkToken(accessToken, openId);
		
	}


	@Override
	public List<String> getAllFollowedAuthorOpenIds(String accessToken, String openId, int channelType) {
		
		
		return null;
	}

}