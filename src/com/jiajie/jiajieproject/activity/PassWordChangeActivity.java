/**
 * 
 */
package com.jiajie.jiajieproject.activity;

import java.util.HashMap;
import java.util.Map;

import com.jiajie.jiajieproject.R;
import com.jiajie.jiajieproject.contents.InterfaceParams;
import com.jiajie.jiajieproject.model.OnlyClass;
import com.jiajie.jiajieproject.utils.StringUtil;
import com.jiajie.jiajieproject.utils.ToastUtil;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 项目名称：NewProject 类名称：PassWordChangeActivity 类描述： 创建人：王蕾 创建时间：2015-10-16
 * 下午3:59:01 修改备注：密码修改
 */
public class PassWordChangeActivity extends BaseActivity implements
		OnClickListener {
	private ImageView headerleftImg;
	private EditText edittext1, edittext2, edittext3;
	private ImageView save_button;

	@Override
	protected void onInit(Bundle bundle) {
		// TODO Auto-generated method stub
		super.onInit(bundle);
		setContentView(R.layout.passwordvchange_layout);
		InitView();
	}

	private void InitView() {
		activityManager.pushActivity(this);
		headerleftImg = (ImageView) findViewById(R.id.headerleftImg);
		save_button = (ImageView) findViewById(R.id.save_button);
		edittext1 = (EditText) findViewById(R.id.edittext1);
		edittext2 = (EditText) findViewById(R.id.edittext2);
		edittext3 = (EditText) findViewById(R.id.edittext3);
		headerleftImg.setOnClickListener(this);
		save_button.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		String oldpassword = edittext1.getText().toString();
		String newpassword1 = edittext2.getText().toString();
		String newpassword2 = edittext3.getText().toString();
		switch (v.getId()) {
		case R.id.headerleftImg:
			finish();
			break;
		case R.id.save_button:
			if (!StringUtil.checkStr(oldpassword)) {
				ToastUtil.showToast(mContext, "旧密码不能为空");
				return;
			} else if (!StringUtil.checkStr(newpassword1)) {
				ToastUtil.showToast(mContext, "新密码不能为空");
				return;
			} else if (!StringUtil.checkStr(newpassword2)) {
				ToastUtil.showToast(mContext, "重复密码不能为空");
				return;
			} else if (oldpassword.length() < 6) {
				ToastUtil.showToast(mActivity, "旧密码长度不符合要求");
				return;
			} else if (oldpassword.length() > 16) {
				ToastUtil.showToast(mActivity, "旧密码长度不符合要求");
				return;
			} else if (newpassword1.length() < 6) {
				ToastUtil.showToast(mActivity, "新密码长度不符合要求");
				return;
			} else if (newpassword1.length() > 16) {
				ToastUtil.showToast(mActivity, "新密码长度不符合要求");
				return;
			} else if (newpassword2.length() < 6) {
				ToastUtil.showToast(mActivity, "重复密码长度不符合要求");
				return;
			} else if (newpassword2.length() > 16) {
				ToastUtil.showToast(mActivity, "重复密码长度不符合要求");
				return;
			} else if (!newpassword1.equalsIgnoreCase(newpassword2)) {
				ToastUtil.showToast(mActivity, "两次密码输入不一致");
				return;
			}
			new PassWordChangeAsyTask(oldpassword, newpassword1).execute();
			finish();
			break;

		default:
			break;
		}

	}

	@SuppressWarnings("unused")
	private class PassWordChangeAsyTask extends AsyncTask {
		private String oldpassword;
		private String passwprd;

		public PassWordChangeAsyTask(String oldpassword, String passwprd) {
			super();
			this.oldpassword = oldpassword;
			this.passwprd = passwprd;

		}

		@SuppressWarnings("unchecked")
		@Override
		protected Object doInBackground(Object... params) {
			Map map = new HashMap<String, String>();
			map.put("old_password", oldpassword);
			map.put("password", passwprd);

			return jsonservice.getData(InterfaceParams.CHANGEPASSWORD, map,
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
				ToastUtil.showToast(mContext, "修改成功");
			}

		}

	}
}
