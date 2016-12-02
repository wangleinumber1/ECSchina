package com.jiajie.jiajieproject.wxapi;

import org.json.JSONException;
import org.json.JSONObject;

import net.sourceforge.simcpux.Constants;

import com.jiajie.jiajieproject.MainActivity;
import com.jiajie.jiajieproject.R;
import com.jiajie.jiajieproject.contents.ReciverContents;
import com.jiajie.jiajieproject.contents.UserData;
import com.jiajie.jiajieproject.net.service.PayService;
import com.jiajie.jiajieproject.utils.MyAsyncTask;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {

	private static final String TAG = "MicroMsg.SDKSample.WXPayEntryActivity";
	private String orderid;
	private String stuName;
	private String idcard;
	private String stuPhone;
	private String feeprice;
	private String eduSystem;
	private String schoolCode;
	private IWXAPI api;
	private String json;
	private PayService payService;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pay_result);
		payService = new PayService(this);
		api = WXAPIFactory.createWXAPI(this, Constants.APP_ID);
		api.handleIntent(getIntent(), this);
	}

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		setIntent(intent);
		api.handleIntent(intent, this);
	}

	@Override
	public void onReq(BaseReq req) {
		
	}

	@Override
	public void onResp(BaseResp resp) {
		Log.d(TAG, "onPayFinish, errCode = " + resp.errCode);
		AlertDialog.Builder builder = null;
		switch (resp.errCode) {
		case 0:
			
			new AliPayAsyTask(this).execute();
			break;
		case -1:
			builder = new AlertDialog.Builder(this);
			builder.setTitle(R.string.app_tip);
			builder.setMessage("支付失败");
			builder.show();
			finish();
			break;
		case -2:
			builder = new AlertDialog.Builder(this);
			builder.setTitle(R.string.app_tip);
			builder.setMessage("支付取消了");
			builder.show();
			finish();
			break;

		default:
			break;
		}
	}
	
	@SuppressWarnings("unused")
	private class AliPayAsyTask extends MyAsyncTask {

		/**
		 * @param context
		 */
		protected AliPayAsyTask(Context context) {
			super(context);
		}

		@Override
		protected Object doInBackground(Object... params) {
			// TODO Auto-generated method stub
			return payService.getPayInfo(stuName, idcard, stuPhone, feeprice,
					eduSystem, schoolCode);
		}

		@Override
		protected void onPostExecute(Object result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if (result != null && (Boolean) result) {
				Intent intent = new Intent();
				Bundle bundle = new Bundle();
				bundle.putString(MainActivity.TAG, "1000");
				intent.putExtras(bundle);
				intent.setAction(ReciverContents.PaySuccess);
				WXPayEntryActivity.this.sendBroadcast(intent);
				WXPayEntryActivity.this.finish();
			}

		}

	}
	

}