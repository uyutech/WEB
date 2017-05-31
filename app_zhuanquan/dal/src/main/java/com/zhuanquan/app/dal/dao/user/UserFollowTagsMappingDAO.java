package com.zhuanquan.app.dal.dao.user;

import java.util.List;
import java.util.Map;

import com.zhuanquan.app.common.model.common.Tag;
import com.zhuanquan.app.common.model.user.UserFollowTag;

/**
 * 用户关注的标签映射表
 * @author zhangjun
 *
 */
public interface UserFollowTagsMappingDAO {
	
	
	
	/**
	 * 批量插入
	 * @param uid
	 * @param tagMappings
	 */
	void insertOrUpdateBatchToFollowTags(long uid,List<UserFollowTag> tagMappings);
	
	
	/**
	 * 插入或者更新一条的关注tag
	 * @param uid
	 * @param record
	 */
	void insertOrUpdateToFollowTag(long uid,UserFollowTag record);
	
	
	
	/**
	 * 取消关注
	 * @param uid
	 * @param tagId
	 */
	void updateToCancelTag(long uid,long tagId);
	
	/**
	 * 查询uid关注的tag
	 * @param uid
	 * @return
	 */
	List<UserFollowTag> queryUserFollowTag(long uid);
	
	
	/**
	 * 查询是否关注了tag
	 * @param uid
	 * @param tagId
	 * @return
	 */
	boolean queryHasFollowedTag(long uid,long tagId);
	

//	/**
//	 * 分页查询
//	 * @param offset
//	 * @param pagSize
//	 * @param excludeIds
//	 * @return
//	 */
//	List<Long> queryHotTagsByPage(int offset,int pagSize,List<Long> excludeIds);
	
	
	/**
	 * 获取最近最火的标签
	 * @param top
	 * @return
	 */
	public List<Long> queryHotTagsRecently(int top) ;

	
}