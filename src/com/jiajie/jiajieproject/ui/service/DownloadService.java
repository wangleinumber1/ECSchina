package com.jiajie.jiajieproject.ui.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;

import com.jiajie.jiajieproject.net.LCHttpUrlConnection;
import com.jiajie.jiajieproject.net.NetUrl;
import com.jiajie.jiajieproject.utils.YokaLog;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.util.Log;

/*
 * 后台下载服务
 */
public class DownloadService extends IntentService {

	private static final String TAG = "DownloadService";
	// private ImgJsonService imgService;
	private Context mContext;

	public DownloadService() {
		super(null);
	}

	public DownloadService(String name) {
		super(TAG);
	}

	@Override
	public void onCreate() {
		super.onCreate();
		mContext = DownloadService.this;
		// imgService = new ImgJsonService(mContext);
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		downLoad();
	}

	private void downLoad() {
		HttpURLConnection httpCon;
		try {
			httpCon = LCHttpUrlConnection.getHttpConnection(NetUrl.DateBaseUrl,
					null);
			// 超过5秒停止请求
			httpCon.setReadTimeout(5000);
			httpCon.setRequestProperty("Accept-Encoding", "identity");
			int code = httpCon.getResponseCode();
			int contentLength = httpCon.getContentLength();
			if (200 == code) {
				DownloadDatabase(mContext, httpCon.getInputStream());
			}
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// stopSelf();
	}

	String ProjectfilePath = "data/data/com.jiajie.jiajieproject/wanghe.db";
	// 数据库存放的文件夹 data/data/com.main.jh 下面
	String pathStr = "data/data/com.jiajie.jiajieproject";
	int i = 1;

	// 网络获取数据库

	public void DownloadDatabase(Context context, InputStream is) {
		System.out.println("filePath:" + ProjectfilePath);
		File jhPath = new File(ProjectfilePath);
		// 查看数据库文件是否存在

		// 不存在先创建文件夹
		File path = new File(pathStr);
		Log.e("test", "pathStr=" + path);
		if (path.mkdir()) {
			Log.e("test", "创建成功");
		} else {
			Log.e("test", "创建失败");
		}
		;
		try {
			Log.e("test", is + "");
			// 用输出流写到SDcard上面
			FileOutputStream fos = new FileOutputStream(jhPath);
			Log.e("test", "fos=" + fos);
			Log.e("test", "jhPath=" + jhPath);
			// 创建byte数组 用于1KB写一次
			byte[] buffer = new byte[1024];
			int count = 0;
			while ((count = is.read(buffer)) > 0) {
				Log.e("test", "得到" + i);
				i++;
				fos.write(buffer, 0, count);
			}
			// 最后关闭就可以了
			fos.flush();
			fos.close();
			is.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}
		// 如果没有这个数据库 我们已经把他写到SD卡上了，然后在执行一次这个方法 就可以返回数据库了

	}
}
