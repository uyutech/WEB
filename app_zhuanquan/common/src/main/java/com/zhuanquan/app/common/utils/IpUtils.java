package com.zhuanquan.app.common.utils;


import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class IpUtils {

	private static final Logger logger = LoggerFactory.getLogger(IpUtils.class);

	/**
	 * 获取本机Ip
	 * 
	 * 通过 获取系统所有的networkInterface网络接口 然后遍历 每个网络下的InterfaceAddress组。 获得符合
	 * <code>InetAddress instanceof Inet4Address</code> 条件的一个IpV4地址
	 * 
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public String localIp() {
		String ip = null;
		Enumeration allNetInterfaces;
		try {
			allNetInterfaces = NetworkInterface.getNetworkInterfaces();
			while (allNetInterfaces.hasMoreElements()) {
				NetworkInterface netInterface = (NetworkInterface) allNetInterfaces.nextElement();
				List<InterfaceAddress> InterfaceAddress = netInterface.getInterfaceAddresses();
				for (InterfaceAddress add : InterfaceAddress) {
					InetAddress Ip = add.getAddress();
					if (Ip != null && Ip instanceof Inet4Address) {
						ip = Ip.getHostAddress();
					}
				}
			}
		} catch (SocketException e) {
			logger.warn("IpUtils  获取本机Ip失败:异常信息:" + e.getMessage(), e);

			return "127.0.0.1";
		}

		return ip;
	}
	
	
	/**
	 * 获取ip地址
	 * @param request
	 * @return
	 */
    public static String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("X-Real-IP");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("x-forwarded-for");
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }

        //防止多级代理时返回过个ip。
        if(ip != null && ip.indexOf(",") != -1){
            ip= ip.substring(0,ip.indexOf(","));
        }
        return ip;
    }
	
    /**
	 * 将字符串类型的IP转换成 int类型的IP
	 * 生成规则：
	 * ip第一位 x 256的3次方 ＋ ip第二位 x 256的2次方 ＋ ip第三位 x 256 + ip第四位
	 *    
	 * @param ipAddress IP地址，10.1.1.1
	 * @return int
	 */
	public static long ip2Long(String ipAddress){
		long ip = 0;
		if(null == ipAddress || ipAddress.isEmpty()){
			return ip;
		}
		//拆分ip地址，如果长度小于4，则ip异常，直接返回0
		String[] ips = ipAddress.split("\\.");
		if(ips.length != 4){
			return ip;
		}
		long ip0 = Integer.parseInt(ips[0]);
		long ip1 = Integer.parseInt(ips[1]);
		long ip2 = Integer.parseInt(ips[2]);
		long ip3 = Integer.parseInt(ips[3]);
		ip = ip0 * (256 * 256 * 256) + ip1 * (256 * 256) + ip2 * 256 + ip3;
		return ip;
	}

	/**
	 *
	 * 将LONG类型的IP地址,转换成字符串类型
	 *
	 * @param ipLong LONGIP地址
	 *
	 * @return String类型的IP地址
	 */
	public static String long2StrIp(long ipLong){
		return new StringBuilder().append(((ipLong >> 24) & 0xff)).append('.')
					.append((ipLong >> 16) & 0xff).append('.').append(
							(ipLong >> 8) & 0xff).append('.').append((ipLong & 0xff))
					.toString();
	}
}