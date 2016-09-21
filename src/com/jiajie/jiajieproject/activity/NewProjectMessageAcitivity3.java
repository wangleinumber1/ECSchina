/**
 * 
 */
package com.jiajie.jiajieproject.activity;

import com.jiajie.jiajieproject.R;
import com.jiajie.jiajieproject.utils.ToastUtil;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

/**
 * 项目名称：NewProject 类名称：salespartsAcitivity 类描述： 创建人：王蕾 创建时间：2015-9-9 上午11:55:04
 * 修改备注： 促销备件
 */
public class NewProjectMessageAcitivity3 extends BaseActivity {
	private WebView webview;
	private ImageView leftImage;

	@Override
	protected void onInit(Bundle bundle) {
		// TODO Auto-generated method stub
		super.onInit(bundle);
		setContentView(R.layout.newprojectmessage_layout3);
		InitView();
	}

	private void InitView() {
		leftImage = (ImageView) findViewById(R.id.headerleftImg);

		webview = (WebView) findViewById(R.id.webview);
		webview.getSettings().setJavaScriptEnabled(true);
		// 设置可以支持缩放
		webview.getSettings().setSupportZoom(true);
		// 设置出现缩放工具
		webview.getSettings().setBuiltInZoomControls(true);
		// 扩大比例的缩放
		webview.getSettings().setUseWideViewPort(true);
		// 自适应屏幕
		webview.getSettings().setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);
		webview.getSettings().setLoadWithOverviewMode(true);
		webview.setWebViewClient(new MyWebViewClient());
		webview.loadUrl("http://info.ecs-svs.com:18080/project/search");
		leftImage.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (webview.canGoBack()) {
					webview.goBack();
				} else {
					ToastUtil.showToast(mContext, "已经是最后一页");
				}

			}

		});
	}

	private class MyWebViewClient extends WebViewClient {
		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			view.loadUrl(url);

			return super.shouldOverrideUrlLoading(view, url);
		}
	}

}
