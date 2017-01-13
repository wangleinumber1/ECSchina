/**
 * 
 */
package com.jiajie.jiajieproject.activity;

import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.graphics.drawable.PaintDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;

import com.alibaba.fastjson.JSON;
import com.alipay.android.app.IAlixPay;
import com.jiajie.jiajieproject.contents.Constants;
import com.jiajie.jiajieproject.contents.InterfaceParams;
import com.jiajie.jiajieproject.contents.UserData;
import com.jiajie.jiajieproject.db.service.UserDataService;
import com.jiajie.jiajieproject.model.DengluClass;
import com.jiajie.jiajieproject.model.OnlyClass;
import com.jiajie.jiajieproject.MainActivity;
import com.jiajie.jiajieproject.R;
import com.jiajie.jiajieproject.utils.IntentUtil;
import com.jiajie.jiajieproject.utils.StringUtil;
import com.jiajie.jiajieproject.utils.ToastUtil;

/**
 * 项目名称：NewProject 类名称：ZhuCeActivity 类描述： 创建人：王蕾 创建时间：2015-10-16 下午5:34:56 修改备注：
 */
public class ZhuCeActivity extends BaseActivity implements OnClickListener {
	private ImageView completetext;
	ImageView headerleftImg;
	private EditText registeedit1, registeedit2, registeedit3, registeedit4;
	private View View;
	public static final String TAG = "ZhuCeActivity";
	private UserDataService userDataService;
	private String phone;
	private ImageView serviceID;

	@Override
	protected void onInit(Bundle bundle) {
		// TODO Auto-generated method stub
		super.onInit(bundle);
		setContentView(R.layout.zhuce_layout);
		userDataService = new UserDataService(mContext);
		InitView();
	}

	private void InitView() {
		phone = getIntent().getExtras().getString("phone");
		View = inflater.inflate(R.layout.zhuce_layout, null);
		headerleftImg = (ImageView) findViewById(R.id.headerleftImg);
		registeedit1 = (EditText) findViewById(R.id.registeedit1);
		registeedit2 = (EditText) findViewById(R.id.registeedit2);
		registeedit3 = (EditText) findViewById(R.id.registeedit3);
		registeedit4 = (EditText) findViewById(R.id.registeedit4);
		completetext = (ImageView) findViewById(R.id.completetext);
		serviceID = (ImageView) findViewById(R.id.serviceID);
		headerleftImg.setOnClickListener(this);
		completetext.setOnClickListener(this);
		serviceID.setOnClickListener(this);
		activityManager.pushActivity(this);
		registeedit4.addTextChangedListener(watcher);
		completetext.setFocusable(false);
	}

	private TextWatcher watcher = new TextWatcher() {

		@Override
		public void onTextChanged(CharSequence s, int start, int before,
				int count) {
			// TODO Auto-generated method stub
			if (registeedit4.getText().length() >= 6) {
				completetext.setImageResource(R.drawable.nextred);
				completetext.setFocusable(true);
			} else {
				completetext.setImageResource(R.drawable.nextgray);
				completetext.setFocusable(false);
			}
		}

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
			// TODO Auto-generated method stub

		}

