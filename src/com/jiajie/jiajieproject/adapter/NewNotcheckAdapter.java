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
import com.jiajie.jiajieproject.activity.OrderDetailActivity;
import com.jiajie.jiajieproject.activity.OrderInformationActivity;
import com.jiajie.jiajieproject.contents.Constants;
import com.jiajie.jiajieproject.contents.InterfaceParams;
import com.jiajie.jiajieproject.model.OnlyClass;
import com.jiajie.jiajieproject.net.service.JosnService;
import com.jiajie.jiajieproject.utils.ImageLoad;
import com.jiajie.jiajieproject.utils.IntentUtil;
import com.jiajie.jiajieproject.utils.MyAsyncTask;
import com.jiajie.jiajieproject.utils.ToastUtil;
import com.mrwujay.cascade.model.produceClass;

/**
 * 项目名称：HaiChuanProject 类名称：FaBuSearchAdapter 类描述： 创建人：王蕾 创建时间：2015-7-29
 * 下午2:19:53 修改备注：
 */
public class NewNotcheckAdapter extends BaseAdapter implements OnClickListener {

	private ArrayList<produceClass> list = new ArrayList<produceClass>();
	private Activity activity;
	private ImageLoad imageLoad;
	private JosnService josnService;
	private Handler myHandler;
	public NewNotcheckAdapter(Activity activity, ImageLoad imageLoad,
			JosnService josnService,Handler myHandler) {
		super();
		this.activity = activity;
		this.imageLoad = imageLoad;
		this.josnService = josnService;
		this.myHandler = myHandler;
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
					R.layout.notcheckitem_layout, null);
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
			vh.notpay_all = (Button) convertView.findViewById(R.id.notpay_all);
			vh.notpay_yingfu = (Button) convertView
					.findViewById(R.id.notpay_yingfu);
			vh.wuliu_button = (ImageView) convertView
					.findViewById(R.id.wuliu_button);
			vh.getgoods_button = (ImageView) convertView
					.findViewById(R.id.getgoods_button);
			convertView.setTag(vh);
		} else {
			vh = (ViewHolder) convertView.getTag();
		}
		vh.notpayitem_layoutlayout1.setTag(position);
		vh.notpayitem_layoutlayout1.setOnClickListener(this);
		vh.notpayitem_layouttext1.setText("订单号" + list.get(position).order_id);
		vh.notpayitem_layouttext2.setText(list.get(position).product_name);
		vh.notpayitem_layouttext3.setText("PN号:" + list.get(position).pn);
		// vh.notpayitem_layouttext4.setText(list.get(position).price);
		vh.notpayitem_layouttext5.setText("x" + list.get(position).order_qty);
		vh.notpay_all.setText(list.get(position).order_qty);
		vh.notpay_yingfu.setText(list.get(position).price);
		vh.getgoods_button.setOnClickListener(this);
		vh.getgoods_button.setTag(position);
		vh.wuliu_button.setOnClickListener(this);
		imageLoad.loadImg(vh.notpayitem_layoutImgeView1,
				list.get(position).image, R.drawable.jifangbanqian);
		return convertView;
	}

	class ViewHolder {
		ImageView notpayitem_layoutImgeView1;
		TextView notpayitem_layouttext1, notpayitem_layouttext11,
				notpayitem_layouttext2, notpayitem_layouttext3,
				notpayitem_layouttext4, notpayitem_layouttext5;
		RelativeLayout notpayitem_layoutlayout1;
		Button notpay_all, notpay_yingfu;
		ImageView getgoods_button, wuliu_button;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {

		case R.id.notpayitem_layoutlayout1:
			int position = (Integer) v.getTag();
			Bundle bundle = new Bundle();
			bundle.putString(OrderDetailActivity.TAG,
					list.get(position).order_id);
			bundle.putString("order_id", list.get(position).order_id);
			bundle.putString("product_name", list.get(position).product_name);
			IntentUtil.activityForward(activity, OrderDetailActivity.class,
					bundle, false);
			break;
		case R.id.getgoods_button:
			int position1 = (Integer) v.getTag();
		new CompleteOrderAsyTask(activity,list.get(position1).order_id).execute();
			break;
		case R.id.wuliu_button:
			ToastUtil.showToast(activity, "开发中");
			break;

		default:
			break;
		}

	}

	// 确认收货
	private class CompleteOrderAsyTask extends MyAsyncTask {
		private String order_id;

		public CompleteOrderAsyTask(Context context, String order_id) {
			super(context);
			this.order_id = order_id;
		}

		@Override
		protected Object doInBackground(Object... params) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("order_id",order_id );
			return josnService.getData(InterfaceParams.completeOrder, map,
					false, null);
		}

		@Override
		protected void onPostExecute(Object result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if (result == null) {
				return;
			}
			OnlyClass onlyClass = (OnlyClass) result;
			ToastUtil.showToast(activity, onlyClass.data);
			myHandler.sendEmptyMessage(1);

		}

	}

}
