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
import org.springframework.util.Assert;

import com.alibaba.fastjson.JSONObject;
import com.zhuanquan.app.common.constants.user.LoginType;
import com.zhuanquan.app.common.exception.BizErrorCode;
import com.zhuanquan.app.common.exception.BizException;
import com.zhuanquan.app.common.utils.HttpUtil;
import com.zhuanquan.app.common.view.bo.openapi.AuthTokenBo;
import com.zhuanquan.app.common.view.bo.openapi.WeiboUserInfoBo;
import com.zhuanquan.app.server.openapi.OpenApiConnector;

@Component
public class WeiboOpenApi implements OpenApiConnector {

	private static Logger logger = LoggerFactory.getLogger(WeiboOpenApi.class);

	// 微博url，检测token的uid是哪个
	private final String WEIBO_TOKEN_CHECK_URL = "https://api.weibo.com/oauth2/get_token_info";

	/**
	 * 关注关注用户id
	 */
	private final String WEIBO_GET_FOLLOW_UIDS = "https://api.weibo.com/2/friendships/friends/ids.json";

	/**
	 * 授权登录
	 */
	private final String WEIBO_AUTHORIZE = "https://api.weibo.com/oauth2/authorize";

	/**
	 * 获取accesstoken
	 */
	private final String WEIBO_GET_AUTH_TOKEN = "https://api.weibo.com/oauth2/access_token";

	/**
	 * 用户信息
	 */
	private static final String WEIBO_USER_SHOW = "https://api.weibo.com/2/users/show.json";

	private static final String appKey = "4139489763";

	/**
	 * 
	 */
	private static final String appSecurity = "1152af67bb15530c50d91728f86c43df";

	/**
	 * 回掉
	 */
	private static final String redirctUrl = "http://rhymesland.com/openapi/weiboAuthCallback.action";

	private static final String grant_type_authorization_code = "authorization_code";

