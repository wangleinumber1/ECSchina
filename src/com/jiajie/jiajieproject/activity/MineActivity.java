/**
 * 
 */
package com.jiajie.jiajieproject.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;

import com.jiajie.jiajieproject.MainActivity;
import com.jiajie.jiajieproject.R;
import com.jiajie.jiajieproject.activity.SettingActivity.popListener;
import com.jiajie.jiajieproject.contents.InterfaceParams;
import com.jiajie.jiajieproject.contents.UserData;
import com.jiajie.jiajieproject.model.MyAccountClass;
import com.jiajie.jiajieproject.model.OnlyClass;
import com.jiajie.jiajieproject.utils.IntentUtil;
import com.jiajie.jiajieproject.utils.PublicPopWindow;
import com.jiajie.jiajieproject.utils.ToastUtil;

/**
 * 项目名称：NewProject 类名称：MineActivity 类描述： 创建人：王蕾 创建时间：2015-9-28 下午3:41:25 修改备注：
 */
public class MineActivity extends BaseActivity implements OnClickListener {
	private ImageView headImage, unlogin_icon, setting, rb_obligation,
			rb_waitgoods, rb_havegoods, rb_order;
	private TextView headname;
	private RelativeLayout Mycare, clearchache, Aboutshop, quitapp,
			quitapp_gray;
	private PopupWindow popupWindow;
	private View view;
	private LinearLayout head_layout;
	private String[] str = { "提示", "确定要退出登录吗", "确定", "取消" };
	private String[] clearstr = { "提示", "确定清除缓存吗", "确定", "取消" };
	private int qiutcode = 100;
	private int clearcache = 101;

	@Override
	protected void onInit(Bundle bundle) {
		// TODO Auto-generated method stub
		super.onInit(bundle);
		setContentView(R.layout.mine_layout);
		InitView();
	}

	private void InitView() {
		view = inflater.inflate(R.layout.mine_layout, null);
		headname = (TextView) findViewById(R.id.headname);
		headImage = (ImageView) findViewById(R.id.headImage);
		unlogin_icon = (ImageView) findViewById(R.id.unlogin_icon);
		setting = (ImageView) findViewById(R.id.setting);
		rb_obligation = (ImageView) findViewById(R.id.rb_obligation);
		rb_waitgoods = (ImageView) findViewById(R.id.rb_waitgoods);
		rb_havegoods = (ImageView) findViewById(R.id.rb_havegoods);
		rb_order = (ImageView) findViewById(R.id.rb_order);
		Mycare = (RelativeLayout) findViewById(R.id.Mycare);
		clearchache = (RelativeLayout) findViewById(R.id.clearchache);
		Aboutshop = (RelativeLayout) findViewById(R.id.Aboutshop);
		quitapp = (RelativeLayout) findViewById(R.id.quitapp);
		quitapp_gray = (RelativeLayout) findViewById(R.id.quitapp_gray);
		head_layout = (LinearLayout) findViewById(R.id.head_layout);
		Mycare.setOnClickListener(this);
		head_layout.setOnClickListener(this);
		setting.setOnClickListener(this);
		clearchache.setOnClickListener(this);
		Aboutshop.setOnClickListener(this);
		quitapp.setOnClickListener(this);
		rb_obligation.setOnClickListener(this);
		rb_waitgoods.setOnClickListener(this);
		rb_havegoods.setOnClickListener(this);
		rb_order.setOnClickListener(this);
		unlogin_icon.setOnClickListener(this);
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		new UserInfoAsyTask().execute();
	}

	@Override
	public void onClick(View v) {
		if (userDataService.getUserId() != null) {
			switch (v.getId()) {
			case R.id.head_layout:

				// popwindowUpDown();
				IntentUtil.activityForward(mActivity, MyAccontActivity.class,
						null, false);
				break;
			case R.id.unlogin_icon:

				// popwindowUpDown();
				IntentUtil.activityForward(mActivity, LoginActivity.class,
						null, false);
				break;
			case R.id.setting:
				IntentUtil.activityForward(mActivity, MyAccontActivity.class,
						null, false);
				break;

			case R.id.Aboutshop:
				IntentUtil.activityForward(mActivity, AboutActivity.class,
						null, false);
				break;

			case R.id.rb_obligation:
				IntentUtil.activityForward(mActivity, NotPayActivity.class,
						null, false);
				break;
			case R.id.rb_waitgoods:
				IntentUtil.activityForward(mActivity, NotCheckActivity.class,
						null, false);
				break;
			case R.id.rb_havegoods:

				IntentUtil.activityForward(mActivity,
						HistoryBuyPartsActivity.class, null, false);
				break;
			case R.id.rb_order:
				IntentUtil.activityForward(mActivity, MyOrderActivity.class,
						null, false);
				break;
			case R.id.Mycare:
				IntentUtil.activityForward(mActivity,
						MyCarefulPartsActivity.class, null, false);
				break;

			case R.id.quitapp:
				// 退出登录
				Bundle bundle = new Bundle();
				bundle.putStringArray(ClearCacheActivity.TAG, str);
				IntentUtil.startActivityForResult(mActivity,
						ClearCacheActivity.class, qiutcode, bundle);
				break;
			case R.id.clearchache:
				Bundle bundle1 = new Bundle();
				bundle1.putStringArray(ClearCacheActivity.TAG, clearstr);
				IntentUtil.startActivityForResult(mActivity,
						ClearCacheActivity.class, clearcache, bundle1);
				break;

			default:
				break;
			}
		} else {
			IntentUtil.activityForward(this, LoginActivity.class, null, false);
		}
	}

	private class MineLoginOutAsyTask extends MyAsyncTask {

		@SuppressWarnings("unchecked")
		@Override
		protected Object doInBackground(Object... params) {
			return jsonservice.getData(InterfaceParams.LOGINOUT, null, false,
					null);
		}

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
					userDataService.clearData();
					quitapp.setVisibility(View.GONE);
					quitapp_gray.setVisibility(View.VISIBLE);
					unlogin_icon.setVisibility(View.VISIBLE);
					head_layout.setVisibility(View.GONE);
					// mImgLoad.loadImg(headImage, null,
					// R.drawable.jiazaitupian);
				}
				ToastUtil.showToast(mActivity, onlyClass.data);
			}

		}
	}

	/**
	 * 用户信息
	 * */
	@SuppressWarnings("unused")
	private class UserInfoAsyTask extends MyAsyncTask {
		public UserInfoAsyTask() {
			super();
		}

		@SuppressWarnings("unchecked")
		@Override
		protected Object doInBackground(Object... params) {

			return jsonservice.getData(InterfaceParams.myInfo, null, false,
					MyAccountClass.class);
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
				try {
					MyAccountClass myAccountClass = (MyAccountClass) result;
					headname.setText(myAccountClass.name);
					mImgLoad.loadImg(headImage, myAccountClass.head,
							R.drawable.jiazaitupian);
					quitapp.setVisibility(View.VISIBLE);
					quitapp_gray.setVisibility(View.GONE);
					unlogin_icon.setVisibility(View.GONE);
					head_layout.setVisibility(View.VISIBLE);
				} catch (Exception e) {
					e.printStackTrace();
				}

			}

		}

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_OK) {
			switch (requestCode) {
			case 100:
				new MineLoginOutAsyTask().execute();
				break;
			case 101:
				ToastUtil.showToast(mActivity, "缓存清理完成");
				break;

			}
		}
	}

}