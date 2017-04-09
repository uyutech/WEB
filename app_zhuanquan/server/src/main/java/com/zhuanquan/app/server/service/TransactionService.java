package com.zhuanquan.app.server.service;

import java.util.List;

import com.zhuanquan.app.common.component.sesssion.UserSession;
import com.zhuanquan.app.common.model.user.UserProfile;
import com.zhuanquan.app.common.view.vo.user.LoginByOpenIdRequestVo;
import com.zhuanquan.app.common.view.vo.user.RegisterRequestVo;
import com.zhuanquan.app.common.view.vo.user.RegisterResponseVo;

/**
 * 事务处理的service
 * @author zhangjun
 *
 */
public interface TransactionService {
	
	/**
	 * 手机注册
	 * @param vo
	 * @return
	 */
	RegisterResponseVo registerMobile(RegisterRequestVo vo) ;
	
	/**
	 * 第三方普通账户注册，非大v注册，大v都是预先注册好的
	 * @return
	 */
	UserProfile normalOpenAccountRegister(LoginByOpenIdRequestVo vo);
	
	
	/**
	 * 设置关注的作者
	 * @param uid
	 * @param authorIds
	 */
	void setFollowAuthorsOnRegisterStep3(long uid, List<Long> authorIds);
	
	/**
	 * 注册第二步 设置关注标签和分类
	 * @param uid
	 */
	void setFollowTagOnRegisterStep2(long uid, List<Long> topicTags, List<Long> workCategries);
	
	
	
	
    /**
     * 设置用户关注的标签
     * 
     * @param uid 用户id
     * @param tagIds 标签id
     * @param isInRegister 是否在注册流程
     */
	void setUserFollowTags(long uid,List<Long> tagIds,boolean isInRegister);
}