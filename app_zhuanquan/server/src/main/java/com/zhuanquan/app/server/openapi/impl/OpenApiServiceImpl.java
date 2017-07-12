package com.zhuanquan.app.server.openapi.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.zhuanquan.app.common.component.cache.redis.utils.RedisHelper;
import com.zhuanquan.app.common.constants.AuthorizeType;
import com.zhuanquan.app.common.constants.user.LoginType;
import com.zhuanquan.app.common.exception.BizErrorCode;
import com.zhuanquan.app.common.exception.BizException;
import com.zhuanquan.app.common.model.common.RegisterAppointment;
import com.zhuanquan.app.common.model.common.WeiboRegisterAppointmentUserData;
import com.zhuanquan.app.common.model.common.WeiboRegisterAppointmentUserFollowData;
import com.zhuanquan.app.common.view.bo.openapi.AuthTokenBo;
import com.zhuanquan.app.common.view.bo.openapi.WeiboUserInfoBo;
import com.zhuanquan.app.dal.dao.common.WeiboRegisterAppointmentUserDataDAO;
import com.zhuanquan.app.dal.dao.common.WeiboRegisterAppointmentUserFollowDataDAO;
import com.zhuanquan.app.server.openapi.OpenApiAdapter;
import com.zhuanquan.app.server.openapi.OpenApiConnector;
import com.zhuanquan.app.server.openapi.OpenApiService;
import com.zhuanquan.app.server.service.RegisterService;

@Service
public class OpenApiServiceImpl implements OpenApiService {

	private static Logger logger = LoggerFactory.getLogger(OpenApiServiceImpl.class);

	@Resource
	private OpenApiAdapter adaptor;

	@Resource
	private RedisHelper redisHelper;

	@Resource
	private RegisterService registerService;
	
	@Resource
	private WeiboRegisterAppointmentUserFollowDataDAO weiboRegisterAppointmentUserFollowDataDAO;
	
	@Resource
	private WeiboRegisterAppointmentUserDataDAO weiboRegisterAppointmentUserDataDAO;

	@Override
	public void checkToken(String accessToken, String openId, int channelType) {

		OpenApiConnector connector = adaptor.getConnectorInstance(channelType);

		if (connector == null) {
			throw new BizException(BizErrorCode.EX_ILLEGLE_REQUEST_PARM.getCode(), "不支持的渠道类型!");
		}

		connector.checkToken(accessToken, openId);

	}

	@Override
	public List<String> getAllFollowedAuthorOpenIds(String accessToken, String openId, int channelType) {

		OpenApiConnector connector = adaptor.getConnectorInstance(channelType);

		if (connector == null) {
			throw new BizException(BizErrorCode.EX_ILLEGLE_REQUEST_PARM.getCode(), "不支持的渠道类型!");
		}

		return connector.getAllFollowedAuthorOpenIds(accessToken, openId);
	}

	@Override
	public AuthTokenBo getAuthTokenFromLoginCallBack(int channelType, String code) {

		OpenApiConnector connector = adaptor.getConnectorInstance(channelType);

		if (connector == null) {
			throw new BizException(BizErrorCode.EX_ILLEGLE_REQUEST_PARM.getCode(), "不支持的渠道类型!");
		}

		return connector.getAuthTokenFromLoginCallBack(code);
	}

	@Override
	public String getOpenApiLoginUrl(int channelType, String validateKey) {

		OpenApiConnector connector = adaptor.getConnectorInstance(channelType);

		if (connector == null) {
			throw new BizException(BizErrorCode.EX_ILLEGLE_REQUEST_PARM.getCode(), "不支持的渠道类型!");
		}
		
		return connector.getOpenApiAuthLoginUrl(validateKey);
	}

	@Override
	public void parseWeiboAuthCallback(String stat, String code) {

		logger.info("OpenApiService parseWeiboAuthCallback,[stat]:" + stat + ",[code]:" + code);

		if (StringUtils.isEmpty(stat) || StringUtils.isEmpty(code)) {
			return;
		}

		String authorizeType = redisHelper.valueGet(stat);

		// 授权码为空，已过期，或者是非法的回掉
		if (StringUtils.isEmpty(authorizeType)) {
			return;
		}

		AuthTokenBo bo = getAuthTokenFromLoginCallBack(LoginType.CHANNEL_WEIBO, code);
		if (bo == null) {
			return;
		}

		// 注册预约
		if (AuthorizeType.REG_APPOINTMENT.equals(authorizeType)) {
			//
			registerService.registerAppointment(LoginType.CHANNEL_WEIBO, bo);

		}

	}



}