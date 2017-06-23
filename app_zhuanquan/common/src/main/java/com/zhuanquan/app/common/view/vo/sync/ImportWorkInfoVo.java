package com.zhuanquan.app.common.view.vo.sync;

import java.util.List;

import com.zhuanquan.app.common.view.vo.work.WorkReferedTagVo;

/**
 * 导入作品信息
 * 
 * 
 * 作品名 
 * 作品标签 可选 
 * 作品简介 可选 
 * 作品封面 可选 
 * 策划 可选 
 * 出品人 可选 
 * 
 * 添加的多媒体资源
 * 
 *        内容类型(音频/视频/图片)
 * 
 *        详细的资源分类(歌曲/广播剧 ，MAD／MMD／PV／MV)
 * 
 *        资源地址(url)
 * 
 *        资源来源渠道(B站/A站/秒拍,网易云/5sing等等)。
 * 
 *        相关作者（角色,作者ID,创作灵感信息）
 * 
 *        其他资源属性(list结构的扩展属性信息，比如点击数，下载数等等)。
 * 
 *        order
 * 
 * 
 * 
 * 
 * @author zhangjun
 *
 */
public class ImportWorkInfoVo {

	/**
	 * 作品标题
	 */
	private String subject;

	/**
	 * 作品标签信息:可选
	 */
	private List<WorkReferedTagVo> tagInfoList;
	
	
	/**
	 * 出品人信息
	 */
	private List<MediaSourceReleatedAuthorVo> productorList;
	
	/**
	 * 策划信息
	 */
	private List<MediaSourceReleatedAuthorVo> editorList;


	/**
	 * 作品简介:可选
	 */
	private String summary;

	/**
	 * 封面图片地址:可选
	 */
	private String covPicUrl;

	/**
	 * 多媒体资源
	 */
	private List<MediaSourceInfoVo> mediaSource;

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public List<WorkReferedTagVo> getTagInfoList() {
		return tagInfoList;
	}

	public void setTagInfoList(List<WorkReferedTagVo> tagInfoList) {
		this.tagInfoList = tagInfoList;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getCovPicUrl() {
		return covPicUrl;
	}

	public void setCovPicUrl(String covPicUrl) {
		this.covPicUrl = covPicUrl;
	}

	public List<MediaSourceInfoVo> getMediaSource() {
		return mediaSource;
	}

	public void setMediaSource(List<MediaSourceInfoVo> mediaSource) {
		this.mediaSource = mediaSource;
	}

	public List<MediaSourceReleatedAuthorVo> getProductorList() {
		return productorList;
	}

	public void setProductorList(List<MediaSourceReleatedAuthorVo> productorList) {
		this.productorList = productorList;
	}

	public List<MediaSourceReleatedAuthorVo> getEditorList() {
		return editorList;
	}

	public void setEditorList(List<MediaSourceReleatedAuthorVo> editorList) {
		this.editorList = editorList;
	}
	
	
	

}