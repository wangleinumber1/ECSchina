/**
 * 
 */
package com.jiajie.jiajieproject.activity;

import java.util.ArrayList;
import java.util.Calendar;
import org.apache.http.Header;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import com.alibaba.fastjson.JSON;
import com.amap.api.location.AMapLocation;
import com.jiajie.jiajieproject.R;
import com.jiajie.jiajieproject.adapter.NewProjectMessageAdapter2;
import com.jiajie.jiajieproject.contents.Constants;
import com.jiajie.jiajieproject.contents.DeviceParamsDB;
import com.jiajie.jiajieproject.model.OnlyClass;
import com.jiajie.jiajieproject.model.newprojectmodel;
import com.jiajie.jiajieproject.utils.IntentUtil;
import com.jiajie.jiajieproject.utils.PullToRefreshView;
import com.jiajie.jiajieproject.utils.StringUtil;
import com.jiajie.jiajieproject.utils.PullToRefreshView.OnFooterRefreshListener;
import com.jiajie.jiajieproject.utils.PullToRefreshView.OnHeaderRefreshListener;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.jiajie.jiajieproject.utils.ToastUtil;

/**
 * 项目名称：NewProject 类名称：salespartsAcitivity 类描述： 创建人：王蕾 创建时间：2015-9-9 上午11:55:04
 * 修改备注： 促销备件
 */
public class NewProjectMessageAcitivity2 extends LocationActivity implements
		OnClickListener, OnHeaderRefreshListener, OnFooterRefreshListener {
	private Button leftImg;
	private EditText searchedit;
	private TextView searchbutton;
	private ListView projectmessagelayout_listview;
	private PullToRefreshView newprojectlayout_pull;
	private NewProjectMessageAdapter2 NewprojectmessageAdapter;
	private int page = 1;
	private String nowcity;

	@Override
	protected void onInit(Bundle bundle) {
		// TODO Auto-generated method stub
		super.onInit(bundle);
		setContentView(R.layout.newprojectmessage_layout2);
		initView();
	}

	/**
	 * 布局加载
	 */
	private void initView() {
		leftImg = (Button) findViewById(R.id.leftImg);
		searchedit = (EditText) findViewById(R.id.searchedit);
		searchbutton = (TextView) findViewById(R.id.searchbutton);
		searchbutton.setOnClickListener(this);
		leftImg.setOnClickListener(this);
		newprojectlayout_pull = (PullToRefreshView) findViewById(R.id.newprojectlayout_pull);
		projectmessagelayout_listview = (ListView) findViewById(R.id.projectmessagelayout_listview);
		NewprojectmessageAdapter = new NewProjectMessageAdapter2(mActivity);
		projectmessagelayout_listview.setAdapter(NewprojectmessageAdapter);
		newprojectlayout_pull.setOnFooterRefreshListener(this);
		newprojectlayout_pull.setOnHeaderRefreshListener(this);
		stopLocation();
	
	}


	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		if (StringUtil.checkStr(Constants.LocalProvice)) {
			nowcity = getCityString(Constants.LocalProvice);
			leftImg.setText(nowcity);
			String Message = getNowTime(0) + "/" + nowcity;
			getNetWorkData(Message, NewprojectmessageAdapter, "");
		} else {
			ToastUtil.showToast(mContext, "定位失败请选择城市");
		}

	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		if (resultCode == RESULT_OK) {
			Bundle bundle = data.getExtras();
			String city = bundle.getString("newprojectcity");
			leftImg.setText(city);

			nowcity = city;
			String Message = getNowTime(0) + "/" + nowcity;
			getNetWorkData(Message, NewprojectmessageAdapter, "");
		}

	}

	private String search;

	@Override
	public void onClick(View v) {
		switch (v.getId()) {

		case R.id.searchbutton:
			search = searchedit.getText().toString();
			if (!StringUtil.checkStr(search)) {
				ToastUtil.showToast(mActivity, "搜索不能为空");
				return;
			}
			ToastUtil.showToast(mActivity, "开发中");
			// list = ObjectToArrayList(queryTitleData(search));
			// NewprojectmessageAdapter.setdata(list);
			// NewprojectmessageAdapter.notifyDataSetChanged();
			break;
		case R.id.leftImg:
		
			IntentUtil.startActivityForResult(mActivity,
					NewprojectMessageCityActivity.class, 1, null);
			break;

		default:
			break;
		}

	}

	// 获取定位省份去掉省市后缀
	private String getCityString(String city) {
		String str;
		if (city.length() > 3) {
			str = city.substring(0, 3);
		} else {
			str = city.substring(0, 2);
		}
		return str;

	}

	@Override
	public void onFooterRefresh(PullToRefreshView view) {
		if (StringUtil.checkStr(leftImg.getText().toString())) {
			nowcity = leftImg.getText().toString();
			page = page++;
			String Message = getNowTime(page) + "/" + nowcity;
			getNetWorkData(Message, NewprojectmessageAdapter, "");
		} else {
			ToastUtil.showToast(mContext, "定位失败请选择城市");
		}
		view.onFooterRefreshComplete();
	}

	@Override
	public void onHeaderRefresh(PullToRefreshView view) {
		if (StringUtil.checkStr(leftImg.getText().toString())) {
			nowcity = leftImg.getText().toString();
			page = 0;
			String Message = getNowTime(page) + "/" + nowcity;
			getNetWorkData(Message, NewprojectmessageAdapter, "");
		} else {
			ToastUtil.showToast(mContext, "定位失败请选择城市");
		}
		view.onHeaderRefreshComplete();
	}

	/*
	 * 获取时间， 参数为天
	 */
	@SuppressWarnings("unused")
	private String getNowTime(int time) {
		Calendar now = Calendar.getInstance();
		String month;
		if ((now.get(Calendar.MONTH) + "").length() < 2) {
			month = "0" + (now.get(Calendar.MONTH) + 1);
		} else {
			month = (now.get(Calendar.MONTH) + 1) + "";
		}
		return (now.get(Calendar.YEAR) + "" + month
				+ (now.get(Calendar.DAY_OF_MONTH)) + "");

	}

	// 网络获取数据
	@SuppressWarnings("unused")
	private void getNetWorkData(String url2,
			final NewProjectMessageAdapter2 newProjectMessageAdapter2,
			String search) {

		String url = "http://106.120.89.226:5000/svs/" + url2;
		AsyncHttpClient client = new AsyncHttpClient();
		client.addHeader("Cookie", DeviceParamsDB.cookie);
		client.setTimeout(200000);
		client.post(url, null, new AsyncHttpResponseHandler() {

			@Override
			public void onFailure(int arg0, Header[] arg1, byte[] arg2,
					Throwable arg3) {
				// TODO Auto-generated method stub
				ToastUtil.showToast(NewProjectMessageAcitivity2.this,
						"连接服务器失败....");
			}

			@Override
			public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
				JSONObject jsonObject;
				String str = new String(arg2);
				OnlyClass onlyClass = JSON.parseObject(new String(arg2),
						OnlyClass.class);
				if (onlyClass.success) {

					ArrayList<newprojectmodel> list = (ArrayList<newprojectmodel>) JSON
							.parseArray(onlyClass.datas, newprojectmodel.class);
					newProjectMessageAdapter2.setdata(list);
					newProjectMessageAdapter2.notifyDataSetChanged();
				}
			}
		});

	}
}
