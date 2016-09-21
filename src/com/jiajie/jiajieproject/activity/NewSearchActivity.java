/**
 * 
 */
package com.jiajie.jiajieproject.activity;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;

import com.jiajie.jiajieproject.R;
import com.jiajie.jiajieproject.adapter.NewSearchgridAdapter;
import com.jiajie.jiajieproject.adapter.NewSearchlistAdapter;
import com.jiajie.jiajieproject.db.service.SharePreferDB;
import com.jiajie.jiajieproject.utils.IntentUtil;
import com.jiajie.jiajieproject.utils.ToastUtil;
import com.jiajie.jiajieproject.widget.MyListView;

/**
 * 项目名称：NewProject 类名称：NewSearchActivity 类描述： 创建人：王蕾 创建时间：2016-8-8 上午11:16:17
 * 修改备注： 搜索页面
 */
public class NewSearchActivity extends BaseActivity implements OnClickListener,
		OnItemClickListener {
	private GridView seach_gridview;
	private MyListView history_search_list;
	private EditText searchpage_search;
	private ImageView cancle;
	private ImageView search_bottom;
	private View searchHeader, searchFooter;
	private NewSearchlistAdapter NewSearchlistAdapter;
	private NewSearchgridAdapter NewSearchgridAdapter;
	private String searchname;
	public static final String TAG = "NewSearchActivity";
	private SharePreferDB sharePreferDB;
	private ArrayList<String> list = new ArrayList<String>();

	@Override
	protected void onInit(Bundle bundle) {
		// TODO Auto-generated method stub
		super.onInit(bundle);
		setContentView(R.layout.newsearch_layout);
		initView();
	}

	private void initView() {
		sharePreferDB = new SharePreferDB(mContext, "search.db");
		searchHeader = inflater.inflate(R.layout.newsearch_header_layout, null);
		searchFooter = inflater.inflate(R.layout.newsearch_bottom_layout, null);
		seach_gridview = (GridView) searchHeader
				.findViewById(R.id.seach_gridview);
		history_search_list = (MyListView) findViewById(R.id.history_search_list);
		searchpage_search = (EditText) findViewById(R.id.searchpage_search);
		list = MapToList(sharePreferDB.readData());

		NewSearchlistAdapter = new NewSearchlistAdapter(this, mImgLoad, list);
		NewSearchgridAdapter = new NewSearchgridAdapter(this, mImgLoad);
		cancle = (ImageView) findViewById(R.id.cancle);
		search_bottom = (ImageView) searchFooter
				.findViewById(R.id.search_bottom);
		history_search_list.addHeaderView(searchHeader);
		history_search_list.addFooterView(searchFooter);
		history_search_list.setAdapter(NewSearchlistAdapter);
		seach_gridview.setAdapter(NewSearchgridAdapter);
		history_search_list.setOnItemClickListener(this);
//		seach_gridview.setOnItemClickListener(this);
		cancle.setOnClickListener(this);
		search_bottom.setOnClickListener(this);
		if (list.size() > 0) {
			search_bottom.setVisibility(View.VISIBLE);
		} else {
			search_bottom.setVisibility(View.GONE);
		}
		searchpage_search.setOnKeyListener(new OnKeyListener() {

			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				if (keyCode == KeyEvent.KEYCODE_ENTER) {
					// 先隐藏键盘
					((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE))
							.hideSoftInputFromWindow(NewSearchActivity.this
									.getCurrentFocus().getWindowToken(),
									InputMethodManager.HIDE_NOT_ALWAYS);
					// 进行搜索操作的方法，在该方法中可以加入mEditSearchUser的非空判断
					search();
				}
				return false;
			}
		});
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.cancle:
			finish();
			break;
		case R.id.search_bottom:
			// 清除历史记录
			sharePreferDB.deletePreference();
			NewSearchlistAdapter.clearData();
			NewSearchlistAdapter.notifyDataSetChanged();
			search_bottom.setVisibility(View.GONE);
			break;

		default:
			break;
		}

	}

	private void search() {

		String searchContext = searchpage_search.getText().toString().trim();
		if (TextUtils.isEmpty(searchContext)) {
			ToastUtil.showToast(this, "输入框为空，请输入");
		} else {
			// 调用搜索的API方法
			// searchUser(searchContext);
			Bundle bundle = new Bundle();
			bundle.putString(TAG, searchContext);
			Map<String, String> map = sharePreferDB.readData();
			map.put(searchContext, searchContext);
			sharePreferDB.saveData(map);
			IntentUtil.activityForward(mActivity, SearchActivity.class, bundle,
					true);
		}
	}

	/* Map转list */
	private ArrayList<String> MapToList(Map<String, String> map) {
		ArrayList<String> list = new ArrayList<String>();
		Iterator<String> it = map.keySet().iterator();
		while (it.hasNext()) {
			String key = it.next().toString();
			list.add(key);
		}
		return list;

	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		if (list.size() > 0) {

			Bundle bundle = new Bundle();
			bundle.putString(TAG, list.get(arg2-1));
			IntentUtil.activityForward(mActivity, SearchActivity.class, bundle,
					true);
		}

	}
}
