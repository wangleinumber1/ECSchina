package com.jiajie.jiajieproject.activity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import cn.jpush.android.api.JPushInterface;

import com.amap.api.location.AMapLocation;
import com.jiajie.jiajieproject.MainActivity;
import com.jiajie.jiajieproject.R;
import com.jiajie.jiajieproject.contents.Constants;
import com.jiajie.jiajieproject.contents.UserData;
import com.jiajie.jiajieproject.db.service.SharePreferDB;
import com.jiajie.jiajieproject.db.service.UserDataService;
import com.jiajie.jiajieproject.net.LCHttpUrlConnection;
import com.jiajie.jiajieproject.net.NetUrl;
import com.jiajie.jiajieproject.net.service.JpushIdPostService;
import com.jiajie.jiajieproject.ui.service.DownloadService;
import com.jiajie.jiajieproject.utils.IntentUtil;
import com.jiajie.jiajieproject.utils.NetworkUtil;

public class LogoActivity extends LocationActivity {
	private static final int DELAYED_TIME = 2 * 1000;
	private Activity mActivity;
	private Myhandler mHandler;
	private UserDataService userDataService;
	public static final String TAG = "LogoActivity";
	private JpushIdPostService jpushIdPostService;
	public static boolean isCross = true;
	private SharePreferDB sharePreferDB;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.logolayout);
		mActivity = this;
		mHandler = new Myhandler();
		userDataService = new UserDataService(this);
		jpushIdPostService = new JpushIdPostService(this);
		sharePreferDB = new SharePreferDB(this, "Map");
		stopLocation();
//		new AsyncLoad().execute();
		initData();
	}

	private void initData() {
		if (!NetworkUtil.isConnected(mActivity)) {
			mHandler.sendEmptyMessageDelayed(1, DELAYED_TIME);
			return;
		}
		new AsyncLoad().execute();
		
	}

	private class AsyncLoad extends AsyncTask<Void, Void, ArrayList<Object>> {
		@Override
		protected ArrayList<Object> doInBackground(Void... params) {
			try {
				Thread.sleep(2000);

			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			// 数据库下载过程跟欢迎页图片同步

			return null;

		}

		@Override
		protected void onPostExecute(ArrayList<Object> result) {
			super.onPostExecute(result);

			activityForward();

		}

	}

	/*
	 * 跳转到图片展示页或首页
	 */
	@SuppressWarnings("unchecked")
	private void activityForward() {
		// open | closed
		// 以后需要改回来。为了首次安装显示欢迎页

		if (userDataService.getUserIsFirst() == null) {
			Map map = new HashMap<String, String>();
			map.put(Constants.IS_FIRST, "132");
			userDataService.saveData(map);
			IntentUtil.activityForward(mActivity, StartFragmentActivity.class,
					null, true);
//			mActivity.overridePendingTransition(R.anim.alpha_in,
//					R.anim.alpha_out);
		} else {
			IntentUtil.activityForward(mActivity, MainActivity.class, null,
					true);
//			mActivity.overridePendingTransition(R.anim.alpha_in,
//					R.anim.alpha_out);
		}

	}

	class Myhandler extends Handler {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				activityForward();
				break;
			default:
				break;
			}

		}
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if (!TextUtils.isEmpty(JPushInterface.getRegistrationID(this))) {
			UserData.JpushId = JPushInterface.getRegistrationID(this);
		}
		;

	}



}
