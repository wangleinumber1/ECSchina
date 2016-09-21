/**
 * 
 */
package com.jiajie.jiajieproject.activity;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.TabActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;

import com.jiajie.jiajieproject.R;
import com.jiajie.jiajieproject.contents.UserData;
import com.jiajie.jiajieproject.utils.IntentUtil;

/**
 * 项目名称：NewProject 类名称：GoldMedelandperfisionalActivity 类描述： 创建人：王蕾
 * 创建时间：2015-9-22 下午5:29:12 修改备注：
 */
public class GoldMedelAndperfisionalActivity extends TabActivity implements
		OnClickListener {
	public String TAB_LIST[] = { "金牌服务", "专业服务" };

	public Class mHomeTabClassArray[] = { GoldMedalActivity.class,
			PerfissioinActivity.class };
	public TabHost mTabHost;
	private RadioGroup mRadioGroup;
	private RadioButton radioButton1;
	private RadioButton radioButton2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.goldmedelandperfisionallayout);
		initView();

	}

	public void initView() {
		mTabHost = getTabHost();
		int count = TAB_LIST.length;
		for (int i = 0; i < count; i++) {
			TabSpec tabSpec = mTabHost.newTabSpec(TAB_LIST[i])
					.setIndicator(TAB_LIST[i]).setContent(getTabItemIntent(i));
			mTabHost.addTab(tabSpec);
		}
		mRadioGroup = (RadioGroup) findViewById(R.id.main_radiogroup);
		radioButton1 = (RadioButton) findViewById(R.id.main_fabu);
		radioButton2 = (RadioButton) findViewById(R.id.main_zhaungxiu);
		mTabHost.setCurrentTab(0);
		mRadioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				switch (checkedId) {
				case R.id.main_fabu:
					mTabHost.setCurrentTabByTag(TAB_LIST[0]);
					break;
				case R.id.main_zhaungxiu:
					mTabHost.setCurrentTabByTag(TAB_LIST[1]);
					break;
				}
			}
		});

		((RadioButton) mRadioGroup.getChildAt(0)).toggle();
	}

	public void setRadioGroupCheckById(int id) {
		switch (id) {
		case 1:
			mRadioGroup.check(R.id.main_fabu);

			break;
		case 2:
			mRadioGroup.check(R.id.main_zhaungxiu);

		}
	}

	private Intent getTabItemIntent(int index) {
		Intent intent = new Intent(this, mHomeTabClassArray[index]);

		return intent;
	}

	long backPressTime;

	@Override
	public void onClick(View v) {

	}

}
