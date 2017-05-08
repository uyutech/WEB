package com.zhuanquan.app.common.utils;

import java.math.BigDecimal;
import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zhuanquan.app.common.exception.BizErrorCode;
import com.zhuanquan.app.common.exception.BizException;

/**
 * 
 */
public class CommonUtil {

	/**
	 * 获取客户端的IP
	 * 
	 * @return
	 */
	public static int getUserIp() {
		// TODO 获取IP信息
		return 0;
	}

	private static boolean validate(String reg, String value) {
		Pattern p = Pattern.compile(reg);
		Matcher m = p.matcher(value);
		boolean ret = m.matches();
		return ret;
	}

	/**
	 * 保留小数
	 * 
	 * @param data
	 * @param num
	 * @return
	 */
	public static String formatNum(float data, int num) {
		BigDecimal b = new BigDecimal(data);
		return b.setScale(num, BigDecimal.ROUND_HALF_UP).toString();
	}

	/**
	 * 验证手机号
	 * 
	 * @param mobile
	 * @return
	 */
	public static boolean validateMobile(String mobile) {
		return validate("^1\\d{10}$", mobile);
	}

	/**
	 * 验证email
	 * 
	 * @param mail
	 * @return
	 */
	public static boolean validateMail(String mail) {
		return validate("^([a-z0-9A-Z]+[-|\\.|_]?)+(\\w)?@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]+$", mail);
	}

	/**
	 * 宽泛验证email
<<<<<<< HEAD
     */
	public static boolean checkMail(String mail){
		return mail.contains("@");
	}


	public static void main(String[] args) {
		System.out.println(checkMail("970474341@qq.com"));
	}

	/**
	 * 如果有国际区位码，添加手机区位码
	 * 
	 * @param area
	 * @param mobile
	 * @return
	 */
	public static String makeMobile(String area, String mobile) {
		if (StringUtils.isEmpty(area) || area.equals("86")) {
			return mobile;
		}
		return area + "-" + mobile;
	}
//
//	/**
//	 * 验证手机号是否在制定的区域内
//	 * 
//	 * @param area
//	 * @param mobile
//	 * @return
//	 */
//	public static boolean areaMobielVerify(String area, String mobile) {
//		if (StringUtils.isEmpty(area)) {
//			area = ProfileConstant.DEFAULT_AREA;
//		}
//		MobileAreaModel m = ProfileConstant.mobileAreaMap.get(area);
//		if (m == null) {
//			return false;
//		}
//		return mobile.matches(m.getRegexp());
//	}
//
//	/**
//	 * 验证手机号是否在制定的区域内
//	 * 
//	 * @param areaMobile
//	 * @return
//	 */
//	public static boolean areaMobile(String areaMobile) {
//		if (StringUtils.isEmpty(areaMobile)) {
//			return false;
//		}
//		if (areaMobile.indexOf("-") < 0) {
//			return areaMobielVerify(ProfileConstant.DEFAULT_AREA, areaMobile);
//		}
//		String[] arr = splitMobile(areaMobile);
//		if (arr.length != 2) {
//			return false;
//		}
//		return areaMobielVerify(arr[0], arr[1]);
//	}

	/**
	 * 拆分手机号
	 * 
	 * @param mobile
	 * @return
	 */
	public static String[] splitMobile(String mobile) {
		if (StringUtils.isEmpty(mobile) || mobile.indexOf("-") < 0) {
			return null;
		}
		return mobile.split("-");
	}

	/**
	 * 生成一定长度的随机字符串
	 * 
	 * @param length
	 * @return
	 */
	public static String getRandomString(int length) {
		String base = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
		Random random = new Random();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < length; i++) {
			int number = random.nextInt(base.length());
			sb.append(base.charAt(number));
		}
		return sb.toString();
	}

	/***
	 * MD5加码 生成32位md5码
	 */
	public static String string2MD5(String inStr) {
		MessageDigest md5 = null;
		try {
			md5 = MessageDigest.getInstance("MD5");
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
		char[] charArray = inStr.toCharArray();
		byte[] byteArray = new byte[charArray.length];

		for (int i = 0; i < charArray.length; i++)
			byteArray[i] = (byte) charArray[i];
		byte[] md5Bytes = md5.digest(byteArray);
		StringBuffer hexValue = new StringBuffer();
		for (int i = 0; i < md5Bytes.length; i++) {
			int val = ((int) md5Bytes[i]) & 0xff;
			if (val < 16)
				hexValue.append("0");
			hexValue.append(Integer.toHexString(val));
		}
		return hexValue.toString();
	}

	/**
	 * 注册校验密码，密码规则，英文字母或者数字
	 * 
	 * @param password
	 *            string
	 * @return
	 */
	public static boolean registerValidatePassword(String password) {
		if (StringUtils.isBlank(password)) {
			return false;
		}
		String reg = "^[a-zA-Z0-9~!@#$%^&*()-_+=<,>./?;:\"'{\\[}\\]\\|]{6,20}$";
		return password.matches(reg);
	}

	public static String makePass(String code) {
		String rmd = getRandomString(32);
		String crypt = string2MD5(code + rmd);
		return crypt + ":" + rmd;
	}

	public static boolean checkPass(String code, String hash) {
		String[] codes = hash.split(":");
		if (codes.length == 2) {
			if (string2MD5(code + codes[1]).equals(codes[0])) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}

	}

	/**
	 * 合并对象
	 * 
	 * @param objs
	 * @return
	 */
	public static Map<String, Object> mergeObj(Object... objs) {
		if (objs == null || objs.length == 0) {
			return null;
		}
		Map<String, Object> map = new HashMap<String, Object>();
		for (Object obj : objs) {
			if (obj == null) {
				continue;
			}
			JSONObject j = (JSONObject) JSON.toJSON(obj);
			for (Entry<String, Object> entry : j.entrySet()) {
				map.put(entry.getKey(), entry.getValue());
			}
		}
		return map;
	}


	public static void assertNotNull(String key, String propretyName) {

		if (StringUtils.isEmpty(key)) {
			throw new BizException(BizErrorCode.EX_ILLEGLE_REQUEST_PARM.getCode(),
					propretyName + " can not be null!");
		}

	}


	/**
	 * 获取 原密码加密之后的字符串
	 * @param ordPassword
	 * @return
	 */
	public static String makeEncriptPassword(String ordPassword) {
		
		
		return MD5.md5(ordPassword);
		
		
	}
	
	
}