	/**
	 * 
	 */
	private static final String auth_response_type = "code";

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

		} catch (BizException e) {
			throw e;
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

	/**
	 * 
	 * 
	 * 
	 */
	@Override
	public AuthTokenBo getAuthTokenFromLoginCallBack(String code) {

		// client_id=4139489763&client_secret=1152af67bb15530c50d91728f86c43df&redirect_uri=http://army8735.me/zhuanquan/login&code=d2408f5037ffb2bdd0ac3a55c81e5f61&grant_type=authorization_code

		try {
			// Map<String, String> parmMap = new HashMap<String, String>();
			// parmMap.put("client_id", appKey);
			// parmMap.put("client_secret", appSecurity);
			// parmMap.put("redirect_uri", redirctUrl);
			// parmMap.put("grant_type", grant_type_authorization_code);
			// parmMap.put("code", code);

			StringBuilder sb = new StringBuilder();
			sb.append("client_id=").append(appKey).append("&client_secret=").append(appSecurity)
					.append("&redirect_uri=").append(redirctUrl).append("&grant_type=")
					.append(grant_type_authorization_code).append("&code=").append(code);

			String str = HttpUtil.sendPostRequest(WEIBO_GET_AUTH_TOKEN, sb.toString(), true);
			// String str = HttpUtil.sendPostSSLRequest(WEIBO_GET_AUTH_TOKEN,
			// parmMap);

			// {"access_token":"2.00Snu4JCv4sIWEba52cf883dng4v8B","remind_in":"132051","expires_in":132051,"uid":"1970952552"}

			logger.info("OpenApiServiceImpl getAuthTokenFromLoginCallBack :str = " + (str == null ? "null" : str)
					+ ",[code]:" + code);

			if (!StringUtils.isEmpty(str)) {

				JSONObject obj = JSONObject.parseObject(str);

				if (obj != null) {
					String authToken = obj.getString("access_token");

					String uid = obj.getString("uid");

					if (StringUtils.isNotEmpty(authToken) && StringUtils.isNotEmpty(uid)) {
						AuthTokenBo bo = new AuthTokenBo();

						bo.setAuthToken(authToken);
						bo.setOpenId(uid);

						return bo;
					}

				}
			}

			throw new BizException(BizErrorCode.EX_OPEN_ACCOUNT_WEIBO_GET_AUTH_TOKEN_ERROR.getCode());

		} catch (BizException e) {
			throw e;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new BizException(BizErrorCode.EX_OPEN_ACCOUNT_WEIBO_GET_AUTH_TOKEN_ERROR.getCode());
		}

	}

	@Override
	public String getOpenApiAuthLoginUrl(String validateKey) {

		// https://api.weibo.com/oauth2/authorize?client_id=4139489763&redirect_uri=http://army8735.me/zhuanquan/login&response_type=code&state=123456

		StringBuilder sb = new StringBuilder().append(WEIBO_AUTHORIZE).append("?").append("client_id=").append(appKey)
				.append("&redirect_uri=").append(redirctUrl).append("&response_type=").append(auth_response_type)
				.append("&state=").append(validateKey);

		return sb.toString();
	}

	@Override
	public WeiboUserInfoBo getWeiboUserBaseInfo(String accessToken, String openUid) {

		// https://api.weibo.com/2/users/show.json?access_token=2.00Snu4JCv4sIWEba52cf883dng4v8B&uid=1970952552
		try {
			StringBuilder sb = new StringBuilder().append(WEIBO_USER_SHOW).append("?").append("access_token=")
					.append(accessToken).append("&uid=").append(openUid);

			//
			String response = HttpUtil.sendGetRequest(sb.toString(), "UTF-8");

			logger.info("OpenApiServiceImpl getUserBaseInfo :" + ",[accessToken]:" + accessToken + ",[openUid]:"
					+ openUid + ",[response] = " + (response == null ? "null" : response));

			if (!StringUtils.isEmpty(response)) {

				JSONObject obj = JSONObject.parseObject(response);

				if (obj != null) {

					WeiboUserInfoBo bo = new WeiboUserInfoBo();

					String city = (String) parseUserAttrVal(obj, "city");
					if (StringUtils.isNotEmpty(city)) {
						bo.setCity(Integer.parseInt(city));
					}

					String created_at = (String) parseUserAttrVal(obj, "created_at");
					if (StringUtils.isNotEmpty(created_at)) {

						bo.setCreated_at(created_at);
					}

					String description = (String) parseUserAttrVal(obj, "description");

					if (StringUtils.isNotEmpty(description)) {

						bo.setDescription(description);
					}

					String domain = (String) parseUserAttrVal(obj, "domain");
					if (StringUtils.isNotEmpty(domain))
						bo.setDomain(domain);

					String favourites_count = (String) parseUserAttrVal(obj, "favourites_count");

					if (StringUtils.isNotEmpty(favourites_count)) {
						bo.setFavourites_count(Integer.parseInt(favourites_count));
					}

					String followers_count = (String) parseUserAttrVal(obj, "followers_count");
					if (StringUtils.isNotEmpty(followers_count)) {

						bo.setFollowers_count(Integer.parseInt(followers_count));
					}

					String friends_count = (String) parseUserAttrVal(obj, "friends_count");
					if (StringUtils.isNotEmpty(friends_count)) {

						bo.setFriends_count(Integer.parseInt(friends_count));
					}

					String statuses_count = (String) parseUserAttrVal(obj, "statuses_count");
					if (StringUtils.isNotEmpty(statuses_count)) {

						bo.setStatuses_count(Integer.parseInt(statuses_count));
					}

					String gender = (String) parseUserAttrVal(obj, "gender");
					if (StringUtils.isNotEmpty(gender)) {

						bo.setGender(gender);
					}

					String location = (String) parseUserAttrVal(obj, "location");
					if (StringUtils.isNotEmpty(location)) {

						bo.setLocation(location);
					}

					String name = (String) parseUserAttrVal(obj, "name");
					if (StringUtils.isNotEmpty(name)) {

						bo.setName(name);
					}

					String profile_url = (String) parseUserAttrVal(obj, "profile_url");
					if (StringUtils.isNotEmpty(profile_url)) {

						bo.setProfile_url(profile_url);
					}

					String profile_image_url = (String) parseUserAttrVal(obj, "profile_image_url");
					if (StringUtils.isNotEmpty(profile_image_url)) {

						bo.setProfile_image_url(profile_image_url);
					}					
					
					
					
					String province = (String) parseUserAttrVal(obj, "province");
					if (StringUtils.isNotEmpty(province)) {

						bo.setProvince(Integer.parseInt(province));
					}

					String screen_name = (String) parseUserAttrVal(obj, "screen_name");
					if (StringUtils.isNotEmpty(screen_name)) {

						bo.setScreen_name(screen_name);
					}

					String url = (String) parseUserAttrVal(obj, "url");
					if (StringUtils.isNotEmpty(url)) {

						bo.setUrl(url);
					}

					String weihao = (String) parseUserAttrVal(obj, "weihao");
					if (StringUtils.isNotEmpty(weihao)) {

						bo.setWeihao(weihao);
					}

					return bo;

				}
			}

			throw new BizException(BizErrorCode.EX_OPEN_ACCOUNT_WEIBO_GET_USER_BASE_INFO_ERROR.getCode());

		} catch (BizException e) {
			logger.error("OpenApiServiceImpl 获取用户信息失败!access_token:" + accessToken + ",uid=" + openUid, e);

			throw e;
		} catch (Exception e) {

			logger.error("OpenApiServiceImpl 获取用户信息非预期失败!access_token:" + accessToken + ",uid=" + openUid, e);
			throw new BizException(BizErrorCode.EX_OPEN_ACCOUNT_WEIBO_GET_USER_BASE_INFO_ERROR.getCode(), e);

		}
	}

	/**
	 * 解析string变量值
	 * 
	 * @param obj
	 * @param targetAttr
	 * @return
	 */
	private Object parseUserAttrVal(JSONObject obj, String targetAttr) {

		if (obj == null) {
			return null;
		}

		Object val = obj.getString(targetAttr);

		return val;
	}

}