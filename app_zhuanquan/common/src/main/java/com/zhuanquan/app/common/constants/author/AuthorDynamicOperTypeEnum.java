package com.zhuanquan.app.common.constants.author;



/**
 * 作者动态 操作类型的定义
 * @author zhangjun
 *
 */
public enum AuthorDynamicOperTypeEnum {
	
	OPER_TYPE_PUBLISH_MUSIC(100001,"发表了新歌"),
	
	
	;
	
	
	/**
	 * 操作类型
	 */
	private int operType;
	
	/**
	 * 描述
	 */
	private String desc;
	
	
	
	
	public int getOperType() {
		return operType;
	}




	public void setOperType(int operType) {
		this.operType = operType;
	}




	public String getDesc() {
		return desc;
	}




	public void setDesc(String desc) {
		this.desc = desc;
	}




	private AuthorDynamicOperTypeEnum(int operType,String desc ){
		
		
		this.operType = operType;
		
		this.desc = desc;
	}
	
	
	
	
	/**
	 * 获取操作类型的描述
	 * @param operType
	 * @return
	 */
	public static String getOperTypeDesc(int operType) {
		
		
		AuthorDynamicOperTypeEnum[] records = AuthorDynamicOperTypeEnum.values();
		
		
		for(AuthorDynamicOperTypeEnum record:records) {
			if(record.getOperType() == operType) {
				return record.getDesc();
			}
		}
		
		return null;
		
	}
}