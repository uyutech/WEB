package com.zhuanquan.app.common.view.vo.author;

import java.io.Serializable;
import java.util.List;

import com.zhuanquan.app.common.view.bo.work.AuthorAlbumInfoBo;

/**
 * 分页查询作者的专辑
 * @author zhangjun
 *
 */
public class AuthorAlbumPageQueryVo implements Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1320001335349097611L;


	/**
	 * 作者id
	 */
	private Long authorId;
	
	
	/**
	 * 从哪个开始
	 */
	private int fromIndex;
	
	/**
	 * 一次查几个
	 */
	private int limit;
	
	
	/**
	 * 专辑list
	 */
	private List<AuthorAlbumInfoBo> albumList;


	public Long getAuthorId() {
		return authorId;
	}


	public void setAuthorId(Long authorId) {
		this.authorId = authorId;
	}


	public int getFromIndex() {
		return fromIndex;
	}


	public void setFromIndex(int fromIndex) {
		this.fromIndex = fromIndex;
	}


	public int getLimit() {
		return limit;
	}


	public void setLimit(int limit) {
		this.limit = limit;
	}


	public List<AuthorAlbumInfoBo> getAlbumList() {
		return albumList;
	}


	public void setAlbumList(List<AuthorAlbumInfoBo> albumList) {
		this.albumList = albumList;
	}
	
	
	
}
