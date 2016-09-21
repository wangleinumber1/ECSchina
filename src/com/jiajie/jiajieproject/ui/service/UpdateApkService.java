package com.jiajie.jiajieproject.ui.service;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.widget.RemoteViews;

import com.jiajie.jiajieproject.MainActivity;
import com.jiajie.jiajieproject.R;
import com.jiajie.jiajieproject.contents.ParamsKey;
import com.jiajie.jiajieproject.utils.NetworkUtil;
import com.jiajie.jiajieproject.utils.StringUtil;
import com.jiajie.jiajieproject.utils.ToastUtil;
import com.jiajie.jiajieproject.utils.YokaLog;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
 * 后台服务更新
 */
public class UpdateApkService extends IntentService {
	private static final String TAG = "UpdateApkService";
	private String downloadUrl;
	private String path = Environment.getExternalStorageDirectory().toString();
	private String fileName="jiajie.apk";
	public volatile boolean isRuning = false; // 判断下载的服务是否正在运行
	// Notification
	private long progress = 0;
	private long progressMax = 0;

	private Notification mNotificationDownloading;
	private NotificationManager mNotificationManager;
	private int updatePBfrequency; // 用于降低更新进度条频率的累加数
	private final int NOTIFICATION_DOWNLOADING = 0; // 下载中的Notification标识
	private final int NOTIFICATION_INSTALL_APK = 1; // 下载完成后安装apk的Notification标识

	private Context mContext;

	public UpdateApkService() {
		super(TAG);
	}

