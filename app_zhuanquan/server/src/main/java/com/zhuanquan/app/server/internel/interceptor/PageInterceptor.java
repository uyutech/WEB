
package com.zhuanquan.app.server.internel.interceptor;

import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.executor.statement.RoutingStatementHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.scripting.defaults.DefaultParameterHandler;
import com.zhuanquan.app.common.component.db.pagination.PageLocal;
import com.zhuanquan.app.common.component.db.pagination.Pagination;
import com.zhuanquan.app.common.utils.ReflectHelper;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

/**
 * 
 * 
 * 使用MyBatis 3.4.1或者其以上版本
 * 
 * @Intercepts({ @Signature(type = StatementHandler. class, method = "prepare",
 *               args = {Connection. class, Integer.class})})
 * 
 *               使用MyBatis 3.4.1(不包含)以下 @Intercepts({ @Signature(type =
 *               StatementHandler. class, method = "prepare", args =
 *               {Connection. class})})
 * @author zhangjun
 *
 */
@Intercepts({ @Signature(type = StatementHandler.class, method = "prepare", args = { Connection.class }) })
public class PageInterceptor implements Interceptor {

	private String dialect = "postgre"; // 数据库方言

	@Override
	public Object intercept(Invocation invocation) throws Throwable {

		Pagination pagination = PageLocal.getPagination();

		// 为null不需要分页
		if (pagination == null) {
			return invocation.proceed();
		}

		RoutingStatementHandler statementHandler = (RoutingStatementHandler) invocation.getTarget();

		StatementHandler delegate = (StatementHandler) ReflectHelper.getFieldValue(statementHandler, "delegate");
		BoundSql boundSql = delegate.getBoundSql();

		// 通过反射获取delegate父类BaseStatementHandler的mappedStatement属性
		MappedStatement mappedStatement = (MappedStatement) ReflectHelper.getFieldValue(delegate, "mappedStatement");

		// 拦截到的prepare方法参数是一个Connection对象
		Connection connection = (Connection) invocation.getArgs()[0];

		// 获取当前要执行的Sql语句，也就是我们直接在Mapper映射语句中写的Sql语句
		String sql = boundSql.getSql();

		// 以分号结尾,去掉分号
		sql = sqlOptimization(sql);

		// 给当前的page参数对象设置总记录数
		this.setTotalRecord(pagination, mappedStatement, connection);

		// 获取分页Sql语句
		String pageSql = this.getPageSql(pagination, sql);
		// 利用反射设置当前BoundSql对应的sql属性为我们建立好的分页Sql语句
		ReflectHelper.setFieldValue(boundSql, "sql", pageSql);

		return invocation.proceed();

	}

	@Override
	public void setProperties(Properties properties) {

		System.out.println("setProperties~~~~~~~~~~~~~~~~~~~~`");

	}

