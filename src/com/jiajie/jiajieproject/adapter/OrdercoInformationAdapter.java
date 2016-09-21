/**
 * 
 */
package com.jiajie.jiajieproject.adapter;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jiajie.jiajieproject.R;
import com.jiajie.jiajieproject.utils.ImageLoad;
import com.mrwujay.cascade.model.produceClass;

/**
 * 项目名称：HaiChuanProject 类名称：FaBuSearchAdapter 类描述： 创建人：王蕾 创建时间：2015-7-29
 * 下午2:19:53 修改备注：
 */
public class OrdercoInformationAdapter extends BaseAdapter {

	private Activity activity;
	private ArrayList<produceClass> list = new ArrayList<produceClass>();
	private ImageLoad imageLoad;

	@SuppressLint("UseSparseArrays")
	public OrdercoInformationAdapter(Activity activity, ImageLoad imageLoad) {
		this.activity = activity;
		this.imageLoad = imageLoad;
	}

	public ArrayList<produceClass> setData(ArrayList<produceClass> list) {
		return this.list = list;

	}

	public ArrayList<produceClass> getData() {
		return list;

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
					R.layout.orderconfirmation_itemlayout, null);
			vh.imgeView2 = (ImageView) convertView.findViewById(R.id.imgeView2);

			vh.orderconfirmationitem_layouttext1 = (TextView) convertView
					.findViewById(R.id.orderconfirmationitem_layouttext1);
			vh.pricetext = (TextView) convertView
					.findViewById(R.id.pricetext);
			vh.numbercount = (TextView) convertView
					.findViewById(R.id.numbercount);
			convertView.setTag(vh);
		} else {
			vh = (ViewHolder) convertView.getTag();
		}
		imageLoad.loadImg(vh.imgeView2, list.get(position).image, R.drawable.jiazaitupian);

		vh.pricetext.setText("¥"+list.get(position).price.substring(0, list.get(position).price.lastIndexOf('.'))+".00");
		vh.orderconfirmationitem_layouttext1.setText(list.get(position).name);
		vh.numbercount.setText(list.get(position).qty_ordered.substring(0, list.get(position).qty_ordered.lastIndexOf('.'))+".00");
		return convertView;
	}

	class ViewHolder {
		ImageView imgeView2;
		TextView orderconfirmationitem_layouttext1, numbercount,pricetext;
	}

}
