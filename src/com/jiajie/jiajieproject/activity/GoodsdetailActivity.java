/**
 * 
 */
package com.jiajie.jiajieproject.activity;

import java.util.HashMap;
import java.util.Map;

import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ImageView.ScaleType;
import android.widget.TextView;

import com.jiajie.jiajieproject.R;
import com.jiajie.jiajieproject.contents.Constants;
import com.jiajie.jiajieproject.contents.InterfaceParams;
import com.jiajie.jiajieproject.model.OnlyClass;
import com.jiajie.jiajieproject.utils.ImageloderUtil;
import com.jiajie.jiajieproject.utils.IntentUtil;
import com.jiajie.jiajieproject.utils.StringUtil;
import com.jiajie.jiajieproject.utils.ToastUtil;
import com.jiajie.jiajieproject.view.SquareCenterImageView;
import com.mrwujay.cascade.model.MainPageObject;
import com.nostra13.universalimageloader.core.ImageLoader;

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
	private ImageView detail_back, qq_link, phone_link, wechat_link,
			email_link, detai_incar;
	private TextView detailtext1, detailtext2, detailtext3, detailtext4,
			detaildescription;
	private CheckBox careful;
	private String product_id;
	private String item_id;
	private String URL;
	private int count;
	private LinearLayout image_linear;
	SquareCenterImageView detail_imageView;

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
		ImageloderUtil.initImageLoader(mContext);
		image_linear = (LinearLayout) findViewById(R.id.detail_imageView);
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
		detaildescription = (TextView) findViewById(R.id.detaildescription);
		detail_back.setOnClickListener(this);
		qq_link.setOnClickListener(this);
		phone_link.setOnClickListener(this);
		wechat_link.setOnClickListener(this);
		email_link.setOnClickListener(this);
		detai_incar.setOnClickListener(this);
		careful.setOnCheckedChangeListener(new cheCkchangeClass());
		detail_imageView = new SquareCenterImageView(this);
		detail_imageView.setId(R.id.detail_imageView);
		detail_imageView.setScaleType(ScaleType.CENTER_CROP);
		// ImageLoader.getInstance().displayImage(datas.get(position),
		// detail_imageView);
		detail_imageView.setOnClickListener(this);
		image_linear.addView(detail_imageView);
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		if (StringUtil.checkStr(mainPageObject.small_image)) {
			URL = mainPageObject.small_image;
		} else {
			URL = mainPageObject.image;

		}
		ImageLoader.getInstance().displayImage(URL, detail_imageView);
		detailtext1.setText(mainPageObject.name);
		detailtext2.setText("PN:" + mainPageObject.pn);
		detaildescription.setText(mainPageObject.description);
		detailtext3.setText("¥"
				+ mainPageObject.price.substring(0,
						mainPageObject.price.lastIndexOf("00")));
		count = Integer.parseInt(mainPageObject.qty.substring(0,
				mainPageObject.qty.lastIndexOf('.')));
		detailtext4.setText("库存" + count + "件");
		Map<String, String> map = new HashMap<String, String>();

		if (StringUtil.checkStr(mainPageObject.id)) {
			product_id = mainPageObject.id;
		} else {
			product_id = mainPageObject.product_id;

		}
		map.put("product_id", product_id);
		new orderCarefulsyTask(map, InterfaceParams.userWishList).execute();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.detail_back:
			finish();
			break;
		case R.id.email_link:
			openEmail();
			break;
		case R.id.qq_link:
			openQQ();
			break;
		case R.id.phone_link:
			callphone();
			break;
		case R.id.wechat_link:
			openWechat();
			break;
		case R.id.detail_imageView:
			Intent intent = new Intent(this, SpaceImageDetailActivity.class);
			Bundle bundle = new Bundle();
			bundle.putString("images", URL);
			int[] location = new int[2];
			detail_imageView.getLocationOnScreen(location);
			bundle.putInt("locationX", location[0]);
			bundle.putInt("locationY", location[1]);
			bundle.putInt("width", detail_imageView.getWidth());
			bundle.putInt("height", detail_imageView.getHeight());
			intent.putExtras(bundle);
			startActivity(intent);
			overridePendingTransition(0, 0);
			break;

		case R.id.detai_incar:
			if (userDataService.getUserId() == null) {
				String[] str = { "登录", "是否登陆", "是", "否" };
				Bundle bundle1 = new Bundle();
				bundle1.putStringArray(ClearCacheActivity.TAG, str);
				IntentUtil.startActivityForResult(GoodsdetailActivity.this,
						ClearCacheActivity.class, 1111, bundle1);
			} else {

				Map<String, String> map1 = new HashMap<String, String>();
				if (StringUtil.checkStr(mainPageObject.id)) {
					product_id = mainPageObject.id;
				} else {
					product_id = mainPageObject.product_id;

				}
				map1.put("product_id", product_id);
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

			OnlyClass onlyClass = (OnlyClass) result;
			if (Interface.equalsIgnoreCase(InterfaceParams.addCart)) {
				if (count > 0) {
					count--;
					detailtext4.setText("库存" + count + "件");
				} else {
					detailtext4.setText("库存" + 0 + "件");
				}
			}else if(Interface.equalsIgnoreCase(InterfaceParams.removeWishList)){
				GoodsdetailActivity.this.finish();
			}
			ToastUtil.showToast(mActivity, onlyClass.data);

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

			OnlyClass onlyClass = (OnlyClass) result;
			wishlist_id = onlyClass.wishlist_id;
			ToastUtil.showToast(mContext, onlyClass.data);

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
			wishlist_id = onlyClass.data;
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
				map.put("product_id", product_id);
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

		@SuppressWarnings("unchecked")
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
						map.put("id", product_id);
						new AddCarefulsyTask(map, InterfaceParams.addWishList)
								.execute();
						ToastUtil.showToast(mActivity, "已关注");
					}
				} else {

					// map.put("c_id", categrayId);
					if (isFromcare) {
						isFirstCare++;
						map.put("item_id", mainPageObject.item_id);
						new CarefulsyTask(map, InterfaceParams.removeWishList)
								.execute();
						ToastUtil.showToast(mActivity, "移除关注");
						
					} else {
						isFirstCare++;
						map.put("product_id", product_id);
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

	/**
	 * 打开手机qq
	 * */
	@SuppressWarnings("unused")
	private void openQQ() {
		try {
			String url = "mqqwpa://im/chat?chat_type=wpa&uin=3410743855";
			startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
		} catch (Exception e) {
			ToastUtil.showToast(mContext, "QQ还没有安装");
		}

	}

	/**
	 * 打开手机微信
	 * */
	@SuppressWarnings("unused")
	private void openWechat() {
		Intent intent = new Intent();
		ComponentName cmp = new ComponentName("com.tencent.mm",
				"com.tencent.mm.ui.LauncherUI");
		intent.setAction(Intent.ACTION_MAIN);
		intent.addCategory(Intent.CATEGORY_LAUNCHER);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.setComponent(cmp);
		startActivityForResult(intent, 0);
	}

	/**
	 * 打开手机邮箱
	 * */
	@SuppressWarnings("unused")
	private void openEmail() {
		Uri uri = Uri.parse("mailto:" + Constants.email);
		Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
		intent.putExtra(Intent.EXTRA_CC, "excemple@ecsits.com"); // 抄送人
		intent.putExtra(Intent.EXTRA_SUBJECT, "这是邮件的主题部分"); // 主题
		intent.putExtra(Intent.EXTRA_TEXT, "这是邮件的正文部分"); // 正文
		startActivity(Intent.createChooser(intent, "请选择邮件类应用"));
	}

	// 打电话
	private void callphone() {
		Intent phoneIntent = new Intent("android.intent.action.CALL",
				Uri.parse("tel:" + Constants.phonenumber));
		// 启动
		startActivity(phoneIntent);
	}
}
