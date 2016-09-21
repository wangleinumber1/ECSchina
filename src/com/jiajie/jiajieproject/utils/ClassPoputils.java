/**
 * 
 */
package com.jiajie.jiajieproject.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jiajie.jiajieproject.R;
import com.jiajie.jiajieproject.activity.BaseActivity;
import com.jiajie.jiajieproject.adapter.AdressManageAdapter;
import com.jiajie.jiajieproject.contents.InterfaceParams;
import com.jiajie.jiajieproject.db.manager.DBManager;
import com.jiajie.jiajieproject.model.CategoriesClass;
import com.jiajie.jiajieproject.model.MyListItem;
import com.jiajie.jiajieproject.model.OnlyClass;
import com.jiajie.jiajieproject.net.service.JosnService;
import com.jiajie.jiajieproject.widget.CartClassImageView;
import com.jiajie.jiajieproject.widget.MediaImageView;
import com.nostra13.universalimageloader.core.imageaware.ImageViewAware;
import com.jiajie.jiajieproject.utils.ToastUtil;

import android.R.integer;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.res.ColorStateList;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
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
public class ClassPoputils implements OnClickListener {
	private ArrayList<CategoriesClass> List = new ArrayList<CategoriesClass>();

	public ClassPoputils(Context context, BaseActivity mActivity,
			LayoutInflater inflater) {
		this.context = context;
		this.activity = mActivity;
		this.inflater = inflater;

	}

	private Button button;
	private TextView text, locationtext, headtext;
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

	@SuppressWarnings({ "unused", "unchecked" })
	public void classpoplist(Handler handler, View leftImage,
			JosnService jsonservice, String id, int what) {
		i = 0;
		View view = inflater.inflate(R.layout.classlistview, null);
		headtext = (TextView) view.findViewById(R.id.headtext);
		listview = (ListView) view.findViewById(R.id.city_list);
		headerleftImg = (Button) view.findViewById(R.id.headerleftImg);
		headerrightImg = (Button) view.findViewById(R.id.headerrightImg);
		locationtext = (TextView) view.findViewById(R.id.locationtext);
		listViewAdapter = new ListViewAdapter(handler, what);
		NotificationUtil notificationUtil = new NotificationUtil(activity);
		headtext.setHeight(notificationUtil.getNotifyheight());
		// listViewAdapter.getData(list);
		headerleftImg.setOnClickListener(this);
		headerrightImg.setOnClickListener(this);
		listview.setAdapter(listViewAdapter);
		popupWindow = SlidpopWindowutil.initPopuptWindow(view, activity);
		popupWindow.showAtLocation(leftImage, Gravity.RIGHT, 0, 0);
		new GetIdAsyTask(id, jsonservice).execute();
	}

	private class ListViewAdapter extends BaseAdapter implements
			OnClickListener {
		private ArrayList<CategoriesClass> adpterlist = new ArrayList<CategoriesClass>();
		public HashMap<Integer, Boolean> isSelected;
		private ColorStateList headercolors;
		private ColorStateList graycolors;
		private Handler handler;

		public ListViewAdapter(Handler handler, int what) {
			super();
			this.handler = handler;
			this.what = what;

			headercolors = activity.getResources().getColorStateList(
					R.color.headercolor);
			graycolors = activity.getResources().getColorStateList(
					R.color.fapiaotextcolor);
		}

		private String id = 0 + "";
		private int what;

		public void setData(ArrayList<CategoriesClass> adpterlist) {
			this.adpterlist.clear();
			CategoriesClass categoriesClass = new CategoriesClass();
			categoriesClass.id = 0 + "";
			categoriesClass.name = "全部";
			this.adpterlist.add(categoriesClass);
			this.adpterlist.addAll(1, adpterlist);
			init();
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return adpterlist.size();
		}

		// 初始化 设置所有checkbox都为未选择
		public void init() {
			isSelected = new HashMap<Integer, Boolean>();
			for (int i = 0; i <= adpterlist.size(); i++) {
				if (i == 0) {
					isSelected.put(i, true);
				} else {
					isSelected.put(i, false);
				}

			}
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
						R.layout.classlistviewitem, null);

				vh.checkimage = (CartClassImageView) convertView
						.findViewById(R.id.checkimage);
				vh.citytext = (TextView) convertView
						.findViewById(R.id.citytextView);
				vh.citylistLayout = (RelativeLayout) convertView
						.findViewById(R.id.citylistLayout);
				convertView.setTag(vh);
			} else {
				vh = (ViewHolder) convertView.getTag();
			}

			vh.citytext.setText(adpterlist.get(position).name);
			vh.citytext.setTextColor(graycolors);

			vh.citylistLayout.setOnClickListener(this);
			vh.citylistLayout.setTag(vh.checkimage);
			vh.checkimage.setTag(position);
			vh.checkimage.setChecked(isSelected.get(position));
			return convertView;
		}

		class ViewHolder {
			CartClassImageView checkimage;
			TextView citytext;
			RelativeLayout citylistLayout;
		}

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.citylistLayout:
				RelativeLayout layout = (RelativeLayout) v;
				CartClassImageView checkimage = (CartClassImageView) layout
						.getTag();
				Integer tagnumber = (Integer) checkimage.getTag();
				// ToastUtil.showToast(activity, tagnumber + "///////////////");

				for (int i = 0; i < isSelected.size(); i++) {
					if (i != tagnumber) {
						isSelected.put(i, false);
					} else {
						isSelected.put(i, true);
						Message message = handler.obtainMessage(what);
						message.obj = adpterlist.get(i).id;
						handler.sendMessage(message);

					}
				}

				ListViewAdapter.this.notifyDataSetChanged();
				break;

			default:
				break;
			}

		}

	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.headerleftImg:

			dissmissPop();
			break;
		case R.id.headerrightImg:

			dissmissPop();
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

	/**
	 * 获取Id
	 * */
	@SuppressWarnings("unused")
	private class GetIdAsyTask extends AsyncTask {

		private String id;
		private JosnService jsonservice;

		public GetIdAsyTask(String id, JosnService jsonservice) {
			super();
			this.id = id;
			this.jsonservice = jsonservice;
		}

		@SuppressWarnings("unchecked")
		@Override
		protected Object doInBackground(Object... params) {
			Map map = new HashMap<String, String>();
			map.put("c_id", id);
			return jsonservice.getDataList(InterfaceParams.getCategoriesByCid,
					map, true, CategoriesClass.class);
		}

		@SuppressWarnings("unchecked")
		@Override
		protected void onPostExecute(Object result) {

			if (result == null) {
				return;
			}

			try {
				if (jsonservice.getToastMessage()) {
					OnlyClass onlyClass = (OnlyClass) result;
					ToastUtil.showToast(activity, onlyClass.data);
				}
				if (jsonservice.getsuccessState()) {
					List = (ArrayList<CategoriesClass>) result;
					// CategoriesClass categoriesClass = new CategoriesClass();
					// categoriesClass.id = 1 + "";
					// categoriesClass.name = "123";
					// List.add(categoriesClass);
					listViewAdapter.setData(List);
					listViewAdapter.notifyDataSetChanged();
				}

			} catch (Exception e) {
				// TODO: handle exception
			}
		}

	}

}
