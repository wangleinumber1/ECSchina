/**
 * 
 */
package com.jiajie.jiajieproject.activity;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.CompoundButton.OnCheckedChangeListener;

import com.jiajie.jiajieproject.R;
import com.jiajie.jiajieproject.Fragment.GoodsDetailIntroduceFragment;
import com.jiajie.jiajieproject.Fragment.GoodsDetailParamesFragment;
import com.jiajie.jiajieproject.Fragment.ServiceProductDetailFragmet1;
import com.jiajie.jiajieproject.Fragment.ServiceProductDetailFragmet2;
import com.jiajie.jiajieproject.utils.ToastUtil;

/**
 * 项目名称：NewProject 类名称：ServiceProductDetailActivity 类描述： 创建人：王蕾 创建时间：2015-9-25
 * 上午10:34:35 修改备注：
 */
public class ServiceProductDetailActivity extends BaseActivity implements
		OnClickListener, OnPageChangeListener, OnCheckedChangeListener {

	@Override
	protected void onInit(Bundle bundle) {
		// TODO Auto-generated method stub
		super.onInit(bundle);
		setContentView(R.layout.serviceproductdetail_layout);
		InitView();
	}

	private CheckBox careful;
	private TextView text1, text2, byphone;
	private ImageView headerleftImg;
	private ViewPager viewPager;

	private void InitView() {
		viewPager = (ViewPager) findViewById(R.id.serviceproductviewpager);
		headerleftImg = (ImageView) findViewById(R.id.headerleftImg);
		byphone = (TextView) findViewById(R.id.byphone);
		careful = (CheckBox) findViewById(R.id.careful);
		text1 = (TextView) findViewById(R.id.serviceproductdetailheadertext1);
		text2 = (TextView) findViewById(R.id.serviceproductdetailheadertext2);
		text1.setOnClickListener(this);
		text2.setOnClickListener(this);
		careful.setOnCheckedChangeListener(this);
		byphone.setOnClickListener(this);
		headerleftImg.setOnClickListener(this);
		viewPager.setAdapter(new FragmentPagerAdapter1());
		viewPager.setOnPageChangeListener(this);
		viewPager.setCurrentItem(0);
		headercolors = getResources().getColorStateList(R.color.headercolor);
		whitecolors = getResources().getColorStateList(R.color.white);
	}

	private ColorStateList headercolors;
	private ColorStateList whitecolors;

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.serviceproductdetailheadertext1:
			text2.setTextColor(whitecolors);
			text1.setTextColor(headercolors);
			text1.setBackgroundResource(R.drawable.fuwuxiangqingbg);
			text2.setBackgroundResource(R.drawable.blue);

			viewPager.setCurrentItem(0);
			break;
		case R.id.serviceproductdetailheadertext2:
			text1.setTextColor(whitecolors);
			text2.setTextColor(headercolors);
			text2.setBackgroundResource(R.drawable.fuwuxiangqingbg);
			text1.setBackgroundResource(R.drawable.blue);

			viewPager.setCurrentItem(1);
			break;
		case R.id.headerleftImg:
			finish();
			break;
		case R.id.byphone:
			callphone();
			break;

		default:
			break;
		}

	}

	class FragmentPagerAdapter1 extends FragmentPagerAdapter {

		/**
		 * @param fm
		 */
		public FragmentPagerAdapter1() {
			super(ServiceProductDetailActivity.this.getSupportFragmentManager());
			// TODO Auto-generated constructor stub
		}

		@Override
		public Fragment getItem(int arg0) {
			Fragment fragment = null;
			switch (arg0) {
			case 0:
				fragment = new ServiceProductDetailFragmet1();
				break;
			case 1:
				fragment = new ServiceProductDetailFragmet2();
				break;

			}
			return fragment;
		}

		@Override
		public int getCount() {

			return 2;
		}

	}

	@Override
	public void onPageScrollStateChanged(int arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onPageSelected(int arg0) {
		switch (arg0) {
		case 0:
			text2.setTextColor(whitecolors);
			text1.setTextColor(headercolors);
			text1.setBackgroundResource(R.drawable.fuwuxiangqingbg);
			text2.setBackgroundResource(R.drawable.blue);
			break;
		case 1:
			text1.setTextColor(whitecolors);
			text2.setTextColor(headercolors);
			text2.setBackgroundResource(R.drawable.fuwuxiangqingbg);
			text1.setBackgroundResource(R.drawable.blue);
			break;
		case R.id.byphone:
			callphone();
			break;
		default:
			break;
		}
	}

	// 打电话
	private void callphone() {
		Intent phoneIntent = new Intent("android.intent.action.CALL",
				Uri.parse("tel:" + "18032853584"));
		// 启动
		startActivity(phoneIntent);
	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		if (isChecked) {
			ToastUtil.showToast(mActivity, "已关注");
		} else {
			ToastUtil.showToast(mActivity, "取消关注");
		}

	}

}
