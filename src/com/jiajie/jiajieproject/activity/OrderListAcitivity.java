package com.jiajie.jiajieproject.activity;

import java.util.ArrayList;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

import com.jiajie.jiajieproject.R;
import com.jiajie.jiajieproject.activity.BaseActivity.MyAsyncTask;
import com.jiajie.jiajieproject.adapter.OrderListAdapter;
import com.jiajie.jiajieproject.contents.InterfaceParams;
import com.jiajie.jiajieproject.model.OnlyClass;
import com.jiajie.jiajieproject.utils.ToastUtil;
import com.jiajie.jiajieproject.widget.MyListView;
import com.mrwujay.cascade.model.SomeMessage;
import com.mrwujay.cascade.model.produceClass;

public class OrderListAcitivity extends BaseActivity {
	private ImageView headerleftImg;
	private MyListView orderlistlayout_listview;
	private OrderListAdapter orderListAdapter;
	private ArrayList<produceClass> list = new ArrayList<produceClass>();

	@Override
	protected void onInit(Bundle bundle) {
		// TODO Auto-generated method stub
		super.onInit(bundle);
		setContentView(R.layout.orderlist_layout);
		InitView();
	}

	private void InitView() {
		findViewById(R.id.headerleftImg).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						finish();
					}
				});
		orderlistlayout_listview = (MyListView) findViewById(R.id.orderlistlayout_listview);
		orderListAdapter = new OrderListAdapter(mActivity, mImgLoad);
		orderlistlayout_listview.setAdapter(orderListAdapter);
	}

	
}