		@Override
		public void afterTextChanged(Editable s) {
			// TODO Auto-generated method stub

		}
	};

	private PopupWindow popupWindow;

	@Override
	public void onClick(View v) {
		String name = registeedit1.getText().toString();
		String mail = registeedit2.getText().toString();
		String password1 = registeedit3.getText().toString();
		String password2 = registeedit4.getText().toString();
		switch (v.getId()) {
		case R.id.headerleftImg:
			finish();
			break;
		case R.id.serviceID:
			IntentUtil.activityForward(mActivity, WebActivity.class, null,
					false);
			break;
		case R.id.completetext:
			if (!StringUtil.checkStr(name)) {
				ToastUtil.showToast(mActivity, "姓名不能为空");
				return;
			} else if (!StringUtil.checkStr(mail)) {
				ToastUtil.showToast(mActivity, "邮箱不能为空");
				return;

			} else if (!isEmail(mail)) {
				ToastUtil.showToast(mActivity, "邮箱格式不正确");
				return;

			} else if (!StringUtil.checkStr(password1)) {
				ToastUtil.showToast(mActivity, "密码不能为空");
				return;
			} else if (password1.length() < 6) {
				ToastUtil.showToast(mActivity, "密码长度不符合要求");
				return;
			} else if (password1.length() > 16) {
				ToastUtil.showToast(mActivity, "密码长度不符合要求");
				return;
			}

			else if (!StringUtil.checkStr(password2)) {
				ToastUtil.showToast(mActivity, "重复密码不能为空");
				return;
			} else if (!password2.equalsIgnoreCase(password1)) {
				ToastUtil.showToast(mActivity, "两次密码输入不一致");
				return;
			}

			popupWindow = publicPop(this, View, inflater, new popListener(name,
					mail, password1));

			break;

		default:
			break;
		}

	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		if (popupWindow != null) {
			popupWindow.dismiss();
			setPopBackgroud(1);
		}
	}

	class popListener implements OnClickListener {
		private String username;

		public popListener(String username, String useremail,
				String userpassword) {
			super();
			this.username = username;
			this.useremail = useremail;
			this.userpassword = userpassword;
		}

		private String useremail;
		private String userpassword;

		@Override
		public void onClick(View v) {
			new RegisterAsyTask(username, useremail, userpassword).execute();
		}

	}

	public PopupWindow publicPop(Activity activity, View view,
			LayoutInflater inflater, OnClickListener onClickListener) {
		PopupWindow mPopupWindow;
		View poplayout = inflater.inflate(R.layout.zhucepopwindow_layout, null);
		TextView text1 = (TextView) poplayout.findViewById(R.id.text1);
		TextView text2 = (TextView) poplayout.findViewById(R.id.text2);
		TextView text3 = (TextView) poplayout.findViewById(R.id.text3);
		text1.setText("温馨提示");
		text2.setText("普通会员已注册成功");
		text3.setText("确定");
		text3.setOnClickListener(onClickListener);

		mPopupWindow = new PopupWindow(poplayout, LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT, true);
		mPopupWindow.setAnimationStyle(R.style.mypopwindow_anim_updownstyle);
		mPopupWindow.setFocusable(true);
		setPopBackgroud(0.7f);
		// mPopupWindow.setBackgroundDrawable(new PaintDrawable());
		mPopupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
		return mPopupWindow;
	}

	private class RegisterAsyTask extends MyAsyncTask {
		private String username;

		private String useremail;
		private String userpassword;

		public RegisterAsyTask(String username, String useremail,
				String userpassword) {
			super();
			this.username = username;
			this.useremail = useremail;
			this.userpassword = userpassword;
		}

		@SuppressWarnings("unchecked")
		@Override
		protected Object doInBackground(Object... params) {
			Map map = new HashMap<String, String>();
			map.put("name", username);
			map.put("email", useremail);
			map.put("password", userpassword);
			map.put("phone", phone);
			return jsonservice.getData(InterfaceParams.REGISTER, map, false,
					DengluClass.class);
		}

		@SuppressWarnings("unchecked")
		@Override
		protected void onPostExecute(Object result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if (result == null) {
				return;
			}
			if (popupWindow != null & popupWindow.isShowing()) {
				popupWindow.dismiss();
				setPopBackgroud(1);
			}
			if (jsonservice.getToastMessage()) {
				OnlyClass onlyClass = (OnlyClass) result;
				ToastUtil.showToast(mContext, onlyClass.data);
			}
			if (jsonservice.getsuccessState()) {
				DengluClass denglu = (DengluClass) result;
				UserData.userId = denglu.entity_id;
				Map map = new HashMap();
				map.put(Constants.NEW_USER_ID, denglu.entity_id);
				userDataService.saveData(map);
				IntentUtil.activityForward(mActivity, MainActivity.class, null,
						false);
				activityManager.popAllActivity();
				finish();

			}
		}
	}

	@Override
	public boolean dispatchKeyEvent(KeyEvent event) {
		if (popupWindow != null) {
			popupWindow.dismiss();
			setPopBackgroud(1);
		}
		return super.dispatchKeyEvent(event);

	}

}
