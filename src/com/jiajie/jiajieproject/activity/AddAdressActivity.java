/**
 * 
 */
package com.jiajie.jiajieproject.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import kankan.wheel.widget.OnWheelChangedListener;
import kankan.wheel.widget.WheelView;
import kankan.wheel.widget.adapters.ArrayWheelAdapter;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.PaintDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;

import com.jiajie.jiajieproject.R;
import com.jiajie.jiajieproject.activity.BaseActivity.MyAsyncTask;
import com.jiajie.jiajieproject.contents.InterfaceParams;
import com.jiajie.jiajieproject.model.AdressClass;
import com.jiajie.jiajieproject.model.OnlyClass;
import com.jiajie.jiajieproject.utils.StringUtil;
import com.jiajie.jiajieproject.utils.ToastUtil;

/**
 * 项目名称：NewProject 类名称：AddAdressActivity 类描述： 创建人：王蕾 创建时间：2015-10-9 上午11:28:27
 * 修改备注：新建收货人
 */
public class AddAdressActivity extends WheelViewBaseActivity implements
		OnClickListener, OnWheelChangedListener {
	private ImageView headerleftImg;
	private TextView headerrightImg, addadress_layouttext3;
	private EditText addadress_layoutedit1, addadress_layoutedit2,
			addadress_layoutedit3;
	private RelativeLayout addadress_layout1;
	private ImageView addadress_layoutImage1;
	private WheelView mViewProvince;
	private WheelView mViewCity;
	private WheelView mViewDistrict;
	private TextView dissmisstext, completetext,headercentertextview;
	private String completecontent;
	private String name;
	private String city;
	private String street;
	private String telephone;
	private String id = "";
	private ImageView addAdress;
	@Override
	protected void onInit(Bundle bundle) {
		// TODO Auto-generated method stub
		super.onInit(bundle);
		setContentView(R.layout.addadress_layout);
		InitView();

	}

	private void InitView() {
		headerleftImg = (ImageView) findViewById(R.id.headerleftImg);
		addAdress = (ImageView) findViewById(R.id.addAdress);
		headerrightImg = (TextView) findViewById(R.id.headerrightImg);
		addadress_layout1 = (RelativeLayout) findViewById(R.id.addadress_layout1);
		addadress_layoutImage1 = (ImageView) findViewById(R.id.addadress_layoutImage1);
		addadress_layoutedit1 = (EditText) findViewById(R.id.addadress_layoutedit1);
		addadress_layoutedit2 = (EditText) findViewById(R.id.addadress_layoutedit2);
		addadress_layoutedit3 = (EditText) findViewById(R.id.addadress_layoutedit3);
		addadress_layouttext3 = (TextView) findViewById(R.id.addadress_layouttext3);
		headercentertextview = (TextView) findViewById(R.id.headercentertextview);
		headerrightImg.setVisibility(View.GONE);
		headerleftImg.setOnClickListener(this);
		headerrightImg.setOnClickListener(this);
		addAdress.setOnClickListener(this);
		addadress_layout1.setOnClickListener(this);
		addadress_layoutImage1.setOnClickListener(this);
		if (getIntent().getExtras() != null) {
			id = getIntent().getExtras().getString("adressid");
			headerrightImg.setVisibility(View.VISIBLE);
			new AdressdetailAsyTask().execute();
		}

	}

	@Override
	public void onClick(View v) {
		name = addadress_layoutedit1.getEditableText().toString();
		telephone = addadress_layoutedit2.getEditableText().toString();
		street = addadress_layoutedit3.getEditableText().toString();
		city = addadress_layouttext3.getText().toString();
		switch (v.getId()) {
		case R.id.headerleftImg:
			finish();
			break;
			//删除
		case R.id.headerrightImg:
			addadress_layoutedit1.setText("");
			addadress_layoutedit2.setText("");
			addadress_layoutedit3.setText("");
			addadress_layouttext3.setText("");
			new AdressDeleteAsyTask(id).execute();
			break;
		case R.id.addAdress:
			if (!StringUtil.checkStr(name)) {
				ToastUtil.showToast(mActivity, "收货人不能为空");
				return;
			} else if (!StringUtil.checkStr(telephone)) {
				ToastUtil.showToast(mActivity, "收货人手机号不能为空");
				return;
			} else if (!StringUtil.isMobileNO(telephone)) {
				ToastUtil.showToast(mActivity, "收货人手机号格式不正确");
				return;
			} else if (!StringUtil.checkStr(street)) {
				ToastUtil.showToast(mActivity, "收货人详细地址不能为空");
				return;
			} else if (!StringUtil.checkStr(city)) {
				ToastUtil.showToast(mActivity, "收货人收货地区不能为空");
				return;
			}
			;
			if (id.equals("")) {
				new AdressAsyTask(InterfaceParams.saveAddress).execute();
			} else {
				new AdressAsyTask(InterfaceParams.editAddress).execute();
			}

			break;
		case R.id.addadress_layout1:
			popwindowUpDown();
			/* 弹出键盘 */
			getPopKeyboard();

			break;
		case R.id.addadress_layoutImage1:
			// 获取联系人手机号
			startActivityForResult(new Intent(Intent.ACTION_PICK,
					ContactsContract.Contacts.CONTENT_URI), 0);
			break;
		case R.id.dissmisstext:
			if (mPopupWindow != null) {
				mPopupWindow.dismiss();
				setPopBackgroud(1);
			}
			break;
		case R.id.completetext:
			showSelectedResult();

			break;
		default:
			break;
		}

	}

	String usernumber;

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK) {
			ContentResolver reContentResolverol = getContentResolver();
			Uri contactData = data.getData();
			@SuppressWarnings("deprecation")
			Cursor cursor = managedQuery(contactData, null, null, null, null);
			cursor.moveToFirst();

			String contactId = cursor.getString(cursor
					.getColumnIndex(ContactsContract.Contacts._ID));
			Cursor phone = reContentResolverol.query(
					ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
					ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = "
							+ contactId, null, null);
			while (phone.moveToNext()) {
				usernumber = phone
						.getString(phone
								.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
				addadress_layoutedit2.setText(usernumber);
			}

		}
	}

	PopupWindow mPopupWindow;

	// popwindow弹窗
	private void popwindowUpDown() {

		View view = inflater.inflate(R.layout.citypop_layout, null);
		View poplayout = inflater.inflate(R.layout.addadress_layout, null);
		setUpViews(view);
		setUpListener();
		setUpData();
		mPopupWindow = new PopupWindow(view, LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT, true);
		mPopupWindow.setFocusable(true);
		setPopBackgroud(0.7f);
		// mPopupWindow.setBackgroundDrawable(new PaintDrawable());
		mPopupWindow.setAnimationStyle(R.style.mypopwindow_anim_updownstyle);
		mPopupWindow.showAtLocation(poplayout, Gravity.BOTTOM, 0, 0);
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		if (mPopupWindow != null) {
			mPopupWindow.dismiss();
			setPopBackgroud(1);
		}
	}

	private void setUpViews(View view) {
		mViewProvince = (WheelView) view.findViewById(R.id.id_province);
		mViewCity = (WheelView) view.findViewById(R.id.id_city);
		mViewDistrict = (WheelView) view.findViewById(R.id.id_district);
		completetext = (TextView) view.findViewById(R.id.completetext);
		dissmisstext = (TextView) view.findViewById(R.id.dissmisstext);
		completetext.setOnClickListener(this);
		dissmisstext.setOnClickListener(this);
	}

	private void setUpListener() {
		mViewProvince.addChangingListener(this);
		mViewCity.addChangingListener(this);
		mViewDistrict.addChangingListener(this);

	}

	private void setUpData() {
		initProvinceDatas();
		mViewProvince.setViewAdapter(new ArrayWheelAdapter<String>(this,
				mProvinceDatas));
		mViewProvince.setVisibleItems(7);
		mViewCity.setVisibleItems(7);
		mViewDistrict.setVisibleItems(7);
		updateCities();
		updateAreas();
	}

	@Override
	public void onChanged(WheelView wheel, int oldValue, int newValue) {
		// TODO Auto-generated method stub
		if (wheel == mViewProvince) {
			updateCities();
		} else if (wheel == mViewCity) {
			updateAreas();
		} else if (wheel == mViewDistrict) {
			mCurrentDistrictName = mDistrictDatasMap.get(mCurrentCityName)[newValue];
			mCurrentZipCode = mZipcodeDatasMap.get(mCurrentDistrictName);
		}
	}

	private void updateAreas() {
		int pCurrent = mViewCity.getCurrentItem();
		mCurrentCityName = mCitisDatasMap.get(mCurrentProviceName)[pCurrent];
		String[] areas = mDistrictDatasMap.get(mCurrentCityName);

		if (areas == null) {
			areas = new String[] { "" };
		}
		mViewDistrict
				.setViewAdapter(new ArrayWheelAdapter<String>(this, areas));
		mViewDistrict.setCurrentItem(0);
	}

	/**
	 * ��ݵ�ǰ��ʡ��������WheelView����Ϣ
	 */
	private void updateCities() {
		int pCurrent = mViewProvince.getCurrentItem();
		mCurrentProviceName = mProvinceDatas[pCurrent];
		String[] cities = mCitisDatasMap.get(mCurrentProviceName);
		if (cities == null) {
			cities = new String[] { "" };
		}
		mViewCity.setViewAdapter(new ArrayWheelAdapter<String>(this, cities));
		mViewCity.setCurrentItem(0);
		updateAreas();
	}

	private void showSelectedResult() {

		completecontent = mCurrentProviceName + mCurrentCityName
				+ mCurrentDistrictName;

		if (mPopupWindow != null) {
			addadress_layouttext3.setText(completecontent);
			mPopupWindow.dismiss();
			setPopBackgroud(1);
		}
	}

	/**
	 * 添加地址
	 * */
	@SuppressWarnings("unused")
	private class AdressAsyTask extends MyAsyncTask {
		private String InterFace;

		public AdressAsyTask(String interFace) {
			super();
			InterFace = interFace;
		}

		@SuppressWarnings("unchecked")
		@Override
		protected Object doInBackground(Object... params) {
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("name", name);
			map.put("city", city);
			map.put("id", id);
			map.put("street", street);
			map.put("postcode", "123");
			map.put("telephone", telephone);
			return jsonservice.getData(InterFace, map, false, null);
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
				finish();
			}

		}

	}

	/**
	 * 地址详情
	 * */
	@SuppressWarnings("unused")
	private class AdressdetailAsyTask extends MyAsyncTask {

		public AdressdetailAsyTask() {
			super();
		}

		@SuppressWarnings("unchecked")
		@Override
		protected Object doInBackground(Object... params) {
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("id", id);
			return jsonservice.getData(InterfaceParams.addressInfo, map, false,
					AdressClass.class);
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
			if (jsonservice.getsuccessState()) {
				AdressClass adressClass = (AdressClass) result;
				addadress_layoutedit1.setText(adressClass.lastname);
				addadress_layoutedit2.setText(adressClass.telephone);
				addadress_layouttext3.setText(adressClass.city);
				addadress_layoutedit3.setText(adressClass.street);
			}

		}

	}

	/* 弹出键盘 */
	private void getPopKeyboard() {
		InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
	}

	/**
	 * 地址删除
	 * */
	@SuppressWarnings("unused")
	private class AdressDeleteAsyTask extends MyAsyncTask {
		private String id;

		public AdressDeleteAsyTask(String id) {
			this.id = id;
		}

		@SuppressWarnings("unchecked")
		@Override
		protected Object doInBackground(Object... params) {
			Map map = new HashMap<String, String>();
			map.put("id", id);
			return jsonservice.getData(InterfaceParams.deleteAddress, map,
					false, null);
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
	
	
}
