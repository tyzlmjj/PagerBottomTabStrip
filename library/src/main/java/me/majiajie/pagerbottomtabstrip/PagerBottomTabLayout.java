package me.majiajie.pagerbottomtabstrip;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import me.majiajie.pagerbottomtabstrip.i.TabStripLinstener;

/**
 * 底部导航栏主布局
 */
public class PagerBottomTabLayout extends FrameLayout implements TabStripLinstener
{
    private Context mContext;

    private int mMode;

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

        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams
                (FrameLayout.LayoutParams.MATCH_PARENT,FrameLayout.LayoutParams.MATCH_PARENT);
        mPagerBottomTabStrip.setLayoutParams(lp);

        return mPagerBottomTabStrip.builder(PagerBottomTabLayout.this);
    }

    @Override
    public void onFinishBuild()
    {
        if((mPagerBottomTabStrip.mMode & TabStripMode.MULTIPLE_COLOR) > 0)
        {
            mChangeColorsView = new ChangeColorsView(mContext);

            mChangeColorsView.setBackgroundColor(
                    mPagerBottomTabStrip.mTabItems.get(mPagerBottomTabStrip.mIndex).getSelectedColor());

            PagerBottomTabLayout.this.addView(mChangeColorsView,0);

            FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams
                    (FrameLayout.LayoutParams.MATCH_PARENT,FrameLayout.LayoutParams.MATCH_PARENT);
            mChangeColorsView.setLayoutParams(lp);
        }
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

    private float touch_x;

    private float touch_y;
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev)
    {
        touch_x = ev.getX();
        touch_y = ev.getY();
        return super.onInterceptTouchEvent(ev);
    }
}
