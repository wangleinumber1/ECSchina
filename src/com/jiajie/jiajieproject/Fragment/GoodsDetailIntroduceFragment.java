/**
 * 
 */
package com.jiajie.jiajieproject.Fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.jiajie.jiajieproject.R;
import com.jiajie.jiajieproject.activity.BaseActivity;
import com.jiajie.jiajieproject.activity.CartsClassActivity;
import com.jiajie.jiajieproject.activity.GoodsdetailActivity;
import com.jiajie.jiajieproject.contents.InterfaceParams;
import com.jiajie.jiajieproject.model.OnlyClass;
import com.jiajie.jiajieproject.net.service.JosnService;
import com.jiajie.jiajieproject.utils.CityPoputils;
import com.jiajie.jiajieproject.utils.GoodDetailCityPoputils;
import com.jiajie.jiajieproject.utils.MyAsyncTask;
import com.jiajie.jiajieproject.utils.SlidpopWindowutil;
import com.mrwujay.cascade.model.produceClass;
import com.jiajie.jiajieproject.utils.ToastUtil;

/**
 * 项目名称：NewProject 类名称：GoodsDetailIntroduceFragment 类描述： 创建人：王蕾 创建时间：2015-9-21
 * 下午6:16:03 修改备注：
 */
public class GoodsDetailIntroduceFragment extends BaseFragment implements
		OnClickListener {

	private ImageView bt01, bt02;
	private EditText edt;
	private TextView ttt;
	int num = 0;// 数量
	private Button locationbutton;
	private TextView goodsdetailparamestext1, goodsdetailparamestext2,
			goodsdetailparamestext3, goodsdetailparamestext21,
			goodsdetailparamestext4, goodsdetailparamestext5;
	private String product_id;
	protected JosnService jsonservice;

	@Override
	protected void initView() {
		if(((GoodsdetailActivity)getActivity()).getIntent().getExtras()!=null){
		Bundle bundle2 = ((GoodsdetailActivity)getActivity()).getIntent().getExtras();
		product_id = bundle2.getString("id");}
		InitView();
		bt01 = (ImageView) findViewById(R.id.addbt);
		bt02 = (ImageView) findViewById(R.id.subbt);
		edt = (EditText) findViewById(R.id.edt);
		ttt = (TextView) findViewById(R.id.ttt);
		bt01.setTag("+");
		bt02.setTag("-");
		edt.setInputType(android.text.InputType.TYPE_CLASS_NUMBER);
		edt.setText(String.valueOf(num));
		setViewListener();
	}

	/* 加载布局 */
	private void InitView() {
		jsonservice = new JosnService(mActivity);
		locationbutton = (Button) findViewById(R.id.locationbutton);
		goodsdetailparamestext1 = (TextView) findViewById(R.id.goodsdetailparamestext1);
		goodsdetailparamestext21 = (TextView) findViewById(R.id.goodsdetailparamestext21);
		goodsdetailparamestext2 = (TextView) findViewById(R.id.goodsdetailparamestext2);
		goodsdetailparamestext3 = (TextView) findViewById(R.id.goodsdetailparamestext3);
		goodsdetailparamestext4 = (TextView) findViewById(R.id.goodsdetailparamestext4);
		goodsdetailparamestext5 = (TextView) findViewById(R.id.goodsdetailparamestext5);
		locationbutton.setOnClickListener(this);
		Bundle bundle=getArguments();
		produceClass produceClass=(produceClass) bundle.getSerializable("Object");
		goodsdetailparamestext1.setText(produceClass.name);
		if(produceClass.pn!=null){
			goodsdetailparamestext2.setText("PN："+produceClass.pn);
		}else{
			goodsdetailparamestext2.setText("PN："+"暂无");
		}
		
		goodsdetailparamestext3.setText("价格：￥"+produceClass.price.substring(0, produceClass.price.lastIndexOf('.'))+".00");
		goodsdetailparamestext4.setText("库存："+produceClass.qty.substring(0, produceClass.qty.lastIndexOf('.')));
		goodsdetailparamestext21.setText(produceClass.short_description);
//		new GoodsDetailAsyTask().execute();
	}

	/**
	 * 设置文本变化相关监听事件
	 */
	private void setViewListener() {
		bt01.setOnClickListener(new OnButtonClickListener());
		bt02.setOnClickListener(new OnButtonClickListener());
		edt.addTextChangedListener(new OnTextChangeListener());
	}

	/**
	 * 加减按钮事件监听器
	 * 
	 * 
	 */
	class OnButtonClickListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			String numString = edt.getText().toString();
			if (numString == null || numString.equals("")) {
				num = 0;
				edt.setText("0");
			} else {
				if (v.getTag().equals("-")) {
					if (++num < 0) // 先加，再判断
					{
						num--;
						Toast.makeText(mActivity, "请输入一个大于0的数字",
								Toast.LENGTH_SHORT).show();
					} else {
						edt.setText(String.valueOf(num));
						ttt.setText(edt.getText());
					}
				} else if (v.getTag().equals("+")) {
					if (--num < 0) // 先减，再判断
					{
						num++;
						Toast.makeText(mActivity, "请输入一个大于0的数字",
								Toast.LENGTH_SHORT).show();
					} else {
						edt.setText(String.valueOf(num));
						ttt.setText(edt.getText());

					}
				}
			}
		}
	}

	/**
	 * EditText输入变化事件监听器
	 */
	class OnTextChangeListener implements TextWatcher {

		@Override
		public void afterTextChanged(Editable s) {
			String numString = s.toString();
			if (numString == null || numString.equals("")) {
				num = 0;
				ttt.setText(edt.getText());
			} else {
				int numInt = Integer.parseInt(numString);
				if (numInt < 0) {
					Toast.makeText(mActivity, "请输入一个大于0的数字", Toast.LENGTH_SHORT)
							.show();
				} else {
					// 设置EditText光标位置 为文本末端
					edt.setSelection(edt.getText().toString().length());
					num = numInt;
					ttt.setText(edt.getText());

				}
			}
		}

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onTextChanged(CharSequence s, int start, int before,
				int count) {
			// TODO Auto-generated method stub

		}
	}

	@Override
	protected int setContentView() {
		// TODO Auto-generated method stub
		return R.layout.goodsdetailparames_layout;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.locationbutton:
			cityPoputils = new GoodDetailCityPoputils(mContext,
					(BaseActivity) mActivity, mInflater, locationbutton, null);
			cityPoputils.citypoplist(locationbutton,mActivity);
			break;

		default:
			break;
		}

	}

	private GoodDetailCityPoputils cityPoputils;

	@Override
	public void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		if (cityPoputils != null)
			cityPoputils.dissmissPop();
	}

	/**
	 * 备件
	 * */
	@SuppressWarnings("unused")
	private class GoodsDetailAsyTask extends MyAsyncTask {
		public GoodsDetailAsyTask() {
			super(mActivity);
		}

		@SuppressWarnings("unchecked")
		@Override
		protected Object doInBackground(Object... params) {
			Map map = new HashMap<String, String>();
			map.put("product_id", product_id);
			return jsonservice.getData(InterfaceParams.productInfo, map, false,
					produceClass.class);
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
				produceClass produceClass = (produceClass) result;
				
				goodsdetailparamestext1.setText(produceClass.name);
				goodsdetailparamestext2.setText("PN："+produceClass.pn);
				goodsdetailparamestext3.setText("价格：￥"+produceClass.price);
				goodsdetailparamestext4.setText("库存："+produceClass.qty);
				goodsdetailparamestext21.setText(produceClass.short_description);
			}

		}

	}
}
