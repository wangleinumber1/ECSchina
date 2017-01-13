/**
 * 
 */
package com.jiajie.jiajieproject.activity;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.MediaStore.MediaColumns;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout.LayoutParams;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jiajie.jiajieproject.R;
import com.jiajie.jiajieproject.activity.BaseActivity.MyAsyncTask;
import com.jiajie.jiajieproject.contents.InterfaceParams;
import com.jiajie.jiajieproject.model.MyAccountClass;
import com.jiajie.jiajieproject.model.OnlyClass;
import com.jiajie.jiajieproject.utils.FileToServer;
import com.jiajie.jiajieproject.utils.IntentUtil;
import com.jiajie.jiajieproject.utils.IoUtils;
import com.jiajie.jiajieproject.utils.TaskUtil;
import com.jiajie.jiajieproject.utils.TaskUtil.BackFore;
import com.jiajie.jiajieproject.utils.ToastUtil;
import com.jiajie.jiajieproject.utils.Tools;
import com.jiajie.jiajieproject.widget.CircleImageView;

/**
 * 项目名称：NewProject 类名称：MyAccontActivity 类描述： 创建人：王蕾 创建时间：2015-10-8 上午11:37:16
 * 修改备注：我的账户
 */
public class MyAccontActivity extends BaseActivity implements OnClickListener {
	private CircleImageView headerImage;
	private ImageView headerleftImg;
	private RelativeLayout layout1, layout2, layout3, layout4, layout5,
			layout6, layout7;
	private Button Button1, Button2, Button3, Button4;
	private final int namecode = 11, companycode = 12, emailcode = 13,
			phonecode = 14;

	private String name, company, email, phone;
	private static final String IMAGE_FILE_NAME = Environment
			.getExternalStorageDirectory() + File.separator + "faceimage.jpg";

	private String filePath = IoUtils.getImageCacheDir().getAbsolutePath()
			+ File.separator + System.currentTimeMillis() + ".jpg";
	/* 请求码 */
	private static final int IMAGE_REQUEST_CODE = 0;
	private static final int CAMERA_REQUEST_CODE = 1;
	private static final int RESULT_REQUEST_CODE = 2;
	public static String TAG = "MyAccontActivity";

	@Override
	protected void onInit(Bundle bundle) {

		super.onInit(bundle);
		setContentView(R.layout.myaccount_layout);
		InitView();
	}

	private void InitView() {
		headerImage = (CircleImageView) findViewById(R.id.headerImage);
		headerleftImg = (ImageView) findViewById(R.id.headerleftImg);
		layout1 = (RelativeLayout) findViewById(R.id.layout1);
		layout2 = (RelativeLayout) findViewById(R.id.layout2);
		layout3 = (RelativeLayout) findViewById(R.id.layout3);
		layout4 = (RelativeLayout) findViewById(R.id.layout4);
		layout5 = (RelativeLayout) findViewById(R.id.layout5);
		layout6 = (RelativeLayout) findViewById(R.id.layout6);
		layout7 = (RelativeLayout) findViewById(R.id.layout7);
		Button1 = (Button) findViewById(R.id.Button1);
		Button2 = (Button) findViewById(R.id.Button2);
		Button3 = (Button) findViewById(R.id.Button3);
		Button4 = (Button) findViewById(R.id.Button4);
		layout1.setOnClickListener(this);
		layout2.setOnClickListener(this);
		layout3.setOnClickListener(this);
		layout4.setOnClickListener(this);
		layout5.setOnClickListener(this);
		layout6.setOnClickListener(this);
		layout7.setOnClickListener(this);
		headerleftImg.setOnClickListener(this);
		new UserInfoAsyTask().execute();
	}

