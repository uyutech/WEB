package com.zhuanquan.app.common.constants;

public enum AsyncUpdateType {

	// 收藏->作品
	ASYNC_TYPE_FAV(0, "fav"),

	// 关注->作者
	ASYNC_TYPE_FOLLOW(1, "follow"),

	// 点赞->作品
	ASYNC_TYPE_UPVOTE(2, "upvote"),

	;
	/**
	 * 
	 */
	private int type;

	/**
	 * 
	 */
	private String desc;

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;

	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	private AsyncUpdateType(int type, String desc) {

		this.type = type;
		this.desc = desc;
	}

}