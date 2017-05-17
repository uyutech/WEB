package com.zhuanquan.app.dal.dao;


import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Component;

import com.zhuanquan.app.common.component.event.redis.RedisCacheCleanManager;
import com.zhuanquan.app.common.component.event.redis.RedisCacheEnum;

/**
 * 基础Dao接口实现类，实现改类的子类必须设置泛型类型
 */

@Component
public abstract class BaseDao
{
	@Resource
	protected SqlSession sqlSessionTemplate;
	
	@Resource
	protected RedisCacheCleanManager redisCacheCleanManager;

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


	/**
	 * 通知清理缓存
	 * @param cache
	 * @param parmMap
	 */
	protected void notifyCacheClean(RedisCacheEnum cache,Map<String,String> parmMap) {
		
		try {
			redisCacheCleanManager.sendCleanEvent(cache, parmMap);
			
		} catch(Exception e) {
			
			e.printStackTrace();
		}
		
	}
	
	
	//拼接in语句
	public String listToString(List<Long> list)
	{
		
		if(CollectionUtils.isEmpty(list)) {
			return null;
		}

		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < list.size(); i++)
		{
			sb.append(list.get(i)).append(",");
		}
		String newStr = sb.toString().substring(0, sb.toString().length() - 1);
		return newStr;
	}



}
