package com.zhuanquan.app.common.model.work;


/**
 * 作品属性与创作者的映射关系：
 * 
 * 比如，作品多个作者，表示作品和作者的映射关系；多个编剧,表示作品和编剧的关系
 * 
 * @author zhangjun
 *
 */
public class WorkAttrbuteMapping {
	
	
	/**
	 * 作品id
	 */
	private Long workId;
	

	/**
	 * 属性类型
	 */
	private Integer attrType;
	
	/**
	 * 作品属性值 
	 */
	private String value;
	
	/**
	 * 排名
	 */
	private int order;
	
	/**
	 * 状态  0-disable 1-enable
	 */
	private int status;
	


	public Long getWorkId() {
		return workId;
	}

	public void setWorkId(Long workId) {
		this.workId = workId;
	}

	public Integer getAttrType() {
		return attrType;
	}

	public void setAttrType(Integer attrType) {
		this.attrType = attrType;
	}



	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
	
	
	
	
	
}