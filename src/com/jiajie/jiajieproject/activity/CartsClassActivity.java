/**
 * 
 */
package com.jiajie.jiajieproject.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jiajie.jiajieproject.R;
import com.jiajie.jiajieproject.adapter.CartsClassAdapter;
import com.jiajie.jiajieproject.contents.Constants;
import com.jiajie.jiajieproject.contents.InterfaceParams;
import com.jiajie.jiajieproject.model.OnlyClass;
import com.jiajie.jiajieproject.utils.CityPoputils;
import com.jiajie.jiajieproject.utils.ClassPoputils;
import com.jiajie.jiajieproject.utils.IntentUtil;
import com.jiajie.jiajieproject.utils.NotificationUtil;
import com.jiajie.jiajieproject.utils.PullToRefreshView;
import com.jiajie.jiajieproject.utils.PullToRefreshView.OnFooterRefreshListener;
import com.jiajie.jiajieproject.utils.PullToRefreshView.OnHeaderRefreshListener;
import com.jiajie.jiajieproject.utils.SlidpopWindowutil;
import com.jiajie.jiajieproject.utils.StringUtil;
import com.jiajie.jiajieproject.utils.ToastUtil;
import com.mrwujay.cascade.model.produceClass;

/**
 * 项目名称：NewProject 类名称：partsclass 类描述： 创建人：王蕾 创建时间：2015-9-9 上午11:55:35 修改备注：
 */
