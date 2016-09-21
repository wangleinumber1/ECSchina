package com.jiajie.jiajieproject.utils;



import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.jiajie.jiajieproject.R;
import com.jiajie.jiajieproject.widget.MyProgressDialog;

/*
 * 自定义AsyncTask，用于加载过程中dialog弹框提示
 */
public abstract class MyAsyncTask extends AsyncTask<Object, Object, Object> {

	private Context context;
	private MyProgressDialog myProgressDialog;
	protected MyAsyncTask(Context context){
		this.context = context;
		myProgressDialog=new MyProgressDialog(context);
		myProgressDialog.initDialog();

	}
	@Override
	protected abstract Object doInBackground(Object... params);

	@Override
	protected void onPostExecute(Object result) {
		super.onPostExecute(result);
		YokaLog.d("MyAsyncTask", "MyAsyncTask==onPostExecute()"+result);
		if(myProgressDialog.isShowing()){
    		myProgressDialog.colseDialog();
    	}
	}

}
