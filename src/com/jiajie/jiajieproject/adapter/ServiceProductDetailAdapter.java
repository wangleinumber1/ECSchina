/**
 * 
 */
package com.jiajie.jiajieproject.adapter;

import com.jiajie.jiajieproject.R;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 项目名称：NewProject 类名称：CartsClassAdapter 类描述： 创建人：王蕾 创建时间：2015-9-17 下午5:08:41
 * 修改备注：
 */
public class ServiceProductDetailAdapter extends BaseAdapter {
	private Activity activity;

	public ServiceProductDetailAdapter(Activity activity) {
		this.activity = activity;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 10;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder vh = null;
		if (convertView == null) {
			vh = new ViewHolder();
			convertView = LayoutInflater.from(activity).inflate(
					R.layout.serviceproductdetaillist_item, null);

			vh.text1 = (TextView) convertView.findViewById(R.id.text1);
			vh.text2 = (TextView) convertView.findViewById(R.id.text2);
			convertView.setTag(vh);
		} else {
			vh = (ViewHolder) convertView.getTag();
		}

		return convertView;
	}

	class ViewHolder {

		TextView text1, text2;
	}

}
