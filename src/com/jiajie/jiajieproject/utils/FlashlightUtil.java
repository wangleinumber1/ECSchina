package com.jiajie.jiajieproject.utils;

import android.content.Context;
import android.content.pm.FeatureInfo;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.widget.Toast;
import android.widget.ToggleButton;
/**手电
 * */
public class FlashlightUtil {
	private Context context;
	private Camera camera;
	private ToggleButton button;

	public FlashlightUtil(Context context, Camera camera, ToggleButton button) {
		super();
		this.context = context;
		this.camera = camera;
		this.button = button;
	}

	public void tryOPenLight() {
		PackageManager pm = context.getPackageManager();
		FeatureInfo[] features = pm.getSystemAvailableFeatures();
		for (FeatureInfo f : features) {
			if (PackageManager.FEATURE_CAMERA_FLASH.equals(f.name)) // 判断设备是否支持闪光灯
			{
				if (null == camera) {
					camera = Camera.open();
				}
				Camera.Parameters parameters = camera.getParameters();
				parameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
				camera.setParameters(parameters);
				camera.startPreview();
			}
		}
		if (!pm.hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH)) {
			button.setChecked(false);
			Toast.makeText(context, "你的手机不支持手电", Toast.LENGTH_SHORT).show();
		}
	}

	public void tryClosedLight() {
		if (camera != null) {
			camera.stopPreview();
			camera.release();
			camera = null;
		}
	}

}
