/**
 * 
 */
package com.jiajie.jiajieproject.adapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import android.app.Activity;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.amap.api.services.core.ac;

import com.jiajie.jiajieproject.R;
import com.jiajie.jiajieproject.activity.ProjectDetailActivity;
import com.jiajie.jiajieproject.model.People;
import com.jiajie.jiajieproject.utils.ImageLoad;
import com.jiajie.jiajieproject.utils.IntentUtil;

/**
 * 项目名称：NewProject 类名称：SalessalespartsAdapter 类描述： 创建人：王蕾 创建时间：2015-9-17
 * 上午10:27:24 修改备注：
 */
public class NewProjectMessageAdapter extends BaseAdapter implements
		OnClickListener {
	private ArrayList<People> list = new ArrayList<People>();
	private Activity activity;
	private SQLiteDatabase database;
	private ColorStateList graycolors;
	private ColorStateList blackcolors;
	private Handler handler;
	private static HashMap hashmap = new HashMap<String, String>();

	public NewProjectMessageAdapter(Activity activity, SQLiteDatabase database,
			Handler handler) {
		this.activity = activity;
		this.database = database;
		this.handler = handler;
		graycolors = activity.getResources().getColorStateList(
				R.color.fapiaotextcolor);
		blackcolors = activity.getResources().getColorStateList(R.color.black);
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

	public ArrayList<People> getdata() {
		return list;
	}

	public void setdata(ArrayList<People> list) {
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
					R.layout.newprojectmessagelistview_item, null);
			vh.newprojectmessagepoint = (ImageView) convertView
					.findViewById(R.id.newprojectmessagepoint);
			// vh.fanyeicon = (ImageView)
			// convertView.findViewById(R.id.fanyeicon);
			vh.newprojectmessageTitle = (TextView) convertView
					.findViewById(R.id.newprojectmessageTitle);
			vh.newprojectmessagetime = (TextView) convertView
					.findViewById(R.id.newprojectmessagetime);
			convertView.setOnClickListener(this);
			convertView.setTag(vh);
		} else {
			vh = (ViewHolder) convertView.getTag();

		}
		convertView.setTag(R.layout.newprojectmessagelistview_item, position);
		vh.newprojectmessageTitle.setText(list.get(position).KEY_TITLE);
		vh.newprojectmessagetime.setText(list.get(position).KEY_PUB_DATE);
		// getIsSelected().get(position)

		return convertView;
	}

	class ViewHolder {
		ImageView newprojectmessagepoint, fanyeicon;
		TextView newprojectmessageTitle, newprojectmessagetime;
	}

	// 时间转换
	// public String getTime(long time) {
	// SimpleDateFormat sf = null;
	// Long tsLong = System.currentTimeMillis();
	// Log.d("///////////////////", tsLong + "//////////////////");
	// Date d = new Date(time * 1000);
	// Log.d("///////////////////", time + "********************");
	// sf = new SimpleDateFormat("yyyy.MM.dd");
	// return sf.format(d);
	//
	// }

	@Override
	public void onClick(View v) {
		int position = (Integer) v
				.getTag(R.layout.newprojectmessagelistview_item);
		String url = list.get(position).KEY_URL;
		// vh.newprojectmessagepoint.setImageResource(R.drawable.dispoint);
		// vh.newprojectmessageTitle.setTextColor(graycolors);
		// Bundle bundle = new Bundle();
		// bundle.putString("weburl", url);
		// IntentUtil.activityForward(activity, ProjectDetailActivity.class,
		// bundle, false);
		// handler.sendEmptyMessage(1);
		Intent it = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
		it.setClassName("com.android.browser",
				"com.android.browser.BrowserActivity");
		activity.startActivity(it);
		// 刷新数据
		// notifyDataSetChanged();

	}

}
