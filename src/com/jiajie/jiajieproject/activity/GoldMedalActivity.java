/**
 * 
 */
package com.jiajie.jiajieproject.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.content.res.ColorStateList;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jiajie.jiajieproject.R;
import com.jiajie.jiajieproject.adapter.ClassPopAdapter;
import com.jiajie.jiajieproject.adapter.GoldFragmentadapter;
import com.jiajie.jiajieproject.contents.Constants;
import com.jiajie.jiajieproject.contents.InterfaceParams;
import com.jiajie.jiajieproject.model.CategoriesClass;
import com.jiajie.jiajieproject.utils.IntentUtil;
import com.jiajie.jiajieproject.utils.ToastUtil;
import com.jiajie.jiajieproject.widget.ReboundScrollView;
import com.mrwujay.cascade.model.MainPageObject;

/**
 * 项目名称：NewProject 类名称：GoldMedalActivity 类描述： 创建人：王蕾 创建时间：2015-9-8 下午5:24:49
 * 修改备注： 分类
 */
public class GoldMedalActivity extends BaseActivity implements OnClickListener,
		OnItemClickListener {
	private TextView toolsTextViews[];
	private View views[];
	private ReboundScrollView scrollView;
	private int scrllViewWidth = 0, scrollViewMiddle = 0;
	private int currentItem = 0;
	private ListView MyListView;
	private ImageView leftImage, RightImage, searchimage;
	private ArrayList<CategoriesClass> CategoriesClassList = new ArrayList<CategoriesClass>();
	private GoldFragmentadapter goldFragmentadapter;
	private ArrayList<MainPageObject> goldFragmentList=new ArrayList<MainPageObject>();
	private LinearLayout toolsLayout;;
	private ClassPopAdapter classPopAdapter;
	private int phonecode = 102;
	private ArrayList<CategoriesClass> poplist = new ArrayList<CategoriesClass>();
	private RelativeLayout class_layout;
	public static final String TAG="GoldMedalActivity";
	private MyHandler myHandler=new MyHandler();
	private RelativeLayout goldListViewCover;
	@SuppressWarnings("unchecked")
	@Override
	protected void onInit(Bundle bundle) {
		// TODO Auto-generated method stub
		super.onInit(bundle);
		setContentView(R.layout.goldactivity_layout);
		scrollView = (ReboundScrollView) findViewById(R.id.tools_scrlllview);
		new GetIdAsyTask("2").execute();
		new GetProductsByCidAsyTask("2").execute();
		InitView();

	}

	private void InitView() {
		leftImage = (ImageView) findViewById(R.id.headerleftImg);
		RightImage = (ImageView) findViewById(R.id.mainpage_phone);
		searchimage = (ImageView) findViewById(R.id.mainpage_search);
		MyListView = (ListView) findViewById(R.id.goldListView);
		toolsLayout = (LinearLayout) findViewById(R.id.tools);
		goldListViewCover = (RelativeLayout) findViewById(R.id.goldListViewCover);
		goldFragmentadapter = new GoldFragmentadapter(mContext, mImgLoad);
		classPopAdapter = new ClassPopAdapter(this);
		MyListView.setAdapter(goldFragmentadapter);
		MyListView.setOnItemClickListener(this);
		leftImage.setOnClickListener(this);
		RightImage.setOnClickListener(this);
		searchimage.setOnClickListener(this);
		goldListViewCover.setOnClickListener(this);
		redcolors = getResources().getColorStateList(R.color.classredcolor);
		blackcolors = getResources().getColorStateList(R.color.classblackcolor);
		

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {

		case R.id.headerleftImg:
			IntentUtil.startActivityForResult(mActivity, CaptureActivity.class,
					16, null);

			break;
		case R.id.goldListViewCover:
			goldListViewCover.setVisibility(View.GONE);
			
			break;
		case R.id.mainpage_phone:
			// 多加一个空格来区分跳转页面
			String strArray[] = { "确定要拨打电话吗？", Constants.phonenumber, "确定",
					"取消", "" };
			Bundle bundle = new Bundle();
			bundle.putStringArray(ClearCacheActivity.TAG, strArray);
			IntentUtil.startActivityForResult(mActivity,
					ClearCacheActivity.class, phonecode, bundle);
			break;
		case R.id.mainpage_search:
			IntentUtil.activityForward(mActivity, NewSearchActivity.class,
					null, false);
			//
			break;

		default:
			break;
		}

	}

	@SuppressWarnings("unused")
	private void showToolsView(ArrayList<CategoriesClass> list) {
		 toolsTextViews=new TextView[list.size()];
//		toolsTextViews = new TextView[toolsList.length];
//		views = new View[toolsList.length];
		 views = new View[list.size()];

		for (int i = 0; i < list.size(); i++) {
			View view = inflater.inflate(R.layout.item_b_top_nav_layout, null);
			view.setId(i);
			view.setTag(list.get(i).id.toString());
			view.setOnClickListener(toolsItemListener);
			TextView textView = (TextView) view.findViewById(R.id.text);
			textView.setText(list.get(i).name);
			toolsLayout.addView(view);
			toolsTextViews[i] = textView;
			views[i] = view;
		}

		// for (int i = 0; i < toolsList.length; i++) {
		// View view = inflater.inflate(R.layout.item_b_top_nav_layout, null);
		// view.setId(i);
		// view.setOnClickListener(toolsItemListener);
		// TextView textView = (TextView) view.findViewById(R.id.text);
		// // textView.setText(CategoriesClassList.get(i).name);
		// textView.setText(toolsList[i]);
		// toolsLayout.addView(view);
		// toolsTextViews[i] = textView;
		// views[i] = view;
		// }
		//让第一个处于选中状态
//		changeTextColor(0);
	}

	private View.OnClickListener toolsItemListener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			changeTextColor(v.getId());
			changeTextLocation(v.getId());
			getPopupWindow(v.getHeight(),(String)v.getTag());
			if ((CategoriesClassList.size() - v.getId()) < 4) {
				int[] location = new int[2];
				v.getLocationOnScreen(location);
				popupWindow.showAtLocation(v, Gravity.NO_GRAVITY, location[0]
						+ v.getWidth(), location[1] - v.getHeight() * 3);
				goldListViewCover.setVisibility(View.VISIBLE);
			} else {
				popupWindow.showAsDropDown(v, v.getWidth(), -v.getHeight(),
						Gravity.LEFT);
				goldListViewCover.setVisibility(View.VISIBLE);
			}
			
		
		}
		
		
	};

	/**
	 * �ı�textView����ɫ
	 * 
	 * @param id
	 */
	private ColorStateList redcolors;
	private ColorStateList blackcolors;

	private void changeTextColor(int id) {
		for (int i = 0; i < toolsTextViews.length; i++) {
			if (i != id) {
				toolsTextViews[i]
						.setBackgroundResource(R.drawable.classbg_grey);
				toolsTextViews[i].setTextColor(blackcolors);
			}
		}

		toolsTextViews[id].setBackgroundResource(R.drawable.classbg_white);
		toolsTextViews[id].setTextColor(redcolors);

	}

	/**
	 * �ı���Ŀλ��
	 * 
	 * @param clickPosition
	 */
	private void changeTextLocation(int clickPosition) {

		int x = (views[clickPosition].getTop() - getScrollViewMiddle() + (getViewheight(views[clickPosition]) / 2));
		scrollView.smoothScrollTo(0, x);
	}

	/**
	 * ����scrollview���м�λ��
	 * 
	 * @return
	 */
	private int getScrollViewMiddle() {
		if (scrollViewMiddle == 0)
			scrollViewMiddle = getScrollViewheight() / 2;
		return scrollViewMiddle;
	}

	private int getScrollViewheight() {
		if (scrllViewWidth == 0)
			scrllViewWidth = scrollView.getBottom() - scrollView.getTop();
		return scrllViewWidth;
	}

	private int getViewheight(View view) {
		return view.getBottom() - view.getTop();
	}

	private PopupWindow popupWindow;

	@SuppressWarnings("unchecked")
	protected void initPopuptWindow(int height,String c_id) {
		// TODO Auto-generated method stub
		// 获取自定义布局文件activity_popupwindow_left.xml的视图
		
		View popupWindow_view = getLayoutInflater().inflate(
				R.layout.poplistview, null, false);
		// 创建PopupWindow实例,200,LayoutParams.MATCH_PARENT分别是宽度和高度
		ListView listview = (ListView) popupWindow_view;
		new GetIdSecondAsyTask(c_id).execute();
		listview.setAdapter(classPopAdapter);
		popupWindow = new PopupWindow(popupWindow_view, 200, height * 4, true);
		popupWindow.setBackgroundDrawable(new BitmapDrawable());
		popupWindow.setOutsideTouchable(true);
		Message message=myHandler.obtainMessage(1);
		message.arg1=Integer.parseInt(c_id);
		myHandler.sendMessage(message);
		listview.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				
				Message message=myHandler.obtainMessage(1);
				message.arg1=Integer.parseInt(poplist.get(arg2).id);
				myHandler.sendMessage(message);
				arg1.setBackgroundResource(R.drawable.classbg_white);
				if (popupWindow != null && popupWindow.isShowing()) {
					popupWindow.dismiss();
					goldListViewCover.setVisibility(View.GONE);
					popupWindow = null;
				}

			}
		});
	}

	/***
	 * 获取PopupWindow实例
	 */
	@SuppressWarnings("unchecked")
	private void getPopupWindow(int height,String cid) {
//		if (null != popupWindow) {
//			popupWindow.dismiss();
//
//			return;
//		} else {
		
			initPopuptWindow(height,cid);
//		}
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		@SuppressWarnings("unused")
		MainPageObject mainPageObject=goldFragmentList.get(arg2-1);
		Bundle bundle=new Bundle();
		bundle.putSerializable(PartsActivity.TAG, mainPageObject);
		IntentUtil.activityForward(mActivity, GoodsdetailActivity.class, bundle,
				false);
	}

	// 获取左侧列表数据
	private class GetIdAsyTask extends AsyncTask {
		private String c_id;

		@SuppressWarnings("unused")
		public GetIdAsyTask(String c_id) {
			this.c_id = c_id;
		}

		@Override
		protected Object doInBackground(Object... params) {
			// TODO Auto-generated method stub
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("c_id", c_id);
			return jsonservice.getDataList(InterfaceParams.getCategoriesByCid,
					map, true, CategoriesClass.class);
		}

		@SuppressWarnings("unchecked")
		@Override
		protected void onPostExecute(Object result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if (result == null) {
				return;
			}
			CategoriesClassList=(ArrayList<CategoriesClass>) result;
			showToolsView(CategoriesClassList);
			
			
		}

	}
	// 获取左侧列表二级数据
	@SuppressWarnings("unused")
	private class GetIdSecondAsyTask extends AsyncTask {
		private String c_id;
		
		@SuppressWarnings("unused")
		public GetIdSecondAsyTask(String c_id) {
			this.c_id = c_id;
		}
		
		@Override
		protected Object doInBackground(Object... params) {
			// TODO Auto-generated method stub
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("c_id", c_id);
			return jsonservice.getDataList(InterfaceParams.getCategoriesByCid,
					map, true, CategoriesClass.class);
		}
		
		@SuppressWarnings("unchecked")
		@Override
		protected void onPostExecute(Object result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if (result == null) { 
				return;
			}
			
			poplist=(ArrayList<CategoriesClass>) result;
			classPopAdapter.setdata(poplist);
			classPopAdapter.notifyDataSetChanged();
			if(poplist.size()==0){
				popupWindow.dismiss();
				goldListViewCover.setVisibility(View.GONE);
				
			}
		
		}
		
	}
	// 获取右侧列表数据
	@SuppressWarnings("unused")
	private class GetProductsByCidAsyTask extends MyAsyncTask {
		private String c_id;
		
		@SuppressWarnings("unused")
		public GetProductsByCidAsyTask(String c_id) {
			this.c_id = c_id;
		}
		
		@Override
		protected Object doInBackground(Object... params) {
			// TODO Auto-generated method stub
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("c_id", c_id);
			return jsonservice.getDataList(InterfaceParams.getProductsByCid,
					map, true, MainPageObject.class);
		}
		
		@SuppressWarnings("unchecked")
		@Override
		protected void onPostExecute(Object result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if (result == null) {
				return;
			}
			goldFragmentList=(ArrayList<MainPageObject>) result;
			goldFragmentadapter.setdata(goldFragmentList);
			goldFragmentadapter.notifyDataSetChanged();
		}
		
	}
	
	@SuppressWarnings("unused")
	private class MyHandler extends Handler{

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			switch (msg.what) {
			case 1:
				new GetProductsByCidAsyTask(msg.arg1+"").execute();
				break;

			default:
				break;
			}
		}
		
	}
	
}
