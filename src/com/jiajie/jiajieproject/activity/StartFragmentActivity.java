/**
 * 
 */
package com.jiajie.jiajieproject.activity;

import com.jiajie.jiajieproject.R;
import com.jiajie.jiajieproject.Fragment.FragmnetPage1;
import com.jiajie.jiajieproject.Fragment.FragmnetPage2;
import com.jiajie.jiajieproject.Fragment.FragmnetPage3;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;

/**
 * 项目名称：NewProject 类名称：StartFragment 类描述： 创建人：王蕾 创建时间：2016-4-19 下午6:00:29 修改备注：
 */
public class StartFragmentActivity extends BaseActivity {
	private ViewPager goodsdetailviewpager;

	@Override
	protected void onInit(Bundle bundle) {
		// TODO Auto-generated method stub
		super.onInit(bundle);
		setContentView(R.layout.startimagelayout);
		InitView();
	}

	private void InitView() {
		goodsdetailviewpager = (ViewPager) findViewById(R.id.viewPager);
		goodsdetailviewpager.setAdapter(new FragmentPagerAdapter1());
//		goodsdetailviewpager.setOnPageChangeListener(this);
		goodsdetailviewpager.setCurrentItem(0);
	}



	class FragmentPagerAdapter1 extends FragmentPagerAdapter {

		/**
		 * @param fm
		 */
		public FragmentPagerAdapter1() {
			super(StartFragmentActivity.this.getSupportFragmentManager());
			// TODO Auto-generated constructor stub
		}

		@Override
		public Fragment getItem(int arg0) {
			Fragment fragment = null;
			switch (arg0) {
			case 0:

				fragment = new FragmnetPage1();
				break;
			case 1:

				fragment = new FragmnetPage2();
				break;
			case 2:

				fragment = new FragmnetPage3();
				break;

			}
			return fragment;

		}

		@Override
		public int getCount() {

			return 3;
		}

	}
}
