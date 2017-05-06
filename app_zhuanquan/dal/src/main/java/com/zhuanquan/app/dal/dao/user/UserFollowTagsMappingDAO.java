package com.zhuanquan.app.dal.dao.user;

import java.util.List;
import java.util.Map;

import com.zhuanquan.app.common.model.user.UserFollowTagsMapping;

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
	void insertBatchToFollowTags(long uid,List<UserFollowTagsMapping> tagMappings);
	
	/**
	 * 批量update 来关注tag
	 * @param uid
	 * @param tagIds
	 */
	void updateBatchToFollowTags(long uid,List<Long> tagIds);
	
	
	/**
	 * 根据uid和tag id查询
	 * 
	 * @param uid
	 * @param tags
	 * @return
	 */
	Map<String,String> queryByTagIds(long uid,List<Long> tagIds);
	
}