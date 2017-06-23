package com.zhuanquan.app.common.view.vo.discovery;

import java.io.Serializable;
import java.util.List;

/**
 * 发现页
 * 
 * @author zhangjun
 *
 */
public class DiscoveryInfoVo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8616999128191464256L;
	
	
	
	
	/**
	 * 热点作品
	 */
	private List<DiscoveryHotWorkVo> hotWorks;

	
	/**
	 * 热点专辑
	 */
	private List<DiscoveryHotWorkAlbumVo> hotAlbums;
	
	/**
	 * 热点作者
	 */
	private List<DiscoveryHotAuthorVo> hotAuthors;

	public List<DiscoveryHotWorkVo> getHotWorks() {
		return hotWorks;
	}

	public void setHotWorks(List<DiscoveryHotWorkVo> hotWorks) {
		this.hotWorks = hotWorks;
	}

	public List<DiscoveryHotWorkAlbumVo> getHotAlbums() {
		return hotAlbums;
	}

	public void setHotAlbums(List<DiscoveryHotWorkAlbumVo> hotAlbums) {
		this.hotAlbums = hotAlbums;
	}

	public List<DiscoveryHotAuthorVo> getHotAuthors() {
		return hotAuthors;
	}

	public void setHotAuthors(List<DiscoveryHotAuthorVo> hotAuthors) {
		this.hotAuthors = hotAuthors;
	}
	
	
	
	
	
	

}