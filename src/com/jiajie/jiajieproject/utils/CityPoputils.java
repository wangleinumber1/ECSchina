/**
 * 
 */
package com.jiajie.jiajieproject.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.jiajie.jiajieproject.R;
import com.jiajie.jiajieproject.activity.BaseActivity;
import com.jiajie.jiajieproject.adapter.AdressManageAdapter;
import com.jiajie.jiajieproject.db.manager.DBManager;
import com.jiajie.jiajieproject.model.MyListItem;
import com.jiajie.jiajieproject.widget.CartClassImageView;
import com.jiajie.jiajieproject.widget.MediaImageView;
import com.nostra13.universalimageloader.core.imageaware.ImageViewAware;

import android.R.integer;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * 项目名称：NewProject 类名称：CityPoputils 类描述： 创建人：王蕾 创建时间：2015-10-20 下午3:23:28 修改备注：
 */
@SuppressLint("UseSparseArrays")
public class CityPoputils implements OnClickListener {
	public CityPoputils(Context context, BaseActivity mActivity,
			LayoutInflater inflater, Button button, TextView text) {
		this.context = context;
		this.activity = mActivity;
		this.inflater = inflater;
		this.button = button;
		this.text = text;
	}

	private Button button;
	private TextView text, locationtext;
	private DBManager dbm;
	private SQLiteDatabase db;
	private String province = null;
	private String city = null;
	private String district = null;
	private Context context;
	private BaseActivity activity;
	private LayoutInflater inflater;
	private PopupWindow popupWindow;
	private Button headerleftImg, headerrightImg;
	ListView listview;
	ListViewAdapter listViewAdapter;
	private int i = 0;
	private String adress;

	@SuppressWarnings("unused")
	public void citypoplist(View leftImage) {
		i = 0;
		View view = inflater.inflate(R.layout.citylistview, null);
		listview = (ListView) view.findViewById(R.id.city_list);
		headerleftImg = (Button) view.findViewById(R.id.headerleftImg);
		headerrightImg = (Button) view.findViewById(R.id.headerrightImg);
		locationtext = (TextView) view.findViewById(R.id.locationtext);
		listViewAdapter = new ListViewAdapter();
		headerleftImg.setOnClickListener(this);
		headerrightImg.setOnClickListener(this);
		listview.setOnItemClickListener(new PopOnitemclicListener());
		listview.setAdapter(listViewAdapter);
		initSpinner1();
		popupWindow = SlidpopWindowutil.initPopuptWindow(view,activity);
		popupWindow.showAtLocation(leftImage, Gravity.RIGHT, 0, 0);

	}

	private List<MyListItem> provincelist;
	private List<MyListItem> citylist;
	private List<MyListItem> districtlist;

	private void initSpinner1() {
		dbm = new DBManager(activity,R.raw.city);
		dbm.openDatabase(context,R.raw.city);
		db = dbm.getDatabase();
		provincelist = new ArrayList<MyListItem>();

		try {
			String sql = "select * from province";
			Cursor cursor = db.rawQuery(sql, null);
			cursor.moveToFirst();
			while (!cursor.isLast()) {
				String code = cursor.getString(cursor.getColumnIndex("code"));
				byte bytes[] = cursor.getBlob(2);
				String name = new String(bytes, "gbk");
				MyListItem myListItem = new MyListItem();
				myListItem.setName(name);
				myListItem.setPcode(code);
				provincelist.add(myListItem);
				cursor.moveToNext();
			}
			String code = cursor.getString(cursor.getColumnIndex("code"));
			byte bytes[] = cursor.getBlob(2);
			String name = new String(bytes, "gbk");
			MyListItem myListItem = new MyListItem();
			myListItem.setName(name);
			myListItem.setPcode(code);
			provincelist.add(myListItem);

		} catch (Exception e) {
		}
		dbm.closeDatabase();
		db.close();
		listViewAdapter.getData((ArrayList<MyListItem>) provincelist);
		listViewAdapter.notifyDataSetChanged();
		//
		// MyAdapter myAdapter = new MyAdapter(activity, list);
		// spinner1.setAdapter(myAdapter);
		// spinner1.setOnItemSelectedListener(new SpinnerOnSelectedListener1());
	}

