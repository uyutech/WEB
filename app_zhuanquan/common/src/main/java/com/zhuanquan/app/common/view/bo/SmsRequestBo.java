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
     * 验证码
     */
	private String code;
	
	/**
	 * 短信类型
	 * @see   com.zhuanquan.app.common.component.sms.constants.SmsTypeConstants
	 */
	private int type; 


	public String getMobile() {
		return mobile;
	}


	public void setMobile(String mobile) {
		this.mobile = mobile;
	}



	public String getCode() {
		return code;
	}


	public void setCode(String code) {
		this.code = code;
	}


	public int getType() {
		return type;
	}


	public void setType(int type) {
		this.type = type;
	}
	

	
}