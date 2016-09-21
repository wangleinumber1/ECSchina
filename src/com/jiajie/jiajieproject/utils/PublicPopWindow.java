/**
 * 
 */
package com.jiajie.jiajieproject.utils;

import android.app.Activity;
import android.graphics.drawable.PaintDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;

import com.jiajie.jiajieproject.R;
import com.jiajie.jiajieproject.activity.MyAccontActivity;

/**
 * 项目名称：NewProject 类名称：PublicPopWindow 类描述： 创建人：王蕾 创建时间：2015-9-29 下午12:00:25
 * 修改备注：
 */
public class PublicPopWindow {

	public static PopupWindow publicPop(Activity activity,View view, LayoutInflater inflater,
			OnClickListener onClickListener, String[] str) {
		PopupWindow mPopupWindow;
		View poplayout = inflater.inflate(R.layout.popwindow_layout, null);
		TextView text1 = (TextView) poplayout.findViewById(R.id.text1);
		TextView text2 = (TextView) poplayout.findViewById(R.id.text2);
		TextView text3 = (TextView) poplayout.findViewById(R.id.text3);
		TextView text4 = (TextView) poplayout.findViewById(R.id.text4);
		text1.setText(str[0]);
		text2.setText(str[1]);
		text3.setText(str[2]);
		text4.setText(str[3]);
		text4.setOnClickListener(onClickListener);
		text3.setOnClickListener(onClickListener);
		// int[] i = DisplayUtil.getDeviceWidthHeight();
		// int popheight = i[1] - DisplayUtil.dipToPixel(30);

		mPopupWindow = new PopupWindow(poplayout, LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT, true);
		mPopupWindow.setAnimationStyle(R.style.mypopwindow_anim_updownstyle);
		mPopupWindow.setFocusable(true);
		WindowManager.LayoutParams params = activity.getWindow()
				.getAttributes();
		params.alpha = 0.7f;

		activity.getWindow().setAttributes(params);
		mPopupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
		return mPopupWindow;
	}
}