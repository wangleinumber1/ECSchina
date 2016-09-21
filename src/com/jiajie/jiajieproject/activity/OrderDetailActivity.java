/**
 * 
 */
package com.jiajie.jiajieproject.activity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.jiajie.jiajieproject.R;

/**
 * 项目名称：NewProject 类名称：OrderDetailActivity 类描述： 创建人：王蕾 创建时间：2016-8-31 下午3:53:48
 * 修改备注： 订单详情
 */
public class OrderDetailActivity extends BaseActivity implements OnClickListener{
	private TextView orderdetailtext1, orderdetailtext2;
	private TextView headernametext, headernnmbertext, headeradresstext;
	private ImageView orderdetail_layoutImgeView1, go_pay, cancle_order,
			headerleftImg;
	private TextView orderdetail_layouttext2, orderdetail_layouttext3,
			orderdetail_layouttext4, orderdetail_layouttext5;

	@Override
	protected void onInit(Bundle bundle) {
		// TODO Auto-generated method stub
		super.onInit(bundle);
		setContentView(R.layout.orderdetail_layout);
		InitView();
	}

	private void InitView() {
		orderdetailtext1 = (TextView) findViewById(R.id.orderdetailtext1);
		orderdetailtext2 = (TextView) findViewById(R.id.orderdetailtext2);
		headernametext = (TextView) findViewById(R.id.headernametext);
		headernnmbertext = (TextView) findViewById(R.id.headernnmbertext);
		headeradresstext = (TextView) findViewById(R.id.headeradresstext);
		orderdetail_layouttext2 = (TextView) findViewById(R.id.orderdetail_layouttext2);
		orderdetail_layouttext3 = (TextView) findViewById(R.id.orderdetail_layouttext3);
		orderdetail_layouttext4 = (TextView) findViewById(R.id.orderdetail_layouttext4);
		orderdetail_layouttext5 = (TextView) findViewById(R.id.orderdetail_layouttext5);
		orderdetail_layoutImgeView1 = (ImageView) findViewById(R.id.orderdetail_layoutImgeView1);
		headerleftImg = (ImageView) findViewById(R.id.headerleftImg);
		go_pay = (ImageView) findViewById(R.id.go_pay);
		cancle_order = (ImageView) findViewById(R.id.cancle_order);

		headerleftImg.setOnClickListener(this);
	}

	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.headerleftImg:
			finish();
			break;

		default:
			break;
		}
	}
}
