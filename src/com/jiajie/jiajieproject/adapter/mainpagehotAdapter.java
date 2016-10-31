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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jiajie.jiajieproject.R;
import com.jiajie.jiajieproject.activity.GoodsdetailActivity;
import com.jiajie.jiajieproject.activity.PartsActivity;
import com.jiajie.jiajieproject.utils.ImageLoad;
import com.jiajie.jiajieproject.utils.IntentUtil;
import com.mrwujay.cascade.model.MainPageObject;

/**
 * 项目名称：HaiChuanProject 类名称：FaBuSearchAdapter 类描述： 创建人：王蕾 创建时间：2015-7-29
 * 下午2:19:53 修改备注：
 */
public class mainpagehotAdapter extends BaseAdapter implements OnClickListener{

	private ArrayList<MainPageObject> list = new ArrayList<MainPageObject>();
	private Activity activity;
	private ImageLoad imageLoad;

	public mainpagehotAdapter(Activity activity, ImageLoad imageLoad) {
		this.activity = activity;
		this.imageLoad = imageLoad;
	
	}


	@Override
	public int getCount() {
		if (list.size() >= 4) {
			return 4;
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
					R.layout.hotpart_itemlayout, null);
			vh.hotpartimage = (ImageView) convertView
					.findViewById(R.id.hotpartproduce);
			vh.hot_layout = (LinearLayout) convertView
					.findViewById(R.id.hot_layout);
			vh.hotpartprice = (TextView) convertView
					.findViewById(R.id.hotpartprice);
			vh.hotparttext = (TextView) convertView
					.findViewById(R.id.hotparttext);
			convertView.setTag(vh);
		} else {
			vh = (ViewHolder) convertView.getTag();
		}
		vh.hot_layout.setOnClickListener(this);
		vh.hot_layout.setTag(position);
		imageLoad.loadImg(vh.hotpartimage, list.get(position).small_image, R.drawable.jiazaitupian);
		vh. hotparttext.setText(list.get(position).name);
	
		 vh.hotpartprice.setText("¥"+list.get(position).price.substring(
					0, list.get(position).price.lastIndexOf('.'))+".00");
		return convertView;
	}

	class ViewHolder {
		LinearLayout hot_layout;
		ImageView hotpartimage;
		TextView hotparttext, hotpartprice;

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
