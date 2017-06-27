package com.zhuanquan.app.server.service;


import java.util.List;

import com.zhuanquan.app.common.view.vo.author.AuthorAlbumPageQueryVo;
import com.zhuanquan.app.common.view.vo.author.AuthorHomeInfoVo;
import com.zhuanquan.app.common.view.vo.author.AuthorRelationshipPageQueryVo;
import com.zhuanquan.app.common.view.vo.author.AuthorWorksPageQueryVo;
import com.zhuanquan.app.common.view.vo.author.SuggestAuthorRequestVo;
import com.zhuanquan.app.common.view.vo.author.SuggestAuthorResponseVo;

/**
 * 
 * @author zhangjun
 *
 */
public interface AuthorService {
	
	/**
	 * 
	 * 根据作者感兴趣的领域和tag标签，获取推荐作者信息
	 * 
	 * @return
	 */
	SuggestAuthorResponseVo getSuggestAuthors(SuggestAuthorRequestVo vo);
	
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