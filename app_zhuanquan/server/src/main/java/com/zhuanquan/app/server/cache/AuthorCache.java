package com.zhuanquan.app.server.cache;

import java.util.List;
import java.util.Map;

import com.zhuanquan.app.common.model.author.AuthorThirdPlatformDefine;
import com.zhuanquan.app.common.model.author.AuthorThirdPlatformInfo;
import com.zhuanquan.app.common.view.bo.author.AuthorBaseInfoBo;
import com.zhuanquan.app.common.view.vo.discovery.DiscoveryHotAuthorVo;

public interface AuthorCache {
	
	/**
	 * 批量查询作者信息
	 * @param authorIds
	 * @return
	 */
	Map<String, AuthorBaseInfoBo> batchQueryAuthorBaseByIds(List<Long> authorIds);
	
	/**
	 * 查询作者热度
	 * @return
	 */
	long queryAuthorHotIndex(long authorId);
	
	
	/**
	 * 查询作者入住的第三方平台信息
	 * @param platformId
	 * @return
	 */
	AuthorThirdPlatformDefine queryById(long platformId);
	
	
	/**
	 * 查询其他平台的信息
	 * @param authorId
	 * @return
	 */
	List<AuthorThirdPlatformInfo> queryAuthorOtherPlatformInfo(long authorId);
	
	/**
	 * 发现页 热点作者分页查询
	 * @param fromIndex
	 * @param limit
	 * @return
	 */
	List<DiscoveryHotAuthorVo> getDiscoverHotAuthorByPage(int fromIndex,int limit);
	
}