public class CartsClassActivity extends BaseActivity implements
		OnItemClickListener, OnFooterRefreshListener, OnHeaderRefreshListener,
		OnClickListener {
	private Button cartsclassbutton2;
	private ImageView rightImg, leftImage, cartsclassImage1;
	private EditText searchedit;
	private TextView searchbutton, cartsclassbutton1;
	private PullToRefreshView cartsclasslayout_pull;
	private ListView cartsclasslayout_listview;
	private Button cartsclassbutton3;
	private int i = 0;
	private MyHandler myHandler = new MyHandler();
	private String endID;
	private LinearLayout cartsclasslayout1;
	// 价格
	// private ArrayList<CategoriesClass> priceList;
	// // 类型
	// private ArrayList<CategoriesClass> classList;
	// // 种类
	// private ArrayList<CategoriesClass> kindsList;
	// // 品牌
	// private ArrayList<CategoriesClass> cartsList;
	// // 品牌
	private String classid;
	private String kindsid;

	// private String cartsid;
	private CartsClassAdapter cartsClassAdapter;
	private int page = 1;
	private String sort;
	private String sortColumn;
	private String search;

	@Override
	protected void onInit(Bundle bundle) {
		// TODO Auto-generated method stub
		super.onInit(bundle);
		setContentView(R.layout.cartsclasslayout);
		initView();
	}

	/**
	 * 布局加载
	 */
	private void initView() {
		leftImage = (ImageView) findViewById(R.id.leftImage);
		rightImg = (ImageView) findViewById(R.id.rightImg);
		cartsclassImage1 = (ImageView) findViewById(R.id.cartsclassImage1);
		searchedit = (EditText) findViewById(R.id.searchedit);
		searchbutton = (TextView) findViewById(R.id.searchbutton);
		searchbutton.setOnClickListener(this);
		leftImage.setOnClickListener(this);
		rightImg.setOnClickListener(this);
		cartsclasslayout1 = (LinearLayout) findViewById(R.id.cartsclasslayout1);
		cartsclassbutton1 = (TextView) findViewById(R.id.cartsclassbutton1);
		cartsclassbutton2 = (Button) findViewById(R.id.cartsclassbutton2);
		cartsclassbutton3 = (Button) findViewById(R.id.cartsclassbutton3);
		cartsclasslayout1.setOnClickListener(this);
		cartsclassbutton2.setOnClickListener(this);
		cartsclassbutton3.setOnClickListener(this);
		cartsclasslayout_pull = (PullToRefreshView) findViewById(R.id.cartsclasslayout_pull);
		cartsclasslayout_listview = (ListView) findViewById(R.id.cartsclasslayout_listview);
		cartsClassAdapter = new CartsClassAdapter(mActivity, mImgLoad);
		cartsclasslayout_listview.setAdapter(cartsClassAdapter);
		cartsclasslayout_pull.setOnFooterRefreshListener(this);
		cartsclasslayout_pull.setOnHeaderRefreshListener(this);
		cartsclasslayout_listview.setOnItemClickListener(this);
		titlecolorsdeep = getResources().getColorStateList(
				R.color.titlecolorsdeep);
		graycolors = getResources().getColorStateList(R.color.fapiaotextcolor);
		// 加载假数据
		// addData();
		sortColumn = "销量";
		new CartsClassAsyTask(sortColumn, "", page + "", sort,
				Constants.CartsClassCID).execute();

	}

	private Animation myAnimation_Rotate;

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.leftImage:
			finish();
			break;
		case R.id.searchbutton:
			search = searchedit.getText().toString();
			if (!StringUtil.checkStr(search)) {
				ToastUtil.showToast(mActivity, "搜索不能为空");
				return;
			}
			Bundle bundle = new Bundle();
			bundle.putString("search", search);
			bundle.putString("c_id", Constants.CartsClassCID + "");
			IntentUtil.activityForward(mActivity, SearchActivity.class, bundle,
					false);
			break;

		case R.id.cartsclasslayout1:
			sortColumn = "价格";
			search = "";
			cartsclassbutton1.setTextColor(titlecolorsdeep);
			cartsclassbutton3.setTextColor(graycolors);
			cartsclassImage1.setImageResource(R.drawable.jiangxu);
			i++;
			if (i % 2 == 0) {

				myAnimation_Rotate = AnimationUtils.loadAnimation(this,
						R.anim.my_rotate_action2);
				myAnimation_Rotate.setFillAfter(true);
				cartsclassImage1.startAnimation(myAnimation_Rotate);
				sort = "desc";
				page = 1;
				cartsClassAdapter.clearData();
				new CartsClassAsyTask(sortColumn, search, page + "", sort,
						Constants.CartsClassCID).execute();
			} else {
				myAnimation_Rotate = AnimationUtils.loadAnimation(this,
						R.anim.my_rotate_action);
				myAnimation_Rotate.setFillAfter(true);
				cartsclassImage1.startAnimation(myAnimation_Rotate);
				sort = "asc";
				page = 1;
				cartsClassAdapter.clearData();
				new CartsClassAsyTask(sortColumn, search, page + "", sort,
						Constants.CartsClassCID).execute();
			}
			break;
		case R.id.cartsclassbutton3:
			search = "";
			cartsclassbutton3.setTextColor(titlecolorsdeep);
			cartsclassbutton1.setTextColor(graycolors);

			if (i % 2 == 0) {
				cartsclassImage1.setImageResource(R.drawable.triangleup);
				myAnimation_Rotate = AnimationUtils.loadAnimation(this,
						R.anim.my_rotate_action2);
				myAnimation_Rotate.setFillBefore(true);
				cartsclassImage1.startAnimation(myAnimation_Rotate);
			} else {
				cartsclassImage1.setImageResource(R.drawable.triangleup);

			}
			sort = "asc";
			sortColumn = "销量";
			page = 1;
			cartsClassAdapter.clearData();
			new CartsClassAsyTask(sortColumn, search, page + "", sort,
					Constants.CartsClassCID).execute();
			break;
		case R.id.cartsclassbutton2:

			ShaiXuanPop();
			break;
		case R.id.rightImg:
			IntentUtil.startActivityForResult(mActivity, CaptureActivity.class,
					16, null);
			break;
		default:
			break;
		}

	}

	@Override
	public void onHeaderRefresh(PullToRefreshView view) {
		page = 1;
		cartsClassAdapter.clearData();
		new CartsClassAsyTask(sortColumn, search, page + "", sort,
				Constants.CartsClassCID).execute();
		view.onHeaderRefreshComplete();

	}

	@Override
	public void onFooterRefresh(PullToRefreshView view) {

		++page;
		new CartsClassAsyTask(sortColumn, search, page + "", sort,
				Constants.CartsClassCID).execute();
		view.onFooterRefreshComplete();
	}

	/**
	 ** 设置滑动监听
	 */
	private boolean mBusy = false;

	private void setOnScrowListener() {
		cartsclasslayout_listview
				.setOnScrollListener(new AbsListView.OnScrollListener() {
					@Override
					public void onScrollStateChanged(AbsListView view,
							int scrollState) {
						if (scrollState == OnScrollListener.SCROLL_STATE_FLING
								|| scrollState == OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
							mBusy = true;
						} else {
							mBusy = false;
							cartsClassAdapter.notifyDataSetChanged();
						}
						mImgLoad.setBusy(mBusy);
					}

					@Override
					public void onScroll(AbsListView view,
							int firstVisibleItem, int visibleItemCount,
							int totalItemCount) {
					}
				});
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		String produce_id = cartsClassAdapter.getdata().get(arg2).id;
		Bundle bundle = new Bundle();
		bundle.putString("id", produce_id);
		IntentUtil.activityForward(mActivity, GoodsdetailActivity.class,
				bundle, false);
	}

	private PopupWindow popupWindow;
	private TextView shaixuanheaderleftImg, shaixuanheaderright;
	private RelativeLayout shaixuanlayout1, shaixuanlayout2, shaixuanlayout3,
			shaixuanlayout4, shaixuanlayout5, shaixuanlayout6;
	private TextView shaixuanlayout1textview1, shaixuanlayout2textview1,
			shaixuanlayout2textview2, shaixuanlayout3textview1,
			shaixuanlayout4textview1, shaixuanlayout5textview1, headtext;
	private Button shaixuanlayout3button1, shaixuanlayout4button1,
			shaixuanlayout5button1;

	// 筛选侧滑
	private void ShaiXuanPop() {
		popwindowOnclikListiner popOnclikListiner = new popwindowOnclikListiner();
		View view = inflater.inflate(R.layout.shaixuanlayout, null);
		classPoputils = new ClassPoputils(mContext, CartsClassActivity.this,
				inflater);

		headtext = (TextView) view.findViewById(R.id.headtext);
		shaixuanheaderleftImg = (TextView) view
				.findViewById(R.id.shaixuanheaderleftImg);
		shaixuanheaderright = (TextView) view
				.findViewById(R.id.shaixuanheaderright);
		shaixuanlayout1textview1 = (TextView) view
				.findViewById(R.id.shaixuanlayout1textview1);
		shaixuanlayout2textview1 = (TextView) view
				.findViewById(R.id.shaixuanlayout2textview1);
		shaixuanlayout2textview2 = (TextView) view
				.findViewById(R.id.shaixuanlayout2textview2);
		shaixuanlayout3textview1 = (TextView) view
				.findViewById(R.id.shaixuanlayout3textview1);
		shaixuanlayout4textview1 = (TextView) view
				.findViewById(R.id.shaixuanlayout4textview1);
		shaixuanlayout5textview1 = (TextView) view
				.findViewById(R.id.shaixuanlayout5textview1);
		shaixuanlayout1 = (RelativeLayout) view
				.findViewById(R.id.shaixuanlayout1);
		shaixuanlayout2 = (RelativeLayout) view
				.findViewById(R.id.shaixuanlayout2);
		shaixuanlayout3 = (RelativeLayout) view
				.findViewById(R.id.shaixuanlayout3);
		shaixuanlayout4 = (RelativeLayout) view
				.findViewById(R.id.shaixuanlayout4);
		shaixuanlayout5 = (RelativeLayout) view
				.findViewById(R.id.shaixuanlayout5);
		shaixuanlayout3button1 = (Button) view
				.findViewById(R.id.shaixuanlayout3button1);
		shaixuanlayout4button1 = (Button) view
				.findViewById(R.id.shaixuanlayout4button1);
		shaixuanlayout5button1 = (Button) view
				.findViewById(R.id.shaixuanlayout5button1);
		shaixuanlayout6 = (RelativeLayout) view
				.findViewById(R.id.shaixuanlayout6);
		shaixuanheaderleftImg.setOnClickListener(popOnclikListiner);
		shaixuanheaderright.setOnClickListener(popOnclikListiner);
		shaixuanlayout1.setOnClickListener(popOnclikListiner);
		shaixuanlayout2.setOnClickListener(popOnclikListiner);
		shaixuanlayout3button1.setOnClickListener(popOnclikListiner);
		shaixuanlayout4button1.setOnClickListener(popOnclikListiner);
		shaixuanlayout5button1.setOnClickListener(popOnclikListiner);
		shaixuanlayout6.setOnClickListener(popOnclikListiner);
		shaixuanlayout2textview1.setOnClickListener(popOnclikListiner);
		// 已修改
		// // 已修改
		shaixuanlayout2textview2.setOnClickListener(popOnclikListiner);
		// 设置textview的高度使之与通知栏高度一样
		NotificationUtil notificationUtil = new NotificationUtil(mActivity);
		headtext.setHeight(notificationUtil.getNotifyheight());
		popupWindow = SlidpopWindowutil.initPopuptWindow(view, this);

		setPopBackgroud((float) 0.7);

		popupWindow.showAtLocation(leftImage, Gravity.RIGHT, 0, 0);
		headercolors = getResources().getColorStateList(R.color.headercolor);

	}

	private ColorStateList headercolors;
	private ColorStateList graycolors;
	// 深色
	private ColorStateList titlecolorsdeep;
	private CityPoputils cityPoputils;
	private ClassPoputils classPoputils;

	private class popwindowOnclikListiner implements OnClickListener {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.shaixuanheaderleftImg:
				popupWindow.dismiss();
				setPopBackgroud(1);
				break;
			case R.id.shaixuanheaderright:
				page = 1;
				cartsClassAdapter.clearData();
				new CartsClassAsyTask(sortColumn, "", page + "", sort, endID)
						.execute();
				popupWindow.dismiss();
				setPopBackgroud(1);

				break;
			case R.id.shaixuanlayout1:
				// 地址选择暂时不可用
				cityPoputils = new CityPoputils(mContext,
						CartsClassActivity.this, inflater, null,
						shaixuanlayout1textview1);
				cityPoputils.citypoplist(leftImage);
				break;
			case R.id.shaixuanlayout2textview1:
				shaixuanlayout2textview1
						.setBackgroundResource(R.drawable.shaixuanyexuanzhong);
				shaixuanlayout2textview1.setTextColor(headercolors);
				shaixuanlayout2textview2
						.setBackgroundResource(R.drawable.shaixuanyelankuang);
				shaixuanlayout2textview2.setTextColor(graycolors);
				break;
			case R.id.shaixuanlayout2textview2:
				shaixuanlayout2textview2
						.setBackgroundResource(R.drawable.shaixuanyexuanzhong);
				shaixuanlayout2textview2.setTextColor(headercolors);
				shaixuanlayout2textview1
						.setBackgroundResource(R.drawable.shaixuanyelankuang);
				shaixuanlayout2textview1.setTextColor(graycolors);
				break;
			case R.id.shaixuanlayout2:

				break;
			case R.id.shaixuanlayout3button1:
				// 品牌

				classPoputils.classpoplist(myHandler, leftImage, jsonservice,
						Constants.CartsClassCID, 8);
				break;
			case R.id.shaixuanlayout4button1:
				// 类型
				classPoputils.classpoplist(myHandler, leftImage, jsonservice,
						classid, 9);
				break;
			case R.id.shaixuanlayout5button1:
				// 种类
				classPoputils.classpoplist(myHandler, leftImage, jsonservice,
						kindsid, 10);
				break;
			case R.id.shaixuanlayout6:
				// 价格
				// classPoputils.classpoplist(leftImage, jsonservice);
				break;

			default:
				break;
			}

		}

	}

	/** 分类popwindow */
	@SuppressWarnings("unused")
	private void productClassPop() {
		popwindowOnclikListiner popOnclikListiner = new popwindowOnclikListiner();
		View view = inflater.inflate(R.layout.shaixuanlayout, null);
		shaixuanheaderleftImg = (TextView) view
				.findViewById(R.id.shaixuanheaderleftImg);
		shaixuanheaderright = (TextView) view
				.findViewById(R.id.shaixuanheaderright);
		shaixuanlayout1textview1 = (TextView) view
				.findViewById(R.id.shaixuanlayout1textview1);
		shaixuanlayout2textview1 = (TextView) view
				.findViewById(R.id.shaixuanlayout2textview1);
		shaixuanlayout2textview2 = (TextView) view
				.findViewById(R.id.shaixuanlayout2textview2);
		shaixuanlayout3textview1 = (TextView) view
				.findViewById(R.id.shaixuanlayout3textview1);
		shaixuanlayout4textview1 = (TextView) view
				.findViewById(R.id.shaixuanlayout4textview1);
		shaixuanlayout5textview1 = (TextView) view
				.findViewById(R.id.shaixuanlayout5textview1);
		shaixuanlayout1 = (RelativeLayout) view
				.findViewById(R.id.shaixuanlayout1);
		shaixuanlayout2 = (RelativeLayout) view
				.findViewById(R.id.shaixuanlayout2);
		shaixuanlayout3 = (RelativeLayout) view
				.findViewById(R.id.shaixuanlayout3);
		shaixuanlayout4 = (RelativeLayout) view
				.findViewById(R.id.shaixuanlayout4);
		shaixuanlayout5 = (RelativeLayout) view
				.findViewById(R.id.shaixuanlayout5);
		shaixuanheaderleftImg.setOnClickListener(popOnclikListiner);
		shaixuanheaderright.setOnClickListener(popOnclikListiner);
		shaixuanlayout1.setOnClickListener(popOnclikListiner);
		shaixuanlayout2.setOnClickListener(popOnclikListiner);
		shaixuanlayout3.setOnClickListener(popOnclikListiner);
		shaixuanlayout4.setOnClickListener(popOnclikListiner);
		shaixuanlayout5.setOnClickListener(popOnclikListiner);
		popupWindow = SlidpopWindowutil.initPopuptWindow(view, this);
		 popupWindow.showAtLocation(leftImage, Gravity.LEFT, 0, 0);
//		popupWindow.showAsDropDown(leftImage, Gravity.LEFT, 50, 50);
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		if (cityPoputils != null) {
			cityPoputils.dissmissPop();
		}

		if (popupWindow != null)
			popupWindow.dismiss();
	}

	/**
	 * 备件
	 * */
	@SuppressWarnings("unused")
	private class CartsClassAsyTask extends MyAsyncTask {
		private String sortColumn;
		private String id;

		public CartsClassAsyTask(String sortColumn, String search, String page,
				String sort, String id) {
			super();
			this.sortColumn = sortColumn;
			this.search = search;
			this.page = page;
			this.sort = sort;
			this.id = id;
		}

		private String search;
		private String page;
		private String sort;

		// c_id=分类的id、sortColumn=排序的字段、search=搜索的产品名、
		// sort=升序/降序(我这里有默认值为升序，可以不传)、page=当前页数、pageSize=每页显示数
		@SuppressWarnings("unchecked")
		@Override
		protected Object doInBackground(Object... params) {
			Map map = new HashMap<String, String>();
			map.put("c_id", id);
			map.put("sortColumn", sortColumn);
			// map.put("search", search);
			map.put("page", page);
			map.put("pageSize", Constants.goodsize);
			map.put("sort", sort);
			return jsonservice.getDataList(InterfaceParams.getProductsByCid,
					map, false, produceClass.class);
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
			} else if (jsonservice.getsuccessState()) {

				ArrayList<produceClass> list = (ArrayList<produceClass>) result;
				cartsClassAdapter.setdata(list);

				cartsClassAdapter.notifyDataSetChanged();
			}

		}

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == 16 && resultCode == RESULT_OK) {

			if (data != null) {
				String result = data.getStringExtra("result");
				Bundle bundle = new Bundle();
				bundle.putString("pncode", result);
				IntentUtil.activityForward(this, ErWeiMaActivity.class, bundle,
						false);
			}
		}
	}

	class MyHandler extends Handler {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case 8:
				// 类型
				classid = (String) msg.obj;
				endID = classid;
				shaixuanlayout4.setVisibility(View.VISIBLE);
				break;
			case 9:
				// 种类
				kindsid = (String) msg.obj;
				endID = kindsid;
				shaixuanlayout5.setVisibility(View.VISIBLE);
				break;
			case 10:
				// 最终id
				endID = (String) msg.obj;
				break;

			default:
				break;
			}
		}

	}
}
