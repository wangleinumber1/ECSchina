package com.jiajie.jiajieproject.activity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.jiajie.jiajieproject.R;
import com.jiajie.jiajieproject.contents.ParamsKey;
import com.jiajie.jiajieproject.db.service.TimeService;
import com.jiajie.jiajieproject.ui.service.UpdateApkService;
import com.jiajie.jiajieproject.utils.IntentUtil;
import com.jiajie.jiajieproject.utils.SDCardUtil;
import com.jiajie.jiajieproject.utils.ToastUtil;
import com.jiajie.jiajieproject.utils.YokaLog;


/*
 * 版本更新弹窗界面
 */
public class UpdateVersionActivity extends BaseActivity implements OnClickListener{
	
	private static final String TAG = "UpdateVersionActivity";
	private String android_link,android_title;
	private boolean mustUpdate;
	@Override
	protected void onInit(Bundle bundle) {
		super.onInit(bundle);
		setContentView(R.layout.update_version); 
		initParams();
		initView();
	}

	private void initParams() {	
	}

	private void initView() {	
		Bundle bundle = getIntent().getExtras();
//		android_title = bundle.getString(ParamsKey.version_title);
//		String android_size = bundle.getString(ParamsKey.version_size);
//		String android_desc = bundle.getString(ParamsKey.version_desc);
		android_link = bundle.getString(ParamsKey.version_url);
//		String android_no = bundle.getString(ParamsKey.android_no);
//		mustUpdate = bundle.getBoolean(ParamsKey.mustUpdate,false);
		Button btn_left = (Button) findViewById(R.id.btn_left);
		btn_left.setOnClickListener(this);
		Button btn_right = (Button) findViewById(R.id.btn_right);
		btn_right.setOnClickListener(this);
//		if(mustUpdate){
//			btn_left.setVisibility(View.GONE);
//			btn_right.setText("立即更新");
//		}
//		if(StringUtil.checkStr(android_title))
//			((TextView)findViewById(R.id.title)).setText(android_title);
//		if(StringUtil.checkStr(android_no))
//			((TextView)findViewById(R.id.version_name)).setText("版本号："+android_no);	
//		if(StringUtil.checkStr(android_title))
//			((TextView)findViewById(R.id.appsize)).setText("软件包大小："+android_size);
//		if(StringUtil.checkStr(android_desc))
//			((TextView)findViewById(R.id.appdesc)).setText("更新一下内容："+android_desc);
		findViewById(R.id.btn_left).setOnClickListener(this);
		findViewById(R.id.btn_right).setOnClickListener(this);

		/*if(StringUtil.checkStr(android_title))
			((TextView)findViewById(R.id.title)).setText(android_title);*/
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_left:
			//立即更新
			if(!SDCardUtil.isAvaiableSpace(mActivity)){
				ToastUtil.showToast(mActivity, 0, "您的存储空间不足，请先释放一些空间",true);
				return;
			}
			YokaLog.d(TAG, "更新===服务启动");
			Bundle bundle = new Bundle();
			bundle.putString(ParamsKey.version_url, android_link);
//			bundle.putString(ParamsKey.version_title, android_title);
			IntentUtil.serviceForward(mActivity, UpdateApkService.class, bundle, true);
			break;
		case R.id.btn_right:
			//暂不更新
			new TimeService(mActivity).saveData();
			finish();
			break;
		default:
			break;
		}
	}
}
