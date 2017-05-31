package com.zhuanquan.app.server.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.zhuanquan.app.common.view.bo.ThirdChannelSyncFollowAuthorRequestBo;

/**
 * 同步第三方数据的线程池
 * @author zhangjun
 *
 */
public class ThirdChannelSyncThreadPool {
	
	//最多允许10个并发
	private static ExecutorService executor = Executors.newFixedThreadPool(10);
	
	/**
	 * 发送同步第三方关注作者列表的请求
	 * @param request
	 */
	public static void scheduleSyncFollowAuthorRequest(ThirdChannelSyncFollowAuthorRequestBo  request) {
		
		executor.submit(new ThirdChannelSyncRunnable(request));
		
	}
	

	
}