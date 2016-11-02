package com.jiajie.jiajieproject.widget;

import com.jiajie.jiajieproject.R;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 
 * @ClassName: MyAddAndSubView
 * 
 * @author 591704276@qq_com
 * 
 * @Description: 这是自定义简单的左加右减的控件，主要用于界面的展示
 * 
 * @date 2015-12-10 下午1:47:28
 * 
 */
public class MyAddAndSubView extends LinearLayout implements OnClickListener {

	private Context mContext;
	private TextView tv_left_sub;
	private TextView tv_middle_content;
	private TextView tv_right_add;
	private int count = 1;

	private int min;
	private int max;

	public int getMin() {
		return min;
	}

	public void setMin(int min) {
		this.min = min;
	}

	public int getMax() {
		return max;
	}

	public void setMax(int max) {
		this.max = max;
	}

	public MyAddAndSubView(Context context) {
		super(context);
		this.mContext = context;
		initView();
	}

	public MyAddAndSubView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.mContext = context;
		initView();
	}

	private void initView() {
		View view = View.inflate(mContext, R.layout.subview, null);
		tv_left_sub = (TextView) view.findViewById(R.id.tv_left_sub);
		tv_middle_content = (TextView) view
				.findViewById(R.id.tv_middle_content);
		tv_right_add = (TextView) view.findViewById(R.id.tv_right_add);
		addView(view);
		tv_left_sub.setOnClickListener(this);
		tv_right_add.setOnClickListener(this);
	}

	
	
	
	@Override
	public void onClick(View v) {

		if (v.getId() == R.id.tv_left_sub) {
			if (count > 1) {
				count--;
				tv_middle_content.setText(String.valueOf(count));
				onClickAddAndSubListener.clickSub(count,this);
			} else {
				tv_middle_content.setText(String.valueOf(count));
			}

		} else if (v.getId() == R.id.tv_right_add) {
			if (count >= 1) {
				count++;
				tv_middle_content.setText(String.valueOf(count));
				onClickAddAndSubListener.clickAdd(count,this);
			}
		}
	}

	/** 获取数量 */
	public int getCount() {
		return Integer.parseInt(tv_middle_content.getText().toString());
	}

	/** 设置数量 */
	public void setCount(String number) {
		count = Integer.parseInt(number);
		tv_middle_content.setText(number);
	}

	public interface OnClickAddAndSubListener {
		void clickAdd(int count,View view);

		void clickSub(int count,View view);
	}

	public OnClickAddAndSubListener onClickAddAndSubListener;

	

	public void setOnClickAddAndSubListener(
			OnClickAddAndSubListener onClickAddAndSubListener) {
		this.onClickAddAndSubListener = onClickAddAndSubListener;
	}

}
