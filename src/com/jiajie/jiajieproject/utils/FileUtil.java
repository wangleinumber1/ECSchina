package com.jiajie.jiajieproject.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class FileUtil {

	private static final String TAG = "FileUtil";
	
	/**
	 * 将源文件拷贝到目标文件
	 */
	public static boolean copyFile(File src, File dst) {
		try {
			InputStream in = new FileInputStream(src);
			if (!dst.exists()) {
				dst.createNewFile();
			}
			OutputStream out = new FileOutputStream(dst);
			StreamUtil.copyStream(in, out);
			return true;
		} catch (Exception e) {
			YokaLog.e(e);
		}
		return false;
	}

	/*
	 * 删掉本地文件数据
	 */
	public static void deleteFiles(String filePath){
		if(!StringUtil.checkStr(filePath)) return ;
		deleteImgDatas(new File(filePath));
	}
	/*
	 * 删掉本地文件数据
	 */
	public static void deleteImgDatas(File file) {
		if(file.isDirectory()){
			File[] files = file.listFiles();
			if(null == files || files.length == 0)return;
			for(int i=0;i<files.length;i++){
				if(files[i].isDirectory()){
					deleteImgDatas(files[i]);
				}else{
					files[i].delete();
				}
			}
		}else{
			file.delete();
		}
	}
	
	public static boolean checkExist(String path){
		if(!StringUtil.checkStr(path)) return false;
		YokaLog.d(TAG, "FileUtil==checkExist()==path is "+path);
		return new File(path).exists();
	}
}
