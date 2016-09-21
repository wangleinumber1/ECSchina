/**
 * 
 */
package com.jiajie.jiajieproject.activity;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.jiajie.jiajieproject.R;
import com.jiajie.jiajieproject.adapter.MyCarefulPartsAdapter;
import com.jiajie.jiajieproject.utils.IntentUtil;
import com.jiajie.jiajieproject.utils.PullToRefreshView;

/**
 * 项目名称：NewProject 类名称：MyCarefulParts 类描述： 创建人：王蕾 创建时间：2015-9-29 下午4:44:20 修改备注：
 */
public class MyCarefulServicePoductActivity extends BaseActivity implements
		OnItemClickListener,
		OnClickListener {
	private ImageView headerleftImg;
	private PullToRefreshView mycarefulpartslayout_pull;
	private GridView mycarefulpartslayout_gridview;
	private TextView headercentertextview;
	private MyCarefulPartsAdapter myCarefulPartsAdapter;
	@Override
	protected void onInit(Bundle bundle) {
		// TODO Auto-generated method stub
		super.onInit(bundle);
		setContentView(R.layout.mycarefulparts_layout);
		InitView();
	}

	private void InitView() {
		headercentertextview=(TextView) findViewById(R.id.headercentertextview);
//		mycarefulpartslayout_pull = (PullToRefreshView) findViewById(R.id.mycarefulpartslayout_pull);
		mycarefulpartslayout_gridview = (GridView) findViewById(R.id.mycarefulpartslayout_gridview);
//		mycarefulpartslayout_gridview.setAdapter(new MyCarefulPartsAdapter(
//				mActivity, mImgLoad));
		myCarefulPartsAdapter=new MyCarefulPartsAdapter(mActivity, mImgLoad);
		mycarefulpartslayout_gridview.setAdapter(myCarefulPartsAdapter);
		mycarefulpartslayout_gridview.setOnItemClickListener(this);
//		mycarefulpartslayout_pull.setOnFooterRefreshListener(this);
//		mycarefulpartslayout_pull.setOnHeaderRefreshListener(this);
		headerleftImg = (ImageView) findViewById(R.id.headerleftImg);
		headerleftImg.setOnClickListener(this);
		// 消除gridview黄色边框
		mycarefulpartslayout_gridview.setSelector(new ColorDrawable(
				Color.TRANSPARENT));
		headercentertextview.setText("我关注的服务产品");
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
//	@Override
//	public void onHeaderRefresh(PullToRefreshView view) {
//		view.onHeaderRefreshComplete();
//
//	}
//
//	@Override
//	public void onFooterRefresh(PullToRefreshView view) {
//		view.onFooterRefreshComplete();
//
//	}
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		IntentUtil.activityForward(mActivity, ServiceProductDetailActivity.class, null,false);
	}

}
