/**
 * 
 */
package com.jiajie.jiajieproject.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView.OnItemClickListener;

import com.jiajie.jiajieproject.R;
import com.jiajie.jiajieproject.activity.BaseActivity.MyAsyncTask;
import com.jiajie.jiajieproject.adapter.PartsAdapter;
import com.jiajie.jiajieproject.adapter.PerfissionServiceAdapter;
import com.jiajie.jiajieproject.contents.InterfaceParams;
import com.jiajie.jiajieproject.model.OnlyClass;
import com.jiajie.jiajieproject.utils.IntentUtil;
import com.jiajie.jiajieproject.utils.PullToRefreshView;
import com.jiajie.jiajieproject.utils.PullToRefreshView.OnFooterRefreshListener;
import com.jiajie.jiajieproject.utils.PullToRefreshView.OnHeaderRefreshListener;
import com.mrwujay.cascade.model.produceClass;
import com.jiajie.jiajieproject.utils.ToastUtil;

/**
 * 项目名称：NewProject 类名称：PerfisionalActivity 类描述： 创建人：王蕾 创建时间：2015-9-22 下午5:29:25
 * 修改备注：
 */
public class PerfisionallistActivity extends BaseActivity implements
		OnItemClickListener, OnFooterRefreshListener, OnHeaderRefreshListener,
		OnClickListener {
	private ImageView headerleftImg;
	private EditText searchedit;
	private TextView searchbutton;
	private PullToRefreshView perfissionservicelayout_pull;
	private ListView perfissionservicelayout_listview;
	private PerfissionServiceAdapter perfissionServiceAdapter;
	private int page = 1;
	private String id;
	@Override
	protected void onInit(Bundle bundle) {
		// TODO Auto-generated method stub
		super.onInit(bundle);
		setContentView(R.layout.perfissionsevicelistlayout);
		if(getIntent().getExtras()!=null){
			id=getIntent().getExtras().getString("id");
		}
		initView();
	}

	/**
	 * 布局加载
	 */
	private void initView() {
		headerleftImg = (ImageView) findViewById(R.id.headerleftImg);
		headerleftImg.setOnClickListener(this);
		perfissionservicelayout_pull = (PullToRefreshView) findViewById(R.id.perfissionservicelayout_pull);
		perfissionservicelayout_listview = (ListView) findViewById(R.id.perfissionservicelayout_listview);
//		perfissionservicelayout_listview
//				.setAdapter(new PerfissionServiceAdapter(mActivity));
		perfissionServiceAdapter=new PerfissionServiceAdapter(mActivity,mImgLoad);
		perfissionservicelayout_listview.setAdapter(perfissionServiceAdapter);
		perfissionservicelayout_pull.setOnFooterRefreshListener(this);
		perfissionservicelayout_pull.setOnHeaderRefreshListener(this);
		perfissionservicelayout_listview.setOnItemClickListener(this);
		setOnScrowListener();
		new PartsAsyTask("", "", page + "").execute();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.headerleftImg:
			finish();
		default:
			break;
		}

	}

	@Override
	public void onHeaderRefresh(PullToRefreshView view) {
		page = 1;
		perfissionServiceAdapter.clearData();
		new PartsAsyTask("", "", page + "").execute();
		view.onHeaderRefreshComplete();
		

	}

	@Override
	public void onFooterRefresh(PullToRefreshView view) {
		++page;
		new PartsAsyTask("", "", page + "").execute();
		view.onFooterRefreshComplete();
	}
	
	/**
	 ** 设置滑动监听
	 */
	private boolean mBusy = false;
	private void setOnScrowListener() {
		perfissionservicelayout_listview
				.setOnScrollListener(new AbsListView.OnScrollListener() {
					@Override
					public void onScrollStateChanged(AbsListView view,
							int scrollState) {
						if (scrollState == OnScrollListener.SCROLL_STATE_FLING
								|| scrollState == OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
							mBusy = true;
						} else {
							mBusy = false;
							perfissionServiceAdapter.notifyDataSetChanged();
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
		IntentUtil.activityForward(mActivity, PerfissionalDetailActivity.class,
				null, false);
	}
	/**
	 * 备件
	 * */
	@SuppressWarnings("unused")
	private class PartsAsyTask extends MyAsyncTask {
		private String sortColumn;

		public PartsAsyTask(String sortColumn, String search, String page) {
			super();
			this.sortColumn = sortColumn;
			this.search = search;
			this.page = page;
		}

		private String search;
		private String page;

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
			map.put("pageSize", "2");
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
				perfissionServiceAdapter.setdata(list);
				perfissionServiceAdapter.notifyDataSetChanged();
			}

		}

	}
	
	
}
