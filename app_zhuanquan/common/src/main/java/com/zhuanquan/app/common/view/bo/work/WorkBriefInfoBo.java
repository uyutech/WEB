package com.zhuanquan.app.common.view.bo.work;


import java.io.Serializable;

/**
 * 作品基本信息
 * @author zhangjun
 *
 */
public class WorkBriefInfoBo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4979816331534360600L;
	
	
    /**
     * 作品id
     */
	private long workId;
	
	/**
	 * 作品名
	 */
	private String workName;
	
	/**
	 * 封面
	 */
	private String covPic;
	
	

	public long getWorkId() {
		return workId;
	}

	public void setWorkId(long workId) {
		this.workId = workId;
	}

	public String getWorkName() {
		return workName;
	}

	public void setWorkName(String workName) {
		this.workName = workName;
	}

	public String getCovPic() {
		return covPic;
	}

	public void setCovPic(String covPic) {
		this.covPic = covPic;
	}
	
	
	
	
	
	
}