	private void initSpinner2(String pcode) {
		dbm = new DBManager(activity,R.raw.city);
		dbm.openDatabase(context,R.raw.city);
		db = dbm.getDatabase();
		citylist = new ArrayList<MyListItem>();

		try {
			String sql = "select * from city where pcode='" + pcode + "'";
			Cursor cursor = db.rawQuery(sql, null);
			cursor.moveToFirst();
			while (!cursor.isLast()) {
				String code = cursor.getString(cursor.getColumnIndex("code"));
				byte bytes[] = cursor.getBlob(2);
				String name = new String(bytes, "gbk");
				MyListItem myListItem = new MyListItem();
				myListItem.setName(name);
				myListItem.setPcode(code);
				citylist.add(myListItem);
				cursor.moveToNext();
			}
			String code = cursor.getString(cursor.getColumnIndex("code"));
			byte bytes[] = cursor.getBlob(2);
			String name = new String(bytes, "gbk");
			MyListItem myListItem = new MyListItem();
			myListItem.setName(name);
			myListItem.setPcode(code);
			citylist.add(myListItem);

		} catch (Exception e) {
		}
		dbm.closeDatabase();
		db.close();
		listViewAdapter.getData((ArrayList<MyListItem>) citylist);
		listViewAdapter.notifyDataSetChanged();
		//
		// MyAdapter myAdapter = new MyAdapter(context, list);
		// spinner2.setAdapter(myAdapter);
		// spinner2.setOnItemSelectedListener(new SpinnerOnSelectedListener2());
	}

	private void initSpinner3(String pcode) {
		dbm = new DBManager(activity,R.raw.city);
		dbm.openDatabase(context,R.raw.city);
		db = dbm.getDatabase();
		// db = dbm.getDatabase();
		districtlist = new ArrayList<MyListItem>();

		try {
			String sql = "select * from district where pcode='" + pcode + "'";
			Cursor cursor = db.rawQuery(sql, null);
			cursor.moveToFirst();
			while (!cursor.isLast()) {
				String code = cursor.getString(cursor.getColumnIndex("code"));
				byte bytes[] = cursor.getBlob(2);
				String name = new String(bytes, "gbk");
				MyListItem myListItem = new MyListItem();
				myListItem.setName(name);
				myListItem.setPcode(code);
				districtlist.add(myListItem);
				cursor.moveToNext();
			}
			String code = cursor.getString(cursor.getColumnIndex("code"));
			byte bytes[] = cursor.getBlob(2);
			String name = new String(bytes, "gbk");
			MyListItem myListItem = new MyListItem();
			myListItem.setName(name);
			myListItem.setPcode(code);
			districtlist.add(myListItem);

		} catch (Exception e) {
		}
		dbm.closeDatabase();
		db.close();
		listViewAdapter.getData((ArrayList<MyListItem>) districtlist);
		listViewAdapter.notifyDataSetChanged();
		//
		// MyAdapter myAdapter = new MyAdapter(context, list);
		// spinner3.setAdapter(myAdapter);
		// spinner3.setOnItemSelectedListener(new SpinnerOnSelectedListener3());
	}

	private class ListViewAdapter extends BaseAdapter {
		private ArrayList<MyListItem> adpterlist = new ArrayList<MyListItem>();
		public HashMap<Integer, Boolean> isSelected;

		public ListViewAdapter() {
			super();

		}

		// 获取数据
		@SuppressWarnings("unused")
		public void getData(ArrayList list) {
			adpterlist = list;

		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			if (adpterlist == null) {
				return 0;
			}
			return adpterlist.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder vh = null;
			if (convertView == null) {
				vh = new ViewHolder();
				convertView = LayoutInflater.from(activity).inflate(
						R.layout.citylistviewitem, null);

				vh.citytext = (TextView) convertView
						.findViewById(R.id.citytextView);
				vh.citylistLayout = (RelativeLayout) convertView
						.findViewById(R.id.citylistLayout);
				convertView.setTag(vh);
			} else {
				vh = (ViewHolder) convertView.getTag();
			}
			vh.citytext.setText(adpterlist.get(position).getName());

			// vh.checkimage.setChecked(isSelected.get(position));
			// vh.checkimage.setChecked(true);
			return convertView;
		}

		class ViewHolder {
			CartClassImageView checkimage;
			TextView citytext;
			RelativeLayout citylistLayout;
		}

	}

	private class PopOnitemclicListener implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {

			if (i == 0) {
				province = provincelist.get(arg2).getName();
				adress = province;
				String pcode1 = provincelist.get(arg2).getPcode();

				initSpinner2(pcode1);
			} else if (i == 1) {
				city = citylist.get(arg2).getName();
				adress = adress + city;
				String pcode2 = citylist.get(arg2).getPcode();
				initSpinner3(pcode2);
			} else if (i == 2) {
				district = districtlist.get(arg2).getName();
				adress = adress + district;
				if (popupWindow != null) {
					popupWindow.dismiss();

					if (button != null) {
						button.setText(adress);
					} else if (text != null) {
						text.setText(adress);
					}
				}

			}
			locationtext.setText(adress);
			if (button != null) {
				button.setText(adress);
			} else if (text != null) {
				text.setText(adress);
			}
			i++;
			// 改变单选状态

			listViewAdapter.notifyDataSetChanged();
		}

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.headerleftImg:
			if (popupWindow != null) {

				popupWindow.dismiss();

			}
			break;
		case R.id.headerrightImg:
			if (popupWindow != null) {

				popupWindow.dismiss();

			}
			break;

		default:
			break;
		}

	}

	public void dissmissPop() {
		if (popupWindow != null) {

			popupWindow.dismiss();
		}
	}
}
