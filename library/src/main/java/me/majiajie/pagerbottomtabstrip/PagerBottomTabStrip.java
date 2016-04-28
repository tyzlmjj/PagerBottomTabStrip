package me.majiajie.pagerbottomtabstrip;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import me.majiajie.library.R;

/**
 * 底部导航栏
 */
public class PagerBottomTabStrip extends LinearLayout
{
    private List<TabItem> mTabItems = new ArrayList<>();

    private Context mContext;

    private int mMode;

    private int mIndex = 0;


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
        setBackgroundColor(Color.WHITE);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(getMeasuredWidth(), (int) Utils.dp2px(mContext,56));

        if(mTabItems.size() <= 0)
        {
            return;
        }

        int width_dp = (int) Utils.px2dp(mContext,getMeasuredWidth());

        //设置按钮的初始宽度
        if((mMode & TabStripMode.HIDE_TEXT) > 0)
        {
            int max_w = HIDE_TEXT_MAX_WIDTH*(mTabItems.size()-1)+HIDE_TEXT_SELECTED_MAX_WIDTH;
            if(width_dp > max_w)
            {
                int leftPadding = (int) Utils.dp2px(mContext,(width_dp-max_w)/2f);
                setPadding(leftPadding,0,0,0);

                setTabItemWidth((int) Utils.dp2px(mContext,HIDE_TEXT_MAX_WIDTH));
                mTabItems.get(mIndex).getLayoutParams().width = (int) Utils.dp2px(mContext,HIDE_TEXT_SELECTED_MAX_WIDTH);
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
                setTabItemWidth((int) Utils.dp2px(mContext,default_width));
                mTabItems.get(mIndex).getLayoutParams().width = (int) Utils.dp2px(mContext,selected_width);
            }
        }
        else
        {
            int w = width_dp/mTabItems.size();
            if(w > DEFAULT_MAX_WIDTH)
            {
                int leftPadding = (int) Utils.dp2px(mContext,(w-DEFAULT_MAX_WIDTH)*mTabItems.size()/2f);
                setPadding(leftPadding,0,0,0);
                setTabItemWidth((int) Utils.dp2px(mContext,DEFAULT_MAX_WIDTH));
            }
            else
            {
                setTabItemWidth((int) Utils.dp2px(mContext,w));
            }
        }


        setSelect(mIndex);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    public TabStripBuilder builder()
    {
        return new builder();
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

    OnClickListener tabClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {

        }
    };

    /**
     * 设置
     * @param index
     */
    private void setSelect(int index)
    {
        if(mIndex == index || index >= mTabItems.size())
        {
            return;
        }

        for(int i = 0; i < mTabItems.size(); i++)
        {
            mTabItems.get(i).setSelect(i == index);
        }
    }

    class builder implements TabStripBuilder
    {
        private int messageNumberColor;

        private int messageBackgroundColor;

        @Override
        public PagerBottomTabStrip build()
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
            return PagerBottomTabStrip.this;
        }

        @Override
        public TabStripBuilder addTabItem(@DrawableRes int drawable, @NonNull String text)
        {
            addTabItem(ContextCompat.getDrawable(mContext,drawable),text);
            return builder.this;
        }

        @Override
        public TabStripBuilder addTabItem(@DrawableRes int drawable, @NonNull String text, @ColorInt int selectedColor)
        {
            addTabItem(ContextCompat.getDrawable(mContext,drawable),text,selectedColor);
            return builder.this;
        }

        @Override
        public TabStripBuilder addTabItem(@DrawableRes int drawable, @DrawableRes int selectedDrawable, @NonNull String text)
        {
            addTabItem(ContextCompat.getDrawable(mContext,drawable),ContextCompat.getDrawable(mContext,selectedDrawable),text);
            return builder.this;
        }

        @Override
        public TabStripBuilder addTabItem(@DrawableRes int drawable, @DrawableRes int selectedDrawable, @NonNull String text, @ColorInt int selectedColor)
        {
            addTabItem(ContextCompat.getDrawable(mContext,drawable),ContextCompat.getDrawable(mContext,selectedDrawable),text,selectedColor);
            return builder.this;
        }

        @Override
        public TabStripBuilder addTabItem(@NonNull Drawable drawable, @NonNull String text)
        {
            addItem(drawable,null,text);
            return builder.this;
        }

        @Override
        public TabStripBuilder addTabItem(@NonNull Drawable drawable, @NonNull String text, @ColorInt int selectedColor)
        {
            addItem(drawable,null,text,selectedColor);
            return builder.this;
        }

        @Override
        public TabStripBuilder addTabItem(@NonNull Drawable drawable, @NonNull Drawable selectedDrawable, @NonNull String text)
        {
            addItem(drawable,selectedDrawable,text);
            return builder.this;
        }

        @Override
        public TabStripBuilder addTabItem(@NonNull Drawable drawable, @NonNull Drawable selectedDrawable, @NonNull String text, @ColorInt int selectedColor)
        {
            addItem(drawable,selectedDrawable,text,selectedColor);
            return builder.this;
        }

        @Override
        public TabStripBuilder setMessageBackgroundColor(@ColorInt int color)
        {
            messageBackgroundColor = color;
            return builder.this;
        }

        @Override
        public TabStripBuilder setMessageNumberColor(@ColorInt int color)
        {
            messageNumberColor = color;
            return builder.this;
        }

        @Override
        public TabStripBuilder setMode(int tabStripMode)
        {
            mMode = tabStripMode;
            return builder.this;
        }
    }

    private void addItem(Drawable drawable,Drawable selectedDrawable,String text)
    {
        TypedValue typedValue = new TypedValue();
        mContext.getTheme().resolveAttribute(R.attr.colorAccent, typedValue, true);
        int selectedColor = ContextCompat.getColor(mContext,typedValue.resourceId);

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
