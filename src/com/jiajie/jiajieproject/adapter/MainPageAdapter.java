/**
 * 
 */
package com.jiajie.jiajieproject.adapter;

import java.util.ArrayList;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.mrwujay.cascade.model.MainPageObject;

/**   
 * 项目名称：NewProject   
 * 类名称：MainPageAdapter   
 * 类描述：   
 * 创建人：王蕾
 * 创建时间：2016-9-18 上午10:39:41   
 * 修改备注：    
 */
public class MainPageAdapter extends BaseAdapter{
	protected ArrayList<MainPageObject> list=new ArrayList<MainPageObject>();
	
	
	public void setdata(ArrayList<MainPageObject> list) {
		this.list=list;
	}

	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getItem(int)
	 */
	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getItemId(int)
	 */
	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getView(int, android.view.View, android.view.ViewGroup)
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		return null;
	}

}
