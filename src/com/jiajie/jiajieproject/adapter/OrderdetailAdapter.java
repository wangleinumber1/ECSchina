/**
 * 
 */
package com.jiajie.jiajieproject.adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.content.Context;
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
import com.jiajie.jiajieproject.R;
import com.jiajie.jiajieproject.activity.OrderDetailActivity;
import com.jiajie.jiajieproject.contents.InterfaceParams;
import com.jiajie.jiajieproject.net.service.JosnService;
import com.jiajie.jiajieproject.utils.ImageLoad;
import com.jiajie.jiajieproject.utils.IntentUtil;
import com.mrwujay.cascade.model.produceClass;

/**
 * 项目名称：HaiChuanProject 类名称：FaBuSearchAdapter 类描述： 创建人：王蕾 创建时间：2015-7-29
 * 下午2:19:53 修改备注：
 */
public class OrderdetailAdapter extends BaseAdapter {

	private ArrayList<produceClass> list = new ArrayList<produceClass>();
	private Activity activity;
	private ImageLoad imageLoad;

	public OrderdetailAdapter(Activity activity,ImageLoad imageLoad) {
		super();
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
					R.layout.orderdetail_item, null);
			vh.orderdetail_layoutImgeView1 = (ImageView) convertView
					.findViewById(R.id.orderdetail_layoutImgeView1);
			vh.orderdetail_layouttext2 = (TextView) convertView
					.findViewById(R.id.orderdetail_layouttext2);
			vh.orderdetail_layouttext3 = (TextView) convertView
					.findViewById(R.id.orderdetail_layouttext3);
			vh.orderdetail_layouttext4 = (TextView) convertView
					.findViewById(R.id.orderdetail_layouttext4);
			vh.orderdetail_layouttext5 = (TextView) convertView
					.findViewById(R.id.orderdetail_layouttext5);
			convertView.setTag(vh);
		} else {
			vh = (ViewHolder) convertView.getTag();
		}
		imageLoad.loadImg(vh.orderdetail_layoutImgeView1, list.get(position).image, R.drawable.jiazaitupian);
		vh.orderdetail_layouttext2.setText(list.get(position).product_name);
		vh.orderdetail_layouttext3.setText("PN号:"+list.get(position).pn);
		vh.orderdetail_layouttext4.setText("￥"+list.get(position).price.substring(0, list.get(position).price.lastIndexOf("00")));
		vh.orderdetail_layouttext5.setText("x"+list.get(position).qty.substring(0, list.get(position).qty.lastIndexOf('.')));
		return convertView;
	}

	class ViewHolder {
		ImageView orderdetail_layoutImgeView1;
		TextView orderdetail_layouttext2, orderdetail_layouttext3,
				orderdetail_layouttext4, orderdetail_layouttext5;

	}


}
