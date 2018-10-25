package me.majiajie.pagerbottomtabstriptest.custom;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;

import androidx.annotation.DrawableRes;
import androidx.core.content.ContextCompat;
import me.majiajie.pagerbottomtabstrip.item.BaseTabItem;
import me.majiajie.pagerbottomtabstriptest.R;

/**
 * 自定义一个只有图标的Item
 */
public class OnlyIconItemView extends BaseTabItem {

    private ImageView mIcon;

    private Drawable mDefaultDrawable;
    private Drawable mCheckedDrawable;

    private boolean mChecked;

    public OnlyIconItemView(Context context) {
        this(context, null);
    }

    public OnlyIconItemView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public OnlyIconItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        LayoutInflater.from(context).inflate(R.layout.item_only_icon, this, true);

        mIcon = findViewById(R.id.icon);
    }

    public void initialize(@DrawableRes int drawableRes, @DrawableRes int checkedDrawableRes) {
        mDefaultDrawable = ContextCompat.getDrawable(getContext(), drawableRes);
        mCheckedDrawable = ContextCompat.getDrawable(getContext(), checkedDrawableRes);
    }

    @Override
    public void setChecked(boolean checked) {
        mIcon.setImageDrawable(checked ? mCheckedDrawable : mDefaultDrawable);
        mChecked = checked;
    }

    @Override
    public void setMessageNumber(int number) {
        //不需要就不用管
    }

    @Override
    public void setHasMessage(boolean hasMessage) {
        //不需要就不用管
    }

    @Override
    public void setTitle(String title) {
        //不需要就不用管
    }

    @Override
    public void setDefaultDrawable(Drawable drawable) {
        mDefaultDrawable = drawable;
        if (!mChecked) {
            mIcon.setImageDrawable(drawable);
        }
    }

    @Override
    public void setSelectedDrawable(Drawable drawable) {
        mCheckedDrawable = drawable;
        if (mChecked) {
            mIcon.setImageDrawable(drawable);
        }
    }

    @Override
    public String getTitle() {
        return "no title";
    }
}
