package com.zhuanquan.app.common.utils;

import java.math.BigDecimal;

public class AmountUtils {
	
	/**
	 * 元转为分
	 * @param yuan
	 * @return
	 */
	public static int yuanToFen(String yuan) {
		
		BigDecimal b=new BigDecimal(yuan); 
		
		BigDecimal a=b.multiply(new BigDecimal(100));//乘以100(单位：分) 
		
		return a.intValue();
	}
	
	/**
	 * 分转为元
	 * @param fen
	 * @return
	 */
	public static String fenToYuan(int fen) {
		return BigDecimal.valueOf(Integer.valueOf(fen)).divide(new BigDecimal(100))
		.toString();
	}
	
	
}