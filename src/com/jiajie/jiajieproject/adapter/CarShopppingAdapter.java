/**
 * 
 */
package com.jiajie.jiajieproject.adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jiajie.jiajieproject.R;
import com.jiajie.jiajieproject.activity.CarShopppingActivity;
import com.jiajie.jiajieproject.db.service.SharePreferDB;
import com.jiajie.jiajieproject.utils.ImageLoad;
import com.jiajie.jiajieproject.utils.ToastUtil;
import com.jiajie.jiajieproject.utils.YokaLog;
import com.jiajie.jiajieproject.widget.MyAddAndSubView;
import com.jiajie.jiajieproject.widget.MyAddAndSubView.OnClickAddAndSubListener;
import com.mrwujay.cascade.model.produceClass;

/**
 * 项目名称：NewProject 类名称：CartsClassAdapter 类描述： 创建人：王蕾 创建时间：2015-9-17 下午5:08:41
 * 修改备注：购物车适配器
 */
@SuppressWarnings("unused")
public class CarShopppingAdapter extends BaseAdapter implements
		OnCheckedChangeListener, OnClickAddAndSubListener {
	private Activity activity;
	private ArrayList<produceClass> list = new ArrayList<produceClass>();
	public static Map<Integer, produceClass> isSelected = new HashMap<Integer, produceClass>();
	private Handler mHandler;
	private ImageLoad imageLoad;
	// private SharePreferDB sharePreferDB;
	private static String TAG = "CarShopppingAdapter";

	@SuppressLint("UseSparseArrays")
	public CarShopppingAdapter(Activity activity, Handler mHandler,
			ImageLoad imageLoad) {
		this.activity = activity;
		this.mHandler = mHandler;
		this.imageLoad = imageLoad;
		// this.sharePreferDB = sharePreferDB;
		// initDate();
	}

	// 初始化isSelected的数据
	private void initDate() {
		if(CarShopppingAdapter.isSelected.size()<1)
		for (int i = 0; i < list.size(); i++) {
			produceClass produceClass = list.get(i);
			produceClass.isChoosed = false;
			CarShopppingAdapter.isSelected.put(i, produceClass);

		}

	}

	// public static Map<String, String> getIsSelected() {
	// return isSelected;
	// }

	// public static void setIsSelected(Map<String, String> isSelected) {
	// CarShopppingAdapter.isSelected = isSelected;
	// }

	public void setData(ArrayList<produceClass> list) {
		this.list = list;
		initDate();
	}

	public ArrayList<produceClass> getData() {
		return list;

	}

	// 清空数据
	public void clearData() {
		list.clear();

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
					R.layout.carshoppingitem_layout, null);
			vh.imgeView1 = (CheckBox) convertView.findViewById(R.id.imgeView1);
			vh.imgeView2 = (ImageView) convertView.findViewById(R.id.imgeView2);
			vh.my_add_sub = (MyAddAndSubView) convertView
					.findViewById(R.id.my_add_sub);
			vh.pricetext = (TextView) convertView.findViewById(R.id.pricetext);
			vh.numbertext = (TextView) convertView
					.findViewById(R.id.numbertext);
			vh.catshoppingitem_layouttext1 = (TextView) convertView
					.findViewById(R.id.catshoppingitem_layouttext1);
			convertView.setTag(vh);
		} else {
			vh = (ViewHolder) convertView.getTag();
		}
		vh.imgeView1.setOnCheckedChangeListener(this);
		vh.imgeView1.setTag(position);
		if (CarShopppingAdapter.isSelected != null) {
			vh.imgeView1.setChecked(CarShopppingAdapter.isSelected
					.get(position).isChoosed);
		}
		vh.numbertext.setText("¥"
				+ list.get(position).price.substring(0,
						list.get(position).price.lastIndexOf('.')) + ".00");
		vh.pricetext.setText(list.get(position).productName);
		vh.my_add_sub.setOnClickAddAndSubListener(this);
		vh.my_add_sub.setTag(position);
		imageLoad.loadImg(vh.imgeView2, list.get(position).image,
				R.drawable.jiazaitupian);
		return convertView;
	}

	class ViewHolder {
		CheckBox imgeView1;
		ImageView imgeView2;
		MyAddAndSubView my_add_sub;
		TextView pricetext, catshoppingitem_layouttext1, numbertext;

	}

	/**
	 * 判断是否购物车中所有的商品全部被选中
	 * 
	 * @return true所有条目全部被选中 false还有条目没有被选中
	 */
	private boolean isAllSelected() {
		boolean flag = true;
		for (int i = 0; i < list.size(); i++) {
			if (!list.get(i).isChoosed) {
				flag = false;
				break;
			}
		}
		return flag;
	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		CheckBox imge = (CheckBox) buttonView;
		int position = (Integer) imge.getTag();
		produceClass produceClass = list.get(position);
		produceClass.isChoosed = imge.isChecked();
		CarShopppingAdapter.isSelected.put(position, produceClass);

		// 如果所有的物品全部被选中，则全选按钮也默认被选中

		if (isAllSelected()) {
			Message message = mHandler.obtainMessage(9);
			message.obj = true;
			mHandler.sendMessage(message);
		}
		mHandler.sendEmptyMessage(13);
	}

	@Override
	public void clickAdd(int count, View view) {
		MyAddAndSubView view1 = (MyAddAndSubView) view;
		Message message = mHandler.obtainMessage();
		message.obj = Integer.parseInt(list.get((Integer) view.getTag()).price);
		message.arg1 = count;
		message.arg2 = Integer.parseInt(view.getTag().toString());
		message.what = 11;
		mHandler.sendMessage(message);

	}

	@Override
	public void clickSub(int count, View view) {
		// TODO Auto-generated method stub
		MyAddAndSubView view1 = (MyAddAndSubView) view;
		Message message = mHandler.obtainMessage();
		message.obj = -Integer.parseInt(list.get((Integer) view.getTag()).price);
		message.arg1 = count;
		message.arg2 = Integer.parseInt(view.getTag().toString());
		message.what = 11;
		mHandler.sendMessage(message);

	}

	// @Override
	// public void onClick(View v) {
	// // TODO Auto-generated method stub
	// CheckBox imge = (CheckBox) v;
	// int position = (Integer) imge.getTag();
	// produceClass produceClass = list.get(position);
	// produceClass.isChoosed = imge.isChecked();
	// CarShopppingActivity.isSelected.put(position, produceClass);
	//
	// // 如果所有的物品全部被选中，则全选按钮也默认被选中
	//
	// if (isAllSelected()) {
	// Message message = mHandler.obtainMessage(9);
	// message.obj = true;
	// mHandler.sendMessage(message);
	// }
	//
	// }

}
