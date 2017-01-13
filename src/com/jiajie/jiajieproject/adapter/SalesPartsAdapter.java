/**
 * 
 */
package com.jiajie.jiajieproject.adapter;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jiajie.jiajieproject.R;
import com.jiajie.jiajieproject.activity.GoodsdetailActivity;
import com.jiajie.jiajieproject.activity.PartsActivity;
import com.jiajie.jiajieproject.utils.ImageLoad;
import com.jiajie.jiajieproject.utils.IntentUtil;
import com.mrwujay.cascade.model.MainPageObject;

/**
 * 项目名称：NewProject 类名称：SalessalespartsAdapter 类描述： 创建人：王蕾 创建时间：2015-9-17
 * 上午10:27:24 修改备注：
 */
public class SalesPartsAdapter extends BaseAdapter implements OnClickListener {
	private ArrayList<MainPageObject> list = new ArrayList<MainPageObject>();
	private Activity activity;
	private ImageLoad imageLoad;

	public SalesPartsAdapter(Activity activity, ImageLoad imageLoad) {
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

	public ArrayList<MainPageObject> getdata() {
		return list;
	}

	public void setdata(ArrayList<MainPageObject> list) {
		this.list.addAll(list);
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
					R.layout.salespartslistview_item, null);
			vh.salespartsitemimage = (ImageView) convertView
					.findViewById(R.id.salespartsitemimage);
			vh.search_layout = (RelativeLayout) convertView
					.findViewById(R.id.search_layout);
			vh.salespartsitemtext1 = (TextView) convertView
					.findViewById(R.id.salespartsitemtext1);
			vh.salespartsitemtext2 = (TextView) convertView
					.findViewById(R.id.salespartsitemtext2);
			vh.salespartsitemtext3 = (TextView) convertView
					.findViewById(R.id.salespartsitemtext3);
			convertView.setTag(vh);
		} else {
			vh = (ViewHolder) convertView.getTag();
		}
		MainPageObject MainPageObject = list.get(position);
		vh.search_layout.setOnClickListener(this);
		vh.search_layout.setTag(position);
		imageLoad.loadImg(vh.salespartsitemimage, MainPageObject.image,
				R.drawable.jiazaitupian);
		vh.salespartsitemtext1.setText(MainPageObject.name);
		if (list.get(position).price.equals("暂无报价")) {
			vh.salespartsitemtext2.setText(list.get(position).price);
		} else {
			vh.salespartsitemtext2.setText("¥"+list.get(position).price.substring(
					0, list.get(position).price.lastIndexOf('.'))+".00");
		}
		vh.salespartsitemtext3.setText("库存数量："+MainPageObject.qty.substring(0, MainPageObject.qty.lastIndexOf('.')));
		return convertView;
	}

	class ViewHolder {
		ImageView salespartsitemimage;
		TextView salespartsitemtext1, salespartsitemtext2,
				salespartsitemtext3;
		RelativeLayout search_layout;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.search_layout:
			Bundle bundle = new Bundle();
			bundle.putSerializable(PartsActivity.TAG, list.get((Integer) v.getTag()));
			IntentUtil.activityForward(activity,
					GoodsdetailActivity.class, bundle, false);
			break;

		default:
			break;
		}
	
		
	}

}
