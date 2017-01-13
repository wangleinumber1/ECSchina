/**
 * 
 */
package com.jiajie.jiajieproject.adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.jiajie.jiajieproject.R;
import com.jiajie.jiajieproject.activity.CarShopppingActivity;
import com.jiajie.jiajieproject.contents.InterfaceParams;
import com.jiajie.jiajieproject.db.service.SharePreferDB;
import com.jiajie.jiajieproject.model.OnlyClass;
import com.jiajie.jiajieproject.net.service.JosnService;
import com.jiajie.jiajieproject.utils.ImageLoad;
import com.jiajie.jiajieproject.utils.MyAsyncTask;
import com.jiajie.jiajieproject.utils.ToastUtil;
import com.jiajie.jiajieproject.utils.YokaLog;
import com.jiajie.jiajieproject.widget.MyAddAndSubView;
import com.jiajie.jiajieproject.widget.MyAddAndSubView.OnClickAddAndSubListener;
import com.mrwujay.cascade.model.produceClass;

/**
 * 项目名称：NewProject 类名称：CartsClassAdapter 类描述： 创建人：王蕾 创建时间：2015-9-17 下午5:08:41
 * 修改备注：购物车适配器
 */
@SuppressWarnings("unused")
public class CarShopppingAdapter extends BaseAdapter implements
		OnCheckedChangeListener, OnClickAddAndSubListener {
	private Activity activity;
	private ArrayList<produceClass> list = new ArrayList<produceClass>();
	public static Map<String, Boolean> isSelected = new HashMap<String, Boolean>();
	private Handler mHandler;
	private ImageLoad imageLoad;
	private static String TAG = "CarShopppingAdapter";
	private JosnService josnService;
	public Boolean isclear = false;

	@SuppressLint("UseSparseArrays")
	public CarShopppingAdapter(Activity activity, Handler mHandler,
			ImageLoad imageLoad) {
		this.activity = activity;
		this.mHandler = mHandler;
		this.imageLoad = imageLoad;
		isSelected = new HashMap<String, Boolean>();
		josnService = new JosnService(activity);
	}

	// 初始化isSelected的数据
	private void initDate() {
		for (int i = 0; i < list.size(); i++) {
			getIsSelected().put((list.get(i).id), false);
		}
	}

	public static Map<String, Boolean> getIsSelected() {
		return isSelected;
	}

	public static void setIsSelected(Map<String, Boolean> isSelected) {
		CarShopppingAdapter.isSelected = isSelected;
	}

	public void setData(ArrayList<produceClass> list) {
		this.list = list;
		initDate();
	}

	public ArrayList<produceClass> getData() {
		return list;

	}

	// 清空数据
	public void clearData() {
		isclear = true;
		list.clear();

	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder vh = null;
		if (convertView == null) {
			vh = new ViewHolder();
			convertView = LayoutInflater.from(activity).inflate(
					R.layout.carshoppingitem_layout, null);
			vh.imgeView1 = (CheckBox) convertView.findViewById(R.id.imgeView1);
			vh.imgeView2 = (ImageView) convertView.findViewById(R.id.imgeView2);
			vh.my_add_sub = (MyAddAndSubView) convertView
					.findViewById(R.id.my_add_sub);
			vh.pricetext = (TextView) convertView.findViewById(R.id.pricetext);
			vh.numbertext = (TextView) convertView
					.findViewById(R.id.numbertext);
			vh.catshoppingitem_layouttext1 = (TextView) convertView
					.findViewById(R.id.catshoppingitem_layouttext1);
			convertView.setTag(vh);
		} else {
			vh = (ViewHolder) convertView.getTag();
		}
		vh.imgeView1.setOnCheckedChangeListener(this);
		vh.imgeView1.setTag(position);
		vh.imgeView1.setChecked(getIsSelected().get((list.get(position).id)));

		vh.numbertext.setText("¥"
				+ list.get(position).price.substring(0,
						list.get(position).price.lastIndexOf("00")) );
		vh.catshoppingitem_layouttext1.setText(list.get(position).pn);
		vh.pricetext.setText(list.get(position).productName);
		vh.my_add_sub.setOnClickAddAndSubListener(this);
		vh.my_add_sub.setTag(position);
		vh.my_add_sub.setCount(list.get(position).qty);
		imageLoad.loadImg(vh.imgeView2, list.get(position).image,
				R.drawable.jiazaitupian);
		return convertView;
	}

	class ViewHolder {
		CheckBox imgeView1;
		ImageView imgeView2;
		MyAddAndSubView my_add_sub;
		TextView pricetext, catshoppingitem_layouttext1, numbertext;

	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean flag) {

		int position = (Integer) buttonView.getTag();
		getIsSelected().put(list.get(position).id, flag);
		produceClass produceClass = list.get(position);
		produceClass.setChoosed(flag);
		mHandler.sendMessage(mHandler.obtainMessage(10, getTotalPrice()));
		// 如果所有的物品全部被选中，则全选按钮也默认被选中
		mHandler.sendMessage(mHandler.obtainMessage(11, isAllSelected()));
	}

	/**
	 * 计算选中商品的金额
	 * 
	 * @return 返回需要付费的总金额
	 */
	private double getTotalPrice() {
		produceClass produceClass = null;
		Double totalPrice = 0.00;
		Double price;

		try {
			for (int i = 0; i < list.size(); i++) {
				produceClass = list.get(i);
				if (produceClass.isChoosed()) {
					price = Double
							.parseDouble(produceClass.price.split("00")[0]);
					totalPrice += Integer.parseInt(produceClass.qty) * price;
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return totalPrice;
	}

	/**
	 * 判断是否购物车中所有的商品全部被选中
	 * 
	 * @return true所有条目全部被选中 false还有条目没有被选中
	 */
	private boolean isAllSelected() {
		boolean flag = true;
		for (int i = 0; i < list.size(); i++) {
			if (!getIsSelected().get(list.get(i).id)) {
				flag = false;
				break;
			}
		}

		return flag;
	}

	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			if (msg.what == 1) { // 更改商品数量
				// 更改商品数量后，通知Activity更新需要付费的总金额
				mHandler.sendMessage(mHandler
						.obtainMessage(10, getTotalPrice()));
			}
		}
	};

	@SuppressWarnings("unchecked")
	@Override
	public void clickAdd(int count, View view) {
		MyAddAndSubView myAddView=(MyAddAndSubView) view;
		produceClass produceClass = list.get((Integer) view.getTag());
		produceClass.setqty(count + "");
		handler.sendEmptyMessage(1);
		new UpdateAsyTask(produceClass.id, count + "",produceClass.productId,myAddView).execute();
	}

	@SuppressWarnings("unchecked")
	@Override
	public void clickSub(int count, View view) {
		MyAddAndSubView mySubView=(MyAddAndSubView) view;
		if (count > 0) {
			produceClass produceClass1 = list.get((Integer) view.getTag());
			produceClass1.setqty(count + "");
			handler.sendEmptyMessage(1);
			new UpdateAsyTask(produceClass1.id, count + "",produceClass1.productId,mySubView).execute();
		}
	}

	/**
	 * 提交数量
	 * */
	@SuppressWarnings("unused")
	private class UpdateAsyTask extends MyAsyncTask {
		private String qty;
		private String id;
		private String product_id;
		private MyAddAndSubView view;

		public UpdateAsyTask(String id, String qty,String product_id,MyAddAndSubView view) {
			super(activity);
			this.qty = qty;
			this.id = id;
			this.product_id = product_id;
			this.view = view;

		}

		@SuppressWarnings("unchecked")
		@Override
		protected Object doInBackground(Object... params) {
			HashMap<String, String> map = new HashMap<String, String>();
			String message = "{" + "\"" + id + "\"" + ":" + "{\"" + "qty"
					+ "\"" + ":" + "\"" + qty +"\"" +","+"\""+"product_id"
					+ "\"" + ":" + "\"" + product_id + "\"" + "}}";
			map.put("update_cart_action", "update_qty");
			map.put("cart", message);
			return josnService.getData(InterfaceParams.updateCart, map, false,
					null);
		}

		@SuppressWarnings("unchecked")
		@Override
		protected void onPostExecute(Object result) {
			// TODO Auto-generated method
			super.onPostExecute(result);
			if (result == null) {
				return;
			}

			if (josnService.getToastMessage()) {
				OnlyClass onlyClass = (OnlyClass) result;
				if (!onlyClass.success) {
					//数量达到最大控制在这个数量减一的基础上
//					Integer count=Integer.parseInt(qty);
//					view.setMax(count-1);
				}
				ToastUtil.showToast(activity, onlyClass.data);
			}

		}

	}
}
