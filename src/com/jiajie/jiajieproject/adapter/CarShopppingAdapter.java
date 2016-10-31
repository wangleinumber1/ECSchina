/**
 * 
 */
package com.jiajie.jiajieproject.adapter;

import java.util.ArrayList;
import java.util.HashMap;

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
import com.jiajie.jiajieproject.utils.ImageLoad;
import com.jiajie.jiajieproject.utils.ToastUtil;
import com.jiajie.jiajieproject.widget.MyAddAndSubView;
import com.jiajie.jiajieproject.widget.MyAddAndSubView.OnClickAddAndSubListener;
import com.mrwujay.cascade.model.produceClass;

/**
 * 项目名称：NewProject 类名称：CartsClassAdapter 类描述： 创建人：王蕾 创建时间：2015-9-17 下午5:08:41
 * 修改备注：购物车适配器
 */
@SuppressWarnings("unused")
public class CarShopppingAdapter extends BaseAdapter implements
		OnCheckedChangeListener, OnClickListener {
	private Activity activity;
	private ArrayList<produceClass> list = new ArrayList<produceClass>();
	private static HashMap<Integer, Boolean> isSelected;
	private Handler mHandler;
	private ImageLoad imageLoad;
	@SuppressLint("UseSparseArrays")
	public CarShopppingAdapter(Activity activity, Handler mHandler,
			ImageLoad imageLoad) {
		this.activity = activity;
		this.mHandler = mHandler;
		this.imageLoad = imageLoad;
		isSelected = new HashMap<Integer, Boolean>();

	}

	// 初始化isSelected的数据
	private void initDate(ArrayList<produceClass> list) {
	
			for (int i = 0; i < list.size(); i++) {
				getIsSelected().put(i, false);
			}
		
	}

	public static HashMap<Integer, Boolean> getIsSelected() {
		return isSelected;
	}

	public static void setIsSelected(HashMap<Integer, Boolean> isSelected) {
		CarShopppingAdapter.isSelected = isSelected;
	}

	public ArrayList<produceClass> setData(ArrayList<produceClass> list) {
		initDate(list);
		return this.list = list;

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
			// vh.bt01 = (ImageView) convertView.findViewById(R.id.addbt);
			// vh.bt02 = (ImageView) convertView.findViewById(R.id.subbt);
			// vh.edt = (EditText) convertView.findViewById(R.id.edt);
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
		// vh.amount_view.setOnAmountChangeListener(new AmountViewListener());
		// produceClass produceClass = list.get(position);
		// imageLoad.loadImg(vh.imgeView2, produceClass.image,
		// R.drawable.jiazaitupian);
		// vh.bt01.setTag("+");
		// vh.bt02.setTag("-");
		// vh.bt01.setTag(R.id.addbt, position);
		// vh.bt02.setTag(R.id.addbt, position);
		// vh.edt.setInputType(android.text.InputType.TYPE_CLASS_NUMBER);
		// vh.edt.setFocusable(false);
		// vh.edt.setEnabled(false);
		// // vh.edt.setText(list.get(position).qty + "");
		// vh.edt.setTag(position);
		// setViewListener(vh, activity);
		// vh.imgeView1.setTag(position);

		vh.imgeView1.setOnCheckedChangeListener(this);
		vh.imgeView1.setChecked(getIsSelected().get(position));
		vh.numbertext.setText("¥"
				+ list.get(position).price.substring(0,
						list.get(position).price.lastIndexOf('.')) + ".00");
		vh.pricetext.setText(list.get(position).productName);
		vh.my_add_sub.setCount(list.get(position).qty);
		vh.my_add_sub.setOnClickListener(this);
		vh.my_add_sub.setTag(position);
		imageLoad.loadImg(vh.imgeView2, list.get(position).image,
				R.drawable.jiazaitupian);
		return convertView;
	}

	class ViewHolder {
		CheckBox imgeView1;
		ImageView imgeView2, bt01, bt02;
		MyAddAndSubView my_add_sub;
		TextView pricetext, catshoppingitem_layouttext1, numbertext;
		EditText edt;

	}

	/**
	 * 加减按钮事件监听器
	 * 
	 * 
	 */
	// class OnButtonClickListener implements OnClickListener, TextWatcher {
	// private EditText edt;
	// private Activity mActivity;
	// int num = 0;
	//
	// public OnButtonClickListener(EditText edt, Activity mActivity) {
	// super();
	// this.edt = edt;
	//
	// this.mActivity = mActivity;
	// // edt.setText(String.valueOf(num));
	// }

	// @Override
	// public void onClick(View v) {
	// int position = (Integer) v.getTag(R.id.addbt);
	// String numString = edt.getText().toString();
	// num = Integer.parseInt(numString);
	// if (numString == null || numString.equals("")) {
	// num = 0;
	// edt.setText("0");
	// } else {
	// if (v.getTag().equals("-")) {
	// if (++num < 0) // 先加，再判断
	// {
	// num--;
	//
	// Toast.makeText(mActivity, "请输入一个大于0的数字",
	// Toast.LENGTH_SHORT).show();
	// } else {
	// Message message = mHandler.obtainMessage();
	// message.arg1 = num;
	// message.arg2 = position;
	// message.what = 11;
	// mHandler.sendMessage(message);
	// edt.setText(String.valueOf(num));
	// list.get((Integer) edt.getTag()).qty = num + "";
	// }
	// } else if (v.getTag().equals("+")) {
	// if (--num < 0) // 先减，再判断
	// {
	// num++;
	//
	// Toast.makeText(mActivity, "请输入一个大于0的数字",
	// Toast.LENGTH_SHORT).show();
	// } else {
	// Message message = mHandler.obtainMessage();
	// message.arg1 = num;
	// message.arg2 = position;
	// message.what = 11;
	// mHandler.sendMessage(message);
	// edt.setText(String.valueOf(num));
	// list.get((Integer) edt.getTag()).qty = num + "";
	//
	// }
	// }
	// }
	//
	// }

	// @Override
	// public void afterTextChanged(Editable s) {
	// String numString = s.toString();
	// if (numString == null || numString.equals("")) {
	// num = 0;
	//
	// } else {
	// int numInt = Integer.parseInt(numString);
	// if (numInt < 0) {
	// Toast.makeText(mActivity, "请输入一个大于0的数字", Toast.LENGTH_SHORT)
	// .show();
	// } else {
	//
	// // 设置EditText光标位置 为文本末端
	// edt.setSelection(edt.getText().toString().length());
	// num = numInt;
	//
	// }
	// }
	// }
	//
	// @Override
	// public void beforeTextChanged(CharSequence s, int start, int count,
	// int after) {
	// // TODO Auto-generated method stub
	//
	// }
	//
	// @Override
	// public void onTextChanged(CharSequence s, int start, int before,
	// int count) {
	// // TODO Auto-generated method stub
	//
	// }
	// }

	/**
	 * 设置文本变化相关监听事件
	 */
	// private void setViewListener(ViewHolder vh, Activity mActivity) {
	// OnButtonClickListener onButtonClickListener = new OnButtonClickListener(
	// vh.edt, mActivity);
	// vh.bt01.setOnClickListener(onButtonClickListener);
	// vh.bt02.setOnClickListener(onButtonClickListener);
	// vh.edt.addTextChangedListener(onButtonClickListener);
	// }

	// @Override
	// public void onClick(View v) {
	// switch (v.getId()) {
	// case R.id.
	// :
	// // MediaImageView imge = (MediaImageView) v;
	// // int position = (Integer) v.getTag();
	// //// list.get(position).isChoosed = imge.isChecked();
	// // getIsSelected().put(position, imge.isChecked());
	// // // 如果所有的物品全部被选中，则全选按钮也默认被选中
	// // mHandler.sendMessage(mHandler.obtainMessage(9, isAllSelected()));
	// break;
	//
	// default:
	// break;
	// }
	//
	// }

	/**
	 * 判断是否购物车中所有的商品全部被选中
	 * 
	 * @return true所有条目全部被选中 false还有条目没有被选中
	 */
	private boolean isAllSelected() {
		boolean flag = true;
		for (int i = 0; i < list.size(); i++) {
			if (!getIsSelected().get(i)) {
				flag = false;
				break;
			}
		}
		return flag;
	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		switch (buttonView.getId()) {
		case R.id.imageView1:
			CheckBox imge = (CheckBox) buttonView;
			int position = (Integer) buttonView.getTag();
			list.get(position).isChoosed = imge.isChecked();
			getIsSelected().put(position, imge.isChecked());
			// 如果所有的物品全部被选中，则全选按钮也默认被选中
			if (isAllSelected()) {
				mHandler.sendMessage(mHandler.obtainMessage(9, true));
			}
			break;

		default:
			break;
		}

	}

	@Override
	public void onClick(View v) {
		MyAddAndSubView view = (MyAddAndSubView) v;
		Message message = mHandler.obtainMessage();
		message.arg1 = view.getCount();
		message.arg2 = Integer.parseInt(view.getTag().toString());
		message.what = 11;
		mHandler.sendMessage(message);

	}

}
