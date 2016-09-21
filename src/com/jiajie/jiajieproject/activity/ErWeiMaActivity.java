/**
 * 
 */
package com.jiajie.jiajieproject.activity;

import java.util.HashMap;
import java.util.Map;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.jiajie.jiajieproject.R;
import com.jiajie.jiajieproject.adapter.MyCarefulPartsAdapter;
import com.jiajie.jiajieproject.contents.InterfaceParams;
import com.jiajie.jiajieproject.model.OnlyClass;
import com.jiajie.jiajieproject.utils.ToastUtil;

/**
 * 项目名称：NewProject 类名称：ErWeiMaActivity 类描述： 创建人：王蕾 创建时间：2015-11-25 上午10:52:16
 * 修改备注：
 */
public class ErWeiMaActivity extends BaseActivity implements
		OnItemClickListener, OnClickListener {
	private ImageView headerleftImg;
	// private PullToRefreshView mycarefulpartslayout_pull;
	private GridView mycarefulpartslayout_gridview;
	private MyCarefulPartsAdapter myCarefulPartsAdapter;
	private TextView headercentertextview;
	private String pncode;

	// private int page = 1;
	@Override
	protected void onInit(Bundle bundle) {
		// TODO Auto-generated method stub
		super.onInit(bundle);
		setContentView(R.layout.mycarefulparts_layout);
		if (getIntent().getExtras() != null) {
			pncode = getIntent().getExtras().getString("pncode");
		}
		InitView();
	}

	private void InitView() {
		// mycarefulpartslayout_pull = (PullToRefreshView)
		// findViewById(R.id.mycarefulpartslayout_pull);
		mycarefulpartslayout_gridview = (GridView) findViewById(R.id.mycarefulpartslayout_gridview);
		myCarefulPartsAdapter = new MyCarefulPartsAdapter(mActivity, mImgLoad);
		mycarefulpartslayout_gridview.setAdapter(myCarefulPartsAdapter);
		mycarefulpartslayout_gridview.setOnItemClickListener(this);
		// mycarefulpartslayout_pull.setOnFooterRefreshListener(this);
		// mycarefulpartslayout_pull.setOnHeaderRefreshListener(this);
		headerleftImg = (ImageView) findViewById(R.id.headerleftImg);
		headercentertextview = (TextView) findViewById(R.id.headercentertextview);
		headercentertextview.setText("二维码列表");
		// 消除gridview黄色边框
		mycarefulpartslayout_gridview.setSelector(new ColorDrawable(
				Color.TRANSPARENT));
		headerleftImg.setOnClickListener(this);
		new PartsCarefulAsyTask().execute();
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

	// @Override
	// public void onHeaderRefresh(PullToRefreshView view) {
	// page = 1;
	// myCarefulPartsAdapter.clearData();
	// new PartsCarefulAsyTask("", "", page + "").execute();
	// view.onHeaderRefreshComplete();
	//
	//
	// }
	//
	// @Override
	// public void onFooterRefresh(PullToRefreshView view) {
	//
	// ++page;
	// new PartsCarefulAsyTask("", "", page + "").execute();
	// view.onFooterRefreshComplete();
	// }
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// String
		// produce_id=myCarefulPartsAdapter.getdata().get(arg2).product_id;
		// String item_id=myCarefulPartsAdapter.getdata().get(arg2).item_id;
		// Bundle bundle=new Bundle();
		// bundle.putString("id", produce_id);
		// bundle.putString("item_id", item_id);
		// bundle.putBoolean("isFromcare", isFromcare);
		// IntentUtil.activityForward(mActivity, GoodsdetailActivity.class,
		// bundle,false);
	}

	/**
	 * 我关注的备件
	 * */
	@SuppressWarnings("unused")
	private class PartsCarefulAsyTask extends MyAsyncTask {

		public PartsCarefulAsyTask() {
			super();
		}

		// c_id=分类的id、sortColumn=排序的字段、search=搜索的产品名、
		// sort=升序/降序(我这里有默认值为升序，可以不传)、page=当前页数、pageSize=每页显示数
		@SuppressWarnings("unchecked")
		@Override
		protected Object doInBackground(Object... params) {
			Map map = new HashMap<String, String>();
			map.put("pn_code", pncode);
			return jsonservice.getDataList(InterfaceParams.getProductByScan,
					map, false, null);
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
			// if (jsonservice.getsuccessState()) {
			// ArrayList<produceClass> list = (ArrayList<produceClass>) result;
			// myCarefulPartsAdapter.setdata(list);
			// myCarefulPartsAdapter.notifyDataSetChanged();
			// }

		}

	}

}
