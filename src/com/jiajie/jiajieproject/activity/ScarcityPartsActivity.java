/**
 * 
 */
package com.jiajie.jiajieproject.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jiajie.jiajieproject.R;
import com.jiajie.jiajieproject.activity.BaseActivity.MyAsyncTask;
import com.jiajie.jiajieproject.adapter.ScarcityPartsAdapter;
import com.jiajie.jiajieproject.contents.Constants;
import com.jiajie.jiajieproject.contents.InterfaceParams;
import com.jiajie.jiajieproject.model.OnlyClass;
import com.jiajie.jiajieproject.utils.IntentUtil;
import com.jiajie.jiajieproject.utils.PullToRefreshView;
import com.jiajie.jiajieproject.utils.StringUtil;
import com.jiajie.jiajieproject.utils.PullToRefreshView.OnFooterRefreshListener;
import com.jiajie.jiajieproject.utils.PullToRefreshView.OnHeaderRefreshListener;
import com.mrwujay.cascade.model.produceClass;
import com.jiajie.jiajieproject.utils.ToastUtil;

/**
 * 项目名称：NewProject 类名称：scarcitypartsActivity 类描述： 创建人：王蕾 创建时间：2015-9-9
 * 上午11:56:08 修改备注：稀缺备件
 */
public class ScarcityPartsActivity extends BaseActivity implements
		OnItemClickListener, OnFooterRefreshListener, OnHeaderRefreshListener,
		OnClickListener {
	private ImageView leftImage;
	private ImageView rightImg;
	private EditText searchedit;
	private TextView searchbutton;
	private PullToRefreshView scarcitypartslayout_pull;
	private GridView scarcitypartslayout_gridview;
	private ScarcityPartsAdapter scarcityPartsAdapter;
	private int page = 1;
	@Override
	protected void onInit(Bundle bundle) {
		// TODO Auto-generated method stub
		super.onInit(bundle);
		setContentView(R.layout.scarcitypartslayout);
		initView();
	}

	/**
	 * 布局加载
	 */
	private void initView() {
		leftImage = (ImageView) findViewById(R.id.fanhui);
		rightImg = (ImageView) findViewById(R.id.rightImg);
		searchedit = (EditText) findViewById(R.id.searchedit);
		searchbutton = (TextView) findViewById(R.id.searchbutton);
		searchbutton.setOnClickListener(this);
		leftImage.setOnClickListener(this);
		rightImg.setOnClickListener(this);
		scarcitypartslayout_pull = (PullToRefreshView) findViewById(R.id.scarcitypartslayout_pull);
		scarcitypartslayout_gridview = (GridView) findViewById(R.id.scarcitypartslayout_gridview);
		scarcityPartsAdapter = new ScarcityPartsAdapter(mActivity, mImgLoad);
		scarcitypartslayout_gridview.setAdapter(scarcityPartsAdapter);
		scarcitypartslayout_gridview.setOnItemClickListener(this);
		scarcitypartslayout_pull.setOnFooterRefreshListener(this);
		scarcitypartslayout_pull.setOnHeaderRefreshListener(this);
		// 消除gridview黄色边框
		scarcitypartslayout_gridview.setSelector(new ColorDrawable(
				Color.TRANSPARENT));
		new ScarcityPartsAsyncTask("", "", page + "").execute();
	}

	private String search;

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.fanhui:
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
			bundle.putString("c_id", 7 + "");
			IntentUtil.activityForward(mActivity, SearchActivity.class, bundle,
					false);
			// search = searchedit.getText().toString();
			// page = 1;
			// scarcityPartsAdapter.clearData();
			// new ScarcityPartsAsyncTask("", search, page + "").execute();
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
		scarcityPartsAdapter.clearData();
		new ScarcityPartsAsyncTask("", "", page + "").execute();
		view.onHeaderRefreshComplete();

	}

	@Override
	public void onFooterRefresh(PullToRefreshView view) {

		++page;
		new ScarcityPartsAsyncTask("", "", page + "").execute();
		view.onFooterRefreshComplete();
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		String produce_id = scarcityPartsAdapter.getdata().get(arg2).id;
		Bundle bundle = new Bundle();
		bundle.putString("id", produce_id);
		IntentUtil.activityForward(mActivity, GoodsdetailActivity.class,
				bundle, false);
	}

	/**
	 ** 设置滑动监听
	 */
	private boolean mBusy = false;

	private void setOnScrowListener() {
		scarcitypartslayout_gridview
				.setOnScrollListener(new AbsListView.OnScrollListener() {
					@Override
					public void onScrollStateChanged(AbsListView view,
							int scrollState) {
						if (scrollState == OnScrollListener.SCROLL_STATE_FLING
								|| scrollState == OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
							mBusy = true;
						} else {
							mBusy = false;
							scarcityPartsAdapter.notifyDataSetChanged();
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

	/**
	 * 稀缺备件
	 * */
	@SuppressWarnings("unused")
	private class ScarcityPartsAsyncTask extends MyAsyncTask {
		private String sortColumn;

		public ScarcityPartsAsyncTask(String sortColumn, String search,
				String page) {
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
			map.put("c_id", Constants.ScarcityPartsCID);
			map.put("sortColumn", sortColumn);
			// map.put("search", search);
			map.put("page", page);
			map.put("pageSize", "3");
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
				
				scarcityPartsAdapter.setdata(list);
				scarcityPartsAdapter.notifyDataSetChanged();
				
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
