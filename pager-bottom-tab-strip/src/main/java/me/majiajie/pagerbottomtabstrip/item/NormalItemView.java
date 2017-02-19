package me.majiajie.pagerbottomtabstrip.item;


import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import me.majiajie.pagerbottomtabstrip.R;

public class NormalItemView extends BaseTabItem {

    private final int mDefaultHeight;

    private ImageView mIcon;
    private final TextView mTitle;
    private final TextView mMessageView;
    private final View mOval;

    private Drawable mDefaultDrawable;
    private Drawable mCheckedDrawable;

    private boolean mChecked;

    public NormalItemView(Context context) {
        this(context,null);
    }

    public NormalItemView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public NormalItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mDefaultHeight = getResources().getDimensionPixelSize(R.dimen.material_bottom_navigation_height);

        LayoutInflater.from(context).inflate(R.layout.item_material, this, true);

        mIcon = (ImageView) findViewById(R.id.icon);
        mTitle = (TextView) findViewById(R.id.title);
        mMessageView = (TextView) findViewById(R.id.messages);
        mOval = findViewById(R.id.oval);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(getMeasuredWidth(), mDefaultHeight);
    }

    @Override
    void setChecked(boolean checked) {
        mChecked = checked;
        mIcon.setImageDrawable(mChecked ? mCheckedDrawable : mDefaultDrawable);
    }

    @Override
    void setTitle(String title) {
        mTitle.setText(title);
    }

    @Override
    void setIcon(Drawable drawable) {
        mDefaultDrawable = drawable;
        if(!mChecked){
            mIcon.setImageDrawable(mDefaultDrawable);
        }
    }

    @Override
    void setCheckedIcon(Drawable drawable) {
        mCheckedDrawable = drawable;
        if(mChecked)
        {
            mIcon.setImageDrawable(mCheckedDrawable);
        }
    }

    @Override
    void setMessageNumber(int number)
    {
        if(number > 0) {
            mMessageView.setVisibility(View.VISIBLE);
            mMessageView.setText(number + "");
        }
        else {
            mMessageView.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    void setHasMessage(boolean hasMessage) {
        mOval.setVisibility(hasMessage ? View.VISIBLE : View.INVISIBLE);
    }


    @Override
    void setColor(int color) {}

    @Override
    void setCheckedColor(int color) {}
}
