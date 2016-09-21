package com.jiajie.jiajieproject.utils;

import java.io.File;

import android.os.Environment;

public class getDatebaseUtil {

	private static final String tag = "getDatebaseUtil";
	private static String ADDRESS_DATABASE;

	/**
	 * 本地数据库存存放地址
	 * */
	// public static final String ADDRESS_DATABASE="mnt/sdcard/testQuestion.db";

	// 暂时不用
	// 初始化数据库
//	public static SQLiteDatabase openDatabase(Context context) {
//		SQLiteDatabase db;
//		ADDRESS_DATABASE = getSDPath() + "/testQuestion.db";
//		try {
//			// String databaseFilename = DATABASE_PATH + "/" +
//			// DATABASE_FILENAME;
//
//			if (SDCardUtil.hasSdcard() && SDCardUtil.isAvaiableSpace(context)) {
//				File dir = new File(ADDRESS_DATABASE);
//				Log.d(tag, ADDRESS_DATABASE);
//				if (!dir.exists())
//					dir.mkdir();
//				if (!(new File(ADDRESS_DATABASE)).exists()) {
//					InputStream is = context.getResources().openRawResource(
//							R.raw.jxedt_user);
//					FileOutputStream fos = new FileOutputStream(
//							ADDRESS_DATABASE);
//					byte[] buffer = new byte[8192];
//					int count = 0;
//					while ((count = is.read(buffer)) > 0) {
//						fos.write(buffer, 0, count);
//					}
//					fos.close();
//					is.close();
//				}
//				db = SQLiteDatabase
//						.openOrCreateDatabase(ADDRESS_DATABASE, null);
//				Log.d(tag, "数据库写入" + ADDRESS_DATABASE + "成功");
//				return db;
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//		return null;
//	}

	public static String getSDPath() {
		File sdDir = null;
		boolean sdCardExist = Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED);
		// 判断sd卡是否存在
		if (sdCardExist) {
			sdDir = Environment.getExternalStorageDirectory();// 获取跟目录
		}
		return sdDir.toString();
	}
}
