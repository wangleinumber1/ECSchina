package com.jiajie.jiajieproject.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.widget.Toast;

import com.jiajie.jiajieproject.contents.DeviceParamsDB;
import com.jiajie.jiajieproject.contents.InterfaceParams;
import com.jiajie.jiajieproject.contents.UserData;
import com.jiajie.jiajieproject.model.MyAccountClass;
import com.jiajie.jiajieproject.net.NetUrl;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

@SuppressLint("ShowToast")
public class FileToServer {

	public static void reg(final Activity activity, final String filePath,
			File file, MyAccountClass myacount) {

		try {
			String url = "";
			RequestParams params = new RequestParams();
			// file : File userId : 用户唯一标志
			Bitmap photodata = BitmapFactory.decodeFile(filePath);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			photodata.compress(Bitmap.CompressFormat.PNG, 100, baos);
			baos.close();
			byte[] buffer = baos.toByteArray();
			System.out.println("图片数据" + buffer.length);
			final String photo = Base64.encodeToString(buffer, 0,
					buffer.length, Base64.DEFAULT);
			params.put("name", myacount.name);
			params.put("company", myacount.company);
			params.put("phone", myacount.phone);
			params.put("email", myacount.email);
			params.put("path", myacount.path);
			params.put("image", file);
			url = NetUrl.TEST_HOST + InterfaceParams.editUserInfo;
			AsyncHttpClient client = new AsyncHttpClient();
			client.addHeader("Cookie", DeviceParamsDB.cookie);		
			client.setTimeout(200000);
			client.post(url, params, new AsyncHttpResponseHandler() {

				@Override
				public void onFailure(int arg0, Header[] arg1, byte[] arg2,
						Throwable arg3) {
					// TODO Auto-generated method stub
					ToastUtil.showToast(activity, 1, "连接服务器失败....", true);
				}

				@Override
				public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
					// TODO Auto-generated method stub
					// Toast.makeText(activity, "上传成功!", 0).show();
					JSONObject jsonObject;
					try {
						jsonObject = new JSONObject(new String(arg2));

						if (jsonObject.getString("success").equals("true")) {
							Toast.makeText(activity, jsonObject.getString("data"), 0).show();
							
						
						} else {
							Toast.makeText(activity,jsonObject.getString("data"), 0).show();
						}

					} catch (JSONException e) {
						e.printStackTrace();
					}

				}
			});

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	// },1000);
	// }

}
