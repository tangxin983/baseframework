package com.tx.framework.common.util;

import java.util.Date;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class JodaTimeUtil {
	
	public static final String defaultPattern = "yyyy-MM-dd HH:mm:ss";
	
	/**
	 * 日期转字符串<br>
	 * 默认格式:yyyy-MM-dd HH:mm:ss
	 * @param date
	 * @return
	 */
	public static String convertToString(Date date) {
		DateTime dt = new DateTime(date);
		return dt.toString(defaultPattern);
	}
	
	/**
	 * 日期转字符串
	 * @param date
	 * @param pattern
	 * @return
	 */
	public static String convertToString(Date date, String pattern) {
		DateTime dt = new DateTime(date);
		return dt.toString(pattern);
	}
	
	/**
	 * 字符串转日期<br>
	 * 默认格式:yyyy-MM-dd HH:mm:ss
	 * @param dateString
	 * @return
	 */
	public static Date convertFromString(String dateString) {
		DateTimeFormatter fmt = DateTimeFormat.forPattern(defaultPattern);
		DateTime dt = fmt.parseDateTime(dateString);
		return dt.toDate();
	}
	
	/**
	 * 字符串转日期
	 * @param dateString
	 * @param pattern
	 * @return
	 */
	public static Date convertFromString(String dateString, String pattern) {
		DateTimeFormatter fmt = DateTimeFormat.forPattern(pattern);
		DateTime dt = fmt.parseDateTime(dateString);
		return dt.toDate();
	}
	
	/**
	 * 当前日期转字符串
	 * @param pattern
	 * @return
	 */
	public static String convertCurtimeToString(String pattern) {
		DateTime dt = new DateTime(new Date());
		return dt.toString(pattern);
	}
	
	/**
	 * 当前日期转字符串<br>
	 * 默认格式:yyyy-MM-dd HH:mm:ss
	 * @return
	 */
	public static String convertCurtimeToString() {
		DateTime dt = new DateTime(new Date());
		return dt.toString(defaultPattern);
	}
	
	/**
	 * 将unix时间戳转字符串
	 * @param timeMill 时间戳
	 * @param pattern 时间格式
	 * @return
	 */
	public static String convertTimeMillToString(long timeMill, String pattern) {
		DateTime dt = new DateTime(timeMill);
		return dt.toString(pattern);
	}
	 
}
