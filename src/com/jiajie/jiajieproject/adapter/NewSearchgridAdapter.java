/**
 * 
 */
package com.jiajie.jiajieproject.adapter;

import java.util.ArrayList;

import android.R.integer;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.jiajie.jiajieproject.R;
import com.jiajie.jiajieproject.activity.GoodsdetailActivity;
import com.jiajie.jiajieproject.activity.HistoryLogisticsActivity;
import com.jiajie.jiajieproject.activity.NewSearchActivity;
import com.jiajie.jiajieproject.activity.OrderInformationActivity;
import com.jiajie.jiajieproject.activity.SearchActivity;
import com.jiajie.jiajieproject.adapter.HistoryBuyPartsAdapter.ViewHolder;
import com.jiajie.jiajieproject.contents.Constants;
import com.jiajie.jiajieproject.utils.ImageLoad;
import com.jiajie.jiajieproject.utils.IntentUtil;
import com.jiajie.jiajieproject.utils.ToastUtil;

/**
 * 项目名称：HaiChuanProject 类名称：FaBuSearchAdapter 类描述： 创建人：王蕾 创建时间：2015-7-29
 * 下午2:19:53 修改备注：
 */
@SuppressWarnings("unused")
public class NewSearchgridAdapter extends BaseAdapter implements OnClickListener{
	JSONArray list = new JSONArray();
	private Activity activity;

	public NewSearchgridAdapter(Activity activity, ImageLoad imageLoad) {
		this.activity = activity;
	}

	@Override
	public int getCount() {
		if (list.size() < 8) {
			return list.size();

		} else {
			return 8;
		}

	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	public void setdata(JSONArray list) {
		this.list = list;
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
					R.layout.newsearch_griditem, null);

			vh.newsearch_griditem = (TextView) convertView
					.findViewById(R.id.newsearch_griditem);
			convertView.setTag(vh);
		} else {
			vh = (ViewHolder) convertView.getTag();
		}
		vh.newsearch_griditem.setText(list.getString(position));
		vh.newsearch_griditem.setTag(position);
		vh.newsearch_griditem.setOnClickListener(this);
		return convertView;
	}

	class ViewHolder {

		TextView newsearch_griditem;

	}

	@Override
	public void onClick(View v) {
		int position=(Integer) v.getTag();
		Bundle bundle = new Bundle();
		bundle.putString(NewSearchActivity.TAG, list.get(position).toString());
		IntentUtil.activityForward(activity, SearchActivity.class, bundle,
				true);
	}


}
