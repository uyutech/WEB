package com.zhuanquan.app.common.view.vo.discovery;



import java.io.Serializable;
import java.util.List;

/**
 * 发现页面查询推荐标签的请求
 * @author zhangjun
 *
 */
public class DiscoveryQuerySuggestTagRequest implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6648284864491449528L;
	
	
	/**
	 * 用户id
	 */
	private long uid;
	
	
	/**
	 * 资源类型id，比如 广播剧，视频这种
	 */
	private List<Integer> ids;


	public long getUid() {
		return uid;
	}


	public void setUid(long uid) {
		this.uid = uid;
	}


	public List<Integer> getIds() {
		return ids;
	}


	public void setIds(List<Integer> ids) {
		this.ids = ids;
	}
	
	
}