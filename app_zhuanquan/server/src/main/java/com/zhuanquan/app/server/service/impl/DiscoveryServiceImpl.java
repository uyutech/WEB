package com.zhuanquan.app.server.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.zhuanquan.app.common.model.work.WorkAlbum;
import com.zhuanquan.app.common.view.bo.work.WorkSourceTypeInfoBo;
import com.zhuanquan.app.common.view.vo.discovery.DiscoveryHotAuthorVo;
import com.zhuanquan.app.common.view.vo.discovery.DiscoveryHotWorkAlbumVo;
import com.zhuanquan.app.common.view.vo.discovery.DiscoveryHotWorkVo;
import com.zhuanquan.app.common.view.vo.discovery.DiscoveryPageQueryRequest;
import com.zhuanquan.app.common.view.vo.discovery.DiscoveryQuerySuggestTagRequest;
import com.zhuanquan.app.common.view.vo.discovery.DiscoverySuggestSourceTypeVo;
import com.zhuanquan.app.common.view.vo.discovery.DiscoverySuggestTagInfoVo;
import com.zhuanquan.app.dal.dao.work.WorkAlbumDAO;
import com.zhuanquan.app.dal.dao.work.WorkAlbumMemberDAO;
import com.zhuanquan.app.dal.dao.work.WorkSourceTypeDefineDAO;
import com.zhuanquan.app.server.cache.AuthorCache;
import com.zhuanquan.app.server.cache.AuthorHotIndexesCache;
import com.zhuanquan.app.server.cache.SuggestSourceMgrCache;
import com.zhuanquan.app.server.cache.WorksCache;
import com.zhuanquan.app.server.service.DiscoveryService;

@Service
public class DiscoveryServiceImpl implements DiscoveryService {

	@Resource
	private WorksCache worksCache;

	@Resource
	private AuthorCache authorCache;

	@Resource
	private AuthorHotIndexesCache authorHotIndexesCache;

	@Resource
	private SuggestSourceMgrCache suggestSourceMgrCache;

	@Resource
	private WorkAlbumMemberDAO workAlbumMemberDAO;

	@Resource
	private WorkAlbumDAO workAlbumDAO;

	@Resource
	private WorkSourceTypeDefineDAO workSourceTypeDefineDAO;

	/**
	 * 分页查询
	 * 
	 * @param fromIndex
	 *            从第几个开始
	 * @param limit
	 * @return
	 */
	@Override
	public List<DiscoveryHotWorkVo> getDiscoverHotWorksByPage(DiscoveryPageQueryRequest request) {

		return worksCache.queryDiscoverHotWorksByPage(request);

	}

	/**
	 * 获取热度 top n的 信息，分页查询
	 * 
	 * @param num
	 * @return
	 */
	@Override
	public List<DiscoveryHotAuthorVo> getDiscoverHotAuthorByPage(DiscoveryPageQueryRequest request) {

		return authorCache.getDiscoverHotAuthorByPage(request);
	}

	@Override
	public DiscoverySuggestSourceTypeVo querySuggestSourceType() {

		List<WorkSourceTypeInfoBo> sourceTypes = suggestSourceMgrCache.getDiscoverSuggestSourceType();

		DiscoverySuggestSourceTypeVo vo = new DiscoverySuggestSourceTypeVo();
		vo.setSourceType(sourceTypes);

		return vo;
	}

	@Override
	public DiscoverySuggestTagInfoVo queryDiscoverSuggestTags(DiscoveryQuerySuggestTagRequest request) {

		return suggestSourceMgrCache.queryDiscoverSuggestTags(request);

	}

	@Override
	public List<DiscoveryHotWorkAlbumVo> getDiscoverHotWorkAlbumByPage(DiscoveryPageQueryRequest request) {

		if (CollectionUtils.isEmpty(request.getSourceTypes()) || CollectionUtils.isEmpty(request.getTags())) {
			return null;
		}

		// 页面传入的只是父类，查询实际所有包含的子类
		List<String> typeList = workSourceTypeDefineDAO.querySourceTypeAndSubType(request.getSourceTypes());
		Assert.notEmpty(typeList);

		List<Pair<Long, Long>> albumList = workAlbumMemberDAO.querySuggestAlbumsByPage(typeList, request.getTags(),
				request.getFromIndex(), request.getLimit());

		if (CollectionUtils.isEmpty(albumList)) {
			return null;
		}

		List<DiscoveryHotWorkAlbumVo> target = new ArrayList<>();

		for (Pair<Long, Long> pair : albumList) {

			DiscoveryHotWorkAlbumVo vo = new DiscoveryHotWorkAlbumVo();
			vo.setAlbumId(pair.getLeft());
			vo.setHotScore(pair.getRight());

			WorkAlbum album = workAlbumDAO.queryById(pair.getLeft());

			Assert.notNull(album);

			vo.setSubject(album.getSubject());

			target.add(vo);
		}

		return target;
	}

}