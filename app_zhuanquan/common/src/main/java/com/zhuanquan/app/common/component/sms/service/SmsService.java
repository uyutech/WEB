package com.zhuanquan.app.common.component.sms.service;

import com.zhuanquan.app.common.view.bo.SmsRequestBo;
import com.zhuanquan.app.common.view.bo.SmsResponseBo;

/**
 * 短信服务
 * 
 * @author zhangjun
 *
 */

public interface SmsService {

	/**
	 * 发送短信
	 * 
	 * @param vo
	 * @return
	 */
	public SmsResponseBo sendSms(SmsRequestBo vo);

}