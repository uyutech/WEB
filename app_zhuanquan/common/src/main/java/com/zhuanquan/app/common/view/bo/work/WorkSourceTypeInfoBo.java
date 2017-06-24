package com.zhuanquan.app.common.view.bo.work;



import java.io.Serializable;


/**
 * 作品的类型，比如 广播，电视剧，电影，视频这种
 * @author zhangjun
 *
 */
public class WorkSourceTypeInfoBo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -392985177432230295L;
	
	
	/**
	 * source类型
	 */
	private int sourceType;
	
	/**
	 * source类型的描述
	 */
	private String typeName;

	public int getSourceType() {
		return sourceType;
	}

	public void setSourceType(int sourceType) {
		this.sourceType = sourceType;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	

	
}