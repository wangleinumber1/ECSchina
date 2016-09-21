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
public class ServiceProductAdapter extends BaseAdapter {
	private ArrayList<produceClass> list = new ArrayList<produceClass>();
	private Activity activity;
	private ImageLoad imageLoad;
	
	public ServiceProductAdapter(Activity activity,ImageLoad imageLoad) {
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
	public ArrayList<produceClass> getdata(){
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
					R.layout.serviceproductlist_item, null);

			vh.serviceproductitemimage = (ImageView) convertView
					.findViewById(R.id.serviceproductitemimage);
			vh.serviceproductitemtext1 = (TextView) convertView
					.findViewById(R.id.serviceproductitemtext1);
			vh.serviceproductitemtext2 = (TextView) convertView
					.findViewById(R.id.serviceproductitemtext2);
			vh.hoticon = (TextView) convertView.findViewById(R.id.hoticon);
			convertView.setTag(vh);
		} else {
			vh = (ViewHolder) convertView.getTag();
		}
		imageLoad.loadImg(vh.serviceproductitemimage, list.get(position).image, R.drawable.jiazaitupian);
		vh.serviceproductitemtext1.setText(list.get(position).name);
		vh.serviceproductitemtext2.setText(list.get(position).price.substring(0, list.get(position).price.lastIndexOf('.')));
		return convertView;
	}

	class ViewHolder {
		ImageView serviceproductitemimage;
		TextView serviceproductitemtext1, serviceproductitemtext2, hoticon;
	}

}
