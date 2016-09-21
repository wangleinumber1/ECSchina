/**
 * 
 */
package com.jiajie.jiajieproject.adapter;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
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
import com.jiajie.jiajieproject.activity.OrderInformationActivity;
import com.jiajie.jiajieproject.contents.Constants;
import com.jiajie.jiajieproject.utils.ImageLoad;
import com.jiajie.jiajieproject.utils.IntentUtil;
import com.jiajie.jiajieproject.utils.ToastUtil;
import com.mrwujay.cascade.model.produceClass;

/**
 * 项目名称：HaiChuanProject 类名称：FaBuSearchAdapter 类描述： 创建人：王蕾 创建时间：2015-7-29
 * 下午2:19:53 修改备注：
 */
public class HistoryBuyAdapter extends BaseAdapter implements OnClickListener {

	private ArrayList<produceClass> list = new ArrayList<produceClass>();
	private Activity activity;
	private ImageLoad imageLoad;

	public HistoryBuyAdapter(Activity activity, ImageLoad imageLoad) {
		this.activity = activity;
		this.imageLoad = imageLoad;
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
		this.list.addAll(list);
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
					R.layout.historyitem_layout, null);
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
			vh.buy_again = (ImageView) convertView.findViewById(R.id.buy_again);
			vh.delete_order = (ImageView) convertView
					.findViewById(R.id.delete_order);
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
		vh.buy_again.setOnClickListener(this);
		vh.delete_order.setOnClickListener(this);
		return convertView;
	}

	class ViewHolder {
		ImageView notpayitem_layoutImgeView1;
		TextView notpayitem_layouttext1, notpayitem_layouttext11,
				notpayitem_layouttext2, notpayitem_layouttext3,
				notpayitem_layouttext4, notpayitem_layouttext5;
		RelativeLayout notpayitem_layoutlayout1;
		Button notpay_all, notpay_yingfu;
		ImageView buy_again, delete_order;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {

		case R.id.notpayitem_layoutlayout1:
			int position = (Integer) v.getTag();
			Bundle bundle = new Bundle();
			bundle.putString("notpayid", list.get(position).id);
			bundle.putString("notpayadressid", list.get(position).address_id);
			IntentUtil.activityForward(activity,
					OrderInformationActivity.class, bundle, false);
			break;
		case R.id.buy_again:
			ToastUtil.showToast(activity, "再次购买");
			break;
		case R.id.delete_order:
			ToastUtil.showToast(activity, "删除订单");
			break;

		default:
			break;
		}

	}

}
