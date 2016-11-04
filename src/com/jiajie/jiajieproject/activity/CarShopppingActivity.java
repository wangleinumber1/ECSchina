package com.jiajie.jiajieproject.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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
	public static Map<String, String> isSelected = new HashMap<String, String>();
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
	private SharePreferDB sharePreferDB;

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
		sharePreferDB = new SharePreferDB(mContext, "carshopping.db");
		carShopppingAdapter = new CarShopppingAdapter(mActivity, myHandler,
				mImgLoad, sharePreferDB);
		carshopping_layoutlistview.setAdapter(carShopppingAdapter);
		headerRightImg.setOnClickListener(this);
		movetocare.setOnClickListener(this);
		car_delete.setOnClickListener(this);
		balance.setOnClickListener(this);
		headerRightImg.setOnCheckedChangeListener(this);
		carshopping_layoutlistview.setMenuCreator(this);
		// 右侧icon点击事件
		carshopping_layoutlistview.setOnMenuItemClickListener(this);
		allselect.setOnCheckedChangeListener(this);

	}

	// 无数据状态
	private void backInitView() {
		// backViewListener bViewListener=new backViewListener();
		setContentView(R.layout.konglayout);
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

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
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
				return;
			}

			if (jsonservice.getToastMessage()) {
				OnlyClass onlyClass = (OnlyClass) result;
				ToastUtil.showToast(mActivity, onlyClass.data);
				// 显示无数据的背景
				backLayout.setVisibility(View.VISIBLE);
				backInitView();
			}

			if (jsonservice.getsuccessState()) {
				list = (ArrayList<produceClass>) result;
				if (list.size() > 0) {
					backLayout.setVisibility(View.GONE);
					sharePreferDB.saveData(isSelected);
					carShopppingAdapter.setData(list);
					carShopppingAdapter.notifyDataSetChanged();
					SomeMessage jsonMessage = jsonservice.getsomemessage();
					tatalprice = jsonMessage.total_price.substring(0,
							jsonMessage.total_price.lastIndexOf('.')) + ".00";
					// carshopping_price.setText(
					// jsonMessage.total_price.substring(0,
					// jsonMessage.total_price.lastIndexOf('.'))
					// + ".00");
					// catshoppingtext2.setText("商品数量：        "
					// + jsonMessage.count + "件");
				
				} else {
					backLayout.setVisibility(View.VISIBLE);
				}

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
			myHandler.sendEmptyMessage(10);
			break;
		// 删除
		case 1:

			Message message1 = myHandler.obtainMessage(2);
			message1.arg1 = position;
			myHandler.sendMessage(message1);
			myHandler.sendEmptyMessage(10);
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

				boolean isallselect = (Boolean) msg.obj;
				if (isallselect) {
					flag = true;
					allselect.setChecked(flag);
//					carshopping_price.setText(tatalprice);
				} else {
					flag = false;
					allselect.setChecked(flag);
//					carshopping_price.setText("0.00");
				}
				
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
				myHandler.sendEmptyMessage(10);
				break;
			case 2:
				// 删除单个
				HashMap<String, String> deletemap = new HashMap<String, String>();
				deletemap.put("id", list.get(msg.arg1).id);
				new AddRemoveCarsDeleteAsyTask(deletemap,
						InterfaceParams.deleteCart).execute();
				carShopppingAdapter.notifyDataSetChanged();
				myHandler.sendEmptyMessage(10);
				break;
			case 10:
				// 刷新
				sharePreferDB.saveData(isSelected);
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
					carShopppingAdapter.notifyDataSetChanged();
					myHandler.sendEmptyMessage(10);
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
					carShopppingAdapter.notifyDataSetChanged();
					myHandler.sendEmptyMessage(8);
					myHandler.sendEmptyMessage(10);
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
				new UpdateAsyTask(map, InterfaceParams.updateCart).execute();
				myHandler.sendEmptyMessage(10);

				break;
			case 6:
				// 结算全部
				Map map1 = new HashMap<String, String>();

				map1.put("update_cart_action", "update_qty");
				map1.put("cart", JsonBySelf());
				new UpdateAsyTask(map1, InterfaceParams.updateCart).execute();
				myHandler.sendEmptyMessage(12);
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
				//每次点击勾选涮新一次总价
//				sendEmptyMessage(10);
				ArrayList<produceClass> newlist = CarShopppingActivity.getselectedList(
						CarShopppingActivity.isSelected, list);
				Double countprice = 0.0000;
				for (int i = 0; i < newlist.size(); i++) {
					// 产品数量
//					int qty = Integer.parseInt(newlist.get(i).qty);
//					int price = Integer.parseInt(list.get(position).price.substring(0,
//							list.get(position).price.lastIndexOf('.')));
					
					
					ToastUtil.showToast(mContext, newlist.get(i)+"");
					
					countprice = countprice + Double.parseDouble( newlist.get(i).total_price);
				}
//				ToastUtil.showToast(mContext, countprice+"");
				carshopping_price.setText(countprice+"");
				
				break;

			default:
				break;
			}
		}

	}
	
	// // 全选或全取消
	@SuppressWarnings("static-access")
	private void selectedAll() {
		for (int i = 0; i < list.size(); i++) {
			isSelected.put(list.get(i).id, flag + "");
		}
		carShopppingAdapter.notifyDataSetChanged();
	}

	/**
	 * 自己封装json
	 * */
	@SuppressWarnings({ "unused", "static-access" })
	private String JsonBySelf() {

		ArrayList<produceClass> list = carShopppingAdapter.getData();
		// StringBuilder builder = new StringBuilder();
		String message = "{";
		for (int i = 0; i < list.size(); i++) {
			produceClass produceClass = list.get(i);
			// if (carShopppingAdapter.getIsSelected().get(i)) {

			if (list.size() > 1) {
				message = message + "\"" + produceClass.id.toString() + "\""
						+ ":" + "{\"" + "qty" + "\"" + ":" + "\""
						+ produceClass.qty + "\"" + "},";
			} else {
				message = message + "\"" + produceClass.id.toString() + "\""
						+ ":" + "{\"" + "qty" + "\"" + ":" + "\""
						+ produceClass.qty + "\"" + "}";
			}

			// }
		}
		// ToastUtil.showToast(mActivity, message);
		if (list.size() < 2) {
			return message + "}";
		} else {
			message = message.substring(0, message.length() - 1) + "}";
			return message;
		}

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
		if (allselect.isChecked()) {
			flag = true;
			selectedAll();
			// int count = selected(list);
		
		} else {
			flag = false;
			selectedAll();
		
		}

	}

	// 获取总价
	@SuppressWarnings("unused")
	private int selected(ArrayList<produceClass> list) {
		int count = 0;
		for (int i = 0; i < list.size(); i++) {
			// 产品数量

			int num = Integer.parseInt(list.get(i).qty);
			int price = Integer.parseInt(list.get(i).price);
			count = count + num * price;
		}

		return count;

	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		sharePreferDB.deletePreference();
	}

	// 将勾选的的产品重新放进一个list
	@SuppressWarnings("unused")
	public static ArrayList<produceClass> getselectedList(Map<String, String> maps,
			ArrayList<produceClass> list) {
		ArrayList<produceClass> newlist = new ArrayList<produceClass>();
		for (Map.Entry<String, String> map : maps.entrySet()) {
			String key = map.getKey();
			// 遍历list找到对应ID的值
			for (int i = 0; i < list.size(); i++) {
				if (list.get(i).isChoosed) {
					newlist.add(list.get(i));
				}
			}

		}

		return newlist;

	}

}
