package com.jiajie.jiajieproject.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;

public class IoUtils {

	private static String imageCacheDir = ".images";
	
	/**
	 * �õ�ͼƬ�Ļ���Ŀ¼
	 * @return
	 */
	@SuppressLint("NewApi")
	public static File getImageCacheDir(){
		File imageDir = null;
		String appName = "egle";
		if(Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED)){
			File dir = Environment.getExternalStorageDirectory();
			String path = dir.getAbsolutePath() + File.separator
					+ appName +File.separator + imageCacheDir;
			imageDir = new File(path);
			if(!imageDir.exists()){
				imageDir.mkdirs();
			}
		}
		return imageDir;
	}
	
	
	/**
	 * ��bitmap���浽ָ��·��
	 * 
	 * @param map
	 * @param path
	 * @return
	 */
	public static void saveBitmap(Bitmap map, String path) {

		FileOutputStream out = null;
		try {

			File file = new File(path);
			if (file.exists()) {
				file.delete();
			}
			CompressFormat format = getFormat(path);
			if (null != format) {
				out = new FileOutputStream(file);
				if (map.compress(format, 100, out)) {
					out.flush();
					out.close();
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		if (map != null && !map.isRecycled()) {
			map.recycle();
			map = null;
		}
	}
	
	private static CompressFormat getFormat(String path) {

		String type = getType(path);
		CompressFormat format = null;
		if (!TextUtils.isEmpty(type)) {
			if (type.equalsIgnoreCase("png")) {
				format = CompressFormat.PNG;
			} else if (type.equalsIgnoreCase("jpg")
					|| type.equalsIgnoreCase("jpe")
					|| type.equalsIgnoreCase("jpeg")) {
				format = CompressFormat.JPEG;
			}
		}

		return format;
	}
	
	private static String getType(String path) {

		String type;
		type = "";
		try {
			int pos = path.lastIndexOf(".");
			if (pos != -1) {
				type = path.substring(pos + 1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return type;
	}
	
	// decodefile �������ڴ�������ж�
	public static Bitmap decodeFile(File f) {
		try {
			// decode image size
			BitmapFactory.Options o = new BitmapFactory.Options();
			o.inJustDecodeBounds = true;
			BitmapFactory.decodeStream(new FileInputStream(f));

			// Find the correct scale value. It should be the power of 2.
			final int REQUIRED_SIZE = 100;
			int width_tmp = o.outWidth, height_tmp = o.outHeight;
			int scale = 1;
			while (true) {
				if (width_tmp / 2 < REQUIRED_SIZE
						|| height_tmp / 2 < REQUIRED_SIZE)
					break;
				width_tmp /= 2;
				height_tmp /= 2;
				scale *= 2;
			}

			// decode with inSampleSize
			BitmapFactory.Options o2 = new BitmapFactory.Options();
			o2.inSampleSize = 1;
			return BitmapFactory.decodeStream(new FileInputStream(f));
		} catch (Exception e) {
			e.printStackTrace();
			Log.e("IoUtils","��ȡ�ļ�����.");
		}
		return null;
	}
	
	/**
	 * ��ͼƬѹ����500k����
	 * 
	 * @param path
	 */
	public static Bitmap compressImageFifty(Bitmap image) {
		return null;
	}
	
	public static void compressImageAndSave(Bitmap image,String path) {

		File file = new File(path);
		if (file.exists()) {
			file.delete();
		}
		
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		image.compress(Bitmap.CompressFormat.JPEG, 100, baos);//����ѹ������������100��ʾ��ѹ������ѹ�������ݴ�ŵ�baos��
		int options = 100;
		while ( baos.toByteArray().length / 1024>500) {	//ѭ���ж����ѹ����ͼƬ�Ƿ����100kb,���ڼ���ѹ��		
			baos.reset();//����baos�����baos
			options -= 10;//ÿ�ζ�����10
			if(options == 0){
				break;
			}
			image.compress(Bitmap.CompressFormat.JPEG, options, baos);//����ѹ��options%����ѹ�������ݴ�ŵ�baos��
		}
//		ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());//��ѹ��������baos��ŵ�ByteArrayInputStream��
//		Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);//��ByteArrayInputStream������ͼƬ
		try {
			FileOutputStream out = new FileOutputStream(file);
			baos.writeTo(out);
			out.flush();
			out.close();
//			if (bitmap.compress(Bitmap.CompressFormat.JPEG, options+10, out)) {
//				out.flush();
//				out.close();
//			}
//			if (bitmap != null && !bitmap.isRecycled()) {
//				bitmap.recycle();
//				bitmap = null;
//			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		//return bitmap;
	}
	
}
