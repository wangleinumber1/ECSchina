/**
 * 
 */
package com.jiajie.jiajieproject.activity;

import java.util.ArrayList;

import com.amap.api.location.AMapLocation;
import com.city.list.main.CityModel;
import com.jiajie.jiajieproject.R;
import com.jiajie.jiajieproject.activity.CityListActivity.CityListOnItemClick;
import com.jiajie.jiajieproject.adapter.NewProjectMessageAdapter;
import com.jiajie.jiajieproject.adapter.NewprojectMessageCityAdapter;
import com.jiajie.jiajieproject.db.manager.DBManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

/**
 * 项目名称：NewProject 类名称：NewprojectMessageActivity 类描述： 创建人：王蕾 创建时间：2016-3-25
 * 上午11:49:48 修改备注：
 */
public class NewprojectMessageCityActivity extends LocationActivity {
	private View view;
	private TextView locationcity;
	private Button leftImg;
	private ListView mCityLit;
	private ArrayList<String> list;
	private NewprojectMessageCityAdapter newprojectMessageCityAdapter;
	private String[] citys = { "北京", "天津", "河北", "山西", "内蒙古", "辽宁", "大连", "吉林",
			"黑龙江", "上海", "江苏", "浙江", "宁波", "安徽", "福建", "厦门", "江西", "山东", "青岛",
			"河南", "湖北", "湖南", "广东", "深圳", "广西", "海南", "重庆", "四川", "贵州", "云南",
			"西藏", "陕西", "甘肃", "青海", "宁夏", "新疆", "台湾" };

	@Override
	protected void onInit(Bundle bundle) {
		super.onInit(bundle);
		setContentView(R.layout.newprojectcity_layout);
		initView();
	}

	/**
	 * 布局加载
	 */
	private void initView() {

		view = inflater.inflate(R.layout.citylist_headerlayout, null);
		leftImg = (Button) findViewById(R.id.cityleftImg);
		locationcity = (TextView) view.findViewById(R.id.locationcity);
		mCityLit = (ListView) findViewById(R.id.city_list);
		mCityLit.addHeaderView(view);
		mCityLit.setOnItemClickListener(new CityListOnItemClick());
		list = StringToArray();
		newprojectMessageCityAdapter = new NewprojectMessageCityAdapter(
				inflater, list);
		mCityLit.setAdapter(newprojectMessageCityAdapter);
		stopLocation();
		leftImg.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();

			}
		});
	}

	@Override
	public void onLocationChanged(AMapLocation arg0) {
		locationcity.setText(arg0.getCity());
	}

	/**
	 * 城市列表点击事件
	 * 
	 * @author
	 * 
	 */
	class CityListOnItemClick implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int pos,
				long arg3) {
			Intent intent = new Intent();
			Bundle bundle = new Bundle();
			bundle.putString("newprojectcity", list.get(pos-1));
			intent.putExtras(bundle);
			setResult(RESULT_OK, intent);
			finish();
		}
	}

	// 字符串转list
	private ArrayList<String> StringToArray() {
		ArrayList<String> list = new ArrayList<String>();
		for (int i = 0; i < citys.length; i++) {
			list.add(citys[i]);
		}
		return list;

	}

}
