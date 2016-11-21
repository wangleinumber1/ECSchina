/**
 * 
 */
package com.jiajie.jiajieproject.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.jiajie.jiajieproject.R;
import com.jiajie.jiajieproject.adapter.PosterAdapter;
import com.jiajie.jiajieproject.adapter.mainpagehotAdapter;
import com.jiajie.jiajieproject.adapter.mainpagenewAdapter;
import com.jiajie.jiajieproject.adapter.mainpagesellAdapter;
import com.jiajie.jiajieproject.contents.Constants;
import com.jiajie.jiajieproject.contents.InterfaceParams;
import com.jiajie.jiajieproject.db.service.SharePreferDB;
import com.jiajie.jiajieproject.model.BannerPic;
import com.jiajie.jiajieproject.model.DeviceInfo;
import com.jiajie.jiajieproject.model.OnlyClass;
import com.jiajie.jiajieproject.utils.DeviceInfoUtil;
import com.jiajie.jiajieproject.utils.DisplayUtil;
import com.jiajie.jiajieproject.utils.DownLoadApp;
import com.jiajie.jiajieproject.utils.ImageLoad;
import com.jiajie.jiajieproject.utils.IntentUtil;
import com.jiajie.jiajieproject.utils.NetworkUtil;
import com.jiajie.jiajieproject.utils.ToastUtil;
import com.jiajie.jiajieproject.widget.ChildViewPager;
import com.jiajie.jiajieproject.widget.CirclePageIndicator;
import com.jiajie.jiajieproject.widget.MyGridView;
import com.jiajie.jiajieproject.widget.ReboundScrollView;
import com.jiajie.jiajieproject.widget.ReboundScrollView.OnScrollChangedListener;
import com.mrwujay.cascade.model.MainPageObject;

/**
 * 项目名称：NewProject 类名称：PartsActivity 类描述： 创建人：王蕾 创建时间：2015-9-8 下午5:24:26
 *
 */
public class PartsActivity extends BaseActivity implements OnClickListener {
	private ChildViewPager vp_banner;
	private CirclePageIndicator indicator;
	private int currentItem;
	private ScheduledExecutorService scheduledExecutorService;
	private List<BannerPic> bannerList;
	private List<BannerPic> rows;
	private Handler handler = new Handler();
	// private PartsAdapter partsAdapter;
	private int page = 1;
	public static String version = "";
	public static String appUrl;
	private DeviceInfo deviceInfo;
	String versioncodeNumber;
	private TextView text4;
	private MyGridView sellgrid, hotgrid, recommandgrid, newgrid;
	private ImageView leftImage, RightImage, searchimage;
	private mainpagesellAdapter mainpagesellAdpter;
	private mainpagehotAdapter mainpagehotAdpter;
	private mainpagenewAdapter	mainpagenewAdpter;
	private mainpagesellAdapter	maintuijianAdapter;
	private ReboundScrollView ReboundScrollView;
	private RelativeLayout mainpage_header;
	private int phonecode = 102;
	public static final String TAG = "PartsActivity";
	ArrayList<MainPageObject> BestSelllist = new ArrayList<MainPageObject>();
	ArrayList<MainPageObject> Hotlist = new ArrayList<MainPageObject>();
	ArrayList<MainPageObject> Tuijianlist = new ArrayList<MainPageObject>();
	ArrayList<MainPageObject> Newlist = new ArrayList<MainPageObject>();

