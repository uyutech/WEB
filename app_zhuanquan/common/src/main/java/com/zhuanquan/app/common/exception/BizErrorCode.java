package com.zhuanquan.app.common.exception;

import com.framework.core.error.exception.code.ErrorCode;
import com.framework.core.error.exception.internel.ErrorCodeLoader;

public enum BizErrorCode implements ErrorCode {


	//系统异常
	 EX_PAY_SYSTEM_ERROR (1001), 
	 
     //非法请求参数
	 EX_PAY_ILLEGLE_REQUEST_PARM (1002), 
	 
	 //非预期异常
	 EX_PAY_UNEXPECTED_ERROR (1003), 

	 
	 
	 //注册短信验证码校验失败
	 EX_REGISTER_VERIFY_CODE_ERR (100001001), 

	 //登录验证码错误
	 EX_LOGIN_VERIFY_CODE_ERR (100002001), 
	 
	 //登录密码错误
	 EX_LOGIN_PWD_ERR (100002002), 
	 
	 //登录forbidden
	 EX_LOGIN_FORBIDDEN (100002003), 
	 
	 //手机已经绑定注册过了
	 EX_BIND_MOBILE_HAS_BIND (100002004), 

	 //手机没有被绑定注册
	 EX_BIND_MOBILE_HAS_NOT_BIND (100002005), 
	 
	 
	 //短信验证码为空
	 EX_VERIFY_CODE_EMPTY (100002006), 

	 //uid与当前登录的uid不一致
	 EX_UID_NOT_CURRENT_LOGIN_USER (100002007), 

	 //用户不存在
	 EX_UID_NOT_EXSIT (100002008), 
  
	;

	

    /**
     * 异常码
     */
    private final int code;
    
    
    private BizErrorCode(int code){
        this.code = code;
    }
	

	@Override
	public String getMessage() {
		
		return ErrorCodeLoader.getErrorMessageByCode(this.code);
	}

	@Override
	public int getCode() {
		return code;
	}
	
	
	
}	