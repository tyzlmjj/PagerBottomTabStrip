package me.majiajie.pagerbottomtabstrip;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Looper;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import me.majiajie.pagerbottomtabstrip.listener.OnTabItemSelectListener;


/**
 * 底部导航栏按钮存放
 */
class PagerBottomTabStrip extends LinearLayout
{
    protected List<TabItem> mTabItems = new ArrayList<>();

    public int mMode;

    private Context mContext;

    public OnTabItemSelectListener mOnTabItemClickListener;

    private TabStripLinstener mTabStripLinstener;

    /**
     * 记录当前选中项
     */
    public int mIndex = 0;

    /**
     * 记录之前选中的项
     */
    public int mOldIndex = 0;

    /**
     * 用于记录选中项按钮的宽度
     */
    private int mSelectedWidth = 0;

    /**
     * 用于记录未选中项按钮的宽度
     */
    private int mDefultWidth = 0;

    private boolean isCreated = false;


    //Material Design 标准 按钮宽度限制
    private final int DEFAULT_MAX_WIDTH = 168;

    private final int DEFAULT_MIN_WIDTH = 104;

    private final int HIDE_TEXT_MAX_WIDTH = 96;

    private final int HIDE_TEXT_MIN_WIDTH = 64;

    private final int HIDE_TEXT_SELECTED_MAX_WIDTH = 168;

    private final int HIDE_TEXT_SELECTED_MIN_WIDTH = 96;


    public PagerBottomTabStrip(Context context)
    {
        super(context);
        init(context);
    }

    public PagerBottomTabStrip(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init(context);
    }

    public PagerBottomTabStrip(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context)
    {
        mContext = context;
        this.setOrientation(HORIZONTAL);
        this.setBackgroundColor(Color.TRANSPARENT);
    }

    private boolean once = false;
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        if(isCreated)
        {
            return;
        }

        //当导航按钮为0时不执行任何操作
        if(mTabItems.size() <= 0)
        {
            return;
        }

        //将导航栏宽度转换成DP,用于计算
        int width_dp = (int) Utils.px2dp(mContext,getMeasuredWidth());

        //设置按钮的初始宽度
        if((mMode & TabLayoutMode.HIDE_TEXT) > 0)
        {
            int max_w = HIDE_TEXT_MAX_WIDTH*(mTabItems.size()-1)+HIDE_TEXT_SELECTED_MAX_WIDTH;
            if(width_dp > max_w)
            {
                int leftPadding = (int) Utils.dp2px(mContext,(width_dp-max_w)/2f);
                setPadding(leftPadding,0,0,0);

                mSelectedWidth = (int) Utils.dp2px(mContext,HIDE_TEXT_SELECTED_MAX_WIDTH);
                mDefultWidth = (int) Utils.dp2px(mContext,HIDE_TEXT_MAX_WIDTH);
            }
            else
            {
                int default_width = HIDE_TEXT_MIN_WIDTH;
                int selected_width = width_dp - default_width*(mTabItems.size()-1);

                if(selected_width >= HIDE_TEXT_SELECTED_MAX_WIDTH)
                {
                    default_width = HIDE_TEXT_MIN_WIDTH + (selected_width - HIDE_TEXT_SELECTED_MAX_WIDTH)/(mTabItems.size()-1);

                    if(default_width <= HIDE_TEXT_MAX_WIDTH - 16)
                    {
                        selected_width = HIDE_TEXT_SELECTED_MAX_WIDTH -(mTabItems.size()-1)*16;
                        default_width +=16;
                    }
                    else
                    {
                        selected_width = HIDE_TEXT_SELECTED_MAX_WIDTH;
                    }
                }

                mDefultWidth = (int) Utils.dp2px(mContext,default_width);
                mSelectedWidth = (int) Utils.dp2px(mContext,selected_width);
            }

            setTabItemWidth(mDefultWidth);
            mTabItems.get(mIndex).getLayoutParams().width = mSelectedWidth;
        }
        else
        {
            int w = width_dp/mTabItems.size();
            if(w > DEFAULT_MAX_WIDTH)
            {
                int leftPadding = (int) Utils.dp2px(mContext,(w-DEFAULT_MAX_WIDTH)*mTabItems.size()/2f);
                setPadding(leftPadding,0,0,0);
                mSelectedWidth = mDefultWidth = (int) Utils.dp2px(mContext,DEFAULT_MAX_WIDTH);
            }
            else
            {
                mSelectedWidth = mDefultWidth = (int) Utils.dp2px(mContext,w);
            }

            setTabItemWidth(mDefultWidth);
        }

