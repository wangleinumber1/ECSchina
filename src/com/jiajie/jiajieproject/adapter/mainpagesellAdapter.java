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
import android.util.Log;
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
import com.jiajie.jiajieproject.activity.GoldMedalActivity;
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
public class mainpagesellAdapter extends BaseAdapter implements OnClickListener{

	private ArrayList<MainPageObject> list = new ArrayList<MainPageObject>();
	private Activity activity;
	private ImageLoad imageLoad;

	public mainpagesellAdapter(Activity activity, ImageLoad imageLoad) {
		this.activity = activity;
		this.imageLoad = imageLoad;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		if (list.size() >= 6) {
			return 6;
		} else {
			return list.size();
		}

	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	public ArrayList<MainPageObject> getdata() {
		return list;
	}

	public void setDate(ArrayList<MainPageObject> list) {
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
					R.layout.sellpart_itemlayout, null);
			vh.sellpartimage = (ImageView) convertView
					.findViewById(R.id.sellpartimage);
			vh.sell_layout = (RelativeLayout) convertView
					.findViewById(R.id.sell_layout);
			vh.sellpartprice = (TextView) convertView
					.findViewById(R.id.sellpartprice);
			vh.sellparttext = (TextView) convertView
					.findViewById(R.id.sellparttext);
			convertView.setTag(vh);
		} else {
			vh = (ViewHolder) convertView.getTag();
		}
		vh.sell_layout.setOnClickListener(this);
		vh.sell_layout.setTag(position);
		imageLoad.loadImg(vh.sellpartimage, list.get(position).small_image,
				R.drawable.jiazaitupian);
		vh.sellparttext.setText(list.get(position).name);
		vh.sellpartprice.setText("¥"
				+ list.get(position).price.substring(0,
						list.get(position).price.lastIndexOf('.')) + ".00");
		return convertView;
	}

	class ViewHolder {
		RelativeLayout sell_layout;
		ImageView sellpartimage;
		TextView sellparttext, sellpartprice;

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
