package com.zhuanquan.app.server.base.component.sms.service;

import com.zhuanquan.app.server.base.component.sms.view.SmsRequestBo;
import com.zhuanquan.app.server.base.component.sms.view.SmsResponseBo;

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