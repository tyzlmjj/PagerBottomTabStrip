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
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import me.majiajie.library.R;
import me.majiajie.pagerbottomtabstrip.i.TabStripLinstener;

/**
 * 底部导航栏按钮存放
 */
class PagerBottomTabStrip extends LinearLayout
{
    public List<TabItem> mTabItems = new ArrayList<>();

    public int mMode;

    private Context mContext;

    private TabStripLinstener mTabStripLinstener;

    /**
     * 记录当前选中项
     */
    public int mIndex = 0;

    /**
     * 记录之前选中的项
     */
    private int mOldIndex = 0;

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


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        Log.i("asd","onMeasure");
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
        if((mMode & TabStripMode.HIDE_TEXT) > 0)
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

                if(selected_width > HIDE_TEXT_SELECTED_MAX_WIDTH)
                {
                    default_width = HIDE_TEXT_MIN_WIDTH + (selected_width - HIDE_TEXT_SELECTED_MAX_WIDTH)/(mTabItems.size()-1);
                    selected_width = HIDE_TEXT_SELECTED_MAX_WIDTH;
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

        //设置默认选中项
        mTabItems.get(mIndex).setSelect(true);

    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);

        Log.i("asd","onDraw");
        isCreated = true;

        if((mMode & TabStripMode.HIDE_TEXT) > 0 && mIndex != mOldIndex)
        {
            TabItem newView = mTabItems.get(mIndex);
            TabItem oldView = mTabItems.get(mOldIndex);

            int new_width  = newView.getWidth();
            int old_width  = oldView.getWidth();
            Log.i("asd","mIndex != mOldIndex");
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
    private void invalidateView()
    {
        if (Looper.getMainLooper() == Looper.myLooper())
        {
            Log.i("asd","invalidate();");
            invalidate();
        }
        else
        {
            postInvalidate();
        }
    }

    /**
     * 设置选中项
     * @param index 顺序索引
     */
    private void setSelect(int index)
    {
        if(mIndex == index || index >= mTabItems.size())
        {
            return;
        }

        mOldIndex = mIndex;
        mIndex = index;

        mTabStripLinstener.onSelect();

        for(int i = 0; i < mTabItems.size(); i++)
        {
            mTabItems.get(i).setSelect(i == mIndex);
        }

        if((mMode & TabStripMode.HIDE_TEXT) > 0)
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
        this.mTabStripLinstener = onFinishBuild;
        return new builder();
    }

    class builder implements TabStripBuild
    {
        private int messageNumberColor;

        private int messageBackgroundColor;

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

            //回调
            mTabStripLinstener.onFinishBuild();

            return null;
        }

        @Override
        public TabStripBuild addTabItem(@NotNull TabItem tabItem)
        {
            mTabItems.add(tabItem);
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
        mTabItems.add(new TabItem(mContext).builder()
                .setDefaultIcon(drawable)
                .setSelectedIcon(selectedDrawable)
                .setText(text)
                .setSelectedColor(selectedColor)
                .build());
    }
}
