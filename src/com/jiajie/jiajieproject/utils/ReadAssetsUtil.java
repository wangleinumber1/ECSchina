package com.jiajie.jiajieproject.utils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.util.EncodingUtils;



import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.os.Bundle;

/**
 * @ClassName ReadAssetsUtil
 * @Description 读取assets目录下文件内容工具类
 */
public class ReadAssetsUtil {

	private static final String TAG = "ReadAssetsUtil";
	
	private static final String ENCODING = "UTF-8";
	private static final String AGENCY_ID = "agencyid";
	private static final String CUSTOMER_ID = "customerid";
	private static final String SEPARATOR_EQUALS = "=";

	/** 
	 * 读取assets中单个的文件
	 * @param fileName
	 * @return 读取的内容 
	 * @author huangke@yoka.com 
	 */
	public String readFile(Context context, String fileName) {
		String content = null;
		try {
			InputStream is = context.getAssets().open(fileName);
			// 获取文件的字节数
			int length = is.available();
			// 创建byte数组
			byte[] data = new byte[length];
			// 将文件中的数据读到byte数组中
			is.read(data);
			content = EncodingUtils.getString(data, ENCODING);
			is.close();
		} catch (Exception e) {
			YokaLog.d(TAG, "读取文件时出错");
		}
		return content;
	}
	
	/** 
	 * 读取agencyID
	 * @param context 调用者上下文环境
	 * @param fileName agency.txt
	 * @return agencyID 
	 */
	public static String readAgencyID(Context context, String fileName) {
		String agencyID = null;
		try {
			InputStream is = context.getAssets().open(fileName);
			InputStreamReader isr = new InputStreamReader(is);
			BufferedReader br = new BufferedReader(isr);
			String temp = null;
			// 循环读取
			while ((temp = br.readLine()) != null) {
				if (temp.indexOf(AGENCY_ID) > -1) {
					String [] temps = temp.split(SEPARATOR_EQUALS);
					agencyID = temps[1];
				}
			}
			br.close();
			isr.close();
			is.close();
		} catch (Exception e) {
			YokaLog.d(TAG, "读取" + fileName + "文件时出错");
		}
		return agencyID;
	}
	
	/** 
	 * 读取customerID
	 * @param context 调用者上下文环境
	 * @param fileName customer.txt
	 * @return customerID 
	 */
	public static String readCustomerID(Context context, String fileName) {
		String customerID = null;
		try {
			InputStream is = context.getAssets().open(fileName);
			InputStreamReader isr = new InputStreamReader(is);
			BufferedReader br = new BufferedReader(isr);
			String temp = null;
			// 循环读取
			while ((temp = br.readLine()) != null) {
				if (temp.indexOf(CUSTOMER_ID) > -1) {
					String [] temps = temp.split(SEPARATOR_EQUALS);
					customerID = temps[1];
				}
			}
			br.close();
			isr.close();
			is.close();
		} catch (Exception e) {
			YokaLog.d(TAG, "读取" + fileName + "文件时出错");
		}
		return customerID;
	}
	
	/*
	 * 取得渠道名称
	 */
//	public static String getChannelName(Context con){
//		String key = ParamsKey.CHANNEL_NAME_KEY;
//	    ApplicationInfo appInfo = con.getApplicationInfo();
//	    Bundle bundle = appInfo.metaData;
//	    if(null != bundle && bundle.containsKey(key))
//	    	return bundle.getString(key);
//		return "";
//	} 
}
