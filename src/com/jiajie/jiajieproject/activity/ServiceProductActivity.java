/**
 * 
 */
package com.jiajie.jiajieproject.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.content.res.ColorStateList;
import android.graphics.drawable.PaintDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.jiajie.jiajieproject.R;
import com.jiajie.jiajieproject.activity.BaseActivity.MyAsyncTask;
import com.jiajie.jiajieproject.adapter.ServiceProductAdapter;
import com.jiajie.jiajieproject.adapter.PopwindowListAdapter;
import com.jiajie.jiajieproject.adapter.ServiceProductAdapter;
import com.jiajie.jiajieproject.contents.InterfaceParams;
import com.jiajie.jiajieproject.model.OnlyClass;
import com.jiajie.jiajieproject.utils.DisplayUtil;
import com.jiajie.jiajieproject.utils.IntentUtil;
import com.jiajie.jiajieproject.utils.PullToRefreshView;
import com.jiajie.jiajieproject.utils.StringUtil;
import com.jiajie.jiajieproject.utils.PullToRefreshView.OnFooterRefreshListener;
import com.jiajie.jiajieproject.utils.PullToRefreshView.OnHeaderRefreshListener;
import com.mrwujay.cascade.model.produceClass;
import com.jiajie.jiajieproject.utils.ToastUtil;

/**
 * 项目名称：NewProject 类名称：ServiceProductActivity 类描述： 创建人：王蕾 创建时间：2015-9-24
 * 下午4:26:16 修改备注：服务产品列表
 */
