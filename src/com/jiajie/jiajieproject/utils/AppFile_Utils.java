package com.jiajie.jiajieproject.utils;
import java.io.File;

import android.content.Context;
import android.os.Environment;

public class AppFile_Utils {

	// 检查外部存储(是否挂载，可读或可写)
	private static boolean isMounted = false;
	private static boolean isReadOnly = false;
	private static final String APP_IMG_CACHEDIR = "/56jkw/cache/img";
	private static final String APP_JSON_CACHEDIR = "/56jkw/cache/json";
	private static final String APP_SAVEIMG_DIR = "/56jkw/saveImgs";
	private static final String APP_VERSION_UPDATEDIR = "/56jkw/update";

	// 是否挂载
	public static boolean isMounted() {
		final String state = Environment.getExternalStorageState();
		if (Environment.MEDIA_MOUNTED.equals(state)) {
			isMounted = true;
		}
		return isMounted;
	}

	// 是否可读
	public static boolean isReadOnly() {
		final String state = Environment.getExternalStorageState();
		if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
			isReadOnly = true;
		}
		return isReadOnly;
	}

	public static String getSDCARD_PATH() {
		if (isMounted() && !isReadOnly()) {
			return Environment.getExternalStorageDirectory().getAbsolutePath();
		}
		return "";
	}

	// 创建应用程序的图片保存目录
	public static void createAppSaveImg(Context context) {
		if (!"".equals(getSDCARD_PATH())) {
			File cacheFile = new File(getSDCARD_PATH() + APP_SAVEIMG_DIR);
			if (!cacheFile.exists()) {
				cacheFile.mkdirs();
			}
		} else {
			File file = new File(context.getFilesDir().getAbsolutePath()
					+ APP_SAVEIMG_DIR);
			if (!file.exists()) {
				file.mkdirs();
			}
		}
	}

	// 创建应用程序的json数据保存目录
	public static void createAppSaveJson(Context context) {
		if (!"".equals(getSDCARD_PATH())) {
			File cacheFile = new File(getSDCARD_PATH() + APP_JSON_CACHEDIR);
			if (!cacheFile.exists()) {
				cacheFile.mkdirs();
			}
		} else {
			File file = new File(context.getFilesDir().getAbsolutePath()
					+ APP_JSON_CACHEDIR);
			if (!file.exists()) {
				file.mkdirs();
			}
		}
	}

	public static String getAppSaveJson(Context context) {
		createAppSaveJson(context);
		if (!"".equals(getSDCARD_PATH())) {
			return getSDCARD_PATH() + APP_JSON_CACHEDIR;
		} else {
			return context.getFilesDir().getAbsolutePath() + APP_JSON_CACHEDIR;
		}
	}

	// 创建应用程序的更新目录
	public static void createAppUpdateVersion(Context context) {
		if (!"".equals(getSDCARD_PATH())) {
			File cacheFile = new File(getSDCARD_PATH() + APP_VERSION_UPDATEDIR);
			if (!cacheFile.exists()) {
				cacheFile.mkdirs();
			}
		} else {
			File file = new File(context.getFilesDir().getAbsolutePath()
					+ APP_VERSION_UPDATEDIR);
			if (!file.exists()) {
				file.mkdirs();
			}
		}
	}

	public static String getAppUpdateVersion(Context context) {
		createAppUpdateVersion(context);
		if (!"".equals(getSDCARD_PATH())) {
			return getSDCARD_PATH() + APP_VERSION_UPDATEDIR;
		} else {
			return context.getFilesDir().getAbsolutePath()
					+ APP_VERSION_UPDATEDIR;
		}
	}

	public static String getAppSaveImg(Context context) {
		createAppSaveImg(context);
		if (!"".equals(getSDCARD_PATH())) {
			return getSDCARD_PATH() + APP_SAVEIMG_DIR;
		} else {
			return context.getFilesDir().getAbsolutePath() + APP_SAVEIMG_DIR;
		}
	}

	// 创建应用程序的图片缓存目录
	public static void createAppImgCache(Context context) {
		if (!"".equals(getSDCARD_PATH())) {
			File cacheFile = new File(getSDCARD_PATH() + APP_IMG_CACHEDIR);
			if (!cacheFile.exists()) {
				cacheFile.mkdirs();
			}
		} else {
			File file = new File(context.getFilesDir().getAbsolutePath()
					+ APP_IMG_CACHEDIR);
			if (!file.exists()) {
				file.mkdirs();
			}
		}
	}

	public static String getAppImgCache(Context context) {
		createAppImgCache(context);
		if (!"".equals(getSDCARD_PATH())) {
			return getSDCARD_PATH() + APP_IMG_CACHEDIR;
		} else {
			return context.getFilesDir().getAbsolutePath() + APP_IMG_CACHEDIR;
		}
	}

	// 清理缓存
	public static boolean clearCache(Context context) {
		boolean delted = false;
		if (!(getSDCARD_PATH() + APP_IMG_CACHEDIR)
				.equals(getAppImgCache(context))) {
			return true;
		}
		File cacheFile = new File(getAppImgCache(context) + "/volley");
		File[] files = cacheFile.listFiles();
		if (files.length > 0) {
			for (int i = 0; i < files.length; i++) {
				files[i].delete();
			}
			delted = true;
		}
		return delted;
	}
}

