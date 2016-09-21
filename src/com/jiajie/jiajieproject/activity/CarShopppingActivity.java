package com.jiajie.jiajieproject.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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
import com.jiajie.jiajieproject.model.OnlyClass;
import com.jiajie.jiajieproject.utils.IntentUtil;
import com.jiajie.jiajieproject.utils.ToastUtil;
import com.mrwujay.cascade.model.SomeMessage;
import com.mrwujay.cascade.model.produceClass;

/**
 * 项目名称：NewProject 类名称：CatShopppingActivity 类描述： 创建人：王蕾 创建时间：2015-10-12
 * 下午3:19:35 修改备注： 购物车列表
 */
public class CarShopppingActivity extends BaseActivity implements
		OnClickListener, SwipeMenuCreator, OnMenuItemClickListener,
		OnSwipeListener, OnCheckedChangeListener {
	private ToggleButton headerRightImg;
	private boolean flag = true;
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
		headerRightImg = (ToggleButton) findViewById(R.id.headerRightImg);
		backLayout = (RelativeLayout) findViewById(R.id.backLayout);
		carshopping_layoutlistview = (SwipeMenuListView) findViewById(R.id.carshopping_layoutlistview);
		carShopppingAdapter = new CarShopppingAdapter(mActivity, myHandler,
				mImgLoad);
		carshopping_layoutlistview.setAdapter(carShopppingAdapter);
		headerRightImg.setOnClickListener(this);
		movetocare.setOnClickListener(this);
		car_delete.setOnClickListener(this);
		balance.setOnClickListener(this);
		headerRightImg.setOnCheckedChangeListener(this);
		carshopping_layoutlistview.setMenuCreator(this);
		carshopping_layoutlistview.setOnMenuItemClickListener(this);
		carshopping_layoutlistview.setOnSwipeListener(this);
		allselect.setOnCheckedChangeListener(this);
	}

	// 无数据状态
	// private void backInitView(){
	// backViewListener bViewListener=new backViewListener();
	// setContentView(R.layout.konglayout);
	// findViewById(R.id.headerleftImg).setOnClickListener(bViewListener);
	// findViewById(R.id.text4).setOnClickListener(bViewListener);
	// }

	@Override
	protected void onStart() {
		super.onStart();
		// new CarsAsyTask().execute();
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.headerleftImg:
			finish();
			break;
		case R.id.balance:
			IntentUtil.activityForward(mActivity,
					OrderInformationActivity.class, null, false);
			break;
		case R.id.partbutton:
			// Bundle bundle=new Bundle();
			// bundle.putBoolean("isFromBusiness", true);
			Constants.isfromcarshoping = true;
			IntentUtil.activityForward(this, MainActivity.class, null, true);
			break;

		}

	}

	@Override
	public void create(SwipeMenu menu) {
		SwipeMenuItem openItem = new SwipeMenuItem(getApplicationContext());
		// set item background
		openItem.setBackground(new ColorDrawable(Color.rgb(0x99, 0x99, 0x99)));
		// set item width
		openItem.setWidth(dp2px(74));
		// set item title font color
		openItem.setIcon(R.drawable.move_care);

		// add to menu
		menu.addMenuItem(openItem);
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

		public CarsAsyTask() {
			super();
		}

		@SuppressWarnings("unchecked")
		@Override
		protected Object doInBackground(Object... params) {
			return jsonservice.getCarDataList(InterfaceParams.cart, null,
					false, produceClass.class, myHandler);
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
				backLayout.setVisibility(View.VISIBLE);
				// backInitView();
			}

			if (jsonservice.getsuccessState()) {
				list = (ArrayList<produceClass>) result;
				if (list.size() > 0) {
					backLayout.setVisibility(View.GONE);

					carShopppingAdapter.setData(list);
					carShopppingAdapter.notifyDataSetChanged();
					SomeMessage jsonMessage = jsonservice.getsomemessage();
					// catshoppingtext1.setText("合计：¥"
					// + jsonMessage.total_price.substring(0,
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
	// @SuppressWarnings("unused")
	// private class AddRemoveCarsDeleteAsyTask extends MyAsyncTask {
	// private String interfacename;
	// private Map map;
	//
	// public AddRemoveCarsDeleteAsyTask(Map map, String interfacename) {
	// super();
	// this.map = map;
	// this.interfacename = interfacename;
	//
	// }
	//
	// @SuppressWarnings("unchecked")
	// @Override
	// protected Object doInBackground(Object... params) {
	// return jsonservice.getData(interfacename, map, false, null);
	// }
	//
	// @SuppressWarnings("unchecked")
	// @Override
	// protected void onPostExecute(Object result) {
	// // TODO Auto-generated method stub
	// super.onPostExecute(result);
	// if (result == null) {
	// return;
	// }
	//
	// if (jsonservice.getToastMessage()) {
	// OnlyClass onlyClass = (OnlyClass) result;
	// if (onlyClass.success) {
	//
	// myHandler.sendEmptyMessage(10);
	// }
	// ToastUtil.showToast(mActivity, onlyClass.data);
	// }
	//
	// }
	//
	// }

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
					// myHandler.sendEmptyMessage(8);
					IntentUtil.activityForward(mActivity,
							OrdercoConfirmationActivity.class, null, true);
				}
				ToastUtil.showToast(mActivity, onlyClass.data);
			}

		}

	}

	private int dp2px(int dp) {
		return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
				getResources().getDisplayMetrics());
	}

	@Override
	public void onMenuItemClick(int position, SwipeMenu menu, int index) {

		// 删除
		// Map map = new HashMap<String, String>();
		// map.put("id", list.get(index).id);
		// new AddRemoveCarsDeleteAsyTask(map, InterfaceParams.deleteCart)
		// .execute();
		// carShopppingAdapter.notifyDataSetChanged();
		// 删除后刷新数据

	}

	private class MyHandler extends Handler {

		@SuppressWarnings("unchecked")
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 9:
				// carShopppingAdapter.clearData();
				// carShopppingAdapter.notifyDataSetChanged();
				// // catshoppingtext1.setText("合计：¥" + 0);
				// // catshoppingtext2.setText("数量：        " + 0 + "个");
				// backLayout.setVisibility(View.VISIBLE);
				boolean isallselect = (Boolean) msg.obj;
				allselect.setChecked(isallselect);
				break;
			case 10:
				// 刷新
				// new CarsAsyTask().execute();
				break;
			// case 8:
			// Map map2 = new HashMap<String, String>();
			// map2.put("update_cart_action", "empty_cart");
			// new AddRemoveCarsDeleteAsyTask(map2, InterfaceParams.updateCart)
			// .execute();
			// carShopppingAdapter.clearData();
			// carShopppingAdapter.notifyDataSetChanged();
			// break;
			case 11:
				// 刷新
				// Map map = new HashMap<String, String>();
				// String message = "{" + "\"" +
				// list.get(msg.arg2).id.toString()
				// + "\"" + ":" + "{\"" + "qty" + "\"" + ":" + "\""
				// + msg.arg1 + "\"" + "}}";
				// map.put("update_cart_action", "update_qty");
				// map.put("cart", message);
				// new AddRemoveCarsDeleteAsyTask(map,
				// InterfaceParams.updateCart)
				// .execute();
				// sendEmptyMessage(10);
				break;

			default:
				break;
			}
		}

	}

	// // 全选或全取消
	private void selectedAll() {
		// for (int i = 0; i < list.size(); i++) {
		for (int i = 0; i < 10; i++) {
			carShopppingAdapter.getIsSelected().put(i, flag);
		}
		carShopppingAdapter.notifyDataSetChanged();
	}

	@Override
	public void onSwipeStart(int position) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onSwipeEnd(int position) {
		// TODO Auto-generated method stub

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
			selectedAll();
			flag = false;
		} else {

			selectedAll();
			flag = true;
		}

	}

}
