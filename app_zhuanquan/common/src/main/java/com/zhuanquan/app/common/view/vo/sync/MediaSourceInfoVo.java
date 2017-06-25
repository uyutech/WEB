package com.zhuanquan.app.common.view.vo.sync;

import java.io.Serializable;
import java.util.List;
import com.zhuanquan.app.common.view.vo.work.WorkContentSourceExtendVo;
import com.zhuanquan.app.common.view.vo.work.WorkReferedTagVo;

/**
 * 
 * @author zhangjun
 *
 */
public class MediaSourceInfoVo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 336862740948944117L;

	/**
	 * 多媒体资源类型：10001-音频 10002- 视频 10003-图片
	 * 
	 * @see WorkSourceCategoryConstants
	 */
	private int sourceCategory;

	/**
	 * 具体的资源类型 歌曲/广播剧 ，MAD／MMD／PV／MV
	 */
	private String sourceType;

	/**
	 * 排序字段
	 */
	private int orderNum;

	/**
	 * 链接地址
	 */
	private String sourceUrl;

	/**
	 * 资源渠道(B站/A站/秒拍,网易云/5sing等等)
	 */
	private int sourceChannel;
	
	/**
	 * 如果是同人作品，需要设置原作品id
	 */
	private long originSourceId;

	/**
	 * 作者列表
	 */
	private List<MediaSourceReleatedAuthorVo> authorList;
	
	
	/**
	 * 具体资源关联的标签
	 */
	private List<WorkReferedTagVo> sourceReferedTags;

	/**
	 * 扩展属性的list 其他资源属性(list结构的扩展属性信息，比如点击数，下载数等等)。
	 * 
	 */
	private List<WorkContentSourceExtendVo> extendAttrList;

	public int getSourceCategory() {
		return sourceCategory;
	}

	public void setSourceCategory(int sourceCategory) {
		this.sourceCategory = sourceCategory;
	}

	public String getSourceType() {
		return sourceType;
	}

	public void setSourceType(String sourceType) {
		this.sourceType = sourceType;
	}

	public int getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(int orderNum) {
		this.orderNum = orderNum;
	}

	public String getSourceUrl() {
		return sourceUrl;
	}

	public void setSourceUrl(String sourceUrl) {
		this.sourceUrl = sourceUrl;
	}

	public int getSourceChannel() {
		return sourceChannel;
	}

	public void setSourceChannel(int sourceChannel) {
		this.sourceChannel = sourceChannel;
	}


	public List<WorkContentSourceExtendVo> getExtendAttrList() {
		return extendAttrList;
	}

	public void setExtendAttrList(List<WorkContentSourceExtendVo> extendAttrList) {
		this.extendAttrList = extendAttrList;
	}

	public List<MediaSourceReleatedAuthorVo> getAuthorList() {
		return authorList;
	}

	public void setAuthorList(List<MediaSourceReleatedAuthorVo> authorList) {
		this.authorList = authorList;
	}

	public long getOriginSourceId() {
		return originSourceId;
	}

	public void setOriginSourceId(long originSourceId) {
		this.originSourceId = originSourceId;
	}

	public List<WorkReferedTagVo> getSourceReferedTags() {
		return sourceReferedTags;
	}

	public void setSourceReferedTags(List<WorkReferedTagVo> sourceReferedTags) {
		this.sourceReferedTags = sourceReferedTags;
	}
	
	

}
