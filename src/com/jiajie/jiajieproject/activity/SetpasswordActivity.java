/**
 * 
 */
package com.jiajie.jiajieproject.activity;

import java.util.HashMap;
import java.util.Map;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.jiajie.jiajieproject.R;
import com.jiajie.jiajieproject.contents.Constants;
import com.jiajie.jiajieproject.contents.InterfaceParams;
import com.jiajie.jiajieproject.contents.UserData;
import com.jiajie.jiajieproject.db.service.UserDataService;
import com.jiajie.jiajieproject.model.DengluClass;
import com.jiajie.jiajieproject.model.OnlyClass;
import com.jiajie.jiajieproject.utils.StringUtil;
import com.jiajie.jiajieproject.utils.ToastUtil;

/**
 * 项目名称：NewProject 类名称：LoginActivity 类描述： 创建人：王蕾 创建时间：2015-9-9 上午11:19:39
 * 修改备注：登录
 */
public class SetpasswordActivity extends BaseActivity implements
		OnClickListener {
	private TextView completetext;
	private ImageView headerleftImg;
	private EditText mimaedit1, mimaedit2;
	private UserDataService userDataService;

	@Override
	protected void onInit(Bundle bundle) {
		// TODO Auto-generated method stub
		super.onInit(bundle);
		setContentView(R.layout.setpassword_layout);
		activityManager.pushActivity(this);
		userDataService = new UserDataService(mActivity);
		InitView();
	}

	private void InitView() {
		headerleftImg = (ImageView) findViewById(R.id.headerleftImg);
		mimaedit1 = (EditText) findViewById(R.id.mimaedit1);
		mimaedit2 = (EditText) findViewById(R.id.mimaedit2);
		completetext = (TextView) findViewById(R.id.completetext);
		headerleftImg.setOnClickListener(this);
		completetext.setOnClickListener(this);
		mimaedit2.addTextChangedListener(watcher);
		completetext.setFocusable(false);
	}

	private TextWatcher watcher = new TextWatcher() {

		@Override
		public void onTextChanged(CharSequence s, int start, int before,
				int count) {
			// TODO Auto-generated method stub
			if (count>=6) {
				completetext.setBackgroundColor(R.color.loginbackgroundcolor);
				completetext.setFocusable(true);
			} else {
				completetext
						.setBackgroundColor(R.color.loginbackgroundigreycolor);
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

	@Override
	public void onClick(View v) {
		String pass1 = mimaedit1.getText().toString();
		String pass2 = mimaedit2.getText().toString();
		switch (v.getId()) {
		case R.id.headerleftImg:
			finish();
			break;
		case R.id.completetext:
			if (!StringUtil.checkStr(pass1)) {
				ToastUtil.showToast(mActivity, "密码不能为空");
				return;
			} else if (!StringUtil.checkStr(pass2)) {
				ToastUtil.showToast(mContext, "重复密码不能为空");
				return;
			} else if (pass2.length() < 6) {
				ToastUtil.showToast(mActivity, "重复密码长度不符合要求");
				return;
			} else if (pass2.length() > 16) {
				ToastUtil.showToast(mActivity, "重复密码长度不符合要求");
				return;
			} else if (!pass1.equalsIgnoreCase(pass2)) {
				ToastUtil.showToast(mActivity, "两次密码输入不一致");
				return;
			} else if (pass1.length() < 6) {
				ToastUtil.showToast(mActivity, "重复密码长度不符合要求");
				return;
			} else if (pass1.length() > 16) {
				ToastUtil.showToast(mActivity, "重复密码长度不符合要求");
				return;
			}
			new SetpasswordAsyTask(pass1).execute();

			break;
		default:
			break;
		}

	}

	/**
	 * 设置密码
	 * */
	private class SetpasswordAsyTask extends MyAsyncTask {
		private String password;

		@SuppressWarnings("unused")
		public SetpasswordAsyTask(String password) {
			super();
			this.password = password;

		}

		@SuppressWarnings("unchecked")
		@Override
		protected Object doInBackground(Object... params) {
			Map map = new HashMap<String, String>();
			map.put("password", password);
			return jsonservice.getData(InterfaceParams.RESETPASSWORD, map,
					false, DengluClass.class);
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
			if(jsonservice.getsuccessState()){
				DengluClass denglu = (DengluClass) result;
				UserData.userId = denglu.entity_id;
				Map map=new HashMap();
				map.put(Constants.NEW_USER_ID, denglu.entity_id);
				userDataService.saveData(map);
				activityManager.popAllActivity();
				finish();
			}
			

		}

	}

}
