package com.jiajie.jiajieproject.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.app.ActivityManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.support.v4.util.LruCache;
import android.view.View;
import android.widget.ImageView;
import android.widget.RemoteViews;

public class WidgetImgLoad {

	private static String TAG = "WidgetImgLoad";

	public static final String defaultImgDir = SDCardUtil
			.getStoragePath(File.separator + "." + "liwudianimg0304");
	// 获取系统分配给每个应用程序的最大内存，每个应用系统分配32M
	private static int maxMemory = (int) Runtime.getRuntime().maxMemory();
	private static int memCacheSize = Math.round(Runtime.getRuntime()
			.maxMemory() / (1024 *4));

	private Context mContext;
	private LruCache<String, Bitmap> inercachBitmap;
	
	private RemoteViews remoteView;
	public WidgetImgLoad(Context con,RemoteViews remoteView){
		mContext = con;
		this.remoteView = remoteView;
		final int memClass = (int) Runtime.getRuntime().maxMemory();
		final int cacheSize = Math.round(Runtime.getRuntime().maxMemory() / (1024 * 8)); 
		YokaLog.d(TAG, "ImageLoad(Context con)==memClass is "+memClass+",cacheSize is "+cacheSize);
	    inercachBitmap = new LruCache<String, Bitmap>(
				cacheSize) {
			// 必须重写此方法，来测量Bitmap的大小
			@Override
			protected int sizeOf(String key, Bitmap bitmap) {
				if (null == bitmap)
					return 0;
				return bitmap.getRowBytes() * bitmap.getHeight();  
			}
		};
	}

	/*
	 * 设置以全背景形式显示图片
	 */
	private boolean isDrawableBg = false;
	public void setDrawableBg (boolean isDrawableBg) {
		this.isDrawableBg = isDrawableBg;
	}
	
	/*
	 * 加载图片并显示
	 */
	public void loadImg(int imgId,String url) {
		Bitmap bitmap = getBitmapFromInnercach(url);
		if (null != bitmap) {
			displayImg(imgId,bitmap);
		} else {
			new AsyncImg(imgId,url).execute();
		}
	}

	private class AsyncImg extends AsyncTask<String, Integer, Bitmap> {
		int imgId;
		String url;
		AsyncImg(int imgId,String url) {
			this.imgId = imgId;
			this.url = url;
		}

		@Override
		protected Bitmap doInBackground(String... params) {
			Bitmap bitmap = getBitmapByUrl(url);
			if (null != bitmap) {
				saveBitmap(url, bitmap);
				addBitmapTocach(url, bitmap);
			}
			return bitmap;//getBitmapFromInnercach(url,mImg);
		}

		@Override
		protected void onPostExecute(Bitmap result) {
			super.onPostExecute(result);
			if (result != null) {
				displayImg(imgId,result);
			}
		}
	}

	/*
	 * 根据url从本地或网络获得一个bitmap
	 */
	private static Bitmap getBitmapByUrl(String url) {
		// 先从本地找
		if (StringUtil.checkStr(url)) {
			String imgPath = defaultImgDir + File.separator
					+ url.substring(url.lastIndexOf("/") + 1);
			File imgFile = new File(imgPath);
			if (imgFile.exists()) {
				try {
					
					return BitmapFactory.decodeFile(imgPath);
				} catch (OutOfMemoryError outMemError) {
					outMemError.printStackTrace();
					return null;
				}
			} else {
				return getInStream(url);
			}
		}
		return null;
	}

	private static Bitmap getInStream(String imgurl) {
		try {
			HttpURLConnection httpConn = (HttpURLConnection) new URL(imgurl)
					.openConnection();
			int code = httpConn.getResponseCode();
			int contentLength = httpConn.getContentLength();
			if (200 == code) {
				try {
					return getBitmapByStream(httpConn.getInputStream(),
							contentLength);// BitmapFactory.decodeStream(httpConn.getInputStream());
				} catch (OutOfMemoryError error) {
					return null;
				}
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		return null;
	}

	private static void saveBitmap(String url, Bitmap bitmap) {
		if (StringUtil.checkStr(url)) {
			String imgName = url.substring(url.lastIndexOf("/") + 1);
			String format = imgName.substring(imgName.lastIndexOf(".") + 1);

			try {
				File imgFile = new File(defaultImgDir, imgName);
				if(imgFile.exists())
					return;
				FileOutputStream fos = new FileOutputStream(imgFile);
				if (format != null && format.trim().equals("png")) {
					bitmap.compress(CompressFormat.PNG, 100, fos);
				} else {
					bitmap.compress(CompressFormat.JPEG, 100, fos);
				}
				fos.flush();
				fos.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private static Bitmap getBitmapByStream(InputStream inputStream,
			int contentLength) {
		try {
			byte[] byteData = StreamUtil.readStream(inputStream);
			inputStream.close();
			if (contentLength == byteData.length) {
				return BitmapFactory.decodeByteArray(byteData, 0,
						byteData.length);// decodeStream(inputStream);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	private void displayImg(int imgId,Bitmap bitmap) {
		YokaLog.d(TAG, "displayImg()==imgId is "+imgId+",bitmap is "+bitmap);
		if(null != remoteView)
			remoteView.setImageViewBitmap(imgId,bitmap);
	}

	/*
	 * 添加至内存缓存
	 */
	private void addBitmapTocach(String url, Bitmap bitmap) {
		if (StringUtil.checkStr(url)
				&& (null != bitmap && !bitmap.isRecycled())) {
			if(null == getBitmapFromInnercach(url)){
				inercachBitmap.put(url, bitmap);
			}
		}
	}

	/*
	 * 从内存缓存qu图片
	 */
	private Bitmap getBitmapFromInnercach(String url) {
		if (!StringUtil.checkStr(url))
			return null;
		Bitmap bitmap = inercachBitmap.get(url);
		if (null == bitmap || bitmap.isRecycled())
			return null;
		return bitmap;
	}


	/*
	 * 判斷圖片本地是否存在
	 */
	public boolean isExist(String netUrl) {
		String localpath = getLocalImgPath(netUrl);
		if (!StringUtil.checkStr(localpath))
			return false;
		return new File(localpath).exists();
	}

	/*
	 * 得到本地图片的储存路径
	 */
	public String getLocalImgPath(String netUrl) {
		if (!StringUtil.checkStr(netUrl))
			return null;
		return defaultImgDir + File.separator
				+ netUrl.substring(netUrl.lastIndexOf("/") + 1);
	}

	/*
	 * 清除内存缓存中的图片
	 */
	public void clearCachBitmap() {
		if (inercachBitmap.size() == 0)
			return;
		//inercachBitmap.trimToSize(arg0);
		inercachBitmap.evictAll();
	}

	/*
	 * 清除物理缓存中的图片
	 */
	public void clearDiskCachBitmap() {
		FileUtil.deleteFiles(defaultImgDir);
	}
}
