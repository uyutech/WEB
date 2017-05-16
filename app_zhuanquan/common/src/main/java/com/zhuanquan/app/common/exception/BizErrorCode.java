package com.zhuanquan.app.common.exception;

import com.zhuanquan.app.common.exception.code.ErrorCode;
import com.zhuanquan.app.common.exception.internel.ErrorCodeLoader;

public enum BizErrorCode implements ErrorCode {


	//系统异常
	 EX_SYSTEM_ERROR (1001), 
	 
     //非法请求参数
	 EX_ILLEGLE_REQUEST_PARM (1002), 
	 
	 //非预期异常
	 EX_UNEXPECTED_ERROR (1003), 

	 //不允许并发操作
	 EX_NOT_ALLOW_CONCURRENT_OPER (1004), 
	 
	 //脏数据，需要修复
	 EX_DIRTY_DATA (1005), 

	 
//***************短信验证码****************************************	 
	 
	 //短信验证码校验失败
	 EX_VERIFY_CODE_ERR (100001001), 


	 
//***************登录注册****************************************	 
	 
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
	 
	 //昵称不能为空
	 EX_UID_NICK_NAME_CAN_NOT_BE_NULL (100002009), 
	 
	 //昵称不允许重复
	 EX_UID_NICK_NAME_CAN_NOT_BE_DUPLICATE_WITH_PROFILE (100002010), 
	 
	 //不允许和作者名重复
	 EX_UID_NICK_NAME_CAN_NOT_BE_DUPLICATE_WITH_AUTHORNAME (100002011), 

     //其他渠道登录的不允许修改手机登录的密码
	 EX_PWD_NOT_PERMIT_TO_MODIFY (100002012), 
	 
	 //密码不能为空
	 EX_PWD_NOT_BE_EMPTY (100002013), 
	 
	 //密码不合法
	 EX_PWD_IS_NOT_ILLEGLE (100002014), 
	 
	 //第三方token鉴权失败
	 EX_OPEN_ACCOUNT_TOKEN_VALIDATE_ERROR (100002015), 
	 
	 //失败次数过多
	 EX_OPEN_ACCOUNT_TOO_MANY_FAIL_TIMES_ERROR (100002016), 



	//***************用户收藏****************************************	 

	 //收藏状态不对
	 EX_FAV_ILLEGLE_FAV_STATUS(100003001), 

	 
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