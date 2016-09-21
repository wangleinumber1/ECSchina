/**
 * 
 */
package com.jiajie.jiajieproject.activity;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
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
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.jiajie.jiajieproject.R;
import com.jiajie.jiajieproject.activity.BaseActivity.MyAsyncTask;
import com.jiajie.jiajieproject.adapter.NewProjectMessageAdapter;
import com.jiajie.jiajieproject.adapter.PartsAdapter;
import com.jiajie.jiajieproject.adapter.SalesPartsAdapter;
import com.jiajie.jiajieproject.contents.InterfaceParams;
import com.jiajie.jiajieproject.db.manager.DBManager;
import com.jiajie.jiajieproject.model.OnlyClass;
import com.jiajie.jiajieproject.model.People;
import com.jiajie.jiajieproject.utils.IntentUtil;
import com.jiajie.jiajieproject.utils.NewprojectSqliteUtil;
import com.jiajie.jiajieproject.utils.PullToRefreshView;
import com.jiajie.jiajieproject.utils.StringUtil;
import com.jiajie.jiajieproject.utils.PullToRefreshView.OnFooterRefreshListener;
import com.jiajie.jiajieproject.utils.PullToRefreshView.OnHeaderRefreshListener;
import com.mrwujay.cascade.model.produceClass;
import com.jiajie.jiajieproject.utils.ToastUtil;

/**
 * 项目名称：NewProject 类名称：salespartsAcitivity 类描述： 创建人：王蕾 创建时间：2015-9-9 上午11:55:04
 * 修改备注： 促销备件
 */
