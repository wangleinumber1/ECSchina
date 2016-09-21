package com.jiajie.jiajieproject.utils;

import android.app.Activity;
import android.util.DisplayMetrics;

/*
 * 与屏幕信息相关的工具类，如宽高，密度，转换等
 */
public class ScreenUtil {

	private static DisplayMetrics initScreen(Activity activity){
		DisplayMetrics metric = new DisplayMetrics();
		activity.getWindowManager().getDefaultDisplay().getMetrics(metric);
		return metric;
	}
	public static int getWidth(Activity activity){
		return initScreen(activity).widthPixels;
	}
	public static int getHeight(Activity activity){
		return initScreen(activity).heightPixels;
	}
}
