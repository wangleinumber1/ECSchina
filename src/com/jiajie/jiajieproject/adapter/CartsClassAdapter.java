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
public class CartsClassAdapter extends BaseAdapter {
	private ArrayList<produceClass> list = new ArrayList<produceClass>();
	private Activity activity;
	private ImageLoad imageLoad;
	
	public CartsClassAdapter(Activity activity,ImageLoad imageLoad) {
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
					R.layout.cartsclasslistview_item, null);
			vh.cartclassitemimage = (ImageView) convertView
					.findViewById(R.id.cartclassitemimage);
			vh.hoticon = (TextView) convertView.findViewById(R.id.hoticon);
			vh.cartclassitemtext1 = (TextView) convertView
					.findViewById(R.id.cartclassitemtext1);
			
			vh.cartclassitemtext2 = (TextView) convertView
					.findViewById(R.id.cartclassitemtext2);
			vh.cartclassitemtext3 = (TextView) convertView
					.findViewById(R.id.cartclassitemtext3);
			convertView.setTag(vh);
		} else {
			vh = (ViewHolder) convertView.getTag();
		}
		produceClass produceClass=list.get(position);
		imageLoad.loadImg(vh.cartclassitemimage, produceClass.image,  R.drawable.jiazaitupian);
		vh.cartclassitemtext1.setText(produceClass.name);
		if (list.get(position).price.equals("暂无报价")) {
			vh.cartclassitemtext2.setText(list.get(position).price);
		} else {
			vh.cartclassitemtext2.setText("¥"+list.get(position).price.substring(
					0, list.get(position).price.lastIndexOf('.'))+".00");
		}
//		vh.cartclassitemtext2.setText(list.get(position).price);;
		vh.cartclassitemtext3.setText("库存仅剩"+produceClass.qty.substring(0, produceClass.qty.lastIndexOf('.'))+"件");
		return convertView;
	}

	class ViewHolder {
		ImageView cartclassitemimage;
		TextView cartclassitemtext1, cartclassitemtext2, cartclassitemtext3, hoticon;
	}

}
