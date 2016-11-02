package com.jiajie.jiajieproject.activity;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView.ScaleType;

import com.jiajie.jiajieproject.R;
import com.jiajie.jiajieproject.view.SmoothImageView;
import com.nostra13.universalimageloader.core.ImageLoader;

public class SpaceImageDetailActivity extends BaseActivity {

	private String mDatas;
	private int mPosition;
	private int mLocationX;
	private int mLocationY;
	private int mWidth;
	private int mHeight;
	SmoothImageView imageView = null;
	Bundle bundle;
	@SuppressWarnings("unchecked")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		bundle=getIntent().getExtras();
		mDatas = bundle.getString("images");
		mPosition = bundle.getInt("position", 0);
		mLocationX = bundle.getInt("locationX", 0);
		mLocationY = bundle.getInt("locationY", 0);
		mWidth = bundle.getInt("width", 0);
		mHeight =bundle.getInt("height", 0);
		imageView = new SmoothImageView(this);
		imageView.setOriginalInfo(mWidth, mHeight, mLocationX, mLocationY);
		imageView.transformIn();
		imageView.setLayoutParams(new ViewGroup.LayoutParams(-1, -1));
		imageView.setScaleType(ScaleType.FIT_CENTER);
		setContentView(imageView);
		imageView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				imageView.setOnTransformListener(new SmoothImageView.TransformListener() {
					@Override
					public void onTransformComplete(int mode) {
						if (mode == 2) {
							finish();
						}
					}
				});
				imageView.transformOut();
			}
			
		});
		ImageLoader.getInstance().displayImage(mDatas, imageView);
		
	}

//	@Override
//	public void onBackPressed() {
//		imageView.setOnTransformListener(new SmoothImageView.TransformListener() {
//			@Override
//			public void onTransformComplete(int mode) {
//				if (mode == 2) {
//					finish();
//				}
//			}
//		});
//		imageView.transformOut();
//	}

	@Override
	protected void onPause() {
		super.onPause();
		if (isFinishing()) {
			overridePendingTransition(0, 0);
		}
	}

}
