/**
 * 
 */
package com.jiajie.jiajieproject.adapter;

import java.util.ArrayList;

import com.jiajie.jiajieproject.R;
import com.jiajie.jiajieproject.utils.ImageLoad;
import com.mrwujay.cascade.model.produceClass;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 项目名称：NewProject 类名称：CartsClassAdapter 类描述： 创建人：王蕾 创建时间：2015-9-17 下午5:08:41
 * 修改备注：
 */
public class MyCarefulPartsAdapter extends BaseAdapter {
	private ArrayList<produceClass> list = new ArrayList<produceClass>();
	private Activity activity;
	private ImageLoad imageLoad;

	public MyCarefulPartsAdapter(Activity activity, ImageLoad imageLoad) {
		super();
		this.activity = activity;
		this.imageLoad = imageLoad;
	}

	public void clearData() {
		list.clear();

	}

	public void setdata(ArrayList<produceClass> list) {
		this.list=list;
	}

	public ArrayList<produceClass> getdata() {
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
					R.layout.mycarefulpartsitem_layout, null);
			vh.mycarefulpartsitemimage = (ImageView) convertView
					.findViewById(R.id.mycarefulpartsitemimage);

			vh.mycarefulpartsitemtext1 = (TextView) convertView
					.findViewById(R.id.mycarefulpartsitemtext1);

			vh.mycarefulpartsitemtext2 = (TextView) convertView
					.findViewById(R.id.mycarefulpartsitemtext2);

			convertView.setTag(vh);
		} else {
			vh = (ViewHolder) convertView.getTag();
		}
		imageLoad.loadImg(vh.mycarefulpartsitemimage, list.get(position).image, R.drawable.jiazaitupian);
		vh.mycarefulpartsitemtext1.setText(list.get(position).name);
		vh.mycarefulpartsitemtext2.setText("¥"+list.get(position).price.substring(0, list.get(position).price.lastIndexOf('.'))+".00");
		return convertView;
	}

	class ViewHolder {
		ImageView mycarefulpartsitemimage;
		TextView mycarefulpartsitemtext1, mycarefulpartsitemtext2;
	}

}
