package com.zhuanquan.app.server.service.impl;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zhuanquan.app.common.constants.ChannelType;
import com.zhuanquan.app.common.exception.BizErrorCode;
import com.zhuanquan.app.common.exception.BizException;
import com.zhuanquan.app.common.model.author.Tag;
import com.zhuanquan.app.common.model.user.UserFollowTagsMapping;
import com.zhuanquan.app.common.model.user.UserOpenAccount;
import com.zhuanquan.app.common.model.user.UserProfile;
import com.zhuanquan.app.common.view.vo.user.LoginByOpenIdRequestVo;
import com.zhuanquan.app.common.view.vo.user.RegisterRequestVo;
import com.zhuanquan.app.common.view.vo.user.RegisterResponseVo;
import com.zhuanquan.app.dal.dao.author.TagDAO;
import com.zhuanquan.app.dal.dao.user.UserFollowAuthorDAO;
import com.zhuanquan.app.dal.dao.user.UserFollowTagsMappingDAO;
import com.zhuanquan.app.dal.dao.user.UserOpenAccountDAO;
import com.zhuanquan.app.dal.dao.user.UserProfileDAO;
import com.zhuanquan.app.server.cache.UserOpenAccountCache;
import com.zhuanquan.app.server.service.TransactionService;



@Service
public class TransactionServiceImpl implements TransactionService {

	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	
	@Resource
	private UserProfileDAO userProfileDAO;
	
	
	@Resource
	private UserOpenAccountDAO userOpenAccountDAO;
	
	
	@Resource
	private UserFollowAuthorDAO userFollowAuthorDAO;
	
	@Resource
	private TagDAO tagDAO;
	
	@Resource
	private UserFollowTagsMappingDAO userFollowTagsMappingDAO;
	
	@Resource
	private UserOpenAccountCache userOpenAccountCache;
	
	
	@Override
    @Transactional(rollbackFor = Exception.class)
	public RegisterResponseVo registerMobile(RegisterRequestVo vo) {
		
		//
		
		UserOpenAccount account = userOpenAccountCache.queryByOpenId(vo.getProfile(), ChannelType.CHANNEL_MOBILE);
		
		//手机号已注册
		if(account != null) {
			throw new BizException(BizErrorCode.EX_BIND_MOBILE_HAS_BIND.getCode());
		}

		
		//uid创建
		UserProfile profile = UserProfile.createMobileRegisterRecord(vo);
		long uid = userProfileDAO.insertRecord(profile);

		//account创建
		account = UserOpenAccount.createMobileAccount(vo.getProfile(), vo.getPassword(), uid);
		userOpenAccountDAO.insertUserOpenAccount(account);

		
		RegisterResponseVo response = new RegisterResponseVo();

		response.setUid(uid);

		return response;
	}


	@Override
    @Transactional(rollbackFor = Exception.class)
	public UserProfile normalOpenAccountRegister(LoginByOpenIdRequestVo vo) {

		//创建normal account
		UserProfile profile = UserProfile.registerThirdLoginUser();
		
		long uid = userProfileDAO.insertRecord(profile);
		
		UserOpenAccount openAccount = UserOpenAccount.createNormalOpenAccount(vo.getOpenId(), vo.getToken(), uid,vo.getChannelType());
		
		userOpenAccountDAO.insertUserOpenAccount(openAccount);

		return profile;
	}


	@Override
    @Transactional(rollbackFor = Exception.class)	
	public void setFollowAuthorsOnRegisterStep3(long uid, List<Long> authorIds) {
		

		userFollowAuthorDAO.insertBatchFollowAuthorIds(uid, authorIds);
		
		//第三步注册完了，设置状态为normal，
		userProfileDAO.updateRegisterStatus(uid, UserProfile.REG_STAT_NORMAL);
	}


	@Override
    @Transactional(rollbackFor = Exception.class)	
	public void setFollowTagOnRegisterStep2(long uid, List<Long> topicTags, List<Long> workCategries) {
		
		
		//设置关注的话题tag
		setUserFollowTags(uid, topicTags, true);
		
		//设置关注的作品分类
		setUserFollowTags(uid, workCategries, true);

		
		
		//完成第二步，设置状态为第三步等待执行
		userProfileDAO.updateRegisterStatus(uid, UserProfile.REG_STAT_BEFORE_STEP3);
		
	}


	@Override
	public void setUserFollowTags(long uid, List<Long> tagIds, boolean isInRegister) {

		if(CollectionUtils.isEmpty(tagIds)){
			return;
		}
		
		List<Tag> tags = tagDAO.queryTagsByIds(tagIds);
		
		if(CollectionUtils.isEmpty(tags)){
			return;
		}
		
		
		//size 不相等,说明有部分tag是非法的
		if(tagIds.size()!=tags.size()){
			throw new BizException(BizErrorCode.EX_ILLEGLE_REQUEST_PARM.getCode());
		}
		
		//刚注册，数据是干净的，直接插入；否则可能要考虑某些要做update操作
		if(isInRegister) {	

			userFollowTagsMappingDAO.insertBatchToFollowTags(uid, transferToUserFollowTags(uid, tags));
			return;
		}
		
		
		//根据tag ids查询
		Map<String,String> map = userFollowTagsMappingDAO.queryByTagIds(uid, tagIds);
		
		//如果为空，那么说明这些都没被关注过，直接insert
		if(MapUtils.isEmpty(map)){
			userFollowTagsMappingDAO.insertBatchToFollowTags(uid, transferToUserFollowTags(uid, tags));
			return;
		}
		
		
		List<UserFollowTagsMapping> insertList = new ArrayList<UserFollowTagsMapping>();
		
		List<Long> updateList = new ArrayList<Long>();

		
		for(Tag tag:tags) {

			String value = map.get(tag.getTagId().toString());
			
			if(value == null) {
				insertList.add(UserFollowTagsMapping.transferToMapping(uid, tag));
			} else {
				updateList.add(tag.getTagId());
			}
		}
		
		//部分insert
		userFollowTagsMappingDAO.insertBatchToFollowTags(uid, insertList);
		
        //部分update
		userFollowTagsMappingDAO.updateBatchToFollowTags(uid, updateList);
		
		
	}
	
	
	/**
	 * 对象转化
	 * @param uid
	 * @param tags
	 * @return
	 */
	private List<UserFollowTagsMapping> transferToUserFollowTags(long uid,List<Tag> tags) {
		
		if(CollectionUtils.isEmpty(tags)) {
			return null;
		}
		
		List<UserFollowTagsMapping> list =new ArrayList<UserFollowTagsMapping>();
		for(Tag tag:tags) {
			list.add(UserFollowTagsMapping.transferToMapping(uid, tag));
		}
		
		return list;
	}
	
	
	
}