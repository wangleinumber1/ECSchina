package com.jiajie.jiajieproject.push;

import android.content.Context;
import android.telephony.TelephonyManager;

import com.jiajie.jiajieproject.YmallApplication;

public class App {
	private static YmallApplication app;

	public static void setApp(YmallApplication application) {
		app = application;
	}

	public static YmallApplication getApp() {
		return app;
	}

	private static String imei;

	public static String imei() {
		if (imei == null) {
			TelephonyManager tm = (TelephonyManager) app.getSystemService(Context.TELEPHONY_SERVICE);
			imei = tm.getDeviceId();
		}
		return imei;
	}
}
