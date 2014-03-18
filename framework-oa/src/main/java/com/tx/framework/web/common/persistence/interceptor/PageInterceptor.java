package com.tx.framework.web.common.persistence.interceptor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Properties;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.executor.statement.BaseStatementHandler;
import org.apache.ibatis.executor.statement.RoutingStatementHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.scripting.defaults.DefaultParameterHandler;
import org.springframework.stereotype.Component;

import com.tx.framework.web.common.persistence.entity.Page;

/**
 * 
 * <p>
 * 判断如果参数中有 {@link Page} 对象，那么执行分页查询。(1.查询总数并放入page对象中。 2.构造带有limit子句的sql替换原始的sql)
 * </p>
 * <p>
 * 目前只支持把page放到HashMap中(或使用接口时，把page作为方法的参数),并且key为"page"
 * </p>
 * 
 * @author tangx
 */
@Intercepts({ @Signature(type = StatementHandler.class, method = "prepare", args = { Connection.class }) })
@Component
public class PageInterceptor implements Interceptor {

	public static final String PAGE_KEY = "page";
	
	/**hsqldb,mysql,oracle...*/
	private String dialect = "mysql";

	public Object intercept(Invocation invocation) throws Throwable {
		if (invocation.getTarget() instanceof RoutingStatementHandler) {
			RoutingStatementHandler statementHandler = (RoutingStatementHandler) invocation.getTarget();
			StatementHandler handler = (StatementHandler) FieldUtils.readField(statementHandler, "delegate", true);
			MappedStatement ms = (MappedStatement) FieldUtils.readField(handler, "mappedStatement", true);
			BoundSql bs = handler.getBoundSql();
			Object param = bs.getParameterObject();
			String sql = bs.getSql();

			if (param instanceof HashMap) {

				HashMap map = (HashMap) param;
				Page<?> p = (Page<?>) map.get(PAGE_KEY);
				if (p != null) {
					p.setTotal(queryTotal(invocation, ms, bs, param, sql));
					FieldUtils.writeField(bs, "sql", pageSql(sql, p), true);
				}

			}
		}
		return invocation.proceed();
	}


	/**
	 * 生成特定数据库的分页语句
	 * 
	 * @param sql
	 * @param page
	 * @return
	 */
	private String pageSql(String sql, Page page) {
		if (page == null || dialect == null || dialect.equals("")) {
			return sql;
		}

		StringBuilder sb = new StringBuilder();
		if ("hsqldb".equals(dialect)) {
			String s = sql;
			sb.append("select limit ");
			sb.append(page.getCurrentResult());
			sb.append(" ");
			sb.append(page.getSize());
			sb.append(" ");
			sb.append(s.substring(6));
		} else if ("mysql".equals(dialect)) {
			sb.append(sql);
			sb.append(" limit " + page.getCurrentResult() + ","
					+ page.getSize());
		} else if ("oracle".equals(dialect)) {
			sb.append("select * from (select tmp_tb.*,ROWNUM row_id from (");
			sb.append(sql);
			sb.append(")  tmp_tb where ROWNUM<=");
			sb.append(page.getCurrentResult() + page.getSize());
			sb.append(") where row_id>");
			sb.append(page.getCurrentResult());
		} else {
			throw new IllegalArgumentException("PageInterceptor error:does not support " + dialect);
		}
		return sb.toString();
	}

	public Object plugin(Object arg0) {
		return Plugin.wrap(arg0, this);
	}

	public void setProperties(Properties p) {
		//dialect = p.getProperty("dialect");
	}

	/**
	 * <p>
	 * 查询总数
	 * </p>
	 * 
	 * @param invocation
	 * @param ms
	 * @param boundSql
	 * @param param
	 * @param sql
	 * @return
	 * @throws SQLException
	 */
	private int queryTotal(Invocation invocation, MappedStatement ms, BoundSql boundSql,
			Object param, String sql) throws SQLException {
		Connection conn = (Connection) invocation.getArgs()[0];
		int index = sql.indexOf("from");  
		if(index == -1){
			throw new RuntimeException("PageInterceptor error:statement has no 'from' key word");
		}
		String countSql = "select count(*) " + sql.substring(index);
		BoundSql countBoundSql = new BoundSql(ms.getConfiguration(), countSql,
				boundSql.getParameterMappings(), param);
		ParameterHandler parameterHandler = new DefaultParameterHandler(ms, param, countBoundSql);
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int count = 0;
		try {
			pstmt = conn.prepareStatement(countSql);
			//通过parameterHandler给PreparedStatement对象设置参数  
	        parameterHandler.setParameters(pstmt);  
			rs = pstmt.executeQuery();
			if (rs.next()) {
				count = rs.getInt(1);
			}
		} finally {
			rs.close();
			pstmt.close();
		}
		return count;
	}

}