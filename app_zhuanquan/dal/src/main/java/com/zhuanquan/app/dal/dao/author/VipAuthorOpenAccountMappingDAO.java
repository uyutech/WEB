package com.zhuanquan.app.dal.dao.author;

import java.util.List;

import com.zhuanquan.app.common.model.author.VipAuthorOpenAccountMapping;

/**
 * 
 * @author zhangjun
 *
 */
public interface VipAuthorOpenAccountMappingDAO {
	
	
	/**
	 * 根据openid查询
	 * @param openId
	 * @param channelType
	 * @return
	 */
	public VipAuthorOpenAccountMapping queryRecordByOpenId(String openId,int channelType);
	
	/**
	 * 批量根据openid查询
	 * @param openIds
	 * @param channelType
	 * @return
	 */
	public List<VipAuthorOpenAccountMapping> queryRecordListByOpenIds(List<String> openIds,int channelType);

	
}