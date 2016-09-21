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
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.jiajie.jiajieproject.R;
import com.jiajie.jiajieproject.adapter.HistoryBuyAdapter;
import com.jiajie.jiajieproject.contents.InterfaceParams;
import com.jiajie.jiajieproject.model.OnlyClass;
import com.jiajie.jiajieproject.utils.ToastUtil;
import com.jiajie.jiajieproject.widget.MyListView;
import com.jiajie.jiajieproject.widget.ReboundScrollView;
import com.mrwujay.cascade.model.produceClass;

/**
 * 项目名称：NewProject 类名称：HistoryBuyPartsActivity 类描述： 创建人：王蕾 创建时间：2015-10-10
 * 下午3:12:18 修改备注： 已收货
 */
public class HistoryBuyPartsActivity extends BaseActivity implements
		OnClickListener {
	private ImageView headerleftImg;
	private ReboundScrollView Myorderlayout;
	private RelativeLayout no_orderlayout;
	private MyListView historybuyparts_layout_listview;
	private HistoryBuyAdapter HistoryBuyAdapter;

	@Override
	protected void onInit(Bundle bundle) {
		// TODO Auto-generated method stub
		super.onInit(bundle);
		setContentView(R.layout.historybuyparts_layout);
		InitView();
	}

	private void InitView() {
		Myorderlayout = (ReboundScrollView) findViewById(R.id.Myorderlayout);
		no_orderlayout = (RelativeLayout) findViewById(R.id.no_orderlayout);
		historybuyparts_layout_listview = (MyListView) findViewById(R.id.historybuyparts_layout_listview);
		HistoryBuyAdapter = new HistoryBuyAdapter(mActivity, mImgLoad);
		historybuyparts_layout_listview.setAdapter(HistoryBuyAdapter);
		headerleftImg = (ImageView) findViewById(R.id.headerleftImg);
		headerleftImg.setOnClickListener(this);
		new PartsAsyTask().execute();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.headerleftImg:
			finish();
			break;
		default:
			break;
		}

	}

	/**
	 * 备件
	 * */
	@SuppressWarnings("unused")
	private class PartsAsyTask extends MyAsyncTask {
		private String sortColumn;

		public PartsAsyTask() {
			super();
		}

		private String search;
		private String page;

		// c_id=分类的id、sortColumn=排序的字段、search=搜索的产品名、
		// sort=升序/降序(我这里有默认值为升序，可以不传)、page=当前页数、pageSize=每页显示数
		@SuppressWarnings("unchecked")
		@Override
		protected Object doInBackground(Object... params) {
			Map map = new HashMap<String, String>();
			map.put("status", "complete");
			return jsonservice.getDataList(InterfaceParams.orderList, map,
					false, produceClass.class);
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

				if (list.size() > 0) {
					HistoryBuyAdapter.setdata(list);
					HistoryBuyAdapter.notifyDataSetChanged();
					/* 有数据显示列表 */
					Myorderlayout.setVisibility(View.VISIBLE);
					no_orderlayout.setVisibility(View.GONE);
				} else {
					/* 无数据显背景 */
					Myorderlayout.setVisibility(View.GONE);
					no_orderlayout.setVisibility(View.VISIBLE);
				}
			}

		}

	}

}
