package com.zhuanquan.app.common.constants.author;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Assert;


/**
 * role code的前三位就是类型
 * @author zhangjun
 *
 */
public enum AuthorRoleCategoryEnum {

	ROLE_CATEGORY_策(100),
	ROLE_CATEGORY_文(101),
	ROLE_CATEGORY_图(102),		
	ROLE_CATEGORY_影(103),		
	ROLE_CATEGORY_设(104),		
	ROLE_CATEGORY_技(105),		
	ROLE_CATEGORY_舞(106),		
	ROLE_CATEGORY_COS(107),		
	ROLE_CATEGORY_配(108),		
	ROLE_CATEGORY_歌(109),		
	ROLE_CATEGORY_乐(110),	
	ROLE_CATEGORY_混(111),	
	ROLE_CATEGORY_曲(112),	
	
	;
	
	
	private int code;
	

	
	public int getCode() {
		return code;
	}




	public void setCode(int code) {
		this.code = code;
	}




	private AuthorRoleCategoryEnum(int categoryCode){
		code = categoryCode;
	}
	
	
	/**
	 * 
	 * @param roleCode
	 * @return
	 */
	public static int getRoleCategoryCode(String roleCode){
		
		Assert.isTrue(StringUtils.isNotEmpty(roleCode)&&roleCode.length()>=3);
		
		//取前三位,就是类型的定义
		String prefix = roleCode.substring(0, 3);

		return Integer.parseInt(prefix);
		
	}
	
	
	
}