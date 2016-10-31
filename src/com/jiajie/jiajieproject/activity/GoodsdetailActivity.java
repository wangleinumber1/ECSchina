/**
 * 
 */
package com.jiajie.jiajieproject.activity;

import java.util.HashMap;
import java.util.Map;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.jauker.widget.BadgeView;
import com.jiajie.jiajieproject.MainActivity;
import com.jiajie.jiajieproject.R;
import com.jiajie.jiajieproject.Fragment.GoodsDetailIntroduceFragment;
import com.jiajie.jiajieproject.Fragment.GoodsDetailParamesFragment;
import com.jiajie.jiajieproject.contents.InterfaceParams;
import com.jiajie.jiajieproject.model.OnlyClass;
import com.jiajie.jiajieproject.utils.IntentUtil;
import com.jiajie.jiajieproject.utils.ScreenUtil;
import com.jiajie.jiajieproject.utils.StringUtil;
import com.jiajie.jiajieproject.utils.ToastUtil;
import com.mrwujay.cascade.model.MainPageObject;
import com.mrwujay.cascade.model.produceClass;
import com.ta.utdid2.android.utils.StringUtils;

/**
 * 项目名称：NewProject 类名称：GoodsdetailActivity 类描述： 创建人：王蕾 创建时间：2015-9-18 下午3:46:00
 * 修改备注：取消关注没加
 */
