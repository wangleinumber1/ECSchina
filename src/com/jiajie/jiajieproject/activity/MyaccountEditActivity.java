/**
 * 
 */
package com.jiajie.jiajieproject.activity;

import java.util.Map;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.jiajie.jiajieproject.R;
import com.jiajie.jiajieproject.contents.InterfaceParams;
import com.jiajie.jiajieproject.model.OnlyClass;
import com.jiajie.jiajieproject.utils.StringUtil;
import com.jiajie.jiajieproject.utils.ToastUtil;

/**
 * 项目名称：NewProject 类名称：MyaccountFragment 类描述： 用来修改昵称，公司名 创建人：王蕾 创建时间：2016-8-15
 * 下午4:01:49 修改备注：
 */
public class MyaccountEditActivity extends BaseActivity implements
		OnClickListener {
	private ImageView headerleftImg;
	private TextView headercentertextview, headerRightImg;
	private EditText edittext;
	private String editString;
	private String namechange = "修改用户名", companychange = "修改公司名",
			emailchange = "修改邮箱", phonechange = "修改电话号码";
	private String changecode;

	@Override
	protected void onInit(Bundle bundle) {
		// TODO Auto-generated method stub
		super.onInit(bundle);
		setContentView(R.layout.myacount_editlayout);
		initView();
	}

	private void initView() {
		headerleftImg = (ImageView) findViewById(R.id.headerleftImg);
		headercentertextview = (TextView) findViewById(R.id.headercentertextview);
		headerRightImg = (TextView) findViewById(R.id.headerRightImg);
		edittext = (EditText) findViewById(R.id.edittext);
		headerleftImg.setOnClickListener(this);
		headerRightImg.setOnClickListener(this);
		changecode = getIntent().getExtras().getString(MyAccontActivity.TAG);
		if (changecode.equalsIgnoreCase("1")) {
			headercentertextview.setText(namechange);
		} else if (changecode.equalsIgnoreCase("2")) {
			headercentertextview.setText(companychange);
		} else if (changecode.equalsIgnoreCase("3")) {
			headercentertextview.setText(emailchange);
		} else if (changecode.equalsIgnoreCase("4")) {
			headercentertextview.setText(phonechange);
		}
	}

	@Override
	public void onClick(View v) {
		editString=edittext.getText().toString();
		switch (v.getId()) {
		case R.id.headerleftImg:
			finish();
			break;
		case R.id.headerRightImg:
			if(!StringUtil.checkStr(editString)){
				ToastUtil.showToast(mContext, "填写不能为空");
				return;
			}else{
			if(changecode.equalsIgnoreCase("4")){
				if(!StringUtil.isMobileNO(editString)){
					ToastUtil.showToast(mContext, "手机号格式不正确");
					return;
				}
				
			}else if(changecode.equalsIgnoreCase("3")){
				if(!isEmail(editString)){
					ToastUtil.showToast(mContext, "邮箱格式不正确");
					return;
				}
			}
			Intent intent = new Intent();
			Bundle bundle = new Bundle();
			bundle.putString(MyAccontActivity.TAG, editString);
			intent.putExtras(bundle);
			setResult(RESULT_OK, intent);
			finish();
			break;
			}
		default:
			break;
		}

	}
	
	
	

}
