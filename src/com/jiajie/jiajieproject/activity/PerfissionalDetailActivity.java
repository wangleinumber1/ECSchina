/**
 * 
 */
package com.jiajie.jiajieproject.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.jiajie.jiajieproject.R; 
import com.jiajie.jiajieproject.utils.ToastUtil;

/**
 * 项目名称：NewProject 类名称：PerfissionalDetailActivity 类描述： 创建人：王蕾 创建时间：2015-9-24
 * 下午12:00:32 修改备注：
 */
public class PerfissionalDetailActivity extends BaseActivity implements
		OnClickListener, OnCheckedChangeListener {
	private ImageView headerleftImg, headerImage;
	private TextView perfissionaldetail_text3, perfissionaldetail_text2,
			perfissionaldetail_text1, byphone;
	private CheckBox careful;

	@Override
	protected void onInit(Bundle bundle) {
		super.onInit(bundle);
		setContentView(R.layout.perfissionaldetail_layout);
		InitView();
	}

	// 加载布局
	private void InitView() {
		headerleftImg = (ImageView) findViewById(R.id.headerleftImg);
		headerImage = (ImageView) findViewById(R.id.headerImage);
		perfissionaldetail_text3 = (TextView) findViewById(R.id.perfissionaldetail_text3);
		perfissionaldetail_text2 = (TextView) findViewById(R.id.perfissionaldetail_text2);
		perfissionaldetail_text1 = (TextView) findViewById(R.id.perfissionaldetail_text1);
		byphone = (TextView) findViewById(R.id.byphone);
		careful = (CheckBox) findViewById(R.id.careful);
		headerleftImg.setOnClickListener(this);
		careful.setOnCheckedChangeListener(this);
		byphone.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.headerleftImg:
			finish();
			break;
		case R.id.byphone:
			callphone();
			break;

		default:
			break;
		}

	};

	// 打电话
	private void callphone() {
		Intent phoneIntent = new Intent("android.intent.action.CALL",
				Uri.parse("tel:" + "18032853584"));
		// 启动
		startActivity(phoneIntent);
	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		if (isChecked) {
			ToastUtil.showToast(mActivity, "已关注");
		} else {
			ToastUtil.showToast(mActivity, "取消关注");
		}

	}

}
