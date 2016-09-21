package com.jiajie.jiajieproject;

import android.app.Application;
import android.app.Notification;
import android.content.Context;
import android.os.Handler;
import cn.jpush.android.api.BasicPushNotificationBuilder;
import cn.jpush.android.api.JPushInterface;

import com.jiajie.jiajieproject.contents.UserData;
import com.jiajie.jiajieproject.db.service.UserDataService;
import com.jiajie.jiajieproject.utils.YokaLog;

public class YmallApplication extends Application {

	public static final String TAG = "YmallApplication";
	public static YmallApplication mContext;
	public ActivityManager activityManager;
	public Thread mUiThread;
	
	// public ArrayList<OrderInfoCoupon> couponList;
	
	public final int MSGID_KILLACTIVITYS = 1;
	public final Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case MSGID_KILLACTIVITYS:
				activityManager.popAllActivity();
				break;

			default:
				break;
			}
		};
	};

	@Override
	public void onCreate() {
		super.onCreate();
		mContext = this;
		initUserData();
		initJPush();
		activityManager = ActivityManager.getActivityManagerInstance();
		mUiThread = Thread.currentThread();
	}

	/**
	 * 初始化JPush
	 */
	private void initJPush() {
		JPushInterface.init(getApplicationContext());
		//调试模式
		JPushInterface.setDebugMode(true);
		JPushInterface.init(this);		
		BasicPushNotificationBuilder builder = new BasicPushNotificationBuilder(
				this);
		builder.notificationFlags = Notification.FLAG_AUTO_CANCEL;
		builder.notificationDefaults = Notification.DEFAULT_LIGHTS
				| Notification.DEFAULT_VIBRATE;
		JPushInterface.setPushNotificationBuilder(1, builder);		
		YokaLog.e(TAG, JPushInterface.getRegistrationID(getApplicationContext())+"///////////////////////");
		UserData.JpushId=JPushInterface.getRegistrationID(this);
	}

	/**
	 * @return the activityManager
	 */
	public ActivityManager getActivityManager() {
		return activityManager;
	}

	/**
	 * @param activityManager
	 *            the activityManager to set
	 */
	public void setActivityManager(ActivityManager activityManager) {
		this.activityManager = activityManager;
	}

	

	@Override
	public void onLowMemory() {
		super.onLowMemory();
	}

	public final void runOnUiThread(Runnable action) {
		if (Thread.currentThread() != mUiThread) {
			mHandler.post(action);
		} else {
			action.run();
		}
	}

	// @Override
	// public void gotResult(int arg0, String arg1, Set<String> arg2) {
	// }
	public void initUserData() {
		UserDataService userService = new UserDataService(mContext);
		UserData.userId = userService.getUserId();		
		YokaLog.d("YmallApplication", "初始化用户信息==userId is "
				);
	}
	public Context getContext() {
		return mContext;
	}
	
	
}
