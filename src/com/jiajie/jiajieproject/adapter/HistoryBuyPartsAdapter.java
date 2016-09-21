/**
 * 
 */
package com.jiajie.jiajieproject.adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.jiajie.jiajieproject.R;
import com.jiajie.jiajieproject.activity.GoodsdetailActivity;
import com.jiajie.jiajieproject.activity.HistoryLogisticsActivity;
import com.jiajie.jiajieproject.activity.OrderInformationActivity;
import com.jiajie.jiajieproject.contents.Constants;
import com.jiajie.jiajieproject.contents.InterfaceParams;
import com.jiajie.jiajieproject.model.OnlyClass;
import com.jiajie.jiajieproject.net.service.JosnService;
import com.jiajie.jiajieproject.utils.ImageLoad;
import com.jiajie.jiajieproject.utils.IntentUtil;
import com.jiajie.jiajieproject.utils.MyAsyncTask;
import com.mrwujay.cascade.model.produceClass;
import com.jiajie.jiajieproject.utils.ToastUtil;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
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
public class HistoryBuyPartsAdapter extends BaseAdapter implements
		OnClickListener {
	private ArrayList<produceClass> list = new ArrayList<produceClass>();
	private Activity activity;
	private ImageLoad imageLoad;
	private JosnService jsonservice;
	public HistoryBuyPartsAdapter(Activity activity, ImageLoad imageLoad,JosnService jsonservice) {
		this.activity = activity;
		this.imageLoad = imageLoad;
		this.jsonservice = jsonservice;
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
					R.layout.historybuypartsitem_layout, null);
			vh.historybuypartsitemImgeView1 = (ImageView) convertView
					.findViewById(R.id.historybuypartsitemImgeView1);
			vh.historybuypartsitemtext1 = (TextView) convertView
					.findViewById(R.id.historybuypartsitemtext1);
			vh.historybuypartsitemtext2 = (TextView) convertView
					.findViewById(R.id.historybuypartsitemtext2);
			vh.historybuypartsitemtext3 = (TextView) convertView
					.findViewById(R.id.historybuypartsitemtext3);
			vh.historybuypartsitemtext21 = (TextView) convertView
					.findViewById(R.id.historybuypartsitemtext21);
			vh.historybuypartsitemtext31 = (TextView) convertView
					.findViewById(R.id.historybuypartsitemtext31);
			vh.historybuypartsitemtext4 = (TextView) convertView
					.findViewById(R.id.historybuypartsitemtext4);
			vh.historybuypartsitemtext5 = (TextView) convertView
					.findViewById(R.id.historybuypartsitemtext5);
			vh.historybuypartsitembutton1 = (TextView) convertView
					.findViewById(R.id.historybuypartsitembutton1);
			vh.historybuypartsitembutton2 = (TextView) convertView
					.findViewById(R.id.historybuypartsitembutton2);
			vh.historybuypartsitembutton3 = (TextView) convertView
					.findViewById(R.id.historybuypartsitembutton3);
			vh.historybuypartsitemlayout1 = (RelativeLayout) convertView
					.findViewById(R.id.historybuypartsitemlayout1);

			convertView.setTag(vh);
		} else {
			vh = (ViewHolder) convertView.getTag();
		}
		vh.historybuypartsitembutton1.setOnClickListener(this);
		vh.historybuypartsitembutton2.setOnClickListener(this);
		vh.historybuypartsitembutton3.setOnClickListener(this);
		vh.historybuypartsitemlayout1.setOnClickListener(this);
		vh.historybuypartsitemlayout1.setTag(position);
		vh.historybuypartsitemtext1.setText("订单号"
				+ list.get(position).order_code);
		vh.historybuypartsitemtext2.setText(list.get(position).product_name);
//		vh.historybuypartsitemtext21.setText("¥"+ list.get(position).order_price.sun);
		vh.historybuypartsitemtext21.setText("¥"+list.get(position).order_price.substring(0, list.get(position).order_price.lastIndexOf('.'))+".00");
		//商品数量不为空就显示
		if (list.get(position).order_qty != null) {
			vh.historybuypartsitemtext31.setText("x"
					+ list.get(position).order_qty);
		};
		// vh.notpayitem_layouttext11.setText(list.get(position).status);
		// vh.notpayitem_layouttext4.setText("打款截止日期："+list.get(position).order_date);
		
		// vh.notpayitem_layouttext5.setText("共一次服务  合计￥"+list.get(position).total_price);
		vh.historybuypartsitembutton1.setTag(position);
		
		return convertView;
		
	}

	class ViewHolder {
		ImageView historybuypartsitemImgeView1;
		TextView historybuypartsitemtext1, historybuypartsitemtext2,
				historybuypartsitemtext3, historybuypartsitemtext4,
				historybuypartsitemtext5, historybuypartsitemtext21,
				historybuypartsitemtext31;
		TextView historybuypartsitembutton1, historybuypartsitembutton2,
				historybuypartsitembutton3;
		RelativeLayout historybuypartsitemlayout1;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.historybuypartsitemlayout1:
			int position=(Integer) v.getTag();
			Bundle bundle=new Bundle();
			bundle.putString("notpayid", list.get(position).id);
			bundle.putString("notpayadressid", list.get(position).address_id);
			IntentUtil.activityForward(activity, OrderInformationActivity.class, bundle, false);
			break;
		case R.id.historybuypartsitembutton1:
			int position1=(Integer) v.getTag();
			new DeleteOrderAsyTask().execute(list.get(position1).id);
			break;
		case R.id.historybuypartsitembutton2:
//			IntentUtil.activityForward(activity,
//					HistoryLogisticsActivity.class, null, false);
			ToastUtil.showToast(activity, "正在开发");
			break;
		case R.id.historybuypartsitembutton3:
			callphone();
			break;

		default:
			break;
		}

	}

	// 打电话
	private void callphone() {
		Intent phoneIntent = new Intent("android.intent.action.CALL",
				Uri.parse("tel:" +Constants.phonenumber));
		// 启动
		activity.startActivity(phoneIntent);
	}

	/**
	 * 登录
	 * */
	@SuppressWarnings("unused")
	private class DeleteOrderAsyTask extends AsyncTask {

		public DeleteOrderAsyTask() {

		}

		@SuppressWarnings("unchecked")
		@Override
		protected Object doInBackground(Object... params) {
			Map map = new HashMap<String, String>();
			map.put("order_id",params[0]);

			return jsonservice.getData(InterfaceParams.deletelOrder, map, false, null);
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
				ToastUtil.showToast(activity, "删除成功");
			}

		}

	}
	
	
	
}
