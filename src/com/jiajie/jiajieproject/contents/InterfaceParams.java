package com.jiajie.jiajieproject.contents;

/*
 * 接口连接地址  http://59.151.9.86/api_intro/index.htm
 */
public class InterfaceParams {
	/*
	 * 注册
	 */
	public static final String REGISTER = "app_api/api/signIn";
	/*
	 * 获取验证码
	 */
	public static final String SENDSMS = "app_api/api/sendSms";
	/*
	 * 忘记密码验证码提交
	 */
	public static final String ForgetPOSTSMSCODE = "app_api/api/postSmsForPass";
	/*
	 * 验证码提交
	 */
	public static final String POSTSMSCODE = "app_api/api/postSmsCode";

	/*
	 * 登陆接口
	 */
	public static final String LOGIN = "app_api/api/login";

	/*
	 * 设置密码接口
	 */
	public static final String RESETPASSWORD = "app_api/api/resetPassword";
	/*
	 * 修改密码接口
	 */
	public static final String CHANGEPASSWORD = "app_api/api/changePassword";
	/*
	 * 注销接口
	 */
	public static final String LOGINOUT = "app_api/api/logout";

	/*
	 * 修改用户信息接口
	 */
	public static final String editUserInfo = "app_api/api/editUserInfo";
	/*
	 * 用户信息接口
	 */
	public static final String myInfo = "app_api/api/myInfo";
	/*
	 * 搜索产品列表接口
	 */
	public static final String searchProducts = "app_api/api/searchProducts";
	/*
	 * 获取产品列表接口
	 */
	public static final String listProducts = "app_api/api/listProducts";
	/*
	 * 获取产品列表接口
	 * 二维码接口
	 */
	public static final String getProductByScan = "app_api/api/getProductByScan";
	/*
	 * 获取分类接口
	 */
	public static final String getCategoriesByCid = "app_api/api/getCategoriesByCid";
	/*
	 * 根据分类获取产品接口
	 */
	public static final String getProductsByCid = "app_api/api/getProductsByCid";
	/*
	 * 产品详情接口
	 */
	public static final String productInfo = "app_api/api/productInfo";
	/*
	 * 添加购物车接口
	 */
	public static final String addCart = "app_api/api/addCart";
	/*
	 * 获取购物车列表接口
	 */
	public static final String cart = "app_api/api/cart";
	/*
	 * 清空购物车接口
	 */
	public static final String updateCart = "app_api/api/updateCart";
	/*
	 * 移除购物车接口
	 */
	public static final String deleteCart = "app_api/api/deleteCart";
	/*
	 * 获取订单列表接口
	 */
	public static final String orderList = "app_api/api/orderListByStatus";
	
	/*
	 * 我的订单接口
	 */
	public static final String MyorderList = "app_api/api/orderList";
	
	/*
	 * 订单详情接口
	 */
	public static final String orderInfo = "app_api/api/orderInfo";
	/*
	 * 取消订单接口
	 */
	public static final String cancelOrder = "app_api/api/cancelOrder";
	/*
	 *删除订单接口
	 */
	public static final String deletelOrder = "app_api/api/deleteOrder";
	/*
	 * 生成订单接口
	 */
	public static final String saveOrder = "app_api/api/saveOrder";
	/*
	 * 保存收货地址接口
	 */
	public static final String saveAddress = "app_api/api/saveAddress";
	/*
	 * 获取收货地址列表接口
	 */
	public static final String listAddress = "app_api/api/listAddress";
	/*
	 * 修改收货地址接口
	 */
	public static final String editAddress = "app_api/api/editAddress";
	/*
	 * 删除收货地址接口
	 */
	public static final String deleteAddress = "app_api/api/deleteAddress";
	/*
	 * 地址详情接口
	 */
	public static final String addressInfo = "app_api/api/addressInfo";
	/*
	 * 设置默认地址接口
	 */
	public static final String setDefaultAddress = "app_api/api/setDefaultAddress";
	/*
	 * 获取默认地址接口
	 */
	public static final String getDefaultAddress = "app_api/api/getDefaultAddress";
	/*
	 * 添加关注商品接口
	 */
	public static final String addWishList = "app_api/api/addWishList";
	/*
	 * 关注商品列表接口
	 */
	public static final String WishList = "app_api/api/wishList";
	/*
	 * 判断用户是否关注此商品接口
	 */
	public static final String userWishList = "app_api/api/userWishList";
	/*
	 * 移除我的关注商品接口
	 */
	public static final String removeWishList = "app_api/api/removeWishList";
	/*
	 * 取消我的关注商品接口
	 */
	public static final String deleteWishListByProduct = "app_api/api/deleteWishListByProduct";
	/*
	 * 移除我的关注商品接口
	 */
	public static final String getCms = "app_api/api/getCms";
	
	/*支付回调接口
	 * */
	public static final String payintrerface="自己的支付接口";
	/*畅销备件接口
	 * */
	public static final String getBestseller="app_api/api/getBestseller";
	/*热卖备件接口
	 * */
	public static final String getOnsale="app_api/api/getOnsale";
	/*最新备件接口
	 * */
	public static final String getNew="app_api/api/getNew";
	/*浏览最多接口
	 * */
	public static final String getMostviewed="app_api/api/getMostviewed";
	/*推荐备件接口
	 * */
	public static final String getFeatured="app_api/api/getFeatured";
	
	/*获取首页数据
	 * */
	public static final String getAll="app_api/api/getAll";
	
	/*热门搜索接口
	 * */
	public static final String getTags="app_api/api/getTags";
	/*微信订单完成回掉接口
	 * */
	public static final String notifyForApp="app_api/wexinpay/notifyForApp";
	/*微信订单完成回掉接口
	 * */
	public static final String alinotifyForApp="app_api/alipayapi/notifyForApp";
	/*去支付接口
	 * */
	public static final String Repay="app_api/api/rePay";
	/*确认收货接口
	 * */
	public static final String completeOrder="app_api/api/completeOrder";
	/*再次购买接口
	 * */
	public static final String reorder="app_api/api/reorder";
	
	
}
