package com.jiajie.jiajieproject.utils;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.UUID;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;

import com.jiajie.jiajieproject.model.DeviceInfo;

/** 
 * @ClassName DeviceInfoUtil 
 * @Description 获取DeviceInfo工具类
 */
public class DeviceInfoUtil {
	
	private static final String TAG = "DeviceInfoUtil";
	/** 
	 * 获取DeviceInfo对象
	 * @param context 要获取对象的环境
	 * @return DeviceInfo对象 
	 */
	public static DeviceInfo getDeviceInfo(Context context) {
		DeviceInfo deviceInfo = DeviceInfo.getInstance();

		// 获取屏幕分辨率
		DisplayMetrics displayMetrics = new DisplayMetrics();
		displayMetrics = context.getResources().getDisplayMetrics();
		deviceInfo.setScreenWidth(displayMetrics.widthPixels);
		deviceInfo.setScreenHeight(displayMetrics.heightPixels);
		
		// 获取DeviceID
		TelephonyManager telephonyManager = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
		String deviceID = telephonyManager.getDeviceId();
		if(!StringUtil.checkStr(deviceID)){
			deviceID = id(context);
		}
		YokaLog.d(TAG, "deviceID is "+deviceID);
		
		deviceInfo.setDeviceID(deviceID);

		// 获取设备型号
		deviceInfo.setDeviceModel(android.os.Build.MODEL);

		// 获取设备系统版本
		deviceInfo.setSystemVersion(android.os.Build.VERSION.RELEASE);

		// 获取软件版本号
		PackageManager packageManager = context.getPackageManager();
		try {
			PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
			deviceInfo.setSoftVersion(packageInfo.versionName);
			deviceInfo.setAppVersionCode(packageInfo.versionCode);
		} catch (NameNotFoundException e) {
			deviceInfo.setSoftVersion("");
			deviceInfo.setAppVersionCode(1);
		}
		return deviceInfo;
	}

	private static String sID = null;  
    private static final String INSTALLATION = "INSTALLATION";  
    public synchronized static String id(Context context) {  
        if (sID == null) {    
            File installation = new File(context.getFilesDir(), INSTALLATION);  
            try {  
                if (!installation.exists())  
                    writeInstallationFile(installation);  
                sID = readInstallationFile(installation);  
            } catch (Exception e) {  
                throw new RuntimeException(e);  
            }  
        }  
        return sID;  
    }  
    private static String readInstallationFile(File installation) throws IOException {  
        RandomAccessFile f = new RandomAccessFile(installation, "r");  
        byte[] bytes = new byte[(int) f.length()];  
        f.readFully(bytes);  
        f.close();  
        
        return new String(bytes);  
    }  
    private static void writeInstallationFile(File installation) throws IOException {  
        FileOutputStream out = new FileOutputStream(installation);  
        String id = UUID.randomUUID().toString();  
        out.write(id.getBytes());  
        out.close();  
    }  
}
