package com.zhuanquan.app.server.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.framework.core.error.exception.BizException;
import com.zhuanquan.app.common.constants.ChannelType;
import com.zhuanquan.app.common.exception.BizErrorCode;
import com.zhuanquan.app.common.utils.ApiConnector;
import com.zhuanquan.app.server.service.OpenApiService;

@Service
public class OpenApiServiceImpl implements OpenApiService {

	private static Logger logger = LoggerFactory.getLogger(OpenApiServiceImpl.class);

	// 微博url，检测token的uid是哪个
	private final String WEIBO_TOKEN_CHECK_URL = "https://api.weibo.com/oauth2/get_token_info";

	@Override
	public void checkToken(String accessToken, String openId, int channelType) {

		switch (channelType) {
		case ChannelType.CHANNEL_WEIBO:

			checkWeiboToken(accessToken, openId);
			return;

		default:

			throw new BizException(BizErrorCode.EX_ILLEGLE_REQUEST_PARM.getCode());

		}

	}

	/**
	 * 检测微博token是否合法
	 * 
	 * https://api.weibo.com/oauth2/get_token_info?access_token=2.
	 * 00TiLlpB0PERQyad7d047a15o8EcVB
	 * 
	 * 返回 {"uid":1680972425,"appkey":"890459019","scope":null,"create_at":
	 * 1491031220,"expire_in":157357699}
	 * 
	 * 
	 * @param accessToken
	 * @param openId
	 */
	private void checkWeiboToken(String accessToken, String openId) {

		try {

			List<NameValuePair> pairs = new ArrayList<NameValuePair>();

			pairs.add(new BasicNameValuePair("access_token", accessToken));

			String str = ApiConnector.post(WEIBO_TOKEN_CHECK_URL, pairs);

			logger.info("OpenApiServiceImpl checkWeiboToken :str = " + (str == null ? "null" : str));

			if (!StringUtils.isEmpty(str)) {
				
				JSONObject obj = JSONObject.parseObject(str);
				if (obj != null) {
					String uid = obj.getString("uid");
					if (openId.equals(uid)) {
						return;
					}

				}
			}

			throw new BizException(BizErrorCode.EX_ILLEGLE_REQUEST_PARM.getCode());

		} catch (Exception e) {

			logger.error(e.getMessage(), e);
			throw new BizException(BizErrorCode.EX_SYSTEM_ERROR.getCode());
		}

	}

}