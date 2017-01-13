/**
 * 
 */
package com.jiajie.jiajieproject.activity;

import com.jiajie.jiajieproject.R;
import com.jiajie.jiajieproject.contents.Constants;
import com.jiajie.jiajieproject.utils.IntentUtil;
import com.jiajie.jiajieproject.utils.PublicPopWindow;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * 项目名称：NewProject 类名称：AboutActivity 类描述： 创建人：王蕾 创建时间：2015-9-29 下午2:59:38 修改备注：
 */
public class AboutActivity extends BaseActivity implements OnClickListener {
	private TextView text1;
	ImageView headerleftImg;
	private RelativeLayout phone_button;
	private String[] str = { "确定要拨打热线吗？",Constants.phonenumber, "取消", "确定" };
	private View view;
	private TextView serviceID;

	@Override
	protected void onInit(Bundle bundle) {
		// TODO Auto-generated method stub
		super.onInit(bundle);
		setContentView(R.layout.about_layout);
		view = inflater.inflate(R.layout.about_layout, null);
		headerleftImg = (ImageView) findViewById(R.id.headerleftImg);
		text1 = (TextView) findViewById(R.id.text1);
		serviceID = (TextView) findViewById(R.id.serviceID);
		phone_button = (RelativeLayout) findViewById(R.id.phone_button);
		headerleftImg.setOnClickListener(this);
		phone_button.setOnClickListener(this);
		serviceID.setOnClickListener(this);
	}

	private PopupWindow popupWindow;

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.phone_button:

			popupWindow = PublicPopWindow.publicPop(mActivity, view, inflater,
					new popListener(), str);
			break;
		case R.id.headerleftImg:
			finish();
			break;
		case R.id.serviceID:
			IntentUtil.activityForward(mActivity, WebActivity.class, null, false);
			break;

		default:
			break;
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

	// 退出登录
	class popListener implements OnClickListener {

		@Override
		public void onClick(View v) {

			switch (v.getId()) {
			case R.id.text3:
				if (popupWindow != null & popupWindow.isShowing()) {
					popupWindow.dismiss();
					setPopBackgroud(1);
				}
				break;
			case R.id.text4:
				Intent phoneIntent = new Intent("android.intent.action.CALL",
						Uri.parse("tel:" + Constants.phonenumber));
				startActivity(phoneIntent);

				break;

			}

		}

	}

}
