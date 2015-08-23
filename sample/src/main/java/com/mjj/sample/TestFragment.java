package com.mjj.sample;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.mjj.PagerBottomTabStrip;

public class TestFragment extends Fragment {
	
	int n;
	public TestFragment(){
		
	}
	
	public TestFragment(int n) {
		this.n = n;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View view = inflater.inflate(R.layout.fragment, container, false);
		
		TextView textView = (TextView) view.findViewById(R.id.text);
		textView.setText(n+"");
		
		return view;
	}
}
