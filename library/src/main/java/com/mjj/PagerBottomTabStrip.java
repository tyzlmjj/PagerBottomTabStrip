package com.mjj;

import java.util.ArrayList;
import java.util.List;
import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.view.View;
import android.view.View.OnClickListener;

public class PagerBottomTabStrip extends LinearLayout implements OnClickListener, OnPageChangeListener,IMode{

	private List<Tabstrip> mTabstrips;

	private List<String> mTexts;

	private Context mContext;

	private ColorModeBuilder mColorModeBuilder;

	private DefaultModeBuilder mDefaultModeBuilder;

	private int mCount;

	private ViewPager mViewPager;

	private OnPageChangeListener mOnPageChangeListener;

	private float[] mBitmapAlpha;

	private int[] mMessageNumber;

	private boolean[] mNews;

	public PagerBottomTabStrip(Context context) {
		this(context,null);
	}

	public PagerBottomTabStrip(Context context, AttributeSet attrs) {
		this(context, attrs,0);
	}

	public PagerBottomTabStrip(Context context, AttributeSet attrs,
							   int defStyleAttr) {
		super(context, attrs, defStyleAttr);

		this.setOrientation(HORIZONTAL);
		mContext = context;

	}

	/**
	 * 从这里开始构建导航栏
	 * @param viewPager
	 * @return
	 */
	public IMode builder(ViewPager viewPager){
		mViewPager = viewPager;
		viewPager.addOnPageChangeListener(this);

		mTabstrips = new ArrayList<Tabstrip>();
		mTexts = new ArrayList<String>();

		mCount = viewPager.getAdapter().getCount();
		for (int i = 0; i < mCount; i++) {

			mTexts.add(viewPager.getAdapter().getPageTitle(i).toString());
			Tabstrip tabstrip = new Tabstrip(mContext);
			tabstrip.setTabText(mTexts.get(i));
			tabstrip.setTag(i);
			tabstrip.setOnClickListener(this);
			mTabstrips.add(tabstrip);
		}

		return this;
	}

	/**
	 * 设置按钮显示的消息数量
	 * @param postion 第几个按钮,从0开始。
	 * @param messageNumber 需要显示的消息数量
	 */
	public void setMessageNumber(int postion,int messageNumber){
		mTabstrips.get(postion).setMessageNumber(messageNumber);
	}

	/**
	 * 增减按钮显示的消息数量
	 * @param postion 第几个按钮,从0开始。
	 * @param messageNumber 需要增减的消息数量
	 */
	public void addMessageNumber(int postion,int messageNumber){
		mTabstrips.get(postion).addMessageNumber(messageNumber);
	}

	/**
	 * 控制无数字小圆点的显示与消失
	 * @param postion 第几个按钮,从0开始。
	 * @param news false消失,true显示。
	 */
	public void setNews(int postion,Boolean news){
		mTabstrips.get(postion).setNews(news);
	}

	/**
	 * 设置选中的Tab项，从0开始
	 * @param number
	 */
	public void setFocus(int number){
		if (number < mTabstrips.size()) {
			for (Tabstrip btn:mTabstrips){
				btn.setBitmapAlpha(0f);
			}
			mTabstrips.get(number).setBitmapAlpha(1.0f);
		}
	}

	/**
	 * 增加ViewPager滑动监听
	 * @param listener
	 */
	public void addOnPageChangeListener(OnPageChangeListener listener){
		mOnPageChangeListener = listener;
	}

	@Override
	public IBuilderDefault DefaultMode() {
		mDefaultModeBuilder = new DefaultModeBuilder(this, mTabstrips, mContext);
		return mDefaultModeBuilder;
	}

	@Override
	public IBuilderColor ColorMode() {
		mColorModeBuilder = new ColorModeBuilder(this, mTabstrips, mContext);
		return mColorModeBuilder;
	}

	@Override
	public void onClick(View v) {
		int n = (Integer) v.getTag();
		mViewPager.setCurrentItem(n,false);
		setFocus(n);
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {
		if (mOnPageChangeListener != null) {
			mOnPageChangeListener.onPageScrollStateChanged(arg0);
		}
	}

	@Override
	public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
		if (positionOffsetPixels != 0){
			mTabstrips.get(position).setBitmapAlpha(1 - positionOffset);
			mTabstrips.get(position+1).setBitmapAlpha(positionOffset);
		}

		if (mOnPageChangeListener != null) {
			mOnPageChangeListener.onPageScrolled(position,positionOffset,positionOffsetPixels);
		}
	}

	@Override
	public void onPageSelected(int arg0) {

		if (mOnPageChangeListener != null) {
			mOnPageChangeListener.onPageSelected(arg0);
		}
	}

	private static final String INSTANCE_STATUS = "instance_status";
	private static final String STATUS_ALPHAS = "status_alphas";
	private static final String STATUS_MESSAGENUMBER = "status_messagenumber";
	private static final String STATUS_NEWS = "status_news";

	@Override
	protected Parcelable onSaveInstanceState()
	{
		Bundle bundle = new Bundle();
		mBitmapAlpha = new float[mCount];
		mMessageNumber = new int[mCount];
		mNews = new boolean[mCount];
		for (int i = 0; i < mCount; i++) {
			mBitmapAlpha[i] = mTabstrips.get(i).getBitmapAlpha();
			mMessageNumber[i] = mTabstrips.get(i).getMessageNumber();
			mNews[i] = mTabstrips.get(i).getNews();
		}
		bundle.putParcelable(INSTANCE_STATUS, super.onSaveInstanceState());
		bundle.putFloatArray(STATUS_ALPHAS, mBitmapAlpha);
		bundle.putIntArray(STATUS_MESSAGENUMBER,mMessageNumber);
		bundle.putBooleanArray(STATUS_NEWS, mNews);
		return bundle;
	}

	@Override
	protected void onRestoreInstanceState(Parcelable state)
	{
		if (state instanceof Bundle)
		{
			Bundle bundle = (Bundle) state;
			mBitmapAlpha = bundle.getFloatArray(STATUS_ALPHAS);
			mMessageNumber = bundle.getIntArray(STATUS_MESSAGENUMBER);
			mNews = bundle.getBooleanArray(STATUS_NEWS);
			for (int i = 0; i < mBitmapAlpha.length; i++) {
				mTabstrips.get(i).setBitmapAlpha(mBitmapAlpha[i]);
				mTabstrips.get(i).setNews(mNews[i]);
				mTabstrips.get(i).setMessageNumber(mMessageNumber[i]);
			}
			super.onRestoreInstanceState(bundle.getParcelable(INSTANCE_STATUS));
			return;
		}
		super.onRestoreInstanceState(state);
	}

}
