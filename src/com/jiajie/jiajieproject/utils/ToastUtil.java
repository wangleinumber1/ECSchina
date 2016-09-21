package com.jiajie.jiajieproject.utils;

import android.content.Context;
import android.os.Looper;
import android.view.Gravity;
import android.widget.Toast;

import com.jiajie.jiajieproject.YmallApplication;

public class ToastUtil {
	private static Toast toast = null;

	/**
	 * 提示信息
	 */
	public static void showToast(Context context, int textID, boolean isLongTime) {

		/*
		 * if (isLongTime) { toast = Toast.makeText(context, textID,
		 * Toast.LENGTH_LONG); } else { toast = Toast.makeText(context, textID,
		 * Toast.LENGTH_SHORT); }
		 */
		if (toast == null) {
			toast = Toast.makeText(context, textID,
					isLongTime ? Toast.LENGTH_LONG : Toast.LENGTH_SHORT);
		} else {
			toast.setText(textID);
			toast.setDuration(isLongTime ? Toast.LENGTH_LONG
					: Toast.LENGTH_SHORT);
		}
		final int y = DisplayUtil.getDeviceWidthHeight()[1] / 8;
		YokaLog.d("ToastUtil", "showToast y偏移量 is " + y);
		toast.setGravity(Gravity.BOTTOM, 0, y);
		toast.show();
	}

	public static void showToast(Context context, String Message) {
		toast = Toast.makeText(context, Message, Toast.LENGTH_SHORT);

		final int y = DisplayUtil.getDeviceWidthHeight()[1] / 8;
		YokaLog.d("ToastUtil", "showToast y偏移量 is " + y);
		toast.setGravity(Gravity.BOTTOM, 0, y);
		toast.show();
	}

	/**
	 * 提示信息 textID和text只能传其1
	 */
	public static void showToast(final Context context, final int textID,
			final String text, final boolean isLongTime) {
		final String textStr;
		// final int y =
		// DisplayUtil.getDeviceWidthHeight()[1]/8;//ScreenUtil.getHeight(context)/8;
		// YokaLog.d("ToastUtil", "showToast y偏移量 is "+y);
		if (Looper.myLooper() != Looper.getMainLooper()) {
			YmallApplication.mContext.runOnUiThread(new Runnable() {
				@Override
				public void run() {
					show(context, textID, text, isLongTime, 0);
				}
			});
		} else {
			show(context, textID, text, isLongTime, 0);
		}

	}

	private static void show(final Context context, final int textID,
			final String text, final boolean isLongTime, final int y) {
		if (StringUtil.checkStr(text)) {
			if (toast == null) {
				toast = Toast.makeText(context, text,
						isLongTime ? Toast.LENGTH_LONG : Toast.LENGTH_SHORT);
			} else {
				toast.setText(text);
				toast.setDuration(isLongTime ? Toast.LENGTH_LONG
						: Toast.LENGTH_SHORT);
			}
		} else {
			if (toast == null) {
				toast = Toast.makeText(context, textID,
						isLongTime ? Toast.LENGTH_LONG : Toast.LENGTH_SHORT);
			} else {
				toast.setText(textID);
				toast.setDuration(isLongTime ? Toast.LENGTH_LONG
						: Toast.LENGTH_SHORT);
			}
		}

		toast.setGravity(Gravity.BOTTOM, 0, y);
		toast.show();
	}

}
