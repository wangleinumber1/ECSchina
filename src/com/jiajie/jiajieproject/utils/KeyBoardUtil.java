package com.jiajie.jiajieproject.utils;
import android.content.Context;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

public class KeyBoardUtil {

	/*
	 * 隐藏收回键盘
	 */
	public static void hideKeyboard(Context context,ViewGroup contentView){
		InputMethodManager imm = (InputMethodManager)context.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(contentView.getWindowToken(), 0);
	}
}
