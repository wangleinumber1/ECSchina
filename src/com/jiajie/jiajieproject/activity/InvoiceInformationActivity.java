/**
 * 
 */
package com.jiajie.jiajieproject.activity;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

import com.jiajie.jiajieproject.R;
import com.jiajie.jiajieproject.utils.StringUtil;
import com.jiajie.jiajieproject.utils.ToastUtil;

/**
 * 项目名称：NewProject 类名称：InvoiceInformationActivity 类描述： 创建人：王蕾 创建时间：2015-10-15
 * 上午10:18:27 修改备注： 发票信息
 */
public class InvoiceInformationActivity extends BaseActivity implements
		OnClickListener, OnCheckedChangeListener {
	private ImageView headerleftImg;
	private TextView fapiaotext1, fapiaotext2, compeletetext;
	private EditText edittext1;
	private RadioGroup invoiceRadiogroup;
	private RadioButton invoiceRadiobutton1;
	private RadioButton invoiceRadiobutton2;
	public static final String TAG="InvoiceInformationActivity";
	public static final String fapiaoclass="fapiaoclass";
	public static final String fapiaoheader="fapiaoheader";
	public static final String fapiaocontent="fapiaocontent";
	private Boolean isselect=true;
	@Override
	protected void onInit(Bundle bundle) {
		// TODO Auto-generated method stub
		super.onInit(bundle);
		setContentView(R.layout.invoiceinformation_layout);
		InitView();
	}

	private void InitView() {
		headerleftImg = (ImageView) findViewById(R.id.headerleftImg);
		headerleftImg.setOnClickListener(this);
		compeletetext = (TextView) findViewById(R.id.compeletetext);
		fapiaotext1 = (TextView) findViewById(R.id.fapiaotext1);
		fapiaotext2 = (TextView) findViewById(R.id.fapiaotext2);
		edittext1 = (EditText) findViewById(R.id.edittext1);
		invoiceRadiogroup = (RadioGroup) findViewById(R.id.invoiceRadiogroup);
		invoiceRadiobutton1 = (RadioButton) findViewById(R.id.invoiceRadiobutton1);
		invoiceRadiobutton2 = (RadioButton) findViewById(R.id.invoiceRadiobutton2);
		fapiaotext1.setOnClickListener(this);
		fapiaotext2.setOnClickListener(this);
		headercolors = getResources().getColorStateList(R.color.headercolor);
		graycolors = getResources().getColorStateList(R.color.fapiaotextcolor);
		compeletetext.setOnClickListener(this);
	}
	private ColorStateList headercolors;
	private ColorStateList graycolors;
	@Override
	public void onClick(View v) {
		String fapiaoheaderString=edittext1.getText().toString();
		switch (v.getId()) {
		case R.id.headerleftImg:
			finish();
			break;
		case R.id.compeletetext:
			if(!StringUtil.checkStr(fapiaoheaderString)){
				ToastUtil.showToast(mContext, "请填写公司名称");
				return;
			}
			Intent intent=new Intent();
			Bundle bundle=new Bundle();
			intent.putExtras(bundle);
			bundle.putString(fapiaoclass,invoiceRadiobutton1.isChecked()?"普通发票":"增值税发票");
			bundle.putString(fapiaoheader,fapiaoheaderString);
			bundle.putString(fapiaocontent,isselect?"服务":"备件");	
			intent.putExtras(bundle);
			setResult(RESULT_OK, intent);
			finish();
			break;
		case R.id.fapiaotext1:
			fapiaotext1.setBackgroundResource(R.drawable.fapiaocheck);
			fapiaotext1.setTextColor(headercolors);
			fapiaotext2.setBackgroundResource(R.drawable.fapiaocheckup);
			fapiaotext2.setTextColor(graycolors);
			isselect=true;
			break;
		case R.id.fapiaotext2:
			fapiaotext2.setBackgroundResource(R.drawable.fapiaocheck);
			fapiaotext2.setTextColor(headercolors);
			fapiaotext1.setBackgroundResource(R.drawable.fapiaocheckup);
			fapiaotext1.setTextColor(graycolors);
			isselect=false;
			break;

		default:
			break;
		}

	}

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {

	}
}
