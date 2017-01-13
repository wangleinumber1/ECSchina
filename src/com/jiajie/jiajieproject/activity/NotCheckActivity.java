/**
 * 
 */
package com.jiajie.jiajieproject.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.jiajie.jiajieproject.R;
import com.jiajie.jiajieproject.adapter.NewNotcheckAdapter;
import com.jiajie.jiajieproject.contents.InterfaceParams;
import com.jiajie.jiajieproject.model.OnlyClass;
import com.mrwujay.cascade.model.produceClass;
import com.jiajie.jiajieproject.utils.ToastUtil;
import com.jiajie.jiajieproject.widget.MyListView;
import com.jiajie.jiajieproject.widget.ReboundScrollView;

/**
 * 项目名称：NewProject 类名称：NotCheckActivity 类描述： 创建人：王蕾 创建时间：2015-10-14 上午11:44:06
 * 修改备注： 待收货
 */
public class NotCheckActivity extends BaseActivity implements OnClickListener {
	private ImageView headerleftImg;
	private MyListView notcheck_layout_listview;
	private NewNotcheckAdapter NewNotcheckAdapter;
	private ReboundScrollView notchecklayout;
	private RelativeLayout no_orderlayout;
	private Handler myHandler=new Myhandler();
	@Override
	protected void onInit(Bundle bundle) {
		// TODO Auto-generated method stub
		super.onInit(bundle);
		setContentView(R.layout.notcheck_layout);
		InitView();
	}

	private void InitView() {
		notchecklayout = (ReboundScrollView) findViewById(R.id.notchecklayout);
		no_orderlayout = (RelativeLayout) findViewById(R.id.no_orderlayout);
		headerleftImg = (ImageView) findViewById(R.id.headerleftImg);
		notcheck_layout_listview = (MyListView) findViewById(R.id.notcheck_layout_listview);
		NewNotcheckAdapter = new NewNotcheckAdapter(mActivity, mImgLoad,jsonservice,myHandler);
		notcheck_layout_listview.setAdapter(NewNotcheckAdapter);
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
			map.put("status", "processing");
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
					NewNotcheckAdapter.setdata(list);
					NewNotcheckAdapter.notifyDataSetChanged();
					/* 有数据显示列表 */
					notchecklayout.setVisibility(View.VISIBLE);
					no_orderlayout.setVisibility(View.GONE);
				} else {
					/* 无数据显背景 */
					notchecklayout.setVisibility(View.GONE);
					no_orderlayout.setVisibility(View.VISIBLE);
				}
			}
		}

	}
	
	
	@SuppressWarnings("unused")
	private class Myhandler extends Handler {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			switch (msg.what) {
			case 1:
				new PartsAsyTask().execute();
				break;
			

		}
		}
	}
}
