package com.zhuanquan.app.dal.dao.common.impl;



import java.util.List;

import org.springframework.stereotype.Repository;

import com.zhuanquan.app.dal.dao.BaseDao;
import com.zhuanquan.app.dal.dao.common.SuggestSourceMgrDAO;


@Repository
public class SuggestSourceMgrDAOImpl extends BaseDao implements SuggestSourceMgrDAO {

	@Override
	public List<String> queryCurrentSuggestRecord() {
		
		
		return this.sqlSessionTemplate.selectList(getSqlName("queryCurrentSuggestRecord"));
	}
	
}