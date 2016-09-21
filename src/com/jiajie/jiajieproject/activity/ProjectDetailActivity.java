/**
 * 
 */
package com.jiajie.jiajieproject.activity;

import com.jiajie.jiajieproject.R;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

/**
 * 项目名称：NewProject 类名称：ProjectDetailActivity 类描述： 创建人：王蕾 创建时间：2015-9-28
 * 下午3:23:03 修改备注：
 */
public class ProjectDetailActivity extends BaseActivity implements
		OnClickListener {
	private ImageView headerleftImg;
	private String url;
	private WebView mealsView;

	@SuppressLint("SetJavaScriptEnabled")
	@Override
	protected void onInit(Bundle bundle) {
		// TODO Auto-generated method stub
		super.onInit(bundle);
		setContentView(R.layout.projectdetail_layout);
		headerleftImg = (ImageView) findViewById(R.id.headerleftImg);
		headerleftImg.setOnClickListener(this);
		url = getIntent().getExtras().getString("weburl");
		mealsView = (WebView) findViewById(R.id.mealsWebview);
		mealsView.getSettings().setJavaScriptEnabled(true);
		WebSettings settings = mealsView.getSettings();
		settings.setUseWideViewPort(true);
		settings.setLoadWithOverviewMode(true);
		mealsView.setWebViewClient(new MyWebViewClient());
		mealsView.loadUrl(url);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.headerleftImg:
			finish();
			break;

		default:
			break;
		}

	}

	private class MyWebViewClient extends WebViewClient {
		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			view.loadUrl(url);

			return super.shouldOverrideUrlLoading(view, url);
		}
	}

}
