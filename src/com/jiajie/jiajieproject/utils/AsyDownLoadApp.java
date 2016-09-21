package com.jiajie.jiajieproject.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;

import android.content.Context;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.jiajie.jiajieproject.R;
import com.jiajie.jiajieproject.contents.FileDir;
import com.jiajie.jiajieproject.net.LCHttpUrlConnection;



/*
 * 异步下载app
 */
public class AsyDownLoadApp extends AsyncTask<String, Integer, Boolean> {
	Context context;
	ProgressBar progressbar;
	TextView textprogress;
	String appURl;
	AsyDownLoadApp(Context context,String appURl) {
		this.context = context;
		mInflater = LayoutInflater.from(context);
		this.appURl=appURl;
		View view = mInflater.inflate(R.layout.progress_layout, null);
		progressbar = (ProgressBar) view.findViewById(R.id.progressbar);
		textprogress = (TextView) view.findViewById(R.id.textprogress);
	}

	//ProgressDialog dialog;

	ProgressBar pb;
	private LayoutInflater mInflater;
	@Override
	protected void onCancelled() {
		// TODO Auto-generated method stub
		super.onCancelled();
		//复位
		progressbar.setProgress(0);
		textprogress.setText(0);
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		//dialog = ProgressDialog.show(context, "", "开始下载");
		//pb = context.
		
	}

	//@Overrid
	protected void onProgressUpdate(Integer... values) {
		super.onProgressUpdate(values);
		YokaLog.d("AsyDownLoadApp", "onProgressUpdate（）==values is "+values[0]);
		progressbar.setProgress(values[0]);
		textprogress.setText(values[0]+"%");
	}

	@Override
		protected Boolean doInBackground(String... params) {
			try {
				HttpURLConnection httpConnection = LCHttpUrlConnection.getHttpConnection(appURl,"");
				if(null !=httpConnection){
					int code = httpConnection.getResponseCode();
					if(200 == code){
						InputStream isStream = httpConnection.getInputStream();
						File file = new File(FileDir.APP_LOCAL_PATH_DIR,FileDir.APP_LOCAL_FILE_NAME);
						if(!file.exists())
							file.createNewFile();
						FileOutputStream fileoutputStream = new FileOutputStream(file);
						byte[] buffer = new byte[1024];
						fileoutputStream.write(buffer, 0, httpConnection.getContentLength());
						fileoutputStream.close();
						return true;
					}
				}
				return false;
			} catch (MalformedURLException e) {
				e.printStackTrace();
				return false;
			} catch (IOException e) {
								
				e.printStackTrace();
				return false;
			}
		}

	@Override
	protected void onPostExecute(Boolean result) {
		super.onPostExecute(result);
		/*if (null != dialog && dialog.isShowing()) {
			dialog.dismiss();
			dialog = null;
		}*/
	}

}