	@Override
	protected void onInit(Bundle bundle) {
		// TODO Auto-generated method stub
		super.onInit(bundle);
		// 判断是否有网
		if (!NetworkUtil.isConnected(mActivity)) {
			setContentView(R.layout.nomessageload_view_layout);
			text4 = (TextView) findViewById(R.id.text4);
			text4.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					setContentView(R.layout.mainpagelayout);
					initView();

				}
			});

		} else {
			setContentView(R.layout.mainpagelayout);
			deviceInfo = DeviceInfoUtil.getDeviceInfo(mContext);
			if (!version.equals("")) {
				Integer versioncode = Integer.valueOf(version);
				if (versioncode > deviceInfo.getAppVersionCode()) {
					DownLoadApp.showDialog(PartsActivity.this, "应用版本更新",
							appUrl);

				}
			}
			initView();
		}
	}

	@SuppressWarnings("unchecked")
	private void initView() {

		deviceInfo = DeviceInfoUtil.getDeviceInfo(mContext);
		// partsAdapter = new PartsAdapter(mActivity, mImgLoad);
		initBanner();
		autoChange();
		leftImage = (ImageView) findViewById(R.id.headerleftImg);
		RightImage = (ImageView) findViewById(R.id.mainpage_phone);
		searchimage = (ImageView) findViewById(R.id.mainpage_search);
		sellgrid = (MyGridView) findViewById(R.id.sellingpartgridview);
		hotgrid = (MyGridView) findViewById(R.id.hotpartgridview);
		recommandgrid = (MyGridView) findViewById(R.id.recommandpartgridview);
		newgrid = (MyGridView) findViewById(R.id.newpartgridview);
		ReboundScrollView = (ReboundScrollView) findViewById(R.id.mainScroll);
		mainpage_header = (RelativeLayout) findViewById(R.id.mainpage_header);
		
		
		leftImage.setOnClickListener(this);
		RightImage.setOnClickListener(this);
		searchimage.setOnClickListener(this);
		ReboundScrollView.smoothScrollTo(0, 0);
		ReboundScrollView.setOnScrollListener(new OnScrollChangedListener() {
			int movepageheight = DisplayUtil.dipToPixel(200);

			@Override
			public void onScrollChanged(int x, int y, int oldxX, int oldY) {
				// scroll移动的距离正好为banner高度，搜索框变色
				if (y <= 0) {
					mainpage_header.setBackgroundColor(Color.argb((int) 0, 227,
							29, 26));// AGB由相关工具获得，或者美工提供
				} else if (y > 0 && y <= movepageheight) {
					float scale = (float) y / movepageheight;
					float alpha = (255 * scale);
					// 只是layout背景透明(仿知乎滑动效果)
					mainpage_header.setBackgroundColor(Color.argb((int) alpha,
							255, 255, 225));
					leftImage.setImageResource(R.drawable.qr_code);
					RightImage.setImageResource(R.drawable.phone);
					searchimage.setImageResource(R.drawable.search_button);

				} else {
					mainpage_header.setBackgroundColor(Color.argb((int) 255,
							255, 255, 255));
					leftImage.setImageResource(R.drawable.qr_code_grey);
					RightImage.setImageResource(R.drawable.phone_grey);
					searchimage.setImageResource(R.drawable.search_greyicon);

				}
			}

		});

	}

	@SuppressWarnings("unchecked")
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		new getBestsellerAsyTask(InterfaceParams.getAll).execute();
	}



	private void initBanner() {
		vp_banner = (ChildViewPager) findViewById(R.id.vp_banner);
		indicator = (CirclePageIndicator) findViewById(R.id.indicator);
		vp_banner.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int item) {
				currentItem = item;
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
			}

			@Override
			public void onPageScrollStateChanged(int arg0) {

			}
		});
		bannerList = new ArrayList<BannerPic>();
		rows = new ArrayList<BannerPic>();
		BannerPic bannerPic1 = new BannerPic();
		BannerPic bannerPic2 = new BannerPic();
//		BannerPic bannerPic3 = new BannerPic();
		bannerPic1.res = R.drawable.banner1;
		bannerPic2.res = R.drawable.banner2;
//		bannerPic3.res = R.drawable.banner3;
		rows.add(bannerPic1);
		rows.add(bannerPic2);
//		rows.add(bannerPic3);
		bannerList.add(bannerPic1);
		bannerList.add(bannerPic2);
