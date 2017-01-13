/**
 * 
 */
package com.jiajie.jiajieproject.model;

import java.util.Map;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.alipay.sdk.pay.demo.AuthResult;
import com.alipay.sdk.pay.demo.PayResult;
import com.jiajie.jiajieproject.net.service.PayService;


/**
 * 项目名称：DriveStudent_student 类名称：AlipayClass 类描述： 创建人：王蕾 创建时间：2015-6-9
 * 上午10:58:27 修改备注：
 */
public class AlipayClass {
	// 商户PID
//	public static final String PARTNER = "2088911580508812";
//	// 商户收款账号
//	public static final String SELLER = "seven@dyhoa.com";
//	// 商户私钥，pkcs8格式已修改
//	public static final String RSA_PRIVATE = "MIICeAIBADANBgkqhkiG9w0BAQEFAASCAmIwggJeAgEAAoGBAMBNV2F0hlXTiFrQ1QDaEhHmyAjBv0d5wMwN9KlGeWRguCplzcKdNjOlTzUpLogQPJcwhbt4RvDfSFAMn5pXpk6E1HunsvZBhTYuQooHADdzxFGIGGsF52Lwuw04zJjV8V18viZiwEWvo9BTv+RIWptoJahnyRHM0/6QVBOcB5uNAgMBAAECgYEAvY+/8j5madZSlItFXUiaBXGEgDVU9AVOCxg6tF7XLR62jHy8PvqwQmrTUKkeyFuRDsxzF6Dx9WF1LAu1jPSX5xnk+aguCpmieQjeC6+sgTfPMQqMGbpDE9aXQatd49ySRL1J8sUeTUi6Nv1jpWDtfwtuOaD3+WQLMmW5sD1T6RUCQQDwkmp1L4cZytiq3BAOyj87u+TNwz/nF+OwGiK8KJmolS8Om/XnRGb69/GE1w1ojDaBHkkK8umc+MYJe52E1bP7AkEAzKJ08OCFovyC6l+vqzMb5sPU4zT+liSaUp5jAkNuAdJf4MpfX08dMF0sRqOPnoqpEoy3Rw0oRyuaCUzSM+JQFwJAVUTAQQr4itbQFzdq5aMf6I8/mQL0mndoN5n7589IL225QSdccH1ZNuk9DMWgtgbEpt1SLHRPA1lV8DSFb3jDkwJBAKB0kjV+F8thLFYSiXA4NxyPWZJ+r1GTid5Wi1PvA4cyKjPc+0OEiWKu7FTHU4oBN+lvpRZ1XqvPqIy6S+ibACkCQQCIem2ks8cZ3RF4RWSWRjItJ+91sfnbylduU8I68uk93D1mJ+lx7YMQoHJqtGv66r2kFwZ0HBhBW2k5WqdTh9YR";
	// 商户PID
//		public static final String PARTNER = "2088521250549092";
//		// 商户收款账号
//		public static final String SELLER = "wang.lei@ecschina.com";
//		// 商户私钥，pkcs8格式已修改
//		public static final String RSA_PRIVATE = "MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBAN7TX3llx3HAzBvsMnpez+i8ANdW6LpoElFUCiZxYGAm7mo2jVsETxcWtqXWsq5rjJPF9eG3LCsvm/GTIdWJJy1tkfSWToPcz1FK+vDaCkYub8DOBrr9Bx7S58ZgPg3oegAgC1b71TDkS3ICOwBUCp8GNHFcMo791+Dm92U6AkJXAgMBAAECgYByeJxfvU3MvoMdBSaZfwPISG0WM2uz96sRZN8iCLS+MMHihsvvqjMHZX/HipiYyl+ZuuUqY/Oo6/htDm0fSip5UpzCVmbT1lzH6MCBqgfJli0Se9LHnWDXNRmDSRfjsKtFY/LnVmCQoVCqyoPTcM1feO1d4D2WLRni7mJtysj0gQJBAPDrTIdiNP4lofPjfc3pK4hsLeUntgRdDx2YTO5U62srPJJmZ8KwxIstEHTuAB+8LK3EkyzjA6YkE0QfqcPf+DECQQDsxiDfIgDY4R0Qhsdtv8+CaJs0vv9Aj6BoBXj/1OctO9xY32WWVV4w5pHD75ztGX4g/BanDqbJXINUwguYRMkHAkB5nZ4tPYMYxHCDyFQKbOk/3bQxWKGGhF+MBgTXzuBkYhgaoz0/x7H/u8jx7e78FibLtU7COvrtlIpxOjadnWMRAkBV0MWPOdrGwGhxWfgkDfYe1N0T8/m9o5DGwt0FFFwpZ93IFEfUgeJepOLF+i2NPcUaHWP0bnoC11X+PUpIiP23AkAMSd5dpmGQ059CSlb/u/kecKUS5JPSw3muhcttW49+at2DfW0tGauyBsRw9L6puRbziGGtvXx/zRABsrsqPdvk";
//	// 支付宝公钥
//	public static final String RSA_PUBLIC = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCnxj/9qwVfgoUh/y2W89L6BkRAFljhNhgPdyPuBV64bfQNN1jbCzkIM6qRdKBoLPXmKKMiFYnkd6rAoprih3/PrQEB/VsW8OoM8fxn67UDYuyBTqA23MML9q1+ilIZwBC2AQ2UBVOrFXfFl75p6/B5KsiNG9zpgmLCUYuLkxpLQIDAQAB";

