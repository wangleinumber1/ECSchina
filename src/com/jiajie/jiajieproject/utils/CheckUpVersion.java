/**
 * 
 */
package com.jiajie.jiajieproject.utils;

import android.content.Context;
import android.os.Bundle;

import com.jiajie.jiajieproject.activity.UpdateVersionActivity;
import com.jiajie.jiajieproject.contents.DeviceParamsDB;
import com.jiajie.jiajieproject.contents.ParamsKey;
import com.jiajie.jiajieproject.db.service.TimeService;
import com.jiajie.jiajieproject.model.DeviceInfo;

/**
 * 项目名称：NewProject 类名称：CheckUpVersion 类描述： 创建人：王蕾 创建时间：2015-11-26 下午5:03:24
 * 修改备注：
 */
public class CheckUpVersion {
	private Context mContext;

	public CheckUpVersion(Context con) {
		mContext = con;
	}



	public void checkUp(String versioncode, DeviceInfo deParamsDB,String url) {

		int appVersionCode = DeviceParamsDB.appVersionCode;// 本地版本code
		if (versioncode.equalsIgnoreCase(deParamsDB.getAppVersionCode() + "")) {
			Bundle bundle1 = new Bundle();
			Bundle bundle = new Bundle();
			bundle.putString(ParamsKey.version_url, url);
//			bundle.putString(ParamsKey.version_title, android_title);
			IntentUtil.activityForward(mContext, UpdateVersionActivity.class, bundle, false);
		}
	}
}
