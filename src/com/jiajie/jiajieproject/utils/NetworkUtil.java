package com.jiajie.jiajieproject.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/** 
 * @ClassName NetworkUtil 
 * @Description 网络连接工具类
 * @author huangke@yoka.com 
 * @date 2011-12-5 上午11:00:23 
 *  
 */
public class NetworkUtil {
	
//	private static final String TAG = "NetworkUtil";

	private static final String TYPE_NAME = "mobile";
	
	/** 
	 * @Description 得到当前网络连接类型 
	 * @author huangke@yoka.com
	 * @param context 环境
	 * @return String 当前网络连接类型名称
	 * @date 2011-12-5 上午11:02:07 
	 */
	public static String getCurrentNetworkTypeName(Context context) {
		
		ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
		
		if (networkInfo == null) {
			return null;
		} else {// 当前存在网络接入方式，获取具体的网络接入类型值
			String typeName = networkInfo.getTypeName();
			if (typeName.contains(TYPE_NAME)) {
				String extraInfo = networkInfo.getExtraInfo();
				if (extraInfo == null) {
					extraInfo = "ChinaTelecom";
				}
				return extraInfo;
			}
			return typeName;
		}
	}
	
	/** 
	 * @Description 判断网络连接是否存在并可用 
	 * @param context 调用者的上下文环境
	 * @return boolean true可用，false不可用
	 * @date 2012-3-7 下午06:51:05
	 * @author huangke@yoka.com 
	 */
	public static boolean isConnected(Context context) {
		ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
		if (networkInfo == null) {
			return false;
		}else {
			if (networkInfo.isConnected()) {
				return true;
			}else {
				return false;
			}
		}
	}
}
