package com.jiajie.jiajieproject.activity;

import java.util.Timer;
import java.util.TimerTask;

import android.location.Location;
import android.os.Bundle;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.location.LocationManagerProxy;
import com.amap.api.location.LocationProviderProxy;
import com.jiajie.jiajieproject.contents.Constants;

public class LocationActivity extends BaseActivity implements
		AMapLocationListener {
	protected LocationManagerProxy mLocationManagerProxy;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		init();
	}

	/** * 初始化定位 */
	private void init() {
		mLocationManagerProxy = LocationManagerProxy.getInstance(this);
		// 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
		// 注意设置合适的定位时间的间隔，并且在合适时间调用removeUpdates()方法来取消定位请求
		// 在定位结束后，在合适的生命周期调用destroy()方法
		// 其中如果间隔时间为-1，则定位只定一次
		mLocationManagerProxy.requestLocationData(
				LocationProviderProxy.AMapNetwork, 500, 15, this);
		mLocationManagerProxy.setGpsEnable(false);

	}

	@Override
	public void onLocationChanged(Location arg0) {

	}

	public void onStatusChanged(String arg0, int arg1, Bundle arg2) {

	}

	@Override
	public void onLocationChanged(AMapLocation arg0) {
		// Constants.Localcity=arg0.getCity();
		Constants.LocalProvice = arg0.getProvince();
		Constants.Localcity = arg0.getCity();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();

	}

	@Override
	public void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		// mLocationManagerProxy.removeUpdates(this);
	}

	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub

	}

	// 停止定位
	protected void stopLocation() {
		Timer timer = new Timer();

		timer.schedule(new TimerTask() {

			@Override
			public void run() {
				mLocationManagerProxy.removeUpdates(LocationActivity.this);

			}
		}, 10000);// 5秒后启动任务

	}
}
