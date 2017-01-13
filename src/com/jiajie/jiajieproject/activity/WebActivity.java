package com.jiajie.jiajieproject.activity;

import com.jiajie.jiajieproject.R;

import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;

public class WebActivity extends BaseActivity{
	private WebView webview;
	@Override
	protected void onInit(Bundle bundle) {
		// TODO Auto-generated method stub
		super.onInit(bundle);
		setContentView(R.layout.web_layout);
		webview = (WebView) findViewById(R.id.webview);  
		findViewById(R.id.headerleftImg).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
				
			}
		});  
        WebSettings webSettings = webview.getSettings();  
        //设置WebView属性，能够执行Javascript脚本    
        webSettings.setJavaScriptEnabled(true);    
        //设置可以访问文件  
        webSettings.setAllowFileAccess(true);  
         //设置支持缩放  
        webSettings.setBuiltInZoomControls(true);  
        //加载需要显示的网页    
        webview.loadUrl("http://www.ecsits.com/service_clause.html");    
      
	}

	
}
