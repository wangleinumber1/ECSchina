/**
 * 
 */
package com.jiajie.jiajieproject.net.service;

import java.util.Map;

import android.content.Context;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.jiajie.jiajieproject.contents.InterfaceParams;
import com.jiajie.jiajieproject.model.OnlyClass;
import com.jiajie.jiajieproject.net.NetRequestService;
import com.jiajie.jiajieproject.utils.StringUtil;

/**   
 * 项目名称：NewProject   
 * 类名称：MineService   
 * 类描述：   
 * 创建人：王蕾
 * 创建时间：2015-11-4 上午10:30:41   
 * 修改备注：    
 */
public class MineService {
	private static final String TAG = "MineService";
	private Context mContext;
	private NetRequestService mNetRequService;

	public MineService(Context context) {
		this.mContext = context;
		mNetRequService = new NetRequestService(mContext);
	}

	public boolean mNeedCach = false;// 是否需要缓存

	public void setNeedCach(boolean needCach) {
		mNeedCach = needCach;
	}

	/** 注销 */
	public OnlyClass PostMineLoginOutData() {
		String str = mNetRequService.requestData("POST",
				InterfaceParams.LOGINOUT, null, false);
		if (!StringUtil.checkStr(str))
			return null;
		Log.d(TAG, str);

		OnlyClass onlyClass = JSON.parseObject(str, OnlyClass.class);

		return onlyClass;

	}

}