public class NewProjectMessageAcitivity extends LocationActivity implements
		OnItemClickListener, OnClickListener {
	private Button leftImg;
	// 数据库在raw的id
	private EditText searchedit;
	private TextView searchbutton;
	// private PullToRefreshView projectmessagelayout_pull;
	private ListView projectmessagelayout_listview;
	private SQLiteDatabase database;
	private NewProjectMessageAdapter NewprojectmessageAdapter;
	public static final String DB_TABLE = "info_tbl";
	public static final String KEY_ID = "id";
	public static final String KEY_SOURCEID = "source_id";
	public static final String KEY_SURID = "suid";
	public static final String KEY_FETCH_DATE = "fetch_date";
	public static final String KEY_TITLE = "title";
	public static final String KEY_URL = "url";
	public static final String KEY_AUTHOR = "author";
	public static final String KEY_SUMMARY = "summary";
	public static final String KEY_PUB_DATE = "pub_date";

	private MyHandler myhandler = new MyHandler();
	private ArrayList<People> list = new ArrayList<People>();
	private NewprojectSqliteUtil newProjectSQL;

	// private int page=1;
	@Override
	protected void onInit(Bundle bundle) {
		// TODO Auto-generated method stub
		super.onInit(bundle);
		setContentView(R.layout.newprojectmessage_layout);
		initView();
	}

	/**
	 * 布局加载
	 */
	private void initView() {
		newProjectSQL = new NewprojectSqliteUtil();
		leftImg = (Button) findViewById(R.id.leftImg);
		searchedit = (EditText) findViewById(R.id.searchedit);
		searchbutton = (TextView) findViewById(R.id.searchbutton);
		searchbutton.setOnClickListener(this);
		leftImg.setOnClickListener(this);
		projectmessagelayout_listview = (ListView) findViewById(R.id.projectmessagelayout_listview);
		projectmessagelayout_listview.setOnItemClickListener(this);
		stopLocation();
		new SqlAsyTask().execute();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		if (resultCode == RESULT_OK) {
			Bundle bundle = data.getExtras();
			String city = bundle.getString("newprojectcity");
			leftImg.setText(city);
			list = ObjectToArrayList(QueryCityData(city));
			NewprojectmessageAdapter.setdata(list);
			NewprojectmessageAdapter.notifyDataSetChanged();
		}

	}
	String Province;
	@Override
	public void onLocationChanged(AMapLocation arg0) {
		// TODO Auto-generated method stub
		super.onLocationChanged(arg0);
		if (StringUtil.checkStr(arg0.getCity())) {
			// 省份
			Province = getCityString(arg0.getProvince());
			leftImg.setText(Province);

		// leftImg.setText(arg0.getCity());
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
			list = ObjectToArrayList(queryTitleData(search));
			NewprojectmessageAdapter.setdata(list);
			NewprojectmessageAdapter.notifyDataSetChanged();
			break;
		case R.id.leftImg:
			// IntentUtil.activityForward(mActivity,
			// NewprojectMessageCityActivity.class, null, false);
			IntentUtil.startActivityForResult(mActivity,
					NewprojectMessageCityActivity.class, 1, null);
			break;

		default:
			break;
		}

	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// String produce_id=NewprojectmessageAdapter.getdata().get(arg2).id;
		// Bundle bundle=new Bundle();
		// bundle.putString("id", produce_id);
		// IntentUtil.activityForward(mActivity, GoodsdetailActivity.class,
		// bundle,
		// false);
	}

	// 查询全部数据
	public People[] queryAllData() {
		Cursor results = database.query(DB_TABLE, new String[] { KEY_ID,
				KEY_SOURCEID, KEY_SURID, KEY_FETCH_DATE, KEY_TITLE, KEY_URL,
				KEY_AUTHOR, KEY_SUMMARY, KEY_PUB_DATE }, null, null, null,
				null, null);
		return ConvertToPeople(results);
	}

	// 查询包含关键字数据
	public People[] queryTitleData(String key) {

		// database.execSQL("select * from "+DB_TABLE+"  where"+
		// KEY_TITLE+" like %"+key+"%");

		String sql = "select * from " + DB_TABLE + " where " + KEY_TITLE
				+ " like '%" + key + "%' order by pub_date desc";
		Log.d("NewProject", sql);
		Cursor results = database.rawQuery(sql, null);
		return ConvertToPeople(results);
	}

	// 匹配城市字段
	public People[] QueryCityData(String city) {

		// database.execSQL("select * from "+DB_TABLE+"  where"+
		// KEY_TITLE+" like %"+key+"%");

		String sql = "select * from " + DB_TABLE + " where " + "author='"
				+ city + "'order by pub_date desc ";
		Log.d("NewProject", sql);
		Cursor results = database.rawQuery(sql, null);
		return ConvertToPeople(results);
	}

	// 数据库转成对象
	private People[] ConvertToPeople(Cursor cursor) {

		int resultCounts;
		resultCounts = cursor.getCount();
		// }
		if (resultCounts == 0 || !cursor.moveToFirst()) {
			return null;
		}
		People[] peoples = new People[resultCounts];
		for (int i = 0; i < resultCounts; i++) {
			peoples[i] = new People();
			peoples[i].KEY_ID = cursor.getInt(0);
			peoples[i].KEY_SOURCEID = cursor.getString(cursor
					.getColumnIndex(KEY_SOURCEID));
			peoples[i].KEY_SURID = cursor.getString(cursor
					.getColumnIndex(KEY_SURID));
			peoples[i].KEY_FETCH_DATE = cursor.getInt(cursor
					.getColumnIndex(KEY_FETCH_DATE));
			peoples[i].KEY_TITLE = cursor.getString(cursor
					.getColumnIndex(KEY_TITLE));
			peoples[i].KEY_URL = cursor.getString(cursor
					.getColumnIndex(KEY_URL));
			peoples[i].KEY_AUTHOR = cursor.getString(cursor
					.getColumnIndex(KEY_AUTHOR));
			peoples[i].KEY_SUMMARY = cursor.getString(cursor
					.getColumnIndex(KEY_SUMMARY));
			peoples[i].KEY_PUB_DATE = cursor.getString(cursor
					.getColumnIndex(KEY_PUB_DATE));

			cursor.moveToNext();
		}
		return peoples;
	}

	// 对象转list
	private ArrayList<People> ObjectToArrayList(People[] peoples) {
		ArrayList<People> list = new ArrayList<People>();
		if (peoples != null) {
			for (int i = 0; i < peoples.length; i++) {
				list.add(peoples[i]);

			}
			return list;
		} else {
			return new ArrayList<People>();
		}
	}

	private class MyHandler extends Handler {

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				NewprojectmessageAdapter.setdata(list);
				NewprojectmessageAdapter.notifyDataSetChanged();
				break;
			default:
				break;
			}
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

	private class SqlAsyTask extends MyAsyncTask {

		@Override
		protected Object doInBackground(Object... params) {
			newProjectSQL.downLoad(NewProjectMessageAcitivity.this);
			return null;
		}

		@Override
		protected void onPostExecute(Object result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
//			DBManager dbManager = new DBManager(NewProjectMessageAcitivity.this);
			File jhPath = new File(ProjectfilePath);
			database = SQLiteDatabase.openOrCreateDatabase(jhPath, null);;
			NewprojectmessageAdapter = new NewProjectMessageAdapter(mActivity,
					database, myhandler);
			projectmessagelayout_listview.setAdapter(NewprojectmessageAdapter);
			NewprojectmessageAdapter.notifyDataSetChanged();
			if(StringUtil.checkStr(Province)){
				list = ObjectToArrayList(QueryCityData(Province));
				NewprojectmessageAdapter.setdata(list);
				NewprojectmessageAdapter.notifyDataSetChanged();
			}else{
				ToastUtil.showToast(mContext, "定位失败请手动获取");
			}
		}

	}
	String ProjectfilePath = "data/data/com.jiajie.jiajieproject/wanghe.db";
}
