/**
 * 
 */
package com.jiajie.jiajieproject.model;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.alipay.sdk.pay.demo.PayResult;
import com.alipay.sdk.pay.demo.SignUtils;
import com.jiajie.jiajieproject.MainActivity;
import com.jiajie.jiajieproject.contents.ReciverContents;
import com.jiajie.jiajieproject.net.service.PayService;
import com.jiajie.jiajieproject.utils.MyAsyncTask;


/**
 * 项目名称：DriveStudent_student 类名称：AlipayClass 类描述： 创建人：王蕾 创建时间：2015-6-9
 * 上午10:58:27 修改备注：
 */
public class AlipayClass {
	// 商户PID
	public static final String PARTNER = "2088911580508812";
	// 商户收款账号
	public static final String SELLER = "seven@dyhoa.com";
	// 商户私钥，pkcs8格式已修改
	public static final String RSA_PRIVATE = "MIICeAIBADANBgkqhkiG9w0BAQEFAASCAmIwggJeAgEAAoGBAMBNV2F0hlXTiFrQ1QDaEhHmyAjBv0d5wMwN9KlGeWRguCplzcKdNjOlTzUpLogQPJcwhbt4RvDfSFAMn5pXpk6E1HunsvZBhTYuQooHADdzxFGIGGsF52Lwuw04zJjV8V18viZiwEWvo9BTv+RIWptoJahnyRHM0/6QVBOcB5uNAgMBAAECgYEAvY+/8j5madZSlItFXUiaBXGEgDVU9AVOCxg6tF7XLR62jHy8PvqwQmrTUKkeyFuRDsxzF6Dx9WF1LAu1jPSX5xnk+aguCpmieQjeC6+sgTfPMQqMGbpDE9aXQatd49ySRL1J8sUeTUi6Nv1jpWDtfwtuOaD3+WQLMmW5sD1T6RUCQQDwkmp1L4cZytiq3BAOyj87u+TNwz/nF+OwGiK8KJmolS8Om/XnRGb69/GE1w1ojDaBHkkK8umc+MYJe52E1bP7AkEAzKJ08OCFovyC6l+vqzMb5sPU4zT+liSaUp5jAkNuAdJf4MpfX08dMF0sRqOPnoqpEoy3Rw0oRyuaCUzSM+JQFwJAVUTAQQr4itbQFzdq5aMf6I8/mQL0mndoN5n7589IL225QSdccH1ZNuk9DMWgtgbEpt1SLHRPA1lV8DSFb3jDkwJBAKB0kjV+F8thLFYSiXA4NxyPWZJ+r1GTid5Wi1PvA4cyKjPc+0OEiWKu7FTHU4oBN+lvpRZ1XqvPqIy6S+ibACkCQQCIem2ks8cZ3RF4RWSWRjItJ+91sfnbylduU8I68uk93D1mJ+lx7YMQoHJqtGv66r2kFwZ0HBhBW2k5WqdTh9YR";
	// 商户PID
//		public static final String PARTNER = "2088521250549092";
//		// 商户收款账号
//		public static final String SELLER = "wang.lei@ecschina.com";
//		// 商户私钥，pkcs8格式已修改
//		public static final String RSA_PRIVATE = "MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBAN7TX3llx3HAzBvsMnpez+i8ANdW6LpoElFUCiZxYGAm7mo2jVsETxcWtqXWsq5rjJPF9eG3LCsvm/GTIdWJJy1tkfSWToPcz1FK+vDaCkYub8DOBrr9Bx7S58ZgPg3oegAgC1b71TDkS3ICOwBUCp8GNHFcMo791+Dm92U6AkJXAgMBAAECgYByeJxfvU3MvoMdBSaZfwPISG0WM2uz96sRZN8iCLS+MMHihsvvqjMHZX/HipiYyl+ZuuUqY/Oo6/htDm0fSip5UpzCVmbT1lzH6MCBqgfJli0Se9LHnWDXNRmDSRfjsKtFY/LnVmCQoVCqyoPTcM1feO1d4D2WLRni7mJtysj0gQJBAPDrTIdiNP4lofPjfc3pK4hsLeUntgRdDx2YTO5U62srPJJmZ8KwxIstEHTuAB+8LK3EkyzjA6YkE0QfqcPf+DECQQDsxiDfIgDY4R0Qhsdtv8+CaJs0vv9Aj6BoBXj/1OctO9xY32WWVV4w5pHD75ztGX4g/BanDqbJXINUwguYRMkHAkB5nZ4tPYMYxHCDyFQKbOk/3bQxWKGGhF+MBgTXzuBkYhgaoz0/x7H/u8jx7e78FibLtU7COvrtlIpxOjadnWMRAkBV0MWPOdrGwGhxWfgkDfYe1N0T8/m9o5DGwt0FFFwpZ93IFEfUgeJepOLF+i2NPcUaHWP0bnoC11X+PUpIiP23AkAMSd5dpmGQ059CSlb/u/kecKUS5JPSw3muhcttW49+at2DfW0tGauyBsRw9L6puRbziGGtvXx/zRABsrsqPdvk";
	// 支付宝公钥
	public static final String RSA_PUBLIC = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCnxj/9qwVfgoUh/y2W89L6BkRAFljhNhgPdyPuBV64bfQNN1jbCzkIM6qRdKBoLPXmKKMiFYnkd6rAoprih3/PrQEB/VsW8OoM8fxn67UDYuyBTqA23MML9q1+ilIZwBC2AQ2UBVOrFXfFl75p6/B5KsiNG9zpgmLCUYuLkxpLQIDAQAB";

