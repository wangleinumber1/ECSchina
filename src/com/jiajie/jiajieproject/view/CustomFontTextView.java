/**
 * 
 */
package com.jiajie.jiajieproject.view;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * 项目名称：textTest 类名称：CustomFontTextView 类描述： 创建人：王蕾 创建时间：2016-8-3 上午11:35:26
 * 修改备注：
 */
public class CustomFontTextView extends TextView {

	/**
	 * @param context
	 */
	public CustomFontTextView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public CustomFontTextView(Context context, AttributeSet attrs,
			int defStyleAttr, int defStyleRes) {
		super(context, attrs, defStyleAttr, defStyleRes);
		// TODO Auto-generated constructor stub
	}

	public CustomFontTextView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}

	public CustomFontTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	private void init(Context context) {
		AssetManager assetManager = context.getAssets();
		Typeface typeface = Typeface.createFromAsset(assetManager,
				"STHeiti.ttf");
		setTypeface(typeface);
	}
}
