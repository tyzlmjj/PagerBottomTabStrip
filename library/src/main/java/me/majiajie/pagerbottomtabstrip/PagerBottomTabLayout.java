package me.majiajie.pagerbottomtabstrip;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

public class PagerBottomTabLayout extends FrameLayout
{
    private Context mContext;

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



    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);


    }
}
