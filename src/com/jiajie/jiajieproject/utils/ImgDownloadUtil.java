package com.jiajie.jiajieproject.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.util.ArrayList;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.support.v4.util.LruCache;
import android.util.Log;

import com.jiajie.jiajieproject.model.StartImg;
import com.jiajie.jiajieproject.net.LCHttpUrlConnection;


/*
 * 图片的下载
 */
public class ImgDownloadUtil {

	private static final String TAG = "ImgDownloadUtil";
	/*private static final String defaultImgDir = SDCardUtil
			.getStoragePath(File.separator  + ".showlargeimg");//+ "."
*/	
	private static final String defaultImgDir = SDCardUtil.getStoragePath(File.separator + "." + "kesifeichuangimg0304");
	private static int memCacheSize = Math.round(Runtime.getRuntime()
			.maxMemory() / (1024 * 10));
	private static LruCache<String, Bitmap> inercachBitmap = new LruCache<String, Bitmap>(
			memCacheSize) {
		// 必须重写此方法，来测量Bitmap的大小
		@Override
		protected int sizeOf(String key, Bitmap value) {
			if (null == value)
				return 0;
			final int bitmapSize = value.getRowBytes() * value.getHeight()
					/ 1024;
			return bitmapSize == 0 ? 1 : bitmapSize;
		}
	}; // 内存缓存
	/*
	 * 下载图片
	 */
	public static void downloadImg(String imgurl){
		YokaLog.d(TAG, "网络下载 图片==imgurl is "+imgurl);
		if(!StringUtil.checkStr(imgurl)) return;
		String imgname = imgurl.substring(imgurl.lastIndexOf("/")+1);
		try {
			if(null != getBitmapByUrl(imgurl)){
				//本地有
				YokaLog.d(TAG, "网络下载 图片==本地有");
			}else{
				//网络下载 
				HttpURLConnection httpCon = LCHttpUrlConnection.getHttpConnection(imgurl, null);
				httpCon.setRequestProperty("Accept-Encoding", "identity"); 
				int code = httpCon.getResponseCode();
				int contentLength = httpCon.getContentLength();
				if(200 == code){
					Bitmap bitmap = getBitmapByStream(httpCon.getInputStream(), contentLength);
					YokaLog.d(TAG, "网络下载 图片==bitmap is "+bitmap+",imgurl is "+imgurl);
					saveBitmap(imgurl, bitmap);
				}
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * 保存Bitmap到本地
	 */
	private static void saveBitmap(String url, Bitmap bitmap) {
		if (StringUtil.checkStr(url)) {
			String imgName = url.substring(url.lastIndexOf("/") + 1);
			String format = imgName.substring(imgName.lastIndexOf(".") + 1);

			if(null == bitmap || bitmap.isRecycled())
				return;
			try {
				File imgFile = new File(defaultImgDir, imgName);
				FileOutputStream fos = new FileOutputStream(imgFile);
				if (format != null && format.trim().equals("png")) {
					bitmap.compress(CompressFormat.PNG, 100, fos);
				} else {
					bitmap.compress(CompressFormat.JPEG, 100, fos);
				}
				inercachBitmap.put(url, bitmap);
				fos.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/*
	 * 根据url从本地获得一个bitmap
	 */
	public static Bitmap getBitmapByUrl(String url) {
		// 先从本地找
		if (StringUtil.checkStr(url)) {
			String imgPath = defaultImgDir + File.separator
					+ url.substring(url.lastIndexOf("/") + 1);
			Log.d(TAG, "查找图片==imgPath is " + imgPath);
			File imgFile = new File(imgPath);
			if (imgFile.exists()) {
				/*try {
					return BitmapFactory.decodeFile(imgPath);
				} catch (OutOfMemoryError outMemError) {
					outMemError.printStackTrace();
					return null;
				}*/
				return BitmapFactory.decodeFile(imgPath);
			}
		}
		return null;
	}
	
	private static Bitmap getBitmapByStream(InputStream inputStream,
			int contentLength) {
		try {
			byte[] byteData = StreamUtil.readStream(inputStream);
			if(null == byteData || byteData.length<=0)
				return null;
			if (contentLength == byteData.length) {
				return BitmapFactory.decodeByteArray(byteData, 0,
						byteData.length);// decodeStream(inputStream);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/*
	 * 检查本地目录是否图片
	 */
	public static boolean checkImgExist(ArrayList<StartImg> startImg){
		YokaLog.d(TAG, "检查本地目录是否图片==startImg is "+startImg);
		if(null == startImg)
			return false;
		int count = startImg.size();
		File fileDir = new File(defaultImgDir);
		ArrayList<StartImg> imgList = startImg;
		if(null == imgList)return false;
		String url1 = imgList.get(0).imgSn;
		url1 = url1.substring(url1.lastIndexOf("/")+1);
		String url2 = imgList.get(count-1).imgSn;
		url2 = url2.substring(url2.lastIndexOf("/")+1);
		return FileUtil.checkExist(defaultImgDir+File.separator+url1) && FileUtil.checkExist(defaultImgDir+File.separator+url2);
	}
	
	public static Bitmap getCachBitmap(String url){
		if(StringUtil.checkStr(url)){
			return inercachBitmap.get(url);
		}
		return null;
	}
	/*
	private Bitmap getOperBitmap(int width,Bitmap bitmap){
		BitmapFactory.Options opts = new BitmapFactory.Options();
		//不去真的解析图片 只是获取图片的头部信息 宽高   injustDecodeBounds
		opts.inJustDecodeBounds = true;
		BitmapFactory.decodeFile("/sdcard/a.jpeg",opts);
		int imgHeight = opts.outHeight;
		int imgWidth = opts.outWidth;
		//需要缩放的高度
		int height = width *imgHeight/imgWidth;
		//真的解析图片
		opts.inJustDecodeBounds = false;
		opts.outHeight = height;
		opts.outWidth = imgWidth;
		return BitmapFactory.decodeByteArray(bytes, 0, bytes.length, options); 
	}*/
}
