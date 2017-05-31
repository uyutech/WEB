package com.zhuanquan.app.server.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.zhuanquan.app.common.view.vo.author.SuggestTagResponseVo;
import com.zhuanquan.app.common.view.vo.author.SuggestTagVo;
import com.zhuanquan.app.server.cache.TagCache;
import com.zhuanquan.app.server.service.TagService;


@Service
public class TagServiceImpl implements TagService {


	@Resource
	private TagCache tagCache;
	
	@Override
	public SuggestTagResponseVo getSuggestTags(long uid,int fromIndex,int limit) {
		
		List<SuggestTagVo> list =  tagCache.getSuggestTag(uid,fromIndex,limit);
		
		SuggestTagResponseVo response = new SuggestTagResponseVo();
		
		response.setDataList(list);
		response.setFromIndex(fromIndex);
		response.setUid(uid);
		response.setLimit(limit);
		
		return response;

	}
	
}