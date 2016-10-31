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

import com.jiajie.jiajieproject.R;
import com.jiajie.jiajieproject.activity.GoodsdetailActivity;
import com.jiajie.jiajieproject.activity.HistoryLogisticsActivity;
import com.jiajie.jiajieproject.activity.OrderInformationActivity;
import com.jiajie.jiajieproject.activity.PartsActivity;
import com.jiajie.jiajieproject.adapter.HistoryBuyPartsAdapter.ViewHolder;
import com.jiajie.jiajieproject.contents.Constants;
import com.jiajie.jiajieproject.utils.ImageLoad;
import com.jiajie.jiajieproject.utils.IntentUtil;
import com.mrwujay.cascade.model.MainPageObject;
import com.mrwujay.cascade.model.produceClass;
import com.jiajie.jiajieproject.utils.ToastUtil;

/**
 * 项目名称：HaiChuanProject 类名称：FaBuSearchAdapter 类描述： 创建人：王蕾 创建时间：2015-7-29
 * 下午2:19:53 修改备注：
 */
public class mainpagenewAdapter extends BaseAdapter implements OnClickListener{

	private ArrayList<MainPageObject> list = new ArrayList<MainPageObject>();
	private Activity activity;
	private ImageLoad imageLoad;

	public mainpagenewAdapter(Activity activity, ImageLoad imageLoad) {
		this.activity = activity;
		this.imageLoad = imageLoad;
	}


	@Override
	public int getCount() {
		if (list.size() >= 6) {
			return 6;
		} else {
			return list.size();
		}
	}

	public void setDate(ArrayList<MainPageObject> list) {
		this.list = list;
	}
	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return position;
	}

//	public void setdata(ArrayList<Object> list) {
//		this.list.addAll(list);
//	}

	public ArrayList<MainPageObject> getdata() {
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
					R.layout.newpart_itemlayout, null);
			vh.newpartimage = (ImageView) convertView
					.findViewById(R.id.newpartimage);
			vh.new_layout = (RelativeLayout) convertView
					.findViewById(R.id.new_layout);
			vh.newpartprice = (TextView) convertView
					.findViewById(R.id.newpartprice);
			vh.newparttext = (TextView) convertView
					.findViewById(R.id.newparttext);
			convertView.setTag(vh);
		} else {
			vh = (ViewHolder) convertView.getTag();
		}
		vh.new_layout.setOnClickListener(this);
		vh.new_layout.setTag(position);
		imageLoad.loadImg(vh.newpartimage, list.get(position).small_image, R.drawable.jiazaitupian);
		vh. newparttext.setText(list.get(position).name);
		 vh.newpartprice.setText("¥"+list.get(position).price.substring(
					0, list.get(position).price.lastIndexOf('.'))+".00");
		return convertView;
	}

	class ViewHolder {
		ImageView newpartimage;
		RelativeLayout new_layout;
		TextView newparttext, newpartprice;

	}

	@Override
	public void onClick(View v) {
		Bundle bundle = new Bundle();
		bundle.putSerializable(PartsActivity.TAG, list.get((Integer) v.getTag()));
		IntentUtil.activityForward(activity,
				GoodsdetailActivity.class, bundle, false);
		
	}

	// // 打电话
	// private void callphone() {
	// Intent phoneIntent = new Intent("android.intent.action.CALL",
	// Uri.parse("tel:" + Constants.phonenumber));
	// // 启动
	// activity.startActivity(phoneIntent);
	// }

}
