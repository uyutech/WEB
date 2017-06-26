package com.zhuanquan.app.common.constants.work;


/**
 * 里程碑属性
 * @author zhangjun
 *
 */
public enum WorkMilestoneAttrConstants {
	
	
	ATTR_SCHEDULE(0,"开坑"),
	
	ATTR_FORESHOW (1,"预告"),
	
	ATTR_PUBLISH(2,"发布"),
	
	;
	
	/**
	 * 
	 */
	private int mileAttr;
	
	/**
	 * 
	 */
	private String mileDesc;
	
	
	
	public int getMileAttr() {
		return mileAttr;
	}



	public void setMileAttr(int mileAttr) {
		this.mileAttr = mileAttr;
	}



	public String getMileDesc() {
		return mileDesc;
	}



	public void setMileDesc(String mileDesc) {
		this.mileDesc = mileDesc;
	}



	private WorkMilestoneAttrConstants(int attr,String desc){
		
		mileAttr = attr;
		
		mileDesc = desc;
	}
	
	
	
	/**
	 * 获取属性名
	 * @param mileAttr
	 * @return
	 */
	public static String getAttrDesc(int mileAttr) {
		
		WorkMilestoneAttrConstants[] attrs = WorkMilestoneAttrConstants.values();
		
		for(WorkMilestoneAttrConstants attr:attrs) {
			
			if(attr.getMileAttr()==mileAttr) {
				return attr.getMileDesc();
			}
		}
		
		
		return null;
		
	}
	
	
	
}