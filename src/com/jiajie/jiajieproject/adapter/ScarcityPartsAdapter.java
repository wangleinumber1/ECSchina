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
 * 项目名称：NewProject   
 * 类名称：ScarcityPartsAdapter   
 * 类描述：   
 * 创建人：王蕾
 * 创建时间：2015-9-17 上午11:22:43   
 * 修改备注：    
 */
public class ScarcityPartsAdapter extends BaseAdapter{

	private ArrayList<produceClass> list = new ArrayList<produceClass>();
	private Activity activity;
	private ImageLoad imageLoad;
	public ScarcityPartsAdapter(Activity activity,ImageLoad imageLoad) {
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
					R.layout.scarcitypartsgridview_item, null);
			vh.salespartsitemimage = (ImageView) convertView
					.findViewById(R.id.salespartsitemimage);
			vh.hoticon = (TextView) convertView.findViewById(R.id.hoticon);
			vh.salespartsitemtext1 = (TextView) convertView
					.findViewById(R.id.salespartsitemtext1);
			vh.salespartsitemtext2 = (TextView) convertView
					.findViewById(R.id.salespartsitemtext2);
			
			convertView.setTag(vh);
		} else {
			vh = (ViewHolder) convertView.getTag();
		}
		produceClass produceClass=list.get(position);
		imageLoad.loadImg(vh.salespartsitemimage, produceClass.image,  R.drawable.jiazaitupian);
		vh.salespartsitemtext1.setText(produceClass.name);
		vh.salespartsitemtext2.setText("¥"+list.get(position).price.substring(0, list.get(position).price.lastIndexOf('.'))+".00");
		
		return convertView;
	}

	class ViewHolder {
		ImageView salespartsitemimage;
		TextView salespartsitemtext1, salespartsitemtext2, hoticon;
	}


}
