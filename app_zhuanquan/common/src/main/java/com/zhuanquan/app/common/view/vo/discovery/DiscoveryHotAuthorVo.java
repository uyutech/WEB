package com.zhuanquan.app.common.view.vo.discovery;


import java.io.Serializable;
import java.util.List;

/**
 * 热门作者信息
 * @author zhangjun
 *
 */
public class DiscoveryHotAuthorVo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8014198954050920478L;

	/**
	 * 作者id
	 */
	private long authorId;
	
	
	/**
	 * 头像
	 */
	private String headUrl;
	
	
	/**
	 * 作者名
	 */
	private String authorName;
	
	
//	/**
//	 * 角色列表:比如策划，歌手等等
//	 */
//	private List<Integer> roleTypes;
	
	/**
	 * 热度
	 */
	private long score;
	
	
	


	public long getScore() {
		return score;
	}


	public void setScore(long score) {
		this.score = score;
	}


	public long getAuthorId() {
		return authorId;
	}


	public void setAuthorId(long authorId) {
		this.authorId = authorId;
	}


	public String getHeadUrl() {
		return headUrl;
	}


	public void setHeadUrl(String headUrl) {
		this.headUrl = headUrl;
	}


	public String getAuthorName() {
		return authorName;
	}


	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}

//
//	public List<Integer> getRoleTypes() {
//		return roleTypes;
//	}
//
//
//	public void setRoleTypes(List<Integer> roleTypes) {
//		this.roleTypes = roleTypes;
//	}
//	
//	
//	
	
	
}