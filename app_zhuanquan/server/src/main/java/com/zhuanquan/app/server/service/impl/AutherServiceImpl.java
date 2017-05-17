 package com.zhuanquan.app.server.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.zhuanquan.app.common.view.vo.author.SuggestAuthorResponseVo ;
import com.zhuanquan.app.server.service.AutherService;



@Service
public class AutherServiceImpl implements AutherService {
	
	
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	public List<SuggestAuthorResponseVo> getSuggestAuthors(long uid) {
		return null;
	}
	
	
	


	
}