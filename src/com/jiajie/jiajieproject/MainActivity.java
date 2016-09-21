package com.jiajie.jiajieproject;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.app.TabActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;
import android.widget.Toast;

import com.jiajie.jiajieproject.activity.CarShopppingActivity;
import com.jiajie.jiajieproject.activity.ClearCacheActivity;
import com.jiajie.jiajieproject.activity.GoldMedalActivity;
import com.jiajie.jiajieproject.activity.LoginActivity;
import com.jiajie.jiajieproject.activity.MineActivity;
import com.jiajie.jiajieproject.activity.PartsActivity;
import com.jiajie.jiajieproject.contents.Constants;
import com.jiajie.jiajieproject.db.service.UserDataService;
import com.jiajie.jiajieproject.utils.IntentUtil;
import com.jiajie.jiajieproject.utils.ToolNetwork;

public class MainActivity extends TabActivity {

	// public static String TAB_LIST[] = { "备件", "金牌专业", "项目信息", "我" };
	public static String TAB_LIST[] = { "首页", "分类", "购物车", "我的" };

	// public static Class mHomeTabClassArray[] = { PartsActivity.class,
	// GoldMedelAndperfisionalActivity.class,
	// NewProjectMessageAcitivity.class, MineActivity.class };
	public static Class mHomeTabClassArray[] = { PartsActivity.class,
			GoldMedalActivity.class, CarShopppingActivity.class,
			MineActivity.class };
	public static TabHost mTabHost;
	private RadioGroup mRadioGroup;
	private ActivityManager activityManager;
	private RadioButton radioButton1;
	private RadioButton radioButton2;
	private RadioButton radioButton3;
	private RadioButton radioButton4;
	private UserDataService userDataService;
	private boolean isfromLogin;
	public static String TAG = "MainActivity";
	private Myhandler myhandler = new Myhandler();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		activityManager = ActivityManager.getActivityManagerInstance();
		userDataService = new UserDataService(this);
		// MainActivity.this.startService(new Intent(MainActivity.this,
		// DownloadService.class));
		initView();
		// setRadioGroupCheckById(getIntent().getIntExtra("id", 1));
		ToolNetwork.getInstance().init(this).validateNetWork();
	}

	@SuppressWarnings("deprecation")
	public void initView() {
		mTabHost = getTabHost();
		int count = TAB_LIST.length;
		for (int i = 0; i < count; i++) {
			TabSpec tabSpec = mTabHost.newTabSpec(TAB_LIST[i])
					.setIndicator(TAB_LIST[i]).setContent(getTabItemIntent(i));
			mTabHost.addTab(tabSpec);
		}
		mRadioGroup = (RadioGroup) findViewById(R.id.radiogroup);
		radioButton1 = (RadioButton) findViewById(R.id.rb_mainpage);
		radioButton2 = (RadioButton) findViewById(R.id.rb_class);
		radioButton3 = (RadioButton) findViewById(R.id.rb_cart);
		radioButton4 = (RadioButton) findViewById(R.id.rb_mine);

		mTabHost.setCurrentTab(0);
		mRadioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				switch (checkedId) {

				case R.id.rb_mainpage:
					mTabHost.setCurrentTabByTag(TAB_LIST[0]);
					break;
				case R.id.rb_class:
					mTabHost.setCurrentTabByTag(TAB_LIST[1]);
					break;

				case R.id.rb_cart:
					mTabHost.setCurrentTabByTag(TAB_LIST[2]);
					break;
				case R.id.rb_mine:
					if (userDataService.getUserId() == null) {
						String[] str = { "登录", "是否登陆", "是", "否" };
						Bundle bundle = new Bundle();
						bundle.putStringArray(ClearCacheActivity.TAG, str);
						IntentUtil.startActivityForResult(MainActivity.this,
								ClearCacheActivity.class, 1111, bundle);
					} else {
						mTabHost.setCurrentTabByTag(TAB_LIST[3]);
					}

					break;

				}
			}
		});

		((RadioButton) mRadioGroup.getChildAt(0)).toggle();
	}

	public void setRadioGroupCheckById(int id) {
		switch (id) {
		case 1:
			mRadioGroup.check(R.id.rb_mainpage);

			break;

		case 2:
			mRadioGroup.check(R.id.rb_class);

			break;

		case 3:
			mRadioGroup.check(R.id.rb_cart);

			break;
		case 4:

			mRadioGroup.check(R.id.rb_mine);

			break;

		}
	}

	/**/
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		if (Constants.isfromcarshoping)
			mRadioGroup.check(R.id.rb_mainpage);
		Constants.isfromcarshoping = false;

	}

	private Intent getTabItemIntent(int index) {
		Intent intent = new Intent(this, mHomeTabClassArray[index]);

		return intent;
	}

	long backPressTime;

	// 再按一次退出程序
	@Override
	public boolean dispatchKeyEvent(KeyEvent event) {
		if (event.getKeyCode() == KeyEvent.KEYCODE_BACK
				&& event.getAction() == KeyEvent.ACTION_DOWN
				&& event.getRepeatCount() == 0) {
			long current = System.currentTimeMillis();
			if (current - backPressTime > 2000) {
				backPressTime = current;
				Toast.makeText(MainActivity.this, "再次按返回键退出程序", 0).show();
			} else {
				activityManager.popAllActivity();
				finish();
			}
			return true;
		}
		return super.dispatchKeyEvent(event);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		switch (requestCode) {
		case 1111:
			if (resultCode == RESULT_OK) {
				myhandler.sendEmptyMessage(1);
			} else {
				myhandler.sendEmptyMessage(2);
			}
			break;

		}

	}

	class Myhandler extends Handler {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			switch (msg.what) {
			case 1:
				IntentUtil.activityForward(MainActivity.this,
						LoginActivity.class, null, false);
				((RadioButton) mRadioGroup.getChildAt(0)).toggle();
				break;
			case 2:

				((RadioButton) mRadioGroup.getChildAt(0)).toggle();
				break;

			}

		}

	}

}
