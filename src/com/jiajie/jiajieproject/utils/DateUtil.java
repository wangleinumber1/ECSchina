package com.jiajie.jiajieproject.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/*
 * 日期类
 */
public class DateUtil {

	public static String getDate(long time){//yyyy-MM-dd HH:mm:ss E
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date(time);
		return sf.format(date);
	}
	public static String getFullDate(long time){
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss E");
		Date date = new Date(time);
		return sf.format(date);
	}
}