	@Override
	public void onClick(View v) {
		Bundle bundle = new Bundle();
		switch (v.getId()) {
		case R.id.layout1:
			popwindowUpDown();
			break;
		case R.id.headerleftImg:

			finish();
			break;
		case R.id.layout6:
			IntentUtil.activityForward(mActivity, AdressManageActivity.class,
					null, false);
			break;
		case R.id.layout7:
			IntentUtil.activityForward(mActivity, PassWordChangeActivity.class,
					null, false);
			break;
		case R.id.text1:
			Intent intentFromCapture = new Intent(
					MediaStore.ACTION_IMAGE_CAPTURE);
			// 判断存储卡是否可以用，可用进行存储
			if (Tools.hasSdcard()) {
				intentFromCapture.putExtra(MediaStore.EXTRA_OUTPUT,
						Uri.fromFile(new File(IMAGE_FILE_NAME)));
			}
			System.out.println("进入拍照！");
			startActivityForResult(intentFromCapture, CAMERA_REQUEST_CODE);
			mPopupWindow.dismiss();
			setPopBackgroud(1);
			break;

		case R.id.text2:
			Intent intent2 = new Intent(Intent.ACTION_PICK, null);
			intent2.setDataAndType(
					MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
			startActivityForResult(intent2, IMAGE_REQUEST_CODE);
			mPopupWindow.dismiss();
			setPopBackgroud(1);
			break;

		case R.id.text3:
			mPopupWindow.dismiss();
			setPopBackgroud(1);
			break;

		case R.id.layout2:

			bundle.putString(TAG, "1");
			IntentUtil.startActivityForResult(mActivity,
					MyaccountEditActivity.class, namecode, bundle);
			break;
		case R.id.layout3:

			bundle.putString(TAG, "2");
			IntentUtil.startActivityForResult(mActivity,
					MyaccountEditActivity.class, companycode, bundle);
			break;
		case R.id.layout4:

			bundle.putString(TAG, "3");
			IntentUtil.startActivityForResult(mActivity,
					MyaccountEditActivity.class, emailcode, bundle);
			break;
		case R.id.layout5:

			bundle.putString(TAG, "4");
			IntentUtil.startActivityForResult(mActivity,
					MyaccountEditActivity.class, phonecode, bundle);
			break;

		default:
			break;
		}

	}

	PopupWindow mPopupWindow;

	// popwindow弹窗
	private void popwindowUpDown() {

		View view = inflater.inflate(R.layout.mine_layout, null);
		View poplayout = inflater.inflate(R.layout.minepopwindow_layout, null);
		TextView text1 = (TextView) poplayout.findViewById(R.id.text1);
		TextView text2 = (TextView) poplayout.findViewById(R.id.text2);
		TextView text3 = (TextView) poplayout.findViewById(R.id.text3);
		text1.setOnClickListener(this);
		text2.setOnClickListener(this);
		text3.setOnClickListener(this);
		text1.setText("拍照");
		text2.setText("选择已有照片");
		text3.setText("取消");
		mPopupWindow = new PopupWindow(poplayout, LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT, true);
		mPopupWindow.setFocusable(true);
		// 改变背景透明度
		setPopBackgroud((float) 0.7);

		// mPopupWindow.setBackgroundDrawable(new PaintDrawable());
		mPopupWindow.setAnimationStyle(R.style.mypopwindow_anim_updownstyle);
		mPopupWindow.showAtLocation(view, Gravity.BOTTOM, 0, 0);
	}

	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// 结果码不等于取消时候
		if (resultCode != RESULT_CANCELED) {

			switch (requestCode) {
			case IMAGE_REQUEST_CODE:
				startPhotoZoom(data.getData());
				break;
			case CAMERA_REQUEST_CODE:
				if (Tools.hasSdcard()) {
					File tempFile = new File(IMAGE_FILE_NAME);
					startPhotoZoom(Uri.fromFile(tempFile));
				} else {
					System.out.println("照片照完，但是找不到照片，无法切图！");

				}

				break;
			case RESULT_REQUEST_CODE:
				if (data != null) {
					Bundle extras = data.getExtras();
					if (extras != null) {
						final Bitmap photo = extras.getParcelable("data");
						getImageToView(data);
						new BitmapToImageAsyTask(photo).execute();


					}
				}
				break;
			}

		}

		if (resultCode == RESULT_OK) {
			Bundle bundle = null;
			if (data != null) {
				bundle = data.getExtras();
			}

			switch (requestCode) {
			case namecode:
				name = bundle.getString(TAG);
				Button1.setText(name);
				HashMap<String, String> map = new HashMap<String, String>();
				map.put("name", name);
				new MyacountEditAsytask(map).execute();
				break;
			case companycode:
				company = bundle.getString(TAG);
				Button2.setText(company);
				HashMap<String, String> map1 = new HashMap<String, String>();
				map1.put("company", company);
				new MyacountEditAsytask(map1).execute();
				break;
			case emailcode:
				email = bundle.getString(TAG);
				Button3.setText(email);
				HashMap<String, String> map2 = new HashMap<String, String>();
				map2.put("email", email);
				new MyacountEditAsytask(map2).execute();
				break;
			case phonecode:
				phone = bundle.getString(TAG);
				Button4.setText(phone);
				HashMap<String, String> map3 = new HashMap<String, String>();
				map3.put("phone", phone);
				new MyacountEditAsytask(map3).execute();
				break;

			default:
				break;
			}
		}
	}

	/**
	 * 裁剪图片方法实现
	 * 
	 * @param uri
	 */
	public void startPhotoZoom(Uri uri) {

		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");
		// 设置裁剪
		intent.putExtra("crop", "true");
		// aspectX aspectY 是宽高的比例
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		// outputX outputY 是裁剪图片宽高
		intent.putExtra("outputX", 200);
		intent.putExtra("outputY", 200);
		intent.putExtra("return-data", true);
		startActivityForResult(intent, RESULT_REQUEST_CODE);
	}

