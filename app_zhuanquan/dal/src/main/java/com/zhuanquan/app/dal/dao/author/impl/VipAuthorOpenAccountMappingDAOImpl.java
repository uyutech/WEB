package com.zhuanquan.app.dal.dao.author.impl;


import java.util.List;

import org.springframework.stereotype.Repository;

import com.zhuanquan.app.common.model.author.VipAuthorOpenAccountMapping;
import com.zhuanquan.app.dal.dao.BaseDao;
import com.zhuanquan.app.dal.dao.author.VipAuthorOpenAccountMappingDAO;

@Repository
public class VipAuthorOpenAccountMappingDAOImpl extends BaseDao implements VipAuthorOpenAccountMappingDAO {

	@Override
	public VipAuthorOpenAccountMapping queryRecordByOpenId(String openId, int channelType) {
		return null;
	}

	@Override
	public List<VipAuthorOpenAccountMapping> queryRecordListByOpenIds(List<String> openIds, int channelType) {
		return null;
	}
	
}