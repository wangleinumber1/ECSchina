/**
 * 
 */
package com.jiajie.jiajieproject.net.service;

import java.io.UnsupportedEncodingException;
import java.util.Map;

import android.content.Context;
import android.os.Handler;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.jiajie.jiajieproject.activity.LoginActivity;
import com.jiajie.jiajieproject.activity.PartsActivity;
import com.jiajie.jiajieproject.contents.InterfaceParams;
import com.jiajie.jiajieproject.contents.UserData;
import com.jiajie.jiajieproject.model.OnlyClass;
import com.jiajie.jiajieproject.net.NetRequestService;
import com.jiajie.jiajieproject.utils.IntentUtil;
import com.jiajie.jiajieproject.utils.StringUtil;
import com.jiajie.jiajieproject.utils.ToastUtil;
import com.mrwujay.cascade.model.SomeMessage;

/**
 * 项目名称：NewProject 类名称：MineService 类描述： 创建人：王蕾 创建时间：2015-11-4 上午10:30:41 修改备注：
 */
public class JosnService {
	private static final String TAG = "JosnService";
	private Context mContext;
	private NetRequestService mNetRequService;
	private boolean isToast;
	private boolean success;
	private SomeMessage somemessage;
	private Boolean carIszero=false;
	public JosnService(Context context) {
		this.mContext = context;
		mNetRequService = new NetRequestService(mContext);
		somemessage = new SomeMessage();
	}

	public boolean mNeedCach = false;// 是否需要缓存

	public void setNeedCach(boolean needCach) {
		mNeedCach = needCach;
	}

	public boolean getToastMessage() {
		return isToast;
	}

	public boolean getsuccessState() {
		return success;
	}

	public SomeMessage getsomemessage() {
		return somemessage;
	}
	public Boolean getCarcountIsZero(){
		
	return carIszero;
	
}
	/**
	 * 公共方法
	 * 
	 * @param接口名
	 * @param参数map
	 * @param是否缓存
	 * @param接受值得类
	 * */
	public Object getData(String Interface, Map map, boolean needCach,
			Class anyclass) {
		String str = mNetRequService.requestData("POST", Interface, map,
				needCach);
		if (!StringUtil.checkStr(str))
			return null;

		Log.d(TAG, str);

		OnlyClass onlyClass = JSON.parseObject(str, OnlyClass.class);
		// success=onlyClass.success;
		if (onlyClass.success) {
			if (anyclass == null) {
				isToast = true;
				return onlyClass;
			} else {
				success = true;
				@SuppressWarnings("unchecked")
				Object object = JSON.parseObject(onlyClass.data, anyclass);
				isToast = false;
				return object;
			}

		} else {
			isToast = true;
			// if(onlyClass.data.equalsIgnoreCase("请先登录")){
			// =null;
			// IntentUtil.activityForward(mContext, LoginActivity.class, null,
			// false);
			// };
			return onlyClass;
		}

	}
	

	// 单纯返回数据不处理
	public Object getCareData(String Interface, Map map, boolean needCach,
			Class anyclass) {
		String str = mNetRequService.requestData("POST", Interface, map,
				needCach);
		if (!StringUtil.checkStr(str))
			return null;

		Log.d(TAG, str);

		OnlyClass onlyClass = JSON.parseObject(str, OnlyClass.class);
		return onlyClass;

	}

	/**
	 * 首页公共方法
	 * 
	 * @param接口名
	 * @param参数map
	 * @param是否缓存
	 * @param接受值得类
	 * */
	public Object getFirstDataList(String Interface, Map map, boolean needCach,
			Class anyclass) {
		String str = mNetRequService.requestData("POST", Interface, map,
				needCach);
		if (!StringUtil.checkStr(str))
			return null;
		Log.d(TAG, str);

		OnlyClass onlyClass = JSON.parseObject(str, OnlyClass.class);
//		OnlyClass onlyClass1 = JSON.parseObject(onlyClass.v, OnlyClass.class);
		// success=onlyClass.success;
		if (onlyClass.success) {
			if (anyclass == null) {
				isToast = true;
				
				return onlyClass;
			} else {
				success = true;			
//				PartsActivity.appUrl = onlyClass1.url;
//				PartsActivity.version = onlyClass1.version;
				@SuppressWarnings("unchecked")
				Object object = JSON.parseObject(onlyClass.data, OnlyClass.class);
				isToast = false;
				return object;
			}

		} else {
			isToast = true;
			return onlyClass;
		}

	}

	/**
	 * 公共方法
	 * 
	 * @param接口名
	 * @param参数map
	 * @param是否缓存
	 * @param接受值得类
	 * */
	@SuppressWarnings("unchecked")
	public Object getDataList(String Interface, Map map, boolean needCach,
			Class anyclass) {
		String str = mNetRequService.requestData("POST", Interface, map,
				needCach);
		if (!StringUtil.checkStr(str))
			return null;
		Log.d(TAG, str);

		OnlyClass onlyClass = JSON.parseObject(str, OnlyClass.class);
//		if(Interface.equalsIgnoreCase(InterfaceParams.getProductsByCid)){
//		OnlyClass onlyClass2  = (OnlyClass) JSON.parseArray(onlyClass.v, anyclass);
//		PartsActivity.appUrl=onlyClass2.url;
//		PartsActivity.version=onlyClass2.version;
//		}
		// success=onlyClass.success;
		if (onlyClass.success) {
			if (anyclass == null) {
				isToast = true;
				return onlyClass;
			} else {
				success = true;
				@SuppressWarnings("unchecked")
				Object object = JSON.parseArray(onlyClass.data, anyclass);
				isToast = false;
				return object;
			}

		} else {
			isToast = true;
			return onlyClass;
		}

	}

	/**
	 * 购物车List
	 * 
	 * @param接口名
	 * @param参数map
	 * @param是否缓存
	 * @param接受值得类
	 * */
	public Object getCarDataList(String Interface, Map map, boolean needCach,
			Class anyclass) {
		String str = mNetRequService.requestData("POST", Interface, map,
				needCach);
		if (!StringUtil.checkStr(str))
			return null;
		Log.d(TAG, str);

		OnlyClass onlyClass = JSON.parseObject(str, OnlyClass.class);
				OnlyClass onlyClass1 = JSON.parseObject(onlyClass.data,
						OnlyClass.class);
				somemessage.count = onlyClass1.count;
				somemessage.total_price = onlyClass1.total_price;
				if (onlyClass1.count.equalsIgnoreCase("0")) {
					carIszero=true;
					isToast = false;
					return null;
				} else {
					@SuppressWarnings("unchecked")
					Object object = JSON.parseArray(onlyClass1.data, anyclass);
					isToast = false;
					return object;
				}

			}

		}

	

