package com.jiajie.jiajieproject.widget;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.jiajie.jiajieproject.R;

public class MyProgressDialog {
	
	Dialog dialog;
	Context context;
	ImageView imageView1;
	ImageView imageView2;
	ImageView imageView3;
	ImageView imageView4;
	ImageView imageView5;
	boolean flags;
	ImageView []imageView;
	int i=1;
	/**
	 * ����
	 */
	 public MyProgressDialog(Context context) {
				// TODO Auto-generated constructor stub
		 this.context=context;
		 flags=true;
		 dialog=new Dialog(context,R.style.dialog);
		 dialog.setOnCancelListener(onCancelListener);
		 dialog.setCanceledOnTouchOutside(false);
	 }
	 /**
	  * ��ʼ����ȶԻ���
	  */
	 public void initDialog(){
		 
		 LayoutInflater inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		 View view=inflater.inflate(R.layout.myprogressdialog, null);
		 dialog.setContentView(view);
		
		 imageView1=(ImageView)dialog.findViewById(R.id.progress_imageview1);
		 imageView2=(ImageView)dialog.findViewById(R.id.progress_imageview2);
		 imageView3=(ImageView)dialog.findViewById(R.id.progress_imageview3);
		 imageView4=(ImageView)dialog.findViewById(R.id.progress_imageview4);
		 imageView5=(ImageView)dialog.findViewById(R.id.progress_imageview5);
		 imageView=new ImageView[]{imageView1,imageView2,imageView3,imageView4,imageView5};
		 new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				
				try {
					Thread.sleep(200);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				while(flags){
					handler.sendEmptyMessage(0);
					try {
						Thread.sleep(300);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				flags=true;
			}
		}).start();
		 
			 dialog.show();
		 
		
	 }
	 
	 Handler handler=new Handler(){

			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				System.out.println("��̨"+i);
				imageView[i].setImageDrawable(context.getResources().getDrawable(R.drawable.progress_image2));
				imageView[(i+5-1)%5].setImageDrawable(context.getResources().getDrawable(R.drawable.progress_image1));
				i++;
				i=(i+5)%5;
				super.handleMessage(msg);
			}
			 
		 };
	public void colseDialog(){
		flags=false;
		dialog.dismiss();
	}
	public boolean isShowing(){
		if(dialog.isShowing()){
			return true;
		}
		else{
			return false;
		}
	}
	OnCancelListener onCancelListener=new OnCancelListener() {
		
		@Override
		public void onCancel(DialogInterface dialog) {
			// TODO Auto-generated method stub
			flags=false;
			dialog.dismiss();
			System.out.println("cancel!!!!");
		}
	};
}
