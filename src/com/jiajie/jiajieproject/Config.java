package com.jiajie.jiajieproject;


/*
 * 程序的配置信息，如开关设置，系统常量值,标识值
 */
public class Config {
	public static final int MIN_HEAP_SIZE = 8* 1024* 1024 ;//最小堆内存
	public static final float TARGET_HEAP_UTILIZATION = 0.75f;//加强法度堆内存的处理惩罚效力
	public static final int SDCARD_SPACE_UNVALIABLE = 100;//sdcard_space_unvailable
	public static final int SD_AVAIABLE_SIZE = 100;//SD卡大小100Mb
	public static final boolean IS_DEBUG = true;//log日志的开关,true为输出，false为不输出
	public static final boolean IS_TEST = true;//网络连接地址的开关，true为测试环境，false为正式环境 
	public static final int CONNECT_TIME_OUT = 10*1000;//连接超时时间设置
	public static final boolean ISOA=false;
} 
