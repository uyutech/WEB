package com.zhuanquan.app.server.controller.user;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhuanquan.app.common.exception.BizErrorCode;
import com.zhuanquan.app.common.exception.BizException;
import com.zhuanquan.app.common.view.ApiResponse;
import com.zhuanquan.app.common.view.vo.user.BindAndChoosePersistRequestVo;
import com.zhuanquan.app.common.view.vo.user.MobileRegisterRequestVo;
import com.zhuanquan.app.common.view.vo.user.RegisterResponseVo;
import com.zhuanquan.app.common.view.vo.user.SelectFollowAuthorRequestVo;
import com.zhuanquan.app.common.view.vo.user.SelectFollowTagsRequestVo;
import com.zhuanquan.app.server.controller.common.BaseController;
import com.zhuanquan.app.server.service.RegisterService;

@Controller
@RequestMapping(value = "/register")
public class RegisterController extends BaseController {

	@Resource
	private RegisterService registerService;

	@RequestMapping(value = "/registerByMobile")
	@ResponseBody
	public ApiResponse registerByMobile(MobileRegisterRequestVo vo) {

		RegisterResponseVo response = registerService.mobileRegister(vo);

		return ApiResponse.success(response);

	}

	/**
	 * 绑定未注册过的手机号
	 * 
	 * @param uid
	 *            原来账号的uid
	 * @param mobile
	 *            手机号
	 * @param verifycode
	 *            验证码
	 * @return
	 */
	@RequestMapping(value = "/bindUnregisterMobile")
	@ResponseBody
	public ApiResponse bindUnregisterMobile(long uid, String mobile, String password, String verifycode) {

		checkLoginUid(uid);

		registerService.bindUnregisterMobile(uid, mobile, password, verifycode);

		return ApiResponse.success();
	}

	/**
	 * 如果手机号已经注册了，那么用户需要选择，当前保留哪一个账号
	 * 
	 * @param vo
	 */
	@RequestMapping(value = "/bindMobileAndChoosePersistAccount")
	@ResponseBody
	public ApiResponse bindMobileAndChoosePersistAccount(BindAndChoosePersistRequestVo vo) {

		checkLoginUid(vo.getUid());

		registerService.mergeMobileAccount(vo.getUid(), vo.getMobile(), vo.getVerifycode(),
				vo.getPersistMobileAccount() == 1);

		return ApiResponse.success();
	}

	/**
	 * 检查手机是否已经被绑定了
	 * 
	 * @param mobile
	 * @return
	 */
	@RequestMapping(value = "/beforeBindCheck")
	@ResponseBody
	public ApiResponse beforeBindCheck(String mobile) {

		int result = registerService.beforeBindCheck(mobile);

		return ApiResponse.success(result);
	}

	/**
	 * 如果手机号已经被注册过，用户需要选择当前保留哪个账号的数据.
	 * 
	 * @param uid
	 *            登录的uid
	 * @param mobile
	 *            手机号
	 * @param verifycode
	 *            手机验证码
	 * @param persistMobileAccount
	 *            是否保留手机账号的数据 1-保留 0-不保留
	 */

	@RequestMapping(value = "/mergeMobileAccount")
	@ResponseBody
	public ApiResponse mergeMobileAccount(long uid, String mobile, String verifycode, int persistMobileAccount) {

		checkLoginUid(uid);

		boolean isPersistMobileAccount = true;

		if (persistMobileAccount == 1) {
			isPersistMobileAccount = true;
		} else if (persistMobileAccount == 0) {
			isPersistMobileAccount = false;
		} else {
			throw new BizException(BizErrorCode.EX_ILLEGLE_REQUEST_PARM.getCode());
		}

		registerService.mergeMobileAccount(uid, mobile, verifycode, isPersistMobileAccount);

		return ApiResponse.success();

	}

	/**
	 * 发送注册的短信验证
	 * 
	 * @param mobile
	 * @return
	 */
	@RequestMapping(value = "/sendRegSms")
	@ResponseBody
	public ApiResponse sendRegSms(@RequestParam(value = "mobile", required = true) String mobile) {

		registerService.sendRegisterSms(mobile);

		return ApiResponse.success();
	}

	/**
	 * 注册设置性别
	 * 
	 * @param uid
	 * @param gender
	 *            0-男 1-女
	 * @return
	 */
	@RequestMapping(value = "/setGenderOnRegister")
	@ResponseBody
	public ApiResponse setGenderOnRegister(long uid, int gender) {

		checkLoginUid(uid);

		registerService.setGenderOnRegister(uid, gender);

		return ApiResponse.success();
	}

	/**
	 * 注册设置昵称
	 * 
	 * @param uid
	 * @param nickName
	 * @return
	 */
	@RequestMapping(value = "/setNickNameAndGenderOnRegister")
	@ResponseBody
	public ApiResponse setNickNameOnRegister(long uid, String nickName,int gender) {
		checkLoginUid(uid);

		registerService.setNickNameAndGenderOnRegister(uid, nickName,gender);

		return ApiResponse.success();
	}

	/**
	 * 注册设置关注的tag
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/setFollowTagsOnRegister")
	@ResponseBody
	public ApiResponse setFollowTagsOnRegister(@RequestBody SelectFollowTagsRequestVo request) {
		checkLoginUid(request.getUid());

		registerService.setFollowTagsOnRegister(request.getUid(), request.getTagIds());

		return ApiResponse.success();
	}

	/**
	 * 注册设置关注的作者
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/setFollowAuthorsOnRegister")
	@ResponseBody
	public ApiResponse setFollowAuthorsOnRegister(@RequestBody SelectFollowAuthorRequestVo request) {
		checkLoginUid(request.getUid());

		registerService.setFollowAuthorsOnRegister(request.getUid(), request.getAuthorIds());

		return ApiResponse.success();
	}

}