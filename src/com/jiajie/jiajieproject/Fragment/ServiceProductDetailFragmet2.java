/**
 * 
 */
package com.jiajie.jiajieproject.Fragment;

import android.view.View;
import android.widget.ListView;

import com.jiajie.jiajieproject.R;
import com.jiajie.jiajieproject.adapter.ServiceProductAdapter;
import com.jiajie.jiajieproject.adapter.ServiceProductDetailAdapter;

/**   
 * 项目名称：NewProject   
 * 类名称：ServiceProductDetailFragmet2   
 * 类描述：   
 * 创建人：王蕾
 * 创建时间：2015-9-25 下午2:55:27   
 * 修改备注：    
 */
public class ServiceProductDetailFragmet2 extends BaseFragment{

	@Override
	protected void initView() {
		View view = mInflater
				.inflate(R.layout.serviceproductdetailheader, null);
		ListView listview = (ListView) contentView;
		listview.setAdapter(new ServiceProductDetailAdapter(mActivity));
		listview.addHeaderView(view);
	};

	@Override
	protected int setContentView() {
		// TODO Auto-generated method stub
		return R.layout.serviceproductlistview;
	}

}
