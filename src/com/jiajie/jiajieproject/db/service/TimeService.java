package com.jiajie.jiajieproject.db.service;

import java.util.HashMap;
import java.util.Map;

import android.content.Context;

import com.jiajie.jiajieproject.contents.FileDir;
import com.jiajie.jiajieproject.contents.ParamsKey;
import com.jiajie.jiajieproject.utils.StringUtil;
import com.jiajie.jiajieproject.utils.YokaLog;


/*
 * 用于对本地时间的记录
 */
public class TimeService {

	private static final String TAG = "TimeService";
	private Context mActivity;
	private SharePreferDB mSharePreferDB;
	public TimeService(Context activity){
		mActivity = activity;
		mSharePreferDB = new SharePreferDB(mActivity, FileDir.TIME_FILE_NAME);
	}
	
	/*
	 * 存储信息
	 * Map<String, String> maps
	 */
	public void saveData(){
		Map<String, String> maps = new HashMap<String, String>();
		long time = System.currentTimeMillis();
		YokaLog.d(TAG, "TimeService==saveData()=time is "+time);
		maps.put(ParamsKey.LOCAL_TIME, String.valueOf(time));
		mSharePreferDB.saveData(maps);
	}
	
	public long getTime(){
		Map<String, String> maps = mSharePreferDB.readData();
		if(null == maps || maps.isEmpty())
			return 0l;
		String orgintime = maps.get(ParamsKey.LOCAL_TIME);
		if(StringUtil.checkStr(orgintime)){
			long time = Long.parseLong(orgintime);
			return time;
		}
		return 0;
	}
	/*
	 * 和当前系统时间对比，是否大于一天
	 */
	public boolean isOverTime(){
		Map<String, String> maps = mSharePreferDB.readData();
		if(null == maps || maps.isEmpty())
			return false;
		String orgintime = maps.get(ParamsKey.LOCAL_TIME);
		YokaLog.d(TAG, "TimeService==isOverTime()==orgintime is "+orgintime);
		if(StringUtil.checkStr(orgintime)){
			long differTime = System.currentTimeMillis() - Long.parseLong(orgintime);
			YokaLog.d(TAG, "TimeService==isOverTime()==differTime is "+differTime);
			if(differTime>24*60*60*1000){
				return true;
			}
		}
		return false;
	}
}
