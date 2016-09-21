/**
 * 
 */
package com.jiajie.jiajieproject.utils;

import android.app.Activity;
import android.graphics.drawable.PaintDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout.LayoutParams;
import android.widget.PopupWindow;

import com.jiajie.jiajieproject.R;

/**
 * 项目名称：NewProject 类名称：SlidpopWindowutil 类描述： 创建人：王蕾 创建时间：2015-9-23 下午5:20:56
 * 修改备注：
 */
public class SlidpopWindowutil {
	public static PopupWindow initPopuptWindow(View view, Activity activity) {
		PopupWindow mPopupWindow;

		int popheight = DisplayUtil.getDeviceWidthHeight()[1];
		mPopupWindow = new PopupWindow(view, LayoutParams.WRAP_CONTENT,
				popheight, true);

		mPopupWindow.setFocusable(true);
		// mPopupWindow.setBackgroundDrawable(new PaintDrawable());
		mPopupWindow.setAnimationStyle(R.style.mypopwindow_anim_style);
		return mPopupWindow;
	}
}
