package com.zhuanquan.app.dal.dao.work;

import java.util.List;

import com.zhuanquan.app.common.model.work.WorkAttender;
import com.zhuanquan.app.common.view.bo.author.AuthorPartnerInfoBo;

/**
 * 作品参与者
 * 
 * @author zhangjun
 *
 */
public interface WorkAttenderDAO {

	/**
	 * 批量处理
	 * 
	 * @param list
	 */
	void insertOrUpdateBatch(List<WorkAttender> list);

	/**
	 * 查询作品参与者
	 * 
	 * @param workId
	 * @return
	 */
	List<WorkAttender> queryWorkAttender(long workId);

	/**
	 * 查询作者参与的作品列表
	 * 
	 * @param authorId
	 * @return
	 */
	List<Long> queryAuthorAttendWorkIds(long authorId);

	/**
	 * 查询作者 所有的角色code
	 * 
	 * @param authorId
	 * @return
	 */
	List<String> queryAuthorRoleCodesByAuthorId(long authorId);

	/**
	 * 查询 作者的合作情况
	 * 
	 * @param authorId
	 *            作者id
	 * @param workIds
	 *            作者参与的作品id
	 * @return
	 */
	List<AuthorPartnerInfoBo> queryAuthorPartnerInfo(long authorId, List<Long> workIds);

}