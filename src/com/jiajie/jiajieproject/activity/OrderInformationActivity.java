/**
 * 
 */
package com.jiajie.jiajieproject.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.jiajie.jiajieproject.R;
import com.jiajie.jiajieproject.activity.BaseActivity.MyAsyncTask;
import com.jiajie.jiajieproject.adapter.OrdercoInformationAdapter;
import com.jiajie.jiajieproject.adapter.PartsAdapter;
import com.jiajie.jiajieproject.contents.InterfaceParams;
import com.jiajie.jiajieproject.model.AdressClass;
import com.jiajie.jiajieproject.model.OnlyClass;
import com.jiajie.jiajieproject.utils.IntentUtil;
import com.jiajie.jiajieproject.utils.PullToRefreshView;
import com.jiajie.jiajieproject.utils.PullToRefreshView.OnFooterRefreshListener;
import com.jiajie.jiajieproject.utils.PullToRefreshView.OnHeaderRefreshListener;
import com.mrwujay.cascade.model.SomeMessage;
import com.mrwujay.cascade.model.produceClass;
import com.jiajie.jiajieproject.utils.ToastUtil;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
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
public class OrderInformationActivity extends BaseActivity implements
		OnClickListener {
	private String fapiaoclass;
	private String fapiaoheader;
	private String fapiaocontent;
	// 是否有默认地址
	private boolean isdefaultAdress = false;
	private String AdressId;
	private String Id;
	private ImageView headerleftImg,send_order;
	private TextView headernametext, headernnmbertext, headeradresstext,
			produceCount;
	private LinearLayout adress_layout, produce_list;
	private RadioGroup pay_radioGroup, fapiaoRadioGroup1, fapiaoRadioGroup2,
			fapiaoRadioGroup3;
	private EditText company_edittext;
	private RadioButton radioPay1, radioPay2, radioPay3, fapiaostyle1,
			fapiaostyle2, fapiaostyle3, fapiaostyle4, fapiaocontent1,
			fapiaocontent2;

	@Override
	protected void onInit(Bundle bundle) {
		// TODO Auto-generated method stub
		super.onInit(bundle);
		setContentView(R.layout.orderconfirmation_layout);
		InitView();
	}

	private void InitView() {
//		Id = getIntent().getExtras().getString("notpayid");
//		AdressId = getIntent().getExtras().getString("notpayadressid");
		headerleftImg = (ImageView) findViewById(R.id.headerleftImg);
		send_order = (ImageView) findViewById(R.id.send_order);
		headernametext = (TextView) findViewById(R.id.headernametext);
		headernnmbertext = (TextView) findViewById(R.id.headernnmbertext);
		headeradresstext = (TextView) findViewById(R.id.headeradresstext);
		adress_layout = (LinearLayout) findViewById(R.id.adress_layout);
		produce_list = (LinearLayout) findViewById(R.id.produce_list);
		pay_radioGroup = (RadioGroup) findViewById(R.id.pay_radioGroup);
		fapiaoRadioGroup1 = (RadioGroup) findViewById(R.id.fapiaoRadioGroup1);
		fapiaoRadioGroup2 = (RadioGroup) findViewById(R.id.fapiaoRadioGroup2);
		fapiaoRadioGroup3 = (RadioGroup) findViewById(R.id.fapiaoRadioGroup3);
		radioPay1 = (RadioButton) findViewById(R.id.radioPay1);
		radioPay2 = (RadioButton) findViewById(R.id.radioPay2);
		radioPay3 = (RadioButton) findViewById(R.id.radioPay3);
		fapiaostyle1 = (RadioButton) findViewById(R.id.fapiaostyle1);
		fapiaostyle2 = (RadioButton) findViewById(R.id.fapiaostyle2);
		fapiaostyle3 = (RadioButton) findViewById(R.id.fapiaostyle3);
		fapiaostyle4 = (RadioButton) findViewById(R.id.fapiaostyle4);
		fapiaocontent1 = (RadioButton) findViewById(R.id.fapiaocontent1);
//		fapiaocontent2 = (RadioButton) findViewById(R.id.fapiaocontent2);
		OncheckChangeObject oncheckChangeObject = new OncheckChangeObject();
		pay_radioGroup.setOnCheckedChangeListener(oncheckChangeObject);
		fapiaoRadioGroup1.setOnCheckedChangeListener(oncheckChangeObject);
		fapiaoRadioGroup2.setOnCheckedChangeListener(oncheckChangeObject);
		fapiaoRadioGroup3.setOnCheckedChangeListener(oncheckChangeObject);
		send_order.setOnClickListener(this);
		// new CarsAsyTask().execute();
	}

	@SuppressWarnings("unchecked")
	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.headerleftImg:
			finish();
			break;
		case R.id.send_order:
			IntentUtil.activityForward(mActivity, OrderDetailActivity.class, null,false);
			break;

		default:
			break;
		}

	}

	/**
	 * 购物车列表
	 * */
	@SuppressWarnings("unused")
	private class CarsAsyTask extends MyAsyncTask {

		@SuppressWarnings("unchecked")
		@Override
		protected Object doInBackground(Object... params) {
			Map map = new HashMap<String, String>();
			map.put("id", Id);
			map.put("address_id", AdressId);
			return jsonservice.getCareData(InterfaceParams.orderInfo, map,
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

			OnlyClass onlyClass = (OnlyClass) result;
			if (onlyClass.success) {
				OnlyClass onlyClass1 = JSON.parseObject(onlyClass.data,
						OnlyClass.class);
				ArrayList<produceClass> list = (ArrayList<produceClass>) JSON
						.parseArray(onlyClass1.product_info, produceClass.class);
				String totalprice = onlyClass1.total_price;
				String totalqty = onlyClass1.total_qty;
				AdressClass adressClass = JSON.parseObject(
						onlyClass1.address_info, AdressClass.class);
				// OrdercoInformationAdapter.setData(list);
				// OrdercoInformationAdapter.notifyDataSetChanged();
				// footpricetext.setText("¥"+ totalprice);
				// footnumbertext.setText("共  " + totalqty + "件商品 合计：");
				// headernametext.setText(adressClass.customer_name);
				// headeradresstext.setText(adressClass.city
				// + adressClass.street);
				// headernnmbertext.setText(adressClass.telephone);
			} else {
				ToastUtil.showToast(mContext, onlyClass.data);
			}

		}

	}

	// radiobutton监控类
	private class OncheckChangeObject implements OnCheckedChangeListener {

		@Override
		public void onCheckedChanged(RadioGroup group, int checkedId) {
			if (radioPay1.getId() == checkedId) {

			} else if (radioPay2.getId() == checkedId) {

			} else if (radioPay3.getId() == checkedId) {

			} else if (fapiaostyle1.getId() == checkedId) {

			} else if (fapiaostyle2.getId() == checkedId) {

			} else if (fapiaostyle3.getId() == checkedId) {

			} else if (fapiaostyle4.getId() == checkedId) {

			} else if (fapiaocontent1.getId() == checkedId) {

			} else if (fapiaocontent2.getId() == checkedId) {

			}

		}

	}

}
