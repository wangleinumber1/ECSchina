package com.jiajie.jiajieproject.utils;

import java.io.File;

import android.content.Context;
import android.os.Environment;
import android.os.StatFs;
import android.util.Log;

import com.jiajie.jiajieproject.Config;

/**
 * @ClassName SDCardUtil
 * @Description SDCard工具类
 */
public class SDCardUtil {

	public static boolean hasSdcard() {
		String sdCardStatus = Environment.getExternalStorageState();
		if (sdCardStatus.equals(Environment.MEDIA_MOUNTED)) {
			return true;
		}
		return false;
	}

	public static String getStoragePath(String relativePath) {
		File file = null;
		if(hasSdcard()) {
			file =  new File(Environment.getExternalStorageDirectory().getAbsolutePath() + relativePath);
		} else {
			file = new File(Environment.getDownloadCacheDirectory().getAbsolutePath() + relativePath);
		}
		if(!file.exists())
			file.mkdirs();
		return file.getPath();
	}
	
	/*
	 * 判断SD卡可用空间大小
	 */
	public static boolean isAvaiableSpace(Context context){
		if(hasSdcard()){
			String sdcardPath = Environment.getExternalStorageDirectory().getPath();
			StatFs statFs = new StatFs(sdcardPath);
			long blockSize = statFs.getBlockSize();
            long blocks = statFs.getAvailableBlocks();
            long availableSpare = (blocks * blockSize) / (1024 * 1024);
            Log.d("剩余空间", "availableSpare = " + availableSpare);
            if (availableSpare > Config.SD_AVAIABLE_SIZE) {
                return true;
            }
           // Toast.makeText(context, R.string.sdcard_space_unvailable, Toast.LENGTH_LONG).show();
			return false;
		}
		return false;
	}
}
