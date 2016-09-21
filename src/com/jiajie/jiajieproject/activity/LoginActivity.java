/**
 * 
 */
package com.jiajie.jiajieproject.activity;

import java.util.HashMap;
import java.util.Map;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.jiajie.jiajieproject.R;
import com.jiajie.jiajieproject.activity.BaseActivity.MyAsyncTask;
import com.jiajie.jiajieproject.activity.ForgetCodeActivity.MyCount;
import com.jiajie.jiajieproject.contents.Constants;
import com.jiajie.jiajieproject.contents.InterfaceParams;
import com.jiajie.jiajieproject.contents.UserData;
import com.jiajie.jiajieproject.db.service.UserDataService;
import com.jiajie.jiajieproject.model.DengluClass;
import com.jiajie.jiajieproject.model.OnlyClass;
import com.jiajie.jiajieproject.utils.IntentUtil;
import com.jiajie.jiajieproject.utils.StringUtil;
import com.jiajie.jiajieproject.utils.ToastUtil;

/**
 * 项目名称：NewProject 类名称：LoginActivity 类描述： 创建人：王蕾 创建时间：2015-9-9 上午11:19:39
 * 修改备注：登录
 */
public class LoginActivity extends BaseActivity implements OnClickListener {
	private TextView forgettext, completetext, register_button;
	ImageView headerleftImg;
	private EditText loginedit1, loginedit2;
	private UserDataService userDataService;

	@Override
	protected void onInit(Bundle bundle) {
		// TODO Auto-generated method stub
		super.onInit(bundle);
		setContentView(R.layout.login_layout);
		activityManager.pushActivity(this);
		userDataService = new UserDataService(mActivity);
		InitView();
	}

	private void InitView() {
		headerleftImg = (ImageView) findViewById(R.id.headerleftImg);
		forgettext = (TextView) findViewById(R.id.forgettext);
		register_button = (TextView) findViewById(R.id.register_button);
		loginedit1 = (EditText) findViewById(R.id.loginedit1);
		loginedit2 = (EditText) findViewById(R.id.loginedit2);
		completetext = (TextView) findViewById(R.id.completetext);
		headerleftImg.setOnClickListener(this);
		forgettext.setOnClickListener(this);
		completetext.setOnClickListener(this);
		register_button.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		String name = loginedit1.getText().toString();
		String passwprd = loginedit2.getText().toString();
		switch (v.getId()) {
		case R.id.headerleftImg:
			finish();
			break;

		case R.id.register_button:
			

			IntentUtil.activityForward(mActivity, ZhuCeCodeActivity.class, null,
					true);
			break;
		case R.id.completetext:
			if (!StringUtil.checkStr(name)) {
				ToastUtil.showToast(mActivity, "电话不能为空");
				return;
			}

			else if (!StringUtil.isMobileNO(name)) {
				ToastUtil.showToast(mActivity, "电话格式不正确");
				return;
			}

			new LoginAsyTask(name, passwprd).execute();
			break;

		case R.id.forgettext:
			IntentUtil.activityForward(mActivity, ForgetCodeActivity.class,
					null, false);

			break;

		default:
			break;
		}

	}

	/**
	 * 登录
	 * */
	@SuppressWarnings("unused")
	private class LoginAsyTask extends MyAsyncTask {
		private String username;
		private String passwprd;

		public LoginAsyTask(String username, String passwprd) {
			super();
			this.username = username;
			this.passwprd = passwprd;

		}

		@SuppressWarnings("unchecked")
		@Override
		protected Object doInBackground(Object... params) {
			Map map = new HashMap<String, String>();
			map.put("username", username);
			map.put("password", passwprd);

			return jsonservice.getData(InterfaceParams.LOGIN, map, false,
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

			if (jsonservice.getToastMessage()) {
				OnlyClass onlyClass = (OnlyClass) result;
				ToastUtil.showToast(mActivity, onlyClass.data);
			}
			if (jsonservice.getsuccessState()) {
				DengluClass denglu = (DengluClass) result;
				UserData.userId = denglu.entity_id;
				@SuppressWarnings("rawtypes")
				Map map = new HashMap();
				map.put(Constants.NEW_USER_ID, denglu.entity_id);
				userDataService.saveData(map);
				finish();
			}

		}

	}

}
