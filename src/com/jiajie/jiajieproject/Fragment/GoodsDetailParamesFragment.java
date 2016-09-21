/**
 * 
 */
package com.jiajie.jiajieproject.Fragment;

import java.util.HashMap;
import java.util.Map;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.jiajie.jiajieproject.R;
import com.jiajie.jiajieproject.activity.GoodsdetailActivity;
import com.jiajie.jiajieproject.contents.InterfaceParams;
import com.jiajie.jiajieproject.model.OnlyClass;
import com.jiajie.jiajieproject.net.service.JosnService;
import com.jiajie.jiajieproject.utils.MyAsyncTask;
import com.mrwujay.cascade.model.produceClass;
import com.jiajie.jiajieproject.utils.ToastUtil;

/**
 * 项目名称：NewProject 类名称：GoodsDetailParamesFragment 类描述： 创建人：王蕾 创建时间：2015-9-21
 * 下午6:16:52 修改备注：
 */
public class GoodsDetailParamesFragment extends BaseFragment {
	private String product_id;
	protected JosnService jsonservice;
	private TextView goodsdetailintroducetext1, goodsdetailintroducetext2,
			goodsdetailintroducetext3;

	@Override
	protected void initView() {
		// TODO Auto-generated method stub
		if (((GoodsdetailActivity) getActivity()).getIntent().getExtras() != null) {
			Bundle bundle2 = ((GoodsdetailActivity) getActivity()).getIntent()
					.getExtras();
			product_id = bundle2.getString("id");
		}
		InitView();
	}

	@Override
	protected int setContentView() {
		// TODO Auto-generated method stub
		return R.layout.goodsdetailintroduce_layout;
	}

	/* 加载布局 */
	private void InitView() {
		jsonservice = new JosnService(mActivity);
		goodsdetailintroducetext1 = (TextView) findViewById(R.id.goodsdetailintroducetext1);
		goodsdetailintroducetext2 = (TextView) findViewById(R.id.goodsdetailintroducetext2);
		goodsdetailintroducetext3 = (TextView) findViewById(R.id.goodsdetailintroducetext3);
		Bundle bundle=getArguments();
		produceClass produceClass=(produceClass) bundle.getSerializable("Object");
		goodsdetailintroducetext3.setText(produceClass.description);
//		new GoodsDetailAsyTask().execute();
	}

//	/**
//	 * 备件
//	 * */
//	@SuppressWarnings("unused")
//	private class GoodsDetailAsyTask extends MyAsyncTask {
//		public GoodsDetailAsyTask() {
//			super(mActivity);
//		}
//
//		@SuppressWarnings("unchecked")
//		@Override
//		protected Object doInBackground(Object... params) {
//			Map map = new HashMap<String, String>();
//			map.put("product_id", product_id);
//			return jsonservice.getData(InterfaceParams.productInfo, map, false,
//					produceClass.class);
//		}
//
//		@SuppressWarnings("unchecked")
//		@Override
//		protected void onPostExecute(Object result) {
//			// TODO Auto-generated method stub
//			super.onPostExecute(result);
//			if (result == null) {
//				return;
//			}
//
//			if (jsonservice.getToastMessage()) {
//				OnlyClass onlyClass = (OnlyClass) result;
//				ToastUtil.showToast(mActivity, onlyClass.data);
//			}
//			if (jsonservice.getsuccessState()) {
//				produceClass produceClass = (produceClass) result;
////				goodsdetailintroducetext1.setText(produceClass.name);
//				// goodsdetailintroducetext2.setText("PN："+produceClass.pn);
//				goodsdetailintroducetext3.setText(
//						 produceClass.description);
//
//			}
//
//		}
//
//	}
}
