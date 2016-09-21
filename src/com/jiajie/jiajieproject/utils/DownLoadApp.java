package com.jiajie.jiajieproject.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;

import com.jiajie.jiajieproject.R;
import com.jiajie.jiajieproject.activity.UpdateVersionActivity;
import com.jiajie.jiajieproject.contents.ParamsKey;
import com.jiajie.jiajieproject.ui.service.UpdateApkService;

public class DownLoadApp {

	public static void showDialog(final Context mContext, String Message,
			final String appUrl) {

		AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
		builder.setTitle(R.string.title);
		builder.setIcon(R.drawable.logoicon);
		builder.setMessage(Message);
		builder.setPositiveButton("确定", new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				Bundle bundle1 = new Bundle();
				bundle1.putString(ParamsKey.version_url, appUrl);
				IntentUtil.serviceForward(mContext, UpdateApkService.class, bundle1, false);
			}
		});
		builder.setNegativeButton("取消", new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
		builder.create();
		builder.show();
	}

}
