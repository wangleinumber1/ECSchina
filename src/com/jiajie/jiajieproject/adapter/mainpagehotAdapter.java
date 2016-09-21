/**
 * 
 */
package com.jiajie.jiajieproject.adapter;

import java.util.ArrayList;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jiajie.jiajieproject.R;
import com.jiajie.jiajieproject.utils.ImageLoad;
import com.mrwujay.cascade.model.MainPageObject;

/**
 * 项目名称：HaiChuanProject 类名称：FaBuSearchAdapter 类描述： 创建人：王蕾 创建时间：2015-7-29
 * 下午2:19:53 修改备注：
 */
public class mainpagehotAdapter extends MainPageAdapter{

//	private ArrayList<produceClass> list = new ArrayList<produceClass>();
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
			vh.hotpartprice = (TextView) convertView
					.findViewById(R.id.hotpartprice);
			vh.hotparttext = (TextView) convertView
					.findViewById(R.id.hotparttext);
			convertView.setTag(vh);
		} else {
			vh = (ViewHolder) convertView.getTag();
		}
		imageLoad.loadImg(vh.hotpartimage, list.get(position).small_image, R.drawable.jiazaitupian);
		vh. hotparttext.setText(list.get(position).name);
		 vh.hotpartprice.setText(list.get(position).price);
		return convertView;
	}

	class ViewHolder {
		ImageView hotpartimage;
		TextView hotparttext, hotpartprice;

	}

	// // 打电话
	// private void callphone() {
	// Intent phoneIntent = new Intent("android.intent.action.CALL",
	// Uri.parse("tel:" + Constants.phonenumber));
	// // 启动
	// activity.startActivity(phoneIntent);
	// }

}
