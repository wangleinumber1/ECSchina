package com.jiajie.jiajieproject.db.service;

import java.util.Map;

import android.content.Context;

import com.jiajie.jiajieproject.contents.Constants;



/*
 * 用户相关数据的存储与获取
 */
public class UserDataService {

	private Context mActivity;
	private SharePreferDB mSharePreferDB;
	public UserDataService(Context activity){
		mActivity = activity;
		mSharePreferDB = new SharePreferDB(mActivity, Constants.USER_DATA_FILE_NAME);
	}
	
	/*
	 * 存储信息
	 */
	public void saveData(Map<String, String> map){
		mSharePreferDB.saveData(map);
	}
	
	/*
	 * 得到用户的userID
	 */
	public String getUserId(){
		Map<String, String> maps = readUserData();
		if(null == maps) return null;
		return maps.get(Constants.NEW_USER_ID);
	}
	public String getUserIsFirst(){
		Map<String, String> maps = readUserData();
		if(null == maps) return null;
		return maps.get(Constants.IS_FIRST);
	}

//	/*
//	 * 得到用户的token信息
//	 */
//	public String getUserToken(){
//		Map<String, String> maps = readUserData();
//		if(null == maps) return null;
//		return maps.get(Constants.NEW_USER_TOKEN);
//	}
//	
//	/*
//	 * 得到用户的电话号码
//	 */
//	public String getUserPhone(){
//		Map<String, String> maps = readUserData();
//		if(null == maps) return null;
//		return maps.get(Constants.NEW_USER_PHONE);
//	}
	
	/*
	 * 得到指定电话号码是否有设置过密码
	 * 0为没有，其他为有
	 */
//	public int hasPwd(){
//		Map<String, String> maps = readUserData();
//		if(null == maps) return 0;
//		if(StringUtil.checkStr(getUserPhone())){
//			String haspwd = maps.get(getUserPhone());
//			if(StringUtil.checkStr(haspwd))
//				return Integer.parseInt(haspwd);
//		}
//		return 0;
//	}
	/*
	 * 清除本地文件数据
	 */
	public void clearData(){
		mSharePreferDB.deletePreference();
	}
	/*
	 * 读取文件信息
	 */
	public Map<String, String> readUserData(){
		return mSharePreferDB.readData();
	}
	
}
