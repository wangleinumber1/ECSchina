package com.jiajie.jiajieproject.activity;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import cn.jpush.android.api.JPushInterface;

import com.jiajie.jiajieproject.ActivityManager;
import com.jiajie.jiajieproject.R;
import com.jiajie.jiajieproject.YmallApplication;
import com.jiajie.jiajieproject.db.service.UserDataService;
import com.jiajie.jiajieproject.net.service.JosnService;
import com.jiajie.jiajieproject.utils.ImageLoad;
import com.jiajie.jiajieproject.utils.IntentUtil;
import com.jiajie.jiajieproject.utils.ToastUtil;
import com.jiajie.jiajieproject.utils.YokaLog;
import com.jiajie.jiajieproject.widget.MyProgressDialog;

public class BaseActivity extends FragmentActivity {
	private static final String TAG = "BaseActivity";
	protected Activity mActivity;
	protected YmallApplication mContext;
	protected LayoutInflater inflater;
	public String currentUID;
	protected UserDataService userDataService;
	protected TextView centertextview;
	protected LayoutInflater mInflater;
	public Handler mHandler = new Handler();
	protected ImageLoad mImgLoad;
	protected ActivityManager activityManager;
	protected JosnService jsonservice;
	
	@Override
	protected void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		mActivity = this;
		mContext = (YmallApplication) this.getApplicationContext();
		activityManager = ActivityManager.getActivityManagerInstance();
		mInflater = LayoutInflater.from(mActivity);
		mImgLoad = new ImageLoad(mActivity);
		mImgLoad.clearCachBitmap();
		// 设置屏幕为竖屏
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		inflater = getLayoutInflater().from(mActivity);
		jsonservice = new JosnService(mActivity);
		userDataService = new UserDataService(mContext);
		onInit(bundle);
	}

	/*
	 * 每个activity的onCreate()方法的替代品，对view的初始化操作在该方法里执行
	 */
	protected void onInit(Bundle bundle) {
	}

	@Override
	protected void onResume() {
		super.onResume();
		JPushInterface.onResume(this);
	}

	@Override
	protected void onPause() {
		super.onPause();
		JPushInterface.onPause(this);
	}

	/*
	 * 左边button的隐藏
	 */
	protected void hideLeftBtn() {
		View leftImg = findViewById(R.id.leftImg);
		if (null != leftImg)
			leftImg.setVisibility(View.INVISIBLE);
	}

	/*
	 * 右边button的隐藏
	 */
	protected void hideRightBtn() {
		View rightImg = findViewById(R.id.rightImg);
		if (null != rightImg)
			rightImg.setVisibility(View.INVISIBLE);
	}

	/*
	 * 设置中间背景色
	 */
	protected void setCentreTextView(int resID) {
		RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.headlayout);
		if (null != relativeLayout)
			relativeLayout.setBackgroundColor(resID);
	}

	/*
	 * 设置中间textView上的文字
	 */
	protected void setCentreTextView(String s, int color) {
		TextView textview = (TextView) findViewById(R.id.headercentertextview);
		if (null != textview)
			textview.setText(s);
		textview.setTextColor(color);
	}

	/*
	 * 设置左边button的文字
	 */
	protected void setLeftBtntext(String text, OnClickListener clickListener) {
		Button leftImg = (Button) findViewById(R.id.leftImg);
		if (null != leftImg) {
			leftImg.setText(text);
			leftImg.setOnClickListener(clickListener);
		}
	}

	/*
	 * 设置右边button的文字
	 */
	protected void setRightBtntext(String text, OnClickListener clickListener) {
		Button rightImg = (Button) findViewById(R.id.rightImg);
		if (null != rightImg) {
			rightImg.setText(text);
			rightImg.setOnClickListener(clickListener);
		}
	}

	/*
	 * 设置左边边button的图片
	 */
	protected void setLeftBtnImage(int resID, OnClickListener clickListener) {
		Button leftImg = (Button) findViewById(R.id.leftImg);
		if (null != leftImg) {
			leftImg.setCompoundDrawables(null, null, this.getResources()
					.getDrawable(resID), null);
			leftImg.setOnClickListener(clickListener);
		}
	}

	/*
	 * 设置右边边button的图片
	 */
	protected void setRightBtnImage(int resID, OnClickListener clickListener) {
		ImageView righttImg = (ImageView) findViewById(R.id.rightImg);
		if (null != righttImg) {
			righttImg.setImageResource(resID);
			righttImg.setOnClickListener(clickListener);
		}
	}

	/*
	 * 应用退出的处理
	 */
	@Override
	protected void onDestroy() {
		super.onDestroy();
		mImgLoad.clearCachBitmap();

	}

	@Override
	public void finish() {
		super.finish();
		/*
		 * overridePendingTransition(R.anim.push_left_out, R.anim.push_left_in);
		 */
		// (mContext.getActivityManager()).popActivity(this);
	}

	protected MyProgressDialog myProgressDialog;

	protected abstract class MyAsyncTask extends
			AsyncTask<Object, Object, Object> {
		protected MyAsyncTask() {
			myProgressDialog = new MyProgressDialog(mActivity);
			myProgressDialog.initDialog();

		}

		@Override
		protected abstract Object doInBackground(Object... params);

		@Override
		protected void onPostExecute(Object result) {
			super.onPostExecute(result);
			YokaLog.d("MyAsyncTask", "MyAsyncTask==onPostExecute()" + result);
			if (myProgressDialog.isShowing()) {
				myProgressDialog.colseDialog();
			}
		}
	}

	// 判断是否是手机号
	public boolean isMobileNum(String mobiles) {
		Pattern p = Pattern
				.compile("^[1]([0-8]{1}[0-9]{1}|59|58|88|89)[0-9]{8}");
		Matcher m = p.matcher(mobiles);
		return m.matches();
	}

	// 判断手机号是否有效
	public boolean isMobileValid(String mobile) {

		if (TextUtils.isEmpty(mobile)) {
			ToastUtil.showToast(mActivity, 0, "手机号不能为空", false);
			return false;
		}
		if (!isMobileNum(mobile)) {
			ToastUtil.showToast(mActivity, 0, "手机号格式错误", false);
			return false;
		}
		return true;
	}

	public boolean isEmail(String email) {

		String str = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";

		Pattern p = Pattern.compile(str);

		Matcher m = p.matcher(email);

		return m.matches();

	}

	// 判断是否全是数字

	public boolean isNumeric(String str) {

		Pattern pattern = Pattern.compile("[0-9]*");

		Matcher isNum = pattern.matcher(str);

		if (!isNum.matches()) {

			return false;

		}

		return true;

	}

	// 设置半透明背景
	public void setPopBackgroud(float alpha) {
		WindowManager.LayoutParams params = mActivity.getWindow()
				.getAttributes();
		params.alpha = alpha;
		mActivity.getWindow().setAttributes(params);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		switch (requestCode) {
		case 16:
			if (requestCode == RESULT_OK) {
				if (data != null) {
					String result = data.getStringExtra("result");
					Bundle bundle = new Bundle();
					bundle.putString("pncode", result);
					IntentUtil.activityForward(mActivity,
							CartsClassActivity.class, bundle, false);
				}
			}
			break;

		default:
			break;
		}

		super.onActivityResult(requestCode, resultCode, data);
	}

}
