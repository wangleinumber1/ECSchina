/**
 * 
 */
package com.jiajie.jiajieproject.activity;

import java.util.ArrayList;

import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.jiajie.jiajieproject.R;
import com.jiajie.jiajieproject.adapter.AdressManageAdapter;
import com.jiajie.jiajieproject.contents.InterfaceParams;
import com.jiajie.jiajieproject.model.AdressClass;
import com.jiajie.jiajieproject.model.OnlyClass;
import com.jiajie.jiajieproject.utils.IntentUtil;
import com.jiajie.jiajieproject.utils.ToastUtil;
import com.jiajie.jiajieproject.widget.MyListView;
import com.jiajie.jiajieproject.widget.ReboundScrollView;

/**
 * 项目名称：NewProject 类名称：AdressManageActivity 类描述： 创建人：王蕾 创建时间：2015-10-8 下午5:26:22
 * 修改备注：
 */
public class AdressManageActivity extends BaseActivity implements
		OnClickListener {
	private MyListView adressmanage_layout_listview;
	ImageView headerleftImg;
	private ImageView addAdress;
	private ReboundScrollView adressListLayout;
	private Myhandler myhandler = new Myhandler();
	private AdressManageAdapter adressManageAdapter;
	private RelativeLayout noadress_back;

	@Override
	protected void onInit(Bundle bundle) {
		// TODO Auto-generated method stub
		super.onInit(bundle);
		setContentView(R.layout.adressmanage_layout);
		headerleftImg = (ImageView) findViewById(R.id.headerleftImg);
		noadress_back = (RelativeLayout) findViewById(R.id.noadress_back);
		adressListLayout = (ReboundScrollView) findViewById(R.id.adressListLayout);
		addAdress = (ImageView) findViewById(R.id.addAdress);
		adressmanage_layout_listview = (MyListView) findViewById(R.id.adressmanage_layout_listview);
		adressManageAdapter = new AdressManageAdapter(mActivity, jsonservice,
				myhandler);
		adressmanage_layout_listview.setAdapter(adressManageAdapter);
		headerleftImg.setOnClickListener(this);
		addAdress.setOnClickListener(this);

	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		new AdressListAsyTask().execute();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.headerleftImg:
			finish();
			break;
		case R.id.addAdress:
			IntentUtil.activityForward(mActivity, AddAdressActivity.class,
					null, false);
			break;

		default:
			break;
		}

	}

	/**
	 * 地址列表
	 * */
	@SuppressWarnings("unused")
	private class AdressListAsyTask extends MyAsyncTask {

		public AdressListAsyTask() {
			super();
		}

		@SuppressWarnings("unchecked")
		@Override
		protected Object doInBackground(Object... params) {
			return jsonservice.getDataList(InterfaceParams.listAddress, null,
					false, AdressClass.class);
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
				ArrayList<AdressClass> list = (ArrayList<AdressClass>) result;
				if (list.size() > 0) {
					adressManageAdapter.setdata(list);
					adressManageAdapter.notifyDataSetChanged();
					adressListLayout.setVisibility(View.VISIBLE);
					noadress_back.setVisibility(View.GONE);
				} else {
					adressListLayout.setVisibility(View.GONE);
					noadress_back.setVisibility(View.VISIBLE);
				}

			}

		}

	}

	private class Myhandler extends Handler {

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 10:
				new AdressListAsyTask().execute();
				break;

			default:
				break;
			}

		}

	}

}
