package com.tx.framework.web.webservice.errlog;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RestExceptionLogger {

	private static Logger logger = LoggerFactory.getLogger(RestExceptionLogger.class);

	/**
	 * 记录rest异常日志
	 * @param target 抛出异常的方法
	 * @param args 方法参数
	 * @param ex 异常
	 */
	public static void error(String target, String args, Throwable ex){
		logger.error("target:{}\nargs:{}\nexp:{}\n", target, args, ex.getMessage());
	}
}
