/**
 * 
 */
package com.jiajie.jiajieproject.activity;

import com.jiajie.jiajieproject.R;
import com.jiajie.jiajieproject.contents.Constants;
import com.jiajie.jiajieproject.utils.IntentUtil;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

/**
 * 项目名称：NewProject 类名称：GoldMedalActivity 类描述： 创建人：王蕾 创建时间：2015-9-8 下午5:24:49
 * 修改备注：专业服务
 */
public class PerfissioinActivity extends BaseActivity implements
		OnClickListener {
	private ImageView goldbarIamge;
	private Button button1, button2, button3, button4, button5, button6;

	@Override
	protected void onInit(Bundle bundle) {
		// TODO Auto-generated method stub
		super.onInit(bundle);
		setContentView(R.layout.perfissionservicelayout);
		initView();
	}

	// 添加布局
	private void initView() {
		goldbarIamge = (ImageView) findViewById(R.id.goldbarIamge);
		button1 = (Button) findViewById(R.id.button1);
		button2 = (Button) findViewById(R.id.button2);
		button3 = (Button) findViewById(R.id.button3);
		button4 = (Button) findViewById(R.id.button4);
		button5 = (Button) findViewById(R.id.button5);
		button6 = (Button) findViewById(R.id.button6);
		button1.setOnClickListener(this);
		button2.setOnClickListener(this);
		button3.setOnClickListener(this);
		button4.setOnClickListener(this);
		button5.setOnClickListener(this);
		button6.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		Bundle bundle=new Bundle();
		switch (v.getId()) {
		case R.id.button1:
			bundle.putString("id", Constants.PerfisionallistCID1);
			IntentUtil.activityForward(mActivity,
					PerfisionallistActivity.class, bundle, false);
			break;
		case R.id.button2:
			bundle.putString("id", Constants.PerfisionallistCID2);
			IntentUtil.activityForward(mActivity,
					PerfisionallistActivity.class, bundle, false);
			break;
		case R.id.button3:
			bundle.putString("id", Constants.PerfisionallistCID3);
			IntentUtil.activityForward(mActivity,
					PerfisionallistActivity.class, bundle, false);
			break;
		case R.id.button4:
			bundle.putString("id", Constants.PerfisionallistCID4);
			IntentUtil.activityForward(mActivity,
					PerfisionallistActivity.class, bundle, false);
			break;
		case R.id.button5:
			bundle.putString("id", Constants.PerfisionallistCID5);
			IntentUtil.activityForward(mActivity,
					PerfisionallistActivity.class, bundle, false);
			break;
		case R.id.button6:
			bundle.putString("id", Constants.PerfisionallistCID6);
			IntentUtil.activityForward(mActivity,
					PerfisionallistActivity.class, bundle, false);
			break;

		default:
			break;
		}

	}

}
