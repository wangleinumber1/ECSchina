/**
 * 
 */
package com.jiajie.jiajieproject.push;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import cn.jpush.android.api.JPushInterface;

import com.jiajie.jiajieproject.MainActivity;
import com.jiajie.jiajieproject.contents.UserData;
import com.jiajie.jiajieproject.utils.YokaLog;

public class JPushReceiver extends BroadcastReceiver {
	private static final String TAG = "JPushReceiver";
	public static boolean islogin = false;

	public void onReceive(Context context, Intent intent) {
		Bundle bundle = intent.getExtras();
		Log.d(TAG, "onReceive - " + intent.getAction());

		if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
			/** 获取极光唯一识别码 */
			Bundle bundle1 = intent.getExtras();
			YokaLog.d(TAG,
					bundle1.getString(JPushInterface.ACTION_REGISTRATION_ID)
							+ "////////////////");
			// IntentUtil.serviceForward(context, JpushService.class, bundle1,
			// true);
			// i.setAction(ReciverContents.JpushidReciver);
			// context.sendBroadcast(i);
		} else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent
				.getAction())) {
			System.out.println("收到了自定义消息。消息内容是："
					+ bundle.getString(JPushInterface.EXTRA_MESSAGE));
			// 自定义消息不会展示在通知栏，完全要开发者写代码去处理
		} else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent
				.getAction())) {
			System.out.println("收到了通知");
			// 在这里可以做些统计，或者做些其他工作
		} else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent
				.getAction())) {
			System.out.println("用户点击打开了通知");
			// 在这里可以自己写代码去定义用户点击后的行为
			// 此处待定
			Intent i = new Intent(); // 自定义打开的界面
//			i.setAction(ReciverContents.JpushReciver);
			i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			UserData.isOpenNote = true;
			islogin = UserData.userId != null;
			i.setClass(context, MainActivity.class);
			context.startActivity(i);
		} else {
			Log.d(TAG, "Unhandled intent - " + intent.getAction());
		}
	}

}
