package com.zhuanquan.app.dal.dao.user.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Repository;

import com.zhuanquan.app.common.model.user.UserFollowTag;
import com.zhuanquan.app.common.utils.DateUtil;
import com.zhuanquan.app.common.utils.DateUtils;
import com.zhuanquan.app.dal.dao.BaseDao;
import com.zhuanquan.app.dal.dao.user.UserFollowTagsMappingDAO;

@Repository
public class UserFollowTagsMappingDAOImpl extends BaseDao implements UserFollowTagsMappingDAO {

	@Override
	public void insertOrUpdateBatchToFollowTags(long uid, List<UserFollowTag> tagMappings) {

		if (CollectionUtils.isEmpty(tagMappings)) {
			return;
		}

		sqlSessionTemplate.insert(getSqlName("insertOrUpdateBatchToFollowTags"), tagMappings);
	}

	@Override
	public void insertOrUpdateToFollowTag(long uid, UserFollowTag record) {

		sqlSessionTemplate.insert(getSqlName("insertOrUpdateToFollowTag"), record);
	}

	@Override
	public void updateToCancelTag(long uid, long tagId) {

		Map map = new HashMap();
		map.put("uid", uid);
		map.put("tagId", tagId);
		map.put("status", UserFollowTag.STAT_DISABLE);

		sqlSessionTemplate.update(getSqlName("updateTagStatus"), map);

	}

	@Override
	public List<UserFollowTag> queryUserFollowTag(long uid) {

		return sqlSessionTemplate.selectList(getSqlName("queryUserFollowTag"), uid);

	}

	@Override
	public boolean queryHasFollowedTag(long uid, long tagId) {

		UserFollowTag record = queryByUidAndTagId(uid, tagId);

		return record == null ? false : true;
	}

	/**
	 * 
	 * @param uid
	 * @param tagId
	 * @return
	 */
	private UserFollowTag queryByUidAndTagId(long uid, long tagId) {

		Map map = new HashMap();
		map.put("uid", uid);
		map.put("tagId", tagId);

		return sqlSessionTemplate.selectOne(getSqlName("queryByUidAndTagId"), map);
	}

	// @Override
	// public List<Long> queryHotTagsByPage(int offset, int pagSize,List<Long>
	// excludeIds) {
	//
	//
	// return queryHotTagByPage(offset, pagSize, excludeIds, null);
	//
	// }
	//

	/**
	 * 获取最近最火的tag
	 * 
	 * @return
	 */
	@Override
	public List<Long> queryHotTagsRecently(int top) {

		// 从offset 0 开始，查询15条
		return queryHotTag(top, 2);

	}

	/**
	 * 
	 * @param offset
	 * @param pagSize
	 * @param excludeIds
	 * @param nDaysBefore
	 * @return
	 */
	private List<Long> queryHotTag(int top, Integer nDaysBefore) {

		Map map = new HashMap();

		map.put("limit", top);
		map.put("compareTime", nDaysBefore == null ? null : DateUtils.getNDaysBefore(nDaysBefore, new Date()));

		List<Long> tags = sqlSessionTemplate.selectList(getSqlName("queryHotTag"), map);

		if (tags != null && tags.size() == top) {
			return tags;
		}

		// 如果查询到的个数小于预期的个数，那么全量的去查tag,补满100个

		//删除时间的约束，重新查一次
		map.remove("compareTime");
		List<Long> ids = sqlSessionTemplate.selectList(getSqlName("queryHotTag"), map);

		return merge(tags, ids, top);

	}

	/**
	 * 
	 * @param target
	 * @param temp
	 * @return
	 */
	private List<Long> merge(List<Long> target, List<Long> temp, int targetSize) {

		Set<String> set = new HashSet<String>();

		if (target != null) {
			for (Long id : target) {
				set.add(id.toString());
			}
		}

		if (temp != null) {
			for (Long id : temp) {
				if (!set.contains(id.toString())) {

					target.add(id);

					if (target.size() >= targetSize) {
						break;
					}
				}
			}
		}
		return target;
	}


	@Override
	public List<Long> queryUserFollowTagByPage(long uid, int fromIndex, int limit) {
		Map map = new HashMap();

		map.put("uid", uid);
		map.put("offset", fromIndex);
		map.put("limit", limit);

		return sqlSessionTemplate.selectList(getSqlName("queryUserFollowTagByPage"), map);
	}

}