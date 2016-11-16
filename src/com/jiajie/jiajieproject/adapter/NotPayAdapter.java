/**
 * 
 */
package com.jiajie.jiajieproject.adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jiajie.jiajieproject.R;
import com.jiajie.jiajieproject.activity.NotPayActivity;
import com.jiajie.jiajieproject.activity.OrderDetailActivity;
import com.jiajie.jiajieproject.activity.OrderInformationActivity;
import com.jiajie.jiajieproject.contents.Constants;
import com.jiajie.jiajieproject.contents.InterfaceParams;
import com.jiajie.jiajieproject.net.service.JosnService;
import com.jiajie.jiajieproject.utils.ImageLoad;
import com.jiajie.jiajieproject.utils.IntentUtil;
import com.jiajie.jiajieproject.utils.ToastUtil;
import com.mrwujay.cascade.model.produceClass;

/**
 * 项目名称：HaiChuanProject 类名称：FaBuSearchAdapter 类描述： 创建人：王蕾 创建时间：2015-7-29
 * 下午2:19:53 修改备注：
 */
public class NotPayAdapter extends BaseAdapter implements OnClickListener {

	private ArrayList<produceClass> list = new ArrayList<produceClass>();
	private Activity activity;
	private ImageLoad imageLoad;
	private JosnService josnService;
	private Handler myhandler;

	public NotPayAdapter(Activity activity, ImageLoad imageLoad,
			JosnService josnService,Handler myhandler) {
		super();
		this.activity = activity;
		this.imageLoad = imageLoad;
		this.josnService = josnService;
		this.myhandler = myhandler;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	public void setdata(ArrayList<produceClass> list) {
		this.list=list;
	}


	public ArrayList<produceClass> getdata() {
		return list;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder vh = null;
		if (convertView == null) {
			vh = new ViewHolder();
			convertView = LayoutInflater.from(activity).inflate(
					R.layout.notpayitem_layout, null);
			vh.notpayitem_layoutImgeView1 = (ImageView) convertView
					.findViewById(R.id.notpayitem_layoutImgeView1);
			vh.notpayitem_layouttext1 = (TextView) convertView
					.findViewById(R.id.notpayitem_layouttext1);
			vh.notpayitem_layouttext11 = (TextView) convertView
					.findViewById(R.id.notpayitem_layouttext11);
			vh.notpayitem_layouttext2 = (TextView) convertView
					.findViewById(R.id.notpayitem_layouttext2);
			vh.notpayitem_layouttext3 = (TextView) convertView
					.findViewById(R.id.notpayitem_layouttext3);
	
			vh.notpayitem_layouttext4 = (TextView) convertView
					.findViewById(R.id.notpayitem_layouttext4);
			vh.notpayitem_layouttext5 = (TextView) convertView
					.findViewById(R.id.notpayitem_layouttext5);
			vh.notpayitem_layoutlayout1 = (RelativeLayout) convertView
					.findViewById(R.id.notpayitem_layoutlayout1);
			vh.notpay_all=(Button) convertView.findViewById(R.id.notpay_all);
			vh.notpay_yingfu=(Button) convertView.findViewById(R.id.notpay_yingfu);
			vh.go_pay=(ImageView) convertView.findViewById(R.id.go_pay);
			vh.cancle_order=(ImageView) convertView.findViewById(R.id.cancle_order);
			convertView.setTag(vh);
		} else {
			vh = (ViewHolder) convertView.getTag();
		}
		vh.notpayitem_layoutlayout1.setTag(position);
		vh.notpayitem_layoutlayout1.setOnClickListener(this);
		vh.notpayitem_layouttext1
				.setText("订单号" + list.get(position).order_code);
		vh.notpayitem_layouttext2.setText(list.get(position).product_name);
		vh.notpayitem_layouttext4.setText(list.get(position).price);
		vh.notpayitem_layouttext5.setText(list.get(position).order_qty);
		vh.notpay_all.setText(list.get(position).order_qty);
		vh.notpay_yingfu.setText(list.get(position).total_price);
		vh.go_pay.setOnClickListener(this);
		vh.cancle_order.setOnClickListener(this);
		vh.cancle_order.setTag(position);
		return convertView;
	}

	class ViewHolder {
		ImageView notpayitem_layoutImgeView1;
		TextView notpayitem_layouttext1, notpayitem_layouttext11,
				notpayitem_layouttext2, notpayitem_layouttext3,
				notpayitem_layouttext4, notpayitem_layouttext5;
		RelativeLayout notpayitem_layoutlayout1;
		Button notpay_all,notpay_yingfu;
		ImageView go_pay,cancle_order;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {

		case R.id.notpayitem_layoutlayout1:
			int position=(Integer) v.getTag();
			Bundle bundle=new Bundle();
			bundle.putString(OrderDetailActivity.TAG, list.get(position).id);	
			bundle.putString(OrderDetailActivity.TAG1, list.get(position).address_id);	
			IntentUtil.activityForward(activity, OrderDetailActivity.class, bundle, false);
			break;
		case R.id.go_pay:
			ToastUtil.showToast(activity, "去支付");
			break;
		case R.id.cancle_order:
			int pos=(Integer) v.getTag();
			new CancleOrderAsyTask(activity, list.get(pos).id).execute();
			break;

		default:
			break;
		}

	}

	//取消订单
		private class CancleOrderAsyTask extends com.jiajie.jiajieproject.utils.MyAsyncTask{
			private String id;
			public CancleOrderAsyTask(Context context, String id) {
				super(context);
				this.id = id;
			}

		
			@Override
			protected Object doInBackground(Object... params) {
				Map map=new HashMap<String, String>();
				map.put("id", id);		
				return josnService.getData(InterfaceParams.cancelOrder, map, false, null);
			}

			@Override
			protected void onPostExecute(Object result) {
				// TODO Auto-generated method stub
				super.onPostExecute(result);
				if(result==null){
					return;
				}
				myhandler.sendEmptyMessage(1);
				
			}
			
		}

}
