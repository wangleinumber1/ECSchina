package com.jiajie.jiajieproject.net;

import com.jiajie.jiajieproject.Config;





/*
 * 请求数据的url
 */
//
public class NetUrl {
	
	/*
	 * 测试服务器ip
	 */
	//public static final String TEST_HOST = "http://59.151.9.86/api/";//外网测试地址 测试环境 + 测试数据//TEST_NORMAL_DATA_HOST
	//public static final String TEST_HOST = "http://192.168.52.108/api/";
	
	/*
	 * 准生产环境 测试环境 + 正式数据
	 */
//	public static final String TEST_HOST = "http://117.78.36.198/index.php/";
	public static final String TEST_HOST = "http://mall.ecsits.com/index.php/";
//	public static final String TEST_HOST = "http://172.31.160.59/index.php/";
//	public static final String TEST_HOST = "http://43.254.3.166/index.php/";
	public static final String DateBaseUrl = "http://117.78.35.247:8080/index.php/download";
	

	
	//外网
//	public static final String TEST_HOST = "http://123.56.248.192/index.php/";
//	public static final String TEST_HOST = "http://172.31.167.33/index.php/";
	
   // public static final String TEST_HOST = "http://192.168.53.75/api/";
   // public static final String TEST_HOST = "http://59.151.3.137/api/";
	//public static final String TEST_HOST = "http://192.168.53.67/api/";

	/*
	 * 正式服务器IP(域名)
	 */
	public static final String ONLINE_HOST = "http://m.ymall.com/api/";
	
	/*
	 * ali支付正式环境(web)
	 */
	public static final String ALI_ZHIFU_ONLINE = ONLINE_HOST+"paycenter/payform?";//type=alipay
	
	/*
	 * ali支付测试环境(web)
	 */
	public static final String ALI_ZHIFU_TEST = TEST_HOST+"paycenter/payform?";//type=alipay
	//取得web支付接口信息
	//GET	/api/payment/payForm?type=alipay&id=#order_id
	//POST	/api/payment/webnotify?type=alipay (传给支付宝的回调服务器修改订单状态地址,客户端需要处理)


	//GET	/api/help/alipay (客户端支付相关信息)
	//POST	/api/payment/webnotify?type=appalipay  传给支付宝的回调服务器修改订单状态地址

	/*
	 * ali老版本支付正式环境(客户端)
	 */
	public static final String ALI_OLD_ZHIFU_CLIENT_ONLINE = ONLINE_HOST+"payment/webnotify?type=appalipay";
	/*
	 * ali老版本支付测试环境(客户端)
	 */
	public static final String ALI_OLD_ZHIFU_CLIENT_TEST = TEST_HOST+"payment/webnotify?type=appalipay";
	/*
	 * 得到老版本ali Client的回调
	 */
	public static String getOldZhifuClientCallBack(){
		return Config.IS_TEST?ALI_OLD_ZHIFU_CLIENT_TEST:ALI_OLD_ZHIFU_CLIENT_ONLINE;
	}
	
	/*
	 * ali支付正式环境(客户端)
	 */
	public static final String ALI_ZHIFU_CLIENT_ONLINE = ONLINE_HOST+"paycenter/webnotify?type=appalipay";
	/*
	 * ali支付测试环境(客户端)
	 */
	public static final String ALI_ZHIFU_CLIENT_TEST = TEST_HOST+"paycenter/webnotify?type=appalipay";
	
	/*
	 * 得到ali Client的回调
	 */
	public static String getZhifuClientCallBack(){
		return Config.IS_TEST?ALI_ZHIFU_CLIENT_TEST:ALI_ZHIFU_CLIENT_ONLINE;
	}
	
	/*
	 * 得到ali web的回调
	 */
	public static String getZhifuWebCallBack(){
		return Config.IS_TEST?ALI_ZHIFU_TEST:ALI_ZHIFU_ONLINE;
	}
	/*
	 * 测试环境分享链接
	 * http://59.151.3.137/app_proxy.html?goods_id=104247
	 */
	public static final String TEST_SHAREURL = "http://59.151.9.86/app_proxy.html?goods_id=";
	/*
	 * 正式环境分享链接
	 */
	public static final String ONLINE_SHAREURL = "http://m.ymall.com/app_proxy.html?goods_id=";
	/*
	 * 得到分享链接
	 */
	public static String getShareurl(String goods_id){
		return Config.IS_TEST?TEST_SHAREURL+goods_id:ONLINE_SHAREURL+goods_id;
	}
	
}
