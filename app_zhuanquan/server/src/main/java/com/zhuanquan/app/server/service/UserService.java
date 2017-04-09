package com.zhuanquan.app.server.service;

import java.util.List;

import com.zhuanquan.app.common.model.author.Tag;
import com.zhuanquan.app.common.model.user.UserProfile;

/**
 * user service
 * @author zhangjun
 *
 */
public interface UserService {
	
	/**
	 * 根据uid查询信息
	 * @param uid
	 * @return
	 */
	UserProfile queryUserProfileByUid(long uid);
	
    /**
     * 设置用户关注的标签
     * 
     * @param uid 用户id
     * @param tagIds 标签id
     * @param isInRegister 是否在注册流程
     */
	void setUserFollowTags(long uid,List<Long> tagIds,boolean isInRegister);
	
}