	@Override
	public void onCreate() {
		super.onCreate();
		YokaLog.d(TAG, "UpdateApkService==onCreate()");
		mContext = this;
		mNotificationManager = (NotificationManager) this
				.getSystemService(android.content.Context.NOTIFICATION_SERVICE);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		YokaLog.d(TAG, "UpdateApkService==onDestroy()");
		clearNotification(NOTIFICATION_DOWNLOADING);
		isRuning = false;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		YokaLog.d(TAG, "UpdateApkService==onStartCommand()");
		Bundle bundle = intent.getExtras();
		downloadUrl = bundle.getString(ParamsKey.version_url);
		
//		fileName = downloadUrl.substring(downloadUrl.lastIndexOf("/") + 1);
		showNotification();
		// 记录发送此请求的时间
		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	protected void onHandleIntent(Intent arg0) {
		YokaLog.d(TAG, "UpdateApkService==onHandleIntent()");
		isRuning = true;
		YokaLog.d(TAG, "onHandleIntent()==downloadUrl is " + downloadUrl
				+ ",path is " + path + ",fileName is " + fileName);
		if (NetworkUtil.isConnected(this)) {
			downLoadFile(downloadUrl, path, fileName);
		} else {
			interruptDownloading();
		}
		isRuning = false;
	}

	private void sendDownloadMaxSize(long max) {
		Intent intent = new Intent("android.intent.action.updateversion");
		progressMax = (int) max;
		intent.putExtra("max", max);
		intent.setAction("android.intent.action.updateversion");// action与接收器相同
		sendBroadcast(intent);
	}

	@SuppressWarnings("deprecation")
	private void sendDownloadProgress(long progress) {
		Intent intent = new Intent("android.intent.action.updateversion");
		intent.putExtra("progress", progress);
		intent.putExtra("max", progressMax);
		intent.setAction("android.intent.action.updateversion");// action与接收器相同
		progress = (int) progress;
		if (progress > (progressMax / 30 * updatePBfrequency)) {
//			mNotificationDownloading.contentView.setProgressBar(
//					R.id.update_notification_pb, (int) progressMax,
//					(int) progress, false);
			mNotificationDownloading.contentView.setTextViewText(
					R.id.update_notification_tv,
					getString(R.string.update_notification_downloading) + ":"
							+ ((progress * 100) / progressMax) + "%");
			mNotificationManager.notify(NOTIFICATION_DOWNLOADING,
					mNotificationDownloading);
			updatePBfrequency++;
		}

		// 下载完成后更换Notification
		if (progress == progressMax) {
			YokaLog.d(TAG, "****************finsih****************");
			// 下载之前都是临时文件xx没有后缀的。现在下载完成需要改成。apk
			File file = new File(path, fileName);
			if (file.exists()) {
				String apkPaht = file.getAbsolutePath();
				file.renameTo(new File(apkPaht));
				
			}
			clearNotification(NOTIFICATION_DOWNLOADING);
			CharSequence contentTitle = getString(R.string.update_notification_download_finish); // 通知栏标题
			CharSequence contentText = getString(R.string.update_notification_install_apk); // 通知栏内容
			Notification instNotification = new Notification(
					R.drawable.logoicon, contentTitle,
					System.currentTimeMillis());
			instNotification.flags = Notification.FLAG_AUTO_CANCEL;
			Intent instintent = new Intent();
			instintent.setAction(Intent.ACTION_VIEW);
			instintent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			instintent.setDataAndType(
					Uri.fromFile(new File(path + File.separator + fileName)),
					"application/vnd.android.package-archive");
			PendingIntent contentItent = PendingIntent.getActivity(this, 0,
					instintent, PendingIntent.FLAG_CANCEL_CURRENT);
			instNotification.setLatestEventInfo(this,
					getString(R.string.app_name), contentText, contentItent);
			instNotification.contentIntent = contentItent;
			mNotificationManager.notify(NOTIFICATION_INSTALL_APK,
					instNotification);
			YokaLog.d(TAG, "安装完成====");
		}
		sendBroadcast(intent);
	}

	private File inputSD(String path, String fileName, InputStream inputstream)
			throws IOException {
		File file = null;
		int size = 0;
		OutputStream outputstream = null;
		try {
			File dir = new File(path);
			if (!dir.exists()) {
				if (!dir.isDirectory()) {
					dir.mkdirs();
				}
			}
			file = new File(path + File.separator + fileName);
			file.createNewFile();
			outputstream = new FileOutputStream(file); // outputstream作为file的输出流
			byte[] buffer = new byte[4 * 1024];
			int len;
			while (((len = inputstream.read(buffer)) != -1) && isRuning) {
				// outputstream.write(buffer);
				// //将inputstream的内容读取，然后写进outputstream
				outputstream.write(buffer, 0, len);
				size += len;
				sendDownloadProgress(size);
			}
			outputstream.flush(); // 清空缓存
		} finally {
			try {
				outputstream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return file; // 返回新建的文件
	}

	private boolean downLoadFile(String strurl, String path, String fileName) {
		InputStream inputstream = null;
		try {
			strurl=strurl+"?name=bj";
			// 新建HttpGet对象
			HttpGet httpget = new HttpGet(strurl);
			// 获取HttpClient对象
			HttpClient httpClient = new DefaultHttpClient();
			// 获取HttpResponse实例
			HttpResponse urlConn = httpClient.execute(httpget);
			YokaLog.d(TAG, "code is " + urlConn.getStatusLine().getStatusCode());
			if (urlConn == null
					|| urlConn.getStatusLine().getStatusCode() != 200) {
				interruptDownloading();
				YokaLog.i(TAG, "网络连接失败");
				return false;
			}

			YokaLog.i(TAG, "网络连接成功");
			HttpEntity entity = urlConn.getEntity();
			if (entity == null || entity.getContentLength() == -1) {
				interruptDownloading();
				return false;
			}
			long size = entity.getContentLength();
			
			sendDownloadMaxSize(size);
			inputstream = entity.getContent(); // 得到inputstream
			File file = inputSD(path, fileName, inputstream); // 写入SDCARD
			if (file == null) {
				return false;
			} else {
				return true;
			}
		} catch (MalformedURLException e) {
			interruptDownloading();
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			interruptDownloading();
			e.printStackTrace();
			return false;
		} catch (Exception e) {
			interruptDownloading();
			e.printStackTrace();
			return false;
		} finally {
			try {
				if (inputstream != null) {
					inputstream.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private void interruptDownloading() {
		Intent intent = new Intent("android.intent.action.updateversion");
		intent.putExtra("interrupt", true);
		intent.setAction("android.intent.action.updateversion");// action与接收器相同
		sendBroadcast(intent);
		clearNotification(0);
	}

	/**
	 * 在状态栏显示通知
	 */
	private void showNotification() {
		RemoteViews rv = new RemoteViews(getPackageName(),
				R.layout.layout_update_notification);
		rv.setImageViewResource(R.id.update_notification_image,
				R.drawable.logoicon);
//		rv.setProgressBar(R.id.update_notification_pb, (int) progressMax,
//				(int) progress, false);
		rv.setTextViewText(R.id.update_notification_tv,
				getString(R.string.update_notification_downloading) + ":"
						+ progress + "%");

		int icon = R.drawable.logoicon;
		CharSequence tickerText = getString(R.string.update_notification_downloading);
		mNotificationDownloading = new Notification(icon, tickerText,
				System.currentTimeMillis());
		// mNotificationDownloading.flags |= Notification.FLAG_ONGOING_EVENT; //
		// 将此通知放到通知栏的"Ongoing"即"正在运行"组中
		// mNotificationDownloading.flags |= Notification.FLAG_NO_CLEAR; //
		// 表明在点击了通知栏中的"清除通知"后，此通知不清除
		mNotificationDownloading.contentView = rv;

		mNotificationDownloading.flags |= Notification.FLAG_AUTO_CANCEL;
		Intent notificationIntent = new Intent(this,
				MainActivity.class);
		// notificationIntent.putExtra("type",
		// UpdateVersionActivity.TYPE_DOWNLOAD);
		Bundle bundle = new Bundle();
		// bundle.putSerializable("updateEntity", upateEntity);
		notificationIntent.putExtras(bundle);
		PendingIntent contentItent = PendingIntent.getActivity(this, 0,
				notificationIntent, PendingIntent.FLAG_CANCEL_CURRENT);
		mNotificationDownloading.contentIntent = contentItent;
		mNotificationManager.notify(NOTIFICATION_DOWNLOADING,
				mNotificationDownloading);
	}

	/**
	 * 清除通知
	 * 
	 * @param id
	 */
	private void clearNotification(int id) {
		NotificationManager notificationManager = (NotificationManager) this
				.getSystemService(NOTIFICATION_SERVICE);
		notificationManager.cancel(id);

	}
}
