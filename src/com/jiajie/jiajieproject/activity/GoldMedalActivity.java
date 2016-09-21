/**
 * 
 */
package com.jiajie.jiajieproject.activity;

import java.util.ArrayList;

import android.content.res.ColorStateList;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
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
import com.jiajie.jiajieproject.utils.IntentUtil;
import com.jiajie.jiajieproject.widget.ReboundScrollView;

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
	// private ArrayList<CategoriesClass> CategoriesClassList = new
	// ArrayList<CategoriesClass>();
	private String[] toolsList = new String[] { "IBM", "HP", "华为", "EMC",
			"Cisic", "DELL", "ORACLE", "H3C", "苹果", "联想", "INTER", "爱国者", "小米" };
	private GoldFragmentadapter goldFragmentadapter;
	private LinearLayout toolsLayout;;
	private ClassPopAdapter classPopAdapter;
	private int phonecode = 102;
	private ArrayList<String> poplist = new ArrayList<String>();
	private RelativeLayout class_layout;

	@Override
	protected void onInit(Bundle bundle) {
		// TODO Auto-generated method stub
		super.onInit(bundle);
		setContentView(R.layout.goldactivity_layout);
		scrollView = (ReboundScrollView) findViewById(R.id.tools_scrlllview);
		// new GetIdAsyTask().execute();
		InitView();

	}

	private void InitView() {
		leftImage = (ImageView) findViewById(R.id.headerleftImg);
		RightImage = (ImageView) findViewById(R.id.mainpage_phone);
		searchimage = (ImageView) findViewById(R.id.mainpage_search);
		MyListView = (ListView) findViewById(R.id.goldListView);
		toolsLayout = (LinearLayout) findViewById(R.id.tools);
		goldFragmentadapter = new GoldFragmentadapter(mContext, mImgLoad);
		classPopAdapter = new ClassPopAdapter(this);
		poplist.add("p系列1");
		poplist.add("p系列2");
		poplist.add("p系列3");
		poplist.add("p系列4");
		poplist.add("p系列5");
		poplist.add("p系列6");
		poplist.add("p系列7");
		poplist.add("p系列8");
		poplist.add("p系列9");
		poplist.add("p系列10");
		poplist.add("p系列11");
		MyListView.setAdapter(goldFragmentadapter);
		MyListView.setOnItemClickListener(this);
		leftImage.setOnClickListener(this);
		RightImage.setOnClickListener(this);
		searchimage.setOnClickListener(this);
		redcolors = getResources().getColorStateList(R.color.classredcolor);
		blackcolors = getResources().getColorStateList(R.color.classblackcolor);
		showToolsView(toolsList);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {

		case R.id.headerleftImg:
			IntentUtil.startActivityForResult(mActivity, CaptureActivity.class,
					16, null);

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

	private void showToolsView(String[] toolsList) {
		// toolsTextViews=new TextView[CategoriesClassList.size()];
		toolsTextViews = new TextView[toolsList.length];
		views = new View[toolsList.length];
		// views = new View[CategoriesClassList.size()];

		/*
		 * for (int i = 0; i < CategoriesClassList.size(); i++) { View
		 * view=inflater.inflate(R.layout.item_b_top_nav_layout, null);
		 * view.setId(i); view.setOnClickListener(toolsItemListener); TextView
		 * textView=(TextView) view.findViewById(R.id.text);
		 * textView.setText(CategoriesClassList.get(i).name);
		 * toolsLayout.addView(view); toolsTextViews[i]=textView; views[i]=view;
		 * }
		 */
		for (int i = 0; i < toolsList.length; i++) {
			View view = inflater.inflate(R.layout.item_b_top_nav_layout, null);
			view.setId(i);
			view.setOnClickListener(toolsItemListener);
			TextView textView = (TextView) view.findViewById(R.id.text);
			// textView.setText(CategoriesClassList.get(i).name);
			textView.setText(toolsList[i]);
			toolsLayout.addView(view);
			toolsTextViews[i] = textView;
			views[i] = view;
		}
		changeTextColor(0);
	}

	private View.OnClickListener toolsItemListener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			changeTextColor(v.getId());
			changeTextLocation(v.getId());
			getPopupWindow(v.getHeight());
			if ((toolsList.length - v.getId()) < 4) {
				int[] location = new int[2];
				v.getLocationOnScreen(location);
				popupWindow.showAtLocation(v, Gravity.NO_GRAVITY, location[0]+v.getWidth(),
						location[1] -v.getHeight()*3);
			} else {
				popupWindow.showAsDropDown(v, v.getWidth(), -v.getHeight(),
						Gravity.LEFT);
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

	protected void initPopuptWindow(int height) {
		// TODO Auto-generated method stub
		// 获取自定义布局文件activity_popupwindow_left.xml的视图
		View popupWindow_view = getLayoutInflater().inflate(
				R.layout.poplistview, null, false);
		// 创建PopupWindow实例,200,LayoutParams.MATCH_PARENT分别是宽度和高度
		ListView listview = (ListView) popupWindow_view;
		classPopAdapter.setdata(poplist);
		listview.setAdapter(classPopAdapter);
		classPopAdapter.notifyDataSetChanged();
		popupWindow = new PopupWindow(popupWindow_view, 200,
				height*4, true);
		popupWindow.setBackgroundDrawable(new BitmapDrawable());
		popupWindow.setOutsideTouchable(true);
		listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				if (popupWindow != null && popupWindow.isShowing()) {
					popupWindow.dismiss();
					popupWindow = null;
				}

			}
		});
	}

	/***
	 * 获取PopupWindow实例
	 */
	private void getPopupWindow(int height) {
		if (null != popupWindow) {
			popupWindow.dismiss();

			return;
		} else {
			initPopuptWindow(height);
		}
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		IntentUtil.activityForward(mActivity, GoodsdetailActivity.class, null,
				false);
	}
}
