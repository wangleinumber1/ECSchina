package com.jiajie.jiajieproject.adapter;

import java.util.ArrayList;

import com.jiajie.jiajieproject.R;
import com.jiajie.jiajieproject.model.Type;
import com.jiajie.jiajieproject.utils.ImageLoad;
import com.mrwujay.cascade.model.produceClass;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


public class GoldFragmentadapter extends BaseAdapter {
	// ����Context
		private LayoutInflater mInflater;
	    private ArrayList<produceClass> list=new ArrayList<produceClass>();
	    private Context context;
	    private ImageLoad mImageLoad;
		public GoldFragmentadapter(Context context,ImageLoad mImageLoad){
			mInflater=LayoutInflater.from(context);
			this.context=context;
			this.mImageLoad=mImageLoad;
		}
		
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
//			return list.size();
			return 10;
		}
		@Override
		public Object getItem(int position) {
			return position;
		}

		public void clearData() {
			list.clear();

		}

		public void setdata(ArrayList<produceClass> list) {
			this.list.addAll(list);
		}
		public ArrayList<produceClass> getdata(){
			return list;
		}
		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(final int position, View convertView, ViewGroup parent) {
			final MyView view;
			if(convertView==null){
				view=new MyView();
				convertView=mInflater.inflate(R.layout.goldfragmentlist_item, null);
				view.produce_Image=(ImageView)convertView.findViewById(R.id.produce_Image);
				view.producename=(TextView)convertView.findViewById(R.id.producename);
				view.produceprice=(TextView)convertView.findViewById(R.id.produceprice);
				view.producecount=(TextView)convertView.findViewById(R.id.producecount);
				convertView.setTag(view);
			}else{
				view=(MyView) convertView.getTag();
				
			}
//			mImageLoad.loadImg(view.produce_Image, list.get(position).image, R.drawable.jiazaitupian);
//			view.producename.setText(list.get(position).name);
	        return convertView;
		}

		
		private class MyView{
			private ImageView produce_Image;		
			private TextView producename,produceprice,producecount;
		}


		
		
}
