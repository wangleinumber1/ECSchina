/**
 * 
 */
package com.jiajie.jiajieproject.adapter;

import java.util.ArrayList;

import com.jiajie.jiajieproject.R;
import com.jiajie.jiajieproject.model.People;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * 项目名称：NewProject 类名称：NewprojectMessageCityAdapter 类描述： 创建人：王蕾 创建时间：2016-3-25
 * 下午3:44:31 修改备注：
 */
public class NewprojectMessageCityAdapter extends BaseAdapter {
	private ArrayList<String> list = new ArrayList<String>();
	private LayoutInflater inflate;

	public NewprojectMessageCityAdapter(LayoutInflater inflate,
			ArrayList<String> list) {
		this.inflate = inflate;
		this.list = list;
	}

	public void clearData() {
		list.clear();

	}

	public ArrayList<String> getdata() {
		return list;
	}

	public void setdata(ArrayList<String> list) {
		this.list = list;
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
		View view = inflate.inflate(R.layout.citylistviewitem, null);
		TextView text = (TextView) view.findViewById(R.id.citytextView);
		text.setText(list.get(position));
		return view;
	}

}
