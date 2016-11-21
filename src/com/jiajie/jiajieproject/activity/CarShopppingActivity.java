package com.jiajie.jiajieproject.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.R.integer;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.TypedValue;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import cn.jpush.android.api.m;

import com.alibaba.fastjson.JSONArray;
import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.baoyz.swipemenulistview.SwipeMenuListView.OnMenuItemClickListener;
import com.baoyz.swipemenulistview.SwipeMenuListView.OnSwipeListener;
import com.jiajie.jiajieproject.MainActivity;
import com.jiajie.jiajieproject.R;
import com.jiajie.jiajieproject.adapter.CarShopppingAdapter;
import com.jiajie.jiajieproject.contents.Constants;
import com.jiajie.jiajieproject.contents.InterfaceParams;
import com.jiajie.jiajieproject.contents.ReciverContents;
import com.jiajie.jiajieproject.db.service.SharePreferDB;
import com.jiajie.jiajieproject.model.OnlyClass;
import com.jiajie.jiajieproject.utils.IntentUtil;
import com.jiajie.jiajieproject.utils.ToastUtil;
import com.jiajie.jiajieproject.utils.YokaLog;
import com.mrwujay.cascade.model.SomeMessage;
import com.mrwujay.cascade.model.produceClass;

/**
 * 项目名称：NewProject 类名称：CatShopppingActivity 类描述： 创建人：王蕾 创建时间：2015-10-12
 * 下午3:19:35 修改备注： 购物车列表
 */
