package com.zhuanquan.app.server.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.zhuanquan.app.common.constants.LoginType;
import com.zhuanquan.app.common.exception.BizErrorCode;
import com.zhuanquan.app.common.exception.BizException;
import com.zhuanquan.app.common.model.author.VipAuthorOpenAccountMapping;
import com.zhuanquan.app.common.model.common.Tag;
import com.zhuanquan.app.common.model.user.UserOpenAccount;
import com.zhuanquan.app.common.model.user.UserProfile;
import com.zhuanquan.app.common.model.work.WorkAttender;
import com.zhuanquan.app.common.model.work.WorkBase;
import com.zhuanquan.app.common.model.work.WorkContentSource;
import com.zhuanquan.app.common.model.work.WorkContentSourceExtend;
import com.zhuanquan.app.common.model.work.WorkTagMapping;
import com.zhuanquan.app.common.view.bo.work.WorkTagBo;
import com.zhuanquan.app.common.view.vo.sync.ImportWorkInfoVo;
import com.zhuanquan.app.common.view.vo.sync.MediaSourceInfoVo;
import com.zhuanquan.app.common.view.vo.sync.MediaSourceReleatedAuthorVo;
import com.zhuanquan.app.common.view.vo.user.LoginByOpenIdRequestVo;
import com.zhuanquan.app.common.view.vo.user.MobileRegisterRequestVo;
import com.zhuanquan.app.common.view.vo.work.WorkContentSourceExtendVo;
import com.zhuanquan.app.common.view.vo.work.WorkReferedTagVo;
import com.zhuanquan.app.dal.dao.author.AuthorBaseDAO;
import com.zhuanquan.app.dal.dao.author.VipAuthorOpenAccountMappingDAO;
import com.zhuanquan.app.dal.dao.user.UserFollowAuthorDAO;
import com.zhuanquan.app.dal.dao.user.UserFollowTagsMappingDAO;
import com.zhuanquan.app.dal.dao.user.UserOpenAccountDAO;
import com.zhuanquan.app.dal.dao.user.UserProfileDAO;
import com.zhuanquan.app.dal.dao.work.WorkAttenderDAO;
import com.zhuanquan.app.dal.dao.work.WorkBaseDAO;
import com.zhuanquan.app.dal.dao.work.WorkContentSourceDAO;
import com.zhuanquan.app.dal.dao.work.WorkContentSourceExtendDAO;
import com.zhuanquan.app.dal.dao.work.WorkTagMappingDAO;
import com.zhuanquan.app.server.cache.TagCache;
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
	private UserFollowTagsMappingDAO userFollowTagsMappingDAO;

	@Resource
	private UserOpenAccountCache userOpenAccountCache;

	@Resource
	private VipAuthorOpenAccountMappingDAO vipAuthorOpenAccountMappingDAO;

	@Resource
	private UserFollowAuthorDAO userFollowAuthorDAO;

	@Resource
	private AuthorBaseDAO authorBaseDAO;

	@Resource
	private WorkBaseDAO workBaseDAO;

	@Resource
	private TagCache tagCache;

	@Resource
	private WorkTagMappingDAO workTagMappingDAO;

	@Resource
	private WorkContentSourceDAO workContentSourceDAO;

	@Resource
	private WorkContentSourceExtendDAO workContentSourceExtendDAO;

	@Resource
	private WorkAttenderDAO workAttenderDAO;

	@Override
	@Transactional(rollbackFor = Exception.class)
	public long registerMobile(MobileRegisterRequestVo vo) {

		//
		UserOpenAccount account = userOpenAccountCache.queryByOpenId(vo.getMobile(), LoginType.CHANNEL_MOBILE);

		// 手机号已注册
		if (account != null) {
			throw new BizException(BizErrorCode.EX_BIND_MOBILE_HAS_BIND.getCode());
		}

		// uid创建
		UserProfile profile = UserProfile.createMobileRegisterRecord(vo);
		long uid = userProfileDAO.insertRecord(profile);

		// account创建
		account = UserOpenAccount.createMobileAccount(vo.getMobile(), vo.getPassword(), uid);
		userOpenAccountDAO.insertUserOpenAccount(account);

		return uid;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public UserProfile normalOpenAccountRegister(LoginByOpenIdRequestVo vo) {

		// vip校验
		VipAuthorOpenAccountMapping record = vipAuthorOpenAccountMappingDAO.queryRecordByOpenId(vo.getOpenId(),
				vo.getChannelType());

		UserProfile profile = null;
		// 非大v用户/没有录入作者信息
		if (record == null || record.getAuthorId() == null) {

			profile = UserProfile.registerNormalThirdLoginUser();

		} else {
			profile = UserProfile.registerVipThirdLoginUser(record.getAuthorId());
		}

		long uid = userProfileDAO.insertRecord(profile);

		UserOpenAccount openAccount = UserOpenAccount.createNormalOpenAccount(vo.getOpenId(), vo.getToken(), uid,
				vo.getChannelType());

		userOpenAccountDAO.insertUserOpenAccount(openAccount);

		return profile;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void setUserFollowAuthors(long uid, List<Long> authorIds) {

		// 批量插入更新
		userFollowAuthorDAO.insertBatchFollowAuthorIds(uid, authorIds);

		// 粉丝数增加，需要改成异步
		authorBaseDAO.updateBatchToIncreaseOrDecreaseFans(authorIds, true, 1);

	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void cancelFollowAuthor(long uid, long authorId) {
		// 批量插入
		userFollowAuthorDAO.updateToCancelFollowAuthor(uid, authorId);

		// 粉丝数减少，需要改成异步
		authorBaseDAO.updateBatchToIncreaseOrDecreaseFans(Lists.newArrayList(authorId), false, 1);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void doImportWork(ImportWorkInfoVo vo) {

		// 解析作品基础信息
		WorkBase base = WorkBase.createWorkBase(vo.getSubject(), vo.getSummary(), vo.getCovPicUrl());
		// 获取workid
		long workId = workBaseDAO.insertWorkBaseInfo(base);

		
		List<WorkTagBo> allTag = new ArrayList<WorkTagBo>();
		
		//解析作品标签，针对整个作品的标签 全局的tag
		parseWorkTag(allTag, vo.getTagInfoList(), 0);


		// 作品参与人
		List<WorkAttender> attenderList = new ArrayList<>();

		// 解析出品人信息
		parseWorkAttender(attenderList, vo.getProductorList(), workId, 0, 0);
		// 解析策划信息
		parseWorkAttender(attenderList, vo.getEditorList(), workId, 0, 0);

		// 处理多媒体信息
		parseMediaSource(workId, allTag,attenderList, vo.getMediaSource());

		//所有的tag入库
		saveWorkTag(allTag, workId);
		
		// 统一处理作品相关作者信息。
		//
		workAttenderDAO.insertOrUpdateBatch(attenderList);

	}

	/**
	 * 解析出品人信息
	 * 
	 * @param productorList
	 */
	private void parseWorkAttender(List<WorkAttender> attenderList, List<MediaSourceReleatedAuthorVo> list, long workId,
			long sourceId, int sourceCategory) {

		if (CollectionUtils.isNotEmpty(list)) {

			for (MediaSourceReleatedAuthorVo record : list) {

				WorkAttender workattender = WorkAttender.createInstance(workId, sourceId, sourceCategory,
						record.getRoleType(), record.getAuthorId(), record.getOrderNum(), record.getInspiration());

				attenderList.add(workattender);
			}

		}
	}

	
	private void parseWorkTag(List<WorkTagBo> allList,List<WorkReferedTagVo> workTags,long sourceId){
		

		if (CollectionUtils.isEmpty(workTags)) {
			return;
		}

		
		for(WorkReferedTagVo record:workTags){
			
			WorkTagBo bo = new WorkTagBo();
			
			bo.setOrderNum(record.getOrderNum());
			bo.setSourceId(sourceId);
			bo.setTagId(record.getTagId());
		}
	}
	
	
	
	
	private void saveWorkTag(List<WorkTagBo>  tagList, long workId) {

		if (CollectionUtils.isEmpty(tagList)) {
			return;
		}

		List<WorkTagMapping> list = new ArrayList<>();

		for (WorkTagBo record : tagList) {
			Tag tag = tagCache.getTagById(record.getTagId());

			if (tag != null) {
				WorkTagMapping mapping = WorkTagMapping.createRecord(workId, record.getTagId(), tag.getTagType(),
						record.getOrderNum(),record.getSourceId());
				list.add(mapping);
			}

		}

		if (CollectionUtils.isEmpty(list)) {
			workTagMappingDAO.insertOrUpdateBatch(list);
		}

	}

	/**
	 * 解析多媒体资源
	 */
	private void parseMediaSource(long workId, List<WorkTagBo> allTagList,List<WorkAttender> attenderList, List<MediaSourceInfoVo> mediaSources) {

		if (CollectionUtils.isEmpty(mediaSources)) {
			return;
		}

		for (MediaSourceInfoVo media : mediaSources) {

			WorkContentSource source = WorkContentSource.createInstance(workId, media);

			long sourceId = workContentSourceDAO.insertOrUpdateRecord(source);

			List<MediaSourceReleatedAuthorVo> authorList = media.getAuthorList();
			// 解析具体内容资源的作者信息，放到attenderList里
			parseWorkAttender(attenderList, authorList, workId, sourceId, media.getSourceCategory());

			//解析source里的tag标签
			parseWorkTag(allTagList, media.getSourceReferedTags(), sourceId);
			
			
			// 处理扩展信息
			List<WorkContentSourceExtendVo> extendAttrs = media.getExtendAttrList();

			if (CollectionUtils.isNotEmpty(extendAttrs)) {

				List<WorkContentSourceExtend> extendList = new ArrayList<WorkContentSourceExtend>();

				for (WorkContentSourceExtendVo extend : extendAttrs) {

					WorkContentSourceExtend record = WorkContentSourceExtend.createRecord(sourceId, extend);

					extendList.add(record);
				}

				if (CollectionUtils.isNotEmpty(extendList)) {
					workContentSourceExtendDAO.insertOrUpdateBatch(extendList);
				}

			}

		}

	}

}