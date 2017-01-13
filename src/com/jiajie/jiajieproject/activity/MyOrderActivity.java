/**
 * 
 */
package com.jiajie.jiajieproject.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import net.sourceforge.simcpux.Constants;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.alibaba.fastjson.JSON;
import com.jiajie.jiajieproject.R;
import com.jiajie.jiajieproject.activity.BaseActivity.MyAsyncTask;
import com.jiajie.jiajieproject.adapter.myorderAdapter;
import com.jiajie.jiajieproject.contents.InterfaceParams;
import com.jiajie.jiajieproject.model.AlipayClass;
import com.jiajie.jiajieproject.model.OnlyClass;
import com.jiajie.jiajieproject.utils.ToastUtil;
import com.jiajie.jiajieproject.widget.MyListView;
import com.jiajie.jiajieproject.widget.ReboundScrollView;
import com.mrwujay.cascade.model.produceClass;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

/**
 * 项目名称：NewProject 类名称：myorderActivity 类描述： 创建人：王蕾 创建时间：2015-10-13 下午6:02:58
 * 修改备注：未付款
 */
public class MyOrderActivity extends BaseActivity  implements
 OnClickListener {
	private ImageView headerleftImg;
	private MyListView myorder_layout_listview;
//	private PullToRefreshView myorder_layout_pull;
	private myorderAdapter myorderAdapter;
	private ReboundScrollView nopaylayout;
	private RelativeLayout no_orderlayout;
	private Myhandler myhandler = new Myhandler();
	final IWXAPI msgApi = WXAPIFactory.createWXAPI(this, null);
	Map<String, String> resultunifiedorder;
	StringBuffer sb;
	IWXAPI api;
	private PayReq req;
	@Override
	protected void onInit(Bundle bundle) {
		// TODO Auto-generated method stub
		super.onInit(bundle);
		setContentView(R.layout.myorder_layout);
		api = WXAPIFactory.createWXAPI(this, Constants.APP_ID);
		api.registerApp(Constants.APP_ID);
		req = new PayReq();
		InitView();
	}

	private void InitView() {
		nopaylayout=(ReboundScrollView) findViewById(R.id.nopaylayout);
		no_orderlayout=(RelativeLayout) findViewById(R.id.no_orderlayout);
		headerleftImg = (ImageView) findViewById(R.id.headerleftImg);
		myorder_layout_listview = (MyListView) findViewById(R.id.myorder_layout_listview);
		myorderAdapter=new  myorderAdapter(mActivity,mImgLoad,jsonservice,myhandler);
		myorder_layout_listview.setAdapter(myorderAdapter);
		headerleftImg.setOnClickListener(this);
		
	}

	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		new PartsAsyTask().execute();
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

	
	/**
	 * 备件
	 * */
	@SuppressWarnings("unused")
	private class PartsAsyTask extends MyAsyncTask {

		public PartsAsyTask() {
			super();
		}
		@SuppressWarnings("unchecked")
		@Override
		protected Object doInBackground(Object... params) {
			Map map=new HashMap<String, String>();
			return jsonservice.getDataList(InterfaceParams.MyorderList,
					null, false, produceClass.class);
		}

		@SuppressWarnings("unchecked")
		@Override
		protected void onPostExecute(Object result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if (result == null) {
				return;
			}

			if (jsonservice.getToastMessage()) {
				OnlyClass onlyClass = (OnlyClass) result;
				ToastUtil.showToast(mActivity, onlyClass.data);
			}
			if (jsonservice.getsuccessState()) {
				ArrayList<produceClass> list = (ArrayList<produceClass>) result;
				if(list.size()>0){
					myorderAdapter.setdata(list);			
					myorderAdapter.notifyDataSetChanged();
					/*有数据显示列表*/
					nopaylayout.setVisibility(View.VISIBLE);
					no_orderlayout.setVisibility(View.GONE);
				}else{
					/*无数据显背景*/
					nopaylayout.setVisibility(View.GONE);
					no_orderlayout.setVisibility(View.VISIBLE);
				}
				
			}

		}

	}

	
	@SuppressWarnings("unused")
	private class Myhandler extends Handler {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			switch (msg.what) {
			case 1:
				new PartsAsyTask().execute();
				break;
			case 2:
				produceClass produceClass=(produceClass) msg.obj;
				String paymentMethod=produceClass.pay_method;
				if(paymentMethod.equalsIgnoreCase("checkmo")){
					ToastUtil.showToast(mActivity, "对公转账请联系线下客服");
				}else if(paymentMethod.equalsIgnoreCase("purchaseorder")){
					ToastUtil.showToast(mActivity, "对公转账请联系线下客服");
				}else{				
					Paydata paydata=new Paydata();
					Constants.ORDER_ID=produceClass.order_id;
					paydata.order_id=produceClass.order_id;
					paydata.product_name=produceClass.product_name;
					paydata.total=produceClass.price;
					String payData=JSON.toJSONString(paydata);
					new RePayasyTask(payData,paymentMethod).execute();
				}
				break;

			default:
				break;
			}

		}

	}
	
	
	//去支付
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
				Map<String, String> map=new HashMap<String, String>();
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
				}else if (paymentMethod.equalsIgnoreCase("weixin")) {
					WXPay(onlyClass.sign);				
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
private class Paydata{
	public String order_id;
	public String product_name;
	public String total;
}
	
}
