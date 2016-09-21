/**
 * 
 */
package com.jiajie.jiajieproject.activity;

import java.util.ArrayList;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.PopupWindow;

import com.jiajie.jiajieproject.R;
import com.jiajie.jiajieproject.adapter.LogisticStateAdapter;
import com.jiajie.jiajieproject.contents.InterfaceParams;
import com.jiajie.jiajieproject.model.OnlyClass;
import com.jiajie.jiajieproject.utils.PullToRefreshView;
import com.jiajie.jiajieproject.utils.PullToRefreshView.OnFooterRefreshListener;
import com.jiajie.jiajieproject.utils.PullToRefreshView.OnHeaderRefreshListener;
import com.jiajie.jiajieproject.utils.ToastUtil;
import com.jiajie.jiajieproject.widget.MyListView;
import com.mrwujay.cascade.model.produceClass;

/**
 * 项目名称：NewProject 类名称：LogisticsStateActivity 类描述： 创建人：王蕾 创建时间：2015-9-8
 * 下午5:25:21 修改备注：物流查询
 */
public class LogisticsStateActivity extends BaseActivity implements
		OnClickListener {
	private ImageView leftImage;
	private MyListView logisticstatelayout_listview;
	private View mcontentview;
	private LogisticStateAdapter logisticStateAdapter;

	@Override
	protected void onInit(Bundle bundle) {
		// TODO Auto-generated method stub
		super.onInit(bundle);
		setContentView(R.layout.logisticstatelayout);
		initView();
	}

	/**
	 * 布局加载
	 */
	private void initView() {
		leftImage = (ImageView) findViewById(R.id.headerleftImg);
		mcontentview = inflater.inflate(R.layout.logisticstatelisthead, null);
		leftImage.setOnClickListener(this);
		logisticstatelayout_listview = (MyListView) findViewById(R.id.logisticstatelayout_listview);
		logisticStateAdapter = new LogisticStateAdapter(mActivity, mImgLoad);
		logisticstatelayout_listview.setAdapter(logisticStateAdapter);
		logisticstatelayout_listview.addHeaderView(mcontentview);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.headerleftImg:
			finish();
			break;

		default:
			break;
		}

	}

}
