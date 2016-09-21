///**

package com.jiajie.jiajieproject.utils;

import android.app.Activity;
import android.graphics.Point;
import android.graphics.Rect;
import android.view.Display;

/**
 * 项目名称：NewProject 类名称：NotificationUtil 类描述： 创建人：王蕾 创建时间：2016-4-11 上午10:41:50
 * 修改备注：
 */
public class NotificationUtil {
	private Activity activity;

	public NotificationUtil(Activity activity) {
		this.activity = activity;
	}

	// 获取通知栏的高
	public int getNotifyheight() {
		return getScreenHeight() - getAppHeight();

	}

	// 获取整个屏幕的高
	private int getScreenHeight() {

		Display disp = activity.getWindowManager().getDefaultDisplay();
		Point outP = new Point();
		disp.getSize(outP);
		return outP.y;
	}

	// 获取应用显示区域的高
	private int getAppHeight() {
		Rect outRect = new Rect();
		activity.getWindow().getDecorView()
				.getWindowVisibleDisplayFrame(outRect);
		return outRect.height();
	}
}
