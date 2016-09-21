package com.jiajie.jiajieproject.Fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AbsListView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.jiajie.jiajieproject.R;
import com.jiajie.jiajieproject.activity.ServiceProductActivity;
import com.jiajie.jiajieproject.adapter.CartsClassAdapter;
import com.jiajie.jiajieproject.adapter.GoldFragmentadapter;
import com.jiajie.jiajieproject.contents.InterfaceParams;
import com.jiajie.jiajieproject.model.OnlyClass;
import com.jiajie.jiajieproject.model.Type;
import com.jiajie.jiajieproject.net.service.JosnService;
import com.jiajie.jiajieproject.utils.IntentUtil;
import com.jiajie.jiajieproject.utils.MyAsyncTask;
import com.jiajie.jiajieproject.utils.PullToRefreshView;
import com.jiajie.jiajieproject.utils.PullToRefreshView.OnFooterRefreshListener;
import com.jiajie.jiajieproject.utils.PullToRefreshView.OnHeaderRefreshListener;
import com.mrwujay.cascade.model.produceClass;
import com.jiajie.jiajieproject.utils.ToastUtil;

public class GoldFragment extends BaseFragment implements OnFooterRefreshListener, OnHeaderRefreshListener{
	private GoldFragmentadapter adapter;
	private Type type;
	private PullToRefreshView goldclasslayout_pull;
	private GridView goldclasslayout_gridview;
	private String typename;
	private String id;
	private JosnService jsonservice;
	private GoldFragmentadapter goldFragmentadapter;
	private int page=1;
	private String sortColumn;
	private String search;
	ArrayList<produceClass> list;
	@Override
	protected void initView() {
		jsonservice=new JosnService(mActivity);
		goldclasslayout_pull = (PullToRefreshView) findViewById(R.id.goldlayout_pull);
		goldclasslayout_gridview = (GridView) findViewById(R.id.goldgridView);
		goldFragmentadapter = new GoldFragmentadapter(mActivity, mImgLoad);
		goldclasslayout_gridview.setAdapter(goldFragmentadapter);
		goldclasslayout_pull.setOnFooterRefreshListener(this);
		goldclasslayout_pull.setOnHeaderRefreshListener(this);	
		typename = getArguments().getString("typename");
		id = getArguments().getString("id");
		((TextView) findViewById(R.id.toptype)).setText(typename);
		//消除gridview黄色边框
		goldclasslayout_gridview.setSelector(new ColorDrawable(Color.TRANSPARENT));
		goldclasslayout_gridview.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Bundle bundle=new Bundle();
				bundle.putString("id", list.get(arg2).id);
				IntentUtil.activityForward(getActivity(),ServiceProductActivity.class , bundle, false);
			}
		});
		new GoldAsyTask( page + "").execute();
		setOnScrowListener();
	}

	
	@Override
	protected int setContentView() {
		// TODO Auto-generated method stub
		return R.layout.goldfragment;
	}


	
	
	
	@Override
	public void onHeaderRefresh(PullToRefreshView view) {
		page = 1;
		goldFragmentadapter.clearData();
		new GoldAsyTask( page + "").execute();
		view.onHeaderRefreshComplete();
		

	}

	@Override
	public void onFooterRefresh(PullToRefreshView view) {

		++page;
		new GoldAsyTask( page + "").execute();
		view.onFooterRefreshComplete();
	}

	/**
	 ** 设置滑动监听
	 */
	private boolean mBusy = false;

	private void setOnScrowListener() {
		goldclasslayout_gridview
				.setOnScrollListener(new AbsListView.OnScrollListener() {
					@Override
					public void onScrollStateChanged(AbsListView view,
							int scrollState) {
						if (scrollState == OnScrollListener.SCROLL_STATE_FLING
								|| scrollState == OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
							mBusy = true;
						} else {
							mBusy = false;
							goldFragmentadapter.notifyDataSetChanged();
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
	 * 金牌服务
	 * */
	@SuppressWarnings("unused")
	private class GoldAsyTask extends AsyncTask {
		public GoldAsyTask( String page) {
			this.page = page;
		}

		private String page;

		// c_id=分类的id、sortColumn=排序的字段、search=搜索的产品名、
		// sort=升序/降序(我这里有默认值为升序，可以不传)、page=当前页数、pageSize=每页显示数
		@SuppressWarnings("unchecked")
		@Override
		protected Object doInBackground(Object... params) {
			Map map = new HashMap<String, String>();
			map.put("c_id", id);
			map.put("sortColumn", "");
			map.put("search", "");
			map.put("page", page);
			map.put("pageSize", "2");
			return jsonservice.getDataList(InterfaceParams.getProductsByCid,
					map, false, produceClass.class);
		}

		@SuppressWarnings("unchecked")
		@Override
		protected void onPostExecute(Object result) {
			if (result == null) {
				return;
			}

			if (jsonservice.getToastMessage()) {
				OnlyClass onlyClass = (OnlyClass) result;
				ToastUtil.showToast(mActivity, onlyClass.data);
			}
			if (jsonservice.getsuccessState()) {
				list = (ArrayList<produceClass>) result;
				goldFragmentadapter.setdata(list);
				goldFragmentadapter.notifyDataSetChanged();
			}

		}

	}


}
