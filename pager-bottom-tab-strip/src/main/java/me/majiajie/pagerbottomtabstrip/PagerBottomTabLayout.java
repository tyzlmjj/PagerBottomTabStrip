package me.majiajie.pagerbottomtabstrip;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.FrameLayout;

import me.majiajie.pagerbottomtabstrip.listener.OnTabItemSelectListener;


/**
 * 底部导航栏主布局
 */
public class PagerBottomTabLayout extends FrameLayout implements TabStripLinstener
{
    private Context mContext;

    private PagerBottomTabStrip mPagerBottomTabStrip;

    private ChangeColorsView mChangeColorsView;

    public PagerBottomTabLayout(Context context) {
        super(context);
        init(context);
    }

    public PagerBottomTabLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public PagerBottomTabLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        mContext = context;
        setBackgroundColor(Color.WHITE);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        setMeasuredDimension(getMeasuredWidth(), (int) Utils.dp2px(mContext,56));
    }

    /**
     * 构建导航栏
     * @return {@link TabStripBuild}
     */
    public TabStripBuild builder()
    {
        mPagerBottomTabStrip = new PagerBottomTabStrip(mContext);
        PagerBottomTabLayout.this.addView(mPagerBottomTabStrip);

        LayoutParams lp = new LayoutParams
                (LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        mPagerBottomTabStrip.setLayoutParams(lp);

        return mPagerBottomTabStrip.builder(PagerBottomTabLayout.this);
    }

    @Override
    public Controller onFinishBuild()
    {
        if((mPagerBottomTabStrip.mMode & TabLayoutMode.CHANGE_BACKGROUND_COLOR) > 0)
        {
            mChangeColorsView = new ChangeColorsView(mContext);

            mChangeColorsView.setBackgroundColor(
                    mPagerBottomTabStrip.mTabItems.get(mPagerBottomTabStrip.mIndex).getSelectedColor());

            PagerBottomTabLayout.this.addView(mChangeColorsView,0);

            LayoutParams lp = new LayoutParams
                    (LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
            mChangeColorsView.setLayoutParams(lp);
        }

        return mController;
    }

    @Override
    public void onSelect()
    {
        if(mChangeColorsView != null)
        {
            mChangeColorsView.addOvalColor(
                    mPagerBottomTabStrip.mTabItems.get(mPagerBottomTabStrip.mIndex).getSelectedColor(),touch_x,touch_y);
        }
    }

    @Override
    public void onSelect(float x, float y)
    {
        if(mChangeColorsView != null)
        {
            mChangeColorsView.addOvalColor(
                    mPagerBottomTabStrip.mTabItems.get(mPagerBottomTabStrip.mIndex).getSelectedColor(),x,y);
        }
    }

    @Override
    public void onNotMeasure(int color)
    {
        if(mChangeColorsView != null) {
            mChangeColorsView.setBackgroundColor(color);
        }
    }

    private float touch_x;

    private float touch_y;

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev)
    {
        touch_x = ev.getX();
        touch_y = ev.getY();
        return super.onInterceptTouchEvent(ev);
    }


    Controller mController = new Controller()
    {
        @Override
        public void addTabItemClickListener(OnTabItemSelectListener listener)
        {
            mPagerBottomTabStrip.mOnTabItemClickListener = listener;
        }

        @Override
        public void setMessageNumber(int index, int number)
        {
            mPagerBottomTabStrip.mTabItems.get(index).setMessageNumber(number);
        }

        @Override
        public void setMessageNumber(Object tag, int number)
        {
            TabItem tabItem = selectTag(tag);
            if(tabItem != null)
            {
                tabItem.setMessageNumber(number);
            }
        }

        @Override
        public void setDisplayOval(int index, boolean display)
        {
            mPagerBottomTabStrip.mTabItems.get(index).setDisplayOval(display);
        }

        @Override
        public void setDisplayOval(Object tag, boolean display)
        {
            TabItem tabItem = selectTag(tag);
            if(tabItem != null)
            {
                tabItem.setDisplayOval(display);
            }
        }

        @Override
        public void setSelect(int index)
        {
            mPagerBottomTabStrip.setSetectManually(index);
        }

        @Override
        public void setSelect(Object tag)
        {
            for(int i = 0; i < mPagerBottomTabStrip.mTabItems.size(); i++)
            {
                if(mPagerBottomTabStrip.mTabItems.get(i).getTag().equals(tag))
                {
                    setSelect(i);
                }
            }
        }

        @Override
        public int getSelected()
        {

            return mPagerBottomTabStrip.mIndex;
        }

        @Override
        public Object getSelectedTag()
        {
            return mPagerBottomTabStrip.mTabItems.get(mPagerBottomTabStrip.mIndex).getTag();
        }

        @Override
        public void setBackgroundColor(@ColorInt int color) {
            PagerBottomTabLayout.this.setBackgroundColor(color);
        }

        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
        @Override
        public void setBackground(Drawable drawable) {
            PagerBottomTabLayout.this.setBackground(drawable);
        }

        @Override
        public void setBackgroundResource(@DrawableRes int resid) {
            PagerBottomTabLayout.this.setBackgroundResource(resid);
        }

        private TabItem selectTag(Object tag)
        {
            for(TabItem tabItem:mPagerBottomTabStrip.mTabItems)
            {
                if(tabItem.getTag().equals(tag))
                {
                    return tabItem;
                }
            }
            return null;
        }
    };







}
