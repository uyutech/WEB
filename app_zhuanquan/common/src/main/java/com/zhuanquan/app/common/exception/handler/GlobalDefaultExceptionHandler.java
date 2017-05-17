package com.zhuanquan.app.common.exception.handler;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.support.spring.FastJsonJsonView;
import com.zhuanquan.app.common.exception.BizErrorCode;
import com.zhuanquan.app.common.exception.BizException;
import com.zhuanquan.app.common.exception.GatewayException;
import com.zhuanquan.app.common.exception.SessionExpireException;
import com.zhuanquan.app.common.exception.internel.ErrorCodeLoader;
import com.zhuanquan.app.common.utils.HttpRequestUtils;

/**
 * Gateway全局异常处理。
 *
 * 如果是 #{@link BizException} 或者 #{@link GatewayException}, 则返回200，并且返回json消息体
 *
 */

@ControllerAdvice
public class GlobalDefaultExceptionHandler {
	private final Logger log = LoggerFactory.getLogger(getClass());

	@ExceptionHandler(value = Exception.class)
	public ModelAndView defaultErrorHandler(HttpServletRequest request, HttpServletResponse response, Exception e)
			throws Exception {

		// //用户未登录,或登录 会话超时
		// final String serviceName = ServletUtils.getServiceName(request);

		// 会话过期返回401
		if (e instanceof SessionExpireException) {
			log.info("session expire at url:{}, params:{} exception is:{}", request.getRequestURI(),
					HttpRequestUtils.getRequestParams(request), e);
			response.setStatus(401);
			return new ModelAndView();
		}

		// 返回200
		if (e instanceof GatewayException || e instanceof BizException) {
			int code;
			String desc;
			if (e instanceof GatewayException) {
				code = ((GatewayException) e).getErrorCode();
				desc = ((GatewayException) e).getDesc();
			} else if (e instanceof BizException) {
				// 服务异常，不能直接返回给客户端，必须映射一下
				BizException serviceException = (BizException) e;

				code = serviceException.getErrorCode();

				desc = StringUtils.isNotEmpty(serviceException.getMessage()) ? serviceException.getMessage()
						: ErrorCodeLoader.getErrorMessageByCode(code);

			} else {

				code = BizErrorCode.EX_UNEXPECTED_ERROR.getCode();
				desc = "非预期异常";

			}

			log.info("service exception happened at:{}. code:{} desc:{}", request.getRequestURI(), code, desc);
			ModelAndView mv = getErrorJsonView(code, desc);
			return mv;
		} else {
	
			int code = BizErrorCode.EX_UNEXPECTED_ERROR.getCode();
			String desc = "非预期异常";
			
			log.info("service exception happened at:{}. code:{} desc:{}", request.getRequestURI(), code, desc);
			ModelAndView mv = getErrorJsonView(code, desc);
			
			log.warn("spring mvc exception at url:{}, params:{} exception is:{}.", request.getRequestURI(),
					HttpRequestUtils.getRequestParams(request), ExceptionUtils.getStackTrace(e));
			response.setStatus(500);
			return mv;
			
		}

	}

	/**
	 * 使用FastJson提供的FastJsonJsonView视图返回，不需要捕获异常
	 */
	private ModelAndView getErrorJsonView(int code, String message) {

		ModelAndView modelAndView = new ModelAndView();
		FastJsonJsonView jsonView = new FastJsonJsonView();
		Map<String, Object> errorInfoMap = new HashMap<>();
		errorInfoMap.put("code", code);
		errorInfoMap.put("message", message);
		errorInfoMap.put("success", false);

		jsonView.setAttributesMap(errorInfoMap);

		modelAndView.setView(jsonView);

		return modelAndView;
	}

}