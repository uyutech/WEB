package com.zhuanquan.app.dal.dao;


import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Component;

/**
 * 基础Dao接口实现类，实现改类的子类必须设置泛型类型
 */

@Component
public abstract class BaseDao
{
	@Resource
	protected SqlSession sqlSessionTemplate;

	public static final String SQLNAME_SEPARATOR = ".";

	/**
	 * @fields sqlNamespace SqlMapping命名空间 默认为T的全类名
	 */
	private String sqlNamespace = getDefaultSqlNamespace();

	/**
	 * 获取泛型类型的实体对象类全名
	 * 
	 * @return
	 */
	protected String getDefaultSqlNamespace()
	{
		
		return this.getClass().getName();

	}

	/**
	 * 获取SqlMapping命名空间
	 * 
	 */
	public String getSqlNamespace()
	{
		if(sqlNamespace == null) {
			sqlNamespace = getDefaultSqlNamespace();
		}
		
		return sqlNamespace;
	}

	/**
	 * 设置SqlMapping命名空间。 以改变默认的SqlMapping命名空间， 不能滥用此方法随意改变SqlMapping命名空间。
	 * 
	 */
	public void setSqlNamespace(String sqlNamespace)
	{
		this.sqlNamespace = sqlNamespace;
	}

	/**
	 * 将SqlMapping命名空间与给定的SqlMapping名组合在一起。
	 * 
	 * @param sqlName
	 *            SqlMapping名
	 * @return 组合了SqlMapping命名空间后的完整SqlMapping名
	 */
	protected String getSqlName(String sqlName)
	{
		return getSqlNamespace() + SQLNAME_SEPARATOR + sqlName;
	}



}