	private Activity activcty;
	private Boolean isfinish=false;
	public static String TAG = "PayDemoActivity";
	private static final int SDK_PAY_FLAG = 1;
	private static final int SDK_AUTH_FLAG = 2;
	@SuppressLint("HandlerLeak")
	private Handler mHandler = new Handler() {
		@SuppressWarnings("unused")
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case SDK_PAY_FLAG: {
				@SuppressWarnings("unchecked")
				PayResult payResult = new PayResult((Map<String, String>) msg.obj);
				/**
				 对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
				 */
				String resultInfo = payResult.getResult();// 同步返回需要验证的信息
				String resultStatus = payResult.getResultStatus();
				// 判断resultStatus 为9000则代表支付成功
				if (TextUtils.equals(resultStatus, "9000")) {
					// 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
					Toast.makeText(activcty, "支付成功", Toast.LENGTH_SHORT).show();
					//支付成功结束页面
					if(isfinish){
					activcty.finish();
					}
				} else {
					// 该笔订单真实的支付结果，需要依赖服务端的异步通知。
					Toast.makeText(activcty, "支付失败", Toast.LENGTH_SHORT).show();
				}
				break;
			}
			case SDK_AUTH_FLAG: {
				@SuppressWarnings("unchecked")
				AuthResult authResult = new AuthResult((Map<String, String>) msg.obj, true);
				String resultStatus = authResult.getResultStatus();

				// 判断resultStatus 为“9000”且result_code
				// 为“200”则代表授权成功，具体状态码代表含义可参考授权接口文档
				if (TextUtils.equals(resultStatus, "9000") && TextUtils.equals(authResult.getResultCode(), "200")) {
					// 获取alipay_open_id，调支付时作为参数extern_token 的value
					// 传入，则支付账户为该授权账户
					Toast.makeText(activcty,
							"授权成功\n" + String.format("authCode:%s", authResult.getAuthCode()), Toast.LENGTH_SHORT)
							.show();
				} else {
					// 其他状态值则为授权失败
					Toast.makeText(activcty,
							"授权失败" + String.format("authCode:%s", authResult.getAuthCode()), Toast.LENGTH_SHORT).show();

				}
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
	public AlipayClass(String orderInfo, Activity activcty) {
		super();
		this.activcty = activcty;
		pay(orderInfo);
	}
	public AlipayClass(String orderInfo, Activity activcty,boolean isfinish) {
		super();
		this.activcty = activcty;
		this.isfinish = isfinish;
		pay(orderInfo);
	}
	
	/**
	 * call alipay sdk pay. 调用SDK支付
	 * 
	 */
	public void pay(final String orderInfo) {
		Runnable payRunnable = new Runnable() {

			@Override
			public void run() {
				PayTask alipay = new PayTask(activcty);
				Map<String, String> result = alipay.payV2(orderInfo, true);
				Log.i("msp", result.toString());
				
				Message msg = new Message();
				msg.what = SDK_PAY_FLAG;
				msg.obj = result;
				mHandler.sendMessage(msg);
			}
		};

		Thread payThread = new Thread(payRunnable);
		payThread.start();
	}

	
}
