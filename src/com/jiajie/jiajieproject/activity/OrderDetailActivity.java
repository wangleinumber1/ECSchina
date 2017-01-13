/**
 * 
 */
package com.jiajie.jiajieproject.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import net.sourceforge.simcpux.Constants;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.jiajie.jiajieproject.MainActivity;
import com.jiajie.jiajieproject.R;
import com.jiajie.jiajieproject.activity.BaseActivity.MyAsyncTask;
import com.jiajie.jiajieproject.adapter.OrderdetailAdapter;
import com.jiajie.jiajieproject.contents.InterfaceParams;
import com.jiajie.jiajieproject.model.AlipayClass;
import com.jiajie.jiajieproject.model.OnlyClass;
import com.jiajie.jiajieproject.model.OrderInfoClass;
import com.jiajie.jiajieproject.utils.IntentUtil;
import com.jiajie.jiajieproject.utils.ToastUtil;
import com.jiajie.jiajieproject.widget.MyListView;
import com.mrwujay.cascade.model.produceClass;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

/**
 * 项目名称：NewProject 类名称：OrderDetailActivity 类描述： 创建人：王蕾 创建时间：2016-8-31 下午3:53:48
 * 修改备注： 订单详情
 */
public class OrderDetailActivity extends BaseActivity implements
		OnClickListener {
	private TextView orderdetailtext1, orderdetailtext2;
	private TextView headernametext, headernnmbertext, headeradresstext;
	private ImageView go_pay, cancle_order, headerleftImg;
	private String orderid;
	public static String TAG = "OrderDetailActivity";
	public static String TAG1 = "OrderDetailActivity1";
	private Button notpay_all, notpay_yingfu;
	@SuppressWarnings("unused")
	private MyListView myListView;
	private View footview;
	private View headview;
	private OrderdetailAdapter orderdetailadapter;
	private String id;
	private String paymethod;
	private String productname;
	private String totalprice;
	final IWXAPI msgApi = WXAPIFactory.createWXAPI(this, null);
	Map<String, String> resultunifiedorder;
	StringBuffer sb;
	IWXAPI api;
	private PayReq req;
	private String status;

	@Override
	protected void onInit(Bundle bundle) {
		// TODO Auto-generated method stub
		super.onInit(bundle);
		setContentView(R.layout.orderdetail_layout);
		api = WXAPIFactory.createWXAPI(this, Constants.APP_ID);
		api.registerApp(Constants.APP_ID);
		req = new PayReq();
		orderid = getIntent().getExtras().getString(TAG);
		id = getIntent().getExtras().getString("id");
		productname = getIntent().getExtras().getString("product_name");
		InitView();
	}

	private void InitView() {
		orderdetailadapter = new OrderdetailAdapter(mActivity, mImgLoad);
		footview = inflater.inflate(R.layout.orderdetail_foot_layout, null);
		headview = inflater.inflate(R.layout.orderdetail_head_layout, null);
		myListView = (MyListView) findViewById(R.id.orderdetail_layout_listview);
		notpay_all = (Button) footview.findViewById(R.id.notpay_all);
		notpay_yingfu = (Button) footview.findViewById(R.id.notpay_yingfu);
		orderdetailtext1 = (TextView) headview
				.findViewById(R.id.orderdetailtext1);
		orderdetailtext2 = (TextView) footview
				.findViewById(R.id.orderdetailtext2);
		headernametext = (TextView) headview.findViewById(R.id.headernametext);
		headernnmbertext = (TextView) headview
				.findViewById(R.id.headernnmbertext);
		headeradresstext = (TextView) headview
				.findViewById(R.id.headeradresstext);
		headerleftImg = (ImageView) findViewById(R.id.headerleftImg);
		go_pay = (ImageView) footview.findViewById(R.id.go_pay);
		cancle_order = (ImageView) footview.findViewById(R.id.cancle_order);
		myListView.addHeaderView(headview);
		myListView.addFooterView(footview);
		myListView.setAdapter(orderdetailadapter);
		new OrderInfoAsyTask().execute();
		headerleftImg.setOnClickListener(this);
		cancle_order.setOnClickListener(this);
		go_pay.setOnClickListener(this);
		orderdetailtext1.setText("订单号：" + orderid);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.headerleftImg:
			finish();
			break;
		case R.id.go_pay:
			if (status.equals("pending")) {
				// 不是待支付就隐藏按钮
				Paydata paydata = new Paydata();
				paydata.order_id = orderid;
				paydata.product_name = productname;
				paydata.total = totalprice;
				String payData = JSON.toJSONString(paydata);
				new RePayasyTask(payData, paymethod).execute();

			} else if (status.equals("processing")) {
				// 待发货
				ToastUtil.showToast(this, "开发中");
			} else if (status.equals("canceled")||status.equals("complete")) {
				// 已发货
				@SuppressWarnings("rawtypes")
				Map map=new HashMap();
				map.put("order_id", orderid);
				new OrderAsyTask(InterfaceParams.reorder,map).execute();
			}

			break;
		case R.id.cancle_order:
			if (status.equals("pending")) {
				// 不是待支付就隐藏按钮
				Map<String, String> map=new HashMap<String, String>();
				map.put("order_id", orderid);
				new OrderAsyTask(InterfaceParams.cancelOrder, map).execute();

			} else if (status.equals("processing")) {
				// 待发货
				Map<String, String> map=new HashMap<String, String>();
				map.put("order_id", orderid);
				new OrderAsyTask(InterfaceParams.completeOrder, map).execute();
				
			} else if (status.equals("canceled")||status.equals("complete")) {
				Map<String, String> map=new HashMap<String, String>();
				map.put("order_id", orderid);
				new OrderAsyTask(InterfaceParams.deletelOrder,map).execute();
			} 
			break;

		default:
			break;
		}
	}

	// 订单详情
	private class OrderInfoAsyTask extends MyAsyncTask {

		@Override
		protected Object doInBackground(Object... params) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("order_id", orderid);
			return jsonservice.getData(InterfaceParams.orderInfo, map, false,
					OrderInfoClass.class);
		}

		@Override
		protected void onPostExecute(Object result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if (result == null) {
				return;
			}

			OrderInfoClass orderInfoClass = (OrderInfoClass) result;
			@SuppressWarnings("unused")
			ArrayList<produceClass> list = (ArrayList<produceClass>) JSON
					.parseArray(orderInfoClass.products, produceClass.class);
			orderdetailadapter.setdata(list);
			orderdetailadapter.notifyDataSetChanged();
			paymethod = orderInfoClass.pay_method;
			productname = list.get(0).product_name;
			totalprice = orderInfoClass.total_price;
			headernametext.setText(orderInfoClass.customer_name);
			headernnmbertext.setText(orderInfoClass.phone);
			headeradresstext.setText(orderInfoClass.city
					+ orderInfoClass.street);
			// 数据为double类型需要去掉尾部小数
			notpay_all.setText(orderInfoClass.total_qty);
			notpay_yingfu.setText(orderInfoClass.total_price);
			status = orderInfoClass.status;
			if (status.equals("pending")) {
				// 不是待支付就隐藏按钮
				go_pay.setImageResource(R.drawable.go_pay);
				cancle_order.setImageResource(R.drawable.cancle_order);

			} else if (status.equals("processing")) {
				// 待发货
				go_pay.setImageResource(R.drawable.wuliu_button);
				cancle_order.setImageResource(R.drawable.getgoods_button);
			} else if (status.equals("canceled")) {
			
				go_pay.setImageResource(R.drawable.buy_again);
				cancle_order.setImageResource(R.drawable.delete_order);
			}else if (status.equals("complete")) {
		
				go_pay.setImageResource(R.drawable.buy_again);
				cancle_order.setImageResource(R.drawable.delete_order);
			}
			if (orderInfoClass.bill_title.equals("Personal")) {
				orderdetailtext2.setText("个人");
			} else {
				orderdetailtext2.setText(orderInfoClass.bill_company);
			}

		}

	}

	// 取消订单，再次购买，确认收货
	@SuppressWarnings("unused")
	private class OrderAsyTask extends MyAsyncTask {
		private String interfaceParams;
		@SuppressWarnings("unused")
		public OrderAsyTask(String interfaceParams, Map<String, String> map) {
			super();
			this.interfaceParams = interfaceParams;
			this.map = map;
		}

		private Map<String, String> map;
		@Override
		protected Object doInBackground(Object... params) {
			return jsonservice.getData(interfaceParams, map, false,
					null);
		}

		@Override
		protected void onPostExecute(Object result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if (result == null) {
				return;
			}
			OnlyClass onlyClass=(OnlyClass) result;			
			ToastUtil.showToast(mContext,onlyClass.data);
			if(interfaceParams.equals(InterfaceParams.reorder)){
				com.jiajie.jiajieproject.contents.Constants.isReOrder=true;
				IntentUtil.activityForward(OrderDetailActivity.this, MainActivity.class, null, true);
			}
		}

	}


	// 去支付
	@SuppressWarnings("unused")
	private class RePayasyTask extends MyAsyncTask {
		private String paymentMethod;

		public RePayasyTask(String payData, String paymentMethod) {
			super();
			this.paymentMethod = paymentMethod;
			this.payData = payData;
		}

		private String payData;

		@SuppressWarnings("unchecked")
		@Override
		protected Object doInBackground(Object... params) {
			// TODO Auto-generated method stub
			Map<String, String> map = new HashMap<String, String>();
			map.put("paymentMethod", paymentMethod);
			map.put("payData", payData);
			return jsonservice.getCareData(InterfaceParams.Repay, map, false,
					null);
		}

		@Override
		protected void onPostExecute(Object result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if (result == null) {
				return;
			}
			OnlyClass onlyClass = (OnlyClass) result;
			if (paymentMethod.equalsIgnoreCase("alipay")) {
				new AlipayClass(onlyClass.sign, mActivity);
			} else if (paymentMethod.equalsIgnoreCase("weixin")) {
				Constants.ORDER_ID = orderid;
				WXPay(onlyClass.sign);
			} else {
				ToastUtil.showToast(mActivity, "对公转账请联系线下客服");
			}
		}

	}

	/** 微信josn解析 */
	private void WXPay(String pag_sign) {
		JSONObject json;
		try {
			json = new JSONObject(pag_sign);

			req.appId = json.getString("appid");
			req.partnerId = json.getString("partnerid");
			req.prepayId = json.getString("prepayid");
			req.nonceStr = json.getString("noncestr");
			req.timeStamp = json.getString("timestamp");
			req.packageValue = json.getString("package");
			req.sign = json.getString("sign");
			req.extData = "app data"; // optional
			api.sendReq(req);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public class Paydata {
		public String order_id;
		public String product_name;
		public String total;
	}
}
