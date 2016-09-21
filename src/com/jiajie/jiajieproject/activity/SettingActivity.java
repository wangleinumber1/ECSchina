/**
 * 
 */
package com.jiajie.jiajieproject.activity;

import cn.jpush.android.api.JPushInterface;

import com.jiajie.jiajieproject.R;
import com.jiajie.jiajieproject.adapter.PopwindowListAdapter;
import com.jiajie.jiajieproject.utils.IntentUtil;
import com.jiajie.jiajieproject.utils.PublicPopWindow;

import android.app.Application;
import android.graphics.drawable.PaintDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;

/**
 * 项目名称：NewProject 类名称：SettingActivity 类描述： 创建人：王蕾 创建时间：2015-9-28 下午5:00:27
 * 修改备注：
 */
public class SettingActivity extends BaseActivity implements OnClickListener {
	private RelativeLayout settinglayout1, settinglayout2, settinglayout3;
	private ImageView headerleftImg, settingImageswith;
	private View view;
	private PopupWindow popupWindow;
	private String[] str = { "清除缓存", "根据缓存文件的大小，清除从几秒到几分钟不等，请耐心等待。", "清除", "取消" };
	int i = 0;

	@Override
	protected void onInit(Bundle bundle) {
		// TODO Auto-generated method stub
		super.onInit(bundle);
		setContentView(R.layout.setting_layout);
		InitView();
	}

	private void InitView() {
		view = inflater.inflate(R.layout.setting_layout, null);
		settinglayout1 = (RelativeLayout) findViewById(R.id.settinglayout1);
		settinglayout2 = (RelativeLayout) findViewById(R.id.settinglayout2);
		settinglayout3 = (RelativeLayout) findViewById(R.id.settinglayout3);
		headerleftImg = (ImageView) findViewById(R.id.headerleftImg);
		settingImageswith = (ImageView) findViewById(R.id.settingImageswith);
		settinglayout1.setOnClickListener(this);
		settinglayout2.setOnClickListener(this);
		settinglayout3.setOnClickListener(this);
		headerleftImg.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.settinglayout1:
			if (i % 2 == 0) {
				settingImageswith.setImageResource(R.drawable.shezhiyebtn);
				stopPush(mContext);
				i++;
			} else {
				settingImageswith.setImageResource(R.drawable.shezhiyebtnup);
				 restartPush(mContext);
				i++;
			}
			break;
		case R.id.settinglayout2:

			popupWindow = PublicPopWindow.publicPop(this, view, inflater,
					new popListener(), str);
			break;
		case R.id.settinglayout3:
			IntentUtil.activityForward(mActivity, AboutActivity.class, null,
					false);
			break;
		case R.id.headerleftImg:
			finish();
			break;

		default:
			break;
		}

	}

	class popListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			if (popupWindow != null & popupWindow.isShowing()) {
				popupWindow.dismiss();
				setPopBackgroud(1);

			}

		}

	}


	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		if (popupWindow != null) {
			popupWindow.dismiss();
			setPopBackgroud(1);
		}
	}
	
	/**
	 * 停止推送
	 * */
	public void stopPush(Application application) {
		JPushInterface.stopPush(application);
	}

	/**
	 * 重启推送
	 * */
	public void restartPush(Application application) {
		JPushInterface.resumePush(application);
	}
}
