package com.zhuanquan.app.common.utils;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.config.SocketConfig;

import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.DefaultConnectionReuseStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultConnectionKeepAliveStrategy;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

public class HttpConnectionManager {

	private static PoolingHttpClientConnectionManager poolConnManager = null;
    private static CloseableHttpClient httpclient = null;

	/**
	 * 最大连接数
	 */
	public final static int MAX_TOTAL_CONNECTIONS = 200;
	/**
	 * 获取连接的最大等待时间
	 */
	public final static int WAIT_TIMEOUT = 60000;
	/**
	 * 每个路由最大连接数
	 */
	public final static int MAX_ROUTE_CONNECTIONS = 30;
	/**
	 * 连接超时时间
	 */
	public final static int CONNECT_TIMEOUT = 30000;

	static {

		X509TrustManager xtm = new X509TrustManager() {

			@Override
			public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
				// TODO Auto-generated method stub

			}

			@Override
			public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
				// TODO Auto-generated method stub

			}

			@Override
			public X509Certificate[] getAcceptedIssuers() {
				// TODO Auto-generated method stub
				return null;
			}

		};

		try {
			SSLContext ctx = SSLContext.getInstance("TLS");
			// 使用TrustManager来初始化该上下文，TrustManager只是被SSL的Socket所使用
			ctx.init(null, new TrustManager[] { xtm }, null);

			// SSLContext sslcontext =
			// SSLContexts.custom().loadTrustMaterial(null, new
			// TrustSelfSignedStrategy()).build();
			// HostnameVerifier hostnameVerifier =
			// SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER;
			SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(ctx);
			Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory> create()
					.register("http", PlainConnectionSocketFactory.getSocketFactory()).register("https", sslsf).build();
			poolConnManager = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
			// Increase max total connection to 200
			poolConnManager.setMaxTotal(MAX_TOTAL_CONNECTIONS);
			// Increase default max connection per route to 20
			poolConnManager.setDefaultMaxPerRoute(MAX_ROUTE_CONNECTIONS);
			SocketConfig socketConfig = SocketConfig.custom().setSoTimeout(CONNECT_TIMEOUT).build();
			poolConnManager.setDefaultSocketConfig(socketConfig);

			
		       httpclient = HttpClients
		                .custom()
		                .setConnectionManager(poolConnManager)
		                .setRetryHandler(DefaultHttpRequestRetryHandler.INSTANCE)
		                .setConnectionReuseStrategy(
		                        DefaultConnectionReuseStrategy.INSTANCE)
		                .setKeepAliveStrategy(
		                        DefaultConnectionKeepAliveStrategy.INSTANCE).build();
			
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

	public CloseableHttpClient getConnection() {

		RequestConfig requestConfig = RequestConfig.custom().setConnectionRequestTimeout(WAIT_TIMEOUT)
				.setConnectTimeout(CONNECT_TIMEOUT).setSocketTimeout(CONNECT_TIMEOUT).build();
		CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(poolConnManager)
				.setDefaultRequestConfig(requestConfig).build();

		return httpClient;
	}
	
	
	
	
	public static String postHttps(String url, String jsonStr)  
    {  
        String returnStr = null;  
        //参数检测  
        if(url==null||"".equals(url))  
        {  
            return returnStr;  
        }  
        HttpPost httpPost = new HttpPost(url);  
        try {  
              
            long currentTime=System.currentTimeMillis();  
            List <NameValuePair> nvps = new ArrayList <NameValuePair>();  
//            nvps.add(new BasicNameValuePair("jsonstr", jsonStr));  
            httpPost.setEntity(new UrlEncodedFormEntity(nvps, "UTF-8"));  
            CloseableHttpResponse response = httpclient.execute(httpPost);  
            int status = response.getStatusLine().getStatusCode();  
            if (status >= 200 && status < 300) {  
                HttpEntity entity = response.getEntity();  
                String resopnse="";  
                if(entity != null)  
                {  
                    resopnse=EntityUtils.toString(entity,"utf-8");  
                }  
//                sysoutLog(currentTime+" 接收响应：url"+url+" status="+status);  
                return entity != null ? resopnse : null;  
            } else {  
                HttpEntity entity = response.getEntity();  
                httpPost.abort();  
//                sysoutLog(currentTime+" 接收响应：url"+url+" status="+status+" resopnse="+EntityUtils.toString(entity,"utf-8"));  
                throw new ClientProtocolException("Unexpected response status: " + status);  
            }  
        } catch (Exception e) {  
            httpPost.abort();  
//            log.error(" Exception"+e.toString()+" url="+url+" jsonstr="+jsonStr);  
        }   
        return returnStr;  
    }  
	
	
	
	public static void main(String[] args) {
		
		postHttps("https://www.baidu.com", "");
	}

}