	Bitmap ImageData;

	/**
	 * 保存裁剪之后的图片数据
	 * 
	 * @param picdata
	 */
	private void getImageToView(Intent data) {
		Bundle extras = data.getExtras();
		if (extras != null) {
			Bitmap photo = extras.getParcelable("data");
			Drawable drawable = new BitmapDrawable(photo);
			headerImage.setImageDrawable(drawable);
			ImageData = photo;

		}
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		if (mPopupWindow != null) {
			mPopupWindow.dismiss();
			setPopBackgroud(1);
		}
	}

	/**
	 * 获取图片路径
	 * */

	// @SuppressWarnings("deprecation")
	// public String getPath(Uri uri) {
	// String[] projection = { MediaColumns.DATA };
	// Cursor cursor = managedQuery(uri, projection, null, null, null);
	// int column_index = cursor.getColumnIndexOrThrow(MediaColumns.DATA);
	// cursor.moveToFirst();
	// return cursor.getString(column_index);
	// }

	// @SuppressWarnings("unused")
	// private void done(final String path) {
	//
	// TaskUtil.backFore(new BackFore() {
	//
	// @Override
	// public void onFore() {
	// File file = null;
	// if (path != null && new File(filePath).exists()) {
	// file = new File(filePath);
	// FileToServer.reg(MyAccontActivity.this, "", file, path);
	// }
	// }
	//
	// @Override
	// public void onBack() {
	//
	// new Timer().schedule(new TimerTask() {
	//
	// @Override
	// public void run() {
	// if (path != null && new File(filePath).exists()) {
	//
	// } else {
	// finish();
	// }
	//
	// }
	//
	// }, 10);
	// }
	//
	// });
	// }

	// // 提交个人信息
	// @SuppressWarnings("unused")
	// private void EditMineMessage(String path) {
	// File file = null;
	// file = new File(filePath);
	// MyAccountClass myacount = new MyAccountClass();
	// myacount.name = name;
	// myacount.company = company;
	// myacount.phone = phone;
	// myacount.email = email;
	// myacount.path = filePath;
	// FileToServer.reg(MyAccontActivity.this, filePath, file, myacount);
	//
	// }

	/**
	 * 用户信息
	 * */
	@SuppressWarnings("unused")
	private class UserInfoAsyTask extends MyAsyncTask {
		public UserInfoAsyTask() {
			super();
		}

		@SuppressWarnings("unchecked")
		@Override
		protected Object doInBackground(Object... params) {

			return jsonservice.getData(InterfaceParams.myInfo, null, false,
					MyAccountClass.class);
		}

		@SuppressWarnings("unchecked")
		@Override
		protected void onPostExecute(Object result) {
			super.onPostExecute(result);
			if (result == null) {
				return;
			}

			if (jsonservice.getToastMessage()) {
				OnlyClass onlyClass = (OnlyClass) result;
				ToastUtil.showToast(mActivity, onlyClass.data);
			}
			if (jsonservice.getsuccessState()) {
				MyAccountClass myAccountClass = (MyAccountClass) result;
				Button1.setText(myAccountClass.name);
				Button4.setText(myAccountClass.phone);
				Button2.setText(myAccountClass.company);
				Button3.setText(myAccountClass.email);
				mImgLoad.loadImg(headerImage, myAccountClass.head,
						R.drawable.jiazaitupian);
			}

		}
	}

	@SuppressWarnings("unused")
	private class MyacountEditAsytask extends MyAsyncTask {
		private Map map;

		@SuppressWarnings("unused")
		public MyacountEditAsytask(Map map) {
			super();
			this.map = map;
		}

		@Override
		protected Object doInBackground(Object... params) {
			// TODO Auto-generated method stub
			return jsonservice.getData(InterfaceParams.editUserInfo, map,
					false, null);
		}

		@Override
		protected void onPostExecute(Object result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if (result == null) {
				return;
			}

			OnlyClass onlyClass = (OnlyClass) result;
			if (onlyClass.success) {
				ToastUtil.showToast(mContext, onlyClass.data);
			}

		}

	}

	// bitmap转成图片
	@SuppressWarnings("unused")
	private class BitmapToImageAsyTask extends MyAsyncTask {
		Bitmap bitmap;

		@SuppressWarnings("unused")
		public BitmapToImageAsyTask(Bitmap bitmap) {
			super();
			this.bitmap = bitmap;
		}

		@Override
		protected void onPostExecute(Object result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			FileToServer.reg(mActivity, filePath);
		}

		@Override
		protected Object doInBackground(Object... params) {
			IoUtils.saveBitmap(bitmap, filePath);
			return null;
		}

	}

}
