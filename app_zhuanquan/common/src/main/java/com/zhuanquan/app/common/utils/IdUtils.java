package com.zhuanquan.app.common.utils;

import java.util.UUID;

public class IdUtils {
	
	
	/**
	 * 
	 * @return
	 */
	public static String generateUUID(){
		
		String uuid = UUID.randomUUID().toString();
		uuid = uuid.replace("-","");
		return uuid;
		
	}
}