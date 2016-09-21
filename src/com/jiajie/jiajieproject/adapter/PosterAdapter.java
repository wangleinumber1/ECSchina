package com.jiajie.jiajieproject.adapter;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import com.jiajie.jiajieproject.R;
import com.jiajie.jiajieproject.model.BannerPic;
import com.jiajie.jiajieproject.utils.ScreenUtil;

/**
 * 广告位的适配器
 * 
 * @author Bruce.Wang
 * 
 */
public class PosterAdapter extends FragmentPagerAdapter {

	private List<BannerPic> posters;
	
	private List<Fragment> views;
	
	@Override
	public int getCount() {
		return posters.size();
	}

	public PosterAdapter(FragmentManager manager,List<BannerPic> posters) {
		super(manager);
		this.posters = posters;
		views = new ArrayList<Fragment>();
		for (int i = 0;i < posters.size(); i++ ) {
			PosterFragment frg = new PosterFragment();
			frg.setPoster(posters.get(i));
			views.add(frg);
		}
	}
	
	public void reset(List<BannerPic> posters){
		this.posters = posters;
		views = new ArrayList<Fragment>();
		for (int i = 0;i < posters.size(); i++ ) {
			PosterFragment frg = new PosterFragment();
			frg.setPoster(posters.get(i));
			views.add(frg);
		}
		notifyDataSetChanged();
	}
	
	
	@Override
	public Fragment getItem(int arg0) {
		return views.get(arg0);
	}
	
	public static class PosterFragment extends Fragment{
		
		private BannerPic poster;
		
		public PosterFragment(){}
		
		public void setPoster(BannerPic poster){
			this.poster = poster;
		}
		
		
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View view = inflater.inflate(R.layout.item_poster, null);
			return view;
		}
		
		@Override
		public void onViewCreated(View view, Bundle savedInstanceState) {
			super.onViewCreated(view, savedInstanceState);
			if (poster == null) {
				return;
			}
			final ImageView iv = (ImageView) view.findViewById(R.id.iv);
			LayoutParams ps = iv.getLayoutParams();
			
			ps.width = ScreenUtil.getWidth(getActivity());
			ps.height = ScreenUtil.getWidth(getActivity())*315/540;
			iv.setScaleType(ImageView.ScaleType.FIT_XY);
			iv.setLayoutParams(ps);
			iv.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					
					
				}
			});
			iv.setImageResource(poster.res);
		}

	}


}
