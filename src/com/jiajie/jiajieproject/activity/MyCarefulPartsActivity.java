/**
 * 
 */
package com.jiajie.jiajieproject.activity;

import java.util.ArrayList;
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

import com.jiajie.jiajieproject.MainActivity;
import com.jiajie.jiajieproject.R;
import com.jiajie.jiajieproject.adapter.MyCarefulPartsAdapter;
import com.jiajie.jiajieproject.contents.Constants;
import com.jiajie.jiajieproject.contents.InterfaceParams;
import com.jiajie.jiajieproject.model.OnlyClass;
import com.jiajie.jiajieproject.utils.IntentUtil;
import com.jiajie.jiajieproject.utils.PullToRefreshView;
import com.jiajie.jiajieproject.utils.PullToRefreshView.OnFooterRefreshListener;
import com.jiajie.jiajieproject.utils.PullToRefreshView.OnHeaderRefreshListener;
import com.jiajie.jiajieproject.utils.ToastUtil;
import com.jiajie.jiajieproject.widget.MyGridView;
import com.mrwujay.cascade.model.MainPageObject;

/**
 * 项目名称：NewProject 类名称：MyCarefulParts 类描述： 创建人：王蕾 创建时间：2015-9-29 下午4:44:20 修改备注：
 * 我关注的备件
 */
public class MyCarefulPartsActivity extends BaseActivity implements
		OnItemClickListener, OnClickListener {
	private ImageView headerleftImg;
	private GridView mycarefulpartslayout_gridview;
	private MyCarefulPartsAdapter myCarefulPartsAdapter;
	private boolean isFromcare = true;
	private ArrayList<MainPageObject> list=new ArrayList<MainPageObject>();
	@Override
	protected void onInit(Bundle bundle) {
		// TODO Auto-generated method stub
		super.onInit(bundle);
		setContentView(R.layout.mycarefulparts_layout);
		InitView();
	}

	private void InitView() {
		mycarefulpartslayout_gridview = (GridView) findViewById(R.id.mycarefulpartslayout_gridview);
		myCarefulPartsAdapter = new MyCarefulPartsAdapter(mActivity, mImgLoad);
		mycarefulpartslayout_gridview.setAdapter(myCarefulPartsAdapter);
		mycarefulpartslayout_gridview.setOnItemClickListener(this);
		headerleftImg = (ImageView) findViewById(R.id.headerleftImg);
		// 消除gridview黄色边框
		mycarefulpartslayout_gridview.setSelector(new ColorDrawable(
				Color.TRANSPARENT));
		headerleftImg.setOnClickListener(this);
		// new PartsCarefulAsyTask().execute();
	}

	private void backInitView() {
		setContentView(R.layout.konglayout);
		ImageView kongimage=(ImageView) findViewById(R.id.kongback);
		kongimage.setOnClickListener(this);
		kongimage.setImageResource(R.drawable.nocare_icon);
		findViewById(R.id.headerleftImg).setOnClickListener(this);
	}

	

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		new PartsCarefulAsyTask().execute();	
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.headerleftImg:
			finish();
			break;
		case R.id.kongback:
			// Bundle bundle=new Bundle();
			// bundle.putBoolean("isFromBusiness", true);
			Constants.isfromcarshoping = true;
			IntentUtil.activityForward(this, MainActivity.class, null, true);
			break;
		}

	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
//		String produce_id = myCarefulPartsAdapter.getdata().get(arg2).product_id;
//		String item_id = myCarefulPartsAdapter.getdata().get(arg2).item_id;
//		Bundle bundle = new Bundle();
//		bundle.putString("id", produce_id);
//		bundle.putString("item_id", item_id);
		Bundle bundle = new Bundle();
		bundle.putSerializable(PartsActivity.TAG, list.get(arg2));
		bundle.putBoolean("isFromcare", isFromcare);
		IntentUtil.activityForward(mActivity, GoodsdetailActivity.class,
				bundle, false);
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
			map.put("c_id", Constants.PartsCarefulCID);		
			return jsonservice.getDataList(InterfaceParams.WishList, map,
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
				 list = (ArrayList<MainPageObject>) result;
				if (list.size() > 0) {
				
					myCarefulPartsAdapter.setdata(list);
					myCarefulPartsAdapter.notifyDataSetChanged();
				} else {
					backInitView();
				}
			}
		}

	}

	

}
