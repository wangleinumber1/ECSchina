/**
 * 
 */
package com.jiajie.jiajieproject.adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jiajie.jiajieproject.MainActivity;
import com.jiajie.jiajieproject.R;
import com.jiajie.jiajieproject.activity.OrderDetailActivity;
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
public class myorderAdapter extends BaseAdapter implements OnClickListener {

	private ArrayList<produceClass> list = new ArrayList<produceClass>();
	private Activity activity;
	private ImageLoad imageLoad;
	private JosnService josnService;
	private Handler myhandler;
	private String status;

	public myorderAdapter(Activity activity, ImageLoad imageLoad,
			JosnService josnService, Handler myhandler) {
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
		this.list = list;
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
					R.layout.myorderitem_layout, null);
			vh.myorderitem_layoutImgeView1 = (ImageView) convertView
					.findViewById(R.id.myorderitem_layoutImgeView1);
			vh.myorderitem_layouttext1 = (TextView) convertView
					.findViewById(R.id.myorderitem_layouttext1);
			vh.myorderitem_layouttext11 = (TextView) convertView
					.findViewById(R.id.myorderitem_layouttext11);
			vh.myorderitem_layouttext2 = (TextView) convertView
					.findViewById(R.id.myorderitem_layouttext2);
			vh.myorderitem_layouttext3 = (TextView) convertView
					.findViewById(R.id.myorderitem_layouttext3);

			vh.myorderitem_layouttext4 = (TextView) convertView
					.findViewById(R.id.myorderitem_layouttext4);
			vh.myorderitem_layouttext5 = (TextView) convertView
					.findViewById(R.id.myorderitem_layouttext5);
			vh.myorderitem_layoutlayout1 = (RelativeLayout) convertView
					.findViewById(R.id.myorderitem_layoutlayout1);
			vh.myorder_all = (Button) convertView
					.findViewById(R.id.myorder_all);
			vh.myorder_yingfu = (Button) convertView
					.findViewById(R.id.myorder_yingfu);
			vh.go_pay = (ImageView) convertView.findViewById(R.id.go_pay);
			vh.cancle_order = (ImageView) convertView
					.findViewById(R.id.cancle_order);
			convertView.setTag(vh);
		} else {
			vh = (ViewHolder) convertView.getTag();
		}
		status = list.get(position).status;
		if (status.equals("pending")) {
			vh.myorderitem_layouttext11.setText("等待付款");
			vh.go_pay.setImageResource(R.drawable.go_pay);
			vh.cancle_order.setImageResource(R.drawable.cancle_order);
		} else if (status.equals("processing")) {
			vh.myorderitem_layouttext11.setText("待收货");
			vh.go_pay.setImageResource(R.drawable.wuliu_button);
			vh.cancle_order.setImageResource(R.drawable.getgoods_button);
		} else if (status.equals("canceled")) {
			
			vh.myorderitem_layouttext11.setText("已取消");
			vh.go_pay.setImageResource(R.drawable.buy_again);
			vh.cancle_order.setImageResource(R.drawable.delete_order);
		}else if (status.equals("complete")) {
			
			vh.myorderitem_layouttext11.setText("已完成");
			vh.go_pay.setImageResource(R.drawable.buy_again);
			vh.cancle_order.setImageResource(R.drawable.delete_order);
		}
		vh.myorderitem_layoutlayout1.setTag(position);
		vh.myorderitem_layoutlayout1.setOnClickListener(this);
		vh.myorderitem_layouttext1.setText("订单号" + list.get(position).order_id);
		vh.myorderitem_layouttext2.setText(list.get(position).product_name);
		vh.myorderitem_layouttext3.setText("PN号:" + list.get(position).pn);
		vh.myorderitem_layouttext5.setText("x" + list.get(position).order_qty);
		vh.myorder_all.setText(list.get(position).order_qty);
		vh.myorder_yingfu.setText(list.get(position).price );
		vh.go_pay.setOnClickListener(this);
		imageLoad.loadImg(vh.myorderitem_layoutImgeView1,
				list.get(position).image, R.drawable.jiazaitupian);
		vh.cancle_order.setOnClickListener(this);
		vh.cancle_order.setTag(position);
		vh.go_pay.setTag(position);
		return convertView;
	}

	class ViewHolder {
		ImageView myorderitem_layoutImgeView1;
		TextView myorderitem_layouttext1, myorderitem_layouttext11,
				myorderitem_layouttext2, myorderitem_layouttext3,
				myorderitem_layouttext4, myorderitem_layouttext5;
		RelativeLayout myorderitem_layoutlayout1;
		Button myorder_all, myorder_yingfu;
		ImageView go_pay, cancle_order;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void onClick(View v) {
		switch (v.getId()) {

		case R.id.myorderitem_layoutlayout1:
			int position = (Integer) v.getTag();
			Bundle bundle = new Bundle();
			bundle.putString(OrderDetailActivity.TAG,
					list.get(position).order_id);
			bundle.putString("id", list.get(position).id);
			bundle.putString("product_name", list.get(position).product_name);
			IntentUtil.activityForward(activity, OrderDetailActivity.class,
					bundle, false);
			break;
		case R.id.go_pay:
			int position1 = (Integer) v.getTag();
			status=list.get(position1).status;
			if (status.equals("pending")) {
				Message message = new Message();
				message.what = 2;
				message.obj = list.get(position1);
				myhandler.sendMessage(message);

			} else if (status.equals("processing")) {
				// 待发货
				ToastUtil.showToast(activity, "开发中");
			} else if (status.equals("canceled") || status.equals("complete")) {
				// 已发货
				@SuppressWarnings("rawtypes")
				Map map = new HashMap();
				map.put("order_id", list.get(position1).order_id);
				new OrderAsyTask(InterfaceParams.reorder, map).execute();
			}

			break;
		case R.id.cancle_order:
			int position2 = (Integer) v.getTag();
			status=list.get(position2).status;
			if (status.equals("pending")) {
				// 不是待支付就隐藏按钮
				Map<String, String> map = new HashMap<String, String>();
				map.put("order_id", list.get(position2).order_id);
				new OrderAsyTask(InterfaceParams.cancelOrder, map).execute();

			} else if (status.equals("processing")) {
				// 待发货
				Map<String, String> map = new HashMap<String, String>();
				map.put("order_id", list.get(position2).order_id);
				new OrderAsyTask(InterfaceParams.completeOrder, map).execute();

			} else if (status.equals("canceled")|status.equals("complete")) {
				@SuppressWarnings("rawtypes")
				Map map = new HashMap();
				map.put("order_id", list.get(position2).order_id);
				new OrderAsyTask(InterfaceParams.deletelOrder, map).execute();
			}
			break;
		}

	}

	// 取消订单，再次购买，确认收货
	@SuppressWarnings("unused")
	private class OrderAsyTask extends MyAsyncTask {
		private String interfaceParams;

		@SuppressWarnings("unused")
		public OrderAsyTask(String interfaceParams, Map<String, String> map) {
			super(activity);
			this.interfaceParams = interfaceParams;
			this.map = map;
		}

		private Map<String, String> map;

		@Override
		protected Object doInBackground(Object... params) {
			return josnService.getData(interfaceParams, map, false, null);
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
			if (interfaceParams.equals(InterfaceParams.reorder)) {
				com.jiajie.jiajieproject.contents.Constants.isReOrder = true;
				IntentUtil.activityForward(activity, MainActivity.class, null,
						true);
			}else{
				// 刷新列表
				myhandler.sendEmptyMessage(1);
			}
			
		}

	}

	

}
