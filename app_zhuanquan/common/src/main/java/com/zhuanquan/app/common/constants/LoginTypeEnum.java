package com.zhuanquan.app.common.constants;

//登录方式
public enum LoginTypeEnum {

	// web请求
	SOURCE_TYPE_WEB(1),

	// 客户端，包括android和ios
	SOURCE_TYPE_CLIENT(0),

	;
	/**
	 * code
	 */
	private int code;

	private LoginTypeEnum(int code) {
		this.code = code;
	}

	public int getCode() {

		return code;
	}

	public void setCode(int code) {

		this.code = code;
	}

}