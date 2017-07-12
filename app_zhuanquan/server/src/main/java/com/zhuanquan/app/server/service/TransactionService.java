package com.zhuanquan.app.server.service;

import java.util.List;

import com.zhuanquan.app.common.model.common.RegisterAppointment;
import com.zhuanquan.app.common.model.user.UserProfile;
import com.zhuanquan.app.common.view.vo.sync.ImportWorkInfoVo;
import com.zhuanquan.app.common.view.vo.user.LoginByOpenIdRequestVo;
import com.zhuanquan.app.common.view.vo.user.MobileRegisterRequestVo;

/**
 * 事务处理的service
 * 
 * @author zhangjun
 *
 */
public interface TransactionService {

	/**
	 * 手机注册
	 * 
	 * @param vo
	 * @return
	 */
	long registerMobile(MobileRegisterRequestVo vo);

	/**
	 * 第三方普通账户注册，非大v注册，大v都是预先注册好的
	 * 
	 * @return
	 */
	UserProfile normalOpenAccountRegister(LoginByOpenIdRequestVo vo);
	
	
	/**
	 * 设置关注作者
	 * @param uid
	 * @param authorIds
	 */
	void setUserFollowAuthors(long uid, List<Long> authorIds);
	
	/**
	 * 取消关注
	 * @param uid
	 * @param authorId
	 */
	void cancelFollowAuthor(long uid,long authorId);
	
	/**
	 * 执行作品导入
	 * @param vo
	 */
	void doImportWork(ImportWorkInfoVo vo);
	
	
    
    /**
     * 同步预约同步的数据
     * @param record
     */
    void syncRegisterAppointmentUserData(RegisterAppointment record);
	

}