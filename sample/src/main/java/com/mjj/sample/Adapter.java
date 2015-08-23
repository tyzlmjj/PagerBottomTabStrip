package com.mjj.sample;

import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class Adapter extends FragmentPagerAdapter {
	
     private List<Fragment> mFragmentList;
     private String[] titles = {"first","second","third","fourth"};
     
     public Adapter(FragmentManager fm,List<Fragment> fragList ) {
           super(fm );
           mFragmentList=fragList ;
     }
     
     @Override
     public Fragment getItem( int arg0 ) {
           return mFragmentList .get(arg0 );
     }
     @Override
     public int getCount() {
           return mFragmentList .size();
     }
     
     @Override
    public CharSequence getPageTitle(int position) {
    	return titles[position];
    }
     
}

