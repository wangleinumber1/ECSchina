package com.jiajie.jiajieproject.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.bumptech.glide.Glide;


import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v4.util.LruCache;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
//import android.util.LruCache;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class ImageLoad {

	private static String TAG = "ImageLoad";

	public static final int TRIANGLE_SHAPE = 101;// 三角形
	public static final int ROUND_SHAPE = 102;// 圆形
	public static final int SQUARE_SHAPE = 103;// 正方形
	public static final int RECTANGLE_SHAPE = 104;// 长方形
	public static final int ROUND_SQUARE_SHAPE = 105;// 圆角正方形
	public static final int ROUND_RECTANGLE_SHAPE = 105;// 圆角长方形

	public static final String defaultImgDir = SDCardUtil
			.getStoragePath(File.separator + "." + "jiajieimg");
	// 获取系统分配给每个应用程序的最大内存，每个应用系统分配32M
	private static int maxMemory = (int) Runtime.getRuntime().maxMemory();
	private static int memCacheSize = Math.round(Runtime.getRuntime()
			.maxMemory() / (1024 *4));

	private int width,height;
	private Context mContext;
	private LruCache<String, Bitmap> inercachBitmap;
	public ImageLoad(Context con){
		mContext = con;
		//int maxMem = (int) Runtime.getRuntime().maxMemory(); 
	  //  int cacheSize = maxMem / 8; 
		/*final int memClass = ((ActivityManager) mContext.getSystemService( 
	            Context.ACTIVITY_SERVICE)).getMemoryClass();
		final int cacheSize = 1024 * 1024 * memClass / 4; 
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
		};*/
		// 获取到可用内存的最大值，使用内存超出这个值会引起OutOfMemory异常。 
	    // LruCache通过构造函数传入缓存值，以KB为单位。 
	    int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024); 
	    // 使用最大可用内存值的1/8作为缓存的大小。 
	    int cacheSize = maxMemory / 12; 
	    inercachBitmap = new LruCache<String, Bitmap>(cacheSize) { 
			@Override
	        protected int sizeOf(String key, Bitmap bitmap) { 
	            // 重写此方法来衡量每张图片的大小，默认返回图片数量。 
	            return bitmap.getByteCount() / 1024; 
	        } 
	    }; 
	}

	/*
	 * 设置要显示的图片宽高
	 */
	public void setWidthHeight(int width,int height){
		this.width = width;
		this.height = height;
	}
	/*
	 * 设置以全背景形式显示图片
	 */
	private boolean isDrawableBg = false;
	public void setDrawableBg (boolean isDrawableBg) {
		this.isDrawableBg = isDrawableBg;
	}
	
	private boolean isAddFangkuaiBg = true;
	public void setFangkuaiBg(boolean isAddFangkuaiBg){
		this.isAddFangkuaiBg = isAddFangkuaiBg;
	}
	/*
	 * 设置是否ScalType
	 */
	private boolean isScalType;
	public void setScalType(boolean isScalType){
		this.isScalType = isScalType;
	}
	/*
	 * 设置是否在滑动过程中加载图片
	 */
	private boolean mBusy = false;
	public void setBusy(boolean busy) {	
		mBusy = busy;
	}


	
	
	
	private class AsyncImg extends AsyncTask<String, Integer, Bitmap> {
		ImageView mImg;
		String url;
		AsyncImg(ImageView imageView,String url) {
			mImg = imageView;
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
				if(mBusy)
					return;
				displayImg(mImg,url,result);
			}
			AsyncImg.this.cancel(true);
		}
	}

	/*
	 * 根据url从本地或网络获得一个bitmap
	 */
	private Bitmap getBitmapByUrl(String url) {
		// 先从本地找
		if (StringUtil.checkStr(url)) {
			String imgPath = defaultImgDir + File.separator
					+ url.substring(url.lastIndexOf("/") + 1);
//			String imgPath = defaultImgDir + File.separator
//					+ url;
			File imgFile = new File(imgPath);
			if (imgFile.exists()) {
				try {
					return BitmapFactory.decodeFile(imgPath);
					} catch (OutOfMemoryError outMemError) {
					outMemError.printStackTrace();
					clearCachBitmap();
					return null;
				}
			} else {
				return getInStream(url);
			}
		}
		return null;
	}

	private Bitmap getInStream(String imgurl) {
		try {
			HttpURLConnection httpConn = (HttpURLConnection) new URL(imgurl)
					.openConnection();
			httpConn.setRequestProperty("Accept-Encoding", "identity"); 
			httpConn.setChunkedStreamingMode(0);
			
			int code = httpConn.getResponseCode();
			int contentLength = httpConn.getContentLength();
			if (200 == code) {
				try {
					return getBitmapByStream(httpConn.getInputStream(),
							contentLength);// BitmapFactory.decodeStream(httpConn.getInputStream());
				} catch (OutOfMemoryError error) {
					clearCachBitmap();
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

	
	
	/*
	 * 加载图片并显示
	 */
	public void loadImg(ImageView img, String url, int defaultImgId) {
//		Bitmap bitmap = getBitmapFromInnercach(url);
//		img.setTag(url);
//		if (null != bitmap) {
//			displayImg(img, url,bitmap);
//		} else {
//			if(defaultImgId>0){
//				if(isDrawableBg){
//					img.setBackgroundResource(defaultImgId);
//				}else{
//					img.setImageResource(defaultImgId);
//				}
//			}
//			new AsyncImg(img,url).execute();
//		}
		Glide.with(mContext).load(url).centerCrop()
		.placeholder(defaultImgId).crossFade(2000)
        .into(img);
		
	}

	
	private static void saveBitmap(String url, Bitmap bitmap) {
		if (StringUtil.checkStr(url)) {
			String imgName = url.substring(url.lastIndexOf("/") + 1);
			String format = imgName.substring(imgName.lastIndexOf(".") + 1);
			if(null == bitmap || bitmap.isRecycled())
				return;
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

	private Bitmap getBitmapByStream(InputStream inputStream,
			int contentLength) {
		try {
			byte[] byteData = StreamUtil.readStream(inputStream);
			if(null==byteData || byteData.length<=0)
				return null;
			inputStream.close();
			if (contentLength == byteData.length) {
				return BitmapFactory.decodeByteArray(byteData,
						0,byteData.length);// decodeStream(inputStream);	
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	private void displayImg(ImageView img, String url,Bitmap bitmap) {
		if(null != topbar_layout){
			topbar_layout.setVisibility(View.VISIBLE);
		}
		
		if(!StringUtil.checkStr(url) || null == img)
			return;
		if(url.equals(img.getTag()) && url == (String)img.getTag()){
			//width, height
			if(isScalType){
				if(isAddFangkuaiBg){
					//img.setBackgroundResource(R.drawable.fangkuai_grayline_whitebg);
				}
				int bitmapWidth = bitmap.getWidth();
				float scalew = width / (float) bitmapWidth;
				scalew = scalew>1.0f?scalew:1.0f;
				Matrix matrix = new Matrix();
				matrix.postScale(scalew, scalew, 0, 0);
				img.setScaleType(ScaleType.CENTER_CROP);
				img.setImageMatrix(matrix);
				img.setImageBitmap(bitmap);
				
				return;
			}
			if(isDrawableBg){
				img.setBackgroundDrawable(new BitmapDrawable(bitmap));
			}else{
				img.setImageBitmap(bitmap);
			}
			
		}
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

	private View topbar_layout;
	public void setExternView(View topbar_layout) {
		this.topbar_layout = topbar_layout;
	}
}