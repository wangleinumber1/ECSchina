package com.jiajie.jiajieproject.utils;

/*
 * 快速点击
 */
public class FastClickUtil {

	private static final String TAG = "FastClickUtil";
	private static long clickTime=0L;
	public static boolean isFastClick(){
		long time = System.currentTimeMillis();
		YokaLog.d(TAG, "FastClickUtil==time is "+time+",clickTime is "+clickTime);
		long diffTime = clickTime-time;
		if(diffTime>0L&&diffTime<600L){
			clickTime=0L;
			return true;
		}
		clickTime = time;
		return false;
	}
}
