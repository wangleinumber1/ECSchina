package com.jiajie.jiajieproject.net;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.CookieHandler;
import java.net.CookiePolicy;
import java.net.CookieStore;
import java.net.HttpCookie;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.jiajie.jiajieproject.R;
import com.jiajie.jiajieproject.contents.Constants;
import com.jiajie.jiajieproject.contents.DeviceParamsDB;
import com.jiajie.jiajieproject.db.service.UserDataService;
import com.jiajie.jiajieproject.utils.NetworkUtil;
import com.jiajie.jiajieproject.utils.StringUtil;
import com.jiajie.jiajieproject.utils.ToastUtil;
import com.jiajie.jiajieproject.utils.YokaLog;

/*
 * 根据Http请求得到数据，String类型
 */
public class NetRequestService {
	public static ArrayList<String> cookieList = new ArrayList<String>();
	private static final String TAG = "NetRequestService";
	private Context mContext;
	private HashMap<String, String> cookieMap = null;
	private UserDataService userDataService;
	private MyHandler myhandler;

	public NetRequestService(Context context) {
		this.mContext = context;
		userDataService = new UserDataService(mContext);
		myhandler = new MyHandler();
	}

	private HttpURLConnection getConnection(String params)
			throws MalformedURLException, IOException {
		return LCHttpUrlConnection
				.getHttpConnectionWithHeader(mContext, params);

	}

	/**
	 * method，请求方式 params，get请求url后的参数 urlState, 请求路径的状态，具体参看 HttpRequestHeader
	 * 类 paramsMap，post请求，body里的参数 throws MalformedURLException, IOException
	 * needShowCach 是否需要展示缓存 true为需要，false为不需要
	 */
	@SuppressWarnings("unchecked")
	public String requestData(String method, String params,
			Map<String, String> paramMaps, boolean needShowCach) {
		// 生成缓存文件内容的key值
		/*
		 * if(StringUtil.checkStr(params)) params =
		 * URLEncodUtil.getEncodeStr(params);
		 */
		String cachContentKey = generateKey(params, paramMaps);
		// LocalCachService localcach = new LocalCachService(mContext,
		// UserData.userId);
		// if(needShowCach){
		// //先读取缓存文件
		// return localcach.getCachData(cachContentKey);
		// }
		if (!NetworkUtil.isConnected(mContext)) {
			ToastUtil.showToast(mContext, R.string.POOR_NETWORK_STATUS, null,
					true);
			return null;
		}
		try {
			HttpURLConnection httpConn = getConnection(params);

			if (null == httpConn)
				return null;
			httpConn.setRequestMethod(method);

			// 请求参数放在body里时的post请求
			if (null != paramMaps && !paramMaps.isEmpty()) {
				StringBuilder data = new StringBuilder();
				httpConn.setDoOutput(true);
				httpConn.setDoInput(true);
				for (Map.Entry<String, String> map : paramMaps.entrySet()) {// Map.Entry<String,String>
																			// map:paramsMap.entrySet()
					data.append(map.getKey()).append("=");
					String value = map.getValue();
					if (StringUtil.checkStr(value)) {
						value = URLEncoder.encode(value, "utf-8");
					} else {
						value = "0";
					}
					data.append(value);
					data.append("&");
				}
				data.deleteCharAt(data.length() - 1);
				byte[] paramsdata = data.toString().getBytes();// Content-Type:
																// application/x-www-form-urlencoded
				if ("POST".equals(method))
					httpConn.setRequestProperty("Content-Type",
							"application/x-www-form-urlencoded");
				// httpConn.setRequestProperty("Content-Type","application/json");

				DataOutputStream ds = new DataOutputStream(
						httpConn.getOutputStream());

				ds.write(paramsdata);
				ds.flush();
				ds.close();
			}
			int code = httpConn.getResponseCode();
			YokaLog.d("requestData", "请求 的  code is " + code);
			if (code == 200) {
				String result = LCHttpUrlConnection
						.decodeConnectionToString(httpConn);
				/* 获取cookie */
				getSessionId(httpConn);

				return result;
			} else {

				ToastUtil.showToast(mContext, R.string.server_exception, null,
						true);
				return null;
			}
		} catch (MalformedURLException e1) {
			YokaLog.d(TAG, "MalformedURLException==" + e1.getMessage());
			ToastUtil
					.showToast(mContext, R.string.reques_error_url, null, true);
			return null;
		} catch (SocketTimeoutException ste) {
			YokaLog.d(TAG, "SocketTimeoutException==" + ste.getMessage());
			ToastUtil
					.showToast(mContext, R.string.connect_time_out, null, true);
			return null;
		} catch (IOException e1) {
			YokaLog.d(TAG, "IOException==" + mContext + e1.getMessage());
			ToastUtil.showToast(mContext, R.string.POOR_NETWORK_STATUS, null,
					true);
			return null;
		} catch (Exception e) {
			YokaLog.d(TAG, "Exception==" + e.getMessage());
			ToastUtil.showToast(mContext, R.string.server_or_net_error, null,
					true);
			return null;
		}
	}

	private String generateKey(String params, Map<?, ?> paramMaps) {
		String key = null;
		if (null == paramMaps) {
			key = params;
		} else {
			key = params + paramMaps.toString();
		}
		return key;
	}

	public String getSessionId(HttpURLConnection hc) {
		String sessionId = "";
		Map map = hc.getHeaderFields();
		List<String> list = (List) map.get("Set-Cookie");
		if (list.size() == 0 || list == null) {
			myhandler.sendEmptyMessage(1);
		}
		StringBuilder builder = new StringBuilder();

		if (list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				sessionId = list.get(i).split(";")[0] + ";" + sessionId;
			}
		} else {
			sessionId = list.get(0).split(";")[0];
		}
		myhandler.sendMessage(myhandler.obtainMessage(2, sessionId));
		return sessionId;
	}

	class MyHandler extends Handler {

		@SuppressWarnings("unchecked")
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				DeviceParamsDB.cookie = userDataService.readUserData().get(
						"session");
				Log.d(TAG, DeviceParamsDB.cookie);
				break;
			case 2:
				HashMap<String, String> map = new HashMap<String, String>();
				map.put("session", (String) msg.obj);
				userDataService.saveData(map);
				DeviceParamsDB.cookie = userDataService.readUserData().get(
						"session");
				break;

			default:
				break;
			}
		}

	}

}
