package com.zhuanquan.app.server.openapi.internel.weibo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.zhuanquan.app.common.constants.LoginType;
import com.zhuanquan.app.common.exception.BizErrorCode;
import com.zhuanquan.app.common.exception.BizException;
import com.zhuanquan.app.common.utils.HttpUtil;
import com.zhuanquan.app.server.openapi.OpenApiConnector;

@Component
public class WeiboOpenApi implements OpenApiConnector {

	private static Logger logger = LoggerFactory.getLogger(WeiboOpenApi.class);

	// 微博url，检测token的uid是哪个
	private final String WEIBO_TOKEN_CHECK_URL = "https://api.weibo.com/oauth2/get_token_info";

	private final String WEIBO_GET_FOLLOW_UIDS = "https://api.weibo.com/2/friendships/friends/ids.json";

	@Override
	public void checkToken(String accessToken, String openId) {
		try {

			Map<String, String> parmMap = new HashMap<String, String>();
			parmMap.put("access_token", accessToken);

			String str = HttpUtil.sendPostSSLRequest(WEIBO_TOKEN_CHECK_URL, parmMap);

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

			throw new BizException(BizErrorCode.EX_OPEN_ACCOUNT_WEIBO_TOKEN_VALIDATE_ERROR.getCode());

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new BizException(BizErrorCode.EX_OPEN_ACCOUNT_WEIBO_TOKEN_VALIDATE_ERROR.getCode());
		}

	}

	/**
	 * http://open.weibo.com/wiki/2/friendships/friends/ids 获取关注的用户id
	 * 
	 */
	@Override
	public List<String> getAllFollowedAuthorOpenIds(String accessToken, String openId) {

		//
		// 必选 类型及范围 说明
		// access_token true string 采用OAuth授权方式为必填参数，OAuth授权后获得。
		// uid false int64 需要查询的用户UID。
		// screen_name false string 需要查询的用户昵称。
		// count false int 单页返回的记录条数，默认为5，最大不超过5。
		// cursor false int
		// 返回结果的游标，下一页用返回值里的next_cursor，上一页用previous_cursor，默认为0。

		int count = 5;

		int cursor = 0;

		//
		// {
		// "ids": [
		// 1726475555,
		// 1404376560,
		// 1233616152,
		// ...
		// ],
		// "next_cursor": 5,
		// "previous_cursor": 0,
		// "total_number": 668
		// }
		//
		List<String> openIds = new ArrayList<String>();

		try {
			while (true) {

				String url = generateGetFollowUidsUrl(accessToken, cursor, count);

				String str = HttpUtil.sendGetRequest(url, "UTF-8");

				logger.info("OpenApiServiceImpl getAllFollowedAuthorOpenIds :url = " + url + ",response=" + str);

				// 网络异常，直接中断查询
				if (StringUtils.isEmpty(str)) {
					break;
				}

				JSONObject obj = JSONObject.parseObject(str);
				// 数据异常，直接中断查询
				if (obj == null) {
					break;
				}

				String idsStr = obj.getString("ids");

				if (StringUtils.isEmpty(idsStr)) {
					break;
				}

				List<Long> idsList = JSONObject.parseArray(idsStr, Long.class);

				if (CollectionUtils.isEmpty(idsList)) {
					break;
				}

				// 添加到返回结果
				for (Long uid : idsList) {
					openIds.add(uid.toString());
				}

				// 查询的结果小于预期的结果，说明不需要继续查下去了，反之，cursor指向下一批
				if (idsList.size() < count) {
					break;
				} else {

					String nextCursor = obj.getString("next_cursor");

					// cursor 指向下一个
					cursor = Integer.parseInt(nextCursor);
				}

			}

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new BizException(BizErrorCode.EX_OPEN_ACCOUNT_WEIBO_GET_FOLLOW_UIDS_ERROR.getCode());
		}

		return openIds;
	}

	@Override
	public int getChannelType() {

		return LoginType.CHANNEL_WEIBO;
	}

	/**
	 * 拼装url，获取关注列表url
	 * 
	 * @param token
	 * @param cursor
	 * @param count
	 * @return
	 */
	private String generateGetFollowUidsUrl(String token, int cursor, int count) {

		return WEIBO_GET_FOLLOW_UIDS + "?" + "access_token=" + token + "&count=" + count + "&cursor=" + cursor;
	}
}