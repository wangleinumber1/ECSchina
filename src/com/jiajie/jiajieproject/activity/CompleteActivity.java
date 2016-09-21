/**
 * 
 */
package com.jiajie.jiajieproject.activity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import com.jiajie.jiajieproject.R;
import android.text.format.Time;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 项目名称：NewProject 类名称：InvoiceInformationActivity 类描述： 创建人：王蕾 创建时间：2015-10-15
 * 上午10:18:27 修改备注： 发票信息
 */
public class CompleteActivity extends BaseActivity implements OnClickListener {
	private ImageView headerleftImg;
	private TextView layout1text1, layout2text1, layout2text2, layout2text3;
	private String id;

	@Override
	protected void onInit(Bundle bundle) {
		// TODO Auto-generated method stub
		super.onInit(bundle);
		setContentView(R.layout.complete_layout);
		id = getIntent().getExtras().getString("orderid");
		InitView();
	}

	private void InitView() {
		headerleftImg = (ImageView) findViewById(R.id.headerleftImg);
		headerleftImg.setOnClickListener(this);
		layout1text1 = (TextView) findViewById(R.id.layout1text1);
		layout2text1 = (TextView) findViewById(R.id.layout2text1);
		layout2text2 = (TextView) findViewById(R.id.layout2text2);
		layout2text3 = (TextView) findViewById(R.id.layout2text3);
		layout1text1.setText("按照协议，请务必在" + getTime()
				+ "前完成向协议对公账户打款。若果超过上述时间，将会影响公司在佳杰信控系统中的评级。");
		layout2text1.setText("若需退货，请在" + getTime() + "前联系客服。");

		layout2text3.setText("本次下单的订单号：" + id);
		changeTextColor();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.headerleftImg:
			finish();
			break;

		default:
			break;
		}

	}

	// 改变文字颜色
	private void changeTextColor() {

		SpannableStringBuilder builder = new SpannableStringBuilder(
				layout1text1.getText().toString());
		SpannableStringBuilder builder1 = new SpannableStringBuilder(
				layout2text1.getText().toString());
		SpannableStringBuilder builder2 = new SpannableStringBuilder(
				layout2text2.getText().toString());
		SpannableStringBuilder builder3 = new SpannableStringBuilder(
				layout2text3.getText().toString());

		ForegroundColorSpan redSpan = new ForegroundColorSpan(
				(Color.parseColor("#FF9055")));

		builder.setSpan(redSpan, 9, 15, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		layout1text1.setText(builder);
		builder1.setSpan(redSpan, 7, 13, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		layout2text1.setText(builder1);
		builder2.setSpan(redSpan, 5, layout2text2.getText().length(),
				Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		layout2text2.setText(builder2);
		builder3.setSpan(redSpan, 9, layout2text3.getText().length(),
				Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		layout2text3.setText(builder3);
	}

	// 获取前十天的日期
		public String getTime() {
			SimpleDateFormat sf = null;
			Long tsLong = System.currentTimeMillis();
			Long moreLong=tsLong+3600*24*10*1000;
			Date d = new Date(moreLong);
			sf = new SimpleDateFormat("MM月dd日");
			return sf.format(d);
			
			
		}

}
