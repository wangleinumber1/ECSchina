/**
 * 
 */
package com.jiajie.jiajieproject.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.jiajie.jiajieproject.R;
import com.jiajie.jiajieproject.contents.InterfaceParams;
import com.jiajie.jiajieproject.model.AdressClass;
import com.jiajie.jiajieproject.model.OnlyClass;
import com.jiajie.jiajieproject.model.OrderInfoClass;
import com.jiajie.jiajieproject.utils.ImageLoad;
import com.jiajie.jiajieproject.utils.ToastUtil;
import com.mrwujay.cascade.model.produceClass;

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
	private String id,address_id;
	public static String TAG="OrderDetailActivity";
	public static String TAG1="OrderDetailActivity1";
	private Button notpay_all,notpay_yingfu;
	@Override
	protected void onInit(Bundle bundle) {
		// TODO Auto-generated method stub
		super.onInit(bundle);
		setContentView(R.layout.orderdetail_layout);
		id=getIntent().getExtras().getString(TAG);
		address_id=getIntent().getExtras().getString(TAG1);
		InitView();
	}

	private void InitView() {
		notpay_all = (Button) findViewById(R.id.notpay_all);
		notpay_yingfu = (Button) findViewById(R.id.notpay_yingfu);
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
		
		new  OrderInfoAsyTask().execute();
		headerleftImg.setOnClickListener(this);
		cancle_order.setOnClickListener(this);
	}

	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.headerleftImg:
			finish();
			break;
		case R.id.cancle_order:
			new CancleOrderAsyTask().execute();
			break;

		default:
			break;
		}
	}
	//订单详情
	private class OrderInfoAsyTask extends MyAsyncTask{

		@Override
		protected Object doInBackground(Object... params) {
			Map map=new HashMap<String, String>();
			map.put("id", id);
			map.put("address_id", address_id);
			return jsonservice.getData(InterfaceParams.orderInfo, map, false, null);
		}

		@Override
		protected void onPostExecute(Object result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if(result==null){
				return;
			}
			
			OnlyClass onlyClass=(OnlyClass) result;
			@SuppressWarnings("unused")
			OrderInfoClass orderinfoclass=JSON.parseObject(onlyClass.data, OrderInfoClass.class);
			notpay_all.setText(orderinfoclass.total_qty+"");
			notpay_yingfu.setText(orderinfoclass.total_price+".00");
			ArrayList<produceClass> product_info=(ArrayList<produceClass>) JSON.parseArray(orderinfoclass.product_info, produceClass.class);
			@SuppressWarnings("unused")
			AdressClass address_info= JSON.parseObject(orderinfoclass.address_info, AdressClass.class);
			orderdetailtext1.setText("订单号："+product_info.get(0).id);
			headernametext.setText(address_info.customer_name);
			headernnmbertext.setText(address_info.telephone);
			headeradresstext.setText(address_info.city+address_info.street);
			mImgLoad.loadImg(orderdetail_layoutImgeView1, product_info.get(0).image, R.drawable.jiazaitupian);
			orderdetail_layouttext2.setText(product_info.get(0).name.split("\t")[0]);
			orderdetail_layouttext5.setText("x"+orderinfoclass.total_qty);
			orderdetail_layouttext3.setText("sn号："+product_info.get(0).name.split("\t")[1]);
			orderdetail_layouttext4.setText("¥"+orderinfoclass.total_price/orderinfoclass.total_qty+".00");
		}
		
	}
	//取消订单
	private class CancleOrderAsyTask extends MyAsyncTask{

		@Override
		protected Object doInBackground(Object... params) {
			Map map=new HashMap<String, String>();
			map.put("id", id);		
			return jsonservice.getData(InterfaceParams.cancelOrder, map, false, null);
		}

		@Override
		protected void onPostExecute(Object result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if(result==null){
				return;
			}
		
			
		}
		
	}
}
