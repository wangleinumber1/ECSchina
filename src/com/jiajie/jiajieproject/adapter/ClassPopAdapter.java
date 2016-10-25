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
import android.widget.TextView;

import com.jiajie.jiajieproject.R;
import com.jiajie.jiajieproject.model.CategoriesClass;

/**
 * 项目名称：HaiChuanProject 类名称：FaBuSearchAdapter 类描述： 创建人：王蕾 创建时间：2015-7-29
 * 下午2:19:53 修改备注：
 */
public class ClassPopAdapter extends BaseAdapter {

	private ArrayList<CategoriesClass> list = new ArrayList<CategoriesClass>();
	private Activity activity;

	public ClassPopAdapter(Activity activity) {
		this.activity = activity;
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

	public void setdata(ArrayList<CategoriesClass> list) {
		this.list=list;
	}

	public ArrayList<CategoriesClass> getdata() {
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
					R.layout.item_b_top_nav_layout, null);
			vh.text=(TextView) convertView.findViewById(R.id.text);
			convertView.setTag(vh);
		} else {
			vh = (ViewHolder) convertView.getTag();
		}
		vh.text.setText(list.get(position).name);
		
		return convertView;
	}

	class ViewHolder {
		TextView text;
	}
	
}
