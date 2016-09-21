package com.jiajie.jiajieproject;

import java.util.Stack;

import android.app.Activity;

/**
 * @ClassName ActivityManager
 * @Description 采用压栈式管理Activity
 * 
 */
public class ActivityManager {   

	private static Stack<Activity> activityStack;

	private static ActivityManager activityManagerInstance;

	public ActivityManager() {
	}

	/** 
	 * @Description 静态工厂方法，返回该类唯一的实例。当没有初始化的时候，才初始化 
	 * @return ActivityManager 
	 */
	synchronized public static ActivityManager getActivityManagerInstance() {

		if (activityManagerInstance == null) {
			activityManagerInstance = new ActivityManager();
		}

		return activityManagerInstance;
	}

	/** 
	 * @Description 退出栈并关闭 
	 * @param activity 要退出的Activity
	 */
	public void popActivity(Activity activity) {

		if (activity != null) {
			if(!activity.isFinishing())
			activity.finish();
			activityStack.remove(activity);
			activity = null;
		} else {
			activity = activityStack.pop();
			if(!activity.isFinishing())
			activity.finish();
			activity = null;
		}
	}

	/** 
	 * @Description 进栈管理 
	 * @param activity 要进栈的Activity
	 */
	public void pushActivity(Activity activity) {

		if (activityStack == null) {
			activityStack = new Stack<Activity>();
		}
		activityStack.push(activity);
	}

	/** 
	 * @Description 获取栈顶的Activity 
	 * @return Activity 栈顶的Activity
	 */
	public Activity getActivityAtTopOfStack() {

		Activity activity = null;
		if (activityStack != null) {
			if (!activityStack.isEmpty()) {
				activity = activityStack.lastElement();
			}
		}
		return activity;
	}

	/** 
	 * @Description 退出所有的Activity  
	 */
	public void popAllActivity() {

		while (true) {
			Activity activity = getActivityAtTopOfStack();
			if (activity == null) {
				break;
			}
			popActivity(activity);
		}
	}
	
	public int getStackSize(){
		if(activityStack != null){
			return activityStack.size();
		}
		return 0;
	}
}
