package com.zhuanquan.app.common.component.db.pagination;

public class PageLocal {
	

	/**
	 * 分页参数
	 */
	private static ThreadLocal<Pagination> pageParmLocal = new ThreadLocal<Pagination>();
	
	
	/**
	 * 设置 总记录数
	 */
	private static ThreadLocal<Integer> countLocal = new ThreadLocal<Integer>();

	

	
	/**
	 * 设置分页参数
	 * @param pagination
	 */
	public static void setPagination(Pagination pagination) {
		
		pageParmLocal.set(pagination);
	}
	
	
	/**
	 * 获取分页参数
	 * @return
	 */
	public static Pagination getPagination() {
				
		return pageParmLocal.get();
	}	
	
	
	/**
	 * 设置 总记录数
	 * @param 
	 */
	public static void setCountPair(int count) {
		
		countLocal.set(count);
	}
	
	
	/**
	 * 获取总记录数
	 * @return
	 */
	public static int getCountPair() {
		
		return countLocal.get();
	}
		
	
	
	public static void clearLocal(){
		
		
		pageParmLocal.remove();
		
		countLocal.remove();
	}
	
	
}