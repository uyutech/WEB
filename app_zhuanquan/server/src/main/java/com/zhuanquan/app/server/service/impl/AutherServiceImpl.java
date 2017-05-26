 package com.zhuanquan.app.server.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.zhuanquan.app.common.constants.RegisterFlowConstants;
import com.zhuanquan.app.common.model.author.VipAuthorOpenAccountMapping;
import com.zhuanquan.app.common.view.vo.author.SuggestAuthorRequestVo;
import com.zhuanquan.app.common.view.vo.author.SuggestAuthorResponseVo ;
import com.zhuanquan.app.common.view.vo.author.SuggestAuthorUnit;
import com.zhuanquan.app.dal.dao.author.VipAuthorOpenAccountMappingDAO;
import com.zhuanquan.app.server.service.AutherService;



@Service
public class AutherServiceImpl implements AutherService {
	
	
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	
	@Resource
	private VipAuthorOpenAccountMappingDAO vipAuthorOpenAccountMappingDAO;
	
	@Override
	public SuggestAuthorResponseVo getSuggestAuthors(SuggestAuthorRequestVo vo) {
		

		int batchLimit = RegisterFlowConstants.REG_SUGGEST_AUTHOR_BATCH_NUM_LIMIT;
		

		List<String> openIds = vo.getFollowOpenIds();
		
		List<Long> followedIds = new ArrayList<Long>();
		
		//openid
		if(CollectionUtils.isNotEmpty(openIds)) {
			
			List<VipAuthorOpenAccountMapping> vipList = vipAuthorOpenAccountMappingDAO.queryRecordListByOpenIds(vo.getFollowOpenIds(), vo.getChannelType());
			
			//list 不为空
			if(CollectionUtils.isNotEmpty(vipList)){
				
				//
                 int startIndex = (vo.getPage()-1)*batchLimit;
                 
                 int size = 0;
                 for(int i= startIndex;i<vipList.size();i++) {
                	 followedIds.add(vipList.get(i).getAuthorId());
                	 size++;
                	 
                	 if(size>=batchLimit) {
                		 break;
                	 }
                 }
			}
		}
		
	


		return getTestData(vo.getPage(), vo.getUid());
	}
	
	

	
	private SuggestAuthorResponseVo getTestData(int page,long uid){
	
		
		SuggestAuthorResponseVo vo = new SuggestAuthorResponseVo();
		
		vo.setPage(1);
		vo.setUid(1000L);
		
		
		List<SuggestAuthorUnit> list =new ArrayList<SuggestAuthorUnit>();
		
		
		for(int i=1;i<10;i++){
		SuggestAuthorUnit record = new SuggestAuthorUnit();
		record.setAuthorId(i);
		record.setAuthorName("author:"+record.getAuthorId());
		record.setHeadUrl("http://avsadasda.gif");
		record.setIsDefaultFollow(i<4?1:0);
		
		list.add(record);
		}
		vo.setSuggestAuthors(list);
		
		return vo;
	}

	
}