/**
 * 
 */
package com.jiajie.jiajieproject.Fragment;

import android.view.View;
import android.view.View.OnClickListener;

import com.jiajie.jiajieproject.MainActivity;
import com.jiajie.jiajieproject.R;
import com.jiajie.jiajieproject.utils.IntentUtil;

/**   
 * 项目名称：NewProject   
 * 类名称：FragmnetPage1   
 * 类描述：   
 * 创建人：王蕾
 * 创建时间：2016-4-19 下午6:02:48   
 * 修改备注：    
 */
public class FragmnetPage3 extends BaseFragment{

	@Override
	protected void initView() {
		findViewById(R.id.textid).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				IntentUtil.activityForward(mActivity, MainActivity.class, null, true);
				
			}
		});
		
	}

	
	@Override
	protected int setContentView() {
		// TODO Auto-generated method stub
		return R.layout.startthree;
	}

}
