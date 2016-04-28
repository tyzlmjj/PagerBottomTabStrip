package me.majiajie.pagerbottomtabstrip;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.TypedValue;
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
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = (int) Utils.px2dp(mContext,getMeasuredWidth());
        int height = (int) Utils.px2dp(mContext,getMeasuredHeight());
        

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    public TabStripBuilder builder()
    {
        return new builder();
    }

    class builder implements TabStripBuilder
    {
        private int messageNumberColor;

        private int messageBackgroundColor;

        @Override
        public PagerBottomTabStrip build()
        {

            for(TabItem item:mTabItems)
            {
                if(messageNumberColor != 0) {
                    item.setMessageTextColor(messageNumberColor);
                }
                if(messageBackgroundColor != 0)
                {
                    item.setMessageBackgroundColor(messageBackgroundColor);
                }
                item.setMode(mMode);

                PagerBottomTabStrip.this.addView(item);
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
        mTabItems.add(new TabItem(mContext).builder()
                .setDefaultIcon(drawable)
                .setSelectedIcon(selectedDrawable)
                .setText(text)
                .setSelectedColor(selectedColor)
                .build());
    }
}
