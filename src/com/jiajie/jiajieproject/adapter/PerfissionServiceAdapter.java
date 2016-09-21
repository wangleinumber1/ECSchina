/**
 * 
 */
package com.jiajie.jiajieproject.adapter;

import java.util.ArrayList;

import com.jiajie.jiajieproject.R;
import com.jiajie.jiajieproject.adapter.SalesPartsAdapter.ViewHolder;
import com.jiajie.jiajieproject.utils.ImageLoad;
import com.mrwujay.cascade.model.produceClass;
import com.jiajie.jiajieproject.utils.ToastUtil;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**   
 * 项目名称：NewProject   
 * 类名称：PerfissionServiceAdapter   
 * 类描述：   
 * 创建人：王蕾
 * 创建时间：2015-9-22 下午7:10:54   
 * 修改备注：    
 */
public class PerfissionServiceAdapter extends BaseAdapter{
	private ArrayList<produceClass> list = new ArrayList<produceClass>();
	private Activity activity;
	private ImageLoad imageLoad;

	public PerfissionServiceAdapter(Activity activity, ImageLoad imageLoad) {
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
					R.layout.perfissionsevicelayout_item, null);
			vh.perfisssionitemimage = (ImageView) convertView
					.findViewById(R.id.perfisssionitemimage);
//			vh.smileicon = (ImageView) convertView.findViewById(R.id.smileicon);
			vh.perfisssionitemtext1 = (TextView) convertView
					.findViewById(R.id.perfisssionitemtext1);
			vh.perfisssionitemtext2 = (TextView) convertView
					.findViewById(R.id.perfisssionitemtext2);
			
			convertView.setTag(vh);
		} else {
			vh = (ViewHolder) convertView.getTag();
		}
		
//		Log.d("PerfissionServiceAdapter", list.get(position).image);
//		ToastUtil.showToast(activity, list.get(position).image);
		imageLoad.loadImg(vh.perfisssionitemimage,list.get(position).image, R.drawable.jiazaitupian);
		vh.perfisssionitemtext1.setText(list.get(position).name);
		vh.perfisssionitemtext2.setText(list.get(position).price);
//		vh.perfisssionitemtext2.setText("//////");
		
		return convertView;
	}

	class ViewHolder {
		ImageView perfisssionitemimage;
		TextView perfisssionitemtext1,perfisssionitemtext2; 
		ImageView smileicon;
	}

}
