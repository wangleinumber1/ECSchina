/**
 * 
 */
package com.jiajie.jiajieproject.activity;

import java.util.HashMap;
import java.util.Map;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
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
	private TextView forgettext,  register_button;
	ImageView headerleftImg,completetext;
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
		completetext = (ImageView) findViewById(R.id.completetext);
		headerleftImg.setOnClickListener(this);
		forgettext.setOnClickListener(this);
		completetext.setOnClickListener(this);
		register_button.setOnClickListener(this);
		loginedit2.addTextChangedListener(watcher);
		completetext.setFocusable(false);
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

	private TextWatcher watcher = new TextWatcher() {
	    
	    @Override
	    public void onTextChanged(CharSequence s, int start, int before, int count) {
	        // TODO Auto-generated method stub
	        if(loginedit2.getText().length()>=6){
	        	completetext.setImageResource(R.drawable.longin_redbutton);
	        	completetext.setFocusable(true);
	        }else{
	        	completetext.setImageResource(R.drawable.login_greybutton);
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
