package com.jiajie.jiajieproject.db.manager;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import com.jiajie.jiajieproject.R;

import android.content.Context;
import android.content.res.AssetManager;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.util.Log;

public class DBManager {
	public static String TABLENAME = "web_note1";
	private SQLiteDatabase db;
	int i = 1;
	// 数据库存储路径
	String filePath = "data/data/com.jiajie.jiajieproject/city1.db";
	String ProjectfilePath = "data/data/com.jiajie.jiajieproject/wanghe.db";
	// 数据库存放的文件夹 data/data/com.main.jh 下面
	String pathStr = "data/data/com.jiajie.jiajieproject";

	// private final int BUFFER_SIZE = 1024;
	// public static final String DB_NAME = "city_cn.s3db";
	// public static final String PACKAGE_NAME = "com.jiajie.jiajieproject";
	// public static final String DB_PATH = "/data"
	// + Environment.getDataDirectory().getAbsolutePath() + "/"
	// + PACKAGE_NAME;
	// private SQLiteDatabase database;
	// private Context context;
	// private File file = null;

	public DBManager(Context context,int resouceId) {
		db = openDatabase(context, resouceId);
		db = ProjectopenDatabase();
	}

	// public void openDatabase() {
	// Log.e("cc", "openDatabase()");
	// this.db = this.openDatabase();
	// }
	// //
	public SQLiteDatabase getDatabase() {
		Log.e("cc", "getDatabase()");
		return this.db;
	}

	

	public SQLiteDatabase openDatabase(Context context, int resourdId) {
		System.out.println("filePath:" + filePath);
		File jhPath = new File(filePath);
		// 查看数据库文件是否存在
		if (jhPath.exists()) {
			Log.i("test", "存在数据库");
			// 存在则直接返回打开的数据库
			return SQLiteDatabase.openOrCreateDatabase(jhPath, null);
		} else {
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
				// 得到资源
				AssetManager am = context.getAssets();
				// 得到数据库的输入流
				// InputStream is = am.open("china_city");
				InputStream is = context.getResources().openRawResource(
						resourdId);
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
				return null;
			}
			// 如果没有这个数据库 我们已经把他写到SD卡上了，然后在执行一次这个方法 就可以返回数据库了
			return openDatabase(context, resourdId);
		}
	}

	// 主要用来加载项目信息页面数据
	public SQLiteDatabase ProjectopenDatabase() {
		System.out.println("ProjectfilePath:" + ProjectfilePath);
		File jhPath = new File(ProjectfilePath);
		// 查看数据库文件是否存在
		// 存在则直接返回打开的数据库
		return SQLiteDatabase.openOrCreateDatabase(jhPath, null);

	}

	// public SQLiteDatabase ProjectopenDatabase(Context context, int resourdId)
	// {
	// System.out.println("ProjectfilePath:" + ProjectfilePath);
	// File jhPath = new File(ProjectfilePath);
	// // 查看数据库文件是否存在
	// if (jhPath.exists()) {
	// Log.i("test", "存在数据库");
	// // 存在则直接返回打开的数据库
	// return SQLiteDatabase.openOrCreateDatabase(jhPath, null);
	// } else {
	// // 不存在先创建文件夹
	// File path = new File(pathStr);
	// Log.e("test", "pathStr=" + path);
	// if (path.mkdir()) {
	// Log.e("test", "创建成功");
	// } else {
	// Log.e("test", "创建失败");
	// }
	// ;
	// try {
	// // 得到资源
	// AssetManager am = context.getAssets();
	// // 得到数据库的输入流
	// // InputStream is = am.open("china_city");
	// InputStream is = context.getResources().openRawResource(
	// resourdId);
	// Log.e("test", is + "");
	// // 用输出流写到SDcard上面
	// FileOutputStream fos = new FileOutputStream(jhPath);
	// Log.e("test", "fos=" + fos);
	// Log.e("test", "jhPath=" + jhPath);
	// // 创建byte数组 用于1KB写一次
	// byte[] buffer = new byte[1024];
	// int count = 0;
	// while ((count = is.read(buffer)) > 0) {
	// Log.e("test", "得到" + i);
	// i++;
	// fos.write(buffer, 0, count);
	// }
	// // 最后关闭就可以了
	// fos.flush();
	// fos.close();
	// is.close();
	// } catch (IOException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// return null;
	// }
	// // 如果没有这个数据库 我们已经把他写到SD卡上了，然后在执行一次这个方法 就可以返回数据库了
	// return ProjectopenDatabase(context, resourdId);
	// }
	// }

	public void closeDatabase() {
		Log.e("cc", "closeDatabase()");
		if (this.db != null)
			this.db.close();
	}
}