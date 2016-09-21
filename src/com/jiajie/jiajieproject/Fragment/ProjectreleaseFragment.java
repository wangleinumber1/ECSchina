/**
 * 
 */
package com.jiajie.jiajieproject.Fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

import com.jiajie.jiajieproject.R;
import com.jiajie.jiajieproject.activity.ProjectDetailActivity;
import com.jiajie.jiajieproject.adapter.PartsAdapter;
import com.jiajie.jiajieproject.adapter.ProjectreleaseAdapter;
import com.jiajie.jiajieproject.contents.InterfaceParams;
import com.jiajie.jiajieproject.model.OnlyClass;
import com.jiajie.jiajieproject.net.service.JosnService;
import com.jiajie.jiajieproject.utils.IntentUtil;
import com.jiajie.jiajieproject.utils.MyAsyncTask;
import com.mrwujay.cascade.model.produceClass;
import com.jiajie.jiajieproject.utils.ToastUtil;

/**
 * 项目名称：NewProject 类名称：ProjectreleaseFragment 类描述： 创建人：王蕾 创建时间：2015-9-28
 * 上午11:23:06 修改备注：
 */
public class ProjectreleaseFragment extends BaseFragment implements
		 OnItemClickListener {
//	private PullToRefreshView projectrealese_pull;
	private ListView projectrealese_listview;
	private String data;
	private JosnService jsonservice;
	ArrayList<produceClass> list=new ArrayList<produceClass>();
	@Override
	protected void initView() {
		jsonservice=new JosnService(mActivity);
		projectrealese_listview = (ListView) findViewById(R.id.projectrealese_listview);
		projectrealese_listview.setOnItemClickListener(this);
		data=getArguments().getString("text");
		new PartsAsyTask().execute();
	}

	@Override
	protected int setContentView() {
		// TODO Auto-generated method stub
		return R.layout.projectrealse_listview;
	}

	
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		Bundle bundle=new Bundle();
		bundle.putString("url", list.get(arg2).url);
		IntentUtil.activityForward(mActivity, ProjectDetailActivity.class, bundle,false);

	}

	/**
	 * 备件
	 * */
	@SuppressWarnings("unused")
	private class PartsAsyTask extends AsyncTask {


		public PartsAsyTask() {
			super();
		}

		@SuppressWarnings("unchecked")
		@Override
		protected Object doInBackground(Object... params) {
			Map map = new HashMap<String, String>();
			map.put("date", data);
			return jsonservice.getDataList(InterfaceParams.getCms,
					map, false, produceClass.class);
		}

		@SuppressWarnings("unchecked")
		@Override
		protected void onPostExecute(Object result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if (result == null) {
				return;
			}

			if (jsonservice.getToastMessage()) {
				OnlyClass onlyClass = (OnlyClass) result;
				ToastUtil.showToast(mActivity, onlyClass.data);
			}
			if (jsonservice.getsuccessState()) {
				list=(ArrayList<produceClass>) result;
				projectrealese_listview.setAdapter(new ProjectreleaseAdapter(list, mActivity));
			}

		}

	}

}
