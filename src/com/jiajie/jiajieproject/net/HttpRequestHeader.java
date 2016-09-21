package com.jiajie.jiajieproject.net;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;

import android.content.Context;

import com.jiajie.jiajieproject.Config;
import com.jiajie.jiajieproject.contents.DeviceParamsDB;
import com.jiajie.jiajieproject.contents.UserData;
import com.jiajie.jiajieproject.db.service.UserDataService;
import com.jiajie.jiajieproject.utils.YokaLog;

/*
 * 构建http请求头,返回一个HttpURLConnection对象
 */
public class HttpRequestHeader {

	private static final String TAG = "HttpRequestHeader";
	public static HttpURLConnection constructHeader(Context context,
			String params) throws MalformedURLException, IOException {
		// 当前未得到有效的网络
		String requestUrl = null;
		 UserDataService userDataService=new UserDataService(context);
		 if(userDataService.readUserData().get("session")!=null){
			 DeviceParamsDB.cookie= userDataService.readUserData().get("session");
		 };
		if (Config.IS_TEST) {
			requestUrl = NetUrl.TEST_HOST;
		} else {
			requestUrl = NetUrl.ONLINE_HOST;
		}

		HttpURLConnection httpConnection = LCHttpUrlConnection
				.getHttpConnection(requestUrl, params);
		YokaLog.d(TAG, "requestUrl is " + requestUrl + ",httpConnection is "
				+ httpConnection);
		if (null == httpConnection)
			return null;

//		httpConnection.setRequestProperty(Header.DEVICEMODE,
//				DeviceParamsDB.deviceModel);
//		httpConnection.setRequestProperty(Header.DEVICESIZE,
//				DeviceParamsDB.deviceSize);
//		httpConnection.setRequestProperty(Header.APPVERSION,
//				DeviceParamsDB.appVersion);
//		httpConnection.setRequestProperty(Header.SYSTEM_VERSION,
//				DeviceParamsDB.systemVersion);
//		httpConnection.setRequestProperty(Header.UID, UserData.userId);
//		httpConnection.setRequestProperty(Header.CHANNELID,
//				DeviceParamsDB.channelID);
//		httpConnection.setRequestProperty(Header.CHANNELNAME,
//				DeviceParamsDB.channelName);
//		httpConnection.setRequestProperty(Header.ACCESS_MODE,
//				DeviceParamsDB.currentNetType);
		// httpConnection.setRequestProperty(Header.ACCESS_TOKEN,
		// UserData.userToken);
		httpConnection.setRequestProperty(Header.PLATFORM,
				DeviceParamsDB.platform);
		// httpConnection.setRequestProperty(Header.COOKIE,
		// DeviceParamsDB.cookie+";"+"name=123132454654"+";"+"name=0000000000000000");
//		httpConnection.setRequestProperty(Header.CLIENTID,
//				DeviceParamsDB.deviceId);
//		httpConnection.setRequestProperty(Header.ACCEPT_ENCODING, "gzip");
		httpConnection.addRequestProperty("Cookie",DeviceParamsDB.cookie);
		httpConnection.setRequestProperty("charsert", "utf-8");
		httpConnection.setRequestProperty("Connection", "Close");
		httpConnection.setRequestProperty("Content-Type", "json");
		httpConnection.setConnectTimeout(Config.CONNECT_TIME_OUT);
		httpConnection.setReadTimeout(Config.CONNECT_TIME_OUT);
		return httpConnection;
	}

}
