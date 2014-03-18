package com.tx.framework.web.common.persistence.interceptor;

import java.lang.reflect.Method;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.ibatis.executor.statement.RoutingStatementHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ResultMap;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.springframework.stereotype.Component;

import com.tx.framework.web.common.persistence.dao.BaseDaoNew;
import com.tx.framework.web.common.persistence.entity.Page;
import com.tx.framework.web.common.persistence.util.PersistenceUtil;

/**
 * 
 * <p>
 * 判断如果参数中有 {@link Page} 对象，那么执行分页查询。(1.查询总数并放入page对象中。
 * 2.构造带有limit子句的sql替换原始的sql)
 * </p>
 * <p>
 * 目前只支持把page放到HashMap中(或使用接口时，把page作为方法的参数),并且key为"page"
 * </p>
 * 
 * @author tangx
 */
@Intercepts({ @Signature(type = StatementHandler.class, method = "prepare", args = { Connection.class }) })
@Component
public class SelectInterceptor implements Interceptor {

	public Object intercept(Invocation invocation) throws Throwable {
		if (invocation.getTarget() instanceof RoutingStatementHandler) {
			RoutingStatementHandler statementHandler = (RoutingStatementHandler) invocation
					.getTarget();
			StatementHandler handler = (StatementHandler) FieldUtils.readField(
					statementHandler, "delegate", true);
			MappedStatement ms = (MappedStatement) FieldUtils.readField(
					handler, "mappedStatement", true);

			if (PersistenceUtil.isBaseDaoSelectMethod(getDaoMethodName(ms))) {
				List<ResultMap> resultMaps = new ArrayList<ResultMap>();
				resultMaps.add(ms.getConfiguration().getResultMap(
						getDaoClassName(ms) + ".BaseResultMap"));
				FieldUtils.writeField(ms, "resultMaps", resultMaps, true);
			}

		}
		return invocation.proceed();
	}

	public Object plugin(Object arg0) {
		return Plugin.wrap(arg0, this);
	}

	public void setProperties(Properties p) {
		// dialect = p.getProperty("dialect");
	}

	/**
	 * 获取MappedStatement对应的Dao类名
	 */
	private String getDaoClassName(MappedStatement ms) {
		String id = ms.getId();
		String daoFullName = "";
		if (StringUtils.isNotBlank(id) && (id.indexOf(".") != -1)) {
			daoFullName = id.substring(0, StringUtils.lastIndexOf(id, "."));
		}
		return daoFullName;
	}

	/**
	 * 获取MappedStatement对应的方法名
	 */
	private String getDaoMethodName(MappedStatement ms) {
		String id = ms.getId();
		String methodName = "";
		if (StringUtils.isNotBlank(id) && (id.indexOf(".") != -1)) {
			methodName = id.substring(StringUtils.lastIndexOf(id, ".") + 1);
		}
		System.out.println("methodName==" + methodName);
		return methodName;
	}

}