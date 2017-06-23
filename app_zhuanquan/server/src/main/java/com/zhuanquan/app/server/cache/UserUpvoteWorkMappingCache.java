package com.zhuanquan.app.server.cache;



/**
 * 用户对作品点赞的缓存
 * @author zhangjun
 *
 */
public interface UserUpvoteWorkMappingCache {
	
	/**
	 * 对作品点赞,不允许在事务中调用!!!!因为这里是先写了缓存，没直接写库
	 * @param uid uid
	 * @param workId 作品id
	 */
	void upvoteWork(long uid,long workId);
	
	/**
	 * 
	 * 
	 * 取消对作品的点赞，对作品点赞,不允许在事务中调用!!!!因为这里是先写了缓存，没直接写库。
	 * 
	 * @param uid
	 * @param workId
	 */
	void cancelUpvoteWork(long uid,long workId);
	
	
//	/**
//	 * 查询作品的点赞数
//	 * 
//	 * @param workId
//	 * @return
//	 */
//	long queryWorkUpvoteNum(long workId);
	
	
	
	/**
	 * 是否点赞了
	 * @param uid
	 * @param workId
	 * @return
	 */
	boolean hasUpvoteWork(long uid,long workId);
	
	
	
	/**
	 * 执行持久化到 点赞总数和
	 */
	void doPersistUpvoteNumTask();
	
}