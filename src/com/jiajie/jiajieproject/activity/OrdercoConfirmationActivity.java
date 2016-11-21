/**
 * 
 */
package com.jiajie.jiajieproject.activity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jiajie.jiajieproject.R;
import com.jiajie.jiajieproject.activity.BaseActivity.MyAsyncTask;
import com.jiajie.jiajieproject.adapter.CarShopppingAdapter;
import com.jiajie.jiajieproject.adapter.OrdercoConfirmationAdapter;
import com.jiajie.jiajieproject.adapter.PartsAdapter;
import com.jiajie.jiajieproject.contents.InterfaceParams;
import com.jiajie.jiajieproject.model.AdressClass;
import com.jiajie.jiajieproject.model.OnlyClass;
import com.jiajie.jiajieproject.utils.IntentUtil;
import com.jiajie.jiajieproject.utils.PullToRefreshView;
import com.jiajie.jiajieproject.utils.StringUtil;
import com.jiajie.jiajieproject.utils.YokaLog;
import com.jiajie.jiajieproject.utils.PullToRefreshView.OnFooterRefreshListener;
import com.jiajie.jiajieproject.utils.PullToRefreshView.OnHeaderRefreshListener;
import com.mrwujay.cascade.model.SomeMessage;
import com.mrwujay.cascade.model.produceClass;
import com.jiajie.jiajieproject.utils.ToastUtil;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * 项目名称：NewProject 类名称：OrdercoConfirmationActivity 类描述： 创建人：王蕾 创建时间：2015-10-14
 * 下午4:51:24 修改备注：订单确认
 */
