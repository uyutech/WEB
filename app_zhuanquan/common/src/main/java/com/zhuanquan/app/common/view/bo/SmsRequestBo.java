package com.zhuanquan.app.common.view.bo;

import java.io.Serializable;

/**
 * sms  请求体
 * @author zhangjun
 *
 */
public class SmsRequestBo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8540977602012093814L;
	
	/**
	 * 手机号
	 */
	private String mobile;
	
	/**
	 * 短信模版
	 */
	private String template;
	
    /**
     * 验证码
     */
	private String code;


	public String getMobile() {
		return mobile;
	}


	public void setMobile(String mobile) {
		this.mobile = mobile;
	}


	public String getTemplate() {
		return template;
	}


	public void setTemplate(String template) {
		this.template = template;
	}


	public String getCode() {
		return code;
	}


	public void setCode(String code) {
		this.code = code;
	}
	
	
	
	
	
}