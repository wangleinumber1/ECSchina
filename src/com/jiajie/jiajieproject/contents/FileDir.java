package com.jiajie.jiajieproject.contents;

import com.jiajie.jiajieproject.utils.SDCardUtil;



public class FileDir {

	/*
	 * 图片本地缓存目录
	 */
	public static final String IMG_DIR = "";
	
	/*
	 * 渠道文件名agency.txt
	 */
	public static final String AGENCY_FILE = "agency.txt";
	
	/*
	 * app下载到本地的地址
	 */
	public static final String APP_LOCAL_PATH_DIR = SDCardUtil.getStoragePath("/app");
	public static final String APP_LOCAL_FILE_NAME = "jiajiekeji.apk";
	public static final String TIME_FILE_NAME = "localtime";
	
	public static final String ADDRESS_INFO = "address_info";
}
