package com.zhuanquan.app.dal.dao.user.impl;



import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.http.util.Asserts;
import org.springframework.stereotype.Repository;

import com.alibaba.fastjson.JSON;
import com.sun.tools.javac.util.Assert;
import com.zhuanquan.app.common.exception.BizErrorCode;
import com.zhuanquan.app.common.exception.BizException;
import com.zhuanquan.app.common.model.user.UserProfile;
import com.zhuanquan.app.dal.dao.BaseDao;
import com.zhuanquan.app.dal.dao.user.UserProfileDAO;


@Repository
public class UserProfileDAOImpl extends BaseDao implements UserProfileDAO {

	@Override
	public UserProfile queryById(long uid) {

		return sqlSessionTemplate.selectOne(getSqlName("queryById"), uid);
	}

	@Override
	public long insertRecord(UserProfile profile) {
		
		sqlSessionTemplate.insert(getSqlName("insertRecord"), profile);
		
		return profile.getUid();
	}

	@Override
	public boolean queryNickNameHasBeenUsed(String nickName) {
		
		UserProfile profile = sqlSessionTemplate.selectOne(getSqlName("queryByNickNameLimit1"), nickName);

		return profile == null?false:true;
	}

	@Override
	public int updateNickName(long uid, String nickName) {
		Map map = new HashMap();
		map.put("uid", uid);
		map.put("nickName", nickName);

		return sqlSessionTemplate.update(getSqlName("updateNickName"), map);
	}

	@Override
	public int updateRegisterStatus(long uid, int registerStatus) {
		
		Map map = new HashMap();
		map.put("uid", uid);
		map.put("regStat", registerStatus);

		return sqlSessionTemplate.update(getSqlName("updateRegisterStatus"), map);
	}

	@Override
	public UserProfile queryByAuthorId(long authorId) {
		
		//没有申请作者账户的 authorid对应都为0，查询没有意义，所以直接返回null
		if(authorId <= 0 ) {
			return null;
		}
		
		List<UserProfile> list = sqlSessionTemplate.selectList(getSqlName("queryByAuthorId"), authorId);
		
		//一个author id 对应了2个uid了，
        if(list!=null && list.size() > 1) {
        	throw new BizException(BizErrorCode.EX_DIRTY_DATA.getCode(),"user_profile中脏数据，一个author对应了多个uid记录![authorId]="+authorId+",[list]="+JSON.toJSONString(list));
        }
        
        return CollectionUtils.isEmpty(list)?null:list.get(0);
	}

	@Override
	public int updateGender(long uid, int gender) {
		
		Map map = new HashMap();
		map.put("uid", uid);
		map.put("gender", gender);

		return sqlSessionTemplate.update(getSqlName("updateGender"), map);
	}

	
}