        if(!once)
        {
            once = true;

            //设置默认选中项
            mTabItems.get(mIndex).setSelect(true);

            if(mOnTabItemClickListener != null)
            {
                mOnTabItemClickListener.onSelected(mIndex,mTabItems.get(mIndex).getTag());
            }
        }

    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);

        isCreated = true;

        if((mMode & TabLayoutMode.HIDE_TEXT) > 0 && mIndex != mOldIndex)
        {
            TabItem newView = mTabItems.get(mIndex);
            TabItem oldView = mTabItems.get(mOldIndex);

            int new_width  = newView.getWidth();
            int old_width  = oldView.getWidth();
            if(old_width  != mDefultWidth || new_width != mSelectedWidth)
            {
                int n = mSelectedWidth - mDefultWidth;
                old_width -= n < 10 ? 1 : n/10;
                new_width += n < 10 ? 1 : n/10;

                old_width = old_width < mDefultWidth ? mDefultWidth:old_width;
                new_width = new_width > mSelectedWidth ? mSelectedWidth:new_width;

                ViewGroup.LayoutParams lp = newView.getLayoutParams();
                lp.width = new_width;
                newView.setLayoutParams(lp);

                ViewGroup.LayoutParams lp2 = oldView.getLayoutParams();
                lp2.width = old_width;
                oldView.setLayoutParams(lp2);

                invalidateView();
            }
        }
    }

    /**
     * 刷新视图
     */
    protected void invalidateView()
    {
        if (Looper.getMainLooper() == Looper.myLooper())
        {
            PagerBottomTabStrip.this.invalidate();
        }
        else
        {
            PagerBottomTabStrip.this.postInvalidate();
        }
    }

    /**
     * 手动设置选中项
     * @param index
     */
    protected void setSetectManually(int index)
    {
        if(mIndex == index )
        {
            return;
        }
        //不应该存在的索引错误
        if(index >= mTabItems.size())
        {
            return;
        }
        //判断是否还未执行过onMeasure(),此时宽度大小都没有计算过，为无法执行后面的计算
        if(mDefultWidth == 0)
        {
            mIndex = index;
            mTabStripLinstener.onNotMeasure(mTabItems.get(mIndex).getSelectedColor());
            return;
        }

        mOldIndex = mIndex;
        mIndex = index;

        //外部回调
        if(mOnTabItemClickListener != null)
        {
            mOnTabItemClickListener.onSelected(mIndex,mTabItems.get(mIndex).getTag());
        }

        //回调，主要用于背景颜色切换的模式下
        float x = mOldIndex > mIndex ? (mIndex+0.5f)*mDefultWidth : (mIndex-0.5f)*mDefultWidth+mSelectedWidth;
        float y = Utils.dp2px(mContext,28);
        mTabStripLinstener.onSelect(x,y);

        for(int i = 0; i < mTabItems.size(); i++)
        {
            mTabItems.get(i).setSelect(i == mIndex);
        }

        if((mMode & TabLayoutMode.HIDE_TEXT) > 0)
        {
            invalidateView();
        }
    }

    /**
     * 设置选中项
     * @param index 顺序索引
     */
    private void setSelect(int index)
    {
        //点击已选中项
        if(mIndex == index )
        {
            if(mOnTabItemClickListener != null)
            {
                mOnTabItemClickListener.onRepeatClick(mIndex,mTabItems.get(mIndex).getTag());
            }
            return;
        }

        //不应该存在的索引错误
        if(index >= mTabItems.size())
        {
            return;
        }

        mOldIndex = mIndex;
        mIndex = index;
        //外部回调
        if(mOnTabItemClickListener != null)
        {
            mOnTabItemClickListener.onSelected(mIndex,mTabItems.get(mIndex).getTag());
        }

        //回调，主要用于背景颜色切换的模式下
        mTabStripLinstener.onSelect();

        for(int i = 0; i < mTabItems.size(); i++)
        {
            mTabItems.get(i).setSelect(i == mIndex);
        }

        if((mMode & TabLayoutMode.HIDE_TEXT) > 0)
        {
            invalidateView();
        }
    }

    /**
     * 设置导航按钮的宽度
     * @param width
     */
    private void setTabItemWidth(int width)
    {
        for(TabItem item:mTabItems)
        {
            item.getLayoutParams().width = width;
        }
    }

    protected TabStripBuild builder(TabStripLinstener onFinishBuild)
    {
        mTabItems.clear();
        PagerBottomTabStrip.this.removeAllViews();
        this.mTabStripLinstener = onFinishBuild;
        return new builder();
    }

    class builder implements TabStripBuild
    {
        private int messageNumberColor;

        private int messageBackgroundColor;

        private int defaultColor;

        @Override
        public Controller build()
        {
            for(int i = 0; i < mTabItems.size(); i++)
            {
                TabItem item = mTabItems.get(i);

                if(messageNumberColor != 0) {
                    item.setMessageTextColor(messageNumberColor);
                }

                if(messageBackgroundColor != 0)
                {
                    item.setMessageBackgroundColor(messageBackgroundColor);
                }

                if(defaultColor != 0)
                {
                    item.setDefaultColor(defaultColor);
                }

                item.setMode(mMode);

                PagerBottomTabStrip.this.addView(item);

                final int finalI = i;

                item.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        setSelect(finalI);
                    }
                });
            }

            return mTabStripLinstener.onFinishBuild();
        }

        @Override
        public TabStripBuild addTabItem(@NotNull TabItemBuilder tabItemBuilder)
        {
            mTabItems.add(tabItemBuilder.getTabItem());
            return builder.this;
        }

        @Override
        public TabStripBuild addTabItem(@DrawableRes int drawable, @NonNull String text)
        {
            addTabItem(ContextCompat.getDrawable(mContext,drawable),text);
            return builder.this;
        }

        @Override
        public TabStripBuild addTabItem(@DrawableRes int drawable, @NonNull String text, @ColorInt int selectedColor)
        {
            addTabItem(ContextCompat.getDrawable(mContext,drawable),text,selectedColor);
            return builder.this;
        }

        @Override
        public TabStripBuild addTabItem(@DrawableRes int drawable, @DrawableRes int selectedDrawable, @NonNull String text)
        {
            addTabItem(ContextCompat.getDrawable(mContext,drawable),ContextCompat.getDrawable(mContext,selectedDrawable),text);
            return builder.this;
        }

        @Override
        public TabStripBuild addTabItem(@DrawableRes int drawable, @DrawableRes int selectedDrawable, @NonNull String text, @ColorInt int selectedColor)
        {
            addTabItem(ContextCompat.getDrawable(mContext,drawable),ContextCompat.getDrawable(mContext,selectedDrawable),text,selectedColor);
            return builder.this;
        }

        @Override
        public TabStripBuild addTabItem(@NonNull Drawable drawable, @NonNull String text)
        {
            addItem(drawable,null,text);
            return builder.this;
        }

        @Override
        public TabStripBuild addTabItem(@NonNull Drawable drawable, @NonNull String text, @ColorInt int selectedColor)
        {
            addItem(drawable,null,text,selectedColor);
            return builder.this;
        }

        @Override
        public TabStripBuild addTabItem(@NonNull Drawable drawable, @NonNull Drawable selectedDrawable, @NonNull String text)
        {
            addItem(drawable,selectedDrawable,text);
            return builder.this;
        }

        @Override
        public TabStripBuild addTabItem(@NonNull Drawable drawable, @NonNull Drawable selectedDrawable, @NonNull String text, @ColorInt int selectedColor)
        {
            addItem(drawable,selectedDrawable,text,selectedColor);
            return builder.this;
        }

        @Override
        public TabStripBuild setMessageBackgroundColor(@ColorInt int color)
        {
            messageBackgroundColor = color;
            return builder.this;
        }

        @Override
        public TabStripBuild setMessageNumberColor(@ColorInt int color)
        {
            messageNumberColor = color;
            return builder.this;
        }

        @Override
        public TabStripBuild setDefaultColor(@ColorInt int color)
        {
            defaultColor = color;
            return builder.this;
        }

        @Override
        public TabStripBuild setMode(int tabStripMode)
        {
            mMode = tabStripMode;
            return builder.this;
        }
    }

    private void addItem(Drawable drawable,Drawable selectedDrawable,String text)
    {
        int selectedColor = Utils.getAttrColor(mContext,R.attr.colorAccent);

        addItem(drawable,selectedDrawable,text,selectedColor);
    }

    private void addItem(Drawable drawable,Drawable selectedDrawable,String text,int selectedColor)
    {
        if(selectedDrawable == null)
        {
            selectedDrawable = drawable;
        }
        mTabItems.add(new TabItemBuilder(mContext).create()
                .setDefaultIcon(drawable)
                .setSelectedIcon(selectedDrawable)
                .setText(text)
                .setSelectedColor(selectedColor)
                .build().getTabItem());
    }






}
