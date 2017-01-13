/**
 * 
 */
package com.jiajie.jiajieproject.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.jiajie.jiajieproject.R;
import com.jiajie.jiajieproject.adapter.SalesPartsAdapter;
import com.jiajie.jiajieproject.contents.Constants;
import com.jiajie.jiajieproject.contents.InterfaceParams;
import com.jiajie.jiajieproject.model.OnlyClass;
import com.jiajie.jiajieproject.utils.IntentUtil;
import com.jiajie.jiajieproject.utils.PullToRefreshView;
import com.jiajie.jiajieproject.utils.PullToRefreshView.OnFooterRefreshListener;
import com.jiajie.jiajieproject.utils.PullToRefreshView.OnHeaderRefreshListener;
import com.mrwujay.cascade.model.MainPageObject;
import com.mrwujay.cascade.model.produceClass;
import com.jiajie.jiajieproject.utils.ToastUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView.OnItemClickListener;

/**
 * 项目名称：NewProject 类名称：SearchActivity 类描述： 创建人：王蕾 创建时间：2015-11-30 上午10:23:27
 * 修改备注：
 */
public class SearchActivity extends BaseActivity implements
		OnItemClickListener, OnFooterRefreshListener, OnHeaderRefreshListener,
		OnClickListener {
	private ImageView leftImage, headercentertextview;
	private PullToRefreshView salespartslayout_pull;
	private ListView salespartslayout_listview;
	private int page = 1;
	private SalesPartsAdapter salesPartsAdapter;
	private String searchname;
	

	@Override
	protected void onInit(Bundle bundle) {
		// TODO Auto-generated method stub55
		super.onInit(bundle);
		setContentView(R.layout.searchlayout);
		Bundle bundle2 = getIntent().getExtras();
		searchname=bundle2.getString(NewSearchActivity.TAG);
		initView();
	}

	/**
	 * 布局加载
	 */
	private void initView() {
		
		leftImage = (ImageView) findViewById(R.id.headerleftImg);
		headercentertextview = (ImageView) findViewById(R.id.headercentertextview);
		leftImage.setOnClickListener(this);
		headercentertextview.setOnClickListener(this);
		salespartslayout_pull = (PullToRefreshView) findViewById(R.id.salespartslayout_pull);
		salespartslayout_listview = (ListView) findViewById(R.id.salespartslayout_listview);
		salesPartsAdapter = new SalesPartsAdapter(mActivity, mImgLoad);
		salespartslayout_listview.setAdapter(salesPartsAdapter);
		salespartslayout_pull.setOnFooterRefreshListener(this);
		salespartslayout_pull.setOnHeaderRefreshListener(this);
		salespartslayout_listview.setOnItemClickListener(this);
		new SearchAsyTask(page + "").execute();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.headerleftImg:
			finish();
			break;
		case R.id.headercentertextview:
			IntentUtil.activityForward(mActivity, NewSearchActivity.class,
					null, true);
			break;
	
		default:
			break;
		}

	}

	@Override
	public void onHeaderRefresh(PullToRefreshView view) {
		page = 1;
		salesPartsAdapter.clearData();
		new SearchAsyTask(page + "").execute();
		view.onHeaderRefreshComplete();

	}

	@Override
	public void onFooterRefresh(PullToRefreshView view) {

		++page;
		new SearchAsyTask(page + "").execute();
		view.onFooterRefreshComplete();
	}

	/**
	 ** 设置滑动监听
	 */
	private boolean mBusy = false;

	private void setOnScrowListener() {
		salespartslayout_listview
				.setOnScrollListener(new AbsListView.OnScrollListener() {
					@Override
					public void onScrollStateChanged(AbsListView view,
							int scrollState) {
						if (scrollState == OnScrollListener.SCROLL_STATE_FLING
								|| scrollState == OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
							mBusy = true;
						} else {
							mBusy = false;
							salesPartsAdapter.notifyDataSetChanged();
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
		String produce_id = salesPartsAdapter.getdata().get(arg2).id;
		Bundle bundle = new Bundle();
		bundle.putString("id", produce_id);
		IntentUtil.activityForward(mActivity, GoodsdetailActivity.class,
				bundle, false);
	
		
	}

	/**
	 * 促销备件
	 * */
	@SuppressWarnings("unused")
	private class SearchAsyTask extends MyAsyncTask {
		// private String sortColumn;

		public SearchAsyTask(String page) {
			super();
			// this.sortColumn = sortColumn;
			this.page = page;
		}

		private String page;

		// c_id=分类的id、sortColumn=排序的字段、search=搜索的产品名、
		// sort=升序/降序(我这里有默认值为升序，可以不传)、page=当前页数、pageSize=每页显示数
		@SuppressWarnings("unchecked")
		@Override
		protected Object doInBackground(Object... params) {
			Map map = new HashMap<String, String>();
			map.put("c_id", Constants.PartsCID);
			// map.put("sortColumn", sortColumn);
			map.put("search", searchname);
			map.put("page", page);
			map.put("pageSize", Constants.goodsize);
			return jsonservice.getDataList(InterfaceParams.searchProducts, map,
					false, MainPageObject.class);
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
				ArrayList<MainPageObject> list = (ArrayList<MainPageObject>) result;
				salesPartsAdapter.setdata(list);
				salesPartsAdapter.notifyDataSetChanged();
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

}
