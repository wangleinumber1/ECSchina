/**
 * 
 */
package com.jiajie.jiajieproject.activity;

import java.util.HashMap;
import java.util.Map;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.jiajie.jiajieproject.R;
import com.jiajie.jiajieproject.activity.BaseActivity.MyAsyncTask;
import com.jiajie.jiajieproject.contents.DeviceParamsDB;
import com.jiajie.jiajieproject.contents.InterfaceParams;
import com.jiajie.jiajieproject.model.OnlyClass;
import com.jiajie.jiajieproject.utils.IntentUtil;
import com.jiajie.jiajieproject.utils.StringUtil;
import com.jiajie.jiajieproject.utils.ToastUtil;

/**
 * 项目名称：NewProject 类名称：CodeActivity 类描述： 创建人：王蕾 创建时间：2015-10-16 上午11:23:37 修改备注：
 */
public class RegiteCodeActivity extends BaseActivity implements OnClickListener {
	private TextView codetext1, completetext;
	private EditText codeedit, codeedit1;
	private String phonenamber;
	private String secret;
	private Boolean istrue = false;
	private ImageView headerleftImg;

	@Override
	protected void onInit(Bundle bundle) {
		// TODO Auto-generated method stub
		super.onInit(bundle);
		setContentView(R.layout.newcode_layout);
		InitView();
	}

	private void InitView() {
		codetext1 = (TextView) findViewById(R.id.codetext1);
		completetext = (TextView) findViewById(R.id.completetext);
		codeedit = (EditText) findViewById(R.id.codeedit);
		headerleftImg = (ImageView) findViewById(R.id.headerleftImg);
		codeedit1 = (EditText) findViewById(R.id.codeedit1);
		completetext.setOnClickListener(this);
		codetext1.setOnClickListener(this);
		headerleftImg.setOnClickListener(this);
		DeviceParamsDB.needtwocookie = true;
	}

	@Override
	public void onClick(View v) {
		phonenamber = codeedit.getText().toString();
		secret = codeedit1.getText().toString();
		switch (v.getId()) {
		case R.id.codetext1:
			if (!StringUtil.checkStr(phonenamber)) {
				ToastUtil.showToast(mActivity, "电话不能为空");
				return;
			}

			else if (!StringUtil.isMobileNO(phonenamber)) {
				ToastUtil.showToast(mActivity, "电话格式不正确");
				return;
			}
			getCode(phonenamber);
			break;
		case R.id.headerleftImg:

			finish();
			break;

		case R.id.completetext:
			if (!isMobileValid(phonenamber)) {
				return;
			} else if (secret.isEmpty()) {
				ToastUtil.showToast(mActivity, "请填写验证码");
				return;
			}
			new RegisterPostCodeAsyTask(phonenamber, secret).execute();

			break;
		}

	}

	/**
	 * 发送验证码
	 */
	private void sendSMS(String mobile) {
		new RegisterCodeAsyTask(mobile).execute();
	}

	/**
	 * 获取验证码
	 */
	private void getCode(String phonenamber) {
		final MyCount mc = new MyCount(60000, 1000);
		mc.start();

		sendSMS(phonenamber);
	}

	/**
	 * 验证注册码
	 */
	private String mobile;

	private void checkCode() {
		mobile = codeedit.getText().toString();
		String checkcode = codeedit1.getText().toString();

		if (!isMobileValid(mobile))
			return;

		if (TextUtils.isEmpty(checkcode)) {
			ToastUtil.showToast(mActivity, "验证码不能为空");
			return;
		}

	}


	/* 定义一个倒计时的内部类 */
	class MyCount extends CountDownTimer {
		public MyCount(long millisInFuture, long countDownInterval) {
			super(millisInFuture, countDownInterval);
		}

		@Override
		public void onFinish() {
			codetext1.setBackgroundResource(R.color.white);
			codetext1.setText("重新获取");
			codetext1.setClickable(true);

		}

		@Override
		public void onTick(long millisUntilFinished) {
			codetext1.setBackgroundResource(R.color.loginbackgroundcolor);
			codetext1.setClickable(false);
			codetext1.setText(millisUntilFinished / 1000 + "s");
		}
	}

	/**
	 * 获取验证码
	 * */
	private class RegisterCodeAsyTask extends MyAsyncTask {
		private String mobil;

		public RegisterCodeAsyTask(String mobil) {
			super();
			this.mobil = mobil;

		}

		@SuppressWarnings("unchecked")
		@Override
		protected Object doInBackground(Object... params) {
			Map map = new HashMap<String, String>();
			map.put("mobile", mobil);

			return jsonservice.getData(InterfaceParams.SENDSMS, map, false,
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

				ToastUtil.showToast(mContext, onlyClass.data);
			}

		}

	}

	/**
	 * 提交验证码
	 * */
	@SuppressWarnings("unused")
	private class RegisterPostCodeAsyTask extends MyAsyncTask {
		private String mobil;
		private String secret;

		public RegisterPostCodeAsyTask(String mobil, String secret) {
			super();
			this.mobil = mobil;
			this.secret = secret;

		}

		@SuppressWarnings("unchecked")
		@Override
		protected Object doInBackground(Object... params) {
			Map map = new HashMap<String, String>();
			map.put("mobile", mobil);
			map.put("secret", secret);

			return jsonservice.getData(InterfaceParams.POSTSMSCODE, map, false,
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
				istrue = onlyClass.success;
				ToastUtil.showToast(mContext, onlyClass.data);
			}
			// 判断验证码填写是否正确
			if (istrue) {
//				activityManager.popAllActivity();
//				finish();
				Bundle bundle=new Bundle();
				bundle.putString("phone", phonenamber);
				IntentUtil.activityForward(mActivity, ZhuCeActivity.class, bundle,
				false);
			}
			// activityManager.popAllActivity();
			// finish();
		}

	}

}