	/**
	 * 给当前的参数对象page设置总记录数
	 * 
	 * @param page
	 *            Mapper映射语句对应的参数对象
	 * @param mappedStatement
	 *            Mapper映射语句
	 * @param connection
	 *            当前的数据库连接
	 */
	private void setTotalRecord(Pagination page, MappedStatement mappedStatement, Connection connection) {
		// 如果不需要count
		if (!page.isNeedCount()) {
			return;
		}

		// 获取对应的BoundSql，这个BoundSql其实跟我们利用StatementHandler获取到的BoundSql是同一个对象。
		// delegate里面的boundSql也是通过mappedStatement.getBoundSql(paramObj)方法获取到的。
		BoundSql boundSql = mappedStatement.getBoundSql(page);
		// 获取到我们自己写在Mapper映射语句中对应的Sql语句
		String sql = boundSql.getSql();
		sql = sqlOptimization(sql);

		// 通过查询Sql语句获取到对应的计算总记录数的sql语句
		String countSql = this.getCountSql(sql);
		// 通过BoundSql获取对应的参数映射
		List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
		// 利用Configuration、查询记录数的Sql语句countSql、参数映射关系parameterMappings和参数对象page建立查询记录数对应的BoundSql对象。
		BoundSql countBoundSql = new BoundSql(mappedStatement.getConfiguration(), countSql, parameterMappings, page);
		// 通过mappedStatement、参数对象page和BoundSql对象countBoundSql建立一个用于设定参数的ParameterHandler对象
		ParameterHandler parameterHandler = new DefaultParameterHandler(mappedStatement, page, countBoundSql);
		// 通过connection建立一个countSql对应的PreparedStatement对象。
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = connection.prepareStatement(countSql);
			// 通过parameterHandler给PreparedStatement对象设置参数
			parameterHandler.setParameters(pstmt);
			// 之后就是执行获取总记录数的Sql语句和获取结果了。
			rs = pstmt.executeQuery();
			if (rs.next()) {
				int totalRecord = rs.getInt(1);

				// 给当前的参数page对象设置总记录数
				PageLocal.setCountPair(totalRecord);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 根据原Sql语句获取对应的查询总记录数的Sql语句
	 * 
	 * @param sql
	 * @return
	 */
	private String getCountSql(String sql) {

		StringBuilder stringBuilder = new StringBuilder(sql.length() + 40);
		stringBuilder.append("select count(1) from (");
		stringBuilder.append(sql);
		stringBuilder.append(") tmp_count");

		return stringBuilder.toString();
	}

	/**
	 * 根据page对象获取对应的分页查询Sql语句，
	 * 
	 * @param page
	 *            分页对象
	 * @param sql
	 *            原sql语句
	 * @return
	 */
	private String getPageSql(Pagination page, String sql) {
		StringBuffer sqlBuffer = new StringBuffer(sql);

		if ("postgre".equalsIgnoreCase(dialect)) {
			return getPostgrePageSql(page, sqlBuffer);
		} else if ("mysql".equalsIgnoreCase(dialect)) {
			return getMysqlPageSql(page, sqlBuffer);
		} else if ("oracle".equalsIgnoreCase(dialect)) {
			return getOraclePageSql(page, sqlBuffer);
		}
		return sqlBuffer.toString();
	}

	/**
	 * 获取Mysql数据库的分页查询语句
	 * 
	 * @param page
	 *            分页对象
	 * @param sqlBuffer
	 *            包含原sql语句的StringBuffer对象
	 * @return Mysql数据库分页语句
	 */
	private String getMysqlPageSql(Pagination page, StringBuffer sqlBuffer) {
		// 计算第一条记录的位置，Mysql中记录的位置是从0开始的。
		// System.out.println("page:"+page.getPage()+"-------"+page.getRows());
		int offset = (page.getPageIndex() - 1) * page.getPageLength();
		sqlBuffer.append(" limit ").append(offset).append(",").append(page.getPageLength());
		return sqlBuffer.toString();
	}

	/**
	 * 获取postgre数据库的分页查询语句
	 * 
	 * @param page
	 *            分页对象
	 * @param sqlBuffer
	 *            包含原sql语句的StringBuffer对象
	 * @return postgre数据库分页语句
	 */
	private String getPostgrePageSql(Pagination page, StringBuffer sqlBuffer) {
		// 计算第一条记录的位置。
		int offset = (page.getPageIndex() - 1) * page.getPageLength();

		sqlBuffer.append(" LIMIT ").append(page.getPageLength());

		if (offset > 0) {
			sqlBuffer.append(" OFFSET ").append(offset);
		}
		return sqlBuffer.toString();
	}

	/**
	 * 获取Oracle数据库的分页查询语句
	 * 
	 * @param page
	 *            分页对象
	 * @param sqlBuffer
	 *            包含原sql语句的StringBuffer对象
	 * @return Oracle数据库的分页查询语句
	 */
	private String getOraclePageSql(Pagination page, StringBuffer sqlBuffer) {
		// 计算第一条记录的位置，Oracle分页是通过rownum进行的，而rownum是从1开始的
		int offset = (page.getPageIndex() - 1) * page.getPageLength() + 1;
		sqlBuffer.insert(0, "select u.*, rownum r from (").append(") u where rownum < ")
				.append(offset + page.getPageLength());
		sqlBuffer.insert(0, "select * from (").append(") where r >= ").append(offset);
		// 上面的Sql语句拼接之后大概是这个样子：
		// select * from (select u.*, rownum r from (select * from t_user) u
		// where rownum < 31) where r >= 16
		return sqlBuffer.toString();
	}

	/**
	 * 拦截器对应的封装原始对象的方法
	 */
	public Object plugin(Object arg0) {
		if (arg0 instanceof StatementHandler) {
			return Plugin.wrap(arg0, this);
		} else {
			return arg0;
		}
	}

	/**
	 * sql优化
	 * 
	 * @param sql
	 * @return
	 */
	private String sqlOptimization(String sql) {
		// 以分号结尾,去掉分号
		if (sql.trim().endsWith(";")) {
			sql = sql.trim().replaceAll(";", "");
		}

		return sql;
	}

}
