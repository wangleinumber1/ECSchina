/**
 * 
 */
package com.jiajie.jiajieproject.net.service;

import java.util.HashMap;
import java.util.Map;

import android.content.Context;

import com.jiajie.jiajieproject.contents.InterfaceParams;
import com.jiajie.jiajieproject.net.NetRequestService;

/**   
 * 项目名称：DriveStudent_student   
 * 类名称：PayService   
 * 类描述：   
 * 创建人：王蕾
 * 创建时间：2015-7-6 下午6:15:49   
 * 修改备注：    
 */
public class PayService {
	private static final String TAG = "PayService";
	private Context mContext;
	private NetRequestService mNetRequService;

	public PayService(Context context) {
		this.mContext = context;
		mNetRequService = new NetRequestService(mContext);
	}

	public boolean mNeedCach = false;// 是否需要缓存

	public void setNeedCach(boolean needCach) {
		mNeedCach = needCach;
	}

	/*
	 * http://m.ymall.com/api/help/share
	 */
	public boolean getPayInfo(String stuName, String idcard, String stuPhone,String feeprice,String eduSystem,String schoolCode) {

		String url = InterfaceParams.payintrerface;
		Map<String, String> map = new HashMap<String, String>();
		map.put("stuName", stuName);
		map.put("idcard",idcard);
		map.put("stuPhone", stuPhone);
		map.put("feeprice", feeprice);
		map.put("stuPhone", stuPhone);
		map.put("eduSystem", eduSystem);
		map.put("schoolCode", schoolCode);
		
		String str = mNetRequService.requestData("POST", url, map,
				mNeedCach);
		if(str!=null){
			return true;
		}
		return false;
		
	}
}
