/**
 * 
 */
package com.jiajie.jiajieproject.activity;

import com.jiajie.jiajieproject.R;
import com.jiajie.jiajieproject.adapter.LogisticDetailAdapter;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;

/**
 * 项目名称：NewProject 类名称：HistoryLogisticsActivity 类描述： 创建人：王蕾 创建时间：2015-10-10
 * 下午6:43:03 修改备注：
 */
public class HistoryLogisticsActivity extends BaseActivity {
	private ListView listview;

	@Override
	protected void onInit(Bundle bundle) {
		// TODO Auto-generated method stub
		super.onInit(bundle);
		setContentView(R.layout.logisticsdetail_layout);
		listview = (ListView) findViewById(R.id.logisticdetail_listview);
		Button headerleftImg = (Button) findViewById(R.id.headerleftImg);
		headerleftImg.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}

		});
		listview.setAdapter(new LogisticDetailAdapter(mActivity));
	}

}
