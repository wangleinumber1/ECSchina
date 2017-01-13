package com.jiajie.jiajieproject.activity;

import java.util.*;

import android.widget.Toast;

import net.sourceforge.simcpux.Constants;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import com.jiajie.jiajieproject.R;
import com.jiajie.jiajieproject.net.NetUrl;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

public class WXPayActivity extends Activity {

	public static final String TAG = "MicroMsg.SDKSample.WXPayActivity";
	final IWXAPI msgApi = WXAPIFactory.createWXAPI(this, null);
	Map<String, String> resultunifiedorder;
	StringBuffer sb;
	private String notifyUrl = NetUrl.TEST_HOST+"/app_api/wexinpay/notifyy";
	IWXAPI api;
	private String pag_sign;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pay);
		api = WXAPIFactory.createWXAPI(this, Constants.APP_ID);
		api.registerApp(Constants.APP_ID); 
		pag_sign=getIntent().getExtras().getString(OrdercoConfirmationActivity.TAG);
		PayReq req = new PayReq();
		JSONObject json;
		try {
			json = new JSONObject(pag_sign);
		
		//req.appId = "wxf8b4f85f3a794e77";  // 测试用appId
		req.appId			= json.getString("appid");
		req.partnerId		= json.getString("partnerid");
		req.prepayId		= json.getString("prepayid");
		req.nonceStr		= json.getString("noncestr");
		req.timeStamp		= json.getString("timestamp");
		req.packageValue	= json.getString("package");
		req.sign			= json.getString("sign");
		req.extData			= "app data"; // optional
		Toast.makeText(WXPayActivity.this, "正常调起支付", Toast.LENGTH_SHORT).show();
		// 在支付之前，如果应用没有注册到微信，应该先调用IWXMsg.registerApp将应用注册到微信
		api.sendReq(req);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			finish();
		}
	}

	
}
