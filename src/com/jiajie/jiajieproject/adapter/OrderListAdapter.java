/**
 * 
 */
package com.jiajie.jiajieproject.adapter;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.jiajie.jiajieproject.R;
import com.jiajie.jiajieproject.utils.ImageLoad;
import com.mrwujay.cascade.model.produceClass;

/**
 * 项目名称：NewProject 类名称：SalessalespartsAdapter 类描述： 创建人：王蕾 创建时间：2015-9-17
 * 上午10:27:24 修改备注：
 */
public class OrderListAdapter extends BaseAdapter {
	private ArrayList<produceClass> list = new ArrayList<produceClass>();
	private Activity activity;
	private ImageLoad imageLoad;

	public OrderListAdapter(Activity activity, ImageLoad imageLoad) {
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

	public void clearData() {
		list.clear();

	}

	public ArrayList<produceClass> getdata() {
		return list;
	}

	public void setdata(List<produceClass> list) {
		this.list=(ArrayList<produceClass>) list;
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
					R.layout.salespartslistview_item, null);
			vh.salespartsitemimage = (ImageView) convertView
					.findViewById(R.id.salespartsitemimage);
			vh.salespartsitemtext1 = (TextView) convertView
					.findViewById(R.id.salespartsitemtext1);
			vh.salespartsitemtext2 = (TextView) convertView
					.findViewById(R.id.salespartsitemtext2);
			vh.salespartsitemtext3 = (TextView) convertView
					.findViewById(R.id.salespartsitemtext3);
			convertView.setTag(vh);
		} else {
			vh = (ViewHolder) convertView.getTag();
		}
		produceClass produceClass = list.get(position);
		imageLoad.loadImg(vh.salespartsitemimage, produceClass.image,
				R.drawable.jiazaitupian);
		vh.salespartsitemtext1.setText(produceClass.productName+" "+produceClass.pn);
		if (list.get(position).price.equals("暂无报价")) {
			vh.salespartsitemtext2.setText(list.get(position).price);
		} else {
			vh.salespartsitemtext2.setText("¥"+list.get(position).price.substring(
					0, list.get(position).price.lastIndexOf("00")));
		}
		vh.salespartsitemtext3.setText("数量："+produceClass.qty);
		return convertView;
	}

	class ViewHolder {
		ImageView salespartsitemimage;
		TextView salespartsitemtext1, salespartsitemtext2,
				salespartsitemtext3;
	}

}
