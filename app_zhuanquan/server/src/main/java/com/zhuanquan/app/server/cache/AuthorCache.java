package com.zhuanquan.app.server.cache;

import java.util.List;
import java.util.Map;

import com.zhuanquan.app.common.model.author.AuthorThirdPlatformDefine;
import com.zhuanquan.app.common.model.author.AuthorThirdPlatformInfo;
import com.zhuanquan.app.common.view.bo.author.AuthorBaseInfoBo;
import com.zhuanquan.app.common.view.vo.author.AuthorAlbumPageQueryVo;
import com.zhuanquan.app.common.view.vo.author.AuthorHomeInfoVo;
import com.zhuanquan.app.common.view.vo.author.AuthorRelationshipPageQueryVo;
import com.zhuanquan.app.common.view.vo.author.AuthorWorksPageQueryVo;
import com.zhuanquan.app.common.view.vo.discovery.DiscoveryHotAuthorVo;
import com.zhuanquan.app.common.view.vo.discovery.DiscoveryPageQueryRequest;

public interface AuthorCache {
	
	
	/**
	 * 批量查询作者信息
	 * @param authorIds
	 * @return
	 */
	AuthorBaseInfoBo queryAuthorBaseById(long authorId);
	
	
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
	List<DiscoveryHotAuthorVo> getDiscoverHotAuthorByPage(DiscoveryPageQueryRequest request);
	
	
	/**
	 * 查询作者主页信息
	 * @param authorId
	 * @return
	 */
	AuthorHomeInfoVo queryAuthorHomeInfoVo(long authorId);
	
	
	
	/**
	 * 分页查询作者作品
	 * @param authorId
	 * @param offset
	 * @param limit
	 * @return
	 */
	AuthorWorksPageQueryVo pageQueryAuthorWorksPageQueryVo(long authorId,int offset,int limit);
	
	/**
	 * 分页查询作者专辑
	 * @param authorId
	 * @param fromIndex
	 * @param limit
	 * @return
	 */
	AuthorAlbumPageQueryVo pageQueryAuthorAlbumsVo(long authorId, int fromIndex,int limit);
	
	
	/**
	 * 分页查询作者相关的关系
	 * @param authorId
	 * @param fromIndex
	 * @param limit
	 * @return
	 */
	AuthorRelationshipPageQueryVo pageQueryAuthorRelationship( long authorId,int fromIndex,int limit);
	
}