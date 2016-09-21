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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.jiajie.jiajieproject.R;
import com.jiajie.jiajieproject.utils.ImageLoad;
import com.mrwujay.cascade.model.produceClass;

/**
 * 项目名称：NewProject 类名称：LogisticStateAdapter 类描述： 创建人：王蕾 创建时间：2015-9-18 下午2:08:32
 * 修改备注：
 */
public class LogisticStateAdapter extends BaseAdapter {
	private ArrayList<produceClass> list = new ArrayList<produceClass>();
	private Activity activity;
	private ImageLoad imageLoad;

	public LogisticStateAdapter(Activity activity, ImageLoad imageLoad) {
		this.activity = activity;
		this.imageLoad = imageLoad;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 5;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return position;
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
					R.layout.logisticstatelistview_item, null);
			vh.Button1 = (Button) convertView.findViewById(R.id.button1);
			vh.Button2 = (Button) convertView.findViewById(R.id.button2);
			vh.Button3 = (ImageView) convertView.findViewById(R.id.button3);
			vh.wuliutext1 = (TextView) convertView
					.findViewById(R.id.wuliutext1);
			vh.wuliutext2 = (TextView) convertView
					.findViewById(R.id.wuliutext2);
			convertView.setTag(vh);
		} else {
			vh = (ViewHolder) convertView.getTag();
		}
		if(position==0){
			vh.Button1.setVisibility(View.VISIBLE);
			vh.Button2.setVisibility(View.GONE);
			vh.Button3.setVisibility(View.GONE);
		}else if(position==4){
			vh.Button1.setVisibility(View.GONE);
			vh.Button2.setVisibility(View.VISIBLE);
			vh.Button3.setVisibility(View.GONE);
		}else{
			vh.Button1.setVisibility(View.GONE);
			vh.Button2.setVisibility(View.GONE);
			vh.Button3.setVisibility(View.VISIBLE);
		}
		return convertView;

	}

	private class ViewHolder {
		private TextView wuliutext1, wuliutext2;
		private Button Button1, Button2;
		ImageView Button3;
	}
}
