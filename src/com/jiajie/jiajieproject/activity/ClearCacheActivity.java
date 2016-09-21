/**
 * 
 */
package com.jiajie.jiajieproject.activity;

import com.jiajie.jiajieproject.R;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

/**
 * 项目名称：NewProject 类名称：ClearCacheActivity 类描述： 创建人：王蕾 创建时间：2016-8-26 上午9:55:38
 * 修改备注：
 */
public class ClearCacheActivity extends BaseActivity implements OnClickListener {
	public static final String TAG = "ClearCacheActivity";
	private String Str[];

	@Override
	protected void onInit(Bundle bundle) {
		// TODO Auto-generated method stub
		super.onInit(bundle);
		setContentView(R.layout.popwindow_layout);
		Str = getIntent().getExtras().getStringArray(TAG);
		TextView text1 = (TextView) findViewById(R.id.text1);
		TextView text2 = (TextView) findViewById(R.id.text2);
		TextView text3 = (TextView) findViewById(R.id.text3);
		TextView text4 = (TextView) findViewById(R.id.text4);
		text1.setText(Str[0]);
		text2.setText(Str[1]);
		text3.setText(Str[2]);
		text4.setText(Str[3]);
		text3.setOnClickListener(this);
		text4.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.text3:
			setResult(RESULT_OK);
			break;
		case R.id.text4:
			setResult(RESULT_CANCELED);
			break;

		}
		finish();
	}
}
