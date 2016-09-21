/**
 * 
 */
package com.jiajie.jiajieproject.adapter;

import java.util.ArrayList;

import com.jiajie.jiajieproject.R;
import com.jiajie.jiajieproject.activity.GoodsdetailActivity;
import com.jiajie.jiajieproject.activity.HistoryLogisticsActivity;
import com.jiajie.jiajieproject.activity.OrderInformationActivity;
import com.jiajie.jiajieproject.contents.Constants;
import com.jiajie.jiajieproject.utils.ImageLoad;
import com.jiajie.jiajieproject.utils.IntentUtil;
import com.mrwujay.cascade.model.produceClass;
import com.jiajie.jiajieproject.utils.ToastUtil;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * 项目名称：NewProject 类名称：CartsClassAdapter 类描述： 创建人：王蕾 创建时间：2015-9-17 下午5:08:41
 * 修改备注：
 */
public class NotCheckAdapter extends BaseAdapter implements OnClickListener {
	private ArrayList<produceClass> list = new ArrayList<produceClass>();
	private Activity activity;
	private ImageLoad imageLoad;

	public NotCheckAdapter(Activity activity, ImageLoad imageLoad) {
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
					R.layout.notpayitem_layout, null);
			vh.notpayitem_layoutImgeView1 = (ImageView) convertView
					.findViewById(R.id.notpayitem_layoutImgeView1);
			vh.notpayitem_layouttext1 = (TextView) convertView
					.findViewById(R.id.notpayitem_layouttext1);
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

			convertView.setTag(vh);
		} else {
			vh = (ViewHolder) convertView.getTag();
		}
		vh.notpayitem_layoutlayout1.setOnClickListener(this);
		vh.notpayitem_layoutlayout1.setTag(position);
		vh.notpayitembutton1.setOnClickListener(this);
		vh.notpayitem_layoutbutton3.setOnClickListener(this);
		vh.notpayitem_layoutlayout1.setOnClickListener(this);

		vh.notpayitem_layouttext1.setText("订单号"
				+ list.get(position).order_code);
		vh.notpayitem_layouttext2.setText(list.get(position).product_name);
		vh.notpayitem_layouttext21.setText("¥"+list.get(position).order_price.substring(0, list.get(position).order_price.lastIndexOf('.'))+".00");
		//商品数量不为空就显示
		if (list.get(position).order_qty != null) {
			vh.notpayitem_layouttext31.setText("x"
					+ list.get(position).order_qty);
		}
		;
		// vh.notpayitem_layouttext11.setText(list.get(position).status);
		// vh.notpayitem_layouttext4.setText("打款截止日期："+list.get(position).order_date);
		vh.notpayitem_layouttext5.setText("共一次服务  合计￥"+list.get(position).total_price.substring(0, list.get(position).total_price.lastIndexOf('.'))+".00");

		return convertView;
	}

	class ViewHolder {
		ImageView notpayitem_layoutImgeView1;
		TextView notpayitem_layouttext1, notpayitem_layouttext2,
				notpayitem_layouttext3, notpayitem_layouttext4,
				notpayitem_layouttext5, notpayitem_layouttext21,
				notpayitem_layouttext31;
		TextView notpayitembutton1, notpayitem_layoutbutton3;
		RelativeLayout notpayitem_layoutlayout1;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {


		case R.id.notpayitem_layoutlayout1:
			int position=(Integer) v.getTag();
			Bundle bundle=new Bundle();
			bundle.putString("notpayid", list.get(position).id);
			bundle.putString("notpayadressid", list.get(position).address_id);
			IntentUtil.activityForward(activity, OrderInformationActivity.class, bundle, false);
			break;

		default:
			break;
		}

	}


}
