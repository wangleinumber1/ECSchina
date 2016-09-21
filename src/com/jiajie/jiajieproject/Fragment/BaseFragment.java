package com.jiajie.jiajieproject.Fragment;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jiajie.jiajieproject.YmallApplication;
import com.jiajie.jiajieproject.utils.ImageLoad;
import com.jiajie.jiajieproject.utils.YokaLog;

/**
 * 
 */

/**
 * 项目名称：HaiChuanProject 类名称：BaseFragment 类描述： 创建人：王蕾 创建时间：2015-7-28 下午2:09:44
 * 修改备注：
 */
public abstract class BaseFragment extends Fragment {
	private static final String TAG = "BaseFragment";
	protected Activity mActivity;
	protected YmallApplication mContext;
	protected View contentView;
	protected View headLayout;

	protected LayoutInflater mInflater;
	protected Handler mHandler = new Handler();

	protected ImageLoad mImgLoad;

	@SuppressWarnings("NewApi")
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		this.mActivity = activity;
		mContext = (YmallApplication) activity.getApplication();
		mImgLoad = new ImageLoad(mActivity);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		YokaLog.d(TAG, "BaseFragment==onActivityCreated（）");
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		this.mInflater = inflater;
		YokaLog.d(TAG, "BaseFragment==onCreateView（）");
		if (contentView == null) {
			contentView = inflater.inflate(setContentView(), null);
			// initTitle();
			initView();
		} else {
			((ViewGroup) (contentView.getParent())).removeView(contentView);
		}
		return contentView;
	}

	/*
	 * 子类最好重载，对代码的封装
	 */
	protected abstract void initView();

	/*
	 * 加载布局文件
	 */
	protected abstract int setContentView();

	/*
	 * 加载布局文件
	 */
	protected View setContentView(LayoutInflater inflater, int resId) {
		return contentView = inflater.inflate(resId, null);
	}

	/*
	 * 加载布局文件完后找到一个view对象
	 */
	protected View findViewById(int resId) {
		return contentView.findViewById(resId);
	}

	/*
	 * 左边button的隐藏
	 * 
	 * protected void hideLeftBtn() { View leftImg = findViewById(R.id.leftImg);
	 * if (null != leftImg) leftImg.setVisibility(View.INVISIBLE); }
	 * 
	 * 
	 * 右边button的隐藏
	 * 
	 * protected void hideRightBtn() { View rightImg =
	 * findViewById(R.id.rightImg); if (null != rightImg)
	 * rightImg.setVisibility(View.INVISIBLE); }
	 * 
	 * 
	 * 设置中间textView上的文字
	 * 
	 * protected void setCentreTextView(int resID) { TextView textview =
	 * (TextView) findViewById(R.id.textview); if (null != textview)
	 * textview.setText(resID); }
	 * 
	 * 
	 * 设置中间textView上的文字
	 * 
	 * protected void setCentreTextView(String s) { TextView textview =
	 * (TextView) findViewById(R.id.textview); if (null != textview)
	 * textview.setText(s); }
	 * 
	 * 
	 * 设置左边button的背景
	 * 
	 * protected void setLeftBtnBg(int resID) { ImageView leftImg = (ImageView)
	 * findViewById(R.id.leftImg); if (null != leftImg)
	 * leftImg.setImageResource(resID); leftImg.setImageDrawable(DisplayUtil
	 * .createSelector(mContext, resID)); }
	 * 
	 * 
	 * 设置左边button的背景
	 * 
	 * protected void setRightBtnBg(int resID) { ImageView rightImg =
	 * (ImageView) findViewById(R.id.rightImg); if (null != rightImg)
	 * rightImg.setImageResource(resID);
	 * rightImg.setImageDrawable(DisplayUtil.createSelector(mContext, resID)); }
	 * 
	 * 
	 * 当页面没数据时显示的默认图片
	 * 
	 * protected void setNodataDefaultImg(int resID, OnClickListener
	 * clickListener) { ImageView nodata_default_img = (ImageView)
	 * findViewById(R.id.nodata_default_img); if (null != nodata_default_img) {
	 * nodata_default_img.setImageResource(resID);
	 * nodata_default_img.setOnClickListener(clickListener); } }
	 * 
	 * 当页面没数据时显示的默认文字
	 * 
	 * protected void setNodataDefaultTxt(int resID) { TextView
	 * nodata_default_txt = (TextView) findViewById(R.id.nodata_default_txt); if
	 * (null != nodata_default_txt) { nodata_default_txt.setText(resID); } }
	 * 
	 * 隐藏当页面没数据时的默认界面
	 * 
	 * protected void showNodataDefaultTxt() { View nodata_default_ll =
	 * findViewById(R.id.nodata_default_ll); if (null != nodata_default_ll) {
	 * nodata_default_ll.setVisibility(View.VISIBLE); } }
	 */
}