public class ServiceProductActivity extends BaseActivity implements
		OnItemClickListener, OnFooterRefreshListener, OnHeaderRefreshListener,
		OnClickListener {

	private ImageView rightImg, leftImage, serviceproductImage1,
			serviceproductImage3;
	private EditText searchedit;
	private TextView searchbutton, serviceproductbutton1;
	private PullToRefreshView serviceproductlayout_pull;
	private ListView serviceproductlayout_listview;
	private Button serviceproductbutton2;
	private int i = 0;
	private int j = 0;
	private LinearLayout serviceproductlayout1, serviceproductlayout3;
	private String id;
	private int page = 1;
	private String sort;
	private String sortColumn;
	private String search;
	private ServiceProductAdapter serviceProductAdapter;
	@Override
	protected void onInit(Bundle bundle) {
		// TODO Auto-generated method stub
		super.onInit(bundle);
		setContentView(R.layout.serviceproductlist_layout);
		if (getIntent().getExtras() != null) {
			id = getIntent().getExtras().getString("id");
		}
		initView();
	}

	/**
	 * 布局加载
	 */
	private void initView() {
		leftImage = (ImageView) findViewById(R.id.fanhui);
		rightImg = (ImageView) findViewById(R.id.rightImg);
		serviceproductImage1 = (ImageView) findViewById(R.id.serviceproductImage1);
		serviceproductImage3 = (ImageView) findViewById(R.id.serviceproductImage3);
		searchedit = (EditText) findViewById(R.id.searchedit);
		searchbutton = (TextView) findViewById(R.id.searchbutton);
		searchbutton.setOnClickListener(this);
		leftImage.setOnClickListener(this);
		serviceproductlayout1 = (LinearLayout) findViewById(R.id.serviceproductlayout1);
		serviceproductbutton1 = (TextView) findViewById(R.id.serviceproductbutton1);
		serviceproductbutton2 = (Button) findViewById(R.id.serviceproductbutton2);
		serviceproductlayout3 = (LinearLayout) findViewById(R.id.serviceproductlayout3);
		serviceproductlayout1.setOnClickListener(this);
		serviceproductlayout3.setOnClickListener(this);
		serviceproductbutton2.setOnClickListener(this);
		serviceproductlayout_pull = (PullToRefreshView) findViewById(R.id.serviceproductlayout_pull);
		serviceproductlayout_listview = (ListView) findViewById(R.id.serviceproductlayout_listview);
		serviceProductAdapter = new ServiceProductAdapter(mActivity, mImgLoad);
		serviceproductlayout_listview.setAdapter(serviceProductAdapter);
		serviceproductlayout_pull.setOnFooterRefreshListener(this);
		serviceproductlayout_pull.setOnHeaderRefreshListener(this);
		serviceproductlayout_listview.setOnItemClickListener(this);
		titlecolorsdeep = getResources().getColorStateList(
				R.color.titlecolorsdeep);
		graycolors = getResources().getColorStateList(R.color.fapiaotextcolor);
		cpuListView();
		setOnScrowListener();
		sortColumn="销量";
		new ServiceProductAsyTask(sortColumn, "", page + "",sort).execute();
	}

	private ColorStateList graycolors;
	// 深色
	private ColorStateList titlecolorsdeep;
	private Animation myAnimation_Rotate;

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.fanhui:
			finish();
			break;
		case R.id.searchbutton:
//			search=searchedit.getText().toString();
//			sortColumn="";
//			page=1;
//			serviceProductAdapter.clearData();
//			new ServiceProductAsyTask(sortColumn,search, page + "","").execute();
			search = searchedit.getText().toString();
			if (!StringUtil.checkStr(search)) {
				ToastUtil.showToast(mActivity, "搜索不能为空");
				return;
			}
			Bundle bundle=new Bundle();
			bundle.putString("search",search);
			bundle.putString("c_id",id);
			IntentUtil.activityForward(mActivity, SearchActivity.class, bundle, false);
			break;
		case R.id.serviceproductlayout1:
			sortColumn="价格";
			search="";
			serviceproductbutton1.setFocusable(true);	
			serviceproductbutton2.setEnabled(true);
			serviceproductbutton1.setTextColor(titlecolorsdeep);
			serviceproductbutton2.setTextColor(graycolors);
			serviceproductImage1.setImageResource(R.drawable.jiangxu);
			i++;
			if (i % 2 == 0) {

				myAnimation_Rotate = AnimationUtils.loadAnimation(this,
						R.anim.my_rotate_action2);
				myAnimation_Rotate.setFillAfter(true);
				serviceproductImage1.startAnimation(myAnimation_Rotate);
				sort="desc";
				page = 1;
				serviceProductAdapter.clearData();
				new ServiceProductAsyTask(sortColumn,search,page+"",sort).execute();
			} else {
				myAnimation_Rotate = AnimationUtils.loadAnimation(this,
						R.anim.my_rotate_action);
				myAnimation_Rotate.setFillAfter(true);
				serviceproductImage1.startAnimation(myAnimation_Rotate);
				sort="asc";
				page = 1;
				serviceProductAdapter.clearData();
				new ServiceProductAsyTask(sortColumn, search,page+"",sort).execute();
			}
			break;
		case R.id.serviceproductbutton2:
			search="";
			serviceproductbutton2.setFocusable(false);	
			serviceproductbutton2.setEnabled(false);	
			serviceproductbutton2.setTextColor(titlecolorsdeep);
			serviceproductbutton1.setTextColor(graycolors);
			
			if (i % 2 == 0) {
				serviceproductImage1.setImageResource(R.drawable.triangleup);
				myAnimation_Rotate = AnimationUtils.loadAnimation(this,
						R.anim.my_rotate_action2);
				myAnimation_Rotate.setFillBefore(true);
				serviceproductImage1.startAnimation(myAnimation_Rotate);
			}else{
				serviceproductImage1.setImageResource(R.drawable.triangleup);
				
			}
			sort="asc";
			sortColumn="销量";
			page = 1;
			serviceProductAdapter.clearData();
			new ServiceProductAsyTask(sortColumn,search,page+"",sort).execute();
			break;
		case R.id.serviceproductlayout3:
			serviceproductlayout3.setFocusable(false);
			serviceproductImage3.setImageResource(R.drawable.xialajiantou);
			popWindow(popListView);

			break;
		default:
			break;
		}

	}
	@Override
	public void onHeaderRefresh(PullToRefreshView view) {
		page = 1;
		serviceProductAdapter.clearData();
		new ServiceProductAsyTask(sortColumn, search, page + "",sort).execute();
		view.onHeaderRefreshComplete();
		

	}

	@Override
	public void onFooterRefresh(PullToRefreshView view) {

		++page;
		new ServiceProductAsyTask(sortColumn,search, page + "",sort).execute();
		view.onFooterRefreshComplete();
	}

	/**
	 ** 设置滑动监听
	 */
	private boolean mBusy = false;

	private void setOnScrowListener() {
		serviceproductlayout_listview
				.setOnScrollListener(new AbsListView.OnScrollListener() {
					@Override
					public void onScrollStateChanged(AbsListView view,
							int scrollState) {
						if (scrollState == OnScrollListener.SCROLL_STATE_FLING
								|| scrollState == OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
							mBusy = true;
						} else {
							mBusy = false;
							serviceProductAdapter.notifyDataSetChanged();
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
		IntentUtil.activityForward(mActivity,
				ServiceProductDetailActivity.class, null, false);
	}

	private PopupWindow popupWindow;

	// 下拉弹框
	private void popWindow(View view) {
		int[] i = DisplayUtil.getDeviceWidthHeight();
		int popheight = i[1] / 6;
		popupWindow = new PopupWindow(view, popheight,
				LayoutParams.WRAP_CONTENT, true);

		popupWindow.setTouchable(true);
		/*
		 * popupWindow.setTouchInterceptor(new OnTouchListener() {
		 * 
		 * @Override public boolean onTouch(View v, MotionEvent event) { if
		 * (popupWindow != null){ popupWindow.dismiss(); }
		 * 
		 * Log.i("mengdd", "onTouch : ");
		 * 
		 * return false; // 这里如果返回true的话，touch事件将被拦截 // 拦截后
		 * PopupWindow的onTouchEvent不被调用，这样点击外部区域无法dismiss } });
		 */

		// 如果不设置PopupWindow的背景，无论是点击外部区域还是Back键都无法dismiss弹框
		// 我觉得这里是API的一个bug
		// popupWindow.setBackgroundDrawable(new PaintDrawable());

		// 设置好参数之后再show
		popupWindow.showAsDropDown(serviceproductlayout3);

	}

	private ListView popListView;

	// 下拉框listview
	private void cpuListView() {
		popListView = (ListView) inflater.inflate(R.layout.poplistview, null);
		popListView.setAdapter(new PopwindowListAdapter(mActivity));

		popListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View view, int pos,
					long arg3) {
				if (popupWindow != null) {
					serviceproductImage3
							.setImageResource(R.drawable.xialajiantoushang);
					serviceproductlayout3.setFocusable(true);
					sortColumn="价格";
					search="";
					page = 1;
					serviceProductAdapter.clearData();
					new ServiceProductAsyTask(sortColumn,search,page+"",sort).execute();
					popupWindow.dismiss();
				}
				Toast.makeText(ServiceProductActivity.this, "点击事件", 2000)
						.show();
			}
		});

	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		if (popupWindow != null) {
			popupWindow.dismiss();
		}
	}
	
	
	@SuppressWarnings("unused")
	private class ServiceProductAsyTask extends MyAsyncTask {
		private String sortColumn;

		public ServiceProductAsyTask(String sortColumn, String search, String page,String sort) {
			super();
			this.sortColumn = sortColumn;
			this.search = search;
			this.page = page;
			this.sort = sort;
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
			map.put("search", search);
			map.put("page", page);
			map.put("pageSize", "3");
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
			}
			if (jsonservice.getsuccessState()) {
				ArrayList<produceClass> list = (ArrayList<produceClass>) result;
				serviceProductAdapter.setdata(list);
				serviceProductAdapter.notifyDataSetChanged();
			}

		}

	}
}
