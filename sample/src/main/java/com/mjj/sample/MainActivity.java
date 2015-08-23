package com.mjj.sample;

import java.util.ArrayList;
import java.util.List;

import com.mjj.PagerBottomTabStrip;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.View;


public class MainActivity extends FragmentActivity{
	
	private int[] iconResid = {R.drawable.ic_bookmark_outline_grey600_24dp,
								R.drawable.ic_search_black_24dp,
								R.drawable.ic_notifications_none_grey600_24dp,
								R.drawable.ic_favorite_outline_grey600_24dp};
	
	private int[] iconResidClick = {R.drawable.ic_bookmark_black_24dp,
									R.drawable.ic_search_black_24dp,
									R.drawable.ic_notifications_black_24dp,
									R.drawable.ic_favorite_grey600_24dp};
	
	private ViewPager mViewPager;
	private PagerBottomTabStrip mPagerBottomTabStrip;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		initAdapter();
		
		mPagerBottomTabStrip = (PagerBottomTabStrip) findViewById(R.id.tab);
		
		mPagerBottomTabStrip.builder(mViewPager)
		.ColorMode()
		.TabIcon(iconResid)
		.TabBackground(R.drawable.background_tab)
		.TabClickIcon(iconResidClick)
		.TabPadding(5)
		.build();
	}
	
	private void initAdapter() {
		mViewPager = (ViewPager) findViewById(R.id.viewpager);
		List<Fragment> list = new ArrayList<Fragment>();
		for (int i = 0; i < 4; i++) {
			list.add(new TestFragment(i));
		}
		Adapter adapter = new Adapter(getSupportFragmentManager(), list);
		mViewPager.setAdapter(adapter);
	}
	
	public void message(View v){
		mPagerBottomTabStrip.setMessageNumber(mViewPager.getCurrentItem(), 90);
	}
	
	public void add(View v){
		mPagerBottomTabStrip.addMessageNumber(mViewPager.getCurrentItem(), 1);
	}
	
	public void remove(View v){
		mPagerBottomTabStrip.addMessageNumber(mViewPager.getCurrentItem(), -1);
	}
	private Boolean news = true;
	public void news(View v){
		mPagerBottomTabStrip.setNews(mViewPager.getCurrentItem(), news);
		news = news?false:true;
	}
}
