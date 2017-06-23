package com.zhuanquan.app.dal.dao.author;

import java.util.List;

import com.zhuanquan.app.common.model.author.AuthorGroupMember;

public interface AuthorGroupMemberDAO {
	
	
	/**
	 * 查询 组合里的 所有作者
	 * @param groupId
	 * @return
	 */
	List<AuthorGroupMember> queryGroupMember(long groupId);
	
	
	
	/**
	 * 查询作者所属组合
	 * @param authorId
	 * @return
	 */
	List<AuthorGroupMember> queryAuthorGroups(long authorId);
	
}