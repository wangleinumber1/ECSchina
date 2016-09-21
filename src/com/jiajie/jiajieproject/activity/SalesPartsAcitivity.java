/**
 * 
 */
package com.jiajie.jiajieproject.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jiajie.jiajieproject.R;
import com.jiajie.jiajieproject.activity.BaseActivity.MyAsyncTask;
import com.jiajie.jiajieproject.adapter.PartsAdapter;
import com.jiajie.jiajieproject.adapter.SalesPartsAdapter;
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
 * 项目名称：NewProject   
 * 类名称：salespartsAcitivity   
 * 类描述：   
 * 创建人：王蕾
 * 创建时间：2015-9-9 上午11:55:04   
 * 修改备注：    促销备件
 */
public class SalesPartsAcitivity extends BaseActivity implements OnItemClickListener,OnFooterRefreshListener, OnHeaderRefreshListener, OnClickListener{
	private ImageView rightImg;
	private ImageView leftImage;
	private EditText searchedit;
	private TextView searchbutton;
	private PullToRefreshView salespartslayout_pull;
	private ListView salespartslayout_listview;
	private int page = 1;
	private SalesPartsAdapter salesPartsAdapter;
	
	@Override
	protected void onInit(Bundle bundle) {
		// TODO Auto-generated method stub
		super.onInit(bundle);
		setContentView(R.layout.salespartslayout);
		initView();
	}

	/**
	 * 布局加载
	 */
	private void initView() {
//		backloading_layout=(RelativeLayout) findViewById(R.id.backloading_layout);
//		loadingMore=(TextView) findViewById(R.id.loadingMore);
		rightImg = (ImageView) findViewById(R.id.rightImg);
		leftImage=(ImageView) findViewById(R.id.fanhui);
		searchedit=(EditText) findViewById(R.id.searchedit);
		searchbutton=(TextView) findViewById(R.id.searchbutton);
		searchbutton.setOnClickListener(this);
//		loadingMore.setOnClickListener(this);
		leftImage.setOnClickListener(this);
		rightImg.setOnClickListener(this);
		salespartslayout_pull = (PullToRefreshView) findViewById(R.id.salespartslayout_pull);
		salespartslayout_listview = (ListView) findViewById(R.id.salespartslayout_listview);
		salesPartsAdapter=new SalesPartsAdapter(mActivity, mImgLoad);
		salespartslayout_listview.setAdapter(salesPartsAdapter);
		salespartslayout_pull.setOnFooterRefreshListener(this);
		salespartslayout_pull.setOnHeaderRefreshListener(this);
		salespartslayout_listview.setOnItemClickListener(this);
		new PartsAsyTask("", "", page + "").execute();
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
			Bundle bundle=new Bundle();
			bundle.putString("search",search);
			bundle.putString("c_id",Constants.SalesPartsCID);
			IntentUtil.activityForward(mActivity, SearchActivity.class, bundle, false);
//			search=searchedit.getText().toString();
//			page=1;
//			salesPartsAdapter.clearData();
//			new PartsAsyTask("",search, page + "").execute();
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
		salesPartsAdapter.clearData();
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
			String produce_id=salesPartsAdapter.getdata().get(arg2).id;
			Bundle bundle=new Bundle();
			bundle.putString("id", produce_id);
			IntentUtil.activityForward(mActivity, GoodsdetailActivity.class, bundle,
					false);
	}
	/**
	 * 促销备件
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
			map.put("c_id", Constants.SalesPartsCID);
			map.put("sortColumn", sortColumn);
//			map.put("search", search);
			map.put("page", page);
			map.put("pageSize", Constants.goodsize);
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
				IntentUtil.activityForward(this, ErWeiMaActivity.class,
						bundle, false);
			}
		}
		
	}
}