public class CarShopppingActivity extends BaseActivity implements
		OnClickListener, SwipeMenuCreator, OnMenuItemClickListener,
		OnCheckedChangeListener {
	private ToggleButton headerRightImg;
	private boolean flag = false;
	private CarShopppingAdapter carShopppingAdapter;
	private RelativeLayout backLayout;
	private MyHandler myHandler = new MyHandler();
	private SwipeMenuListView carshopping_layoutlistview;
	// id和个数
	private String message;

	// 所有物品
	private ArrayList<produceClass> list;
	// 底部
	private RelativeLayout BottomLayout1, BottomLayout2;
	private CheckBox allselect;
	private ImageView movetocare, car_delete, balance;
	private Button carshopping_price;
	// 选中的商品
	private ArrayList<produceClass> selectedproduce = new ArrayList<produceClass>();
	private String tatalprice;
	// private SharePreferDB sharePreferDB;
	public static String TAG = "CarShopppingActivity";
	private int countprice;
	@Override
	protected void onInit(Bundle bundle) {
		// TODO Auto-generated method stub
		super.onInit(bundle);
		InitView();
		// backInitView();
	}

	private void InitView() {
		setContentView(R.layout.carshopping_layout);
		BottomLayout1 = (RelativeLayout) findViewById(R.id.BottomLayout1);
		BottomLayout2 = (RelativeLayout) findViewById(R.id.BottomLayout2);
		allselect = (CheckBox) findViewById(R.id.allselect);
		movetocare = (ImageView) findViewById(R.id.movetocare);
		car_delete = (ImageView) findViewById(R.id.car_delete);
		balance = (ImageView) findViewById(R.id.balance);
		carshopping_price = (Button) findViewById(R.id.carshopping_price);
		headerRightImg = (ToggleButton) findViewById(R.id.headerRightImg);
		backLayout = (RelativeLayout) findViewById(R.id.backLayout);
		carshopping_layoutlistview = (SwipeMenuListView) findViewById(R.id.carshopping_layoutlistview);
		// sharePreferDB = new SharePreferDB(mContext, "carshopping.db");
		
		headerRightImg.setOnClickListener(this);
		movetocare.setOnClickListener(this);
		car_delete.setOnClickListener(this);
		balance.setOnClickListener(this);
		headerRightImg.setOnCheckedChangeListener(this);
		carshopping_layoutlistview.setMenuCreator(this);
		// 右侧icon点击事件
		carshopping_layoutlistview.setOnMenuItemClickListener(this);
		allselect.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if (allselect.isChecked()) {
					flag = true;
					selectedAll();
					selectedGetPrice();
				} else {
					flag = false;
					selectedAll();
					selectedGetPrice();
				}
				
			}
		});

	}

	// 无数据状态
	private void backInitView() {
		// backViewListener bViewListener=new backViewListener();
		findViewById(R.id.head).setVisibility(View.GONE);
		findViewById(R.id.kongback).setOnClickListener(this);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.headerleftImg:
			finish();
			break;
		case R.id.car_delete:
			// 全删
			myHandler.sendEmptyMessage(8);
			break;
		case R.id.movetocare:
			// 全部关注
			myHandler.sendEmptyMessage(7);
			myHandler.sendEmptyMessage(8);
			break;
		case R.id.balance:
			// 结算
			myHandler.sendEmptyMessage(6);

			break;
		case R.id.kongback:
			// 发送广播改变底部的tab位置
			Intent intent = new Intent();
			intent.setAction(ReciverContents.cityListReciver);
			CarShopppingActivity.this.sendBroadcast(intent);

			break;

		}

	}

	@SuppressWarnings("unchecked")
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		carShopppingAdapter = new CarShopppingAdapter(mActivity, myHandler,
				mImgLoad);
		// carShopppingAdapter = new CarShopppingAdapter(mActivity, myHandler,
		// mImgLoad);
		carshopping_layoutlistview.setAdapter(carShopppingAdapter);
		new CarsAsyTask().execute();
	}

	@Override
	public void create(SwipeMenu menu) {
		SwipeMenuItem careItem = new SwipeMenuItem(getApplicationContext());
		// set item background
		careItem.setBackground(new ColorDrawable(Color.rgb(0x99, 0x99, 0x99)));
		// set item width
		careItem.setWidth(dp2px(74));
		// set item title font color
		careItem.setIcon(R.drawable.move_care);

		// add to menu
		menu.addMenuItem(careItem);
		SwipeMenuItem deleteItem = new SwipeMenuItem(getApplicationContext());
		// set item background
		deleteItem
				.setBackground(new ColorDrawable(Color.rgb(0xE9, 0x45, 0x16)));
		// set item width
		deleteItem.setWidth(dp2px(74));
		// set a icon
		deleteItem.setIcon(R.drawable.carshopping_delete);
		// add to menu
		menu.addMenuItem(deleteItem);

	}

	/**
	 * 购物车列表
	 * */
	@SuppressWarnings("unused")
	private class CarsAsyTask extends MyAsyncTask {

		@SuppressWarnings("unchecked")
		@Override
		protected Object doInBackground(Object... params) {
			return jsonservice.getCarDataList(InterfaceParams.cart, null,
					false, produceClass.class);
		}

		@SuppressWarnings("unchecked")
		@Override
		protected void onPostExecute(Object result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if (result == null) {
				backInitView();
				backLayout.setVisibility(View.VISIBLE);

			} else {
				list = (ArrayList<produceClass>) result;
				backLayout.setVisibility(View.GONE);
				// sharePreferDB.saveData(isSelected);
				carShopppingAdapter.clearData();
				carShopppingAdapter.setData(list);
				carShopppingAdapter.notifyDataSetChanged();
				SomeMessage jsonMessage = jsonservice.getsomemessage();
				// tatalprice = jsonMessage.total_price.substring(0,
				// jsonMessage.total_price.lastIndexOf('.')) + ".00";
				// carshopping_price.setText(
				// jsonMessage.total_price.substring(0,
				// jsonMessage.total_price.lastIndexOf('.'))
				// + ".00");
				// catshoppingtext2.setText("商品数量：        "
				// + jsonMessage.count + "件");
				selectedGetPrice();
			}

		}

	}

	/**
	 * 移除购物车 添加 结算
	 * */
	@SuppressWarnings({ "unused", "rawtypes" })
	private class AddRemoveCarsDeleteAsyTask extends AsyncTask {
		private String interfacename;
		private Map map;

		public AddRemoveCarsDeleteAsyTask(Map map, String interfacename) {
			super();
			this.map = map;
			this.interfacename = interfacename;

		}

		@SuppressWarnings("unchecked")
		@Override
		protected Object doInBackground(Object... params) {
			return jsonservice.getData(interfacename, map, false, null);
		}

		@SuppressWarnings("unchecked")
		@Override
		protected void onPostExecute(Object result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if (result == null) {
				return;
			}

			if (jsonservice.getToastMessage()) {
				OnlyClass onlyClass = (OnlyClass) result;
				if (onlyClass.success) {

				}
				ToastUtil.showToast(mActivity, onlyClass.data);
			}

		}

	}

	/**
	 * 结算
	 * */
	@SuppressWarnings("unused")
	private class UpdateAsyTask extends AsyncTask {
		private String interfacename;
		private Map map;

		public UpdateAsyTask(Map map, String interfacename) {
			super();
			this.map = map;
			this.interfacename = interfacename;

		}

		@SuppressWarnings("unchecked")
		@Override
		protected Object doInBackground(Object... params) {
			return jsonservice.getData(interfacename, map, false, null);
		}

		@SuppressWarnings("unchecked")
		@Override
		protected void onPostExecute(Object result) {
			// TODO Auto-generated method
			super.onPostExecute(result);
			if (result == null) {
				return;
			}

			if (jsonservice.getToastMessage()) {
				OnlyClass onlyClass = (OnlyClass) result;
				if (onlyClass.success) {

				}
				ToastUtil.showToast(mActivity, onlyClass.data);
			}

		}

	}

	private int dp2px(int dp) {
		return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
				getResources().getDisplayMetrics());
	}

	@SuppressWarnings("unchecked")
	@Override
	public void onMenuItemClick(int position, SwipeMenu menu, int index) {
		switch (index) {
		// 移入关注
		case 0:
			Message message = myHandler.obtainMessage(1);
			message.arg1 = position;
			myHandler.sendMessage(message);
			
			break;
		// 删除
		case 1:
			Message message1 = myHandler.obtainMessage(2);
			message1.arg1 = position;
			myHandler.sendMessage(message1);
			
			break;

		default:
			break;
		}
		// 删除

	}

	private class MyHandler extends Handler {

		@SuppressWarnings({ "unchecked", "static-access" })
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 9:

				flag = (Boolean) msg.obj;
				allselect.setChecked(flag);
				break;
			case 1:
				// 移入关注
				HashMap<String, String> caremap = new HashMap<String, String>();
				caremap.put("id", list.get(msg.arg1).productId);
				new AddRemoveCarsDeleteAsyTask(caremap,
						InterfaceParams.addWishList).execute();
				carShopppingAdapter.notifyDataSetChanged();
				Message message1 = myHandler.obtainMessage(2);
				message1.arg1 = msg.arg1;
				myHandler.sendMessage(message1);
			
				break;
			case 2:
				// 删除单个
				HashMap<String, String> deletemap = new HashMap<String, String>();
				deletemap.put("id", list.get(msg.arg1).id);
				new AddRemoveCarsDeleteAsyTask(deletemap,
						InterfaceParams.deleteCart).execute();
				carShopppingAdapter.notifyDataSetChanged();
				carShopppingAdapter.getData().remove(msg.arg1);
				CarShopppingAdapter.isSelected.remove(msg.arg1);
				carShopppingAdapter.notifyDataSetChanged();
				break;
			case 10:
				// // 刷新
				// sharePreferDB.deletePreference();
				// sharePreferDB.saveData(isSelected);
				new CarsAsyTask().execute();
				break;
			case 8:
				// 全部删除
				if (flag) {
					Map map2 = new HashMap<String, String>();
					String message = "";
					for (int i = 0; i < list.size(); i++) {
						message = message + list.get(i).productId + ",";
					}
					message = message.substring(0, message.length() - 1);
					map2.put("id", message);
					new AddRemoveCarsDeleteAsyTask(map2,
							InterfaceParams.deleteCart).execute();
					carShopppingAdapter.clearData();
					carShopppingAdapter.notifyDataSetChanged();
					
				}
				break;
			case 7:
				// 全部移入关注
				if (flag) {
					HashMap<String, String> caremap1 = new HashMap<String, String>();
					String message = "";
					for (int i = 0; i < list.size(); i++) {
						message = message + list.get(i).productId + ",";
					}
					message = message.substring(0, message.length() - 1);
					caremap1.put("id", message);
					new AddRemoveCarsDeleteAsyTask(caremap1,
							InterfaceParams.addWishList).execute();
					myHandler.sendEmptyMessage(8);
					
				}
				break;
			case 11:
				// 提交个数
				Map map = new HashMap<String, String>();
				String message = "{" + "\"" + list.get(msg.arg2).id.toString()
						+ "\"" + ":" + "{\"" + "qty" + "\"" + ":" + "\""
						+ msg.arg1 + "\"" + "}}";
				map.put("update_cart_action", "update_qty");
				map.put("cart", message);
				int price=(Integer) msg.obj;
				
				new UpdateAsyTask(map, InterfaceParams.updateCart).execute();

				// mHandler.sendEmptyMessage(13);
				break;
			case 6:
				// 结算全部
				// Map map1 = new HashMap<String, String>();
				// map1.put("update_cart_action", "update_qty");
				// map1.put("cart", JsonBySelf());
				// new UpdateAsyTask(map1,
				// InterfaceParams.updateCart).execute();
				// myHandler.sendEmptyMessage(12);

				OrdercoConfirmationActivity.newlist = getselectedList(list);
				IntentUtil.activityForward(mActivity,
						OrdercoConfirmationActivity.class, null, false);
				break;
			case 12:
				// 清空
				Map map2 = new HashMap<String, String>();
				map2.put("update_cart_action", "empty_cart");
				new AddRemoveCarsDeleteAsyTask(map2, InterfaceParams.updateCart)
						.execute();
				carShopppingAdapter.clearData();
				carShopppingAdapter.notifyDataSetChanged();

				break;
			case 13:
				// 每次点击勾选涮新一次总价
				// sendEmptyMessage(10);
				selectedGetPrice();

				break;

			}

		}
	}

	// // 全选或全取消
	@SuppressWarnings("static-access")
	private void selectedAll() {
		for (int i = 0; i < list.size(); i++) {
			produceClass produceClass = list.get(i);
			produceClass.isChoosed = flag;
			CarShopppingAdapter.isSelected.put(i, produceClass);
		}
		carShopppingAdapter.notifyDataSetChanged();
	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		if (headerRightImg.isChecked()) {
			BottomLayout1.setVisibility(View.GONE);
			BottomLayout2.setVisibility(View.VISIBLE);
		} else {
			BottomLayout2.setVisibility(View.GONE);
			BottomLayout1.setVisibility(View.VISIBLE);
		}
	

	}

	// 将勾选的的产品重新放进一个list
	@SuppressWarnings("unused")
	public ArrayList<produceClass> getselectedList(ArrayList<produceClass> list) {
		ArrayList<produceClass> newlist = new ArrayList<produceClass>();
		// 遍历list找到对应ID的值
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).isChoosed) {
				newlist.add(list.get(i));
			}
		}

		return newlist;

	}

	/**
	 * 通过选择的商品获取总价
	 * */
	private void selectedGetPrice() {
		ArrayList<produceClass> newlist = getselectedList(list);
		int countprice = 0;
		for (int i = 0; i < newlist.size(); i++) {
			// String key = maps.getKey();
			// 遍历list找到对应ID的值
			produceClass produceClass = newlist.get(i);
			// YokaLog.e(TAG, produceClass.toString()
			// + "00000000000000000000");
			countprice = countprice
					+ Integer.parseInt(produceClass.total_price.substring(0,
							produceClass.total_price.lastIndexOf('.')));
		}
		YokaLog.e(TAG, "list.size()=" + list.size() + "newlist.size()="
				+ newlist.size() + "isSelected.size()="
				+ CarShopppingAdapter.isSelected.size());
		carshopping_price.setText(countprice + ".00");
	}

}
