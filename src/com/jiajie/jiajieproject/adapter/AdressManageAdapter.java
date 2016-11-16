/**
 * 
 */
package com.jiajie.jiajieproject.adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;

import com.jiajie.jiajieproject.R;
import com.jiajie.jiajieproject.activity.AddAdressActivity;
import com.jiajie.jiajieproject.contents.InterfaceParams;
import com.jiajie.jiajieproject.model.AdressClass;
import com.jiajie.jiajieproject.model.OnlyClass;
import com.jiajie.jiajieproject.net.service.JosnService;
import com.jiajie.jiajieproject.utils.IntentUtil;
import com.jiajie.jiajieproject.utils.MyAsyncTask;
import com.jiajie.jiajieproject.utils.ToastUtil;
import com.jiajie.jiajieproject.widget.MediaImageView;

/**
 * 项目名称：HaiChuanProject 类名称：FaBuSearchAdapter 类描述： 创建人：王蕾 创建时间：2015-7-29
 * 下午2:19:53 修改备注：
 */
public class AdressManageAdapter extends BaseAdapter implements OnClickListener {
	private Activity activity;
	private int newposition;
	private Handler myhandler;
	ArrayList<AdressClass> list = new ArrayList<AdressClass>();
	private JosnService jsonservice;

	public AdressManageAdapter(Activity activity, JosnService jsonservice,
			Handler myhandler) {
		this.activity = activity;
		this.jsonservice = jsonservice;
		this.myhandler = myhandler;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	public void setdata(ArrayList<AdressClass> list) {
		this.list = list;
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
					R.layout.adressmanageitem_layout, null);
			vh.imgeView1 = (MediaImageView) convertView
					.findViewById(R.id.imgeView1);
			vh.text1 = (TextView) convertView.findViewById(R.id.text1);
			vh.text2 = (TextView) convertView.findViewById(R.id.text2);
			vh.text3 = (TextView) convertView.findViewById(R.id.text3);
			vh.button1 = (Button) convertView.findViewById(R.id.button1);
			vh.button2 = (Button) convertView.findViewById(R.id.button2);

			convertView.setTag(vh);
		} else {
			vh = (ViewHolder) convertView.getTag();
		}

		vh.imgeView1.setOnClickListener(this);
		vh.imgeView1.setTag(position);
		vh.button1.setOnClickListener(this);
		vh.button1.setTag(position);
		vh.button2.setTag(position);
		vh.button2.setOnClickListener(this);
		vh.imgeView1.setChecked(list.get(position).default_shipping);
		vh.text3.setText(list.get(position).city + list.get(position).street);
		vh.text1.setText(list.get(position).lastname);
		vh.text2.setText(list.get(position).telephone);
		return convertView;
	}

	class ViewHolder {
		MediaImageView imgeView1;
		TextView text1, text2, text3;
		Button button1, button2;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.imgeView1:
			
			MediaImageView checkImage = (MediaImageView) v;
			
			//只有不是默认地址时可以点击
			if(checkImage.isChecked()){
			Integer tagnumber = (Integer) v.getTag();
			new AdressDefaultAsyTask(list.get(tagnumber).entity_id).execute();
			}
			break;
		case R.id.button2:
			Button button = (Button) v;
			Integer adressidposition = (Integer) v.getTag();
			Bundle bundle = new Bundle();
			bundle.putString("adressid", list.get(adressidposition).entity_id);
			IntentUtil.activityForward(activity, AddAdressActivity.class,
					bundle, false);
			break;

		case R.id.button1:
			Integer id = (Integer) v.getTag();
			popwindowUpDown(list.get(id).entity_id);
			break;

		default:
			break;
		}

	}

	/**
	 * 地址删除
	 * */
	@SuppressWarnings("unused")
	private class AdressDeleteAsyTask extends MyAsyncTask {
		private String id;

		public AdressDeleteAsyTask(String id) {
			super(activity);
			this.id = id;
		}

		@SuppressWarnings("unchecked")
		@Override
		protected Object doInBackground(Object... params) {
			Map map = new HashMap<String, String>();
			map.put("id", id);
			return jsonservice.getData(InterfaceParams.deleteAddress, map,
					false, null);
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
				if (onlyClass.success) {
					myhandler.sendEmptyMessage(10);
					mPopupWindow.dismiss();
				}
				ToastUtil.showToast(activity, onlyClass.data);
			}

		}

	}

	/**
	 * 默认地址
	 * */
	@SuppressWarnings("unused")
	private class AdressDefaultAsyTask extends MyAsyncTask {
		private String id;

		public AdressDefaultAsyTask(String id) {
			super(activity);
			this.id = id;
		}

		@SuppressWarnings("unchecked")
		@Override
		protected Object doInBackground(Object... params) {
			Map map = new HashMap<String, String>();
			map.put("address_id", id);
			return jsonservice.getData(InterfaceParams.setDefaultAddress, map,
					false, null);
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
				if (onlyClass.success) {
					myhandler.sendEmptyMessage(10);
				}
				ToastUtil.showToast(activity, onlyClass.data);
			}

		}

	}

	PopupWindow mPopupWindow;

	// 底部弹出popwindow
	@SuppressWarnings("unused")
	private void popwindowUpDown(final String id) {
		
		View view = LayoutInflater.from(activity).inflate(R.layout.mine_layout,
				null);
		View poplayout = LayoutInflater.from(activity).inflate(
				R.layout.minepopwindow_layout, null);
		TextView text1 = (TextView) poplayout.findViewById(R.id.text1);
		TextView text2 = (TextView) poplayout.findViewById(R.id.text2);
		TextView text3 = (TextView) poplayout.findViewById(R.id.text3);
		text2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				new AdressDeleteAsyTask(id).execute();

			}
		});
		text3.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				mPopupWindow.dismiss();

			}
		});
		text1.setText("是否删除？");
		text2.setText("删除");
		text3.setText("取消");
		mPopupWindow = new PopupWindow(poplayout, LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT, true);
		mPopupWindow.setFocusable(true);
		// 改变背景透明度
		setPopBackgroud((float) 0.7);

		// mPopupWindow.setBackgroundDrawable(new PaintDrawable());
		mPopupWindow.setAnimationStyle(R.style.mypopwindow_anim_updownstyle);
		mPopupWindow.showAtLocation(view, Gravity.BOTTOM, 0, 0);
	}

	// 设置半透明背景
	public void setPopBackgroud(float alpha) {
		WindowManager.LayoutParams params = activity.getWindow()
				.getAttributes();
		params.alpha = alpha;
		activity.getWindow().setAttributes(params);
	}

}
