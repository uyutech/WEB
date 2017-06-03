package com.zhuanquan.app.dal.dao.user.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.stereotype.Repository;

import com.zhuanquan.app.common.model.user.UserFollowAuthor;
import com.zhuanquan.app.dal.dao.BaseDao;
import com.zhuanquan.app.dal.dao.user.UserFollowAuthorDAO;

@Repository
public class UserFollowAuthorDAOImpl extends BaseDao implements UserFollowAuthorDAO {

	@Resource
	private SqlSessionFactory sqlSessionFactory;

	@Override
	public void insertOrUpdateToFollowAuthor(long uid, long authId) {

		UserFollowAuthor record = UserFollowAuthor.createFollowRecord(uid, authId);

		sqlSessionTemplate.insert(getSqlName("insertOrUpdateToFollowAuthor"), record);

	}

	@Override
	public int updateToCancelFollowAuthor(long uid, long authorId) {

		Map map = new HashMap();
		map.put("uid", uid);
		map.put("followAuthorId", authorId);
		map.put("status", UserFollowAuthor.STAT_DISABLE);

		return sqlSessionTemplate.update(getSqlName("updateFollowAuthorStat"), map);
	}

	@Override
	public int queryUserFollowAuthorCount(long uid) {

		return sqlSessionTemplate.selectOne(getSqlName("queryUserFollowAuthorCount"), uid);
	}

	@Override
	public List<Long> queryUserFollowAuthorIds(long uid) {

		return sqlSessionTemplate.selectList(getSqlName("queryUserFollowAuthorIds"), uid);
	}

	@Override
	public int queryAuthorFollowersCount(long authorId) {

		return sqlSessionTemplate.selectOne(getSqlName("queryAuthorFollowersCount"), authorId);
	}

	@Override
	public List<Long> queryAuthorFollowersIds(long authorId) {

		return sqlSessionTemplate.selectList(getSqlName("queryAuthorFollowersIds"), authorId);
	}

	@Override
	public void insertBatchFollowAuthorIds(long uid, List<Long> authorIds) {

		if (CollectionUtils.isEmpty(authorIds)) {
			return;
		}

		List<UserFollowAuthor> list = new ArrayList<UserFollowAuthor>();

		for (Long authorId : authorIds) {

			list.add(UserFollowAuthor.createFollowRecord(uid, authorId));
		}

		sqlSessionTemplate.insert(getSqlName("insertOrUpdateBatchFollowAuthorIds"), list);

	}

	@Override
	public boolean queryHasFollowAuthor(long uid, long authorId) {
		
		Map map = new HashMap();
		map.put("uid", uid);
		map.put("authorId", authorId);

		UserFollowAuthor record = sqlSessionTemplate.selectOne(getSqlName("queryHasFollowAuthor"), map);
		
		if(record == null || record.getStatus() == UserFollowAuthor.STAT_DISABLE) {
			return false;
		}
		
		return true;
	}

}