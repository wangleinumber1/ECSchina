package com.jiajie.jiajieproject.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.annotation.SuppressLint;
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
import android.widget.ToggleButton;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.baoyz.swipemenulistview.SwipeMenuListView.OnMenuItemClickListener;
import com.jiajie.jiajieproject.R;
import com.jiajie.jiajieproject.adapter.CarShopppingAdapter;
import com.jiajie.jiajieproject.contents.InterfaceParams;
import com.jiajie.jiajieproject.contents.ReciverContents;
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
@SuppressWarnings("unused")
public class CarShopppingActivity extends BaseActivity implements
		OnClickListener, SwipeMenuCreator, OnMenuItemClickListener,
		OnCheckedChangeListener {
	private ToggleButton headerRightImg;
	private boolean flag = false;
	private CarShopppingAdapter carShopppingAdapter;
	private RelativeLayout backLayout;
	private MyHandler myHandler = new MyHandler();
	private SwipeMenuListView carshopping_layoutlistview;
	// 所有物品
	private ArrayList<produceClass> list;
	// 底部
	private RelativeLayout BottomLayout1, BottomLayout2;
	private CheckBox allselect;
	private ImageView movetocare, car_delete, balance;
	private Button carshopping_price;
	// 选中的商品
	private ArrayList<produceClass> selectedproduce = new ArrayList<produceClass>();
	// private SharePreferDB sharePreferDB;
	public static String TAG = "CarShopppingActivity";

	@Override
	protected void onInit(Bundle bundle) {
		// TODO Auto-generated method stub
		super.onInit(bundle);
		InitView();
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
		headerRightImg.setOnClickListener(this);
		movetocare.setOnClickListener(this);
		car_delete.setOnClickListener(this);
		balance.setOnClickListener(this);
		headerRightImg.setOnCheckedChangeListener(this);
		carshopping_layoutlistview.setMenuCreator(this);
		// 右侧icon点击事件
		carshopping_layoutlistview.setOnMenuItemClickListener(this);
		allselect.setOnClickListener(this);

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
		case R.id.allselect:
			selectedAll();
			break;
		case R.id.headerleftImg:
			finish();
			break;
		case R.id.car_delete:
			if (!carShopppingAdapter.isclear) {
				ArrayList<produceClass> arraydelete = getselectedList(carShopppingAdapter
						.getData());
				Map map2 = new HashMap<String, String>();
				String message11 = "";
				for (int i = 0; i < arraydelete.size(); i++) {
					message11 = message11 + arraydelete.get(i).id + ",";
				}
				message11 = message11.substring(0, message11.length() - 1);
				map2.put("id", message11);
				new AddRemoveCarsDeleteAsyTask(map2, InterfaceParams.deleteCart)
						.execute();
				carShopppingAdapter.clearData();
				carShopppingAdapter.notifyDataSetChanged();
			}
			break;
		case R.id.movetocare:
			if (!carShopppingAdapter.isclear) {
				ArrayList<produceClass> arraycare = getselectedList(carShopppingAdapter
						.getData());
				HashMap<String, String> caremap1 = new HashMap<String, String>();
				String message = "";
				for (int i = 0; i < arraycare.size(); i++) {
					message = message + arraycare.get(i).productId + ",";
				}
				message = message.substring(0, message.length() - 1);
				caremap1.put("id", message);
				new AddRemoveCarsDeleteAsyTask(caremap1,
						InterfaceParams.addWishList).execute();
				// 关注后清空列表
				myHandler.sendEmptyMessage(7);
			}
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
				carShopppingAdapter.setData(list);
				carshopping_price.setText("0.00");
				carShopppingAdapter.notifyDataSetChanged();
			}

		}

	}

	/**
	 * 移除购物车 添加 结算
	 * */
	@SuppressWarnings({ "unused", "rawtypes" })
	private class AddRemoveCarsDeleteAsyTask extends MyAsyncTask {
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

				ToastUtil.showToast(mActivity, onlyClass.data);
			}

		}

	}

	/**
	 * 结算
	 * */
	@SuppressWarnings("unused")
	private class UpdateAsyTask extends MyAsyncTask {
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

	@SuppressLint("HandlerLeak")
	private class MyHandler extends Handler {
		@SuppressWarnings("unchecked")
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
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
				carShopppingAdapter.getData().remove(msg.arg1);
				flag = false;
				carshopping_price.setText("0.00");
				selectedAll(); // 删除商品后，取消所有的选择
				flag = true;
				carShopppingAdapter.notifyDataSetChanged();
				break;

			case 10:
				float price = (Float) msg.obj;

				carshopping_price.setText("" + price);

				break;
			case 11:
				// 所有列表中的商品全部被选中，让全选按钮也被选中
				// flag记录是否全被选中
				flag = !(Boolean) msg.obj;

				allselect.setChecked((Boolean) msg.obj);
				break;
			case 6:

				OrdercoConfirmationActivity.newlist = getselectedList(carShopppingAdapter
						.getData());
				if (OrdercoConfirmationActivity.newlist.size() > 0) {
					IntentUtil.activityForward(mActivity,
							OrdercoConfirmationActivity.class, null, false);
				} else {
					ToastUtil.showToast(mContext, "购物车没有任何货物");
					Intent intent = new Intent();
					intent.setAction(ReciverContents.cityListReciver);
					CarShopppingActivity.this.sendBroadcast(intent);
				}
				break;
			case 7:

				if (!carShopppingAdapter.isclear) {
					ArrayList<produceClass> arraydelete = getselectedList(carShopppingAdapter
							.getData());
					Map map2 = new HashMap<String, String>();
					String message11 = "";
					for (int i = 0; i < arraydelete.size(); i++) {
						message11 = message11 + arraydelete.get(i).id
								+ ",";
					}
					message11 = message11.substring(0, message11.length() - 1);
					map2.put("id", message11);
					new AddRemoveCarsDeleteAsyTask(map2,
							InterfaceParams.deleteCart).execute();
					carShopppingAdapter.clearData();
					carShopppingAdapter.notifyDataSetChanged();
				}
				break;

			}
		}

	}

	// // 全选或全取消

	@SuppressWarnings("static-access")
	private void selectedAll() {

		for (int i = 0; i < list.size(); i++) {
			carShopppingAdapter.getIsSelected().put(i, flag);
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

}
