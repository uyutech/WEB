package com.zhuanquan.app.common.exception;

import com.framework.core.error.exception.code.ErrorCode;
import com.framework.core.error.exception.internel.ErrorCodeLoader;

public enum BizErrorCode implements ErrorCode {


  
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