	private String notifyUrl = "http://www.onlcy.com/d/pay/alipay/notify";

	private static final int SDK_PAY_FLAG = 1;

	private static final int SDK_CHECK_FLAG = 2;
	private String json;
	private String orderMessage;
	private String orderprice;
	private String ordername;
	private String orderid;
	private String stuName;
	private String idcard;
	private String stuPhone;
	private String feeprice;
	private String eduSystem;
	private String schoolCode;
	private Activity activcty;
	public static String TAG = "PayDemoActivity";
	private PayService payService;
	private Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case SDK_PAY_FLAG: {
				PayResult payResult = new PayResult((String) msg.obj);
				// 支付宝返回此次支付结果及加签，建议对支付宝签名信息拿签约时支付宝提供的公钥做验签
				String resultInfo = payResult.getResult();
				String resultStatus = payResult.getResultStatus();

				// 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
				if (TextUtils.equals(resultStatus, "9000")) {
//					Toast.makeText(activcty, "支付成功", Toast.LENGTH_SHORT).show();
					new AliPayAsyTask(activcty).execute();
					// Intent intent=new Intent();
					// Bundle bundle=new Bundle();
					// bundle.putString(MainActivity.TAG,"1000");
					// intent.putExtras(bundle);
					// intent.setAction(ReciverContents.PaySuccess);
					// activcty.sendBroadcast(intent);

				} else {
					// 判断resultStatus 为非“9000”则代表可能支付失败
					// “8000”代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
					if (TextUtils.equals(resultStatus, "8000")) {
						Toast.makeText(activcty, "支付结果确认中", Toast.LENGTH_SHORT)
								.show();

					} else {
						// 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
						Toast.makeText(activcty, "支付失败", Toast.LENGTH_SHORT)
								.show();

					}
				}
				break;
			}
			case SDK_CHECK_FLAG: {
				Toast.makeText(activcty, "检查结果为：" + msg.obj, Toast.LENGTH_SHORT)
						.show();
				break;
			}
			default:
				break;
			}
		};
	};

	/**
	 * 构造方法
	 * */
	public AlipayClass(String json, Activity activcty) {
		super();
		this.json = json;
		this.activcty = activcty;
		payService=new PayService(activcty);
		pay();
	}
	/**
	 * 构造方法
	 * */
	public AlipayClass( Activity activcty) {
		this.activcty = activcty;
		payService=new PayService(activcty);
		pay();
	}

	// @Override
	// protected void onCreate(Bundle savedInstanceState) {
	// super.onCreate(savedInstanceState);
	// setContentView(R.layout.pay_main);
	// // if (getIntent().getExtras().getString(TAG) != null) {
	// // json = getIntent().getExtras().getString(TAG);
	// // orderJson();
	// // } else {
	// // Toast.makeText(this, "获取订单失败,请重新再试", Toast.LENGTH_LONG).show();
	// // // finish();
	// // }
	//
	// }

	/**
	 * call alipay sdk pay. 调用SDK支付
	 * 
	 */
	public void pay() {

		orderJson();
		// 订单
		String orderInfo = getOrderInfo(ordername, orderMessage, orderprice);

		// 对订单做RSA 签名
		String sign = sign(orderInfo);
		try {
			// 仅需对sign 做URL编码
			sign = URLEncoder.encode(sign, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		// 完整的符合支付宝参数规范的订单信息
		final String payInfo = orderInfo + "&sign=\"" + sign + "\"&"
				+ getSignType();

		Runnable payRunnable = new Runnable() {

			@Override
			public void run() {
				// 构造PayTask 对象
				PayTask alipay = new PayTask(activcty);
				// 调用支付接口，获取支付结果
				String result = alipay.pay(payInfo);

				Message msg = new Message();
				msg.what = SDK_PAY_FLAG;
				msg.obj = result;
				mHandler.sendMessage(msg);
			}
		};

		// 必须异步调用
		Thread payThread = new Thread(payRunnable);
		payThread.start();
	}

	/**
	 * check whether the device has authentication alipay account.
	 * 查询终端设备是否存在支付宝认证账户
	 * 
	 */
	public void check() {
		Runnable checkRunnable = new Runnable() {

			@Override
			public void run() {
				// 构造PayTask 对象
				PayTask payTask = new PayTask(activcty);
				// 调用查询接口，获取查询结果
				boolean isExist = payTask.checkAccountIfExist();

				Message msg = new Message();
				msg.what = SDK_CHECK_FLAG;
				msg.obj = isExist;
				mHandler.sendMessage(msg);
			}
		};

		Thread checkThread = new Thread(checkRunnable);
		checkThread.start();

	}

	/**
	 * get the sdk version. 获取SDK版本号
	 * 
	 */
	// public void getSDKVersion() {
	// PayTask payTask = new PayTask(this);
	// String version = payTask.getVersion();
	// Toast.makeText(this, version, Toast.LENGTH_SHORT).show();
	// }

	/**
	 * create the order info. 创建订单信息
	 * 
	 */
	public String getOrderInfo(String subject, String body, String price) {
		// 签约合作者身份ID
		String orderInfo = "partner=" + "\"" + PARTNER + "\"";

		// 签约卖家支付宝账号
		orderInfo += "&seller_id=" + "\"" + SELLER + "\"";

		// 商户网站唯一订单号
		orderInfo += "&out_trade_no=" + "\"" + getOutTradeNo() + "\"";

		// 商品名称
		orderInfo += "&subject=" + "\"" + subject + "\"";

		// 商品详情
		orderInfo += "&body=" + "\"" + body + "\"";

		// 商品金额
		orderInfo += "&total_fee=" + "\"" + orderprice+"\"";

		// 服务器异步通知页面路径
		orderInfo += "&notify_url=" + "\"" + notifyUrl + "\"";

		// 服务接口名称， 固定值
		orderInfo += "&service=\"mobile.securitypay.pay\"";

		// 支付类型， 固定值
		orderInfo += "&payment_type=\"1\"";

		// 参数编码， 固定值
		orderInfo += "&_input_charset=\"utf-8\"";

		// 设置未付款交易的超时时间
		// 默认30分钟，一旦超时，该笔交易就会自动被关闭。
		// 取值范围：1m～15d。
		// m-分钟，h-小时，d-天，1c-当天（无论交易何时创建，都在0点关闭）。
		// 该参数数值不接受小数点，如1.5h，可转换为90m。
		orderInfo += "&it_b_pay=\"30m\"";

		// extern_token为经过快登授权获取到的alipay_open_id,带上此参数用户将使用授权的账户进行支付
		// orderInfo += "&extern_token=" + "\"" + extern_token + "\"";

		// 支付宝处理完请求后，当前页面跳转到商户指定页面的路径，可空
		orderInfo += "&return_url=\"m.alipay.com\"";

		// 调用银行卡支付，需配置此参数，参与签名， 固定值 （需要签约《无线银行卡快捷支付》才能使用）
		// orderInfo += "&paymethod=\"expressGateway\"";

		return orderInfo;
	}

	/**
	 * get the out_trade_no for an order. 生成商户订单号，该值在商户端应保持唯一（可自定义格式规范）
	 * 
	 */
	public String getOutTradeNo() {
		SimpleDateFormat format = new SimpleDateFormat("MMddHHmmss",
				Locale.getDefault());
		Date date = new Date();
		String key = format.format(date);

		Random r = new Random();
		key = key + r.nextInt();
		key = key.substring(0, 15);
		return key;
	}

	/**
	 * sign the order info. 对订单信息进行签名
	 * 
	 * @param content
	 *            待签名订单信息
	 */
	public String sign(String content) {
		return SignUtils.sign(content, RSA_PRIVATE);
	}

	/**
	 * get the sign type we use. 获取签名方式
	 * 
	 */
	public String getSignType() {
		return "sign_type=\"RSA\"";
	}

	@SuppressWarnings("unused")
	private class AliPayAsyTask extends MyAsyncTask {

		/**
		 * @param context
		 */
		protected AliPayAsyTask(Context context) {
			super(context);
			// TODO Auto-generated constructor stub
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
				activcty.sendBroadcast(intent);
			}

		}

	}

	/***
	 * 订单信息解析
	 */
	@SuppressWarnings("unused")
	private void orderJson() {
		String json="{'schoolCode': '"+"1001"+"','stuName':'"+"123"+"','idcard':'"+"123"+"','stuPhone':'"+"123"+"','eduSystem':'"+"123"+"','feeprice':'"+"0.01"+"','orderid':'"+"123"+"','ordertitle':'"+"123"+"','ordercon':'"+"123"+"','orderprice':'"+"0.01"+"'}";
		try {
			JSONObject jsonObject = new JSONObject(json);			
			orderMessage = jsonObject.getString("ordercon");
			orderprice = jsonObject.getString("orderprice");
			ordername = jsonObject.getString("ordertitle");
			orderid = jsonObject.getString("orderid");
			stuName = jsonObject.getString("stuName");
			idcard = jsonObject.getString("idcard");
			stuPhone = jsonObject.getString("stuPhone");
			feeprice = jsonObject.getString("feeprice");
			eduSystem = jsonObject.getString("eduSystem");
			schoolCode = jsonObject.getString("schoolCode");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