@SuppressWarnings("unused")
public class OrdercoConfirmationActivity extends BaseActivity implements
		OnClickListener, OnCheckedChangeListener {
	// 地址
	private TextView headernametext, headernnmbertext, headeradresstext;
	private LinearLayout adress_layout;
	// private PullToRefreshView orderconfirmation_layoutpull;
	private ImageView headerleftImg;
	// 是否有默认地址
	private boolean isdefaultAdress = false;
	private String AdressId;
	// 产品列表
	private LinearLayout produce_list;
	// 列表图
	private ImageView imageView1, imageView2, imageView3;
	// 产品数量
	private TextView produceCount;

	private RadioGroup pay_radioGroup, fapiaoRadioGroup1, fapiaoRadioGroup2,
			fapiaoRadioGroup3;
	// 支付方式
	private RadioButton radioPay1, radioPay2, radioPay3;
	// 发票信息
	private RadioButton fapiaostyle1, fapiaostyle2;
	// 类型
	private RadioButton fapiaostyle3, fapiaostyle4;
	// 发票内容
	private RadioButton fapiaocontent1, fapiaocontent2;
	// 抬头
	private EditText company_edittext;
	// 提交订单
	ImageView send_order;
	// 总价
	Button pricecount;
	private String bill_type = "General Invoice";
	private String bill_content = "";
	private String bill_title = "Personal";
	private String bill_company;
	public static List<produceClass> newlist = new ArrayList<produceClass>();

	@Override
	protected void onInit(Bundle bundle) {
		// TODO Auto-generated method stub
		super.onInit(bundle);
		setContentView(R.layout.orderconfirmation_layout);
		InitView();

	}

	@SuppressWarnings("unchecked")
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		new GetDefaultAdressAsyTask().execute();

	}

	@SuppressWarnings("unchecked")
	private void InitView() {
		headerleftImg = (ImageView) findViewById(R.id.headerleftImg);

		send_order = (ImageView) findViewById(R.id.send_order);
		pricecount = (Button) findViewById(R.id.pricecount);
		headerleftImg.setOnClickListener(this);
		send_order.setOnClickListener(this);
		headView();
		middleView();
	}

	// 支付发票部分
	private void middleView() {
		produce_list = (LinearLayout) findViewById(R.id.produce_list);
		pay_radioGroup = (RadioGroup) findViewById(R.id.pay_radioGroup);
		company_edittext = (EditText) findViewById(R.id.company_edittext);
		fapiaoRadioGroup1 = (RadioGroup) findViewById(R.id.fapiaoRadioGroup1);
		fapiaoRadioGroup2 = (RadioGroup) findViewById(R.id.fapiaoRadioGroup2);
		fapiaoRadioGroup3 = (RadioGroup) findViewById(R.id.fapiaoRadioGroup3);
		radioPay1 = (RadioButton) findViewById(R.id.radioPay1);
		radioPay2 = (RadioButton) findViewById(R.id.radioPay2);
		radioPay3 = (RadioButton) findViewById(R.id.radioPay3);
		// 普通、专用
		fapiaostyle1 = (RadioButton) findViewById(R.id.fapiaostyle1);
		fapiaostyle2 = (RadioButton) findViewById(R.id.fapiaostyle2);
		// 个人、单位
		fapiaostyle3 = (RadioButton) findViewById(R.id.fapiaostyle3);
		fapiaostyle4 = (RadioButton) findViewById(R.id.fapiaostyle4);
		// 服务办公
		fapiaocontent1 = (RadioButton) findViewById(R.id.fapiaocontent1);
		fapiaocontent2 = (RadioButton) findViewById(R.id.fapiaocontent2);
		produceCount=(TextView) findViewById(R.id.produceCount);
		pay_radioGroup.setOnCheckedChangeListener(this);
		fapiaoRadioGroup1.setOnCheckedChangeListener(this);
		fapiaoRadioGroup2.setOnCheckedChangeListener(this);
		fapiaoRadioGroup3.setOnCheckedChangeListener(this);
		produce_list.setOnClickListener(this);
		produceCount.setText("共"+newlist.size()+"件");
	}

	private void headView() {
		headernametext = (TextView) findViewById(R.id.headernametext);
		headernnmbertext = (TextView) findViewById(R.id.headernnmbertext);
		headeradresstext = (TextView) findViewById(R.id.headeradresstext);
		adress_layout = (LinearLayout) findViewById(R.id.adress_layout);
		imageView1 = (ImageView) findViewById(R.id.imageView1);
		imageView2 = (ImageView) findViewById(R.id.imageView2);
		imageView3 = (ImageView) findViewById(R.id.imageView3);
		adress_layout.setOnClickListener(this);
		setIMage(newlist);
		
		int countprice = 0;
		for (int i = 0; i < newlist.size(); i++) {
			produceClass produceClass = newlist.get(i);
			countprice = countprice
					+ Integer.parseInt(produceClass.total_price.substring(0,
							produceClass.total_price.lastIndexOf('.')));
		}
		pricecount.setText(countprice + ".00");
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.headerleftImg:
			finish();
			break;
		case R.id.adress_layout:
			IntentUtil.activityForward(mActivity, AdressManageActivity.class,
					null, false);
			break;
		// 去掉发票功能
		case R.id.produce_list:

			IntentUtil.activityForward(mActivity, OrderListAcitivity.class,
					null, false);

			break;
		case R.id.send_order:
			if (isdefaultAdress) {
				if (fapiaostyle4.isChecked()) {
					if (!StringUtil.checkStr(company_edittext.getText()
							.toString())) {
						ToastUtil.showToast(mContext, "请填写发票抬头");
						return;
					}
				}
				// 提交订单
				@SuppressWarnings("rawtypes")
				Map map1 = new HashMap<String, String>();
				map1.put("address_id", AdressId);
				map1.put("fapiao[bill_type]", bill_type);
				map1.put("fapiao[bill_content]", bill_content);
				map1.put("fapiao[bill_title]", bill_title);
				map1.put("fapiao[bill_company]", company_edittext.getText()
						.toString());

				map1.put("products", JsonBySelf());
				String message = "";
				for (int i = 0; i < newlist.size(); i++) {
					message = message + newlist.get(i).id + ",";
				}
				message = message.substring(0, message.length() - 1);
				map1.put("cart_id", message);
				new UpdateAsyTask(map1, InterfaceParams.saveOrder).execute();

			} else {
				// 没有地址创建一个
				ToastUtil.showToast(mContext, "请增加一个默认收货地址");
				return;
			}

			break;

		default:
			break;
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
				CarShopppingAdapter.isSelected.clear();
				ToastUtil.showToast(mActivity, onlyClass.data);
			}

		}

	}
	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		switch (group.getId()) {

		case R.id.pay_radioGroup:
			switch (checkedId) {
			case R.id.radioPay1:
				ToastUtil.showToast(mContext, "支付宝");
				break;
			case R.id.radioPay2:
				ToastUtil.showToast(mContext, "威信");
				break;
			case R.id.radioPay3:
				ToastUtil.showToast(mContext, "对公");
				break;

			}
			break;
		case R.id.fapiaoRadioGroup1:
			switch (checkedId) {
			case R.id.fapiaostyle1:
				// 增值
				bill_type = "VAT Invoice";
				break;
			case R.id.fapiaostyle2:
				// 普通
				bill_type = "General Invoice";
				break;

			}
			break;

		case R.id.fapiaoRadioGroup2:
			switch (checkedId) {
			case R.id.fapiaostyle3:
				// 个人
				company_edittext.setVisibility(View.GONE);

				break;
			case R.id.fapiaostyle4:
				// 单位
				company_edittext.setVisibility(View.VISIBLE);
				bill_title = "Units";
				break;

			}
			break;

		case R.id.fapiaoRadioGroup3:
			switch (checkedId) {
			case R.id.fapiaocontent1:
				ToastUtil.showToast(mContext, "服务费");
				break;
			case R.id.fapiaocontent2:
				ToastUtil.showToast(mContext, "办公用品");
				break;

			}
			break;
		}
	}

	/**
	 * 获取默认地址
	 * */
	class GetDefaultAdressAsyTask extends MyAsyncTask {

		@SuppressWarnings("unchecked")
		@Override
		protected Object doInBackground(Object... params) {
			return jsonservice.getData(InterfaceParams.getDefaultAddress, null,
					false, AdressClass.class);
		}

		@SuppressWarnings("unchecked")
		@Override
		protected void onPostExecute(Object result) {
			super.onPostExecute(result);
			if (result == null) {
				return;
			}

			if (jsonservice.getToastMessage()) {
				OnlyClass onlyClass = (OnlyClass) result;
				ToastUtil.showToast(mActivity, onlyClass.data);
			}
			if (jsonservice.getsuccessState()) {
				AdressClass adressClass = (AdressClass) result;
				if (adressClass.name == null) {
					isdefaultAdress = false;
					headernametext.setText("请添加一个默认收货地址");
					headeradresstext.setText("姓名");
					headernnmbertext.setText("电话");
				} else {
					isdefaultAdress = true;
					AdressId = adressClass.id;
					headernametext.setText(adressClass.name);
					headeradresstext.setText(adressClass.city
							+ adressClass.street);
					headernnmbertext.setText(adressClass.telephone);

				}

			}

		}
	}

	/**
	 * 自己封装json
	 * */
	@SuppressWarnings({ "unused", "static-access" })
	private String JsonBySelf() {

		ArrayList<produceClass> list = (ArrayList<produceClass>) newlist;
		// StringBuilder builder = new StringBuilder();
		String message = "{";
		for (int i = 0; i < list.size(); i++) {
			produceClass produceClass = list.get(i);
			// if (carShopppingAdapter.getIsSelected().get(i)) {

			if (list.size() > 1) {
				message = message + "\"" + produceClass.productId.toString()
						+ "\"" + ":" + "{\"" + "qty" + "\"" + ":" + "\""
						+ produceClass.qty + "\"" + "},";
			} else {
				message = message + "\"" + produceClass.productId.toString()
						+ "\"" + ":" + "{\"" + "qty" + "\"" + ":" + "\""
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

	// 设置头部图片
	private void setIMage(List<produceClass> list) {
		if (list.size() == 1) {
			mImgLoad.loadImg(imageView1, newlist.get(0).image,
					R.drawable.jiazaitupian);

		} else if (list.size() == 2) {
			mImgLoad.loadImg(imageView1, newlist.get(0).image,
					R.drawable.jiazaitupian);
			mImgLoad.loadImg(imageView2, newlist.get(1).image,
					R.drawable.jiazaitupian);

		} else if (list.size() == 3 | list.size() > 3) {
			mImgLoad.loadImg(imageView1, newlist.get(0).image,
					R.drawable.jiazaitupian);
			mImgLoad.loadImg(imageView2, newlist.get(1).image,
					R.drawable.jiazaitupian);
			mImgLoad.loadImg(imageView3, newlist.get(2).image,
					R.drawable.jiazaitupian);
		}
	}

}
