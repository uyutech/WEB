package com.zhuanquan.app.server.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.zhuanquan.app.common.view.vo.author.SuggestTagVo;
import com.zhuanquan.app.server.cache.TagCache;
import com.zhuanquan.app.server.service.TagService;


@Service
public class TagServiceImpl implements TagService {


	@Resource
	private TagCache tagCache;
	
	@Override
	public List<SuggestTagVo> getSuggestTags(long uid,int pageNum,int pagesize) {
		
		return tagCache.getSuggestTag(pageNum, pagesize);

	}
	
}