//		bannerList.add(bannerPic3);
		PosterAdapter adapter = new PosterAdapter(getSupportFragmentManager(),
				bannerList);
		vp_banner.setAdapter(adapter);
		indicator.setViewPager(vp_banner);
	}

	/**
	 * 自动滚动
	 */
	private void autoChange() {
		scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
		// 每隔3秒钟切换一张图片
		scheduledExecutorService.scheduleWithFixedDelay(new ViewPagerTask(), 2,
				3, TimeUnit.SECONDS);
	}

	/**
	 * 自动切换页面任务
	 * 
	 * @author Bruce.Wang
	 * 
	 */
	class ViewPagerTask implements Runnable {

		@Override
		public void run() {
			if (rows == null && rows.size() <= 0) {
				return;
			}
			currentItem = (currentItem + 1) % rows.size();
			handler.post(new Runnable() {

				@Override
				public void run() {
					if (vp_banner.getAdapter() != null) {
						vp_banner.setCurrentItem(currentItem);
						indicator.setViewPager(vp_banner);
						indicator.setCurrentItem(currentItem);
					}
				}
			});
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {

		case R.id.headerleftImg:
			IntentUtil.startActivityForResult(mActivity, CaptureActivity.class,
					16, null);
			break;
		case R.id.mainpage_phone:
			// 多加一个空格来区分跳转页面
			String strArray[] = { "确定要拨打电话吗？", Constants.phonenumber, "确定",
					"取消", "" };
			Bundle bundle = new Bundle();
			bundle.putStringArray(ClearCacheActivity.TAG, strArray);
			IntentUtil.startActivityForResult(mActivity,
					ClearCacheActivity.class, phonecode, bundle);

			break;
		case R.id.mainpage_search:
			IntentUtil.activityForward(mActivity, NewSearchActivity.class,
					null, false);
			//
			break;

		default:
			break;
		}

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_OK) {
			switch (requestCode) {
			case 16:
				if (data != null) {
					String result = data.getStringExtra("result");
					Bundle bundle = new Bundle();
					bundle.putString("pncode", result);
					IntentUtil.activityForward(this, ErWeiMaActivity.class,
							bundle, false);
				}
				break;
			case 102:
				callphone();
				break;

			default:
				break;
			}

		}

	}

	/**
	 * 备件
	 * */
	@SuppressWarnings("unused")
	private class getBestsellerAsyTask extends MyAsyncTask {
		private String interfaceString;
		private BaseAdapter adpter;

		public getBestsellerAsyTask(String interfaceString) {
			super();
			this.interfaceString = interfaceString;
		}

		@SuppressWarnings("unchecked")
		@Override
		protected Object doInBackground(Object... params) {
			return jsonservice.getFirstDataList(interfaceString, null, true,
					MainPageObject.class);
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

				OnlyClass onlyClass = (OnlyClass) result;
				BestSelllist = (ArrayList<MainPageObject>) JSON.parseArray(
						onlyClass.bestseller, MainPageObject.class);
				Hotlist = (ArrayList<MainPageObject>) JSON.parseArray(
						onlyClass.onsale, MainPageObject.class);
				Tuijianlist = (ArrayList<MainPageObject>) JSON.parseArray(
						onlyClass.featured, MainPageObject.class);
				Newlist = (ArrayList<MainPageObject>) JSON.parseArray(
						onlyClass.newArrival, MainPageObject.class);
				mainpagesellAdpter = new mainpagesellAdapter(PartsActivity.this, mImgLoad);
				mainpagehotAdpter = new mainpagehotAdapter(PartsActivity.this, mImgLoad);
				mainpagenewAdpter = new mainpagenewAdapter(PartsActivity.this, mImgLoad);
				maintuijianAdapter = new mainpagesellAdapter(PartsActivity.this, mImgLoad);
				sellgrid.setAdapter(mainpagesellAdpter);
				hotgrid.setAdapter(mainpagehotAdpter);
				recommandgrid.setAdapter(maintuijianAdapter);
				newgrid.setAdapter(mainpagenewAdpter);

					mainpagesellAdpter.setDate(BestSelllist);
					mainpagesellAdpter.notifyDataSetChanged();

					mainpagehotAdpter.setDate(Hotlist);
					mainpagehotAdpter.notifyDataSetChanged();
					
					maintuijianAdapter.setDate(Tuijianlist);
					maintuijianAdapter.notifyDataSetChanged();
					 
					mainpagenewAdpter.setDate(Newlist);
					mainpagenewAdpter.notifyDataSetChanged();
				}

				

			}
		}

	

	// 打电话
	private void callphone() {
		Intent phoneIntent = new Intent("android.intent.action.CALL",
				Uri.parse("tel:" + Constants.phonenumber));
		// 启动
		startActivity(phoneIntent);
	}
}
