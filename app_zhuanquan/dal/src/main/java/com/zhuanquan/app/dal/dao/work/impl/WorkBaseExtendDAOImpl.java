package com.zhuanquan.app.dal.dao.work.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.zhuanquan.app.common.model.work.WorkBaseExtend;
import com.zhuanquan.app.dal.dao.BaseDao;
import com.zhuanquan.app.dal.dao.work.WorkBaseExtendDAO;


@Repository
public class WorkBaseExtendDAOImpl extends BaseDao implements WorkBaseExtendDAO {

	@Override
	public List<WorkBaseExtend> queryExtendInfoByWorkId(long workId) {
		// TODO Auto-generated method stub
		return null;
	}
	
}