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
import com.mrwujay.cascade.model.produceClass;

/**
 * 项目名称：HaiChuanProject 类名称：FaBuSearchAdapter 类描述： 创建人：王蕾 创建时间：2015-7-29
 * 下午2:19:53 修改备注：
 */
public class ProjectreleaseAdapter extends BaseAdapter {
	private ArrayList<produceClass> list = new ArrayList<produceClass>();
	private Activity activity;

	public ProjectreleaseAdapter(ArrayList<produceClass> list, Activity activity) {
		super();
		this.list = list;
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
					R.layout.projectrelease_item, null);
			vh.projectreleaseitemimage = (ImageView) convertView
					.findViewById(R.id.projectreleaseitemimage);

			vh.projectreleaseitemtext1 = (TextView) convertView
					.findViewById(R.id.projectreleaseitemtext1);
			vh.projectreleaseitemtext2 = (TextView) convertView
					.findViewById(R.id.projectreleaseitemtext2);
			convertView.setTag(vh);
		} else {
			vh = (ViewHolder) convertView.getTag();
		}
		vh.projectreleaseitemtext1.setText(list.get(position).title);
		return convertView;
	}

	class ViewHolder {
		ImageView projectreleaseitemimage;
		TextView projectreleaseitemtext1, projectreleaseitemtext2;
	}

}