public class GoodsdetailActivity extends BaseActivity implements
		OnClickListener {

	private int i = 0;
	private MainPageObject mainPageObject;
	// 用户是否已经关注过
	private String qty;
	// 是否来自关注卡列表
	private boolean isFromcare = false;
	private LinearLayout bottomtab;
	private static String wishlist_id;
	private handler handler = new handler();
	// 是不是刚进入页面
	private int isFirstCare = 0;
	private ImageView detail_imageView, detail_back, qq_link, phone_link,
			wechat_link, email_link,  detai_incar;
	private TextView detailtext1, detailtext2, detailtext3, detailtext4;
	private CheckBox careful;
	private String product_id;
	private String item_id;

	@Override
	protected void onInit(Bundle bundle) {
		// TODO Auto-generated method stub
		super.onInit(bundle);
		setContentView(R.layout.newgooddetail);
		if (getIntent().getExtras() != null) {
			Bundle bundle2 = mActivity.getIntent().getExtras();
			mainPageObject = (MainPageObject) bundle2
					.getSerializable(PartsActivity.TAG);
			isFromcare = bundle2.getBoolean("isFromcare", false);
		}
		initView();

	}

	@SuppressWarnings("unchecked")
	private void initView() {
		detail_imageView = (ImageView) findViewById(R.id.detail_imageView);
		detail_back = (ImageView) findViewById(R.id.detail_back);
		qq_link = (ImageView) findViewById(R.id.qq_link);
		phone_link = (ImageView) findViewById(R.id.phone_link);
		wechat_link = (ImageView) findViewById(R.id.wechat_link);
		email_link = (ImageView) findViewById(R.id.email_link);
		careful = (CheckBox) findViewById(R.id.detai_care);
		detai_incar = (ImageView) findViewById(R.id.detai_incar);
		detailtext1 = (TextView) findViewById(R.id.detailtext1);
		detailtext2 = (TextView) findViewById(R.id.detailtext2);
		detailtext3 = (TextView) findViewById(R.id.detailtext3);
		detailtext4 = (TextView) findViewById(R.id.detailtext4);
		detail_back.setOnClickListener(this);
		qq_link.setOnClickListener(this);
		phone_link.setOnClickListener(this);
		wechat_link.setOnClickListener(this);
		email_link.setOnClickListener(this);
		detai_incar.setOnClickListener(this);
		careful.setOnCheckedChangeListener(new cheCkchangeClass());
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		if(StringUtil.checkStr(mainPageObject.small_image)){
			mImgLoad.loadImg(detail_imageView, mainPageObject.small_image,
					R.drawable.jiazaitupian);
		}else{
			mImgLoad.loadImg(detail_imageView, mainPageObject.image,
					R.drawable.jiazaitupian);
		}
		detailtext1.setText(mainPageObject.name);
		detailtext2.setText("PN:" + mainPageObject.pn);
		detailtext3.setText("¥"+mainPageObject.price.substring(0, mainPageObject.price.lastIndexOf('.'))+".00");
		detailtext4.setText("库存" + mainPageObject.qty.substring(0, mainPageObject.qty.lastIndexOf('.')) + "件");
		Map<String, String> map = new HashMap<String, String>();
		map.put("product_id", mainPageObject.id);
		new orderCarefulsyTask(map, InterfaceParams.userWishList).execute();
	}

	@SuppressWarnings({ "unchecked", "unchecked", "unchecked" })
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.detail_back:
			finish();
			break;
//		case R.id.detai_care:
//			
//			if(!StringUtil.checkStr(wishlist_id)){
//				// 加关注
//				Map<String, String> map = new HashMap<String, String>();
//				map.put("id", mainPageObject.id);
//				new AddCarefulsyTask(map).execute();
//			}else{
//				//取消关注
//				
//				
//			}
		
//			break;

		case R.id.detai_incar:
			if (userDataService.getUserId() == null) {
				String[] str = { "登录", "是否登陆", "是", "否" };
				Bundle bundle = new Bundle();
				bundle.putStringArray(ClearCacheActivity.TAG, str);
				IntentUtil.startActivityForResult(GoodsdetailActivity.this,
						ClearCacheActivity.class, 1111, bundle);
			} else {

				Map<String, String> map1 = new HashMap<String, String>();
				map1.put("product_id", mainPageObject.id);
				map1.put("qty", "1");
				new CarefulsyTask(map1, InterfaceParams.addCart).execute();
			}

			break;

	
		}

	}

	/**
	 * 关注; 添加购物车 详情
	 * */
	@SuppressWarnings("unused")
	private class CarefulsyTask extends MyAsyncTask {
		@SuppressWarnings("rawtypes")
		Map map;
		String Interface;

		public CarefulsyTask(Map map, String interface1) {
			super();
			this.map = map;
			Interface = interface1;
		}

		@SuppressWarnings("unchecked")
		@Override
		protected Object doInBackground(Object... params) {
			return jsonservice.getData(Interface, map, false, null);
		}

		@SuppressWarnings("unchecked")
		@Override
		protected void onPostExecute(Object result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if (result == null) {
				return;
			}

//			if (jsonservice.getToastMessage()) {
				OnlyClass onlyClass = (OnlyClass) result;
//				if (!onlyClass.success) {
					
					ToastUtil.showToast(mActivity, onlyClass.data);
//				}
//			}

		}

	}

	
	/*
	 * 关注
	 */
	@SuppressWarnings("unused")
	private class AddCarefulsyTask extends MyAsyncTask {
		@SuppressWarnings("rawtypes")
		Map map;

		public AddCarefulsyTask(Map map, String Interface) {
			super();
			this.map = map;

		}

		@SuppressWarnings("unchecked")
		@Override
		protected Object doInBackground(Object... params) {
			return jsonservice.getData(InterfaceParams.addWishList, map, false,
					null);
		}

		@SuppressWarnings("unchecked")
		@Override
		protected void onPostExecute(Object result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if (result == null) {
				return;
			}

//			if (jsonservice.getsuccessState()) {

				OnlyClass onlyClass = (OnlyClass) result;
				wishlist_id = onlyClass.wishlist_id;
				ToastUtil.showToast(mContext, onlyClass.data);
//			}

		}

	}

	/*
	 * 确认用户关注接口
	 */
	@SuppressWarnings("unused")
	private class orderCarefulsyTask extends MyAsyncTask {
		@SuppressWarnings("rawtypes")
		Map map;

		public orderCarefulsyTask(Map map, String Interface) {
			super();
			this.map = map;

		}

		@SuppressWarnings("unchecked")
		@Override
		protected Object doInBackground(Object... params) {
			return jsonservice.getCareData(InterfaceParams.userWishList, map,
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
			
			OnlyClass onlyClass = (OnlyClass) result;
			wishlist_id=onlyClass.data;
			if (onlyClass.success) {
				careful.setChecked(true);
				isFirstCare++;
			} else {
				careful.setChecked(false);
				isFirstCare++;
			}
		}

	}


	private class handler extends Handler {

		@SuppressWarnings("unchecked")
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				// 用户已经关注的接口
				Map map = new HashMap<String, String>();
				map.put("product_id", mainPageObject.id);
				new orderCarefulsyTask(map, InterfaceParams.userWishList)
						.execute();
				break;
			case 2:
				IntentUtil.activityForward(GoodsdetailActivity.this,
						LoginActivity.class, null, false);

				break;
			case 3:

				break;
			default:
				break;
			}
		}

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		switch (requestCode) {
		case 1111:
			if (resultCode == RESULT_OK) {
				handler.sendEmptyMessage(2);
			} else {
				handler.sendEmptyMessage(3);
			}
			break;

		}

	}
	
	class cheCkchangeClass implements
	android.widget.CompoundButton.OnCheckedChangeListener {

@Override
public void onCheckedChanged(CompoundButton buttonView,
		boolean isChecked) {
	if (userDataService.getUserId() == null) {
		careful.setChecked(false);
		
		String[] str = { "登录", "是否登陆", "是", "否" };
		Bundle bundle = new Bundle();
		bundle.putStringArray(ClearCacheActivity.TAG, str);
		IntentUtil.startActivityForResult(GoodsdetailActivity.this,
				ClearCacheActivity.class, 1111, bundle);
	} else {
		Map map = new HashMap<String, String>();
		if (isChecked) {
			if (isFirstCare > 0) {
				// 防止一进入界面就调关注接口		
				map.put("id", mainPageObject.id);
				new AddCarefulsyTask(map, InterfaceParams.addWishList)
						.execute();
				ToastUtil.showToast(mActivity, "已关注");
			}
		} else {

			// map.put("c_id", categrayId);
			if (isFromcare) {
				isFirstCare++;
				map.put("item_id", item_id);
				new CarefulsyTask(map, InterfaceParams.removeWishList)
						.execute();
				ToastUtil.showToast(mActivity, "移除关注");
			} else {
				isFirstCare++;
				map.put("product_id", mainPageObject.id);
				map.put("wishlist_id", wishlist_id);
				// product_id = 当前的产品id wishlist_id =
				new CarefulsyTask(map,
						InterfaceParams.deleteWishListByProduct)
						.execute();
				ToastUtil.showToast(mActivity, "取消关注");
			}

		}

	}
}
}
}
