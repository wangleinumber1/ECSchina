package com.jiajie.jiajieproject.db.service;

import java.util.HashMap;
import java.util.Map;

import com.jiajie.jiajieproject.utils.StringUtil;
import com.jiajie.jiajieproject.utils.YokaLog;



import android.content.Context;
import android.content.SharedPreferences;

public class SharePreferDB {

	private static final String TAG = "SharedPreferDB";
	private Context mContext;
	private SharedPreferences mSharedPreferences;
	private String mFileName;
	private SharedPreferences.Editor mEditor ;

	
	public SharePreferDB(Context context,String fileName){   
		this.mContext = context;
		this.mFileName = fileName;
	}
	
	/*
	 * 存数据
	 */
	public void saveData(Map<String ,String> maps){
		if(null == maps || maps.size() == 0)return;
		mSharedPreferences = mContext.getSharedPreferences(StringUtil.checkStr(mFileName)?mFileName:"fileName", Context.MODE_PRIVATE);
		mEditor = mSharedPreferences.edit();  
		for(Map.Entry<String, String> map:maps.entrySet()){
			String key = map.getKey();
			String value = map.getValue();
			YokaLog.d(TAG, "存数据==key is "+key+",value is "+value);
			mEditor.putString(key, value);
		}
		mEditor.commit();
	}
	/*
	 * 读数据，返回一个Map<String, String>
	 */
	public Map<String, String> readData(){
		mSharedPreferences = mContext.getSharedPreferences(StringUtil.checkStr(mFileName)?mFileName:"fileName", Context.MODE_PRIVATE);
		mEditor = mSharedPreferences.edit();  
		Map<String, String> map =  (Map<String, String>) mSharedPreferences.getAll();
		return map;
	} 
	
	/*
	 * 根据文件名删除文件里的数据
	 */
	public void deletePreference(){
		mSharedPreferences = mContext.getSharedPreferences(StringUtil.checkStr(mFileName)?mFileName:"fileName", Context.MODE_PRIVATE);
		mSharedPreferences.getAll().clear();
		mEditor = mSharedPreferences.edit(); 
		mEditor.clear().commit();
	}
}
