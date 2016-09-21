/**
 * 
 */
package com.jiajie.jiajieproject.adapter;

import java.util.ArrayList;

import android.app.Activity;
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
public class PartsAdapter extends BaseAdapter {
	private ArrayList<produceClass> list = new ArrayList<produceClass>();
	private Activity activity;
	private ImageLoad imageLoad;

	public PartsAdapter(Activity activity, ImageLoad imageLoad) {
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
					R.layout.partslistview_item, null);
			vh.partsitemimage = (ImageView) convertView
					.findViewById(R.id.partsitemimage);
			vh.hoticon = (ImageView) convertView.findViewById(R.id.hoticon);
			vh.partsitemtext1 = (TextView) convertView
					.findViewById(R.id.partsitemtext1);
			vh.partsitemtext2 = (TextView) convertView
					.findViewById(R.id.partsitemtext2);
			convertView.setTag(vh);
		} else {
			vh = (ViewHolder) convertView.getTag();
		}
		produceClass produceClass = list.get(position);
		imageLoad.loadImg(vh.partsitemimage, produceClass.image,
				R.drawable.jiazaitupian);
		vh.partsitemtext1.setText(produceClass.name);
		if (list.get(position).price.equals("暂无报价")) {
			vh.partsitemtext2.setText(list.get(position).price);
		} else {
			vh.partsitemtext2.setText("¥"+list.get(position).price.substring(
					0, list.get(position).price.lastIndexOf('.'))+".00");
		}
//		vh.partsitemtext2.setText(list.get(position).price);
		return convertView;
	}

	class ViewHolder {
		ImageView partsitemimage, hoticon;
		TextView partsitemtext1, partsitemtext2;
	}

}
